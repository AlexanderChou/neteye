package com.report.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.base.model.FaultHistory;
import com.base.model.Report;
import com.base.util.Constants;
import com.base.util.HibernateUtil;
import com.report.dto.CreatedTempInfo;

public class ReportDAO {
	private String path = Constants.webRealPath + "file/report/";

	public void create(Report report) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(report);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
	}

	public List<Report> getReports(String sql) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<Report> list = session.createQuery(sql).list();
			tx.commit();
			return list;
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
	}

	public List<CreatedTempInfo> getTemplate(String sql) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		List<CreatedTempInfo> vec = new ArrayList<CreatedTempInfo>();
		try {
			tx = session.beginTransaction();
			List<String> list = session.createSQLQuery(sql).list();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					String template = list.get(i).toString();
					File file = new File(path + template + ".jrprint");
					if (file.exists()) {
						CreatedTempInfo createdTempInfo = new CreatedTempInfo();
						createdTempInfo.setCreatedFile(template);
						vec.add(createdTempInfo);
					}
				}
			}
			tx.commit();
			return vec;
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
	}

	public List<Report> getAllTemplate(String sql) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<Report> list = session.createSQLQuery(sql).addEntity(
					Report.class).list();
			tx.commit();
			return list;
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
	}

	public List<FaultHistory> getFaultTime(String IP) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<FaultHistory> list = session
					.createCriteria(FaultHistory.class).add(
							Restrictions.eq("faultIp", IP)).list();
			tx.commit();
			return list;
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
	}
}
