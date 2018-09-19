/**
 * 
 */
package com.sstc.hmis.portal.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.sstc.hmis.model.constants.Constants;
import com.sstc.hmis.model.constants.GenCodeConstants;
import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.model.exception.AppException;
import com.sstc.hmis.model.tree.TreeNode;
import com.sstc.hmis.permission.data.GroupHotel;
import com.sstc.hmis.permission.data.GrpMember;
import com.sstc.hmis.permission.data.WorkTeam;
import com.sstc.hmis.permission.service.GrpHotelService;
import com.sstc.hmis.permission.service.PortalCommonService;
import com.sstc.hmis.permission.service.WorkTeamService;
import com.sstc.hmis.permission.web.LoginInfoHolder;
import com.sstc.hmis.portal.common.base.BaseController;

/**
 * <p>
 * Title: TestCtrl
 * </p>
 * <p>
 * Description:
 * <p>
 * Company: SSTC
 * </p>
 * 
 * @author Qxiaoxiang
 * @date 2017年4月5日 下午4:35:34
 */
@Controller
@RequestMapping("/workTeam")
public class WorkTeamController extends BaseController {
	@Autowired
	WorkTeamService workTeamService;
	@Autowired
	GrpHotelService grpHotelService;
	@Autowired
	private PortalCommonService commonService;

	@RequestMapping("/page")
	public String page(HttpServletRequest request) {
		jspSelInfo(request);
		return "manage/workteam/workTeam";
	}

	/*
	 * 展示界面列表数据；
	 */
	@RequestMapping("/index")
	@ResponseBody
	public PageResult<WorkTeam> queryGrid(@RequestParam(defaultValue = "0", name = "pageIndex") int index,
			@RequestParam(defaultValue = "10", name = "limit") int size, WorkTeam t) {
		return workTeamService.list(index, size, t);
	}

	/*
	 * 展示界面列表数据；
	 */
	@RequestMapping("/lock")
	@ResponseBody
	public String lockGrid(String idvalue) {
		return workTeamService.lockGrid(idvalue);
	}

	/*
	 * 保存/修改数据；
	 */
	@RequestMapping("/save")
	@ResponseBody
	public String saveWorkTeamData(WorkTeam t) {
		String id = t.getId();
		if (StringUtils.isNotBlank(id)) {
			return updateWorkTeamData(t, null);
		}
		int res = workTeamService.insert(t);
		if (res == 1) {
			return "success";
		} else if (res == 2) {
			return "repeat";
		} else {
			return "fail";
		}
	}

