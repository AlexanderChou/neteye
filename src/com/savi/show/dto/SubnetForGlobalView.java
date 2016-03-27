package com.savi.show.dto;

import java.util.ArrayList;
import java.util.List;
import com.savi.show.dto.SwitchForGlobalView;
/**
 * Subnet entity. @author MyEclipse Persistence Tools
 */

public class SubnetForGlobalView{

	// Fields

	private Long id;
	private String name;
	private int switchNum;
	private int userNum;
	private List<SwitchForGlobalView> switchForGlobalViewList=new ArrayList<SwitchForGlobalView>();

	// Constructors
	/** default constructor */
	public SubnetForGlobalView() {
	}
	/** full constructor */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public int getSwitchNum() {
		return switchNum;
	}
	public void setSwitchNum(int switchNum) {
		this.switchNum = switchNum;
	}
	public int getUserNum() {
		return userNum;
	}
	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}
	public List<SwitchForGlobalView> getSwitchForGlobalViewList() {
		return switchForGlobalViewList;
	}
	public void setSwitchForGlobalViewList(
			List<SwitchForGlobalView> switchForGlobalViewList) {
		this.switchForGlobalViewList = switchForGlobalViewList;
	}
}