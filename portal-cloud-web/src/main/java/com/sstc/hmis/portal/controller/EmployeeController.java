/**
 * 
 */
package com.sstc.hmis.portal.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.sstc.hmis.model.constants.Constants;
import com.sstc.hmis.model.constants.GenCodeConstants;
import com.sstc.hmis.model.data.Result;
import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.model.exception.AppException;
import com.sstc.hmis.model.tree.TreeNode;
import com.sstc.hmis.permission.data.GroupHotel;
import com.sstc.hmis.permission.data.PostResponsibility;
import com.sstc.hmis.permission.data.Role;
import com.sstc.hmis.permission.data.Staff;
import com.sstc.hmis.permission.data.StaffHotel;
import com.sstc.hmis.permission.service.DepartmentService;
import com.sstc.hmis.permission.service.EmployeeService;
import com.sstc.hmis.permission.service.GrpHotelService;
import com.sstc.hmis.permission.service.PortalCommonService;
import com.sstc.hmis.permission.service.PostResponsibilityService;
import com.sstc.hmis.permission.service.RoleService;
import com.sstc.hmis.permission.web.LoginInfoHolder;
import com.sstc.hmis.portal.common.base.BaseController;

/**
  * <p> Title: TestCtrl </p>
  * <p> Description:  
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年4月5日 下午4:35:34
   */
@Controller
@RequestMapping("/employee")
public class EmployeeController extends BaseController{

	@Autowired
	private PortalCommonService portalCommonService;

	@Autowired
	GrpHotelService grpHotelService;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	RoleService roleService;
	@Autowired
	PostResponsibilityService postResponsibilityService;
	@Autowired
	DepartmentService departmentService;

	@RequestMapping("/validcertNumber")
	@ResponseBody
	public String validcertNumber(String certTypeCode, String certno, String id) {
		String validRes = employeeService.validcertNumber(certTypeCode, certno, id);
		return validRes;
	}

	@RequestMapping("/info")
	public String detail() {
		String grpId = LoginInfoHolder.getLoginGrpId();
		request.setAttribute("staff", employeeService.findStaffById(LoginInfoHolder.getLoginInfo().getId()));
		Map<String, String> degreeMap = portalCommonService.getSelectEnumByHotelId(GenCodeConstants.PORTAL,
				GenCodeConstants.DEGREE_TYPE, grpId, grpId);
		request.setAttribute("degreeMap", degreeMap);
		request.setAttribute("date", DateUtils.formatDate(new Date(), "yyyy/MM/dd"));
		return "manage/employee/info";
	}

	@RequestMapping("/password")
	public String password() {

		return "manage/employee/password";
	}
	
	@ResponseBody
	@RequestMapping("/pwd/update")
	public Result updatePwd(@RequestParam String password,@RequestParam String newPwd,@RequestParam String pwdConfirm){
		if(StringUtils.isNoneBlank(password,newPwd,pwdConfirm) && StringUtils.equals(newPwd, pwdConfirm)){
			return employeeService.updatePwd(password,newPwd,LoginInfoHolder.getLoginInfo().getId());
		}else{
			return Result.ERROR_PARAMS;
		}
		
	}

	@RequestMapping("/workorder")
	public String workorder() {

		return "manage/employee/workorder";
	}

	@RequestMapping("/orderadd")
	public String orderadd() {

		return "manage/employee/orderadd";
	}

	@RequestMapping("/questions")
	public String questions() {

		return "manage/employee/questions";
	}

	@RequestMapping("/page")
	public String page(HttpServletRequest request, String postId, String teamId) {
		request.setAttribute("postId", postId);
		request.setAttribute("teamId", teamId);
		String defaultHotelId = LoginInfoHolder.getLoginHotelId();
		request.setAttribute("loginHotelVal", defaultHotelId);
		return "manage/employee/employee";
	}

	@RequestMapping("/orgTab")
	public String orgTab(Model model, String orgId, String nodeType) {
		model.addAttribute("orgId", orgId);
		model.addAttribute("nodeType", nodeType);
		return "manage/employee/employeeTab";
	}

