package com.tdrouting.dto;

public class Edge {
	private String from;
	private String to; 
	
	public Edge() {}
	
	public Edge(String _from, String _to)
	{
		this.from = _from;
		this.to = _to;
	}
	
	public String getFrom()
	{
		return this.from;
	}
	
	public String getTo()
	{
		return this.to;
	}
	
	public void setFrom(String _from) 
	{
		this.from = _from;
	}
	
	public void setTo(String _to) 
	{
		this.to = _to;
	}
}
