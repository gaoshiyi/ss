/**
 * 
 */
package com.sstc.hmis.mq.rabbitmq.config;

import java.util.UUID;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sstc.hmis.model.constants.MqConstants;

/**
  * <p> Title: RabbitConfig </p>
  * <p> Description:  TODO </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年12月4日 下午2:27:38
   */
@Configuration
@ConditionalOnProperty(name = "feign.service.type", havingValue = "consumer")
public class RabbitConfig {
	
	@Value("${spring.application.name}")
	private String appName;
	
	/**零时队列名称*/
	public static final String FANOUT_QUEUE = "ws-" + UUID.randomUUID().toString();

	@Bean
	public Queue switchQueue(){
		return new Queue(MqConstants.QUEUE_SWITCH);
	}
	
	@Bean
	public Queue kickoutQueue(){
		return new Queue(MqConstants.QUEUE_KICKOUT);
	}
	
	@Bean
 	public DirectExchange directExchange(){
		DirectExchange directExchange=new DirectExchange(MqConstants.EX_SESSION);
 		return directExchange;
 	}
	
	@Bean
 	public Binding binding(){
 		Binding binding=BindingBuilder.bind(switchQueue()).to(directExchange()).with(appName);
 		return binding;
 	}
	
	/**
	 * 定义websocket广播类型路由器
	 * 
	 * @return 路由器
	 */
	@Bean
	public FanoutExchange webSocketExchange() {
		return new FanoutExchange(MqConstants.EX_WEBSOCKET);
	}

	/**
	 * 定义websocket临时队列
	 * 
	 * @return 临时队列
	 */
	@Bean
	public Queue webSocketQueue() {
		return new Queue(FANOUT_QUEUE, true, false, true);
	}

	/**
	 * 绑定websocket临时队列
	 * 
	 * @return 绑定信息
	 */
	@Bean
	public Binding bindWebSocketQueue() {
		return BindingBuilder.bind(webSocketQueue()).to(webSocketExchange());
	}
}
