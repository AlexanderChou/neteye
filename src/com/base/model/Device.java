package com.base.model;

import java.util.LinkedHashSet;
import java.util.Set;

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
public class Device extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private String productor;
	private String model;
	private String serial;
	private String location;
	private String responser;
	private String supporter;
	private String label;
	private String systemVersion;
	private String name;
	private String chineseName;
	private String description;
	private String snmpVersion;
	private String loopbackIP;
	private String loopbackIPv6;
	private Integer ifNum;
	private String readCommunity;
	private String writeCommunity;
	private String trafficIp;
	private String service;
	private Integer faultFlag;
	private DeviceType deviceType;
	private Set<IfInterface> ifinterfaces = new LinkedHashSet<IfInterface>();

	public String getProductor() {
		return productor;
	}
	public void setProductor(String productor) {
		this.productor = productor;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getResponser() {
		return responser;
	}
	public void setResponser(String responser) {
		this.responser = responser;
	}
	public String getSupporter() {
		return supporter;
	}
	public void setSupporter(String supporter) {
		this.supporter = supporter;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getSystemVersion() {
		return systemVersion;
	}
	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}
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
	public String getSnmpVersion() {
		return snmpVersion;
	}
	public void setSnmpVersion(String snmpVersion) {
		this.snmpVersion = snmpVersion;
	}
	public String getLoopbackIP() {
		return loopbackIP;
	}
	public void setLoopbackIP(String loopbackIP) {
		this.loopbackIP = loopbackIP;
	}
	public String getLoopbackIPv6() {
		return loopbackIPv6;
	}
	public void setLoopbackIPv6(String loopbackIPv6) {
		this.loopbackIPv6 = loopbackIPv6;
	}
	public Integer getIfNum() {
		return ifNum;
	}
	public void setIfNum(Integer ifNum) {
		this.ifNum = ifNum;
	}
	public String getReadCommunity() {
		return readCommunity;
	}
	public void setReadCommunity(String readCommunity) {
		this.readCommunity = readCommunity;
	}
	public String getWriteCommunity() {
		return writeCommunity;
	}
	public void setWriteCommunity(String writeCommunity) {
		this.writeCommunity = writeCommunity;
	}
	public DeviceType getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}
	public Set<IfInterface> getIfinterfaces() {
		return ifinterfaces;
	}
	public void setIfinterfaces(Set<IfInterface> ifinterfaces) {
		this.ifinterfaces = ifinterfaces;
	}
	public String getTrafficIp() {
		return trafficIp;
	}
	public void setTrafficIp(String trafficIp) {
		this.trafficIp = trafficIp;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public Integer getFaultFlag() {
		return faultFlag;
	}
	public void setFaultFlag(Integer faultFlag) {
		this.faultFlag = faultFlag;
	}
}
