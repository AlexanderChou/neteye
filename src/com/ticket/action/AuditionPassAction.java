package com.ticket.action;

import java.util.Date;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.exe.ProcessInstance;

import com.base.model.Ticket;
import com.base.service.TicketService;
import com.opensymphony.xwork2.ActionSupport;
import com.ticket.jbpmUtil.JbpmUtil;
/**
 * 被处理后的ticket
 * 审核通过
 * ticket状态设置为通过
 * @author sunlujing
 *
 */
public class AuditionPassAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
    private String pid;
	private Boolean success;
	private String id;
	private String auditionInfo="";
	private TicketService ticketService=new TicketService();
	public String execute()throws Exception{
		Ticket ticket=ticketService.getById(Long.parseLong(id));
		
		String con=ticket.getContent();
		ticket.setContent(con+"\n审批情况:\n"+auditionInfo);
		ticket.setApproverPassTime(new Date());
	    ticketService.modifyTicket(ticket);
		JbpmConfiguration jbpmConfiguration = JbpmConfiguration.getInstance();
    	JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
    	ProcessInstance processInstance = jbpmContext.getProcessInstance(Long.parseLong(pid));
    	JbpmUtil.endCurrentTaskInstance(processInstance, "finalCheck", "success");
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

	public String getAuditionInfo() {
		return auditionInfo;
	}

	public void setAuditionInfo(String auditionInfo) {
		this.auditionInfo = auditionInfo;
	}
	
}
