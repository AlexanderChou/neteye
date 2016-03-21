package com.savi.base.model;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * Switchcur entity. @author MyEclipse Persistence Tools
 */

public class Switchcur implements java.io.Serializable {

	// Fields

	private Long id;
	private Switchbasicinfo switchbasicinfo;
	private Integer ipVersion;
	private Integer systemMode;
	private Integer maxDadDelay;
	private Integer maxDadPrepareDelay;
	private Integer staticNum;
	private Integer slaacNum;
	private Integer dhcpNum;
	private Set<Ifinterfacecur> ifInterfaceCurs = new LinkedHashSet<Ifinterfacecur>();
	// Constructors

	/** default constructor */
	public Switchcur() {
	}

	/** full constructor */
	public Switchcur(Switchbasicinfo switchbasicinfo, Integer ipVersion,
			Integer systemMode, Integer maxDadDelay,
			Integer maxDadPrepareDelay, Integer staticNum, Integer slaacNum,
			Integer dhcpNum) {
		this.switchbasicinfo = switchbasicinfo;
		this.ipVersion = ipVersion;
		this.systemMode = systemMode;
		this.maxDadDelay = maxDadDelay;
		this.maxDadPrepareDelay = maxDadPrepareDelay;
		this.staticNum = staticNum;
		this.slaacNum = slaacNum;
		this.dhcpNum = dhcpNum;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Switchbasicinfo getSwitchbasicinfo() {
		return this.switchbasicinfo;
	}

	public void setSwitchbasicinfo(Switchbasicinfo switchbasicinfo) {
		this.switchbasicinfo = switchbasicinfo;
	}

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

	public Integer getStaticNum() {
		return this.staticNum;
	}

	public void setStaticNum(Integer staticNum) {
		this.staticNum = staticNum;
	}

	public Integer getSlaacNum() {
		return this.slaacNum;
	}

	public void setSlaacNum(Integer slaacNum) {
		this.slaacNum = slaacNum;
	}

	public Integer getDhcpNum() {
		return this.dhcpNum;
	}

	public void setDhcpNum(Integer dhcpNum) {
		this.dhcpNum = dhcpNum;
	}
	@JSON(serialize=false)
	public Set<Ifinterfacecur> getIfInterfaceCurs() {
		return ifInterfaceCurs;
	}
	public void setIfInterfaceCurs(Set<Ifinterfacecur> ifInterfaceCurs) {
		this.ifInterfaceCurs = ifInterfaceCurs;
	}
}