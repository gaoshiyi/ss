/**
 * 
 */
package com.sstc.hmis.permission.service;

import java.util.List;

import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.model.exception.AppException;

/**
  * <p> Title: BaseService </p>
  * <p> Description:  对象基础业务 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年3月31日 上午10:38:20
   */
public interface BaseService<T> {

	/**
	 * 查询对象
	 * @param t
	 * @return
	 * @throws AppException
	 */
	T find(T t) throws AppException ;
	
	/**
	 * 更新对象
	 * @param t
	 * @return
	 * @throws AppException
	 */
	int update(T t) throws AppException ;
	
	/**
	 * 插入对象
	 * @param t
	 * @return
	 * @throws AppException
	 */
	int insert(T t) throws AppException ;
	
	/**
	 * 查询对象列表
	 * @param t
	 * @return
	 * @throws AppException
	 */
	List<T> list(T t) throws AppException ;
	
	/**
	 * 分页查询
	 * @param index 
	 * @param size
	 * @param t
	 * @return
	 * @throws AppException
	 */
	PageResult<T> list(int index,int size,T t) throws AppException ;
	
	
	/**
	 * 分页查询
	 * @param index 
	 * @param size
	 * @param t
	 * @return
	 * @throws AppException
	 */
	PageResult<T> list(PageResult<T> pageInfo,T t) throws AppException ;
}
