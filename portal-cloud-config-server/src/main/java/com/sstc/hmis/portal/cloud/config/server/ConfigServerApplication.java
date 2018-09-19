package com.sstc.hmis.portal.cloud.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * <p> Title: ConfigServerApplication </p>
 * <p> Description: 分布式配置中心 </p>
 * <p> Company: SSTC </p>
 * @author Qxiaoxiang
 * @date 2017年10月11日 上午11:42:31
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigServer
public class ConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}
}
