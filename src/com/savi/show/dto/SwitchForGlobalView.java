package com.savi.show.dto;


public class SwitchForGlobalView{

	// Fields

	private Long switchBasicInfoId;
	private Integer ipVersion;
	private Integer systemMode;
	private int userNum;
	// Constructors
	/** default constructor */
	public SwitchForGlobalView() {
	}

	/** full constructor */

	public Long getSwitchBasicInfoId() {
		return switchBasicInfoId;
	}
	public void setSwitchBasicInfoId(Long switchBasicInfoId) {
		this.switchBasicInfoId = switchBasicInfoId;
	}
	public Integer getIpVersion() {
		return ipVersion;
	}
	public void setIpVersion(Integer ipVersion) {
		this.ipVersion = ipVersion;
	}
	public Integer getSystemMode() {
		return systemMode;
	}
	public void setSystemMode(Integer systemMode) {
		this.systemMode = systemMode;
	}
	public int getUserNum() {
		return userNum;
	}
	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}
}