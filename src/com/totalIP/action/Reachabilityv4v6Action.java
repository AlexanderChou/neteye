package com.totalIP.action;

import java.util.List;

import com.base.util.BaseAction;
import com.totalIP.dao.ReachabilityDaov4v6;
import com.totalIP.dto.NodeReachabilityshow;

public class Reachabilityv4v6Action extends BaseAction {
	private List<NodeReachabilityshow> reachability;
	public String execute() throws Exception {
		reachability = new ReachabilityDaov4v6().getAllReachability();
		return SUCCESS;
	}
	public List<NodeReachabilityshow> getReachability() {
		return reachability;
	}
	public void setReachability(List<NodeReachabilityshow> reachability) {
		this.reachability = reachability;
	}
}
