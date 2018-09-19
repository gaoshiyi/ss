package com.sstc.hmis.notification.data;

import java.util.Date;

public class NoticeBean {


    private String id;
    /**标题*/
    private String title;
    /**概要*/
    private String summary;
    /**详情*/
    private String detail;
    /**类型：对应码表PORTAL-NOTICETYPE，只用集团的码表数据，存编码*/
    private String type;
    /**有效开始时间*/
    private String validStarttime;
    /**有效结束时间*/
    private String validEndtime;
    /**发布人姓名*/
    private String publisher;
    /**发布人ID*/
    private String publisherId;
    /**是否发布：0 未发布；1 已发布*/
    private Short publishStatus;
    /**发布时间：只有当发布状态为1时，才有值，否则为空*/
    private String publishTime;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValidStarttime() {
		return validStarttime;
	}
	public void setValidStarttime(String validStarttime) {
		this.validStarttime = validStarttime;
	}
	public String getValidEndtime() {
		return validEndtime;
	}
	public void setValidEndtime(String validEndtime) {
		this.validEndtime = validEndtime;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}
	public Short getPublishStatus() {
		return publishStatus;
	}
	public void setPublishStatus(Short publishStatus) {
		this.publishStatus = publishStatus;
	}
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
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
