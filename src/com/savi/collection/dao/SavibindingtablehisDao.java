package com.savi.collection.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.savi.base.model.Savibindingtablehis;
import com.savi.base.model.Switchbasicinfo;
import com.savi.base.util.HibernateUtil;

public class SavibindingtablehisDao {
	private HibernateUtil hibernateUtil = new HibernateUtil();
	public List<Savibindingtablehis> getSwitchhisOnline(Long switchBasicInfoID,Integer ipVersion){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String hql ="select t from Savibindingtablehis t JOIN t.ifinterfacehis i JOIN i.switchhis h JOIN h.switchbasicinfo b"+
					" where b.id="+switchBasicInfoID+" and t.status=1 and h.ipVersion="+ipVersion;
		Query query = session.createQuery(hql);
		List<Savibindingtablehis> list = query.list();
		transaction.commit();
		return list;
	}
	public List<Savibindingtablehis> getSwitchhisOnline(Switchbasicinfo switchBasicInfo){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String hql ="select t from Savibindingtablehis t JOIN t.ifinterfacehis i JOIN i.switchhis h JOIN h.switchbasicinfo b"+
					" where b.id="+switchBasicInfo.getId()+" and t.status=1";
		Query query = session.createQuery(hql);
		List<Savibindingtablehis> list = query.list();
		transaction.commit();
		return list;
	}
	public int calculateTotalNum(){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql="select count(t) as num from Savibindingtablehis t JOIN t.ifinterfacehis i JOIN i.switchhis h" +
			" JOIN h.switchbasicinfo b where b.isDelete=0";
		Query query = session.createQuery(sql);
		List list = query.list();
		int num=0;
		try{
			num=Integer.parseInt(query.list().get(0).toString());
			transaction.commit();
			return num;
		}catch(Exception e){
			transaction.commit();
			return num;
		}

	}
	public void update(Savibindingtablehis bindingTable){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try{
			session.saveOrUpdate(bindingTable);
			transaction.commit();
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
		}
	}
	public static void main(String[] args) {
		SavibindingtablehisDao dao=new SavibindingtablehisDao();
		List<Savibindingtablehis> list=dao.getSwitchhisOnline(new Long(1), 1);
		System.out.println(list.size());
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i).getClass());
		}
	}
}
