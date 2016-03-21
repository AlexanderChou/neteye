package com.user.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.base.model.Resource;
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
 * 资源管理
 * @author 李宪亮
 *
 */
public class ResourceDAO {
	/**
	 * 保存资源
	 * @param resource
	 * @return
	 */
	public Resource save(Resource resource){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(resource);
		transaction.commit();
		return resource;
	}
	
	/**
	 * 删除资源
	 * @param resource
	 * @return
	 */
	public Resource delete(Resource resource) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(resource);
		transaction.commit();
		return resource;
	}
	
	/**
	 * 根据组得到资源列表
	 * @param id
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	public List<Resource> getResourcesByGroup(String id, String firstResult, String maxResult){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Resource> resources = session.createCriteria(Resource.class).add(Restrictions.eq("group", Long.parseLong(id))).setFirstResult(Integer.parseInt(firstResult)).setMaxResults(Integer.parseInt(maxResult)).list();
		transaction.commit();
		return resources;
	}
	
	/**
	 * 得到所有的资源列表
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	public List<Resource> getAllResources(String firstResult, String maxResult) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Resource> resources = session.createCriteria(Resource.class).setFirstResult(Integer.parseInt(firstResult)).setMaxResults(Integer.parseInt(maxResult)).list();
		transaction.commit();
		return resources;
	}
	public List<Resource> getAllResources() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Resource> resources = session.createCriteria(Resource.class).list();
		transaction.commit();
		return resources;
	}
	public List<Resource> getLeafResources(int id){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Resource> resources = session.createCriteria(Resource.class).add(Restrictions.eq("resourceTypeId",id)).add(Restrictions.eq("flag",1)).list();
		transaction.commit();
		return resources;
	}
	public String getNameById(Long id){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Resource resource=(Resource) session.createCriteria(Resource.class).add(Restrictions.eq("id", id)).uniqueResult();
		transaction.commit();
		return resource.getName();
	}
	public Resource getResourceById(long id){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Resource resource = (Resource)session.load(Resource.class, id);
		transaction.commit();
		return resource;
	}
	public Resource getResourceByURL(String url){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Resource resource=(Resource) session.createCriteria(Resource.class).add(Restrictions.eq("url", url)).uniqueResult();
		transaction.commit();
		return resource;
	}
	public Long urlIsInUser(Long userId,Long resourceId) {
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		tx=session.beginTransaction();
		String sql = "select count(*) as totalCount from resource_group_popedom  rgp where rgp.resource_id="+resourceId+" and rgp.resource_id in ("+
		" select rgp.resource_id from resource_group_popedom  rgp where rgp.resource_group_id in ( "+
		" select ugp.resource_group_id from user_group_popedom ugp where ugp.user_group_id in("+
		" select up.group_id from user_popedom up where up.user_id="+userId+
		")))";
		Long count = (Long)session.createSQLQuery(sql).addScalar("totalCount",Hibernate.LONG).uniqueResult();
		System.out.println("count="+count);
		return count;
	}
	public static void main(String[]args){
		ResourceDAO test = new ResourceDAO();
		System.out.println(test.urlIsInUser(1L,20L));
	}
}
