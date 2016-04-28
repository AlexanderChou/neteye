package com.tdrouting.dto;

public class Interface implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String ipv6;
	private String ipv4;
	private String routername;
	
	public Interface() 
	{}
	
	public Interface(String _name, String _ipv6, String _ipv4, String _routername)
	{
		this.name = _name;
		this.ipv6 = _ipv6;
		this.ipv4 = _ipv4;
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
	
	public void setIpv4(String _ipv4) 
	{
		this.ipv4 = _ipv4;
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
	
	public String getIpv4()
	{
		return this.ipv4;
	}
	
	public String getRoutername()
	{
		return this.routername;
	}
}
