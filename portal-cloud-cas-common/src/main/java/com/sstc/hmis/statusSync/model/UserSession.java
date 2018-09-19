/**
 * 
 */
package com.sstc.hmis.statusSync.model;

import java.io.Serializable;

/**
  * <p> Title: UserSession </p>
  * <p> Description:  TODO </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年9月6日 下午2:03:13
   */
public class UserSession implements Serializable{

	private static final long serialVersionUID = -4574080590420858017L;
	/**正常状态的Session*/
	public static final int STATUS_NORMAL = 0;
	/**超时状态的Session*/
	public static final int STATUS_TIMEOUT = 1;
	/**踢出状态的Session*/
	public static final int STATUS_KICKOUT = 2;
	
	private String account;
	
	private String sessionId;
	
	private String serviceTicket;
	
	private String casServiceUri;
	
	private String appName;
	
	private int status;
	
	public UserSession() {
	}

	public UserSession(String account, String sessionId, String serviceTicket,String casServiceUri) {
		this.account = account;
		this.sessionId = sessionId;
		this.serviceTicket = serviceTicket;
		this.casServiceUri = casServiceUri;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getServiceTicket() {
		return serviceTicket;
	}

	public void setServiceTicket(String serviceTicket) {
		this.serviceTicket = serviceTicket;
	}

	public String getCasServiceUri() {
		return casServiceUri;
	}

	public void setCasServiceUri(String casServiceUri) {
		this.casServiceUri = casServiceUri;
	}

	
	
}
