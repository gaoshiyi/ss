package com.sstc.hmis.permission.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Permission implements Serializable{

	private static final long serialVersionUID = -6694757882319826887L;
	
	private String id;
	private String name;
	private Short type;
	private Short status;
	private Short blockup;
	private String url;
	private Short isBase;
	private Short hasOthers;
	private String pid;
	private BigDecimal price;
	private String grpId;
	private String hotelId;
	private Date createTime;
	private String createBy;
	private Date modifyTime;
	private String modifyBy;
	private String[] subUrl;
	
	private String iconClass;
	
	private Short order;
	
	private Short isMenu;
	
	private Short isReport;
	
	private Short openType;
	
	private Integer height;
	
	private Integer width;
	
	/**
	 * @return the isReport
	 */
	public Short getIsReport() {
		return isReport;
	}
	/**
	 * @param isReport the isReport to set
	 */
	public void setIsReport(Short isReport) {
		this.isReport = isReport;
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
	 * @return the height
	 */
	public Integer getHeight() {
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}
	/**
	 * @return the width
	 */
	public Integer getWidth() {
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private List<Permission> children;
	
	/**
	 * @return the isMenu
	 */
	public Short getIsMenu() {
		return isMenu;
	}
	/**
	 * @param isMenu the isMenu to set
	 */
	public void setIsMenu(Short isMenu) {
		this.isMenu = isMenu;
	}
	public List<Permission> getChildren() {
		return children;
	}
	public void setChildren(List<Permission> children) {
		this.children = children;
	}
	/**
	 * @return the iconClass
	 */
	public String getIconClass() {
		return iconClass;
	}
	/**
	 * @param iconClass the iconClass to set
	 */
	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}
	/**
	 * @return the order
	 */
	public Short getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(Short order) {
		this.order = order;
	}
	private String first;
	
	private String fourth;
	
	private String second;
	
	private String third;
	
	private String permPath;
	
	
	/**
	 * @return the permPath
	 */
	public String getPermPath() {
		return permPath;
	}
	/**
	 * @param permPath the permPath to set
	 */
	public void setPermPath(String permPath) {
		this.permPath = permPath;
	}
	/**
	 * @return the subUrl
	 */
	public String[] getSubUrl() {
		return subUrl;
	}
	/**
	 * @param subUrl the subUrl to set
	 */
	public void setSubUrl(String[] subUrl) {
		this.subUrl = subUrl;
	}
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getFourth() {
		return fourth;
	}
	public void setFourth(String fourth) {
		this.fourth = fourth;
	}
	public String getSecond() {
		return second;
	}
	public void setSecond(String second) {
		this.second = second;
	}
	public String getThird() {
		return third;
	}
	public void setThird(String third) {
		this.third = third;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Short getType() {
		return type;
	}
	public void setType(Short type) {
		this.type = type;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public Short getBlockup() {
		return blockup;
	}
	public void setBlockup(Short blockup) {
		this.blockup = blockup;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Short getIsBase() {
		return isBase;
	}
	public void setIsBase(Short isBase) {
		this.isBase = isBase;
	}
	public Short getHasOthers() {
		return hasOthers;
	}
	public void setHasOthers(Short hasOthers) {
		this.hasOthers = hasOthers;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getGrpId() {
		return grpId;
	}
	public void setGrpId(String grpId) {
		this.grpId = grpId;
	}
	public String getHotelId() {
		return hotelId;
	}
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	
	
}