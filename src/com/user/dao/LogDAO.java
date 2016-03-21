package com.user.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import com.base.model.Log;
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
/**
 * 
 * @author 李宪亮
 *
 */
public class LogDAO {
	
	/**
	 * 得到所有的日者信息  是个降序排列
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	public List<Log> getAllLogInfoList(String firstResult, String maxResult){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Log> logs = session.createCriteria(Log.class).setFirstResult(Integer.parseInt(firstResult)).setMaxResults(Integer.parseInt(maxResult)).addOrder(Order.desc("id")).list();
		transaction.commit();
		return logs;
	}
	
	/**
	 * 更新 jobId 这条记录
	 * @param logId
	 */
	public void updateLogByLogId(long logId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "update " + Log.class.getName() + " set quitTime = '" + formatTime(new Date()) + "' where id = ?";
		session.createQuery(sql).setLong(0, logId).executeUpdate();
		transaction.commit();
	}
	
	/**
	 * 用户登录后添加一条日志
	 * @param userName
	 * @param requestIP
	 */
	public void addLogInfo(String userName, String requestIP) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Log log = new Log();
		log.setIp(requestIP);
		log.setLoginTime(formatTime(new Date()));
		log.setUserName(userName);
		session.save(log);
		transaction.commit();
		
		//暂且记下logId号 在用户退出时 更新该记录 添加退出时间
	    ServletActionContext.getRequest().getSession().setAttribute("logId", log.getId());
	}
	
	/**
	 * 得到所有记录的记录数
	 * @return
	 */
	public int getLogCount(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		int count = (Integer)session.createCriteria(Log.class).setProjection(Projections.rowCount()).uniqueResult();
		transaction.commit();
		return count;
	}
	
	/**
	 * 批量删除 用户登录信息
	 * @param logIds
	 */
	public void deleteLogs(String logIds){
		String ids = logIds.substring(0, logIds.length() - 1).replaceAll(";", ",");
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		String hql = "delete from user_log where id in (" + ids  + ")";
		Transaction transaction = session.beginTransaction();
		session.createSQLQuery(hql).executeUpdate();
		transaction.commit();
	}
	
	/**
	 * 日期函数的转换
	 * @param date
	 * @return
	 */
	public String formatTime(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
	
	
}
