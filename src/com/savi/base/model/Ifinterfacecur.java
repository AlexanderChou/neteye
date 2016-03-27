package com.savi.base.model;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * Ifinterfacecur entity. @author MyEclipse Persistence Tools
 */

public class Ifinterfacecur implements java.io.Serializable {

	// Fields

	private Long id;
	private Switchcur switchcur;
	private Integer ipVersion;
	private Integer ifIndex;
	private Integer ifValidationStatus;
	private Integer ifTrustStatus;
	private Long ifFilteringNum;
	private Set<Savibindingtablecur> saviBindingTableCurs = new LinkedHashSet<Savibindingtablecur>();
	// Constructors

	/** default constructor */
	public Ifinterfacecur() {
	}
  
	public Ifinterfacecur(Integer ifIndex){
		this.ifIndex = ifIndex;
	}
	
	/** full constructor */
	public Ifinterfacecur(Switchcur switchcur, Integer ipVersion,
			Integer ifIndex, Integer ifValidationStatus, Integer ifTrustStatus,
			Long ifFilteringNum) {
		this.switchcur = switchcur;
		this.ipVersion = ipVersion;
		this.ifIndex = ifIndex;
		this.ifValidationStatus = ifValidationStatus;
		this.ifTrustStatus = ifTrustStatus;
		this.ifFilteringNum = ifFilteringNum;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Switchcur getSwitchcur() {
		return this.switchcur;
	}

	public void setSwitchcur(Switchcur switchcur) {
		this.switchcur = switchcur;
	}

	public Integer getIpVersion() {
		return this.ipVersion;
	}

	public void setIpVersion(Integer ipVersion) {
		this.ipVersion = ipVersion;
	}

	public Integer getIfIndex() {
		return this.ifIndex;
	}

	public void setIfIndex(Integer ifIndex) {
		this.ifIndex = ifIndex;
	}

	public Integer getIfValidationStatus() {
		return this.ifValidationStatus;
	}

	public void setIfValidationStatus(Integer ifValidationStatus) {
		this.ifValidationStatus = ifValidationStatus;
	}

	public Integer getIfTrustStatus() {
		return this.ifTrustStatus;
	}

	public void setIfTrustStatus(Integer ifTrustStatus) {
		this.ifTrustStatus = ifTrustStatus;
	}

	public Long getIfFilteringNum() {
		return this.ifFilteringNum;
	}

	public void setIfFilteringNum(Long ifFilteringNum) {
		this.ifFilteringNum = ifFilteringNum;
	}
	@JSON(serialize=false)
	public Set<Savibindingtablecur> getSaviBindingTableCurs() {
		return saviBindingTableCurs;
	}

	public void setSaviBindingTableCurs(
			Set<Savibindingtablecur> saviBindingTableCurs) {
		this.saviBindingTableCurs = saviBindingTableCurs;
	}
}