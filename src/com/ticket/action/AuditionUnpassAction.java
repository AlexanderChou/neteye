package com.ticket.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

import com.base.model.Ticket;
import com.base.service.TicketService;
import com.base.util.TicketState;
import com.opensymphony.xwork2.ActionSupport;
import com.ticket.jbpmUtil.JbpmUtil;
/**
 * 被处理后的ticket
 * 审核不通过
 * ticket状态设置为不通过
 * 任务滚到处理处
 * @author sunlujing
 *
 */
public class AuditionUnpassAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
    private TicketService ticketService =new TicketService();
    private String pid;//从页面传过来的ticket的pid的值
	private Boolean success;
	private String id;
	private String auditionInfo="";
    public String execute()throws Exception{
	  
        Ticket tic=ticketService.getById(Long.parseLong(id));
		
		String con=tic.getContent();
		tic.setContent(con+"\n审批不通过原因:\n"+auditionInfo);
	    ticketService.modifyTicket(tic);
	JbpmConfiguration jbpmConfiguration = JbpmConfiguration.getInstance();
   	JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
   	ProcessInstance processInstance = jbpmContext.getProcessInstance(Long.parseLong(pid));
	Session session = jbpmContext.getSession();   
	 
	 //不接受委托时工作流回退
	 //关闭当前的任务
	 SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
     String date = df.format(new Date());
	 Query query = session.createQuery("update TaskInstance t set t.isOpen=0,t.isSignalling=0,t.end='"+date+"'"+
			 " where t.token="+processInstance.getId()+"and t.end is null");
	 query.executeUpdate();
	 
	 //开启回退节点的任务
	 session.createQuery("update TaskInstance t set t.isOpen=1,t.isSignalling=1,t.end=null"+
		 " where t.token="+processInstance.getId()+"and t.name='decideDelegation'").executeUpdate();
     

	 //把令牌设置为回退节点
	 Node n = processInstance.getProcessDefinition().getNode("DelegateDecide");
	 long tokenId = n.getId();
     query =session.createQuery("update Token t set t.node="+tokenId+"where t.id="+processInstance.getId()); 
     query.executeUpdate();
    jbpmContext.save(processInstance);
    jbpmContext.close();
    
    
    Ticket ticket = ticketService.getTicketByPid(Long.parseLong(pid));
	//设置ticket的状态为不被接受状态
	 if(ticket != null){
		if(ticket.getStatus()==TicketState.DEAL_DONE_WAIT_AUDITING){
			
			ticket.setStatus(TicketState.TICKET_UNPASS);
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
	public String getAuditionInfo() {
		return auditionInfo;
	}
	public void setAuditionInfo(String auditionInfo) {
		this.auditionInfo = auditionInfo;
	}
    
}