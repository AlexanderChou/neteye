package com.topo.dto;

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
/**
 * 该 bean 为拓扑发现结束初始化时 储存 数据库中的device 和 发现的设备 
 * 
 * @author 李宪亮
 * 
 */
public class Device {
	
	/* 数据库中的设备信息 */
	private long id;
	private String name;
	private String chineseName;
	private String ipv4;
	private String ipv6;
	private Integer ifNum;
	private String deviceTypeName;
	
	/* 拓扑发现后 xml 文件中的设备的信息 */
	private int changeName;
	private String topo_name;
	private String topo_ip;
	private Integer topo_ifNum;
	private String community;
	private String topo_deviceTypeName;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public Integer getIfNum() {
		return ifNum;
	}
	public void setIfNum(Integer ifNum) {
		this.ifNum = ifNum;
	}
	public String getTopo_name() {
		return topo_name;
	}
	public void setTopo_name(String topo_name) {
		this.topo_name = topo_name;
	}
	public String getTopo_ip() {
		return topo_ip;
	}
	public void setTopo_ip(String topo_ip) {
		this.topo_ip = topo_ip;
	}
	public Integer getTopo_ifNum() {
		return topo_ifNum;
	}
	public void setTopo_ifNum(Integer topo_ifNum) {
		this.topo_ifNum = topo_ifNum;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public String getDeviceTypeName() {
		return deviceTypeName;
	}
	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}
	public String getTopo_deviceTypeName() {
		return topo_deviceTypeName;
	}
	public void setTopo_deviceTypeName(String topo_deviceTypeName) {
		this.topo_deviceTypeName = topo_deviceTypeName;
	}
	public int getChangeName() {
		return changeName;
	}
	public void setChangeName(int changeName) {
		this.changeName = changeName;
	}
}
