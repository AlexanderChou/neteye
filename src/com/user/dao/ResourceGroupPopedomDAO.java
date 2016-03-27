package com.user.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.base.model.Resource;
import com.base.model.ResourceGroupPopedom;

public class ResourceGroupPopedomDAO extends BaseHibernateDAO {
	/**
	 * 保存权限
	 * @param userPopedom
	 * @return
	 */
	public ResourceGroupPopedom save(ResourceGroupPopedom popedom) {
		Session session = getSession();
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
//	public ResourceGroupPopedom isHave(long userGroupId, long resourceGroupId){
//		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
//		Transaction transaction = session.beginTransaction();
//		ResourceGroupPopedom popedom = (ResourceGroupPopedom)session.createCriteria(ResourceGroupPopedom.class).add(Restrictions.eq("userGroup.id", userGroupId)).add(Restrictions.eq("resourceGroup.id", resourceGroupId)).uniqueResult();
//		transaction.commit();
//		return popedom;
//	}
	
	

	/**
	 * 删除资源关联资源组
	 * @param group
	 * @return
	 */
	public void delete(ResourceGroupPopedom rg) {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
	//	session.clear();
		session.delete(rg);
		transaction.commit();

	}
	
	public List<ResourceGroupPopedom> getResourceByGroupId(Long groupId){
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		List<ResourceGroupPopedom>rgPopedom=session.createCriteria(ResourceGroupPopedom.class).add(Restrictions.eq("resourceGroupId", groupId)).list();;
		transaction.commit();
		return rgPopedom;
	}
	
	/**
	 * 得到该用户组的所有资源组
	 * @param userId
	 * @return
	 */
	public List<Resource> getResourceById(String id) {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		String sql = "SELECT * FROM resource WHERE id IN ( SELECT resource_id FROM reource_group_popedom WHERE resource_group_id ="+ id + ")";
		List<Resource> rgs = session.createSQLQuery(sql).list();
		transaction.commit();
		return rgs;
	}
	public List<Resource> getNoSsignResources(String id) {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		String sql = "SELECT * FROM resource WHERE id NOT IN ( SELECT resource_id FROM resource_group_popedom WHERE resource_group_id ="+ id + ") AND resource_id>=0";
		List<Resource> resources = session.createSQLQuery(sql).list();
		transaction.commit();
		return resources;
	}
	/**
	 * 根据资源组id
	 * @param groupId 
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
//	public List<UserGroupPopedom> getPopedomByResourceGroupId(long resourceGroupId,String firstResult, String maxResult) {
//		Session session = getSession();
//		Transaction transaction = session.beginTransaction();
//		List<UserGroupPopedom> ups = session.createCriteria(UserGroupPopedom.class).add(Restrictions.eq("resourceGroup.id", resourceGroupId)).setFirstResult(Integer.parseInt(firstResult)).setMaxResults(Integer.parseInt(maxResult)).list();
//		transaction.commit();
//		return ups;
//	}
	/**
	 * 检验记录是否已经存在
	 * @param resourceId，groupId
	 * @return 返回false 说明该记录存在  
	 */
	public boolean ifExist(Long resourceId,Long groupId){
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		List<ResourceGroupPopedom> users = session.createCriteria(ResourceGroupPopedom.class).add(Restrictions.eq("resourceId", resourceId)).add(Restrictions.eq("resourceGroupId", groupId)).list();
		transaction.commit();
		return users.isEmpty();
	}
}
