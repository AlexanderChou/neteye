package com.flow.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.base.model.Device;
import com.base.model.DicContent;
import com.base.model.View;
import com.base.service.DeviceTypeService;
import com.base.service.DicContentService;
import com.base.service.IfInterfaceService;
import com.base.service.LinkService;
import com.base.service.ViewService;
import com.base.util.Constants;
import com.flow.dao.FlowDao;
import com.flow.dao.FlowListViewDao;
import com.flow.dto.FlowListTemp;
import com.opensymphony.xwork2.ActionSupport;
import com.view.dao.ViewDAO;
import com.view.dto.Router;
import com.view.util.MyXmlUtil;

/*
** Copyright (c) 2008, 2009, 2010
**      The Regents of the Tsinghua University, PRC.  All rights reserved.
**
** Redistribution and use in source and binary forms, with or without  modification, are permitted provided that the following conditions are met:
** 1. Redistributions of source code must retain the above copyright  notice, this list of conditions and the following disclaimer.
** 2. Redistributions in binary form must reproduce the above copyright  notice, this list of conditions and the following disclaimer in the  documentation and/or other materials provided with the distribution.
** 3. All advertising materials mentioning features or use of this software  must display the following acknowledgement:
**  This product includes software (iNetBoss) developed by Tsinghua University, PRC and its contributors.
** THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
** ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
** IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
** ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
** FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
** DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
** OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
** HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
** LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
** OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
** SUCH DAMAGE.
*
*/
public class PerformanceAllWatchAction extends ActionSupport{
	private List<View> views = new ArrayList<View>() ;
	private String viewname;
	private String submitView;
	private List<DicContent> dicDetailList;

	public String allPerformance() throws Exception{
		ViewService viewService = new ViewService();
		//以下处理页面视图下拉列表的排序问题（顺序：用户选择的视图　主监控视图　其它视图）
		List<View> tempviews = viewService.getViewList();
		ViewDAO AllviewDAO = new ViewDAO();
    	 View viewmain = AllviewDAO.getViewMain();
		 
		List<View> tempviewsmain= new ArrayList<View>();
		List<View> tempviewsother= new ArrayList<View>();
		
		String tempname="";
		if(submitView==null||"null".equals(submitView)){
			submitView=viewmain.getName()+"_"+viewmain.getId();
		}
		
		if(submitView.trim().length()>0){
			tempname = submitView.substring(0,submitView.lastIndexOf("_"));
			viewname=tempname;
		}
		
		for(int i=0;i<tempviews.size();i++){
			if(!tempviews.get(i).getName().equals(tempname)){
				tempviewsother.add(tempviews.get(i));
			}else{
				tempviewsmain.add(tempviews.get(i));
			}
		}
		
		View nullviews = new View();
		nullviews.setId((long)-1);
		nullviews.setName("ALL");
		
		if(("ALL_-1").equals(submitView)){
			views.add(nullviews);
			views.addAll(tempviewsmain);
			views.addAll(tempviewsother);
			viewname="全部";
		}else{
			views.addAll(tempviewsmain);
			views.addAll(tempviewsother);
			views.add(nullviews);
		}
		//数据字典性能参数列表
		dicDetailList= new DicContentService().getByDicTypeId("performancePara");
		return SUCCESS;
	}
	public void setViews(List<View> views) {
		this.views = views;
	}
	public List<View> getViews() {
		return views;
	}
	public String getViewname() {
		return viewname;
	}
	public void setViewname(String viewname) {
		this.viewname = viewname;
	}
	public List<DicContent> getDicDetailList() {
		return dicDetailList;
	}
	public void setDicDetailList(List<DicContent> dicDetailList) {
		this.dicDetailList = dicDetailList;
	}
	public String getSubmitView() {
		return submitView;
	}
	public void setSubmitView(String submitView) {
		this.submitView = submitView;
	}
}
