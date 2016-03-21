package com.config.action;


import java.util.ArrayList;
import java.util.List;

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
public class SpilthAction extends ActionSupport{
	private String exist;
	private  List<Device> existList=new ArrayList<Device>();
	private String deviceTypeId;
	private String status;
	
	public String execute() throws Exception{
		DeviceService service=new DeviceService();
		DeviceTypeService tservice=new DeviceTypeService();
		DeviceType type=(DeviceType)tservice.findById(Long.parseLong(deviceTypeId));
		if(exist!=null && exist.trim().length()>0){
			String temp=this.toStr(exist);
			String a[]=(temp.trim()).split(";");
				if(a!=null){				
					for(int i=0;i<a.length;i++){
						if(!a[i].trim().equals("")){
							String b[]=(a[i]+" ").split("\\|");
							if(b.length>0){	
								Device device=new Device();
								Device deviceold=(Device)service.findDeviceByIP(b[1]);
								if(!b[0].trim().equals("")){
									device.setChineseName(b[0].trim());
								}
								if(!b[1].trim().equals("")){						
									device.setLoopbackIP(b[1]);
								}								
								if(!b[2].trim().equals("")){
									device.setReadCommunity(b[2].trim());					
								}
								if(!b[3].trim().equals("")){
									device.setSnmpVersion(b[3].trim());				
								}
								device.setModel(type.getName());								
								device.setSerial(deviceold.getChineseName());
								device.setName(deviceold.getName());
								device.setSupporter(deviceold.getDeviceType().getName());
								device.setProductor(deviceold.getLoopbackIP());
								device.setLoopbackIPv6(deviceold.getLoopbackIPv6());
								device.setIfNum(deviceold.getIfinterfaces().size());
								existList.add(device);
							}
						}
					}
				}
		}
		return SUCCESS;
	}
	  public String toStr(String binStr){
	        String[] tempStr = StrToStrArray(binStr);
	        char[] tempChar = new char[tempStr.length];
	        for(int i = 0; i < tempStr.length; i++){
	            tempChar[i] = toChar(tempStr[i]);
	        }
	        return String.valueOf(tempChar);
	    }
	  private String[] StrToStrArray(String str){
	        return str.split(" ");
	    }
	   private char toChar(String binStr){
	        int[] temp = binStrToIntArray(binStr);
	        int sum = 0;
	        
	        for(int i = 0; i < temp.length; i++){

	            sum += temp[temp.length - 1 - i] << i;

	        }
	    
	        return (char)sum;
	        
	    }
	   private int[] binStrToIntArray(String binStr){
	        
	        char[] temp = binStr.toCharArray();
	        int[] result = new int[temp.length];
	    
	        for(int i = 0; i < temp.length; i++){
	            result[i] = temp[i] - 48;
	        }
	        return result;
	        
	    }
	public String getExist() {
		return exist;
	}	

	public void setExist(String exist) {
		this.exist = exist;
	}

	public List<Device> getExistList() {
		return existList;
	}

	public void setExistList(List<Device> existList) {
		this.existList = existList;
	}


	public String getDeviceTypeId() {
		return deviceTypeId;
	}


	public void setDeviceTypeId(String deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
}
