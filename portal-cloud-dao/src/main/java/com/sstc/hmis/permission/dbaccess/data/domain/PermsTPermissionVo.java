/**
 * 
 */
package com.sstc.hmis.permission.dbaccess.data.domain;

import com.sstc.hmis.permission.dbaccess.data.PermsTPermission;

/**
  * <p> Title: PermsTPermissionVo </p>
  * <p> Description:  TODO </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年5月8日 下午2:45:47
   */
public class PermsTPermissionVo extends PermsTPermission{

	private String clPermPath;

	/**
	 * @return the clPermPath
	 */
	public String getClPermPath() {
		return clPermPath;
	}

	/**
	 * @param clPermPath the clPermPath to set
	 */
	public void setClPermPath(String clPermPath) {
		this.clPermPath = clPermPath;
	}
	
	
}
