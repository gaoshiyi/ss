/**
 * 
 */
package com.sstc.hmis.statusSync.handler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.util.Assert;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.alibaba.fastjson.JSON;
import com.sstc.hmis.model.constants.PortalConstants;
import com.sstc.hmis.permission.cas.CasConstants;
import com.sstc.hmis.permission.data.Staff;
import com.sstc.hmis.permission.data.cas.PrincipalVo;
import com.sstc.hmis.permission.web.LoginInfoHolder;
import com.sstc.hmis.statusSync.model.SessionUpdateInfo;
import com.sstc.hmis.statusSync.model.UserSession;

/**
  * <p> Title: UserSessionHandler </p>
  * <p> Description:  用户session处理器 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年9月6日 下午1:49:07
   */
public class UserSessionHandler {

	public static final String PARAM_NAME_SYNC_TYPE = "sync";
	public static final String PARAM_NAME_SID = "sid";
	public static final String PARAM_NAME_DATA = "data";
	
	public static final String SYNC_LOCALE = "locale";
	public static final String SYNC_SESSION = "session";
	public static final String SYNC_STAFF = "staff";
	
	public static final String ST_PARAM_NAME = "ticket";
	
	protected static final String[] SYNC_DATA = new String[]{SYNC_STAFF,SYNC_LOCALE};
	
	public static final int LONG_CACHE_HOURS_TIME = 6;
	
	private RedisTemplate<String,String> redisTemplate;
	
