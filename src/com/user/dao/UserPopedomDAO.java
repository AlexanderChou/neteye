package com.user.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.base.model.UserPopedom;
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
 * 用户权限管理
 * @author 李宪亮
 *
 */
public class UserPopedomDAO {
	
	/**
	 * 保存用户权限
	 * @param userPopedom
	 * @return
	 */
	public UserPopedom save(UserPopedom userPopedom) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(userPopedom);
		transaction.commit();
		return userPopedom;
	}
	
	/**
	 * 批量修改用户组
	 * @param userGroupId
	 */
	public void updateUgBatch(long userGroupId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "update user_popedom set group_id = 1 where group_id=" + userGroupId;
		session.createSQLQuery(sql).executeUpdate();
		transaction.commit();
	}
	
	/**
	 * 删除用户权限
	 * @param userPopedom
	 * @return
	 */
	public UserPopedom delete(UserPopedom userPopedom) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.delete(userPopedom);
		transaction.commit();
		return userPopedom;
	}
	
	
	/**
	 * 批量删除用户权限 主要是删除用户信息
	 * @param userPopedom
	 * @return
	 */
	public void deleteBatch(long userId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "delete from user_popedom where user_id = " + userId;
		session.createSQLQuery(sql).executeUpdate();
		transaction.commit();
	}
	
	/**
	 * 根据用户id查找 用户组
	 * @param userId 
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	public List<UserPopedom> getUserPopedomByUserId(long userId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<UserPopedom> ups = session.createCriteria(UserPopedom.class).add(Restrictions.eq("user.id", userId)).list();
		transaction.commit();
		return ups;
	}
	
	/**
	 * 根据用户组id查找 用户
	 * @param groupId 
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	public List<UserPopedom> getUserPopedomByGroupId(long groupId,String firstResult, String maxResult) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<UserPopedom> ups = session.createCriteria(UserPopedom.class).add(Restrictions.eq("group.id", groupId)).setFirstResult(Integer.parseInt(firstResult)).setMaxResults(Integer.parseInt(maxResult)).list();
		transaction.commit();
		return ups;
	}	
	
	/**
	 * 判断是否 已经存在该资源组
	 * @param userId
	 * @param groupId
	 * @return
	 */
	public UserPopedom isHave(long userId, long groupId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		UserPopedom userPopedom = (UserPopedom)session.createCriteria(UserPopedom.class).add(Restrictions.eq("group.id", groupId)).add(Restrictions.eq("user.id", userId)).uniqueResult();
		transaction.commit();
		return userPopedom;
	}
}
