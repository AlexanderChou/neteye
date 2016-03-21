package com.savi.show.dto;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;



public class HuaSanBindingTableInfo {
	private Long id;
	private Integer ipAddressType;
	private String apName;
	private String ipAddress;
	private String macAddress;
	private String user;
	private Integer bindingState;
	private List<Long> lifeTime = new LinkedList<Long>();//以秒为单位

	public HuaSanBindingTableInfo(){
		
	}
	Date dt=new Date();
	long lSysTime = (long) (dt.getTime() / 1000); 
	
	public HuaSanBindingTableInfo(HuaSanSaviFilterTableCurInfo savi, Long maxLifetime, String user){
		this.id = savi.getId();
		this.ipAddress = savi.getIpAddress();
		this.ipAddressType = savi.getIpAddressType();
		this.apName = savi.getApName();
		this.macAddress = savi.getMacAddress();
		this.user = user;
		if(savi.getCount()!=null){
			Long life=lSysTime-savi.getCount()/1000;
			lifeTime.add(life);
		}
		System.out.println("timenow::::"+lSysTime);
		System.out.println("time::::"+savi.getCount());
		lifeTime.add(lSysTime-maxLifetime/1000);
	}
	
	public HuaSanBindingTableInfo(HuaSanSaviFilterTableCurInfo savi, Long maxLifetime){
		this.id = savi.getId();
		this.ipAddress = savi.getIpAddress();
		this.ipAddressType = savi.getIpAddressType();
		this.apName = savi.getApName();
		this.macAddress = savi.getMacAddress();
		if(savi.getCount()!=null){
			Long life=lSysTime-savi.getCount()/1000;
			lifeTime.add(life);
		}
		System.out.println("timenow::::"+lSysTime);
		System.out.println("time::::"+savi.getCount());
		lifeTime.add(lSysTime-maxLifetime/1000);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApName() {
		return apName;
	}

	public void setApName(String apName) {
		this.apName = apName;
	}

	public Integer getIpAddressType() {
		return ipAddressType;
	}

	public void setIpAddressType(Integer ipAddressType) {
		this.ipAddressType = ipAddressType;
	}



	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Integer getBindingState() {
		return bindingState;
	}

	public void setBindingState(Integer bindingState) {
		this.bindingState = bindingState;
	}

	public List<Long> getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(List<Long> lifeTime) {
		this.lifeTime = lifeTime;
	}


	

	
	

}
