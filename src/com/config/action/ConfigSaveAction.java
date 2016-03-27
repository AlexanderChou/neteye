package com.config.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.base.model.Device;
import com.base.service.DeviceService;
import com.base.service.DeviceTypeService;
import com.base.util.BaseAction;
import com.base.util.Constants;
import com.config.dao.DeviceDAO;

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
public class ConfigSaveAction extends BaseAction {
	private String deviceArr;
	private DeviceService service=new DeviceService();	
	public String execute() throws Exception{
		if(deviceArr!=null && deviceArr.trim().length()>0){
			String a[]=(deviceArr.trim()).split("\\|");
			if(a!=null){
			List content=new ArrayList();
			DeviceDAO dao=new DeviceDAO();
			DeviceTypeService typeService=new DeviceTypeService();
				for(int i=0;i<a.length;i++){
					if(!a[i].trim().equals("")){
						String b[]=(a[i]+" ").split(";");
						if(b.length>0){
							if(Integer.parseInt(b[4].trim())==0){
								continue;
							}else if(Integer.parseInt(b[4].trim())==1){
								if(!b[1].trim().equals("")){
									Device device=service.findDeviceByIP(b[1]);
									if(!b[0].trim().equals("")){
										device.setChineseName(b[0]);
									}
									if(!b[2].trim().equals("")){
										device.setReadCommunity(b[2]);
									}
									if(!b[3].trim().equals("")){
										device.setSnmpVersion(b[3]);
									}
									service.update(device);
								}
							}else if(Integer.parseInt(b[4].trim())==2){								
								if(!b[1].trim().equals("")){									
									dao.deleteDevice(service.findDeviceByIP(b[1]).getId());
									String deviceTypeId=Long.toString( typeService.getId(b[5].trim()));
									content.add(deviceTypeId+"//"+b[1].trim() + "//" + b[2].trim() + "// " + b[0].trim());								
								}
							}
						}
					}
				}
				if(content!=null&&content.size()>0){
					dao.topoSaveInitText(content, "temp", true);
					String textDataFilePath = Constants.webRealPath + "file/topo/data/temp.txt";
					File txtFile = new File(textDataFilePath);
					if (txtFile.exists()) {
						String cmd = "initial --file " + textDataFilePath +" 2>1 >/dev/null &";
						try{ 
							Process ps=java.lang.Runtime.getRuntime().exec(cmd);  
							ps.getErrorStream();
							ps.waitFor();
//						    txtFile.delete();
						}catch(java.io.IOException e){
							e.printStackTrace();             
						}
					}
				}
			}
		}			
			
		return SUCCESS;		
	}
	
	public String getDeviceArr() {
		return deviceArr;
	}

	public void setDeviceArr(String deviceArr) {
		this.deviceArr = deviceArr;
	}
}