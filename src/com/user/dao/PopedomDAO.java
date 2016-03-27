package com.user.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.base.model.Popedom;
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
public class PopedomDAO {
	/**
	 * 保存权限
	 * @param userPopedom
	 * @return
	 */
	public Popedom save(Popedom popedom) {
		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(popedom);
		transaction.commit();
		return popedom;
	}
	/**
	 * 以用户组和资源组为查询条件 目的是查看是否有重复的记录
	 * @param userGroupId
	 * @param resourceGroupId
	 * @return
	 */
	public Popedom isHave(long userGroupId, long resourceGroupId){
		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Popedom popedom = (Popedom)session.createCriteria(Popedom.class).add(Restrictions.eq("userGroup.id", userGroupId)).add(Restrictions.eq("resourceGroup.id", resourceGroupId)).uniqueResult();
		transaction.commit();
		return popedom;
	}
	
	
	/**
	 * 删除权限
	 * @param popedom
	 * @return
	 */
	public Popedom delete(Popedom popedom) {
		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.save(popedom);
		transaction.commit();
		return popedom;
	}
	
	/**
	 * 批量删除 
	 * @param userPopedom
	 * @return
	 */
	public void deleteBatchByUserGroup(long userGroupId) {
		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "delete from popedom where user_group_id = " + userGroupId;
		session.createSQLQuery(sql).executeUpdate();
		transaction.commit();
	}
	
	/**
	 * 根据用户组id
	 * @param userGroupId 
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	public List<Popedom> getPopedomByUserGroup(long userGroupId,String firstResult, String maxResult) {
		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Popedom> ups = session.createCriteria(Popedom.class).add(Restrictions.eq("userGroup.id", userGroupId)).setFirstResult(Integer.parseInt(firstResult)).setMaxResults(Integer.parseInt(maxResult)).list();
		transaction.commit();
		return ups;
	}
	
	/**
	 * 得到该用户的所有用户组
	 * @param userId
	 * @return
	 */
	public List<Object[]> getUserGroupsByUserId(String userId) {
		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select * from user_group where id in (select group_id from user_popedom where user_id = "+ Long.valueOf(userId) +")";
		List<Object[]> ps = session.createSQLQuery(sql).list();
		transaction.commit();
		return ps;
	}
	
	
	/**
	 * 得到该用户组的所有资源组
	 * @param userId
	 * @return
	 */
	public List<Object[]> getResourceGroupByugId(String userGroupId) {
		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select * from resource_group where id in (select resource_group_id from popedom where user_group_id = " + userGroupId + ")";
		List<Object[]> ugs = session.createSQLQuery(sql).list();
		transaction.commit();
		return ugs;
	}
	
	/**
	 * 根据资源组id
	 * @param groupId 
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	public List<Popedom> getPopedomByResourceGroupId(long resourceGroupId,String firstResult, String maxResult) {
		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Popedom> ups = session.createCriteria(Popedom.class).add(Restrictions.eq("resourceGroup.id", resourceGroupId)).setFirstResult(Integer.parseInt(firstResult)).setMaxResults(Integer.parseInt(maxResult)).list();
		transaction.commit();
		return ups;
	}
	
	public static void main(String[] args) {
		PopedomDAO dao = new PopedomDAO();
		dao.deleteBatchByUserGroup(1l);
	}
}
