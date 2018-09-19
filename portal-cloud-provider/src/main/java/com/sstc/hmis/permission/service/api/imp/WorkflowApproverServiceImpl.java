package com.sstc.hmis.permission.service.api.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.permission.data.ApproveDetail;
import com.sstc.hmis.permission.data.ApproveEntity;
import com.sstc.hmis.permission.data.Department;
import com.sstc.hmis.permission.data.PostResponsibility;
import com.sstc.hmis.permission.data.WorkTeam;
import com.sstc.hmis.permission.dbaccess.dao.PermsTPostResponsibilityMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTStaffMapper;
import com.sstc.hmis.permission.dbaccess.dao.WorkFlowApproveMapper;
import com.sstc.hmis.permission.dbaccess.data.PermsTDepartment;
import com.sstc.hmis.permission.dbaccess.data.PermsTPostResponsibility;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaff;
import com.sstc.hmis.permission.dbaccess.data.PermsTWorkTeam;
import com.sstc.hmis.permission.service.PostResponsibilityService;
import com.sstc.hmis.permission.service.WorkTeamService;
import com.sstc.hmis.permission.service.WorkflowApproverService;
import com.sstc.hmis.util.bean.utils.BeanUtils;
import com.sstc.hmis.wf.service.workflow.api.WfCommonService;
import com.sstc.hmis.wf.service.workflow.data.ApproveDetailEntity;

/**
 * 流程审批管理Service实现类
 * @author Qxiaoxiang
 *
 */
@RestController
public class WorkflowApproverServiceImpl implements WorkflowApproverService {

	@Autowired
	private WfCommonService wfCommonService;
	
