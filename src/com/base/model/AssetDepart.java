package com.base.model;

//default package

import java.util.HashSet;
import java.util.Set;

/**
 * Priority entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AssetDepart implements java.io.Serializable {

	// Fields
    private long id;
	private String name;
	private String departcode;
	private String address;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setDepartcode(String departcode) {
		this.departcode = departcode;
	}
	public String getDepartcode() {
		return departcode;
	}

	// Constructors

	
}
