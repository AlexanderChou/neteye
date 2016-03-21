package com.flow.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import com.base.model.Device;
import com.base.model.View;
import com.base.service.DeviceTypeService;
import com.base.service.IfInterfaceService;
import com.base.service.LinkService;
import com.base.service.ViewService;
import com.base.util.Constants;
import com.flow.dao.FlowDao;
import com.opensymphony.xwork2.ActionSupport;
import com.view.dao.ViewDAO;

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
public class AllWatchAction extends ActionSupport{
	private Vector routerAndIfIndex = new Vector();	
	private List<View> views = new ArrayList<View>() ;
	private List<View> tempviews= new ArrayList<View>();
	private String viewname ="";
	public String execute() throws Exception{
		ViewDAO AllviewDAO = new ViewDAO();
		 View  viewmain = AllviewDAO.getViewMain();
		ViewService viewService = new ViewService();
		tempviews = viewService.getViewList();
		if(viewmain!=null&&!("").equals(viewmain)){
		viewname=viewmain.getName();
			views.add(viewmain);
		}
		for(int i=0;i<tempviews.size();i++){
			if(!tempviews.get(i).getName().equals(viewname)){
				views.add(tempviews.get(i));
			}
		}
		View nullviews = new View();
		nullviews.setId((long)-1);
		nullviews.setName("ALL");
		views.add(nullviews);
//		IfInterfaceService service=new IfInterfaceService();
//		LinkService lService=new LinkService();
//		Hashtable deviceHT= new Hashtable();
//		FlowDao dao=new FlowDao();
//		File name = new File( Constants.webRealPath+"file/flow/flowscan/dat/flow.txt" );
//		Hashtable table = new Hashtable();
//		if(name.exists()){	
//			deviceHT=dao.getDevice(1);
//			BufferedReader input = new BufferedReader( new FileReader( name ) );
//			String text;					
//			while ( ( text = input.readLine() ) != null )
//			{
//				if(!text.equals("")&&text.contains("|")){
//					String a[]=text.split("\\|");
//					String url=a[0]+"_"+a[1];
//					Device device=(Device)deviceHT.get(a[0]);
//					String interfaceName="";					
//					if(device!=null&&device.getId()!=null){
//						long ifindexId=service.getId(device.getId(), a[1]);
//						boolean flag=lService.isUsed(device.getId(), ifindexId);
//						if(flag){
////							System.out.println("aaa="+a[1]+"dd="+device.getId());
////								String nametemp =service.getIfInterName(device.getId(), a[1]);
////								System.out.println("nametemp="+nametemp);
////								if(nametemp!=null&&!nametemp.equals("")){
////								 interfaceName=nametemp;							
////								}else{
//									if(device.getChineseName()!=null&&!device.getChineseName().equals("")){
//										 interfaceName=device.getChineseName()+"-"+a[1];
//									}else if(device.getName()!=null&&!device.getName().equals("")){
//										 interfaceName=device.getName()+"-"+a[1];
//									}else{
//										interfaceName=a[0]+"-"+a[1];
//									}
////								}
//								table.put(url,interfaceName);
//							}											
//						}
//					}
//				}
//			
//			input.close();
//		}
//		routerAndIfIndex.add(table);
//		
		
		return SUCCESS;
	}
public Vector getRouterAndIfIndex() {
	return routerAndIfIndex;
}
public void setRouterAndIfIndex(Vector routerAndIfIndex) {
	this.routerAndIfIndex = routerAndIfIndex;
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
