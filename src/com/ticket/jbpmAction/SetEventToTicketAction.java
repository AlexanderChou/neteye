package com.ticket.jbpmAction;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;

import com.base.model.Ticket;
import com.base.service.TicketService;
import com.base.util.TicketState;

public class SetEventToTicketAction implements ActionHandler {

	private static final long serialVersionUID = 1L;
   
	public void execute(ExecutionContext context) throws Exception {
		//默认的状态为超过状态无需动作
	}
}
