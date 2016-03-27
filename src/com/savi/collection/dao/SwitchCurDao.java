package com.savi.collection.dao;


import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.savi.base.util.Constants;

import com.savi.base.model.Switchbasicinfo;
import com.savi.base.model.Switchcur;
import com.savi.base.util.HibernateUtil;

public class SwitchCurDao {
	private HibernateUtil hibernateUtil = new HibernateUtil();	
	public void saveSwitchCurInfo(Switchcur switchCur){
  		synchronized(Constants.lock2){
			Session session = hibernateUtil.getSessionFactory().getCurrentSession();
			Transaction transaction = session.beginTransaction();
			try{
				session.saveOrUpdate(switchCur);
				transaction.commit();
			}catch(Exception e){
				e.printStackTrace();
				transaction.rollback();
			}
  		}
	}
	public List<Switchcur> getSwitchCurList(Switchbasicinfo switchBasicInfo){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Switchcur> switchCurList=session.createCriteria(Switchcur.class).add(Restrictions.eq("switchbasicinfo",switchBasicInfo)).list();
		transaction.commit();
		return switchCurList;
	}
	public List<Switchcur> getSwitchCur(Switchbasicinfo switchBasicInfo,int ipVersion){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Switchcur> switchCurList=session.createCriteria(Switchcur.class).
					add(Restrictions.eq("switchbasicinfo",switchBasicInfo)).
					add(Restrictions.eq("ipVersion",ipVersion)).list();
		transaction.commit();
		if(switchCurList!=null&&switchCurList.size()!=0){
			return switchCurList;
		}else{
			return null;
		} 
	}
	public void delete(Switchcur switchCur){
		synchronized(Constants.lock2){
			Session session = hibernateUtil.getSessionFactory().getCurrentSession();
			Transaction transaction = session.beginTransaction();
			try{
				session.delete(switchCur);
				transaction.commit();
			}catch(Exception e){
				e.printStackTrace();
				transaction.rollback();
			}
		}
	}
	public static void main(String[] args) {
		SwitchCurDao dao=new SwitchCurDao();
		SwitchBasicInfoDao InfoDao=new SwitchBasicInfoDao();
		Switchbasicinfo switchBasicInfo=InfoDao.getSwitchBasicInfo(new Long(100));
		List<Switchcur> list=dao.getSwitchCurList(switchBasicInfo);
		System.out.println(list.size());
		for(int i=0;i<list.size();i++){
			
		}
	}
}
