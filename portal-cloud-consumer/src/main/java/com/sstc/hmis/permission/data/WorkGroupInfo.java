package com.sstc.hmis.permission.data;

import java.util.List;

/**
 * 工作圈信息
 * @author Lrongrong
 *
 */
public class WorkGroupInfo {

	/**
	 * 工作圈id
	 */
	private String groupId;
	
	/**
	 * 工作圈名字
	 */
	private String groupName;
	
	/**
	 * 工作圈所属酒店id
	 */
	private String hotelId;
	
	/**
	 * 工作圈所属酒店名称
	 */
	private String hotelName;
	
	/**
	 * 工作圈的成员列表
	 */
	private List<LinkmanInfo> list;

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

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
	 * @return the list
	 */
	public List<LinkmanInfo> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<LinkmanInfo> list) {
		this.list = list;
	}
}
