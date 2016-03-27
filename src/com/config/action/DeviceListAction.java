package com.config.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.base.model.Device;
import com.base.model.DeviceType;
import com.base.service.DeviceService;
import com.base.service.DeviceTypeService;
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
public class DeviceListAction extends ActionSupport{
	private long deviceTypeId;	
	private long style;
	private List list=new ArrayList();
	private DeviceType devicetype;
	private DeviceService service=new DeviceService();
	private DeviceTypeService serv=new DeviceTypeService();
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
		List	deviceList=service.getPageList(deviceTypeId,start,limit);
		if(deviceList!=null){
			for(int i=0;i<deviceList.size();i++){
				Device device=(Device) deviceList.get(i);
				if(device.getChineseName()==null||device.getChineseName().equals("")){
					if(device.getName()!=null&&!device.getName().equals("")){
					device.setChineseName(device.getName());
					}else{
						if(device.getLoopbackIP()!=null&&!device.getLoopbackIP().equals("")){
						device.setChineseName(device.getLoopbackIP());
						}else{
							device.setChineseName(device.getLoopbackIPv6());
						}
					}
				}
				list.add(device);
			}
		}
		
		devicetype=serv.findById(deviceTypeId);				
			if(deviceTypeId>0&&deviceTypeId<=2){
					if(style==3){
						return SUCCESS;						
					}else if(style==2){
						return "smallIcon";
					}else{
						return  devicetype.getName();
					}					
				}else if(deviceTypeId==3){
					return devicetype.getName();
				}
				else if(deviceTypeId>3){
					return "others";
				}		
				else{
					return ERROR;
				}
	}
	public long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
	public long getStyle() {
		return style;
	}
	public void setStyle(long style) {
		this.style = style;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}
