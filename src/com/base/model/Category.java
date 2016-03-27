package com.base.model;
/**
 * Category entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Category extends BaseEntity{
	private String name;
	private String description;

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