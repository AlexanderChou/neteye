package com.savi.base.model;

import java.util.LinkedHashSet;
import java.util.Set;


public class Deviceinfo implements java.io.Serializable {

	// Fields

	private Long id;
	private String name;
	private Integer equipmentType;
	private String ipv4address;
	private String ipv6address;
	private String description;
	private String snmpVersion;
	private String readCommunity;
	private String writeCommunity;
	private String authKey;
	private String privateKey;
	private Integer status;
	private Integer isDelete;
	
	private Set<Apinfo> apinfos = new LinkedHashSet<Apinfo>();
	
	// Constructors
	/** default constructor */
	public Deviceinfo() {
	}
	public Deviceinfo(Deviceinfo s) {
		this.id = s.getId();
		this.name = s.getName();
		this.equipmentType = s.getEquipmentType();
		this.ipv4address = s.getIpv4address();
		this.ipv6address = s.getIpv6address();
		this.description = s.getDescription();
		this.snmpVersion = s.getSnmpVersion();
		this.readCommunity = s.getReadCommunity();
		this.writeCommunity = s.getWriteCommunity();
		this.authKey = s.getAuthKey();
		this.privateKey = s.getPrivateKey();
		this.status = s.getStatus();
		this.isDelete = s.getIsDelete();
		this.apinfos=s.getApinfos();
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



	public Integer getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(Integer equipmentType) {
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

	public Integer getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public Set<Apinfo> getApinfos() {
		return apinfos;
	}
	public void setApinfos(Set<Apinfo> apinfos) {
		this.apinfos = apinfos;
	}
	

}