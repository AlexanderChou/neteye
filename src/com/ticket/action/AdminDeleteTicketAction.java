package com.ticket.action;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.exe.ProcessInstance;

import com.base.model.Ticket;
import com.base.service.TicketService;
import com.opensymphony.xwork2.ActionSupport;

public class AdminDeleteTicketAction extends ActionSupport{
    private boolean success;
    private String pid;
    private String id;
    TicketService ticketService=new TicketService();
	public String execute()throws Exception{
		ticketService.deleteById(Long.parseLong(id));
		
	
	    this.success=true;
    	return SUCCESS;
    }
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}