/**
 * 
 */
package com.sstc.hmis.permission.data;

import java.io.Serializable;
import java.util.List;

/**
  * <p> Title: MenuTreeNode </p>
  * <p> Description:  菜单树形结构 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年7月10日 下午3:13:41
   */
public class MenuTreeNode implements Serializable{
	
	private static final long serialVersionUID = 1983778829765378628L;

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
	private List<MenuTreeNode> items;
	
	private String pid;
	
	private Integer dialogHeight;
	
	private Integer dialogWidth;
	
	private Short openType;
	
	
	/**
	 * @return the dialogHeight
	 */
	public Integer getDialogHeight() {
		return dialogHeight;
	}

	/**
	 * @param dialogHeight the dialogHeight to set
	 */
	public void setDialogHeight(Integer dialogHeight) {
		this.dialogHeight = dialogHeight;
	}

	/**
	 * @return the dialogWidth
	 */
	public Integer getDialogWidth() {
		return dialogWidth;
	}

	/**
	 * @param dialogWidth the dialogWidth to set
	 */
	public void setDialogWidth(Integer dialogWidth) {
		this.dialogWidth = dialogWidth;
	}

	/**
	 * @return the openType
	 */
	public Short getOpenType() {
		return openType;
	}

	/**
	 * @param openType the openType to set
	 */
	public void setOpenType(Short openType) {
		this.openType = openType;
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

	public MenuTreeNode() {
		super();
	}

	/**
	 * @param id 
	 * @param elCls icon样式
	 * @param text 文本
	 * @param href 链接
	 * @param menu 子菜单
	 */
	public MenuTreeNode(String id, String elCls, String text, String href, String pid) {
		super();
		this.id = id;
		this.elCls = elCls;
		this.text = text;
		this.href = href;
		this.pid = pid;
	}
	
	
	public MenuTreeNode(String id, String elCls, String text, String href, String pid, Integer height, Integer width,
			Short openType) {
		this.id = id;
		this.elCls = elCls;
		this.text = text;
		this.href = href;
		this.pid = pid;
		this.dialogHeight = height;
		this.dialogWidth = width;
		this.openType = openType;
	}

	/**
	 * @return the item
	 */
	public List<MenuTreeNode> getItems() {
		return items;
	}

	/**
	 * @param item the item to set
	 */
	public void setItems(List<MenuTreeNode> items) {
		this.items = items;
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
	
	
}
