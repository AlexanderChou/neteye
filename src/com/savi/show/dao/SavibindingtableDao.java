package com.savi.show.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.savi.base.util.HibernateUtil;

public class SavibindingtableDao {
	private HibernateUtil hibernateUtil = new HibernateUtil();
	
	//获得subnetID子网当前在线的且绑定类型为 static用户
	@SuppressWarnings({"unchecked","static-access"})
	public List getSwitchhisStaticUsers(Long subnetID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select new Savibindingtablehis(t.startTime) from Savibindingtablehis t JOIN t.ifinterfacehis i JOIN i.switchhis h JOIN h.switchbasicinfo b JOIN b.subnet s"+
		            " where t.bindingType=1 and t.status=1 and b.isDelete=0 and  s.id="+subnetID;
		Query query = session.createQuery(sql);
		List list = query.list();
		transaction.commit();
		return list;
	}
	
	//获得subnetID子网当前在线的且绑定类型为 slaac用户
	@SuppressWarnings({"unchecked","static-access"})
	public List getSwitchhisSlaacUsers(Long subnetID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select new Savibindingtablehis(t.startTime) from Savibindingtablehis t JOIN t.ifinterfacehis i JOIN i.switchhis h JOIN h.switchbasicinfo b JOIN b.subnet s"+
		            " where t.bindingType=2 and b.isDelete=0 and t.status=1 and s.id="+subnetID;
		Query query = session.createQuery(sql);
		List list = query.list();
		transaction.commit();
		return list;
	}
	
	//获得subnetID子网当前在线的且绑定类型为 dhcp用户
	@SuppressWarnings({"unchecked","static-access"})
	public List getSwitchhisDhcpUsers(Long subnetID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select new Savibindingtablehis(t.startTime) from Savibindingtablehis t JOIN t.ifinterfacehis i JOIN i.switchhis h JOIN h.switchbasicinfo b JOIN b.subnet s"+
		            " where t.bindingType=3 and t.status=1 and b.isDelete=0 and s.id="+subnetID;
		Query query = session.createQuery(sql);
		List list = query.list();
		transaction.commit();
		return list;
	}
	
	//获得switchID交换机当前在线的且绑定类型为 static用户
	@SuppressWarnings({"unchecked","static-access"})
	public List getSwitchhisOnlineStaticUsers(Long switchID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select new Savibindingtablehis(t.startTime) from Savibindingtablehis t JOIN t.ifinterfacehis i JOIN i.switchhis h JOIN h.switchbasicinfo b"+
		            " where t.bindingType=1 and t.status=1 and" +
		            " h.ipVersion=(select c.ipVersion from Switchcur c where c.id="+switchID+") and"+
		            " b.id=(select b.id from Switchcur c JOIN c.switchbasicinfo b where c.id="+switchID+")";
		Query query = session.createQuery(sql);
		List list = query.list();
		transaction.commit();
		return list;
	}
	
	//获得switchID交换机当前在线的且绑定类型为 slaac用户
	@SuppressWarnings({"unchecked","static-access"})
	public List getSwitchhisOnlineSlaacUsers(Long switchID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select new Savibindingtablehis(t.startTime) from Savibindingtablehis t JOIN t.ifinterfacehis i JOIN i.switchhis h JOIN h.switchbasicinfo b"+
		            " where t.bindingType=2 and t.status=1 and"+
		            " h.ipVersion=(select c.ipVersion from Switchcur c where c.id="+switchID+") and"+
		            " b.id=(select b.id from Switchcur c JOIN c.switchbasicinfo b where c.id="+switchID+")";
		Query query = session.createQuery(sql);
		List list = query.list();
		transaction.commit();
		return list;
	}
	
	//获得switchID交换机当前在线的且绑定类型为 dhcp用户
	@SuppressWarnings({"unchecked","static-access"})
	public List getSwitchhisOnlineDhcpUsers(Long switchID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select new Savibindingtablehis(t.startTime) from Savibindingtablehis t JOIN t.ifinterfacehis i JOIN i.switchhis h JOIN h.switchbasicinfo b"+
		            " where t.bindingType=3 and t.status=1 and"+
		            " h.ipVersion=(select c.ipVersion from Switchcur c where c.id="+switchID+") and"+
		            " b.id=(select b.id from Switchcur c JOIN c.switchbasicinfo b where c.id="+switchID+")";
		Query query = session.createQuery(sql);
		List list = query.list();
		transaction.commit();
		return list;
	}
	
