package com.totalIP.dto;

import java.util.List;

import com.base.model.NodeIPHourNum;

public class NodeHourIP {
	private List<NodeIPHourNum> IPNum;
	private String chineseName;
	
	public List<NodeIPHourNum> getIPNum() {
		return IPNum;
	}

	public void setIPNum(List<NodeIPHourNum> num) {
		IPNum = num;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	
	
}
