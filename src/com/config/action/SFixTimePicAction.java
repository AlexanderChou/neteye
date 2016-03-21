package com.config.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.config.dto.Server;
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

public class SFixTimePicAction extends  ActionSupport{ 
	private String url;
	private String type;
	private int flag = 0;
	private String style;
	private String ip;
	private List urlList;
	public String execute() throws Exception {
		if(type.equals("snmp")){
			flag=1;
			urlList=new ArrayList();
			File fileDir=new File("/opt/NetEye/file/service/dat/timetypepic/");
			String[] filelist = fileDir.list();
			 for (int i = 0; i < filelist.length; i++) {
		          File delfile = new File( filelist[i]);
		          if (!delfile.isDirectory()) {        	  
		        	  if(delfile.getName().contains(url)&&delfile.getName().contains(style)&&delfile.getName().contains("day")){
		        		  Server server=new Server();
		        		  server.setUrl(delfile.getName().substring(0,delfile.getName().lastIndexOf("_")));
			              urlList.add(server);
		        	  }
		          }
		        }

		}
		return SUCCESS;
	}
	
	
	public List getUrlList() {
		return urlList;
	}


	public void setUrlList(List urlList) {
		this.urlList = urlList;
	}


	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	public String getStyle() {
		return style;
	}


	public void setStyle(String style) {
		this.style = style;
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}
}
