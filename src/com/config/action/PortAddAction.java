package com.config.action;

import com.base.model.Device;
import com.base.model.IfInterface;
import com.base.service.DeviceService;
import com.base.service.PortService;
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
public class PortAddAction extends  ActionSupport{
	private long deviceId;
	
	private String ports;
	
	private long style;
	
	private PortService service=new PortService();
	
	private DeviceService serv =new DeviceService();
	
	public String execute() throws Exception{
			String a[]=(ports.trim()).split("\n");
			if(a!=null){
				for(int i=0;i<a.length;i++){
					if(!a[i].trim().equals("")){
						IfInterface ifinterface=new IfInterface();
						String b[]=(a[i]+" ").split("\\|");
						if(!b[0].trim().equals("")){
							ifinterface.setDescription(b[0].trim());
						}
						if(!b[1].trim().equals("")){
							ifinterface.setChineseName(b[1].trim());
						}	
						if(!b[2].trim().equals("")){
							ifinterface.setIfindex(b[2].trim());
						}
						if(!b[3].trim().equals("")){
							ifinterface.setIpv4(b[3].trim());						
						}										
						if(!b[4].trim().equals("")){
								ifinterface.setIpv6(b[4].trim());							
						}
						if(!b[5].trim().equals("")){
							ifinterface.setMaxSpeed(Double.valueOf((b[5].trim())));							
					}
						ifinterface.setTrafficFlag(1);
						Device device=new Device();
						device=serv.findById(deviceId);
						ifinterface.setDevice(device);
						service.save(ifinterface);
					}
					
				}
			}
			return null;	
		
	}

	public long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}

	public String getPorts() {
		return ports;
	}

	public void setPorts(String ports) {
		this.ports = ports;
	}

	public long getStyle() {
		return style;
	}

	public void setStyle(long style) {
		this.style = style;
	}
}
