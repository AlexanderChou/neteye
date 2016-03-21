package com.totalIP.action;

import java.util.List;

import com.base.model.NodeIPDayNum;
import com.base.model.NodeIPHourNum;
import com.base.model.NodeIPMonthNum;
import com.base.model.NodeIPWeekNum;
import com.base.util.BaseAction;
import com.totalIP.dao.IPDayDao;
import com.totalIP.dao.IPHourDao;
import com.totalIP.dao.IPMonthDao;
import com.totalIP.dao.IPWeekDao;
import com.totalIP.util.NodeUtil;

/**
 * 获取某节点12小时/7天/4周/12个月每个时间段的IP地址数
 * 
 * @author hmg 节点页面Action
 */
public class NodeIPAction extends BaseAction {
	private String engName = "";
	private String chineseName = "";
	private String dateStr = "";
	private List<NodeIPHourNum> nodeDayIPNumList;
	private List<NodeIPDayNum> nodeWeekIPNumList;
	private List<NodeIPWeekNum> nodeMonthIPNumList;
	private List<NodeIPMonthNum> nodeYearIPNumList;

	public String execute() throws Exception {
		chineseName = NodeUtil.engToChinese(engName);
		return SUCCESS;
	}

	public String nodeHourIP() throws Exception {
		IPHourDao hourDao = new IPHourDao();
		nodeDayIPNumList = hourDao.readNode24HourIPNum("", engName);
		return SUCCESS;
	}

	public String nodeDayIP() throws Exception {
		IPDayDao dayDao = new IPDayDao();
		nodeWeekIPNumList = dayDao.readNode15DayIPNum("", engName);
		return SUCCESS;
	}

	public String nodeWeekIP() throws Exception {
		IPWeekDao weekDao = new IPWeekDao();
//		nodeMonthIPNumList = weekDao.readNode4WeekIPNum("20121105", engName);
		nodeMonthIPNumList = weekDao.readNode12WeekIPNum("", engName);
		return SUCCESS;
	}

	public String nodeMonthIP() throws Exception {
		IPMonthDao monthDao = new IPMonthDao();
		nodeYearIPNumList = monthDao.readNode12MonthIPNum("", engName);
		return SUCCESS;
	}

	public String getEngName() {
		return engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public List<NodeIPHourNum> getNodeDayIPNumList() {
		return nodeDayIPNumList;
	}

	public void setNodeDayIPNumList(List<NodeIPHourNum> nodeDayIPNumList) {
		this.nodeDayIPNumList = nodeDayIPNumList;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public List<NodeIPDayNum> getNodeWeekIPNumList() {
		return nodeWeekIPNumList;
	}

	public void setNodeWeekIPNumList(List<NodeIPDayNum> nodeWeekIPNumList) {
		this.nodeWeekIPNumList = nodeWeekIPNumList;
	}

	public List<NodeIPWeekNum> getNodeMonthIPNumList() {
		return nodeMonthIPNumList;
	}

	public void setNodeMonthIPNumList(List<NodeIPWeekNum> nodeMonthIPNumList) {
		this.nodeMonthIPNumList = nodeMonthIPNumList;
	}

	public List<NodeIPMonthNum> getNodeYearIPNumList() {
		return nodeYearIPNumList;
	}

	public void setNodeYearIPNumList(List<NodeIPMonthNum> nodeYearIPNumList) {
		this.nodeYearIPNumList = nodeYearIPNumList;
	}

}
