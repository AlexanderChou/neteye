package com.view.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.json.annotations.JSON;

import com.base.model.Device;
import com.base.model.ServiceManage;
import com.base.model.View;
import com.base.service.DeviceService;
import com.base.service.LinkService;
import com.base.service.ServiceManageService;
import com.base.util.Constants;
import com.base.util.JDOMXML;
import com.opensymphony.xwork2.Action;
import com.view.dao.ViewDAO;
import com.view.dto.Link;

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
public class DeviceChoseJsonAction implements ServletRequestAware, ServletResponseAware{
	protected HttpServletRequest request; 
	protected HttpServletResponse response;
	private String success;
	private String failure;
	private String viewName;
	private String[] checkId;
	private String[] checkText;
	ArrayList<Link> VirtualLink = new ArrayList<Link>();
	

	@JSON(serialize=false)
	public String execute(){
		response.setContentType("text/html;charset=utf-8");        
	    response.setHeader("Cache-Control","no-cache");  
		LinkService linkService = new LinkService();
		ServiceManageService serviceManageService = new ServiceManageService();
		//获得所选的节点，计算其坐标（默认以同心圆方式显示），存入viewName.xml文件
		//同时存入文件的还有与该节点相关的所有链路
		int circleX = 400;
		int circleY = 200;
		int redius = 200;//同心圆半径
		int n = 0;//设备数目	
		double angle = 0;
		double imageRedius = 12.5;//设备图片的半径
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\n");
		xml.append("<to:view xmlns:to=\"http://www.inetboss.com/view\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.inetboss.com/view /file/view/schema/ViewSchema.xsd\">").append("\n");
		n = checkId.length;
		int x = 0;
		int y = 0;
		DeviceService service=new DeviceService();
		try{
				if(checkId.length>0&&!checkId[0].equals("")){
					int count = 0;
					angle = 2*Math.PI/n;
						for(int i=0;i<checkId.length;i++){
							if(!checkId[i].equals("")){
								Device device=service.findById(Long.valueOf(checkId[i]));
									if(device.getDeviceType().getId()==1){
										count++;
									x = (int)(circleX + redius*Math.cos(angle*i)+imageRedius);
									y = (int)(circleY + redius*Math.sin(angle*i)+imageRedius);
									if(count == 1){
										xml.append("<to:routers>").append("\n");
									}
									xml.append("<to:router>").append("\n");
									xml.append("<to:id>").append(checkId[i]).append("</to:id>").append("\n");
									xml.append("<to:name>").append(checkText[i]).append("</to:name>").append("\n");
									xml.append("<to:coordinateX>").append(x).append("</to:coordinateX>").append("\n");
									xml.append("<to:coordinateY>").append(y).append("</to:coordinateY>").append("\n");
									xml.append("<to:picture>images/green_router.gif</to:picture>").append("\n");									
									xml.append("<to:iconId>").append("1").append("</to:iconId>\n"); 
									xml.append("<to:isChangeIcon>").append("0").append( "</to:isChangeIcon>\n");
									xml.append("<to:iconWidth>").append(Constants.ICON_WIDTH).append("</to:iconWidth>\n"); 
									xml.append("<to:iconHeight>").append(Constants.ICON_HEIGHT).append( "</to:iconHeight>\n");
									
									xml.append("</to:router>").append("\n");
								}else{
									continue;
								}
						}
					}//Endof for
						if(count > 0){
						xml.append("</to:routers>").append("\n");
						}
				}
						
			if(checkId.length>0&&!checkId[0].equals("")){
				int count=0;
				angle = 2*Math.PI/n;
					for(int i=0;i<checkId.length;i++){
						if(!checkId[i].equals("")){
							Device device=service.findById(Long.valueOf(checkId[i]));
								if(device.getDeviceType().getId()==2){
									count++;
									x = (int)(circleX + redius*Math.cos(angle*i)+imageRedius);
									y = (int)(circleY + redius*Math.sin(angle*i)+imageRedius);
								if(count==1){
									xml.append("<to:switches>").append("\n");	
								}
								xml.append("<to:switch>").append("\n");
								xml.append("<to:id>").append(checkId[i]).append("</to:id>").append("\n");
								xml.append("<to:name>").append(checkText[i]).append("</to:name>").append("\n");
								xml.append("<to:coordinateX>").append(x).append("</to:coordinateX>").append("\n");
								xml.append("<to:coordinateY>").append(y).append("</to:coordinateY>").append("\n");
								xml.append("<to:picture>images/green_switch.gif</to:picture>").append("\n");
								xml.append("<to:iconId>").append("2").append("</to:iconId>\n"); 
								xml.append("<to:isChangeIcon>").append("0").append( "</to:isChangeIcon>\n");
								xml.append("<to:iconWidth>").append(Constants.ICON_WIDTH).append("</to:iconWidth>\n"); 
								xml.append("<to:iconHeight>").append(Constants.ICON_HEIGHT).append( "</to:iconHeight>\n");
								xml.append("</to:switch>").append("\n");
							}
					}
				}//Endof for
			if(count>0){
				xml.append("</to:switches>").append("\n");
				}
			}
			if(checkId.length>0&&!checkId[0].equals("")){
				int count=0;
				angle = 2*Math.PI/n;
					for(int i=0;i<checkId.length;i++){
						if(!checkId[i].equals("")){
							Device device=service.findById(Long.valueOf(checkId[i]));
								if(device.getDeviceType().getId()==3){
									count++;
									x = (int)(circleX + redius*Math.cos(angle*i)+imageRedius);
									y = (int)(circleY + redius*Math.sin(angle*i)+imageRedius);
								if(count==1){
									xml.append("<to:servers>").append("\n");	
								}
								xml.append("<to:server>").append("\n");
								xml.append("<to:id>").append(checkId[i]).append("</to:id>").append("\n");
								xml.append("<to:name>").append(checkText[i]).append("</to:name>").append("\n");
								xml.append("<to:coordinateX>").append(x).append("</to:coordinateX>").append("\n");
								xml.append("<to:coordinateY>").append(y).append("</to:coordinateY>").append("\n");
								xml.append("<to:picture>images/green_server.gif</to:picture>").append("\n");
								xml.append("<to:iconId>").append("3").append("</to:iconId>\n"); 
								xml.append("<to:isChangeIcon>").append("0").append( "</to:isChangeIcon>\n");
								xml.append("<to:iconWidth>").append(Constants.ICON_WIDTH).append("</to:iconWidth>\n"); 
								xml.append("<to:iconHeight>").append(Constants.ICON_HEIGHT).append( "</to:iconHeight>\n");
								//由服务器的id查找servicemanage数据库表
								List services = serviceManageService.getListByDeviceId(Integer.parseInt(checkId[i]));
								if(services.size()>0){
									xml.append("<to:services>").append("\n");
									int m=device.getService().split(";").length;

									double angle2 = Math.PI/m;
									double redius2 = redius/2;
									double iniAngle = Math.atan(circleY/circleX);
									int x1 = 0;
									int y1 = 0;
									for(int j=0;j<services.size();j++){
										x1 = (int)(x + redius2*Math.cos(iniAngle+angle2*j)+imageRedius);
										y1 = (int)(y + redius2*Math.sin(iniAngle+angle2*j)+imageRedius);

										ServiceManage serviceManage = (ServiceManage)services.get(j);
										Link temp = new Link();
										temp.setId(device.getId());//所属服务器的Id
										temp.setName(serviceManage.getServiceType());
										temp.setSrcId(Long.parseLong(checkId[i]));											
										temp.setDestId(serviceManage.getId());//应用服务的Id	
										xml.append("<to:service>").append("\n");
										xml.append("<to:id>").append(device.getId()+"_"+serviceManage.getId()).append("</to:id>").append("\n");
										xml.append("<to:name>").append(serviceManage.getServiceType()).append("</to:name>").append("\n");
										xml.append("<to:port>"+serviceManage.getPort()+"</to:port>").append("\n");
										xml.append("<to:coordinateX>").append(x1).append("</to:coordinateX>").append("\n");
										xml.append("<to:coordinateY>").append(y1).append("</to:coordinateY>").append("\n");
										if(serviceManage.getServiceType().equals("DNS")){
											xml.append("<to:picture>images/green_dns.gif</to:picture>\n");
										}else if(serviceManage.getServiceType().equals("Email")){	
											xml.append("<to:picture>images/green_email.gif</to:picture>\n");
										}else if(serviceManage.getServiceType().equals("隧道")){	
											xml.append("<to:picture>images/green_tunnel.gif</to:picture>\n");
										}else if(serviceManage.getServiceType().equals("FTP")){
											xml.append("<to:picture>images/green_ftp.gif</to:picture>\n");
										}else if(serviceManage.getServiceType().equals("HTTP")){
											xml.append("<to:picture>images/green_http.gif</to:picture>\n");
										}
										xml.append("</to:service>").append("\n");
										VirtualLink.add(temp);
									}
									xml.append("</to:services>").append("\n");
								}
							xml.append("</to:server>").append("\n");
							}
					}
				}//Endof for
			if(count>0){
				xml.append("</to:servers>").append("\n");
				}
			}
			if(checkId.length>0&&!checkId[0].equals("")){
				int count=0;
				angle = 2*Math.PI/n;
					for(int i=0;i<checkId.length;i++){
						if(!checkId[i].equals("")){
							Device device=service.findById(Long.valueOf(checkId[i]));
								if(device.getDeviceType().getId()==4){
									count++;
									x = (int)(circleX + redius*Math.cos(angle*i)+imageRedius);
									y = (int)(circleY + redius*Math.sin(angle*i)+imageRedius);
								if(count==1){
									xml.append("<to:workstations>").append("\n");	
								}
								xml.append("<to:workstation>").append("\n");
								xml.append("<to:id>").append(checkId[i]).append("</to:id>").append("\n");
								xml.append("<to:name>").append(checkText[i]).append("</to:name>").append("\n");
								xml.append("<to:coordinateX>").append(x).append("</to:coordinateX>").append("\n");
								xml.append("<to:coordinateY>").append(y).append("</to:coordinateY>").append("\n");
								xml.append("<to:picture>images/green_workstation.gif</to:picture>").append("\n");
								xml.append("<to:iconId>").append("4").append("</to:iconId>\n"); 
								xml.append("<to:isChangeIcon>").append("0").append( "</to:isChangeIcon>\n");
								xml.append("<to:iconWidth>").append(Constants.ICON_WIDTH).append("</to:iconWidth>\n"); 
								xml.append("<to:iconHeight>").append(Constants.ICON_HEIGHT).append( "</to:iconHeight>\n");
								xml.append("</to:workstation>").append("\n");
							}
					}
				}//Endof for
			if(count>0){
				xml.append("</to:workstations>").append("\n");
				}
			}
			if(checkId.length>0&&!checkId[0].equals("")){
				int count=0;
				angle = 2*Math.PI/n;
					for(int i=0;i<checkId.length;i++){
						if(!checkId[i].equals("")){
							Device device=service.findById(Long.valueOf(checkId[i]));
								if(device.getDeviceType().getId()>4){
									count++;
									x = (int)(circleX + redius*Math.cos(angle*i)+imageRedius);
									y = (int)(circleY + redius*Math.sin(angle*i)+imageRedius);
								if(count==1){
									xml.append("<to:customs>").append("\n");	
								}
								xml.append("<to:custom>").append("\n");
								xml.append("<to:id>").append(checkId[i]).append("</to:id>").append("\n");
								xml.append("<to:name>").append(checkText[i]).append("</to:name>").append("\n");
								xml.append("<to:coordinateX>").append(x).append("</to:coordinateX>").append("\n");
								xml.append("<to:coordinateY>").append(y).append("</to:coordinateY>").append("\n");
								xml.append("<to:picture>images/green_custom.gif</to:picture>").append("\n");
								xml.append("<to:iconId>").append("4").append("</to:iconId>\n"); 
								xml.append("<to:isChangeIcon>").append("0").append( "</to:isChangeIcon>\n");
								xml.append("<to:iconWidth>").append(Constants.ICON_WIDTH).append("</to:iconWidth>\n"); 
								xml.append("<to:iconHeight>").append(Constants.ICON_HEIGHT).append( "</to:iconHeight>\n");
							xml.append("</to:custom>").append("\n");
							}
					}
				}//Endof for
			if(count>0){
				xml.append("</to:customs>").append("\n");
				}
			}
			if(checkId.length>0&&!checkId[0].equals("")){
				
				String deviceId=checkId[0];
				for(int i=1;i<checkId.length;i++){
					if(!checkId[i].equals("")){
						deviceId=deviceId+","+checkId[i];
					}
				}
				if(checkId.length>0&&!checkId[0].equals("")){
					List linklist=linkService.getLinkListByDeviceId(deviceId);	
					Iterator it = linklist.iterator();
					int count=0;					
					while(it.hasNext()) {
						Object[] object = (Object[])it.next();
						count++;
						if(count==1){
							xml.append("<to:links>").append("\n");
						}
						xml.append("<to:link>").append("\n");
						xml.append("<to:id>").append(object[0].toString()).append("</to:id>").append("\n");
						if(object[1]!=null&&!object[1].equals("")){
							xml.append("<to:name>").append(object[1].toString()).append("</to:name>").append("\n");	
						}else{
							xml.append("<to:name/>").append("\n");
						}
						xml.append("<to:srcId>").append(object[2].toString()).append("</to:srcId>").append("\n");
						xml.append("<to:destId>").append(object[3].toString()).append("</to:destId>").append("\n");
						xml.append("<to:srcInterfaceId>").append(object[4].toString()).append("</to:srcInterfaceId>").append("\n");
						xml.append("<to:destInterfaceId>").append(object[5].toString()).append("</to:destInterfaceId>").append("\n");
						xml.append("</to:link>").append("\n");
					}
				for(int i = 0;i<VirtualLink.size();i++){	
					count++;
					if(count==1){
						xml.append("<to:links>").append("\n");
					}
					xml.append("<to:link>\n");
					xml.append("<to:id>").append(VirtualLink.get(i).getId()+"_"+VirtualLink.get(i).getDestId()).append("</to:id>").append("\n");
					xml.append("<to:name>").append(VirtualLink.get(i).getName()).append("</to:name>").append("\n");	
					xml.append("<to:srcId>").append(VirtualLink.get(i).getSrcId()).append("</to:srcId>\n");
					xml.append("<to:destId>").append(VirtualLink.get(i).getId()+"_"+VirtualLink.get(i).getDestId()).append("</to:destId>\n");
					xml.append("<to:srcInterfaceId>0</to:srcInterfaceId>").append("\n");
					xml.append("<to:destInterfaceId>0</to:destInterfaceId>").append("\n");
					xml.append("</to:link>\n");
					}
					if(count>0){
						xml.append("</to:links>").append("\n");
					}
				}
			}
			
			xml.append("<to:backGround>").append("images/china.jpg").append("</to:backGround>").append("\n");
			xml.append("</to:view>").append("\n");
			//将文件存入file/user目录下
			ViewDAO viewDAO = new ViewDAO();
			View view = viewDAO.getViewByViewName(viewName);
			String path = Constants.webRealPath + "file/user/" + view.getUserName() + "_" + view.getUserId() + "/";
			JDOMXML.writeXML(path+viewName+".xml",xml.toString());
			success = "true";
			failure = "false";
		}catch(Exception e){
			e.printStackTrace();
			success = "false";
			failure = "true";
		}
		return Action.SUCCESS;
	}
	
	public void setServletRequest(HttpServletRequest request) {		this.request = request;		}
	public void setServletResponse(HttpServletResponse response) {		this.response = response;	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getFailure() {
		return failure;
	}

	public void setFailure(String failure) {
		this.failure = failure;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	
	public String[] getCheckId() {
		return checkId;
	}

	public void setCheckId(String[] checkId) {
		this.checkId = checkId;
	}

	public String[] getCheckText() {
		return checkText;
	}

	public void setCheckText(String[] checkText) {
		this.checkText = checkText;
	}
}
