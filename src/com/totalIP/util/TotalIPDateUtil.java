package com.totalIP.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TotalIPDateUtil {
	//获取当前日期,没过0点30分获取昨天日期
	public static String getCurrentDay() {    
	     DateFormat format = new SimpleDateFormat("yyyyMMdd");
		 Calendar calendar = Calendar.getInstance();
		 Date date = new Date();
		 if(calendar.get(calendar.HOUR_OF_DAY)==0&&calendar.get(calendar.MINUTE)<30){
			 calendar.add(calendar.DATE, -1);
		 }
		 date=calendar.getTime();
		 return format.format(date);
	} 
	//获取当前小时,没过30分获取上一小时
	public static String getCurrentHour() {    
	     DateFormat format = new SimpleDateFormat("yyyyMMddHH");
		 Calendar calendar = Calendar.getInstance();
		 Date date = new Date();
		 if(calendar.get(calendar.MINUTE)<30){
			 calendar.add(calendar.HOUR, -1);
		 }
		 date=calendar.getTime();
		 return format.format(date);
	}
	//获取本周一日期,没过周一0点30分获取上周一日期
	public static String getCurrentWeek() {    
	     DateFormat format = new SimpleDateFormat("yyyyMMdd");
		 Calendar calendar = Calendar.getInstance();
		 Date date = new Date();
		 if(calendar.get(calendar.DAY_OF_WEEK)==1&&calendar.get(calendar.HOUR_OF_DAY)==1&&calendar.get(calendar.MINUTE)<30){
			 calendar.add(calendar.HOUR, -1);
		 }
		 date=calendar.getTime();
		 return format.format(date);
	}
}
