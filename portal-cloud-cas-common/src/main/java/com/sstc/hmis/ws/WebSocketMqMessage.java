package com.sstc.hmis.ws;

import java.io.Serializable;

import com.sstc.hmis.websocket.bean.WebSocketMessage;

/**
 * websocket消息
 * 
 * @author wangzhuhua@sstcsoft.com
 * @date 2018-04-24
 */
public class WebSocketMqMessage implements Serializable{

	private static final long serialVersionUID = 9042085276943509516L;
	
	/**主题名称*/
	private String topic;
	/**消息内容*/
	private WebSocketMessage body;
		
	/**
	 * 获取 主题名称
	 *
	 * @return the topic name
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * 设置 主题名称
	 * 
	 * @param topicName
	 *            主题名称
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	/**
	 * 获取消息内容
	 * 
	 * @return
	 */
	public WebSocketMessage getBody() {
		return body;
	}

	/**
	 * 设置消息内容
	 * 
	 * @param body
	 *            消息体
	 */
	public void setBody(WebSocketMessage body) {
		this.body = body;
	}
	
}
