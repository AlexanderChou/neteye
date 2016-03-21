package com.base.model;



import com.base.model.BaseEntity;

public class NodeIPDayNum extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String engName;
	private String day;//日期-小时
	private int IPNum;//数量
	private String chineseName;
	private String groupName;
	
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
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
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	

	
	
}
