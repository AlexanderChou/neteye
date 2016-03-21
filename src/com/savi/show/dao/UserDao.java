package com.savi.show.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.savi.base.util.HibernateUtil;

public class UserDao {
	private HibernateUtil hibernateUtil = new HibernateUtil();

	// 获得switchID交换机当前在线用户的在线时间和IP
	@SuppressWarnings( { "unchecked", "static-access" })
	public List getOnlineUserName(String ip) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select u.name from User u where u.status=1 and u.ipAddress='"
				+ ip + "'";
		Query query = session.createQuery(sql);
		List list = query.list();
		transaction.commit();
		return list;
	}
	
//	 public static void main(String[] args) {
//		UserDao userDao=new UserDao();
//		 try {
//			 List list = userDao.getUserChangeInfo("1", "0", "20");
//			 int num = userDao.getUserChangeInfoNum("3");
//			 System.out.println(list.size() + " " + num);
//		} catch (Exception e) {
//			 e.printStackTrace();
//		}
//	 }
}