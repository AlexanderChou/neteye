package com.tdrouting.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.tdrouting.action.BaseAction;
import com.tdrouting.dao.TdroutingDao;


//By Alex. 2016/3/25 
public class TdroutingAction extends BaseAction{
	

	public String getRouterlist() throws Exception
	{
		TdroutingDao router = new TdroutingDao();
		List routerlist = router.getRouterlist();
		System.out.println("test");
		System.out.println(routerlist.size());
		return SUCCESS;
	}
}
