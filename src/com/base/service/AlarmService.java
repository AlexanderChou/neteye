package com.base.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.base.model.Alarm;
import com.base.util.HibernateUtil;

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
public class AlarmService {
	private Session session=null;
	public Long save(Alarm alarm) throws Exception{
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			session.save(alarm);
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
		}
		if(alarm.getId()!=null){
			return alarm.getId();
		}else{
			return null;
		}
	}
	public static Long findAlarmById(Long userId) throws Exception{
		Long num = null;
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			String sql = "select count(*) as num from user_message where objStatus='unread' and user_id="+userId;
			num = (Long)session.createSQLQuery(sql).addScalar("num",Hibernate.LONG).uniqueResult();
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
		}
		return num;
	}
	public static String deleteAlarm(Long userId,Long alarmId) throws Exception{
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		String status = ""; 
		try{
			tx=session.beginTransaction();
			String sql = "delete from UserMessage u where u.objStatus='read' and u.objType='alarm' and u.objId =" + alarmId + " and u.userId=" + userId;
			session.createQuery(sql).executeUpdate();
			session.flush();
			sql = "delete from Alarm a where a.id="+alarmId;
			session.createQuery(sql).executeUpdate();
			tx.commit();
			status = "succes";
		}catch(Exception e){
			status = "false";
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
		}
		return status;
	}
	public static String clearAlarms(Long userId,String objType) throws Exception{
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		String status = ""; 
		try{
			tx=session.beginTransaction();
			String sql = "select u.obj_id from user_message u where u.objstatus='read' and u.objtype='"+ objType +"' and u.user_id=" + userId;
			List alarms = session.createSQLQuery(sql).list();
			sql = "delete from UserMessage u where u.objStatus='read' and u.objType='"+ objType +"' and u.userId=" + userId;
			session.createQuery(sql).executeUpdate();
			session.flush();
			StringBuffer bf = new StringBuffer();
			for(Object o:alarms){
				bf.append(o.toString()).append(",");
			}
			String temp = bf.toString();
//			System.out.println("temp="+temp);
			sql = "delete from alarm where id in("+temp.substring(0,temp.length()-2)+")";
			
			session.createSQLQuery(sql).executeUpdate();
			tx.commit();
			status = "succes";
		}catch(Exception e){
			status = "false";
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
		}
		return status;
	}
	public static List<Alarm> findAlarmsById(Long userId) throws Exception{
		List<Alarm> alarms = new ArrayList<Alarm>();
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		String sql = "";
		try{
			tx=session.beginTransaction();
			sql = "update UserMessage m set m.objStatus='read' where m.objStatus='unread' and m.userId="+userId;
			session.createQuery(sql).executeUpdate(); 
			session.flush(); 
			sql = "select a.id,a.moduleid,a.moduletype,a.typevalue,a.obj_id,a.objname,a.objtype,a.objipv4,a.objipv6,a.occurtime,a.title,a.content "
					   + "from alarm a,user_message m "
					   + "where a.id=m.obj_id and m.user_id="+userId+" and m.objtype='alarm' order by a.occurtime desc ";
			List<Object[]> temp = session.createSQLQuery(sql).list();
			if(temp!=null){
				for(Object[] o:temp){
					Alarm alarm = new Alarm();
					alarm.setId(Long.valueOf(o[0].toString()));
					if(o[1]!=null){
						alarm.setModuleId(o[1].toString());
					}
					if(o[2]!=null){
						alarm.setModuleType(o[2].toString());
					}
					if(o[3]!=null){
						alarm.setTypeValue(o[3].toString());
					}
					if(o[4]!=null){
						alarm.setObjId(Long.valueOf(o[4].toString()));
					}
					if(o[5]!=null){
						alarm.setObjName(o[5].toString());
					}
					if(o[6]!=null){
						alarm.setObjType(o[6].toString());
					}
					if(o[7]!=null){
						alarm.setObjIPv4(o[7].toString());
					}
					if(o[8]!=null){
						alarm.setObjIPv6(o[8].toString());
					}
					if(o[9]!=null){
						alarm.setOccurTime(o[9].toString());
					}
					if(o[10]!=null){
						alarm.setTitle(o[10].toString());
					}
					if(o[11]!=null){
						alarm.setContent(o[11].toString());
					}
					alarms.add(alarm);
				}
			}
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
		}
		return alarms;
	}
}
