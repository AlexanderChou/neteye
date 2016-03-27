package com.base.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.base.model.IfInterface;
import com.base.util.HibernateUtil;
import com.config.dto.Port;

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
public class PortService  extends BaseService{
	
	private Session session=null;
	
	public IfInterface findById(long id) throws Exception{
		IfInterface ifinterface=null;
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			ifinterface=(IfInterface) session.createCriteria(IfInterface.class).add(Restrictions.idEq(id))
			.setFetchMode("device", FetchMode.JOIN).uniqueResult();
			if(!Hibernate.isInitialized(ifinterface)){
				Hibernate.initialize(ifinterface);
			}
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return ifinterface;
	}
	public IfInterface findByIp(String ip) throws Exception{
		IfInterface ifinterface=null;
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			ifinterface=(IfInterface) session.createCriteria(IfInterface.class).add(Restrictions.eq("ipv4",ip)).uniqueResult();
			if(!Hibernate.isInitialized(ifinterface)){
				Hibernate.initialize(ifinterface);
			}
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return ifinterface;
	}
	public List<IfInterface> getPortListByIp(String ip) throws Exception{
		List<IfInterface> list =null;
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			Criteria cri=session.createCriteria(IfInterface.class).add(Restrictions.or(Restrictions.eq("ipv4",ip), Restrictions.eq("ipv6",ip)));
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
	public List<IfInterface> getPortList(long deviceId) throws Exception{		
		List<IfInterface> list =null;
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			Criteria cri=session.createCriteria(IfInterface.class).setFetchMode("device", FetchMode.JOIN);
			cri.createCriteria("device").add(Restrictions.idEq(deviceId));
//			cri.addOrder(Order.desc("trafficFlag"));
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
	public void save(IfInterface ifinterface) throws Exception{
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			session.save(ifinterface);
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
	}
	public int getAllPortCount(long deviceId) {
		List<Port> list = new ArrayList<Port>();
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		tx=session.beginTransaction();
		//String sql = "select new com.config.dto.Router(device.id,device.name,device.chineseName,device.loopbackIP,device.loopbackIPv6) from Device device where device.devicetype_id=1";
		String sql = "select count(ifInterface.id) as num  from ifinterface ifInterface where ifInterface.device_id="+deviceId;
		Long count = (Long)session.createSQLQuery(sql).addScalar("num").uniqueResult();
		tx.commit();
		return count.intValue();
	}
	public List<Port> getPortListBySQL(long deviceId) throws Exception{
		List<Port> list = new ArrayList<Port>();
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			//String sql = "select new com.config.dto.Router(device.id,device.name,device.chineseName,device.loopbackIP,device.loopbackIPv6) from Device device where device.devicetype_id=1";
			String sql = "select ifInterface.id,ifInterface.name,ifInterface.chineseName,ifInterface.ipv4,ifInterface.ipv6," +
					"ifInterface.ifindex,ifInterface.netmask,ifInterface.maxSpeed,ifInterface.description,ifInterface.interfaceType,ifInterface.lowerThreshold,ifInterface.upperThreshold " +
					"from ifinterface ifInterface where ifInterface.device_id="+deviceId;
			List<Object[]> temp = session.createSQLQuery(sql).list();
			for(Object[] obj : temp){
				Port ifinterface = new Port();
				ifinterface.setDeviceId(deviceId);
				ifinterface.setId(Long.valueOf(obj[0].toString()));
				if(obj[1]!=null){
					ifinterface.setName(obj[1].toString().trim());
				}
				if(obj[2]!=null){
					ifinterface.setChineseName(obj[2].toString().trim());
				}
				if(obj[3]!=null){
					ifinterface.setIpv4(obj[3].toString().trim());
				}
				if(obj[4]!=null){
					ifinterface.setIpv6(obj[4].toString().trim());
				}
				if(obj[5]!=null){
					ifinterface.setIfindex(obj[5].toString().trim());
				}

				if(obj[6]!=null){
					ifinterface.setNetmask(obj[6].toString().trim());
				}

				if(obj[7]!=null){
					ifinterface.setMaxSpeed((Double) obj[7]);
				}

				if(obj[8]!=null){
					ifinterface.setDescription(obj[8].toString().trim());
				}

				if(obj[9]!=null){
					ifinterface.setInterfaceType(obj[9].toString().trim());
				}

				if(obj[10]!=null){
					ifinterface.setLowerThreshold((Integer) obj[10]);
				}

				if(obj[11]!=null){
					ifinterface.setUpperThreshold((Integer) obj[11]);
				}
				if(obj[12]!=null){
					ifinterface.setTrafficFlag((Integer) obj[12]);
				}else{
					ifinterface.setTrafficFlag(0);
				}
				list.add(ifinterface);
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
	/**
	 * 根据某一端口的IP获得该端口对象
	 * @param IP
	 * @return
	 */
	public  IfInterface getInterfaceByIfIndex(String ifIndex, Long deviceId){
		SessionFactory factory = HibernateUtil.getSessionFactory();
		session = factory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
		List list = session.createQuery("from IfInterface i where i.ifindex = ? and i.device = ?").setString(0, ifIndex).setLong(1, deviceId).list();
		IfInterface ifInterface = null;
		if(list!=null && list.size()>0){
			ifInterface = (IfInterface)list.get(0);
		}
		 transaction.commit();
		return ifInterface;
	}
	public List<IfInterface> getAllMonitored(Long deviceId,int traffic){
		SessionFactory factory = HibernateUtil.getSessionFactory();
		session = factory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
		List list = session.createQuery("from IfInterface i where i.trafficFlag = ? and i.device = ?").setInteger(0, traffic).setLong(1, deviceId).list();
		 transaction.commit();
		return list;
	}
	public List<Port> getAllPortListBySQL(long deviceTypeId) throws Exception{
		List<Port> list = new ArrayList<Port>();
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			//String sql = "select new com.config.dto.Router(device.id,device.name,device.chineseName,device.loopbackIP,device.loopbackIPv6) from Device device where device.devicetype_id=1";
			String sql = "select ifInterface.id,ifInterface.chinesename,ifInterface.ipv4,ifInterface.ipv6," +
					"ifInterface.ifindex,ifInterface.maxspeed,router.chinesename " +
					"from ifinterface as ifInterface,device as router where ifInterface.device_id=router.id and router.devicetype_id="+deviceTypeId;
			List<Object[]> temp = session.createSQLQuery(sql).list();
//			for(Object[] obj : temp){
//				Port ifinterface = new Port();
//				ifinterface.setId(Long.valueOf(obj[0].toString()));
//				if(obj[1]!=null){
//					ifinterface.setName(obj[1].toString());
//				}
//				if(obj[2]!=null){
//					ifinterface.setChineseName(obj[2].toString());
//				}
//				if(obj[3]!=null){
//					ifinterface.setIpv4(obj[3].toString());
//				}
//				if(obj[4]!=null){
//					ifinterface.setIpv6(obj[4].toString());
//				}
//				if(obj[5]!=null){
//					ifinterface.setIfindex(obj[5].toString());
//				}
//				if(obj[6]!=null){
//					ifinterface.setMaxSpeed((Double) obj[6]);
//				}
//				if(obj[8]!=null){
//					System.out.println(obj[8].toString());
//					ifinterface.setDeviceName(obj[8].toString());
//				}else{
//					System.out.println(obj[7].toString());
//					if(obj[7]!=null){
//					ifinterface.setDeviceName(obj[7].toString());
//					}
//				}				
//				list.add(ifinterface);
//			}
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
	
	public List<Port> getPageList(long deviceId,String firstResult, String maxResult) throws Exception{
		List<Port> list = new ArrayList<Port>();
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			//String sql = "select new com.config.dto.Router(device.id,device.name,device.chineseName,device.loopbackIP,device.loopbackIPv6) from Device device where device.devicetype_id=1";
			String sql = "select ifInterface.id,ifInterface.name,ifInterface.chineseName,ifInterface.ipv4,ifInterface.ipv6,ifInterface.ifindex,ifInterface.netmask," +
					"ifInterface.maxSpeed,ifInterface.description,ifInterface.interfaceType,ifInterface.lowerThreshold,ifInterface.upperThreshold,ifInterface.trafficFlag " +
					"from ifinterface ifInterface where ifInterface.device_id="+deviceId+"  order by ifInterface.trafficFlag DESC";
			List<Object[]> temp = session.createSQLQuery(sql).setFirstResult(Integer.parseInt(firstResult)).setMaxResults(Integer.parseInt(maxResult)).list();
			for(Object[] obj : temp){
				Port ifinterface = new Port();
				ifinterface.setDeviceId(deviceId);
				ifinterface.setId(Long.valueOf(obj[0].toString()));
				if(obj[1]!=null){
					ifinterface.setName(obj[1].toString());
				}
				if(obj[2]!=null){
					ifinterface.setChineseName(obj[2].toString());
				}
				if(obj[3]!=null){
					ifinterface.setIpv4(obj[3].toString());
				}
				if(obj[4]!=null){
					ifinterface.setIpv6(obj[4].toString());
				}
				if(obj[5]!=null){
					ifinterface.setIfindex(obj[5].toString());
				}

				if(obj[6]!=null){
					ifinterface.setNetmask(obj[6].toString());
				}

				if(obj[7]!=null){
					ifinterface.setMaxSpeed((Double) obj[7]);
				}

				if(obj[8]!=null){
					ifinterface.setDescription(obj[8].toString());
				}

				if(obj[9]!=null){
					ifinterface.setInterfaceType(obj[9].toString());
				}

				if(obj[10]!=null){
					ifinterface.setLowerThreshold((Integer) obj[10]);
				}

				if(obj[11]!=null){
					ifinterface.setUpperThreshold((Integer) obj[11]);
				}
				if(obj[12]!=null){
					ifinterface.setTrafficFlag((Integer) obj[12]);
				}else{
					ifinterface.setTrafficFlag(0);
				}
				list.add(ifinterface);
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
	public String getIfindex(long Id) {
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		tx=session.beginTransaction();
		String sql = "select ifInterface.ifindex as name  from ifinterface ifInterface where ifInterface.id="+Id;
		String ifindex = (String)session.createSQLQuery(sql).addScalar("name").uniqueResult();
		tx.commit();
		return ifindex;
	}
}
