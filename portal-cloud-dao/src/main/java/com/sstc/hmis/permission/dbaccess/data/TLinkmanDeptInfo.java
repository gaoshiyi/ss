package com.sstc.hmis.permission.dbaccess.data;

/**
 * 联系人部门职位信息
 * @author Lrongrong
 *
 */
public class TLinkmanDeptInfo {

	/**
	 * 部门ID
	 */
	private String clDeptId;
	
	/**
	 * 部门名称
	 */
	private String clDeptName;
	
	/**
	 * 职位
	 */
	private String clJob;

	/**
	 * @return the clDeptId
	 */
	public String getClDeptId() {
		return clDeptId;
	}

	/**
	 * @param clDeptId the clDeptId to set
	 */
	public void setClDeptId(String clDeptId) {
		this.clDeptId = clDeptId;
	}

	/**
	 * @return the clDeptName
	 */
	public String getClDeptName() {
		return clDeptName;
	}

	/**
	 * @param clDeptName the clDeptName to set
	 */
	public void setClDeptName(String clDeptName) {
		this.clDeptName = clDeptName;
	}

	/**
	 * @return the clJob
	 */
	public String getClJob() {
		return clJob;
	}

	/**
	 * @param clJob the clJob to set
	 */
	public void setClJob(String clJob) {
		this.clJob = clJob;
	}
}
