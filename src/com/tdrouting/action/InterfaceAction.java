package com.tdrouting.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.tdrouting.action.BaseAction;
import com.tdrouting.dao.InterfaceDao;
import com.tdrouting.dto.Interface;


//By Alex. 2016/3/25 
@SuppressWarnings("serial")
public class InterfaceAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private int count;
	private List<Interface> interfacelist;
	private String routername;
	
	@SuppressWarnings("unused")
	public String getInterfaces() throws Exception
	{
		InterfaceDao interfacedao = new InterfaceDao();
		interfacelist = interfacedao.getInterfaces(routername);
		count = interfacelist.size();
		return SUCCESS;
	}
	
	public int getCount()
	{
		return this.count;
	}
	
	public List<Interface> getInterfacelist()
	{
		return this.interfacelist;
	}
	
	public void setRoutername(String _routername)
	{
		this.routername = _routername;
	}
	
	public String getRoutername()
	{
		return this.routername;
	}
}
