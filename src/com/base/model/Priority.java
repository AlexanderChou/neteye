package com.base.model;

// default package

import java.util.HashSet;
import java.util.Set;

/**
 * Priority entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Priority implements java.io.Serializable {

	// Fields

	private Long id;
	private String name;
	private String color;


	// Constructors

	/** default constructor */
	public Priority() {
	}

	/** minimal constructor */
	public Priority(Long id) {
		this.id = id;
	}

	/** full constructor */
	public Priority(Long id, String name, String color) {
		this.id = id;
		this.name = name;
		this.color = color;
	
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}



}