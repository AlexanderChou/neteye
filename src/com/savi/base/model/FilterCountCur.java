package com.savi.base.model;



public class FilterCountCur implements java.io.Serializable {

	// Fields

	private Long id;
	private Long acID;
	private Long servicePolicyID;
	private Integer ipVersion;
	private Long ifFilteringCount;
	private Long opTime;
	private Long endTime;
	
	
	
	// Constructors
	/** default constructor */
	public FilterCountCur() {
	}
	public FilterCountCur(FilterCountCur s) {
		this.id = s.getId();
		this.acID = s.getAcID();
		this.servicePolicyID = s.getServicePolicyID();
		this.ipVersion = s.getIpVersion();
		this.ifFilteringCount = s.getIfFilteringCount();
		this.opTime = s.getOpTime();
		this.endTime = s.getEndTime();
	
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAcID() {
		return acID;
	}
	public void setAcID(Long acID) {
		this.acID = acID;
	}
	public Long getServicePolicyID() {
		return servicePolicyID;
	}
	public void setServicePolicyID(Long servicePolicyID) {
		this.servicePolicyID = servicePolicyID;
	}
	public Integer getIpVersion() {
		return ipVersion;
	}
	public void setIpVersion(Integer ipVersion) {
		this.ipVersion = ipVersion;
	}
	public Long getIfFilteringCount() {
		return ifFilteringCount;
	}
	public void setIfFilteringCount(Long ifFilteringCount) {
		this.ifFilteringCount = ifFilteringCount;
	}
	public Long getOpTime() {
		return opTime;
	}
	public void setOpTime(Long opTime) {
		this.opTime = opTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	
	

}