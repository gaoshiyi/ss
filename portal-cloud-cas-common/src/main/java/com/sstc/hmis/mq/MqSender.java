/**
 * 
 */
package com.sstc.hmis.mq;

import com.sstc.hmis.ws.WebSocketMqMessage;

/**
  * <p> Title: MqSender </p>
  * <p> Description:  消息队列发送接口 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年12月4日 下午2:18:27
   */
public interface MqSender {

	void sender(String queue,String routingkey, String msg);
	
	void sender(String queue,String msg);
	
	/**
	 * 发送广播消息
	 * 
	 * @param exchange
	 *            路由器名称
	 * @param msg
	 *            消息内容
	 */
	void sendFanout(String exchange, WebSocketMqMessage msg);
	
}
