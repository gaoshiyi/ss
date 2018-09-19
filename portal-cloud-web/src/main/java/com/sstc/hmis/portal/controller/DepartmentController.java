/**
 * Copyright 2017
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sstc.hmis.portal.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sstc.hmis.model.constants.Constants;
import com.sstc.hmis.model.data.AjaxResult;
import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.model.exception.AppException;
import com.sstc.hmis.permission.data.Department;
import com.sstc.hmis.permission.data.GroupHotel;
import com.sstc.hmis.permission.data.WorkTeam;
import com.sstc.hmis.permission.service.DepartmentService;
import com.sstc.hmis.permission.service.GrpHotelService;
import com.sstc.hmis.permission.service.WorkTeamService;
import com.sstc.hmis.permission.web.LoginInfoHolder;
import com.sstc.hmis.portal.common.base.BaseController;

/**
 * 部门管理Controller
 * <p/>
 * 
 * @author <a href="mailto:cuihu@sstcsoft.com">cuihu</a>
 * @version Date: 2017年4月7日 下午1:08:15
 * @serial 1.0
 * @since 2017年4月7日 下午1:08:15
 */

@Controller
@RequestMapping("/department")
public class DepartmentController extends BaseController {

	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private GrpHotelService grpHotelService;
	
	@Autowired
	private WorkTeamService workTeamService;
	
	/**
	 * 进入部门首页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model,String orgId,String nodeType) {
		model.addAttribute("orgId", orgId);
		model.addAttribute("nodeType", nodeType);
		
		List<GroupHotel> list=new ArrayList<>();
		GroupHotel hotel=new GroupHotel();
		try {
			list=grpHotelService.list(hotel);
		} catch (AppException e) {			
			e.printStackTrace();
		}
		
		model.addAttribute("hotelList", list);
		
		return "manage/department/dpt";
	}

	/**
	 * 查询部门列表
	 * 
	 * @return
	 */
	@RequestMapping("/findDepartmentList")
	@ResponseBody
	public PageResult<Department> findDepartmentList(String deptName, Short deptType, Integer nodeType, String id) {

		Department department = new Department();
		if(StringUtils.isNotBlank(deptName)){
			department.setName(deptName);
		}else{
			department.setName(null);
		}
		department.setDeptType(deptType);
		
		if(nodeType == Constants.NODE_TYPE_HOTEL || nodeType == Constants.NODE_TYPE_ORG){
			department.setHotelId(id);
		}else if(nodeType == Constants.NODE_TYPE_DPT){
			department.setId(id);
		}
		return departmentService.list(index, size, department);
	}

	@RequestMapping("/editDptDlog")
	public String form(String id,String orgId,Model model) throws AppException {
		List<WorkTeam> workTeamList = workTeamService.findAllWorkTeam();
		model.addAttribute("workTeamList", workTeamList);
		model.addAttribute("orgId",orgId);
		List<GroupHotel> list=new ArrayList<>();
		GroupHotel hotel=new GroupHotel();
		try {
			list=grpHotelService.list(hotel);
		} catch (AppException e) {			
			e.printStackTrace();
		}			
		model.addAttribute("hotelList", list);	
		
		Department department = new Department();
		if(StringUtils.isNotBlank(id)) {
			department.setId(id);
			department = departmentService.find(department);
			if(department != null){
				int staffSize = departmentService.countDptStaffSize(id);
				int postSize = departmentService.countDptPostSize(id);
				staffSize = staffSize + postSize;
				model.addAttribute("staffSize",staffSize);
				//model.addAttribute("postSize",postSize);
				String pid = department.getPid();
				if (StringUtils.isNotBlank(pid)) {
					Department dept = new Department();
					dept.setId(pid);
					dept=departmentService.find(dept);
					department.setPname(dept.getName());
				}		
			}
		} 
		model.addAttribute("department", department);
		return "manage/department/editDptDlog";
	}
	
	/**
	 * 保存或修改部门信息
	 * 
	 * @return
	 */
	@RequestMapping("/saveOrUpdateDepartmentInfo")
	@ResponseBody
	public AjaxResult saveDepartmentInfo(Department department) {
		String id = department.getId();
		department.setGrpId(LoginInfoHolder.getLoginInfo().getGrpId());
		try {		
			String pid = department.getPid();
			if(StringUtils.isBlank(pid)){
				department.setPid(null);
			}
			if(StringUtils.isBlank(id)){
				department.setId(UUID.randomUUID().toString());
				department.setStatus(Constants.STATUS_NORMAL);
				department.setBlockup(Constants.BLOCKUP_NO);
				return departmentService.insertDpt(department);
			}else{
				return departmentService.updateDpt(department);
			}
		} catch (AppException e) {
			ajaxResult.setCode(Constants.RES_CODE_EXCEPTION);
			e.printStackTrace();
		}
		return ajaxResult;
	}

	/**
	 * 删除部门信息
	 * 
	 * @return
	 */
	@RequestMapping("/deleteDepartmentInfo")
	@ResponseBody
	public int deleteDepartmentInfo(String id) {
		int flag = 0;
		try {
			flag = departmentService.deleteDepartmentByPrimaryKey(id);
		} catch (AppException e) {
			e.printStackTrace();
		}
		return flag;
	}

}
