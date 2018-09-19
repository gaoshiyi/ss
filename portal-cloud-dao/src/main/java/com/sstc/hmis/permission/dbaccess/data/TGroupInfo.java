package com.sstc.hmis.permission.dbaccess.data;

/**
 * 酒店下部门信息
 * @author Lrongrong
 *
 */
public class TGroupInfo {

	/**
	 * 部门id
	 */
	private String clDepId;
	
	/**
	 * 部门名字
	 */
	private String clDepName;
	
	/**
	 * 父级部门ID
	 */
	private String clParentId;
	
	/**
	 * 人员数量
	 */
	private int clSize;
	
	/**
	 * 部门登记
	 */
	private int clDepth;

	/**
	 * @return the clDepId
	 */
	public String getClDepId() {
		return clDepId;
	}

	/**
	 * @param clDepId the clDepId to set
	 */
	public void setClDepId(String clDepId) {
		this.clDepId = clDepId;
	}

	/**
	 * @return the clDepName
	 */
	public String getClDepName() {
		return clDepName;
	}

	/**
	 * @param clDepName the clDepName to set
	 */
	public void setClDepName(String clDepName) {
		this.clDepName = clDepName;
	}

	/**
	 * @return the clParentId
	 */
	public String getClParentId() {
		return clParentId;
	}

	/**
	 * @param clParentId the clParentId to set
	 */
	public void setClParentId(String clParentId) {
		this.clParentId = clParentId;
	}

	/**
	 * @return the clSize
	 */
	public int getClSize() {
		return clSize;
	}

	/**
	 * @param clSize the clSize to set
	 */
	public void setClSize(int clSize) {
		this.clSize = clSize;
	}

	/**
	 * @return the clDepth
	 */
	public int getClDepth() {
		return clDepth;
	}

	/**
	 * @param clDepth the clDepth to set
	 */
	public void setClDepth(int clDepth) {
		this.clDepth = clDepth;
	}
}
