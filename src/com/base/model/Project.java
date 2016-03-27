package com.base.model;

/**
 * Project entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Project implements java.io.Serializable {

	// Fields

	private Long id;
	private String name;
	private String description;


	// Constructors

	/** default constructor */
	public Project() {
	}

	/** minimal constructor */
	public Project(Long id) {
		this.id = id;
	}

	/** full constructor */
	public Project(Long id, String name, String desc) {
		this.id = id;
		this.name = name;
		this.description = description;
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}