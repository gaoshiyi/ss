/**
 * 
 */
package com.sstc.hmis.mq;

import com.sstc.hmis.ws.WebSocketMqMessage;

/**
  * <p> Title: MqReceiver </p>
  * <p> Description:  消息队列接收（消费者）接口 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年12月4日 下午2:18:44
   */
public interface MqReceiver {

	
	void receiver(String msg);
	
	void kickoutReceiver(String msg);
	
	/**
	 * websocket消息
	 * 
	 * @param message
	 *            消息内容
	 */
	void webSocketReciever(WebSocketMqMessage message);
	
}
