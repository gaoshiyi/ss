package com.sstc.hmis.permission.dbaccess.data;

/**
 * 联系人所属酒店信息
 * @author Lrongrong
 *
 */
public class TLinkHotelInfo {

	/**
	 * 酒店ID
	 */
	private String clHotelId;
	
	/**
	 * 酒店名称
	 */
	private String clHotelName;
	
	/**
	 * 员工类型
	 */
	private Short clUserType;

	/**
	 * @return the clHotelId
	 */
	public String getClHotelId() {
		return clHotelId;
	}

	/**
	 * @param clHotelId the clHotelId to set
	 */
	public void setClHotelId(String clHotelId) {
		this.clHotelId = clHotelId;
	}

	/**
	 * @return the clHotelName
	 */
	public String getClHotelName() {
		return clHotelName;
	}

	/**
	 * @param clHotelName the clHotelName to set
	 */
	public void setClHotelName(String clHotelName) {
		this.clHotelName = clHotelName;
	}

	/**
	 * @return the clUserType
	 */
	public Short getClUserType() {
		return clUserType;
	}

	/**
	 * @param clUserType the clUserType to set
	 */
	public void setClUserType(Short clUserType) {
		this.clUserType = clUserType;
	}
}
