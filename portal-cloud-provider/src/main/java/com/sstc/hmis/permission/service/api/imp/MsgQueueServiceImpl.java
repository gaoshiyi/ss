/**
 * 
 */
package com.sstc.hmis.permission.service.api.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.sstc.hmis.model.constants.MqConstants;
import com.sstc.hmis.mq.MqSender;
import com.sstc.hmis.permission.service.MsgQueueService;
import com.sstc.hmis.websocket.bean.PortalTipsMessage;
import com.sstc.hmis.ws.WebSocketMqMessage;

/**
  * <p> Title: MsgQueueService </p>
  * <p> Description:  TODO </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年12月8日 下午4:47:37
   */
@RestController
public class MsgQueueServiceImpl implements MsgQueueService{

	@Autowired
	private MqSender mqSender;
	
	@Override
	public void sender(String queue, String routingkey, String msg) {
		try {
			mqSender.sender(queue, routingkey, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sender(String queue, String msg) {
		try {
			mqSender.sender(queue, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendWebSocketFanout(String topic, String message) {
		try {
			WebSocketMqMessage msg = new WebSocketMqMessage();
			msg.setTopic(topic);
			
			PortalTipsMessage tip = new PortalTipsMessage();
			tip.setMessage(message);
			
			msg.setBody(tip);
			mqSender.sendFanout(MqConstants.EX_WEBSOCKET, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
