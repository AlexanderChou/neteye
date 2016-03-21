package com.user.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.base.model.Department;
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
 * 部门管理
 * @author 李宪亮
 *
 */
public class DepartmentDAO {
	
	/**
	 * 保存或修改部门
	 * @param department
	 * @return
	 */
	public Department save(Department department){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(department);
		transaction.commit();
		return department;
	}
	
	/**
	 * 删除部门 
	 * @param department
	 * @return
	 */
	public Department delete(Department department) {
		//先更新user中的信息
		UserDAO userDao = new UserDAO();
		userDao.updateUserDepartment(department.getId());
		
		//再删除
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.delete(department);
		transaction.commit();
		return department;
	}
	
	/**
	 * 得到所有的部门列表
	 * @return
	 */
	public List<Department> getAllDepartment(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Department> departments = session.createCriteria(Department.class).list();
		transaction.commit();
		return departments;
	}
	
	/**
	 * 得到所有的部门列表
	 * @return
	 */
	public List<Department> getAllDepartment(String firstReslt, String maxResult){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Department> departments = session.createCriteria(Department.class).setFirstResult(Integer.parseInt(firstReslt)).setMaxResults(Integer.parseInt(maxResult)).list();
		transaction.commit();
		return departments;
	}
	
	/**
	 * 由部门名得到 部门
	 * @param departmentName
	 * @return
	 */
	public Department getDepartmentByName(String departmentName) {
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    Transaction transaction = session.beginTransaction();
	    Department department = (Department)session.createCriteria(Department.class).add(Restrictions.eq("name", departmentName)).uniqueResult();
	    transaction.commit();
	    return department;
	}
	
	/**
	 * 有id得到部门
	 * @param id
	 * @return
	 */
	public Department getDepartmentById(long id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Department department = (Department)session.load(Department.class, id);
		if(!Hibernate.isInitialized(department)) {
			Hibernate.initialize(department);
		}
		transaction.commit();
		return department;
	}
	
	/**
	 * 得到所有部门的个数
	 * @return
	 */
	public int getDepartmentCount(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		int count = (Integer)session.createCriteria(Department.class).setProjection(Projections.rowCount()).uniqueResult();
		transaction.commit();
		return count;
	}
	
	/**
	 * 判断名字是否为空
	 * @param departmentName
	 * @return 为空返回为 true
	 */
	public boolean checkDepartmentNameIsHave(String departmentName) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Department> list = session.createCriteria(Department.class).add(Restrictions.eq("name", departmentName)).list();
		transaction.commit();
		return !list.isEmpty();
	}
	
}
