package com.savi.show.dao;





import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.savi.base.model.FilterCountCur;
import com.savi.base.util.HibernateUtil;


public class FilterCountCurDao {
	private HibernateUtil hibernateUtil = new HibernateUtil();
	
	public void saveFilterCountCur(FilterCountCur filterCountCur) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try{
			session.saveOrUpdate(filterCountCur);
			transaction.commit();
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
		}
		
	}

	
	public void deleteByAcid(Long acid) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try{
			String hql="delete from FilterCountCur f " +
            " where f.acID = :acID";
			Query query = session.createQuery(hql);
			query.setParameter("acID", acid);
			query.executeUpdate();
			transaction.commit();
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
		}

	
}
	
	@SuppressWarnings("unchecked")
	public Integer getApinfoList(long acid) throws Exception{
		List<FilterCountCur> list =null;
//		Session session =hibernateUtil.getSessionFactory().getCurrentSession();
//		Transaction tx=null;
//		try{
//			tx=session.beginTransaction();
//			Criteria cri=session.createCriteria(FilterCountCur.class);
//			cri.add(Restrictions.eq("acID",acid));
//			list=cri.list();
//			tx.commit();
//		
//			
//			
//		}catch(Exception e){
//			if(tx!=null){
//				tx.rollback();
//			}
//			e.printStackTrace();
//			throw e;
//		}
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Integer count=0;
		try{
			String hql="select sum(f.ifFilteringCount) from FilterCountCur f " +
            " where f.acID = :acID";
			Query query = session.createQuery(hql);
			query.setParameter("acID", acid);
			list=query.list();
			if(list!=null&&list.size()!=0&&list.get(0)!=null){
				Object countl=list.get(0);
				count=Integer.parseInt(countl.toString());
				if(count < 1 ) count = 0;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
		}
		transaction.commit();
		return count;
	}
}
