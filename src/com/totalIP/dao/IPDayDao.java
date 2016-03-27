package com.totalIP.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.base.model.NodeIPDayNum;
import com.base.model.NodeIPMonthNum;
import com.base.model.NodeIPWeekNum;
import com.base.util.HibernateUtil;
import com.totalIP.dto.Name;
import com.totalIP.dto.NodeOneDateNum;
import com.totalIP.dto.NodeTotalDateNum;
import com.totalIP.dto.TotalNodeDayIPNum;
import com.totalIP.util.NodeUtil;
import com.totalIP.util.TotalIPDateUtil;

public class IPDayDao {
	/**
	 * @param TotalIPDayNum
	 * @return
	 */
	public NodeIPDayNum save(NodeIPDayNum TotalIPDayNum) {
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.save(TotalIPDayNum);
		transaction.commit();
		return TotalIPDayNum;
	}
	
	/**根据日期和英文名称获取当天的访问的IP个数
	 * @param dateStr日期"20121101"
	 * @return
	 */
	public NodeIPDayNum readNodeDayIPNum(String dateStr,String engName){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<NodeIPDayNum> TotalIPDayNumList =  session.createCriteria(NodeIPDayNum.class).add(Restrictions.eq("day",dateStr)).add(Restrictions.eq("engName", engName)).list();
		transaction.commit();
		NodeIPDayNum dayNum;
		if(TotalIPDayNumList!=null&&TotalIPDayNumList.size()!=0){
			dayNum = TotalIPDayNumList.get(0);
		}else{
			dayNum = new NodeIPDayNum();
			dayNum.setEngName(engName);
			dayNum.setIPNum(0);
			dayNum.setDay(dateStr);
			dayNum.setGroupName(engName.split("_")[0]);
		}
		return dayNum;
	}
	/**根据日期和英文名称获取某节点近7天访问个数
	 * @param dateStr
	 * @return
	 */
	public List<NodeIPDayNum> readNode15DayIPNum(String dateStr,String engName){
		if(dateStr==null||dateStr.trim().length()==0){
			 dateStr = TotalIPDateUtil.getCurrentDay();
		}
		List<NodeIPDayNum> totalNodeWeekIPNumList = new ArrayList<NodeIPDayNum>();
		DateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		try {
			Date date = format1.parse(dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(calendar.DATE,-14);
			for(int i=0;i<14;i++){
				date=calendar.getTime(); 
				dateStr= format1.format(date);
				calendar.add(calendar.DATE,1);
				NodeIPDayNum IPNum = null;
				IPNum = readNodeDayIPNum(dateStr,engName);
				if(IPNum==null){
					IPNum = new NodeIPDayNum();
					IPNum.setDay(dateStr);
					IPNum.setEngName(engName);
					IPNum.setIPNum(0);
				}
				totalNodeWeekIPNumList.add(IPNum);
				
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return totalNodeWeekIPNumList;
	}
	/**根据日期获取每个节点的某一小时访问的ip个数
	 * @param dateStr"2012010123"
	 * @return
	 */
	public List<NodeOneDateNum> readEveryNodeOneDayIPNum(String dateStr){
		List<NodeOneDateNum> TotalIPDayNumList = new ArrayList<NodeOneDateNum>();
		NodeIPDayNum totalDayIP =null;
		List<Name> nameList = NodeUtil.getNodeList();
		for(int i=0;i<nameList.size();i++){
			totalDayIP = new NodeIPDayNum();
			NodeOneDateNum nodeOneDayNum =new NodeOneDateNum();
			totalDayIP = readNodeDayIPNum(dateStr,nameList.get(i).getEngName());
			nodeOneDayNum.setChineseName(nameList.get(i).getChineseName());
			if(totalDayIP==null){
				nodeOneDayNum.setIPNum(0);
			}else {
				nodeOneDayNum.setIPNum(totalDayIP.getIPNum());
			}
			nodeOneDayNum.setEngName(nameList.get(i).getEngName());
			TotalIPDayNumList.add(nodeOneDayNum);
		}
		return TotalIPDayNumList;
	}
	
	/**根据日期和英文名称获取某节点当周的访问的IP个数
	 * @param dateStr
	 * @return
//	 */
//	public List<NodeIPDayNum> readNodeWeekIPNum(String dateStr,String engName){
//		if(dateStr==null||dateStr==""){
//			 dateStr = TotalIPDateUtil.getCurrentDay();
//		}
//		List<NodeIPDayNum> TotalIPDayNumList = new ArrayList<NodeIPDayNum>();
//		DateFormat format1 = new SimpleDateFormat("yyyyMMdd");
//		try {
//			Date date = format1.parse(dateStr);
//			Calendar calendar = Calendar.getInstance();
//			calendar.setTime(date);
//			int dayOfWeek = calendar.get(calendar.DAY_OF_WEEK);
//			if(dayOfWeek!=1){
//				dayOfWeek--;
//			}else{
//				dayOfWeek=7;
//			}
//			while(dayOfWeek>0){
//				TotalIPDayNumList.add(readNodeDayIPNum(dateStr,engName));
//				calendar.add(calendar.DATE,-1);
//				date=calendar.getTime(); 
//				String s= format1.format(date);
//				dateStr = s;
//				dayOfWeek--;
//			}
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return TotalIPDayNumList;
//		
//	}
	
	
	
	
	
	/**根据日期获取当天的所有节点总的访问个数
	 * @param dateStr
	 * @return
	 */
	public String readTotalNodeDayIPNum(String dateStr ){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select sum(IPNum) as totalIPNum from totalip_ipnum_day where day="+dateStr;
		String totalIPNum = (String) session.createSQLQuery(sql).addScalar("totalIPNum", Hibernate.STRING).uniqueResult();
		transaction.commit();
		if(totalIPNum==null||totalIPNum.trim().length()==0){
			totalIPNum = "0";
		}
		return totalIPNum;
	}
	/**根据开始日期和结束日期获取近7天每天的所有节点总的访问个数
	 * 首页折线图(14天)
	 * @param dateStr
	 * @return
	 */
	public List<Object[]>  readTotalNode14DayIPNum(String startDate,String endDate){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select sum(day.IPNum) ,day.day FROM NodeIPDayNum day where day.day>='"+startDate+"' and day.day<'"+endDate+"' group by day.day";
		List<Object[]> IPNumList =  session.createQuery(sql).list();
		transaction.commit();
		return IPNumList;
	}
//	public List<TotalNodeDayIPNum> readTotalNode7DayIPNum(String dateStr){
//		if(dateStr==null||dateStr==""){
//			 dateStr = TotalIPDateUtil.getCurrentDay();
//		}
//		List<TotalNodeDayIPNum> totalNodeWeekIPNumList = new ArrayList<TotalNodeDayIPNum>();
//		DateFormat format1 = new SimpleDateFormat("yyyyMMdd");
//		try {
//			Date date = format1.parse(dateStr);
//			Calendar calendar = Calendar.getInstance();
//			calendar.setTime(date);
//			calendar.add(calendar.DATE,-7);
//			for(int i=0;i<7;i++){
//				date=calendar.getTime(); 
//				dateStr= format1.format(date);
//				TotalNodeDayIPNum totalNodeDayIPNum = new TotalNodeDayIPNum();
//				totalNodeDayIPNum.setDay(dateStr);
//				totalNodeDayIPNum.setIPNum(readTotalNodeDayIPNum(dateStr));
//				totalNodeWeekIPNumList.add(totalNodeDayIPNum);
//				calendar.add(calendar.DATE,1);
//			}
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return totalNodeWeekIPNumList;
//	}
	/**根据日期获取节点一个月的总的访问个数
	 * @param dateStr
	 * @return
	 */
	public String readNodeMonthIPNum(String dateStr,String engName){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select sum(IPNum) as totalIPNum from totalip_ipnum_day where serviced='"+engName+"'and day like  '"+dateStr+"%'";
		String totalIPNum = (String) session.createSQLQuery(sql).addScalar("totalIPNum", Hibernate.STRING).uniqueResult();
		transaction.commit();
		if(totalIPNum==null||totalIPNum.trim().length()==0){
			totalIPNum ="0";
		}
		return totalIPNum;
	}
	/**根据日期获取上月的每个节点的访问个数
	 * @param dateStr
	 * @return
	 */
	public List<NodeIPMonthNum> readEveryNodeMonthIPNum(String dateStr){
		List<Name> nameList = NodeUtil.getNodeList(); 
		List<NodeIPMonthNum> totalNodeMonthIPNumList = new ArrayList<NodeIPMonthNum>();
		for (Name name : nameList) {
			NodeIPMonthNum MonthIPNum = new NodeIPMonthNum();
			String IPNum= readNodeMonthIPNum(dateStr,name.getEngName());
			if(IPNum!=null&&IPNum.trim().length()!=0){
				MonthIPNum.setIPNum(Integer.valueOf(IPNum));
			}else{
				MonthIPNum.setIPNum(0);
			}
			MonthIPNum.setMonth(dateStr);
			MonthIPNum.setEngName(name.getEngName());
			MonthIPNum.setGroupName(name.getGroupId());
			totalNodeMonthIPNumList.add(MonthIPNum);
		}
		return totalNodeMonthIPNumList;
	}
	/**根据日期获取节点上一周的IP地址个数
	 * @param dateStr
	 * @return
	 */
	public String readNodeWeekIPNum(String StartDate,String endDate,String engName ){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select sum(IPNum) as totalIPNum from totalip_ipnum_day where serviced='"+engName+"'and day>="+StartDate+" and day<"+endDate;
		String totalIPNum = (String) session.createSQLQuery(sql).addScalar("totalIPNum", Hibernate.STRING).uniqueResult();
		transaction.commit();
		if(totalIPNum==null||totalIPNum.trim().length()==0){
			totalIPNum ="0";
		}
		return totalIPNum;
	}
	/**根据日期获取上周的每个节点的IP地址个数
	 * @param startDate
	 * @return
	 */
	public List<NodeIPWeekNum> readEveryNodeWeekIPNum(String startDate,String endDate){
		List<Name> nameList = NodeUtil.getNodeList(); 
		List<NodeIPWeekNum> totalNodeWeekIPNumList = new ArrayList<NodeIPWeekNum>();
			for (Name name : nameList) {
				NodeIPWeekNum weekIPNum = new NodeIPWeekNum();
				String IPNum= readNodeWeekIPNum(startDate,endDate,name.getEngName());
				if(IPNum!=null&&IPNum.trim().length()!=0){
					weekIPNum.setIPNum(Integer.valueOf(IPNum));
				}else{
					weekIPNum.setIPNum(0);
				}
				weekIPNum.setWeek(startDate);
				weekIPNum.setEngName(name.getEngName());
				weekIPNum.setGroupName(name.getGroupId());
				totalNodeWeekIPNumList.add(weekIPNum);
			}
		return totalNodeWeekIPNumList;
	}
//	public NodeTotalDateNum readNodeTotal7DayIPNum(String StartDate,String endDate,String engName){
//		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//		Transaction transaction = session.beginTransaction();
//		String sql = "select sum(IPNum) as totalIPNum from totalip_ipnum_day where serviced='"+engName+"'and day>="+StartDate+" and day<="+endDate;
//		String IPNum = (String) session.createSQLQuery(sql).addScalar("totalIPNum", Hibernate.STRING).uniqueResult();
//		transaction.commit();
//		NodeTotalDateNum nodeTotalDateNum = new NodeTotalDateNum();
//		nodeTotalDateNum.setDateStr(endDate);
//		if(IPNum!=null&&IPNum.trim().length()!=0){
//			nodeTotalDateNum.setIPNum(Integer.parseInt(IPNum));
//		}else{
//			nodeTotalDateNum.setIPNum(0);
//		}
//		
//		nodeTotalDateNum.setEngName(engName);
//		return nodeTotalDateNum;
//	}
	/**根据开始日期和结束日期和英文名称获取某节点近7天所有天的访问个数
	 * @param dateStr
	 * 首页饼图(7天)
	 * @param engName
	 * @return
	 */
	public List<Object[]> read25Node7DayIPNum(String startDate,String endDate){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select sum(day.IPNum) ,day.groupName FROM NodeIPDayNum day where day.day>='"+startDate+"' and day.day<'"+endDate+"' group by day.groupName";
		List<Object[]> IPNumList =  session.createQuery(sql).list();
		transaction.commit();
		return IPNumList;
	}
	/**根据日期获取每个节点的近7天所有天总访问的ip个数
	 * 首页饼图(7天)
	 * @param dateStr"2012010123"
	 * @return
	 */
	public List<NodeTotalDateNum> read25Node7Day(String dateStr){
		if(dateStr==null||dateStr.trim().length()==0){
			 dateStr = TotalIPDateUtil.getCurrentDay();
		}
		String startdate;
		String endDate;
		DateFormat format2 = new SimpleDateFormat("yyyyMMdd");
		Date date = null; 
		try {
			date = format2.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		 calendar.setTime(date);
		 date = calendar.getTime();
		 endDate = format2.format(date);
		 calendar.add(calendar.DATE, -7);
		 date = calendar.getTime();
		 startdate = format2.format(date);
		List<NodeTotalDateNum> TotalIPDayNumList = new ArrayList<NodeTotalDateNum>();
		NodeTotalDateNum dto =null;
		Map<String,Name> nameList = NodeUtil.getCernetNameNode();
		List<Object[]> IPNumList = read25Node7DayIPNum(startdate, endDate);
		String nodeName = "";
		for(int i=0;i<IPNumList.size();i++){
			dto = new NodeTotalDateNum();
			dto.setIPNum(Integer.valueOf(IPNumList.get(i)[0].toString()));
			nodeName = IPNumList.get(i)[1].toString();
			dto.setEngName(nodeName);
			if(nameList.containsKey(nodeName)){
				dto.setChineseName(nameList.get(nodeName).getChineseName());
			}
			dto.setDateStr(endDate);
			TotalIPDayNumList.add(dto);
		}
		return TotalIPDayNumList;
	}
	
	public static void main(String[] args) {
		IPDayDao dao = new IPDayDao();
		List<NodeIPDayNum> totalIPDayNumList = new ArrayList<NodeIPDayNum>();
		List<TotalNodeDayIPNum> totalNodeWeekIPNumList = new ArrayList<TotalNodeDayIPNum>();
		List<Object[]> totalNodeWeekIPNumList2 = new ArrayList<Object[]>();
		List<NodeTotalDateNum> everyNodeTotalHourIP = new ArrayList<NodeTotalDateNum>();
		
		//测试获取上一月每个节点IP地址数
//		 List<NodeIPMonthNum> nodeTotalDateNumList = new ArrayList<NodeIPMonthNum>();
//		 nodeTotalDateNumList=dao.readEveryNodeMonthIPNum("20121101");
//		for (NodeIPMonthNum nodeIPWeekNum : nodeTotalDateNumList) {
//			System.out.println(nodeIPWeekNum.getEngName());
//			System.out.println(nodeIPWeekNum.getIPNum());
//			System.out.println(nodeIPWeekNum.getMonth());
//		}
		//测试获取一周每个节点IP地址数
//		 List<NodeIPWeekNum> nodeTotalDateNumList = new ArrayList<NodeIPWeekNum>();
//		 nodeTotalDateNumList=dao.readEveryNodeWeekIPNum("20121112");
//		for (NodeIPWeekNum nodeIPWeekNum : nodeTotalDateNumList) {
//			System.out.println(nodeIPWeekNum.getEngName());
//			System.out.println(nodeIPWeekNum.getIPNum());
//			System.out.println(nodeIPWeekNum.getWeek());
//		}
		
		
		
		//测试获取每个节点7天所有天的总的
		everyNodeTotalHourIP = dao.read25Node7Day("20121124");
		for (NodeTotalDateNum nodeTotalDateNum : everyNodeTotalHourIP) {
			System.out.println(nodeTotalDateNum.getChineseName());
			System.out.println(nodeTotalDateNum.getDateStr());
			System.out.println(nodeTotalDateNum.getEngName());
			System.out.println(nodeTotalDateNum.getIPNum());
		}
		//测试获取7天所有节点和
//		totalNodeWeekIPNumList = dao.readTotalNode7DayIPNum("20121107");
//		for (TotalNodeDayIPNum totalNodeDayIPNum : totalNodeWeekIPNumList) {
//			System.out.println(totalNodeDayIPNum.getDay());
//			System.out.println(totalNodeDayIPNum.getIPNum());
//		}
		totalNodeWeekIPNumList2 = dao.readTotalNode14DayIPNum("20121117","20121124");
		for (Object[] totalNodeDayIPNum : totalNodeWeekIPNumList2) {
			System.out.println(totalNodeDayIPNum[1].toString());
			System.out.println(totalNodeDayIPNum[0].toString());
		}
		//测试获取某天所有节点和
//		String totalNodeDayIPNum = dao.readTotalNodeDayIPNum("20121201");
//		System.out.println(totalNodeDayIPNum);

//		测试获取某天某节点
//		NodeIPDayNum nodeIPDayNum = dao.readNodeDayIPNum("20121209", "thu");
//		System.out.println(nodeIPDayNum.getDay());
//		System.out.println(nodeIPDayNum.getEngName());
//		System.out.println(nodeIPDayNum.getIPNum());
		//测试获取7天某节点
//		totalIPDayNumList = dao.readNode7DayIPNum("", "thu");
//		for (NodeIPDayNum nodeIPDayNum : totalIPDayNumList) {
//			System.out.println(nodeIPDayNum.getEngName());
//			System.out.println(nodeIPDayNum.getDay());
//			System.out.println(nodeIPDayNum.getIPNum());
//		}
//		
		
		
		
		//生成测试数据
//		List<Name> nameList = NodeUtil.getNodeList();
//			for (int j = 0; j < nameList.size(); j++) {
//				String name = nameList.get(j).getEngName();
//				for(int i=1;i<10;i++){
//					NodeIPDayNum totalIPDayNum = new NodeIPDayNum();
//					totalIPDayNum.setDay("2012110"+i);
//					totalIPDayNum.setEngName(name);
//					totalIPDayNum.setIPNum(15440+i*1924);
//					dao.save(totalIPDayNum);
//			}
//		}
	}
}
