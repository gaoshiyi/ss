/**
 * 
 */
package com.sstc.hmis.permission.dbaccess.data.domain;

import java.io.Serializable;

/**
  * <p> Title: PermParent </p>
  * <p> Description:  权限的父节点关系 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年8月24日 上午10:11:09
   */
public class PermParent implements Serializable{

	private static final long serialVersionUID = -8762932927156581812L;

	private String permId;
	
	private String parentId;
	
	private String systemId;
	
	private Short permlevel;
	

	/**
	 * @return the systemId
	 */
	public String getSystemId() {
		return systemId;
	}

	/**
	 * @param systemId the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * @return the permId
	 */
	public String getPermId() {
		return permId;
	}

	/**
	 * @param permId the permId to set
	 */
	public void setPermId(String permId) {
		this.permId = permId;
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
	 * @return the permlevel
	 */
	public Short getPermlevel() {
		return permlevel;
	}

	/**
	 * @param permlevel the permlevel to set
	 */
	public void setPermlevel(Short permlevel) {
		this.permlevel = permlevel;
	}
	
	
	
}
