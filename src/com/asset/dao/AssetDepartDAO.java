package com.asset.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.base.model.AssetDepart;


import com.base.util.HibernateUtil;


public class AssetDepartDAO {
	private HibernateUtil hibernateUtil = new HibernateUtil();
	/**
	 * 获得规定数目的项目记录
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	public List<AssetDepart> getAssetDeparts(String firstResult, String maxResult) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		String sql = "select * from asset_depart  limit " + firstResult + "," + maxResult ;
		List<AssetDepart> assetDeparts = session.createSQLQuery(sql).addEntity(AssetDepart.class).list();
		
		transaction.commit();
		return assetDeparts;
	}
	public List<AssetDepart> getAssetDepartNames() {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		String sql = "select * from asset_depart  " ;
		List<AssetDepart> assetDepartNames = session.createSQLQuery(sql).addEntity(AssetDepart.class).list();
		
		transaction.commit();
		return assetDepartNames;
	}
	/**
	 * 获得所有项目的记录数目
	 * @return
	 */
	public int getAssetDepartsCount(){
		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		int count = (Integer)session.createCriteria(AssetDepart.class).setProjection(Projections.rowCount()).uniqueResult();
		transaction.commit();
		return count;
	}
	public void delete(Long assetDepartId) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		AssetDepart assetDepart = (AssetDepart)session.get(AssetDepart.class, assetDepartId);
		if(assetDepart!=null){
			session.delete(assetDepart);
		}
		transaction.commit();
	}
	public AssetDepart getassetDepartById(long id) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		AssetDepart assetDepart = (AssetDepart)session.get(AssetDepart.class, id);
		transaction.commit();
		return assetDepart;
	}
	public boolean checkassetDepartNameIsExist(String assetDepartName){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<AssetDepart> assetDeparts = session.createCriteria(AssetDepart.class).add(Restrictions.eq("name", assetDepartName)).list();
		transaction.commit();
		return !assetDeparts.isEmpty();
	}
	public AssetDepart save(AssetDepart assetDepart) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
//		System.out.println("assetDepart="+assetDepart);
		session.saveOrUpdate(assetDepart);
		transaction.commit();
		return assetDepart;
	}
	public String showdepartname(long id) throws Exception{
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		AssetDepart assetDepart = (AssetDepart)session.get(AssetDepart.class, id);
		String departname = assetDepart.getName();
		
		
		return departname;
	}

		

}
