package com.savi.base.model;

/**
 * Savibindingtablehis entity. @author MyEclipse Persistence Tools
 */

public class Savibindingtablehis implements java.io.Serializable {

	// Fields

	private Long id;
	private Ifinterfacehis ifinterfacehis;
	private Integer ipAddressType;
	private Integer bindingType;
	private Integer ifIndex;
	private String ipAddress;
	private String macAddress;
	private Long startTime;
	private Long endTime;
	private Integer status;
	private Integer isInFilteringTable;
	private String userName;
	// Constructors

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/** default constructor */
	public Savibindingtablehis() {
	}
	
	public Savibindingtablehis(Long startTime) {
		this.startTime = startTime;
	}
	
	public Savibindingtablehis(Integer bindingType,Long startTime,Long endTime){
		this.bindingType = bindingType;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/** full constructor */
	public Savibindingtablehis(Ifinterfacehis ifinterfacehis,
			Integer ipAddressType, Integer bindingType, Integer ifIndex,
			String ipAddress, String macAddress, Long startTime, Long endTime,
			Integer status, Integer isInFilteringTable) {
		this.ifinterfacehis = ifinterfacehis;
		this.ipAddressType = ipAddressType;
		this.bindingType = bindingType;
		this.ifIndex = ifIndex;
		this.ipAddress = ipAddress;
		this.macAddress = macAddress;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.isInFilteringTable = isInFilteringTable;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Ifinterfacehis getIfinterfacehis() {
		return this.ifinterfacehis;
	}

	public void setIfinterfacehis(Ifinterfacehis ifinterfacehis) {
		this.ifinterfacehis = ifinterfacehis;
	}

	public Integer getIpAddressType() {
		return this.ipAddressType;
	}

	public void setIpAddressType(Integer ipAddressType) {
		this.ipAddressType = ipAddressType;
	}

	public Integer getBindingType() {
		return this.bindingType;
	}

	public void setBindingType(Integer bindingType) {
		this.bindingType = bindingType;
	}

	public Integer getIfIndex() {
		return this.ifIndex;
	}

	public void setIfIndex(Integer ifIndex) {
		this.ifIndex = ifIndex;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getMacAddress() {
		return this.macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public Long getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsInFilteringTable() {
		return this.isInFilteringTable;
	}

	public void setIsInFilteringTable(Integer isInFilteringTable) {
		this.isInFilteringTable = isInFilteringTable;
	}

}