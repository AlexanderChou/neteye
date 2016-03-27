package com.totalIP.action;

import java.util.Date;
import java.util.List;

import com.base.util.BaseAction;
import com.totalIP.dao.IPDayDao;
import com.totalIP.dao.IPHourDao;
import com.totalIP.dao.IPMonthDao;
import com.totalIP.dao.IPWeekDao;
import com.totalIP.dto.NodeOneDateNum;
import com.totalIP.dto.NodeTotalDateNum;

/**
 * @author hmg
 *获取每一个节点12小时/7天/4周/12个月总的IP地址数
 *饼图数据的Action
 */
public class EveryNodeTotalDateAction extends BaseAction {
	
	private String dateStr="";
	
	private List<NodeTotalDateNum> everyNodeTotalDateIP;
	private IPHourDao hourDao = new IPHourDao();
	private IPDayDao dayDao = new IPDayDao();
	private IPWeekDao weekDao = new IPWeekDao();
	private IPMonthDao monthDao = new IPMonthDao();
	
	public String everyNodeTotal12Hour() throws Exception{

		everyNodeTotalDateIP = hourDao.read25Node12Hour(dateStr);
//		everyNodeTotalDateIP = hourDao.readEveryNodeTotal12HourIPNum("2012110409");
		return SUCCESS;
	}
	public String everyNodeTotal7Day() throws Exception{
		everyNodeTotalDateIP = dayDao.read25Node7Day(dateStr);
		return SUCCESS;
	}
	public String everyNodeTotal4Week() throws Exception{
		everyNodeTotalDateIP = weekDao.read25Node4Week(dateStr);
//		everyNodeTotalDateIP = weekDao.readEveryNodeTotal4WeekIPNum("20121105");
		return SUCCESS;
	}
	public String everyNodeTotal12Month() throws Exception{
		everyNodeTotalDateIP = monthDao.read25Node12Month(dateStr);
		return SUCCESS;
	}
	
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public List<NodeTotalDateNum> getEveryNodeTotalDateIP() {
		return everyNodeTotalDateIP;
	}
	public void setEveryNodeTotalDateIP(List<NodeTotalDateNum> everyNodeTotalDateIP) {
		this.everyNodeTotalDateIP = everyNodeTotalDateIP;
	}
	
	
	
	
}
