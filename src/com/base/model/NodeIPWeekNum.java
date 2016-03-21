package com.base.model;



import com.base.model.BaseEntity;

public class NodeIPWeekNum extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String engName;
	private String week;//日期-小时
	private int IPNum;//数量
	private String groupName;
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getEngName() {
		return engName;
	}
	public void setEngName(String engName) {
		this.engName = engName;
	}
	public int getIPNum() {
		return IPNum;
	}
	public void setIPNum(int num) {
		IPNum = num;
	}
	

	
	
}
