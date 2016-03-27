package com.ticket.action;

import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

import com.base.model.Category;
import com.base.model.Priority;
import com.base.model.Project;
import com.base.model.Ticket;
import com.base.model.UserPojo;
import com.base.service.TicketService;
import com.opensymphony.xwork2.ActionSupport;
import com.ticket.jbpmUtil.JbpmUtil;

/**
 * 一个被审批通过ticket重置为新的ticket
 * 重置为新的ticket的action
 * @author Administrator
 */
public class EventReSubAction extends ActionSupport{
	private Boolean success;//用来记录是否提交成功的变量返回json
	private String project_id;
	private String undertakerId;
	private String category_id;
	private String priority_id;
	private String title;
	private String content;
	private String desc;
	private Date createTime;
	private String isdigest;
	private String cc_ids;
	private String pid;
	private String id;
	private TicketService ticketService =new TicketService();
	public  String execute() throws Exception{
		Ticket temp=ticketService.getById(Long.parseLong(id));
		Ticket ticket=new Ticket();
	    ticket.setId(temp.getId());
		ticket.setCategory(temp.getCategory());
		ticket.setUserByUndertakerId(temp.getUserByUndertakerId());
		ticket.setPriority(temp.getPriority());
		ticket.setProject(temp.getPriority());
		ticket.setCcIds(temp.getCcIds());
		ticket.setContent("重置前内容:\n"+temp.getContent()+"重置后内容:\n");
	
		ticket.setIsdigest(temp.getIsdigest());
		ticket.setTitle(temp.getTitle());
		createTime=new Date();
	    ticket.setCreateTime(createTime);
	   
	    //启动工作流实例
		JbpmConfiguration jbpmConfiguration = JbpmConfiguration.getInstance();
	    JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
		
		ProcessDefinition processDefinition = jbpmContext.getGraphSession().findLatestProcessDefinition("ticket");
		ProcessInstance processInstance = new ProcessInstance(processDefinition);
	    ticket.setPid(processInstance.getId());
		
		//增加ticket
		ticketService.modifyTicket(ticket);
		
		//工作流流程逻辑代码
		//让流程往下进行一步
		ContextInstance ci = processInstance.getContextInstance();
		//把用户Id给当前任务，记录谁创建过Event
		String userId =ServletActionContext.getRequest().getSession().getAttribute("userId").toString();
		ci.setVariable("eventType","0");
		ci.setVariable("userId",userId);
		ci.setVariable("undertakerId",undertakerId);
		Token token = processInstance.getRootToken();
		token.signal();
		//保存流程实例与状态
	    JbpmUtil.endCurrentTaskInstance(processInstance, "createEventTask", "to next");
		jbpmContext.save(processInstance);
		jbpmContext.save(token);
		jbpmContext.close();
		this.success=true;

		return SUCCESS;
	}

	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
	
	public String getUndertakerId() {
		return undertakerId;
	}

	public void setUndertakerId(String undertakerId) {
		this.undertakerId = undertakerId;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getIsdigest() {
		return isdigest;
	}
	public void setIsdigest(String isdigest) {
		this.isdigest = isdigest;
	}
	public String getCc_ids() {
		return cc_ids;
	}
	public void setCc_ids(String cc_ids) {
		this.cc_ids = cc_ids;
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
	public TicketService getTicketService() {
		return ticketService;
	}
	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	
}
