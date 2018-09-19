/**
 * 
 */
package com.sstc.hmis.permission.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
  * <p> Title: MqService </p>
  * <p> Description:  TODO </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年12月8日 下午4:17:11
   */
@FeignClient(name = "${feign.provider.portal}")
@RequestMapping("/msgQueueService")
public interface MsgQueueService {
	
	/**
	 * 选择路由键的方式发送到指定队列
	 * @param queue 队列
	 * @param routingkey 路由键
	 * @param msg 消息
	 */
	@RequestMapping(value = "/sender1" , method = RequestMethod.POST)
	void sender(@RequestParam("queue")String queue,
			@RequestParam("routingkey")String routingkey, @RequestParam("msg")String msg);
	
	
	/**
	 * 发送信息到队列
	 * @param queue
	 * @param msg
	 */
	@RequestMapping(value = "/sender2" , method = RequestMethod.POST)
	void sender(@RequestParam("queue")String queue,@RequestParam("msg")String msg);
	
	/**
	 * 发送websocket广播消息
	 * 
	 * @param topic
	 *            主题名称
	 * @param message
	 *            消息内容
	 */
	@RequestMapping(value = "/sendWsFanout" , method = RequestMethod.POST)
	public void sendWebSocketFanout(@RequestParam("topic")String topic, @RequestParam("message") String message);
	

}
