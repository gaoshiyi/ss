package com.sstc.hmis.permission.dbaccess.data.domain;

import java.util.List;

public class Dept {
	
    private String id;

    private String name;

    private String pid;
    
    private List<Dept> departmentList;
	
	private List<Post> postList;
    
	public List<Dept> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<Dept> departmentList) {
		this.departmentList = departmentList;
	}

	public List<Post> getPostList() {
		return postList;
	}

	public void setPostList(List<Post> postList) {
		this.postList = postList;
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

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
 
}