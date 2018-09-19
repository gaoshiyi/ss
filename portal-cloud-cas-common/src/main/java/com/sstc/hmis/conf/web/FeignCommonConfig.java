/**
 * 
 */
package com.sstc.hmis.conf.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Request;
import feign.Retryer;

/**
  * <p> Title: FeignCommonConfig </p>
  * <p> Description:  feign客户端优化配置 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年6月30日 下午5:09:09
   */
@Configuration
public class FeignCommonConfig {

	/**
	 * Feign重试次数设置
	 * @return
	 */
	@Bean
	Retryer feignRetryer(){
		//错误重试1次，第一次等待100毫秒重置，最大等待1秒重试
		return new Retryer.Default(100, 1000, 2);
	}
	
	
	/**
	 * feign超时重试
	 * @return
	 */
	@Bean
	Request.Options feignOptions(){
		return new Request.Options(3*1000  /*connectTimeoutMillis*/, 60*1000 /*readTimeoutMillis*/);
	}
	
}
