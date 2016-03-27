package com.report.action;

import java.util.ArrayList;
import java.util.List;

import com.base.model.Report;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.report.dao.ReportDAO;
import com.report.dto.CreatedTempInfo;

public class ReportCreate extends ActionSupport {
	private List<Report> allTemplates = new ArrayList<Report>();
	private List<CreatedTempInfo> templates = new ArrayList<CreatedTempInfo>();
	
	public  String execute() throws Exception{
		ReportDAO dao = new ReportDAO();
		String sql = "select rp.template from report rp where rp.templateType='1' group by rp.template";
		allTemplates = dao.getAllTemplate(sql);
		templates = dao.getTemplate(sql);
		ActionContext.getContext().getSession().put("allTemplates", allTemplates);
		ActionContext.getContext().getSession().put("templates", templates);
		return SUCCESS;
	}	
}
