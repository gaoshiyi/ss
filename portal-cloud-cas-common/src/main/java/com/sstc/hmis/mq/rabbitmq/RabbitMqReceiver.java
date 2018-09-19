/**
 * 
 */
package com.sstc.hmis.mq.rabbitmq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apereo.cas.ticket.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.sstc.hmis.model.constants.MqConstants;
import com.sstc.hmis.mq.MqReceiver;
import com.sstc.hmis.permission.cas.CasConstants;
import com.sstc.hmis.statusSync.handler.UserSessionHandler;
import com.sstc.hmis.statusSync.model.UserSession;
import com.sstc.hmis.ws.WebSocketMqMessage;

/**
  * <p> Title: RabbitMqReceiver </p>
  * <p> Description:  RabbitMQ </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年12月4日 下午2:19:58
   */
@Component
@ConditionalOnProperty(name = "feign.service.type", havingValue = "consumer")
public class RabbitMqReceiver implements MqReceiver{

	private static final Logger LOG = LoggerFactory.getLogger(RabbitMqReceiver.class);

	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Resource
	private RedisTemplate<String, Ticket> redisTemplate;
	@Resource
	private DefaultWebSessionManager sessionManager;
	@Resource
	private SimpMessageSendingOperations messageSendingOperations;

	@SuppressWarnings("unchecked")
	@RabbitHandler
	@RabbitListener(queues = MqConstants.QUEUE_SWITCH)
	@Override
	public void receiver(String msg) {
		UserSessionHandler handler = new UserSessionHandler(stringRedisTemplate);
		Map<String,Object> params = JSON.parseObject(msg, HashMap.class);
		handler.dataUpdate(params, sessionManager);
	}

	@RabbitHandler
	@RabbitListener(queues = MqConstants.QUEUE_KICKOUT)
	@Override
	public void kickoutReceiver(String msg){
		
		if(StringUtils.isNotBlank(msg)){
			Map<String,Object> data = JSON.parseObject(msg);
			String account = (String) data.get("account");
			Integer type = (Integer) data.get("type");
			LOG.debug("处理多地登录提出功能:{}",account);
			UserSessionHandler handler = new UserSessionHandler(stringRedisTemplate);
			List<UserSession> sessions = handler.getAccountSessionCache(account);
//			String tgtIdnearby = CasConstants.getAccountTgtId(account, stringRedisTemplate);
//			LOG.debug("帐号"+account+"最近一次登录生成的TgtId为 "+tgtIdnearby);
//			sessions.stream().filter(s -> s.getStatus() == UserSession.STATUS_NORMAL)
//			.forEach(session -> {
//				try {
//					String sessionId = session.getSessionId();
//					String stId = session.getServiceTicket();
//					String tgtId = CasConstants.getStTgtMapping(stId, stringRedisTemplate);
//					if(!StringUtils.equalsIgnoreCase(tgtId, tgtIdnearby)|| type == MqConstants.MSG_TYPE_LOGOUT){
//						session.setStatus(UserSession.STATUS_KICKOUT);
//						handler.delSession(sessionManager, sessionId);
//						final String redisKey = CasConstants.getTicketRedisKey(tgtId);
//						this.redisTemplate.delete(redisKey);
//						LOG.debug("帐号{}多地登录，销毁上次登录的session[{}],stId[{}],tgt[{}]", account, sessionId, stId, tgtId);
//					}else {
//						LOG.debug("不销毁帐号{}同机其他系统的session[{}]",account, sessionId);
//					}
//				} catch (Exception e) {
//					LOG.error("帐号{}多地登录，销毁上次登录的session,tgt信息 异常", account, e);
//				}
//			});
			if(type == MqConstants.MSG_TYPE_LOGOUT){
//				handler.destroyAllUserSession(account);
			}else{
				sessions = sessions.stream()
									.filter(s -> s.getStatus() == UserSession.STATUS_NORMAL)
									.collect(Collectors.toList());
				CasConstants.setAccountSessionCache(account, sessions,stringRedisTemplate);
			}
		}
		
	}
	
	@RabbitHandler
	@RabbitListener(queues = "#{rabbitConfig.FANOUT_QUEUE}")
	@Override
	public void webSocketReciever(WebSocketMqMessage message) {
		try {
			messageSendingOperations.convertAndSend(message.getTopic(), message.getBody());
		} catch (Exception e) {
			LOG.error("推送消息：{}，发送异常", message.getTopic(), e);
		}
	}

}
