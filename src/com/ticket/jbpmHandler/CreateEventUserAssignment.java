package com.ticket.jbpmHandler;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.exe.Assignable;


public class CreateEventUserAssignment implements AssignmentHandler {

	public void assign(Assignable assignable, ExecutionContext executionContext)
			throws Exception {
	
		String userid =(String)executionContext.getContextInstance().getVariable("userId");
		assignable.setActorId(userid);


	}
}
