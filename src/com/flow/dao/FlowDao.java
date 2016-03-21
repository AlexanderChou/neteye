package com.flow.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.base.model.Device;
import com.base.model.Link;
import com.base.service.DeviceService;
import com.base.service.LinkService;
import com.base.service.PortService;
import com.base.util.Constants;
import com.base.util.HibernateUtil;
import com.base.util.JDOMXML;
import com.base.util.W3CXML;
import com.opensymphony.xwork2.ActionContext;

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
public class FlowDao {
	private DeviceService service=new DeviceService();
	private PortService ports=new PortService();
	public Hashtable getDevice(int flag)throws Exception{
		Hashtable table = new Hashtable();
		Hashtable temp = new Hashtable();
		File name;
		if(flag==1){
			name = new File( Constants.webRealPath+"file/flow/flowscan/dat/flow.txt" );
		}else{
			name = new File( Constants.webRealPath+"file/flow/physicalscan/dat/flow.txt" );	
		}
		
		if(name.exists()){
			BufferedReader input = new BufferedReader( new FileReader( name ) );
			String text;					
			while ( ( text = input.readLine() ) != null )
			{
				if(text.contains("|")){
					String ip=text.substring(0,text.indexOf("|"));
					if(temp.isEmpty()){
						temp.put(ip, 1);
					}else{
						if(temp.get(ip)==null){
							temp.put(ip, 1);
						}
					}
				}
			}
		}
		if(temp!=null&&!temp.isEmpty()){
			Iterator  iterator=temp.keySet().iterator();
			while(iterator.hasNext()){				
				String ip=iterator.next().toString();
				Device device=new Device();
				if(ip!=null&&!"".equals(ip)){
				device=service.findDeviceByIP(ip);}
				
				if(device!=null){
					table.put(ip,device);
				}
			}
		}
		return table;
	}
	
