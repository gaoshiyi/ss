/**
 * 
 */
package com.sstc.hmis.permission.data.cas;

import java.io.Serializable;

/**
  * <p> Title: SimpleVo </p>
  * <p> Description:  TODO </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年5月26日 下午3:06:50
   */
public class SimpleVo implements Serializable{
	
	
	private static final long serialVersionUID = 2618831020784772196L;

	private String id;
	
	private String name;
	
	private String regin;

	public SimpleVo() {
		super();
	}

	/**
	 * @param id
	 * @param name
	 */
	public SimpleVo(String id, String name,String regin) {
		super();
		this.id = id;
		this.name = name;
		this.regin = regin;
	}
	
	/**
	 * @return the regin
	 */
	public String getRegin() {
		return regin;
	}

	/**
	 * @param regin the regin to set
	 */
	public void setRegin(String regin) {
		this.regin = regin;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	

}
