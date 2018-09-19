package com.sstc.hmis.conf.cas.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CasSessionListener implements SessionListener {
	
    private static Logger logger  = LoggerFactory.getLogger(CasSessionListener.class);
    
	@Override
	public void onStart(Session session) {
		logger.warn("Session id:'"+session.getId()+"' have started!!!!");
		logger.warn("Session timeout:'"+session.getTimeout()+"'");
		logger.warn("Session class:'"+session.getClass()+"'");
		logger.warn("Session object:'"+session+"'");
	}

	@Override
	public void onStop(Session session) {
		logger.warn("Session id:'"+session.getId()+"' have stoped!!!!");
		
	}

	@Override
	public void onExpiration(Session session) {
		logger.warn("Session id:'"+session.getId()+"' have been expiration!!!!");
		
	}

}
