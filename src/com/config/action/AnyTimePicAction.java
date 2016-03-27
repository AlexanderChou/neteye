package com.config.action;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Calendar;

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
 * <p>Title: 生成任意时段的流量图</p>
 * <p>Description: 针对用户的输入，生成相应时段的流量图</p>
 * @version 1.0
 * @author 郭玺
 * <p>Company: 网络中心</p>
 * <p>Copyright: Copyright (c) 2009</p>
 */
public class AnyTimePicAction extends  ActionSupport{ 
	private String url;
	private String type;
	private String time;
	private String length;
	private String success;
	private String failure;
	private String style;
	
	public String execute() throws Exception{
		//用户输入的任一时间段
		if(time==null || "".equals(time)){
			time = this.getCurrentTime();
		}
		//调用后台perl程序，生成年、月、周、日图片
		String cmd = new String();
		if(type.equals("snmp")){
			cmd = "/usr/bin/get_pic_select_time  --ip " + url + " --type " + style + " --time " + time + " --offset " + length;
		}else{
			String a[]=url.split("_");
			cmd = "/usr/bin/get_pic_select_time  --ip " + a[0] + " --type " + type +" --port "+a[1]+ " --time " + time + " --offset " + length;	
		}
		Process ps=java.lang.Runtime.getRuntime().exec(cmd);  
		ps.waitFor();
		
		return SUCCESS;
	}
	private String getCurrentTime() {
		Timestamp ts = new Timestamp(Calendar.getInstance().getTimeInMillis());
		return ts.toString().substring(0, 10);
	}

	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
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
}
