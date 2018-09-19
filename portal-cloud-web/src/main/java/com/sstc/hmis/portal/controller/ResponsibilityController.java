package com.sstc.hmis.portal.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sstc.hmis.model.constants.Constants;
import com.sstc.hmis.model.constants.GenCodeConstants;
import com.sstc.hmis.model.data.AjaxResult;
import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.model.exception.AppException;
import com.sstc.hmis.model.tree.TreeNode;
import com.sstc.hmis.permission.data.Department;
import com.sstc.hmis.permission.data.PostResponsibility;
import com.sstc.hmis.permission.data.Role;
import com.sstc.hmis.permission.data.WorkTeam;
import com.sstc.hmis.permission.service.GrpHotelService;
import com.sstc.hmis.permission.service.PortalCommonService;
import com.sstc.hmis.permission.service.PostResponsibilityService;
import com.sstc.hmis.permission.service.RoleService;
import com.sstc.hmis.permission.service.WorkTeamService;
import com.sstc.hmis.permission.web.LoginInfoHolder;
import com.sstc.hmis.portal.common.base.BaseController;

@Controller
@RequestMapping("/post")
public class ResponsibilityController extends BaseController{

	
	@Autowired
	private GrpHotelService grpHotelService;
	
	@Autowired
	private WorkTeamService workTeamService;
	@Autowired
	private PostResponsibilityService postResponsibilityService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PortalCommonService commonService;
	
	/**
	 * 跳转到职位列表
	 * @author CKang
	 * @date 2017年4月18日 下午2:39:32
	 * @param model
	 * @param deptId
	 * @return
	 * @throws AppException
	 */
	@RequestMapping("/index")
	public String index(Model model,String orgId,String nodeType) throws AppException {
		model.addAttribute("orgId", orgId);
		model.addAttribute("nodeType", nodeType);
		return "manage/responsibility/post";
	}
	
	/**
	 * 跳转到添加或编辑页面
	 * @author CKang
	 * @date 2017年4月18日 下午2:39:08
	 * @param postId
	 * @param model
	 * @return
	 * @throws AppException
	 */
	@RequestMapping("/form")
	public String form(String postId,String belongId,String hotelId,Model model) throws AppException {
		// 职位名称
		Map<String, String> typeSel = commonService.getSelectEnumByHotelId(GenCodeConstants.PORTAL, GenCodeConstants.POST_TYPE,LoginInfoHolder.getLoginGrpId(),hotelId);
		model.addAttribute("typeSel", typeSel);
		
		if(StringUtils.isNotBlank(postId)) {// 编辑
			PostResponsibility postResponsibility = postResponsibilityService.findPostResponsibilityById(postId);
			List<Department> departmentList = postResponsibilityService.findDeptByPostId(postId);
			
			postResponsibility.setPostIdCode(postResponsibility.getPostId()+ "+" +postResponsibility.getPostCode());
			model.addAttribute("postResponsibility", postResponsibility);
			if(departmentList.size() > 0){
				model.addAttribute("department", departmentList.get(0));
			}
			
			// 默认工作圈
			List<WorkTeam> workTeamList = workTeamService.findAllWorkTeam();
			model.addAttribute("workTeamList", workTeamList);
			
			// 酒店id
			model.addAttribute("belongId", hotelId);
			
			// 判断该职位是否有员工
			int count = postResponsibilityService.findStaffCountByPostId(postId);
			if(count > 0){
				model.addAttribute("staffCount", "yes");
			}
			return "manage/responsibility/update";
		} else {// 添加
			// 默认工作圈
			List<WorkTeam> workTeamList = workTeamService.findAllWorkTeam();
			model.addAttribute("workTeamList", workTeamList);
			model.addAttribute("belongId", belongId);
			return "manage/responsibility/add";
		}
		
	}
	
	/**
	 * 组合条件+分页查询职位列表信息
	 * @author CKang
	 * @date 2017年4月18日 下午2:39:02
	 * @param orgId
	 * @param nodeType
	 * @param condition
	 * @param pageIndex
	 * @param pageSize
	 * @param startRecord
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public PageResult<PostResponsibility> list(String orgId,Integer nodeType,String condition,Model model) {
		
		PostResponsibility postResponsibility = new PostResponsibility();
		
 		if(StringUtils.isNotBlank(condition)){
			postResponsibility.setCondition(condition.trim());
		}
		
		if(nodeType == Constants.NODE_TYPE_DPT){
			postResponsibility.setDptId(orgId);
		} else if(nodeType == Constants.NODE_TYPE_HOTEL || nodeType == Constants.NODE_TYPE_ORG){
			postResponsibility.setHotelId(orgId);
		}
		return postResponsibilityService.findPostResponsibilityByDeptId(postResponsibility,index,size);
	}
	
	
	/**
	 * 组织结构树信息；
	 * @author CKang
	 * @date 2017年4月18日 下午2:40:01
	 * @param parms
	 * @return
	 */
	@RequestMapping("/treeOrg")
	@ResponseBody
	public List<TreeNode> treeInfo(@RequestParam(value="orgLevel",required=false) Short orgLevel,
			@RequestParam(value="hotelId",required=false) String hotelId,Integer disableLevel){
		List<TreeNode> treeInfoList = grpHotelService.list(orgLevel,hotelId,disableLevel);
		return treeInfoList;
	}
	
