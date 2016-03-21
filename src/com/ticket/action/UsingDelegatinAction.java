package com.ticket.action;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.exe.ProcessInstance;


import com.base.model.Ticket;
import com.base.model.UserPojo;
import com.base.service.TicketService;
import com.opensymphony.xwork2.ActionSupport;
import com.ticket.jbpmUtil.JbpmUtil;

/**
 * 在已经接受的ticket上点击
 * 使用代理提交
 * @author Administrator
 *
 */
public class UsingDelegatinAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
    private String pid;
    private String delegatorId;
	private Boolean success;
	private String id;
	private TicketService ticketService=new TicketService();
	public String execute()throws Exception{
		//加入审批信息
			Ticket ticket= ticketService.getById(Long.parseLong(id));
		    UserPojo user=(UserPojo)ticketService.QueryByHql("from UserPojo where id="+Long.parseLong(delegatorId)).get(0);
	        ticket.setDescription("处于委托状态：委托人 "+user.getName());
	        ticketService.modifyTicket(ticket);
		JbpmConfiguration jbpmConfiguration = JbpmConfiguration.getInstance();
	    JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();

    	ProcessInstance processInstance = jbpmContext.getProcessInstance(Long.parseLong(pid));
		//让流程往下进行一步
		//没有使用代理	
		ContextInstance ci = processInstance.getContextInstance();
		//把用户Id给当前任务，记录被委托人
		
		//委托人
		ci.setVariable("delegatorId",delegatorId);
		JbpmUtil.endCurrentTaskInstance(processInstance, "decideDelegation", "delegate_true");
		jbpmContext.save(processInstance);
		jbpmContext.close();	
		this.success=true;
		return SUCCESS;
	}
	public String getPid(){
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public String getDelegatorId() {
		return delegatorId;
	}
	public void setDelegatorId(String delegatorId) {
		this.delegatorId = delegatorId;
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
    
           
}
