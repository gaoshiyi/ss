package com.sstc.hmis.log.service.api.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.sstc.hmis.log.data.LogOperation;
import com.sstc.hmis.log.dbaccess.dao.OperationLogMapper;
import com.sstc.hmis.log.dbaccess.data.OperationLog;
import com.sstc.hmis.log.service.OperationLogService;
import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.permission.web.LoginInfoHolder;
import com.sstc.hmis.util.bean.utils.BeanUtils;

@RestController
public class OperationLogServiceImp implements OperationLogService {
	
	@Autowired
	private OperationLogMapper operationLogMapper ;

	@Override
	public List<String> selectLogFun(String tableName) {		
		return operationLogMapper.selectLogFun(tableName,LoginInfoHolder.getLoginHotelId());
	}

	@Override
	public List<String> selectLogType(String tableName, String clFun) {
		return operationLogMapper.selectLogType(tableName, clFun,LoginInfoHolder.getLoginHotelId());
	}

	@Override
	public PageResult<LogOperation> selectLogData(int pageSize, int pageIndex,@RequestBody LogOperation logOperation) {
		PageResult<LogOperation> pageResult = new PageResult<LogOperation>();
        PageHelper.startPage(pageIndex + 1, pageSize);
        OperationLog operationLog = new OperationLog() ;
        operationLog.setClHotelId(LoginInfoHolder.getLoginHotelId()); 
        if(StringUtil.isNotEmpty(logOperation.getAccno())){
            operationLog.setClAccnos(logOperation.getAccno().split(",")); 
        }
        if(StringUtil.isNotEmpty(logOperation.getRoomnos())){
            operationLog.setClRoomnos(logOperation.getRoomnos().split(",")); 
        }
        if(StringUtil.isNotEmpty(logOperation.getCreateBy())){
            operationLog.setClCreateBys(logOperation.getCreateBy().split(",")); 
        }
        operationLog.setStartDate(logOperation.getStartDate());
        operationLog.setEndDate(logOperation.getEndDate());
        //operationLog.setClCreateBy(logOperation.getCreateBy());
        operationLog.setClFun(logOperation.getFun());
        operationLog.setClOperation(logOperation.getOperation());
        operationLog.setTableName(logOperation.getTableName());
        List<OperationLog> listLog = operationLogMapper.selectLogByClass(operationLog) ;
        Page<OperationLog> page = (Page<OperationLog>)listLog;
        List<LogOperation> listResult = new ArrayList<LogOperation>();
        
        for (OperationLog log : listLog){
        	LogOperation logOper = new LogOperation();
            try{
            	logOper = BeanUtils.copyDbBean2ServiceBean(log, logOper);
            	logOper.setTableName(logOperation.getTableName()); 
            	logOper.setModular(logOperation.getModular());
            }
            catch (IllegalArgumentException | IllegalAccessException e){
                e.printStackTrace();
            }
            listResult.add(logOper);
        }
        long total = page.getTotal();
        pageResult.setResult(true);
        pageResult.setResults(total);
        pageResult.setRows(listResult);
        
        return pageResult;
	}

	@Override
	public LogOperation selectLogById(String tableName, String clId) {
		OperationLog operationLog = operationLogMapper.selectLogById(tableName,clId) ;
		LogOperation logOper = new LogOperation();
        try{
        	logOper = BeanUtils.copyDbBean2ServiceBean(operationLog, logOper);
        }
        catch (IllegalArgumentException | IllegalAccessException e){
            e.printStackTrace();
        }
		return logOper;
	}


}
