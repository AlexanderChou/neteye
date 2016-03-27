package com.traffic.action;

import java.util.ArrayList;
import java.util.List;

import com.base.util.BaseAction;
import com.traffic.dto.TrafficDto;

public class TrafficStatAction extends BaseAction {
      private int number;
      private List statList;      

	public String portStatList() throws Exception {
		  statList =new ArrayList();
    	  String tmp=this.getRequest().getParameter("number");
    	  if(tmp!=null){
    	    number = Integer.parseInt(tmp);
    	  }else{
    		  number=20;
    	  }
    	  TrafficDto dto=new TrafficDto();
    	  dto.setBytes(1111111);
    	  dto.setPort(85);
    	  dto.setPkts("54321");
    	  statList.add(dto);
    	  return SUCCESS;
      }
		public String protocolStatList() throws Exception {
			  statList =new ArrayList();
	  	  String tmp=this.getRequest().getParameter("number");
	  	  if(tmp!=null){
	  	    number = Integer.parseInt(tmp);
	  	  }else{
	  		  number=20;
	  	  }
	  	  TrafficDto dto=new TrafficDto();
	  	  dto.setBytes(1111111);
	  	  dto.setPort(85);
	  	  dto.setPkts("54321");
	  	  dto.setPortName("ftp");
	  	  statList.add(dto);
	  	  return SUCCESS;
	    }
		public String sessionStatList() throws Exception {
			  statList =new ArrayList();
	  	  String tmp=this.getRequest().getParameter("number");
	  	  if(tmp!=null){
	  	    number = Integer.parseInt(tmp);
	  	  }else{
	  		  number=20;
	  	  }
	  	  TrafficDto dto=new TrafficDto();
	  	  dto.setBytes(1111111);
	  	  dto.setPort(85);
	  	  dto.setPkts("54321");
	  	  dto.setProtocolNum(51);
	  	  statList.add(dto);
	  	  return SUCCESS;
	    }
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public List getStatList() {
		return statList;
	}

	public void setStatList(List statList) {
		this.statList = statList;
	}
}
