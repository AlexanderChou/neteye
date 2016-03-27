package com.netflow.dto;

import java.io.Serializable;
import java.util.List;

public class TopNTotalData implements Serializable{
	private List resultData;
	private String picName;
	public List getResultData() {
		return resultData;
	}
	public void setResultData(List resultData) {
		this.resultData = resultData;
	}
	public String getPicName() {
		return picName;
	}
	public void setPicName(String picName) {
		this.picName = picName;
	}
}
