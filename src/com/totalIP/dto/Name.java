package com.totalIP.dto;

public class Name {
	private String engName;
	private String chineseName;
	private String groupId;
	public String getGroupId() {
		return groupId;
	}
	public Name setGroupId(String groupId) {
		this.groupId = groupId;
		return this;
	}
	public String getEngName() {
		return engName;
	}
	public Name setEngName(String engName) {
		this.engName = engName;
		return this;
	}
	public String getChineseName() {
		return chineseName;
	}
	public Name setChineseName(String chineseName) {
		this.chineseName = chineseName;
		return this;
	}
	
}