	/**
	 * 查询集团组织结构
	 * 
	 * @return
	 */
	@RequestMapping("/treeOrg2")
	@ResponseBody
	public List<TreeNode> treeInfo2(@RequestParam(value="orgLevel",required=false)Short orgLevel) {
		List<TreeNode> list = grpHotelService.list(orgLevel);
		return list;
	}
	
	
	@RequestMapping("/findPosts")
	@ResponseBody
	public AjaxResult list(PostResponsibility postResponsibility){
		PageResult<PostResponsibility> page = postResponsibilityService.findPostResponsibilityByDeptIdCopy(postResponsibility, 0, 10000);
		if (page != null) {
			ajaxResult.setObj(page.getRows());
		}
		return ajaxResult;
	}	
	/**
	 * 添加页面左侧列表显示
	 * @author CKang
	 * @date 2017年4月18日 下午2:41:45
	 * @param model
	 * @param belongId 所属集团或酒店id
	 * @return
	 * @throws AppException
	 */
	@RequestMapping("/roleList")
	@ResponseBody
	public List<Role> roleList(Model model,String belongId,String roleName,String postIds) throws AppException {
		List<Role> roleList = roleService.findRoleByBelongId(belongId,roleName,postIds);
		
		return roleList;
	}
	
	/**
	 * 编辑页面右侧列表显示
	 * @author CKang
	 * @date 2017年4月19日 下午1:39:20
	 * @param model
	 * @param postId 职位id
	 * @return
	 * @throws AppException
	 */
	@RequestMapping("/editRightRoleList")
	@ResponseBody
	public List<Role> editRightRoleList(Model model,String postId) throws AppException {
		PostResponsibility postResponsibility = postResponsibilityService.findPostResponsibilityById(postId);
		String[] roleIds = postResponsibility.getDefaultRole();
		List<Role> roleList = roleService.findRoleByIds(roleIds);
		return roleList;
	}
	
	/**
	 * 编辑页面左侧列表显示
	 * @author CKang
	 * @date 2017年4月19日 下午1:42:04
	 * @param model
	 * @param belongId 所属集团或酒店id
	 * @param postId 职位id
	 * @return
	 * @throws AppException
	 */
	@RequestMapping("/editLeftRoleList")
	@ResponseBody
	public List<Role> editLeftRoleList(Model model,String belongId,String postId,String roleName,String postIds) throws AppException {
		PostResponsibility postResponsibility = postResponsibilityService.findPostResponsibilityById(postId);
		String[] roleIds = postResponsibility.getDefaultRole();
		if(StringUtils.isNotBlank(postIds)){
			List<Role> allRoleList = roleService.findRoleByBelongId(belongId,roleName,postIds);
			return allRoleList;
		}else {
			List<Role> rightRoleList = roleService.findRoleByIds(roleIds);
			List<Role> allRoleList = roleService.findRoleByBelongId(belongId,roleName,postIds);
			List<Role> leftRoleList = new ArrayList<Role>();
			for(int i=0; i<allRoleList.size(); i++){
				boolean falg = true;
				Role allRole = allRoleList.get(i);
				String allRoleId = allRole.getId();
				for(int j=0; j<rightRoleList.size(); j++){
					Role role = rightRoleList.get(j);
					String roleId = role.getId();
					if(StringUtils.equals(allRoleId, roleId)){
						falg = false;
					}
				}
				if(falg){
					leftRoleList.add(allRole);
				}
			}
			return leftRoleList;
		}
		
	}
	
