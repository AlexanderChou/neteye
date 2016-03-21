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

import com.base.model.NodeIPWeekNum;
import com.base.util.DateUtil;
import com.base.util.HibernateUtil;
import com.totalIP.dto.Name;
import com.totalIP.dto.NodeOneDateNum;
import com.totalIP.dto.NodeTotalDateNum;
import com.totalIP.dto.TotalNodeWeekIPNum;
import com.totalIP.util.NodeUtil;


public class IPWeekDao {
	public NodeIPWeekNum save(NodeIPWeekNum NodeIPWeekNum) {
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.save(NodeIPWeekNum);
		transaction.commit();
		return NodeIPWeekNum;
	}
	/**根据本周星期一的日期和英文名称获取某节点本周的访问个数
	 * @param dateStr
	 * @param engName
	 * @return
	 */
	public NodeIPWeekNum readNodeWeekIPNum(String dateStr,String engName){
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<NodeIPWeekNum> NodeIPWeekNumList =  session.createCriteria(NodeIPWeekNum.class).add(Restrictions.eq("week",dateStr)).add(Restrictions.eq("engName", engName)).list();
		transaction.commit();
		if(NodeIPWeekNumList!=null&&NodeIPWeekNumList.size()>0){
			return NodeIPWeekNumList.get(0);
		}
		else {
			return null;
		}
	}
	/**根据周日期获取每个节点的某一周访问的ip个数
	 * @param dateStr"2012010123"
	 * @return
	 */
	public List<NodeOneDateNum> readEveryNodeOneWeekIPNum(String dateStr){
		List<NodeOneDateNum> TotalIPWeekNumList = new ArrayList<NodeOneDateNum>();
		NodeIPWeekNum totalWeekIP =null;
		List<Name> nameList = NodeUtil.getNodeList();
		for(int i=0;i<nameList.size();i++){
			totalWeekIP = new NodeIPWeekNum();
			NodeOneDateNum nodeOneWeekNum =new NodeOneDateNum();
			totalWeekIP = readNodeWeekIPNum(dateStr,nameList.get(i).getEngName());
			nodeOneWeekNum.setChineseName(nameList.get(i).getChineseName());
			if(totalWeekIP==null){
				nodeOneWeekNum.setIPNum(0);
			}else{
				nodeOneWeekNum.setIPNum(totalWeekIP.getIPNum());
			}
			nodeOneWeekNum.setEngName(nameList.get(i).getEngName());
			TotalIPWeekNumList.add(nodeOneWeekNum);
		}
		return TotalIPWeekNumList;
	}
	/**根据日期和英文名称获取某节点近4周的访问个数
	 * @param dateStr
	 * @param engName
	 * @return
	 */
	public List<NodeIPWeekNum> readNode12WeekIPNum(String dateStr,String engName){
		List<NodeIPWeekNum> list = new ArrayList<NodeIPWeekNum>();
		int lastMonday = 0;

		if(dateStr==null||dateStr==""){
			 DateFormat format = new SimpleDateFormat("yyyyMMdd");
			 Date date2 = new Date();
			 Calendar calendar = Calendar.getInstance();
			 calendar.setTime(date2);
			 if(calendar.get(calendar.DAY_OF_WEEK)==1&&calendar.get(calendar.HOUR_OF_DAY)==1&&calendar.get(calendar.MINUTE)<30){
				 calendar.add(calendar.DATE, -1);
				 date2=calendar.getTime();
			 }
			 dateStr = format.format(date2);
		}
		DateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		Date date;
		try {
			date = format1.parse(dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int dayOfWeek = calendar.get(calendar.DAY_OF_WEEK);
			if(dayOfWeek!=1){
				lastMonday=-dayOfWeek+2-91;
			}else{
				lastMonday=-6-91;
			}
			calendar.add(calendar.DATE,lastMonday);
			for(int i=0;i<12;i++){
				calendar.add(calendar.DATE,+7);
				date=calendar.getTime(); 
				dateStr= format1.format(date);
				NodeIPWeekNum IPNum = new NodeIPWeekNum();
				IPNum = readNodeWeekIPNum(dateStr,engName);
				list.add(IPNum);
			}
		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**根据开始日期和结束日期25个节点前4周总的IP地址数
	 * 饼图(4周)
	 * @param dateStr
	 * @param engName
	 * @return
	 */
	public List<Object[]> read25Node4WeekIPNum(String startDate,String endDate){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select sum(week.IPNum),week.groupName FROM NodeIPWeekNum week where week.week>= '"+startDate+"' and week.week<'"+endDate+"' group by week.groupName";
		List<Object[]> IPNumList =  session.createQuery(sql).list();
		transaction.commit();
		return IPNumList;
	}
//	public NodeTotalDateNum readNodeTotal4WeekIPNum(String startDate,String endDate,String engName){
//		NodeTotalDateNum NodeTotalDateNum = new NodeTotalDateNum();
//		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//		Transaction transaction = session.beginTransaction();
//		String sql = "select sum(IPNum) as totalIPNum from totalip_ipnum_week where serviced='"+engName+"'and week>="+startDate+" and week<="+endDate;
//		String IPNum = (String) session.createSQLQuery(sql).addScalar("totalIPNum", Hibernate.STRING).uniqueResult();		
//		transaction.commit();
//		if(IPNum!=null&&IPNum.trim().length()>0){
//			NodeTotalDateNum.setIPNum(Integer.parseInt(IPNum));
//		}else{
//			NodeTotalDateNum.setIPNum(0);
//		}
//		NodeTotalDateNum.setDateStr(endDate);
//		NodeTotalDateNum.setEngName(engName);
//		return NodeTotalDateNum;
//	}
	/**根据日期获取每个节点的近4周所有周总访问的ip个数
	 * @param dateStr"2012010123"
	 * @return
	 */
	public List<NodeTotalDateNum> read25Node4Week(String dateStr){
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		if(dateStr==null||dateStr==""){
			 Date date2 = new Date();
			 Calendar calendar = Calendar.getInstance();
			 calendar.setTime(date2);
			 if(calendar.get(calendar.DAY_OF_WEEK)==1&&calendar.get(calendar.HOUR_OF_DAY)==1&&calendar.get(calendar.MINUTE)<30){
				 calendar.add(calendar.DATE, -1);
				 date2=calendar.getTime();
			 }
			 dateStr = format.format(date2);
		}
		String startdate;
		String endDate;
		Date date=null;
		try {
			date = format.parse(dateStr);
		}catch (ParseException e) {
			// TODO: handle exception
		}
		Calendar calendar = Calendar.getInstance();
		 calendar.setTime(date);
		 date = calendar.getTime();
		 endDate = format.format(date);
		 calendar.add(calendar.WEEK_OF_MONTH, -4);
		 date = calendar.getTime();
		 startdate = format.format(date);
		 List<NodeTotalDateNum> TotalIPWeekNumList = new ArrayList<NodeTotalDateNum>();
		NodeTotalDateNum dto =null;
		List<Object[]> IPNumList = read25Node4WeekIPNum(startdate, endDate);
		for(int i=0;i<IPNumList.size();i++){
			dto = new NodeTotalDateNum();
			dto.setIPNum(Integer.valueOf(IPNumList.get(i)[0].toString()));
			dto.setChineseName(IPNumList.get(i)[1].toString());
			dto.setDateStr(endDate);
			TotalIPWeekNumList.add(dto);
		}
		return TotalIPWeekNumList;
	}
	/**根据一周第一天日期获取单周所有节点总的访问个数
	 * @param dateStr
	 * @return
	 */
	public String readTotalNodeWeekIPNum(String dateStr){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select sum(IPNum) as totalIPNum from totalip_ipnum_week where week="+dateStr;
		String totalIPNum = (String) session.createSQLQuery(sql).addScalar("totalIPNum", Hibernate.STRING).uniqueResult();
		transaction.commit();
		if(totalIPNum==null){
			totalIPNum="0";
		}
		return totalIPNum;
	}
	
	
	public List<Object[]> readTotalNode12WeekIPNum(String startDate,String endDate){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select sum(week.IPNum) ,week.week FROM NodeIPWeekNum week where week.week>='"+startDate+"' and week.week<'"+endDate+"' group by week.week";
		List<Object[]> IPNumList =  session.createQuery(sql).list();
		transaction.commit();
		return IPNumList;
	}
	
	
	
	/**根据日期获取前4周所有节点的访问个数的和
	 * @param dateStr
	 * @return
	 */
	
//	public List<TotalNodeWeekIPNum> readTotalNode4WeekIPNum(String dateStr){
//		List<TotalNodeWeekIPNum> list = new ArrayList<TotalNodeWeekIPNum>();
//		if(dateStr==null||dateStr==""){
//			 DateFormat format = new SimpleDateFormat("yyyyMMdd");
//			 Date date2 = new Date();
//			 Calendar calendar = Calendar.getInstance();
//			 calendar.setTime(date2);
//			 if(calendar.get(calendar.DAY_OF_WEEK)==1&&calendar.get(calendar.HOUR_OF_DAY)==1&&calendar.get(calendar.MINUTE)<30){
//				 calendar.add(calendar.DATE, -1);
//				 date2=calendar.getTime();
//			 }
//			 dateStr = format.format(date2);
//		}
//		DateFormat format1 = new SimpleDateFormat("yyyyMMdd");
//		Date date;
//		try {
//			date = format1.parse(dateStr);
//			Calendar calendar = Calendar.getInstance();
//			calendar.setTime(date);
//			int dayOfWeek = calendar.get(calendar.DAY_OF_WEEK);
//			
//			if(dayOfWeek!=1){
//				calendar.add(calendar.DATE,-dayOfWeek+2-91);
//			}else{
//				calendar.add(calendar.DATE,-6-91);
//			}
//			for(int i=0;i<12;i++){
//				TotalNodeWeekIPNum nodeWeekIPNum = new TotalNodeWeekIPNum();
//				calendar.add(calendar.DATE,+7);
//				date=calendar.getTime(); 
//				dateStr = format1.format(date);
//				nodeWeekIPNum.setIPNum(readTotalNodeWeekIPNum(dateStr));
//				nodeWeekIPNum.setWeek(dateStr);
//				list.add(nodeWeekIPNum);
//			}
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return list;
//	}
	
	public static void main(String[] args) {
		IPWeekDao dao= new IPWeekDao();
		
		List<NodeTotalDateNum> everyNodeTotalHourIP = new ArrayList<NodeTotalDateNum>();
		
		//测试获取每个节点7天所有天的总的
		everyNodeTotalHourIP = dao.read25Node4Week("");
		System.out.println(everyNodeTotalHourIP.size());
		for (NodeTotalDateNum nodeTotalDateNum : everyNodeTotalHourIP) {
			System.out.println("getChineseName"+nodeTotalDateNum.getChineseName());
			System.out.println("getDateStr"+nodeTotalDateNum.getDateStr());
			System.out.println("getEngName"+nodeTotalDateNum.getEngName());
			System.out.println("getIPNum"+nodeTotalDateNum.getIPNum());
		}

		//测试读取1周某节点访问次数
//		NodeIPWeekNum nodeIPWeekNum = dao.readNodeWeekIPNum("20121112", "thu");
//		System.out.println(nodeIPWeekNum.getEngName());
//		System.out.println(nodeIPWeekNum.getIPNum());
//		System.out.println(nodeIPWeekNum.getWeek());

		
		//测试读取近4周某节点访问次数
//		List<NodeIPWeekNum> nodeIPWeekNumList = new ArrayList<NodeIPWeekNum>();
//		nodeIPWeekNumList = dao.readNode12WeekIPNum("", "southeast");
//		for (NodeIPWeekNum nodeIPWeekNum : nodeIPWeekNumList) {
//			System.out.println(nodeIPWeekNum.getEngName());
//			System.out.println(nodeIPWeekNum.getIPNum());
//			System.out.println(nodeIPWeekNum.getWeek());
//		}
		
		
		
		
		//测试获取一周的所有节点总的		
//		System.out.println(dao.readTotalNodeWeekIPNum("20121112"));
		
		
//		//测试近4周所有节点总的IPNum
//		List<TotalNodeWeekIPNum> totalNodeWeekIPNumList = dao.readTotalNode4WeekIPNum("");
//		for (TotalNodeWeekIPNum totalNodeWeekIPNum : totalNodeWeekIPNumList) {
//			System.out.println(totalNodeWeekIPNum.getIPNum());
//			System.out.println(totalNodeWeekIPNum.getWeek());
//		}
//		//测试饼图
//		List<Object[]>  totalNode12WeekIPNum = dao.readTotalNode12WeekIPNum("20120826","20121126");
//		System.out.println(totalNode12WeekIPNum.size());
//		for (Object[] objects : totalNode12WeekIPNum) {
//			System.out.println(objects[0]);
//			System.out.println(objects[1]);
//		}
//		List<Object[]>  totalNode12WeekIPNum = dao.read25NodeTotal4Week("20121030","20121127");
//		System.out.println(totalNode12WeekIPNum.size());
//		for (Object[] objects : totalNode12WeekIPNum) {
//			System.out.println(objects[0]);
//			System.out.println(objects[1]);
//		}
		
		
		
//		生成测试数据
//		List<Name> nameList = changeNameDao.readName();
//		for (int j = 0; j < nameList.size(); j++) {
//				NodeIPWeekNum ipNum = new NodeIPWeekNum();
//				ipNum.setEngName(nameList.get(j).getEngName());
//				ipNum.setIPNum(12000+j*1650);
//				ipNum.setWeek("20121001");
//				dao.save(ipNum);
//		}
//		
		
		
		
		
		//测试save
//		NodeIPWeekNum nodeIPWeekNum = new NodeIPWeekNum();
//		nodeIPWeekNum.setEngName("thu");
//		nodeIPWeekNum.setIPNum(1212122);
//		nodeIPWeekNum.setWeek("20121112");
//		dao.save(nodeIPWeekNum);
	
	
	}
}
