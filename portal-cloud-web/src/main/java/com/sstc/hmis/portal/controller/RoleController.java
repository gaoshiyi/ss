/**
 * 
 */
package com.sstc.hmis.portal.controller;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sstc.hmis.logger.annotation.HmisLogger;
import com.sstc.hmis.logger.constant.LoggerConstants;
import com.sstc.hmis.logger.threadlocal.LoggerInfoHolder;
import com.sstc.hmis.model.data.Result;
import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.model.exception.AppException;
import com.sstc.hmis.model.tree.TreeNode;
import com.sstc.hmis.permission.data.GroupHotel;
import com.sstc.hmis.permission.data.Permission;
import com.sstc.hmis.permission.data.PostResponsibility;
import com.sstc.hmis.permission.data.Role;
import com.sstc.hmis.permission.data.Staff;
import com.sstc.hmis.permission.service.EmployeeService;
import com.sstc.hmis.permission.service.GrpHotelService;
import com.sstc.hmis.permission.service.PermissionService;
import com.sstc.hmis.permission.service.PostResponsibilityService;
import com.sstc.hmis.permission.service.RoleService;
import com.sstc.hmis.permission.web.LoginInfoHolder;
import com.sstc.hmis.portal.common.base.BaseController;

/**
  * <p> Title: OrganizationController </p>
  * <p> Description:  角色信息控制器 </p>
  * <p> Company: SSTC </p> 
  * @author  cKang
   */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private GrpHotelService grpHotelService;
	@Autowired
	private PostResponsibilityService postResponsibilityService;
	@Autowired
	private EmployeeService employeeService;
	
	
	/**
	 * 跳转到角色管理页面
	 * @author CKang
	 * @date 2017年4月11日 下午4:22:56
	 * @param model
	 * @return
	 * @throws AppException 
	 */
	@RequestMapping("/index")
	@HmisLogger(action="role/index",msg="角色列表展示", type = LoggerConstants.LOG_QUERY)
	public String page(Model model) throws AppException {
		List<GroupHotel> groupHotelList = grpHotelService.list(new GroupHotel());
		model.addAttribute("groupHotelList", groupHotelList);
		model.addAttribute("hotelId", LoginInfoHolder.getLoginInfo().getHotelId());
		LoggerInfoHolder.setLoggerInfoData("abc", "bcd");
		return "manage/role/role";
	}
	
	/**
	 * 跳转到添加或编辑dialog框
	 * @author CKang
	 * @date 2017年4月14日 下午4:04:39
	 * @param roleId
	 * @param model
	 * @return
	 * @throws AppException
	 */
	@RequestMapping("/form")
	public String form(String roleId, Model model) throws AppException{
		if(StringUtils.isNotBlank(roleId)) {
			List<GroupHotel> groupHotelList = grpHotelService.list(new GroupHotel());
			model.addAttribute("groupHotelList", groupHotelList);
			Role role = roleService.editBeforeRole(roleId);
			model.addAttribute("role", role);
			
			return "/manage/role/update";
		}else {
			List<GroupHotel> groupHotelList = grpHotelService.list(new GroupHotel());
			model.addAttribute("groupHotelList", groupHotelList);
			return "/manage/role/add";
		}
		
	}
	
	/**
	 * 跳转到添加员工dialog框
	 * @author CKang
	 * @date 2017年4月14日 下午4:03:35
	 * @param id
	 * @return
	 * @throws AppException 
	 */
	@RequestMapping("/toEditEmployee")
	public String toEditEmployee(String roleId,Model model) throws AppException {
		Role role = roleService.findRoleById(roleId);
		model.addAttribute("role", role);
		List<GroupHotel> groupHotelList = grpHotelService.list(new GroupHotel());
		model.addAttribute("groupHotelList", groupHotelList);
		return "/manage/role/employee";
	}
	/**
	 * 角色添加员工右侧列表显示
	 * @author CKang
	 * @date 2017年5月18日 上午8:57:50
	 * @param roleId
	 * @param hotelId
	 * @return
	 * @throws AppException
	 */
	@RequestMapping("/getRoleAddStaffRightList")
	@ResponseBody
	public List<Staff> getRoleAddStaffRightList(String roleId,String hotelId) throws AppException {
		Role role = new Role();
		role.setId(roleId);
		role.setHotelId(hotelId);
		List<Staff> staffList = employeeService.findStaffListByRoleId(role);
		return staffList;
	}
	
	/**
	 * 角色添加员工左侧列表显示
	 * @author CKang
	 * @date 2017年5月18日 上午8:58:15
	 * @param roleId
	 * @param hotelId
	 * @param condition
	 * @param staffIds
	 * @return
	 * @throws AppException
	 */
	@RequestMapping("/findStaffListByRoleIdLeft")
	@ResponseBody
	public List<Staff> findStaffListByRoleIdLeft(String roleId,String hotelId,String condition,String staffIds) throws AppException {
		Role role = new Role();
		role.setId(roleId);
		role.setHotelId(hotelId);
		if(StringUtils.isNotBlank(condition)){
			role.setCondition(condition.trim());
		}
		if(StringUtils.isNotBlank(staffIds)){
			List<String> staffIdList = Arrays.asList(staffIds.split(","));
			role.setId("");
			role.setStaffIdList(staffIdList);
		}
		List<Staff> staffList = employeeService.findStaffListByRoleIdLeft(role);
		return staffList;
	}
	/**
	 * 组合条件+分页查询角色信息列表
	 * @author CKang
	 * @date 2017年4月11日 下午4:26:17
	 * @param pageIndex
	 * @param pageSize
	 * @param startRecord
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public PageResult<Role> list(String condition,String status,String belongId,Model model) {
		Role role = new Role();
		
		if(StringUtils.isNotBlank(condition)){
			role.setCondition(condition.trim());
		}
		if(StringUtils.isNotBlank(status)){
			role.setStatus(Short.parseShort(status));
		}
		
		if(StringUtils.isNotBlank(belongId)){
			if(StringUtils.equals(belongId, "-1")){
				role.setBelongId(null);
			} else {
				role.setBelongId(belongId);
			}
		} else {
			role.setBelongId(LoginInfoHolder.getLoginInfo().getHotelId());
		}
		
		PageResult<Role> pageResult = roleService.findAllRole(role, index, size);
		return pageResult;
	}
	
	
	/**
	 * 删除单个角色
	 * @author CKang
	 * @date 2017年4月11日 下午4:29:43
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/deleteRoleById")
	public @ResponseBody String deleteRoleById(String roleId) {
		Role role = new Role();
		PostResponsibility postResponsibility = new PostResponsibility();
		//role.setBelongId(LoginInfoHolder.getLoginInfo().getHotelId());
		role.setId(roleId);
		String[] roleIdx = new String[]{roleId};
		postResponsibility.setDefaultRole(roleIdx);
		int staffCount = roleService.findStaffCountByRoleId(role);
		int postCount = postResponsibilityService.findPostCountByRoleId(postResponsibility);
		if(staffCount > 0){
			return "staffError";
		} else if(postCount > 0){
			return "postError";
		}else {
			roleService.deleteRoleById(roleId);
			return "success";
		}
		
	}
	
	/**
	 * 批量删除角色
	 * @author CKang
	 * @date 2017年4月11日 下午4:32:12
	 * @param roleIds
	 * @return
	 */
	@RequestMapping("/deleteAll")
	public @ResponseBody String deleteAll(String roleIds) {
		String[] roleIdx = roleIds.split(",");
		for(int i=0; i<roleIdx.length; i++){
			Role role = new Role();
			PostResponsibility postResponsibility = new PostResponsibility();
			//role.setBelongId(LoginInfoHolder.getLoginInfo().getHotelId());
			role.setId(roleIdx[i]);
			String[] roleIdx2 = new String[]{roleIdx[i]};
			postResponsibility.setDefaultRole(roleIdx2);
			int count = roleService.findStaffCountByRoleId(role);
			int postCount = postResponsibilityService.findPostCountByRoleId(postResponsibility);
			if(count > 0){
				return "staffError";
			}
			if(postCount > 0){
				return "postError";
			}
		}
		roleService.batchDeleteRole(roleIds);
		return "success";
	}
	
	/**
	 * 添加角色信息 
	 * @author CKang
	 * @date 2017年4月12日 下午5:44:20
	 * @param permissionIds 多个权限id
	 * @param chinaName 中文名称
	 * @param englishName 英文名称
	 * @param belongId2 所属集团或酒店id
	 * @return
	 */
	@RequestMapping(value="/saveRole",produces="application/json;charset=UTF-8")
	public @ResponseBody Result saveRole(String permissionIds,String chinaName,Short disaccountRate,String englishName,String belongId2,Model model) {
		Result result = Result.SUCCESS;
		Role role = new Role();
		role.setName(chinaName.trim());
		role.setEnglishName(englishName.trim());
		role.setBelongId(belongId2);
		role.setHotelId(belongId2);
		role.setDisaccountRate(disaccountRate);
		if(StringUtils.isNotBlank(permissionIds)){
			String[] permissionIdx = permissionIds.split(",");
			result = roleService.saveRole(role, permissionIdx);
		} else {
			result = roleService.saveRole(role, null);
		}
		return result;
	}
	
	/**
	 * 编辑角色信息
	 * @author CKang
	 * @date 2017年4月14日 上午11:06:43
	 * @param permissionIds
	 * @param roleId
	 * @param chinaName
	 * @param englishName
	 * @param belongId2
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/updateRole")
	@ResponseBody 
	public Result updateRole(String permissionIds,String roleId,String chinaName,
			Short disaccountRate,String englishName,String belongId,Model model) {
		try {
			Role role = new Role();
			role.setId(roleId);
			role.setName(chinaName.trim());
			role.setEnglishName(englishName.trim());
			role.setBelongId(belongId);
			role.setHotelId(belongId);
			role.setDisaccountRate(disaccountRate);
			if(StringUtils.isNotBlank(permissionIds)){
				String[] permissionIdx = permissionIds.split(",");
				roleService.updateRole(role, permissionIdx);
			} else {
				roleService.updateRole(role, null);
			}
			return Result.SUCCESS;
		} catch (AppException e) {
			e.printStackTrace();
			return Result.ERROR_SYS;
		}
	}
	
	/**
	 * 解锁或锁定
	 * @author CKang
	 * @date 2017年4月14日 上午9:49:36
	 * @param roleIds
	 * @return
	 */
	@RequestMapping("/unLock")
	public @ResponseBody String unLock(String roleId) {
		String flag = roleService.updateRoleStatus(roleId);
		if(StringUtils.equals(flag, "normal")){
			return "normal";
		} else if(StringUtils.equals(flag, "lock")){
			return "lock";
		}else {
			return null;
		}
		
		
	}
	
	/**
	 * 加载角色权限列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/perms")
	public List<TreeNode> loadPermissionTree(String roleId){
		return permissionService.loadPermissionTree(roleId);
	}
	
	/**
	 * 角色添加员工
	 * @author CKang
	 * @date 2017年4月14日 下午3:13:24
	 * @param roleId
	 * @param staffIds
	 * @return
	 */
	@RequestMapping("/saveRoleStaffList")
	public @ResponseBody String saveRoleStaffList(String roleId,String hotelId,String staffIdx) {
//		List<GrpMember> grpMemberList = JSONObject.parseArray(parms, GrpMember.class);
//		String[] staffIds = new String[grpMemberList.size()];
//			for(int i=0; i<grpMemberList.size(); i++){
//				GrpMember grpMember = grpMemberList.get(i);
//				staffIds[i] = grpMember.getStaffId();
//			}
		if(StringUtils.isNotBlank(staffIdx)){
			String[] staffIds = staffIdx.split(",");
			roleService.saveRoleStaffList2(roleId, staffIds, hotelId);
		}else {
			roleService.saveRoleStaffList2(roleId, null, hotelId);
		}
			
		return "success";
	}
	/**
	 * 加载已选中的权限列表
	 * @author CKang
	 * @date 2017年4月20日 下午1:56:59
	 * @param roleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/loadcheckedPermissionList")
	public List<Permission> loadcheckedPermissionList(String roleId){
		return permissionService.loadcheckedPermissionList(roleId);
	}
	
	/**
	 * 跳转到复制角色页面
	 * @author CKang
	 * @date 2017年4月20日 下午1:57:17
	 * @param model
	 * @return
	 * @throws AppException
	 */
	@RequestMapping("/copy")
	public String copy(Model model) throws AppException{
		List<GroupHotel> groupHotelList = grpHotelService.findGrpHotelNotByHotelId();
		model.addAttribute("groupHotelList", groupHotelList);
		return "/manage/role/copy";
	}
	
	/**
	 * 复制操作左侧列表显示
	 * @author CKang
	 * @date 2017年4月21日 上午11:16:24
	 * @param belongId
	 * @param roleName
	 * @return
	 * @throws AppException 
	 */
	@ResponseBody
	@RequestMapping("/findRoleByBelongId")
	public List<Role> findRoleByBelongId(String belongId, String roleName,String roleIds) throws AppException{
		Role role = new Role();
		// 获取酒店id
		String defaultHotelId = LoginInfoHolder.getLoginInfo().getHotelId();
		role.setBelongId2(defaultHotelId);
		if(StringUtils.isNotBlank(belongId)){
			role.setBelongId(belongId);
		}
		if(StringUtils.isNotBlank(roleName)){
			role.setName(roleName.trim());
		}
		if(StringUtils.isNotBlank(roleIds)){
			String[] roleIdx = roleIds.split(",");
			role.setIds(Arrays.asList(roleIdx));
		}
		List<Role> roleList = roleService.findNotLocalRoleList(role);
		return roleList;
	}
	@RequestMapping(value="/copyRole",produces="application/json;charset=UTF-8")
	public @ResponseBody String copyRole(String roleIds) {
		try {
			roleService.copyRole(roleIds);
		} catch (AppException e) {
			return e.getMsg();
		}
		return "success";
	}
	
}
