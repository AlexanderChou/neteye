package com.base.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.base.model.Link;
import com.base.util.HibernateUtil;
import com.view.dto.DeviceInfo;

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
public class LinkService extends BaseService{
	
	
	/**
	 * 得到所有列表的总数
	 * @return
	 */
	public int getAllLinksCount() {
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = s.beginTransaction();
		Integer count = (Integer)s.createCriteria(Link.class).setProjection(Projections.rowCount()).uniqueResult();
		transaction.commit();
		return count.intValue();
	}
	
	
	public Link findById(long id) throws Exception{
		Link link=null;
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();			
			Criteria cri=session.createCriteria(Link.class).add(Restrictions.eq("id",id));
			link=(Link)cri.uniqueResult();
			if(!Hibernate.isInitialized(link)){
				Hibernate.initialize(link);
			}
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace(); 
			throw e;
		}
		return link;
	}
	public Link getLink(long id) throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		Link link = null;
		try{
			tx=session.beginTransaction();
			link =(Link)session.createCriteria(Link.class).add(Restrictions.idEq(id))
			.setFetchMode("upInterface", FetchMode.JOIN).setFetchMode("downInterface", FetchMode.JOIN).uniqueResult();
			if(!Hibernate.isInitialized(link)){
				Hibernate.initialize(link);
			}
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return link;
	}
	public List<Link>  getLinkList(Long deviceId) throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		List<Link>  linkList = new ArrayList();
		try{
			tx=session.beginTransaction();
			Criteria cri= session.createCriteria(Link.class).setFetchMode("upInterface", FetchMode.JOIN).setFetchMode("downInterface", FetchMode.JOIN);
			cri.add(Restrictions.or(Restrictions.eq("upDevice",deviceId), Restrictions.eq("downDevice",deviceId)));
			linkList=cri.list();
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return linkList;
	}
	public List getLinkListByDeviceId(String deviceIds) throws Exception{
		List list =null;
		String sql = "select link.id,link.name,link.updevice,link.downdevice,link.upinterface,link.downinterface from link  where link.`updevice` in ("+deviceIds+") and link.`downdevice` in ("+deviceIds+")";
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
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
	
	/**
	 * 得到所有的列表 有分页
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<Link> getAllLinkList(String firstResult, String maxResult) throws Exception{
		List<Link> list=null;
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			list=session.createCriteria(Link.class).setFirstResult(Integer.parseInt(firstResult)).setMaxResults(Integer.parseInt(maxResult)).list();
		}catch(Exception e){
			if(tx!=null){
				tx.commit();
			}
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	/*
	 * @autor:JiangNing  update:guoxi
	 * input:设备的ID
	 * output:设备的基本信息
	 * 该函数是在轮询设备状态信息时被调用，目前DeviceInfo类只使用了status属性
	 * 注：sql更改的原因：数据库event表最初设计时，仅从端口级别进行事件监控（实际上有些设备类型是没有端口的）
	 * 字段ifinterface_id和serviceManage_id分别与表ifinterface和servicemanage进行主外键关联
	 * 为便于以后扩展，将ifinterface_id和serviceManage_id合并为obj_id,同时增加objtype字段(该字段可能值为:device、interface、service等)
	 */
	public DeviceInfo getDeviceInfoByDeviceId(long deviceId) throws Exception{
		DeviceInfo deviceInfo=new DeviceInfo();
		deviceInfo.setStatus("green");
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		String sql = "";
		try{
			tx=session.beginTransaction();
			sql = "select e.typevalue from event e,eventstatus estatus where estatus.event_id=e.id and e.objtype='device' and e.obj_id="+deviceId;
			List tempStatusList = session.createSQLQuery(sql).list();
			//String tempStatus = (String)session.createSQLQuery(sql).uniqueResult();
			//取到的值不唯一.待查.
			String tempStatus = null;
			if(tempStatusList!=null && tempStatusList.size()>0){
				tempStatus = tempStatusList.get(0).toString();
			}
			if(tempStatus!=null){
				if(!"".equals(tempStatus) && tempStatus.equals("down")){
					deviceInfo.setStatus("red");
				}
			}else{
				/*
				sql = "select e.typevalue " +
						"from link l,event e,ifinterface interface,eventstatus estatus where interface.device_id="+deviceId+
						" and ((interface.id=l.upinterface and e.ifinterface_id=l.upinterface) or "+
						"(interface.id=l.downinterface and e.ifinterface_id=l.downinterface)) and estatus.event_id=e.id";*/
				sql = "select e.typevalue " +
				"from link l,event e,ifinterface interface,eventstatus estatus where interface.device_id="+deviceId+
				" and ((interface.id=l.upinterface and e.obj_id=l.upinterface) or "+
				"(interface.id=l.downinterface and e.obj_id=l.downinterface)) and estatus.event_id=e.id and e.objtype='interface'";
				List<String> temp = session.createSQLQuery(sql).list();
				for(String obj : temp){
					String typeValue=null;
					if(obj!=null){
						typeValue=obj;
					}
	                if(typeValue.equals("down")){
	                	deviceInfo.setStatus("red");
	                	break;
	                }
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
		return deviceInfo;
	}	
	public List<Link> getLinkListByInterfId(long interfaceId) throws Exception{
		List<Link> list =new ArrayList();
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
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
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
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
	/**
	 * 判断数据库中的链路是否重复(需补充完整)
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
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
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
		/*
		long upInterfaceId=link.getUpInterface().getId();
		long downInterfaceId=link.getDownInterface().getId();
		PortService service=new PortService();
		IfInterfaceService infService=new IfInterfaceService();
		IfInterface upInterface=service.findById(upInterfaceId);
		if(upInterface!=null){
			upInterface.setTrafficFlag(0);
			infService.saveInf(upInterface);
		}
		IfInterface downInterface=service.findById(downInterfaceId);
		if(downInterface!=null){
			downInterface.setTrafficFlag(0);
			infService.saveInf(downInterface);
		}*/
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
	
	/**
	 * 由上行设备名称和下行设备名称 得到链路
	 * @param upName 上行设备名称
	 * @param downName 下行设备名称
	 * @return
	 */
	public List<Link> getLinksByUnAndDn(String upName, String downName){
		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String hql = "from Link where name like '" + upName + "%" + downName + "%'";
		List<Link> links = session.createQuery(hql).list();
		transaction.commit();
		return links;
	}
	
	public static void main(String[] args) {
		LinkService s = new LinkService();
	}
	
}
