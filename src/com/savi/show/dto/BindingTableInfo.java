package com.savi.show.dto;

import java.util.LinkedList;
import java.util.List;

import com.savi.base.model.Savibindingtablecur;

public class BindingTableInfo {
	private Integer id;
	private Integer ipAddressType;
	private Integer bindingType;
	private Integer ifIndex;
	private String switchName;
	private String ipAddress;
	private String macAddress;
	private String user;
	private Integer bindingState;
	private List<Integer> lifeTime = new LinkedList<Integer>();//以秒为单位
	private Integer isInFilteringTable;

	public BindingTableInfo(){
		
	}
	
	public BindingTableInfo(Savibindingtablecur savi, Integer maxLifetime, String user){
		this.id = savi.getId();
		this.ipAddress = savi.getIpAddress();
		this.ipAddressType = savi.getIpAddressType();
		this.bindingType = savi.getBindingType();
		this.ifIndex = savi.getIfIndex();
		this.switchName = savi.getIfinterfacecur().getSwitchcur().getSwitchbasicinfo().getName();
		this.macAddress = savi.getMacAddress();
		this.user = user;
		lifeTime.add(savi.getBindingLifetime()/100);
		lifeTime.add(maxLifetime/100);
		this.bindingState = savi.getBindingState();
		this.isInFilteringTable = savi.getIsInFilteringTable();
	}
	
	public BindingTableInfo(Savibindingtablecur savi, Integer maxLifetime){
		this.id = savi.getId();
		this.ipAddress = savi.getIpAddress();
		this.ipAddressType = savi.getIpAddressType();
		this.bindingType = savi.getBindingType();
		this.ifIndex = savi.getIfIndex();
		this.switchName = savi.getIfinterfacecur().getSwitchcur().getSwitchbasicinfo().getName();
		this.macAddress = savi.getMacAddress();
		this.user = savi.getUserName();
		lifeTime.add(savi.getBindingLifetime()/100);
		lifeTime.add(maxLifetime/100);
		this.bindingState = savi.getBindingState();
		this.isInFilteringTable = savi.getIsInFilteringTable();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIpAddressType() {
		return ipAddressType;
	}

	public void setIpAddressType(Integer ipAddressType) {
		this.ipAddressType = ipAddressType;
	}

	public Integer getBindingType() {
		return bindingType;
	}

	public void setBindingType(Integer bindingType) {
		this.bindingType = bindingType;
	}

	public Integer getIfIndex() {
		return ifIndex;
	}

	public void setIfIndex(Integer ifIndex) {
		this.ifIndex = ifIndex;
	}

	public String getSwitchName() {
		return switchName;
	}

	public void setSwitchName(String switchName) {
		this.switchName = switchName;
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
	
	public List<Integer> getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(List<Integer> lifeTime) {
		this.lifeTime = lifeTime;
	}

	public Integer getIsInFilteringTable() {
		return isInFilteringTable;
	}

	public void setIsInFilteringTable(Integer isInFilteringTable) {
		this.isInFilteringTable = isInFilteringTable;
	}
}
