package com.netflow.dto;

import java.io.Serializable;

public class NetFlowData implements Serializable{
	private String name;
	private String IP;	
	private String port;
	private static final long serialVersionUID = 1L;
	
	

	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
}
