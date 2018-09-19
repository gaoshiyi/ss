package com.sstc.hmis.conf.cas.session;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.util.XmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSON;
import com.sstc.hmis.conf.cas.MyCasRealm;
import com.sstc.hmis.model.constants.PortalConstants;
import com.sstc.hmis.permission.data.Staff;
import com.sstc.hmis.statusSync.handler.UserSessionHandler;

/**
 * Performs CAS single sign-out operations in an API-agnostic fashion.
 * 参考自{@link org.jasig.cas.client.session.SingleSignOutHandler}
 *
 */
public final class SingleSignOutHandler {

    /** Logger instance */
    private final Logger log = LoggerFactory.getLogger(SingleSignOutHandler.class);
    
    /** Parameter name that stores logout request */
    private String logoutParameterName = "logoutRequest";

    private UserSessionHandler storage = new UserSessionHandler();

	private RedisTemplate<String, String> redisTemplate;
    
	public void setRedisTemplate(RedisTemplate<String,String> redisTemplate) {
		this.redisTemplate = redisTemplate;
		storage.setRedisTemplate(redisTemplate);
	}
	protected SingleSignOutHandler(){
        init();
    }

    public void setLogoutParameterName(final String name) {
        this.logoutParameterName = name;
    }
    protected String getLogoutParameterName() {
        return this.logoutParameterName;
    }

    /**
     * Initializes the component for use.
     */
    public void init() {
        CommonUtils.assertNotNull(this.logoutParameterName, "logoutParameterName cannot be null.");
    }

    /**
     * 判断是否为认证中心回调的退出请求
     * @param request
     * @return
     */
    public boolean isLogoutRequest(final HttpServletRequest request) {
        return "POST".equals(request.getMethod()) && !isMultipartRequest(request) &&
            CommonUtils.isNotBlank(CommonUtils.safeGetParameter(request, this.logoutParameterName));
    }
    
    /**
     * 判断是否为本地系统发起的退出请求
     * @param request
     * @return
     */
    public boolean isLocalLogoutRequest(final HttpServletRequest request) {
    	return "GET".equals(request.getMethod()) && !isMultipartRequest(request) &&
    			StringUtils.equals(request.getRequestURI(), "/logout");
    }

    /**
     * 判断是否是ticket校验请求
     * @param request
     * @return
     */
    public boolean isTokenRequest(final HttpServletRequest request) {
        return CommonUtils.isNotBlank(CommonUtils.safeGetParameter(request, UserSessionHandler.ST_PARAM_NAME));
    }

    /**
     * 从logoutRequest参数中解析出token，根据token获取到sessionID，再根据sessionID获取到session，设置logoutRequest参数为true
     * 从而标记此session已经失效。
     * @param request HTTP request containing a CAS logout message.
     * @param response 
     */
    public void invalidateSession(final HttpServletRequest request, HttpServletResponse response, final SessionManager sessionManager) {
        final String logoutMessage = CommonUtils.safeGetParameter(request, this.logoutParameterName);
        log.debug("认证中心发起退出回调请求 ：{}", logoutMessage);
        final String token = XmlUtils.getTextForElement(logoutMessage, "SessionIndex");
        if (CommonUtils.isNotBlank(token)) {
            Serializable sessionId = storage.getSessionIDByMappingId(token);
//        	String sessionId = (String) redisTemplate.opsForValue().get(token);
            try {
            	clearSession(sessionId,sessionManager,request,response);
			} catch (Exception e) {
				log.error("清理session异常,id为:{}",sessionId);
				e.printStackTrace();
			}
        }
    }


	public void clearSession(Serializable sessionId,final SessionManager sessionManager,
			final ServletRequest request, final ServletResponse response){
		if (sessionId!=null) {
            Session session = sessionManager.getSession(new DefaultSessionKey(sessionId));
            if(session != null) {
            	Staff staff = (Staff) session.getAttribute(PortalConstants.SESSION_USER_KEY);
            	if(staff != null && StringUtils.isNotBlank(staff.getAccount())){
            		String account = staff.getAccount();
            		UserSessionHandler sessionHandler = new UserSessionHandler(redisTemplate);
            		sessionHandler.destroyAllUserSession(account);
            	}
            	DefaultWebSessionManager dwsm = (DefaultWebSessionManager) sessionManager; 
    			SessionKey sessionKey = new WebSessionKey(sessionId, request, response);
    			dwsm.stop(sessionKey);
    			storage.removeSessionById(sessionId.toString());
    			
                RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        		MyCasRealm casRealm = (MyCasRealm) securityManager.getRealms().iterator().next();
        		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        		log.debug("需要清理的Principal信息:{}",JSON.toJSONString(principals));
        		casRealm.clearCachedAuthenticationInfo(principals);
        		casRealm.clearCachedAuthorizationInfo(principals);
            }
		}
	}
	
    private boolean isMultipartRequest(final HttpServletRequest request) {
        return request.getContentType() != null && request.getContentType().toLowerCase().startsWith("multipart");
    }
}
