package com.flow.dto;

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
public class Statistic {
	private Long id;
	private String linkName;
	private String loopbackIP;
	private Integer ifIndex;
	private String averageFlow;
	private String maxFlow;
	private String minFlow;
	private String totalFlow;
	private String startTime;
	private String endTime;
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getLoopbackIP() {
		return loopbackIP;
	}
	public void setLoopbackIP(String loopbackIP) {
		this.loopbackIP = loopbackIP;
	}
	public String getAverageFlow() {
		return averageFlow;
	}
	public void setAverageFlow(String averageFlow) {
		this.averageFlow = averageFlow;
	}
	public String getMaxFlow() {
		return maxFlow;
	}
	public void setMaxFlow(String maxFlow) {
		this.maxFlow = maxFlow;
	}
	public String getMinFlow() {
		return minFlow;
	}
	public void setMinFlow(String minFlow) {
		this.minFlow = minFlow;
	}
	public String getTotalFlow() {
		return totalFlow;
	}
	public void setTotalFlow(String totalFlow) {
		this.totalFlow = totalFlow;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getIfIndex() {
		return ifIndex;
	}
	public void setIfIndex(Integer ifIndex) {
		this.ifIndex = ifIndex;
	}
}
