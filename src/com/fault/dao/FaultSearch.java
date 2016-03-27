package com.fault.dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.base.model.FaultCurrent;
import com.base.service.BaseService;
import com.base.util.HibernateUtil;

public class FaultSearch extends BaseService {
	public List getFaultByTime(Timestamp startTime,Timestamp endTime,String firstResult, String maxResult) throws Exception{
		List list=null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
			tx = session.beginTransaction();
			list=session.createCriteria(FaultCurrent.class).add(Restrictions.ge("beginTime",startTime)).
			add(Restrictions.le("beginTime",endTime)).
			setFirstResult(Integer.parseInt(firstResult)).setMaxResults(Integer.parseInt(maxResult)).list();
			tx.commit();
		return list;
	}
	
	public int getFaultTimeCount(Timestamp start,Timestamp end){
		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		int count = (Integer)session.createCriteria(FaultCurrent.class).
		add(Restrictions.ge("beginTime",start)).add(Restrictions.le("beginTime",end)).
		setProjection(Projections.rowCount()).uniqueResult();
		transaction.commit();
		return count;
	}
	public List getFaultByIp(String ip,String firstResult, String maxResult) throws Exception{
		List list=null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			list=session.createCriteria(FaultCurrent.class).add(Restrictions.eq("faultIp",ip)).
			setFirstResult(Integer.parseInt(firstResult)).setMaxResults(Integer.parseInt(maxResult)).list();
			tx.commit();
		}catch (Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	public int getFaultIpCount(String ip){
		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		int count = (Integer)session.createCriteria(FaultCurrent.class).
		add(Restrictions.eq("faultIp",ip)).setProjection(Projections.rowCount()).uniqueResult();
		transaction.commit();
		return count;
	}
}
