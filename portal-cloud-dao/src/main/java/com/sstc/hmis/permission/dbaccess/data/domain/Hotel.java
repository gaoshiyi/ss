package com.sstc.hmis.permission.dbaccess.data.domain;

import java.util.List;

public class Hotel {
	
    private String id;

    private String name;

    private String pid;
    
    private List<Hotel> hotelList;	
	
	private List<Dept> departmentList;
    
	public List<Hotel> getHotelList() {
		return hotelList;
	}

	public void setHotelList(List<Hotel> hotelList) {
		this.hotelList = hotelList;
	}

	public List<Dept> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<Dept> departmentList) {
		this.departmentList = departmentList;
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