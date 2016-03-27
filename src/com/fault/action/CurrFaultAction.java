package com.fault.action;

import java.util.ArrayList;
import java.util.List;

import com.base.model.FaultCurrent;
import com.base.service.FaultCurrService;
import com.base.util.BaseAction;
import com.fault.dto.FaultNode;

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
public class CurrFaultAction extends BaseAction{
	private List faultList;
	private String totalCount;
	private FaultCurrService service;
	public String execute() throws Exception{
		service =new FaultCurrService();		
		String start = this.getRequest().getParameter("start");
		String limit = this.getRequest().getParameter("limit");
		totalCount = String.valueOf(service.getFaultCount());
		List list=service.getFaultByNumber(start,limit);
		faultList=new ArrayList();
		if(list!=null){
			for(int i=0;i<list.size();i++){
				FaultCurrent current=(FaultCurrent) list.get(i);
				FaultNode node=new FaultNode();
				node.setId(current.getId());
				node.setIp(current.getFaultIp());
				node.setLoss("100");
				node.setRrt("~~");
				node.setBeginTime(current.getBeginTime());
				node.setPing("ping");
				node.setTraceroute("traceroute");
				faultList.add(node);
			}
		}
		return SUCCESS;
	}
	public List getFaultList() {
		return faultList;
	}
	public void setFaultList(List faultList) {
		this.faultList = faultList;
	}
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
}
