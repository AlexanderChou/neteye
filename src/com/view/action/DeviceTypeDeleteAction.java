package com.view.action;

import java.io.PrintWriter;

import org.apache.struts2.ServletActionContext;

import com.base.model.DeviceType;
import com.base.service.BaseService;
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
public class DeviceTypeDeleteAction extends  ActionSupport{ 
	private static final long serialVersionUID = 1L;
	private String submitDeviceType ;
	public String execute() throws Exception{
		if(submitDeviceType!=null && submitDeviceType.trim().length()>0){
			String a[] = submitDeviceType.split(";");
			if(a!=null){
				for(int i=0;i<a.length;i++){
					if(!a[i].equals("")){
						DeviceType deviceType = new DeviceType();
						deviceType.setId(Long.parseLong(a[i]));
						boolean isHave = DeviceService.someDeviceTypeIsHaveDevices(Long.parseLong(a[i]));
						if (!isHave) {
							BaseService.delete(deviceType);
						}
					}
				}
			}
		}
		return SUCCESS;
	}
	
	public String getSubmitDeviceType() {
		return submitDeviceType;
	}
	
	public void setSubmitDeviceType(String submitDeviceType) {
		this.submitDeviceType = submitDeviceType;
	}	
}