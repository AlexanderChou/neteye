package com.report.action;

import com.opensymphony.xwork2.ActionSupport;
import com.report.bpo.ReportBPO;

public class ReportDelete extends ActionSupport {
	private String deleteNode;
	private boolean success;
	public String execute() throws Exception{
		success = false;
		if(!deleteNode.equals("")){
			if(new ReportBPO().deleteTemplate(deleteNode)){
				success = true;
			}
		}
		return SUCCESS;
	}

	public String getDeleteNode() {
		return deleteNode;
	}

	public void setDeleteNode(String deleteNode) {
		this.deleteNode = deleteNode;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
