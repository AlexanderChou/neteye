package com.savi.base.model;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * Ifinterfacehis entity. @author MyEclipse Persistence Tools
 */

public class Ifinterfacehis implements java.io.Serializable {

	// Fields

	private Long id;
	private Switchhis switchhis;
	private Integer ipVersion;
	private Integer ifIndex;
	private Integer ifValidationStatus;
	private Integer ifTrustStatus;
	private Long ifFilteringNum;
	private Set<User> users = new LinkedHashSet<User>();
	private Set<Savibindingtablehis> savibindingtablehises = new LinkedHashSet<Savibindingtablehis>();

	// Constructors
	/** default constructor */
	public Ifinterfacehis() {
	}

	/** full constructor */
	public Ifinterfacehis(Switchhis switchhis, Integer ipVersion,
			Integer ifIndex, Integer ifValidationStatus, Integer ifTrustStatus,
			Long ifFilteringNum, Set users, Set savibindingtablehises) {
		this.switchhis = switchhis;
		this.ipVersion = ipVersion;
		this.ifIndex = ifIndex;
		this.ifValidationStatus = ifValidationStatus;
		this.ifTrustStatus = ifTrustStatus;
		this.ifFilteringNum = ifFilteringNum;
		this.users = users;
		this.savibindingtablehises = savibindingtablehises;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Switchhis getSwitchhis() {
		return this.switchhis;
	}

	public void setSwitchhis(Switchhis switchhis) {
		this.switchhis = switchhis;
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
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	@JSON(serialize=false)
	public Set<Savibindingtablehis> getSavibindingtablehises() {
		return savibindingtablehises;
	}

	public void setSavibindingtablehises(
			Set<Savibindingtablehis> savibindingtablehises) {
		this.savibindingtablehises = savibindingtablehises;
	}


}