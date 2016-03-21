package com.user.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.base.model.Department;
import com.base.model.UserPojo;
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
 * 用户管理
 * @author 李宪亮
 *
 */
public class UserDAO {
	
	/**
	 * 保存用户
	 * @param user
	 * @return
	 */
	public UserPojo save(UserPojo user) {
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(user);
		transaction.commit();
		return user;
	}
	
	/**
	 * 有用户名得到用户
	 * @param name
	 * @return
	 */
	public UserPojo getUserByName(String name) {
		System.out.println("name="+name);
		UserPojo user =null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			Transaction transaction = session.beginTransaction();
			//UserPojo user = (UserPojo)session.createCriteria(UserPojo.class).add(Restrictions.eq("name", name)).uniqueResult();
			user = (UserPojo)session.createCriteria(UserPojo.class).add(Restrictions.eq("name", name)).uniqueResult();
			
			System.out.println(user.getId() );
			transaction.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return user;
	}
	
	/**
	 * 删除用户
	 * @param user
	 * @return
	 */
	public UserPojo delete(UserPojo user) {
        //批量删除用户权限表用的user记录
		UserPopedomDAO userPopedomDao = new UserPopedomDAO();
		userPopedomDao.deleteBatch(user.getId());
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		user = (UserPojo)session.get(UserPojo.class, user.getId());
		if (user != null) {
			session.delete(user);
			String sql = "delete from user_message where user_id = " + user.getId();
			session.createSQLQuery(sql).executeUpdate();
		}
		transaction.commit();
		return user;
	}
	
	
	/**
	 * 删除部门表时 用户user表中更新部门
	 * @param departmentId
	 */
	public void updateUserDepartment(long departmentId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<UserPojo> users = session.createCriteria(UserPojo.class).add(Restrictions.eq("department.id", departmentId)).list();
		transaction.commit();
		//当删除department表中的记录的时候  也要在user表中对应的department字段给更换掉 这里添加了一个默认的组 即id是 1 在对应的数据库中要有
		for (UserPojo user : users) {
			DepartmentDAO departmentDao = new DepartmentDAO();
			Department department = departmentDao.getDepartmentById(1l);
			/**  如果没有id为默认部门添加一个  */
			if (department == null) {
				department = new Department();
				department.setId(1l);
				department.setName("默认部门");
				departmentDao.save(department);
			}
			user.setDepartment(department);
			save(user);
		}
	}
	
	/**
	 * 由用户id得到User
	 * @param id
	 * @return
	 */
	public UserPojo getUserById(long id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		UserPojo user = (UserPojo)session.load(UserPojo.class, id);
		Hibernate.initialize(user.getDepartment());
		transaction.commit();
		return user;
	}
	
	/**
	 * 分页显示所有用户列表
	 * @param firstResult 起始结果
	 * @param maxResult 最大结果
	 * @return
	 */
	public List<UserPojo> getAllUsers(String firstResult, String maxResult) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		String sql = "select * from user  limit " + firstResult + "," + maxResult ;
		List<UserPojo> users = session.createSQLQuery(sql).addEntity(UserPojo.class).list();
		List<UserPojo> us = new ArrayList<UserPojo>();
		for (int i = 0; i < users.size(); i++) {
			UserPojo u = users.get(i);
			long departmentId = 1;
			if (u.getDepartment() != null) {
				departmentId = u.getDepartment().getId();
			}
			
			String sql2 = "select * from department where id = " + departmentId;
			Department department = (Department)session.createSQLQuery(sql2).addEntity(Department.class).uniqueResult();
			u.setDepartment(department);
			us.add(u);
		}
		transaction.commit();
		return us;
	}
	
	
	public List<UserPojo> getUserList() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<UserPojo> users =session.createCriteria(UserPojo.class).list();
		transaction.commit();
		return users;
	}
	/**
	 * 得到所有记录的列数
	 * @return
	 */
	public int getUsersCount(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		int count = (Integer)session.createCriteria(UserPojo.class).setProjection(Projections.rowCount()).uniqueResult();
		transaction.commit();
		return count;
	}
	
	/**
	 * 检验用户名是否已经存在
	 * @param userName
	 * @return 返回TRUE 说明该用户名存在  
	 */
	public boolean checkUserNameIsExist(String userName){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<UserPojo> users = session.createCriteria(UserPojo.class).add(Restrictions.eq("name", userName)).list();
		transaction.commit();
		return !users.isEmpty();
	}
	
	/**
	 * 批量删除用户权限中某用户信息
	 * @param ids
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean updates(String[] ids,Long userId) throws Exception{
		boolean flag = false;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try{
			String sql = "delete from user_popedom where user_id = " + userId;
			session.createSQLQuery(sql).executeUpdate();
			session.flush();			
			int i=0;
			for (String groupId : ids) {
				if(!groupId.equals("")){
					UserPopedom up = new UserPopedom();
					up.setUserId(userId);
					up.setGroupId(Long.valueOf(groupId));
					session.save(up);					
					i++;
				}
			}
			tx.commit();
			flag = true;
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return flag;
	}
}
