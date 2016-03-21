package com.base.service;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.base.model.View;
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
public class ViewService extends BaseService {

	private Session session = null;

	public View findById(long id) throws Exception {
		View view = null;
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			view = (View) session.load(View.class, id);
			if (!Hibernate.isInitialized(view)) {
				Hibernate.initialize(view);
			}
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return view;
	}
	public View findByIdAndUserId(long id) throws Exception {
		View view = null;
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			long currentUserId = (Long)ServletActionContext.getRequest().getSession().getAttribute("userId");
			view = (View) session.createCriteria(View.class).add(Restrictions.eq("id", id)).add(Restrictions.eq("userId", currentUserId)).uniqueResult();
			if (!Hibernate.isInitialized(view)) {
				Hibernate.initialize(view);
			}
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return view;
	}

	public View deleteView(long id) throws Exception {
		View view = null;
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			view = (View) session.load(View.class, id);
			session.delete(view);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return view;
	}
	
	/**
	 * 根据用户id删除视图
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public View deleteViewByUserId(String userId) throws Exception {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		View view = new View();
		view.setUserId(Long.parseLong(userId));
		try {
			tx = session.beginTransaction();
			session.delete(view);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return view;
	}

	public View addViewByCondition(View view) throws Exception {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			if (view.getHomePage()) {
				String sql = "update View v set v.homePage=FALSE where v.homePage=TRUE";
				session.createQuery(sql).executeUpdate();
			}
			session.save(view);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return view;
	}
	/**
	 * 此方法返回系统中所有的视图列表（更改：仅获得当前用户所创建的所有视图，如果是系统管理员，可获得系统所有视图）
	 * @return
	 * @throws Exception
	 */
	public List<View> getViewList() throws Exception {
		List<View> list = null;
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			//long currentUserId = (Long)ServletActionContext.getRequest().getSession().getAttribute("userId");
			list = session.createQuery("from View v order by v.homePage desc").list();
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	public static View isNameUnique(String name) throws Exception {
		List<View> list = null;
		View view = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			list = session.createCriteria(View.class).add(
					Restrictions.eq("name", name)).list();
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		if (list!=null && list.size()>0) {
			view = list.get(0);
		}
		return view;
	}

	public static View findByHomePage() throws Exception {// 更改：找一    再 更改: 李宪亮  这里得出的必须是某个用户的主控界面。
		View view = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Long loginUserId = (Long)ServletActionContext.getRequest().getSession().getAttribute("userId"); //这里得注意存放的name和id应该一致。
			view = (View)session.createCriteria(View.class).add(Restrictions.eq("homePage", Boolean.TRUE)).add(Restrictions.eq("userId", loginUserId)).uniqueResult();
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return view;
	}

	public static Long CheckHomePage() throws Exception {
		Long num = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Long userId = (Long)ServletActionContext.getRequest().getSession().getAttribute("userId");
			String sql = "select count(*) as num from view where homepage=true and user_id=" + userId;
			num = (Long) session.createSQLQuery(sql).addScalar("num",
					Hibernate.LONG).uniqueResult();
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return num;
	}

	public static String CheckName(String name) throws Exception {
		String isHaveRecord = "no";
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			tx = session.beginTransaction();
			int count = (Integer)(session.createCriteria(View.class).add(Restrictions.eq("name", name)).setProjection(Projections.rowCount()).uniqueResult());
			if(count != 0){
				isHaveRecord = "yes";
			}
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return isHaveRecord;
	}
	public static boolean isExistByName(String name) throws Exception{
		Integer num;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			num = (Integer)session.createCriteria(View.class).add(Restrictions.eq("name",name)).
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
	public List<View> findPageById(long currentUserId) throws Exception{
		List<View> list = null;
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			list = session.createCriteria(View.class).add(Restrictions.and(Restrictions.eq("userId", currentUserId),Restrictions.eq("homePage", Boolean.TRUE))).list();
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return list;
	}
}
