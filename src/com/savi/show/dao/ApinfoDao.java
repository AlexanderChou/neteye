package com.savi.show.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.base.model.Device;
import com.savi.base.model.Apinfo;
import com.savi.base.model.Deviceinfo;
import com.savi.base.util.HibernateUtil;
import com.savi.show.dto.Ap;

public class ApinfoDao {
	private HibernateUtil hibernateUtil = new HibernateUtil();
	private Session session=null;
	
	//保存并更新APINFO
	public void saveApInfo(Apinfo apinfo) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try{
			session.saveOrUpdate(apinfo);
			transaction.commit();
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
		}
		
	}
			
	//根据acid,ipaddress获取APINFO
	public Apinfo getApinfoByAcidApid(Long acid, Integer apid) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Apinfo> apInfoList=session.createCriteria(Apinfo.class).add(Restrictions.eq("acid",acid)).add(Restrictions.eq("apid",apid)).list();
		if(apInfoList.size()==0){
			transaction.commit();
			return null;
		}
		transaction.commit();
		return apInfoList.get(0);
		
	}
	
	//根据acid,ipaddress获取APINFO
	@SuppressWarnings("unchecked")
	public Apinfo getApinfoByAcidIp(Long apid) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Apinfo> apInfoList=session.createCriteria(Apinfo.class).add(Restrictions.eq("apid",Integer.parseInt(apid.toString()))).list();
		if(apInfoList.size()==0){
			transaction.commit();
			return null;
		}
		transaction.commit();
		return apInfoList.get(0);
		
	}
	
//	@SuppressWarnings({ "static-access"})
//	public Apinfo getApinfoByAcidIp(Long apid) throws Exception{
//		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
//		Transaction transaction = session.beginTransaction();
//		Apinfo ac=(Apinfo)session.get(Apinfo.class, apid);
//		transaction.commit();
//		return ac;
//	}
	
			
	//根据ACID设置所有STATUS为0
		public void setStatusByAcid(Long acid) {
			Session session = hibernateUtil.getSessionFactory().getCurrentSession();
			Transaction transaction = session.beginTransaction();
			try{
				String hql="update Apinfo s set s.status = 0 " +
	            " where s.acid = :acid";
				Query query = session.createQuery(hql);
				query.setParameter("acid", acid);
				query.executeUpdate();
				transaction.commit();
			}catch(Exception e){
				e.printStackTrace();
				transaction.rollback();
			}
			
		}
		
		
	@SuppressWarnings("unchecked")
	public List<Apinfo> getApinfoList(long acid) throws Exception{
		List<Apinfo> list =null;
		session =hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			Criteria cri=session.createCriteria(Apinfo.class);
			cri.add(Restrictions.eq("acid",acid));
			cri.add(Restrictions.eq("status",1));
			cri.addOrder(Order.asc("apname"));
			list=cri.list();
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<Apinfo> getALLpA() throws Exception{
		List<Apinfo> list =null;
		session =hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			Criteria cri=session.createCriteria(Apinfo.class);
			cri.add(Restrictions.eq("status",1));
			cri.addOrder(Order.asc("apname"));
			
			list=cri.list();
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	
}
