package com.totalIP.action;

import java.util.List;

import com.base.model.NodeReachability;
import com.base.util.BaseAction;
import com.totalIP.dao.ReachabilityDao;
import com.totalIP.dto.NodeReachabilityshow;
import com.totalIP.util.NodeUtil;

public class ReachabilityAction extends BaseAction {
	private List<NodeReachabilityshow> reachability;
	public String execute() throws Exception {
		reachability = new ReachabilityDao().getAllReachability();
		return SUCCESS;
	}
	public List<NodeReachabilityshow> getReachability() {
		return reachability;
	}
	public void setReachability(List<NodeReachabilityshow> reachability) {
		this.reachability = reachability;
	}
}
