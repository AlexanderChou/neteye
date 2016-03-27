package com.savi.show.dto;


/**
 * Subnet entity. @author MyEclipse Persistence Tools
 */

public class SubnetTemp {

	// Fields

	private Long id;
	private String name;
	private String ipv4subNet;
	private String ipv6subNet;
	private Integer isDelete;
	// Constructors

	/** default constructor */
	public SubnetTemp() {
	}
	/** full constructor */
	public SubnetTemp(Long id,String name, String ipv4subNet, String ipv6subNet,Integer isDelete) {
		this.id = id;
		this.name = name;
		this.ipv4subNet = ipv4subNet;
		this.ipv6subNet = ipv6subNet;
		this.isDelete = isDelete;
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

	public String getIpv4subNet() {
		return this.ipv4subNet;
	}

	public void setIpv4subNet(String ipv4subNet) {
		this.ipv4subNet = ipv4subNet;
	}

	public String getIpv6subNet() {
		return this.ipv6subNet;
	}

	public void setIpv6subNet(String ipv6subNet) {
		this.ipv6subNet = ipv6subNet;
	}

	public Integer getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}


}