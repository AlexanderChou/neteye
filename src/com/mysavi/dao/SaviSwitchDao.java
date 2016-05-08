package com.mysavi.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tdrouting.dto.Router;
import com.mysavi.model.HibernateUtil;

public class SaviSwitchDao {
	public List<Router> getSaviSwitch(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		List<Router> routerlist = session.createCriteria(Router.class).list();
		/*Query query = session.createQuery(sqlString);
		List<Object[]> list = query.list();
		Object object[] = list.get(0);
		System.out.println(object[0] + ", " + object[1]);*/
		transaction.commit();
		session.close();
		return routerlist;
	}
}
