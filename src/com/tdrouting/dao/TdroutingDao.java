package com.tdrouting.dao;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.SystemException;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import bsh.This;

import com.tdrouting.dao.HibernateUtil;
import com.tdrouting.dto.Router;

public class TdroutingDao {
	private static HibernateUtil hibernateUtil = new HibernateUtil();
	
	
	public List<Router> getRouterlist(){
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		List<Router> routerlist = session.createCriteria(Router.class).list();
		/*Query query = session.createQuery(sqlString);
		List<Object[]> list = query.list();
		Object object[] = list.get(0);
		System.out.println(object[0] + ", " + object[1]);*/
		transaction.commit();
		session.close();
		return routerlist;
	}
	
	public void clearAll() {
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		String sqlString = "delete from router where 1";
		SQLQuery query = session.createSQLQuery(sqlString);
		query.executeUpdate();
		transaction.commit();
		session.close();
	}
	
	public void save(List<Router> routerList)
	{
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		for (Router router : routerList)
			session.save(router); 
		transaction.commit();
		session.close();
	}
}