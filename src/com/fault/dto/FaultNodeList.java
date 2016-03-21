package com.fault.dto;

import java.util.List;




public class FaultNodeList {
	private long id;
	private String name;
	private String nodestutas;
	private String count;
	private List<FaultNode> faultnodelist;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public List<FaultNode> getFaultnodelist() {
		return faultnodelist;
	}
	public void setFaultnodelist(List<FaultNode> faultnodelist) {
		this.faultnodelist = faultnodelist;
	}
	public void setNodestutas(String nodestutas) {
		this.nodestutas = nodestutas;
	}
	public String getNodestutas() {
		return nodestutas;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getId() {
		return id;
	}
}
