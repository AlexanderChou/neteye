package com.totalIP.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.base.util.HibernateUtil;
import com.base.model.NodeReachability;
import com.totalIP.dto.NodeReachabilityshow;

public class ReachabilityDao {
	public NodeReachabilityshow countReachability(String region) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select sum(nr.interruption) from NodeReachability nr where nr.network_type = 4 and nr.region = '" + region + "'";
		int interruption = Integer.parseInt(String.valueOf(session.createQuery(sql).uniqueResult()));
		session.close();
		NodeReachabilityshow nrs = new NodeReachabilityshow();
		int connection = new ReachabilityDao().countConnection("APNIC");
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
		String sql = "select sum(nr.interruption) from NodeReachability nr where nr.network_type = 4";
		int interruption = Integer.parseInt(String.valueOf(session.createQuery(sql).uniqueResult()));
		session.close();
		NodeReachabilityshow nrs = new NodeReachabilityshow();
		int connection = 5*(new ReachabilityDao().countConnection("APNIC"));
		double interruptrate = (double)interruption/(double)connection;
		nrs.setRegion("v4ALL");
		nrs.setInterruption(interruption);
		nrs.setConnection(connection);
		nrs.setInterrupt_rate(Math.round(interruptrate*10000)/10000.0000);
		return nrs;
	}
	public List<NodeReachabilityshow> getAllReachability() {
		List<NodeReachabilityshow> reachability = new ArrayList<NodeReachabilityshow>();
		reachability.add(new ReachabilityDao().countAllregionReachability());
		reachability.add(new ReachabilityDao().countReachability("APNIC"));
		reachability.add(new ReachabilityDao().countReachability("AFRNIC"));
		reachability.add(new ReachabilityDao().countReachability("ARIN"));
		reachability.add(new ReachabilityDao().countReachability("RIPE"));
		reachability.add(new ReachabilityDao().countReachability("LACNIC"));
		return reachability;
	}
	public int countConnection(String region) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<NodeReachability> NodeReachabilityList = session.createCriteria(NodeReachability.class).add(Restrictions.eq("region",region)).add(Restrictions.eq("network_type",4)).list();
		transaction.commit();
		return (3*NodeReachabilityList.size());
	}
	/*public static void main(String[] args){
		System.out.println(new ReachabilityDao().getAllReachability().get(5).getInterrupt_rate());
	}*/
}
