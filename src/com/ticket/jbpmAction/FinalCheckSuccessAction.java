package com.ticket.jbpmAction;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;

import com.base.model.Ticket;
import com.base.service.TicketService;
import com.base.util.TicketState;

public class FinalCheckSuccessAction implements ActionHandler {

	private static final long serialVersionUID = 1L;
   
	public void execute(ExecutionContext context) throws Exception {
		 TicketService ticketService=new TicketService();
		//得到对应实例ID
		ProcessInstance processInstance = context.getContextInstance().getProcessInstance();

		Ticket ticket = ticketService.getTicketByPid(processInstance.getId());
		//设置ticket为成功处理状态
		if(ticket != null){
			
				if(ticket.getStatus()==TicketState.DEAL_DONE_WAIT_AUDITING){
					ticket.setStatus(TicketState.TICKET_DONE);	
					ticketService.modifyTicket(ticket);
				}
		}
		
	}
}
