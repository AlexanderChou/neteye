package com.base.model;



import com.base.model.BaseEntity;


public class NodeReachability extends BaseEntity{

	private static final long serialVersionUID = 1;
	private int network_type;
	private String region;
	private String time;
	private int interruption;
	
	public int getNetwork_type() {
		return network_type;
	}
	public void setNetwork_type(int network_type) {
		this.network_type = network_type;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getInterruption() {
		return interruption;
	}
	public void setInterruption(int interruption) {
		this.interruption = interruption;
	}

	
	
}