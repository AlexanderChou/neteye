package com.tdrouting.dto;

import com.sun.xml.internal.bind.v2.model.core.ID;

public class Interface implements java.io.Serializable{
	private Integer id;
	private String name;
	private String ipv6;
	private String routername;
	
	public Interface() 
	{}
	
	public Interface(String _name, String _ipv6, String _routername)
	{
		this.name = _name;
		this.ipv6 = _ipv6;
		this.routername = _routername;
	}
	
	public void setId(Integer _id)
	{
		this.id = _id;
	}
	
	public void setName(String _name) 
	{
		this.name = _name;
	}
	
	public void setIpv6(String _ipv6) 
	{
		this.ipv6 = _ipv6;
	}
	
	public void setRoutername(String _routername) 
	{
		this.routername = _routername;
	}
	
	public Integer getId()
	{
		return this.id;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getIpv6()
	{
		return this.ipv6;
	}
	
	public String getRoutername()
	{
		return this.routername;
	}
}
