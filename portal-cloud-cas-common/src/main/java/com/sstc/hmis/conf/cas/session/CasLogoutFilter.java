package com.sstc.hmis.conf.cas.session;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import com.sstc.hmis.conf.cas.MyCasRealm;
import com.sstc.hmis.permission.cas.CasConstants;
import com.sstc.hmis.permission.web.LoginInfoHolder;
import com.sstc.hmis.statusSync.handler.UserSessionHandler;

public class CasLogoutFilter extends LogoutFilter {

	private static final Logger log = LoggerFactory.getLogger(CasLogoutFilter.class);

	private SingleSignOutHandler HANDLER = new SingleSignOutHandler();
	
	private UserSessionHandler sessionHandler = new UserSessionHandler();
	
	public static final String DEFAULT_REDIRECT_URL = "/";

	private String redirectUrl = DEFAULT_REDIRECT_URL;
	
	private SessionManager sessionManager;
	
	private RedisTemplate<String, String> redisTemplate;
	
	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
		Assert.notNull(redisTemplate, "redisTemplate不能为Null");
		this.redisTemplate = redisTemplate;
		this.HANDLER.setRedisTemplate(redisTemplate);
		this.sessionHandler.setRedisTemplate(redisTemplate);
	}

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
//		Ticket校验时候的SessionId和实际访问的SessionId不一样，不知道为什么
		if(HANDLER.isTokenRequest(req)){
            return true;
		}else if(HANDLER.isLogoutRequest(req)){
			log.debug("这个请求是认证中心的回调退出请求:{}",req.getAttribute("logoutRequest"));
			HANDLER.invalidateSession(req,res,sessionManager);
			return false;
		}else if(HANDLER.isLocalLogoutRequest(req)){
			Subject subject = getSubject(req, res);
			String account = (String) subject.getPrincipal();
			log.info("来源IP [{}] 帐号 [{}]注销应用",req.getRemoteAddr(),account);
			if (subject != null) {
				try {
					RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
					// 清除缓存
					MyCasRealm casRealm = (MyCasRealm) securityManager.getRealms().iterator().next();
					PrincipalCollection principals = subject.getPrincipals();
					casRealm.clearCachedAuthorizationInfo(principals);
					casRealm.clearCachedAuthenticationInfo(principals);
					// 登出Subject
					subject.logout();
//					Map<String,Object> msg = new HashMap<>(2);
//					msg.put("account", account);
//					msg.put("type", MqConstants.MSG_TYPE_LOGOUT);
					
//					msgQueueService.sender(MqConstants.QUEUE_KICKOUT, JSON.toJSONString(msg));
					
					// 清除Session
					final String sessionId = LoginInfoHolder.getSessionId();
					final String tgtId = CasConstants.getTgtBySid(sessionId, redisTemplate);
					List<String> sidList = CasConstants.getSidListByTgt(tgtId, redisTemplate);
					if(CollectionUtils.isNotEmpty(sidList)){
						try {
							sidList.stream()
									.filter(sid -> StringUtils.isNotBlank(sid))
									.forEach(sid -> {
											HANDLER.clearSession(sid, securityManager, request, response);
							});
						} catch (Exception e) {
							log.error("session 清除异常,清理的Session信息是:{}", e);
						}
					}
				} catch (SessionException ise) {
					log.error("清理帐号{}的Session信息异常:{}",account, ise);
				}
			}
			WebUtils.issueRedirect(request, response, redirectUrl);
			return false;
		}
		return true;
	}

	@Override
	public String getRedirectUrl() {
		return redirectUrl;
	}

	@Override
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

}
