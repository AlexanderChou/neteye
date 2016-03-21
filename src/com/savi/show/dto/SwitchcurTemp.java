package com.savi.show.dto;

/**
 * Switchcur entity. @author MyEclipse Persistence Tools
 */

public class SwitchcurTemp{
	// Fields
	private Integer ipVersion;
	private Integer systemMode;
	private Integer maxDadDelay;
	private Integer maxDadPrepareDelay;
	private Long switchId;
	private String switchName;
	// Constructors

	/** default constructor */
	public SwitchcurTemp() {
	}

	/** full constructor */
	public SwitchcurTemp(Integer ipVersion,Integer systemMode, Integer maxDadDelay,
			Integer maxDadPrepareDelay,Long switchId,String switchName) {
		this.ipVersion = ipVersion;
		this.systemMode = systemMode;
		this.maxDadDelay = maxDadDelay;
		this.maxDadPrepareDelay = maxDadPrepareDelay;
		this.switchId=switchId;
		this.switchName=switchName;
	}

	// Property accessors
	public Integer getIpVersion() {
		return this.ipVersion;
	}

	public void setIpVersion(Integer ipVersion) {
		this.ipVersion = ipVersion;
	}

	public Integer getSystemMode() {
		return this.systemMode;
	}

	public void setSystemMode(Integer systemMode) {
		this.systemMode = systemMode;
	}

	public Integer getMaxDadDelay() {
		return this.maxDadDelay;
	}

	public void setMaxDadDelay(Integer maxDadDelay) {
		this.maxDadDelay = maxDadDelay;
	}

	public Integer getMaxDadPrepareDelay() {
		return this.maxDadPrepareDelay;
	}

	public void setMaxDadPrepareDelay(Integer maxDadPrepareDelay) {
		this.maxDadPrepareDelay = maxDadPrepareDelay;
	}
	public Long getSwitchId() {
		return switchId;
	}

	public void setSwitchId(Long switchId) {
		this.switchId = switchId;
	}

	public String getSwitchName() {
		return switchName;
	}

	public void setSwitchName(String switchName) {
		this.switchName = switchName;
	}
}