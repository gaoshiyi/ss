/**
 * 
 */
package com.sstc.hmis.portal.cloud.cas.exception.login;

import javax.security.auth.login.AccountException;

/**
  * <p> Title: AccountWaitAuditException </p>
  * <p> Description:  账户待审核登录异常 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年8月21日 下午4:01:59
   */
public class AccountWaitAuditException extends AccountException{

	private static final long serialVersionUID = -1664702005276764871L;
	
	public AccountWaitAuditException(){
		super();
	}
	public AccountWaitAuditException(final String message){
		super(message);
	}

}
