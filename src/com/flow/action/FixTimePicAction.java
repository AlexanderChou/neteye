package com.flow.action;

import java.io.File;
import java.sql.Timestamp;
import java.util.Calendar;

import com.base.util.Constants;
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
 * <p>Title: 生成最近时段的流量图</p>
 * <p>Description: 以当前时间为基准，生成相应的日、周、月、年流量图</p>
 * @version 1.0
 * @author 郭玺
 * <p>Company: 网络中心</p>
 * <p>Copyright: Copyright (c) 2009</p>
 */
public class FixTimePicAction extends  ActionSupport{ 
	private String routerIP;
	private String ifIndex;
	private String picType;
	private String trafficIP;
	private int flag = 0;//0：生成固定时段端口的日、周、月、年流量图 1：生成固定时段cpu|mem|temperature的日、周、月、年流量图
	public String execute() throws Exception {
		/*/取当前时间  -----该命令暂时不用了
		String time = this.getCurrentTime();
		String[] timeArray = time.split("-");
		int year = Integer.parseInt(timeArray[0]);
		int month = Integer.parseInt(timeArray[1]);
		int day = Integer.parseInt(timeArray[2]);
		//调用后台perl程序，生成年、月、周、日图片
		String[] cmd = new String[3];
		if(picType.trim().equals("memusage") || picType.trim().equals("cupusage") || picType.trim().equals("temperature")){
			cmd[0] = "get_fix_phypic " + picType +" "+ routerIP + " "+ ifIndex + " " + year + " " + month + " " + day;
			flag = 1;
		}else{
			cmd[0] = "get_fix_pic " + picType +" "+ routerIP + " "+ ifIndex + " " + year + " " + month + " " + day;
		}
		try{ 
			Process ps=java.lang.Runtime.getRuntime().exec(cmd[0] + " 2>1 >/dev/null &"); 
			ps.waitFor();
			ps.getErrorStream();
		}catch(java.io.IOException   e){
			e.printStackTrace();             
		} */
		return SUCCESS;
	}
	private String getCurrentTime() {
		Timestamp ts = new Timestamp(Calendar.getInstance().getTimeInMillis());
		return ts.toString().substring(0, 10);
	}
	public String getPicType() {
		return picType;
	}
	public void setPicType(String picType) {
		this.picType = picType;
	}
	public String getTrafficIP() {
		return trafficIP;
	}
	public void setTrafficIP(String trafficIP) {
		this.trafficIP = trafficIP;
	}
	public String getRouterIP() {
		return routerIP;
	}
	public void setRouterIP(String routerIP) {
		this.routerIP = routerIP;
	}
	public String getIfIndex() {
		return ifIndex;
	}
	public void setIfIndex(String ifIndex) {
		this.ifIndex = ifIndex;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
}
