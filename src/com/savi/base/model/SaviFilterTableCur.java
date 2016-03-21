package com.savi.base.model;



public class SaviFilterTableCur implements java.io.Serializable {

	// Fields

	private Long id;
	private Long apid;
	private Integer ipAddressType;
	private Integer serviceName;
	private String ipAddress;
	private String macAddress;
	private String userName;
	private String SSIDName;
	private Long startTime;
	private Long endTime;
	private String apName;
	
	
	// Constructors
	/** default constructor */
	public SaviFilterTableCur() {
	}
	public SaviFilterTableCur(SaviFilterTableCur s) {
		this.id = s.getId();
		this.apid = s.getApid();
		this.ipAddressType = s.getIpAddressType();
		this.serviceName = s.getServiceName();
		this.ipAddress = s.getIpAddress();
		this.macAddress = s.getMacAddress();
		this.userName = s.getUserName();
		this.SSIDName = s.getSSIDName();
		this.startTime = s.getStartTime();
		this.endTime=s.getEndTime();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getApid() {
		return apid;
	}
	public void setApid(Long apid) {
		this.apid = apid;
	}
	public Integer getIpAddressType() {
		return ipAddressType;
	}
	public void setIpAddressType(Integer ipAddressType) {
		this.ipAddressType = ipAddressType;
	}
	public Integer getServiceName() {
		return serviceName;
	}
	public void setServiceName(Integer serviceName) {
		this.serviceName = serviceName;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	public String getSSIDName() {
		return SSIDName;
	}
	public void setSSIDName(String sSIDName) {
		SSIDName = sSIDName;
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
	public String getApName() {
		return apName;
	}
	public void setApName(String apName) {
		this.apName = apName;
	}
	
	
	

}