package com.user.dao;

import org.hibernate.Session;

import com.base.util.HibernateUtil;


/**
 * Data access object (DAO) for domain model
 * @author MyEclipse Persistence Tools
 */
public class BaseHibernateDAO implements IBaseHibernateDAO {
	
	public Session getSession() {
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}
	
}