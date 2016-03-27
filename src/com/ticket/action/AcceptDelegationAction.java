package com.ticket.action;

import java.util.Date;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;



import com.base.model.Ticket;
import com.base.service.TicketService;
import com.opensymphony.xwork2.ActionSupport;
import com.ticket.jbpmUtil.JbpmUtil;
/**
 * 用户接受委托给自己的ticket
 * @author Administrator
 *
 */
public class AcceptDelegationAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
    private String pid;//接受委托的tikcet 的pid
	private Boolean success;
    private String id;
    private TicketService ticketService=new TicketService();
    
	public String execute()throws Exception{
		
		Ticket ticket=ticketService.getById(Long.parseLong(id));
		ticket.setReceiveDelegateTime(new Date());
		ticketService.modifyTicket(ticket);
		JbpmConfiguration jbpmConfiguration = JbpmConfiguration.getInstance();
	    JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
    	ProcessInstance processInstance = jbpmContext.getProcessInstance(Long.parseLong(pid));
		//让流程往下进行一步
    	JbpmUtil.endCurrentTaskInstance(processInstance, "decideDelegationAcception", "agree");
       
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
	
}
