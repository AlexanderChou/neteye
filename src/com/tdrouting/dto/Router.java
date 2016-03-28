package com.tdrouting.dto;

import net.sf.jasperreports.engine.util.Java14BigDecimalHandler;

import org.apache.commons.beanutils.converters.IntegerArrayConverter;
import org.apache.commons.beanutils.converters.StringConverter;
//import org.apache.naming.java.javaURLContextFactory;
import org.apache.xpath.operations.String;

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
	
	public Router()
	{}
	
	public Router(Integer _id, String _name, Integer _type, String _netconfuser, String _netconfpasswd, String _netconfipv4, Integer _netconfport)
	{
		this.id = _id;
		this.name = _name;
		this.type = _type;
		this.netconfuser = _netconfuser;
		this.netconfpasswd = _netconfpasswd;
		this.netconfipv4 = _netconfipv4;
		this.netconfport = _netconfport;
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
	
	public Integer getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Integer getType()
	{
		return type;
	}
	
	public String getNetconfuser()
	{
		return netconfuser;
	}
	
	public String getNetconfpasswd()
	{
		return netconfpasswd;
	}
	
	public String getNetconfipv4()
	{
		return netconfipv4;
	}
	
	public Integer getNetconfport()
	{
		return netconfport;
	}
}