	@RequestMapping("/resetPwd")
	@ResponseBody
	public Result resetPwd(String id){
		return employeeService.resetPwd(id);
	}
	
	/**
	 * 角色权限左侧数据；
	 */
	@RequestMapping("/roleLeft")
	@ResponseBody
	public List<Role> roleLeft(String staffId) {
		List<String> workHotelIdList = getWorkHotelIds(staffId);
		List<Role> roleLeft = roleService.loadLeftRoleList(staffId, workHotelIdList);
		return roleLeft;
	}

	private List<String> getWorkHotelIds(String staffId) {
		List<StaffHotel> staffHotelList = employeeService.getStaffWorkHotelInfo(staffId);
		List<String> workHotelIdList = new ArrayList<>();
		if (staffHotelList != null && staffHotelList.size() > 0) {
			for (StaffHotel staffHotel : staffHotelList) {
				workHotelIdList.add(staffHotel.getWorkHotelId());
			}
		}
		return workHotelIdList;
	}

	/**
	 * 角色权限左侧数据；
	 */
	@RequestMapping("/roleRight")
	@ResponseBody
	public List<Role> roleRight(String staffId) {
		List<String> workHotelIds = new ArrayList<>();
		workHotelIds.clear();
		workHotelIds = getWorkHotelIds(staffId);
		List<Role> roleLeft = new ArrayList<>();
		Set<String> workHotelIdSet = new LinkedHashSet<String>();
		workHotelIdSet.addAll(workHotelIds);
		List<String> workHotelIdList = new ArrayList<>();
		workHotelIdList.addAll(workHotelIdSet);
		roleLeft.clear();
		roleLeft = roleService.loadRightRoleList(staffId, workHotelIdList);
		return roleLeft;
	}

	/**
	 * 保存用户角色；
	 */
	@RequestMapping("/saveRight")
	@ResponseBody
	public String saveRight(String parms, String staffVal) {
		List<Role> grpMemberList = JSONObject.parseArray(parms, Role.class);
		List<String> roleIdList = new ArrayList<>();
		for (Role role : grpMemberList) {
			roleIdList.add(role.getId());
		}
		String[] roleIdArr = {};
		roleIdArr = roleIdList.toArray(roleIdArr);
		roleService.saveStaffRoleList(staffVal, roleIdArr);
		return "success";
	}

	/**
	 * 保存工作酒店信息；
	 */
	@RequestMapping("/saveWorkHotel")
	@ResponseBody
	public String savedWorkHotel(String postIdInfo, String staffVal) {
		int res = 0;
		if (StringUtils.isNotBlank(postIdInfo) && StringUtils.isNotBlank(staffVal)) {
			if (postIdInfo.contains("+")) {
				String[] postIdArr = postIdInfo.split("[+]");
				res = employeeService.savedWorkHotel(postIdArr, staffVal);
			}
		}
		return res(res);
	}

	@RequestMapping("/orgGrid")
	@ResponseBody
	public List<Staff> orgGrid(String staffId) {
		List<Staff> staffList = new ArrayList<>();
		if (StringUtils.isNotBlank(staffId)) {
			staffList = employeeService.getorgGridInfos(staffId);
		}
		return staffList;
	}

