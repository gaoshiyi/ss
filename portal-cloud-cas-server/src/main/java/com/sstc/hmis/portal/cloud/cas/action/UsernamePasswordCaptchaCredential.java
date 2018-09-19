/**
 * 
 */
package com.sstc.hmis.portal.cloud.cas.action;

import org.apereo.cas.authentication.UsernamePasswordCredential;

/**
  * <p> Title: UsernamePasswordCaptchaCredential </p>
  * <p> Description:  TODO </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年10月16日 下午5:13:28
   */
public class UsernamePasswordCaptchaCredential extends UsernamePasswordCredential{

	private static final long serialVersionUID = 4476496919991982621L;
	
	private String captcha;

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	
}
