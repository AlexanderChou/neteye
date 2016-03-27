package com.savi.show.dto;

public class OnlineInfo {
	private Long id;
	private String login;
	private String ipv4;
	private String ipv6;
	private String mac;
	private String start;
	private String nasIp;
	private String nasPort;
	private String apname;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getIpv4() {
		return ipv4;
	}
	public void setIpv4(String ipv4) {
		this.ipv4 = ipv4;
	}
	public String getIpv6() {
		return ipv6;
	}
	public void setIpv6(String ipv6) {
		this.ipv6 = ipv6;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getNasIp() {
		return nasIp;
	}
	public void setNasIp(String nasIp) {
		this.nasIp = nasIp;
	}
	public String getNasPort() {
		return nasPort;
	}
	public void setNasPort(String nasPort) {
		this.nasPort = nasPort;
	}
	public String getApname() {
		return apname;
	}
	public void setApname(String apname) {
		this.apname = apname;
	}
	
}
