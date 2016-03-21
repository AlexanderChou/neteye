package com.base.model;



import com.base.model.BaseEntity;

public class NodeIPYearNum extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String engName;
	private String hour;//日期-小时
	private String IPHourNum;//数量
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
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getIPHourNum() {
		return IPHourNum;
	}
	public void setIPHourNum(String hourNum) {
		IPHourNum = hourNum;
	}

	
	
}
