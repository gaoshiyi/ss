/**
 * 
 */
package com.sstc.hmis.conf.cas.constants;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
  * <p> Title: ShiroCasProerties </p>
  * <p> Description:  单点登录配置 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年7月4日 下午5:56:23
   */
@Component
@ConfigurationProperties(prefix = "spring.shiro.cas")
@EnableConfigurationProperties(value = {ShiroCasProperties.class})
public class ShiroCasProperties {
	
	// 网站应用的请求根路径URL(包含端口和根路径,例如http://app:port/pms)
	private String appUrlPrefix;
	
	// Cas服务的请求根路径URL(包含端口和根路径,例如http://cas:port/cas)
	private String ssoServerUrlPrefix;
	
	/**
	 *  网站应用的cas票据验证地址，Cas服务器使用本变量作为网站APP的服务名称。<br/>
	 *  一般为org.apache.shiro.cas.CasFilter过滤的地址，例如http://app:port/pms/cas,<br/>
	 *  默认使用appUrlPrefix+"/cas"
	 */
	private String casService;
	
	/**
	 * Cas服务器登录页面地址，后附带casService参数
	 * 例如http://cas:port/cas/login?service=http://app:port/pms/cas"
	 */
	private String loginUrl;
	
	// 网站应用登出操作后访问的页面的相对路径,不配置默认为网站应用的根路径级(/)
	private String logoutUrl;
	
	// 网站应用登录成功后访问的页面相对路径
	private String authSuccessUrl;
	
	// 网站应用登录失败(无权限)后访问的页面相对路径
	private String unauthorizedUrl;
	

	public String getAppUrlPrefix() {
		return appUrlPrefix;
	}

	public void setAppUrlPrefix(String appUrlPrefix) {
		this.appUrlPrefix = appUrlPrefix;
		this.casService = appUrlPrefix + "/cas";
	}
	
	public String getSsoServerUrlPrefix() {
		return ssoServerUrlPrefix;
	}

	public void setSsoServerUrlPrefix(String ssoServerUrlPrefix) {
		this.ssoServerUrlPrefix = ssoServerUrlPrefix;
		this.loginUrl =  this.ssoServerUrlPrefix + "/login?service=" + appUrlPrefix + "/cas";
	}

	public String getCasService() {
		return casService;
	}

	public void setCasService(String casService) {
		this.casService = casService;
	}
	
	public String getLoginUrl() {
		return loginUrl;
	}
	
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
	

	public String getAuthSuccessUrl() {
		return authSuccessUrl;
	}

	public void setAuthSuccessUrl(String authSuccessUrl) {
		this.authSuccessUrl = authSuccessUrl;
	}

	public String getUnauthorizedUrl() {
		return unauthorizedUrl;
	}

	public void setUnauthorizedUrl(String unauthorizedUrl) {
		this.unauthorizedUrl = unauthorizedUrl;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}
	
}
