package com.sstc.hmis.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.sstc.hmis.model.constants.PortalConstants;
import com.sstc.hmis.permission.data.Staff;
/**
 * 
  * <p> Title: SstcFormAuthenticationFilter </p>
  * <p> Description:  废弃夜审拦截器，此拦截器会阻拦所有请求，导致监控指标的请求 需要认证</p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2018年3月15日 上午9:26:39
 */
public class SstcFormAuthenticationFilter extends FormAuthenticationFilter{

private static final String HEADER_NAME_AJAX = "X-Requested-With";
	
	private static final String HEADER_VALUE_AJAX = "XMLHttpRequest";
	
	private static final int SESSION_EXPIRE_CODE = 598;
	
	private static final String NA_MSG_PAGE = "/namsgpage.jsp";
	
	private static final Logger logger = LoggerFactory.getLogger(SstcFormAuthenticationFilter.class);
	
//	@Resource(name="pmsRedisTemplate")
	RedisTemplate<Object, Object> redisTemplate;
				
	public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpReq = (HttpServletRequest)request;
		HttpSession session = httpReq.getSession();
		session.setAttribute("OLD_URL", httpReq.getRequestURL());
		return super.onAccessDenied(request, response);
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		if(redisTemplate!=null) {
			Session session = SecurityUtils.getSubject().getSession();
			if(session!=null) {
				Staff staff = (Staff)session.getAttribute(PortalConstants.SESSION_USER_KEY);
				if(staff!=null) {
					String id = staff.getHotelId();
					String key = "NA_FLAG_"+ id;
					if(redisTemplate.hasKey(key)) {
						Object na_flag = redisTemplate.opsForValue().get(key);
						if(na_flag != null&&"1".equals(na_flag.toString())) {
							String uri = req.getRequestURI();
							
							if(uri!=null&&uri.toLowerCase().contains("cash/nightaudit")) {
								return super.isAccessAllowed(request, response, mappedValue);
							}
							
							boolean isAjax = HEADER_VALUE_AJAX.equalsIgnoreCase(req.getHeader(HEADER_NAME_AJAX));
							if(isAjax){
								try {
									res.sendError(SESSION_EXPIRE_CODE, "夜审中，请稍后再试。。。");
								} catch (IOException e) {
									logger.error(e.getMessage(),e);
								}
							}else{
								try {
									req.getRequestDispatcher(NA_MSG_PAGE).forward(req, res);
								} catch (ServletException e) {
									logger.error(e.getMessage(),e);
								} catch (IOException e) {
									logger.error(e.getMessage(),e);
								}
							}
						}
					}
				}
			}
		}
		return super.isAccessAllowed(request, response, mappedValue);
	}
}
