package com.savi.statistic.dto;

import java.util.Date;

public class UserInfo {
	private String userName;
	private String userMAC;
	private String userIP;
	private Integer ifIndex;
	private String switchName;
	private String switchIPv4;
	private String switchIPv6;
	private String startTime;
	private String endTime;
	private Integer bindingType;
	@SuppressWarnings({"deprecation"})
	public UserInfo(String userName,String userMAC,String userIP,Integer ifIndex,
			String switchName,String switchIPv4,String switchIPv6,Long startTime,
			Long endTime,Integer bindingType){
		this.userName=userName;
		this.userMAC=userMAC;
		this.userIP=userIP;
		this.ifIndex=ifIndex;
		this.switchName=switchName;
		this.switchIPv4=switchIPv4;
		this.switchIPv6=switchIPv6;
		this.bindingType=bindingType;
		if(startTime!=null){
			this.startTime=new Date(startTime).toLocaleString();
		}
		if(endTime!=null){
			this.endTime=new Date(endTime).toLocaleString();
		}
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserMAC() {
		return userMAC;
	}
	public void setUserMAC(String userMAC) {
		this.userMAC = userMAC;
	}
	public String getUserIP() {
		return userIP;
	}
	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}
	public Integer getIfIndex() {
		return ifIndex;
	}
	public void setIfIndex(Integer ifIndex) {
		this.ifIndex = ifIndex;
	}
	public String getSwitchName() {
		return switchName;
	}
	public void setSwitchName(String switchName) {
		this.switchName = switchName;
	}
	public String getSwitchIPv4() {
		return switchIPv4;
	}
	public void setSwitchIPv4(String switchIPv4) {
		this.switchIPv4 = switchIPv4;
	}
	public String getSwitchIPv6() {
		return switchIPv6;
	}
	public void setSwitchIPv6(String switchIPv6) {
		this.switchIPv6 = switchIPv6;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getBindingType() {
		return bindingType;
	}
	public void setBindingType(Integer bindingType) {
		this.bindingType = bindingType;
	}
}
