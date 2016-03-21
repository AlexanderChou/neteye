package com.savi.show.dto;




/**
 * Switchbasicinfo entity. @author MyEclipse Persistence Tools
 */

public class SwitchInfo {

	// Fields

	private Long id;
	private String subnetName;
	private Long subnetId;
	private String name;
	private String equipmentType;
	private String ipv4address;
	private String ipv6address;
	private String description;
	private String snmpVersion;
	private String readCommunity;
	private String writeCommunity;
	private String authKey;
	private String privateKey;
	private Integer status;
	// Constructors

	/** default constructor */
	public SwitchInfo() {
	}
	public SwitchInfo(Long id,String name,String equipmentType,String ipv4address,String ipv6address,String subnetName,Long subnetId,Integer status,
			String description,String snmpVersion,String readCommunity,String writeCommunity,String authKey,
			String privateKey) {
		this.id = id;
		this.name = name;
		this.equipmentType = equipmentType;
		this.ipv4address = ipv4address;
		this.ipv6address = ipv6address;
		this.subnetName = subnetName;
		this.subnetId=subnetId;
		this.status = status;
		this.description=description;
		this.snmpVersion=snmpVersion;
		this.readCommunity=readCommunity;
		this.writeCommunity=writeCommunity;
		this.authKey=authKey;
		this.privateKey=privateKey;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEquipmentType() {
		return this.equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	public String getIpv4address() {
		return this.ipv4address;
	}

	public void setIpv4address(String ipv4address) {
		this.ipv4address = ipv4address;
	}

	public String getIpv6address() {
		return this.ipv6address;
	}

	public void setIpv6address(String ipv6address) {
		this.ipv6address = ipv6address;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSnmpVersion() {
		return this.snmpVersion;
	}

	public void setSnmpVersion(String snmpVersion) {
		this.snmpVersion = snmpVersion;
	}

	public String getReadCommunity() {
		return this.readCommunity;
	}

	public void setReadCommunity(String readCommunity) {
		this.readCommunity = readCommunity;
	}

	public String getWriteCommunity() {
		return this.writeCommunity;
	}

	public void setWriteCommunity(String writeCommunity) {
		this.writeCommunity = writeCommunity;
	}

	public String getAuthKey() {
		return this.authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public String getPrivateKey() {
		return this.privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSubnetName() {
		return subnetName;
	}
	public void setSubnetName(String subnetName) {
		this.subnetName = subnetName;
	}
	public Long getSubnetId() {
		return subnetId;
	}
	public void setSubnetId(Long subnetId) {
		this.subnetId = subnetId;
	}
}