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
import com.tdrouting.dao.TdroutingDao;
import com.tdrouting.dto.Router;


//By Alex. 2016/3/25 
@SuppressWarnings("serial")
public class TdroutingAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private int count;
	private List<Router> routers;
	
	@SuppressWarnings("unused")
	public String getRouterlist() throws Exception
	{
		TdroutingDao router = new TdroutingDao();
		routers = router.getRouterlist();
		count = routers.size();
		return SUCCESS;
	}
	
	public int getCount()
	{
		return this.count;
	}
	
	public List<Router> getRouters()
	{
		return this.routers;
	}
}
