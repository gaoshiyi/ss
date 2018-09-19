/**
 * 
 */
package com.sstc.hmis.filter;

import java.io.IOException;
import java.text.MessageFormat;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSON;
import com.sstc.hmis.logger.bean.LoggerUser;
import com.sstc.hmis.model.constants.CacheConstants;
import com.sstc.hmis.model.constants.PortalConstants;
import com.sstc.hmis.model.datasource.DataSourceContextHolder;
import com.sstc.hmis.permission.data.GroupHotel;
import com.sstc.hmis.permission.data.Staff;
import com.sstc.hmis.permission.service.GrpHotelService;
import com.sstc.hmis.permission.web.LoginInfoHolder;

/**
  * <p> Title: FeignContextFilter </p>
  * <p> Description:  处理feign消费者header中的用户信息 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年6月29日 下午7:05:46
   */
public class FeignContextFilter implements Filter{

	
	private static final Logger LOG = LoggerFactory.getLogger(FeignContextFilter.class);
	
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
		
	}

	private static final String CHARSET_UTF_8 = "utf-8";
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		String staffJson = req.getHeader("staff");
		
		if(StringUtils.isNotBlank(staffJson)){
			staffJson = new String(staffJson.getBytes("ISO-8859-1"),CHARSET_UTF_8);
			Staff staff = (Staff) JSON.parseObject(staffJson, Staff.class);
			LoginInfoHolder.setLoginInfo(staff);
			String hotelId = LoginInfoHolder.getLoginHotelId();
			LoggerUser log = new LoggerUser(staff.getAccount(), hotelId, staff.getGrpId());
			req.getSession().setAttribute(PortalConstants.SESSION_LOGGER_KEY, log);
			
			String key = MessageFormat.format(CacheConstants.HOTEL_INFO, hotelId);
			GroupHotel hotel = redisTemplate.opsForValue().get(key);
			if(hotel == null){
				hotel = grpHotelService.findGroupHotelById(hotelId);
				redisTemplate.opsForValue().set(key, hotel);
			}
			if(hotel != null){
				LOG.debug("请求接口{}的登录用户:{},数据库是:{}",req.getRequestURI(), staff.getAccount(),hotel.getCode());
				LoginInfoHolder.setHotel(hotel);
				if(DataSourceContextHolder.containsDataSource(hotel.getCode())){
					DataSourceContextHolder.setDataSource(hotel.getCode());
				}else{
					DataSourceContextHolder.setDataSource(DataSourceContextHolder.DEFAULT_DS_ALIAS);
				}
			}
			LOG.debug("qxx--当前线程 {} 的登录用户 {} 工作酒店Code :{}",
					Thread.currentThread().getId(),
					LoginInfoHolder.getLoginAccount(),
					hotel.getCode());
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}

	
	
}
