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

import javafx.scene.chart.PieChart.Data;

import org.apache.struts2.json.annotations.JSON;

import com.tdrouting.action.BaseAction;
import com.tdrouting.dao.RoutingtableDao;
import com.tdrouting.dao.TdroutingDao;
import com.tdrouting.dto.Router;
import com.tdrouting.util.CollectionTask;
import com.tdrouting.util.RegisterRouter;


//By Alex. 2016/3/25 
@SuppressWarnings("serial")
public class TdroutingAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private int count;
	private List<Router> routers;
	private Router router;
	private Boolean success = true;
	private Boolean failure = false;
	
	@SuppressWarnings("unused")
	public String getRouterlist() throws Exception
	{
		TdroutingDao router = new TdroutingDao();
		routers = router.getRouterlist();
		count = routers.size();
		return SUCCESS;
	}

	@JSON(serialize = false)
	public String addRouter() throws Exception
	{
		success = false;
		failure = true;
		RegisterRouter.register(router);
		success = true;
		failure = false;
		return SUCCESS;
	}
	
	public int getCount()
	{
		return this.count;
	}
	
	public void setRouter(Router _router)
	{
		this.router = _router;
	}
	
	public Router getRouter() {
		return this.router;
	}
	
	public List<Router> getRouters()
	{
		return this.routers;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isFailure() {
		return failure;
	}

	public void setFailure(boolean failure) {
		this.failure = failure;
	}
}