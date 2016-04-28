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
import com.tdrouting.dto.Ipv6routing;
import com.tdrouting.dto.Link;

public class LinkDao {
	private static HibernateUtil hibernateUtil = new HibernateUtil();
	
	
	public List<Link> getLinks() throws Exception {
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		List<Link> links = session.createCriteria(Link.class).list();
		System.out.println(links.size());
		transaction.commit();
		session.close();		
		return links;
	}

	public void clearAll()
	{
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		String sqlString = "delete from link where 1";
		SQLQuery query = session.createSQLQuery(sqlString);
		query.executeUpdate();
		transaction.commit();
		session.close();
	}
	
	public void save(List<Link> linkList)
	{
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		for (Link link : linkList)
			session.save(link); 
		transaction.commit();
		session.close();
	}
}
