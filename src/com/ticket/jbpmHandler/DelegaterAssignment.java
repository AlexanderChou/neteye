package com.ticket.jbpmHandler;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.exe.Assignable;

public class DelegaterAssignment implements AssignmentHandler {
    //undertaker可以决定是不是要设置代理
	public void assign(Assignable assignable, ExecutionContext executionContext)
			throws Exception {
		String undertakerId =(String)executionContext.getContextInstance().getVariable("undertakerId");
		assignable.setActorId(undertakerId);
	}
	
}
