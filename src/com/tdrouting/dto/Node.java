package com.tdrouting.dto;

public class Node {
	private Integer id;
	private String name;
	
	public Node() {}
	
	public Node(Integer _id, String _name)
	{
		this.id = _id;
		this.name = _name;
	}
	
	public void setId(Integer _id)
	{
		this.id = _id;
	}
	
	public void setName(String _name) {
		this.name = _name;
	}
	
	public Integer getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
}
