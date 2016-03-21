package com.base.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;

import com.base.model.EventPojo;
import com.base.model.EventSeq;
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
public class EventSeqService {
	private Session session=null;
	/**
	 * 根据查询条件获得相应的事件列表
	 * @param condition
	 * @param start
	 * @param maxResults
	 * @return
	 * @throws Exception
	 */
	public List<EventSeq> getEventSeq(String condition,String start,String limit, int maxResults) throws Exception {
		List<EventSeq> events = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			if(maxResults!=0) {
				events = session.createQuery("from EventSeq " + condition).setMaxResults(maxResults).list();
			}
			else {
				events = session.createQuery("from EventSeq " + condition).setFirstResult(Integer.parseInt(start)).setMaxResults(Integer.parseInt(limit)).list();
			}
			tx.commit();
		}catch (Exception e) {
			if(tx!=null) {
				tx.rollback();
			}
			throw e;
		}
		return events;
	}
	
	/**
	 * 根据查询条件获得相应的事件列表总数
	 * @param condition
	 * @param start
	 * @param limit
	 * @return
	 */
	public Integer getEventSeqCount(String condition) {
		int count = 0;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			count = session.createQuery("from EventSeq " + condition).list().size(); //TODO 这里一个隐患 用size函数得到这个序列的总数 会牺牲很大
			tx.commit();
		}catch (Exception e) {
			if(tx!=null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		
		return count;
	}
	
	public Long save(EventSeq eventSeq) throws Exception{
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			session.save(eventSeq);
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
		}
		if(eventSeq.getId()!=null){
			return eventSeq.getId();
		}else{
			return null;
		}
	}
}
