package com.sstc.hmis.permission.data;

import java.io.Serializable;
import java.util.Date;

public class Staff implements Serializable{
	private static final long serialVersionUID = -1950158676497770918L;
	/*
	 * 主键
	 */
	private String    id;
	/*
	 * 帐号名
	 */	
	private String    account;
	/*
	 * 邮箱
	 */	
	private String    email;
	/*
	 * 密码
	 */	
	private String    password;
	/*
	 * 盐
	 */
	private String    salt;
	/*
	 * 收银帐号
	 */
	private String    cashAccount;
	/*
	 * 收银密码
	 */
	private String    cashPwd;
	/*
	 * 状态
	 */
	private Short     status;
	/*
	 * 停用标识
	 */
	private Short     blockup;
	/*
	 * 过期时间
	 */
	private Date 	  expiredDate;
	/*
	 * 员工工号
	 */
	private String 	  staffNo;
	/*
	 * 默认工作酒店ID
	 */
	private String 	  defaultHotelId;
	/*
	 * 默认部门ID
	 */
	private String 	  defaultDptId;
	/*
	 * 默认职位ID
	 */
	private String 	  defaultPostId;
	/*
	 * 员工姓氏
	 */
	private String 	  familyName;
	/*
	 * 员工名字
	 */
	private String 	  name;
	/*
	 * 英文姓氏
	 */
	private String 	  fmyEn;
	/*
	 * 英文名字
	 */
	private String 	  nameEn;
	/*
	 * 性别
	 */
	private Short 	  sex;
	/*
	 * 出生日期
	 */
	private Date 	  birthDate;
	/*
	 * 联系电话
	 */
	private String 	  contacts;
	/*
	 * 手机号码
	 */
	private String 	  mobilePhone;
	/*
	 * 类型
	 */
	private Short 	  type;
	/*
	 * 学历Id
	 */
	private String 	  degreesId;
	/*
	 * 学历Code
	 */
	private String    degreesCode;
	/*
	 * 国家
	 */
	private String 	  countryId;
	/*
	 * 省
	 */
	private String 	  provinceId;
	/*
	 * 市
	 */
	private String 	  cityId;
	/*
	 * 区
	 */
	private Short 	  district;
	/*
	 * 详细地址
	 */
	private String 	  address;
	/*
	 * 证件类型Id
	 */
	private String 	  cardTypeId;
	/*
	 * 证件类型编码
	 */
	private String 	  cardTypeCode;
	/*
	 * 证件号
	 */
	private String 	  cardNo;
	/*
	 * 联系人1
	 */
	private String 	  emergency1;
	/*
	 * 联系电话1
	 */
	private String 	  emergencyNo1;
	/*
	 * 联系人2
	 */
	private String 	  emergency2;
	/*
	 * 联系电话2
	 */
	private String 	  emergencyNo2;
	/*
	 * 描述
	 */
	private String 	  description;
	/*
	 * 备注
	 */
	private String 	  remark;
	/*
	 * 是否限制登录
	 */
	private Short 	  loginLimit;
	/*
	 * 所属集团
	 */
	private String 	  grpId;
	/*
	 * 所属酒店
	 */
	private String 	  hotelId;
	/*
	 * 创建时间
	 */
	private Date 	  createTime;
	/*
	 * 创建人
	 */
	private String 	  createBy;
	/*
	 * 修改时间
	 */
	private Date 	  modifyTime;
	/*
	 * 
	 */
	private String 	  modifyBy;
	/*
	 * 头像
	 */
	private String avatar;
	/*
	 * 国家编码
	 */
	private String countryCode;
	/**
	 * 省编码；
	 */
	private String provinceCode;
	/**
	 * 市编码
	 */
	private String cityCode;
	/*
	 * 组织机构信息；
	 */
	private String orgInfo;
	/*
	 * 是否删除员工登陆权限标识；
	 */
	private Integer isDelFlag;
	/*
	 * 保存成功后前台account设为disabled，所以需要重新设一个；
	 */
	private String accountAft;
	
	private String condition; // 条件
	
	private String defaultHotelName;//默认酒店名称
	
	private String countryName;// 国家名称
	
	private String provinceName;// 省州名称
	
	private String cityName; //市名称
	
	private boolean loginType;//登录区分
	
	private int returnCode;// 返回值code
	/** 班次ID*/
	private String shiftId;
	private String shiftCode;
	
	// add by liurongrong for 设备区域
	private String equipmentId;
	
	/**
	 * @return the shiftCode
	 */
	public String getShiftCode() {
		return shiftCode;
	}

	/**
	 * @param shiftCode the shiftCode to set
	 */
	public void setShiftCode(String shiftCode) {
		this.shiftCode = shiftCode;
	}

	/** 组织机构*/
	private String pathName;
	
	/**
	 * @return the shiftId
	 */
	public String getShiftId() {
		return shiftId;
	}

	/**
	 * @param shiftId the shiftId to set
	 */
	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getAccountAft() {
		return accountAft;
	}

