package com.netflow.dto;

import java.io.Serializable;

public class TopNData implements Serializable{
	private String typename;
	private String key;
	private String bytes;
	private String pkts;
	private String srcip;
	private String dstip;
	private String srcport;
	private String protocol;
	private String dstport;
	private String starttime;
	private String duration;
	public String getSrcip() {
		return srcip;
	}
	public void setSrcip(String srcip) {
		this.srcip = srcip;
	}
	public String getDstip() {
		return dstip;
	}
	public void setDstip(String dstip) {
		this.dstip = dstip;
	}
	public String getSrcport() {
		return srcport;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public void setSrcport(String srcport) {
		this.srcport = srcport;
	}
	public String getDstport() {
		return dstport;
	}
	public void setDstport(String dstport) {
		this.dstport = dstport;
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
	private static final long serialVersionUID = 1L;
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	
}
