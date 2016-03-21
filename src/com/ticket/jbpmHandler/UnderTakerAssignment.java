package com.ticket.jbpmHandler;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.exe.Assignable;

public class UnderTakerAssignment implements AssignmentHandler {

	public void assign(Assignable assignable, ExecutionContext executionContext)
			throws Exception {
		String undertakerId =(String)executionContext.getContextInstance().getVariable("undertakerId");
		assignable.setActorId(undertakerId);
	}
}
