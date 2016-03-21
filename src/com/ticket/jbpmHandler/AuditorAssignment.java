package com.ticket.jbpmHandler;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.exe.Assignable;

public class AuditorAssignment implements AssignmentHandler {
    //undertaker可以决定是不是要设置代理
	public void assign(Assignable assignable, ExecutionContext executionContext)
			throws Exception {
		String auditorId =(String)executionContext.getContextInstance().getVariable("auditorId");
		assignable.setActorId(auditorId);
	}
}
