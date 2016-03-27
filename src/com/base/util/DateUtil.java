package com.base.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/*
** Copyright (c) 2008, 2009, 2010
**      The Regents of the Tsinghua University, PRC.  All rights reserved.
**
** Redistribution and use in source and binary forms, with or without  modification, are permitted provided that the following conditions are met:
** 1. Redistributions of source code must retain the above copyright  notice, this list of conditions and the following disclaimer.
** 2. Redistributions in binary form must reproduce the above copyright  notice, this list of conditions and the following disclaimer in the  documentation and/or other materials provided with the distribution.
** 3. All advertising materials mentioning features or use of this software  must display the following acknowledgement:
**  This product includes software (iNetBoss) developed by Tsinghua University, PRC and its contributors.
** THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
** ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
** IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
** ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
** FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
** DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
** OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
** HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
** LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
** OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
** SUCH DAMAGE.
*
*/
public class DateUtil {
    //用来全局控制 上一周，本周，下一周的周数变化    
    private  int weeks = 0;  
    public static String getCurrentTime() {
		Timestamp ts = new Timestamp(Calendar.getInstance().getTimeInMillis());
		return ts.toString().substring(0, 19);
	}
	 /**   
     * 得到二个日期间的间隔天数   
     */   
	 public static String getTwoDay(String sj1, String sj2) {    
	     SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");    
	     long day = 0;    
	     try {    
	      java.util.Date date = myFormatter.parse(sj1);    
	      java.util.Date mydate = myFormatter.parse(sj2);    
	      day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);    
	     } catch (Exception e) {    
	      return "";    
	     }    
	     return day + "";    
	 }    
	
	
	 /**   
	     * 根据一个日期，返回是星期几的字符串   
	     *    
	     * @param sdate   
	     * @return   
	     */   
	 public static String getWeek(String sdate) {    
	     // 再转换为时间    
	     Date date = DateUtil.strToDate(sdate);    
	     Calendar c = Calendar.getInstance();    
	     c.setTime(date);    
	     // int hour=c.get(Calendar.DAY_OF_WEEK);    
	     // hour中存的就是星期几了，其范围 1~7    
	     // 1=星期日 7=星期六，其他类推    
	     return new SimpleDateFormat("EEEE").format(c.getTime());    
	 }    
	
	 /**   
	     * 将短时间格式字符串转换为时间 yyyy-MM-dd    
	     *    
	     * @param strDate   
	     * @return   
	     */   
	 public static Date strToDate(String strDate) {    
	     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");    
	     ParsePosition pos = new ParsePosition(0);    
	     Date strtodate = formatter.parse(strDate, pos);    
	     return strtodate;    
	 }    
	
	 /**   
	     * 两个时间之间的天数   
	     *    
	     * @param date1   
	     * @param date2   
	     * @return   
	     */   
	 public static long getDays(String date1, String date2) {    
	     if (date1 == null || date1.equals(""))    
	      return 0;    
	     if (date2 == null || date2.equals(""))    
	      return 0;    
	     // 转换为标准时间    
	     SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");    
	     java.util.Date date = null;    
	     java.util.Date mydate = null;    
	     try {    
	      date = myFormatter.parse(date1);    
	      mydate = myFormatter.parse(date2);    
	     } catch (Exception e) {    
	     }    
	     long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);    
	     return day;    
	 }    
	     
	 // 计算当月最后一天,返回字符串    
	 public String getDefaultDay(){      
	    String str = "";    
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");        
	
	    Calendar lastDate = Calendar.getInstance();    
	    lastDate.set(Calendar.DATE,1);//设为当前月的1号    
	    lastDate.add(Calendar.MONTH,1);//加一个月，变为下月的1号    
	    lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天    
	        
	    str=sdf.format(lastDate.getTime());    
	    return str;      
	 }    
	     
	 // 上月第一天    
	 public String getPreviousMonthFirst(){      
	    String str = "";    
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");        
	
	    Calendar lastDate = Calendar.getInstance();    
	    lastDate.set(Calendar.DATE,1);//设为当前月的1号    
	    lastDate.add(Calendar.MONTH,-1);//减一个月，变为下月的1号    
	    //lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天    
	        
	    str=sdf.format(lastDate.getTime());    
	    return str;      
	 }    
	     
	 //获取当月第一天    
	 public String getFirstDayOfMonth(){      
	    String str = "";    
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");        
	
	    Calendar lastDate = Calendar.getInstance();    
	    lastDate.set(Calendar.DATE,1);//设为当前月的1号    
	    str=sdf.format(lastDate.getTime());    
	    return str;      
	 }    
	     
	 // 获得本周星期日的日期      
	 public String getCurrentWeekday() {    
	     weeks = 0;    
	     int mondayPlus = this.getMondayPlus();    
	     GregorianCalendar currentDate = new GregorianCalendar();    
	     currentDate.add(GregorianCalendar.DATE, mondayPlus+6);    
	     Date monday = currentDate.getTime();    
	         
	     DateFormat df = DateFormat.getDateInstance();    
	     String preMonday = df.format(monday);    
	     return preMonday;    
	 }    
	     
	     
	 //获取当天时间     
	 public String getNowTime(String dateformat){    
	     Date   now   =   new   Date();       
	     SimpleDateFormat   dateFormat   =   new   SimpleDateFormat(dateformat);//可以方便地修改日期格式       
	     String  hehe  = dateFormat.format(now);       
	     return hehe;    
	 }    
	     
	 // 获得当前日期与本周日相差的天数    
	 private int getMondayPlus() {    
	     Calendar cd = Calendar.getInstance();    
	     // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......    
	     int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK)-1;         //因为按中国礼拜一作为第一天所以这里减1    
	     if (dayOfWeek == 1) {    
	         return 0;    
	     } else {    
	         return 1 - dayOfWeek;    
	     }    
	 }    
	     
	 //获得本周一的日期    
	 public String getMondayOFWeek(){    
	      weeks = 0;    
	      int mondayPlus = this.getMondayPlus();    
	      GregorianCalendar currentDate = new GregorianCalendar();    
	      currentDate.add(GregorianCalendar.DATE, mondayPlus);    
	      Date monday = currentDate.getTime();    
	          
	      DateFormat df = DateFormat.getDateInstance();    
	      String preMonday = df.format(monday);    
	      return preMonday;    
	 }    
	     
	//获得相应周的周六的日期    
	 public String getSaturday() {    
	     int mondayPlus = this.getMondayPlus();    
	     GregorianCalendar currentDate = new GregorianCalendar();    
	     currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);    
	     Date monday = currentDate.getTime();    
	     DateFormat df = DateFormat.getDateInstance();    
	     String preMonday = df.format(monday);    
	     return preMonday;    
	 }    
	     
	// 获得上周星期日的日期    
	 public String getPreviousWeekSunday() {    
	     weeks=0;    
	     weeks--;    
	     int mondayPlus = this.getMondayPlus();    
	     GregorianCalendar currentDate = new GregorianCalendar();    
	     currentDate.add(GregorianCalendar.DATE, mondayPlus+weeks);    
	     Date monday = currentDate.getTime();    
	     DateFormat df = DateFormat.getDateInstance();    
	     String preMonday = df.format(monday);    
	     return preMonday;    
	 }    
	
	// 获得上周星期一的日期    
	 public String getPreviousWeekday() {    
	     weeks--;    
	     int mondayPlus = this.getMondayPlus();    
	     GregorianCalendar currentDate = new GregorianCalendar();    
	     currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);    
	     Date monday = currentDate.getTime();    
	     DateFormat df = DateFormat.getDateInstance();    
	     String preMonday = df.format(monday);    
	     return preMonday;    
	 }    
	     
	 // 获得下周星期一的日期    
	 public String getNextMonday() {    
	     weeks++;    
	     int mondayPlus = this.getMondayPlus();    
	     GregorianCalendar currentDate = new GregorianCalendar();    
	     currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);    
	     Date monday = currentDate.getTime();    
	     DateFormat df = DateFormat.getDateInstance();    
	     String preMonday = df.format(monday);    
	     return preMonday; 
	}
	/*****************************************
     * @功能     判断某年是否为闰年
     * @return boolean
     * @throws ParseException
     ****************************************/
    public boolean isLeapYear(int yearNum){
    boolean isLeep = false;
        /**判断是否为闰年，赋值给一标识符flag*/
        if((yearNum % 4 == 0) && (yearNum % 100 != 0)){
        isLeep = true;
        } else if(yearNum % 400 ==0){
        isLeep = true;
        } else {
        isLeep = false;
        }
        return isLeep;
    }
    /*****************************************
     * @功能     计算指定日期某年的第几周
     * @return interger
     * @throws ParseException
     ****************************************/
    public int getWeekNumOfYearDay(String strDate ) throws ParseException{
	    Calendar calendar = Calendar.getInstance();
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    Date curDate = format.parse(strDate);
	    calendar.setTime(curDate);
	    int iWeekNum = calendar.get(Calendar.WEEK_OF_YEAR);
	    return iWeekNum;
    }
    /*****************************************
     * @功能     计算某年某周的开始日期
     * @return interger
     * @throws ParseException
     ****************************************/
    public String getYearWeekFirstDay(int yearNum,int weekNum) throws ParseException {
	    Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.YEAR, yearNum);
	    cal.set(Calendar.WEEK_OF_YEAR, weekNum);
	    cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	    //分别取得当前日期的年、月、日
	    String tempYear = Integer.toString(yearNum);
	    String tempMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
	    String tempDay = Integer.toString(cal.get(Calendar.DATE));
	    if(tempMonth.length()==1){
	    	tempMonth = "0" + tempMonth;
	    }
	    if(tempDay.length()==1){
	    	tempDay = "0" + tempDay;
	    }
	    String tempDate = tempYear + tempMonth + tempDay;
	    return tempDate;
    }
    /*****************************************
     * @功能     计算某年某周的结束日期
     * @return interger
     * @throws ParseException
     ****************************************/
    public String getYearWeekEndDay(int yearNum,int weekNum) throws ParseException {
	    Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.YEAR, yearNum);
	    cal.set(Calendar.WEEK_OF_YEAR, weekNum + 1);
	    cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
	        //分别取得当前日期的年、月、日
	    String tempYear = Integer.toString(yearNum);
	    String tempMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
	    String tempDay = Integer.toString(cal.get(Calendar.DATE));
	    if(tempMonth.length()==1){
	    	tempMonth = "0" + tempMonth;
	    }
	    if(tempDay.length()==1){
	    	tempDay = "0" + tempDay;
	    }
	    String tempDate = tempYear + tempMonth + tempDay;
	    return tempDate;
    }
    /*****************************************
     * @功能     计算某年某月的开始日期
     * @return interger
     * @throws ParseException
     ****************************************/
    public String getYearMonthFirstDay(int yearNum,int monthNum) throws ParseException {
	    //分别取得当前日期的年、月、日
	    String tempYear = Integer.toString(yearNum);
	    String tempMonth = Integer.toString(monthNum);
	    String tempDay = "1";
	    String tempDate = tempYear + "-" +tempMonth + "-" + tempDay;
	    return SetDateFormat(tempDate,"yyyy-MM-dd");
    }
    /*****************************************
     * @功能     计算某年某月的结束日期
     * @return interger
     * @throws ParseException
     ****************************************/
    public String getYearMonthEndDay(int yearNum,int monthNum) throws ParseException {
       //分别取得当前日期的年、月、日
	    String tempYear = Integer.toString(yearNum);
	    String tempMonth = Integer.toString(monthNum);
	    String tempDay = "31";
	    if (tempMonth.equals("1") || tempMonth.equals("3") || tempMonth.equals("5") || tempMonth.equals("7") ||tempMonth.equals("8") || tempMonth.equals("10") ||tempMonth.equals("12")) {
	       tempDay = "31";
	    }
	    if (tempMonth.equals("4") || tempMonth.equals("6") || tempMonth.equals("9")||tempMonth.equals("11")) {
	       tempDay = "30";
	    }
	    if (tempMonth.equals("2")) {
	       if (isLeapYear(yearNum)) {
	        tempDay = "29";
	       } else {
	         tempDay = "28";
	       }
	    }
	    //System.out.println("tempDay:" + tempDay);
	    String tempDate = tempYear + "-" +tempMonth + "-" + tempDay;
	    return SetDateFormat(tempDate,"yyyy-MM-dd");
    }
    /**
     * @see     取得指定时间的给定格式()
     * @return String
     * @throws ParseException
     */
    public String SetDateFormat(String myDate,String strFormat) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
        String sDate = sdf.format(sdf.parse(myDate));

        return sDate;
    }
    public String FormatDateTime(String strDateTime, String strFormat){
        String sDateTime = strDateTime;
        try {
            Calendar Cal = parseDateTime(strDateTime);
            SimpleDateFormat sdf = null;
            sdf = new SimpleDateFormat(strFormat);
            sDateTime = sdf.format(Cal.getTime());
        } catch (Exception e) {

        }
        return sDateTime;
    }
    public static Calendar parseDateTime(String baseDate){
        Calendar cal = null;
        cal = new GregorianCalendar();
        int yy = Integer.parseInt(baseDate.substring(0, 4));
        int mm = Integer.parseInt(baseDate.substring(5, 7)) - 1;
        int dd = Integer.parseInt(baseDate.substring(8, 10));
        int hh = 0;
        int mi = 0;
        int ss = 0;
        if(baseDate.length() > 12)
        {
            hh = Integer.parseInt(baseDate.substring(11, 13));
            mi = Integer.parseInt(baseDate.substring(14, 16));
            ss = Integer.parseInt(baseDate.substring(17, 19));
        }
        cal.set(yy, mm, dd, hh, mi, ss);
        return cal;
    }
	/**
	 * 得到指定日期的前一天
	 * 
	 * @param date 日期
	 * @return 传入日期的前一天
	 */
	public static Date preDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, -1);
		date = (Date) c.getTime();
		return date;
	}
	/**
	 * 输出字符串类型的格式化日期
	 * 
	 * @param dt Date
	 * @param pattern 时间格式
	 * @return sDate
	 */
	public static String getFormatDate(Date dt, String pattern) {
		String sDate;
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		sDate = formatter.format(dt);
		return sDate;
	}

	public static void main(String[] args) throws Exception{    
	        DateUtil tt = new DateUtil();    
	        Date yesterday = tt.preDay(new Date());
	        System.out.println("获取当天日期:"+tt.getFormatDate(yesterday, "yyyyMMdd"));    
//	        System.out.println("获取当天日期:"+tt.getNowTime("yyyy-MM-dd"));    
//	        System.out.println("获取当天日期:"+tt.getNowTime("yyyyMMdd")); 
//	        System.out.println("获取本周一日期:"+tt.getMondayOFWeek());    
//	        System.out.println("获取本周日的日期~:"+tt.getCurrentWeekday());    
//	        System.out.println("获取上周一日期:"+tt.getPreviousWeekday());    
	        System.out.println("获取上周日日期:"+tt.getPreviousWeekSunday());    
	        System.out.println("获取下周一日期:"+tt.getNextMonday());    
	        System.out.println("获得相应周的周六的日期:"+tt.getNowTime("yyyy-MM-dd"));    
	        System.out.println("获取本月第一天日期:"+tt.getFirstDayOfMonth());    
	        System.out.println("获取本月最后一天日期:"+tt.getDefaultDay());    
	        System.out.println("获取上月第一天日期:"+tt.getPreviousMonthFirst()); 
	        System.out.println("获取某年某周的日期："+tt.getYearWeekFirstDay(2010, 10));
	        System.out.println("获取两个日期之间间隔天数2008-12-1~2008-9.29:"+DateUtil.getTwoDay("2008-12-1","2008-9-29"));    
	    }    
}
