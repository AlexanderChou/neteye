package com.flow.action;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.base.util.Constants;
import com.flow.dao.FlowDao;
import com.flow.dto.Statistic;
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
 * <p>Title: 流量统计</p>
 * <p>Description: 根据用户的输入，对流量进行统计，并将统计结果输出到页面</p>
 * @version 1.0
 * @author 郭玺
 * <p>Company: 网络中心</p>
 * <p>Copyright: Copyright (c) 2009</p>
 */
public class FlowStatisticAction extends  ActionSupport{
	private List<Statistic> flows;
	private String start;
	private String limit;
	private int totalCount;
	private String startTime;
	private String endTime;
	private String ip;
	private String ifindex;
	private String viewName;

	public String execute() throws Exception {
		flows = new ArrayList<Statistic>();
		String rootPath = Constants.webRealPath + "file/flow/flowscan/dat/flowstats/";
		String filePath = rootPath + "performance_Stat_" + startTime.replace("-", "") + "--" + endTime.replace("-", "") ;
		if (StringUtils.isNotEmpty(ip)) {
			filePath += "--" + ip;
			if (StringUtils.isNotEmpty(ifindex)) {
				filePath += "--" + ifindex;
			}
		}else if(StringUtils.isNotEmpty(viewName)){
			filePath += "--" + viewName;
		}
		filePath += ".txt";
		this.getDatasFromFile(filePath, flows);
		return SUCCESS;
	}
	
	/**
	 * 阅读流量信息
	 * @param filePath 文件位置
	 * @param flows 数据存放集合
	 */
	
	public void getDatasFromFile(String filePath, List<Statistic> flows) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
			String line = reader.readLine();
			FlowDao flowDao = new FlowDao();
			boolean firstList = true;
			while (StringUtils.isNotEmpty(line)) {
				if (firstList) {
					firstList = false;
					line = reader.readLine();
					continue;
				}
				Statistic statistic = new Statistic();
				String[] strArr = line.split("\t");
				statistic.setEndTime(strArr[11]);
				statistic.setStartTime(strArr[10]);
				statistic.setIfIndex(Integer.parseInt(strArr[1]));
				statistic.setLinkName(flowDao.getLinkName(strArr[0], strArr[1]));
				statistic.setMaxFlow(strArr[6] + " / " + strArr[7]);
				statistic.setMinFlow(strArr[8] + " / " + strArr[9]);
				statistic.setAverageFlow(strArr[4] + " / " + strArr[5]);
				statistic.setTotalFlow(strArr[2] + " / " + strArr[3]);
				statistic.setLoopbackIP(strArr[0]);
				flows.add(statistic);
				line = reader.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public List<Statistic> getFlows() {
		return flows;
	}

	public void setFlows(List<Statistic> flows) {
		this.flows = flows;
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIfindex() {
		return ifindex;
	}

	public void setIfindex(String ifindex) {
		this.ifindex = ifindex;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
}
