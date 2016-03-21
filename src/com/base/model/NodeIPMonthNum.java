package com.base.model;




public class NodeIPMonthNum extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String engName;
	private String month;//月份
	private int IPNum;//数量
	private String groupName;
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getEngName() {
		return engName;
	}
	public void setEngName(String engName) {
		this.engName = engName;
	}

	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getIPNum() {
		return IPNum;
	}
	public void setIPNum(int num) {
		IPNum = num;
	}
	

	
	
}
