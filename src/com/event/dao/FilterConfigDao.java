package com.event.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.base.model.FilterConfig;


import com.base.util.HibernateUtil;


public class FilterConfigDao {
	private HibernateUtil hibernateUtil = new HibernateUtil();
	/**
	 * 获得规定数目的项目记录
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	public List<FilterConfig> getFilterConfigs() {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		String sql = "select * from filter_config" ;
		List<FilterConfig> filterConfigs = session.createSQLQuery(sql).addEntity(FilterConfig.class).list();
		
		transaction.commit();
		return filterConfigs;
	}
	
	
	public FilterConfig save(FilterConfig filterConfig) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(filterConfig);
		transaction.commit();
		return filterConfig;
	}
	public void delete(Long FilterConfigid) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		FilterConfig filterConfig = (FilterConfig)session.get(FilterConfig.class, FilterConfigid);
		if(filterConfig!=null){
			session.delete(filterConfig);
		}
		transaction.commit();
	}

		

}