	@Autowired
	private WorkFlowApproveMapper workFlowApproveMapper;
	@Autowired
	private WorkTeamService workTeamService;
	@Autowired
	private PostResponsibilityService postResponsibilityService;
	@Autowired
	private PermsTStaffMapper permsTStaffMapper;
	@Autowired
	private PermsTPostResponsibilityMapper permsTPostResponsibilityMapper;
	@Override
	public List<ApproveDetail> getDetailByWfPrfAndType(String wfPrfId, String type, String hotelId) {
		List<ApproveDetailEntity> entities = wfCommonService.getDetailByWfPrfAndType(wfPrfId, type, hotelId);
		List<ApproveDetail> resultList = new ArrayList<ApproveDetail>();
		List<String> dptIdList = new ArrayList<String>();
		Map<String, String> dptResultMap = new HashMap<String, String>();
		List<String> workTeamIdList = new ArrayList<String>();
		Map<String, String> workTeamResultMap = new HashMap<String, String>();
		List<String> userIdList = new ArrayList<String>();
		Map<String, String> userResultMap = new HashMap<String, String>();
		List<String> jobIdList = new ArrayList<String>();
		Map<String, String> jobResultMap = new HashMap<String, String>();
		for (ApproveDetailEntity entity : entities) {
			if (!StringUtils.isEmpty(entity.getDptId()) && !dptIdList.contains(entity.getDptId())) {
				dptIdList.add(entity.getDptId());
			}
			if (!StringUtils.isEmpty(entity.getJobId())) {
				jobIdList.add(entity.getJobId());
			}
			if (!StringUtils.isEmpty(entity.getUserId())) {
				userIdList.add(entity.getUserId());
			}
			if (!StringUtils.isEmpty(entity.getWorkTeamId())) {
				workTeamIdList.add(entity.getWorkTeamId());
			}
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 依据dptIdList获取对应部门名称
		if (dptIdList != null && dptIdList.size() > 0) {
			paramMap.put("dptIdList", dptIdList);
			List<PermsTDepartment> permsTDepartmentList = workFlowApproveMapper.getDptInfoByDptId(paramMap);
			for (PermsTDepartment permsDept : permsTDepartmentList) {
				dptResultMap.put(permsDept.getClId(), permsDept.getClName());
			}
		}
		// 依据用户ID获取人员姓名
		if (userIdList != null && userIdList.size() > 0) {
			paramMap.put("userIdList", userIdList);
			List<PermsTStaff> staffList = workFlowApproveMapper.getStaffInfoByUserId(paramMap);
			for (PermsTStaff staff : staffList) {
				userResultMap.put(staff.getClId(), staff.getClAccount() + "_" + staff.getClFamilyName() + "_" + staff.getClName());
			}
		}
		// 依据角色id获取角色名称
		if (jobIdList != null && jobIdList.size() > 0) {
			paramMap.put("jobIdList", jobIdList);
			List<PermsTPostResponsibility> permsTRoleList = workFlowApproveMapper.getJobInfoByJobId(paramMap);
			for (PermsTPostResponsibility role : permsTRoleList) {
				jobResultMap.put(role.getClId(), role.getClName());
			}
		}
		// 依据工作圈ID获取工作圈名称
		if (workTeamIdList != null && workTeamIdList.size() > 0) {
			paramMap.put("workTeamIdList", workTeamIdList);
			List<PermsTWorkTeam> permsTWorkTeamList = workFlowApproveMapper.getWorkTeamInfoById(paramMap);
			for (PermsTWorkTeam workTeam : permsTWorkTeamList) {
				workTeamResultMap.put(workTeam.getClId(), workTeam.getClName());
			}
		}
		
		for (ApproveDetailEntity entity : entities) {
			ApproveDetail approveDetail = new ApproveDetail();
			if (dptResultMap.containsKey(entity.getDptId())) {
				approveDetail.setDptName(dptResultMap.get(entity.getDptId()));				
			}
			if (userResultMap.containsKey(entity.getUserId())) {
				String accAndName = userResultMap.get(entity.getUserId());
				String[] accAndNameArr = accAndName.split("_");
				approveDetail.setUserName(accAndNameArr[1] + accAndNameArr[2]);
				approveDetail.setAccount(accAndNameArr[0]);
			}
			if (jobResultMap.containsKey(entity.getJobId())) {
				approveDetail.setJobName(jobResultMap.get(entity.getJobId()));
			}
			if (workTeamResultMap.containsKey(entity.getWorkTeamId())) {
				approveDetail.setWorkTeamName(workTeamResultMap.get(entity.getWorkTeamId()));
			}
			
			resultList.add(approveDetail);
		}
		return resultList;
	}

	
	@Override
	public void deleteApprove(String wfPrfId, String type) {
		wfCommonService.deleteApprove(wfPrfId, type);
		
	}

	@Override
	public void saveWfAuditDetailNew(String wfPrcfId, String wfTaskKey, String type, @RequestBody List<String> auditIdList,
			String grpId, String hotelId, String userId, String dptId, String documentId) {
		wfCommonService.saveWfAuditDetailNew(wfPrcfId, wfTaskKey, type, auditIdList, grpId, hotelId, userId, dptId, documentId);
		
	}

	@Override
	public PageResult<com.sstc.hmis.permission.data.ApproveDetailEntity> getDetailByWfPrfAndTypePage(int pageIndex,
			int pageSize, String wfPrfId, String type, String hotelId,String documentNo) {
		// TODO Auto-generated method stub
		PageResult<com.sstc.hmis.permission.data.ApproveDetailEntity> pageResult= new PageResult<>();
		PageResult<ApproveDetailEntity> page = wfCommonService.getDetailByWfPrfAndTypePage(pageIndex, pageSize, wfPrfId, type, hotelId,documentNo);
		List<com.sstc.hmis.permission.data.ApproveDetailEntity> list = new ArrayList<com.sstc.hmis.permission.data.ApproveDetailEntity>();
		
		if (CollectionUtils.isNotEmpty(page.getRows())) {

			for (ApproveDetailEntity approveDetailEntity : page.getRows()) {
				com.sstc.hmis.permission.data.ApproveDetailEntity entity = new com.sstc.hmis.permission.data.ApproveDetailEntity();
				if (StringUtils.isNotEmpty(approveDetailEntity.getUserId())) {//若审批对象按员工
					PermsTStaff permsTStaff = permsTStaffMapper.selectByPrimaryKey(approveDetailEntity.getUserId());
					entity.setName((permsTStaff.getClFamilyName()==null?"":permsTStaff.getClFamilyName()).concat(permsTStaff.getClName()==null?"":permsTStaff.getClName()));
					entity.setAccount(permsTStaff.getClAccount());
					entity.setOrgInfo(this.getOrgInfoByStaffId(approveDetailEntity.getUserId()));
				}else if(StringUtils.isNotEmpty(approveDetailEntity.getWorkTeamId())){//若审批对象按工作圈
					WorkTeam workTeam = workTeamService.getWorkTeamEditInfo(approveDetailEntity.getWorkTeamId());
					entity.setTitle(workTeam.getName());
					entity.setCategory(workTeam.getTypeId());
					entity.setHotelId(workTeam.getHotelId());
				}else if(StringUtils.isNotEmpty(approveDetailEntity.getJobId())){//审批对象按职位
					PostResponsibility postRes=postResponsibilityService.findPostResponsibilityById(approveDetailEntity.getJobId());
					List<Department> departmentList=postResponsibilityService.findDeptByPostId(approveDetailEntity.getJobId());
					String orgInFo =permsTPostResponsibilityMapper.findOrganizationByPostId(approveDetailEntity.getJobId());
					entity.setOrgInfo(orgInFo);
					entity.setPosition(postRes.getName());
					Department department = new Department();
					if (CollectionUtils.isNotEmpty(departmentList)) {
						department = departmentList.get(0);
						entity.setDepartment(department.getName());
					}
				}
				list.add(entity);
			}
		}
		pageResult.setIndex(page.getIndex());
		pageResult.setResult(true);
		pageResult.setResults(page.getResults());
		pageResult.setRows(list);
		pageResult.setSize(pageSize);
		return pageResult ;
	}

	/**
	 * 根据员工id获取组织字符串
	 * @param staffId
	 * @return
	 */
	private String getOrgInfoByStaffId(String staffId) {
		PermsTStaff db_permsTStaff = permsTStaffMapper.selectByPrimaryKey(staffId);
		PermsTStaff permsTStaffCondition = new PermsTStaff();
		permsTStaffCondition.setClId(staffId);
		permsTStaffCondition.setClDefaultHotelId(db_permsTStaff.getClHotelId());
		List<String> orgInfoList = permsTStaffMapper.selStaffOrgInfos(permsTStaffCondition);
		String orgInfo = "";
		if (orgInfoList != null && orgInfoList.size() > 0) {
			orgInfo = orgInfoList.get(0);
		}
		return orgInfo;
	}


	@Override
	public PageResult<ApproveEntity> getApproveListPage(int pageIndex, int pageSize, String hotelId) {
		PageResult<ApproveEntity> pageResult= new PageResult<>();
		PageResult<com.sstc.hmis.wf.service.workflow.data.ApproveEntity> page = wfCommonService.getApproveListPage(pageIndex, pageSize, hotelId);
		List<ApproveEntity> list= BeanUtils.copyBeanList2BeanList(page.getRows(), ApproveEntity.class);
		pageResult.setIndex(page.getIndex());
		pageResult.setResult(true);
		pageResult.setResults(page.getResults());
		pageResult.setRows(list);
		pageResult.setSize(pageSize);
		return pageResult ;	
	}

}
