package com.totalIP.dao;

import java.text.DateFormat;
import java.text.MessageFormat;
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

import com.base.model.NodeIPHourNum;
import com.base.util.HibernateUtil;
import com.totalIP.dto.Name;
import com.totalIP.dto.NodeHourIP;
import com.totalIP.dto.NodeOneDateNum;
import com.totalIP.dto.NodeTotalDateNum;
import com.totalIP.dto.TotalNodeHourIPNum;
import com.totalIP.util.NodeUtil;
import com.totalIP.util.TotalIPDateUtil;

/**
 * @author hmg
 *
 */
public class IPHourDao {
	public NodeIPHourNum save(NodeIPHourNum TotalIPHourNum) {
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.save(TotalIPHourNum);
		transaction.commit();
		return TotalIPHourNum;
	}
	/**根据小时和英文名称获取对应的节点当天某一个小时的IP访问个数
	 * @param dateStr 日期加小时 "2012030224"
	 * @param engName 日期加小时 "thu"
	 * @return
	 */
	public NodeIPHourNum readNodeHourIPNum(String dateStr,String engName){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<NodeIPHourNum> TotalIPHourNumList =  session.createCriteria(NodeIPHourNum.class).add(Restrictions.eq("hour",dateStr)).add(Restrictions.eq("engName", engName)).list();
		transaction.commit();
		NodeIPHourNum hourNum = null;
		if(TotalIPHourNumList!=null&&TotalIPHourNumList.size()>0){
			hourNum =  TotalIPHourNumList.get(0);
		}
		return hourNum;
	}
	
	/**
	 * 根据英文名称和小时获取某节点的近12小时每小时IP访问地址个数
	 * @param dateStr
	 * @param engName
	 * @return
	 */
	public List<NodeIPHourNum> readNode24HourIPNum(String dateStr,String engName){
		List<NodeIPHourNum> IPHourNumList = new ArrayList<NodeIPHourNum>();
		String dateStr2 = "";
		Calendar calendar = Calendar.getInstance();
		DateFormat format = new SimpleDateFormat("yyyyMMddHH");
		if(dateStr==null||dateStr==""){
			dateStr=TotalIPDateUtil.getCurrentHour();
		}
		Date date = null;
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calendar.setTime(date);
		calendar.add(calendar.HOUR, -25);
		for(int i=0;i<24;i++){
			calendar.add(calendar.HOUR, 1);
			date = calendar.getTime();
			dateStr2 = format.format(date);
			NodeIPHourNum IPNum = null;
			IPNum = readNodeHourIPNum(dateStr2,engName);
			if(IPNum==null){
				IPNum = new NodeIPHourNum();
				IPNum.setEngName(engName);
				IPNum.setHour(dateStr2);
				IPNum.setIPNum(0);
			}
			IPHourNumList.add(IPNum);
			
		}
		
		return IPHourNumList;
		
	}
	/**
	 * 根据英文名称和小时获取某节点的12小时总的IP访问地址个数
	 * @param dateStr
	 * @param engName
	 * @return
	 */
	public NodeTotalDateNum readNodeTotal12HourIPNum(String startDate,String endDate,String engName){
		NodeTotalDateNum NodeTotalDateNum = new NodeTotalDateNum();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select sum(IPNum) as totalIPNum from totalip_ipnum_hour where serviced='"+engName+"'and hour>="+startDate+" and hour<="+endDate;
		String IPNum = (String) session.createSQLQuery(sql).addScalar("totalIPNum", Hibernate.STRING).uniqueResult();
		transaction.commit();
			NodeTotalDateNum.setDateStr(endDate);
			NodeTotalDateNum.setEngName(engName);
			if(IPNum!=null&&IPNum.trim().length()>0){
				NodeTotalDateNum.setIPNum(Integer.parseInt(IPNum));
			}else {
				NodeTotalDateNum.setIPNum(0);
			}
			
			
		return NodeTotalDateNum;
		
	}
	/**
	 * 根据英文名称和小时获取某节点的12小时总的IP访问地址个数
	 * 首页饼图(12小时)
	 * @param dateStr
	 * @param engName
	 * @return
	 */
	public List<Object[]> read25Node12HourIPNum(String startDate,String endDate){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select sum(hour.IPNum) ,hour.groupName FROM NodeIPHourNum hour where hour.hour>='"+startDate+"' and hour.hour<='"+endDate+"' group by hour.groupName";
		List<Object[]> IPNumList =  session.createQuery(sql).list();
		transaction.commit();
		return IPNumList;
		
	}
	/**根据小时获取每个节点的近12小时所有小时总访问的ip个数
	 * 首页饼图(12小时)
	 * @param dateStr"2012010123"
	 * @return
	 */
	public List<NodeTotalDateNum> read25Node12Hour(String dateStr){
		NodeTotalDateNum dto = null; 
		DateFormat format = new SimpleDateFormat("yyyyMMddHH");
		if(dateStr==null||dateStr.trim().length()==0){
			dateStr=TotalIPDateUtil.getCurrentHour();
		}
		Date date = null; 
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		 calendar.setTime(date);
		 calendar.add(calendar.HOUR, -1);
		 date = calendar.getTime();
		 String dateStr2 = format.format(date);
		 calendar.add(calendar.HOUR, -11);
		 date = calendar.getTime();
		 dateStr = format.format(date);
		List<NodeTotalDateNum> TotalIPHourNumList = new ArrayList<NodeTotalDateNum>();
		NodeTotalDateNum totalHourIP =null;
		Map<String,Name> nameList = NodeUtil.getCernetNameNode();
		List<Object[]> IPNumList = read25Node12HourIPNum(dateStr, dateStr2);
		String nodeName = "";
		for(int i=0;i<IPNumList.size();i++){
			dto = new NodeTotalDateNum();
			dto.setIPNum(Integer.valueOf(IPNumList.get(i)[0].toString()));
			nodeName = IPNumList.get(i)[1].toString();
			dto.setEngName(nodeName);
			dto.setDateStr(dateStr);
			if(nameList.containsKey(nodeName)){
				dto.setChineseName(nameList.get(nodeName).getChineseName());
			}
			TotalIPHourNumList.add(dto);
		}
		return TotalIPHourNumList;
	}
	/**
	 * 根据英文名称和小时获取某节点的当天每个小时的IP访问地址个数
	 * @param dateStr
	 * @param engName
	 * @return
	 */
//	public List<NodeIPHourNum> getHourOfDayIPNum(String dateStr,String engName){
//		//今天的日期
//		String today =dateStr.substring(0, 8);
//		//要查询的日期
//		String dateStr2=today;
//		List<NodeIPHourNum> IPHourNumList = new ArrayList<NodeIPHourNum>();
//		//today今天
//		//要查询的hour小时
//		int hour  =Integer.parseInt(dateStr.substring(8));
//		
//		for(int i=0;i<hour;i++){
//			if(i>=10){
//				dateStr2=today+i;
//				IPHourNumList.add(readNodeHourIPNum(dateStr2,engName));
//			}else if(i>0){
//				dateStr2=today+"0"+i;
//				IPHourNumList.add(readNodeHourIPNum(dateStr2,engName));
//			}else{
//				dateStr2=today+"00";
//				IPHourNumList.add(readNodeHourIPNum(dateStr2,engName));
//			}
//		}
//		return IPHourNumList;
//	}
	
