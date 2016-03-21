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
/**
 * 用户不接受委托给自己的任务
 * @author Administrator
 *
 */
public class DisAcceptDelegationAction 
	extends ActionSupport{
	    private boolean success;
        private String delegationInfo;
        private String id;
		private static final long serialVersionUID = 1L;
	    private String pid;
	    private TicketService ticketService= new TicketService();
		public String execute()throws Exception{
			Ticket tic= ticketService.getById(Long.parseLong(id));
			tic.setDescription("委托不被接受的原因:\n"+delegationInfo);
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
    			if(ticket.getStatus()==TicketState.BEING_DELEGATE){
    				
    				ticket.setStatus(TicketState.DELEGATING_DISACCEPT);
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
			public String getDelegationInfo() {
				return delegationInfo;
			}
			public void setDelegationInfo(String delegationInfo) {
				this.delegationInfo = delegationInfo;
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
