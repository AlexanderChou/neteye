package com.totalIP.dto;

public class NodeReachabilityshow {
	private String region;
	private int interruption;
	private int connection;
	private double interrupt_rate;
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public int getInterruption() {
		return interruption;
	}
	public void setInterruption(int interruption) {
		this.interruption = interruption;
	}
	public int getConnection() {
		return connection;
	}
	public void setConnection(int connection) {
		this.connection = connection;
	}
	public double getInterrupt_rate() {
		return interrupt_rate;
	}
	public void setInterrupt_rate(double interrupt_rate) {
		this.interrupt_rate = interrupt_rate;
	}
}
