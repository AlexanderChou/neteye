package com.totalIP.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.base.util.BaseAction;
import com.totalIP.dao.IPDayDao;
import com.totalIP.dao.IPHourDao;
import com.totalIP.dao.IPMonthDao;
import com.totalIP.dao.IPWeekDao;
import com.totalIP.dto.NodeOneDateNum;
import com.totalIP.dto.NodeTotalDateNum;

/**读取每个节点特定时间点IP地址数
 * @author hmg
 *24个节点页面折线图TotalNodeOneDateAction
 */
public class TotalNodeOneDateAction extends BaseAction {
	private String dateStr="";
	private List<NodeOneDateNum> totalNodeOneDate;
	
	
	
	
	

	public String totalNodeOneHour() throws Exception{
		
		IPHourDao hourDao = new IPHourDao();
		
		totalNodeOneDate = hourDao.readEveryNodeOneHourIPNum(dateStr);
//		totalNodeOneDate = hourDao.readEveryNodeOneHourIPNum("2012110409");
	
		return SUCCESS;
	}
	public String totalNodeOneDay() throws Exception{
		IPDayDao dayDao = new IPDayDao();
		totalNodeOneDate = dayDao.readEveryNodeOneDayIPNum(dateStr);
		
		return SUCCESS;
	}
	public String totalNodeOneWeek() throws Exception{
		IPWeekDao weekDao = new IPWeekDao();
		totalNodeOneDate = weekDao.readEveryNodeOneWeekIPNum(dateStr);
//		totalNodeOneDate = weekDao.readEveryNodeOneWeekIPNum("20121105");
		
		return SUCCESS;
	}
	public String totalNodeOneMonth() throws Exception{
		IPMonthDao monthDao = new IPMonthDao();
		totalNodeOneDate = monthDao.readEveryNodeOneMonthIPNum(dateStr);
			return SUCCESS;
	}
	
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public List<NodeOneDateNum> getTotalNodeOneDate() {
		return totalNodeOneDate;
	}
	public void setTotalNodeOneDate(List<NodeOneDateNum> totalNodeOneDate) {
		this.totalNodeOneDate = totalNodeOneDate;
	}
	
	
	
	
	
}
