package com.flow.action;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.base.util.Constants;
import com.flow.dao.FlowDao;
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
/**
 * 用于查询某设备端口的流量信息
 * @author 李宪亮
 * @update 郭铭起
 */
public class FlowQueryAction extends ActionSupport{
	private String ip;
	private String ifindex;
	private String view;
	private String address;
	private String viewName;
	private String _url;
	
		public String execute() throws Exception {
		String type = getParameter("serviceType");
		StringBuffer start = new StringBuffer();
		StringBuffer end = new StringBuffer();
		if ("year".equals(type)) {
			start.append(getParameter("year"));
			start.append("-01-01");

			end.append(getParameter("year"));
			end.append("-12-31");
		} else if ("month".equals(type)) {
			start.append(getParameter("year"));
			String month = getParameter("month");
			if(month.length()==1){
				month = "0"+month;
			}
			start.append("-" + month + "-");
			start.append("01");

			String year = getParameter("year");
			end.append(getDaysInMonth(year, month));
		} else if ("day".equals(type)) {
			start.append(getParameter("fromDate"));
			end.append(getParameter("toDate"));
		}
		
		String cmd = " flowstats --time " + type + "/" + start + "/" + end ;
		if(address!=null&&address.equals("on")){
			if (StringUtils.isNotEmpty(ip)) {
				cmd += " --ip "  + ip ;
				if (StringUtils.isNotEmpty(ifindex)) {
					cmd += " --ifindex " + ifindex;
				}
			}
			_url="file/flow/flowscan/dat/flowstats/performance_Stat_"+start.toString().replace("-","")+"--"
			+end.toString().replace("-","")+"--"+ip+"--"+ifindex;
		}
		if(view!=null&&view.equals("on")){
			FlowDao dao=new FlowDao();
			if(dao.CreateQueryXML(viewName)){
			String fileName=Constants.webRealPath+"file/flow/flowscan/dat/tmp/"+viewName+".xml";
			cmd+= " --file " + fileName;
			_url="file/flow/flowscan/dat/flowstats/performance_Stat_"+start.toString().replace("-","")+"--"
			+end.toString().replace("-","")+"--"+viewName;
			}
		}
		try {
			Process ps = java.lang.Runtime.getRuntime().exec(cmd);
			ps.getErrorStream();
			ps.waitFor();
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
		ServletActionContext.getRequest().setAttribute("startTime", start);
		ServletActionContext.getRequest().setAttribute("endTime", end);
		ServletActionContext.getRequest().setAttribute("_url", _url);
		return SUCCESS;
	}
	
	public String getParameter(String key) {
		Map<String, String[]> params = ServletActionContext.getRequest().getParameterMap();
		return Arrays.toString(params.get(key)).replace("[", "").replace("]", "");
	}

	public String getDaysInMonth(String year, String mon) {
		java.util.GregorianCalendar date = new java.util.GregorianCalendar(Integer.parseInt(year), Integer.parseInt(mon), 1);
		date.add(Calendar.DATE, -1);
		return year + "-" + mon + "-" + date.get(Calendar.DAY_OF_MONTH);
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getIfindex() {
		return ifindex;
	}
	
	public void setIfindex(String ifindex) {
		this.ifindex = ifindex;
	}	

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	
	public String get_url() {
		return _url;
	}

	public void set_url(String _url) {
		this._url = _url;
	}
}
