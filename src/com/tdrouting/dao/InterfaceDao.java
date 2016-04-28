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

import com.tdrouting.dao.HibernateUtil;
import com.tdrouting.dto.Interface;
import com.tdrouting.dto.Router;

public class InterfaceDao {
	private static HibernateUtil hibernateUtil = new HibernateUtil();
	
	
	public List<Interface> getInterfaces(String routername){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Interface> interfaces = session.createCriteria(Interface.class).add(Restrictions.eq("routername", routername)).list();
		/*Query query = session.createQuery(sqlString);
		List<Object[]> list = query.list();
		Object object[] = list.get(0);
		System.out.println(object[0] + ", " + object[1]);*/
		transaction.commit();
		return interfaces;
	}
	
	public List<Interface> getAllInterfaces(){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Interface> interfaces = session.createCriteria(Interface.class).list();
		transaction.commit();
		return interfaces;
	}
	
	public void clearAll() {
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		String sqlString = "delete from interface where 1";
		SQLQuery query = session.createSQLQuery(sqlString);
		query.executeUpdate();
		transaction.commit();
		session.close();
	}
	
	public void save(List<Interface> interfaceList)
	{
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		for (Interface iface : interfaceList)
			session.save(iface); 
		transaction.commit();
		session.close();
	}
}