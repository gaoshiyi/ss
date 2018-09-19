package com.sstc.hmis.permission.data;

import java.util.Date;

public class WorkTeam {
	/*
	 * 主键
	 */
	private String id;
	/*
	 * 中文名称
	 */
	private String name;
	/*
	 * 英文名称
	 */
	private String englishName;
	/*
	 * 类型编码
	 */
	private String typeCode;
	/*
	 * 类型ID
	 */
	private String typeId;
	/*
	 * 状态
	 */
	private Short status;
	/*
	 * 停用标识
	 */
	private Short blockup;
	/*
	 * 所属集团
	 */
	private String grpId;
	/*
	 * 所属酒店
	 */
	private String hotelId;
	/*
	 * 群主或圈主
	 */
	private String master;
	/*
	 * 管理员
	 */
	private String[] administrator;
	/*
	 * 是否可申请加入
	 */
	private Short applyJoin;
	/*
	 * 人数限制
	 */
	private Integer limitSize;
	/*
	 * 创建时间
	 */
	private Date createTime;
	/*
	 * 创建人
	 */
	private String createBy;
	/*
	 * 修改时间
	 */
	private Date modifyTime;
	/*
	 * 修改人
	 */
	private String modifyBy;
	/*
	 * 所属酒店或者集团
	 */
	private String belongId;
	/*
	 * 简介；
	 */
	private String intro;
	/*
	 * 成员人数
	 */
	private int memberCount;
	
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public int getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
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
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
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
	public String getMaster() {
		return master;
	}
	public void setMaster(String master) {
		this.master = master;
	}
	public String[] getAdministrator() {
		return administrator;
	}
	public void setAdministrator(String[] administrator) {
		this.administrator = administrator;
	}
	public Short getApplyJoin() {
		return applyJoin;
	}
	public void setApplyJoin(Short applyJoin) {
		this.applyJoin = applyJoin;
	}
	public Integer getLimitSize() {
		return limitSize;
	}
	public void setLimitSize(Integer limitSize) {
		this.limitSize = limitSize;
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
	public String getBelongId() {
		return belongId;
	}
	public void setBelongId(String belongId) {
		this.belongId = belongId;
	}

}
