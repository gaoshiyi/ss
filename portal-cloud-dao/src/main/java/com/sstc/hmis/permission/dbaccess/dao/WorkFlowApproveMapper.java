package com.sstc.hmis.permission.dbaccess.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sstc.hmis.permission.dbaccess.data.PermsTDepartment;
import com.sstc.hmis.permission.dbaccess.data.PermsTPostResponsibility;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaff;
import com.sstc.hmis.permission.dbaccess.data.PermsTWorkTeam;

/**
 * 审批管理Mapper接口
 * @author Qxiaoxiang
 *
 */
@Mapper
public interface WorkFlowApproveMapper {

	/**
	 * 依据部门ID获取部门名称
	 */
	List<PermsTDepartment> getDptInfoByDptId(Map<String, Object> paramMap);
	
	/**
	 * 依据用户ID获取用户名称
	 */
	List<PermsTStaff> getStaffInfoByUserId(Map<String, Object> paramMap);
	
	/**
	 * 依据职位ID获取职位名称
	 */
	List<PermsTPostResponsibility> getJobInfoByJobId(Map<String, Object> paramMap);
	
	/**
	 * 依据工作圈ID获取工作圈名称
	 */
	List<PermsTWorkTeam> getWorkTeamInfoById(Map<String, Object> paramMap);
}
