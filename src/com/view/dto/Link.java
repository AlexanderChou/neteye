package com.view.dto;

import java.io.Serializable;

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
public class Link implements Serializable{
	private static final long serialVersionUID = -1887564811388479646L;
	private long id;
	private String name;
	
	private double srcFlow;
	private double destFlow;
	private String srcFlowStr;
	private String destFlowStr;
	
	//路由器名称
	private String srcName;
	private String destName;
	private String srcChineseName;
	private String destChineseName;
	private long srcId;
	private long destId;
	
	//v4地址信息
	private String srcIP;
	private String destIP;
	private String srcInfIP;
	private String destInfIP;
	
	//v6地址信息
	private String srcIPv6;
	private String destIPv6;
	private String srcInfIPv6;
	private String destInfIPv6;
	
	//端口的索引
	private int srcIfIndex;
	private int destIfIndex;
	
	private double srcSpeed;
	private double destSpeed;
	
	private String srcDescription;
	private String destDescription;
	
	private String srcStatus;
	private String destStatus;
	
	//端口Id
	private Long srcInfId;
	private Long destInfId;
	
	//服务类型临时Id
	private String serviceId;
	
	//两台设备之间链路数目
	private int linkNum;
	private int isSrcFlow;//源端口是否进行流量监控 0:无  1:监控
	private int isDestFlow;//目的端口是否进行流量监控
	//链路带宽
	private double bandwidth;
	
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public double getSrcSpeed() {
		return srcSpeed;
	}
	public void setSrcSpeed(double srcSpeed) {
		this.srcSpeed = srcSpeed;
	}
	public double getDestSpeed() {
		return destSpeed;
	}
	public void setDestSpeed(double destSpeed) {
		this.destSpeed = destSpeed;
	}
	public int getSrcIfIndex() {
		return srcIfIndex;
	}
	public void setSrcIfIndex(int srcIfIndex) {
		this.srcIfIndex = srcIfIndex;
	}
	public int getDestIfIndex() {
		return destIfIndex;
	}
	public void setDestIfIndex(int destIfIndex) {
		this.destIfIndex = destIfIndex;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSrcName() {
		return srcName;
	}
	public void setSrcName(String srcName) {
		this.srcName = srcName;
	}
	public String getDestName() {
		return destName;
	}
	public void setDestName(String destName) {
		this.destName = destName;
	}
	public String getSrcIP() {
		return srcIP;
	}
	public void setSrcIP(String srcIP) {
		this.srcIP = srcIP;
	}
	public String getDestIP() {
		return destIP;
	}
	public void setDestIP(String destIP) {
		this.destIP = destIP;
	}
	public String getSrcInfIP() {
		return srcInfIP;
	}
	public void setSrcInfIP(String srcInfIP) {
		this.srcInfIP = srcInfIP;
	}
	public String getDestInfIP() {
		return destInfIP;
	}
	public void setDestInfIP(String destInfIP) {
		this.destInfIP = destInfIP;
	}
	public String getSrcIPv6() {
		return srcIPv6;
	}
	public void setSrcIPv6(String srcIPv6) {
		this.srcIPv6 = srcIPv6;
	}
	public String getDestIPv6() {
		return destIPv6;
	}
	public void setDestIPv6(String destIPv6) {
		this.destIPv6 = destIPv6;
	}
	public String getSrcInfIPv6() {
		return srcInfIPv6;
	}
	public void setSrcInfIPv6(String srcInfIPv6) {
		this.srcInfIPv6 = srcInfIPv6;
	}
	public String getDestInfIPv6() {
		return destInfIPv6;
	}
	public void setDestInfIPv6(String destInfIPv6) {
		this.destInfIPv6 = destInfIPv6;
	}	
	public double getSrcFlow() {
		return srcFlow;
	}
	public void setSrcFlow(double srcFlow) {
		this.srcFlow = srcFlow;
	}
	public double getDestFlow() {
		return destFlow;
	}
	public void setDestFlow(double destFlow) {
		this.destFlow = destFlow;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSrcId() {
		return srcId;
	}
	public void setSrcId(long srcId) {
		this.srcId = srcId;
	}
	public long getDestId() {
		return destId;
	}
	public void setDestId(long destId) {
		this.destId = destId;
	}
	public String getSrcChineseName() {
		return srcChineseName;
	}
	public void setSrcChineseName(String srcChineseName) {
		this.srcChineseName = srcChineseName;
	}
	public String getDestChineseName() {
		return destChineseName;
	}
	public void setDestChineseName(String destChineseName) {
		this.destChineseName = destChineseName;
	}
	public int getLinkNum() {
		return linkNum;
	}
	public void setLinkNum(int linkNum) {
		this.linkNum = linkNum;
	}
	public String getSrcFlowStr() {
		return srcFlowStr;
	}
	public void setSrcFlowStr(String srcFlowStr) {
		this.srcFlowStr = srcFlowStr;
	}
	public String getDestFlowStr() {
		return destFlowStr;
	}
	public void setDestFlowStr(String destFlowStr) {
		this.destFlowStr = destFlowStr;
	}
	public Long getSrcInfId() {
		return srcInfId;
	}
	public void setSrcInfId(Long srcInfId) {
		this.srcInfId = srcInfId;
	}
	public Long getDestInfId() {
		return destInfId;
	}
	public void setDestInfId(Long destInfId) {
		this.destInfId = destInfId;
	}
	public String getSrcDescription() {
		return srcDescription;
	}
	public void setSrcDescription(String srcDescription) {
		this.srcDescription = srcDescription;
	}
	public String getDestDescription() {
		return destDescription;
	}
	public void setDestDescription(String destDescription) {
		this.destDescription = destDescription;
	}
	public String getSrcStatus() {
		return srcStatus;
	}
	public void setSrcStatus(String srcStatus) {
		this.srcStatus = srcStatus;
	}
	public String getDestStatus() {
		return destStatus;
	}
	public void setDestStatus(String destStatus) {
		this.destStatus = destStatus;
	}
	public double getBandwidth() {
		return bandwidth;
	}
	public void setBandwidth(double bandwidth) {
		this.bandwidth = bandwidth;
	}
	public int getIsSrcFlow() {
		return isSrcFlow;
	}
	public void setIsSrcFlow(int isSrcFlow) {
		this.isSrcFlow = isSrcFlow;
	}
	public int getIsDestFlow() {
		return isDestFlow;
	}
	public void setIsDestFlow(int isDestFlow) {
		this.isDestFlow = isDestFlow;
	}
}
