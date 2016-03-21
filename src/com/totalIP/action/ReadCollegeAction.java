package com.totalIP.action;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.MessageFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;

import com.base.model.NodeIPDayNum;
import com.base.model.NodeIPHourNum;
import com.base.util.Constants;
import com.totalIP.dao.IPDayDao;
import com.totalIP.dao.IPHourDao;
import com.totalIP.util.NodeUtil;

public class ReadCollegeAction {
	private static String firstTimeFalg = "true";
	public void readIPNum(String flag)  {
		
		try{
			//轮循file/ip/college/thu/2012112419.txt读取前一小时的统计文件
			String  time = null;
			//定时任务时需要计算时间
			Calendar calendar =Calendar.getInstance();
			if(flag.equals("hour")){
				calendar.add(calendar.HOUR_OF_DAY, -1);
				time = MessageFormat.format("{0,date,yyyyMMddHH}" ,calendar.getTime());
			}else if(flag.equals("day")){
				calendar.add(calendar.DATE, -1);
				time = MessageFormat.format("{0,date,yyyyMMdd}" ,calendar.getTime());
			}
			IPHourDao hourDao= new IPHourDao();
			IPDayDao dayDao = new IPDayDao();
			List<String> nodes = NodeUtil.getCernetNode();
			String cernetNodeDirStr = null;
			File cernetNodeDir = null;
			String path = Constants.webRealPath+"file/ip/college/";
			String ndoeFileStr = null;
			File nodeFile = null;
			LineNumberReader file = null;
			String[] arr = null;
			String s = null;
			NodeIPHourNum hour = null;
			NodeIPDayNum day = null;
			for(String node:nodes){
				cernetNodeDirStr = path + node;
				cernetNodeDir = new File(cernetNodeDirStr);
				if(cernetNodeDir.exists() && cernetNodeDir.isDirectory()){
					//读取该目录下的小时或天的统计文件，将其值存入数据库
					ndoeFileStr = cernetNodeDirStr + "/" + time +".txt";
					nodeFile = new File(ndoeFileStr);
					if(nodeFile.exists() && nodeFile.isFile()){
						//读取txt文件，读取其每一行并存入数据库
						file = new LineNumberReader(new FileReader(nodeFile));
				  		for(s=file.readLine();s!=null;s=file.readLine()){
				  			arr = s.split("\t");
				  			if(flag.equals("hour")){
					  			hour = new NodeIPHourNum();
				    			hour.setEngName(node+"_"+arr[0]);
				    			hour.setChineseName(arr[1]);
				    			hour.setIPNum(Integer.valueOf(arr[2]));
				    			hour.setHour(time);
				    			hour.setGroupName(node);
				    			hourDao.save(hour);
				  			}else if(flag.equals("day")){
				  				day = new NodeIPDayNum();
								day.setEngName(node+"_"+arr[0]);
								day.setChineseName(arr[1]);
								day.setIPNum(Integer.valueOf(arr[2]));
								day.setDay(time);
								day.setGroupName(node);							
								dayDao.save(day);
				  			}
				  		}
					}
				}
			  }
			}catch(java.io.IOException   e){
	    	e.printStackTrace();             
	    } 
		/*
	    //获取历史数据
		String  time = "";
		try{
//			String startDate = "20120824";
//			String endDate =   "20121126";
			String startDate = "2012112713";
			String endDate =   "2012112713";
			DateFormat format = null;
			if(flag.equals("hour")){
				format = new SimpleDateFormat("yyyyMMddHH");
			}else if(flag.equals("day")){
				format = new SimpleDateFormat("yyyyMMdd");
			}
			
			Date date = null;
			try {
				date = format.parse(startDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			while(!time.equals(endDate)){
				date=calendar.getTime();
				time=format.format(date);
				if(flag.equals("hour")){
					calendar.add(calendar.HOUR, 1);
				}else if(flag.equals("day")){
					calendar.add(calendar.DATE, 1);
				}		
			NodeUtil nodeUtil = new NodeUtil();
			IPHourDao hourDao= new IPHourDao();
			IPDayDao dayDao = new IPDayDao();
			List<String> nodes = nodeUtil.getCernetNode();
			String cernetNodeDirStr = null;
			File cernetNodeDir = null;
			String path = Constants.webRealPath+"file/ip/college/";
//			String path = "E:/ip/total/";
			String ndoeFileStr = null;
			File nodeFile = null;
			LineNumberReader file = null;
			String[] arr = null;
			String s = null;
			NodeIPHourNum hour = null;
			NodeIPDayNum day = null;
			System.out.println("time="+time);
			for(String node:nodes){
				cernetNodeDirStr = path + node;
				cernetNodeDir = new File(cernetNodeDirStr);
				if(cernetNodeDir.exists() && cernetNodeDir.isDirectory()){
					//读取该目录下的小时或天的统计文件，将其值存入数据库
					ndoeFileStr = cernetNodeDirStr + "/" + time +".txt";
					nodeFile = new File(ndoeFileStr);
					if(nodeFile.exists() && nodeFile.isFile()){
						//读取txt文件，读取其每一行并存入数据库
						file = new LineNumberReader(new FileReader(nodeFile));
				  		for(s=file.readLine();s!=null;s=file.readLine()){
				  			arr = s.split("\t");
				  			if(flag.equals("hour")){
					  			hour = new NodeIPHourNum();
				    			hour.setEngName(node+"_"+arr[0]);
				    			hour.setChineseName(arr[1]);
				    			hour.setIPNum(Integer.valueOf(arr[2]));
				    			hour.setHour(time);
				    			hour.setGroupName(node);
				    			hourDao.save(hour);
				  			}else if(flag.equals("day")){
				  				day = new NodeIPDayNum();
								day.setEngName(node+"_"+arr[0]);
								day.setChineseName(arr[1]);
								day.setIPNum(Integer.valueOf(arr[2]));
								day.setDay(time);	
								day.setGroupName(node);
								dayDao.save(day);
				  			}
				  		}
					}
				}
			}
		  }
			}catch(Exception   e){
		    	e.printStackTrace();             
		    }*/
	}
	public void test(){
		
		if(firstTimeFalg.equals("true")){
			System.out.println("第一次");
			firstTimeFalg="flase";
		}else if (firstTimeFalg.equals("flase")){
			System.out.println("第二次");
		}
		Calendar calendar = Calendar.getInstance();
		System.out.println(MessageFormat.format("{0,date,yyyyMMddHHmmss}" ,calendar.getTime()));
	}
	
	public static void main(String[] args) throws Exception{
//		ReadCollegeAction readAction = new ReadCollegeAction();
//		readAction.readIPNum("hour");
		Calendar calendar =Calendar.getInstance();
		calendar.set(calendar.HOUR_OF_DAY, -1);
		String time = MessageFormat.format("{0,date,yyyyMMddHH}" ,calendar.getTime());
		System.out.println("time="+time);
			
	}
}
