package com.view.action;

import org.apache.struts2.json.annotations.JSON;

import com.base.model.View;
import com.base.service.ViewService;
import com.base.util.Constants;
import com.base.util.JDOMXML;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

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
public class ViewAddJsonAction{
	private String name;
	private String description;
	private String success;
	private String failure;
	private String data;
	private String homePage;
	
	@JSON(serialize=false)
	public String execute() throws Exception{
		//添加视图
		ViewService service=new ViewService();
		boolean result=service.isExistByName(name.trim());
		if(!result){
			View view = new View(); 
			if(homePage.trim().equals("yes")){
				view.setHomePage(Boolean.TRUE);
			}else{
				view.setHomePage(Boolean.FALSE);
			}
			view.setDescription(description.trim());
			view.setName(name.trim());
			Long userId = (Long)ActionContext.getContext().getSession().get("userId");
			String userName = (String)ActionContext.getContext().getSession().get("userName");
			view.setUserId(userId);
			view.setUserName(userName);
			try{
				service.addViewByCondition(view);
				
				StringBuffer xml = new StringBuffer();
				xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\n");
				xml.append("<to:view xmlns:to=\"http://www.inetboss.com/view\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.inetboss.com/view /file/view/schema/ViewSchema.xsd\">").append("\n");
				
				xml.append("<to:backGround>").append("images/china.jpg").append("</to:backGround>").append("\n");
				xml.append("</to:view>").append("\n");
				//将文件存入file/user目录下
				String path = Constants.webRealPath + "file/user/" + userName + "_" + userId + "/";
				JDOMXML.writeXML(path+name+".xml",xml.toString());
				
				success = "true";
				failure = "false";
			}catch(Exception e){
				e.printStackTrace();
				success = "false";
				failure = "true";
			}
			data = name;
			return Action.SUCCESS;
		}else{
			failure = "true";
			data="添加视图名字重名！";
			return Action.SUCCESS;
		}
	}
	
	
	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getFailure() {
		return failure;
	}

	public void setFailure(String failure) {
		this.failure = failure;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}
}
