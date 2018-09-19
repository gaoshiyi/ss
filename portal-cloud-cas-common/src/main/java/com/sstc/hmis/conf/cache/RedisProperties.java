package com.sstc.hmis.conf.cache;



import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;  
/**
  * <p> Title: RedisConfig </p>
  * <p> Description:  Redis参数 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年6月29日 上午10:06:19
   */
@Component
@ConfigurationProperties(prefix = "spring.redis")
@EnableConfigurationProperties(value = {RedisProperties.class})
public class RedisProperties{

	private int database;
	
	private String host;
	
	private int port;
	
	private String password;
	
	private int expire;

	private Pool pool;

	/**
	 * @return the database
	 */
	public int getDatabase() {
		return database;
	}

	/**
	 * @param database the database to set
	 */
	public void setDatabase(int database) {
		this.database = database;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the expire
	 */
	public int getExpire() {
		return expire;
	}

	/**
	 * @param expire the expire to set
	 */
	public void setExpire(int expire) {
		this.expire = expire;
	}

	public Pool getPool() {
		return pool;
	}

	public void setPool(Pool pool) {
		this.pool = pool;
	}

	/**
	 * Pool properties.
	 */
	public static class Pool {

		/**
		 * Max number of "idle" connections in the pool. Use a negative value to indicate
		 * an unlimited number of idle connections.
		 */
		private int maxIdle = 8;

		/**
		 * Target for the minimum number of idle connections to maintain in the pool. This
		 * setting only has an effect if it is positive.
		 */
		private int minIdle = 0;

		/**
		 * Max number of connections that can be allocated by the pool at a given time.
		 * Use a negative value for no limit.
		 */
		private int maxActive = 8;

		/**
		 * Maximum amount of time (in milliseconds) a connection allocation should block
		 * before throwing an exception when the pool is exhausted. Use a negative value
		 * to block indefinitely.
		 */
		private int maxWait = -1;

		public int getMaxIdle() {
			return this.maxIdle;
		}

		public void setMaxIdle(int maxIdle) {
			this.maxIdle = maxIdle;
		}

		public int getMinIdle() {
			return this.minIdle;
		}

		public void setMinIdle(int minIdle) {
			this.minIdle = minIdle;
		}

		public int getMaxActive() {
			return this.maxActive;
		}

		public void setMaxActive(int maxActive) {
			this.maxActive = maxActive;
		}

		public int getMaxWait() {
			return this.maxWait;
		}

		public void setMaxWait(int maxWait) {
			this.maxWait = maxWait;
		}

	}
	
	
}