	/**根据小时获取每个节点的近12小时每小时访问的ip个数
	 * @param dateStr"2012010123"
	 * @return
	 */
//	public List<NodeHourIP> readEveryNode12HourIPNum(String dateStr){
//		List<NodeHourIP> TotalIPHourNumList = new ArrayList<NodeHourIP>();
//		ChangeNameDao changeNameDao = new ChangeNameDao();
//		NodeHourIP totalHourIP =null;
//		List<NodeIPHourNum>  IPNum ;
//		List<Name> nameList = changeNameDao.readName();
//		for(int i=0;i<nameList.size();i++){
//			totalHourIP = new NodeHourIP();
//			IPNum = readNode12HourIPNum(dateStr,nameList.get(i).getEngName());
//			totalHourIP.setIPNum(IPNum);
//			totalHourIP.setChineseName(nameList.get(i).getChineseName());
//			TotalIPHourNumList.add(totalHourIP);
//		}
//		return TotalIPHourNumList;
//	}
	/**根据小时获取每个节点的某一小时访问的ip个数
	 * @param dateStr"2012010123"
	 * @return
	 */
	public List<NodeOneDateNum> readEveryNodeOneHourIPNum(String dateStr){
		List<NodeOneDateNum> TotalIPHourNumList = new ArrayList<NodeOneDateNum>();
		NodeIPHourNum totalHourIP =null;
		List<Name> nameList = NodeUtil.getNodeList();
		for(int i=0;i<nameList.size();i++){
			totalHourIP = new NodeIPHourNum();
			NodeOneDateNum nodeOneHourNum =new NodeOneDateNum();
			totalHourIP = readNodeHourIPNum(dateStr,nameList.get(i).getEngName());
			nodeOneHourNum.setChineseName(nameList.get(i).getChineseName());
			if(totalHourIP==null){
				nodeOneHourNum.setIPNum(0);
			}else{
				nodeOneHourNum.setIPNum(totalHourIP.getIPNum());
			}
			nodeOneHourNum.setEngName(nameList.get(i).getEngName());
			TotalIPHourNumList.add(nodeOneHourNum);
		}
		return TotalIPHourNumList;
	}
	
