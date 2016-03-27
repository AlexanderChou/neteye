package com.savi.show.dto;

import com.savi.base.model.Ifinterfacecur;
public class InterfaceInfo {
	private Long id;
	private Integer ipVersion;
	private Integer ifIndex;
	private Integer ifValidationStatus;
	private Integer ifTrustStatus;
	private Long maxFilteringNum;
	private Long ifFilteringNum;
	private Integer userNumber;

	public InterfaceInfo(){
		
	}
	
	public InterfaceInfo(Ifinterfacecur interfacecur, Integer userNumber) {
		this.id = interfacecur.getId();
		this.ipVersion = interfacecur.getIpVersion();
		this.ifIndex = interfacecur.getIfIndex();
		this.ifValidationStatus = interfacecur.getIfValidationStatus();
		this.ifTrustStatus = interfacecur.getIfTrustStatus();
		this.ifFilteringNum = interfacecur.getIfFilteringNum();
		this.userNumber = userNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIpVersion() {
		return ipVersion;
	}

	public void setIpVersion(Integer ipVersion) {
		this.ipVersion = ipVersion;
	}

	public Integer getIfIndex() {
		return ifIndex;
	}

	public void setIfIndex(Integer ifIndex) {
		this.ifIndex = ifIndex;
	}

	public Integer getIfValidationStatus() {
		return ifValidationStatus;
	}

	public void setIfValidationStatus(Integer ifValidationStatus) {
		this.ifValidationStatus = ifValidationStatus;
	}

	public Integer getIfTrustStatus() {
		return ifTrustStatus;
	}

	public void setIfTrustStatus(Integer ifTrustStatus) {
		this.ifTrustStatus = ifTrustStatus;
	}

	public Long getIfFilteringNum() {
		return ifFilteringNum;
	}

	public void setIfFilteringNum(Long ifFilteringNum) {
		this.ifFilteringNum = ifFilteringNum;
	}

	public Integer getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(Integer userNumber) {
		this.userNumber = userNumber;
	}

	public Long getMaxFilteringNum() {
		return maxFilteringNum;
	}

	public void setMaxFilteringNum(Long maxFilteringNum) {
		this.maxFilteringNum = maxFilteringNum;
	}
}
