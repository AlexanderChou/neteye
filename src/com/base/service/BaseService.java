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
/**
 * 提供基本的CURD操作
 */
package com.base.service;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

import com.base.model.BaseEntity;

import com.base.util.HibernateUtil;

public class BaseService {
	
	public static void create(BaseEntity o) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			session.save(o);
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
	}
	public static void update(BaseEntity o) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			session.update(o);
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
	}
	public static void read(BaseEntity o) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			session.load(o, o.getId());
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
	}
	
	/**
	 * 根据类类型和id号得到对应的对象
	 * @param c
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public static BaseEntity read(java.lang.Class c, Long id) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			BaseEntity o=(BaseEntity)session.get(c, id);
			tx.commit();
			return o;
		}catch (Exception e)
		{
			if (tx != null)
			{
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
	}
	
	public static void delete(BaseEntity o) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			session.delete(o);
			tx.commit();
		}catch (Exception e){
			if (tx != null)
			{
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 根据类类型和id号删除对应的对象
	 * @param c
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public static void delete(java.lang.Class c, Long id) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			BaseEntity o=(BaseEntity)session.load(c, id);
			session.delete(o);
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
	}
	
	/**
	 * 删除一条记录
	 * @param sql
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public static boolean deleteRecordInfo(String sql, Integer id) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Query query = session.createQuery(sql);
			query.setInteger(0, id);
			query.executeUpdate();
			tx.commit();
			return true;
		}catch (Exception e){
			if (tx != null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}		
	}
	/**
	 * 通过查询语句获得所有记录
	 * @param sql
	 * @param className
	 * @return
	 * @throws Exception
	 */
	public static List getAllRecordInfo(String sql, String className) throws Exception {
		List result = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(Class.forName(className));
			if (sql != null && !sql.equals("")) criteria.add(Expression.sql(sql));
			result = criteria.list();
			tx.commit();
			return result;
		}catch (Exception e){
			if (tx != null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}	
	}
	/**
	 * 通过表名、主键名、主键ID得到某个记录
	 * @param id
	 * @param primaryKey
	 * @param className
	 * @return
	 * @throws Exception
	 */
	public static Object getRecordInfo(Object id, String primaryKey,String className) throws Exception {
		Object object = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(Class.forName(className));
			criteria.add(Expression.eq(primaryKey, id));
			List ivrs = criteria.list();
			Iterator it = ivrs.iterator();
			if(it.hasNext()) object = ivrs.iterator().next();
			tx.commit();
			return object;
		}catch (Exception e){
			if (tx != null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}	
	}
	
	/**
	 * 由该对象的名字检查这个对象是否存在
	 * @param name
	 * @return 为 true 说明该对象存在
	 */
	public static boolean checkObjectIsHaveByName(Class o, String name){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		List<Object> list = session.createCriteria(o).add(Restrictions.eq("name", name)).list();
		tx.commit();
		return !list.isEmpty();
	}
	
}
