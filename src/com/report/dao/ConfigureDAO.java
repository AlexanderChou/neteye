package com.report.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;

import com.base.model.Configure;
import com.base.util.HibernateUtil;

public class ConfigureDAO {
	public Configure hasTemplate(String template, String flag) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		Configure configure = null;
		try {
			tx = session.beginTransaction();
			configure = (Configure) session.createCriteria(Configure.class)
					.add(Expression.eq("myTemplate", template)).add(
							Expression.eq("templateType", flag)).uniqueResult();
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return configure;
	}

	public void updateConfigure(Configure configure) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(configure);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
	}

	public void saveConfigure(Configure configure) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(configure);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
	}
}
