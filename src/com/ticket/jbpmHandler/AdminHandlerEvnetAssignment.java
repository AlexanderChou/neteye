package com.ticket.jbpmHandler;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.exe.Assignable;

public class AdminHandlerEvnetAssignment implements AssignmentHandler {
    //事件平台的ticket选择admin作为负责人
	public void assign(Assignable assignable, ExecutionContext executionContext)
			throws Exception {
		String adminId =(String)executionContext.getContextInstance().getVariable("adminId");
		assignable.setActorId(adminId);
	}
}
