package com.tdrouting.dao;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.control.TableSelectionModel;

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
import com.tdrouting.dto.Ipv6routing;
import com.tdrouting.dto.Ipv6tdrouting;
import com.tdrouting.dto.Router;
import com.tdrouting.dto.Tdroutingcost;

public class RoutingtableDao {
	private static HibernateUtil hibernateUtil = new HibernateUtil();
	
	
	public List<Ipv6routing> getIpv6routing(String routername){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Ipv6routing> table = session.createCriteria(Ipv6routing.class).add(Restrictions.eq("routername", routername)).list();
		System.out.println(table.size());
		transaction.commit();
		return table;
	}
	
	public List<Ipv6routing> getAllIpv6routing(){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Ipv6routing> table = session.createCriteria(Ipv6routing.class).list();
		System.out.println(table.size());
		transaction.commit();
		return table;
	}
	
	public List<Ipv6tdrouting> getIpv6tdrouting(String routername){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Ipv6tdrouting> table = session.createCriteria(Ipv6tdrouting.class).add(Restrictions.eq("routername", routername)).list();
		System.out.println(table.size());
		transaction.commit();
		return table;
	}
	
	public List<Ipv6tdrouting> getAllIpv6tdrouting(){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Ipv6tdrouting> table = session.createCriteria(Ipv6tdrouting.class).list();
		System.out.println(table.size());
		transaction.commit();
		return table;
	}
	
	public List<Tdroutingcost> getRoutingCost(String routername){
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		List<Tdroutingcost> list = session.createCriteria(Tdroutingcost.class).add(Restrictions.eq("routername", routername)).list();
		transaction.commit();
		session.close();
		return list;
	}
	
	public void clearAllRouting() {
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		String sqlString = "delete from ipv6routing where 1";
		SQLQuery query = session.createSQLQuery(sqlString);
		query.executeUpdate();
		transaction.commit();
		session.close();
	}
	
	public void clearAllTwodRouting()
	{
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		String sqlString = "delete from ipv6tdrouting where 1";
		SQLQuery query = session.createSQLQuery(sqlString);
		query.executeUpdate();
		transaction.commit();
		session.close();
	}
	
	public void saveRouting(List<Ipv6routing> v6routingList)
	{
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		for (Ipv6routing routing : v6routingList)
			session.save(routing); 
		transaction.commit();
		session.close();
	}
	
	public void saveTwodRouting(List<Ipv6tdrouting> v6tdroutingList)
	{
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		for (Ipv6tdrouting routing : v6tdroutingList)
			session.save(routing); 
		transaction.commit();
		session.close();
	}
	
	public void clearAllCost()
	{
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		String sqlString = "delete from tdroutingcost where 1";
		SQLQuery query = session.createSQLQuery(sqlString);
		query.executeUpdate();
		transaction.commit();
		session.close();
	}
	
	public void saveCost(List<Tdroutingcost> costList)
	{
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		for (Tdroutingcost cost : costList)
			session.save(cost); 
		transaction.commit();
		session.close();
	}
	
	public void saveOrUpdate(Tdroutingcost routingCost)
	{
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(routingCost);
		transaction.commit();
		session.close();	
	}
	
	public void delRoutingCost(Tdroutingcost routingCost)
	{
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.delete(routingCost);
		transaction.commit();
		session.close();
	}
}