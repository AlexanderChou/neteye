package com.config.action;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.dom4j.Document;

import com.base.model.IfInterface;
import com.base.model.Link;
import com.base.model.View;
import com.base.service.BaseService;
import com.base.service.IfInterfaceService;
import com.base.service.LinkService;
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
public class LinkAction extends ActionSupport implements ModelDriven<Link>,Preparable{
	private long id;
	private String submitId;
	private Link link;
	private LinkService service=new LinkService();
	private IfInterfaceService infService = new IfInterfaceService();
	
	public void prepare() throws Exception {
		if(id==0){
			link=new Link();
		}else{
			link=service.getLink(id);
		}
	}
	public String view() throws Exception{
		return "view";
	}
	public String update() throws Exception{
		service.update(link);
		return "update";
	}
	
	public String delete() throws Exception{
		LinkedHashSet<Long> linkIdSet = new LinkedHashSet<Long>();
		if(submitId!=null){
			String a[]= submitId.split(";");
			if(a!=null){
				for(int i=0;i<a.length;i++){
					if(!a[i].equals("")){
						link=service.findById(Integer.parseInt(a[i]));
						linkIdSet.add(link.getId());
						service.deleteLink(link);
						//更新端口流量采集的字段值 由1改为0
						IfInterface  downinterface = link.getDownInterface();
						IfInterface  upinterface = link.getUpInterface();
						//upinterface.setTrafficFlag(0);
						//downinterface.setTrafficFlag(0);
						//infService.saveInf(upinterface);
						//infService.saveInf(downinterface);
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
					}
				}
			}
		}
		return "delete";
	}
	public Link getModel() {
		return link;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSubmitId() {
		return submitId;
	}
	
	public void setSubmitId(String submitId) {
		this.submitId = submitId;
	}
	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}
}
