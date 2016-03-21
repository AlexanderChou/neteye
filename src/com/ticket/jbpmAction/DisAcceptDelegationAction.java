package com.ticket.jbpmAction;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;

import com.base.model.Ticket;
import com.base.service.TicketService;
import com.base.util.TicketState;

/**
 * 在工作流中处理当用户不接受我委托的任务时采取的动作
 * @author Administrator
 *
 */
public class DisAcceptDelegationAction 	implements ActionHandler {

	private static final long serialVersionUID = 1L;
   
	public void execute(ExecutionContext context) throws Exception {
		 TicketService ticketService=new TicketService();
		//得到对应实例ID
		ProcessInstance processInstance = context.getContextInstance().getProcessInstance();

		Ticket ticket = ticketService.getTicketByPid(processInstance.getId());
		
		//ticket 为委托不被接受的状态
		if(ticket != null){
			
				if(ticket.getStatus()==TicketState.BEING_DELEGATE){
					ticket.setStatus(TicketState.DELEGATING_DISACCEPT);	
					ticketService.modifyTicket(ticket);
				}
		}
		
	}
}
