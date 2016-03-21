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
@SuppressWarnings("all")
public class DeviceUpdateAction  extends ActionSupport{
	private long deviceId;
	private long deviceTypeId;
	private long style;
	private String deviceName;
	private String chineseName;
	private String serial;
	private String snmpVersion;
	private String loopbackIP;
	private String loopbackIPv6;
	private String location;
	private String productor;
	private String model;
	private String trafficIP;
	private String description;
	private String serviceType;
	private long status;
	private String typeId;

	public String execute() throws Exception {
		DeviceService service=new DeviceService();
		Device device=service.getDevice(deviceId);
		if(typeId!=null&&!typeId.equals("")){
		DeviceTypeService deviceService=new DeviceTypeService();
		DeviceType deviceType=(DeviceType) deviceService.findById(Long.parseLong(typeId));
		device.setDeviceType(deviceType);
		}
		
		if(serial!=null&&!serial.trim().equals("")){
			device.setSerial(serial);
		}else{
			device.setSerial("");
		}
		if(snmpVersion!=null&&!snmpVersion.trim().equals("")){
			device.setSnmpVersion(snmpVersion);
			}else{
				device.setSnmpVersion("");
			}
		if(loopbackIP!=null&&!loopbackIP.trim().equals("")){
			device.setLoopbackIP(loopbackIP);
		}else{
			device.setLoopbackIP("");
		}
		if(loopbackIPv6!=null&&!loopbackIPv6.trim().equals("")){
			device.setLoopbackIPv6(loopbackIPv6);
		}else{
			device.setLoopbackIPv6("");
		}
		if(location!=null&&!location.trim().equals("")){
			device.setLocation(location);
		}else{
			device.setLocation("");
		}
		if(productor!=null&&!productor.trim().equals("")){
			device.setProductor(productor);
		}else{
			device.setProductor("");
		}
		if(model!=null&&!model.trim().equals("")){
			device.setModel(model);
		}
		if(trafficIP!=null&&!trafficIP.trim().equals("")){
			device.setTrafficIp(trafficIP);
		}else{
			device.setTrafficIp("");
		}
		if(description!=null&&!description.trim().equals("")){
			device.setDescription(description);
		}else{
			device.setDescription("");
		}
		if(style==1){
			this.setStatus(0);
		}else if(style==2){
			this.setStatus(1);
		}else{
			this.setStatus(2);
		}
		if(deviceTypeId==1||deviceTypeId==2){
			if(chineseName!=null&&!chineseName.trim().equals("")){
				device.setChineseName(chineseName);
			}else{
				device.setChineseName("");
			}
			if(deviceName!=null&&!deviceName.trim().equals("")){
				device.setName(deviceName);
			}else{
				device.setName("");
			}
			 service.update(device);
			 return "update";
		}else if(deviceTypeId==3){
			if(deviceName!=null&&!deviceName.trim().equals("")){
				device.setChineseName(deviceName);
				device.setName(deviceName);
			}else{
				device.setChineseName("");
			}
			String services="";
			if(serviceType.contains("DNS")){
				services="DNS;";
			}else{
				services=";";
			}
			if(serviceType.contains("Email")){
				services+="Email;";
			}else{
				services+=";";
			}
			if(serviceType.contains("FTP")){
				services+="FTP;";
			}else{
				services+=";";
			}
			if(serviceType.contains("HTTP")){
				services+="HTTP";
			}else{
				services+=";";
			}
			device.setService(services);
			service.update(device);
			ServiceManageService manageService=new ServiceManageService();
			String str=device.getService();
			String temp=null;
			if(str!=null){
				String a[]=str.split(";");
				  if(a!=null){
					for(int i=0;i<a.length;i++){
						String type=a[i];
						if(a[i]!=null&&!a[i].equals("")){
						temp+=type+"|";
						boolean flag=manageService.isExistByIP(deviceId, type);
						if(flag){
							continue;
						}else{
							ServiceManage manage=new ServiceManage();
							manage.setDeviceId(deviceId);
							manage.setServiceType(type);
							manage.setPort(Constants.serviceMap.get(type).toString());
							manageService.create(manage);
						}
						}
					}
				}
			}
			List serviceList=manageService.getListByDeviceId(deviceId);
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
			if(deviceName!=null&&!deviceName.trim().equals("")){
				device.setChineseName(deviceName);
				device.setName(deviceName);
			}else{
				device.setChineseName("");
			}
			service.update(device);
		 return "uothers";
		}
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

	public long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

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

	public String getTrafficIP() {
		return trafficIP;
	}

	public void setTrafficIP(String trafficIP) {
		this.trafficIP = trafficIP;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	
	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
}

