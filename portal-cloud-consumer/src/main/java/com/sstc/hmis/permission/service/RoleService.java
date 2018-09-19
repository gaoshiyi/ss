/**
 * 
 */
package com.sstc.hmis.permission.service;

import java.util.List;
import java.util.Set;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sstc.hmis.model.data.Result;
import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.model.exception.AppException;
import com.sstc.hmis.permission.data.Role;
import com.sstc.hmis.permission.data.Staff;

/**
 * Title: RoleService
 * Description: 角色信息
 * @Company: SSTC
 * @author CKang
 * @date 2017年4月11日 下午2:23:55
 */
@FeignClient(name = "${feign.provider.portal}")
@RequestMapping("/roleService")
public interface RoleService {

	/**
	 * 根据所属集团或酒店、状态、中文名称以及英文名称组合条件进行查询
	 * @author CKang
	 * @date 2017年4月11日 下午2:24:10
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/findAllRole" , method = RequestMethod.POST)
	public PageResult<Role> findAllRole(@RequestBody Role role,@RequestParam("pageIndex")int pageIndex, @RequestParam("pageSize")int pageSize);
	
	/**
	 * 通过角色id删除(物理删除)
	 * @author CKang
	 * @date 2017年4月11日 下午2:24:20
	 * @param id
	 */
	@RequestMapping(value = "/deleteRoleById" , method = RequestMethod.GET)
	public void deleteRoleById(@RequestParam("id")String id);
	
	/**
	 * 批量删除角色(物理删除)
	 * @author CKang
	 * @date 2017年4月11日 下午2:24:28
	 * @param ids
	 */
	@RequestMapping(value = "/batchDeleteRole" , method = RequestMethod.GET)
	public void batchDeleteRole(@RequestParam("ids")String ids);
	
	/**
	 * 通过用户id查询角色列表(右侧已选角色)
	 * @author CKang
	 * @date 2017年4月11日 下午2:24:37
	 * @param staffId
	 * @return
	 */
	@RequestMapping(value = "/loadRightRoleList" , method = RequestMethod.POST)
	public List<Role> loadRightRoleList(@RequestParam("staffId")String staffId,@RequestBody List<String> hotelIds);
	
	
	/**
	 * 通过用户id查询角色列表(左侧备选角色)
	 * @author CKang
	 * @date 2017年4月11日 下午2:24:50
	 * @param staffId
	 * @return
	 */
	@RequestMapping(value = "/loadLeftRoleList" , method = RequestMethod.POST)
	public List<Role> loadLeftRoleList(@RequestParam("staffId") String staffId,@RequestBody List<String> hotelIds);
	
	
	/**
	 * 用户添加角色信息
	 * @author CKang
	 * @date 2017年4月11日 下午2:25:01
	 * @param staffId
	 * @param roleIds
	 */
	@RequestMapping(value = "/saveStaffRoleList" , method = RequestMethod.POST)
	public void saveStaffRoleList(@RequestParam("staffId")String staffId, @RequestParam("roleIds")String[] roleIds);
	
	
	/**
	 * 角色添加用户信息
	 * @author CKang
	 * @date 2017年4月11日 下午3:09:58
	 * @param roleId
	 * @param staffIds
	 */
	@RequestMapping(value = "/saveRoleStaffList" , method = RequestMethod.POST)
	public void saveRoleStaffList(@RequestParam("roleId")String roleId, @RequestParam("staffIds")String[] staffIds);
	
