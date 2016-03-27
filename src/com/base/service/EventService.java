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
/*
 * @autor:JiangNing
 * @data:2009-7-20
 */
package com.base.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.base.model.EventPojo;
import com.base.util.HibernateUtil;
import com.view.dto.EventInfo;
import com.view.dto.ServiceEventInfo;
import com.view.dto.ServiceInfo;
/*
 * @autor:JiangNing update:guoxi
 * @data:2009-7-17
 * 注：sql更改的原因：数据库event表最初设计时，仅从端口级别进行事件监控（实际上有些设备类型是没有端口的）
 * 字段ifinterface_id和serviceManage_id分别与表ifinterface和servicemanage进行主外键关联
 * 为便于以后扩展，将ifinterface_id和serviceManage_id合并为obj_id,同时增加objtype字段(该字段可能值为:device、interface、service等)
 */
public class EventService extends BaseService{
	
	private Session session=null;
	
	public EventPojo findById(long id) throws Exception{
		EventPojo event=null;
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			event=(EventPojo) session.load(EventPojo.class, id);
			if(!Hibernate.isInitialized(event)){
				Hibernate.initialize(event);
			}
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return event;
	}
	public ServiceInfo getServiceInfoByServiceId(long serviceId) throws Exception{
		ServiceInfo serviceInfo=new ServiceInfo();
		serviceInfo.setStatus("green");
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			//String sql = "select e.typevalue from event e,eventstatus estatus where e.id=estatus.event_id and e.serviceManage_id="+serviceId;
			String sql = "select e.typevalue from event e,eventstatus estatus where e.id=estatus.event_id and e.objtype='service' and e.obj_id="+serviceId;
			List<String> temp = session.createSQLQuery(sql).list();
			for(String obj : temp){
				String typeValue=null;
				if(obj!=null){
					typeValue=obj;
				}
                if(typeValue.equals("down")){
                	serviceInfo.setStatus("red");
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
		return serviceInfo;
	}
	public  ArrayList<ServiceInfo> getServiceInfoListByServiceId(long deviceId) throws Exception{
		ArrayList<ServiceInfo> list=new ArrayList<ServiceInfo>();
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			//String sql = "select s.id,s.servicetype,s.port,s.description,e.typevalue from event e,servicemanage s where s.id=e.serviceManage_id and s.device_id="+deviceId;
			String sql = "select s.id,s.servicetype,s.port,s.description,e.typevalue from event e,servicemanage s where s.id=e.obj_id and e.objtype='service' and s.device_id="+deviceId;
			List<Object[]> temp = session.createSQLQuery(sql).list();
			for(Object[] obj : temp){
				ServiceInfo serviceInfo = new ServiceInfo();
				serviceInfo.setId(Long.valueOf(obj[0].toString()));
				if(obj[1]!=null){
					serviceInfo.setServiceType(obj[1].toString());
				}
				if(obj[2]!=null){
					serviceInfo.setPort(obj[2].toString());
				}
				if(obj[3]!=null){
					serviceInfo.setDescription(obj[3].toString());
				}
				if(obj[4]!=null){
					serviceInfo.setStatus(obj[4].toString());
				}
				list.add(serviceInfo);
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
	public List getInfsByDeviceId(String deviceId) throws Exception{
		List<Long> list=new ArrayList<Long>();
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			String sql = "select id from ifinterface inf where !inf.ipv4='' and inf.device_id="+Long.parseLong(deviceId);
			list = session.createSQLQuery(sql).list();
			
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
	public static void main(String[] args) {
		
	}
	
	/**
	 * 得到该设备的端口信息总数
	 * @param deviceId
	 * @return
	 */
	public Long getEventCountByDeviceId(long deviceId) {
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		tx=session.beginTransaction();
		/*
			String sql = "select  distinct e.id,e.title,e.moduletype,e.occurtime,e.receivetime,e.typevalue,interface.ipv4,interface.ipv6,interface.ifindex " +
					"from link l,event e,ifinterface interface where interface.device_id="+deviceId+
					" and ((interface.id=l.upinterface and e.ifinterface_id=l.upinterface) or "+
					"(interface.id=l.downinterface and e.ifinterface_id=l.downinterface))";*/
		String sql = "select count(*) as totalCount from link l,event e,ifinterface interface where interface.device_id="+deviceId+
		" and ((interface.id=l.upinterface and e.obj_id=l.upinterface) or "+
		"(interface.id=l.downinterface and e.obj_id=l.downinterface)) and e.objtype='interface'";
		Long count = (Long)session.createSQLQuery(sql).addScalar("totalCount",Hibernate.LONG).uniqueResult();
		return count;
	}
	
	public ArrayList<EventInfo> getEventbyDeviceId(long deviceId, int firstResult, int maxResult) throws Exception{
		ArrayList<EventInfo> list = new ArrayList<EventInfo>();
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			/*
			String sql = "select  distinct e.id,e.title,e.moduletype,e.occurtime,e.receivetime,e.typevalue,interface.ipv4,interface.ipv6,interface.ifindex " +
					"from link l,event e,ifinterface interface where interface.device_id="+deviceId+
					" and ((interface.id=l.upinterface and e.ifinterface_id=l.upinterface) or "+
					"(interface.id=l.downinterface and e.ifinterface_id=l.downinterface))";*/
			String sql = "select  distinct e.id,e.title,e.moduletype,e.occurtime,e.receivetime,e.typevalue,interface.ipv4,interface.ipv6,interface.ifindex " +
			"from link l,event e,ifinterface interface where interface.device_id="+deviceId+
			" and ((interface.id=l.upinterface and e.obj_id=l.upinterface) or "+
			"(interface.id=l.downinterface and e.obj_id=l.downinterface)) and e.objtype='interface'";
			List<Object[]> temp = session.createSQLQuery(sql).setFirstResult(firstResult).setMaxResults(maxResult).list();
			for(Object[] obj : temp){
				EventInfo eventInfo = new EventInfo();
				if(obj[0]!=null){
				    eventInfo.setId(Long.valueOf(obj[0].toString()));
				}
				if(obj[1]!=null){
					eventInfo.setTitle(obj[1].toString());
				}
				if(obj[2]!=null){
					eventInfo.setModuleType(obj[2].toString());
				}
				if(obj[3]!=null){
					eventInfo.setOccurTime(obj[3].toString());
				}
				if(obj[4]!=null){
					eventInfo.setReceiveTime(obj[4].toString());
				}
				if(obj[5]!=null){
					eventInfo.setTypeValue(obj[5].toString());
				}
				if(obj[6]!=null){
					eventInfo.setIpv4(obj[6].toString());
				}
				if(obj[7]!=null){
					eventInfo.setIpv6(obj[7].toString());
				}
				if(obj[8]!=null){
					eventInfo.setIfIndex(obj[8].toString());
				}
				list.add(eventInfo);
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
	
	public ArrayList<EventInfo> getEventbyInfId(Long infId, int firstResult, int maxResult) throws Exception{
		ArrayList<EventInfo> list = new ArrayList<EventInfo>();
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			String sql = "select  distinct e.id,e.occurtime,e.receivetime,e.moduleid,e.moduletype,e.typevalue " +
			"from event e where e.obj_id=" + infId + " and e.objtype='interface'";
			List<Object[]> temp = session.createSQLQuery(sql).setFirstResult(firstResult).setMaxResults(maxResult).list();
			for(Object[] obj : temp){
				EventInfo eventInfo = new EventInfo();
				if(obj[0]!=null){
					eventInfo.setId(Long.valueOf(obj[0].toString()));
				}
				if(obj[1]!=null){
					eventInfo.setOccurTime(obj[1].toString());
				}
				if(obj[2]!=null){
					eventInfo.setReceiveTime(obj[2].toString());
				}
				if(obj[3]!=null){
					eventInfo.setModuleId(obj[3].toString());
				}
				if(obj[4]!=null){
					eventInfo.setModuleType(obj[4].toString());
				}
				if(obj[5]!=null){
					eventInfo.setTypeValue(obj[5].toString());
				}
				list.add(eventInfo);
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
	 * 得到事件列表的总数
	 * @param deviceId
	 * @return
	 */
	public Long getServiceEventCountByDeviceId(long deviceId) {
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		Long count = null;
		try{
			tx=session.beginTransaction();
			/*
			String sql = "select e.id,e.occurtime,s.servicetype,s.port,e.typevalue " +
					"from servicemanage s,event e where s.id=e.serviceManage_id and s.device_id="+deviceId;*/
			String sql = "select count(*) as num from servicemanage s,event e where s.id=e.obj_id and s.device_id="+deviceId;
			count = (Long)session.createSQLQuery(sql).addScalar("num",Hibernate.LONG).uniqueResult();
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
		}
		return count;
	}
	
	
	public ArrayList<ServiceEventInfo> getServiceEventbyDeviceId(long deviceId, int firstResult, int maxResult) throws Exception{
		ArrayList<ServiceEventInfo> list = new ArrayList<ServiceEventInfo>();
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			/*
			String sql = "select e.id,e.occurtime,s.servicetype,s.port,e.typevalue " +
					"from servicemanage s,event e where s.id=e.serviceManage_id and s.device_id="+deviceId;*/
			String sql = "select e.id,e.occurtime,s.servicetype,s.port,e.typevalue " +
			"from servicemanage s,event e where s.id=e.obj_id and s.device_id="+deviceId;
			List<Object[]> temp = session.createSQLQuery(sql).setFirstResult(firstResult).setMaxResults(maxResult).list();
			for(Object[] obj : temp){
				ServiceEventInfo serviceEventInfo = new ServiceEventInfo();
				if(obj[0]!=null){
				    serviceEventInfo.setId(Long.valueOf(obj[0].toString()));
				}
				if(obj[1]!=null){
					serviceEventInfo.setOccurTime(obj[1].toString());
				}
				if(obj[2]!=null){
					serviceEventInfo.setServiceType(obj[2].toString());
				}
				if(obj[3]!=null){
					serviceEventInfo.setPort(obj[3].toString());
				}
				if(obj[4]!=null){
					serviceEventInfo.setTypeValue(obj[4].toString());
				}
				list.add(serviceEventInfo);
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
	 * 根据查询条件获得相应的事件列表
	 * @param condition
	 * @param maxResults
	 * @return
	 * @throws Exception
	 */
	public List<EventPojo> getEvents(String condition,int start, int maxResults) throws Exception {
		List<EventPojo> events = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			System.out.println("sql="+condition);
			if (start== 0 && maxResults == 0) {
				events = session.createQuery("from EventPojo " + condition).list();
			} else {
				events = session.createQuery("from EventPojo " + condition).setFirstResult(start).setMaxResults(maxResults).list();
			}
			tx.commit();
		}catch (Exception e) {
			if(tx!=null) {
				tx.rollback();
			}
			throw e;
		}
		return events;
	}
	
	/**
	 * 根据查询条件获得相应的事件 的总数
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public int getEventsCount(String condition) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		Long count = null;	
		try {
			tx = session.beginTransaction();
			String sql = "select count(*) as num from event " + condition;
			count = (Long)session.createSQLQuery(sql).addScalar("num",Hibernate.LONG).uniqueResult();
			tx.commit();
		}catch (Exception e) {
			if(tx!=null) {
				tx.rollback();
			}
			throw e;
		}
		return count.intValue();
	}
	
	public Long save(EventPojo event) throws Exception{
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			session.save(event);
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
		}
		if(event.getId()!=null){
			return event.getId();
		}else{
			return null;
		}
	}
	public Long getTotalNum(String fromTime,String toTime) throws Exception {
		Long totalNum;
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String sql ="select count(*) from EventPojo where to_days(occurTime)>=to_days('"+fromTime+"') and to_days(occurTime)<=to_days('"+toTime+"') ";
			List result = session.createQuery(sql).list();
			totalNum = (Long)result.get(0);
			tx.commit();
		}catch (Exception e) {
			if(tx!=null) {
				tx.rollback();
			}
			throw e;
		}
		return totalNum;
	}
	public List getFieldStatistic(String fieldName,String fromTime,String toTime) throws Exception {
		List fieldList = null;
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String sql ="select new list("+ fieldName +",count(*)) from EventPojo where to_days(occurTime)>=to_days('"+fromTime+"') and to_days(occurTime)<=to_days('"+toTime+"') group by " + fieldName;
			fieldList = session.createQuery(sql).list();
			tx.commit();
		}catch (Exception e) {
			if(tx!=null) {
				tx.rollback();
			}
			throw e;
		}
	
		return fieldList;
	}
	public List getTop10Ip(String fromTime,String toTime) throws Exception{
		List top10Ips = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			/*String sql = "select objipv4,objipv6,count(*) as num from event where to_days(occurtime)>=to_days('"+fromTime.trim()+"') and to_days(occurtime)<=to_days('"+toTime.trim()+"') and (objipv4!=null or objipv4 !='') "+" group by objipv4 order by num desc";
			top10Ips = session.createSQLQuery(sql)
							.setMaxResults(10)
							.list();*/
		   Criteria cri = session.createCriteria(EventPojo.class);   
		   cri.add(Restrictions.ge("occurTime", fromTime));
		   cri.add(Restrictions.le("occurTime", toTime));
		   cri.add(Restrictions.isNotNull("objIPv4"));
		   ProjectionList projList = Projections.projectionList();   
		   projList.add(Projections.groupProperty("objIPv4"));   
		   projList.add(Projections.count("objIPv4").as("num"));
		   cri.addOrder(Order.desc("num")); 
		   cri.setMaxResults(10);
		   cri.setProjection(projList);
		   top10Ips = cri.list();
		   tx.commit();
		}catch (Exception e) {
			if(tx!=null) {
				tx.rollback();
			}
			throw e;
		}
		return top10Ips;
	}
}
