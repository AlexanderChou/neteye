package com.base.model;

import com.base.model.BaseEntity;
public class NodeLoss extends BaseEntity{
	private static final int serialVersionUID = 1;
	private int network_type;
	private String region;
	private String time;
	private double loss;
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
	public double getLoss() {
		return loss;
	}
	public void setLoss(double loss) {
		this.loss = loss;
	}
}
