package com.sstc.hmis.permission.data;

/**
 * 邀请成员展示Grid表
 * 
 * @author xuzhichuan 创建时间 2017年4月6日
 */
public class GrpMember {
	/**
	 * 员工ID
	 */
	private String staffId;
	/*
	 * 中文名称；
	 */
	private String chName;
	/*
	 * 中文姓氏
	 */
	private String familyName;
	/*
	 * 所属酒店；
	 */
	private String defaultHotelNameId;
	/*
	 * 所属酒店名称；
	 */
	private String defaultHotelName;
	/*
	 * 部门名称Id；
	 */
	private String departmentNameId;
	/*
	 * 部门名称；
	 */
	private String departmentName;
	/*
	 * 职位Id；
	 */
	private String postId;
	/*
	 * 职位名称；
	 */
	private String postName;
	/**
	 * 工作圈ID
	 */
	private String teamId;
	/*
	 * 所属集团；
	 */
	private String grpId;
	/*
	 * 所属酒店
	 */
	private String hotId;
	/*
	 * 创建人
	 */
	private String createBy;
	/*
	 * 组织信息；
	 */
	private String orgInfo;
	
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getGrpId() {
		return grpId;
	}
	public void setGrpId(String grpId) {
		this.grpId = grpId;
	}
	public String getHotId() {
		return hotId;
	}
	public void setHotId(String hotId) {
		this.hotId = hotId;
	}
	public String getOrgInfo() {
		return orgInfo;
	}
	public void setOrgInfo(String orgInfo) {
		this.orgInfo = orgInfo;
	}
	public String getDefaultHotelName() {
		return defaultHotelName;
	}
	public void setDefaultHotelName(String defaultHotelName) {
		this.defaultHotelName = defaultHotelName;
	}
	public String getDepartmentNameId() {
		return departmentNameId;
	}
	public void setDepartmentNameId(String departmentNameId) {
		this.departmentNameId = departmentNameId;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getChName() {
		return chName;
	}
	public void setChName(String chName) {
		this.chName = chName;
	}
	public String getDefaultHotelNameId() {
		return defaultHotelNameId;
	}
	public void setDefaultHotelNameId(String defaultHotelNameId) {
		this.defaultHotelNameId = defaultHotelNameId;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	
}
