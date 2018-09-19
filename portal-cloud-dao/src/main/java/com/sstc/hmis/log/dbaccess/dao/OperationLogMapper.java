package com.sstc.hmis.log.dbaccess.dao;

import com.sstc.hmis.log.dbaccess.data.OperationLog;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface OperationLogMapper {
    
    /**
     * 查询日志
     * @param tableName
     * @return
     */
    List<OperationLog> selectLogByClass(OperationLog operationLog);
    
    /**
     * 查询功能list
     * @param tableName
     * @return
     */
    List<String> selectLogFun(@Param("tableName") String tableName,@Param("clHotelId") String clHotelId) ;
    
    /**
     * 查询日志类型list
     * @param tableName
     * @param clFun
     * @return
     */
    List<String> selectLogType(@Param("tableName") String tableName,@Param("clFun") String clFun,@Param("clHotelId") String clHotelId) ;
    
    
   /**
    * 根据id查询单条日志记录
    * @param tableName
    * @param clId
    * @return
    */
    OperationLog selectLogById(@Param("tableName") String tableName,@Param("clId") String clId) ;
}