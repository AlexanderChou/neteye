package com.view.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspWriter;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.base.model.DeviceIcon;
import com.base.util.Constants;
import com.base.util.W3CXML;
import com.config.dao.DeviceDAO;
import com.view.dto.Router;

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
public class MyXmlUtil {
	private static List<DeviceIcon> icons;
	private static DeviceDAO deviceDAO = new DeviceDAO();
	public static void registerService(JspWriter out,org.w3c.dom.Document xmldoc,String tagName){
		NodeList routerLists = xmldoc.getElementsByTagName(tagName); // NodeList包括所有名字为router的节点
		boolean flag;
		if(routerLists!=null){
			for (int i = 0; i < routerLists.getLength(); i++) {
				flag = false;
				Node routerNode = routerLists.item(i);// routerNode是每一个router节点
				NodeList childLists = routerNode.getChildNodes();// childLists存放的是router的子节点
				String sID = "";
				String sName = "";
				String sX = "";
				String sY = "";
				String sType = "3";
				String sPort = "";
				String sPicture = "";
				String sIconId = "";
				String sIsChangeIcon = "";
				String sWidth = "";
				String sHeight = "";
				String sSubview ="";
				// 以下for把routerNode的内容赋值给router对象
				for (int j = 0; j < childLists.getLength(); j++) {
					Node childNode = childLists.item(j);// childNode是router的每一个子节点
					String nodeName = childNode.getNodeName();// nodeName是每一个子节点的名字
					if (nodeName.equals("to:id")) {
						if("".equals(childNode.getTextContent())){
							flag = true;
						}else{
							sID = "s" + childNode.getTextContent(); //拖拽库貌似不支持int型的ID
						}
					} else if (nodeName.equals("to:name")) {
						sName = childNode.getTextContent();
					} else if (nodeName.equals("to:coordinateX")) {
						sX = childNode.getTextContent();
					} else if (nodeName.equals("to:coordinateY")) {
						sY = childNode.getTextContent();
					}else if (nodeName.equals("to:picture")) {
						sPicture = childNode.getTextContent();
					}else if (nodeName.equals("to:port")) {
						sPort = childNode.getTextContent();
					}else if (nodeName.equals("to:iconId")) {
						sIconId = childNode.getTextContent();
					}else if (nodeName.equals("to:isChangeIcon")) {
						sIsChangeIcon = childNode.getTextContent();
					}else if (nodeName.equals("to:iconWidth")) {
						sWidth = childNode.getTextContent();
					}else if (nodeName.equals("to:iconHeight")) {
						sHeight = childNode.getTextContent();
					}else if (nodeName.equals("to:subView")) {
						sSubview = childNode.getTextContent();
					}
				}
				if(!flag){
					try {
						out.println("registerStation2('" + sID + "','" + sName + "','"+ sPicture + "','"+ sType + "','"
								+ sX + "','" + sY + "','" + sPort +"','" + sIconId +"','" + sIsChangeIcon +"','" + sWidth +"','" + sHeight+ "','" + sSubview +"');");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	public static void registerNode(JspWriter out,org.w3c.dom.Document xmldoc,String tagName){
		NodeList routerLists = xmldoc.getElementsByTagName(tagName); // NodeList包括所有名字为router的节点
		boolean flag;
		if(routerLists!=null){
			for (int i = 0; i < routerLists.getLength(); i++) {
				flag = false;
				Node routerNode = routerLists.item(i);// routerNode是每一个router节点
				NodeList childLists = routerNode.getChildNodes();// childLists存放的是router的子节点
				String sID = "";
				String sName = "";
				String sX = "";
				String sY = "";
				String sType = "";
				String sPicture = "";
				String sIconId = "";
				String sIsChangeIcon = "";
				String sWidth = "";
				String sHeight = "";
				String sSubview ="";
				if(tagName.equals("to:router")){
					sType = "1";
				}else if(tagName.equals("to:switch")){
					sType = "2";
				}else if(tagName.equals("to:server")){
					sType = "3";
				}else if(tagName.equals("to:workstation")){
					sType = "4";
				}else{
					sType = "5";
				}
				// 以下for把routerNode的内容赋值给router对象
				for (int j = 0; j < childLists.getLength(); j++) {
					Node childNode = childLists.item(j);// childNode是router的每一个子节点
					String nodeName = childNode.getNodeName();// nodeName是每一个子节点的名字
					if (nodeName.equals("to:id")) {
						if("".equals(childNode.getTextContent())){
							flag = true;
						}else{
							sID = "s" + childNode.getTextContent(); //拖拽库貌似不支持int型的ID
						}
					} else if (nodeName.equals("to:name")) {
						sName = childNode.getTextContent();
					} else if (nodeName.equals("to:coordinateX")) {
						sX = childNode.getTextContent();
					} else if (nodeName.equals("to:coordinateY")) {
						sY = childNode.getTextContent();
					}else if (nodeName.equals("to:picture")) {
						sPicture = childNode.getTextContent();
					}else if (nodeName.equals("to:iconId")) {
						sIconId = childNode.getTextContent();
					}else if (nodeName.equals("to:isChangeIcon")) {
						sIsChangeIcon = childNode.getTextContent();
					}else if (nodeName.equals("to:iconWidth")) {
						sWidth = childNode.getTextContent();
					}else if (nodeName.equals("to:iconHeight")) {
						sHeight = childNode.getTextContent();
					}else if (nodeName.equals("to:subView")) {
						sSubview = childNode.getTextContent();
					}
				}
				
				if(!flag){
					try {
						out.println("registerStation2('" + sID + "','" + sName + "','"+ sPicture + "','"+ sType + "','"
								+ sX + "','" + sY + "','" + 0 +"','" + sIconId +"','" + sIsChangeIcon +"','" + sWidth +"','" + sHeight+ "','" + sSubview + "');");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	/**
	 * 注册站点和直线
	 * @param out 将内容输出到jsp页面
	 * @param fileName 要读取的xml文件
	 */
	public static void registerFromFile(JspWriter out,String fileName, String userName, String userId){
		String path = Constants.webRealPath + "file/user/" + userName + "_" + userId + "/";
		org.w3c.dom.Document xmldoc = W3CXML.loadXMLDocumentFromFile(path + fileName);
		// 取得viewName.xml的to:touter节点
		if (xmldoc == null) {
			Logger logger = Logger.getLogger(MyXmlUtil.class.getName());
			logger.warn(path + fileName + "该文件不存在！");
			//TODO 注意这里没有正确的输出信息   如果没响应的文件在流程上也没有响应的操作  很简单，先把现在的任务做完。
			return;
		}
		NodeList linkLists = xmldoc.getElementsByTagName("to:link");
		boolean linkFlag;
		registerNode(out,xmldoc,"to:router");
		registerNode(out,xmldoc,"to:switch");
		registerNode(out,xmldoc,"to:server");
		registerNode(out,xmldoc,"to:workstation");
		registerNode(out,xmldoc,"to:custom");
		registerService(out,xmldoc,"to:service");
		if(linkLists!=null){
			for (int i = 0; i < linkLists.getLength(); i++) {
				linkFlag = false;
				Node linkNode = linkLists.item(i);// linkNode是每一个to:link节点
				NodeList childLists = linkNode.getChildNodes();// childLists存放link节点的子节点
				String sID = "";
				String sName = "";
				String sSrcId = "";
				String sDestId = ""; 
				String sSrcInterfaceId = "";
				String sDestInterfaceId = "";
				// 以下for把linkNode的内容赋值给link对象
				for (int j = 0; j < childLists.getLength(); j++) {
					Node childNode = childLists.item(j);// childNode是link的每一个子节点
					String nodeName = childNode.getNodeName();// nodeName是每一个子节点的名字
					if (nodeName.equals("to:id")) {
						if("".equals(childNode.getTextContent())){
							linkFlag = true;
						}else{
							sID = "s" + childNode.getTextContent(); //拖拽库貌似不支持int型的ID
						}
					} else if (nodeName.equals("to:name")) {
						sName = childNode.getTextContent();
					} else if (nodeName.equals("to:srcId")) {
						sSrcId = childNode.getTextContent();
					} else if (nodeName.equals("to:destId")) {
						sDestId = childNode.getTextContent();
					}else if (nodeName.equals("to:srcInterfaceId")) {
						sSrcInterfaceId = childNode.getTextContent();
					}else if (nodeName.equals("to:destInterfaceId")) {
						sDestInterfaceId = childNode.getTextContent();
					}
				}
				if(sSrcInterfaceId.equals("") || sSrcInterfaceId==null){
					sSrcInterfaceId = "0";
				}
				if(sDestInterfaceId.equals("") || sDestInterfaceId==null){
					sDestInterfaceId = "0";
				}
				if(!linkFlag){
					try {
					out.println("registerLine3('" + sID + "','" + sName 
							+ "','s"	+ sSrcId + "','s" + sDestId + "','" + sSrcInterfaceId + "','" + sDestInterfaceId + "');");
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
	}
	public static void writeImage(JspWriter out,String viewName, String userName, String userId){
		String path = Constants.webRealPath + "file/user/" + userName + "_" + userId + "/";
		String fileStr = path + viewName + ".xml";
		try {
			org.w3c.dom.Document xmlViewdoc = W3CXML.loadXMLDocumentFromFile(fileStr);
			NodeList nodes = xmlViewdoc.getElementsByTagName("to:backGround");
			Node node = nodes.item(0);
			String backGround = node.getTextContent();
			out.println("<div id=\"pic1\" style=\"width:2000px;height:2000px;;background-repeat:no-repeat;background-image:url("
							+ backGround + ");border:0px 0px\">");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void writeImage(JspWriter out){
		try {
			//out.println("<div id=\"pic1\" style=\"width:100%;height:768px;;background-repeat:repeat-x;background-image:url(images/null.gif);border:1px solid\">");
			out.println("<div id=\"pic1\">");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void writeIcon(JspWriter out){
		try {
			out.println("<div style=\"display:none;\">");
			//求出图标列表，动态生成图标文件
			icons = deviceDAO.getAllDeviceIcons("all");
			 for(DeviceIcon icon:icons){
				 out.println("<img name=\"img"+icon.getId()+"\" src=\"images/"+icon.getIconFile()+"\" width=\"1\" height=\"1\">");
			 }
			out.println("</div>");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}	
	public static void writeIconHead(JspWriter out){
		try {
			if(icons==null){
				icons = deviceDAO.getAllDeviceIcons("all");
			}
			StringBuffer sb = new StringBuffer();
			sb.append("SET_DHTML(CURSOR_MOVE,");
			for(DeviceIcon icon:icons){
				 sb.append("\"img").append(icon.getId()).append("\",");
			}
			String temp = sb.toString();
			if(temp.lastIndexOf(",")==temp.length()-1){
				temp = temp.substring(0,temp.length()-1);
			}
			out.println(temp+");");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void writeRegister(JspWriter out){
		try {
			StringBuffer sb = new StringBuffer();
			if(icons==null){
				icons = deviceDAO.getAllDeviceIcons("all");
			}
			for(DeviceIcon icon:icons){
				out.println( "case \""+icon.getId()+"\":{");
				out.println("dd.elements.img"+icon.getId()+".copy(1);");
				out.println("i = dd.elements.img"+icon.getId()+".copies.length - 1;");
				out.println("addElement = dd.elements.img"+icon.getId()+".copies[i];");
				out.println("if(isChangeIcon=='1' &&  deviceImg!='images/"+icon.getIconFile()+"'){");
				out.println("addElement.swapImage(deviceImg);");
				out.println("}");
				out.println("break;");
				out.println("}");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	
	public static void main(String[] args) {
		if(icons==null){
			icons = deviceDAO.getAllDeviceIcons("all");
		}
		for(DeviceIcon icon:icons){
			System.out.println( "case \""+icon.getId()+"\":{");
			System.out.println("dd.elements.img"+icon.getId()+".copy(1);");
			System.out.println("i = dd.elements.img"+icon.getId()+".copies.length - 1;");
			System.out.println("addElement = dd.elements.img"+icon.getId()+".copies[i];");
			System.out.println("if(deviceImg!='' &&  deviceImg!='images/"+icon.getIconFile()+"'){");
			System.out.println("addElement.swapImage(deviceImg);");
			System.out.println("}");
			System.out.println("break;");
			System.out.println("}");
		}
	}
	public static void writeDiv(JspWriter out){
		try {
			out.println("</div>");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/**
	 * 获得配置文件中部分标签的值,并以结果集的方式返回(主要用于视图管理中事件状态和故障状态页面)
	 * @param file
	 */
	public static List<Router> getNodeIdList(String file){
		org.w3c.dom.Document xmldoc = W3CXML.loadXMLDocumentFromFile(file);
		List<Router> nodes = new ArrayList<Router>();
		getNodeValue(xmldoc,"to:router",nodes);
		getNodeValue(xmldoc,"to:switch",nodes);
		getNodeValue(xmldoc,"to:server",nodes);
		getNodeValue(xmldoc,"to:workstation",nodes);
		getNodeValue(xmldoc,"to:custom",nodes);
		return nodes;
	}
	
	public static void getNodeValue(org.w3c.dom.Document xmldoc,String tagName,List<Router> nodes){
		NodeList nodeLists = xmldoc.getElementsByTagName(tagName); // NodeList包括所有名字为router的节点
		boolean flag;
		if(nodeLists!=null){
			for (int i = 0; i < nodeLists.getLength(); i++) {
				flag = false;
				Node routerNode = nodeLists.item(i);// routerNode是每一个router节点
				NodeList childLists = routerNode.getChildNodes();// childLists存放的是router的子节点
				Router router = new Router();
				// 以下for把routerNode的内容赋值给router对象
				for (int j = 0; j < childLists.getLength(); j++) {
					Node childNode = childLists.item(j);// childNode是router的每一个子节点
					String nodeName = childNode.getNodeName();// nodeName是每一个子节点的名字
					if (nodeName.equals("to:id")) {
						if("".equals(childNode.getTextContent())){
							flag = true;
						}else{
							router.setId(Integer.valueOf(childNode.getTextContent())); 
						}
					} else if (nodeName.equals("to:name")) {
						router.setName(childNode.getTextContent());
					} 
				}//Endof for (int j = 0; j < childLists.getLength(); j++) {
				if(!flag){
					nodes.add(router);
				}
			}//Endof for (int i = 0; i < nodeLists.getLength(); i++) {
		}//Endof if(nodeLists!=null){
	}
}
