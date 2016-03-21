package com.base.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.base.model.Device;
import com.base.model.IfInterface;
import com.base.model.Link;
import com.base.util.HibernateUtil;
import com.view.dto.InterfaceInfo;

/*
** Copyright (c) 2008, 2009, 2010
**      The Regents of the Tsinghua University, PRC.  All rights reserved.
**
** Redistribution and use in source and binary forms, with or without  modification, are permitted provided that the following conditions are met:
** 1. Redistributions of source code must retain the above copyright  notice, this list of conditions and the following disclaimer.
** 2. Redistributions in binary form must reproduce the above copyright  notice, this list of conditions and the following disclaimer in the  documentation and/or other materials provided with the distribution.
** 3. All advertising materials mentioning features or use of this software  must display the following acknowledgement:
**  This product includes software (iNetBoss) developed by Tsinghua University, PRC and its contributors.
** THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
** ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
** IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
** ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
** FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
** DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
** OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
** HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
** LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
** OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
** SUCH DAMAGE.
*
*/
public class IfInterfaceService extends BaseService{
	
	private Session session=null;
	
	public IfInterface findById(long id) throws Exception{
		IfInterface ifInterface=null;
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			ifInterface=(IfInterface)session.load(IfInterface.class,id);
			if(!Hibernate.isInitialized(ifInterface)){
				Hibernate.initialize(ifInterface);
			}
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace(); 
			throw e;
		}
		return ifInterface;
	}
	public static void main(String[] args) throws Exception{
		IfInterfaceService service = new IfInterfaceService();
	}
	/**
	 * 有设备id得到端口列表
	 * @param deviceId
	 * @param start
	 * @param limit
	 * @return 端口列表
	 * @throws Exception
	 */
	public ArrayList<InterfaceInfo> getIfInterfaceListBydeviceId(long deviceId, int start, int limit) throws Exception{
		ArrayList<InterfaceInfo> list =new ArrayList<InterfaceInfo>() ;
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			String sql = "select interface.id,interface.ifindex,interface.ipv4,interface.ipv6,interface.speed,interface.managestatus,interface.managestatusipv6,interface.operatestatus,interface.operatestatusipv6,interface.description " +
			"from ifinterface interface where interface.device_id="+deviceId;
			List<Object[]> temp = session.createSQLQuery(sql).setFirstResult(start).setMaxResults(limit).list();
			for(Object[] obj : temp){
				InterfaceInfo interfaceInfo = new InterfaceInfo();
				interfaceInfo.setId(Long.valueOf(obj[0].toString()));
				if(obj[1]!=null){
					interfaceInfo.setIndex(Integer.parseInt(obj[1].toString()));
				}
				if(obj[2]!=null){
					interfaceInfo.setIpv4(obj[2].toString());
				}
				if(obj[3]!=null){
					interfaceInfo.setIpv6(obj[3].toString());
				}
				if(obj[4]!=null){
					Double temp4 =Double.valueOf(obj[4].toString())/(1000000);
					interfaceInfo.setSpeed(temp4.toString());
				}
				if(obj[5]!=null){
					interfaceInfo.setManagementStatusV4(obj[5].toString());
				}
				if(obj[6]!=null){
					interfaceInfo.setManagementStatusV6(obj[6].toString());
				}
				if(obj[7]!=null){
					interfaceInfo.setOperationStatusV4( obj[7].toString());
				}
				if(obj[8]!=null){//以前这个地方写错了
					interfaceInfo.setOperationStatusV6( obj[8].toString());
				}
				if(obj[9]!=null){
					interfaceInfo.setDesc(obj[9].toString());
				}
				
				list.add(interfaceInfo);
			}
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		System.out.println("list="+list.size());
		return list;
	}
	
	/**
	 * 由设备的id得到它的所有端口 
	 * @param deviceId
	 * @return 端口的个数
	 * @throws Exception
	 */
	public Long getIfInterfaceCountBydeviceId(long deviceId) throws Exception{
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		Long count = null;
		try{
			tx=session.beginTransaction();
			String sql = "select count(*) as num from ifinterface interface where interface.device_id="+deviceId;
			count = (Long)session.createSQLQuery(sql).addScalar("num",Hibernate.LONG).uniqueResult();
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return count;
	}
	
	public List<Link> getLinkListByDowndeviceId(long deviceId) throws Exception{
		List<Link> list =null;
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			Criteria cri=session.createCriteria(Link.class).setFetchMode("IfInterface", FetchMode.JOIN).
			add(Restrictions.eq("downDevice",deviceId));
			//Criteria cri2=session.createCriteria(Link.class).setFetchMode("device", FetchMode.JOIN);
			list=cri.list();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return list;
	}	
	public List<Link> getLinkListByInterfId(long interfaceId) throws Exception{
		List<Link> list =new ArrayList();
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			List list1=null;
			tx=session.beginTransaction();
			Criteria cri=session.createCriteria(Link.class).setFetchMode("IfInterface", FetchMode.JOIN).setFetchMode("Device", FetchMode.JOIN);
			cri.createCriteria("upInterface").add(Restrictions.idEq(interfaceId));
			list1=cri.list();
			if(list1!=null){
				for(int i=0;i<list1.size();i++){
					Link link=(Link)list1.get(i);
					list.add(link);
				}
			}
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		try{
			List list2=null;
			tx=session.beginTransaction();
			Criteria cri=session.createCriteria(Link.class).setFetchMode("downInterface", FetchMode.JOIN);
			cri.createCriteria("downInterface").add(Restrictions.idEq(interfaceId));
			list2=cri.list();
			if(list2!=null){
				for(int i=0;i<list2.size();i++){
					Link link=new Link();
						link=(Link)list2.get(i);
						list.add(link);
				}
			}
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
	public Long save(Link link) throws Exception{
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		Long id = null;
		try{
			tx=session.beginTransaction();
			session.save(link);
			tx.commit();
			id = link.getId();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return id;
	}
	public Long saveInf(IfInterface inf) throws Exception{
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		Long id = null;
		try{
			tx=session.beginTransaction();
			session.saveOrUpdate(inf);
			tx.commit();
			id = inf.getId();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return id;
	}
	/**
	 * 判断数据库中的链路是否重复
	 * @param link
	 * @return
	 */

	public static boolean isRepeatLink(long srcId,long desId,long srcInifId,long desInifId){
		Integer num = null;
		Integer num2 = null;
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		Transaction tx2=null;
		try{
			tx=session.beginTransaction();
			Criteria cri=session.createCriteria(Link.class);
			cri.add(Restrictions.eq("upDevice",srcId));
			cri.add(Restrictions.eq("downDevice",desId));
			cri.createCriteria("upInterface").add(Restrictions.idEq(srcInifId));
			cri.createCriteria("downInterface").add(Restrictions.idEq(desInifId));
			num=(Integer) cri.setProjection(Projections.rowCount()).list().get(0);
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
		}
		try{
			tx2=session.beginTransaction();
			Criteria cri=session.createCriteria(Link.class);
			cri.add(Restrictions.eq("upDevice",desId));
			cri.add(Restrictions.eq("downDevice",srcId));
			cri.createCriteria("upInterface").add(Restrictions.idEq(desInifId));
			cri.createCriteria("downInterface").add(Restrictions.idEq(srcInifId));
			num2=(Integer) cri.setProjection(Projections.rowCount()).list().get(0);
			tx2.commit();
		}catch(Exception e){
			if(tx2!=null){
				tx2.rollback();
			}
			e.printStackTrace();
		}
		if(num.intValue()==0&&num2.intValue()==0){
			return false;
		}else{
			return true;
		}
	}
	public static boolean isUsed(long srcId,long srcInifId){
		Integer num = null;
		Integer num2 = null;
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		Transaction tx2=null;
		try{
			tx=session.beginTransaction();
			Criteria cri=session.createCriteria(Link.class);
			cri.add(Restrictions.eq("downDevice",srcId));
			cri.createCriteria("downInterface").add(Restrictions.idEq(srcInifId));
			num=(Integer) cri.setProjection(Projections.rowCount()).list().get(0);
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
		}
		try{
			tx2=session.beginTransaction();
			Criteria cri=session.createCriteria(Link.class);
			cri.add(Restrictions.eq("upDevice",srcId));
			cri.createCriteria("upInterface").add(Restrictions.idEq(srcInifId));
			num2=(Integer) cri.setProjection(Projections.rowCount()).list().get(0);
			tx2.commit();
		}catch(Exception e){
			if(tx2!=null){
				tx2.rollback();
			}
			e.printStackTrace();
		}
		if(num.intValue()==0&&num2.intValue()==0){
			return false;
		}else{
		return true;
		}
	}
	public Long isRepeatLink(Link link) throws Exception{
		Long num = null;
		String sql = "select lk.id as num  from link lk where (lk.upinterface="+link.getUpInterface().getId()+" and lk.downinterface="+link.getDownInterface().getId()+") or "
				   + "(lk.upinterface=" + link.getDownInterface().getId()+" and lk.downinterface=" + link.getUpInterface().getId()+")";
		Transaction tx=null;
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			tx=session.beginTransaction();
			num = (Long)session.createSQLQuery(sql)
			.addScalar("num",Hibernate.LONG)
			.uniqueResult();
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return num;
	}
	public static void deleteLink(Link link) throws Exception {
		long upInterfaceId=link.getUpInterface().getId();
		long downInterfaceId=link.getDownInterface().getId();
		PortService service=new PortService();
		IfInterface upInterface=service.findById(upInterfaceId);
		if(upInterface!=null){
			upInterface.setTrafficFlag(0);
			service.update(upInterface);
		}
		IfInterface downInterface=service.findById(downInterfaceId);
		if(downInterface!=null){
			downInterface.setTrafficFlag(0);
			service.update(downInterface);
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			session.delete(link);
			tx.commit();
		}catch (Exception e)
		{
			if (tx != null)
			{
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
	}
	public IfInterface getIfInterfaceByCondition(String condition) throws Exception {
		IfInterface ifInterface = null;
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			List temp = session.createQuery("from IfInterface " + condition).list(); 
			if(temp.size()>0) {
				ifInterface = (IfInterface)temp.get(0);
			}
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return ifInterface;
	}
	/**
	 * 由索引值得到该端口
	 * @param ifIndex
	 * @return
	 */
	public IfInterface getIfInterfaceByIfIndex(String ifIndex) {
		Transaction transaction = session.beginTransaction();
		IfInterface ifInterface = (IfInterface)session.createCriteria(IfInterface.class).add(Restrictions.eq("ifindex", ifIndex)).uniqueResult();
		transaction.commit();
		return ifInterface;
	}	
	public String getIfInterName(long deviceId,String ifIndex)throws Exception {
		String name="";	
		Transaction tx=null;
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		tx=session.beginTransaction();
		name =(String) session.createSQLQuery("select i.chinesename from ifinterface i where i.ifindex = ? and i.device_id = ?").setString(0, ifIndex).setLong(1, deviceId).uniqueResult();			
		tx.commit();
		return name;
	}
	public Long getId2(long deviceId,String ifindex) throws Exception{
		Long id;
		Transaction tx=null;
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		tx=session.beginTransaction();
		id=(Long) session.createSQLQuery("select i.id from ifinterface i where i.ifindex = ? and i.device_id = ?").setString(0, ifindex).setLong(1, deviceId).uniqueResult();
		tx.commit();
		return id;
	}
	public Long getId(long deviceId,String ifindex) throws Exception{
		SessionFactory factory = HibernateUtil.getSessionFactory();
		session = factory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
		List list = session.createQuery("from IfInterface i where i.ifindex = ? and i.device = ?").setString(0, ifindex).setLong(1, deviceId).list();
		IfInterface ifInterface = null;
		if(list!=null && list.size()>0){
			ifInterface = (IfInterface)list.get(0);
		}
		 transaction.commit();
		if(ifInterface!=null){
			return ifInterface.getId();
		 }else{
			 return Long.parseLong("0");
		 }
	}	
}
