/**
 * 
 */
package com.sstc.hmis.permission.data;

import java.io.Serializable;
import java.util.List;

/**
  * <p> Title: SystemMenu </p>
  * <p> Description:  TODO </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年7月12日 上午11:42:12
   */
public class SystemMenu implements Serializable{

	private static final long serialVersionUID = 5600862974169420494L;

	private String id;
	/** 菜单图标样式 */
	private String elCls;
	/** 菜单名*/
	private String text;
	/** 是否收缩树结构*/
	private boolean collapsed = true;
	/** 菜单链接*/
	private String href;
	/** 子菜单*/
	private List<MenuTreeNode> menu;
	
	private String pid;
	
	
	public SystemMenu() {
		super();
	}

	/**
	 * @param id
	 * @param elCls
	 * @param text
	 * @param href
	 * @param pid
	 */
	public SystemMenu(String id, String elCls, String text, String href, String pid, List<MenuTreeNode> menu) {
		super();
		this.id = id;
		this.elCls = elCls;
		this.text = text;
		this.href = href;
		this.pid = pid;
		this.menu = menu;
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
	 * @return the elCls
	 */
	public String getElCls() {
		return elCls;
	}

	/**
	 * @param elCls the elCls to set
	 */
	public void setElCls(String elCls) {
		this.elCls = elCls;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the collapsed
	 */
	public boolean isCollapsed() {
		return collapsed;
	}

	/**
	 * @param collapsed the collapsed to set
	 */
	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}

	/**
	 * @return the href
	 */
	public String getHref() {
		return href;
	}

	/**
	 * @param href the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * @return the menu
	 */
	public List<MenuTreeNode> getMenu() {
		return menu;
	}

	/**
	 * @param menu the menu to set
	 */
	public void setMenu(List<MenuTreeNode> menu) {
		this.menu = menu;
	}

	/**
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * @param pid the pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}

	
	
}
