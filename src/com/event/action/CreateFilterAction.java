package com.event.action;

import com.event.dao.EventDAO;
import com.opensymphony.xwork2.ActionSupport;

public class CreateFilterAction extends  ActionSupport{
	public String execute() throws Exception{
		new EventDAO().createFilter();
		String cmd = "service eventd restart";
		try{ 
			Process ps=java.lang.Runtime.getRuntime().exec(cmd + " 2>1 >/dev/null &");  
			ps.getErrorStream();
		}catch(java.io.IOException   e){
			e.printStackTrace();             
		} 
		return SUCCESS;
	}
}
