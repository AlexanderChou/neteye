package com.savi.show.dto;

import java.util.LinkedHashSet;
import java.util.Set;

public class Ap{

	/**
	 * ap
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer apid;
	private String apname;
	private String ipv4Address;
	private String ipv6Address;
	private Integer status;
	private Long acid;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getApid() {
		return apid;
	}
	public void setApid(Integer apid) {
		this.apid = apid;
	}
	public String getApname() {
		return apname;
	}
	public void setApname(String apname) {
		this.apname = apname;
	}
	public String getIpv4Address() {
		return ipv4Address;
	}
	public void setIpv4Address(String ipv4Address) {
		this.ipv4Address = ipv4Address;
	}
	public String getIpv6Address() {
		return ipv6Address;
	}
	public void setIpv6Address(String ipv6Address) {
		this.ipv6Address = ipv6Address;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getAcid() {
		return acid;
	}
	public void setAcid(Long acid) {
		this.acid = acid;
	}
	
	
	

}
