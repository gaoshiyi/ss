package com.sstc.hmis.task;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class AsynTaskManager implements InitializingBean{

	private ThreadPoolExecutor executorService;
	
	private int corePoolSize;
	
	private int maximumPoolSize;
	
	private int keepAliveTime;
	
	private int workQueueSize;
	
	
	
	public AsynTaskManager(int corePoolSize, int maximumPoolSize, int keepAliveTime,
			int workQueueSize) {
		super();
		this.corePoolSize = corePoolSize;
		this.maximumPoolSize = maximumPoolSize;
		this.keepAliveTime = keepAliveTime;
		this.workQueueSize = workQueueSize;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.isTrue(corePoolSize>0, "corePoolSize value must be greater than zero!");
		Assert.isTrue(maximumPoolSize>0, "maximumPoolSize value must be greater than zero!");
		Assert.isTrue(keepAliveTime>0, "keepAliveTime value must be greater than zero!");
		Assert.isTrue(workQueueSize>0, "workQueueSize value must be greater than zero!");
		
		this.executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(workQueueSize));
	}

	/**
	 * 提交任务到线程池执行
	 * @param task 执行任务
	 */
	public void submitTask(AsynTask task) {
		this.executorService.execute(task);
	}
}
