package com.savi.collection.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.savi.base.util.Constants;
import com.savi.base.model.Switchbasicinfo;
import com.savi.base.util.HibernateUtil;

public class SwitchBasicInfoDao {
	private HibernateUtil hibernateUtil = new HibernateUtil();
    public void update(Switchbasicinfo switchBasicInfo){
    	synchronized(Constants.lock2){
			Session session = hibernateUtil.getSessionFactory().getCurrentSession();
			Transaction transaction = session.beginTransaction();
			try{
				session.saveOrUpdate(switchBasicInfo);
				transaction.commit();
			}catch(Exception e){
				e.printStackTrace();
				transaction.rollback();
			}
    	}
    }
	public List<Switchbasicinfo> getSwitchBasicInfoList(Integer isDelete){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Switchbasicinfo> switchbasicinfoList=session.createCriteria(Switchbasicinfo.class).add(Restrictions.eq("isDelete",isDelete)).list();
		transaction.commit();
		return switchbasicinfoList;
	}
	
	//倒序排序，未被删除的交换机
	public List<Switchbasicinfo> getSwitchBasicInfoListByDel(Integer isDelete){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Switchbasicinfo> switchbasicinfoList=session.createCriteria(Switchbasicinfo.class).add(Restrictions.eq("isDelete",isDelete)).addOrder(Order.desc("id")).list();
		transaction.commit();
		return switchbasicinfoList;
	}
	
	//在CollectionTest类中测试使用
	public Switchbasicinfo getSwitchBasicInfo(Long id){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Switchbasicinfo> switchbasicinfoList=session.createCriteria(Switchbasicinfo.class).add(Restrictions.eq("id",id)).list();
		if(switchbasicinfoList.size()==0){
			transaction.commit();
			return null;
		}
		transaction.commit();
		return switchbasicinfoList.get(0);
		
	}
}
