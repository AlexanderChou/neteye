package com.ticket.action;

import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

import com.base.model.Ticket;
import com.base.service.TicketService;
import com.base.util.Role;
import com.opensymphony.xwork2.ActionSupport;
import com.ticket.jbpmUtil.JbpmUtil;


/**
 * 管理员提交从事件平台来的ticket
 * @author sunlujing
 *
 */
public class SubTicketFromEventAction extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pid;
	private String id;
    private boolean success;

	public  String execute() throws Exception{
	
       
       
	    
        //启动工作流实例

	    JbpmConfiguration jbpmConfiguration = JbpmConfiguration.getInstance();
	    JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();

	     ProcessInstance processInstance = jbpmContext.getProcessInstance(Long.parseLong(pid));
    		//让流程往下进行一步
	
	    JbpmUtil.endCurrentTaskInstance(processInstance, "createEventTask", "to next");
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

    
	
}
