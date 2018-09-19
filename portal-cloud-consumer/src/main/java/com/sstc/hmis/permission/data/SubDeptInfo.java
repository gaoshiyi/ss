package com.sstc.hmis.permission.data;

import java.util.List;

/**
 * 部门的子部门列表（班组列表）
 * @author Lrongrong
 *
 */
public class SubDeptInfo {

	/**
	 * 子部门(班组)列表
	 */
	private List<GroupInfo> sublist;
	
	/**
	 * 子部门领导列表
	 */
	private List<DeptUserList> leaderlist;

	/**
	 * @return the sublist
	 */
	public List<GroupInfo> getSublist() {
		return sublist;
	}

	/**
	 * @param sublist the sublist to set
	 */
	public void setSublist(List<GroupInfo> sublist) {
		this.sublist = sublist;
	}

	/**
	 * @return the leaderlist
	 */
	public List<DeptUserList> getLeaderlist() {
		return leaderlist;
	}

	/**
	 * @param leaderlist the leaderlist to set
	 */
	public void setLeaderlist(List<DeptUserList> leaderlist) {
		this.leaderlist = leaderlist;
	}
}
