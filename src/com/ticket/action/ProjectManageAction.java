package com.ticket.action;

import java.io.PrintWriter;
import java.util.List;

import com.base.model.Project;
import com.base.util.BaseAction;
import com.ticket.dao.ProjectDAO;

public class ProjectManageAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3146811980005589447L;
	private String totalCount;
	private boolean success;
	private boolean failure;
	private List<Project> projects;
	private Project project;
	private String name;
	private String description;
	private ProjectDAO projectDAO = new ProjectDAO();
	
	public String listProjects() throws Exception {
		String start = this.getRequest().getParameter("start");
		String limit = this.getRequest().getParameter("limit");
		projects = projectDAO.getProjects(start, limit);
		totalCount = String.valueOf(projectDAO.getProjectsCount());
		return SUCCESS;
	}
	public String deleteProject() throws Exception {
		String[] projectIds = this.getRequest().getParameter("projectIds").trim().split(";");
		for (String projectId : projectIds) {
			projectDAO.delete(Long.parseLong(projectId));
		}
		PrintWriter writer = this.getResponse().getWriter();
		writer.print("ok");
		writer.close();
		return null;
	}
	public String modifyProject() throws Exception {
		String projectId = this.getRequest().getParameter("projectId");
		Project project = projectDAO.getProjectById(Long.parseLong(projectId));
		boolean projectNameIsHave = projectDAO.checkProjectNameIsExist(name);
		if ((!project.getName().equals(name))){
		if (projectNameIsHave) {
			success = false;
			return SUCCESS;
		}  
		}
			project.setName(name);
			project.setDescription(description);
			projectDAO.save(project);
			success = true;
			return SUCCESS;
		
	}
	public String addProject() throws Exception {
		boolean projectNameIsHave = projectDAO.checkProjectNameIsExist(project.getName());
		if (projectNameIsHave) {
			 failure = true;
			return SUCCESS;
		}
		projectDAO.save(project);
		success = true;
		return SUCCESS;
	}
	public List<Project> getProjects() {
		return projects;
	}
	public void setProjects(List<Project> projects) {
		this.projects = projects;
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
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
