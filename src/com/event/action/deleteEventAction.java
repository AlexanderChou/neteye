package com.event.action;

import java.io.PrintWriter;

import com.base.util.BaseAction;
import com.event.dao.EventDAO;

public class deleteEventAction extends BaseAction {
	public String execute() throws Exception {
		String[] eventIds = this.getRequest().getParameter("eventIds").trim().split(";");
		for (String eventId : eventIds) {
			new EventDAO().delete(Long.valueOf(eventId));
		}
		PrintWriter writer = this.getResponse().getWriter();
		writer.print("ok");
		writer.close();
		return null;
	}
}
