package com.sstc.hmis.log.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sstc.hmis.log.data.LogOperation;
import com.sstc.hmis.model.data.page.PageResult;

/**
 * 操作日志查询
 * @author JLei
 *
 */
@FeignClient(name = "${feign.provider.portal}")
@RequestMapping("/operationLogService")
public interface OperationLogService {
	
	/**
     * 查询功能list
     * @param tableName
     * @return
     */
	@RequestMapping(value = "/selectLogFun" ,method = RequestMethod.POST)
    List<String> selectLogFun(@RequestParam("tableName") String tableName) ;
    
    /**
     * 查询类型list
     * @param tableName
     * @param clFun
     * @return
     */
	@RequestMapping(value = "/selectLogType" ,method = RequestMethod.POST)
    List<String> selectLogType(@RequestParam("tableName") String tableName,@RequestParam("clFun") String clFun) ;
	
	
	
	@RequestMapping(value = "/selectLogData", method = RequestMethod.POST)
	public PageResult<LogOperation> selectLogData(@RequestParam("pageSize") int pageSize,
	        @RequestParam("pageIndex") int pageIndex, @RequestBody LogOperation logOperation);
	
	/**
	 * 根据id来查询日志记录
	 * @param tableName
	 * @param clId
	 * @return
	 */
	@RequestMapping(value = "/selectLogById", method = RequestMethod.POST)
	public LogOperation selectLogById(@RequestParam("tableName") String tableName,@RequestParam("clId") String clId) ;
	
}
