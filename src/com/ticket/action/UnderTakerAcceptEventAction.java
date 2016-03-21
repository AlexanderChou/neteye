package com.ticket.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.base.model.Ticket;
import com.base.service.TicketService;
import com.opensymphony.xwork2.ActionSupport;
import com.ticket.jbpmUtil.JbpmUtil;

import junit.framework.TestCase;
/**
 * ticket负责人点击接受按钮
 * ticket状态同步得到更新
 * @author sunlujing
 *
 */
public class UnderTakerAcceptEventAction extends ActionSupport{
    private String pid;//从页面传过来的Ticket pid
    private boolean success;
    private String id;
    private TicketService ticketService =new TicketService();
	public  String execute() throws Exception{
	    
		Ticket ticket =ticketService.getById(Long.parseLong(id));
		ticket.setReceiveApproverTime(new Date());
		ticketService.modifyTicket(ticket);
		
		JbpmConfiguration jbpmConfiguration = JbpmConfiguration.getInstance();
    	JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
    	ProcessInstance processInstance = jbpmContext.getProcessInstance(Long.parseLong(pid));
    	JbpmUtil.endCurrentTaskInstance(processInstance, "decideAcception", "underTaker_accept");
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

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
