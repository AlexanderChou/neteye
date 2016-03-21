package com.savi.show.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


import com.savi.base.model.Apinfo;
import com.savi.base.model.SaviFilterTableCur;
import com.savi.base.util.HibernateUtil;
import com.savi.show.dto.SaviFilterTableCurInfo;


public class SaviFilterTableCurDao {
	private HibernateUtil hibernateUtil = new HibernateUtil();
	
	//查询userName不为空的记录
	public List<SaviFilterTableCur> getListUserNameisNull() {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Long timeLong = new Date().getTime() - 2*60*60*1000;
		System.out.println(timeLong);
		List<SaviFilterTableCur> saviFilterTableCurList = session.createCriteria(SaviFilterTableCur.class).add(Restrictions.or(Restrictions.isNull("userName"), Restrictions.eq("userName", ""))).add(Restrictions.ge("startTime", timeLong)).add(Restrictions.isNull("endTime")).list();
		if(saviFilterTableCurList.size() == 0){
			transaction.commit();
			return null;
		}
		transaction.commit();
		return saviFilterTableCurList;
	}
		
	public void saveSaviFilterTableCur(SaviFilterTableCur saviFilterTableCur) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try{
			session.saveOrUpdate(saviFilterTableCur);
			transaction.commit();
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
		}
		
	}
	
	//根据MAC，IP地址查询当前在线数
	public List<SaviFilterTableCur> getListByMacIp(String mac, String ip, Integer apid) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<SaviFilterTableCur> saviFilterTableCurList = session.createCriteria(SaviFilterTableCur.class).add(Restrictions.eq("macAddress",mac)).add(Restrictions.eq("ipAddress",ip)).add(Restrictions.eq("apid",apid.longValue())).list();
		if(saviFilterTableCurList.size() == 0){
			transaction.commit();
			return null;
		}
		transaction.commit();
		return saviFilterTableCurList;
	}
	
	//获取在线用户数据，并且根据时间倒排序
	public List<SaviFilterTableCur> getSaviFilterTableCurListOrderByTimer() {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<SaviFilterTableCur> deviceinfoList=session.createCriteria(SaviFilterTableCur.class).add(Restrictions.isNotNull("apName")).add(Restrictions.ne("apName", "")).addOrder(Order.desc("startTime")).list();
		transaction.commit();
		return deviceinfoList;
	}
	
	public List<SaviFilterTableCur> getSaviFilterTableCurList() {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<SaviFilterTableCur> deviceinfoList=session.createCriteria(SaviFilterTableCur.class).list();
		transaction.commit();
		return deviceinfoList;
	}
	
	@SuppressWarnings("unchecked")
	public List<SaviFilterTableCur> getApinfoList(long apid) throws Exception{
		List<SaviFilterTableCur> list =null;
		Session session =hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			Criteria cri=session.createCriteria(SaviFilterTableCur.class);
			cri.add(Restrictions.eq("apid",apid));
			cri.add(Restrictions.isNull("endTime"));
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
	public List<SaviFilterTableCur> getApinfoSizeByapId(long apid) throws Exception{
		List<SaviFilterTableCur> list =null;
		Session session =hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			String hql="select distinct s.macAddress from  SaviFilterTableCur s " + 
					 " where apid = " + apid + 
					 " and endTime is null";
			tx=session.beginTransaction();
			Query query = session.createQuery(hql);
			list = query.list();
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
	
	public void updateEndTime() {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try{
			Long endtimeDate = new Date().getTime();
			String hql="update SaviFilterTableCur s set s.endTime = :endTime " +
            " where s.endTime is null";
			Query query = session.createQuery(hql);
			query.setParameter("endTime", endtimeDate);
			query.executeUpdate();
			transaction.commit();
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
		}
		
	}
	
	//获取所有在线数据
	public List<SaviFilterTableCurInfo> getAllList(int hour, String start, String limit, List<Integer> totalCount) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		int sta=Integer.parseInt(start);
		int end=Integer.parseInt(start)+Integer.parseInt(limit)-1;
		Long millis = System.currentTimeMillis() - hour * 60 * 60 * 1000;
		Date d = new Date(millis);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(df.format(d));
		System.out.println(hour + "以前时间毫秒" + millis);
		String sql ="select t.userName,t.ipAddress,t.startTime,a.apname,t.macAddress from SaviFilterTableCur t,Apinfo a "+
		" where t.apid = a.apid ";
		if(hour != 0){
			sql += "and (t.startTime >=" + millis +") ";
		}
		sql += " order by t.startTime desc";
		Query query = session.createQuery(sql);
		//query.setFirstResult(Integer.parseInt(start));
		//query.setMaxResults(Integer.parseInt(limit));
		List list = query.list();
		List<SaviFilterTableCurInfo> newList = new ArrayList<SaviFilterTableCurInfo>();
		int counter=0;
		if(null != list && list.size() > 0){
			
			//Iterator it = list.iterator();
			//while(it.hasNext()){
			for(int i = 0;i< list.size();i++){
				if(counter >= sta&&counter <= end){
					Object [] obj = new Object[5];
					obj = (Object[])list.get(i);
					String userName = "";
					String ipAddress = "";
					Long startTime = null;
					String apname = "";
					String macAddress = "";
					if(null != obj[0]){
						userName = obj[0].toString();
					}
					if(null != obj[1]){
						ipAddress =  obj[1].toString();
					}
					if(null != obj[2]){
						startTime = Long.parseLong(obj[2].toString());
					}
					if(null != obj[3]){
						apname = obj[3].toString();
					}
					if(null != obj[4]){
						macAddress = obj[4].toString();
					}
					SaviFilterTableCurInfo info = new SaviFilterTableCurInfo(userName, ipAddress, startTime, apname, macAddress);
					newList.add(info);
				}
				counter++;
			}
				
			//}
		}else{
			totalCount.add(0);
			return newList;
		}
		totalCount.add(counter);
		transaction.commit();
		return newList;
	}
	
	 public static void main(String[] args) {
		 SaviFilterTableCurDao saviFilterTableCurDao = new SaviFilterTableCurDao();
		 List<SaviFilterTableCur> list = saviFilterTableCurDao.getSaviFilterTableCurList();
	 }

	



	
	
}
