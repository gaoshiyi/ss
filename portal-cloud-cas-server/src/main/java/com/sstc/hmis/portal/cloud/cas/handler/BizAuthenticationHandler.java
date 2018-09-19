/**
 * 
 */
package com.sstc.hmis.portal.cloud.cas.handler;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;

import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.HandlerResult;
import org.apereo.cas.authentication.InvalidLoginTimeException;
import org.apereo.cas.authentication.MessageDescriptor;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.UsernamePasswordCredential;
import org.apereo.cas.authentication.adaptive.UnauthorizedAuthenticationException;
import org.apereo.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.springframework.beans.factory.annotation.Autowired;

import com.sstc.hmis.model.constants.Constants;
import com.sstc.hmis.model.constants.PortalConstants;
import com.sstc.hmis.permission.service.StaffService;
import com.sstc.hmis.portal.cloud.cas.authentication.pricipal.SstcPrincipal;

/**
  * <p> Title: BizAuthenticationHandler </p>
  * <p> Description:  登录信息处理器 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年8月3日 下午2:57:29
   */
public class BizAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler{

	@Autowired
	private StaffService staffService;
	
	@Override
	protected HandlerResult authenticateUsernamePasswordInternal(UsernamePasswordCredential credential)
			throws GeneralSecurityException, PreventedException {
		String userName = credential.getUsername();
		String password = credential.getPassword();
		Integer result = staffService.casSsoLogin(userName,password,Constants.LOGIN_TYPE_PC);
		if(result == null || result == PortalConstants.LOGIN_CODE_ACCOUNT_ERROR){
			throw new AccountNotFoundException("用户名或密码错误");
		}else if(result == PortalConstants.LOGIN_CODE_LOCK){
			throw new AccountLockedException("帐号被锁定，请联系管理员解锁");
		}else if(result == PortalConstants.LOGIN_CODE_AUDIT){
			throw new UnauthorizedAuthenticationException("帐号审核中，请稍候再试", new HashMap<>(1));
		}else if(result == PortalConstants.LOGIN_CODE_LIMIT){
			throw new InvalidLoginTimeException("帐号受登录策略限制，无法登录");
		}
		return createHandlerResult(credential, new SstcPrincipal(userName), new ArrayList<MessageDescriptor>());
	}
	
	@Override
	public HandlerResult authenticate(final Credential credential) throws GeneralSecurityException, PreventedException {

		if (!preAuthenticate(credential)) {
			throw new FailedLoginException();
		}
		return postAuthenticate(credential, doAuthentication(credential));
	}

}
