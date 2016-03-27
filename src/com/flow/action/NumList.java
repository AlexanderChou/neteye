package com.flow.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.base.util.Constants;
import com.flow.dto.FlowListTemp;
import com.opensymphony.xwork2.ActionSupport;

public class NumList extends  ActionSupport{
	private List<FlowListTemp> numlist=new ArrayList();
	private String routerIP;
	private String ifIndex;
	
	private int totalCount;
	
	public String execute() throws Exception{
		String cmd = new String();
		cmd = "select_any_last1day.pl " + routerIP+"_"+ifIndex + ".rrd";
		Process ps=java.lang.Runtime.getRuntime().exec(cmd);  
		ps.waitFor();
		
		String resname = "file/flow/flowscan/dat/tmp/"+routerIP+"_"+ifIndex+".txt";
		File fixname = new File( Constants.webRealPath+resname );
		String text;
		if(fixname.exists()){	
			BufferedReader input = new BufferedReader( new FileReader( fixname ) );
			while ( ( text = input.readLine() ) != null )
			{ 
				if(!text.equals("")&&text.contains("|")){
					String a[]=text.split("\\|");
					FlowListTemp temp = new FlowListTemp();
					if(a[0]!=null){
					temp.setPic1(a[0]);}
					if(a[1]!=null){
					
					temp.setPic2(a[1]);}
					if(a[2]!=null){
					temp.setPic3(a[2]);}
					
					numlist.add(temp);
					
				    	
					
				}
				
			}
			
		}
		
		setTotalCount(numlist.size());
		
		
		return SUCCESS;	
	}



	public List<FlowListTemp> getNumlist() {
		return numlist;
	}



	public void setNumlist(List<FlowListTemp> numlist) {
		this.numlist = numlist;
	}




	public int getTotalCount() {
		return totalCount;
	}



	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}



	public String getRouterIP() {
		return routerIP;
	}



	public String getIfIndex() {
		return ifIndex;
	}



	public void setRouterIP(String routerIP) {
		this.routerIP = routerIP;
	}



	public void setIfIndex(String ifIndex) {
		this.ifIndex = ifIndex;
	}




}
