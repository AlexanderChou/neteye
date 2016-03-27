package com.totalIP.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.base.model.NodeIPDayNum;
import com.base.model.NodeIPMonthNum;
import com.base.model.NodeIPWeekNum;
import com.base.util.DateUtil;
import com.base.util.HibernateUtil;
import com.totalIP.dto.Name;
import com.totalIP.dto.NodeOneDateNum;
import com.totalIP.dto.NodeTotalDateNum;
import com.totalIP.dto.TotalNodeMonthIPNum;
import com.totalIP.util.NodeUtil;

public class IPMonthDao {
	/**
	 * @param NodeIPMonthNum
	 * @return
	 */
	public NodeIPMonthNum save(NodeIPMonthNum nodeIPMonthNum) {
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.save(nodeIPMonthNum);
		transaction.commit();
		return nodeIPMonthNum;
	}
	/**根据日期和英文名称获取当月的访问的IP个数
	 * @param dateStr日期"201211"
	 * @return
	 */
	public NodeIPMonthNum readNodeOneMonthIPNum(String dateStr,String engName){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<NodeIPMonthNum> nodeIPMonthNumList =  session.createCriteria(NodeIPMonthNum.class).add(Restrictions.eq("month",dateStr)).add(Restrictions.eq("engName",engName)).list();
		transaction.commit();
		if(nodeIPMonthNumList!=null&&nodeIPMonthNumList.size()>0){
			return nodeIPMonthNumList.get(0);
		}else {
			return null;
		}
	}
	/**根据月日期获取每个节点的某一月访问的ip个数
	 * @param dateStr"2012010123"
	 * @return
	 */
	public List<NodeOneDateNum> readEveryNodeOneMonthIPNum(String dateStr){
		List<NodeOneDateNum> TotalIPMonthNumList = new ArrayList<NodeOneDateNum>();
		NodeIPMonthNum totalMonthIP =null;
		List<Name> nameList = NodeUtil.getNodeList();
		for(int i=0;i<nameList.size();i++){
			totalMonthIP = new NodeIPMonthNum();
			NodeOneDateNum nodeOneMonthNum =new NodeOneDateNum();
			totalMonthIP = readNodeOneMonthIPNum(dateStr,nameList.get(i).getEngName());
			nodeOneMonthNum.setChineseName(nameList.get(i).getChineseName());
			if(totalMonthIP==null){
				nodeOneMonthNum.setIPNum(0);
			}else{
				nodeOneMonthNum.setIPNum(totalMonthIP.getIPNum());
			}
			nodeOneMonthNum.setEngName(nameList.get(i).getEngName());
			TotalIPMonthNumList.add(nodeOneMonthNum);
		}
		return TotalIPMonthNumList;
	}
	/**根据日期和英文名称获取某节点12个月的访问个数
	 * @param dateStr
	 * @param engName
	 * @return
	 */
	public List<NodeIPMonthNum> readNode12MonthIPNum(String dateStr,String engName){
		List<NodeIPMonthNum> TotalIPMonthNumList = new ArrayList<NodeIPMonthNum>();
		int monthOfyear;
		String lastYear;
	
		if(dateStr==null||dateStr==""){
			 DateFormat format = new SimpleDateFormat("yyyyMMdd");
			 Date date2 = new Date();
			 Calendar calendar = Calendar.getInstance();
			 calendar.setTime(date2);
			 if(calendar.get(calendar.DAY_OF_MONTH)==1&&calendar.get(calendar.HOUR_OF_DAY)==0&&calendar.get(calendar.MINUTE)<30){
				 calendar.add(calendar.DATE, -1);
				 date2=calendar.getTime();
			 }
			 dateStr = format.format(date2);
		}
		
		lastYear = (Integer.parseInt(dateStr.substring(0,4))-1)+"";
		monthOfyear = Integer.parseInt(dateStr.substring(4,6));
		
		for(int i=0;i<12;i++){
			NodeIPMonthNum IPNum =null;
			if(monthOfyear<10){
				IPNum = readNodeOneMonthIPNum(lastYear+"0"+monthOfyear,engName);
				if(IPNum==null){
					IPNum = new NodeIPMonthNum();
					IPNum.setEngName(engName);
					IPNum.setMonth(lastYear+"0"+monthOfyear);
					IPNum.setIPNum(0);
				}else{
					TotalIPMonthNumList.add(IPNum);
				}
				monthOfyear++;
			}else if(monthOfyear<12){
				IPNum = readNodeOneMonthIPNum(lastYear+monthOfyear,engName);
				if(IPNum==null){
					IPNum = new NodeIPMonthNum();
					IPNum.setEngName(engName);
					IPNum.setMonth(lastYear+monthOfyear);
					IPNum.setIPNum(0);
				}else{
					TotalIPMonthNumList.add(IPNum);
				}
				monthOfyear++;
			}else{
				IPNum = readNodeOneMonthIPNum(lastYear+monthOfyear,engName);
				if(IPNum==null){
					IPNum = new NodeIPMonthNum();
					IPNum.setEngName(engName);
					IPNum.setMonth(lastYear+monthOfyear);
					IPNum.setIPNum(0);
				}else{
					TotalIPMonthNumList.add(IPNum);
				}
				lastYear=(Integer.parseInt(lastYear)+1)+"";
				monthOfyear=1;
			}
		}
		return TotalIPMonthNumList;
	}
	/**根据日期获取当月的所有节点访问的IP个数的和
	 * @param dateStr"201201"
	 * @return
	 */
	public String readTotalNodeOneMonthIPNum(String dateStr){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select sum(IPNum) as totalIPNum from totalip_ipnum_month where month="+dateStr;
		String totalIPNum = (String) session.createSQLQuery(sql).addScalar("totalIPNum", Hibernate.STRING).uniqueResult();
		transaction.commit();
		if(totalIPNum==null){
			totalIPNum ="0";
		}
		return totalIPNum;
	}
	/**根据开始日期和结束日期获取近12个月每个月的所有节点访问的IP地址数
	 * @param dateStr
	 * @return
	 */
	public List<Object[]> readTotalNode12MonthIPNum(String startDate,String endDate){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select sum(month.IPNum) ,month.month FROM NodeIPMonthNum month where month.month>='"+startDate+"' and month.month<'"+endDate+"' group by month.month";
		List<Object[]> IPNumList =  session.createQuery(sql).list();
		transaction.commit();
		return IPNumList;
	}
	
	
	/**根据日期和英文名称获取某节点近12个月所有月的访问个数
	 * 饼图(12个月)
	 * @param dateStr
	 * @param engName
	 * @return
	 */
	public List<Object[]> read25Node12MonthIPNum(String startDate,String endDate){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select sum(month.IPNum) ,month.groupName FROM NodeIPMonthNum month where month.month>='"+startDate+"' and month.month<'"+endDate+"' group by month.groupName";
		List<Object[]> IPNumList = session.createQuery(sql).list();
		transaction.commit();
		return IPNumList;
	}
	
	
//	public NodeTotalDateNum readNodeTotal12MonthIPNum(String startDate,String endDate,String engName){
//		NodeTotalDateNum nodeTotalDateNum = new NodeTotalDateNum();
//		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//		Transaction transaction = session.beginTransaction();
//		String sql = "select sum(IPNum) as totalIPNum from totalip_ipnum_month where serviced='"+engName+"'and month>="+startDate+" and month<="+endDate;
//		String IPNum = (String) session.createSQLQuery(sql).addScalar("totalIPNum", Hibernate.STRING).uniqueResult();
//		transaction.commit();
//		nodeTotalDateNum.setDateStr(endDate);
//		if(IPNum!=null&&IPNum.trim().length()!=0){
//			nodeTotalDateNum.setIPNum(Integer.valueOf(IPNum));
//		}else{
//			nodeTotalDateNum.setIPNum(0);
//		}
//		nodeTotalDateNum.setEngName(engName);
//		return nodeTotalDateNum;
//	}
	/**根据日期获取每个节点的近12个月所有月总访问的ip个数
	 *饼图(12月) 
	 * @param dateStr"2012010123"
	 * @return
	 */
	
