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
import com.tdrouting.dto.Router;

public class TdroutingDao {
	private static HibernateUtil hibernateUtil = new HibernateUtil();
	
	
	public List<Router> getRouterlist(){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		System.out.println("From tdroutingDao.");
		String sqlString = "select name from router";
		Query query = session.createQuery(sqlString);
		List list = query.list();
		System.out.println(list);
		transaction.commit();
		return list;
	}
}