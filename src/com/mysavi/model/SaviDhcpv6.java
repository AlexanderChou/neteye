package com.mysavi.model;

public class SaviDhcpv6 implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Boolean enable;
	private Integer preference;
	private Integer maxDhcpResponsetime;
	private Integer maxLeasequeryDelay;
	private Integer datasnoopingInterval;
	private Integer offlinkDelay;
	private Integer detectionTimeout;
	
	public SaviDhcpv6(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Integer getPreference() {
		return preference;
	}

	public void setPreference(Integer preference) {
		this.preference = preference;
	}

	public Integer getMaxDhcpResponsetime() {
		return maxDhcpResponsetime;
	}

	public void setMaxDhcpResponsetime(Integer maxDhcpResponsetime) {
		this.maxDhcpResponsetime = maxDhcpResponsetime;
	}

	public Integer getMaxLeasequeryDelay() {
		return maxLeasequeryDelay;
	}

	public void setMaxLeasequeryDelay(Integer maxLeasequeryDelay) {
		this.maxLeasequeryDelay = maxLeasequeryDelay;
	}

	public Integer getDatasnoopingInterval() {
		return datasnoopingInterval;
	}

	public void setDatasnoopingInterval(Integer datasnoopingInterval) {
		this.datasnoopingInterval = datasnoopingInterval;
	}

	public Integer getOfflinkDelay() {
		return offlinkDelay;
	}

	public void setOfflinkDelay(Integer offlinkDelay) {
		this.offlinkDelay = offlinkDelay;
	}

	public Integer getDetectionTimeout() {
		return detectionTimeout;
	}

	public void setDetectionTimeout(Integer detectionTimeout) {
		this.detectionTimeout = detectionTimeout;
	}
}