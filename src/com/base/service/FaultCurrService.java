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

import com.base.model.FaultCurrent;
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
public class FaultCurrService extends BaseService{
	
	private Session session=null;
	
	public FaultCurrent findById(long id) throws Exception{
		FaultCurrent FaultCurrent = null;
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			Criteria cri=session.createCriteria(FaultCurrent.class);
			cri.add(Restrictions.eq("id",id));
			FaultCurrent=(FaultCurrent)cri.uniqueResult();
			if(!Hibernate.isInitialized(FaultCurrent)){
				Hibernate.initialize(FaultCurrent);//为什么要初始化一下
			}
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return FaultCurrent;
	}

	public FaultCurrent getFaultCurrentByCondition(String condition) throws Exception {
		FaultCurrent FaultCurrent = null;
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			List temp = session.createQuery("from FaultCurrent " + condition).list(); 
			if(temp.size()>0) {
				FaultCurrent = (FaultCurrent)temp.get(0);
			}
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return FaultCurrent;
	}
	public FaultCurrent findFaultCurrentByIP(String IP) throws Exception{
		FaultCurrent FaultCurrent = new FaultCurrent();
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			Criteria cri=session.createCriteria(FaultCurrent.class);
			cri.add(Restrictions.eq("faultIp",IP));
			FaultCurrent=(FaultCurrent)cri.uniqueResult();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return FaultCurrent;
	}
	
	public Long save(FaultCurrent FaultCurrent) throws Exception{
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			session.save(FaultCurrent);
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
		}
		if(FaultCurrent.getId()!=null){
			return FaultCurrent.getId();
		}else{
			return null;
		}
	}
	public FaultCurrent deleteFaultCurrent(long id) throws Exception{
		FaultCurrent FaultCurrent =null;
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			FaultCurrent =(FaultCurrent) session.load(FaultCurrent.class, id);			
			session.delete(FaultCurrent);
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return FaultCurrent;
	}
	public static boolean isExistByIP(String ip) throws Exception{
		Integer num;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			num = (Integer)session.createCriteria(FaultCurrent.class).add(Restrictions.or(Restrictions.eq("loopbackIP",ip),Restrictions.eq("loopbackIPv6",ip))).
			setProjection(Projections.rowCount()).list().get(0);
			tx.commit();
		}catch (Exception e)
		{
			if (tx != null)
			{
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		if(num.intValue()==0)
			return false;
		else
			return true;
	}
	public Long getRepeatFaultCurrentNum(String sql) throws Exception {
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
	public List getAllFaultCurrent() throws Exception{
		List list=null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			list=session.createCriteria(FaultCurrent.class).list();
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
	public int getFaultCount(){
		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		int count = (Integer)session.createCriteria(FaultCurrent.class).setProjection(Projections.rowCount()).uniqueResult();
		transaction.commit();
		return count;
	}
	
	public List getFaultByNumber(String firstResult, String maxResult) throws Exception{
		List list=null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			list=session.createCriteria(FaultCurrent.class).addOrder(Order.desc("beginTime")).setFirstResult(Integer.parseInt(firstResult)).setMaxResults(Integer.parseInt(maxResult)).list();
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
}
