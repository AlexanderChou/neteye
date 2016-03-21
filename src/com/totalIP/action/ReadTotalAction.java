package com.totalIP.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.base.model.NodeIPDayNum;
import com.base.model.NodeIPHourNum;
import com.base.model.NodeIPMonthNum;
import com.base.model.NodeIPWeekNum;
import com.totalIP.dao.IPDayDao;
import com.totalIP.dao.IPHourDao;
import com.totalIP.dao.IPMonthDao;
import com.totalIP.dao.IPWeekDao;

public class ReadTotalAction {
	public void readHourIPNum(){
		Calendar calendar =Calendar.getInstance();
		calendar.add(calendar.HOUR_OF_DAY, -1);
		String  dateStr = MessageFormat.format("{0,date,yyyyMMddHH}" ,calendar.getTime());
	    IPHourDao hourDao= new IPHourDao();
	    String file = dateStr + "_hour.txt";
	    //每小时的第25分钟执行命令获取file/totalIP/IPv6AddrStat/ip/total目录下**_hour.txt文件，将其记录总数存入totalIP_hour表中
	    String[] result = null;
	    NodeIPHourNum hour = null;
	    String path = "/opt/NetEye/file";
//		    String path = Constants.webRealPath+"file/totalIP/IPv6AddrStat";
	    String cmdPrameter = path+"/ip/total";
	    String cmd = new String();
//		    cmd = "echo 'for i in `find  ./ -name 2012112215_hour.txt`; do n=`echo $i|awk -F \"/\" \\'{\\'print \\$2\\'}\\'`;echo -n \"$n \";cat $i |wc -l;done'|/bin/bash";
	    cmd = "/bin/bash /tmp/a.sh " + file;
	    System.out.println("guoxitest file="+file);
	    try{ 
	    	Runtime rt = Runtime.getRuntime();
	    	Process proc = rt.exec(cmd);
	    	
	    	String output;
	    	BufferedReader bufferedReader = new BufferedReader(
	    			new InputStreamReader(proc.getInputStream()));
	    	while ((output = bufferedReader.readLine()) != null) {
	    		System.out.println("out="+output);
	    		if (!output.equals("")) {
	    			hour = new NodeIPHourNum();
	    			result = output.split(" ");
	    			hour.setEngName(result[0]);
	    			hour.setIPNum(Integer.valueOf(result[1]));
	    			hour.setHour(dateStr);
	    			hourDao.save(hour);
	    		}
	    	}
	    }catch(java.io.IOException   e){
	    	e.printStackTrace();             
	    } 
	}
	public void readDayIPNum(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.DATE, -1);
		String dateStr = MessageFormat.format("{0,date,yyyyMMdd}" ,calendar.getTime());
		IPDayDao dayDao = new IPDayDao();
		String file = dateStr + "_day.txt";
		System.out.println("file="+file);
		String[] result = null; 
		NodeIPDayNum day = null;
//		String path = Constants.webRealPath+"file/totalIP/IPv6AddrStat";
		 String path =  "/opt/NetEye/file";
		String cmdPrameter = path+"/ip/total";
		String cmd = new String();
//		cmd = "for i in `find  ./ -name "+ file +"`; do n=`echo $i|awk -F \"/\" '{print $2}'`;echo -n \"$n \";cat $i |wc -l;done";
		 cmd = "/bin/bash /tmp/a.sh " + file;
		 try{ 
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(cmd);

			String output;
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(proc.getInputStream()));
			while ((output = bufferedReader.readLine()) != null) {
				if (!output.equals("")) {
					System.out.println("output="+output);
					day = new NodeIPDayNum();
					result = output.split(" ");
					day.setEngName(result[0]);
					day.setIPNum(Integer.valueOf(result[1]));
					day.setDay(dateStr);
					
					dayDao.save(day);
				}
			}
		}catch(java.io.IOException   e){
			e.printStackTrace();             
		} 
	}
	public void readWeekIPNum(){
		
		Calendar calendar = Calendar.getInstance();
		String endDate = MessageFormat.format("{0,date,yyyyMMdd}" ,calendar.getTime());
		calendar.add(calendar.DATE, -7);
		String startDate = MessageFormat.format("{0,date,yyyyMMdd}" ,calendar.getTime());
			IPDayDao dayDao = new IPDayDao();
			IPWeekDao weekDao = new IPWeekDao();
			List<NodeIPWeekNum> list = dayDao.readEveryNodeWeekIPNum(startDate,endDate);
			for (NodeIPWeekNum nodeIPWeekNum : list) {
				weekDao.save(nodeIPWeekNum);
			}
		/*	
		String startDate = "20120820";
		String endDate =   "20121119";
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateStr ="";
		Date date = null;
		try {
			date = format.parse(startDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		while(!dateStr.equals(endDate)){
		dateStr = MessageFormat.format("{0,date,yyyyMMdd}" ,calendar.getTime());
		calendar.add(calendar.DATE, 7);
		String endDate2 = MessageFormat.format("{0,date,yyyyMMdd}" ,calendar.getTime());
		System.out.println("dateStr="+dateStr);
		System.out.println("endDate2="+endDate2);
			IPDayDao dayDao = new IPDayDao();
			IPWeekDao weekDao = new IPWeekDao();
			List<NodeIPWeekNum> list = dayDao.readEveryNodeWeekIPNum(dateStr,endDate2);
			for (NodeIPWeekNum nodeIPWeekNum : list) {
				weekDao.save(nodeIPWeekNum);
			}
		}*/
	}
	public void readMonthIPNum(){
		
		Calendar calendar = Calendar.getInstance();
		int dayOfMonth = calendar.get(calendar.DAY_OF_MONTH);
		
		if(dayOfMonth==1){
			calendar.add(calendar.MONTH, -1);
			String dateStr = MessageFormat.format("{0,date,yyyyMM}" ,calendar.getTime());
			IPDayDao dayDao = new IPDayDao();
			IPMonthDao MonthDao = new IPMonthDao();
			List<NodeIPMonthNum> list = new ArrayList<NodeIPMonthNum>();
			list = dayDao.readEveryNodeMonthIPNum(dateStr);
			for (NodeIPMonthNum nodeIPMonthNum : list) {
				MonthDao.save(nodeIPMonthNum);
			}
		}
		/*
		String[] dateStr={"201208","201209","201210"};
		for(int i =0;i<dateStr.length;i++){
			IPDayDao dayDao = new IPDayDao();
			IPMonthDao MonthDao = new IPMonthDao();
			List<NodeIPMonthNum> list = new ArrayList<NodeIPMonthNum>();
			list = dayDao.readEveryNodeMonthIPNum(dateStr[i]);
			System.out.println(dateStr[i]);
			for (NodeIPMonthNum nodeIPMonthNum : list) {
				MonthDao.save(nodeIPMonthNum);
			}
		}*/
		
	}
	public static void main(String[] args) {
		ReadTotalAction action = new ReadTotalAction();
		action.readMonthIPNum();
	}

}
