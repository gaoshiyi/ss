/**
 * 
 */
package com.sstc.hmis.filter;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import com.sstc.hmis.permission.data.GroupHotel;
import com.sstc.hmis.permission.service.GrpHotelService;

/**
  * <p> Title: FeignContextFilterConfig </p>
  * <p> Description:  TODO </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年9月18日 下午7:18:08
   */
@Configuration
@ConditionalOnProperty(name="feign.service.type",havingValue="provider")
public class FeignContextFilterConfig {
	
	private static final Logger LOG = LoggerFactory.getLogger(FeignContextFilter.class);

	@Resource(name="redisTemplate")
	private RedisTemplate<String, GroupHotel> redisTemplate;
	@Resource
	private GrpHotelService grpHotelService;
	
	@Bean
	public FilterRegistrationBean userFilterRegistration(){
		if(LOG.isDebugEnabled()){
			LOG.debug("初始化请求接口公共数据处理过滤器");
		}
		
		Assert.notNull(redisTemplate, "RedisTemplate没有注入");
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		FeignContextFilter contextFilter = new FeignContextFilter();
		contextFilter.setRedisTemplate(redisTemplate);
		contextFilter.setGrpHotelService(grpHotelService);
		filterRegistrationBean.setFilter(contextFilter);
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}
}
