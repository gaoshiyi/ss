
/**
 * 
 */
package com.sstc.hmis.mq.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.sstc.hmis.model.constants.MqConstants;
import com.sstc.hmis.mq.MqSender;
import com.sstc.hmis.ws.WebSocketMqMessage;

/**
  * <p> Title: RabbitMqSender </p>
  * <p> Description:  RabbitMQ </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年12月4日 下午2:19:23
   */
@Component
public class RabbitMqSender implements MqSender{

	
	@Autowired
    private AmqpTemplate rabbitTemplate;
	
	@Override
	public void sender(String queue,String routingkey, String msg) {
		Assert.hasText(msg, "消息内容不能为空");
		this.rabbitTemplate.convertAndSend(MqConstants.EX_SESSION, routingkey , msg);
	}

	@Override
	public void sender(String queue, String msg) {
		this.rabbitTemplate.convertAndSend(queue, msg);
	}
	
	@Override
	public void sendFanout(String exchange, WebSocketMqMessage msg) {
		rabbitTemplate.convertAndSend(exchange, "", msg);
	}

}
