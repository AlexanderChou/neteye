package com.config.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.base.model.DicContent;
import com.base.util.Page;

/**
 * 基础数据详情<br>
 * 
 * @author ChenYun
 * 
 */
public class DicContentDao{

	/**
	 * 新增基础数据详情<br>
	 * 
	 * @param dicContent
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public boolean addDicContent(DicContent dicContent, Session session)
			throws Exception {
		Object obj = session.save(dicContent);
		return obj != null ? true : false;
	}

	/**
	 * 删除基础数据详情<br>
	 * 
	 * @param dicContent
	 * @param session
	 * @throws Exception
	 */
	public void deleteDicContent(DicContent dicContent, Session session)
			throws Exception {
		session.delete(dicContent);
	}

	/**
	 * 通过字典类型删除字典详细表<br>
	 * 
	 * @param dicTypeId
	 * @throws Exception
	 */
	public void deleteByDicTypeId(String dicTypeId, Session session)
			throws Exception {
		String sql = "DELETE FROM ct_dicContent where dicTypeId='" + dicTypeId
				+ "'";
		session.createSQLQuery(sql).executeUpdate();
	}

	/**
	 * 通过主键Id获得基础数据详情对象<br>
	 * 
	 * @param dicContentId
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public DicContent getByDicContentId(Long dicContentId, Session session)
			throws Exception {
		DicContent dicContent = (DicContent) session.get(DicContent.class,
				dicContentId);
		return dicContent;
	}

	/**
	 * 获得基础数据Id获得基础数据详情列表<br>
	 * 
	 * @param dicTypeId
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DicContent> getByDicTypeId(String dicTypeId, Session session)
			throws Exception {
		StringBuffer sql = new StringBuffer(
				"from DicContent dicContent where dicContent.dicTypeId=");
		sql.append("'").append(dicTypeId).append("'");
		List<DicContent> dicContList = session.createQuery(sql.toString())
				.list();
		return dicContList;
	}

	/**
	 * 获得所有的基础数据详情对象<br>
	 * 
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DicContent> getAll(Session session) throws Exception {
		String sql = "SELECT * FROM CT_DICTYPE";
		List<DicContent> list = session.createSQLQuery(sql).list();
		return list;
	}

	public List<DicContent> getAll(Page page, DicContent dicContent,
			Session session) {
		StringBuffer buf = new StringBuffer(
				"from DicContent dicContent where 1=1");
		String hql = getQueryHql(buf, dicContent);
		Query query = session.createQuery(hql);
		setParameter(query, dicContent);
		query.setFirstResult(page.getRecordIndex());
		query.setMaxResults(page.getPageSize());
		return query.list();
	}

	private String getQueryHql(StringBuffer buf, DicContent dicContent) {
		if (dicContent != null) {
			String dicTypeId = dicContent.getDicTypeId();
			String dicContentName = dicContent.getDicContentName();
			String[] dicTypeIdArr = dicTypeId.split(",");
			String passdicTypeId = dicTypeIdArr[0]; // 跟在 url后面的
			String searchDicTypeId = dicTypeIdArr[1].trim();// 表单查询的
			if (null != passdicTypeId && !"".equals(passdicTypeId)
					&& !"null".equals(passdicTypeId) && null != searchDicTypeId
					&& !"".equals(searchDicTypeId)
					&& !"null".equals(searchDicTypeId)) {
				buf.append(" and dicContent.dicTypeId='" + searchDicTypeId
						+ "'");
			} else if (null != passdicTypeId && !"".equals(passdicTypeId)
					&& !"null".equals(passdicTypeId)
					&& (null == searchDicTypeId || "".equals(searchDicTypeId))) {
				buf.append(" and dicContent.dicTypeId='" + passdicTypeId + "'");
			} else if (null != searchDicTypeId && !"".equals(searchDicTypeId)
					&& !" ".equals(searchDicTypeId)) {
				buf.append(" and dicContent.dicTypeId='" + searchDicTypeId
						+ "'");
			}
			if (null != dicContentName && !"".equals(dicContentName)) {
				buf.append(" and dicContent.dicContentName like:dicContentName");
			}
		}
		return buf.toString();
	}

	private void setParameter(Query query, DicContent dicContent) {
		if (dicContent != null) {
			String dicContentName = dicContent.getDicContentName();
			if (null != dicContentName && !"".equals(dicContentName)) {
				query.setString("dicContentName", "%"+dicContentName+"%");
			}
		}
	}

	/**
	 * 获得数据字典的数据<br>
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int countDicContent(DicContent dicContent, Session session)
			throws Exception {
		StringBuffer buf = new StringBuffer(
				"select count(*) from DicContent dicContent where 1=1");
		String hql = getQueryHql(buf, dicContent);
		Query query = session.createQuery(hql);
		setParameter(query, dicContent);
		List list = query.list();
		if (list != null && list.size() > 0) {
			return Integer.valueOf(list.get(0) + "");
		}
		return 0;
	}

	/**
	 * 跟新数据字典详情对象<br>
	 * 
	 * @param dicContent
	 * @param session
	 * @throws Exception
	 */
	public void updateDicContent(DicContent dicContent, Session session)
			throws Exception {
		session.update(dicContent);
	}

}
