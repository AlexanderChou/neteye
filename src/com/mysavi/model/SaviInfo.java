package com.mysavi.model;

import org.apache.struts2.json.annotations.JSON;

public class SaviInfo  implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Boolean saviManual;
	private Boolean saviFcfs;
	private Boolean saviDhcpv4;
	private Boolean saviDhcpv6;
	private Boolean saviSend;
	
	
	public SaviInfo() {}
	
	public Integer getId() {
		return id;
	}
	
	public Boolean getSaviManual() {
		return saviManual;
	}

	public Boolean getSaviFcfs() {
		return saviFcfs;
	}
	
	public Boolean getSaviSend() {
		return saviSend;
	}
	
	public Boolean getSaviDhcpv4() {
		return saviDhcpv4;
	}

	public Boolean getSaviDhcpv6() {
		return saviDhcpv6;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setSaviManual(Boolean flag) {
		saviManual = flag;
	}
	
	public void setSaviSend(Boolean flag) {
		saviSend = flag;
	}
	
	public void setSaviFcfs(Boolean flag) {
		saviFcfs = flag;
	}
	
	public void setSaviDhcpv4(Boolean flag) {
		saviDhcpv4 = flag;
	}
	
	public void setSaviDhcpv6(Boolean flag) {
		saviDhcpv6 = flag;
	}
}
