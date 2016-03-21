package com.savi.show.dto;



public class HuaSanSaviFilterTableCurInfo{

	// Fields

	private Long id;
	private String apName;
	private String serviceName;
	private String ipAddress;
	private String macAddress;
	private String userName;
	private Long count;
	private Integer ipAddressType;
	
	
	
	

	public HuaSanSaviFilterTableCurInfo(Long id,String apName,String serviceName,String ipAddress,String macAddress,String userName,Integer ipAddressType,Long count) {
		this.id = id;
		this.apName =apName;
		this.serviceName = serviceName;
		this.ipAddress = ipAddress;
		this.macAddress = macAddress;
		this.userName = userName;
		this.ipAddressType=ipAddressType;
		this.count = count;
		
	}
	
	public HuaSanSaviFilterTableCurInfo() {
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
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
	
	public String getApName() {
		return apName;
	}
	public void setApName(String apName) {
		this.apName = apName;
	}


	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Integer getIpAddressType() {
		return ipAddressType;
	}

	public void setIpAddressType(Integer ipAddressType) {
		this.ipAddressType = ipAddressType;
	}
	
	
	
	

}