/**
 * 
 */
package com.sstc.hmis.conf.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.sstc.hmis.util.spring.SpringUtil;

/**
  * <p> Title: WebMvcConfiguration </p>
  * <p> Description:  web mvc 主要配置 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年6月23日 下午3:52:51
   */
@Configuration
@ConditionalOnWebApplication
public class WebMvcConfiguration extends WebMvcConfigurerAdapter{

	@Value("${spring.mvc.root.view:main}")
	private String rootView = "main";
	/**
	 * 根视图配置
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName(rootView);
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		super.addViewControllers(registry);
	}
	
	@Bean
	public SpringUtil springUtil(){
		return new SpringUtil();
	}
	
}
