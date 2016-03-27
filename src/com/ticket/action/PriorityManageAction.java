package com.ticket.action;

import java.io.PrintWriter;
import java.util.List;

import com.base.model.Priority;
import com.base.util.BaseAction;
import com.ticket.dao.PriorityDAO;

public class PriorityManageAction extends BaseAction {
	private String totalCount;
	private boolean success;
	private boolean failure;
	private List<Priority> priorities;
	private Priority priority;
	private PriorityDAO priorityDAO = new PriorityDAO();
	
	public String listPriorities() throws Exception {
		String start = this.getRequest().getParameter("start");
		String limit = this.getRequest().getParameter("limit");
		priorities = priorityDAO.getPriorities(start, limit);
		totalCount = String.valueOf(priorityDAO.getPriorityCount());
		return SUCCESS;
	}
	public String deletePriority() throws Exception {
		String[] priorityIds = this.getRequest().getParameter("priorityIds").trim().split(";");
		for (String priorityId : priorityIds) {
			priorityDAO.delete(Long.parseLong(priorityId));
		}
		PrintWriter writer = this.getResponse().getWriter();
		writer.print("ok");
		writer.close();
		return null;
	}
	public String modifyPriority() throws Exception {
		String priorityId = this.getRequest().getParameter("priorityId");
		String name = this.getRequest().getParameter("name");
		String color = this.getRequest().getParameter("color");
		Priority priority = priorityDAO.getPriorityById(Long.parseLong(priorityId));
		
		boolean priorityNameIsHave = priorityDAO.checkPriorityNameIsExist(name);
		if (!(priority.getName().equals(name))){
		if (priorityNameIsHave) {
			PrintWriter w = this.getResponse().getWriter();
			w.print("same");
			w.close();
			return null;
		} 
		}
			priority.setName(name);
			priority.setColor(color);
			priorityDAO.save(priority);
			PrintWriter write = this.getResponse().getWriter();
			write.print("ok");
			write.close();
			return null;
		}
	
	public String addPriority() throws Exception {
		boolean priorityNameIsHave = priorityDAO.checkPriorityNameIsExist(priority.getName());
		if (priorityNameIsHave) {
			failure = true;
			return SUCCESS;
		}
		priorityDAO.save(priority);
		success = true;
		return SUCCESS;
	}
	public List<Priority> getPriorities() {
		return priorities;
	}
	public void setPriorities(List<Priority> priorities) {
		this.priorities = priorities;
	}
	public Priority getPriority() {
		return priority;
	}
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public boolean isFailure() {
		return failure;
	}
	public void setFailure(boolean failure) {
		this.failure = failure;
	}
	
}
