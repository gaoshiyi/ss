package com.sstc.hmis.permission.dbaccess.data;

public class PermGrpMember {

	/**
	 * 员工ID
	 */
	private String clStaffId;
	/*
	 * 中文名称；
	 */
	private String clChName;
	/*
	 * 中文姓氏
	 */
	private String clFamilyName;
	/*
	 * 所属酒店ID；
	 */
	private String clDefaultHotelNameId;
	/*
	 * 所属酒店名称；
	 */
	private String clDefaultHotelName;
	/*
	 * 部门名称Id；
	 */
	private String clDepartmentNameId;
	/*
	 * 部门名称名称；
	 */
	private String clDepartmentName;
	/*
	 * 职位Id；
	 */
	private String clPostId;
	/*
	 * 职位名称；
	 */
	private String clPostName;
	/**
	 * 工作圈ID
	 */
	private String clTeamId;
	/*
	 * 所属集团；
	 */
	private String clGrpId;
	/*
	 * 所属酒店
	 */
	private String clHotId;
	/*
	 * 创建人
	 */
	private String clCreateBy;
	
	public String getClFamilyName() {
		return clFamilyName;
	}
	public void setClFamilyName(String clFamilyName) {
		this.clFamilyName = clFamilyName;
	}
	public String getClGrpId() {
		return clGrpId;
	}
	public void setClGrpId(String clGrpId) {
		this.clGrpId = clGrpId;
	}
	public String getClHotId() {
		return clHotId;
	}
	public void setClHotId(String clHotId) {
		this.clHotId = clHotId;
	}
	public String getClCreateBy() {
		return clCreateBy;
	}
	public void setClCreateBy(String clCreateBy) {
		this.clCreateBy = clCreateBy;
	}
	public String getClDefaultHotelName() {
		return clDefaultHotelName;
	}
	public void setClDefaultHotelName(String clDefaultHotelName) {
		this.clDefaultHotelName = clDefaultHotelName;
	}
	public String getClDepartmentNameId() {
		return clDepartmentNameId;
	}
	public void setClDepartmentNameId(String clDepartmentNameId) {
		this.clDepartmentNameId = clDepartmentNameId;
	}
	public String getClPostId() {
		return clPostId;
	}
	public void setClPostId(String clPostId) {
		this.clPostId = clPostId;
	}
	public String getClStaffId() {
		return clStaffId;
	}
	public void setClStaffId(String clStaffId) {
		this.clStaffId = clStaffId;
	}
	public String getClChName() {
		return clChName;
	}
	public void setClChName(String clChName) {
		this.clChName = clChName;
	}
	public String getClDefaultHotelNameId() {
		return clDefaultHotelNameId;
	}
	public void setClDefaultHotelNameId(String clDefaultHotelNameId) {
		this.clDefaultHotelNameId = clDefaultHotelNameId;
	}
	public String getClDepartmentName() {
		return clDepartmentName;
	}
	public void setClDepartmentName(String clDepartmentName) {
		this.clDepartmentName = clDepartmentName;
	}
	public String getClPostName() {
		return clPostName;
	}
	public void setClPostName(String clPostName) {
		this.clPostName = clPostName;
	}
	public String getClTeamId() {
		return clTeamId;
	}
	public void setClTeamId(String clTeamId) {
		this.clTeamId = clTeamId;
	}
	
}
