package com.asset.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.base.model.AssetUser;


import com.base.util.HibernateUtil;


public class AssetUserDAO {
	private HibernateUtil hibernateUtil = new HibernateUtil();
	
	public List<AssetUser> getAssetUsers(String firstResult, String maxResult) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		String sql = "select * from asset_user  limit " + firstResult + "," + maxResult ;
		List<AssetUser> assetUsers = session.createSQLQuery(sql).addEntity(AssetUser.class).list();
		
		transaction.commit();
		return assetUsers;
	}
	public List<AssetUser> getAssetUserNames() {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		String sql = "select * from asset_user" ;
		List<AssetUser> assetUserNames = session.createSQLQuery(sql).addEntity(AssetUser.class).list();
		
		transaction.commit();
		return assetUserNames;
	}
	/**
	 * 获得所有项目的记录数目
	 * @return
	 */
	public int getAssetUsersCount(){
		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		int count = (Integer)session.createCriteria(AssetUser.class).setProjection(Projections.rowCount()).uniqueResult();
		transaction.commit();
		return count;
	}
	public void delete(Long assetUserId) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		AssetUser assetUser = (AssetUser)session.get(AssetUser.class, assetUserId);
		if(assetUser!=null){
			session.delete(assetUser);
		}
		transaction.commit();
	}
	public AssetUser getassetUserById(long id) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		AssetUser assetUser = (AssetUser)session.get(AssetUser.class, id);
		transaction.commit();
		return assetUser;
	}
	public boolean checkassetUserNameIsExist(String assetUserName){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<AssetUser> assetUsers = session.createCriteria(AssetUser.class).add(Restrictions.eq("userName", assetUserName)).list();
		transaction.commit();
		return !assetUsers.isEmpty();
	}
	public AssetUser save(AssetUser assetUser) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
	//	System.out.println("assetUser="+assetUser);
		session.saveOrUpdate(assetUser);
		transaction.commit();
		return assetUser;
	}

		

}
