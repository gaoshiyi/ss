package com.sstc.hmis.permission.data;

import java.util.Date;
import java.util.List;

public class Role {

	private String id; // 主键
	
	private String name; // 中文名称
	
	private String englishName; // 英文名称
	
	private Short status; // 状态
	
	private String statusString; // 状态（中文）
	
	private Short blockup; // 停用标识
	
	private String belongId; // 归属集团或酒店
	
	private String belongName; //归属集团或酒店名称
	
	private String grpId; // 所属集团
	
	private String hotelId; // 所属酒店
	
	private String orgId; // 所属部门
	 
	private Date createTime; // 创建时间
	
	private String createBy; // 创建人
	
	private Date modifyTime; // 修改时间
	
	private String modifyBy; // 修改人
	
	private String condition; // 条件
	
	private String belongId2;
	
	private List<String> ids;
	
	private List<String> staffIdList;
	/** 折扣率 */
	private Short disaccountRate;
	
	
	/**
	 * @return 折扣率
	 */
	public Short getDisaccountRate() {
		return disaccountRate;
	}

	/**
	 * @param disaccountRate the disaccountRate to set
	 */
	public void setDisaccountRate(Short disaccountRate) {
		this.disaccountRate = disaccountRate;
	}

	public List<String> getStaffIdList() {
		return staffIdList;
	}

	public void setStaffIdList(List<String> staffIdList) {
		this.staffIdList = staffIdList;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getBelongId2() {
		return belongId2;
	}

	public void setBelongId2(String belongId2) {
		this.belongId2 = belongId2;
	}

	private List<RolePermission> rolePermissionList;
	
	
	
	
	
	
	
	


	public List<RolePermission> getRolePermissionList() {
		return rolePermissionList;
	}

	public void setRolePermissionList(List<RolePermission> rolePermissionList) {
		this.rolePermissionList = rolePermissionList;
	}

	public String getBelongName() {
		return belongName;
	}

	public void setBelongName(String belongName) {
		this.belongName = belongName;
	}

	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getBelongId() {
		return belongId;
	}

	public void setBelongId(String belongId) {
		this.belongId = belongId;
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

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
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