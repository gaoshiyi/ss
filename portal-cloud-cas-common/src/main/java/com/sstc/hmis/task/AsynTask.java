package com.sstc.hmis.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sstc.hmis.logger.bean.LoggerInfo;
import com.sstc.hmis.logger.threadlocal.LoggerInfoHolder;
import com.sstc.hmis.model.datasource.DataSourceContextHolder;
import com.sstc.hmis.permission.data.GroupHotel;
import com.sstc.hmis.permission.data.Staff;
import com.sstc.hmis.permission.web.LoginInfoHolder;

public abstract class AsynTask implements Runnable {
    
	private static final Logger log = LoggerFactory.getLogger(AsynTask.class);
	
	private Staff staff;

	private GroupHotel hotelHolder;

	private String sessionHolder;
	
	private LoggerInfo info;

	public AsynTask() {
		this.staff = LoginInfoHolder.getLoginInfo();
		this.hotelHolder = LoginInfoHolder.getHotel();
		this.sessionHolder = LoginInfoHolder.getSessionId();
	}

	/**
	 * 处理用户业务逻辑
	 */
	protected abstract void processBusinessLogic();

	@Override
	public void run() {
		//传递登陆信息到新的线程
		LoginInfoHolder.setHotel(this.hotelHolder);
		LoginInfoHolder.setLoginInfo(this.staff);
		LoginInfoHolder.setSessionId(this.sessionHolder);
		DataSourceContextHolder.setDataSource(LoginInfoHolder.getHotelCode());
		// 记日志
		info = new LoggerInfo();
		info.setGrpId(LoginInfoHolder.getLoginGrpId());
		info.setHotelId(LoginInfoHolder.getLoginHotelId());
		info.setPrincipal(LoginInfoHolder.getLoginAccount());
		LoggerInfoHolder.setLoggerInfo(this.info);
		try {
			// 处理用户业务逻辑
			processBusinessLogic();
		} catch (Throwable e) {
			log.error("线程池执行发生错误！！！",e);
		}
	}
}
