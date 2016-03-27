package com.base.service;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.base.model.FaultHistory;
import com.base.util.HibernateUtil;

/*
** Copyright (c) 2008, 2009, 2010
**      The Regents of the Tsinghua University, PRC.  All rights reserved.
**
** Redistribution and use in source and binary forms, with or without  modification, are permitted provided that the following conditions are met:
** 1. Redistributions of source code must retain the above copyright  notice, this list of conditions and the following disclaimer.
** 2. Redistributions in binary form must reproduce the above copyright  notice, this list of conditions and the following disclaimer in the  documentation and/or other materials provided with the distribution.
** 3. All advertising materials mentioning features or use of this software  must display the following acknowledgement:
**  This product includes software (iNetBoss) developed by Tsinghua University, PRC and its contributors.
** THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
** ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
** IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
** ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
** FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
** DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
** OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
** HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
** LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
** OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
** SUCH DAMAGE.
*
*/
public class FaultHisService extends BaseService{
	
	private Session session=null;
	
	public FaultHistory findById(long id) throws Exception{
		FaultHistory FaultHistory = null;
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			Criteria cri=session.createCriteria(FaultHistory.class);
			cri.add(Restrictions.eq("id",id));
			FaultHistory=(FaultHistory)cri.uniqueResult();
			if(!Hibernate.isInitialized(FaultHistory)){
				Hibernate.initialize(FaultHistory);//为什么要初始化一下
			}
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return FaultHistory;
	}

	public FaultHistory getFaultHistoryByCondition(String condition) throws Exception {
		FaultHistory FaultHistory = null;
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			List temp = session.createQuery("from FaultHistory " + condition).list(); 
			if(temp.size()>0) {
				FaultHistory = (FaultHistory)temp.get(0);
			}
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return FaultHistory;
	}
	public FaultHistory findFaultHistoryByIP(String IP) throws Exception{
		FaultHistory FaultHistory = new FaultHistory();
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			Criteria cri=session.createCriteria(FaultHistory.class);
			cri.add(Restrictions.eq("faultIp",IP));
			FaultHistory=(FaultHistory)cri.uniqueResult();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return FaultHistory;
	}
	
	public Long save(FaultHistory FaultHistory) throws Exception{
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			session.save(FaultHistory);
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
		}
		if(FaultHistory.getId()!=null){
			return FaultHistory.getId();
		}else{
			return null;
		}
	}
	public FaultHistory deleteFaultHistory(long id) throws Exception{
		FaultHistory FaultHistory =null;
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			FaultHistory =(FaultHistory) session.load(FaultHistory.class, id);			
			session.delete(FaultHistory);
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return FaultHistory;
	}
	public Long getRepeatFaultHistoryNum(String sql) throws Exception {
		Long num;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			num = (Long)session.createSQLQuery(sql).addScalar("num",Hibernate.LONG).uniqueResult();
			tx.commit();
		}catch (Exception e){
			if (tx != null)
			{
				tx.rollback();
			}
			throw e;
		}
		return num;
	}
	public List getAllFaultHistory() throws Exception{
		List list=null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			list=session.createCriteria(FaultHistory.class).list();
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
	public List getAllFaultByTime(Timestamp newTime) throws Exception{
		List list=null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			list=session.createCriteria(FaultHistory.class).add(Restrictions.ge("beginTime",newTime)).list();
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
	public List getAllFault(Timestamp newTime,String firstResult, String maxResult) throws Exception{
		List list=null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			list=session.createCriteria(FaultHistory.class).addOrder(Order.desc("beginTime")).add(Restrictions.ge("beginTime",newTime)).
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
	public List getFaultByTime(Timestamp startTime,Timestamp endTime,String firstResult, String maxResult) throws Exception{
		List list=null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			list=session.createCriteria(FaultHistory.class).add(Restrictions.ge("beginTime",startTime)).
			add(Restrictions.le("recoverTime",endTime)).addOrder(Order.desc("beginTime")).
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
	public List getFaultByIp(String ip,String firstResult, String maxResult) throws Exception{
		List list=null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			list=session.createCriteria(FaultHistory.class).add(Restrictions.eq("faultIp",ip)).addOrder(Order.desc("beginTime")).
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
	public List getFaultTimeNull() throws Exception{
		List list=null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			list=session.createCriteria(FaultHistory.class).add(Restrictions.or(Restrictions.eq("persistTime",""),Restrictions.isNull("persistTime"))).list();
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
	public int getFaultCount(Timestamp startTime){
		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		int count = (Integer)session.createCriteria(FaultHistory.class).add(Restrictions.ge("beginTime",startTime)).setProjection(Projections.rowCount()).uniqueResult();
		transaction.commit();
		return count;
	}
	public int getFaultTimeCount(Timestamp start,Timestamp end){
		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		int count = (Integer)session.createCriteria(FaultHistory.class).
		add(Restrictions.ge("beginTime",start)).add(Restrictions.le("recoverTime",end)).
		setProjection(Projections.rowCount()).uniqueResult();
		transaction.commit();
		return count;
	}
	public int getFaultIpCount(String ip){
		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		int count = (Integer)session.createCriteria(FaultHistory.class).
		add(Restrictions.eq("faultIp",ip)).setProjection(Projections.rowCount()).uniqueResult();
		transaction.commit();
		return count;
	}
}
