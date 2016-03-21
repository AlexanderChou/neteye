package com.savi.base.model;

/**
 * Savibindingtablecur entity. @author MyEclipse Persistence Tools
 */

public class Savibindingtablecur implements java.io.Serializable {

	// Fields

	private Integer id;
	private Ifinterfacecur ifinterfacecur;
	private Integer ipAddressType;
	private Integer bindingType;
	private Integer ifIndex;
	private String ipAddress;
	private String macAddress;
	private Integer bindingState;
	private Integer bindingLifetime;
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
	public Savibindingtablecur() {
	}

	/** full constructor */
	public Savibindingtablecur(Ifinterfacecur ifinterfacecur,
			Integer ipAddressType, Integer bindingType, Integer ifIndex,
			String ipAddress, String macAddress, Integer bindingState,
			Integer bindingLifetime, Integer isInFilteringTable) {
		this.ifinterfacecur = ifinterfacecur;
		this.ipAddressType = ipAddressType;
		this.bindingType = bindingType;
		this.ifIndex = ifIndex;
		this.ipAddress = ipAddress;
		this.macAddress = macAddress;
		this.bindingState = bindingState;
		this.bindingLifetime = bindingLifetime;
		this.isInFilteringTable = isInFilteringTable;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Ifinterfacecur getIfinterfacecur() {
		return this.ifinterfacecur;
	}

	public void setIfinterfacecur(Ifinterfacecur ifinterfacecur) {
		this.ifinterfacecur = ifinterfacecur;
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

	public Integer getBindingState() {
		return this.bindingState;
	}

	public void setBindingState(Integer bindingState) {
		this.bindingState = bindingState;
	}

	public Integer getBindingLifetime() {
		return this.bindingLifetime;
	}

	public void setBindingLifetime(Integer bindingLifetime) {
		this.bindingLifetime = bindingLifetime;
	}

	public Integer getIsInFilteringTable() {
		return this.isInFilteringTable;
	}

	public void setIsInFilteringTable(Integer isInFilteringTable) {
		this.isInFilteringTable = isInFilteringTable;
	}

}