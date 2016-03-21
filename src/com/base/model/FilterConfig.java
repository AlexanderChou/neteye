package com.base.model;

//default package

import java.util.HashSet;
import java.util.Set;

/**
 * Priority entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class FilterConfig implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String eventmodel;
	private String eventtype;
	private String ip;

	public String getEventmodel() {
		return eventmodel;
	}
	public void setEventmodel(String eventmodel) {
		this.eventmodel = eventmodel;
	}
	public String getEventtype() {
		return eventtype;
	}
	public void setEventtype(String eventtype) {
		this.eventtype = eventtype;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getId() {
		return id;
	}
	
	
	

	// Constructors

	
}