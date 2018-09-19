package com.sstc.hmis.permission.data;

/**
 * 个性化设置Entity
 * @author Qxiaoxiang
 *
 */
public class PersonalisationEntity {

	/**
	 * 主键
	 */
	private String id;
	
	/**
	 * 用户ID
	 */
	private String userId;
	
	/**
	 * key
	 */
	private String key;
	
	/**
	 * value
	 */
	private String value;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
