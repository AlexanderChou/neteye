package com.savi.base.model;



public class SaviFilterTableHis implements java.io.Serializable {

	// Fields

	private Long id;
	
	//private Long apid;
	private Apinfo apinfo;
	
	
	private Integer ipAddressType;
	private Integer serviceName;
	private String ipAddress;
	private String macAddress;
	private String userName;
	private String SSIDName;
	private Long startTime;
	private Long endTime;
	private Integer status;
	
	
	// Constructors
	/** default constructor */
	public SaviFilterTableHis() {
	}
	public SaviFilterTableHis(SaviFilterTableHis s) {
		this.id = s.getId();
		this.apinfo = s.getApinfo();
		this.ipAddressType = s.getIpAddressType();
		this.serviceName = s.getServiceName();
		this.ipAddress = s.getIpAddress();
		this.macAddress = s.getMacAddress();
		this.userName = s.getUserName();
		this.SSIDName = s.getSSIDName();
		this.startTime = s.getStartTime();
		this.endTime=s.getEndTime();
		this.status=s.getStatus();
	}
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
	
	
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Apinfo getApinfo() {
		return apinfo;
	}
	public void setApinfo(Apinfo apinfo) {
		this.apinfo = apinfo;
	}
	
	
	
	

}