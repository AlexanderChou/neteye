package com.savi.collection.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.savi.base.util.Constants;
import com.savi.base.model.Switchhis;
import com.savi.base.util.HibernateUtil;

public class SwitchHisDao {
	private HibernateUtil hibernateUtil = new HibernateUtil();
    public void saveSwitchHisInfo(Switchhis switchHis){
    	synchronized(Constants.lock3){
			Session session = hibernateUtil.getSessionFactory().getCurrentSession();
			Transaction transaction = session.beginTransaction();
			try{
				session.saveOrUpdate(switchHis);
				transaction.commit();
			}catch(Exception e){
				e.printStackTrace();
				transaction.rollback();
			}
    	}
    }
}

