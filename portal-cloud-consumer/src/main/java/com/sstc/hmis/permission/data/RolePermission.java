package com.sstc.hmis.permission.data;

import java.util.Date;

public class RolePermission {

	private String id;
	private String roleId;
	private String permissionId;
	private Short status;
	private Short blockup;
	private Short hasGovern;
	private String grpId;
	private String hotelId;
	private Date createTime;
	private String createBy;
	private Date modifyTime;
	private String modifyBy;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
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
	public Short getHasGovern() {
		return hasGovern;
	}
	public void setHasGovern(Short hasGovern) {
		this.hasGovern = hasGovern;
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
	
	
	

	
}