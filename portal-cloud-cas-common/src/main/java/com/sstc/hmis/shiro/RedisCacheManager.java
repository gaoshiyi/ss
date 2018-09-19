/**
 * 
 */
package com.sstc.hmis.shiro;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
  * <p> Title: RedisCacheManager </p>
  * <p> Description:  TODO </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年9月11日 下午7:47:19
   */
public class RedisCacheManager<K, V> implements CacheManager{


	private final ConcurrentMap<String, Cache<K, V>> caches = new ConcurrentHashMap<String, Cache<K, V>>();

	private RedisManager redisManager;

	/**
	 * The Redis key prefix for caches 
	 */
	private String keyPrefix = "shiro_redis_cache:";
	
	@SuppressWarnings("unchecked")
	@Override
	public Cache<K, V> getCache(String name) throws CacheException {
		
		Cache<K, V> c = caches.get(name);
		
		if (c == null) {

			// create a new cache instance
			c = new RedisCache<K, V>(redisManager, keyPrefix);
			
			// add it to the cache collection
			caches.put(name, c);
		}
		return c;
	}

	public RedisManager getRedisManager() {
		return redisManager;
	}

	public void setRedisManager(RedisManager redisManager) {
		this.redisManager = redisManager;
	}
}