	/**
	 * 删除数据
	 * 
	 * @param idvalue
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delWorkTeamData(String idvalue) {
		/*
		 * TODO 解散时候是否关联工作圈 ？ 不能解散 ：解散；
		 */
		// idvalue存在，则为删除；
		if (StringUtils.isNotBlank(idvalue)) {
			if (idvalue.contains(",")) {
				// 批量删除
				int idx = idvalue.lastIndexOf(",");
				idvalue = idvalue.substring(0, idx);
				String[] arr = idvalue.split(",");
				for (int i = 0; i < arr.length; i++) {
					String id = arr[i];
					delWorkTeamMember(id);
				}
				return "success";
			} else {
				// 单个删除
				int res = delWorkTeamMember(idvalue);
				if (res == 1) {
					return "success";
				}
			}

		}
		return null;
	}

	/*
	 * 根据Id删除工作圈信息；
	 */
	private int delWorkTeamMember(String id) {
		WorkTeam workTeam = new WorkTeam();
		workTeam.setId(id);
		workTeam = workTeamService.find(workTeam);
		if (workTeam != null) {
			workTeam.setStatus(Constants.STATUS_DEL);
		}
		return workTeamService.update(workTeam);
	}

	/*
	 * 修改数据
	 */
	@RequestMapping("/update")
	@ResponseBody
	public String updateWorkTeamData(WorkTeam t, String idvalue) {
		t.setGrpId(LoginInfoHolder.getLoginInfo().getGrpId());
		t.setHotelId(t.getBelongId());
		int res = workTeamService.update(t);
		if (res == 1) {
			return "success";
		} else if(res == 2){
			return "repeat";
		}else {
			return "fail";
		}
	}

	/*
	 * 已选择员工Grid数据
	 */
	@RequestMapping("/selGrpMember")
	@ResponseBody
	public PageResult<GrpMember> selGrpMember(String teamId, String roleId, String hotelId) {
		// if(StringUtils.isNotBlank(roleId) &&
		// StringUtils.isNotBlank(hotelId)){
		// PageResult<GrpMember> result = new PageResult<GrpMember>();
		// List<GrpMember> grpMemberList = new ArrayList<GrpMember>();
		// List<String> staffIdList =
		// roleService.findStaffIdListByRoleIdAndHotelId(roleId, hotelId);
		// for(String staffId : staffIdList){
		// Staff staff = employeeService.findStaffById(staffId);
		// if(StringUtils.equals(hotelId, staff.getHotelId())){
		// GrpMember grpMember = new GrpMember();
		// List<String> orgList =
		// employeeService.findOrganizationByStaffId(staff);
		// grpMember.setOrgInfo(orgList.get(0));
		// grpMember.setStaffId(staff.getId());
		// grpMember.setCreateBy(staff.getAccount());
		// grpMember.setChName(staff.getName());
		// grpMemberList.add(grpMember);
		// }
		//
		// }
		// result.setRows(grpMemberList);
		// return result;
		// }else {
		try {
			PageResult<GrpMember> result = workTeamService.selGrpMember(teamId, roleId);
			return result;
		} catch (AppException e) {
			e.printStackTrace();
			return null;
		}
	}
	// }

	/*
	 * 去除员工Grid后员工的数据；
	 */
	@RequestMapping("/selGrpMemberAll")
	@ResponseBody
	public PageResult<GrpMember> selGrpMemberAll(String teamId, String roleId, String orgId, Integer nodeType,
			String empName, String selStaffIds) {
		String[] selStaffIdArr = {};
		if (StringUtils.isNotBlank(selStaffIds) && selStaffIds.contains("+")) {
			selStaffIdArr = selStaffIds.split("[+]");
		}
		PageResult<GrpMember> pageResult = new PageResult<GrpMember>();
		try {
			if (StringUtils.isBlank(orgId)) {
				orgId = LoginInfoHolder.getLoginInfo().getGrpId();
			}
			if (nodeType == null) {
				nodeType = (int) Constants.NODE_TYPE_ORG;
			}
			List<GrpMember> grpMembers = workTeamService.selGrpMemberAll(teamId, roleId, orgId, nodeType, empName,
					selStaffIdArr);
			pageResult.setResult(true);
			pageResult.setResults(grpMembers.size());
			pageResult.setRows(grpMembers);
			return pageResult;
		} catch (AppException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * 新建Dialog弹出框；
	 */
	@RequestMapping("/newDlog")
	public String newHtml(HttpServletRequest request, String id) {
		jspSelInfo(request);
		// 编辑；
		if (id != null) {
			WorkTeam workTeam = workTeamService.getWorkTeamEditInfo(id);
			if (workTeam != null) {
				request.setAttribute("workTeam", workTeam);
			}
		}
		return "manage/workteam/workTeamDlog";
	}

	/*
	 * 通用的需要传到前台下拉框的值；
	 */
	private void jspSelInfo(HttpServletRequest request) {
		try {
			// 获取分类的码表值；
			Map<String, String> typeSel = commonService
					.getSelectEnumByHotelId(GenCodeConstants.PORTAL, GenCodeConstants.WORK_TEAM_TYPE,
							LoginInfoHolder.getLoginGrpId(),LoginInfoHolder.getLoginHotelId());
			request.setAttribute("typeSel", typeSel);
			// 所属集团或者酒店
			List<GroupHotel> groupHotelList = grpHotelService.list(new GroupHotel());
			request.setAttribute("groupHotelList", groupHotelList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 添加员工Dialog弹出框；
	 */
	@RequestMapping("/addGrpMemberDlog")
	public String newHtml2(HttpServletRequest request, String teamId, String roleId, Model model) {
		request.setAttribute("orgId", LoginInfoHolder.getLoginInfo().getGrpId());
		request.setAttribute("nodeType", Constants.NODE_TYPE_ORG);
		request.setAttribute("teamId", teamId);
		if (StringUtils.isNotBlank(roleId)) {
			Map<String, String> hotelInfoMap = workTeamService.getHotelInfoByRoleId(roleId);
			request.setAttribute("hotelInfoMap", hotelInfoMap);
			for (String s : hotelInfoMap.keySet()) {
				model.addAttribute("hotelId", s);
			}

		}
		request.setAttribute("roleId", roleId);
		return "manage/workteam/addGrpMemberDlog";
	}

	/*
	 * 添加员工保存；
	 */
	@RequestMapping("/saveGrpMembers")
	@ResponseBody
	public String saveGrpMembers(String parms) {
		int res = 0;
		List<GrpMember> grpMemberList = JSONObject.parseArray(parms, GrpMember.class);
		if (grpMemberList != null && grpMemberList.size() > 0) {
			res = workTeamService.saveGrpMembers(grpMemberList);
		}
		if (res == 1) {
			return "success";
		} else {
			return "fail";
		}
	}

	/*
	 * 组织结构树信息；
	 */
	@RequestMapping("/treeOrg")
	@ResponseBody
	public List<TreeNode> treeInfo(@RequestParam(value = "orgLevel", required = false) Short orgLevel) {
		List<TreeNode> treeInfoList = grpHotelService.list(orgLevel);
		return treeInfoList;
	}
}
