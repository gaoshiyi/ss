/**
  * 文件名：AreaNode.java
  * 日期：2017年3月21日
  * Copyright sstc Corporation 2017 
  * 版权所有
  */
package com.sstc.hmis.permission.data;

import java.io.Serializable;

/**
 * @ClassName AreaNode
 * @Description 国家省市区域数据实体 
 * @author yaodm
 * @date 2017年3月21日 上午9:59:58 
*/
public class Area implements Serializable{
	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = -3002974733223372723L;

	/**
	 * ID
	 */
	private String id;
	
	/**
	 * name：显示值
	 */
	private String name;
	
	/**
	 * namePinyin:名字的拼音（给小秘接口提供的，只针对国家进行拼音的转换）
	 */
	private String namePinyin;
	

	public String getNamePinyin() {
		return namePinyin;
	}

	public void setNamePinyin(String namePinyin) {
		this.namePinyin = namePinyin;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 获取ID
	 * @return id
	*/
	public String getId() {
		return id;
	}
	
	/**  
	 * 设置ID  
	 * @param id ID  
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 获取name：显示值
	 * @return name：显示值
	*/
	public String getName() {
		return name;
	}

	/**  
	 * 设置name：显示值  
	 * @param name name：显示值  
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
