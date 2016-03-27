package com.view.action;
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

import java.util.ArrayList;

import org.apache.struts2.json.annotations.JSON;

import com.base.service.EventService;
import com.base.service.IfInterfaceService;
import com.base.service.LinkService;
import com.opensymphony.xwork2.Action;
import com.view.dto.DeviceInfo;
import com.view.dto.EventInfo;
import com.view.dto.InterfaceInfo;
import com.view.dto.ServiceEventInfo;
import com.view.dto.ServiceInfo;

public class InfoAction {
    private int deviceId;
    private Long infId;
    private int serviceId;
    private ServiceInfo serviceInfo;
	private DeviceInfo deviceInfo;
	private ArrayList<InterfaceInfo> interfaceList;
	private ArrayList<EventInfo> eventInfoList;
	private ArrayList<EventInfo> infEventInfoList;
	private ArrayList<ServiceInfo> serviceInfoList;
    private ArrayList<ServiceEventInfo> serviceEventInfoList;
    private Long totalCount;
    private String start ;
    private String limit;
	@JSON(serialize=false)
	/*
	 * 从数据库中读取deviceId指定的设备的状态信息，并通过参数deviceInfo返回给客户端
	 */
	public String fetchDeviceInfo() {
		LinkService linkService=new LinkService();
		try {
			deviceInfo=linkService.getDeviceInfoByDeviceId(deviceId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Action.SUCCESS; 
	}
	@JSON(serialize=false)
	/*
	 * 从数据库中读取serviceId指定的服务的状态信息，并通过参数serviceInfo返回给客户端
	 */
	public String fetchServiceInfo(){
		EventService eventService=new EventService();
		try {
			serviceInfo=eventService.getServiceInfoByServiceId(serviceId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Action.SUCCESS; 
	}
	@JSON(serialize=false)
	/*
	 * 从数据库中读取deviceId指定的设备的所有端口的状态信息，并通过参数interfaceList返回给客户端
	 */
	public String fetchInterfaces(){
		IfInterfaceService ifInterfaceService=new IfInterfaceService();
		try {
			totalCount = ifInterfaceService.getIfInterfaceCountBydeviceId(deviceId);
			interfaceList = ifInterfaceService.getIfInterfaceListBydeviceId((long)deviceId, Integer.parseInt(start), Integer.parseInt(limit));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	@JSON(serialize=false)
	/*
	 * 从数据库中读取deviceId指定的设备的所有服务的状态信息，并通过参数serviceInfoList返回给客户端
	 */
	public String fetchServices(){
		EventService eventService=new EventService();
		try {
			serviceInfoList=eventService.getServiceInfoListByServiceId(deviceId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	@JSON(serialize=false)
	/*
	 * 从数据库中读取deviceId指定的设备的所有端口事件信息，并通过参数eventInfoList返回给客户端
	 */
	public String fetchEventInfos(){
		EventService eventService=new EventService();
		if(start==null ){
			start = "0";
		}
		if(limit==null){
			limit = "15";
		}
		try {
			eventInfoList=eventService.getEventbyDeviceId(deviceId, Integer.parseInt(start), Integer.parseInt(limit));
			totalCount = eventService.getEventCountByDeviceId(deviceId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	@JSON(serialize=false)
	/*
	 * 从数据库中读取指定端口事件信息，并通过参数eventInfoList返回给客户端
	 */
	public String fetchInfEventInfos(){
		EventService eventService=new EventService();
		try {
			infEventInfoList=eventService.getEventbyInfId(infId, Integer.parseInt(start), Integer.parseInt(limit));
			totalCount = eventService.getEventCountByDeviceId(infId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	/*
	 * 从数据库中读取deviceId指定的设备的所有服务事件信息，并通过参数serviceEventInfoList返回给客户端
	 */
	public String fetchServiceEventInfos(){
		EventService eventService=new EventService();
		try {
			totalCount = eventService.getServiceEventCountByDeviceId(deviceId);
			serviceEventInfoList=eventService.getServiceEventbyDeviceId(deviceId, Integer.parseInt(start), Integer.parseInt(limit));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	public DeviceInfo getDeviceInfo() {
		return deviceInfo;
	}
	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
	public ArrayList<InterfaceInfo> getInterfaceList() {
		return interfaceList;
	}
	public void setInterfaceList(ArrayList<InterfaceInfo> interfaceList) {
		this.interfaceList = interfaceList;
	}
	public ArrayList<EventInfo> getEventInfoList() {
		return eventInfoList;
	}
	public void setEventInfoList(ArrayList<EventInfo> eventInfoList) {
		this.eventInfoList = eventInfoList;
	}
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public ServiceInfo getServiceInfo() {
		return serviceInfo;
	}
	public void setServiceInfo(ServiceInfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}
	public ArrayList<ServiceInfo> getServiceInfoList() {
		return serviceInfoList;
	}
	public void setServiceInfoList(ArrayList<ServiceInfo> serviceInfoList) {
		this.serviceInfoList = serviceInfoList;
	}
	public ArrayList<ServiceEventInfo> getServiceEventInfoList() {
		return serviceEventInfoList;
	}
	public void setServiceEventInfoList(
			ArrayList<ServiceEventInfo> serviceEventInfoList) {
		this.serviceEventInfoList = serviceEventInfoList;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public Long getInfId() {
		return infId;
	}
	public void setInfId(Long infId) {
		this.infId = infId;
	}
	public ArrayList<EventInfo> getInfEventInfoList() {
		return infEventInfoList;
	}
	public void setInfEventInfoList(ArrayList<EventInfo> infEventInfoList) {
		this.infEventInfoList = infEventInfoList;
	}
}