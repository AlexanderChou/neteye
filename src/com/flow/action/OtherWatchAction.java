package com.flow.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.base.model.Device;
import com.base.model.View;
import com.base.service.DeviceTypeService;
import com.base.service.IfInterfaceService;
import com.base.service.LinkService;
import com.base.service.PortService;
import com.base.service.ViewService;
import com.base.util.Constants;
import com.base.util.W3CXML;
import com.fault.dao.FaultListDao;
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
public class OtherWatchAction extends ActionSupport{
	
			private static final long serialVersionUID = 1L;
			private FlowListViewDao flowlistdao = new FlowListViewDao();
			private List<View> views = new ArrayList<View>();
		    private List<FlowListTemp> flowlistdo = new ArrayList<FlowListTemp>();  
		    private List<View> tempviews= new ArrayList<View>();
			
		    private Integer totalCount;
		    private String viewtoname ="";
		    private String submitView;
			private  Map<String,List<Router>> deviceMap = new TreeMap<String,List<Router>>();
			private  Map<String,List<FlowListTemp>> FlowEvent = new TreeMap<String,List<FlowListTemp>>();
			public String execute() throws Exception{
				ViewService viewService = new ViewService();
				tempviews = viewService.getViewList();
				ViewDAO AllviewDAO = new ViewDAO();
		    	 View viewmain = AllviewDAO.getViewMain();
				 
				List<View> tempviewsmain= new ArrayList<View>();
				List<View> tempviewsother= new ArrayList<View>();
				
				String tempname="";
				if(submitView==null||"null".equals(submitView)){
					submitView=viewmain.getName()+"_"+viewmain.getId();
				}
				
				if(submitView.trim().length()>0){
					String b[] = submitView.split(";");
					if(b!=null){
						tempname = b[0].substring(0,b[0].lastIndexOf("_"));
						viewtoname=tempname;
					}
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
					viewtoname="全部";
				}else{
					views.addAll(tempviewsmain);
					views.addAll(tempviewsother);
					views.add(nullviews);
				}
				if(("ALL_-1").equals(submitView)){
				     List<View> viewall =  AllviewDAO.getAllViewByViewName();
					     for(int i=0;i<viewall.size();i++){
											String name = viewall.get(i).getName();
											ViewDAO viewDAO = new ViewDAO();
											View view = viewDAO.getViewByViewName(name);
											File flowConfigFile = new File(Constants.webRealPath + "file/flow/config/" +view.getId() +".txt");
											if(flowConfigFile.exists()){
												flowlistdo=flowlistdao.getFlowViewInfo(flowConfigFile);
											}else{
												String filePath = Constants.webRealPath + "file/user/" + view.getUserName() + "_" + view.getUserId() + "/";
												File file=new File(filePath+name+".xml");
												if(file.exists()){//如果文件存在，获得该文件中包含的所有设备
													Set viewlinktmp = flowlistdao.getlinkformview(filePath+name+".xml");
													flowlistdo=flowlistdao.getinfinfo(viewlinktmp);
												}
											}
					     }
					
				}else if(submitView.trim().length()>0){
					String a[] = submitView.split(";");
					if(a!=null){
						for(int i=0;i<a.length;i++){
							if(!a[i].equals("")){
								if(a[i].lastIndexOf("_")>-1){
									String name = a[i].substring(0,a[i].lastIndexOf("_"));
									ViewDAO viewDAO = new ViewDAO();
									View view = viewDAO.getViewByViewName(name);
									File flowConfigFile = new File(Constants.webRealPath + "file/flow/config/" +view.getId() +".txt");
									if(flowConfigFile.exists()){
										flowlistdo=flowlistdao.getFlowViewInfo(flowConfigFile);
									}else{
										String filePath = Constants.webRealPath + "file/user/" + view.getUserName() + "_" + view.getUserId() + "/";
										File file=new File(filePath+name+".xml");
										if(file.exists()){//如果文件存在，获得该文件中包含的所有设备
											Set viewlinktmp = flowlistdao.getlinkformview(filePath+name+".xml");
											flowlistdo=flowlistdao.getinfinfo(viewlinktmp);
										}
									}
								}
							}
						}
					}
				}else{
					   String name =viewmain.getName();
					   ViewDAO viewDAO = new ViewDAO();
						View view = viewDAO.getViewByViewName(name);
						File flowConfigFile = new File(Constants.webRealPath + "file/flow/config/" +view.getId() +".txt");
						if(flowConfigFile.exists()){
							flowlistdo=flowlistdao.getFlowViewInfo(flowConfigFile);
						}else{
							String filePath = Constants.webRealPath + "file/user/" + view.getUserName() + "_" + view.getUserId() + "/";
							File file=new File(filePath+name+".xml");
							if(file.exists()){//如果文件存在，获得该文件中包含的所有设备
								Set viewlinktmp = flowlistdao.getlinkformview(filePath+name+".xml");
								flowlistdo=flowlistdao.getinfinfo(viewlinktmp);
							}
						}
				}
				totalCount = flowlistdo.size();
             
				Set<String> flowlistothername = new HashSet<String>();
				for(int j=0;j<flowlistdo.size();j++){
					flowlistothername.add(flowlistdo.get(j).getName());
				}
				List<String> flowothernamelist =new ArrayList(flowlistothername);
				Collections.sort(flowothernamelist);
				for(int k=0;k<flowothernamelist.size();k++){
					List<FlowListTemp> inflistothertmp =new ArrayList<FlowListTemp>() ;
					for(int m=0;m<flowlistdo.size();m++){
						if(flowothernamelist.get(k).equals(flowlistdo.get(m).getName())){
							inflistothertmp.add(flowlistdo.get(m));
						}
					}
					FlowEvent.put(flowothernamelist.get(k), inflistothertmp);
				}

				
				return SUCCESS;
			}
			
			public Map<String, List<Router>> getDeviceMap() {
				return deviceMap;
			}
			public void setDeviceMap(Map<String, List<Router>> deviceMap) {
				this.deviceMap = deviceMap;
			}
			public FlowListViewDao getFlowlistdao() {
				return flowlistdao;
			}
			public void setFlowlistdao(FlowListViewDao flowlistdao) {
				this.flowlistdao = flowlistdao;
			}
			public List<FlowListTemp> getFlowlistdo() {
				return flowlistdo;
			}
			public void setFlowlistdo(List<FlowListTemp> flowlistdo) {
				this.flowlistdo = flowlistdo;
			}
			public Integer getTotalCount() {
				return totalCount;
			}
			public void setTotalCount(Integer totalCount) {
				this.totalCount = totalCount;
			}
			public String getSubmitView() {
				return submitView;
			}
			public void setSubmitView(String submitView) {
				this.submitView = submitView;
			}
			public void setViews(List<View> views) {
				this.views = views;
			}
			public List<View> getViews() {
				return views;
			}
			public Map<String, List<FlowListTemp>> getFlowEvent() {
				return FlowEvent;
			}
			public void setFlowEvent(Map<String, List<FlowListTemp>> flowEvent) {
				FlowEvent = flowEvent;
			}
			public void setViewtoname(String viewtoname) {
				this.viewtoname = viewtoname;
			}
			public String getViewtoname() {
				return viewtoname;
			}
		}

