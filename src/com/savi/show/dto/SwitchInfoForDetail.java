package com.savi.show.dto;
/**
 * Switchcur entity. @author MyEclipse Persistence Tools
 */

public class SwitchInfoForDetail{
	// Fields

	private String switchName;
	private String subnetName;
	private int staticNum;
	private int slaacNum;
	private int dhcpNum;
	private String ipv4address;
	private String ipv6address;
	private int ipVersion;
	private String equipmentType;
	private String description;

	// Constructors
	/** default constructor */
	public SwitchInfoForDetail() {
	}

	/** full constructor */
	public SwitchInfoForDetail(String switchName,String subnetName,int staticNum,int slaacNum
			,int dhcpNum,String ipv4address,String ipv6address,int ipVersion,String equipmentType,
			String description) {
		this.switchName=switchName;
		this.subnetName=subnetName;
		this.staticNum=staticNum;
		this.slaacNum=slaacNum;
		this.dhcpNum=dhcpNum;
		this.ipv4address=ipv4address;
		this.ipv6address=ipv6address;
		this.ipVersion=ipVersion;
		this.equipmentType=equipmentType;
		this.description=description;
	}
	// Property accessors
	public String getSwitchName() {
		return switchName;
	}

	public void setSwitchName(String switchName) {
		this.switchName = switchName;
	}

	public String getSubnetName() {
		return subnetName;
	}

	public void setSubnetName(String subnetName) {
		this.subnetName = subnetName;
	}

	public int getStaticNum() {
		return staticNum;
	}

	public void setStaticNum(int staticNum) {
		this.staticNum = staticNum;
	}

	public int getSlaacNum() {
		return slaacNum;
	}

	public void setSlaacNum(int slaacNum) {
		this.slaacNum = slaacNum;
	}

	public int getDhcpNum() {
		return dhcpNum;
	}

	public void setDhcpNum(int dhcpNum) {
		this.dhcpNum = dhcpNum;
	}

	public String getIpv4address() {
		return ipv4address;
	}

	public void setIpv4address(String ipv4address) {
		this.ipv4address = ipv4address;
	}

	public String getIpv6address() {
		return ipv6address;
	}

	public void setIpv6address(String ipv6address) {
		this.ipv6address = ipv6address;
	}

	public int getIpVersion() {
		return ipVersion;
	}

	public void setIpVersion(int ipVersion) {
		this.ipVersion = ipVersion;
	}

	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}