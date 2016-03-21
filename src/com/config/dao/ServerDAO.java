package com.config.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.base.model.Device;
import com.base.service.DeviceService;
import com.base.util.Constants;
import com.base.util.W3CXML;
import com.config.dto.Server;

public class ServerDAO {
	private final String resultFile=Constants.webRealPath+"file/service/dat/servicetest.xml";
	private final String infoFile=Constants.webRealPath+"file/service/serviceinfo.xml";
	public List<Server> getServiceInfo(Long id,String IP) throws Exception{
		List serviceList=new ArrayList();
		File xmlFile=new File(resultFile);
		if(xmlFile.exists()){
			Hashtable urlTable=this.getPicUrl(id);
			org.w3c.dom.Document xmlViewdoc = W3CXML.loadXMLDocumentFromFile(resultFile);
			NodeList nodeLists = xmlViewdoc.getElementsByTagName("device"); 
			if(nodeLists!=null){
				for (int i = 0; i < nodeLists.getLength(); i++) {
					Node routerNode = nodeLists.item(i);
					NodeList childLists = routerNode.getChildNodes();
					for (int j = 0; j < childLists.getLength(); j++) {
						Node childNode = childLists.item(j);
						if(childNode.getNodeName().equals("IP")&&!childNode.getTextContent().equals(IP)){
							j=childLists.getLength();
						}else{
							if(childNode.getFirstChild()!=null){
								if(childNode.getNodeName().equals("dns")){
									Server server=new Server();
									server.setStyle(childNode.getNodeName());
									String description="";
									NodeList serviceLists = childNode.getChildNodes();
									for (int k = 0; k < childLists.getLength(); k++) {
										Node serviceNode = serviceLists.item(k);									
										if(serviceNode.getFirstChild()!=null){
											description+=serviceNode.getNodeName()+": "+serviceNode.getTextContent().trim()+"\n";
											server.setDescription(description.trim());
										}
									}
									server.setUrl(urlTable.get("dns").toString());
									serviceList.add(server);
								}
								if(childNode.getNodeName().equals("ftp")){
									Server server=new Server();
									server.setStyle(childNode.getNodeName());
									String description="";
									NodeList serviceLists = childNode.getChildNodes();
									for (int k = 0; k < serviceLists.getLength(); k++) {
										Node serviceNode = serviceLists.item(k);									
										if(serviceNode.getFirstChild()!=null){
											description+=serviceNode.getNodeName()+": "+serviceNode.getTextContent().trim()+"\n";
											server.setDescription(description.trim());;
										}
									}
									server.setUrl(urlTable.get("ftp").toString());
									serviceList.add(server);
								}
								if(childNode.getNodeName().equals("http")){
									Server server=new Server();
									server.setStyle(childNode.getNodeName());
									String description="";
									NodeList serviceLists = childNode.getChildNodes();
									for (int k = 0; k < serviceLists.getLength(); k++) {
										Node serviceNode = serviceLists.item(k);									
										if(serviceNode.getFirstChild()!=null){
											description+=serviceNode.getNodeName()+": "+serviceNode.getTextContent().trim()+"\n";
											server.setDescription(description.trim());
										}
									}
									server.setUrl(urlTable.get("http").toString());
									serviceList.add(server);
								}
								if(childNode.getNodeName().equals("pop3")){
									Server server=new Server();
									server.setStyle(childNode.getNodeName());
									String description="";
									NodeList serviceLists = childNode.getChildNodes();
									for (int k = 0; k < serviceLists.getLength(); k++) {
										Node serviceNode = serviceLists.item(k);									
										if(serviceNode.getFirstChild()!=null){
											description+=serviceNode.getNodeName()+": "+serviceNode.getTextContent().trim()+"\n";
											server.setDescription(description.trim());
										}
									}
									server.setUrl(urlTable.get("pop3").toString());
									serviceList.add(server);
								}
								if(childNode.getNodeName().equals("smtp")){
									Server server=new Server();
									server.setStyle(childNode.getNodeName());
									String description="";
									NodeList serviceLists = childNode.getChildNodes();
									for (int k = 0; k < serviceLists.getLength(); k++) {
										Node serviceNode = serviceLists.item(k);									
										if(serviceNode.getFirstChild()!=null){
											description+=serviceNode.getNodeName()+": "+serviceNode.getTextContent().trim()+"\n";
											server.setDescription(description.trim());
										}
									}
									server.setUrl(urlTable.get("smtp").toString());
									serviceList.add(server);
								}
								if(childNode.getNodeName().equals("snmp")){
									Server server=new Server();
									server.setStyle(childNode.getNodeName());
									String description="";
									NodeList serviceLists = childNode.getChildNodes();
									for (int k = 0; k < serviceLists.getLength(); k++) {
										Node serviceNode = serviceLists.item(k);									
										if(serviceNode.getFirstChild()!=null){
											if(serviceNode.getNodeName().equals("cpu")){	
												description+=serviceNode.getNodeName()+" \n";
												NodeList processesLists = serviceNode.getChildNodes();
												for (int l = 0; l < processesLists.getLength(); l++) {
													Node processesNode = processesLists.item(l);									
													if(processesNode.getFirstChild()!=null){
														if(processesNode.getNodeName().equals("subcpu")){
															description+="  "+processesNode.getNodeName()+" \n";
															NodeList subLists = processesNode.getChildNodes();
															for (int m = 0; m < subLists.getLength(); m++) {
																Node subNode = subLists.item(m);									
																if(subNode.getFirstChild()!=null){
																	description+="\t"+subNode.getNodeName()+": "+subNode.getTextContent().trim()+"\n";
																}
															}
														}
														if(processesNode.getNodeName().equals("userate")){
															description+=processesNode.getNodeName()+": "+processesNode.getTextContent().trim()+"\n";
															
														}
													}												
												}											
											}if(serviceNode.getNodeName().equals("disk")){
												description+=serviceNode.getNodeName()+" \n";
												NodeList processesLists = serviceNode.getChildNodes();
												for (int l = 0; l < processesLists.getLength(); l++) {
													Node processesNode = processesLists.item(l);									
													if(processesNode.getFirstChild()!=null){
														if(processesNode.getNodeName().equals("size")){
															description+=processesNode.getNodeName()+": "+processesNode.getTextContent().trim()+"\n";
														}
														if(processesNode.getNodeName().equals("subdisk")){
															description+="  "+processesNode.getNodeName()+" \n";
															NodeList subLists = processesNode.getChildNodes();
															for (int m = 0; m < subLists.getLength(); m++) {
																Node subNode = subLists.item(m);									
																if(subNode.getFirstChild()!=null){
																	description+="\t"+subNode.getNodeName()+": "+subNode.getTextContent().trim()+"\n";
																}
															}
														}
													}
												}
											}if(serviceNode.getNodeName().equals("memory")){
												description+=serviceNode.getNodeName()+" \n";
												NodeList processesLists = serviceNode.getChildNodes();
												for (int l = 0; l < processesLists.getLength(); l++) {
													Node processesNode = processesLists.item(l);									
													if(processesNode.getFirstChild()!=null){
														if(processesNode.getNodeName().equals("size")){
															description+=processesNode.getNodeName()+": "+processesNode.getTextContent().trim()+"\n";
														}
														if(processesNode.getNodeName().equals("submem")){
															description+="  "+processesNode.getNodeName()+" \n";
															NodeList subLists = processesNode.getChildNodes();
															for (int m = 0; m < subLists.getLength(); m++) {
																Node subNode = subLists.item(m);									
																if(subNode.getFirstChild()!=null){
																	description+="\t"+subNode.getNodeName()+": "+subNode.getTextContent().trim()+"\n";
																}
															}
														}
													}
												}
											}if(serviceNode.getNodeName().equals("processes")){
												description+=serviceNode.getNodeName()+" \n";
												NodeList processesLists = serviceNode.getChildNodes();
												for (int l = 0; l < processesLists.getLength(); l++) {
													Node processesNode = processesLists.item(l);									
													if(processesNode.getFirstChild()!=null){
														if(processesNode.getNodeName().equals("size")){
															description+=processesNode.getNodeName()+": "+processesNode.getTextContent().trim()+"\n";
														}
														if(processesNode.getNodeName().equals("process")){
															description+="  "+processesNode.getNodeName()+" \n";
															NodeList subLists = processesNode.getChildNodes();
															for (int m = 0; m < subLists.getLength(); m++) {
																Node subNode = subLists.item(m);									
																if(subNode.getFirstChild()!=null){
																	String path=subNode.getTextContent();
																	if(path.length()>20){
																		path="......";
																	}
																	description+="\t"+subNode.getNodeName()+": "+path+"\n";
																}
															}
														}
													}
												}
												server.setDescription(description.trim());
												server.setUrl(IP);
												serviceList.add(server);
											}										
										}									
									}
								}
							}							
						}	
					}
				}
			}
		}
		return serviceList;		
	}
	public Hashtable getPicUrl(Long Id) throws Exception{
		Hashtable table=new Hashtable();
		DeviceService service=new DeviceService();
		Device device=service.getDevice(Id);
		if(device!=null){
			String typle=device.getService();
			if(typle!=null){
				String a[]=typle.split(";");
				String b[]=device.getLabel().split(";");
				for(int i=0;i<a.length;i++){
					table.put(a[i], b[i]+"_"+a[i]);
				}
			}
		}
		return table;
	}
	
