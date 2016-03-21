package com.fault.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.base.model.Device;
import com.base.model.IfInterface;
import com.base.service.DeviceService;
import com.base.service.PortService;
import com.base.util.Constants;
import com.fault.dto.FaultNode;
import com.fault.util.ReadXML;
import com.opensymphony.xwork2.Action;
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
public class StatusAction  extends ActionSupport{
	private String viewName;
	private List nodeList;
	private DeviceService service=new DeviceService();
	private Hashtable result = new Hashtable();
	
	public String execute() throws Exception{
		nodeList=new ArrayList();
		Hashtable active = new Hashtable();
		List nodeIdList=new ArrayList();
		String filePath = Constants.webRealPath+"file/fault";
		File fileResult=new File(filePath+"/active");
		if(fileResult.exists()){
			FileReader fr;
			try {
				fr = new FileReader(fileResult);
				BufferedReader br = new BufferedReader(fr);
	            String myreadline; 
	            while (br.ready()) {
	                myreadline = br.readLine();//读取一行
	                active.put(myreadline, "1");
	            }
	            br.close();
	            fr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}//创建FileReader对象，用来读取字符流
            
		}			
		if(viewName!=null){
			String name[]=viewName.split("|");
			//for(int i=0;i<name.length;i++){			
				ReadXML readXML=new ReadXML();
				nodeIdList=readXML.getNode(viewName);
			//}
			if(nodeIdList!=null){
				for(int i=0;i<nodeIdList.size();i++){
					FaultNode node=new FaultNode();
					String id=(String)nodeIdList.get(i);
					Device device=(Device) service.findById(Long.valueOf(id));
					if(device!=null){
						node.setId(Long.valueOf(id));
						node.setIp(device.getLoopbackIP());
						if(device.getChineseName()!=null&&!device.getChineseName().equals("")){
							node.setName(device.getChineseName());
						}else{
							node.setName(device.getName());
						}
						if(active.get(device.getLoopbackIP())!=null){
							node.setStatus(0);
						}else{
							node.setStatus(1);
						}
						if(device.getDeviceType().getId()<=2){
							node.setType(1);
							PortService portService=new PortService();
							List portList=portService.getPortList(Long.valueOf(id));
							List portNodeList=new ArrayList();
							if(portList!=null){
								for(int j=0;j<portList.size();j++){
									IfInterface port=(IfInterface) portList.get(j);
									if(port.getIpv4()!=null&&!port.getIpv4().equals("")){
										FaultNode portNode=new FaultNode();
										portNode.setIp(port.getIpv4());
										if(port.getChineseName()!=null){
											portNode.setName(port.getChineseName());
										}
										if(active.get(port.getIpv4())!=null){
											portNode.setStatus(0);
										}else{
											portNode.setStatus(1);
										}
										portNodeList.add(portNode);
									}
								}
								result.put(id, portNodeList);
							}
						}else{
							node.setType(0);
						}
						nodeList.add(node);
					}
				}
			}
		}
		return Action.SUCCESS;
	}
	public List getNodeList() {
		return nodeList;
	}

	public void setNodeList(List nodeList) {
		this.nodeList = nodeList;
	}

	public Hashtable getResult() {
		return result;
	}

	public void setResult(Hashtable result) {
		this.result = result;
	}
}
