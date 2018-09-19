package com.sstc.hmis.permission.service.api.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sstc.hmis.model.constants.Constants;
import com.sstc.hmis.model.data.AjaxResult;
import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.model.exception.AppException;
import com.sstc.hmis.permission.data.Department;
import com.sstc.hmis.permission.dbaccess.dao.PermsTDepartmentMapper;
import com.sstc.hmis.permission.dbaccess.data.PermsTDepartment;
import com.sstc.hmis.permission.dbaccess.data.PermsTDepartmentExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTDepartmentExample.Criteria;
import com.sstc.hmis.permission.service.DepartmentService;
import com.sstc.hmis.permission.web.LoginInfoHolder;
import com.sstc.hmis.util.HashUtils;
import com.sstc.hmis.util.bean.utils.BeanUtils;

/**
 * 
 * 部门管理Service
 * <p/>
 * 
 * @author <a href="mailto:cuihu@sstcsoft.com">cuihu</a>
 * @version Date: 2017年4月12日 下午2:28:58
 * @serial 1.0
 * @since 2017年4月12日 下午2:28:58
 */
@RestController
public class DepartmentServiceImpl extends BaseServiceImpl<Department> implements DepartmentService {

	@Autowired
	private PermsTDepartmentMapper permsTDepartmentMapper;
	
	private AjaxResult ajaxResult;

	@Override
	public List<Department> listAll(@RequestBody Department department) {
		PermsTDepartmentExample example = new PermsTDepartmentExample();
		Criteria createCriteria = example.createCriteria();

		if (StringUtils.isNotBlank(department.getHotelId())) {
			createCriteria.andClHotelIdEqualTo(department.getHotelId());
		}
		if (StringUtils.isNotBlank(department.getGrpId())) {
			createCriteria.andClGrpIdEqualTo(department.getGrpId());
			createCriteria.andClHotelIdIsNull();
		}
		createCriteria.andClStatusEqualTo(Constants.STATUS_NORMAL);
		createCriteria.andClPidIsNull();
		List<Department> list = new ArrayList<Department>();
		List<PermsTDepartment> departmentList = permsTDepartmentMapper.selectByExample(example);
		for (PermsTDepartment permsTDepartment : departmentList) {
			department = convertPermsTDepartment2Department(permsTDepartment);
			list.add(department);
		}
		return list;
	}

	@Override
	public Department find(@RequestBody Department department) {
		PermsTDepartment permsTDepartment = permsTDepartmentMapper.selectByPrimaryKey(department.getId());
		return convertPermsTDepartment2Department(permsTDepartment);
	}

	@Override
	public AjaxResult updateDpt(@RequestBody Department department) throws AppException {
		ajaxResult = new AjaxResult(Constants.RES_CODE_FAILED, null);
		if(isNameExists(department)){
			ajaxResult.setCode(Constants.RES_CODE_NAME_EXISTS);
			return ajaxResult;
		}else if(isCodeExists(department)){
			ajaxResult.setCode(Constants.RES_CODE_CODE_EXISTS);
			return ajaxResult;
		}
		PermsTDepartment permsTDepartment = convertDepartment2PermsTDepartment(department);
		permsTDepartment.setClModifyTime(new Date());
		permsTDepartment.setClModifyBy(LoginInfoHolder.getLoginInfo().getAccount());
		int line = permsTDepartmentMapper.updateByPrimaryKeySelective(permsTDepartment);
		if(line > 0){
			ajaxResult.setCode(Constants.RES_CODE_SUCCESS);
			ajaxResult.setObj(department);
		}
		return ajaxResult;
	}

