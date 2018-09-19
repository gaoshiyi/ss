package com.sstc.hmis.portal.cloud.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
  * <p> Title: DiscoveryApplication </p>
  * <p> Description:  门户注册中心 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年6月20日 上午10:35:07
 */
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryApplication {
	public static void main(String[] args) {
		SpringApplication.run(DiscoveryApplication.class, args);
	}
}
