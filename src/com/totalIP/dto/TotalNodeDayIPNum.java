package com.totalIP.dto;

import java.io.Serializable;



public class TotalNodeDayIPNum implements Serializable{
	private String day;
	private String IPNum;
	
	
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getIPNum() {
		return IPNum;
	}
	public void setIPNum(String num) {
		IPNum = num;
	}
	
}
