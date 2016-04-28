package com.tdrouting.dto;

import org.apache.struts2.json.annotations.JSON;

public class Router implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Integer type;
	private String netconfuser;
	private String netconfpasswd;
	private String netconfipv4;
	private Integer netconfport;
	private String netconfstatus;
	
	public Router()
	{}
	
	public Router(String _name, Integer _type, String _netconfuser, String _netconfpasswd, String _netconfipv4, Integer _netconfport, String _status)
	{
		this.name = _name;
		this.type = _type;
		this.netconfuser = _netconfuser;
		this.netconfpasswd = _netconfpasswd;
		this.netconfipv4 = _netconfipv4;
		this.netconfport = _netconfport;
		this.netconfstatus = _status;
	}
	public void setId(Integer _id)
	{
		this.id = _id;
	}
	
	public void setName(String _name) 
	{
		this.name = _name;
	}
	
	
	public void setType(Integer _type)
	{
		this.type = _type;
	}
	
	public void setNetconfuser(String _netconfuser)
	{
		this.netconfuser = _netconfuser;
	}
	
	public void setNetconfpasswd(String _netconfpasswd)
	{
		this.netconfpasswd = _netconfpasswd;
	}
	
	public void setNetconfipv4(String _netconfipv4)
	{
		this.netconfipv4 = _netconfipv4;
	}
	
	public void setNetconfport(Integer _netconfport)
	{
		this.netconfport = _netconfport;
	}
	
	public void setNetconfstatus(String _status)
	{
		this.netconfstatus = _status;
	}
	
	public Integer getId()
	{
		return this.id;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public Integer getType()
	{
		return this.type;
	}
	
	public String getNetconfuser()
	{
		return this.netconfuser;
	}
	
	@JSON(serialize = false)
	public String getNetconfpasswd()
	{
		return this.netconfpasswd;
	}
	
	public String getNetconfipv4()
	{
		return this.netconfipv4;
	}
	
	public Integer getNetconfport()
	{
		return this.netconfport;
	}
	
	public String getNetconfstatus()
	{
		return this.netconfstatus;
	}
}