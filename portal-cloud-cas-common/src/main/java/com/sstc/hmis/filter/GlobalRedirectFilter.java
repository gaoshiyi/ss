/**
 * 
 */
package com.sstc.hmis.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sstc.hmis.model.constants.PortalConstants;

/**
  * <p> Title: GlobleRedirectFilter </p>
  * <p> Description:  全局重定向、异常和业务操作 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年11月9日 下午1:34:12
   */
public class GlobalRedirectFilter extends PermissionsAuthorizationFilter{
	
	
	private static final Logger LOG = LoggerFactory.getLogger(GlobalRedirectFilter.class);
	
	private static final String HEADER_NAME_AJAX = "X-Requested-With";
	
	private static final String HEADER_VALUE_AJAX = "XMLHttpRequest";
	
	private static final int SESSION_EXPIRE_CODE = 599;
	
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		String reqUri = req.getRequestURI();
		LOG.debug("当前请求的url为:{}",reqUri);
		HttpSession session = req.getSession();
		session.setAttribute("OLD_URL", req.getRequestURL());
		if(!StringUtils.equals(reqUri, "/") && (session == null || session.getAttribute(PortalConstants.SESSION_USER_KEY) == null)){
			boolean isAjax = HEADER_VALUE_AJAX.equalsIgnoreCase(req.getHeader(HEADER_NAME_AJAX));
			HttpServletResponse res = (HttpServletResponse) response;
			if(isAjax){
				res.sendError(SESSION_EXPIRE_CODE, "此次ajax请求session无效");
			}else{
				redirectToLogin(request, response);
			}
		}
		return false;
	}
	
	@Override
	public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		return super.onPreHandle(request, response, mappedValue);
	}

}
