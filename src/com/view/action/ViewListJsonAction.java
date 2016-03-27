package com.view.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jdom.input.SAXBuilder;

import com.base.model.View;
import com.base.service.ViewService;
import com.base.util.Constants;
import com.opensymphony.xwork2.Action;
import com.view.util.JdomXML;

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
public class ViewListJsonAction{
	
	private List<View> viewList= new ArrayList<View>();
	private List<View> tempViewList= new ArrayList<View>();
	private Long viewId;
	private Long deiceId;
	private String currentview;
	

	public String execute() throws Exception{
		long currentUserId = (Long)ServletActionContext.getRequest().getSession().getAttribute("userId");
		String currentUserName = (String)ServletActionContext.getRequest().getSession().getAttribute("userName");
		tempViewList = new ViewService().getViewList();
		for(int i=0;i<tempViewList.size();i++){
//			System.out.println("tempViewList.get(i).getId("+tempViewList.get(i).getId()+"viewId"+viewId);
			if(!tempViewList.get(i).getId().equals(viewId)){
				viewList.add(tempViewList.get(i));
			}
		}
		
		View nullObj = new View();
		nullObj.setId(new Long(-1));
//		nullObj.setName("NULL");
		nullObj.setName("取消关联");
		
		nullObj.setUserId(currentUserId);
		nullObj.setUserName(currentUserName);
		viewList.add(nullObj);
		
		View view = new ViewService().findById(Long.valueOf(viewId));
		String file = Constants.webRealPath + "file/user/" + currentUserName + "_" + currentUserId + "/" +view.getName()+".xml";
		currentview=JdomXML.getvalue(file, deiceId.toString());
		return Action.SUCCESS;	
	}




	public List<View> getViewList() {
		return viewList;
	}

	public void setViewList(List<View> viewList) {
		this.viewList = viewList;
	}


	public Long getViewId() {
		return viewId;
	}


	public void setViewId(Long viewId) {
		this.viewId = viewId;
	}


	public Long getDeiceId() {
		return deiceId;
	}


	public void setDeiceId(Long deiceId) {
		this.deiceId = deiceId;
	}




	public String getCurrentview() {
		return currentview;
	}




	public void setCurrentview(String currentview) {
		this.currentview = currentview;
	}


}