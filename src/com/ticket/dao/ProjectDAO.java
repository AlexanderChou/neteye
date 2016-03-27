package com.ticket.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.base.model.Category;
import com.base.model.Project;
import com.base.util.HibernateUtil;

public class ProjectDAO {
	private HibernateUtil hibernateUtil = new HibernateUtil();
	/**
	 * 获得规定数目的项目记录
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	public List<Project> getProjects(String firstResult, String maxResult) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		String sql = "select * from ticket_project  limit " + firstResult + "," + maxResult ;
		List<Project> projects = session.createSQLQuery(sql).addEntity(Project.class).list();
		transaction.commit();
		return projects;
	}
	/**
	 * 获得所有项目的记录数目
	 * @return
	 */
	public int getProjectsCount(){
		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		int count = (Integer)session.createCriteria(Project.class).setProjection(Projections.rowCount()).uniqueResult();
		transaction.commit();
		return count;
	}
	public void delete(Long projectId) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Project project = (Project)session.get(Project.class, projectId);
		if(project!=null){
			session.delete(project);
		}
		transaction.commit();
	}
	public Project getProjectById(long id) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Project project = (Project)session.get(Project.class, id);
		transaction.commit();
		return project;
	}
	public boolean checkProjectNameIsExist(String projectName){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Project> projects = session.createCriteria(Project.class).add(Restrictions.eq("name", projectName)).list();
		transaction.commit();
		return !projects.isEmpty();
	}
	public Project save(Project project) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(project);
		transaction.commit();
		return project;
	}
	public static void main(String[] args) {
		ProjectDAO dao = new ProjectDAO();
		List<Project> list = dao.getProjects("0", "10");
		System.out.println(""+list.size());
	}
	/**
	 * 事件平台生成ticket时调用
	 * @param projectName
	 * @return
	 */
	public Project getProjectByName(String projectName){
		Project project = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Project> projects = session.createCriteria(Project.class).add(Restrictions.eq("name", projectName)).list();
		if(projects!=null && projects.size()>0){
			project = projects.get(0);
		}else{
			project = new Project();
			project.setDescription(projectName);
			project.setName(projectName);
			session.save(project);
		}
		transaction.commit();
		return project;
	}
}