	@Override
	public AjaxResult insertDpt(@RequestBody Department department) throws AppException {
		ajaxResult = new AjaxResult(Constants.RES_CODE_FAILED, null);
		if(isNameExists(department)){
			ajaxResult.setCode(Constants.RES_CODE_NAME_EXISTS);
			return ajaxResult;
		}else if(isCodeExists(department)){
			ajaxResult.setCode(Constants.RES_CODE_CODE_EXISTS);
			return ajaxResult;
		}
		PermsTDepartment permsTDepartment = convertDepartment2PermsTDepartment(department);
		permsTDepartment.setClCreateTime(new Date());
		permsTDepartment.setClCreateBy(LoginInfoHolder.getLoginInfo().getAccount());
		permsTDepartment.setClId(HashUtils.uuidGenerator());
		int line = permsTDepartmentMapper.insert(permsTDepartment);
		if(line > 0){
			ajaxResult.setCode(Constants.RES_CODE_SUCCESS);
			ajaxResult.setObj(department);
		}
		return ajaxResult;
	}
	
	/**
	 * 验证部门Code，酒店内唯一，不论状态。
	 * @param department
	 * @return
	 */
	private boolean isCodeExists(Department department){
		PermsTDepartmentExample example = new PermsTDepartmentExample();
		PermsTDepartmentExample.Criteria criteria = example.createCriteria();
		criteria.andClCodeEqualTo(department.getCode());
		criteria.andClHotelIdEqualTo(department.getHotelId());
		
		String id = department.getId();
		if(StringUtils.isNoneBlank(id)){
			criteria.andClIdNotEqualTo(id);
		}
		
		List<PermsTDepartment> list = permsTDepartmentMapper.selectByExample(example);
		if(list != null && list.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 验证部门名称，酒店内非删除状态唯一
	 * @param department
	 * @return
	 */
	private boolean isNameExists(Department department){
		PermsTDepartmentExample example = new PermsTDepartmentExample();
		PermsTDepartmentExample.Criteria criteria = example.createCriteria();
		criteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
		criteria.andClNameEqualTo(department.getName());
		criteria.andClHotelIdEqualTo(department.getHotelId());
		
		String id = department.getId();
		if(StringUtils.isNoneBlank(id)){
			criteria.andClIdNotEqualTo(id);
		}
		
		List<PermsTDepartment> list = permsTDepartmentMapper.selectByExample(example);
		if(list != null && list.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public PageResult<Department> list(int index, int size, @RequestBody Department department) {
		PageResult<Department> pageResult = new PageResult<>();
		PageHelper.startPage(index + 1, size);
		department.setStatus(Constants.STATUS_NORMAL);
		PermsTDepartment tDepartment = convertDepartment2PermsTDepartment(department);
		Page<PermsTDepartment> page = (Page<PermsTDepartment>) permsTDepartmentMapper.selectBySelective(tDepartment);
		long total = page.getTotal();
		List<PermsTDepartment> tDepartmentList = page.getResult();
		List<Department> departmentlist = new ArrayList<>();
		for (PermsTDepartment permsTDepartment : tDepartmentList) {
			department = convertPermsTDepartment2Department(permsTDepartment);
			departmentlist.add(department);
		}
		pageResult.setResult(true);
		pageResult.setResults(total);
		pageResult.setRows(departmentlist);
		return pageResult;
	}

	@Override
	public int deleteDepartmentByPrimaryKey(String id) throws AppException {
		
		if(StringUtils.isNoneBlank(id)){
			String[] ids = id.split(",");
			if(ids != null && ids.length > 0){
				List<String> idList = Arrays.asList(ids);
				if(canDptDel(idList)){
					Map<String,Object> condition = new HashMap<>();
					condition.put("ids", idList);
					condition.put("clModifyTime", new Date());
					condition.put("clModifyBy", LoginInfoHolder.getLoginAccount());
					permsTDepartmentMapper.deletePostByDptId(condition);
					permsTDepartmentMapper.deleteDptPost(condition);
					int effectLine = permsTDepartmentMapper.deleteByPrimaryKeys(condition);
					return effectLine;
				}else{
					return -1;
				}
			}
		}
		return 0;
	}
	
	
	/**
	 * 部门删除逻辑 ： 1、先检查部门下是否有员工，有关联员工不能删除
	 * @param ids 部门ID列表
	 * @return
	 */
	private boolean canDptDel(List<String> ids){
		Map<String,Object> condition = new HashMap<>(1);
		condition.put("ids", ids);
		int count = permsTDepartmentMapper.countDptStaff(condition);
		if(count > 0){
			return false;
		}else{
//			2、无关联员工，检查是否有职位，有职位不能删除
//			count = permsTDepartmentMapper.countDptPost(condition);
//			if(count > 0){
//				return false;
//			}
		}
		return true;
	}

	public Department convertPermsTDepartment2Department(PermsTDepartment permsTDepartment) {
		Department department = new Department();
		try {
			BeanUtils.copyDbBean2ServiceBean(permsTDepartment, department);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return department;
	}

	public PermsTDepartment convertDepartment2PermsTDepartment(Department department) {
		PermsTDepartment permsTDepartment = new PermsTDepartment();
		try {
			BeanUtils.copyServiceBean2DbBean(department, permsTDepartment);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return permsTDepartment;
	}
	
	@Override
	public List<String> findDepartmentChildId(String pid) {
		PermsTDepartmentExample departmentExample = new PermsTDepartmentExample();
		Criteria createCriteria = departmentExample.createCriteria();
		createCriteria.andClPidEqualTo(pid);
		List<PermsTDepartment> permsTDepartmentList = permsTDepartmentMapper.selectByExample(departmentExample);
		List<String> idList = new ArrayList<String>();
		for(PermsTDepartment permsTDepartment : permsTDepartmentList){
			String deptId = permsTDepartment.getClId();
			idList.add(deptId);
		}
		return idList;
	}
	
	@Override
	public int countDptStaffSize(String id){
		List<String> idList = new ArrayList<>(1);
		idList.add(id);
		Map<String,Object> condition = new HashMap<>(1);
		condition.put("ids", idList);
		return permsTDepartmentMapper.countDptStaff(condition);
	}

	/**
	 * 通过部门id查询部门信息
	 * @author CKang
	 * @date 2017年5月19日 下午2:15:44
	 * @param deptId
	 * @return
	 */
	@Override
	public Department findDepartmentById(String deptId) {
		return convertPermsTDepartment2Department(permsTDepartmentMapper.selectByPrimaryKey(deptId));
	}

	@Override
	public List<Department> findDeptNameByDeptId(String hotelId){
		List<PermsTDepartment> PermsTDepartmentList = permsTDepartmentMapper.findDeptNameByDeptId(hotelId);
		List<Department> departmentList = new ArrayList<Department>();
		for(PermsTDepartment permsTDepartment : PermsTDepartmentList){
			departmentList.add(convertPermsTDepartment2Department(permsTDepartment));
		}
		return departmentList;
	}

	/**
	 * 根据部门id查询部门及其子部门的职位数量
	 * @author CKang
	 * @date 2017年5月23日 下午1:45:03
	 * @param id
	 * @return
	 */
	@Override
	public int countDptPostSize(String id) {
		List<String> idList = new ArrayList<>(1);
		idList.add(id);
		Map<String,Object> condition = new HashMap<>(1);
		condition.put("ids", idList);
		return permsTDepartmentMapper.countDptPost(condition);
	}

	@Override
	public List<Department> findChildDeptList(String pid, String hotelId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pid", pid);
		paramMap.put("hotelId", hotelId);
		List<PermsTDepartment> permsTDepList = permsTDepartmentMapper.findChildDeptList(paramMap);
		List<Department> departmentList = new ArrayList<Department>();
		if (permsTDepList != null && permsTDepList.size() > 0) {
			for (PermsTDepartment permsTDepartment : permsTDepList) {
				Department department = new Department();
				department.setId(permsTDepartment.getClId());
				department.setName(permsTDepartment.getClName());
				departmentList.add(department);
			}
		}
		return departmentList;
	}


}
