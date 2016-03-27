package com.base.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.base.model.EventStatus;
import com.base.util.HibernateUtil;
import com.event.dto.EventInfo;;

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
/**
 * <p>Title: 最新事件状态信息</p>
 * <p>Description: 提供最新事件状态的相关方法</p>
 * @version 1.0
 * @author 郭玺
 * <p>Company: 网络中心</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * 注：黄桂奋最初实现事件模块时，eventstatus、alarm和event表中的字段基本是一样的，这样做，虽然不太符合数据库设计规范，导致数据有些冗余
 *    但能从一定程度上提高查询效率（因为查询时仅对一个表操作，不再与event表关联，而且event表中的数据通常也比较多）
 *    本次更改，对eventstatus和alarm表作了相应简化，这样做，虽然避免了以上不足，但查询时会在一定程度上降低查询效率，而且增加编程的复杂度
 */
public class EventStatusService {
	private Session session=null;
	public EventInfo getEventStatusInfo(String objType,String objId) throws Exception {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery("select s.id,s.event_id,e.title,e.content,e.priority,e.moduleid,e.moduletype,e.typevalue,e.occurtime,e.receivetime,e.obj_id,e.objname,e.objipv4,e.objipv6,objtype from event e,eventstatus s where e.objtype = :objType and e.objId = :objId and e.id = s.event_id order by time desc");
		query.setString("objType", objType).setString("objId", objId);
		List<Object[]> list = query.list();
		EventInfo eventInfo = this.getEventInfo(list);
		return eventInfo;
	}
	public EventInfo getEventStatusInfoByCondition(String condition) throws Exception {
		EventInfo eventInfo = null;
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery("select s.id ,s.event_id,e.title,e.content,e.priority,e.moduleid,e.moduletype,e.typevalue,e.occurtime,e.receivetime,e.obj_id,e.objname,e.objipv4,e.objipv6,objtype from event e,eventstatus s where e.id = s.event_id and " + condition);
		List<Object[]> list = query.list();
		if(list!=null && list.size()>0){
			Object[] args = list.get(0);
			eventInfo = this.getEventInfo(list);
		}
		return eventInfo;
	}
	public EventStatus getEventStatusByCondition(String condition) throws Exception {
		EventStatus objStatus = new EventStatus();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("select s.id,s.event_id from event e,eventstatus s where e.id = s.event_id and " + condition);
		List list = query.list();
		if(list.size()>0){
			Object obj[] = (Object[])list.get(0);
			objStatus.setId(Long.valueOf(obj[0].toString()));
			objStatus.setEventId(Long.valueOf(obj[1].toString()));
		}
		return objStatus;
	}
	public void save(EventStatus o) throws Exception {
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			session.save(o);
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
		}
	}
	private EventInfo getEventInfo(List<Object[]> list){
		EventInfo eventInfo = null;
		if(list!=null && list.size()>0){
			Object obj[] = (Object[])list.get(0);
			eventInfo = new EventInfo();
			if(obj[0]!=null){
				eventInfo.setId(Long.valueOf(obj[0].toString()));
			}
			if(obj[1]!=null){
				eventInfo.setEventId(Long.valueOf(obj[1].toString()));
			}
			if(obj[2]!=null){
				eventInfo.setTitle(obj[2].toString());
			}
			if(obj[3]!=null){
				eventInfo.setContent(obj[3].toString());
			}
			if(obj[4]!=null){
				eventInfo.setPriority(Integer.valueOf(obj[4].toString()));
			}
			if(obj[5]!=null){
				eventInfo.setModuleId(obj[5].toString());
			}
			if(obj[6]!=null){
				eventInfo.setModuleType(obj[6].toString());
			}
			if(obj[7]!=null){
				eventInfo.setTypeValue(obj[7].toString());
			}
			if(obj[8]!=null){
				eventInfo.setOccurTime(obj[8].toString());
			}
			if(obj[9]!=null){
				eventInfo.setReceiveTime(obj[9].toString());
			}
			if(obj[10]!=null){
				eventInfo.setObjId(Long.valueOf(obj[10].toString()));
			}
			if(obj[11]!=null){
				eventInfo.setObjName(obj[11].toString());
			}
			if(obj[12]!=null){
				eventInfo.setObjIPv4(obj[12].toString());
			}
			if(obj[13]!=null){
				eventInfo.setObjIPv6(obj[13].toString());
			}
			if(obj[14]!=null){
				eventInfo.setObjType(obj[14].toString());
			}
		}	
		return eventInfo;
	}
	public static void main(String[] args){
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		EventStatus objStatus = new EventStatus();
		try{
			tx=session.beginTransaction();
			Query query = session.createSQLQuery("select s.id,s.event_id from event e,eventstatus s where e.id = s.event_id and  e.objtype='device' and e.obj_id='1' and e.moduletype='PING'");
			List list = query.list();
			if(list.size()>0){
				Object aa[] = (Object[])list.get(0);
				objStatus.setId(Long.valueOf(aa[0].toString()));
				objStatus.setEventId(Long.valueOf(aa[1].toString()));
			}
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
		}
	}
}
