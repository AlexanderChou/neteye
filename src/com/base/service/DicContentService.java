package com.base.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.base.model.DicContent;
import com.base.util.HibernateUtil;
import com.base.util.Page;
import com.config.dao.DicContentDao;

public class DicContentService {
	private DicContentDao dicContentDao = new DicContentDao();

	/**
	 * 新增数据字典内容项<br>
	 * 
	 * @param dicContent
	 * @return
	 * @throws Exception
	 */
	public boolean addDicContent(DicContent dicContent) throws Exception {
		boolean flag = false;
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		flag = dicContentDao.addDicContent(dicContent, session);
		transaction.commit();
		return flag;
	}

	/**
	 * 删除数据字典内容项<br>
	 * 
	 * @param dicTypeIds
	 * @throws Exception
	 */
	public void deleteDicContent(Long[] dicTypeIds) throws Exception {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		for (int i = 0; i < dicTypeIds.length; i++) {
			DicContent dicContent = dicContentDao.getByDicContentId(
					dicTypeIds[i], session);
			dicContentDao.deleteDicContent(dicContent, session);
		}
		transaction.commit();
	}

	/**
	 * 通过基本数据Id获得数据字典内容项<br>
	 * 
	 * @param dicTypeId
	 * @return
	 * @throws Exception
	 */
	public DicContent getByDicTypeId(Long dicContentId) throws Exception {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		DicContent dicContent = dicContentDao.getByDicContentId(dicContentId,
				session);
		transaction.commit();
		return dicContent;
	}

	/**
	 * 跟新数据字典内容项<br>
	 * 
	 * @param dicType
	 * @param session
	 * @throws Exception
	 */
	public void update(DicContent dicContent) throws Exception {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		DicContent oldDicContent = dicContentDao.getByDicContentId(
				dicContent.getDicContenId(), session);
		oldDicContent.setDicContentDesc(dicContent.getDicContentDesc());
		oldDicContent.setDicContentName(dicContent.getDicContentName());
		oldDicContent.setDicContentOrder(dicContent.getDicContentOrder());
		dicContentDao.updateDicContent(oldDicContent, session);
		transaction.commit();
	}

	/**
	 * 获得所有的数据字典内容项<br>
	 * 
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DicContent> getAll() throws Exception {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		List<DicContent> list = dicContentDao.getAll(session);
		transaction.commit();
		return list;
	}

	/**
	 * 获得所有的数据字典内容(分页)<br>
	 * 
	 * @param firstResult
	 * @param maxResult
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DicContent> getAll(Page page, DicContent dicContent)
			throws Exception {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		List<DicContent> list = dicContentDao.getAll(page, dicContent, session);
		int totalCount = dicContentDao.countDicContent(dicContent, session);
		page.setTotalRecords(totalCount);
		transaction.commit();
		return list;
	}

	/**
	 * 通过数据字典Id获得详情列表<br>
	 * 
	 * @param dicTypeId
	 * @return
	 * @throws Exception
	 */
	public List<DicContent> getByDicTypeId(String dicTypeId) throws Exception {
		if (dicTypeId == null || dicTypeId.equals("")) {
			throw new IllegalArgumentException("dicType is null");
		}
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		List<DicContent> dicContentList = dicContentDao.getByDicTypeId(
				dicTypeId, session);
		transaction.commit();
		return dicContentList;
	}
}