	public List<NodeTotalDateNum> read25Node12Month(String dateStr){
		DateFormat format2 = new SimpleDateFormat("yyyyMM");
		if(dateStr==null||dateStr==""){
			 Date date2 = new Date();
			 Calendar calendar = Calendar.getInstance();
			 calendar.setTime(date2);
			 if(calendar.get(calendar.DAY_OF_MONTH)==1&&calendar.get(calendar.HOUR_OF_DAY)==0&&calendar.get(calendar.MINUTE)<30){
				 calendar.add(calendar.DATE, -1);
				 date2=calendar.getTime();
			 }
			 dateStr = format2.format(date2);
		}
		String startDate;
		String endDate;
		Date date=null;
		try {
			date = format2.parse(dateStr);
		}catch (ParseException e) {
			// TODO: handle exception
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		date=calendar.getTime();
		endDate=format2.format(date);
		calendar.add(calendar.MONTH, -12);
		date=calendar.getTime();
		startDate=format2.format(date);
		List<NodeTotalDateNum> TotalIPHourNumList = new ArrayList<NodeTotalDateNum>();
		NodeTotalDateNum dto =null;
		List<Object[]> IPNumList = read25Node12MonthIPNum(startDate, endDate);
		for(int i=0;i<IPNumList.size();i++){
			dto = new NodeTotalDateNum();
			dto.setIPNum(Integer.valueOf(IPNumList.get(i)[0].toString()));
			dto.setChineseName(IPNumList.get(i)[1].toString());
			dto.setDateStr(endDate);
			TotalIPHourNumList.add(dto);
		}
		return TotalIPHourNumList;
	}
	
	
	/**根据日期和英文名称获取当月每天的访问的IP个数
	 * @param dateStr
	 * @return
	 */
//	public List<NodeIPDayNum> readIPMonthNum(String dateStr,String engName){
//		List<NodeIPDayNum> TotalIPDayNumList = new ArrayList<NodeIPDayNum>();
//		int dayOfMonth;
//		String month;
//		if(dateStr==null||dateStr==""){
//			DateFormat format1 = new SimpleDateFormat("yyyyMMdd");
//			Date date =  new Date();
//			dateStr = format1.format(date);
//		}
//		month = dateStr.substring(0,6);
//		dayOfMonth = Integer.parseInt(dateStr.substring(6));
//		
//			for(int i=1;i<dayOfMonth;i++){
//				if(i<10){
//					TotalIPDayNumList.add(readIPNumByDate(month+"0"+i,engName));
//				}else{
//					TotalIPDayNumList.add(readIPNumByDate(month+i,engName));
//				}
//			}
//		return TotalIPDayNumList;
//	}
	public static void main(String[] args) {
		IPMonthDao dao = new IPMonthDao();
		List<NodeIPDayNum> TotalIPDayNumList = new ArrayList<NodeIPDayNum>();
		List<TotalNodeMonthIPNum> totalNode12MonthIPNum = new ArrayList<TotalNodeMonthIPNum>();
		List<NodeTotalDateNum> everyNodeTotalHourIP = new ArrayList<NodeTotalDateNum>();
		
		//测试获取每个节点12个月总的
		everyNodeTotalHourIP = dao.read25Node12Month("");
		System.out.println(everyNodeTotalHourIP.size());
		for (NodeTotalDateNum nodeTotalDateNum : everyNodeTotalHourIP) {
			System.out.println("getChineseName="+nodeTotalDateNum.getChineseName());
			System.out.println("getDateStr="+nodeTotalDateNum.getDateStr());
			System.out.println("getIPNum="+nodeTotalDateNum.getIPNum());
		}
		//测试读取节点某个月的访问次数
		
//		NodeIPMonthNum nodeIPMonthNum = new NodeIPMonthNum();
//		nodeIPMonthNum = dao.readNodeOneMonthIPNum("201111", "thu");
//		System.out.println(nodeIPMonthNum.getEngName());
//		System.out.println(nodeIPMonthNum.getMonth());
//		System.out.println(nodeIPMonthNum.getIPNum());
//		
		
		//测试读取某节点近12个月访问次数
//		List<NodeIPMonthNum>  nodeIPWeekNumList= new ArrayList<NodeIPMonthNum>();
//		nodeIPWeekNumList = dao.readNode12MonthIPNum("20121131", "thu");
//		for (NodeIPMonthNum nodeIPMonthNum : nodeIPWeekNumList) {
//			System.out.println(nodeIPMonthNum.getEngName());
//			System.out.println(nodeIPMonthNum.getMonth());
//			System.out.println(nodeIPMonthNum.getIPNum());
//		}
		//测试读取某个月所有节点访问次数
//		String nodeIPMonthNum = dao.readTotalNodeOneMonthIPNum("201211");
//		System.out.println(nodeIPMonthNum);
//		
		//测试读取近12个月所有节点访问次数
//		List<Object[]>  totalNode12MonthIPNum2 = dao.readTotalNode12MonthIPNum("201208","201211");
//		for (Object[] totalNodeMonthIPNum : totalNode12MonthIPNum2) {
//			System.out.println(totalNodeMonthIPNum[0]);
//			System.out.println(totalNodeMonthIPNum[1]);
//		}
		//测试饼图
//		List<Object[]>  totalNode12MonthIPNum2 = dao.read25Node12MonthIPNum("201208","201211");
//		for (Object[] totalNodeMonthIPNum : totalNode12MonthIPNum2) {
//			System.out.println(totalNodeMonthIPNum[0]);
//			System.out.println(totalNodeMonthIPNum[1]);
//		}
		//生成测试数据
//		ChangeNameDao changeNameDao = new  ChangeNameDao();
//		List<Name> nameList = changeNameDao.readName();
//		
//		for (int j = 0; j < nameList.size(); j++) {
//			String name = nameList.get(j).getEngName();
//			for(int i=1;i<=2;i++){
//				NodeIPMonthNum totalIPMonthNum = new NodeIPMonthNum();
//				totalIPMonthNum.setMonth("20121"+i);
//				totalIPMonthNum.setEngName(name);
//				totalIPMonthNum.setIPNum(31204+i*1550);
//				dao.save(totalIPMonthNum);
//			}
//		}
	}
}
