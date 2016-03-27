package com.totalIP.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.base.model.NodeIPDayNum;
import com.base.model.NodeIPHourNum;
import com.base.model.NodeIPMonthNum;
import com.base.model.NodeIPWeekNum;
import com.base.util.HibernateUtil;
import com.totalIP.dto.Name;
import com.totalIP.dto.TotalNodeAllDateIPNum;
import com.totalIP.util.NodeUtil;

public class TotalIPDao {
	private String hourStr = "";
	private String dayStr="";
	private String weekStartStr="";
	private String monthStartStr="";
	private  void setDateStr(){
//		DateUtil dateUtil = new DateUtil();
		DateFormat hourFormat = new SimpleDateFormat("yyyyMMddHH");
		DateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");
//		DateFormat monthFormat = new SimpleDateFormat("yyyyMM");
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		if(calendar.get(calendar.MINUTE)<30){
			calendar.add(calendar.HOUR, -1);
		}
		calendar.add(calendar.HOUR, -1);
		date=calendar.getTime();
		hourStr = hourFormat.format(date);
		System.out.println(hourStr);
		calendar.setTime(new Date());
		if(calendar.get(calendar.HOUR_OF_DAY)==0&&calendar.get(calendar.MINUTE)<30){
			calendar.add(calendar.DATE, -1);
		}
		calendar.add(calendar.DATE, -1);
		dayStr = dayFormat.format(calendar.getTime());
		calendar.add(calendar.DATE, -6);
		weekStartStr =  dayFormat.format(calendar.getTime());
		calendar.add(calendar.DATE, -23);
		monthStartStr = dayFormat.format(calendar.getTime());
		//上一周和上一个月的,换成了上7天与上30天
//		calendar.setTime(new Date());
//		int dayOfWeek = calendar.get(calendar.DAY_OF_WEEK);
//		if(dayOfWeek!=1){
//			calendar.add(calendar.DATE,-dayOfWeek-5);
//		}else{
//			if(calendar.get(calendar.HOUR_OF_DAY)==0&&calendar.get(calendar.MINUTE)<30){
//				calendar.add(calendar.DATE, -7);
//			}
//			calendar.add(calendar.DATE,-13);
//		}
//		date=calendar.getTime();
//		weekStr = dayFormat.format(date);
//		calendar.setTime(new Date());
//		if(calendar.get(calendar.DAY_OF_MONTH)==1&&calendar.get(calendar.HOUR_OF_DAY)==0&&calendar.get(calendar.MINUTE)<30){
//			calendar.add(calendar.MONTH, -1);
//		}
//		calendar.add(calendar.MONTH, -1);
//		date=calendar.getTime();
//		monthStr = monthFormat.format(date);
	}
//	public List<NodeTotalDateNum> readTotalIP(String dateStr){
//		String[] arr;
//		String fileName = null;
//		List<NodeTotalDateNum> list = null;
//		NodeTotalDateNum node;
//		//计算昨天日期，获得昨天的统计文件
//		if(dateStr==null || dateStr.equals("")){
//			
//		    DateUtil tt = new DateUtil();
//	        Date yesterday = tt.preDay(new Date());
//	        String yesterdayStr = tt.getFormatDate(yesterday, "yyyyMMdd");
//	        fileName = "20120924";
//	        //fileName =yesterdayStr;
//		}else{
//			fileName = dateStr.replaceAll("-","");
//		}
//        String filePath = Constants.webRealPath + "file/totalIP/IPv6AddrStat/ip/total/"+fileName+".txt";
//        try {
//        	if((new File(filePath)).exists()){
//        		list = new ArrayList<NodeTotalDateNum>();
//        		LineNumberReader file = new LineNumberReader(new FileReader(filePath));
//        		String s;
//        		for(s=file.readLine();s!=null;s=file.readLine()){
//        			arr = s.split("\\|\\|");
//        			node = new  NodeTotalDateNum();
//        			node.setEngName(arr[1]);
//        			node.setIPNum(Integer.parseInt(arr[5]));
//        			list.add(node);
//        		}
//        		file.close();
//        	}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return list;
//	}
	public List<TotalNodeAllDateIPNum> readTotalNodeAllDateIPNum(){
		List<TotalNodeAllDateIPNum> IPNumList = new ArrayList<TotalNodeAllDateIPNum>();
		this.setDateStr();
		List<NodeIPHourNum> hourList = readHourIPNum(hourStr);
		List<NodeIPDayNum> dayList = readDayIPNum(dayStr);
//		List<Object[]> weekList = readEveryNodeDay("20121119","20121124");
//		List<Object[]> monthList = readEveryNodeDay("20121024","20121124");
		List<Object[]> weekList = readEveryNodeDay(weekStartStr,dayStr);
		List<Object[]> monthList = readEveryNodeDay(monthStartStr,dayStr);
		List<Object[]> totalList = readTotalIPNum();
		List<Name> nameList = NodeUtil.getNodeList();
		for(int i=0;i<nameList.size();i++){
			String name =nameList.get(i).getEngName();
			TotalNodeAllDateIPNum nodeAllDateIPNum = new TotalNodeAllDateIPNum();
			nodeAllDateIPNum.setEngName(name);
			nodeAllDateIPNum.setChineseName(nameList.get(i).getChineseName());
			nodeAllDateIPNum.setGroupName(nameList.get(i).getGroupId());
			//数据库没有此节点数据时,设置节点IP地址数位0
			nodeAllDateIPNum.setHourNum("0");
			nodeAllDateIPNum.setDayNum("0");
			nodeAllDateIPNum.setMonthNum("0");
			nodeAllDateIPNum.setWeekNum("0");
			nodeAllDateIPNum.setTotalNum("0");
			for(int j=0;j<hourList.size();j++){
				if( name.equals(hourList.get(j).getEngName())){
					nodeAllDateIPNum.setHourNum(hourList.get(j).getIPNum()+"");
					continue;
				}
			}
			for(int j=0;j<dayList.size();j++){
				if( name.equals(dayList.get(j).getEngName())){
					nodeAllDateIPNum.setDayNum(dayList.get(j).getIPNum()+"");
					continue;
				}
			}
			for(int j=0;j<weekList.size();j++){
				if( name.equals(weekList.get(j)[1].toString())){
					nodeAllDateIPNum.setWeekNum(weekList.get(j)[0].toString());
					continue;
				}
			}
			for(int j=0;j<monthList.size();j++){
				if( name.equals(monthList.get(j)[1].toString())){
					nodeAllDateIPNum.setMonthNum(monthList.get(j)[0].toString());
					continue;
				}
			}
			for(int j=0;j<totalList.size();j++){
				if( name.equals(totalList.get(j)[0])){
					nodeAllDateIPNum.setTotalNum(totalList.get(j)[1].toString());
					continue;
				}
			}
			IPNumList.add(nodeAllDateIPNum);
		}
		return IPNumList;
	}
	public List<NodeIPHourNum> readHourIPNum(String dateStr){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<NodeIPHourNum> hourList = session.createCriteria(NodeIPHourNum.class).add(Restrictions.eq("hour",dateStr)).list();
		transaction.commit();
		return hourList;
	}
//	public Map<String,Integer> readHourIPNum(String dateStr){
//		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//		Transaction transaction = session.beginTransaction();
//		String sql = "select hour.IPNum ,hour.engName FROM NodeIPHourNum hour where hour.hour='"+hourStr+"'";
//		List<NodeIPHourNum> hourList = session.createCriteria(NodeIPHourNum.class).add(Restrictions.eq("hour",dateStr)).list();
//		List<Object[]> IPNumList =  session.createQuery(sql).list();
//		transaction.commit();
//		Map<String, Integer> hourList2
//		return hourList;
//	}
	public List<NodeIPDayNum>  readDayIPNum(String dateStr){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<NodeIPDayNum> daylist = session.createCriteria(NodeIPDayNum.class).add(Restrictions.eq("day",dateStr)).list();
		transaction.commit();
		return daylist;
	}
	/**首页列表:近n天
	 * @param dateStr
	 * @return
	 */
	public List<Object[]> readEveryNodeDay(String startDate,String endDate){
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select sum(day.IPNum) ,day.engName FROM NodeIPDayNum day where day.day>='"+startDate+"' and day.day<='"+endDate+"' group by day.engName";
		List<Object[]> IPNumList =  session.createQuery(sql).list();
		transaction.commit();
		return IPNumList;
	}
	
