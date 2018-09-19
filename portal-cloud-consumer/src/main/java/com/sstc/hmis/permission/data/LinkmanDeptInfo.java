package com.sstc.hmis.permission.data;

/**
 * 联系人部门职位信息
 * @author Lrongrong
 *
 */
public class LinkmanDeptInfo {

	/**
	 * 部门ID
	 */
	private String deptId;
	
	/**
	 * 部门名称
	 */
	private String deptName;
	
	/**
	 * 职位
	 */
	private String job;

	/**
	 * @return the deptId
	 */
	public String getDeptId() {
		return deptId;
	}

	/**
	 * @param deptId the deptId to set
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * @return the job
	 */
	public String getJob() {
		return job;
	}

	/**
	 * @param job the job to set
	 */
	public void setJob(String job) {
		this.job = job;
	}
}
