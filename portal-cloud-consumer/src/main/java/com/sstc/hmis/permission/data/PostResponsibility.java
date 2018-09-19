package com.sstc.hmis.permission.data;

import java.util.Date;

public class PostResponsibility {

	private String id;
	private String name; // 中文名称
	private String englishName; // 英文名称
	private String code;
	private Short status;
	private Short blockup;
	private String[] defaultRole;
	private String defaultTeam;
	private String grpId;
	private String hotelId;
	private Date createTime;
	private String createBy;
	private Date modifyTime;
	private String modifyBy;
	private String postId;
	private String postCode;
	
	private String postIdCode;
	
	private String condition; // 条件
	
	private Integer staffCount=0; // 员工数量
	
	private String[] defaultRoleName; // 默认角色名称
	
	private String defaultTeamName; // 默认工作圈名称
	
	private String organization; // 组织
	
	private String dptId; // 部门id
	
	
	
	
	
	
	
	
	public String getPostIdCode() {
		return postIdCode;
	}
	public void setPostIdCode(String postIdCode) {
		this.postIdCode = postIdCode;
	}
	public String getDptId() {
		return dptId;
	}
	public void setDptId(String dptId) {
		this.dptId = dptId;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getDefaultTeamName() {
		return defaultTeamName;
	}
	public void setDefaultTeamName(String defaultTeamName) {
		this.defaultTeamName = defaultTeamName;
	}
	public String[] getDefaultRoleName() {
		return defaultRoleName;
	}
	public void setDefaultRoleName(String[] defaultRoleName) {
		this.defaultRoleName = defaultRoleName;
	}
	public Integer getStaffCount() {
		return staffCount;
	}
	public void setStaffCount(Integer staffCount) {
		this.staffCount = staffCount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public Short getBlockup() {
		return blockup;
	}
	public void setBlockup(Short blockup) {
		this.blockup = blockup;
	}
	public String[] getDefaultRole() {
		return defaultRole;
	}
	
	public void setDefaultRole(String[] defaultRole) {
		this.defaultRole = defaultRole;
	}
	public String getDefaultTeam() {
		return defaultTeam;
	}
	public void setDefaultTeam(String defaultTeam) {
		this.defaultTeam = defaultTeam;
	}
	public String getGrpId() {
		return grpId;
	}
	public void setGrpId(String grpId) {
		this.grpId = grpId;
	}
	public String getHotelId() {
		return hotelId;
	}
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	
	

}