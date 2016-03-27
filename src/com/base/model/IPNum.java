package com.base.model;



import com.base.model.BaseEntity;

public class IPNum extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String engName;
	private String ChineseName;
	private String dateStr;//日期
	private long dayNum;//节点前一天出现的IP地址数
	private long monthNum;//节点这个月出现的IP地址数
	private long totalNum;//节点截止到目前出现的IP地址数
	
	public String getEngName() {
		return engName;
	}
	public void setEngName(String engName) {
		this.engName = engName;
	}
	
	public long getDayNum() {
		return dayNum;
	}
	public void setDayNum(long dayNum) {
		this.dayNum = dayNum;
	}
	public long getMonthNum() {
		return monthNum;
	}
	public void setMonthNum(long monthNum) {
		this.monthNum = monthNum;
	}
	public long getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(long totalNum) {
		this.totalNum = totalNum;
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public String getChineseName() {
		return ChineseName;
	}
	public void setChineseName(String chineseName) {
		ChineseName = chineseName;
	}
	
}
