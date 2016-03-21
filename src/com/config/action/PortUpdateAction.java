package com.config.action;

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
public class PortUpdateAction extends ActionSupport{
	private long id;
	private long deviceId;
	private long style;
	private String name;
	private String chineseName;
	private String ipv4;
	private String ipv6;
	private int upperThreshold;
	private int lowerThreshold;
	private String netmask;
	private String prefix;
	private Double maxSpeed;
	private String description;
	private String flowflag;

	public String execute() throws Exception {
		PortService service=new PortService();	
		IfInterface ifinterface=new IfInterface();
		if(id>0){
			ifinterface=service.findById(id);
		}
		if(name!=null&&!name.trim().equals("")){
			ifinterface.setName(name.trim());
		}
		if(chineseName!=null&&!name.trim().equals("")){
			ifinterface.setChineseName(chineseName);
		}else{
			ifinterface.setChineseName("");
		}
		if(ipv4!=null&&!ipv4.trim().equals("")){
			ifinterface.setIpv4(ipv4);
		}else{
			ifinterface.setIpv4("");
		}
		if(ipv6!=null&&!ipv6.trim().equals("")){
			ifinterface.setIpv6(ipv6);
		}else{
			ifinterface.setIpv6("");
		}
		if(upperThreshold>0){
			ifinterface.setUpperThreshold(upperThreshold);
		}
		if(lowerThreshold<100){
			ifinterface.setLowerThreshold(lowerThreshold);
		}
		if(netmask!=null&&!netmask.trim().equals("")){
			ifinterface.setNetmask(netmask);
		}
		if(prefix!=null&&!prefix.trim().equals("")){
			ifinterface.setPrefix(prefix);
		}else{
			ifinterface.setPrefix("");;
		}
		if(maxSpeed!=null){
			ifinterface.setMaxSpeed(maxSpeed);
		}
		if(description!=null&&!description.trim().equals("")){
			ifinterface.setDescription(description);
		}
		if(flowflag!=null&&!flowflag.trim().equals("")){
			ifinterface.setTrafficFlag(1);
		}else{
			ifinterface.setTrafficFlag(0);
		}
		service.update(ifinterface);
		return SUCCESS;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}
	public long getStyle() {
		return style;
	}
	public void setStyle(long style) {
		this.style = style;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
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
	public String getNetmask() {
		return netmask;
	}
	public void setNetmask(String netmask) {
		this.netmask = netmask;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getUpperThreshold() {
		return upperThreshold;
	}
	public void setUpperThreshold(int upperThreshold) {
		this.upperThreshold = upperThreshold;
	}
	public int getLowerThreshold() {
		return lowerThreshold;
	}
	public void setLowerThreshold(int lowerThreshold) {
		this.lowerThreshold = lowerThreshold;
	}
	public Double getMaxSpeed() {
		return maxSpeed;
	}
	public void setMaxSpeed(Double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	public String getFlowflag() {
		return flowflag;
	}
	public void setFlowflag(String flowflag) {
		this.flowflag = flowflag;
	}
}
