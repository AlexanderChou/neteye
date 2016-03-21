package com.fault.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.base.model.Device;
import com.base.model.View;
import com.base.service.DeviceService;
import com.base.service.ViewService;
import com.base.util.Constants;
import com.fault.dao.FaultListDao;
import com.fault.dto.FaultNode;
import com.fault.dto.FaultNodeList;
import com.opensymphony.xwork2.ActionSupport;
import com.view.dao.ViewDAO;
import com.view.dto.Router;
import com.view.util.MyXmlUtil;

public class FaultEventSatusView extends  ActionSupport{
	private static final long serialVersionUID = 1L;
    private List<FaultNodeList> faultnodeslists =new ArrayList<FaultNodeList>();
    private int count;
    
    private List<View> views = new ArrayList<View>();
    private String submitView;
    private String viewname ="";
	

//	private  Map<String,List<Router>> deviceMap = new TreeMap<String,List<Router>>();
	
	public String getViewList() throws Exception{
		ViewService viewService = new ViewService();
		List<View> tempviews = viewService.getViewList();
		ViewDAO AllviewDAO = new ViewDAO();
    	 View viewmain = AllviewDAO.getViewMain();
		List<View> tempviewsmain= new ArrayList<View>();
		List<View> tempviewsother= new ArrayList<View>();
		Long viewid;
		if(submitView==null||"null".equals(submitView)||"".equals(submitView)){
			viewid=viewmain.getId();
			viewname=viewmain.getName();
		}else if(submitView=="-1"||"-1".equals(submitView)){
			viewid=viewmain.getId();
			viewname="全部";
		}else {
		    	viewid=Long.parseLong(submitView);
			   View thisview = AllviewDAO.getViewByViewId(viewid);
				viewname=thisview.getName();
		}
		for(int i=0;i<tempviews.size();i++){
			if(!tempviews.get(i).getId().equals(viewid)){
				tempviewsother.add(tempviews.get(i));
			}else{
				tempviewsmain.add(tempviews.get(i));
			}
		}
		
		View nullviews = new View();
		nullviews.setId((long)-1);
		nullviews.setName("ALL");
		
		if(submitView=="-1"||"-1".equals(submitView)){
			views.add(nullviews);
			views.addAll(tempviewsmain);
			views.addAll(tempviewsother);
		}else{
		views.addAll(tempviewsmain);
		views.addAll(tempviewsother);
		views.add(nullviews);
		}
		
		return SUCCESS;
	}
	public String getNodesStutas() throws Exception{
	//	 getViewList();
		 Set<FaultNodeList> faultstmp =new HashSet<FaultNodeList>(); 
		 List<Device> nodeothers = new ArrayList<Device>();
		 
		 ViewDAO AllviewDAO = new ViewDAO();
		 FaultListDao faultlistdao = new FaultListDao();
		 DeviceService finddevice =new DeviceService();
		 
		 if(submitView==null||"null".equals(submitView)){
			 View view = AllviewDAO.getViewMain();
			 String name = view.getName();
			 String filePath = Constants.webRealPath + "file/user/" + view.getUserName() + "_" + view.getUserId() + "/";
			 File file=new File(filePath+name+".xml");
				if(file.exists()){//如果文件存在，获得该文件中包含的所有设备
				List<Router> nodes = MyXmlUtil.getNodeIdList(filePath+name+".xml");
				for(int i=0;i<nodes.size();i++){
					Device device = new Device();
					device=finddevice.findById(nodes.get(i).getId());
					nodeothers.add(device);
				}
				} 
			 
			 
			}else if(submitView=="-1"||"-1".equals(submitView)){
				List<Device> nodebytype1 =finddevice.getDeviceList(1);
			     List<Device> nodebytype2  =finddevice.getDeviceList(2);
			     nodeothers.addAll(nodebytype1);
			     nodeothers.addAll(nodebytype2);
				
			}else{
			 View view = AllviewDAO.getViewByViewId(Long.parseLong(submitView));
			 String name = view.getName();
			 String filePath = Constants.webRealPath + "file/user/" + view.getUserName() + "_" + view.getUserId() + "/";
			 File file=new File(filePath+name+".xml");
				if(file.exists()){//如果文件存在，获得该文件中包含的所有设备
				List<Router> nodes = MyXmlUtil.getNodeIdList(filePath+name+".xml");
				for(int i=0;i<nodes.size();i++){
					Device device = new Device();
					device=finddevice.findById(nodes.get(i).getId());
					nodeothers.add(device);
				}
				}
			}
		 
//		  ViewDAO AllviewDAO = new ViewDAO();
//	     List<View> viewall =  AllviewDAO.getAllViewByViewName();
//	     for(int i=0;i<viewall.size();i++){
//							String name = viewall.get(i).getName();
//							ViewDAO viewDAO = new ViewDAO();
//							View view = viewDAO.getViewByViewName(name);
//							String filePath = Constants.webRealPath + "file/user/" + view.getUserName() + "_" + view.getUserId() + "/";
//							File file=new File(filePath+name+".xml");
//							if(file.exists()){//如果文件存在，获得该文件中包含的所有设备
//							List<Router> nodes = MyXmlUtil.getNodeIdList(filePath+name+".xml");
//		for(int j=0;j<nodes.size();j++){
//			String pic= faultlistdao.checkRouterStuts(nodes.get(j).getId());
//			nodes.get(j).setPicture(pic);
//		}
//								deviceMap.put(name, nodes);
//							}
//						}
	     
	     for(int i=0;i<nodeothers.size();i++){
	    	 
	    	 FaultNodeList faulttemp = new FaultNodeList();
	    	 List<FaultNode> faultnodetmp =new ArrayList<FaultNode>();
	    	 String tmpid = String.valueOf(nodeothers.get(i).getId());
	    	 String tmppic= faultlistdao.checkRouterStuts(Integer.valueOf(tmpid));
	    	 faulttemp.setId(nodeothers.get(i).getId());
	    	 faulttemp.setName("NoName");
	    	 if(!("").equals(nodeothers.get(i).getDescription())&&nodeothers.get(i).getDescription()!=null){
	                faulttemp.setName(nodeothers.get(i).getDescription());				
				};
	    	 if(!("").equals(nodeothers.get(i).getName())&&nodeothers.get(i).getName()!=null)
	    	 {
	    		 faulttemp.setName(nodeothers.get(i).getName());
	    	 };
	    	 if(!("").equals(nodeothers.get(i).getChineseName())&&nodeothers.get(i).getChineseName()!=null){
                 faulttemp.setName(nodeothers.get(i).getChineseName());				
			};
			
			faultnodetmp = faultlistdao.getDeviceEvent(String.valueOf(nodeothers.get(i).getId()));
	         String tmpcout =String.valueOf(faultnodetmp.size());
             faulttemp.setFaultnodelist(faultnodetmp); 
             faulttemp.setCount(tmpcout);
             String namepic = faulttemp.getName()+";"+tmppic;
             faulttemp.setNodestutas(namepic); 
             faultstmp.add(faulttemp);
	     }
	     
	     faultnodeslists= new ArrayList<FaultNodeList>(faultstmp);
	     count= faultnodeslists.size();
	     System.out.println(faultnodeslists);
		return SUCCESS;
	}
	public List<FaultNodeList> getFaultnodeslists() {
		return faultnodeslists;
	}
	public int getCount() {
		return count;
	}
	public List<View> getViews() {
		return views;
	}
	public String getSubmitView() {
		return submitView;
	}
	public String getViewname() {
		return viewname;
	}
	public void setFaultnodeslists(List<FaultNodeList> faultnodeslists) {
		this.faultnodeslists = faultnodeslists;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void setViews(List<View> views) {
		this.views = views;
	}
	public void setSubmitView(String submitView) {
		this.submitView = submitView;
	}
	public void setViewname(String viewname) {
		this.viewname = viewname;
	}
	
}
