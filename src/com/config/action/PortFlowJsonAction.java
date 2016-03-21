package com.config.action;

import com.base.model.IfInterface;
import com.base.service.PortService;
import com.base.util.BaseAction;
import com.config.util.GrowConfig;

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
public class PortFlowJsonAction extends  BaseAction{
	private String mesg;
	private GrowConfig growfile=new GrowConfig();
	
	public String portConfig()  throws Exception{
		String submitPort = this.getRequest().getParameter("submitPort");
		if(submitPort==null&&submitPort.trim().length()==0){
			mesg="发生未知错误，请重新设置！";
		}else{
			PortService service=new PortService();
			String a[]=submitPort.split(";");						
			if(a.length>0){
				for(int i=0;i<a.length;i++){
					if(!a[i].equals("")){
					IfInterface ifinter=new IfInterface();
					ifinter=service.findById(Long.parseLong(a[i]));	
					if(ifinter.getTrafficFlag()==1){
					ifinter.setTrafficFlag(0);
					}else{
					ifinter.setTrafficFlag(1);
					}
					service.update(ifinter);
					}
				}
			}
			growfile.WriteChangeTrifficFile();
			mesg="操作成功！";
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
