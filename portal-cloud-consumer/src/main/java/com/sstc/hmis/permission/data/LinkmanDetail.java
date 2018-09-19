package com.sstc.hmis.permission.data;

import java.util.List;

/**
 * 联系人详情
 * @author Lrongrong
 *
 */
public class LinkmanDetail {

	/**
	 * 联系人姓名
	 */
	private String linkmanName;
	
	/**
	 * 联系人英文名称
	 */
	private String linkmanEnName;
	
	/**
	 * 联系人性别
	 */
	private Short linkmanSex;
	
	/**
	 * 联系人头像
	 */
	private String linkmanAvatar;
	
	/**
	 * 联系人手机号码
	 */
	private String linkmanPhone;
	
	/**
	 * 联系人邮箱地址
	 */
	private String linkmanEmail;
	
	/**
	 * 联系人所属国家
	 */
	private String linkmanCountry;
	
	/**
	 * 联系人省份
	 */
	private String linkmanProvice;
	
	/**
	 * 联系人市
	 */
	private String linkmanCity;
	
	/**
	 * 联系人所属酒店信息
	 */
	private List<LinkHotelInfo> linkHotelList;

	/**
	 * @return the linkmanName
	 */
	public String getLinkmanName() {
		return linkmanName;
	}

	/**
	 * @param linkmanName the linkmanName to set
	 */
	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}

	/**
	 * @return the linkmanSex
	 */
	public Short getLinkmanSex() {
		return linkmanSex;
	}

	/**
	 * @param linkmanSex the linkmanSex to set
	 */
	public void setLinkmanSex(Short linkmanSex) {
		this.linkmanSex = linkmanSex;
	}

	/**
	 * @return the linkmanAvatar
	 */
	public String getLinkmanAvatar() {
		return linkmanAvatar;
	}

	/**
	 * @param linkmanAvatar the linkmanAvatar to set
	 */
	public void setLinkmanAvatar(String linkmanAvatar) {
		this.linkmanAvatar = linkmanAvatar;
	}

	/**
	 * @return the linkmanPhone
	 */
	public String getLinkmanPhone() {
		return linkmanPhone;
	}

	/**
	 * @param linkmanPhone the linkmanPhone to set
	 */
	public void setLinkmanPhone(String linkmanPhone) {
		this.linkmanPhone = linkmanPhone;
	}

	/**
	 * @return the linkmanEmail
	 */
	public String getLinkmanEmail() {
		return linkmanEmail;
	}

	/**
	 * @param linkmanEmail the linkmanEmail to set
	 */
	public void setLinkmanEmail(String linkmanEmail) {
		this.linkmanEmail = linkmanEmail;
	}

	/**
	 * @return the linkmanCountry
	 */
	public String getLinkmanCountry() {
		return linkmanCountry;
	}

	/**
	 * @param linkmanCountry the linkmanCountry to set
	 */
	public void setLinkmanCountry(String linkmanCountry) {
		this.linkmanCountry = linkmanCountry;
	}

	/**
	 * @return the linkmanProvice
	 */
	public String getLinkmanProvice() {
		return linkmanProvice;
	}

	/**
	 * @param linkmanProvice the linkmanProvice to set
	 */
	public void setLinkmanProvice(String linkmanProvice) {
		this.linkmanProvice = linkmanProvice;
	}

	/**
	 * @return the linkmanCity
	 */
	public String getLinkmanCity() {
		return linkmanCity;
	}

	/**
	 * @param linkmanCity the linkmanCity to set
	 */
	public void setLinkmanCity(String linkmanCity) {
		this.linkmanCity = linkmanCity;
	}

	/**
	 * @return the linkHotelList
	 */
	public List<LinkHotelInfo> getLinkHotelList() {
		return linkHotelList;
	}

	/**
	 * @param linkHotelList the linkHotelList to set
	 */
	public void setLinkHotelList(List<LinkHotelInfo> linkHotelList) {
		this.linkHotelList = linkHotelList;
	}

	/**
	 * @return the linkmanEnName
	 */
	public String getLinkmanEnName() {
		return linkmanEnName;
	}

	/**
	 * @param linkmanEnName the linkmanEnName to set
	 */
	public void setLinkmanEnName(String linkmanEnName) {
		this.linkmanEnName = linkmanEnName;
	}
}
