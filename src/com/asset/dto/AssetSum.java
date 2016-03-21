package com.asset.dto;

public class AssetSum{
	private static final long serialVersionUID = 1L;
	private String totalname;
	private Float totalmoney;
	private int totalnumber;
	private String groupname;

	public String getTotalname() {
		return totalname;
	}
	public void setTotalname(String totalname) {
		this.totalname = totalname;
	}
	public Float getTotalmoney() {
		return totalmoney;
	}
	public void setTotalmoney(Float totalmoney) {
		this.totalmoney = totalmoney;
	}
	public long getTotalnumber() {
		return totalnumber;
	}
	public void setTotalnumber(int totalnumber) {
		this.totalnumber = totalnumber;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
}
