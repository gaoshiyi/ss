package com.sstc.hmis.notification.data;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Notice implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
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
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date validStarttime;
    /**有效结束时间*/
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date validEndtime;
    /**发布人姓名*/
    private String publisher;
    /**发布人ID*/
    private String publisherId;
    /**是否发布：0 未发布；1 已发布*/
    private Short publishStatus = 1;
    /**发布时间：只有当发布状态为1时，才有值，否则为空*/
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date publishTime;
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
    /**页面展示有效时间*/
    private String validateDate;
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
	public Date getValidStarttime() {
		return validStarttime;
	}
	public void setValidStarttime(Date validStarttime) {
		this.validStarttime = validStarttime;
	}
	public Date getValidEndtime() {
		return validEndtime;
	}
	public void setValidEndtime(Date validEndtime) {
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
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
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
	public String getValidateDate() {
		return validateDate;
	}
	public void setValidateDate(String validateDate) {
		this.validateDate = validateDate;
	}
}