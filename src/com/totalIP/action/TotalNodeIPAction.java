package com.totalIP.action;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import com.base.util.BaseAction;
import com.totalIP.dao.IPDayDao;
import com.totalIP.dao.IPHourDao;
import com.totalIP.dao.IPMonthDao;
import com.totalIP.dao.IPWeekDao;
import com.totalIP.dao.TotalIPDao;
import com.totalIP.dto.TotalNodeAllDateIPNum;
import com.totalIP.dto.TotalNodeDayIPNum;
import com.totalIP.dto.TotalNodeHourIPNum;
import com.totalIP.dto.TotalNodeMonthIPNum;
import com.totalIP.dto.TotalNodeWeekIPNum;

/**读取所有节点最近12小时/7天/4周/12个月每个时间段IP地址数
 * @author hmg
 *首页折线图/列表TotalNodeIPAction
 *
 */
public class TotalNodeIPAction extends BaseAction {
	private String dateStr="";
	private List<TotalNodeHourIPNum> nodeHourIPNumList;
	private List<TotalNodeDayIPNum> nodeDayIPNumList;
	private List<TotalNodeWeekIPNum> nodeWeekIPNumList;
	private List<TotalNodeMonthIPNum> nodeMonthIPNumList;
	private List<TotalNodeAllDateIPNum> nodeAllDateIPNumlist;
	
	
	
	public String totalNodeHourIP() throws Exception{
		TotalNodeHourIPNum dto = null;
		//求出当前时间
		String startTime,endTime;
		Calendar calendar =Calendar.getInstance();
		endTime = MessageFormat.format("{0,date,yyyyMMddHH}" ,calendar.getTime());
		calendar.add(calendar.HOUR_OF_DAY, -12);
		startTime = MessageFormat.format("{0,date,yyyyMMddHH}" ,calendar.getTime());
		nodeHourIPNumList = new ArrayList<TotalNodeHourIPNum>();
		List<Object[]> objs = new IPHourDao().readTotalNode12HourIPNum(startTime,endTime);
		for(Object[] obj:objs){
			dto = new TotalNodeHourIPNum();
			dto.setHour(obj[1].toString());
			dto.setIPNum(obj[0].toString());
			nodeHourIPNumList.add(dto);
		}
		return SUCCESS;
	}
	public String totalNodeDayIP() throws Exception{
		TotalNodeDayIPNum dto = null;
		String startTime,endTime;
		Calendar calendar = Calendar.getInstance();
		endTime = MessageFormat.format("{0,date,yyyyMMdd}" ,calendar.getTime());
		calendar.add(calendar.DATE, -14);
		startTime = MessageFormat.format("{0,date,yyyyMMdd}" ,calendar.getTime());
		nodeDayIPNumList = new ArrayList<TotalNodeDayIPNum>();
		List<Object[]> objs = new IPDayDao().readTotalNode14DayIPNum(startTime,endTime);
		for(Object[] obj:objs){
			dto = new TotalNodeDayIPNum();
			dto.setDay(obj[1].toString());
			dto.setIPNum(obj[0].toString());
			nodeDayIPNumList.add(dto);
		}
		return SUCCESS;
	}
	public String totalNodeWeekIP() throws Exception{
		TotalNodeWeekIPNum dto = null;
		String startTime,endTime;
		Calendar calendar = Calendar.getInstance();
		endTime = MessageFormat.format("{0,date,yyyyMMdd}" ,calendar.getTime());
		calendar.add(calendar.WEEK_OF_MONTH, -12);
		startTime = MessageFormat.format("{0,date,yyyyMMdd}" ,calendar.getTime());
		nodeWeekIPNumList = new ArrayList<TotalNodeWeekIPNum>();
		List<Object[]> objs = new IPWeekDao().readTotalNode12WeekIPNum(startTime,endTime);
		for(Object[] obj:objs){
			dto = new TotalNodeWeekIPNum();
			dto.setIPNum(obj[0].toString());
			dto.setWeek(obj[1].toString());
			nodeWeekIPNumList.add(dto);
		}
		return SUCCESS;
	}
	public String totalNodeMonthIP() throws Exception{
		TotalNodeMonthIPNum dto = null;
		String startTime,endTime;
		Calendar calendar = Calendar.getInstance();
		endTime = MessageFormat.format("{0,date,yyyyMM}" ,calendar.getTime());
		calendar.add(calendar.MONTH, -12);
		startTime = MessageFormat.format("{0,date,yyyyMM}" ,calendar.getTime());
		nodeMonthIPNumList = new ArrayList<TotalNodeMonthIPNum>();
		List<Object[]> objs = new IPMonthDao().readTotalNode12MonthIPNum(startTime,endTime);
		for(Object[] obj:objs){
			dto = new TotalNodeMonthIPNum();
			dto.setIPNum(obj[0].toString());
			dto.setMonth(obj[1].toString());
			nodeMonthIPNumList.add(dto);
		}
		return SUCCESS;
	}
	public String nodeAllDate() throws Exception{
		TotalIPDao totalIPDao = new TotalIPDao();
		nodeAllDateIPNumlist = totalIPDao.readTotalNodeAllDateIPNum();

		return SUCCESS;
	}

	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public List<TotalNodeHourIPNum> getNodeHourIPNumList() {
		return nodeHourIPNumList;
	}
	public void setNodeHourIPNumList(List<TotalNodeHourIPNum> nodeHourIPNumList) {
		this.nodeHourIPNumList = nodeHourIPNumList;
	}
	public List<TotalNodeDayIPNum> getNodeDayIPNumList() {
		return nodeDayIPNumList;
	}
	public void setNodeDayIPNumList(List<TotalNodeDayIPNum> nodeDayIPNumList) {
		this.nodeDayIPNumList = nodeDayIPNumList;
	}
	public List<TotalNodeWeekIPNum> getNodeWeekIPNumList() {
		return nodeWeekIPNumList;
	}
	public void setNodeWeekIPNumList(List<TotalNodeWeekIPNum> nodeWeekIPNumList) {
		this.nodeWeekIPNumList = nodeWeekIPNumList;
	}
	public List<TotalNodeMonthIPNum> getNodeMonthIPNumList() {
		return nodeMonthIPNumList;
	}
	public void setNodeMonthIPNumList(List<TotalNodeMonthIPNum> nodeMonthIPNumList) {
		this.nodeMonthIPNumList = nodeMonthIPNumList;
	}
	public List<TotalNodeAllDateIPNum> getNodeAllDateIPNumlist() {
		return nodeAllDateIPNumlist;
	}
	public void setNodeAllDateIPNumlist(List<TotalNodeAllDateIPNum> nodeAllDateIPNumlist) {
		this.nodeAllDateIPNumlist = nodeAllDateIPNumlist;
	}
	
	public static void main(String[] args) throws Exception {
		new TotalNodeIPAction().totalNodeWeekIP();
		
	}
}
