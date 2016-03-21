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
import com.base.util.BaseAction;
import com.base.util.Constants;
import com.data.util.PageInfo;
import com.fault.dto.FaultNode;

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
public class NodeStatusAction  extends BaseAction{
	private String totalCount="0";
	private List nodeList;
	private DeviceService service=new DeviceService();
	
	public String nodeLists() throws Exception{
		String type = this.getRequest().getParameter("type");
		nodeList=new ArrayList();
		List tempList=new ArrayList();
		Hashtable active = new Hashtable();
		List nodeIdList=new ArrayList();
		String filePath = Constants.webRealPath+"file/fault/dat";
		File activeFile=new File(filePath+"/active");
		if(activeFile.exists()){
			FileReader fr;
			try {
				fr = new FileReader(activeFile);
				BufferedReader br = new BufferedReader(fr);
	            String myreadline; 
	            while (br.ready()) {
	                myreadline = br.readLine();//读取一行
	                active.put(myreadline, "1");
	            }
	            br.close();
	            fr.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//创建FileReader对象，用来读取字符流
            
		}
		Hashtable unuse = new Hashtable();
		File unuseable=new File(filePath+"/unuseableiplist");
		if(unuseable.exists()){
			FileReader fr;
			try {
				fr = new FileReader(unuseable);
				BufferedReader br = new BufferedReader(fr);
	            String myreadline; 
	            while (br.ready()) {
	                myreadline = br.readLine();//读取一行
	                unuse.put(myreadline, "1");
	            }
	            br.close();
	            fr.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//创建FileReader对象，用来读取字符流
            
		}	
		Hashtable lossrtt = new Hashtable();
		File lossrttFile=new File(filePath+"/lossrttlist");
		if(lossrttFile.exists()){
			FileReader fr;
			try {
				fr = new FileReader(lossrttFile);
				BufferedReader br = new BufferedReader(fr);
	            String myreadline; 
	            while (br.ready()) {
	                myreadline = br.readLine();//读取一行
	                lossrtt.put(myreadline.substring(0,myreadline.indexOf("|")), myreadline.substring(myreadline.indexOf("|")+1, myreadline.lastIndexOf("|")));
	            }
	            br.close();
	            fr.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//创建FileReader对象，用来读取字符流
            
		}	
		nodeIdList=service.getAllDevice();
		if(type!=null&&Integer.parseInt(type)!=6){
			if(nodeIdList!=null){
				Hashtable verify=new Hashtable();
				for(int i=0;i<nodeIdList.size();i++){
					FaultNode node=new FaultNode();
					Device device=(Device) nodeIdList.get(i);
					if(device!=null){
						if(device.getLoopbackIP()!=null&&!device.getLoopbackIP().equals("")){
							if(verify.get(device.getLoopbackIP())==null){
								verify.put(device.getLoopbackIP(), "1");
								node.setId(device.getId());
								node.setIp(device.getLoopbackIP());
								node.setIp6("--");
								if(device.getLoopbackIPv6()!=null){
									node.setIp6(device.getLoopbackIPv6());
								}
								
								if(device.getChineseName()!=null&&!device.getChineseName().equals("")){
									node.setName(device.getChineseName());
								}else{
									node.setName(device.getName());
								}
								if(device.getLoopbackIP()!=null){
									if(active.get(device.getLoopbackIP())!=null){
										node.setStatus(0);
										node.setLoss("100");
										node.setRrt("~~");
									}else if(unuse.get(device.getLoopbackIP())!=null){
										node.setStatus(3);
									}else if(lossrtt.get(device.getLoopbackIP())!=null){
										node.setStatus(2);
										String temp=(String)lossrtt.get(device.getLoopbackIP());
										String a[]=temp.split("\\|");
										if(a!=null){
											node.setLoss(a[0]);
											node.setRrt(a[1]);
										}
									}else{
										node.setStatus(1);
									}
								}
								tempList.add(node);
							}
							if(device.getDeviceType().getId()<=2){
								PortService portService=new PortService();
								List portList=portService.getPortList(device.getId());
								if(portList!=null){
									for(int j=0;j<portList.size();j++){									
										IfInterface port=(IfInterface) portList.get(j);
										if(port.getIpv4()!=null&&!port.getIpv4().equals("")){
											if(verify.get(port.getIpv4())==null){
												verify.put(port.getIpv4(), "1");
											FaultNode portNode=new FaultNode();
											portNode.setIp(port.getIpv4());
											portNode.setIp6("--");
											if(port.getIpv6()!=null){
												portNode.setIp6(port.getIpv6());
											}
											if(port.getChineseName()!=null){
												portNode.setName(port.getChineseName());
											}
											if(active.get(port.getIpv4())!=null){
												portNode.setStatus(0);
												portNode.setLoss("100");
												portNode.setRrt("~~");
											}else if(unuse.get(port.getIpv4())!=null){
												portNode.setStatus(3);
											}else if(lossrtt.get(port.getIpv4())!=null){
												portNode.setStatus(2);
												String temp=(String)lossrtt.get(port.getIpv4());
												String a[]=temp.split("\\|");
												if(a!=null){
													portNode.setLoss(a[0]);
													portNode.setRrt(a[1]);
												}
											}else{
												portNode.setStatus(1);
											}
											tempList.add(portNode);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}else{
			if(nodeIdList!=null){
				Hashtable verify=new Hashtable();
				for(int i=0;i<nodeIdList.size();i++){
					Device device=(Device) nodeIdList.get(i);
					if(device!=null){
						if(device.getLoopbackIPv6()!=null&&!device.getLoopbackIPv6().equals("")){
							if(verify.get(device.getLoopbackIPv6())==null){
									verify.put(device.getLoopbackIPv6(), "1");
									FaultNode node=new FaultNode();							
									node.setId(device.getId());
									node.setIp("--");
									if(device.getLoopbackIP()!=null){
						                 node.setIp(device.getLoopbackIP());
									}
									node.setIp6(device.getLoopbackIPv6());
									if(device.getChineseName()!=null&&!device.getChineseName().equals("")){
										node.setName(device.getChineseName());
									}else{
										node.setName(device.getName());
									}
									if(active.get(device.getLoopbackIPv6())!=null){
										node.setStatus(0);
										node.setLoss("100");
										node.setRrt("~~");
									}else if(unuse.get(device.getLoopbackIPv6())!=null){
										node.setStatus(3);
									}else if(lossrtt.get(device.getLoopbackIPv6())!=null){
										node.setStatus(2);
										String temp=(String)lossrtt.get(device.getLoopbackIPv6());
										String a[]=temp.split("\\|");
										if(a!=null){
											node.setLoss(a[0]);
											node.setRrt(a[1]);
										}
									}else{
										node.setStatus(1);
									}
									tempList.add(node);
							}
							if(device.getDeviceType().getId()<=2){
								PortService portService=new PortService();
								List portList=portService.getPortList(device.getId());
								if(portList!=null){
									for(int j=0;j<portList.size();j++){
										IfInterface port=(IfInterface) portList.get(j);
										if(port.getIpv6()!=null&&!port.getIpv6().equals("")){
											if(verify.get(port.getIpv6())==null){
												verify.put(port.getIpv6(), "1");
												FaultNode portNode=new FaultNode();
												portNode.setIp("--");
												if(port.getIpv4()!=null){
													portNode.setIp(port.getIpv4());
												}
												portNode.setIp6(port.getIpv6());
												if(port.getChineseName()!=null){
													portNode.setName(port.getChineseName());
												}
												if(active.get(port.getIpv6())!=null){
													portNode.setStatus(0);
													portNode.setLoss("100");
													portNode.setRrt("~~");
												}else if(unuse.get(port.getIpv6())!=null){
													portNode.setStatus(3);
												}else if(lossrtt.get(port.getIpv6())!=null){
													portNode.setStatus(2);
													String temp=(String)lossrtt.get(port.getIpv6());
													String a[]=temp.split("\\|");
													if(a!=null){
														portNode.setLoss(a[0]);
														portNode.setRrt(a[1]);
													}
												}else{
													portNode.setStatus(1);
												}
												tempList.add(portNode);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		String start = this.getRequest().getParameter("start");
		String limit = this.getRequest().getParameter("limit");
		
		if(tempList!=null){
			totalCount=Integer.toString(tempList.size());
		}
		PageInfo page=new PageInfo();
		page.setStart(start);
		page.setLimit(limit);
		page.setResult(tempList);
		nodeList=page.getPageResult();
		return SUCCESS;
	}

	public List getNodeList() {
		return nodeList;
	}

	public void setNodeList(List nodeList) {
		this.nodeList = nodeList;
	}
	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
}
