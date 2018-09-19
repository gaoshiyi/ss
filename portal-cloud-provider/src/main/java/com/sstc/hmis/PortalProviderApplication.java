package com.sstc.hmis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.sstc.hmis.dbaccess.datasource.DynamicDataSourceRegister;
import com.sstc.hmis.dbaccess.mybatis.PageProperties;
import com.sstc.hmis.mdata.common.service.data.SmsSendSignProperties;
import com.sstc.hmis.mdata.common.service.data.SmsSendTplProperties;
import com.sstc.hmis.util.msg.MessageSourceHelper;

/**
 * @author Qxiaoxiang
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.sstc.hmis.mdata.service", "com.sstc.hmis.mdata.common.service", "com.sstc.hmis.wf.service"})
@EnableConfigurationProperties(value={PageProperties.class,SmsSendSignProperties.class,SmsSendTplProperties.class})
@EnableAutoConfiguration(exclude = {
		org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration.class})
@Import(DynamicDataSourceRegister.class)
public class PortalProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortalProviderApplication.class, args);
	}
	
	@Bean
	public MessageSourceHelper messageSourceHelper(){
		return new MessageSourceHelper();
	}
}