	private StringRedisTemplate stringRedisTemplate;
	
	
	public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String,String> redisTemplate){
		this.redisTemplate = redisTemplate;
	}
	
	public UserSessionHandler() {
	}

	public UserSessionHandler(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}


	private static final Logger LOG = LoggerFactory.getLogger(UserSessionHandler.class);
	/**
	 * 是否是发送用户session映射信息
	 * @param req   uri/sync?sync=session
	 * @return
	 */
	@Deprecated
	public boolean isSessionSendReq(HttpServletRequest req) {
		String sync = req.getParameter(PARAM_NAME_SYNC_TYPE);
		if(StringUtils.isNotBlank(sync) && StringUtils.equals(SYNC_SESSION, sync)){
			return true;
		}
		return false;
	}
	
    
	
	/**
	 * 根据ST查找SessionId
	 * @param ticket
	 * @return
	 */
	public Serializable getSessionIDByMappingId(final String ticket) {
		String key = CasConstants.getStSessionIdCacheKey(ticket);
		return redisTemplate.opsForValue().get(key);
	}
	
	/**
	 * 根据ticket从缓存中移除SessionId
	 * @param string
	 */
	public void removeSessionById(final String ticket) {
		redisTemplate.delete(CasConstants.getStSessionIdCacheKey(ticket));
	}
    
    
	/**
	 * 发送 UserSession映射信息,记录到portal的缓存中
	 * @param session
	 */
	@Deprecated
	public void sessionSendReq(UserSession session,String portalUri){
		Assert.notNull(session, "映射信息不能为空");
		Map<String,String> params = new HashMap<>();
		params.put(PARAM_NAME_SYNC_TYPE, SYNC_SESSION);
		params.put(SYNC_SESSION, JSON.toJSONString(session));
		try {
			com.sstc.hmis.util.HttpClientUtils.methodPost(portalUri+"/"+PARAM_NAME_SYNC_TYPE, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 记录用户登录帐号与session映射信息
	 * @param req
	 * @param res 
	 * @param redisManager 
	 */
	@Deprecated
	public void recordUserSessionInfo(HttpServletRequest req, HttpServletResponse res) {
		String data = req.getParameter(SYNC_SESSION);
		UserSession session = JSON.parseObject(data, UserSession.class);
		LOG.debug("请求类型:{},请求数据:{}",data, JSON.toJSONString(session));
		recordUserSessionInfo(session);
		res.setStatus(HttpServletResponse.SC_OK);
	}
	
	/**
	 * 记录用户登录帐号与session映射信息
	 * @param session
	 */
	public void recordUserSessionInfo(UserSession session){
		if(session != null){
			String account = session.getAccount();
			Assert.hasText(account, "帐号不能为空");
			Assert.notNull(redisTemplate, "stringRedisTemplate 不能为空");
			
			List<UserSession> list = getAccountSessionCache(account);
			
//			String sessionid = session.getSessionId();
//			boolean isExist = false;
//			for (UserSession userSession : list) {
//				String sid = userSession.getSessionId();
//				if(StringUtils.equals(sessionid, sid) || (
//								StringUtils.equals(userSession.getAccount(), account)
//								&& StringUtils.equals(userSession.getCasServiceUri(), session.getCasServiceUri())
//								) ){
//					isExist = true;
//					userSession.setSessionId(sessionid);
//					userSession.setServiceTicket(session.getServiceTicket());
//					break;
//				}
//			}
//			if(!isExist){
				list.add(session);
//			}
			CasConstants.setAccountSessionCache(account, list, redisTemplate);
		}
	}

	/**
	 * 是否为数据同步请求
	 * @param req
	 * @return
	 */
	@Deprecated
	public boolean isDataSyncReq(HttpServletRequest req) {
		String sync = req.getParameter(PARAM_NAME_SYNC_TYPE);
		if(StringUtils.isNotBlank(sync) && ArrayUtils.contains(SYNC_DATA, sync)){
			return true;
		}
		return false;
	}

	/**
	 * 广播数据更新
	 * @param req
	 * @param res
	 */
	public void broadcastDataChange(SessionUpdateInfo info,String syncType,SessionManager sessionManager ) {
		
		Assert.notNull(info, "待更新的SessionUpdateInfo不能为空");
		
		String sessionId = LoginInfoHolder.getSessionId();
		String tgtId = CasConstants.getTgtBySid(sessionId, stringRedisTemplate);
		if(StringUtils.isNotBlank(tgtId)){
			List<String> sidList = CasConstants.getSidListByTgt(tgtId, stringRedisTemplate);
			for (String sid : sidList) {
				LOG.info("需要更新的sid={}",sid);
				try {
					DefaultWebSessionManager dwsm = (DefaultWebSessionManager) sessionManager; 
					Session session = dwsm.getSessionDAO().readSession(sid);
					Assert.notNull(session,"需要更新的session不存在");
					//同步的数据
					Staff staff = (Staff) session.getAttribute(PortalConstants.SESSION_USER_KEY);
					//切换语言
					Locale locale = Locale.CHINA;
					if("en".equals(info.getLocale())){
						locale = Locale.ENGLISH;
					}
					session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
					staff.setHotelId(info.getHotelId());
					staff.setShiftId(info.getShiftId());
					staff.setShiftCode(info.getShiftCode());
					session.setAttribute(PortalConstants.SESSION_USER_KEY, staff);
					LOG.debug("更新 {}用户 session为 {} 的信息:{}",staff.getAccount(),sid, JSON.toJSONString(info));
					dwsm.getSessionDAO().update(session);
				} catch (Exception e) {
					LOG.error("更新session数据错误:[{}]",sid);
					LOG.error(e.getMessage());
				}
			}
		}
		final PrincipalVo vo = new PrincipalVo(info.getAccount(),info.getHotelId(),info.getLocale(), info.getShiftCode(), info.getShiftId());
		CasConstants.updatePrincipalInfo(vo, info.getAccount(), tgtId, stringRedisTemplate);
		
	}
	
	/**
	 * 获取缓存中存储的帐号在各系统的登录信息
	 * @param account 帐号
	 * @return 登录session
	 */
	public List<UserSession> getAccountSessionCache(String account){
		String key = CasConstants.getPrincipalCacheKey(account);
		String json = redisTemplate.opsForValue().get(key);
		try {
			Assert.hasText(json, "缓存中没有帐号 " + account + " 在各系统的登录信息");
		} catch (Exception e) {
			return new ArrayList<UserSession>();
		}
		return JSON.parseArray(json, UserSession.class);
	}
	

	/**
	 * session数据更新
	 * @param req
	 * @param res
	 * @param sessionManager
	 */
	public void dataUpdate(Map<String,Object> params, SessionManager sessionManager) {
		String sid = (String) params.get(PARAM_NAME_SID);
		if(StringUtils.isNotEmpty(sid)){
			try {
				DefaultWebSessionManager dwsm = (DefaultWebSessionManager) sessionManager; 
				Session session = dwsm.getSessionDAO().readSession(sid);
				Assert.notNull(session,"需要更新的session不存在");
				//同步的数据
				String data = (String) params.get(PARAM_NAME_DATA);
				if(StringUtils.isNotBlank(data)){
					SessionUpdateInfo info = JSON.parseObject(data, SessionUpdateInfo.class);
					Staff staff = (Staff) session.getAttribute(PortalConstants.SESSION_USER_KEY);
					//切换语言
					Locale locale = Locale.CHINA;
					if("en".equals(info.getLocale())){
						locale = Locale.ENGLISH;
					}
					session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
					staff.setHotelId(info.getHotelId());
					staff.setShiftId(info.getShiftId());
					staff.setShiftCode(info.getShiftCode());
					session.setAttribute(PortalConstants.SESSION_USER_KEY, staff);
					LOG.debug("更新 {}用户 session为 {} 的信息:{}",staff.getAccount(),sid,data);
					dwsm.getSessionDAO().update(session);
				}
			} catch (Exception e) {
				LOG.error("更新session数据错误:[{}]",sid);
				LOG.error(e.getMessage());
			}
		}
	}
	
	/**
	 * 销毁用户session 相关的缓存信息
	 * @param account 用户帐号
	 */
	public void destroyAllUserSession(String account){
		try {
			Set<String> keys = redisTemplate.keys("*:" + account + ":*");
			LOG.debug("用户帐号{}退出，根据key销毁所有该账户缓存:{}",account,JSON.toJSONString(keys));
			redisTemplate.delete(keys);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delSession(SessionManager sessionManager,final String sid){
		DefaultWebSessionManager dwsm = (DefaultWebSessionManager) sessionManager; 
		Session session = dwsm.getSessionDAO().readSession(sid);
		dwsm.getSessionDAO().delete(session);
	}

}
