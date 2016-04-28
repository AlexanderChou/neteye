package com.tdrouting.dto;

import org.apache.struts2.json.annotations.JSON;

public class Link {
	private Integer id;
	private Integer from;
	private Integer to;
	private String fromprefix;
	private String toprefix;
	
	public Link() {}
	
	public Link(Integer _from, Integer _to, String _fromprefix, String _toprefix)
	{
		this.from = _from;
		this.to = _to;
		this.fromprefix = _fromprefix;
		this.toprefix = _toprefix;
	}
	
	@JSON(serialize = false)
	public Integer getId()
	{
		return this.id;
	}
	
	public String getName()
	{
		return "";
	}
	
	public Integer getFrom()
	{
		return this.from;
	}
	
	public Integer getTo()
	{
		return this.to;
	}
	
	public String getFromprefix()
	{
		return this.fromprefix;
	}
	
	public String getToprefix()
	{
		return this.toprefix;
	}
	
	public void setId(Integer _id)
	{
		this.id = _id;
	}
	
	public void setFrom(Integer _from) 
	{
		this.from = _from;
	}
	
	public void setTo(Integer _to) 
	{
		this.to = _to;
	}
	
	public void setFromprefix(String _fromprefix)
	{
		this.fromprefix = _fromprefix;
	}
	
	public void setToprefix(String _toprefix) {
		this.toprefix = _toprefix;
	}
}