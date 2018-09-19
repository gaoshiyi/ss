package com.sstc.hmis.permission.data;

/**
 * 审批对象Entity
 * @author Qxiaoxiang
 *
 */
public class ApproveEntity {

	/**
	 * 主键ID
	 */
	private String id;
	
	/**
	 * 生成时间
	 */
	private String date;
	
	/**
	 * 类型编码
	 */
	private String documentNo;
	
	/**
	 * 模版名称
	 */
	private String wfPrfName;
	
	/**
	 * 审批策略
	 */
	private String type;
	
	/**
	 * 审批对象个数
	 */
	private int count;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getWfPrfName() {
		return wfPrfName;
	}

	public void setWfPrfName(String wfPrfName) {
		this.wfPrfName = wfPrfName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
