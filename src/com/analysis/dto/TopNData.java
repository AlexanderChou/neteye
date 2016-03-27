package com.analysis.dto;

import java.io.Serializable;

public class TopNData implements Serializable{
	private String typeName;
	private String userName;
	private double userValue;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public double getUserValue() {
		return userValue;
	}
	public void setUserValue(double userValue) {
		this.userValue = userValue;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public static void main(String[]args){
		double temp = ((double)(Integer.parseInt("187559"))/(1024*1024));
		System.out.println("temp="+temp);
		
	}
}
