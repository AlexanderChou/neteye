package com.totalIP.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.base.model.NodeIPDayNum;
import com.base.model.NodeIPMonthNum;
import com.base.util.DateUtil;
import com.base.util.HibernateUtil;
import com.totalIP.dto.Name;

public class IPYearDao {
	/**
	 * @param NodeIPDayNum
	 * @return
	 */
	public NodeIPMonthNum save(NodeIPMonthNum TotalIPMonthNum) {
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(TotalIPMonthNum);
		transaction.commit();
		return TotalIPMonthNum;
	}
	/**根据日期获取当天的访问的IP个数
	 * @param dateStr日期"20121101"
	 * @return
	 */
	public NodeIPMonthNum readIPNumByDate(String dateStr){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		NodeIPMonthNum TotalIPMonthNum =  (NodeIPMonthNum)session.createCriteria(NodeIPMonthNum.class).add(Restrictions.eq("month",dateStr)).uniqueResult();
		transaction.commit();
		return TotalIPMonthNum;
	}

	/**根据日期和英文名称获取当天的访问的IP个数
	 * @param dateStr日期"20121101"
	 * @return
	 */
	public NodeIPMonthNum readIPNumByDate(String dateStr,String engName){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		NodeIPMonthNum TotalIPMonthNum =  (NodeIPMonthNum)session.createCriteria(NodeIPMonthNum.class).add(Restrictions.eq("month",dateStr)).add(Restrictions.eq("engName",engName)).uniqueResult();
		transaction.commit();
		return TotalIPMonthNum;
	}
	
	/**根据日期和英文名称获取近12个月的访问的IP个数
	 * @param dateStr"20120101"
	 * @return
	 */
	public List<NodeIPMonthNum> readIPMonthNum(String dateStr,String engName){
		List<NodeIPMonthNum> TotalIPMonthNumList = new ArrayList<NodeIPMonthNum>();
		int monthOfyear;
		String lastYear;
		if(dateStr==null||dateStr==""){
			DateFormat format1 = new SimpleDateFormat("yyyyMMdd");
			Date date =  new Date();
			dateStr = format1.format(date);
		}
		
		lastYear = (Integer.parseInt(dateStr.substring(0,4))-1)+"";
		monthOfyear = Integer.parseInt(dateStr.substring(4,6));
		
		for(int i=1;i<13;i++){
			if(monthOfyear<10){
				TotalIPMonthNumList.add(readIPNumByDate(lastYear+"0"+monthOfyear,engName));
				monthOfyear++;
			}else if(monthOfyear<12){
				TotalIPMonthNumList.add(readIPNumByDate(lastYear+monthOfyear,engName));
				monthOfyear++;
			}else{
				TotalIPMonthNumList.add(readIPNumByDate(lastYear+monthOfyear,engName));
				lastYear=(Integer.parseInt(lastYear)+1)+"";
				monthOfyear=1;
			}
		}
		return TotalIPMonthNumList;
	}
	public static void main(String[] args) {
		IPYearDao dao = new IPYearDao();
		
		System.out.println(dao.readIPNumByDate("201210", "southeast").getMonth());
//		List<NodeIPMonthNum> TotalIPMonthNumList = new ArrayList<NodeIPMonthNum>();
//		TotalIPMonthNumList = dao.readIPMonthNum("20121131","southeast");
//		System.out.println(TotalIPMonthNumList.size());
//		for(int i=0;i<TotalIPMonthNumList.size();i++){
//			System.out.println(TotalIPMonthNumList.get(i).getMonth());
//			System.out.println(TotalIPMonthNumList.get(i).getIPNum());
//			System.out.println(TotalIPMonthNumList.get(i).getEngName());
//			
//		}
//		ChangeNameDao changeNameDao = new  ChangeNameDao();
//		List<Name> nameList = changeNameDao.readName();
//		
//		for (int j = 0; j < nameList.size(); j++) {
//			String name = nameList.get(j).getEngName();
//			for(int i=1;i<=9;i++){
//				TotalIPMonthNum totalIPMonthNum = new TotalIPMonthNum();
//				totalIPMonthNum.setMonth("20110"+i);
//				totalIPMonthNum.setEngName(name);
//				totalIPMonthNum.setIPNum("2"+i+"2000");
//				dao.save(totalIPMonthNum);
//			}
//		}
	}
}
