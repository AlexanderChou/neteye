package com.ticket.jbpmDecision;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;



public class EventTypeDecision implements DecisionHandler {

	
	public String decide(ExecutionContext executionContext) throws Exception {

		
		String eventtype = (String)executionContext.getContextInstance().getVariable("eventType");
		if(eventtype.equals("0")){
			return "new";
		}
		else if(eventtype.equals("1")){
			return "eventmodel";
		}
		else{
			return "new";
		}
	}

}
