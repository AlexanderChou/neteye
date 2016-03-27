package com.event.action;


import java.util.Calendar;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.base.model.EventSeq;
import com.base.service.EventSeqService;
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
public class ObjEventSeqAction extends ActionSupport {
	private String howMuch = "0";// 0：显示最近100条 1：显示今天所有 2：显示某一时段 记录
	private String fromTime;// 某一时段的开始时间
	private String toTime;// 某一时段的结束时间
	private String priority;// 事件优先级
	private String eventId;
	private String condition = "";// 根据前台页面组合成的查询条件
	private List<EventSeq> eventSeqs;// 事件结果集
	private String title;
	private Integer totalCount;
	private String start;
	private String limit;
	private String objType;

	@JSON(serialize = false)
	public String execute() throws Exception {
		String where = "where objType='" + objType + "' and eventId=" + eventId;
		if (howMuch.equals("0")) {
			title = "最近100条";
			howMuch = "100";
		} else if (howMuch.equals("1")) {
			title = "今日事件";
			howMuch = "0";
			Calendar c = Calendar.getInstance();
			int yy = c.get(Calendar.YEAR);
			int mm = c.get(Calendar.MONTH) + 1;
			int dd = c.get(Calendar.DAY_OF_MONTH);
			fromTime = yy + "-" + mm + "-" + dd;
			toTime = fromTime;
			where += " and to_days(occurTime)>=to_days('" + fromTime
					+ "') and to_days(occurTime)<=to_days('" + toTime + "')";
		} else if (howMuch.equals("2")) {
			title = "事件记录(" + fromTime + "至" + toTime + ")";
			howMuch = "0";
			where += " and to_days(occurTime)>=to_days('" + fromTime
					+ "') and to_days(occurTime)<=to_days('" + toTime + "')";
		}
		if (priority != null && !priority.equals("")) {
			where += " and priority=" + priority;
		}
		condition += where;
		EventSeqService eventSeqService = new EventSeqService();
		/* 根据查询条件获得相应的事件列表*/
		eventSeqs = eventSeqService.getEventSeq(condition, start, limit, Integer.parseInt(howMuch));
		/* 根据查询条件获得相应的事件列表*/
		totalCount = eventSeqService.getEventSeqCount(condition);

		return SUCCESS;
	}
	public String getEventId(){
		return eventId;
	}
	public void setEventId(String eventId){
		this.eventId = eventId;
	}

	public List<EventSeq> getEventSeqs(){
		return eventSeqs;
	}
	public void setEventSeqs(List<EventSeq> eventSeqs){
		this.eventSeqs = eventSeqs;
	}


	public String getHowMuch() {
		return howMuch;
	}

	public void setHowMuch(String howMuch) {
		this.howMuch = howMuch;
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

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public List<EventSeq> getEvents() {
		return eventSeqs;
	}

	public void setEvents(List<EventSeq> eventSeqs) {
		this.eventSeqs = eventSeqs;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
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
	public String getObjType() {
		return objType;
	}
	public void setObjType(String objType) {
		this.objType = objType;
	}
}
