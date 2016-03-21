package com.base.service;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.base.model.Device;
import com.base.model.IfInterface;
import com.base.model.Link;
import com.base.model.View;
import com.base.util.Constants;
import com.base.util.HibernateUtil;
import com.base.util.JDOMXML;
import com.config.dto.Info;
import com.topo.dto.Router;

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
public class InitService {
	private PortService portService = new PortService();
	private LinkService linkService = new LinkService();
	private DeviceService deviceService =new DeviceService();
	
	/**
	 * 添加设备
	 * @param devices 设备列表
	 * @param topoName 某次拓扑发现名称
	 */
	public Info addNodesByTopo(List<Router> devices,String[] links,String topoName,String viewDescription, String isHomePage) throws Exception{
		Info info = new Info();
		StringBuffer xml = new StringBuffer();
		StringBuffer routerStr = new StringBuffer();
		StringBuffer switchStr = new StringBuffer();
		StringBuffer customStr = new StringBuffer();
		Map<String,List<Device>> errorMap = new HashMap<String,List<Device>>();
		info.setAddNodesNum(devices.size());
		
		xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\n");
		xml.append("<to:view xmlns:to=\"http://www.inetboss.com/view\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.inetboss.com/view /file/view/schema/ViewSchema.xsd\">").append("\n");
		if(devices.size()>0){

			Iterator<Router> routers = devices.iterator();
			
			routerStr.append("<to:routers>\n");
			switchStr.append("<to:switches>\n");
			customStr.append("<to:customs>\n");
			while (routers.hasNext()){
            	Router r = routers.next();
            	if(r.getDeviceType()==1){
            		routerStr.append("<to:router>\n");
	            	routerStr.append("<to:id>").append(r.getId()).append("</to:id>");
					routerStr.append("<to:name>").append(r.getName()).append("</to:name>");
					routerStr.append("<to:coordinateX>").append(r.getRX()).append("</to:coordinateX>");
					routerStr.append("<to:coordinateY>").append(r.getRY()).append("</to:coordinateY>");
					routerStr.append("<to:picture>").append(r.getImgPath()).append("</to:picture>");
					routerStr.append("<to:isChangeIcon>0</to:isChangeIcon>");
					if(r.getIconId()!=null){
						routerStr.append("<to:iconId>").append(r.getIconId()).append("</to:iconId>");
					}else{
						routerStr.append("<to:iconId/>");
					}
					routerStr.append("<to:iconWidth>").append(Constants.ICON_WIDTH).append("</to:iconWidth>\n"); 
					routerStr.append("<to:iconHeight>").append(Constants.ICON_HEIGHT).append( "</to:iconHeight>\n");
					routerStr.append("</to:router>\n");
                }else if(r.getDeviceType()==2){
                	switchStr.append("<to:switch>\n");
	            	switchStr.append("<to:id>").append(r.getId()).append("</to:id>");
					switchStr.append("<to:name>").append(r.getName()).append("</to:name>");
					switchStr.append("<to:coordinateX>").append(r.getRX()).append("</to:coordinateX>");
					switchStr.append("<to:coordinateY>").append(r.getRY()).append("</to:coordinateY>");
					switchStr.append("<to:picture>").append(r.getImgPath()).append("</to:picture>");
					switchStr.append("<to:isChangeIcon>0</to:isChangeIcon>");
					if(r.getIconId()!=null){
						switchStr.append("<to:iconId>").append(r.getIconId()).append("</to:iconId>");
					}else{
						switchStr.append("<to:iconId/>");
					}
					switchStr.append("<to:iconWidth>").append(Constants.ICON_WIDTH).append("</to:iconWidth>\n"); 
					switchStr.append("<to:iconHeight>").append(Constants.ICON_HEIGHT).append( "</to:iconHeight>\n");
					switchStr.append("</to:switch>\n");
                }else{
                	customStr.append("<to:custom>\n");
	            	customStr.append("<to:id>").append(r.getId()).append("</to:id>");
					customStr.append("<to:name>").append(r.getName()).append("</to:name>");
					customStr.append("<to:coordinateX>").append(r.getRX()).append("</to:coordinateX>");
					customStr.append("<to:coordinateY>").append(r.getRY()).append("</to:coordinateY>");
					customStr.append("<to:picture>").append(r.getImgPath()).append("</to:picture>");
					customStr.append("<to:isChangeIcon>0</to:isChangeIcon>");
					if(r.getIconId()!=null){
						customStr.append("<to:iconId>").append(r.getIconId()).append("</to:iconId>");
					}else{
						customStr.append("<to:iconId/>");
					}
					customStr.append("<to:iconWidth>").append(Constants.ICON_WIDTH).append("</to:iconWidth>\n"); 
					customStr.append("<to:iconHeight>").append(Constants.ICON_HEIGHT).append( "</to:iconHeight>\n");
					customStr.append("</to:custom>\n");
                }
			}
			routerStr.append("</to:routers>\n");
			switchStr.append("</to:switches>\n");
			customStr.append("</to:customs>\n");
		}
		
		xml.append(routerStr.toString());
		xml.append(switchStr.toString());
		xml.append(customStr.toString());

		//增加本次拓扑发现的链路信息
		xml.append(this.addLink(links,topoName, devices));
		xml.append("<to:backGround>").append("images/china.jpg").append("</to:backGround>").append("\n");
		xml.append("<to:userId>").append(ServletActionContext.getRequest().getSession().getAttribute("userId").toString()).append("</to:userId>").append("\n");
		xml.append("</to:view>");
		
		/** 将文件存入file/view/data目录下 保存在个人文件夹下 该文件的目录是在user文件夹下，用户名和下划线和用户Id为文件名的文件夹下  */ 
		
		String userId = ServletActionContext.getRequest().getSession().getAttribute("userId").toString();
		String userName = ServletActionContext.getRequest().getSession().getAttribute("userName").toString();
		String filePath = Constants.webRealPath+"file/user/" + userName + "_" + userId + "/";
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		
		JDOMXML.writeXML(filePath+topoName+".xml",xml.toString());
		
		ViewService service=new ViewService();
		boolean result=service.isExistByName(topoName.trim());
		if(!result){
			info.setTopoNameUnique("yes");
			View view = new View();
			if(isHomePage.trim().equals("yes")){
				view.setHomePage(Boolean.TRUE);
			}else{
				view.setHomePage(Boolean.FALSE);
			}
			view.setDescription(viewDescription.trim());
			view.setName(topoName.trim());
			view.setUserId(Long.valueOf(userId));
			view.setUserName(userName);
			try{
				View newView = service.addViewByCondition(view);
				info.setViewId(newView.getId().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			info.setTopoNameUnique("no");
		}
				
		
		info.setErrorMap(errorMap);
		return info;
	}
	
	/**
	 * 将添加时出现例外的设备对象信息作为字符串输出
	 * @param list 设备列表
	 * @return
	 */
	public String getErrorInfo(List<Router> list){
		StringBuffer error = new StringBuffer();
		for(Router router:list){
			error.append(router.getIpv4()+":"+router.getName()+"\n");
		}
		return error.toString();
	}
	
	
	/**
	 * 数据库中存在设备数目
	 * @return
	 */
	public int getNodeNum()
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		int num = 0;
		String sql = "select count(*) from  Device";
		try {			
			tx = session.beginTransaction();		
			List result = session.createQuery(sql).list();		
			num = ((Long)result.get(0)).intValue();			
			tx.commit();	
		} catch (HibernateException e) {
			if(tx!=null){
				tx.rollback();
			}
		}
		return num;
	}
	
	/**
	 * 加入某次拓扑发现的链路信息
	 * @param topoName
	 * @return
	 */
	public String addLink(String[] links,String topoName, List<Router> devices) throws Exception{
		StringBuffer xml = new StringBuffer();
		Long tempId = null;
		boolean isAdd = false;
		if(links.length>0){
			if(links[0]!=null && !"".equals(links[0])){
				xml.append("<to:links>").append("\n");
				for(int k=0;k<links.length;k++){
					try {
						String name = links[k];
						if(name.indexOf("--")==-1){
							continue;
						}
						String[] names = name.split("--");
						//端口IP
						String upNameAndIP = names[0].substring(0,names[0].lastIndexOf("_"));
						String srcName = upNameAndIP.substring(0,upNameAndIP.lastIndexOf("_"));
						String srcIP = upNameAndIP.substring(upNameAndIP.lastIndexOf("_")+1);
						String upStreamIfIndex = names[0].substring(names[0].lastIndexOf("_")+1);
						
						String downNameAndIP = names[1].substring(0,names[1].lastIndexOf("_"));
						String destName = downNameAndIP.substring(0,downNameAndIP.lastIndexOf("_"));
						String destIP = downNameAndIP.substring(downNameAndIP.lastIndexOf("_")+1);
						String downStreamIfIndex = names[1].substring(names[1].lastIndexOf("_")+1);
						
						Long upDeviceId = null;
						Long downDeviceId = null;
						//取得Id
						boolean isSrcHave=false,isDestHave=false;
						for (Router r : devices) {
							if (srcName.trim().equals(r.getName().trim()) && srcIP.trim().equals(r.getIpv4().trim())) {
								upDeviceId = r.getId();
								isSrcHave = true;
							} 
							if (destName.trim().equals(r.getName().trim()) && destIP.trim().equals(r.getIpv4().trim())) {
								downDeviceId = r.getId();
								isDestHave = true;
							}
							if (isSrcHave && isDestHave) {
								break;
							}
						}
						
						if(upDeviceId!=null && downDeviceId!=null && upStreamIfIndex != null && !"".equals(upStreamIfIndex) && downStreamIfIndex != null && !"".equals(downStreamIfIndex)){	
							Link topoFindLink = new Link();
							//根据端口IP获得某一端口
							IfInterface  upinterface = portService.getInterfaceByIfIndex(upStreamIfIndex, upDeviceId);
							if(upinterface != null){							
								topoFindLink.setUpInterface(upinterface);
								IfInterface  downinterface = portService.getInterfaceByIfIndex(downStreamIfIndex, downDeviceId);	
								if(downinterface!=null){							
									topoFindLink.setDownInterface(downinterface);
									//判断是否有重复的链路(使用Criteria进行优化)
									Long isId = linkService.isRepeatLink(topoFindLink);
									if(isId==null){
										if(upinterface.getSpeed()!=null && downinterface.getSpeed()!=null){
											topoFindLink.setSpeed(Math.min(upinterface.getSpeed(), downinterface.getSpeed()));
										}
										if(upinterface.getMaxSpeed()!=null && downinterface.getMaxSpeed()!=null){
											topoFindLink.setMaxSpeed(Math.min(upinterface.getMaxSpeed(), downinterface.getMaxSpeed()));
										}
										//在此处用一段通用程序直接把名字转换成中文名称					
										topoFindLink.setDescription(srcName+"_"+upStreamIfIndex+"--"+destName+"_"+downStreamIfIndex);	
										topoFindLink.setName(srcName+"_"+upStreamIfIndex+"--"+destName+"_"+downStreamIfIndex);
										topoFindLink.setUpDevice(upDeviceId);
										topoFindLink.setDownDevice(downDeviceId);
										linkService.save(topoFindLink);
										//同时更新端口trafficFlag值，令该端口的值为1
										upinterface.setTrafficFlag(1);
										downinterface.setTrafficFlag(1);
										BaseService.update(upinterface);
										BaseService.update(downinterface);
										tempId = topoFindLink.getId();
									}else{
										tempId = isId;
									}
									if(tempId!=null){
										/* 对链路名字的处理 */
										String[] nameArr = name.split("--");
										int index1 = nameArr[0].lastIndexOf("_");
										
										String firstName = nameArr[0].substring(0, index1 - 1);
										firstName = firstName.substring(0 ,firstName.lastIndexOf("_"));
										String firstIfindex = nameArr[0].substring(index1 + 1, nameArr[0].length());
										int index2 = nameArr[1].lastIndexOf("_");
										String secondName = nameArr[1].substring(0, index2 - 1);
										secondName = secondName.substring(0, secondName.lastIndexOf("_"));
										String secondIfindex = nameArr[1].substring(index2 + 1, nameArr[1].length());
										
										String newLinkName = firstName + "_" + firstIfindex + "--" + secondName + "_" + secondIfindex;
										xml.append("<to:link>").append("\n");
										xml.append("<to:id>").append(tempId.toString()).append("</to:id>").append("\n");
										xml.append("<to:name>").append(newLinkName).append("</to:name>").append("\n");
										xml.append("<to:srcId>").append(String.valueOf(upDeviceId)).append("</to:srcId>").append("\n");
										xml.append("<to:destId>").append(String.valueOf(downDeviceId)).append("</to:destId>").append("\n");
										xml.append("<to:srcInterfaceId>").append(upinterface.getId()).append("</to:srcInterfaceId>").append("\n");
										xml.append("<to:destInterfaceId>").append(downinterface.getId()).append("</to:destInterfaceId>").append("\n");
										xml.append("</to:link>").append("\n");
										isAdd = true;
									}
								}								
							}	
						}//Endof if(upStreamPortIP != null && !"".equals(upStreamPortIP))
					} catch (HibernateException e) {
						e.printStackTrace();
					}
				}//Endof for(int k=0;k<record.size();k++)
				xml.append("</to:links>").append("\n");
			}
		}
		return xml.toString();
	}
	/**
	 * 判断数据库中是否有重复设备
	 * @param device 待添加的设备
	 * @param IP 用户输入的IP地址
	 * @param isSNMP 添加方式的判断 true:通过SNMP添加  false:手工添加
	 * @return
	 * @throws Exception
	 */
	public Long hasRepeat(Device device,String IP,boolean isSNMP) throws Exception{
		String sql = "";
		if(isSNMP){
			sql = "select d.id as num from device d,ifinterface f where f.ipv4='"+IP+"' and d.name='"+device.getName()+"' and d.id=f.device_id";
		}else{
			sql = "select d.id as num from device d,ifinterface f where f.ipv4='"+IP+"' and d.chinesename='"+device.getChineseName()+"' and d.id=f.device_id";
		}
		return deviceService.getRepeatDeviceNum(sql);
	}
	
	/**
	 * 
	 * @param devices
	 * @param links
	 * @param topoName
	 */
	public void createTopoViewFile(List<Router> devices, List<Link> links, String topoName){
		/** 总的xml */
		StringBuffer xml = new StringBuffer();
		/** 路由的xml */
		StringBuffer routerStr = new StringBuffer();
		/** 交换机的xml*/
		StringBuffer switchStr = new StringBuffer();
		/** 其他的xml*/
		StringBuffer customStr = new StringBuffer();
		
		/** 其他的xml*/
		StringBuffer linkXml = new StringBuffer();
		
		xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\n");
		xml.append("<to:view xmlns:to=\"http://www.inetboss.com/view\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.inetboss.com/view /file/view/schema/ViewSchema.xsd\">").append("\n");
		if(devices.size()>0){
			Iterator<Router> routers = devices.iterator();
			
			routerStr.append("<to:routers>\n");
			switchStr.append("<to:switches>\n");
			customStr.append("<to:customs>\n");
			while (routers.hasNext()){
            	Router r = routers.next();
            	if(r.getDeviceType()==1){
            		routerStr.append("<to:router>\n");
	            	routerStr.append("<to:id>").append(r.getId()).append("</to:id>");
					routerStr.append("<to:name>").append(r.getName()).append("</to:name>");
					routerStr.append("<to:coordinateX>").append(r.getRX()).append("</to:coordinateX>");
					routerStr.append("<to:coordinateY>").append(r.getRY()).append("</to:coordinateY>");
					routerStr.append("<to:picture>").append(r.getImgPath()).append("</to:picture>");
					routerStr.append("</to:router>\n");
                }else if(r.getDeviceType()==2){
                	switchStr.append("<to:switch>\n");
	            	switchStr.append("<to:id>").append(r.getId()).append("</to:id>");
					switchStr.append("<to:name>").append(r.getName()).append("</to:name>");
					switchStr.append("<to:coordinateX>").append(r.getRX()).append("</to:coordinateX>");
					switchStr.append("<to:coordinateY>").append(r.getRY()).append("</to:coordinateY>");
					switchStr.append("<to:picture>").append(r.getImgPath()).append("</to:picture>");
					switchStr.append("</to:switch>\n");
                }else{
                	customStr.append("<to:custom>\n");
	            	customStr.append("<to:id>").append(r.getId()).append("</to:id>");
					customStr.append("<to:name>").append(r.getName()).append("</to:name>");
					customStr.append("<to:coordinateX>").append(r.getRX()).append("</to:coordinateX>");
					customStr.append("<to:coordinateY>").append(r.getRY()).append("</to:coordinateY>");
					customStr.append("<to:picture>").append(r.getImgPath()).append("</to:picture>");
					customStr.append("</to:custom>\n");
                }
			}
			routerStr.append("</to:routers>\n");
			switchStr.append("</to:switches>\n");
			customStr.append("</to:customs>\n");
		}
		xml.append(routerStr.toString());
		xml.append(switchStr.toString());
		xml.append(customStr.toString());
		
		/** 添加链路 */
		linkXml.append("<to:links>").append("\n");
		for (Link link : links) {
			linkXml.append("<to:link>").append("\n");
			linkXml.append("<to:id>").append(link.getId()).append("</to:id>").append("\n");
			linkXml.append("<to:name>").append(link.getName()).append("</to:name>").append("\n");
			linkXml.append("<to:srcId>").append(link.getUpDevice().toString()).append("</to:srcId>").append("\n");
			linkXml.append("<to:destId>").append(link.getDownDevice().toString()).append("</to:destId>").append("\n");
			linkXml.append("<to:srcInterfaceId>").append(link.getUpInterface().getId().toString()).append("</to:srcInterfaceId>").append("\n");
			linkXml.append("<to:destInterfaceId>").append(link.getDownInterface().getId().toString()).append("</to:destInterfaceId>").append("\n");
			linkXml.append("</to:link>").append("\n");
		}
		linkXml.append("</to:links>").append("\n");
		xml.append("<to:backGround>").append("newChina.jpg").append("</to:backGround>").append("\n");
		xml.append("<to:userId>").append(ServletActionContext.getRequest().getSession().getAttribute("userId").toString()).append("</to:userId>").append("\n");
		xml.append("</to:view>");
		
		String filePath = Constants.webRealPath+"file/topo/topoHis/" + topoName + ".xml";
		File file = new File(filePath);
		/** 删除原来的文件 */
		if (file.exists()) {
			file.delete();
		}
		
		JDOMXML.writeXML(filePath,xml.toString());
	}
}
