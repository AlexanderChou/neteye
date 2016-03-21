package com.view.action;
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
/*
 * @autor:JiangNing
 * @data:2009-7-9
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.base.model.DeviceIcon;
import com.base.model.IfInterface;
import com.base.model.View;
import com.base.service.DeviceService;
import com.base.service.IfInterfaceService;
import com.base.service.LinkService;
import com.base.service.ViewService;
import com.base.util.Constants;
import com.base.util.StringUtil;
import com.base.util.W3CXML;
import com.config.dao.DeviceDAO;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.view.dto.Device;
import com.view.dto.Link;
import com.view.dto.Service;
import com.view.util.PicUtil;

public class ViewShowAction extends ActionSupport{
	private ArrayList<Device> deviceList;
	private ArrayList<Link> linkList;
	private ArrayList<Service> serviceList;
	private String viewId;
	private String background;
	private int serverId;
	private List<String> portList;
	private ViewService viewService = new ViewService();
	@JSON(serialize=false)
	/*
	 * 从配置文件和数据库中读取viewName指定的视图的所有设备信息，并通过参数deviceList返回给客户端
	 */
	
	//DOUBLE精度
	public static double round(double value, int scale, int roundingMode) {    
        BigDecimal bd = new BigDecimal(value);    
        bd = bd.setScale(scale, roundingMode);    
        double d = bd.doubleValue();    
        bd = null;    
        return d;    
	} 
	
	
	public String getChildrenDevices() throws Exception{
		deviceList=new ArrayList<Device>();
		getBackgoundInformation();
		getDeviceInformation("to:router",deviceList);
		getDeviceInformation("to:switch",deviceList);
		getDeviceInformation("to:server",deviceList);
		getDeviceInformation("to:workstation",deviceList);
		getDeviceInformation("to:custom",deviceList);
		return Action.SUCCESS;
	}
	@JSON(serialize=false)
	/*
	 * 从配置文件中读取serverId指定的服务器的所有服务信息，并通过参数serviceList返回给客户端
	 */
	public String getServices()throws Exception{
		View view = viewService.findById(Long.valueOf(viewId));
		String path = Constants.webRealPath + "file/user/" + view.getUserName() + "_" + view.getUserId() + "/";
		serviceList=new ArrayList<Service>();
		String fileStr = path + view.getName() + ".xml";
		org.w3c.dom.Document xmlViewdoc = W3CXML.loadXMLDocumentFromFile(fileStr);
		NodeList nodes = xmlViewdoc.getElementsByTagName("to:server");
		Node selectServerNode=getSelectServerNode(nodes,serverId);
		NodeList selectServerChildlist=selectServerNode.getChildNodes();
		for(int i=0;i<selectServerChildlist.getLength();i++){
			Node serverChildNode=selectServerChildlist.item(i);
			String serverChildNodeName=serverChildNode.getNodeName();
            if(serverChildNodeName.equals("to:services")){
            	NodeList serviceNodeList=serverChildNode.getChildNodes();
            	for(int j=0;j<serviceNodeList.getLength();j++){
        			String name="";
        			String coordinateX="";
        			String coordinateY="";
        			String port="";
        			String id="";
        			String picture="";
        			String subView="";
    				String deviceType="";
    				String imgWidth="";
    				String imgHeight="";
            		Node serviceNode=serviceNodeList.item(j);
            		String serviceNodeName=serviceNode.getNodeName();
            		if(serviceNodeName.equals("to:service")){
                		NodeList serviceChildNodeList=serviceNode.getChildNodes();
                		for(int index=0;index<serviceChildNodeList.getLength();index++){
                			Node serviceChildNode=serviceChildNodeList.item(index);
                			String serviceChildNodeName=serviceChildNode.getNodeName();
                			if(serviceChildNodeName.equals("to:name")){
                				name=serviceChildNode.getTextContent();
                			}else if(serviceChildNodeName.equals("to:port")){
                				port=serviceChildNode.getTextContent();
                			}else if(serviceChildNodeName.equals("to:coordinateX")){
                				coordinateX=serviceChildNode.getTextContent();
                			}else if(serviceChildNodeName.equals("to:coordinateY")){
                				coordinateY=serviceChildNode.getTextContent();
                			}else if(serviceChildNodeName.equals("to:id")){
                				id=serviceChildNode.getTextContent().split("_")[1];
                			}else if(serviceChildNodeName.equals("to:picture")){
                				picture=serviceChildNode.getTextContent();
                			}else if(serviceChildNodeName.equals("to:subView")){//简单起见，此处是指视图的名称，未将视图id写入xml文件
        						subView=serviceChildNode.getTextContent();
        					}else if(serviceChildNodeName.equals("to:iconWidth")){
        						imgWidth=serviceChildNode.getTextContent();
        					}else if(serviceChildNodeName.equals("to:iconHeight")){
        						imgHeight=serviceChildNode.getTextContent();
        					}
                		}
    					Service service=new Service();
    					service.setName(name);
    					service.setPort(port);
    					service.setRX(Integer.parseInt(coordinateX));
    					service.setRY(Integer.parseInt(coordinateY));
    					service.setId(Long.valueOf(id.substring(id.indexOf('_')+1)));
    					service.setPicture(picture);
    					service.setSubView(subView);
    					service.setImgHeight(imgHeight);
    					service.setImgWidth(imgWidth);
    					serviceList.add(service);
                	}
            	}

            }
		}
		return Action.SUCCESS;
	}
	@JSON(serialize=false)
	/*
	 * 从配置文件和数据库中读取viewName指定的视图的所有链路信息，并通过参数linkList返回给客户端
	 */
	public String getChildrenLinks() throws Exception{
		linkList=new ArrayList<Link>();
		LinkService linkService=new LinkService();
		DeviceService deviceService=new DeviceService();
		View view = viewService.findById(Long.valueOf(viewId));
		String path = Constants.webRealPath + "file/user/" + view.getUserName() + "_" + view.getUserId() + "/";
		String fileStr = path + view.getName() + ".xml";
		org.w3c.dom.Document xmlViewdoc = W3CXML.loadXMLDocumentFromFile(fileStr);
		NodeList nodes = xmlViewdoc.getElementsByTagName("to:link");
		Hashtable availability=new Hashtable();
		String flowPath = Constants.webRealPath+"file/flow/flowscan/dat/";
		File flowFile=new File(flowPath+"flow.txt");
		if(flowFile.exists()){
			FileReader fr;
			try {
				fr = new FileReader(flowFile);
				BufferedReader br = new BufferedReader(fr);
	            String myreadline; 
	            while (br.ready()) {
	                myreadline = br.readLine();//读取一行
	                if(myreadline.contains("|")){
		                String a[]=(myreadline.trim()).split("\\|");
		                if(a.length==4){
		                   String key=a[0]+"-"+a[1];
		                   String value=a[2]+"-"+a[3];
		                   availability.put(key, value);
		            	}
	                }
	            }
	            br.close();
	            fr.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//创建FileReader对象，用来读取字符流
            
		}
		Map<String,Integer> repeatLink = new HashMap<String,Integer>();//用来存储两个设备之间的多条链路
		for(int i=0;i<nodes.getLength();i++){
			String id="";
			String name="";
			String srcId="";
			String destId="";
			String srcInfId="";
			String destInfId="";
			String IP = "";
			Node node=nodes.item(i);
			NodeList subNodes=node.getChildNodes();
			for(int j=0;j<subNodes.getLength();j++){
				Node childNode=subNodes.item(j);
				String nodeName=childNode.getNodeName();
				if(nodeName.equals("to:id")){
					id=childNode.getTextContent();
				}else if(nodeName.equals("to:name")){
					name=childNode.getTextContent();
				}else if(nodeName.equals("to:srcId")){
					srcId=childNode.getTextContent();
				}else if(nodeName.equals("to:destId")){
					destId=childNode.getTextContent();
				}else if(nodeName.equals("to:srcInterfaceId")){
					srcInfId=childNode.getTextContent();
				}else if(nodeName.equals("to:destInterfaceId")){
					destInfId=childNode.getTextContent();
				}
			}
			com.base.model.Link baseLink=null;
			String srckey=null,destkey=null;
			try {
				if(isNotLong(id))continue;
				baseLink = linkService.getLink(Long.valueOf(id));
				IfInterface srcIfInterface=baseLink.getUpInterface();
				IfInterface destIfInterface=baseLink.getDownInterface();
			    Link link=new Link();
			    link.setId(Long.valueOf(id));
			    link.setSrcInfId(Long.valueOf(srcInfId));
			    link.setDestInfId(Long.valueOf(destInfId));
			    if(baseLink.getMaxSpeed()!=null){
			    	link.setBandwidth(baseLink.getMaxSpeed());
			    }else{
			    	link.setBandwidth(0);
			    }
			    com.base.model.Device srcDevice =deviceService.getDevice(Integer.parseInt(srcId));
			    if(srcDevice!=null){
			    	link.setSrcName(srcDevice.getName().trim());
			    	link.setSrcIP(srcDevice.getLoopbackIP().trim());
			    	link.setSrcIPv6(srcDevice.getLoopbackIPv6());
			    	link.setSrcChineseName(srcDevice.getChineseName());
			    	if(srcDevice.getLoopbackIP()!=null && !srcDevice.getLoopbackIP().equals("")){
			    		IP = srcDevice.getLoopbackIP();
			    	}else if(srcDevice.getLoopbackIPv6()!=null && !srcDevice.getLoopbackIPv6().equals("")){
			    		IP = srcDevice.getLoopbackIPv6();
			    	}
			    	srckey=IP+"-"+srcIfInterface.getIfindex();
			    }
                com.base.model.Device destDevice=deviceService.getDevice(Integer.parseInt(destId));
                if(destDevice!=null){
                	link.setDestName(destDevice.getName().trim());
                	link.setDestChineseName(destDevice.getChineseName());
                	link.setDestIP(destDevice.getLoopbackIP().trim());
                	link.setDestIPv6(destDevice.getLoopbackIPv6());
                	if(destDevice.getLoopbackIP()!=null && !destDevice.getLoopbackIP().equals("")){
			    		IP = destDevice.getLoopbackIP();
			    	}else if(destDevice.getLoopbackIPv6()!=null && !destDevice.getLoopbackIPv6().equals("")){
			    		IP = destDevice.getLoopbackIPv6();
			    	}
                	destkey=IP+"-"+destIfInterface.getIfindex();
                }
                link.setSrcDescription(srcIfInterface.getDescription());
                link.setDestDescription(destIfInterface.getDescription());
                link.setSrcInfIP(srcIfInterface.getIpv4());
                link.setSrcInfIPv6(srcIfInterface.getIpv6());
                link.setDestInfIP(destIfInterface.getIpv4());
                link.setDestInfIPv6(destIfInterface.getIpv6());
                link.setSrcIfIndex(Integer.parseInt(srcIfInterface.getIfindex()));
                link.setDestIfIndex(Integer.parseInt(destIfInterface.getIfindex()));
                if(srcIfInterface.getMaxSpeed()!=null){
                	link.setSrcSpeed(srcIfInterface.getMaxSpeed());
                }else{
                	link.setSrcSpeed(0);
                }
                if(destIfInterface.getMaxSpeed()!=null){
                	link.setDestSpeed(destIfInterface.getMaxSpeed());
                }else{
                	link.setDestSpeed(0);
                }
                //求出两个端口当前的状态:查询eventstatus和 event表，查出端口的事件类型值
                if(availability.get(srckey)!=null&&availability.get(destkey)!=null){
                	double speed=srcIfInterface.getMaxSpeed();
                	String ablility[]=availability.get(srckey).toString().split("\\-");
                	String ablility2[]=availability.get(destkey).toString().split("\\-");
                	if(speed>0){
                		double srcFlow;
                		double destFlow;
                		String srcFlowStr;
                		String destFlowStr;
                		
                		if(Double.parseDouble(ablility[0])>Double.parseDouble(ablility2[1])){
                			srcFlow=Double.parseDouble(ablility[0])/(1000000); 
                			srcFlowStr=StringUtil.convertSpeed(ablility[0]);
                		}else{
                			srcFlow=Double.parseDouble(ablility2[1])/(1000000);
                			srcFlowStr=StringUtil.convertSpeed(ablility[1]);
                		}
                		if(Double.parseDouble(ablility[1])>Double.parseDouble(ablility2[0])){
                			destFlow=Double.parseDouble(ablility[1])/(1000000);
                			destFlowStr=StringUtil.convertSpeed(ablility[1]);
                		}else{
                			destFlow=Double.parseDouble(ablility2[0])/(1000000);
                			destFlowStr=StringUtil.convertSpeed(ablility2[0]);
                		}
                		
                		
	                
	            	    //将出入流量由原来的以字节为单位，改为以M为单位
	                	link.setDestFlow(round(destFlow,4,BigDecimal.ROUND_HALF_UP));
	                	link.setSrcFlow(round(srcFlow,4,BigDecimal.ROUND_HALF_UP));
	                	link.setDestFlowStr(destFlowStr);
	                	link.setSrcFlowStr(srcFlowStr);
                	}else{
	                	link.setDestFlow(0.00);
	                	link.setSrcFlow(0.00);
	                	link.setDestFlowStr("0.00");
	                	link.setSrcFlowStr("0.00");
                	}
                }else if(availability.get(srckey)!=null){
                	double speed=srcIfInterface.getSpeed();
                	String ablility[]=availability.get(srckey).toString().split("\\-");
                	if(speed>0){               		
                		double	srcFlow=Double.parseDouble(ablility[0])/(1000000);               		
                		double	destFlow=Double.parseDouble(ablility[1])/(1000000);  
                		String srcFlowStr;
                		String destFlowStr;
                		srcFlowStr=StringUtil.convertSpeed(ablility[0]);
                		destFlowStr=StringUtil.convertSpeed(ablility[1]);
                		link.setDestFlow(round(destFlow,4,BigDecimal.ROUND_HALF_UP));
	                	link.setSrcFlow(round(srcFlow,4,BigDecimal.ROUND_HALF_UP));
	                	link.setDestFlowStr(destFlowStr);
	                	link.setSrcFlowStr(srcFlowStr);
                	}else{
	                	link.setDestFlow(0.00);
	                	link.setSrcFlow(0.00);
	                	link.setDestFlowStr("0.00");
	                	link.setSrcFlowStr("0.00");
                	}
                }else if(availability.get(destkey)!=null){
                	double speed=0d;
                	if(srcIfInterface.getSpeed()!=null){
                		speed=srcIfInterface.getSpeed();
                	}
                	String ablility[]=availability.get(destkey).toString().split("\\-");
                	if(speed>0){               		
                		double	srcFlow=Double.parseDouble(ablility[1])/(1000000);               		
                		double	destFlow=Double.parseDouble(ablility[0])/(1000000);  
                		String srcFlowStr;
                		String destFlowStr;
                		srcFlowStr=StringUtil.convertSpeed(ablility[1]);
                		destFlowStr=StringUtil.convertSpeed(ablility[0]);
                		link.setDestFlow(round(destFlow,4,BigDecimal.ROUND_HALF_UP));
	                	link.setSrcFlow(round(srcFlow,4,BigDecimal.ROUND_HALF_UP));
	                	link.setDestFlowStr(destFlowStr);
	                	link.setSrcFlowStr(srcFlowStr);
                	}else{
	                	link.setDestFlow(0.00);
	                	link.setSrcFlow(0.00);
	                	link.setDestFlowStr("0.00");
	                	link.setSrcFlowStr("0.00");
                	}
                }
                name=srcDevice.getId()+"_"+link.getSrcIfIndex()+"--"+destDevice.getId()+"_"+link.getDestIfIndex();
                link.setName(name);
                int lineNum = 1;
                String tempName = srcId + "--" + destId;
                String oppName =  destId + "--" + srcId;
            	if(repeatLink.containsKey(tempName)){
        			lineNum = repeatLink.get(tempName);	
        			lineNum++;
        			repeatLink.put(tempName,lineNum);
        			link.setLinkNum(lineNum);
        		}else if(repeatLink.containsKey(oppName)){
        			lineNum = repeatLink.get(oppName);	
        			lineNum++;
        			repeatLink.put(oppName,lineNum);
        			link.setLinkNum(lineNum);
        		}else{
        			repeatLink.put(tempName,new Integer(1));
        			link.setLinkNum(1);
        		}
                linkList.add(link);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return Action.SUCCESS;
	}
    /*
     * 因为配置文件中的链路有可能是服务器和服务之间的私有链路，此链路id格式为“数字_数字”，此链路本程序不关心，所以要滤去，
     * 而此函数作用是输入一个String类型的id，如果它不是数字形式，则返回false，否则返回true
     */
    private boolean isNotLong(String string){
    	if(string.indexOf('_')==-1)return false;
    	else return true;
    }
    /*
     * 此私有函数是从一个nodeList列表中找出与serverId相匹配的那个Node，并返回之
     */
    private Node getSelectServerNode(NodeList nodeList,int serverId){
    	//返回的是文件中包含指定serverId的<to:server>节点
		for(int i=0;i<nodeList.getLength();i++){
			Node serverNode=nodeList.item(i);
			NodeList subNodes=serverNode.getChildNodes();
			for(int j=0;j<subNodes.getLength();j++){
				Node childNode=subNodes.item(j);
				String nodeName=childNode.getNodeName();
				if(nodeName.equals("to:id")){
					int id=Integer.parseInt(childNode.getTextContent());
					if(id==serverId){
			            return serverNode;
					}
				}
			}
		}
		return null;
    }
    /*
     * 从配置文件中读取viewName指定的视图的背景图片url，并通过参数background返回给客户端
     */
    public void getBackgoundInformation() throws Exception{
		View view = viewService.findById(Long.valueOf(viewId));
		if(view!=null){
			String path = Constants.webRealPath + "file/user/" + view.getUserName() + "_" + view.getUserId() + "/";
			String fileStr = path + view.getName() + ".xml";
			org.w3c.dom.Document xmlViewdoc = W3CXML.loadXMLDocumentFromFile(fileStr);
			if(xmlViewdoc!=null){
				NodeList nodes = xmlViewdoc.getElementsByTagName("to:backGround");
				if(nodes.getLength()!=0)background=nodes.item(0).getTextContent();
			}
		}
    }
	private void getDeviceInformation(String tag,ArrayList<Device> deviceList) throws Exception{
		int flag=0;
		DeviceService baseService=new DeviceService();
		View view = viewService.findById(Long.valueOf(viewId));
		if(view!=null){
			String path = Constants.webRealPath + "file/user/" + view.getUserName() + "_" + view.getUserId() + "/";
			String fileStr = path + view.getName() + ".xml";
			org.w3c.dom.Document xmlViewdoc = W3CXML.loadXMLDocumentFromFile(fileStr);
			NodeList nodes = xmlViewdoc.getElementsByTagName(tag);
			for(int i=0;i<nodes.getLength();i++){
				String id="";
				String chineseName="";
				String CoordinateX="";
				String CoordinateY="";
				String picture="";
				String subView="";
				String deviceType="";
				String imgWidth="";
				String imgHeight="";
				/**
				 * 以下两个变量是用来标识设备所要显示的图标名称
				 */
				String iconId="";
				String isChangeIcon="";
				Node node=nodes.item(i);
				NodeList subNodes=node.getChildNodes();
				for(int j=0;j<subNodes.getLength();j++){
					Node childNode=subNodes.item(j);
					String nodeName=childNode.getNodeName();
					if(nodeName.equals("to:id")){
						id=childNode.getTextContent();
						if("".equals(id)){
							flag=1;
							break;
						}
					}else if(nodeName.equals("to:name")){
						chineseName=childNode.getTextContent();
					}else if(nodeName.equals("to:coordinateX")){
						CoordinateX=childNode.getTextContent();
					}else if(nodeName.equals("to:coordinateY")){
						CoordinateY=childNode.getTextContent();
					}else if(nodeName.equals("to:picture")){
						picture=childNode.getTextContent();
					}else if(nodeName.equals("to:subView")){//简单起见，此处是指视图的名称，未将视图id写入xml文件
						subView=childNode.getTextContent();
					}else if(nodeName.equals("to:iconWidth")){
						imgWidth=childNode.getTextContent();
					}else if(nodeName.equals("to:iconHeight")){
						imgHeight=childNode.getTextContent();
					}else if(nodeName.equals("to:iconId")){
						iconId=childNode.getTextContent();
					}else if(nodeName.equals("to:isChangeIcon")){
						isChangeIcon=childNode.getTextContent();
					}
				}
				if(flag==1){
					break;
				}
				com.base.model.Device baseDevice=null;
				try {
					baseDevice = baseService.getDevice(Long.parseLong(id));
				    Device device=new Device();
				    if(baseDevice!=null){
					    device.setId(Integer.parseInt(id));
					    
					    device.setChineseName(chineseName);
					    if (baseDevice.getChineseName()!=null && !baseDevice.getChineseName().equals("")){
					    	device.setChineseName(baseDevice.getChineseName());
					   	}
					   	/*
					   	 * 需要根据该设备的图标id找到该条记录所对应的其它属性，以便用来匹配轮循和告警时的图标文件名
					   	 * 需要从前台将图标id读出，然后查找device_icon文件
					   	 */
					    DeviceIcon deviceIcon = null;
					    if(!isChangeIcon.equals("0") && !iconId.equals("1") && !iconId.equals("2") && !iconId.equals("3") && !iconId.equals("4")){
					    	deviceIcon = new DeviceDAO().getDeviceIconById(iconId);
					    	if(deviceIcon!=null){
					    		device.setManuIcon(deviceIcon.getManufacture());
					    		device.setModelIcon(deviceIcon.getModel());
					    	}
					    }
					    device.setName(baseDevice.getName());
					    device.setIpv4(baseDevice.getLoopbackIP());
					    device.setIpv6(baseDevice.getLoopbackIPv6());
					    device.setRX(Integer.parseInt(CoordinateX));
					    device.setRY(Integer.parseInt(CoordinateY));
					    device.setPicture(picture);
					    device.setSubView(subView);
					    device.setImgWidth(imgWidth);
					    device.setImgHeight(imgHeight);
					    deviceType=tag.substring(tag.indexOf(':')+1);
					    device.setDeviceType(deviceType);
					    device.setDescription(baseDevice.getDescription());
					    if(baseDevice.getFaultFlag()!=null){
						    if(baseDevice.getFaultFlag()==1)device.setFaultFlag("是");//更改：赵一
						    else device.setFaultFlag("否");
					    }else{
					    	device.setFaultFlag("否");
					    }
					    device.setIfNum(baseDevice.getIfNum());
					    device.setLabel(baseDevice.getLabel());
					    device.setLocation(baseDevice.getLocation());
					    device.setModel(baseDevice.getModel());
					    device.setProductor(baseDevice.getProductor());
					    device.setSerial(baseDevice.getSerial());
					    device.setTrafficIp(baseDevice.getTrafficIp());
					    //对每一个设备生成相应的性能时钟图
					    PicUtil.generatePic(baseDevice.getLoopbackIP(), baseDevice.getLoopbackIPv6());
					    deviceList.add(device);
				    }
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 在添加链路的时候，列出已选择的端口列表
	 * @return
	 * @throws Exception
	 */
	public String initDevicePortList() throws Exception {
		String srcName = ServletActionContext.getRequest().getParameter("srcName");
		String destName = ServletActionContext.getRequest().getParameter("destName");
		
		LinkService linkService = new LinkService();
		List<com.base.model.Link> links = linkService.getLinksByUnAndDn(srcName, destName);
		IfInterfaceService ifInterfaceService = new IfInterfaceService();
		
		for (com.base.model.Link link : links) {
			String[] arr = link.getName().split("--")[0].split("_");
			String[] arr2 = link.getName().split("--")[1].split("_");
			
			String description1 = ifInterfaceService.getIfInterfaceByIfIndex(arr[arr.length - 1]).getDescription();
			String description2 = ifInterfaceService.getIfInterfaceByIfIndex(arr2[arr2.length - 1]).getDescription();
			String description = description1 + "--" + description2;
			portList.add(description);
		}
		return SUCCESS;
	}
	
	public ArrayList<Device> getDeviceList() {
		return deviceList;
	}
	public void setDeviceList(ArrayList<Device> DeviceList) {
		this.deviceList = deviceList;
	}
	public ArrayList<Link> getLinkList() {
		return linkList;
	}
	public void setLinkList(ArrayList<Link> linkList) {
		this.linkList = linkList;
	}
	public ArrayList<Service> getServiceList() {
		return serviceList;
	}
	public void setServiceList(ArrayList<Service> serviceList) {
		this.serviceList = serviceList;
	}
	public int getServerId() {
		return serverId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	public String getBackground() {
		return background;
	}
	public void setBackground(String background) {
		this.background = background;
	}
	public List<String> getPortList() {
		return portList;
	}
	public void setPortList(List<String> portList) {
		this.portList = portList;
	}


	public String getViewId() {
		return viewId;
	}


	public void setViewId(String viewId) {
		this.viewId = viewId;
	}
}
