package com.base.model;



import com.base.model.BaseEntity;

public class NodeIPHourNum extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String engName;
	private String hour;//日期-小时
	private int IPNum;//数量
	private String chineseName;
	private String groupName;
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getEngName() {
		return engName;
	}
	public void setEngName(String engName) {
		this.engName = engName;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public int getIPNum() {
		return IPNum;
	}
	public void setIPNum(int num) {
		IPNum = num;
	}

	
	
}
