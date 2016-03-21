package com.base.model;

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
public class IfInterface extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private String name;
	private String chineseName;
	private String description;
	private String ifindex;
	private Double speed;
	private Double maxSpeed;
	private String MAC;
	private String manageStatus;
	private String operateStatus;
	private String manageStatusIPv6;
	private String operateStatusIPv6;
	private String interfaceType;
	private Integer upperThreshold;
	private Integer lowerThreshold;
	private Device device;
	private String ipv4;
	private String ipv6;
	private String netmask;
	private String prefix;
	private Integer trafficFlag;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIfindex() {
		return ifindex;
	}
	public void setIfindex(String ifindex) {
		this.ifindex = ifindex;
	}
	public Double getSpeed() {
		return speed;
	}
	public void setSpeed(Double speed) {
		this.speed = speed;
	}
	public Double getMaxSpeed() {
		return maxSpeed;
	}
	public void setMaxSpeed(Double ed) {
		this.maxSpeed = ed;
	}
	public String getMAC() {
		return MAC;
	}
	public void setMAC(String mac) {
		MAC = mac;
	}
	public String getManageStatus() {
		return manageStatus;
	}
	public void setManageStatus(String manageStatus) {
		this.manageStatus = manageStatus;
	}
	public String getOperateStatus() {
		return operateStatus;
	}
	public void setOperateStatus(String operateStatus) {
		this.operateStatus = operateStatus;
	}
	public String getManageStatusIPv6() {
		return manageStatusIPv6;
	}
	public void setManageStatusIPv6(String manageStatusIPv6) {
		this.manageStatusIPv6 = manageStatusIPv6;
	}
	public String getOperateStatusIPv6() {
		return operateStatusIPv6;
	}
	public void setOperateStatusIPv6(String operateStatusIPv6) {
		this.operateStatusIPv6 = operateStatusIPv6;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public Integer getTrafficFlag() {
		return trafficFlag;
	}
	public void setTrafficFlag(Integer trafficFlag) {
		this.trafficFlag = trafficFlag;
	}
	public String getInterfaceType() {
		return interfaceType;
	}
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
	public Integer getUpperThreshold() {
		return upperThreshold;
	}
	public void setUpperThreshold(Integer upperThreshold) {
		this.upperThreshold = upperThreshold;
	}
	public Integer getLowerThreshold() {
		return lowerThreshold;
	}
	public void setLowerThreshold(Integer hresholowerThresholdld) {
		this.lowerThreshold = hresholowerThresholdld;
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
}
