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
 * <p>Title: 保存短信配置信息</p>
 * <p>Description: 把新的短信配置信心保存在message.xml文件中</p>
 * @version 1.0
 * @author sunlujing
 * <p>Company: 网络中心</p>
 * <p>Copyright: Copyright (c) 2009</p>
 */
public class MessageInfoSaveAction extends  ActionSupport{
	private String srcNumber;
	private String passwd;
	private String recNumber[];
    
	public String execute() throws Exception{
		Document doc = JDOMXML.readXML(Constants.webRealPath+"/file/event/message.xml");
		JDOMXML.setNodeValues(doc, "/email/datas/data/sendMobile", srcNumber);
		JDOMXML.setNodeValues(doc, "/email/datas/data/sendPasswd", passwd);
		JDOMXML.setNodeValues(doc, "/email/datas/data/receives", recNumber);
		JDOMXML.saveXml(Constants.webRealPath+"/file/event/message.xml", doc);
		//跳转逻辑
		ServletActionContext.getRequest().setAttribute("type", 2);
		return SUCCESS;
	}
	
    public String getSrcNumber() {
		return srcNumber;
	}

	public void setSrcNumber(String srcNumber) {
		this.srcNumber = srcNumber;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String[] getRecNumber() {
		return recNumber;
	}

	public void setRecNumber(String[] recNumber) {
		this.recNumber = recNumber;
	}

	public static void main(String args[]){
    	 Document doc = JDOMXML.readXML("WebContent/file/event/email.xml");
    	 JDOMXML.setNodeValues(doc,"/email/datas/data/userName", "sssun");
    	 JDOMXML.saveXml("WebContent/file/event/email.xml", doc);
    }
	
}
