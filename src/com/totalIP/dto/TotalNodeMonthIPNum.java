package com.totalIP.dto;

import java.io.Serializable;



public class TotalNodeMonthIPNum implements Serializable{
	private String month;
	private String IPNum;
	
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getIPNum() {
		return IPNum;
	}
	public void setIPNum(String num) {
		IPNum = num;
	}
	
}
