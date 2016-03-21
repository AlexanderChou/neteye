package com.flow.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.struts2.ServletActionContext;

import com.base.model.View;
import com.base.service.ViewService;
import com.base.util.Constants;
import com.fault.dao.FaultListDao;
import com.flow.dao.FlowListViewDao;
import com.flow.dto.FlowListTemp;
import com.opensymphony.xwork2.ActionSupport;
import com.view.dao.ViewDAO;
import com.view.dto.Router;

public class FlowEventStatusAction  extends  ActionSupport{
		private static final long serialVersionUID = 1L;
		private FaultListDao faultlistdao = new FaultListDao();
		private FlowListViewDao flowlistdao = new FlowListViewDao();
		private List<View> views = new ArrayList<View>();
		private List<View> tempviews= new ArrayList<View>();
		private List<View> tempviewsmain= new ArrayList<View>();
		private List<View> tempviewsother= new ArrayList<View>();
		private String viewname ="";
	    private List<FlowListTemp> flowlistdo = new ArrayList<FlowListTemp>();  
	    private Integer totalCount;
	    private String submitView;

		private  Map<String,List<Router>> deviceMap = new TreeMap<String,List<Router>>();
	
		public String execute() throws Exception{
			ViewService viewService = new ViewService();
			tempviews = viewService.getViewList();
			String viewName,viewId,userId,userName;
			View view = null;
			if(("ALL_-1").equals(submitView)){
				submitView="";
			}
			for(int ii=0;ii<tempviews.size();ii++){
				String tempname="";
				if(submitView.trim().length()>0){
					String b[] = submitView.split(";");
					if(b!=null){
						tempname = b[0].substring(0,b[0].lastIndexOf("_"));
						viewname=tempname;
					}
					
				}
					
				if(!tempviews.get(ii).getName().equals(tempname)){
					tempviewsother.add(tempviews.get(ii));
				}else{
					tempviewsmain.add(tempviews.get(ii));
				}
			}
			View nullviews = new View();
			nullviews.setId((long)-1);
			nullviews.setName("ALL");
			if(("").equals(submitView)){
				views.add(nullviews);
				views.addAll(tempviewsmain);
				views.addAll(tempviewsother);
				viewname="全部";
			}else{
				views.addAll(tempviewsmain);
				views.addAll(tempviewsother);
				views.add(nullviews);
			}
			if(submitView.trim().length()>0){
				String a[] = submitView.split(";");
				if(a!=null){
					for(int i=0;i<a.length;i++){
						if(!a[i].equals("")){
							if(a[i].lastIndexOf("_")>-1){
								viewName = a[i].substring(0,a[i].lastIndexOf("_"));
								String viewIdStr = a[i].substring(a[i].lastIndexOf("_")+1,a[i].length());;
								//先查找file/flow/config/viewId.txt文件是否存在，若不存在，再去读取视图文件
								viewId = viewIdStr.substring(viewIdStr.lastIndexOf("_")+1,viewIdStr.length());
								view = new ViewDAO().getViewByViewId(Long.valueOf(viewId));
								userId = view.getUserId().toString();
								userName = view.getUserName();
								File flowConfigFile = new File(Constants.webRealPath + "file/flow/config/" + viewId +".txt");
								if(flowConfigFile.exists()){
									flowlistdo=flowlistdao.getFlowViewInfo(flowConfigFile);
								}else{
//									String userId = ServletActionContext.getRequest().getSession().getAttribute("userId").toString();
//									String userName = ServletActionContext.getRequest().getSession().getAttribute("userName").toString();
									String filePath = Constants.webRealPath + "file/user/" + userName + "_" + userId + "/";
									File file=new File(filePath+viewName+".xml");
									if(file.exists()){//如果文件存在，获得该文件中包含的所有设备
										Set viewlinktmp = flowlistdao.getlinkformview(filePath+viewName+".xml");
										flowlistdo=flowlistdao.getinfinfo(viewlinktmp);
									}
								}
							}
						}
					}
				}
			}else{
				 ViewDAO AllviewDAO = new ViewDAO();
			     List<View> viewall =  AllviewDAO.getAllViewByViewName();
				     for(int i=0;i<viewall.size();i++){
						viewName = viewall.get(i).getName();
						viewId = viewall.get(i).getId().toString();
						userId = viewall.get(i).getUserId().toString();
						userName = viewall.get(i).getUserName();
						//先查找file/flow/config/viewId.txt文件是否存在，若不存在，再去读取视图文件
						File flowConfigFile = new File(Constants.webRealPath + "file/flow/config/" + viewId +".txt");
						if(flowConfigFile.exists()){
							flowlistdo=flowlistdao.getFlowViewInfo(flowConfigFile);
						}else{
//							String userId = ServletActionContext.getRequest().getSession().getAttribute("userId").toString();
//							String userName = ServletActionContext.getRequest().getSession().getAttribute("userName").toString();
							String filePath = Constants.webRealPath + "file/user/" + userName + "_" + userId + "/";
							File file=new File(filePath+viewName+".xml");
							if(file.exists()){//如果文件存在，获得该文件中包含的所有设备
								Set viewlinktmp = flowlistdao.getlinkformview(filePath+viewName+".xml");
								flowlistdo=flowlistdao.getinfinfo(viewlinktmp);
							}
						}
			     }
			}
			totalCount = flowlistdo.size();
			return SUCCESS;
		}
		
		public Map<String, List<Router>> getDeviceMap() {
			return deviceMap;
		}
		public void setDeviceMap(Map<String, List<Router>> deviceMap) {
			this.deviceMap = deviceMap;
		}
		public FaultListDao getFaultlistdao() {
			return faultlistdao;
		}
		public void setFaultlistdao(FaultListDao faultlistdao) {
			this.faultlistdao = faultlistdao;
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

		public String getViewname() {
			return viewname;
		}

		public void setViewname(String viewname) {
			this.viewname = viewname;
		}
	}

