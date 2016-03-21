package com.flow.action;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import com.base.util.Constants;
import com.opensymphony.xwork2.ActionSupport;

public class ViewMonitorAddAction  extends ActionSupport {
	private String[] checkedId;
	private String viewName;
	private String success;
	private String failure;
	
	public String execute() throws Exception {
		//为防止出现乱码，采用视图id作为文件名称进行保存
		String viewId = viewName.substring(viewName.lastIndexOf("_")+1,viewName.length());
		String filePath = Constants.webRealPath + "file/flow/config/" + viewId +".txt";
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileOutputStream(filePath));
			for(String str:checkedId){
				writer.println(str);
			}
			setSuccess("true");
			setFailure("false");
			return SUCCESS;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return ERROR;
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
	public String getViewName() {
		return viewName;
	}
	public void setViewName(String viewName) {
		this.viewName = viewName;
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
	public String[] getCheckedId() {
		return checkedId;
	}
	public void setCheckedId(String[] checkedId) {
		this.checkedId = checkedId;
	}
}
