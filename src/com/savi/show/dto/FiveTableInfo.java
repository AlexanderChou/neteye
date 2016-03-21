package com.savi.show.dto;

import java.text.SimpleDateFormat;
import java.util.Date;



public class FiveTableInfo {
	private Long id;
	private Integer ifIndex;
	private String ipAddress;
	private String macAddress;
	private String userName;
	private Long startTime;
	private String startTimeString;
	private String ipv4address;//交换机地址
	public FiveTableInfo(){
		
	}
	
	public FiveTableInfo(Long id, String userName, String ipAddress,String macAddress,Integer ifIndex,Long startTime,String ipv4address){
		this.id = id;
		this.ipAddress = ipAddress;
		this.ifIndex = ifIndex;
		this.macAddress = macAddress;
		this.userName = userName;
		this.startTime = startTime;
		Date date = new Date(this.startTime.longValue());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
		this.startTimeString = sdf.format(date);
		this.ipv4address = ipv4address;

	}
	
	
	
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIfIndex() {
		return ifIndex;
	}

	public void setIfIndex(Integer ifIndex) {
		this.ifIndex = ifIndex;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getStartTimeString() {
		return startTimeString;
	}

	public void setStartTimeString(String startTimeString) {
		this.startTimeString = startTimeString;
	}

	public String getIpv4address() {
		return ipv4address;
	}

	public void setIpv4address(String ipv4address) {
		this.ipv4address = ipv4address;
	}
	
	
}
