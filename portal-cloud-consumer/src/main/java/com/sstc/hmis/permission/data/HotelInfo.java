package com.sstc.hmis.permission.data;

import java.util.List;

/**
 * 酒店列表信息
 * @author Lrongrong
 *
 */
public class HotelInfo {

	/**
	 * 酒店id
	 */
	private String hotelId;
	
	/**
	 * 酒店名字
	 */
	private String hotelName;
	
	/**
	 * 人员数量
	 */
	private int size;
	
	/**
	 * 部门列表
	 */
	private List<GroupInfo> list;

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
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the list
	 */
	public List<GroupInfo> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<GroupInfo> list) {
		this.list = list;
	}
}
