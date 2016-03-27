package com.savi.base.model;

import java.util.LinkedHashSet;
import java.util.Set;


public class Apinfo implements java.io.Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Integer apid;
	private String apname;
	private String ipv4Address;
	private String ipv6Address;
	private Integer status;
	
	
	private Long acid;
	
	
	
	private String usernum;
	
	
	private Set<SaviFilterTableCur> apcurs = new LinkedHashSet<SaviFilterTableCur>();

	
	
	public Apinfo(){
		
	}
	public Apinfo(Apinfo a){
		this.id=a.getId();
		this.acid=a.getAcid();
		this.apname=a.getApname();
		this.ipv4Address=a.getIpv4Address();
		this.ipv6Address=a.getIpv6Address();
		this.status=a.getStatus();
		this.acid=a.getAcid();
		this.apcurs=a.getApcurs();
			
	}
	
	
	public Set<SaviFilterTableCur> getApcurs() {
		return apcurs;
	}
	public void setApcurs(Set<SaviFilterTableCur> apcurs) {
		this.apcurs = apcurs;
	}
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
	public String getUsernum() {
		return usernum;
	}
	public void setUsernum(String usernum) {
		this.usernum = usernum;
	}
	
	
	

}
