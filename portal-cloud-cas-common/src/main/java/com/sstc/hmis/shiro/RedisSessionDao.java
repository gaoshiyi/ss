/**
 * 
 */
package com.sstc.hmis.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
  * <p> Title: RedisSessionDao </p>
  * <p> Description:  TODO </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年9月11日 下午7:51:52
   */
public class RedisSessionDao extends AbstractSessionDAO{

	private static Logger logger = LoggerFactory.getLogger(RedisSessionDao.class);

	private RedisManager redisManager;
	
	private RedisTemplate<String, String> redisTemplate;
	
	private String keyPrefix = "session:shiro";
	
	@Override
	public void update(Session session) throws UnknownSessionException {
		this.saveSession(session);
	}
	
	/**
	 * save session
	 * @param session
	 * @throws UnknownSessionException
	 */
	private void saveSession(Session session) throws UnknownSessionException{
		if(session == null || session.getId() == null){
			logger.error("session or session id is null");
			return;
		}
		
		byte[] key = getByteKey(session.getId());
		byte[] value = SerializeUtils.serialize(session);
//		session.setTimeout((long)redisManager.getExpire()*1000);		
		this.redisManager.set(key, value, redisManager.getExpire());
//		this.redisManager.set(session.getId().toString(), JSON.toJSONString(session), redisManager.getExpire());
	}

	@Override
	public void delete(Session session) {
		if(session == null || session.getId() == null){
			logger.error("session or session id is null");
			return;
		}
		redisManager.del(this.getByteKey(session.getId()));
//		redisManager.del(session.getId().toString());
	}

	@Override
	public Collection<Session> getActiveSessions() {
		Set<Session> sessions = new HashSet<Session>();
		
		Set<byte[]> keys = redisManager.keys(this.keyPrefix + "*");
//		Set<String> keys = redisManager.keysString(this.keyPrefix + "*");
		if(keys != null && keys.size()>0){
			for(byte[] key:keys){
//			for(String key:keys){
				Session s = (Session)SerializeUtils.deserialize(redisManager.get(key));
//				Session s = JSON.parseObject(redisManager.get(key), Session.class);
				sessions.add(s);
			}
		}
		
		return sessions;
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = this.generateSessionId(session);  
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		if(sessionId == null){
			logger.error("session id is null");
			return null;
		}
		
		Session s = (Session)SerializeUtils.deserialize(redisManager.get(this.getByteKey(sessionId)));
//		String sessionStr = redisManager.get(sessionId.toString());
//		Session s = null;
//		if(StringUtils.isNotBlank(sessionStr)){
//			s = JSON.parseObject(sessionStr, Session.class);
//		}
		return s;
	}
	
 	private byte[] getByteKey(Serializable sessionId){
		String preKey = this.keyPrefix + sessionId;
		return preKey.getBytes();
	}
	
	public RedisManager getRedisManager() {
		return redisManager;
	}

	public void setRedisManager(RedisManager redisManager) {
		this.redisManager = redisManager;
	}

	public String getKeyPrefix() {
		return keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	public RedisTemplate<String, String> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
}
