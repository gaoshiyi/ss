/**
 * 
 */
package com.sstc.hmis.permission.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

import com.sstc.hmis.permission.data.GroupHotel;
import com.sstc.hmis.permission.service.GrpHotelService;

/**	
  * <p> Title: LoginInfoFilterConfig </p>
  * <p> Description:  用户登录信息设置过滤器 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年9月19日 下午3:25:11
   */
//@Configuration
//@ConditionalOnProperty(name = "feign.service.type", havingValue = "consumer")
public class LoginInfoFilterConfig {
	
	@Value("${spring.application.name}")
	private String appName;

	private static final Logger log = LoggerFactory.getLogger(LoginInfoFilterConfig.class);
	
	@Resource(name="redisTemplate")
	private RedisTemplate<String, GroupHotel> redisTemplate;
	@Resource
	private GrpHotelService grpHotelService;
	
	@Bean
	public FilterRegistrationBean loginInfoFilterRegistration(){
		if (log.isDebugEnabled()) {
			log.debug("应用{}启动初始化进程", appName);
		}
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		LoginInfoFilter loginInfoFilter = new LoginInfoFilter();
		loginInfoFilter.setRedisTemplate(redisTemplate);
		loginInfoFilter.setGrpHotelService(grpHotelService);
		filterRegistrationBean.setFilter(loginInfoFilter);
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	} 
	
}