	// 获得ifinterfacecurID端口的绑定表的记录数
	@SuppressWarnings({"static-access"})
	public int getInterfaceUserNum(Integer ifinterfacecurID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select COUNT(t.id) from Savibindingtablecur t JOIN t.ifinterfacecur i"+
                    " where i.id="+ifinterfacecurID;
		Query query = session.createQuery(sql);
		int num = Integer.parseInt(query.list().get(0).toString());
		transaction.commit();
		return num;
	}
	
	//获得switchID交换机当前在线用户的在线时间和IP
	@SuppressWarnings({"unchecked","static-access"})
	public List getSwitchhisOnlineTimeUsers(Long switchID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Long millis = System.currentTimeMillis();
		String sql ="select ("+millis+"-t.startTime) AS time,t.ipAddress from Savibindingtablehis t JOIN t.ifinterfacehis i JOIN i.switchhis h JOIN h.switchbasicinfo b"+
		            " where t.status=1 and"+
		            " h.ipVersion=(select c.ipVersion from Switchcur c where c.id="+switchID+") and"+
		            " b.id=(select b.id from Switchcur c JOIN c.switchbasicinfo b where c.id="+switchID+")"+
		            " order by ("+millis+"-t.startTime) DESC";
		Query query = session.createQuery(sql);
		List list = query.list();
		transaction.commit();
		return list;
	}
	
	// 获得switchID的绑定表项数
	@SuppressWarnings({"static-access"})
	public long getInterfaceBindingNum(Long switchID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select COUNT(t.id) from Savibindingtablecur t JOIN t.ifinterfacecur i JOIN i.switchcur c"+
                    " where c.id="+switchID;
		Query query = session.createQuery(sql);
		Long num = Long.parseLong(query.list().get(0).toString());
		transaction.commit();
		return num;
	}
	
	// 获得switchID的绑定表项数
	@SuppressWarnings({"static-access"})
	public long getInterfaceFilteringNum(Long switchID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select COUNT(t.id) from Savibindingtablecur t JOIN t.ifinterfacecur i JOIN i.switchcur c"+
                    " where t.isInFilteringTable=1 and c.id="+switchID;
		Query query = session.createQuery(sql);
		long num = Integer.parseInt(query.list().get(0).toString());
		transaction.commit();
		return num;
	}
	
	// 获得subnetID子网从当前时间之前24小时内的用户
	@SuppressWarnings({"unchecked","static-access"})
	public List getOnlineTimeUsers(Long subnetID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Long millis = System.currentTimeMillis() - 24 * 60 * 60 * 1000;
		String sql ="select New Savibindingtablehis(t.bindingType,t.startTime,t.endTime) from Savibindingtablehis t JOIN t.ifinterfacehis i JOIN i.switchhis h JOIN h.switchbasicinfo b JOIN b.subnet s"+
		            " where (t.startTime >=" + millis +" or t.status=1 or t.endTime>"+millis+") and b.isDelete=0 and s.id="+subnetID;
		Query query = session.createQuery(sql);
		List list = query.list();
		transaction.commit();
		return list;
	}
	
	// 获得switchbasicinfoID交换机从当前时间之前24小时内的用户
	@SuppressWarnings({"unchecked","static-access"})
	public List getSwitchOnlineTimeUsers(Long switchID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Long millis = System.currentTimeMillis() - 24 * 60 * 60 * 1000;
		String sql ="select New Savibindingtablehis(t.bindingType,t.startTime,t.endTime) from Savibindingtablehis t JOIN t.ifinterfacehis i JOIN i.switchhis h JOIN h.switchbasicinfo b"+
		            " where (t.startTime >=" + millis +" or t.status=1 or t.endTime>"+millis+") and h.ipVersion=(select c.ipVersion from Switchcur c where c.id="+switchID+")"+
		            " and b.id=(select b.id from Switchcur c JOIN c.switchbasicinfo b where c.id="+switchID+")";
		Query query = session.createQuery(sql);
		List list = query.list();
		transaction.commit();
		return list;
	}
//	 public static void main(String[] args) {
//		 SavibindingtableDao saviDao=new SavibindingtableDao();
//			 try {
//				 List<BindingTableInfo> list = saviDao.getBindingTableInfo("1", "0", "60");
//				 int num = saviDao.getBindingTableInfoNum("1");
//				 System.out.println(list.size() + " " + num + " " );
//			} catch (Exception e) {
//				 e.printStackTrace();
//			}
//		 }
}