package com.ticket.action;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.base.model.Category;
import com.base.model.Priority;
import com.base.model.Project;
import com.base.model.Ticket;
import com.base.model.UserPojo;
import com.base.service.TicketService;
import com.opensymphony.xwork2.ActionSupport;
import com.ticket.jbpmUtil.JbpmUtil;
/**
 * 负责人处理完交给自己的ticket
 * 提交后等待审核
 * @author Administrator
 *
 */
public class UnderTakerDealEventAction extends ActionSupport{

	private String contentInfo;
	private String id;
	private String pid; 
	private String auditorId;
	
	private Boolean success;
	private TicketService ticketService =new TicketService();
	
	public String execute()throws Exception{
		Ticket ticket=ticketService.getById(Long.parseLong(id));
		ticket.setUserByApproverId(Long.parseLong(auditorId));
		String con=ticket.getContent();
		ticket.setCommitApproverTime(new Date());
		ticket.setContent(con+"\n处理情况:\n"+contentInfo);
	    ticketService.modifyTicket(ticket);
	    
	    //启动工作流实例
		JbpmConfiguration jbpmConfiguration = JbpmConfiguration.getInstance();
	    JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();

	     ProcessInstance processInstance = jbpmContext.getProcessInstance(Long.parseLong(pid));
    		//让流程往下进行一步
	    ContextInstance ci = processInstance.getContextInstance();
	 		
	 		//设置审批人
	 	ci.setVariable("auditorId",auditorId);
        JbpmUtil.endCurrentTaskInstance(processInstance, "decideDelegation", "delegate_false");

		jbpmContext.save(processInstance);
		jbpmContext.close();
		
		//处理ticket的节点
	    JbpmContext jc = jbpmConfiguration.createJbpmContext();
         ProcessInstance pi = jc.getProcessInstance(Long.parseLong(pid));
   
         JbpmUtil.endCurrentTaskInstance(pi,"mywork","me to check");
         jc.save(pi);
         jc.close();
		this.success=true;
		return SUCCESS;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getContentInfo() {
		return contentInfo;
	}

	public void setContentInfo(String contentInfo) {
		this.contentInfo = contentInfo;
	}

	
    
	
}
