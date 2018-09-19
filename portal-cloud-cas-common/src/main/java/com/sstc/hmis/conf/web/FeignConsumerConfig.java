/**
 * 
 */
package com.sstc.hmis.conf.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSON;
import com.sstc.hmis.permission.data.Staff;
import com.sstc.hmis.permission.web.LoginInfoHolder;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
  * <p> Title: FeignClientsConfig </p>
  * <p> Description:  上下文用户信息放入rest 请求的header中达到自动携带登录信息的目的 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年6月29日 下午5:33:58
   */
@Configuration
public class FeignConsumerConfig {
	
	private static final Logger LOG = LoggerFactory.getLogger(FeignConsumerConfig.class);
	
	@Bean
	public RequestInterceptor contextInterceptor(){
		
		return new RequestInterceptor() {
			@Override
			public void apply(RequestTemplate template) {
				LOG.debug("qxx--当前线程 {} 的登录用户 {} 工作酒店Code :{}",
						Thread.currentThread().getId(),
						LoginInfoHolder.getLoginAccount(),
						LoginInfoHolder.getHotelCode());
//				Subject subject = SecurityUtils.getSubject();
// 				String account = (String) subject.getPrincipal();
//				Session session = subject.getSession();
				Staff staff = LoginInfoHolder.getLoginInfo();
				if(staff != null){
					String account = staff.getAccount();
					template.header("account", account);
					if(LOG.isDebugEnabled()){
						LOG.debug("请求服务的登录用户信息 : {}",JSON.toJSON(staff));
					}
					template.header("staff", JSON.toJSONString(staff));
				}
			}
		};
		
	}
	
	
	
}
