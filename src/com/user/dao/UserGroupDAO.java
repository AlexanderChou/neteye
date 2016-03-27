package com.user.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.base.model.UserGroup;
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
 * 用户组管理
 * @author 李宪亮
 *
 */
public class UserGroupDAO {
	/**
	 * 保存用户组
	 * @return
	 */
	public UserGroup save(UserGroup group) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(group);
		transaction.commit();
		return group;
	}
	
	/**
	 * 删除用户组 
	 *    由于表的关联 在删除用户组之前 
	 *    先把权限表里对应的记录删掉 再把用户权限表里的对应的记录的用户的用户组设为最低权限组 这个用户组是默认的
	 * @param group
	 * @return
	 */
	public UserGroup delete(UserGroup group) {
		UserGroupPopedomDAO userGroupPopedomDAO = new UserGroupPopedomDAO();
		//批量删除权限表里的 usergroup 是Group 的记录
		userGroupPopedomDAO.deleteBatchByUserGroup(group.getId());
		//更新用户对应的权限组 
		UserPopedomDAO userPopedomDao = new UserPopedomDAO();
		userPopedomDao.updateUgBatch(group.getId());
		//最后在删除用户组
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.delete(group);
		transaction.commit();
		return group;
	}
	
	/**
	 * 由用户组id得到用户组 嘿嘿这里有一个知识点啊
	 * @param userGroupId
	 * @return
	 */
	public UserGroup getUserGroupById(long userGroupId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		UserGroup userGroup = (UserGroup)session.load(UserGroup.class, userGroupId);
		if (Hibernate.isInitialized(userGroup)) {
			Hibernate.initialize(userGroup);  //在这里
		}
		transaction.commit();
		return userGroup;
	}
	
	/**
	 * 根据用户得到他所在的用户组
	 * @return
	 */
	public List<Object[]> getAllUserGroups(String userId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();	
		String sql = "select * from user_group where id not in (select group_id from user_popedom where user_id = " + userId + ")";
		List<Object[]> userGroups = session.createSQLQuery(sql).list();
		transaction.commit();
		return userGroups;
	}
	
	/**
	 * 得到所有的用户组
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	public List<UserGroup> getAllUserGroups(String firstResult, String maxResult) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<UserGroup> ugs = session.createCriteria(UserGroup.class).setFirstResult(Integer.valueOf(firstResult)).setMaxResults(Integer.parseInt(maxResult)).list();
		transaction.commit();
		return ugs;
	}
	
	/**
	 * 得到所有的用户组
	 * @return
	 */
	public List<UserGroup> getAllUserGroups() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<UserGroup> ugs = session.createCriteria(UserGroup.class).list();
		transaction.commit();
		return ugs;
	}
	
	/**
	 * 得到所有列的
	 * @return
	 */
	public int getUserGroupsCount(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		int count = (Integer)session.createCriteria(UserGroup.class).setProjection(Projections.rowCount()).uniqueResult();
		transaction.commit();
		return count;
	}
	
	/**
	 * 检验用户组的名字是否相同
	 * @param userGroupName
	 * @return true 有
	 */
	public boolean checkUserGroupNameIsHave(String userGroupName){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<UserGroup> userGroups = session.createCriteria(UserGroup.class).add(Restrictions.eq("name", userGroupName)).list();
		transaction.commit();
		return !userGroups.isEmpty();
	}
}
