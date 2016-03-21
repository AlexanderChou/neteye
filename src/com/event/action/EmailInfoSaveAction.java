package com.event.action;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;

import com.base.util.Constants;
import com.base.util.JDOMXML;
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
/**
 * <p>Title: 保存邮件配置信息</p>
 * <p>Description: 把新的邮件配置信心保存在eamil.xml文件中</p>
 * @version 1.0
 * @author sunlujing
 * <p>Company: 网络中心</p>
 * <p>Copyright: Copyright (c) 2009</p>
 */
public class EmailInfoSaveAction extends  ActionSupport{
	private String serverName;
	private String userName;
	private String passwd;
	private String srcEmailAddress;
	private String recEmailAddress[];
    
	public String execute() throws Exception{
		Document doc = JDOMXML.readXML(Constants.webRealPath+"/file/event/email.xml");
		JDOMXML.setNodeValues(doc, "/email/datas/data/userName", userName);
		JDOMXML.setNodeValues(doc, "/email/datas/data/smtp", serverName);
		JDOMXML.setNodeValues(doc, "/email/datas/data/userPasswd", passwd);
		JDOMXML.setNodeValues(doc, "/email/datas/data/sendEmail", srcEmailAddress);
		JDOMXML.setNodeValues(doc, "/email/datas/data/receiveEmails",recEmailAddress );
		JDOMXML.saveXml(Constants.webRealPath+"/file/event/email.xml", doc);
		//跳转逻辑
		ServletActionContext.getRequest().setAttribute("type", 1);
		return SUCCESS;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getSrcEmailAddress() {
		return srcEmailAddress;
	}
	public void setSrcEmailAddress(String srcEmailAddress) {
		this.srcEmailAddress = srcEmailAddress;
	}
	public String[] getRecEmailAddress() {
		return recEmailAddress;
	}
	public void setRecEmailAddress(String[] recEmailAddress) {
		this.recEmailAddress = recEmailAddress;
	}
    public static void main(String args[]){
    	 Document doc = JDOMXML.readXML("WebContent/file/event/email.xml");
    	 JDOMXML.setNodeValues(doc,"/email/datas/data/userName", "sssun");
    	 JDOMXML.saveXml("WebContent/file/event/email.xml", doc);
    }
	
}