	/**
	 * 角色添加用户信息（修改后）
	 * @author CKang
	 * @date 2017年4月11日 下午3:09:58
	 * @param roleId
	 * @param staffIds
	 */
	@RequestMapping(value = "/saveRoleStaffList2" , method = RequestMethod.POST)
	public void saveRoleStaffList2(@RequestParam("roleId")String roleId, @RequestParam("staffIds")String[] staffIds,@RequestParam("hotelId")String hotelId);
	
	
	/**
	 * 通过角色id查询员工id集合
	 * @author CKang
	 * @date 2017年4月11日 下午5:10:07
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/findStaffIdListByRoleId" , method = RequestMethod.POST)
	public List<String> findStaffIdListByRoleId(@RequestParam("roleId")String roleId);
	
	/**
	 * 通过角色id和酒店id查询员工id集合
	 * @author CKang
	 * @date 2017年5月16日 下午5:03:54
	 * @param roleId
	 * @param hotelId
	 * @return
	 */
	@RequestMapping(value = "/findStaffIdListByRoleIdAndHotelId" , method = RequestMethod.GET)
	public List<String> findStaffIdListByRoleIdAndHotelId(@RequestParam("roleId")String roleId,@RequestParam("hotelId")String hotelId);
	
	
	/**
	 * 添加角色信息(新增)
	 * @author CKang
	 * @date 2017年4月11日 下午2:25:14
	 * @param role
	 * @param permissionId
	 */
	@RequestMapping(value = "/saveRole" , method = RequestMethod.POST)
	public Result saveRole(@RequestBody Role role, @RequestParam("permissionId")String[] permissionId);
	
	/**
	 * 编辑角色前（回显数据）
	 * @author CKang
	 * @date 2017年4月11日 下午2:25:21
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/editBeforeRole" , method = RequestMethod.GET)
	public Role editBeforeRole(@RequestParam("roleId")String roleId);
	
	/**
	 * 修改角色信息（编辑之后）
	 * @author CKang
	 * @date 2017年4月11日 下午2:25:29
	 * @param role
	 * @param permissionId
	 */
	@RequestMapping(value = "/updateRole" , method = RequestMethod.POST)
	public void updateRole(@RequestBody Role role, @RequestParam("permissionId")String[] permissionId)throws AppException;
	
	/**
	 * 修改角色状态（锁定或解锁）
	 * @author CKang
	 * @date 2017年4月14日 下午3:00:50
	 * @param roleId
	 */
	@RequestMapping(value = "/updateRoleStatus" , method = RequestMethod.GET)
	public String updateRoleStatus(@RequestParam("roleId")String roleId);
	
	
	/**
	 * 根据所属集团或酒店id查询角色信息
	 * @author CKang
	 * @date 2017年4月18日 下午2:46:24
	 * @param belongId
	 * @return
	 */
	@RequestMapping(value = "/findRoleByBelongId" , method = RequestMethod.GET)
	public List<Role> findRoleByBelongId(@RequestParam("belongId")String belongId,@RequestParam("roleName")String roleName,@RequestParam("postIds")String postIds);
	
	/**
	 * 通过多个角色id查询角色信息
	 * @author CKang
	 * @date 2017年4月19日 下午1:26:09
	 * @param roleIds
	 * @return
	 */
	@RequestMapping(value = "/findRoleByIds" , method = RequestMethod.POST)
	public List<Role> findRoleByIds(@RequestParam("roleIds")String[] roleIds);
	
	/**
	 * 查询本酒店以外的权限信息
	 * @author CKang
	 * @date 2017年4月21日 上午11:09:17
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/findNotLocalRoleList" , method = RequestMethod.POST)
	public List<Role> findNotLocalRoleList(@RequestBody Role role);
	
	/**
	 * 复制角色
	 * @author CKang
	 * @date 2017年4月21日 下午2:38:41
	 * @param roleIds
	 */
	@RequestMapping(value = "/copyRole" , method = RequestMethod.GET)
	public void copyRole(@RequestParam("roleIds")String roleIds)throws AppException;
	
	/**
	 * 去掉角色名称相同的对象
	 * @author CKang
	 * @date 2017年4月21日 下午4:18:38
	 * @return
	 */
	@RequestMapping(value = "/noRepeat" , method = RequestMethod.POST)
	public List<Role> noRepeat(@RequestBody List<Role> list);
	
	/**
	 * 通过角色id查询有效的员工的数量
	 * @author CKang
	 * @date 2017年4月26日 下午4:50:44
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/findStaffCountByRoleId" , method = RequestMethod.POST)
	public int findStaffCountByRoleId(@RequestBody Role role);
	
	/**
	 * 通过id查询角色信息
	 * @author CKang
	 * @date 2017年5月17日 下午4:21:22
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/findRoleById" , method = RequestMethod.GET)
	public Role findRoleById(@RequestParam("roleId")String roleId);
	
	@RequestMapping(value = "/findUserRole" , method = RequestMethod.POST)
	public Set<String> findUserRole(@RequestBody Staff u);

}
