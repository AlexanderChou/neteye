package com.mysavi.action;

import java.util.List;

import com.mysavi.dao.SaviSwitchDao;
import com.tdrouting.action.BaseAction;
import com.tdrouting.dto.Router;

public class SaviSwitchAction extends BaseAction {	
	private static final long serialVersionUID = 1L;
	private int count;
	private List<Router> routers;
	
	
	public String getSaviSwitch() {
		SaviSwitchDao dao = new SaviSwitchDao();
		routers = dao.getSaviSwitch();
		count = routers.size();
		return SUCCESS;
	}

	public int getCount() {
		return count;
	}

	public List<Router> getRouters() {
		return routers;
	}
}
