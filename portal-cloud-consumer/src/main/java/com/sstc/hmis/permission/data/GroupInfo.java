package com.sstc.hmis.permission.data;

import java.util.List;

/**
 * 酒店下部门信息
 * @author Lrongrong
 *
 */
public class GroupInfo {

	/**
	 * 部门id
	 */
	private String depId;
	
	/**
	 * 部门名字
	 */
	private String depName;
	
	/**
	 * 父级部门ID
	 */
	private String parentId;
	
	/**
	 * 人员数量
	 */
	private int size;
	
	/**
	 * 部门登记
	 */
	private int depth;
	
	/**
	 * 子部门领导列表
	 */
	private List<DeptUserList> userList;

	/**
	 * @return the depId
	 */
	public String getDepId() {
		return depId;
	}

	/**
	 * @param depId the depId to set
	 */
	public void setDepId(String depId) {
		this.depId = depId;
	}

	/**
	 * @return the depName
	 */
	public String getDepName() {
		return depName;
	}

	/**
	 * @param depName the depName to set
	 */
	public void setDepName(String depName) {
		this.depName = depName;
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
	 * @return the userList
	 */
	public List<DeptUserList> getUserList() {
		return userList;
	}

	/**
	 * @param userList the userList to set
	 */
	public void setUserList(List<DeptUserList> userList) {
		this.userList = userList;
	}

	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the depth
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * @param depth the depth to set
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}
}
