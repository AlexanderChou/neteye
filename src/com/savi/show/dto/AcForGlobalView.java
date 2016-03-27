package com.savi.show.dto;

import java.util.ArrayList;
import java.util.List;

public class AcForGlobalView {

	private Long id;
	private String name;
	private int userNum;
	private List<ApForGlobalView> apForGlobalViewList=new ArrayList<ApForGlobalView>();

	public AcForGlobalView(){
		
	}

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

	public int getUserNum() {
		return userNum;
	}

	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}

	public List<ApForGlobalView> getApForGlobalViewList() {
		return apForGlobalViewList;
	}

	public void setApForGlobalViewList(List<ApForGlobalView> apForGlobalViewList) {
		this.apForGlobalViewList = apForGlobalViewList;
	}
	
	
	
}
