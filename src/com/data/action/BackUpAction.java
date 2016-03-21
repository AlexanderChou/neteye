package com.data.action;


import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.base.service.DataManageService;
import com.base.util.BaseAction;
import com.base.util.Constants;
import com.base.util.JDOMXML;

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
public class BackUpAction extends BaseAction {	
	private String mesg;

	public String backUp() throws Exception {
		String backUpName = this.getRequest().getParameter("backUpName");
		String userName=ServletActionContext.getRequest().getSession().getAttribute("userName").toString();
		String filePath=Constants.BACKUP_PATH+"/backup/custom/";		
		File file = new File(filePath);
		if(!file.exists()){
			file.mkdirs();
		}
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("backup");
		Element results = root.addElement("style");
		Element result = results.addElement("name");
		result.addText(backUpName);
		Element user= results.addElement("userName");
		user.addText(userName);
		Element time= results.addElement("time");
		Date date =new Date();
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);		
		time.addText(dateFormat.format(date));
		JDOMXML.saveXml(filePath+ backUpName + ".xml", document);
		DataManageService service=new DataManageService();
		boolean flag=service.backupDataBase("0",backUpName);
		File backFile=new File(filePath+ backUpName + ".xml");
		PrintWriter write = this.getResponse().getWriter();
		if(flag&&backFile.exists()){
			write.print("ok");
			write.close();
		}else{
			write.print("no");
			write.close();
		}
		return SUCCESS;
	}
	
	public String config() throws Exception {		
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyyMMddhhmmss");
		String dateTime = format1.format(new Date()).trim();
		String name = "backUp" + dateTime;
		ServletActionContext.getRequest().setAttribute("name", name);
		return SUCCESS;
	}		
	
	public String checkName() throws Exception {
		String name = this.getRequest().getParameter("name");
		String filePath=Constants.BACKUP_PATH+"/backup/custom/";
		boolean isHave = false;
		File file = new File(filePath);
		 if (file.exists()&&file.isDirectory()) {
			 String[] filelist = file.list();
			 for (int i = 0; i < filelist.length; i++) {
		          File fileName = new File(filePath + "/" + filelist[i]);
		          if (!fileName.isDirectory()) {
		            if(fileName.getName().equals(name+".xml")){
		            	isHave=true;
		            	break;
		            }
		          }
			 }
		 }
		 PrintWriter write = this.getResponse().getWriter();
		if (isHave) {			
			write.print("none");
			write.close();
		} else {
			write.print("ok");
			write.close();
		}
		return SUCCESS;
	}
	public String recover()throws Exception{
		String backUpName = this.getRequest().getParameter("backUpName");
		DataManageService service=new DataManageService();
		String filePath=Constants.BACKUP_PATH+"/backup/custom/"+backUpName;
		File file=new File(filePath);
		boolean flag=false;
		if(file.isDirectory()){
			flag=service.restoreDatabase("0",backUpName);
		}
		if(flag){
			mesg="备份成功！";
		}else{
			mesg="备份失败！";
		}
		return SUCCESS;
	}
	public String getMesg() {
		return mesg;
	}

	public void setMesg(String mesg) {
		this.mesg = mesg;
	}	
}
