package com.config.action;

import java.util.List;

import com.base.model.Device;
import com.base.model.DeviceType;
import com.base.model.ServiceManage;
import com.base.service.DeviceService;
import com.base.service.DeviceTypeService;
import com.base.service.ServiceManageService;
import com.base.util.Constants;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

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
public class DeviceAction extends ActionSupport implements ModelDriven<Device>,Preparable{
	private long id;
	
	private Device device;
	
	private long deviceTypeId;
	
	private long style;
	
	private List<DeviceType> deviceTypeList;
		
	private DeviceService service=new DeviceService();
	
	private boolean httpFlag=false;
	
	private boolean emailFlag=false;
	
	private boolean dnsFlag=false;
	
	private boolean ftpFlag=false;
	
	public Device getModel() {
		return device;
	}

	public void prepare() throws Exception {
		if(id==0){
			device=new Device();
		}else{
			device=service.getDevice(id);
		}
		deviceTypeList=new DeviceTypeService().getDeviceTypeList();
	}
	public String view() throws Exception{
		if(id!=0){
			DeviceType deviceType=(DeviceType) device.getDeviceType();
			String type=deviceType.getName();
			if(deviceTypeId==0){
				deviceTypeId=deviceType.getId();
			}
			if(deviceTypeId<=3){
				if(deviceTypeId==3){					
					String str=device.getService()+" ";
					if(str!=null){
						String s[]=str.split(";");
						if(s!=null){
							if(s[0]!=null&&s[0].equals("DNS")){
								dnsFlag=true;
							}
							if(s[1]!=null&&s[1].equals("Email")){
								emailFlag=true;
							}
							if(s[2]!=null&&s[2].equals("FTP")){
								ftpFlag=true;
							}
							if(s[3]!=null&&s[3].equals("HTTP")){
								httpFlag=true;
							}
					    }
					}
				}
				return type;
			}else{
				return	"others";
			}
		}else{
			return "error";
		}
	}
	public String update() throws Exception{
		DeviceType deviceType=new DeviceTypeService().findById(deviceTypeId);
		device.setDeviceType(deviceType);
		if(deviceTypeId==1||deviceTypeId==2){
		 service.update(device);
		 return "update";
		}else if(deviceTypeId==3){
			service.update(device);
			ServiceManageService manageService=new ServiceManageService();
			String str=device.getService();
			String temp=null;
			if(str!=null){
				String a[]=str.split(";");
				for(int i=0;i<a.length;i++){
					String type=a[i];
					temp+=type+"|";
					boolean flag=manageService.isExistByIP(id, type);
					if(flag){
						continue;
					}else{
						ServiceManage manage=new ServiceManage();
						manage.setDeviceId(id);
						manage.setServiceType(type);
						manage.setPort(Constants.serviceMap.get(type).toString());
						manageService.create(manage);
					}
				}
			}
			List serviceList=manageService.getListByDeviceId(id);
			if(serviceList!=null){
				for(int i=0;i<serviceList.size();i++){
					ServiceManage manage=(ServiceManage) serviceList.get(i);
					String type=manage.getServiceType();
					if(temp==null){
						manageService.delete(manage);
					}else{
						if(temp.contains(type)){
							continue;
						}else{
							manageService.delete(manage);
						}
					}
				}
			}
		 return "userver";
		}else{
			service.update(device);
		 return "uothers";
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public long getStyle() {
		return style;
	}

	public void setStyle(long style) {
		this.style = style;
	}

	public List<DeviceType> getDeviceTypeList() {
		return deviceTypeList;
	}

	public void setDeviceTypeList(List<DeviceType> deviceTypeList) {
		this.deviceTypeList = deviceTypeList;
	}

	public boolean isHttpFlag() {
		return httpFlag;
	}

	public void setHttpFlag(boolean httpFlag) {
		this.httpFlag = httpFlag;
	}

	public boolean isEmailFlag() {
		return emailFlag;
	}

	public void setEmailFlag(boolean emailFlag) {
		this.emailFlag = emailFlag;
	}

	public boolean isDnsFlag() {
		return dnsFlag;
	}

	public void setDnsFlag(boolean dnsFlag) {
		this.dnsFlag = dnsFlag;
	}

	public boolean isFtpFlag() {
		return ftpFlag;
	}

	public void setFtpFlag(boolean ftpFlag) {
		this.ftpFlag = ftpFlag;
	}
}
