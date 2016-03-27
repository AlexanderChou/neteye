package com.savi.base.model;

/**
 * SwitchSubnet entity. @author MyEclipse Persistence Tools
 */

public class SwitchSubnet implements java.io.Serializable {

	// Fields

	private Long id;
	private Subnet subnet;
	private Switchbasicinfo switchbasicinfo;

	// Constructors

	/** default constructor */
	public SwitchSubnet() {
	}

	/** full constructor */
	public SwitchSubnet(Subnet subnet, Switchbasicinfo switchbasicinfo) {
		this.subnet = subnet;
		this.switchbasicinfo = switchbasicinfo;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Subnet getSubnet() {
		return this.subnet;
	}

	public void setSubnet(Subnet subnet) {
		this.subnet = subnet;
	}

	public Switchbasicinfo getSwitchbasicinfo() {
		return this.switchbasicinfo;
	}

	public void setSwitchbasicinfo(Switchbasicinfo switchbasicinfo) {
		this.switchbasicinfo = switchbasicinfo;
	}

}