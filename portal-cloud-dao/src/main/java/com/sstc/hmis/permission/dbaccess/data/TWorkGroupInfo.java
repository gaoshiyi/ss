package com.sstc.hmis.permission.dbaccess.data;

/**
 * 工作圈信息
 * @author Lrongrong
 *
 */
public class TWorkGroupInfo {

	/**
	 * 工作圈id
	 */
	private String clGroupId;
	
	/**
	 * @return the clGroupId
	 */
	public String getClGroupId() {
		return clGroupId;
	}

	/**
	 * @param clGroupId the clGroupId to set
	 */
	public void setClGroupId(String clGroupId) {
		this.clGroupId = clGroupId;
	}

	/**
	 * @return the clGroupName
	 */
	public String getClGroupName() {
		return clGroupName;
	}

	/**
	 * @param clGroupName the clGroupName to set
	 */
	public void setClGroupName(String clGroupName) {
		this.clGroupName = clGroupName;
	}

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
	 * 工作圈名字
	 */
	private String clGroupName;
	
	/**
	 * 工作圈所属酒店id
	 */
	private String clHotelId;
	
	/**
	 * 工作圈所属酒店名称
	 */
	private String clHotelName;
}
