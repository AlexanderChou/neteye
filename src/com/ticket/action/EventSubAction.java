package com.ticket.action;

import java.util.Date;

import junit.framework.TestCase;

import org.apache.struts2.ServletActionContext;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

import com.base.model.Attachment;
import com.base.model.Category;
import com.base.model.Priority;
import com.base.model.Project;
import com.base.model.Ticket;
import com.base.model.UserPojo;
import com.base.service.TicketService;
import com.base.util.Role;
import com.opensymphony.xwork2.ActionSupport;
import com.ticket.jbpmUtil.JbpmUtil;

/**
 * 发布事件的action
 * 事件入库
 * 绑定流程并启动
 * 设置ticket为发布状态
 * @author SUNLUJING
 *
 */
public class EventSubAction extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Boolean success;
	private String project_id;
	private String undertaker_id;
	private String category_id;
	private String priority_id;
	private String title;
	private String content;
	private String desc;
	private Date createTime;
	private String isdigest;
	private String cc_ids;
    private String attachflag="0";
    private boolean flag = false;
	private TicketService ticketService =new TicketService();
	public  String execute() throws Exception{
		String userId =ServletActionContext.getRequest().getSession().getAttribute("userId").toString();
		long role[]=ticketService.getGroupIdByUserId(Long.parseLong(userId));
		for(int i = 0 ;i<role.length;i++){
		 if(role[i]==Role.ADMIN){
			 flag = true ;
		}
		}
		
		if(!flag){
			this.success=false;
			return SUCCESS;
		}
		Ticket ticket=new Ticket();
		ticket.setCategory(Long.parseLong(category_id));
		ticket.setUserByUndertakerId(Long.parseLong(undertaker_id));
		ticket.setPriority(Long.parseLong(priority_id));
		ticket.setProject(Long.parseLong(project_id));
		ticket.setCcIds(cc_ids);
		ticket.setContent(content);
		ticket.setDescription(desc);
		ticket.setIsdigest(Byte.valueOf(isdigest));
		ticket.setTitle(title);
		createTime=new Date();
	    ticket.setCreateTime(createTime);
	    
	    
	    
        //启动工作流实例
		JbpmConfiguration jbpmConfiguration = JbpmConfiguration.getInstance();
	    JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
		
		ProcessDefinition processDefinition = jbpmContext.getGraphSession().findLatestProcessDefinition("ticket");
		ProcessInstance processInstance = new ProcessInstance(processDefinition);
	    ticket.setPid(processInstance.getId());
		
		//增加ticket
		ticketService.create(ticket);

		//该ticket含有附件
		if(attachflag.equals("1")){
			
			String attachId=ServletActionContext.getRequest().getSession().getAttribute("attachId").toString();
			long ticketId=ticketService.getTicketByPid(processInstance.getId()).getId();
			Attachment att=((Attachment)ticketService.read(Attachment.class, Long.parseLong(attachId)));
		
			if(att!=null)
				att.setTicket(ticketId);
			ticketService.update(att);
		}
		     
		
		//工作流流程逻辑代码
		//让流程往下进行一步
		ContextInstance ci = processInstance.getContextInstance();
		//把用户Id给当前任务，记录谁创建过Event
		
		ci.setVariable("eventType","0");
		ci.setVariable("userId",userId);
		ci.setVariable("undertakerId",undertaker_id);
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getAttachflag() {
		return attachflag;
	}
	public void setAttachflag(String attachflag) {
		this.attachflag = attachflag;
	}

	
	
}
