package com.mysavi.model;

public class SaviSend implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Boolean enable;
	private Integer preference;
	private Integer tentLt;
	private Integer defaultLt;
	
	public SaviSend() {}

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

	public Integer getTentLt() {
		return tentLt;
	}

	public void setTentLt(Integer tentLt) {
		this.tentLt = tentLt;
	}

	public Integer getDefaultLt() {
		return defaultLt;
	}

	public void setDefaultLt(Integer defaultLt) {
		this.defaultLt = defaultLt;
	}
}
