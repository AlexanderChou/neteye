package com.ticket.action;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.exe.TaskInstance;


import com.base.model.Ticket;
import com.base.service.TicketService;
import com.opensymphony.xwork2.ActionSupport;
import com.ticket.jbpmUtil.JbpmUtil;
/**
 * 委托人接受委托事件
 * 对事件进行处理
 * @author sunlujing
 *
 */
public class DelegatorDealEventAction extends ActionSupport{
	    private String pid;
	    private String auditorId;
		private Boolean success;
		private String id;
		private String contentInfo;
		private TicketService ticketService=new TicketService();
        public String execute()throws Exception{
		
        Ticket tic=ticketService.getById(Long.parseLong(id));
        tic.setCommitApproverTime(new Date());
        tic.setContent(tic.getContent()+"\n处理情况:\n"+contentInfo);
        ticketService.modifyTicket(tic);
        
        
		JbpmConfiguration jbpmConfiguration = JbpmConfiguration.getInstance();
	    JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();

    	ProcessInstance processInstance = jbpmContext.getProcessInstance(Long.parseLong(pid));
		ContextInstance ci = processInstance.getContextInstance();
		
		//设置审批人
		ci.setVariable("auditorId",auditorId);
    	JbpmUtil.endCurrentTaskInstance(processInstance, "delegatorwork", "delegate to check");
		//让流程往下进行一步
        
		jbpmContext.save(processInstance);
		
		jbpmContext.close();
		this.success=true;
		
		return SUCCESS;
	}
        
        public String getPid() {
    		return pid;
    	}

    	public void setPid(String pid) {
    		this.pid = pid;
    	}

		public String getAuditorId() {
			return auditorId;
		}

		public void setAuditorId(String auditorId) {
			this.auditorId = auditorId;
		}

		public Boolean getSuccess() {
			return success;
		}

		public void setSuccess(Boolean success) {
			this.success = success;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getContentInfo() {
			return contentInfo;
		}

		public void setContentInfo(String contentInfo) {
			this.contentInfo = contentInfo;
		}
        
}
