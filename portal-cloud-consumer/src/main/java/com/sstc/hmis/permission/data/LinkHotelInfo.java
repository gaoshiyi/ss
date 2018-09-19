package com.sstc.hmis.permission.data;

import java.util.List;

/**
 * 联系人所属酒店信息
 * @author Lrongrong
 *
 */
public class LinkHotelInfo {

	/**
	 * 酒店ID
	 */
	private String hotelId;
	
	/**
	 * 酒店名称
	 */
	private String hotelName;
	
	/**
	 * 部门信息列表
	 */
	private List<LinkmanDeptInfo> linkmanDeptInfoList;
	
	/**
	 * 员工类型
	 */
	private String userType;

	/**
	 * @return the hotelId
	 */
	public String getHotelId() {
		return hotelId;
	}

	/**
	 * @param hotelId the hotelId to set
	 */
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	/**
	 * @return the hotelName
	 */
	public String getHotelName() {
		return hotelName;
	}

	/**
	 * @param hotelName the hotelName to set
	 */
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return the linkmanDeptInfoList
	 */
	public List<LinkmanDeptInfo> getLinkmanDeptInfoList() {
		return linkmanDeptInfoList;
	}

	/**
	 * @param linkmanDeptInfoList the linkmanDeptInfoList to set
	 */
	public void setLinkmanDeptInfoList(List<LinkmanDeptInfo> linkmanDeptInfoList) {
		this.linkmanDeptInfoList = linkmanDeptInfoList;
	}
}
