package com.ticket.jbpmHandler;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.exe.Assignable;

public class WhoDelegateAssignment implements AssignmentHandler {

	public void assign(Assignable assignable, ExecutionContext executionContext)
			throws Exception {
		String delegatorId =(String)executionContext.getContextInstance().getVariable("delegatorId");
		assignable.setActorId(delegatorId);
	}
}
