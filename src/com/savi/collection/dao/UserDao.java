package com.savi.collection.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.savi.base.model.Savibindingtablehis;
import com.savi.base.model.Switchbasicinfo;
import com.savi.base.model.User;
import com.savi.base.util.HibernateUtil;

public class UserDao {
	private HibernateUtil hibernateUtil = new HibernateUtil();
	public User getUser(){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<User> userList=session.createCriteria(User.class).list();
		if(userList.size()==0){
			transaction.commit();
			return null;
		}
		transaction.commit();
		return userList.get(0);
	}
	public void update(User user){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try{
			session.saveOrUpdate(user);
			transaction.commit();
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
		}
	}
}
