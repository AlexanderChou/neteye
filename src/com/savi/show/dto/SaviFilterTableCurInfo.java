package com.savi.show.dto;

import java.text.SimpleDateFormat;
import java.util.Date;



public class SaviFilterTableCurInfo implements java.io.Serializable {

	// Fields

	private String ipAddress;
	private String userName;
	private String startTime;
	private String apname;
	private String macAddress;
	// Constructors
	/** default constructor */
	public SaviFilterTableCurInfo() {
	}
	
	public SaviFilterTableCurInfo(String userName,String ipAddress,Long startTime,String apname,String macAddress){
		this.ipAddress=ipAddress;
		Date d = new Date(startTime);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.startTime = df.format(d);
		this.apname=apname;
		this.userName=userName;
		this.macAddress = macAddress;
	}
	
	
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	

	

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getApname() {
		return apname;
	}

	public void setApname(String apname) {
		this.apname = apname;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	
	
	

}