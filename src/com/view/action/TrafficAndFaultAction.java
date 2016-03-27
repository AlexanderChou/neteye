package com.view.action;

import java.util.List;

import com.base.model.IfInterface;
import com.base.service.PortService;
import com.opensymphony.xwork2.ActionSupport;

/*
** Copyright (c) 2008, 2009, 2010
**      The Regents of the Tsinghua University, PRC.  All rights reserved.
**
** Redistribution and use in source and binary forms, with or without  modification, are permitted provided that the following conditions are met:
** 1. Redistributions of source code must retain the above copyright  notice, this list of conditions and the following disclaimer.
** 2. Redistributions in binary form must reproduce the above copyright  notice, this list of conditions and the following disclaimer in the  documentation and/or other materials provided with the distribution.
** 3. All advertising materials mentioning features or use of this software  must display the following acknowledgement:
**  This product includes software (iNetBoss) developed by Tsinghua University, PRC and its contributors.
** THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
** ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
** IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
** ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
** FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
** DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
** OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
** HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
** LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
** OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
** SUCH DAMAGE.
*
*/
public class TrafficAndFaultAction extends ActionSupport {
	private long id;
	private String description;
	private String chineseName;
	private String deviceIpv4;
	private String deviceIpv6;
	private String name;
	private String trafficIp;
	private String deviceType;
	private List<IfInterface> portList;
	private long nodeTyple;
	public String execute() throws Exception{
		PortService service=new PortService();
		portList=service.getPortList(id);
		if(description!=null){
			if(description.contains("Juniper")){
				nodeTyple=1;
			}else if(description.contains("Huawei")){
				nodeTyple=2;
			}else{
				nodeTyple=0;
			}
		}else{
			nodeTyple=0;
		}
		return SUCCESS;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<IfInterface> getPortList() {
		return portList;
	}

	public void setPortList(List<IfInterface> portList) {
		this.portList = portList;
	}
	
	public long getNodeTyple() {
		return nodeTyple;
	}

	public void setNodeTyple(long nodeTyple) {
		this.nodeTyple = nodeTyple;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getDeviceIpv4() {
		return deviceIpv4;
	}

	public void setDeviceIpv4(String deviceIpv4) {
		this.deviceIpv4 = deviceIpv4;
	}

	public String getDeviceIpv6() {
		return deviceIpv6;
	}

	public void setDeviceIpv6(String deviceIpv6) {
		this.deviceIpv6 = deviceIpv6;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTrafficIp() {
		return trafficIp;
	}

	public void setTrafficIp(String trafficIp) {
		this.trafficIp = trafficIp;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
}
