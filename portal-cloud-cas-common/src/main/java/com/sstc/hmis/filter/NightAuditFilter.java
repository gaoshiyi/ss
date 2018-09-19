/**
 * 
 */
package com.sstc.hmis.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.sstc.hmis.conf.cas.ShiroCasConfiguration;
import com.sstc.hmis.model.constants.PortalConstants;
import com.sstc.hmis.permission.data.Staff;

/**
  * <p> Title: NightAuditFilter </p>
  * <p> Description:  夜审拦截器 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2018年1月29日 上午9:48:33
   */
public class NightAuditFilter implements Filter{
	
	private static final String HEADER_NAME_AJAX = "X-Requested-With";
	
	private static final String HEADER_VALUE_AJAX = "XMLHttpRequest";
	
	private static final int SESSION_EXPIRE_CODE = 598;
	
	private static final String NA_MSG_PAGE = "/namsgpage.jsp";
	
	private static final String NA_REQ_URL = "/cash/nightaudit";
	
	private static final String[] NA_URL = new String[]{NA_MSG_PAGE, NA_REQ_URL};
	
	public static final String NIGHT_AUDITING = "1";
	
	private static final Logger logger = LoggerFactory.getLogger(NightAuditFilter.class);
	
	RedisTemplate<String, String> redisTemplate;
	
	public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Assert.notNull(redisTemplate, "redisTemplate不能为空,请检查NigntAuditFilter实例化代码");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse) response;
		Subject subject = SecurityUtils.getSubject();
		String uri = req.getRequestURI();
		
		if(!isNightAuditUrl(uri) && //不过滤夜审和夜审提示请求
				!ShiroCasConfiguration.isExculdeUrl(uri) && //不过来配置的excldeUrl
				subject != null && subject.getSession()!=null) {
			Session session = subject.getSession();
			Staff staff = (Staff)session.getAttribute(PortalConstants.SESSION_USER_KEY);
			if(staff != null) {
				String id = staff.getHotelId();
				String key = "NA_FLAG_"+ id;
				if(redisTemplate.hasKey(key)) {
					String na_flag = redisTemplate.opsForValue().get(key);
					if(StringUtils.equals(NIGHT_AUDITING, na_flag)) {
						String axjxHeader = req.getHeader(HEADER_NAME_AJAX);
						try {
							if(StringUtils.equalsIgnoreCase(axjxHeader, HEADER_VALUE_AJAX)){//如果是ajax响应全局response code
								res.sendError(SESSION_EXPIRE_CODE, "夜审中，请稍后再试。。。");
								return;
							}else{
								req.getRequestDispatcher(NA_MSG_PAGE).forward(req, res);
								return;
							}
						} catch (Exception e) {
							logger.error("夜审中，提示或重定向失败",e);
						}
					}
				}
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}
	
	private boolean isNightAuditUrl(final String reqUri){
		if(ArrayUtils.contains(NA_URL, reqUri)){
			return true;
		}else{
			return false;
		}
	}

}
