package com.view.action;

import java.io.File;

import com.base.model.View;
import com.base.service.ViewService;
import com.base.util.Constants;
import com.opensymphony.xwork2.ActionSupport;
import com.view.dao.ViewDAO;

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
public class ViewDeleteAction extends  ActionSupport{ 
	private String submitView;
	private ViewService service = new ViewService();	
	public String execute() throws Exception{
		if(submitView.trim().length()>0){
			String a[] = submitView.split(";");
			if(a!=null){
				for(int i=0;i<a.length;i++){
					if(!a[i].equals("")){
						String id = a[i].substring(a[i].lastIndexOf("_")+1);
						View view =(View) service.findById(Integer.parseInt(id));
						String path = Constants.webRealPath + "file/user/" + view.getUserName() + "_" + view.getUserId() + "/";
						File file=new File(path+view.getName()+".xml");
						if(file.exists()){
							if(file.delete()){
								service.delete(view);
							}else{
								addActionMessage("删除文件失败！");
							}
						}else{
							service.delete(view);
						}
						
					}
				}
			}
		}
		return SUCCESS;
	}
	public String getSubmitView() {
		return submitView;
	}
	public void setSubmitView(String submitView) {
		this.submitView = submitView;
	}
	public ViewService getService() {
		return service;
	}
	public void setService(ViewService service) {
		this.service = service;
	}
	}