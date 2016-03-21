package com.config.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import com.base.model.Device;
import com.base.service.DeviceService;
import com.base.util.BaseAction;
import com.base.util.Constants;

@SuppressWarnings("all")
public class PerformanceParaAction extends BaseAction {
	private String deviceId;
	private String dicDetailId;
	private String performancePara;
	private String success;
	private String failure;
	
	public String execute()  throws Exception{
		//将配置的信息写入file/performance/config/目录下，目录的名字为：performancePara_dicDetailId_deviceId.txt
		String path = Constants.webRealPath + "file/performance/config/";
		File filePath=new File( path); 
		if(!filePath.exists()){
			filePath.mkdirs();
		}
		File file = new File(path+"perfPara_"+dicDetailId+"_"+deviceId+".txt");	
		//根据设备id,获得其相应的IP地址和community
		Device device = new DeviceService().findById(Long.parseLong(deviceId));
		String IP = device.getLoopbackIP();
		if(IP==null || IP.equals("")){
			IP = device.getLoopbackIPv6();
		}
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileOutputStream(file));
			writer.print(IP+"||"+device.getReadCommunity()+"\n");
			writer.print(performancePara);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
		return SUCCESS;
	}
	
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDicDetailId() {
		return dicDetailId;
	}
	public void setDicDetailId(String dicDetailId) {
		this.dicDetailId = dicDetailId;
	}
	public String getPerformancePara() {
		return performancePara;
	}
	public void setPerformancePara(String performancePara) {
		this.performancePara = performancePara;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getFailure() {
		return failure;
	}
	public void setFailure(String failure) {
		this.failure = failure;
	}
}
