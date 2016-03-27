package com.report.action;

import java.util.ArrayList;
import java.util.List;

import com.base.model.Report;
import com.opensymphony.xwork2.ActionSupport;
import com.report.dao.ReportDAO;
import com.report.dto.CreatedTempInfo;

public class ReportSelfCreate extends ActionSupport {
	private List<Report> allTemplates = new ArrayList<Report>();
	private List<CreatedTempInfo> selfTemplates = new ArrayList<CreatedTempInfo>();
	ReportDAO dao = new ReportDAO();

	public String allSelfTemplates() throws Exception {
		String sql = "select * from report rp where rp.templateType='2' group by rp.myTemplate";
		allTemplates = dao.getAllTemplate(sql);
		return SUCCESS;
	}

	public String selfTemplates() throws Exception {
		String sql = "select rp.myTemplate from report rp where rp.templateType='2' group by rp.myTemplate";
		selfTemplates = dao.getTemplate(sql);
		return SUCCESS;
	}

	public List<Report> getAllTemplates() {
		return allTemplates;
	}

	public void setAllTemplates(List<Report> allTemplates) {
		this.allTemplates = allTemplates;
	}

	public List<CreatedTempInfo> getSelfTemplates() {
		return selfTemplates;
	}

	public void setSelfTemplates(List<CreatedTempInfo> selfTemplates) {
		this.selfTemplates = selfTemplates;
	}
}
