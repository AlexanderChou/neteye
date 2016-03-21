package com.event.action;

import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.base.service.AlarmService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.ticket.jbpmUtil.JbpmUtil;
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
 * <p>Title: 获得事件报警信息</p>
 * <p>Description: 根据用户id获得该用户未曾处理的报警信息，通过页面对用户进行提示</p>
 * @version 1.0
 * @author 郭玺
 * <p>Company: 网络中心</p>
 * <p>Copyright: Copyright (c) 2009</p>
 */
public class EventAlarmAction extends  ActionSupport{
	private Long alarmNum;
	private String ticketInfo = "";

	@JSON(serialize=false)
	public String execute() throws Exception{
		Long userId = (Long)ActionContext.getContext().getSession().get("userId");
		//根据用户id获得相关的报警事件
		alarmNum = new AlarmService().findAlarmById(userId);
		//根据用户id获得该用户所属的角色，然后再获得其ticket列表信息
		List mytask=JbpmUtil.getTask_Undo(userId.toString());
        
		if( mytask !=null && mytask.size()!=0){
		   ticketInfo = "您有"+mytask.size()+"条工单需要处理!";
        }
    
		return SUCCESS;
	}
	
	public Long getAlarmNum() {
		return alarmNum;
	}
	public void setAlarmNum(Long alarmNum) {
		this.alarmNum = alarmNum;
	}
	public String getTicketInfo() {
		return ticketInfo;
	}
	public void setTicketInfo(String ticketInfo) {
		this.ticketInfo = ticketInfo;
	}
}
