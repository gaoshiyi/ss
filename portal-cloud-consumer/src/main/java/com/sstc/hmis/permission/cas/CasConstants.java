/**
 * 
 */
package com.sstc.hmis.permission.cas;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.alibaba.fastjson.JSON;
import com.sstc.hmis.permission.data.cas.PrincipalVo;
import com.sstc.hmis.util.CommonUtils;

/**
  * <p> Title: CasConstants </p>
  * <p> Description:  Cas相关缓存常量 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年9月7日 下午7:40:49
   */
public class CasConstants {
	
	public static final int LONG_CACHE_HOURS_TIME = 6;

	public static final String CACHE_USER_STATUS_PRE = "session:account:{0}:tgt:{1}:principal";
	
	public static final String CACHE_KEY_ACCOUNT_CAS = "session:account:{0}:cas";
	/**帐号对应的TGT ID列表*/
	public static final String CACHE_KEY_ACCOUNT_TGTS = "session:account:{0}:tgt";
	
	public static final String CACHE_KEY_SESSION_ST = "session:st:{0}:sid";
	/**TGT下对应的sid列表*/
	public static final String CACHE_KEY_TGT_SID = "session:tgt:{0}:sid";
	
	public static final String CACHE_KEY_SID_TGT = "session:sid:{0}:tgt";
	
	public static final String CACHE_KEY_ST_TGT = "session:st:{0}:tgt";
	
	public static final String CACHE_KEY_TICKET = "session:ticket:{0}";
	
	public static final String ACCOUNT_PERM_SET = "session:perm:{0}";
	
	public static final String UTF8 = "utf-8";
	
	private static final Logger LOG = LoggerFactory.getLogger(CasConstants.class);
	
	public static String getStTgtKey(final String ticketId){
		String key = MessageFormat.format(CACHE_KEY_ST_TGT, ticketId);
		return CommonUtils.trans4UTF8(key);
	}
	
	/**
	 * 根据st获取tgt
	 * @param ticket ServiceTicket
	 * @param client redisTemplate
	 * @return tgt
	 */
	public static String getTgtBySt(final String stId,StringRedisTemplate stringRedisTemplate){
		String key = getStTgtKey(stId);
		return stringRedisTemplate.opsForValue().get(key);
	}
	
	public static void setStTgtMapping(final String stId,final String tgtId,StringRedisTemplate stringRedisTemplate){
		String key = getStTgtKey(stId);
		setCache(key, tgtId, stringRedisTemplate);
	}
	
	public static String getPatternTicketRedisKey() {
		String key = MessageFormat.format(CACHE_KEY_TICKET , "*");
		return CommonUtils.trans4UTF8(key);
    }
	
	/**
	 * 同步数据缓存key
	 * @param account
	 * @return
	 */
	public static String getStatusCacheKey(final String account,final String tgtId){
		return MessageFormat.format(CACHE_USER_STATUS_PRE, account,tgtId);
	}
	/**
	 * 用户登录的系统缓存key
	 * @param account
	 * @return
	 */
	public static String getPrincipalCacheKey(final String account){
		return MessageFormat.format(CACHE_KEY_ACCOUNT_CAS, account);
	}
	/**
	 * 用户的ticket对应的sessionId缓存Key
	 * @param ticket
	 * @return
	 */
	public static String getStSessionIdCacheKey(final String ticket){
		return MessageFormat.format(CACHE_KEY_SESSION_ST, ticket);
	}
	
	public static String getSidTgtCacheKey(final String sid){
		return MessageFormat.format(CACHE_KEY_SID_TGT, sid);
	}
	
    public static void setStSessionIdCache(final String ticket,final String sid,StringRedisTemplate redisTemplate) {
    	LOG.debug("登录的ST:{} 的SessionId 是 {} " , ticket, sid);
    	String key = getStSessionIdCacheKey(ticket);
    	setCache(key, sid, redisTemplate);
    	
    	String tgtId = getTgtBySt(ticket, redisTemplate);
    	if(StringUtils.isNotBlank(tgtId)){
    		setSidTgtCache(sid, tgtId, redisTemplate);
    		setTgtSid(tgtId, sid, redisTemplate);
    	}
    }
    
    public static String getSessionIdBySt(final String ticket, StringRedisTemplate redisTemplate){
    	return getCache(getSessionIdBySt(ticket, redisTemplate), redisTemplate);
    }
    
    public static void setSidTgtCache(final String sid, final String tgtId, StringRedisTemplate redisTemplate){
    	setCache(getSidTgtCacheKey(sid), tgtId, redisTemplate);
    }
    
    public static String getTgtBySid(final String sid, StringRedisTemplate redisTemplate){
    	return getCache(getSidTgtCacheKey(sid), redisTemplate);
    }
    
    public static String getTgtBySid(final String sid, RedisTemplate<String,String> redisTemplate){
    	return redisTemplate.opsForValue().get(getSidTgtCacheKey(sid));
    }
    
