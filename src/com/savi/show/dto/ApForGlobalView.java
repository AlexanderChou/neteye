package com.savi.show.dto;

public class ApForGlobalView {

	private Integer apid;
	private String apname;
	private String ipAddress;
	private String macAddress;


	private int userNum;
	
	public ApForGlobalView(){
		
	}



	public Integer getApid() {
		return apid;
	}



	public void setApid(Integer apid) {
		this.apid = apid;
	}



	public String getApname() {
		return apname;
	}

	public void setApname(String apname) {
		this.apname = apname;
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



	public int getUserNum() {
		return userNum;
	}

	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}
	
	
	
}
