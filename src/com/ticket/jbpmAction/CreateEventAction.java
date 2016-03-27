package com.ticket.jbpmAction;


import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;

import com.base.model.Ticket;
import com.base.service.TicketService;
import com.base.util.TicketState;



public class CreateEventAction 
	implements ActionHandler {

		private static final long serialVersionUID = 1L;
       
		public void execute(ExecutionContext context) throws Exception {
			 TicketService ticketService=new TicketService();
			//得到对应实例ID
			ProcessInstance processInstance = context.getContextInstance().getProcessInstance();
	
			Ticket ticket = ticketService.getTicketByPid(processInstance.getId());
			//设置文章状态为发布中
			if(ticket != null){
				    
					if(ticket.getStatus()==TicketState.UNPUBLISH || ticket.getStatus()==TicketState.PUB_DISACCEPT){
						ticket.setStatus(TicketState.PUBLISH);	
					
						ticketService.modifyTicket(ticket);
					}
			}
	
		}
	
}
