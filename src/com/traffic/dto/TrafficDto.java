package com.traffic.dto;

public class TrafficDto {
		private int port;
		private int  bytes;
		private String pkts;
		private int protocolNum;
		private String portName;
		private String statTime;
		private String duration;
		private String srcIp;
		private String descIp;
		private String srcPort;
		private String descPort;
		private int flows;
		
		public int getPort() {
			return port;
		}
		public void setPort(int port) {
			this.port = port;
		}
		public int getBytes() {
			return bytes;
		}
		public void setBytes(int bytes) {
			this.bytes = bytes;
		}
		public String getPkts() {
			return pkts;
		}
		public void setPkts(String pkts) {
			this.pkts = pkts;
		}
		public int getProtocolNum() {
			return protocolNum;
		}
		public void setProtocolNum(int protocolNum) {
			this.protocolNum = protocolNum;
		}
		public String getPortName() {
			return portName;
		}
		public void setPortName(String portName) {
			this.portName = portName;
		}
		public String getStatTime() {
			return statTime;
		}
		public void setStatTime(String statTime) {
			this.statTime = statTime;
		}
		public String getDuration() {
			return duration;
		}
		public void setDuration(String duration) {
			this.duration = duration;
		}
		public String getSrcIp() {
			return srcIp;
		}
		public void setSrcIp(String srcIp) {
			this.srcIp = srcIp;
		}
		public String getDescIp() {
			return descIp;
		}
		public void setDescIp(String descIp) {
			this.descIp = descIp;
		}
		public String getSrcPort() {
			return srcPort;
		}
		public void setSrcPort(String srcPort) {
			this.srcPort = srcPort;
		}
		public String getDescPort() {
			return descPort;
		}
		public void setDescPort(String descPort) {
			this.descPort = descPort;
		}
		public int getFlows() {
			return flows;
		}
		public void setFlows(int flows) {
			this.flows = flows;
		}
	}
