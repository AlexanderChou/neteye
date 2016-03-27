package com.savi.base.model;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	private Long id;
	private Ifinterfacehis ifinterfacehis;
	private String name;
	private Integer ipVersion;
	private String ipAddress;
	private Long startTime;
	private Long endTime;
	private Integer status;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** full constructor */
	public User(Ifinterfacehis ifinterfacehis, String name, Integer ipVersion,
			String ipAddress, Long startTime, Long endTime, Integer status) {
		this.ifinterfacehis = ifinterfacehis;
		this.name = name;
		this.ipVersion = ipVersion;
		this.ipAddress = ipAddress;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Ifinterfacehis getIfinterfacehis() {
		return this.ifinterfacehis;
	}

	public void setIfinterfacehis(Ifinterfacehis ifinterfacehis) {
		this.ifinterfacehis = ifinterfacehis;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIpVersion() {
		return this.ipVersion;
	}

	public void setIpVersion(Integer ipVersion) {
		this.ipVersion = ipVersion;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Long getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}