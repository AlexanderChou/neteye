package com.savi.statistic.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.savi.base.util.HibernateUtil;
import com.savi.statistic.dto.UserInfo;

public class UserInfoDao {
	private HibernateUtil hibernateUtil = new HibernateUtil();
	@SuppressWarnings({"unchecked","static-access"})
	public List<UserInfo> getUserInfo(String userName,String userIP,String userMAC,Long startTime,Long endTime,boolean isOnline,boolean isNull,String firstResult, String maxResult){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		//Long millis = System.currentTimeMillis() - 24 * 60 * 60 * 1000;
		String sql="select New com.savi.statistic.dto.UserInfo(t.userName,t.macAddress,t.ipAddress,i.ifIndex,b.name,b.ipv4address,"+
			"b.ipv6address,t.startTime,t.endTime,t.bindingType) from Savibindingtablehis t JOIN t.ifinterfacehis i JOIN i.switchhis h" +
			" JOIN h.switchbasicinfo b where b.isDelete=0";
		if(userName!=null&&!userName.equals("")){
			sql+=" and t.userName like '"+userName+"'";
		}
		if(userIP!=null&&!userIP.equals("")){
			sql+=" and t.ipAddress like '"+userIP+"'";
		}
		if(userMAC!=null&&!userMAC.equals("")){
			sql+=" and t.macAddress like '"+userMAC+"'";
		}
		if(startTime!=null){
			sql+=" and t.startTime>="+startTime;
		}
		if(endTime!=null){
			sql+=" and t.endTime<="+endTime;
		}
		if(isOnline){
			sql+=" and t.status=1";
		}
		if(isNull){
			sql+=" and LENGTH(t.userName)>0";
		}
		Query query = session.createQuery(sql);
		query.setFirstResult(Integer.parseInt(firstResult));
		query.setMaxResults(Integer.parseInt(maxResult));
		List list = query.list();
		transaction.commit();
		return list;
	} 
	@SuppressWarnings({"static-access"})
	public int getUserInfoNum(String userName,String userIP,String userMAC,Long startTime,Long endTime,boolean isOnline,boolean isNull){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		//Long millis = System.currentTimeMillis() - 24 * 60 * 60 * 1000;
		String sql="select count(t) as num from Savibindingtablehis t JOIN t.ifinterfacehis i JOIN i.switchhis h" +
			" JOIN h.switchbasicinfo b where b.isDelete=0";
		if(userName!=null&&!userName.equals("")){
			sql+=" and t.userName like '"+userName+"'";
		}
		if(userIP!=null&&!userIP.equals("")){
			sql+=" and t.ipAddress like '"+userIP+"'";
		}
		if(userMAC!=null&&!userMAC.equals("")){
			sql+=" and t.macAddress like '"+userMAC+"'";
		}
		if(startTime!=null){
			sql+=" and t.startTime>="+startTime;
		}
		if(endTime!=null){
			sql+=" and t.endTime<="+endTime;
		}
		if(isOnline){
			sql+=" and t.status=1";
		}
		if(isNull){
			sql+=" and LENGTH(t.userName)>0";
		}
		Query query = session.createQuery(sql);
		int num=Integer.parseInt(query.list().get(0).toString());
		transaction.commit();
		return num;
	}
	
	@SuppressWarnings({"unchecked","static-access"})
	public List<UserInfo> getUserInfoOnline(String firstResult, String maxResult){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		//Long millis = System.currentTimeMillis() - 24 * 60 * 60 * 1000;
		String sql="select New com.savi.statistic.dto.UserInfo(t.userName,t.macAddress,t.ipAddress,i.ifIndex,b.name,b.ipv4address,"+
			"b.ipv6address,t.startTime,t.endTime,t.bindingType) from Savibindingtablehis t JOIN t.ifinterfacehis i JOIN i.switchhis h" +
			" JOIN h.switchbasicinfo b where t.status=1 and b.isDelete=0";
		Query query = session.createQuery(sql);
		query.setFirstResult(Integer.parseInt(firstResult));
		query.setMaxResults(Integer.parseInt(maxResult));
		List list = query.list();
		transaction.commit();
		return list;
	}
	@SuppressWarnings({"static-access"})
	public int getUserInfoOnlineNum(){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		//Long millis = System.currentTimeMillis() - 24 * 60 * 60 * 1000;
		String sql="select count(t) as num from Savibindingtablehis t JOIN t.ifinterfacehis i JOIN i.switchhis h" +
			" JOIN h.switchbasicinfo b where t.status=1 and b.isDelete=0";
		Query query = session.createQuery(sql);
		int num=Integer.parseInt(query.list().get(0).toString());
		transaction.commit();
		return num;
	}
}
