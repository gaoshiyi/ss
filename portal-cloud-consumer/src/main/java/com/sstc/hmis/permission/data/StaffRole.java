package com.sstc.hmis.permission.data;

public class StaffRole {

	private String id; // 主键
	
	
	private String staffId;
	
	private String flag;
	
	private String roleId;
	
	private String roleName;
	
	private String hotelId;
	
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	public String getHotelId() {
		return hotelId;
	}


	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}


	public String getRoleId() {
		return roleId;
	}


	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}


	public String getFlag() {
		return flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getStaffId() {
		return staffId;
	}


	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	
	

	
}