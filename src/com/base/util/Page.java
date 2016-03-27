package com.base.util;

public class Page {
	
	//当前记录数
	private int recordIndex;
	
	//每页个数
	private int pageSize = 15;
	
	private int totalRecords;


	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	//无参构造函数
	public Page(){

	}
	
	public int getRecordIndex() {
		return recordIndex;
	}

	public void setRecordIndex(int recordIndex) {
		this.recordIndex = recordIndex;
	}

	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}	
}