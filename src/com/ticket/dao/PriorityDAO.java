package com.ticket.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.base.model.Category;
import com.base.model.Priority;
import com.base.util.HibernateUtil;

public class PriorityDAO {
	private HibernateUtil hibernateUtil = new HibernateUtil();
	/**
	 * 获得规定数目的优先级记录
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	public List<Priority> getPriorities(String firstResult, String maxResult) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		String sql = "select * from ticket_priority  limit " + firstResult + "," + maxResult ;
		List<Priority> prioritys = session.createSQLQuery(sql).addEntity(Priority.class).list();
		transaction.commit();
		return prioritys;
	}
	/**
	 * 获得所有优先级的记录数目
	 * @return
	 */
	public int getPriorityCount(){
		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		int count = (Integer)session.createCriteria(Priority.class).setProjection(Projections.rowCount()).uniqueResult();
		transaction.commit();
		return count;
	}
	public void delete(Long priorityId) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Priority priority = (Priority)session.get(Priority.class, priorityId);
		if(priority!=null){
			session.delete(priority);
		}
		transaction.commit();
	}
	public Priority getPriorityById(long id) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Priority priority = (Priority)session.get(Priority.class, id);
		transaction.commit();
		return priority;
	}
	public boolean checkPriorityNameIsExist(String priorityName){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Priority> prioritys = session.createCriteria(Priority.class).add(Restrictions.eq("name", priorityName)).list();
		transaction.commit();
		return !prioritys.isEmpty();
	}
	public Priority save(Priority priority) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(priority);
		transaction.commit();
		return priority;
	}
	/**
	 * 事件平台生成的ticket，其默认值为1级
	 * @param priorityName
	 * @return
	 */
	public Priority getPriorityByName(String priorityName){
		Priority priority = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Priority> prioritys = session.createCriteria(Priority.class).add(Restrictions.eq("name", priorityName)).list();
		if(prioritys!=null && prioritys.size()>0){
			priority = prioritys.get(0);
		}else{//如果不存在，创建一条新记录
			priority = new Priority();
			priority.setColor("255.0.255");
			priority.setName(priorityName);
			session.save(priority);
		}
		transaction.commit();
		return priority;
	}
}
