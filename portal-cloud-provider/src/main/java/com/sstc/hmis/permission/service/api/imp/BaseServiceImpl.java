/**
 * 
 */
package com.sstc.hmis.permission.service.api.imp;

import java.util.List;

import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.model.exception.AppException;
import com.sstc.hmis.permission.service.BaseService;

/**
  * <p> Title: BaseServiceImp </p>
  * <p> Description:  TODO </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年3月31日 上午11:51:12
   */
public class BaseServiceImpl<T> implements BaseService<T>{

	@Override
	public T find(T t) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(T t) throws AppException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(T t) throws AppException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<T> list(T t) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageResult<T> list(int index, int size, T t) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.sstc.hmis.permission.service.BaseService#list(com.sstc.hmis.model.data.page.PageResult, java.lang.Object)
	 */
	@Override
	public PageResult<T> list(PageResult<T> pageInfo, T t) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

}
