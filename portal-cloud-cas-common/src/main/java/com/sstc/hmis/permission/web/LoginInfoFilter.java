/**
 * 
 */
package com.sstc.hmis.permission.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.sstc.hmis.model.constants.PortalConstants;
import com.sstc.hmis.permission.data.GroupHotel;
import com.sstc.hmis.permission.data.Staff;
import com.sstc.hmis.permission.service.GrpHotelService;

/**
  * <p> Title: LoginInfoFilter </p>
  * <p> Description:  TODO </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年9月19日 下午3:19:07
   */
public class LoginInfoFilter implements Filter{

	private static final Logger log = LoggerFactory.getLogger(LoginInfoFilter.class);
	
	private RedisTemplate<String, GroupHotel> redisTemplate;
	
	private GrpHotelService grpHotelService;
	
	public void setGrpHotelService(GrpHotelService grpHotelService) {
		this.grpHotelService = grpHotelService;
	}

	public void setRedisTemplate(RedisTemplate<String, GroupHotel> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("初始化用户登录信息过滤器,登录后可以从LoginInfoHolder中获取相关用户信息");
		Assert.notNull(redisTemplate, "RedisTemplate未注入");
		Assert.notNull(grpHotelService, "rpHotelService未注入");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
//		Staff staff = getLoginStaff();
//		if(staff != null){
//			LoginInfoHolder.setLoginInfo(staff);
//			try {
//				String hotelId = LoginInfoHolder.getLoginHotelId();
//				String key = MessageFormat.format(CacheConstants.HOTEL_INFO, hotelId);
//				GroupHotel hotel = redisTemplate.opsForValue().get(key);
//				if(hotel == null){
//					hotel = grpHotelService.findGroupHotelById(hotelId);
//					redisTemplate.opsForValue().set(key, hotel);
//				}
//				LoginInfoHolder.setHotel(hotel);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		chain.doFilter(request, response);
	}

	/**
	 * 从上下文获取登录用户信息
	 * @return
	 */
	public Staff getLoginStaff(){
		Session session = SecurityUtils.getSubject().getSession();
		Staff staff = null;
		if(session != null){
			staff = (Staff) session.getAttribute(PortalConstants.SESSION_USER_KEY);
			if(log.isDebugEnabled()){
				log.debug("登录用户信息：{}",JSON.toJSONString(staff));
			}
		}
		return staff;
	}

	@Override
	public void destroy() {
		log.info("销毁用户登录信息过滤器,应用关闭或者异常挂掉了");
		redisTemplate = null;
		grpHotelService = null;
	}

}
