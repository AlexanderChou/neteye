package com.totalIP.dto;

public class Node {
	private String engName;
	private String chineseName;
	private String IPv4;
	private String IPv6;
	private String dayNum;//节点前一天出现的IP地址数
	private String totalNum;//节点截止到目前出现的IP地址数
	
	public String getEngName() {
		return engName;
	}
	public void setEngName(String engName) {
		this.engName = engName;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getIPv4() {
		return IPv4;
	}
	public void setIPv4(String pv4) {
		IPv4 = pv4;
	}
	public String getIPv6() {
		return IPv6;
	}
	public void setIPv6(String pv6) {
		IPv6 = pv6;
	}
	public String getDayNum() {
		return dayNum;
	}
	public void setDayNum(String dayNum) {
		this.dayNum = dayNum;
	}
	public String getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}
}