    public static void setTgtSid(final String tgtId,final String sid, StringRedisTemplate redisTemplate){
    	List<String> sidList = getSidListByTgt(tgtId, redisTemplate);
    	if(!sidList.contains(sidList)){
    		sidList.add(sid);
    		String key = getTgtSidCacheKey(tgtId);
    		setCache(key, JSON.toJSONString(sidList), redisTemplate);
    	}
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<String> getSidListByTgt(final String tgtId, RedisTemplate redisTemplate){
    	String key = getTgtSidCacheKey(tgtId);
    	String json = getCache(key, redisTemplate);
    	List<String> sidList = new ArrayList<>();
    	if(StringUtils.isNoneBlank(json)){
    		try {
    			sidList = JSON.parseArray(json, String.class);
			} catch(Exception e) {
				LOG.info("tgt [{}]下暂无 sid信息", tgtId);
			}
    	}
    	return sidList;
    }
    
	private static String getTgtSidCacheKey(String tgtId) {
		return MessageFormat.format(CACHE_KEY_TGT_SID, tgtId);
	}

	/**
	 * 缓存帐号在各系统的登录信息
	 * @param account 帐号
	 * @param obj 登录session列表
	 */
	public static void setAccountSessionCache(String account,Object obj, RedisTemplate<String,String> redisTemplate){
		String key = getPrincipalCacheKey(account);
		String value = JSON.toJSONString(obj);
		redisTemplate.opsForValue().set(key, value, LONG_CACHE_HOURS_TIME, TimeUnit.HOURS);
	}
    
	/**
	 * 用户帐号对应的权限列表，主要用户查问题，不参与业务逻辑
	 * @param account
	 * @return
	 */
	public static String getSessionUserPermKey(final String account){
		return MessageFormat.format(ACCOUNT_PERM_SET, account);
	}
	
	/**
	 * 帐号对应的TGTID  KEY
	 * @param account
	 * @return
	 */
	public static String getAccountTgtIdKey(final String account){
		return MessageFormat.format(CACHE_KEY_ACCOUNT_TGTS, account);
	}
	
	/**
	 * 获取帐号对应的最近的一个TGT-ID
	 * @param account
	 * @param stringRedisTemplate
	 * @return
	 */
	public static List<String> getAccountTgtId(final String account,StringRedisTemplate stringRedisTemplate){
		String accountTgtKey = getAccountTgtIdKey(account);
		String json = stringRedisTemplate.opsForValue().get(accountTgtKey);
		List<String> tgtIdList = new ArrayList<>();
		
		try {
			tgtIdList = JSON.parseArray(json, String.class);
		} catch (Exception e) {
			LOG.error("帐号[{}]暂无tgt信息",account);
		}
		if(tgtIdList != null){
			return tgtIdList;
		}
		return new ArrayList<>();
	}
	
	public static void setAccountTgtId(final String account,final String tgtId, StringRedisTemplate stringRedisTemplate){
		List<String> tgtIdList = getAccountTgtId(account, stringRedisTemplate);
		if(!tgtIdList.contains(tgtId)){
			tgtIdList.add(tgtId);
		}
		String key = CasConstants.getAccountTgtIdKey(account);
		if(tgtIdList.size() > 0){
			setCache(key, JSON.toJSONString(tgtIdList), stringRedisTemplate);
		}
	}
	
	public static String getTicketRedisKey(final String ticketId){
		String key = MessageFormat.format(CACHE_KEY_TICKET , ticketId);
		return CommonUtils.trans4UTF8(key);
	}
	
	
	public static void updatePrincipalInfo(PrincipalVo principalVo,final String account,final String tgtId,StringRedisTemplate redisTemplate){
		setCache(CasConstants.getStatusCacheKey(account,tgtId), JSON.toJSONString(principalVo), redisTemplate);
	}
	
	public static PrincipalVo getPrincipalInfo(String account,final String tgtId,StringRedisTemplate redisTemplate){
		String key = CasConstants.getStatusCacheKey(account,tgtId);
		String json = redisTemplate.opsForValue().get(key);
		PrincipalVo vo = null;
		if(StringUtils.isNoneBlank(json)){
			vo = JSON.parseObject(json, PrincipalVo.class);
		}else{
			vo = new PrincipalVo(account);
		}
		return vo;
	}
	
	
	private static void setCache(final String key,final String value,StringRedisTemplate redisTemplate){
		try {
			redisTemplate.opsForValue().set(key, value, LONG_CACHE_HOURS_TIME, TimeUnit.HOURS);
		} catch (Exception e) {
			LOG.error("设置缓存[{}]数据[{}]错误:{}", key, value, e);
		}
	}
	
	private static String getCache(final String key, RedisTemplate<String,String> redisTemplate){
		try {
			return (String) redisTemplate.opsForValue().get(key);
		} catch (Exception e) {
			LOG.error("设置缓存[{}]数据[{}]错误:{}", key, e);
			return StringUtils.EMPTY;
		}
	}

	
}
