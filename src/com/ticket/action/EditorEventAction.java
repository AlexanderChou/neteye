package com.ticket.action;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.exe.ProcessInstance;

import com.base.model.Ticket;
import com.base.service.TicketService;
import com.opensymphony.xwork2.ActionSupport;
import com.ticket.jbpmUtil.JbpmUtil;
/**
 * 在ticket事件不被接受的时候
 * admin可以通过编辑事件从新提交
 * @author sunlujing
 *
 */
public class EditorEventAction extends ActionSupport{
	private Boolean success;
	private String project_id;
	private String undertaker_id;
	private String category_id;
	private String priority_id;
	private String contentInfo;
	private String pid;
	private String id;
	
	
	private TicketService ticketService =new TicketService();

	public String execute()throws Exception{
		Ticket ticket=ticketService.getById(Long.parseLong(id));
		ticket.setContent(contentInfo);
		ticket.setPriority(Long.parseLong(priority_id));
		ticket.setProject(Long.parseLong(project_id));
		ticket.setCategory(Long.parseLong(category_id));
		ticket.setUserByUndertakerId(Long.parseLong(undertaker_id));
		ticketService.modifyTicket(ticket);
		
		
		 //启动工作流实例
		JbpmConfiguration jbpmConfiguration = JbpmConfiguration.getInstance();
	    JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();

	     ProcessInstance processInstance = jbpmContext.getProcessInstance(Long.parseLong(pid));
    		//让流程往下进行一步
	    ContextInstance ci = processInstance.getContextInstance();
	 		
	 		//设置负责人
	    ci.setVariable("undertakerId",undertaker_id);
	    JbpmUtil.endCurrentTaskInstance(processInstance, "createEventTask", "to next");

		jbpmContext.save(processInstance);
		jbpmContext.close();
		this.success=true;
	   return SUCCESS;
   }
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
	public String getUndertaker_id() {
		return undertaker_id;
	}
	public void setUndertaker_id(String undertaker_id) {
		this.undertaker_id = undertaker_id;
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public String getPriority_id() {
		return priority_id;
	}
	public void setPriority_id(String priority_id) {
		this.priority_id = priority_id;
	}
	
	public String getContentInfo() {
		return contentInfo;
	}
	public void setContentInfo(String contentInfo) {
		this.contentInfo = contentInfo;
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
