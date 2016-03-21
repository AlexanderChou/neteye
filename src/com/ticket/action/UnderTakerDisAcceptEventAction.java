package com.ticket.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.Query;
import org.hibernate.Session;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.base.model.Ticket;
import com.base.service.TicketService;
import com.base.util.TicketState;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 负责人点击不接受按钮
 * ticket的状态回滚
 * @author sunlujing
 *
 */
public class UnderTakerDisAcceptEventAction extends ActionSupport{
	    private String pid;//该ticket的pid
		private Boolean success;
		private String id;
		private String noAcceptInfo;
	    private TicketService ticketService=new TicketService();
	    
	    
		public  String execute() throws Exception{
		    TaskInstance instance=null;
			JbpmConfiguration jbpmConfiguration = JbpmConfiguration.getInstance();
	    	JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
	    	ProcessInstance processInstance = jbpmContext.getProcessInstance(Long.parseLong(pid));
	    	
	    	 Session session = jbpmContext.getSession();   
	    	 
	    	 //不接受任务时工作流回退
	    	 //关闭当前的任务
	    	 SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
             String date = df.format(new Date());
			 Query query = session.createQuery("update TaskInstance t set t.isOpen=0,t.isSignalling=0,t.end='"+date+"'"+
					 " where t.token="+processInstance.getId()+"and t.end is null");
			 query.executeUpdate();
			 
			 //开启回退节点的任务
			 session.createQuery("update TaskInstance t set t.isOpen=1,t.isSignalling=1,t.end=null"+
				 " where t.token="+processInstance.getId()+"and t.name='createEventTask'").executeUpdate();
	         

			 //把令牌设置为回退节点
			 Node n = processInstance.getProcessDefinition().getNode("createEvent");
			 long tokenId = n.getId();
	         query =session.createQuery("update Token t set t.node="+tokenId+"where t.id="+processInstance.getId()); 
	         query.executeUpdate();
             jbpmContext.save(processInstance);
             jbpmContext.close();
             
             
             Ticket ticket = ticketService.getTicketByPid(processInstance.getId());
     		//设置ticket的状态为不被接受状态
     		 if(ticket != null){
     			String des=ticket.getDescription();
     			ticket.setDescription(des+"不被负责人接受理由：\n"+noAcceptInfo);
     			if(ticket.getStatus()==TicketState.PUBLISH){
     				
     				ticket.setStatus(TicketState.PUB_DISACCEPT);
     				ticketService.modifyTicket(ticket);
     			}
     		}
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


		public String getNoAcceptInfo() {
			return noAcceptInfo;
		}


		public void setNoAcceptInfo(String noAcceptInfo) {
			this.noAcceptInfo = noAcceptInfo;
		}
		
		
}
