package com.savi.user.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.savi.base.model.Administrator;
import com.savi.base.util.HibernateUtil;

public class AdminDao {
	private HibernateUtil hibernateUtil = new HibernateUtil();

	@SuppressWarnings( { "unchecked", "static-access" })
	public List<Administrator> listUsers(String firstResult, String maxResult)
			throws Exception {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Administrator> userList = session.createCriteria(
				Administrator.class).setFirstResult(
				Integer.parseInt(firstResult)).setMaxResults(
				Integer.parseInt(maxResult)).list();
		transaction.commit();
		return userList;
	}
	
	@SuppressWarnings("static-access")
	public Integer getUserCount(){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Integer count = (Integer)session.createCriteria(Administrator.class).
			setProjection(Projections.rowCount()).
			uniqueResult();
		transaction.commit();
		return count;
	}

	@SuppressWarnings("static-access")
	public void save(Administrator admin) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try{
			session.saveOrUpdate(admin);
			transaction.commit();
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
		}
	}

	@SuppressWarnings("static-access")
	public void delete(Administrator admin) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try{
			session.delete(admin);
			transaction.commit();
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
		}
	}

	@SuppressWarnings({ "unchecked", "static-access" })
	public Administrator getAdmin(String username) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Administrator> adminList = session.createCriteria(
				Administrator.class).add(Restrictions.eq("name", username))
				.list();
		transaction.commit();
		if (adminList.size() > 0)
			return adminList.get(0);
		else
			return null;
	}
}
