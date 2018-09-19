package com.sstc.hmis.websocket.bean;

/**
 * portal 提示消息
 * 
 * @author wangzhuhua@sstcsoft.com
 * @date 2017-04-24
 */
public class PortalTipsMessage implements WebSocketMessage{

	private static final long serialVersionUID = 4853579136691238577L;

	/** 消息内容 */
	private String message;

	/**
	 * 获取 消息内容
	 * 
	 * @return 消息内容
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 设置 消息内容
	 * 
	 * @param message
	 *            消息内容
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