	/**根据小时获取当前小时所有节点访问的ip个数的和
	 * @param dateStr
	 * @return
	 */
	public String readTotalNodeHourIP(String dateStr){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		String sql = "select sum(IPNum) as totalIPNum from totalip_ipnum_hour where hour="+dateStr;
		String totalIPNum = (String) session.createSQLQuery(sql).addScalar("totalIPNum", Hibernate.STRING).uniqueResult();
		transaction.commit();
		if(totalIPNum==null){
			totalIPNum="0";
		}
		return totalIPNum;
	}
	public List<Object[]>  readTotalNode12HourIPNum(String startDate,String endDate){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select sum(totalhour.IPNum) ,totalhour.hour FROM NodeIPHourNum totalhour where totalhour.hour>='"+startDate+"' and totalhour.hour<='"+endDate+"' group by totalhour.hour";
		List<Object[]> IPNumList =  session.createQuery(sql).list();
		transaction.commit();
				
		return IPNumList;
	}
	
	
	
	public static void main(String[] args) {
		IPHourDao dao = new IPHourDao();
		List<NodeIPHourNum> IPNumList = new ArrayList<NodeIPHourNum>();
		List<NodeHourIP> IPNumList1 = new ArrayList<NodeHourIP>();
		List<Object[]> TotalHourIP = new ArrayList<Object[]>();
		List<NodeOneDateNum> TotalNodeHourIP = new ArrayList<NodeOneDateNum>();
		List<NodeTotalDateNum> everyNodeTotalHourIP = new ArrayList<NodeTotalDateNum>();
//		测试获取所有节点一个小时的
//		System.out.println(dao.readTotalNodeHourIP("2012000000"));
		
//		测试获取每个节点近12个小时所有小时的总的
//		everyNodeTotalHourIP = dao.readEveryNodeTotal12HourIPNum("2012112516");
//		System.out.println(everyNodeTotalHourIP.size());
//		for (NodeTotalDateNum nodeTotalDateNum : everyNodeTotalHourIP) {
//			System.out.println(nodeTotalDateNum.getChineseName());
//			System.out.println(nodeTotalDateNum.getDateStr());
//			System.out.println(nodeTotalDateNum.getEngName());
//			System.out.println(nodeTotalDateNum.getIPNum());
//		}
		
		
		//测试获取24个节点1个小时
		
//		TotalNodeHourIP = dao.readEveryNodeOneHourIPNum("2012110409");
//		for (NodeOneDateNum nodeOneDateNum : TotalNodeHourIP) {
//			System.out.println(nodeOneDateNum.getChineseName());
//			System.out.println(nodeOneDateNum.getIPNum());
//		}
		
		
		
		TotalHourIP= dao.read25Node12HourIPNum("2012111401","2012111412");
		System.out.println(TotalHourIP.size());
		for (Object[] obj : TotalHourIP) {
			System.out.println(obj[0]);
			System.out.println(obj[1]);
		}
		
		
		
		//测试获取近12个小时的所有节点总		
//		TotalHourIP= dao.readTotalNode12HourIPNum("2012111401","2012111412");
//		System.out.println(TotalHourIP.size());
//		for (Object[] obj : TotalHourIP) {
//			System.out.println(obj[0]);
//			System.out.println(obj[1]);
//		}
		
		//生成测试数据		
//		List<Name> nameList = changeNameDao.readName();
//		for (int j = 0; j < nameList.size(); j++) {
//			for(int i=0;i<10;i++){
//				NodeIPHourNum ipNum = new NodeIPHourNum();
//				ipNum.setEngName(nameList.get(j).getEngName());
//				ipNum.setIPHourNum((i+3)*800);
//				ipNum.setHour("201211030"+i);
//				dao.save(ipNum);
//			}
//			
//		}
		//测试获取某节点某小时的
//		NodeIPHourNum ipNum = new NodeIPHourNum();
//		NodeIPHourNum ipNum = dao.readNodeHourIPNum("2012120400","sdu");
//		
//		System.out.println(ipNum.getEngName());
//		System.out.println(ipNum.getHour());
//		System.out.println(ipNum.getIPNum());
//		
		//测试获取每个节点近12小时的
//		IPNumList1 = dao.readEveryNode12HourIPNum("2012110408");
//		System.out.println(IPNumList1.size());
//		
//		
//		for(int i=0;i<IPNumList1.size();i++){
//			System.out.println(IPNumList1.get(i).getChineseName());
//			for (int j = 0; j < IPNumList1.get(i).getIPNum().size(); j++) {
//				System.out.println(IPNumList1.get(i).getIPNum().get(j).getEngName());
//				System.out.println(IPNumList1.get(i).getIPNum().get(j).getHour());
//				System.out.println(IPNumList1.get(i).getIPNum().get(j).getIPHourNum());
//			}
//		}
		
//		测试获取某节点近12小时的
//		IPNumList = dao.readNode24HourIPNum("","zzu_0");
//		System.out.println(IPNumList.size());
//		for(int i=0;i<IPNumList.size();i++){
//				System.out.println(IPNumList.get(i).getEngName());
//				System.out.println(IPNumList.get(i).getHour());
//				System.out.println(IPNumList.get(i).getIPNum());
//		}
//		dao.read25NodeTotal12HourIPNum("2012112500", "2012112510");
		
	}
	
}
