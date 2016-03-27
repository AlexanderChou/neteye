package com.savi.show.dto;

public class PreHuaSanUserInfo {
	private Long id;
	private Integer ipAddressType;
	private String ipAddress;
	private Long startTime;
	private Long endTime;
	private Integer status;
	private String apName;
	private String userName;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public PreHuaSanUserInfo(Long id,Integer ipAddressType,String userName,String ipAddress,Long startTime,Long endTime,Integer status,String apName){
		this.id=id;
		this.ipAddressType=ipAddressType;
		this.ipAddress=ipAddress;
		this.startTime=startTime;
		this.endTime=endTime;
		this.status=status;
		this.apName=apName;
		this.userName=userName;
	}
	public PreHuaSanUserInfo(){}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getIpAddressType() {
		return ipAddressType;
	}
	public void setIpAddressType(Integer ipAddressType) {
		this.ipAddressType = ipAddressType;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getApName() {
		return apName;
	}
	public void setApName(String apName) {
		this.apName = apName;
	}

}
