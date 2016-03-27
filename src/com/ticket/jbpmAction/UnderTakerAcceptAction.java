package com.ticket.jbpmAction;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;

import com.base.model.Ticket;
import com.base.service.TicketService;
import com.base.util.TicketState;

public class UnderTakerAcceptAction implements ActionHandler {

	private static final long serialVersionUID = 1L;
   
	public void execute(ExecutionContext context) throws Exception {
		 TicketService ticketService=new TicketService();
		//得到对应实例ID
		ProcessInstance processInstance = context.getContextInstance().getProcessInstance();

		Ticket ticket = ticketService.getTicketByPid(processInstance.getId());
		
		//设置ticket的状态为接受状态
		if(ticket != null){
			    
				if(ticket.getStatus()==TicketState.PUBLISH){
					ticket.setStatus(TicketState.ACCEPT_PUB_AND_WAIT_DEALING);	
					ticketService.modifyTicket(ticket);
				}
		}
	
	}
}
