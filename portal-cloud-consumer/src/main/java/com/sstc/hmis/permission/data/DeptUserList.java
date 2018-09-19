package com.sstc.hmis.permission.data;

/**
 * 子部门(班组)人员列表
 * @author Lrongrong
 *
 */
public class DeptUserList {

	/**
	 * 人员ID
	 */
	private String userId;
	
	/**
	 * 人员姓名
	 */
	private String userName;
	
	/**
	 * 职位
	 */
	private String job;
	
	/**
	 * 手机号
	 */
	private String phone;
	
	/**
	 * 头像URL地址
	 */
	private String avatar;

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the job
	 */
	public String getJob() {
		return job;
	}

	/**
	 * @param job the job to set
	 */
	public void setJob(String job) {
		this.job = job;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the avatar
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * @param avatar the avatar to set
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
