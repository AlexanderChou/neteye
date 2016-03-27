package com.report.action;

import java.io.InputStream;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ReportDownload extends ActionSupport {
	private String inputPath;
	private String submitNodes;

	/*
	 * 下载用的Action应该返回一个InputStream实例， 该方法对应在result里的inputName属性值为targetFile
	 */
	public InputStream getTargetFile() throws Exception {
		return ServletActionContext.getServletContext().getResourceAsStream(
				inputPath);
	}

	public String execute() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		Map session = ctx.getSession();
		String user = (String) session.get("userName");
		// if ( user != null && user.equals("admin"))
		// {
		return SUCCESS;
		// }
		// ctx.put("tip" , "您还没有登陆，或者登陆的用户名不正确，请重新登陆！");
		// return LOGIN;
	}

	public void setInputPath(String value) {
		inputPath = value;
	}

	public String getSubmitNodes() {
		return submitNodes;
	}

	public void setSubmitNodes(String submitNodes) {
		this.submitNodes = submitNodes;
	}
}
