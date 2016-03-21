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
/*
 * @autor:JiangNing
 * @data:2009-7-17
 */
package com.view.dto;

public class InterfaceInfo {
	private Long id;
	private int index;	
	private String ipv4;	
	private String ipv6;
	private String speed; 	
	private String managementStatusV4;
	private String operationStatusV4;
	private String managementStatusV6;
	private String operationStatusV6;
	private String flowChartUrl;
	private String desc;
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
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
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getManagementStatusV4() {
		return managementStatusV4;
	}
	public void setManagementStatusV4(String managementStatusV4) {
		this.managementStatusV4 = managementStatusV4;
	}
	public String getOperationStatusV4() {
		return operationStatusV4;
	}
	public void setOperationStatusV4(String operationStatusV4) {
		this.operationStatusV4 = operationStatusV4;
	}
	public String getManagementStatusV6() {
		return managementStatusV6;
	}
	public void setManagementStatusV6(String managementStatusV6) {
		this.managementStatusV6 = managementStatusV6;
	}
	public String getOperationStatusV6() {
		return operationStatusV6;
	}
	public void setOperationStatusV6(String operationStatusV6) {
		this.operationStatusV6 = operationStatusV6;
	}
	public String getFlowChartUrl() {
		return flowChartUrl;
	}
	public void setFlowChartUrl(String flowChartUrl) {
		this.flowChartUrl = flowChartUrl;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
