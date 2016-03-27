package com.view.action;

import java.util.ArrayList;
import java.util.List;

import com.base.model.Device;
import com.base.model.Link;
import com.base.service.DeviceService;
import com.base.service.LinkService;
import com.base.service.PortService;
import com.opensymphony.xwork2.Action;

public class LinkAddForView {
	private String success;
	private String failure;
	private String checkedId;
	private List linklist=new ArrayList();
	private String resultlist= new String();;
	public String execute() throws Exception {
		DeviceService ds = new DeviceService();
		LinkService ls= new LinkService();
		linklist=ls.getLinkListByDeviceId(checkedId);
		setSuccess("true");
		setFailure("false");
		
		return Action.SUCCESS;
		
		
		
		
	}
	public List getLinklist() {
		return linklist;
	}
	public void setLinklist(List linklist) {
		this.linklist = linklist;
	}
	public String getCheckedId() {
		return checkedId;
	}
	public void setCheckedId(String checkedId) {
		this.checkedId = checkedId;
	}
	public String getResultlist() {
		return resultlist;
	}
	public void setResultlist(String resultlist) {
		this.resultlist = resultlist;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getFailure() {
		return failure;
	}
	public void setFailure(String failure) {
		this.failure = failure;
	}

	
	

}
