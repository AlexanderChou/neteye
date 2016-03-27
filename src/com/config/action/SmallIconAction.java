package com.config.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import com.base.model.Device;
import com.base.model.IfInterface;
import com.base.service.DeviceService;
import com.base.service.LinkService;
import com.config.dto.Port;
import com.opensymphony.xwork2.Action;
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
public class SmallIconAction extends ActionSupport{
	private  List<Port> portList=new ArrayList();
	private long deviceTypeId;
	private int totalCount;
	
	public String execute() throws Exception{
		String start = ServletActionContext.getRequest().getParameter("start");
		String limit = ServletActionContext.getRequest().getParameter("limit");
		totalCount = new DeviceService().getAllDevicesCount(deviceTypeId);
		if(start==null){
			start="0";
		}
		if(limit==null){
			limit=Integer.toString(totalCount);
		}
		List routerList;
		routerList = new DeviceService().getPageList(deviceTypeId,start,limit);
		if(routerList!=null){
			for(int i=0;i<routerList.size();i++){
				Device device=(Device)routerList.get(i);
				Set<IfInterface> ifts = device.getIfinterfaces();
				Iterator InterfaceList = ifts.iterator(); 
				if(InterfaceList!=null){
					while(InterfaceList.hasNext()){
						IfInterface port=(IfInterface)InterfaceList.next();
						Port tempport=new Port();
						tempport.setChineseName(port.getChineseName());
						tempport.setName(port.getName());
						tempport.setId(port.getId());
						tempport.setIfindex(port.getIfindex());
						tempport.setIpv4(port.getIpv4());
						tempport.setIpv6(port.getIpv6());
						tempport.setMaxSpeed(port.getMaxSpeed());
						tempport.setDeviceId(device.getId());
						if(device.getChineseName()!=null&&!device.getChineseName().equals("")){
							tempport.setDeviceName(device.getChineseName());
						}else{
							tempport.setDeviceName(device.getName());
						}
						portList.add(tempport);
					}					
				}
			}
		}
		return Action.SUCCESS;
	}

	public List<Port> getPortList() {
		return portList;
	}

	public void setPortList(List<Port> portList) {
		this.portList = portList;
	}
	public long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}
