package com.sstc.hmis.statusSync.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.sstc.hmis.statusSync.handler.UserSessionHandler;

/**
  * <p> Title: DataSyncFilter </p>
  * <p> Description:  TODO </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年9月6日 下午1:13:13
   */
public class DataSyncFilter implements Filter{
	
	private static final Logger LOG = LoggerFactory.getLogger(DataSyncFilter.class);
	
	public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	@Value("${hostname.portal}")
	private static String portalUri;

	private StringRedisTemplate stringRedisTemplate;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		if(LOG.isDebugEnabled()){
			LOG.debug("Hey,Body!这个过滤器是用来做数据同步的，比如，酒店、语言、班次");
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		UserSessionHandler handler = new UserSessionHandler();
		handler.setRedisTemplate(stringRedisTemplate);
		final HttpServletRequest req = (HttpServletRequest) request;
//		final HttpServletResponse res = (HttpServletResponse) response;
		if (handler.isSessionSendReq(req)) {
			LOG.debug("使用消息对来来处理数据了");
//			handler.recordUserSessionInfo(req, res);
		} else if (handler.isDataSyncReq(req)) {
			LOG.debug("使用消息对来来处理数据了");
//			handler.dataUpdate(req, res, sessionManager);
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		if(LOG.isDebugEnabled()){
			LOG.debug("Oh,Fuck!服务停止了还是异常挂了？？？");
		}
	}

}
