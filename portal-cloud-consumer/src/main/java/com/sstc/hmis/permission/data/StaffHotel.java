package com.sstc.hmis.permission.data;

import java.util.Date;

public class StaffHotel {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 员工ID
	 */
	private String staffId;
	/**
	 * 停用标识
	 */
	private Short blockup;
	/**
	 * 工作酒店
	 */
	private String workHotelId;
	/**
	 * 部门ID
	 */
	private String dptId;
	/**
	 * 职务ID
	 */
	private String postId;
	/**
	 * 是否登录受限
	 */
	private Short isLimit;
	/**
	 * 开始日期
	 */
	private Date beginDate;
	/**
	 * 结束日期
	 */
	private Date endDate;
	/**
	 * 时间区间
	 */
	private String[] timeInterval;
	/**
	 * 限制周几
	 */
	private String[] week;
	/**
	 * 设备类型
	 */
	private Integer[] limitType;
	/**
	 *登录类型
	 */
	private Short loginType;
	/**
	 * 所属集团
	 */
	private String grpId;
	/**
	 * 所属酒店
	 */
	private String hotelId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	/**
	 * 修改人
	 */
	private String modifyBy;
	/*
	 * 状态；
	 */
	private Short status;
	/*
	 * 传到前台的起始时间；
	 */
	private String beginDateStr;
	/*
	 * 传到前台的终止时间；
	 */
	private String endDateStr;
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public String getBeginDateStr() {
		return beginDateStr;
	}
	public void setBeginDateStr(String beginDateStr) {
		this.beginDateStr = beginDateStr;
	}
	public String getEndDateStr() {
		return endDateStr;
	}
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
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
	public Short getBlockup() {
		return blockup;
	}
	public void setBlockup(Short blockup) {
		this.blockup = blockup;
	}
	public String getWorkHotelId() {
		return workHotelId;
	}
	public void setWorkHotelId(String workHotelId) {
		this.workHotelId = workHotelId;
	}
	public String getDptId() {
		return dptId;
	}
	public void setDptId(String dptId) {
		this.dptId = dptId;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public Short getIsLimit() {
		return isLimit;
	}
	public void setIsLimit(Short isLimit) {
		this.isLimit = isLimit;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String[] getTimeInterval() {
		return timeInterval;
	}
	public void setTimeInterval(String[] timeInterval) {
		this.timeInterval = timeInterval;
	}
	public String[] getWeek() {
		return week;
	}
	public void setWeek(String[] week) {
		this.week = week;
	}
	public Integer[] getLimitType() {
		return limitType;
	}
	public void setLimitType(Integer[] limitType) {
		this.limitType = limitType;
	}
	public Short getLoginType() {
		return loginType;
	}
	public void setLoginType(Short loginType) {
		this.loginType = loginType;
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