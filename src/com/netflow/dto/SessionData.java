package com.netflow.dto;

public class SessionData extends TopNData{
	private String srcip;
	private String destip;
	private String srcport;
	private String destport;
	private String protcol;
	private String starttime;
	private String duration;
	private String flows;
	private String bytes;
	private String pkts;
	public String getSrcip() {
		return srcip;
	}
	public void setSrcip(String srcip) {
		this.srcip = srcip;
	}
	public String getDestip() {
		return destip;
	}
	public void setDestip(String destip) {
		this.destip = destip;
	}
	public String getSrcport() {
		return srcport;
	}
	public void setSrcport(String srcport) {
		this.srcport = srcport;
	}
	public String getDestport() {
		return destport;
	}
	public void setDestport(String destport) {
		this.destport = destport;
	}
	public String getProtcol() {
		return protcol;
	}
	public void setProtcol(String protcol) {
		this.protcol = protcol;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getFlows() {
		return flows;
	}
	public void setFlows(String flows) {
		this.flows = flows;
	}
	public String getBytes() {
		return bytes;
	}
	public void setBytes(String bytes) {
		this.bytes = bytes;
	}
	public String getPkts() {
		return pkts;
	}
	public void setPkts(String pkts) {
		this.pkts = pkts;
	}
}