	public void setAccountAft(String accountAft) {
		this.accountAft = accountAft;
	}

	public Integer getIsDelFlag() {
		return isDelFlag;
	}

	public void setIsDelFlag(Integer isDelFlag) {
		this.isDelFlag = isDelFlag;
	}

	/**
	 * @return the defaultDptId
	 */
	public String getDefaultDptId() {
		return defaultDptId;
	}

	/**
	 * @param defaultDptId the defaultDptId to set
	 */
	public void setDefaultDptId(String defaultDptId) {
		this.defaultDptId = defaultDptId;
	}

	/**
	 * @return the defaultPostId
	 */
	public String getDefaultPostId() {
		return defaultPostId;
	}

	/**
	 * @param defaultPostId the defaultPostId to set
	 */
	public void setDefaultPostId(String defaultPostId) {
		this.defaultPostId = defaultPostId;
	}

//	public String getDepartSelId() {
//		return departSelId;
//	}
//
//	public void setDepartSelId(String departSelId) {
//		this.departSelId = departSelId;
//	}
//
//	public String getPostSelId() {
//		return postSelId;
//	}
//
//	public void setPostSelId(String postSelId) {
//		this.postSelId = postSelId;
//	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getOrgInfo() {
		return orgInfo;
	}

	public void setOrgInfo(String orgInfo) {
		this.orgInfo = orgInfo;
	}

	/**
	 * @return the avatar
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * @param avatar the avatar to set
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * @param userId
	 */
	public Staff(String userId) {
		this.id = userId;
	}

	public Staff(){}
	
	public String getId() {
		return id;
	}

	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * @return the provinceName
	 */
	public String getProvinceName() {
		return provinceName;
	}

	/**
	 * @param provinceName the provinceName to set
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getCashAccount() {
		return cashAccount;
	}

	public void setCashAccount(String cashAccount) {
		this.cashAccount = cashAccount;
	}

	public String getCashPwd() {
		return cashPwd;
	}

	public void setCashPwd(String cashPwd) {
		this.cashPwd = cashPwd;
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

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getDefaultHotelId() {
		return defaultHotelId;
	}

	public void setDefaultHotelId(String defaultHotelId) {
		this.defaultHotelId = defaultHotelId;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFmyEn() {
		return fmyEn;
	}

	public void setFmyEn(String fmyEn) {
		this.fmyEn = fmyEn;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public Short getSex() {
		return sex;
	}

	public void setSex(Short sex) {
		this.sex = sex;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public Short getDistrict() {
		return district;
	}

	public void setDistrict(Short district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCardTypeId() {
		return cardTypeId;
	}

	public void setCardTypeId(String cardTypeId) {
		this.cardTypeId = cardTypeId;
	}

	public String getCardTypeCode() {
		return cardTypeCode;
	}

	public void setCardTypeCode(String cardTypeCode) {
		this.cardTypeCode = cardTypeCode;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getEmergency1() {
		return emergency1;
	}

	public void setEmergency1(String emergency1) {
		this.emergency1 = emergency1;
	}

	public String getEmergencyNo1() {
		return emergencyNo1;
	}

	public void setEmergencyNo1(String emergencyNo1) {
		this.emergencyNo1 = emergencyNo1;
	}

	public String getEmergency2() {
		return emergency2;
	}

	public void setEmergency2(String emergency2) {
		this.emergency2 = emergency2;
	}

	public String getEmergencyNo2() {
		return emergencyNo2;
	}

	public void setEmergencyNo2(String emergencyNo2) {
		this.emergencyNo2 = emergencyNo2;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Short getLoginLimit() {
		return loginLimit;
	}

	public void setLoginLimit(Short loginLimit) {
		this.loginLimit = loginLimit;
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

	public String getDegreesId() {
		return degreesId;
	}

	public void setDegreesId(String degreesId) {
		this.degreesId = degreesId;
	}

	public String getDegreesCode() {
		return degreesCode;
	}

	public void setDegreesCode(String degreesCode) {
		this.degreesCode = degreesCode;
	}
	/**
	 * @param modifyTime the modifyTime to set
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}


	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	/**
	 * @return the defaultHotelName
	 */
	public String getDefaultHotelName() {
		return defaultHotelName;
	}

	/**
	 * @param defaultHotelName the defaultHotelName to set
	 */
	public void setDefaultHotelName(String defaultHotelName) {
		this.defaultHotelName = defaultHotelName;
	}

	/**
	 * @return the loginType
	 */
	public boolean isLoginType() {
		return loginType;
	}

	/**
	 * @param loginType the loginType to set
	 */
	public void setLoginType(boolean loginType) {
		this.loginType = loginType;
	}

	/**
	 * @return the returnCode
	 */
	public int getReturnCode() {
		return returnCode;
	}

	/**
	 * @param returnCode the returnCode to set
	 */
	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	/**
	 * @return the pathName
	 */
	public String getPathName() {
		return pathName;
	}

	/**
	 * @param pathName the pathName to set
	 */
	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}
}