/**
 * 
 */
package com.sstc.hmis.shiro;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.util.Set;

/**
  * <p> Title: RedisManager </p>
  * <p> Description:  TODO </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年9月11日 下午7:50:38
   */
@SuppressWarnings("deprecation")
public class RedisManager {

	private RedisConnectionFactory redisConnectionFactory;



	/**
	 * Redis存储默认过期时间,单位秒
	 */
	private int expire = 7200;

	/**
	 * 构造函数
	 * @param redisConnectionFactory RedisConnectionFactory
	 * @param expire Redis存储默认过期时间,单位秒
	 */
	public RedisManager(RedisConnectionFactory redisConnectionFactory,int expire){
		this.redisConnectionFactory = redisConnectionFactory;
		this.expire = expire;
	}

	public int getExpire() {
		return expire;
	}

	/**
	 * 获得Redis连接
	 * @return  Redis连接
	 */
	private RedisConnection getRedisConnection(){
		return redisConnectionFactory.getConnection();
	}

	
	/**
	 * get value from redis
	 * @param key
	 * @return
	 */
	public byte[] get(byte[] key){
		byte[] value = null;
		RedisConnection redisConnection = getRedisConnection();
		try{
			value = redisConnection.get(key);
		}finally{
			redisConnection.close();
		}
		return value;
	}
	
	/**
	 * set 
	 * @param key
	 * @param value
	 * @return
	 */
	public byte[] set(byte[] key,byte[] value){
		RedisConnection redisConnection = getRedisConnection();
		try{
			redisConnection.set(key,value);
			if(this.expire != 0){
				redisConnection.expire(key, this.expire);
		 	}
		}finally{
			redisConnection.close();
		}
		return value;
	}
	
	/**
	 * set 
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	public byte[] set(byte[] key,byte[] value,int expire){
		RedisConnection redisConnection = getRedisConnection();
		try{
			redisConnection.set(key,value);
			if(expire != 0){
				redisConnection.expire(key, expire);
			}
		}finally{
			redisConnection.close();
		}
		return value;
	}
	
	/**
	 * del
	 * @param key
	 */
	public void del(byte[] key){
		RedisConnection redisConnection = getRedisConnection();
		try{
			redisConnection.del(key);
		}finally{
			redisConnection.close();
		}
	}
	
	/**
	 * flush
	 */
	public void flushDB(){
		RedisConnection redisConnection = getRedisConnection();
		try{
			redisConnection.flushDb();
		}finally{
			redisConnection.close();
		}
	}
	
	/**
	 * size
	 */
	public Long dbSize(){
		Long dbSize = 0L;

		RedisConnection redisConnection = getRedisConnection();
		try{
			dbSize = redisConnection.dbSize();
		}finally{
			redisConnection.close();
		}
		return dbSize;
	}

	/**
	 * keys
	 * @param pattern
	 * @return
	 */
	public Set<byte[]> keys(String pattern){
		Set<byte[]> keys = null;
		RedisConnection redisConnection = getRedisConnection();
		try{
			keys = redisConnection.keys(pattern.getBytes());
		}finally{
			redisConnection.close();
		}
		return keys;
	}

}
