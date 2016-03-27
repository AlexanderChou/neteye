package com.ticket.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.base.model.Category;
import com.base.util.HibernateUtil;

public class CategoryDAO {
	private HibernateUtil hibernateUtil = new HibernateUtil();
	/**
	 * 获得规定数目的分类记录
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	public List<Category> getCategories(String firstResult, String maxResult) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		String sql = "select * from ticket_category  limit " + firstResult + "," + maxResult ;
		List<Category> categories = session.createSQLQuery(sql).addEntity(Category.class).list();
		transaction.commit();
		return categories;
	}
	/**
	 * 获得所有分类的记录数目
	 * @return
	 */
	public int getCategoryCount(){
		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		int count = (Integer)session.createCriteria(Category.class).setProjection(Projections.rowCount()).uniqueResult();
		transaction.commit();
		return count;
	}
	public void delete(Long categoryId) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Category category = (Category)session.get(Category.class, categoryId);
		if(category!=null){
			session.delete(category);
		}
		transaction.commit();
	}
	public Category getCategoryById(long id) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Category category = (Category)session.get(Category.class, id);
		transaction.commit();
		return category;
	}
	
	public boolean checkCategoryNameIsExist(String categoryName){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Category> categories = session.createCriteria(Category.class).add(Restrictions.eq("name", categoryName)).list();
		transaction.commit();
		return !categories.isEmpty();
	}
	public Category save(Category category) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(category);
		transaction.commit();
		return category;
	}
	/**
	 * 事件平台生成ticket时调用
	 * @param categoryName
	 * @return
	 */
	public Category getCategoryByName(String categoryName){
		Category  category = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Category> categories = session.createCriteria(Category.class).add(Restrictions.eq("name", categoryName)).list();
		if(categories!=null && categories.size()>0){
			category = categories.get(0);
		}else{//如果不存在，创建一条新记录
			category = new Category();
			category.setDescription(categoryName);
			category.setName(categoryName);
			session.save(category);
		}
		transaction.commit();
		return category;
	}
}
