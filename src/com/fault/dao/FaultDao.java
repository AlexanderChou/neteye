package com.fault.dao;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.base.model.IfInterface;
import com.base.service.DeviceService;
import com.base.service.PortService;
import com.base.util.Constants;
import com.base.util.JDOMXML;
import com.base.util.W3CXML;
import com.opensymphony.xwork2.ActionContext;

public class FaultDao {
	DeviceService service=new DeviceService();
	PortService ports=new PortService();
	public boolean CreateQueryXML(String fileName){
		 boolean flag=false;
		 try {
			File fileDir=new File(Constants.webRealPath+"file/fault/tmp/");
				if(!fileDir.exists()){
					fileDir.mkdirs();
				}else if(!fileDir.isDirectory()){
					fileDir.delete();
					fileDir.mkdirs();
				}
			String path=Constants.webRealPath+"file/fault/tmp/";		
			File file=new File(path+fileName+".xml");
			if(file.exists()){
				file.delete();
			}			
			Long userId = (Long)ActionContext.getContext().getSession().get("userId");
			String userName = (String)ActionContext.getContext().getSession().get("userName");
			String viewPath = Constants.webRealPath + "file/user/" + userName + "_" + userId + "/";
			File viewFile=new File(viewPath+fileName+".xml");
			if(viewFile.exists()){
				org.w3c.dom.Document xmlViewdoc = W3CXML.loadXMLDocumentFromFile(viewPath+fileName+".xml");
				NodeList nodeLists = xmlViewdoc.getElementsByTagName("to:routers"); 
				if(nodeLists!=null){
					Document document = DocumentHelper.createDocument();
					Element root = document.addElement("datas");
					for (int i = 0; i < nodeLists.getLength(); i++) {
						Node rootNode = nodeLists.item(i);
						NodeList childLists = rootNode.getChildNodes();
						for (int j = 0; j < childLists.getLength(); j++) {
							Node childNode = childLists.item(j);
								if(childNode.getFirstChild()!=null){
									if(childNode.getNodeName().equals("to:router")){
										NodeList deviceList = childNode.getChildNodes();
										for (int k = 0; k < deviceList.getLength(); k++) {
											Node deviceNode = deviceList.item(k);
											if(deviceNode.getNodeName().equals("to:id")){
												String IP=service.getLoopbackIp(Integer.parseInt(deviceNode.getTextContent().trim()));
												Element IPResult = root.addElement("IP");					
												IPResult.addText(IP);
												String IPv6=service.getLoopbackIpv6(Integer.parseInt(deviceNode.getTextContent().trim()));
												if(IPv6!=null&&!IPv6.equals("")){
													Element IPv6Result = root.addElement("IP");					
													IPv6Result.addText(IPv6);
												}
												List portList=ports.getPortList(Long.parseLong(deviceNode.getTextContent().trim()));
												if(portList!=null){
													for(int l=0;l<portList.size();l++){
														IfInterface port=(IfInterface)portList.get(l);
														if(port!=null&&port.getIpv4()!=null&&!port.getIpv4().equals("")){
															Element ifinterface = root.addElement("IP");					
															ifinterface.addText(port.getIpv4());
														}
														if(port!=null&&port.getIpv6()!=null&&!port.getIpv6().equals("")){
															Element ifinterface2 = root.addElement("IP");					
															ifinterface2.addText(port.getIpv6());
														}
													}
												}
											}																		
									  }
																										
									}
								}	
						}
					}
					JDOMXML.saveXml(path+fileName+".xml", document);				
				}
			}
			flag=true;
		} catch (Exception e) {
			flag=false;
			e.printStackTrace();			
		} 
		return flag;
	}
}
