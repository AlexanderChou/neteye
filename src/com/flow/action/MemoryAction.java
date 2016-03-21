package com.flow.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Hashtable;
import java.util.Vector;

import com.base.model.Device;
import com.base.service.DeviceService;
import com.base.util.Constants;
import com.flow.dao.FlowDao;
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
public class MemoryAction extends ActionSupport{
	private Vector memory = new Vector();	
	public String execute() throws Exception{
		DeviceService dservice=new DeviceService();
		Hashtable deviceHT= new Hashtable();
		FlowDao dao=new FlowDao();
		File name = new File( Constants.webRealPath+"/file/flow/physicalscan/device" );
		//File name = new File( "D:/flow/device" );
		Hashtable table = new Hashtable();	
		if(name.exists()){			
			deviceHT=dao.getDevice(1);
			BufferedReader input = new BufferedReader( new FileReader( name ) );
			String text;					
			while ( ( text = input.readLine() ) != null )
			{				
				if(!text.equals("")&&text.contains("|")){
					String a[]=text.split("\\|");
					String url="";
					String url2="";
					if(a[2].equals("Huawei")){
					 url=a[0]+"_8704_";
					 url2=a[0]+"_9216_";
					}
					Device device=(Device)deviceHT.get(a[0]);
					String DeviceName="";
					if(device!=null){
							if(device.getChineseName()!=null){
								DeviceName=device.getChineseName()+"-"+a[1];
							}else if(device.getName()!=null){
								DeviceName=device.getName()+"-"+a[1];
							}else{
								DeviceName=a[0];
							}
						}
					if(!url.equals("")){
					table.put(url,DeviceName);	
					table.put(url2,DeviceName);
					}
				 }
				}
			
			input.close();
		}
		memory.add(table);
		return SUCCESS;
	}
	public Vector getMemory() {
		return memory;
	}
	public void setMemory(Vector memory) {
		this.memory = memory;
	}
}
