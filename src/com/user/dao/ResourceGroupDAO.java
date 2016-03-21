package com.user.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.base.model.ResourceGroup;
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
public class ResourceGroupDAO {
	
	/**
	 * 保存资源组
	 * @param group
	 * @return
	 */
	public ResourceGroup save(ResourceGroup group) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(group);
		transaction.commit();
		return group;
	}
	
	/**
	 * 删除资源组
	 * @param group
	 * @return
	 */
	public ResourceGroup delete(ResourceGroup group) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.delete(group);
		transaction.commit();
		return group;
	}
	
	/**
	 * 待选的资源组
	 * @return
	 */
	public List<ResourceGroup> getResourceGroups(String userGroupId){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select * from resource_group where id not in (select resource_group_id from user_group_popedom where user_group_id = " + userGroupId + ");";
		List<Object[]> list = session.createSQLQuery(sql).list();
		List<ResourceGroup> rgs = new ArrayList<ResourceGroup>();
		for (Object[] objects : list) {
			ResourceGroup r = new ResourceGroup();
			r.setId(Long.parseLong(objects[0].toString()));
			r.setName(objects[1].toString());
			rgs.add(r);
		}
		transaction.commit();
		
		return rgs;
	}
	public boolean checkResourceGroupNameIsHave(String resourceGroupName){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<ResourceGroup> list = session.createCriteria(ResourceGroup.class).add(Restrictions.eq("name", resourceGroupName)).list();
		transaction.commit();
		return !list.isEmpty();
	}
	public ResourceGroup getResourceGroupById(Long id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
	//	session.clear();
		ResourceGroup rg = (ResourceGroup)session.load(ResourceGroup.class, id);
		if (Hibernate.isInitialized(rg)) {
			Hibernate.initialize(rg);  //在这里
		}
		transaction.commit();
		return rg;
	}
	/**
	 * 得到所有列的
	 * @return
	 */
	public int getResourceGroupsCount(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		int count = (Integer)session.createCriteria(ResourceGroup.class).setProjection(Projections.rowCount()).uniqueResult();
		transaction.commit();
		return count;
	}
	public List<ResourceGroup> getListResourceGroup(int firstResult, int maxResult){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<ResourceGroup> rgs = session.createCriteria(ResourceGroup.class).setFirstResult(firstResult).setMaxResults(maxResult).list();
		transaction.commit();		
		return rgs;
	}
}