	/**
	 * 由设备loopbackip和interface得到改端口的链路名字  格式为 ： （上端口）中文名字/英文名字---（下端口）中文名字/英文名字
	 * @param ip 设备的loopbackip
	 * @param ifinterface 设备端口号
	 * @return 
	 */
	public String getLinkName(String ip, String ifinterface){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select ufb.name , ufb.chinesename, usb.name, usb.chinesename from  (select name , chinesename  from device join (select downdevice, updevice   from link where upinterface = '" + ifinterface  + "' and updevice in (select id from device where loopbackip='" + ip + "')) as  ttt " +  
				" on ttt.updevice =device.id) as ufb join (select name , chinesename  from device join (select downdevice, updevice   from link where  upinterface = '" + ifinterface + "' and updevice in (select id from device where loopbackip='" + ip + "')) as  ttt " +
                " on ttt.downdevice =device.id) as usb union " +  
                " select dsb.name, dsb.chinesename, dfb.name, dfb.chinesename from  (select name , chinesename from device join (select downdevice, updevice   from link where downinterface='" + ifinterface + "' and downdevice in (select id from device where loopbackip='" + ip + "')) as  ttt " +  
                " on ttt.downdevice =device.id) as dfb join (select name , chinesename  from device join (select downdevice, updevice   from link where  downinterface='" + ifinterface + "' and downdevice in (select id from device where loopbackip='" + ip + "')) as  ttt " +  
                " on ttt.updevice =device.id) as dsb;";
		List<Object[]> list = session.createSQLQuery(sql).list();
		transaction.commit();
		
		StringBuffer sb = new StringBuffer();
		if (!list.isEmpty()) {
			Object[] arr = list.get(0);
			
			if (arr[1] != null) {
				sb.append(arr[1]);
			} else {
				sb.append(arr[0]);
			}
			
			sb.append(" --> ");
			
			if (arr[3] != null) {
				sb.append(arr[3]);
			} else {
				sb.append(arr[2]);
			}
		}
		return sb.toString();
	}
	public boolean CreateQueryXML(String fileName){
		 boolean flag=false;
		 try {
			File fileDir=new File(Constants.webRealPath+"file/flow/flowscan/dat/tmp/");
				if(!fileDir.exists()){
					fileDir.mkdirs();
				}else if(!fileDir.isDirectory()){
					fileDir.delete();
					fileDir.mkdirs();
				}
			String path=Constants.webRealPath+"file/flow/flowscan/dat/tmp/";		
			File file=new File(path+fileName+".xml");
			if(file.exists()){
				file.delete();
			}
			
			if(fileName.equals("All")){
				DeviceService service=new DeviceService();
				LinkService links=new LinkService();
				List deviceList=service.getAllDevice();
				if(deviceList!=null){
					Document document = DocumentHelper.createDocument();
					Element root = document.addElement("datas");
					for(int i=0;i<deviceList.size();i++){
						Device device=(Device) deviceList.get(i);
						Long id=device.getId();
						
						List linkList=links.getLinkList(id);
						if(linkList!=null&&linkList.size()>0){;
							Element results = root.addElement("data");
							Element IPResult = results.addElement("IP");					
							IPResult.addText(device.getLoopbackIP());
							Element result = results.addElement("ifIndexs");
							for(int j=0;j<linkList.size();j++){
								Link link=(Link) linkList.get(j);						
								
								if(link.getUpDevice().intValue()==id.intValue()){
									Element subresult = result.addElement("ifIndex");
									subresult.addText(link.getUpInterface().getIfindex());
								}
								if(link.getDownDevice().intValue()==id.intValue()){
									Element subresult = result.addElement("ifIndex");
									subresult.addText(link.getDownInterface().getIfindex());
								}
							}					
							
						}
					}
					JDOMXML.saveXml(path+fileName+".xml", document);
				}
			}else{
				Long userId = (Long)ActionContext.getContext().getSession().get("userId");
				String userName = (String)ActionContext.getContext().getSession().get("userName");
				String viewPath = Constants.webRealPath + "file/user/" + userName + "_" + userId + "/";
				File viewFile=new File(viewPath+fileName+".xml");
				if(viewFile.exists()){
					org.w3c.dom.Document xmlViewdoc = W3CXML.loadXMLDocumentFromFile(viewPath+fileName+".xml");
					NodeList nodeLists = xmlViewdoc.getElementsByTagName("to:links"); 
					if(nodeLists!=null){
						Document document = DocumentHelper.createDocument();
						Element root = document.addElement("datas");
						for (int i = 0; i < nodeLists.getLength(); i++) {
							Node rootNode = nodeLists.item(i);
							NodeList childLists = rootNode.getChildNodes();
							for (int j = 0; j < childLists.getLength(); j++) {
								Node childNode = childLists.item(j);
									if(childNode.getFirstChild()!=null){
										if(childNode.getNodeName().equals("to:link")){
											NodeList deviceList = childNode.getChildNodes();
											String[] a=new String[4];
											for (int k = 0; k < deviceList.getLength(); k++) {
												Node deviceNode = deviceList.item(k);
												if(deviceNode.getNodeName().equals("to:srcId")){
													String IP=service.getLoopbackIp(Integer.parseInt(deviceNode.getTextContent().trim()));
													a[0]=IP;								
												}
												if(deviceNode.getNodeName().equals("to:srcInterfaceId")){
													String ifindex=ports.getIfindex(Integer.parseInt(deviceNode.getTextContent().trim()));
													a[1]=ifindex;							
												}
												if(deviceNode.getNodeName().equals("to:destId")){
													String IP=service.getLoopbackIp(Integer.parseInt(deviceNode.getTextContent().trim()));
													a[2]=IP;
												}									
												if(deviceNode.getNodeName().equals("to:destInterfaceId")){
													String ifindex=ports.getIfindex(Integer.parseInt(deviceNode.getTextContent().trim()));
													a[3]=ifindex;								
												}									
										  }
											Element results = root.addElement("data");
											Element IPResult = results.addElement("IP");					
											IPResult.addText(a[0]);
											Element result = results.addElement("ifIndexs");
											Element subresult = result.addElement("ifIndex");
											subresult.addText(a[1]);
											
											Element results2 = root.addElement("data");
											Element IPResult2 = results2.addElement("IP");					
											IPResult2.addText(a[2]);
											Element result2 = results2.addElement("ifIndexs");
											Element subresult2 = result2.addElement("ifIndex");
											subresult2.addText(a[3]);						
										}
									}	
							}
						}
						JDOMXML.saveXml(path+fileName+".xml", document);				
					}
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
