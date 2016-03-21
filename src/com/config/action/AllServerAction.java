package com.config.action;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import com.base.model.Device;
import com.base.service.DeviceService;
import com.opensymphony.xwork2.ActionSupport;

public class AllServerAction extends ActionSupport{
	private Vector allServer = new Vector();	
	public String execute() throws Exception{
		DeviceService service=new DeviceService();
		Hashtable table = new Hashtable();
		List deviceList=service.getDeviceList(3);
		if(deviceList!=null){
			for(int i=0;i<deviceList.size();i++){
				Device device=(Device)deviceList.get(i);
				if(device.getService()!=null){
					String a[]=device.getService().split(";");
					String b[]=device.getLabel().split(";");
					for(int j=0;j<a.length;j++){
						String URL=b[j]+"_"+a[j];
						table.put(URL, URL);
					}
				}
			}
		}
		allServer.add(table);
		return SUCCESS;
	}
	public Vector getAllServer() {
		return allServer;
	}
	public void setAllServer(Vector allServer) {
		this.allServer = allServer;
	}
}
