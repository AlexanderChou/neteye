package com.totalIP.dto;

import java.io.Serializable;



public class TotalNodeWeekIPNum implements Serializable{
	private String week;
	private String IPNum;
	
	
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getIPNum() {
		return IPNum;
	}
	public void setIPNum(String num) {
		IPNum = num;
	}
	
}
