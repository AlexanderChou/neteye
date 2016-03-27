package com.fault.action;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.base.model.FaultHistory;
import com.base.service.FaultHisService;
import com.base.util.BaseAction;

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
public class HisFaultAction extends BaseAction{
	private List faultList;
	private FaultHisService service;
	private String totalCount;
	
	public String faultLists() throws Exception{
		service =new FaultHisService();	
		List persistList=service.getFaultTimeNull();
		if(persistList!=null){
			for(int i=0;i<persistList.size();i++){
				FaultHistory history=new FaultHistory();
				history= (FaultHistory) persistList.get(i);				
				Timestamp startTime=history.getBeginTime();
				Timestamp endTime=history.getRecoverTime();
				if(startTime==null||startTime.equals("")){
				    service.delete(history);
				    continue;
				}else if(endTime==null||endTime.equals("")){
					service.delete(history);
					continue;
				}else{
					long starts=startTime.getTime();					
					long ends=endTime.getTime();
					long persists=(ends-starts)/1000;
					history.setPersistTime(Long.toString(persists));
					try {
						service.update(history);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		Date date =new Date();
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss",Locale.ENGLISH);
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);		
		if(cal.get(cal.DATE)<=14){
			cal.roll(Calendar.MONTH, -1);
		}
		cal.roll(Calendar.DATE, -14);
		if(cal.get(cal.MONTH)==11){
			cal.roll(Calendar.YEAR, -1);
		}
		String startTime=dateFormat.format(cal.getTime());
		Timestamp beginTime=Timestamp.valueOf(startTime);
		service =new FaultHisService();		
		String start = this.getRequest().getParameter("start");
		String limit = this.getRequest().getParameter("limit");
		faultList=service.getAllFault(beginTime,start,limit);
		totalCount = String.valueOf(service.getFaultCount(beginTime));
		return SUCCESS;
	}
	public String faultEdit() throws Exception{		
		String id = this.getRequest().getParameter("id");		
		String faultReason = (this.getRequest().getParameter("faultReason")).trim();
		service =new FaultHisService();	
		FaultHistory fault=service.findById(Long.valueOf(id));
		if(faultReason!=null&&!faultReason.equals("")){
			fault.setFaultReason(faultReason);
			service.update(fault);
		}		
		PrintWriter write = this.getResponse().getWriter();
		write.print("ok");
		write.close();
		return null;
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
