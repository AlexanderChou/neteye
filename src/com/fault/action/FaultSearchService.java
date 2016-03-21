package com.fault.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.base.model.FaultCurrent;
import com.base.util.BaseAction;
import com.fault.dao.FaultSearch;
import com.fault.dto.FaultNode;

public class FaultSearchService extends BaseAction{
	private String startTime;
	private String endTime;
	private List faultList;
	private String totalCount;
	private String ip;
	private FaultSearch FaultSearch = new FaultSearch();
	
	public String faultTimeLists() throws Exception{
		startTime=this.getRequest().getParameter("startTime");
		endTime=this.getRequest().getParameter("endTime");
		Timestamp time1=Timestamp.valueOf(startTime+ " 00:00:00");
		Timestamp time2=Timestamp.valueOf(endTime+ " 23:59:59");
		String start = this.getRequest().getParameter("start");
		String limit = this.getRequest().getParameter("limit");
		
		List list=FaultSearch.getFaultByTime(time1, time2,start,limit);
		faultList=new ArrayList();
		if(list!=null){
			for(int i=0;i<list.size();i++){
				FaultCurrent current=(FaultCurrent) list.get(i);
				FaultNode node=new FaultNode();
				node.setId(current.getId());
				node.setIp(current.getFaultIp());
				node.setLoss("100");
				node.setRrt("~~");
				node.setBeginTime(current.getBeginTime());
				node.setPing("ping");
				node.setTraceroute("traceroute");
				faultList.add(node);
			}
		}
		totalCount = String.valueOf(FaultSearch.getFaultTimeCount(time1,time2));
		
		return SUCCESS;
	}
	public String faultIPLists() throws Exception{
		String start = this.getRequest().getParameter("start");
		String limit = this.getRequest().getParameter("limit");
		List list=FaultSearch.getFaultByIp(ip,start,limit);		
		totalCount = String.valueOf(FaultSearch.getFaultIpCount(ip));
		faultList=new ArrayList();
		if(list!=null){
			for(int i=0;i<list.size();i++){
				FaultCurrent current=(FaultCurrent) list.get(i);
				FaultNode node=new FaultNode();
				node.setId(current.getId());
				node.setIp(current.getFaultIp());
				node.setLoss("100");
				node.setRrt("~~");
				node.setBeginTime(current.getBeginTime());
				node.setPing("ping");
				node.setTraceroute("traceroute");
				faultList.add(node);
			}
		}
		return SUCCESS;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public List getFaultList() {
		return faultList;
	}
	public void setFaultList(List faultList) {
		this.faultList = faultList;
	}
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public FaultSearch getFaultSearch() {
		return FaultSearch;
	}
	public void setFaultSearch(FaultSearch faultSearch) {
		FaultSearch = faultSearch;
	}
	
}