	public List<Object[]> readTotalIPNum(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select day.engName,sum(day.IPNum) from NodeIPDayNum day GROUP BY day.engName";
		List<Object[]> list = session.createQuery(sql).list();
		transaction.commit();
		return list;
	} 
	
	
	
	/**首页列表:上一周
	 * @param dateStr
	 * @return
	 */
	@Deprecated 
	public List<NodeIPWeekNum> readWeekIPNum(String dateStr){
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<NodeIPWeekNum> weekList = session.createCriteria(NodeIPWeekNum.class).add(Restrictions.eq("week",dateStr)).list();
		transaction.commit();
		return weekList;
	}
	
	//首页列表:上一月
	@Deprecated 
	public List<NodeIPMonthNum> readMonthIPNum(String dateStr){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<NodeIPMonthNum> monthList = session.createCriteria(NodeIPMonthNum.class).add(Restrictions.eq("month",dateStr)).list();
		transaction.commit();
		return monthList;
	}
	
//	public List<NodeIPTotalNum> readTotalIPNum(){
//		String file = "AllDayNum.txt";
//		List<NodeIPTotalNum> list = new ArrayList<NodeIPTotalNum>();
////		String[] arr;
////		String path = Constants.webRealPath+"ip/college/";
//		List<String> nameList = NodeUtil.getNodeList();
//		for (String name : nameList) {
////			String filePath =  "/opt/NetEye/file/"+name+"/"+file;
//			String filePath =  "e:/"+name+"/"+file;
//			
//			NodeIPTotalNum totalNum = new NodeIPTotalNum();
//			totalNum.setEngName(name);
//			String IPNum = "0";
//			if((new File(filePath)).exists()){
//			RandomAccessFile raf = null; 
//			 try {  
//				    raf = new RandomAccessFile(filePath, "r");
//				    long len = raf.length();  
//				    if (len == 0L) {  
//				    	IPNum = "0";  
//				    } else {  
//				        long pos = len - 1;  
//				        while (pos > 0) {  
//					        pos--;  
//					        raf.seek(pos);  
//					        if (raf.readByte() == '\n') {  
//					        	byte[] bytes = new byte[(int) (len - pos)];  
//					        	raf.read(bytes);  
//					        	String temp = new String(bytes);  
//					        	if(temp.trim().length()!=0){
//					        		String[] arr = temp.split("\\s+");
//					        		IPNum = arr[1];
//					        		break;
//					        	}
//					        }  
//				      }  
//				      if (pos == 0) {  
//				    	  IPNum = "0";  
//				      }  
//				    }
//				    
//				  } catch (Exception e) {  
//				  } finally {  
//				    if (raf != null) {  
//				      try {  
//				        raf.close();  
//				      } catch (Exception e2) {  
//				      }  
//				    }  
//				  } 
//				
//			}
//			totalNum.setIPNum(Integer.valueOf(IPNum));
//			list.add(totalNum);
//		}
//		return list;
//	}
	public static void main(String[] args) {
		TotalIPDao dao = new TotalIPDao();

		long startTime = System.currentTimeMillis();
		List<TotalNodeAllDateIPNum> list = dao.readTotalNodeAllDateIPNum();
		System.out.println("程序运行时间：" + (System.currentTimeMillis() - startTime) + "ms");
		int i =0;
		for (TotalNodeAllDateIPNum totalNodeAllDateIPNum : list) {
			System.out.println(i++);
			System.out.println("chineseName"+totalNodeAllDateIPNum.getChineseName());
			System.out.println("groupName"+totalNodeAllDateIPNum.getGroupName());
			System.out.println("engName"+totalNodeAllDateIPNum.getEngName());
			System.out.println("dayNum"+totalNodeAllDateIPNum.getDayNum());
			System.out.println("HourNum"+totalNodeAllDateIPNum.getHourNum());
			System.out.println("monthNum"+totalNodeAllDateIPNum.getMonthNum());
			System.out.println("weekNum"+totalNodeAllDateIPNum.getWeekNum());
			System.out.println("totalNUM"+totalNodeAllDateIPNum.getTotalNum());
		}
//		List<Object[]> list = dao.readEveryNode7Day("20121118", "20121225");
//		for (Object[] objects : list) {
//			System.out.println(objects[1].toString());
//			System.out.println(objects[0].toString());
//		}
//		
//		 List<NodeIPHourNum> list3 =  dao.readHourIPNum("2012112311");
//		 System.out.println(list3.size());
//		 
//		 for (NodeIPHourNum nodeIPHourNum : list3) {
//			 System.out.println(111111);
//			 System.out.println(nodeIPHourNum.getEngName());
//			 System.out.println(nodeIPHourNum.getHour());
//			 System.out.println(nodeIPHourNum.getIPNum());
//		}
//		List<NodeIPMonthNum> list2 = dao.readMonthIPNum("201210");;i
//		for (NodeIPMonthNum nodeIPMonthNum : list2) {
//			System.out.println(nodeIPMonthNum.getIPNum());
//		}
//		List<Object[]> list4 = dao.readTotalIPNum();
//		for (int i=0;i<list4.size();i++) {
//			System.out.println(list4.get(i)[0]);
//			System.out.println(list4.get(i)[1]);
//		}
	}
}
