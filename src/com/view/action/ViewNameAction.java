package com.view.action;

import java.util.ArrayList;
import java.util.List;

import com.base.model.View;
import com.base.service.ViewService;
import com.base.util.BaseAction;

public class ViewNameAction extends BaseAction{
	private List viewList;
	
	public String execute() throws Exception{
		viewList=new ArrayList();
		ViewService service=new ViewService();
//		View view=new View();
//		view.setId(-1L);
//		view.setName("All");	
//		viewList.add(view);
		List temp=service.getViewList();
		if(temp!=null){
			for(int i=0;i<temp.size();i++){
				View viewNew=(View)temp.get(i);
				viewList.add(viewNew);
			}
		}
		
		return SUCCESS;
	}

	public List getViewList() {
		return viewList;
	}

	public void setViewList(List viewList) {
		this.viewList = viewList;
	}
}
