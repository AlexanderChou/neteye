package com.config.action;

import java.util.List;

import com.base.model.Device;
import com.base.service.DeviceService;
import com.config.dao.ServerDAO;
import com.config.dto.Server;
import com.opensymphony.xwork2.ActionSupport;

public class ServerAction extends  ActionSupport{
	private String deviceTypeId;
	private List<Server> serverList;
	private Long id;
	private Device device;
	
	public String execute() throws Exception{
		DeviceService service=new DeviceService();
		if(id==0){
			device=new Device();
		}else{
			device=service.getDevice(id);
		}
		ServerDAO dao=new ServerDAO();
		if(device!=null){
		serverList=dao.getServiceInfo(id,device.getLoopbackIP());
		}
		return SUCCESS;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public List<Server> getServerList() {
		return serverList;
	}

	public void setServerList(List<Server> serverList) {
		this.serverList = serverList;
	}

	public String getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(String deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
}
