package com.totalIP.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.base.model.NodeReachability;
import com.base.util.HibernateUtil;
import com.totalIP.dto.NodeReachabilityshow;

public class ReachabilityDaov6 {
	public NodeReachabilityshow countReachability(String region) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select sum(nr.interruption) from NodeReachability nr where nr.network_type = 6 and nr.region = '" + region + "'";
		int interruption = Integer.parseInt(String.valueOf(session.createQuery(sql).uniqueResult()));
		session.close();
		NodeReachabilityshow nrs = new NodeReachabilityshow();
		int connection = new ReachabilityDaov6().countConnection("APNIC");
		double interruptrate = (double)interruption/(double)connection;
		nrs.setRegion(region);
		nrs.setInterruption(interruption);
		nrs.setConnection(connection);
		nrs.setInterrupt_rate(Math.round(interruptrate*10000)/10000.0000);
		return nrs;
	}
	public NodeReachabilityshow countAllregionReachability() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select sum(nr.interruption) from NodeReachability nr where nr.network_type = 6";
		int interruption = Integer.parseInt(String.valueOf(session.createQuery(sql).uniqueResult()));
		session.close();
		NodeReachabilityshow nrs = new NodeReachabilityshow();
		int connection = 5*(new ReachabilityDaov6().countConnection("APNIC"));
		double interruptrate = (double)interruption/(double)connection;
		nrs.setRegion("v6ALL");
		nrs.setInterruption(interruption);
		nrs.setConnection(connection);
		nrs.setInterrupt_rate(Math.round(interruptrate*10000)/10000.0000);
		return nrs;
	}
	public List<NodeReachabilityshow> getAllReachability() {
		List<NodeReachabilityshow> reachability = new ArrayList<NodeReachabilityshow>();
		reachability.add(new ReachabilityDaov6().countAllregionReachability());
		reachability.add(new ReachabilityDaov6().countReachability("APNIC"));
		reachability.add(new ReachabilityDaov6().countReachability("AFRNIC"));
		reachability.add(new ReachabilityDaov6().countReachability("ARIN"));
		reachability.add(new ReachabilityDaov6().countReachability("RIPE"));
		reachability.add(new ReachabilityDaov6().countReachability("LACNIC"));
		return reachability;
	}
	public int countConnection(String region) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<NodeReachability> NodeReachabilityList = session.createCriteria(NodeReachability.class).add(Restrictions.eq("region",region)).add(Restrictions.eq("network_type",6)).list();
		transaction.commit();
		return (3*NodeReachabilityList.size());
	}
	/*public static void main(String[] args){
		System.out.println(new ReachabilityDao().getAllReachability().get(5).getInterrupt_rate());
	}*/
}
