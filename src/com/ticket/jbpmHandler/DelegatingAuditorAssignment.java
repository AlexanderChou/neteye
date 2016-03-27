package com.ticket.jbpmHandler;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.exe.Assignable;
/**
 * 把委托人与当前的任务绑定
 * @author Administrator
 *
 */
public class DelegatingAuditorAssignment implements AssignmentHandler {

	public void assign(Assignable assignable, ExecutionContext executionContext)
			throws Exception {
		String delegatorId =(String)executionContext.getContextInstance().getVariable("delegatorId");
		assignable.setActorId(delegatorId);
	}
}