	public void InsertXML(Hashtable serverTable) throws Exception{
	    
	    File fileDir=new File(Constants.webRealPath+"file/service");
		if(!fileDir.exists()){
			fileDir.mkdir();
		}else if(!fileDir.isDirectory()){
			fileDir.delete();
			fileDir.mkdir();
		}
		File xmlFile=new File(infoFile);
		if(!xmlFile.exists()){
			PrintWriter pw= new PrintWriter(new FileOutputStream(xmlFile));
			String top="<?xml version='1.0' encoding='UTF-8'?>";
			pw.write(top) ;
			pw.write("\r\n");
			pw.write("<devices>");
			pw.write("\r\n");
			pw.write("</devices>");
			pw.close();
		}
		DocumentBuilderFactory  dbf=DocumentBuilderFactory.newInstance();
	    DocumentBuilder db=dbf.newDocumentBuilder();
	    Document document=db.parse(new File(infoFile));
	    Node root=document.getDocumentElement();	    
	    NodeList childs=root.getChildNodes();
	    Element newnode=(Element)document.createElement("device");
	    String IP=serverTable.get("IP").toString();
	    Element ip=(Element)document.createElement("IP"); 
	    ip.appendChild(document.createTextNode(IP));
	    newnode.appendChild(ip);
	    
	    if(serverTable.get("snmp")!=null){
	    	String a[]=serverTable.get("snmp").toString().split(";");
		    Element snmp=(Element)document.createElement("snmp"); 
		    Element snmpIp=(Element)document.createElement("IP");
		    snmpIp.appendChild(document.createTextNode(a[0]));
		    Element community=(Element)document.createElement("community");
		    community.appendChild(document.createTextNode(a[1]));
		    snmp.appendChild(snmpIp);
		    snmp.appendChild(community);
		    newnode.appendChild(snmp);
	    }
	    if(serverTable.get("ftp")!=null){
	    	String a[]=serverTable.get("ftp").toString().split(";");
		    Element ftp=(Element)document.createElement("ftp"); 
		    Element server=(Element)document.createElement("server");
		    server.appendChild(document.createTextNode(a[0]));
		    Element port=(Element)document.createElement("port");
		    port.appendChild(document.createTextNode(a[1]));
		    Element username=(Element)document.createElement("username");
		    username.appendChild(document.createTextNode(a[2]));
		    Element password=(Element)document.createElement("password");
		    password.appendChild(document.createTextNode(a[3]));
		    Element URL=(Element)document.createElement("URL");
		    URL.appendChild(document.createTextNode(a[4]));
		    
		    ftp.appendChild(server);
		    ftp.appendChild(port);
		    ftp.appendChild(username);
		    ftp.appendChild(password);
		    ftp.appendChild(URL);
		    newnode.appendChild(ftp);
	    }
	    if(serverTable.get("http")!=null){
	    	String a[]=serverTable.get("http").toString().split(";");
		    Element http=(Element)document.createElement("http"); 
		    Element URL=(Element)document.createElement("URL");
		    URL.appendChild(document.createTextNode(a[0]));
		    Element port=(Element)document.createElement("port");
		    port.appendChild(document.createTextNode(a[1]));
		    http.appendChild(URL);
		    http.appendChild(port);
		    newnode.appendChild(http);
	    }
	    if(serverTable.get("dns")!=null){
	    	String a[]=serverTable.get("dns").toString().split(";");
		    Element dns=(Element)document.createElement("dns"); 
		    Element server=(Element)document.createElement("server");
		    server.appendChild(document.createTextNode(a[0]));
		    Element port=(Element)document.createElement("port");
		    port.appendChild(document.createTextNode(a[1]));
		    
		    dns.appendChild(server);
		    dns.appendChild(port);
		    newnode.appendChild(dns);
	    }
	    if(serverTable.get("smtp")!=null){
	    	String a[]=serverTable.get("smtp").toString().split(";");
		    Element smtp=(Element)document.createElement("smtp"); 		    
		    Element server=(Element)document.createElement("server");
		    server.appendChild(document.createTextNode(a[0]));		    
		    Element source=(Element)document.createElement("source");
		    source.appendChild(document.createTextNode(a[1]));		    
		    Element port=(Element)document.createElement("port");
		    port.appendChild(document.createTextNode(a[2]));		   
		    Element destination=(Element)document.createElement("destination");
		    destination.appendChild(document.createTextNode(a[3]));
		    
		    smtp.appendChild(server);
		    smtp.appendChild(source);
		    smtp.appendChild(port);
		    smtp.appendChild(destination);
		    newnode.appendChild(smtp);
	    }
	    if(serverTable.get("pop3")!=null){
	    	String a[]=serverTable.get("pop3").toString().split(";");
		    Element pop3=(Element)document.createElement("pop3"); 
		    Element server=(Element)document.createElement("server");
		    server.appendChild(document.createTextNode(a[0]));
		    Element port=(Element)document.createElement("port");
		    port.appendChild(document.createTextNode(a[1]));
		    Element username=(Element)document.createElement("username");
		    username.appendChild(document.createTextNode(a[2]));
		    Element password=(Element)document.createElement("password");
		    password.appendChild(document.createTextNode(a[3]));
		    
		    pop3.appendChild(server);
		    pop3.appendChild(port);
		    pop3.appendChild(username);
		    pop3.appendChild(password);
		    newnode.appendChild(pop3);
	    }
	    Node secondnode =childs.item(1);
	    
	    root.insertBefore(newnode,secondnode);
	    TransformerFactory   tFactory   =TransformerFactory.newInstance();
	    Transformer   transformer   =   tFactory.newTransformer();
	    DOMSource   source   =   new   DOMSource(document);
	    StreamResult   result   =   new   StreamResult(new   java.io.File(infoFile));
	    transformer.transform(source,   result);		 	    
	}
	public void deleteXML(String ip) throws Exception{
		 File file=new File(infoFile);
		 if(file.exists()){	
		   org.w3c.dom.Document xmlViewdoc = W3CXML.loadXMLDocumentFromFile(infoFile);
	       NodeList   nodeLists=xmlViewdoc.getDocumentElement().getChildNodes();
	       boolean flag = false;
	       for(int i=0;i<nodeLists.getLength() ;i++){
	    	    Node routerNode = nodeLists.item(i);
				NodeList childLists = routerNode.getChildNodes();
				for (int j = 0; j < childLists.getLength(); j++) {
					Node childNode = childLists.item(j);					
					if(childNode.getNodeName().equals("IP")&&childNode.getTextContent().equals(ip)){
				    	xmlViewdoc.getDocumentElement().removeChild(nodeLists.item(i)) ;
				    	flag = true;
				     }				    
				}
	        }
	        if(flag){
			    javax.xml.transform.Transformer tf=null;
			    javax.xml.transform.dom.DOMSource dm =new javax.xml.transform.dom.DOMSource(xmlViewdoc);
			    javax.xml.transform.TransformerFactory tff=javax.xml.transform.TransformerFactory.newInstance();
			    tf = tff.newTransformer();
			    tf.transform(dm, new javax.xml.transform.stream.StreamResult(new   java.io.File(infoFile)));
	        }
		 }
	}	
}
