package com.totalIP.action;

import com.base.util.BaseAction;

public class TotalNodeOneDateManageAction extends BaseAction{
	private String dateStr ;
	
	public String execute() throws Exception {
		
		return SUCCESS;
	
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	
}
