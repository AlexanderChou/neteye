package com.user.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.base.model.UserGroupPopedom;

/**
 * <p>Title:数据库访问类 </p>
 * <p>Description: 为用户组分配资源组</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: bjrongzhi</p>
 * @author 李宪亮
 * @version 1.0
 */
public class UserGroupPopedomDAO extends BaseHibernateDAO {
	/**
	 * 保存权限
	 * @param userPopedom
	 * @return
	 */
	public UserGroupPopedom save(UserGroupPopedom popedom) {
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
	public UserGroupPopedom isHave(long userGroupId, long resourceGroupId){
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		UserGroupPopedom popedom = (UserGroupPopedom)session.createCriteria(UserGroupPopedom.class).add(Restrictions.eq("userGroup.id", userGroupId)).add(Restrictions.eq("resourceGroup.id", resourceGroupId)).uniqueResult();
		transaction.commit();
		return popedom;
	}
	
	
	/**
	 * 删除权限
	 * @param popedom
	 * @return
	 */
	public UserGroupPopedom delete(UserGroupPopedom popedom) {
		Session session = getSession();
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
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		String sql = "DELETE FROM user_group_popedom WHERE user_group_id = " + userGroupId;
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
	public List<UserGroupPopedom> getPopedomByUserGroup(long userGroupId,String firstResult, String maxResult) {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		List<UserGroupPopedom> ups = session.createCriteria(UserGroupPopedom.class).add(Restrictions.eq("userGroup.id", userGroupId)).setFirstResult(Integer.parseInt(firstResult)).setMaxResults(Integer.parseInt(maxResult)).list();
		transaction.commit();
		return ups;
	}
	
	/**
	 * 得到该用户的所有用户组
	 * @param userId
	 * @return
	 */
	public List<Object[]> getUserGroupsByUserId(String userId) {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		String sql = "SELECT * FROM user_group WHERE id IN (SELECT group_id FROM user_popedom WHERE user_id = "+ Long.valueOf(userId) +")";
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
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		String sql = "SELECT * FROM resource_group WHERE id IN (SELECT resource_group_id FROM user_group_popedom WHERE user_group_id = " + userGroupId + ")";
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
	public List<UserGroupPopedom> getPopedomByResourceGroupId(long resourceGroupId,String firstResult, String maxResult) {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		List<UserGroupPopedom> ups = session.createCriteria(UserGroupPopedom.class).add(Restrictions.eq("resourceGroup.id", resourceGroupId)).setFirstResult(Integer.parseInt(firstResult)).setMaxResults(Integer.parseInt(maxResult)).list();
		transaction.commit();
		return ups;
	}
	
	/**
	 * 根据资源组id
	 * @param groupId 
	 * @return
	 */
	public List<UserGroupPopedom> getPopedomByResourceGroupId(Long resourceGroupId) {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		List<UserGroupPopedom> ups = session.createCriteria(UserGroupPopedom.class).add(Restrictions.eq("resourceGroupId", resourceGroupId)).list();
		transaction.commit();
		return ups;
	}

	
	public static void main(String[] args) {
		UserGroupPopedomDAO dao = new UserGroupPopedomDAO();
		dao.deleteBatchByUserGroup(1l);
	}
}
