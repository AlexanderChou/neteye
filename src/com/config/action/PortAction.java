package com.config.action;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.dom4j.Document;

import com.base.model.IfInterface;
import com.base.model.Link;
import com.base.model.View;
import com.base.service.LinkService;
import com.base.service.PortService;
import com.base.service.ViewService;
import com.base.util.Constants;
import com.config.util.XMLManager;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

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
public class PortAction extends ActionSupport implements ModelDriven<IfInterface>,Preparable{
	private long id;
	private long deviceId;
	private IfInterface ifinterface;
	private String submitPort;
	private long style;
	private long deviceTypeId;
	private boolean flowFlag=false;
	private PortService service=new PortService();	
	private LinkService linkService=new LinkService();
	
	public IfInterface getModel() {
		return ifinterface;
	}
	public void prepare() throws Exception {
		if(id==0){
			ifinterface=new IfInterface();
		}else{
			ifinterface=service.findById(id);
		}
	}
	public String view() throws Exception{		
		this.setDeviceTypeId(ifinterface.getDevice().getDeviceType().getId());
		this.setDeviceId(ifinterface.getDevice().getId());
		if(ifinterface.getTrafficFlag()!=null&&ifinterface.getTrafficFlag()==1){
		this.setFlowFlag(true);
		}
		return "view";
	}
	public String update() throws Exception{
		service.update(ifinterface);
		return "update";
	}
	public String delete() throws Exception{
		LinkedHashSet<Long> linkIdSet = new LinkedHashSet<Long>();
		if(submitPort!=null){
			String a[]= submitPort.split(";");
			if(a!=null){
				for(int i=0;i<a.length;i++){
					if(!a[i].equals("")){
						ifinterface=service.findById(Integer.parseInt(a[i]));
						long portId=Long.valueOf(a[i]);
						List linkList=linkService.getLinkListByInterfId(portId);
						for(int k=0;k<linkList.size();k++){
							Link link=(Link)linkList.get(k);
							linkIdSet.add(link.getId());
							linkService.deleteLink(link);
						}
						service.delete(ifinterface);
						//获得系统中所有的视图
						List<View> views = new ViewService().getViewList();
						for(View view:views){
							Long userId = view.getUserId();
							String userName = view.getUserName();
							String viewName = view.getName();
							String path = Constants.webRealPath + "file/user/";
							String userDirectory = userName + "_" + userId;
							String fileStr = path + userDirectory + "/" + viewName + ".xml"; 
							File file = new File(fileStr);
							if(file.exists()){
								Document document = XMLManager.readXml(fileStr);
								//删除视图中相关链路
								Iterator<Long> it = linkIdSet.iterator();
								while(it.hasNext()) {
									Long linkId = it.next();
									XMLManager.deleteTag(document,"links","link","id",linkId.toString());
								}
								XMLManager.writeXml(document, fileStr);
							}
						}//for(View view:views)
					}//if(!a[i].equals(""))
				}//for(int i=0;i<a.length;i++)
			}//if(a!=null)
		}
		return "delete";
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}
	public IfInterface getIfinterface() {
		return ifinterface;
	}
	public void setIfinterface(IfInterface ifinterface) {
		this.ifinterface = ifinterface;
	}
	public String getSubmitPort() {
		return submitPort;
	}
	public void setSubmitPort(String submitPort) {
		this.submitPort = submitPort;
	}
	public long getStyle() {
		return style;
	}
	public void setStyle(long style) {
		this.style = style;
	}
	public long getDeviceTypeId() {
		return deviceTypeId;
	}
	public void setDeviceTypeId(long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
	public boolean isFlowFlag() {
		return flowFlag;
	}
	public void setFlowFlag(boolean flowFlag) {
		this.flowFlag = flowFlag;
	}
	public static void main(String args[])throws Exception{
		PortService service=new PortService();
		IfInterface ifinterface=service.findById(333);
		System.out.println(ifinterface.getTrafficFlag());
	}
}
