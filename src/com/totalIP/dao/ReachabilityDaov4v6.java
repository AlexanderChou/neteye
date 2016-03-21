package com.totalIP.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.base.model.NodeReachability;
import com.base.util.HibernateUtil;
import com.totalIP.dto.NodeReachabilityshow;

public class ReachabilityDaov4v6 {
	public List<NodeReachabilityshow> getAllReachability() {
		List<NodeReachabilityshow> reachability = new ArrayList<NodeReachabilityshow>();
		reachability.add(new ReachabilityDao().countAllregionReachability());
		reachability.add(new ReachabilityDaov6().countAllregionReachability());
		return reachability;
	}
}
