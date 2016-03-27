package com.base.model;

// default package

import java.util.Date;

/**
 * Score entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Score implements java.io.Serializable {

	// Fields

	private Long id;
	private Long user;
	private Date time;
	private Double score;

	// Constructors

	/** default constructor */
	public Score() {
	}

	/** minimal constructor */
	public Score(Long id, Date time) {
		this.id = id;
		this.time = time;
	}

	/** full constructor */
	public Score(Long id, Long user, Date time, Double score) {
		this.id = id;
		this.user= user;
		this.time = time;
		this.score = score;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUser() {
		return this.user;
	}

	public void setUser(Long User) {
		this.user = User;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

}