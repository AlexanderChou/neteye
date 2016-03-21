package com.netflow.action;

import com.opensymphony.xwork2.ActionSupport;

public class DispachIPAction extends ActionSupport{
	private String IP;
	private String port;
	public String execute() throws Exception{
		System.out.println("port="+port);
		return SUCCESS;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

}
