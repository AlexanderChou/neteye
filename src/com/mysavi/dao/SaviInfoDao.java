package com.mysavi.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mysavi.model.HibernateUtil;
import com.mysavi.model.SaviInfo;

public class SaviInfoDao {
	
	public List<SaviInfo> getSaviInfo(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		List<SaviInfo> infoList = session.createCriteria(SaviInfo.class).list();
		/*Query query = session.createQuery(sqlString);
		List<Object[]> list = query.list();
		Object object[] = list.get(0);
		System.out.println(object[0] + ", " + object[1]);*/
		transaction.commit();
		session.close();
		return infoList;
	}

}
