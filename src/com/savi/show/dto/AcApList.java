package com.savi.show.dto;

import java.util.List;

import com.savi.base.model.Apinfo;




public class AcApList {
	private long id;
	private String name;
	private String status="1";
	private String nodecount;
	private String filtercount;
   	
	private List<Apinfo> aplist;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

	

	public String getFiltercount() {
		return filtercount;
	}
	public void setFiltercount(String filtercount) {
		this.filtercount = filtercount;
	}
	public String getNodecount() {
		return nodecount;
	}
	public void setNodecount(String nodecount) {
		this.nodecount = nodecount;
	}
	public List<Apinfo> getAplist() {
		return aplist;
	}
	public void setAplist(List<Apinfo> aplist) {
		this.aplist = aplist;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getId() {
		return id;
	}
}
