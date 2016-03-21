package com.fault.action;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

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
 * 用于故障的显示
 * @author 李宪亮
 *
 */

public class FaultStaticListAction extends ActionSupport {
	
	private String startTime;
	private String endTime;
	private String viewName;
	private String ip;
	private List<String[]> faults;
	
	public String execute() throws Exception {
		
		faults = new ArrayList<String[]>();
		String rootPath = Constants.webRealPath + "file/fault/dat/faultstats/";		
		String filePath = rootPath + "troublemonitor_Stat_" + startTime + "--" + endTime  ;
		if (StringUtils.isNotEmpty(ip)) {
			filePath += "--" + ip+ ".txt";			
		}else if(StringUtils.isNotEmpty(viewName)){
			filePath += "--" + viewName+ ".txt";
		}
		this.getDatasFromFile(filePath, faults);
		
		return super.execute();
	}

	/**
	 * 阅读故障信息
	 * @param filePath 文件位置
	 * @param flows 数据存放集合
	 */
	public void getDatasFromFile(String filePath, List<String[]> faults) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
			String line = reader.readLine();
			boolean firstLine = true;
			while (StringUtils.isNotEmpty(line)) {
				if (firstLine) {
					firstLine = false;
					line = reader.readLine();
					continue;
				}
				if (StringUtils.isNotEmpty(line)) {
					String[] strArr = line.split("\t");
					faults.add(strArr);
				}
				line = reader.readLine();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	public List<String[]> getFaults() {
		return faults;
	}


	public void setFaults(List<String[]> faults) {
		this.faults = faults;
	}
}