	/**
	 * 保存职位信息
	 * @author CKang
	 * @date 2017年4月18日 下午6:00:28
	 * @param roleIds
	 * @param postId
	 * @param postEnglishName
	 * @param deptId
	 * @param defaultTeam
	 * @return
	 * @throws AppException
	 */
	@RequestMapping(value="/savePost",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String savePost(String roleIds,String postNameId,String postEnglishName,
			String deptId,String defaultTeam,String name) throws AppException {
		if(!StringUtils.isNotBlank(deptId)){
			return "请选择所属部门";
		}
		
		PostResponsibility postResponsibility = new PostResponsibility();
		// 角色id数组
		if(StringUtils.isNotBlank(roleIds)){
			String[] roleIdx = roleIds.split(",");
			postResponsibility.setDefaultRole(roleIdx);
		} else{
			return "请选择默认角色";
		}
		// 职位名称id+code
		if(StringUtils.isNotBlank(postNameId)){
			String[] postIdx = postNameId.split("\\+");
			postResponsibility.setPostId(postIdx[0]);
			postResponsibility.setPostCode(postIdx[1]);
			//------------------------------------------传入酒店id
			//String belongId = LoginInfoHolder.getLoginInfo().getHotelId();
			//------------------------------------------
			String result = postResponsibilityService.validDeptPostName(postIdx[0],postEnglishName,deptId);
			if(StringUtils.isNotBlank(result)){
				return result;
			}
		}
		// 英文名称
		if(StringUtils.isNotBlank(postEnglishName)){
			postResponsibility.setEnglishName(postEnglishName);
		}
		// 默认工作圈
		if(StringUtils.isNotBlank(defaultTeam)){
			postResponsibility.setDefaultTeam(defaultTeam);
		}
		postResponsibility.setName(name); 
		postResponsibilityService.savePostResponsibility(postResponsibility, deptId);
		return "success";
	}
	
	/**
	 * 修改职位信息
	 * @author CKang
	 * @date 2017年4月19日 下午2:30:55
	 * @param postId
	 * @param roleIds
	 * @param postNameId
	 * @param postEnglishName
	 * @param deptId
	 * @param defaultTeam
	 * @return
	 * @throws AppException
	 */
	@RequestMapping(value="/updatePost",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String updatePost(String postId, String roleIds,String postNameId,String postEnglishName,
			String deptId,String defaultTeam,String name) throws AppException {
		PostResponsibility postResponsibility = new PostResponsibility();
		// 职位id
		if(StringUtils.isNotBlank(postId)){
			postResponsibility.setId(postId);
		}
		// 角色id数组
		if(StringUtils.isNotBlank(roleIds)){
			String[] roleIdx = roleIds.split(",");
			postResponsibility.setDefaultRole(roleIdx);
		}else{
			return "请选择默认角色";
		}
		// 职位名称id+code
		if(StringUtils.isNotBlank(postNameId)){
			String[] postIdx = postNameId.split("\\+");
			postResponsibility.setPostId(postIdx[0]);
			postResponsibility.setPostCode(postIdx[1]);
			//------------------------------------------传入酒店id
			//String belongId = LoginInfoHolder.getLoginInfo().getHotelId();
			//------------------------------------------
			String result = postResponsibilityService.validDeptPostNameNotId(postId,postIdx[0],postEnglishName,deptId);
			if(StringUtils.isNotBlank(result)){
				return result;
			}
		}
		// 英文名称
		if(StringUtils.isNotBlank(postEnglishName)){
			postResponsibility.setEnglishName(postEnglishName);
		}
		// 默认工作圈
		if(StringUtils.isNotBlank(defaultTeam)){
			postResponsibility.setDefaultTeam(defaultTeam);
		} 
		postResponsibility.setName(name); 
		postResponsibilityService.updatePostResponsibility(postResponsibility, deptId);
		return "success";
	}
	
	/**
	 * 通过id删除职位信息
	 * @author CKang
	 * @date 2017年4月20日 上午10:20:31
	 * @param deletePostById
	 * @return
	 */
	@RequestMapping("/deletePostById")
	public @ResponseBody String deletePostById(String postId) {
		int count = postResponsibilityService.findStaffCountByPostId(postId);
		if(count > 0){
			return "error";
		} else{
			postResponsibilityService.deletePostById(postId);
			return "success";
		}
	}
	/**
	 * 批量删除
	 * @author CKang
	 * @date 2017年4月20日 上午10:41:14
	 * @param roleIds
	 * @return
	 */
	@RequestMapping("/deleteAll")
	public @ResponseBody String deleteAll(String postIds) {
		String[] postIdx = postIds.split(",");
		for(int i=0; i<postIdx.length; i++){
			int count = postResponsibilityService.findStaffCountByPostId(postIdx[i]);
			if(count > 0){
				return "error";
			}
		}
		postResponsibilityService.batchDeletePost(postIds);
		return "success";
	}
	
}
