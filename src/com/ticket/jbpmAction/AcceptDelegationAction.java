package com.ticket.jbpmAction;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;

import com.base.model.Ticket;
import com.base.service.TicketService;
import com.base.util.TicketState;

/**
 * 处理委托的任务被接受后所处的状态
 * @author Administrator
 *
 */
public class AcceptDelegationAction implements ActionHandler {

	private static final long serialVersionUID = 1L;
   
	public void execute(ExecutionContext context) throws Exception {
		 TicketService ticketService=new TicketService();
		//得到对应实例ID
		ProcessInstance processInstance = context.getContextInstance().getProcessInstance();

		Ticket ticket = ticketService.getTicketByPid(processInstance.getId());
		
		//ticket 为委托不被接受的状态
		if(ticket != null){
			
				if(ticket.getStatus()==TicketState.BEING_DELEGATE){
					ticket.setStatus(TicketState.DELEGATING_ACCEPT);	
					ticketService.modifyTicket(ticket);
				}
		}
		
	}
}
