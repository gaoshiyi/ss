package com.sstc.hmis.permission.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sstc.hmis.model.data.AjaxResult;
import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.model.exception.AppException;
import com.sstc.hmis.permission.data.Department;

/**
 * 
 * 部门管理Service
 * <p/>
 * 
 * @author <a href="mailto:cuihu@sstcsoft.com">cuihu</a>
 * @version  Date: 2017年4月12日 下午2:29:30
 * @serial 1.0
 * @since 2017年4月12日 下午2:29:30
 */
@FeignClient(name = "${feign.provider.portal}")
@RequestMapping("/departmentService")
public interface DepartmentService {
	
	@RequestMapping(value = "/deleteDepartmentByPrimaryKey" ,method = RequestMethod.POST)
	public int deleteDepartmentByPrimaryKey(@RequestParam("id") String id) throws AppException;
	
	/**
	 * 通过部门id查询该部门下的直属子部门
	 * @author CKang
	 * @date 2017年4月17日 下午5:27:31
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "/findDepartmentChildId" ,method = RequestMethod.POST)
	public List<String> findDepartmentChildId(@RequestParam("pid")String pid);
	
	/**
	 * 通过部门ID获取该部门下所有子部门信息
	 */
	@RequestMapping(value = "/findChildDeptList", method = RequestMethod.POST)
	List<Department> findChildDeptList(@RequestParam("pid") String pid, @RequestParam("hotelId") String hotelId);
	
	
	/**
	 * 更新对象
	 * @param t
	 * @return
	 * @throws AppException
	 */
	@RequestMapping(value = "/updateDpt" ,method = RequestMethod.POST)
	AjaxResult updateDpt(@RequestBody Department t) throws AppException ;
	
	/**
	 * 插入对象
	 * @param t
	 * @return
	 * @throws AppException
	 */
	@RequestMapping(value = "/insertDpt" ,method = RequestMethod.POST)
	AjaxResult insertDpt(@RequestBody Department t) throws AppException ;

	/**
	 * 根据部门ID查询部门及其子部门的员工数量
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/countDptStaffSize" ,method = RequestMethod.POST)
	public int countDptStaffSize(@RequestParam("id")String id);
	
	/**
	 * 根据部门id查询部门及其子部门的职位数量
	 * @author CKang
	 * @date 2017年5月23日 下午1:45:03
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/countDptPostSize" ,method = RequestMethod.POST)
	public int countDptPostSize(@RequestParam("id")String id);
	
	/**
	 * 通过部门id查询部门信息
	 * @author CKang
	 * @date 2017年5月19日 下午2:15:44
	 * @param deptId
	 * @return
	 */
	@RequestMapping(value = "/findDepartmentById" ,method = RequestMethod.POST)
	public Department findDepartmentById(@RequestParam("deptId")String deptId);
	
	/**
	 * 通过部门id查询所属部门名称
	 * @author CKang
	 * @date 2017年5月19日 下午5:44:51
	 * @param clId
	 * @return
	 */
	@RequestMapping(value = "/findDeptNameByDeptId" ,method = RequestMethod.POST)
	public List<Department> findDeptNameByDeptId(@RequestParam("hotelId")String hotelId);

	/**
	 * @param index
	 * @param size
	 * @param department
	 * @return
	 */
	@RequestMapping(value = "/list" ,method = RequestMethod.POST)
	public PageResult<Department> list(@RequestParam("index")int index, @RequestParam("size")int size, @RequestBody Department department);

	/**
	 * @param department
	 * @return
	 */
	@RequestMapping(value = "/find" ,method = RequestMethod.POST)
	public Department find(@RequestBody Department department);
	
	@RequestMapping(value = "/listAll", method = RequestMethod.POST)
	public List<Department> listAll(@RequestBody Department department);
}
