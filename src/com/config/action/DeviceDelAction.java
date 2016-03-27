package com.config.action;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.dom4j.Document;

import com.base.model.Device;
import com.base.model.IfInterface;
import com.base.model.Link;
import com.base.model.ServiceManage;
import com.base.model.View;
import com.base.service.DeviceService;
import com.base.service.LinkService;
import com.base.service.PortService;
import com.base.service.ServiceManageService;
import com.base.service.ViewService;
import com.base.util.Constants;
import com.config.dao.ServerDAO;
import com.config.util.XMLManager;
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
public class DeviceDelAction extends ActionSupport{
	private String submitDevice;
	
	private long deviceTypeId;
	
	private long style;
	
	private DeviceService service=new DeviceService();
	
	private PortService portService=new PortService();
	
	private LinkService linkService=new LinkService();
	
	private ServiceManageService virtualService=new ServiceManageService();
	
	private ServerDAO dao;
	
	public String execute()  throws Exception{
		LinkedHashSet<Long> linkIdSet = new LinkedHashSet<Long>();
		LinkedHashSet<Long> routerSet = new LinkedHashSet<Long>();
		LinkedHashSet<Long> switchSet = new LinkedHashSet<Long>();
		LinkedHashSet<Long> serverSet = new LinkedHashSet<Long>();
		LinkedHashSet<Long> workstationSet = new LinkedHashSet<Long>();
		LinkedHashSet<Long> customSet = new LinkedHashSet<Long>();
		if(deviceTypeId==3){
			dao=new ServerDAO();
		}
		if(submitDevice!=null){
			String a[]= submitDevice.split(";");
			if(a!=null){
				for(int i=0;i<a.length;i++){
					if(!a[i].equals("")){
						Long deviceId = Long.valueOf(a[i]);
						List portList=portService.getPortList(deviceId);						
						if(!portList.isEmpty()){
							IfInterface port0 = (IfInterface) portList.get(0);
							Long typeId = port0.getDevice().getDeviceType().getId();
							if(typeId==1){
								routerSet.add(deviceId);
							}else if(typeId==2){
								switchSet.add(deviceId);
							}else if(typeId==3){
								serverSet.add(deviceId);
							}else if(typeId==4){
								workstationSet.add(deviceId);
							}else{
								customSet.add(deviceId);
							}							
							for(int j=0;j<portList.size();j++){
								IfInterface port=(IfInterface) portList.get(j);								
								long portId=port.getId();
								List linkList=linkService.getLinkListByInterfId(portId);
								for(int k=0;k<linkList.size();k++){
									Link link=(Link)linkList.get(k);
									linkIdSet.add(link.getId());
									linkService.deleteLink(link);
								}
								portService.delete(port);
							}
						}
						if(deviceTypeId==3){
							dao.deleteXML(service.getDevice(deviceId).getLoopbackIP());
						}
						service.delete(Device.class, Long.valueOf(a[i]));						
						List virtualList=virtualService.getListByDeviceId(Long.valueOf(a[i]));
						if(virtualList!=null){
							for(int j=0;j<virtualList.size();j++){
								ServiceManage virtualPort=(ServiceManage)virtualList.get(j);
								virtualService.delete(virtualPort);
							}
						}
					}
				}
				
				
				//获得系统中所有的视图
				List<View> views = new ViewService().getViewList();
				for(View view:views){
					Long userId = view.getUserId();
					String userName = view.getUserName();
					String viewName = view.getName();
					String path = Constants.webRealPath + "file/user/";
					String userDirectory = userName + "_" + userId;
					String fileStr = path + userDirectory + "/" + viewName + ".xml"; 
					File file = new File(fileStr);
					if(file.exists()){
						if(file.length()>0){
							Document document = XMLManager.readXml(fileStr);
							//删除视图中相关链路
							Iterator<Long> it = linkIdSet.iterator();
							while(it.hasNext()) {
								Long linkId = it.next();
								XMLManager.deleteTag(document,"links","link","id",linkId.toString());
							}
							//删除视图中相关的设备
							Iterator<Long> routerIt = routerSet.iterator();
							while(routerIt.hasNext()){
								Long routerId = routerIt.next();
								XMLManager.deleteTag(document,"routers","router","id",routerId.toString());
							}
							Iterator<Long> switchIt = switchSet.iterator();
							while(switchIt.hasNext()){
								Long switchId = switchIt.next();
								XMLManager.deleteTag(document,"switchs","switch","id",switchId.toString());
							}
							Iterator<Long> serverIt = serverSet.iterator();
							while(serverIt.hasNext()){
								Long serverId = serverIt.next();
								XMLManager.deleteTag(document,"servers","service","id",serverId.toString());
							}
							Iterator<Long> workstationIt = workstationSet.iterator();
							while(workstationIt.hasNext()){
								Long workstationId = workstationIt.next();
								XMLManager.deleteTag(document,"workstations","workstation","id",workstationId.toString());
							}
							Iterator<Long> customIt = customSet.iterator();
							while(customIt.hasNext()){
								Long customId = customIt.next();
								XMLManager.deleteTag(document,"workstations","workstation","id",customId.toString());
							}
							XMLManager.writeXml(document, fileStr);
						}
					}
				}
			}//if(a!=null)
		}//if(submitDevice!=null)
		
		if(deviceTypeId==1||deviceTypeId==2){
			if(style==1){
				return "bigIcon";
			}
			if(style==2){
				return "smallIcon";
			}else{
				return "success";
			}
		}else if(deviceTypeId==3){
			return "server";
		}else{
			return "others";
		}
	}

	public String getSubmitDevice() {
		return submitDevice;
	}

	public void setSubmitDevice(String submitDevice) {
		this.submitDevice = submitDevice;
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

}
