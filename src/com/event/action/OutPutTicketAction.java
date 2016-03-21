package com.event.action;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

import com.base.model.Category;
import com.base.model.EventPojo;
import com.base.model.Priority;
import com.base.model.Project;
import com.base.model.Ticket;
import com.base.model.UserPopedom;
import com.base.service.EventService;
import com.base.service.TicketService;
import com.base.util.BaseAction;
import com.ticket.dao.CategoryDAO;
import com.ticket.dao.PriorityDAO;
import com.ticket.dao.ProjectDAO;
import com.ticket.jbpmUtil.JbpmUtil;

/**
 * 将选择的事件生成Ticket
 * @author dell
 *
 */
public class OutPutTicketAction  extends BaseAction {
	public String execute() throws Exception {
		String[] eventIds = this.getRequest().getParameter("eventIds").trim().split(";");
		for (String eventId : eventIds) {
			this.createTicket(eventId);
		}
		PrintWriter writer = this.getResponse().getWriter();
		writer.print("ok");
		writer.close();
		return null;
	}
	public void createTicket(String id) throws Exception{
		//由事件id获得该事件对象
		String userId =ServletActionContext.getRequest().getSession().getAttribute("userId").toString();
		EventPojo event = new EventService().findById(Long.parseLong(id));
		Ticket ticket=new Ticket();
		//如果数据库没有默认的分类记录，需要重新创建
		Category category = new CategoryDAO().getCategoryByName(event.getModuleId());
		if(category!=null){
			Long category_id = category.getId();
			if(category_id!=null){
				ticket.setCategory(category_id);
			}
		}
		//从任务承担人列表中随机选择一个承担人
		String undertaker_id = null;
		List undertakers = new TicketService().QueryByHql("from UserPopedom ud where ud.groupId=2");
	   	if(undertakers!=null && undertakers.size()>0){
	   		UserPopedom user =  (UserPopedom)undertakers.get(0);
	   		ticket.setUserByUndertakerId(user.getId());
	   		undertaker_id = user.getId().toString();
	   	}
		//如果数据库没有默认的优先级记录，需要重新创建
	   	Priority priority = new PriorityDAO().getPriorityByName("1");
	   	if(priority!=null){
		   	Long priority_id = priority.getId();
		   	if(priority_id!=null){
		   		ticket.setPriority(priority_id);
		   	}
	   	}
		//如果数据库没有默认的项目记录，需要重新创建
	   	Project project = new ProjectDAO().getProjectByName("CNGI二期");
	   	if(project!=null){
		   	Long project_id = project.getId();
		   	if(project_id!=null){
		   		ticket.setProject(project_id);
		   	}
	   	}
		//默认为当前用户
		ticket.setCcIds(userId);
		ticket.setContent(event.getContent());
		ticket.setDescription(event.getTitle()+"\n"+event.getTypeValue());
		ticket.setIsdigest(Byte.valueOf("1"));
		ticket.setTitle(event.getTitle());
		Date time = new Date();
	    ticket.setCreateTime(time);
	    
        //启动工作流实例
		JbpmConfiguration jbpmConfiguration = JbpmConfiguration.getInstance();
	    JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
		
		ProcessDefinition processDefinition = jbpmContext.getGraphSession().findLatestProcessDefinition("ticket");
		ProcessInstance processInstance = new ProcessInstance(processDefinition);
	    ticket.setPid(processInstance.getId());
		
		//增加ticket
		new  TicketService().create(ticket);
		
		//工作流流程逻辑代码
		//让流程往下进行一步
		ContextInstance ci = processInstance.getContextInstance();
		//把用户Id给当前任务，记录谁创建过Event
		ci.setVariable("eventType","1");//ticket来自自事件平台
		ci.setVariable("adminId",userId);
		ci.setVariable("userId",userId);
		ci.setVariable("undertakerId",undertaker_id);
		Token token = processInstance.getRootToken();
		token.signal();
		//保存流程实例与状态
	    JbpmUtil.endCurrentTaskInstance(processInstance, "ticketFromEventTask", "to create");
		jbpmContext.save(processInstance);
		jbpmContext.save(token);
		jbpmContext.close();
	}
}
