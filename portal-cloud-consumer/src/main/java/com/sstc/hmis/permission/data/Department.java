package com.sstc.hmis.permission.data;

import java.util.Date;
public class Department {

	private String id;

	private String name;

	private String englishName;

	private String code;

	private Short type;

	private Short status;

	private Short blockup;

	private String pid;
	
	private String pname;

	private Short shareFlag;

	private String grpId;

	private String hotelId;

	private Date createTime;

	private String createBy;

	private Date modifyTime;

	private String modifyBy;
	
	private String workTeamId;

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

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
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

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Short getShareFlag() {
		return shareFlag;
	}

	public void setShareFlag(Short shareFlag) {
		this.shareFlag = shareFlag;
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

	
	public String getWorkTeamId() {
		return workTeamId;
	}

	public void setWorkTeamId(String workTeamId) {
		this.workTeamId = workTeamId;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}


	private Short grpType;
	private Short deptType;
	private String orgName;
	private int jobCount;
	private int employeeCount;

	public Short getGrpType() {
		return grpType;
	}

	public void setGrpType(Short grpType) {
		this.grpType = grpType;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Short getDeptType() {
		return deptType;
	}

	public void setDeptType(Short deptType) {
		this.deptType = deptType;
	}

	public int getJobCount() {
		return jobCount;
	}

	public void setJobCount(int jobCount) {
		this.jobCount = jobCount;
	}

	public int getEmployeeCount() {
		return employeeCount;
	}

	public void setEmployeeCount(int employeeCount) {
		this.employeeCount = employeeCount;
	}

}