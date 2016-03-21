package com.event.action;

import java.util.Date;

import com.event.dao.StatisticDAO;
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
 * <p>Title: 事件统计</p>
 * <p>Description: 根据用户的输入，对事件进行统计，并将统计结果生成的图形输出到页面</p>
 * @version 1.0
 * @author 郭玺
 * <p>Company: 网络中心</p>
 * <p>Copyright: Copyright (c) 2009</p>
 */
public class EventStatisticAction extends  ActionSupport{
	private String fromTime;//事件统计开始时间
	private String toTime;//事件统计结束时间
	private Long totalNum;//事件总数
	private String moduleImage;//按模块名称统计生成饼状图的图形名称
	private String titleImage;//按事件主题统计生成饼状图的图形名称
	private String typeValueImage;//按事件类型值统计生成饼状图的图形名称
	private String topIPImage;//按发生事件最多的前10个IP统计生成柱状图的图形名称
	private String time = new Date().toString();//用于刷新页面

	public String execute() throws Exception{
		StatisticDAO statisticDAO = new StatisticDAO();
		moduleImage = statisticDAO.createStatisticImage("moduleId", fromTime, toTime);
		titleImage = statisticDAO.createStatisticImage("title", fromTime, toTime);
		typeValueImage = statisticDAO.createStatisticImage("typeValue", fromTime, toTime);
		topIPImage = statisticDAO.createStatisticImage(null,fromTime, toTime);
		return SUCCESS;
	}
	
	public String getModuleImage() {
		return moduleImage;
	}
	public void setModuleImage(String moduleImage) {
		this.moduleImage = moduleImage;
	}
	public String getTitleImage() {
		return titleImage;
	}
	public void setTitleImage(String titleImage) {
		this.titleImage = titleImage;
	}
	public String getTypeValueImage() {
		return typeValueImage;
	}
	public void setTypeValueImage(String typeValueImage) {
		this.typeValueImage = typeValueImage;
	}
	public String getTopIPImage() {
		return topIPImage;
	}
	public void setTopIPImage(String topIPImage) {
		this.topIPImage = topIPImage;
	}
	public String getFromTime() {
		return fromTime;
	}
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}
	public String getToTime() {
		return toTime;
	}
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
	public Long getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
