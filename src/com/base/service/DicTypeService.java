package com.base.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.base.model.DicType;
import com.base.util.HibernateUtil;
import com.base.util.Page;
import com.config.dao.DicContentDao;
import com.config.dao.DicTypeDao;

public class DicTypeService {
	private DicTypeDao dicTypeDao = new DicTypeDao();
	private DicContentDao dicContentDao = new DicContentDao();

	/**
	 * 新增数据字典<br>
	 * 0:表示成功 <br>
	 * 1：表示失败 <br>
	 * 2：表示dicType已存在<br>
	 * 
	 * @param dicType
	 * @return
	 * @throws Exception
	 */
	public int addDicType(DicType dicType) throws Exception {
		int result = 0;
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		// 先查询dicType是否已经存在
		DicType tmp = dicTypeDao
				.getByDicTypeId(dicType.getDicTypeId(), session);
		if (tmp != null) {
			result = 2;
		} else {
			result = dicTypeDao.addDicType(dicType, session) ? 0 : 1;
		}
		transaction.commit();
		return result;
	}

	/**
	 * 删除基本数据<br>
	 * 同时需要把详细表的数据进行删除<br>
	 * 
	 * @param dicTypeId
	 * @return
	 * @throws Exception
	 */
	public void deleteDicType(String[] dicTypeIds) throws Exception {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		for (int i = 0; i < dicTypeIds.length; i++) {
			String dicTypeId = dicTypeIds[i];
			DicType tmp = dicTypeDao.getByDicTypeId(dicTypeId, session);
			dicTypeDao.deleteDicType(tmp, session);
			dicContentDao.deleteByDicTypeId(dicTypeId,session);
		}
		transaction.commit();
	}

	/**
	 * 通过基本数据Id获得基本数据对象
	 * 
	 * @param dicTypeId
	 * @return
	 * @throws Exception
	 */
	public DicType getByDicTypeId(String dicTypeId) throws Exception {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		DicType dicType = dicTypeDao.getByDicTypeId(dicTypeId, session);
		transaction.commit();
		return dicType;
	}

	/**
	 * 跟新基本数据<br>
	 * 
	 * @param dicType
	 * @param session
	 * @throws Exception
	 */
	public void update(DicType dicType) throws Exception {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		DicType oldDicType=dicTypeDao.getByDicTypeId(dicType.getDicTypeId(), session);
		oldDicType.setDicTypeName(dicType.getDicTypeName());
		oldDicType.setDicTypeDesc(dicType.getDicTypeDesc());
		dicTypeDao.update(oldDicType, session);
		transaction.commit();
	}

	/**
	 * 获得所有的数据字典<br>
	 * 
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DicType> getAll() throws Exception {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		List<DicType> list = dicTypeDao.getAll(session);
		transaction.commit();
		return list;
	}

	/**
	 * 获得所有的数据字典(分页)<br>
	 * 
	 * @param firstResult
	 * @param maxResult
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DicType> getAll(Page page, DicType dicType) throws Exception {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		List<DicType> list = dicTypeDao.getAll(page, dicType, session);
		int totalCount = dicTypeDao.countDicType(dicType, session);
		page.setTotalRecords(totalCount);
		transaction.commit();
		return list;
	}

}
