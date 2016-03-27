package com.base.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.FlushMode;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.base.model.Device;
import com.base.util.DBUtil;
import com.base.util.HibernateUtil;
import com.config.dto.Router;

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
public class DeviceService extends BaseService{
	
	private Session session=null;
	
	public Device findById(long id) throws Exception{
		Device device = null;
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			Criteria cri=session.createCriteria(Device.class).setFetchMode("deviceType", FetchMode.JOIN);
			cri.add(Restrictions.eq("id",id));
			device=(Device)cri.uniqueResult();
			if(!Hibernate.isInitialized(device)){
				Hibernate.initialize(device);//为什么要初始化一下
			}
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return device;
	}
	public List<Device> getDeviceList(long deviceTypeId) throws Exception{
		List<Device> list =null;
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			Criteria cri=session.createCriteria(Device.class).setFetchMode("deviceType", FetchMode.JOIN);
			if(deviceTypeId!=0){
				cri.createCriteria("deviceType").add(Restrictions.idEq(deviceTypeId));	
			}
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
	public List<Device> getRouterList(long deviceTypeId) throws Exception{
		List<Device> list =null;
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			Criteria cri=session.createCriteria(Device.class).setFetchMode("deviceType", FetchMode.JOIN);
			if(deviceTypeId!=0){
				cri.createCriteria("deviceType").add(Restrictions.le("id", deviceTypeId));	
			}
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

	public List<Device> getList(Long id) throws Exception{
		List<Device> list =null;
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			Criteria cri=session.createCriteria(Device.class).setFetchMode("deviceType", FetchMode.JOIN);
				cri.createCriteria("deviceType").add(Restrictions.idEq(id));
				cri.add(Restrictions.eq("faultFlag",true));  
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
	public Device getDeviceByCondition(String condition) throws Exception {
		Device device = null;
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			List temp = session.createQuery("from Device " + condition).list(); 
			if(temp.size()>0) {
				device = (Device)temp.get(0);
			}
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return device;
	}
	public Device findDeviceByIP(String IP) throws Exception{
		Device device = new Device();
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			Criteria cri=session.createCriteria(Device.class);
			cri.add(Restrictions.or(Restrictions.eq("loopbackIP",IP),Restrictions.eq("loopbackIPv6",IP)));
			List result = cri.list();
			device=(Device)result.get(0);
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return device;
	}
	public List<Router> getDeviceListBySQL(long deviceTypeId) throws Exception{
		List<Router> list = new ArrayList<Router>();
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			//String sql = "select new com.config.dto.Router(device.id,device.name,device.chineseName,device.loopbackIP,device.loopbackIPv6) from Device device where device.devicetype_id=1";
			String sql = "select device.id,device.description,device.name,device.chinesename,device.loopbackip,device.loopbackipv6,device.service,device.trafficip,device.faultflag " +
					"from device device where device.devicetype_id="+deviceTypeId;
			List<Object[]> temp = session.createSQLQuery(sql).list();
			for(Object[] obj : temp){
				Router router = new Router();
				router.setId(Long.valueOf(obj[0].toString()));
				if(obj[1]!=null){
					router.setDescription(obj[1].toString());
				}
				if(obj[2]!=null){
					router.setName(obj[2].toString());
				}
				if(obj[3]!=null){
					router.setChineseName(obj[3].toString());
				}
				if(obj[4]!=null){
					router.setIpv4(obj[4].toString());
				}
				if(obj[5]!=null){
					router.setIpv6(obj[5].toString());
				}
				if(obj[6]!=null){
					router.setService((String[]) obj[6]);
				}
				if(obj[7]!=null){
					router.setTrafficIp(obj[7].toString());
				}
				if(obj[8]!=null){
					router.setFaultFlag((Boolean) obj[8]);
				}
				list.add(router);
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

	public Device getDevice(long id) throws Exception{
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		Device device = null;
		try{
			tx=session.beginTransaction();
			device =(Device)session.createCriteria(Device.class).add(Restrictions.idEq(id))
			.setFetchMode("deviceType", FetchMode.JOIN).setFetchMode("ifinterfaces", FetchMode.JOIN).uniqueResult();
			if(!Hibernate.isInitialized(device)){
				Hibernate.initialize(device);
			}
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return device;
	}
	public List<Device> getDeviceByFault(Integer faultFlag) throws Exception{
		List<Device> list =null;
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			list=session.createCriteria(Device.class).add(Restrictions.eq("faultFlag",faultFlag)).list();  
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
	public Long save(Device device) throws Exception{
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			session.save(device);
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
		}
		if(device.getId()!=null){
			return device.getId();
		}else{
			return null;
		}
	}
	public Device deleteDevice(long id) throws Exception{
		Device device =null;
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			device =(Device) session.load(Device.class, id);			
			session.delete(device);
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return device;
	}
	public static boolean isExistByIP(String ip) throws Exception{
		Integer num;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			num = (Integer)session.createCriteria(Device.class).add(Restrictions.or(Restrictions.eq("loopbackIP",ip),Restrictions.eq("loopbackIPv6",ip))).
			setProjection(Projections.rowCount()).list().get(0);
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
		if(num.intValue()==0)
			return false;
		else
			return true;
	}
	public Long getRepeatDeviceNum(String sql) throws Exception {
		Long num;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			num = (Long)session.createSQLQuery(sql).addScalar("num",Hibernate.LONG).uniqueResult();
			tx.commit();
		}catch (Exception e){
			if (tx != null)
			{
				tx.rollback();
			}
			throw e;
		}
		return num;
	}
	
	public List getAllDevice() throws Exception{
		List list=null;
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			list=session.createCriteria(Device.class).list();
			tx.commit();
		}catch (Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	/**
	 * 判断某种设备类型下是否有设备
	 * @param deviceTypeId
	 * @return
	 */
	public static boolean someDeviceTypeIsHaveDevices(long deviceTypeId) {
		boolean have = true;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Device> devices = session.createCriteria(Device.class).add(Restrictions.eq("deviceType.id", deviceTypeId)).list();
		transaction.commit();
		have = !devices.isEmpty();
		return have;
	}
	
	public int getAllDevicesCount(long deviceTypeId) {
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = s.beginTransaction();
		Integer count = (Integer)s.createCriteria(Device.class).add(Restrictions.eq("deviceType.id", deviceTypeId)).setProjection(Projections.rowCount()).uniqueResult();
		transaction.commit();
		return count.intValue();
	}
	
	public List<Device> getPageList(long deviceTypeId,String firstResult, String maxResult) throws Exception{
		List<Device> list =null;
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			Criteria cri=session.createCriteria(Device.class).setFetchMode("deviceType", FetchMode.JOIN).setFirstResult(Integer.parseInt(firstResult)).setMaxResults(Integer.parseInt(maxResult));
			if(deviceTypeId!=0){
				cri.createCriteria("deviceType").add(Restrictions.idEq(deviceTypeId));	
			}
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
	
	/**
	 * 批量删除设备
	 * @param idStr
	 */
	public void deleteDeviceByBatch(String idStr){
		
		idStr = idStr.replace("[", "").replace("]", "");
		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();

		String deleteLinksSql = "delete from link where updevice in (" + idStr + ") or downdevice in (" + idStr + ")"; 
		session.createSQLQuery(deleteLinksSql).executeUpdate();
		
		String deleteIfinterfacesSql = "delete from ifinterface where device_id in (" + idStr + ")";
		session.createSQLQuery(deleteIfinterfacesSql).executeUpdate();
		
		String deleteDeviceSql = "delete from device where id in (" + idStr + ");" ;
		session.createSQLQuery(deleteDeviceSql).executeUpdate();
		
		transaction.commit();
		
	}
	

	/**
	 *  批处理更新设备的名字
	 *  @param map
	 */
	public void updateDeviceByBatch(Map<Long, String> map) {
		 session = HibernateUtil.getSessionFactory().getCurrentSession();
		 Transaction transaction = session.beginTransaction();
		 for (long deviceId : map.keySet()) {
			String sql = "update device set name = '" + map.get(deviceId) + "' where id = " + deviceId;
			session.createSQLQuery(sql).addEntity(Device.class).executeUpdate();
		 }
		 
		 transaction.commit();
	}	
	public String getLoopbackIp(long Id) {
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		tx=session.beginTransaction();
		String sql = "select device.loopbackIP as name  from device device where device.id="+Id;
		String IP = (String)session.createSQLQuery(sql).addScalar("name").uniqueResult();
		tx.commit();
		return IP;
	}
	public String getLoopbackIpv6(long Id) {
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		tx=session.beginTransaction();
		String sql = "select device.loopbackIPv6 as name  from device device where device.id="+Id;
		String IP = (String)session.createSQLQuery(sql).addScalar("name").uniqueResult();
		tx.commit();
		return IP;
	}
}
