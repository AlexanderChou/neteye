package com.config.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.base.model.DicType;
import com.base.util.Page;

/**
 * 
 * @author ChenYun
 * 
 */
public class DicTypeDao {

	/**
	 * 新增基本数据
	 * 
	 * @param dicType
	 * @return
	 * @throws Exception
	 */
	public boolean addDicType(DicType dicType, Session session)
			throws Exception {
		Object obj = session.save(dicType);
		return obj != null ? true : false;
	}

	/**
	 * 删除基本数据
	 * 
	 * @param dicTypeId
	 * @return
	 * @throws Exception
	 */
	public void deleteDicType(DicType dicType, Session session)
			throws Exception {
		session.delete(dicType);
	}

	/**
	 * 通过基本数据Id获得基本数据对象
	 * 
	 * @param dicTypeId
	 * @return
	 * @throws Exception
	 */
	public DicType getByDicTypeId(String dicTypeId, Session session)
			throws Exception {
		DicType dicType = (DicType) session.get(DicType.class, dicTypeId);
		return dicType;
	}

	/**
	 * 跟新基本数据<br>
	 * 
	 * @param dicType
	 * @param session
	 * @throws Exception
	 */
	public void update(DicType dicType, Session session) throws Exception {
		session.update(dicType);
	}

	/**
	 * 获得所有的数据字典<br>
	 * 
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DicType> getAll(Session session) throws Exception {
		String hql = "from DicType dictype";
		Query query = session.createQuery(hql);
		return query.list();
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
	public List<DicType> getAll(Page page, DicType dicType, Session session)
			throws Exception {
		StringBuffer buf = new StringBuffer("from DicType dictype where 1=1");
		String hql = getQueryHql(buf, dicType);
		Query query = session.createQuery(hql);
		setParameter(query, dicType);
		query.setFirstResult(page.getRecordIndex());
		query.setMaxResults(page.getPageSize());
		return query.list();
	}

	private void setParameter(Query query, DicType dicType) {
		if (dicType != null) {
			String dicTypeId = dicType.getDicTypeId();
			String dicTypeName = dicType.getDicTypeName();
			int num=0;
			if (null != dicTypeId && !"".equals(dicTypeId)) {

				query.setString("dicTypeId", "%"+dicTypeId+"%");

			}
			if (null != dicTypeName && !"".equals(dicTypeName)) {

				query.setString("dicTypeName", "%"+dicTypeName+"%");

			}
		}
	}

	private String getQueryHql(StringBuffer buf, DicType dicType) {
		if (dicType != null) {
			String dicTypeId = dicType.getDicTypeId();
			String dicTypeName = dicType.getDicTypeName();
			if (null != dicTypeId && !"".equals(dicTypeId)) {

				buf.append(" and dictype.dicTypeId like :dicTypeId");

			}
			if (null != dicTypeName && !"".equals(dicTypeName)) {

				buf.append(" and dictype.dicTypeName like :dicTypeName");

			}
		}
		return buf.toString();
	}

	/**
	 * 获得数据字典的数据<br>
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int countDicType(DicType dicType, Session session) throws Exception {
		StringBuffer buf = new StringBuffer(
				"select count(*) from DicType dictype where 1=1");
		String hql = getQueryHql(buf, dicType);
		Query query = session.createQuery(hql);
		setParameter(query, dicType);
		List list = query.list();
		if (list != null && list.size() > 0) {
			return Integer.valueOf(list.get(0) + "");
		}
		return 0;
	}

}
