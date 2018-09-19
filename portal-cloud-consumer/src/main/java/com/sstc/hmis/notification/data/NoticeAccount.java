package com.sstc.hmis.notification.data;

import java.io.Serializable;
import java.util.Date;

public class NoticeAccount implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private String id;
    /**通知消息ID*/
    private String noticeId;
    /**账号ID*/
    private String accountId;
    /**确认状态：0确认未填写确认回执 1 确认并填写确认回执*/
    private Short status;
    /**集团ID*/
    private String grpId;
    /**酒店ID*/
    private String hotelId;
    /**创建用户*/
    private String createUserId;
    /**创建时间*/
    private Date createTime;
    /**更新用户*/
    private String updateUserId;
    /**更新时间*/
    private Date updateTime;
    /**操作类型*/
    private String operType;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
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
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
}