	/**
	 * 展示界面列表数据；
	 */
	@RequestMapping("/index")
	@ResponseBody
	public PageResult<Staff> queryGrid(String orgId, Short nodeType, Short statusVal, String infoVal, String postId2,
			String teamId, String orgIdTab, Short nodeTypeTab) {
		try {
			PageResult<Staff> result = employeeService.listSel(index, size, nodeType, orgId, statusVal, infoVal,
					postId2, teamId, orgIdTab, nodeTypeTab);
			return result;
		} catch (AppException e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping("/list")
	@ResponseBody
	public PageResult<Staff> pageList(String orgId, Short nodeType, Short status, String info, String teamId) {
		try {
			PageResult<Staff> result = employeeService.pageList(index, size, nodeType, orgId, status, info, teamId,
					null);
			return result;
		} catch (AppException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 删除数据
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Result delete(String idvalue, String orgId, Short orgType) {
		// idvalue存在，则为删除；
		if (StringUtils.isNotBlank(idvalue)) {
			GroupHotel hotel = grpHotelService.findGrpHotelByOrgInfo(orgId, orgType);
			if (idvalue.contains(",")) {
				// 批量删除
				int idx = idvalue.lastIndexOf(",");
				idvalue = idvalue.substring(0, idx);
				String[] arr = idvalue.split(",");
				for (int i = 0; i < arr.length; i++) {
					String id = arr[i];
					delStaffWorkInfo(id, hotel);
				}
				return Result.SUCCESS;
			} else {
				// 单个删除
				return delStaffWorkInfo(idvalue, hotel);
			}
		}
		return Result.ERROR_PARAMS;
	}

	/**
	 * 锁定数据
	 */
	@RequestMapping("/lock")
	@ResponseBody
	public String lockTeamData(String idvalue) {
		return employeeService.lockTeamData(idvalue);
	}

	/**
	 * 根据Id删除工作员工数据；
	 */
	private Result delStaffWorkInfo(String idvalue, GroupHotel hotel) {
		Staff staff = new Staff(idvalue);
		staff.setStatus(Constants.STATUS_DEL);
		return employeeService.deleteStaff(staff, hotel.getId());
	}

	/**
	 * 登陆策略Dialog弹出框；
	 */
	@RequestMapping("/strateryDlog")
	public String starteryHtml(HttpServletRequest request, String ids, String hotelId) {
		request.setAttribute("staffIds", ids);
		GroupHotel groupHotel = grpHotelService.findGroupHotelById(hotelId);
		List<StaffHotel> staffHotelList = employeeService.getStaffHotelInfo(ids, hotelId);
		StaffHotel staffHotel = new StaffHotel();
		if(ids.split("\\+").length == 1){
			staffHotel = staffHotelList.get(0);
		}
		request.setAttribute("staffHotel", staffHotel);
		request.setAttribute("groupHotel", groupHotel);
		return "manage/employee/strateryDlog";
	}
	
	@RequestMapping("/isStrateryBlank")
	@ResponseBody
	public Result isStrateryBlank(String ids, String hotelId){
		List<StaffHotel> staffHotelList = employeeService.getStaffHotelInfo(ids, hotelId);
		boolean isBlank = true;
		if(ids.split("\\+").length > 1){
			isBlank = isAllStaffStrateryBlank(staffHotelList);
		}
		Result result = Result.SUCCESS;
		result.setResult(isBlank);
		return result;
	}
	
	/**
	 * 是否所有帐号的登录策略都未设置
	 * @param staffHotelList　员工工作酒店列表
	 * @return　true:是 　false:否
	 */
	private boolean isAllStaffStrateryBlank(List<StaffHotel> staffHotelList){
		
		for (StaffHotel staffHotel : staffHotelList) {
			int isLimit = staffHotel.getIsLimit();
			Date beginDate = staffHotel.getBeginDate();
			Date endDate = staffHotel.getEndDate();
			String[] timeInterval = staffHotel.getTimeInterval();
			String[] weeks = staffHotel.getWeek();
			Integer[] limitType = staffHotel.getLimitType();
			if(isLimit == Constants.LOGIN_LIMIT || beginDate != null || endDate != null 
					|| ArrayUtils.isNotEmpty(timeInterval) || ArrayUtils.isNotEmpty(weeks) || ArrayUtils.isNotEmpty(limitType)){
				return false;
			}
		}
		return true;
		
	}

	/**
	 * 根据下拉酒店动态更改登陆策略值
	 */
	@RequestMapping("/strateryEdit")
	@ResponseBody
	public StaffHotel strateryEdit(String hotelId, String staffIds) {
		StaffHotel staffHotel = new StaffHotel();
		if (StringUtils.isNotBlank(staffIds) && !staffIds.contains("+")) {
			List<StaffHotel> staffHotelList = employeeService.getStaffHotelInfo(staffIds, hotelId);
			if (staffHotelList != null && staffHotelList.size() > 0) {
				staffHotel = staffHotelList.get(0);
				Date beginDate = staffHotel.getBeginDate();
				Date endDate = staffHotel.getEndDate();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				if (beginDate != null) {
					String beginDateStr = sdf.format(beginDate);
					staffHotel.setBeginDateStr(beginDateStr);
				}
				if (endDate != null) {
					String endDateStr = sdf.format(endDate);
					staffHotel.setEndDateStr(endDateStr);
				}
			}
		}
		return staffHotel;
	}

	/**
	 * 登陆策略Dialog数据保存；
	 */
	@RequestMapping("/strateryInfoSave")
	@ResponseBody
	public Result strateryInfoSave(StaffHotel staffHotel) {
		int res = employeeService.saveStrateryInfo(staffHotel);
		if(res > 0){
			return Result.SUCCESS;
		}
		return Result.ERROR_SYS;
	}

	/**
	 * 用户角色 Dialog弹出框；
	 */
	@RequestMapping("/customRoleDlog")
	public String customRoleHtml(HttpServletRequest request) {

		return "manage/employee/customRoleDlog";
	}

	/**
	 * 数据权限Dialog弹出框；
	 */
	@RequestMapping("/dataRightDlog")
	public String dataRight(HttpServletRequest request) {

		return "manage/employee/dataRightDlog";
	}

	/**
	 * 工作权限Dialog弹出框；
	 */
	@RequestMapping("/workHotelDlog")
	public String workHotelRight(HttpServletRequest request) {

		return "manage/employee/workHotelDlog";
	}

	/**
	 * 新建Dialog弹出框；
	 */
	@RequestMapping("/newDlog")
	public String newStaffHtml(HttpServletRequest request, final String id, String hotelId) {
		String grpId = LoginInfoHolder.getLoginGrpId();
		Map<String, String> certtypeMap = portalCommonService.getSelectEnumByHotelId(GenCodeConstants.SYSTEM_GCOM,
				GenCodeConstants.CERTTYP, grpId, grpId);
		Map<String, String> degreeMap = portalCommonService.getSelectEnumByHotelId(GenCodeConstants.PORTAL,
				GenCodeConstants.DEGREE_TYPE, grpId, grpId);
		// 酒店下拉列表值；
		Map<String, String> allHotels = employeeService.getAllHotels();
		hotelId = LoginInfoHolder.getLoginHotelId();
		final String htlId = hotelId;
		if(!StringUtils.equals(LoginInfoHolder.getLoginGrpId(), htlId)
				&& allHotels.size() > 0){
			allHotels = allHotels.entrySet().stream()
						.filter(entry -> StringUtils.equals(entry.getKey(), htlId))
						.collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
		}
		
		// 编辑
		if (StringUtils.isNotBlank(id)) {
			Staff staff = employeeService.getEmployeeInfo(id);
			if (staff != null) {
				String deftDptId = staff.getDefaultDptId();
				List<PostResponsibility> postList = postResponsibilityService.findPostByDeptId(deftDptId);
				request.setAttribute("dpt", departmentService.findDepartmentById(staff.getDefaultDptId()));
				request.setAttribute("postList", postList);
				request.setAttribute("staff", staff);
			}
		}
		request.setAttribute("id", id);
		request.setAttribute("certtypeSel", certtypeMap);
		request.setAttribute("degreeSel", degreeMap);
		request.setAttribute("allHotels", allHotels);
		request.setAttribute("date", new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
		return "manage/employee/addDlog";
	}

	/**
	 * 组织结构树信息；
	 */
	@RequestMapping("/treeOrg")
	@ResponseBody
	public List<TreeNode> treeInfo(@RequestParam(value = "orgLevel", required = false) Short orgLevel, String staffId) {
		List<TreeNode> treeInfoList = grpHotelService.list(orgLevel);
		List<String> postIdList = new ArrayList<>();
		String postId = StringUtils.EMPTY;
		if (StringUtils.isNotBlank(staffId)) {
			Staff staff = employeeService.findStaffById(staffId);
			postId = staff.getDefaultPostId();
			List<StaffHotel> staffHotelList = employeeService.getStaffWorkHotelInfo(staffId);
			for (StaffHotel staffHotel : staffHotelList) {
				postIdList.add(staffHotel.getPostId());
			}
		}
		for (TreeNode treeNode : treeInfoList) {
			loopTreeNode(treeNode, postIdList,postId);
		}
		return treeInfoList;
	}

	/**
	 * 组织结构树信息；
	 */
	@RequestMapping("/treeOrgTab")
	@ResponseBody
	public List<TreeNode> treeInfoTab(Short nodeTypeVal, String staffId, String orgIdVal) {
		List<TreeNode> treeInfoList = new ArrayList<>();
		if (nodeTypeVal == -1) {
			treeInfoList = grpHotelService.list2(Constants.HOTEL_TYPE_HTL, LoginInfoHolder.getLoginInfo().getHotelId());
		} else {
			treeInfoList = grpHotelService.list2(nodeTypeVal, orgIdVal);
		}
		if (StringUtils.isNotBlank(staffId)) {
			List<StaffHotel> staffHotelList = employeeService.getStaffWorkHotelInfo(staffId);
			List<String> postIdList = new ArrayList<>(staffHotelList.size());
			for (StaffHotel staffHotel : staffHotelList) {
				postIdList.add(staffHotel.getPostId());
			}
			for (TreeNode treeNode : treeInfoList) {
				loopTreeNode(treeNode, postIdList,StringUtils.EMPTY);
			}
		}
		return treeInfoList;
	}

	private void loopTreeNode(TreeNode tree, List<String> idList,String defaultPostId) {
		if (tree != null) {
			String nodeId = tree.getId();
			if (idList.contains(nodeId)) {
				tree.setChecked(true);
				if(StringUtils.equals(nodeId, defaultPostId)){
					tree.setDisabled(true);
				}
			} else if (tree.getNodeType() != Constants.NODE_TYPE_POST) {
				tree.setChecked(null);
			}
			if (tree.getChildren() != null && tree.getChildren().size() > 0) {
				List<TreeNode> list = tree.getChildren();
				for (TreeNode treeNode : list) {
					loopTreeNode(treeNode, idList,defaultPostId);
				}
			}
		}
	}

	/**
	 * 基本信息的保存；
	 */
	@RequestMapping("/baseInfoSave")
	@ResponseBody
	public Result baseInfoSave(Staff staff) {
		String id = staff.getId();
		String accountAft = staff.getAccountAft();
		if (StringUtils.isNotBlank(accountAft)) {
			staff.setAccount(accountAft);
		}
		String postId = staff.getDefaultPostId();
		if (StringUtils.isNotBlank(postId)) {
			String hasPost = employeeService.hasPost(postId);
			if ("no".equals(hasPost)) {
				Result errorResult = Result.ERROR_PARAMS;
				errorResult.setMessage("该职位已经被删除！");
				errorResult.setResult(staff.getDefaultDptId());
				return errorResult;
			}
		}
		// 修改；
		if (StringUtils.isNotBlank(id)) {
			try {
				Result result = employeeService.updateStaffInfo(staff);
				return result;
			} catch (AppException e) {
				e.printStackTrace();
				return Result.ERROR_SYS;
			}
		} else {
			try {
				staff.setCreateBy(LoginInfoHolder.getLoginInfo().getAccount());
				Result result = employeeService.insertBackId(staff);
				return result;
			} catch (AppException e) {
				e.printStackTrace();
				return Result.ERROR_SYS;
			}
		}
	}

	/**
	 * 员工导出
	 * @param fileName　下载的Excel文件名
	 */
	@RequestMapping("/download")
	public void employeeDownload(String fileName) {
		List<Staff> list = employeeService.listStaffWithOrgInfo(null, LoginInfoHolder.getLoginHotelId());
		super.exportExcel(new String[] { "account", "staffNo", "familyName", "name" },
				new String[] { "帐号", "工号", "姓氏", "名字" }, list, fileName);
	}

	private String res(int res) {
		if (res == 1) {
			return "success";
		} else {
			return "fail";
		}
	}

	/**
	 * 自动转换日期类型的字段格式
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
    
}

  