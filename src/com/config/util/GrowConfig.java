package com.config.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.base.model.Device;
import com.base.model.IfInterface;
import com.base.service.DeviceService;
import com.base.service.LinkService;
import com.base.service.PortService;
import com.base.util.Constants;
import com.base.util.JDOMXML;

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
public class GrowConfig {
	private PortService service=new PortService();
	private DeviceService deviceService=new DeviceService();
	private LinkService lService=new LinkService();
	public void WriteFaultFile() throws Exception{
		//List deviceList=deviceService.getDeviceByFault(Integer.valueOf(1));
		List deviceList=deviceService.getAllDevice();
		Hashtable verify=new Hashtable();
		if(deviceList!=null){
			String filePath = Constants.webRealPath+"file/fault/etc";
			//String filePath = "/opt/nms/fault/etc";
			File fileDir=new File(filePath);
			if(!fileDir.exists()){
				fileDir.mkdir();
			}else if(!fileDir.isDirectory()){
				fileDir.delete();
				fileDir.mkdir();
			}
			File fileName=new File(fileDir+"/nodelist");
			if(fileName.exists()){
				fileName.delete();
				fileName.createNewFile();
			}else{
				fileName.createNewFile();
			}
			File flagName=new File(fileDir+"/dosflag");
			if(flagName.exists()){
				flagName.delete();
				flagName.createNewFile();
			}else{
				flagName.createNewFile();
			}
			File activeFile=new File(Constants.webRealPath+"file/fault/dat/active");
			if(activeFile.exists()){
				activeFile.delete();
			}
			PrintWriter pw= new PrintWriter(new FileOutputStream(fileName));
			for(int i=0;i<deviceList.size();i++){
				Device device=(Device) deviceList.get(i);
				 if(device.getLoopbackIP()!=null&&!device.getLoopbackIP().equals("")){
					if(verify.get(device.getLoopbackIP())==null){
						verify.put(device.getLoopbackIP(), "1");
						pw.write(device.getLoopbackIP()) ;
						pw.write("\r\n");
					}
				 }
				 if(device.getLoopbackIPv6()!=null&&!device.getLoopbackIPv6().equals("")){
					if(verify.get(device.getLoopbackIPv6())==null){
						verify.put(device.getLoopbackIPv6(), "1");
						pw.write(device.getLoopbackIPv6()) ;
						pw.write("\r\n");
					}
				 }
				List portList=service.getPortList(device.getId());
				for(int j=0;j<portList.size();j++){
					IfInterface port=(IfInterface) portList.get(j);
					if(port.getIpv4()!=null&&!port.getIpv4().equals("")){
						if(verify.get(port.getIpv4())==null){
							verify.put(port.getIpv4(), "1");
							pw.write(port.getIpv4()) ;
							pw.write("\r\n");
						}
					}
				}
				for(int j=0;j<portList.size();j++){
					IfInterface port=(IfInterface) portList.get(j);
					if(port.getIpv6()!=null&&!port.getIpv6().equals("")){
						if(verify.get(port.getIpv6())==null){
							verify.put(port.getIpv6(), "1");
							pw.write(port.getIpv6()) ;
							pw.write("\r\n");
						}
						
					}
				}
			}
			pw.close();
			File flagTmp=new File(Constants.webRealPath+"file/fault/tmp/flag");
			if(flagTmp.exists()){
				flagTmp.delete();
				flagTmp.createNewFile();
			}else{
				flagTmp.createNewFile();
			}
		}
	}
	
	public void WriteChangeTrifficFile() throws Exception{
		List deviceList=deviceService.getRouterList(2);
		if(deviceList!=null){
			String filePath = Constants.webRealPath+"file/flow/flowscan";
			File fileDir=new File(filePath);
			if(!fileDir.exists()){
				fileDir.mkdirs();
			}else if(!fileDir.isDirectory()){
				fileDir.delete();
				fileDir.mkdirs();
			}
			
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("devices");
			for(int i=0;i<deviceList.size();i++){
				Device device=(Device) deviceList.get(i);
				if(device.getLoopbackIP()!=null&&!("").equals(device.getLoopbackIP())||device.getLoopbackIPv6()!=null&&!("").equals(device.getLoopbackIPv6())){
					Element results = root.addElement("device");
					Element ipaddr = results.addElement("ip");
					Element ipaddr6 = results.addElement("ipv6");
					if(!("").equals(device.getLoopbackIP())&&device.getLoopbackIP()!=null){
						ipaddr.addText(device.getLoopbackIP());
					}
					if(!("").equals(device.getLoopbackIPv6())&&device.getLoopbackIPv6()!=null){
						ipaddr6.addText(device.getLoopbackIPv6());
					}
					if(device.getReadCommunity()!=null&&!device.getReadCommunity().equals("")){
						Element Community = results.addElement("community");
						Community.addText(device.getReadCommunity());
					}
					Element company = results.addElement("company");
					if(device.getDescription()!=null){						
						if(device.getDescription().contains("Juniper")){
							company.addText("Juniper");
						}else if(device.getDescription().contains("Huawei")) {
							company.addText("Huawei");
						}else{
							company.addText("");
						}
					}else{
						company.addText("");
					}
					Element ports = results.addElement("interfaces");
					List portList=service.getAllMonitored(device.getId(),1);
					if(portList!=null){
						for(int j=0;j<portList.size();j++){
					    		IfInterface interf=(IfInterface)portList.get(j);
								if(interf.getTrafficFlag()==1){
								Element port=ports.addElement("interface");
								Element ifindex = port.addElement("ifindex");
								ifindex.addText(interf.getIfindex());
								Element upperthreshold = port.addElement("upperthreshold");
								if(interf.getUpperThreshold()!=null){
									upperthreshold.addText(interf.getUpperThreshold().toString());
								}else{
									upperthreshold.addText("");
								}							
								Element lowerthreshold = port.addElement("lowerthreshold");
								if(interf.getLowerThreshold()!=null){
									lowerthreshold.addText(interf.getLowerThreshold().toString());
								}else{
									lowerthreshold.addText("");
								}
								}
						}
					}
				}			
			}
//		System.out.println("start write document="+document.toString());
			JDOMXML.saveXml(filePath+ "/node.xml", document);
		}
	}
	
	public void WriteTrifficFile() throws Exception{
		List deviceList=deviceService.getRouterList(2);
		if(deviceList!=null){
			String filePath = Constants.webRealPath+"file/flow/flowscan";
			File fileDir=new File(filePath);
			if(!fileDir.exists()){
				fileDir.mkdirs();
			}else if(!fileDir.isDirectory()){
				fileDir.delete();
				fileDir.mkdirs();
			}
			
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("devices");
			for(int i=0;i<deviceList.size();i++){
				Device device=(Device) deviceList.get(i);
				if(device.getLoopbackIP()!=null&&!("").equals(device.getLoopbackIP())||device.getLoopbackIPv6()!=null&&!("").equals(device.getLoopbackIPv6())){
					Element results = root.addElement("device");
					Element ipaddr = results.addElement("ip");
					Element ipaddr6 = results.addElement("ipv6");
					if(!("").equals(device.getLoopbackIP())&&device.getLoopbackIP()!=null){
						ipaddr.addText(device.getLoopbackIP());
					}
					if(!("").equals(device.getLoopbackIPv6())&&device.getLoopbackIPv6()!=null){
						ipaddr6.addText(device.getLoopbackIPv6());
					}
					if(device.getReadCommunity()!=null&&!device.getReadCommunity().equals("")){
						Element Community = results.addElement("community");
						Community.addText(device.getReadCommunity());
					}
					Element company = results.addElement("company");
					if(device.getDescription()!=null){						
						if(device.getDescription().contains("Juniper")){
							company.addText("Juniper");
						}else if(device.getDescription().contains("Huawei")) {
							company.addText("Huawei");
						}else{
							company.addText("");
						}
					}else{
						company.addText("");
					}
					Element ports = results.addElement("interfaces");
					List portList=service.getAllMonitored(device.getId(),1);
					if(portList!=null){
						for(int j=0;j<portList.size();j++){
							IfInterface interf=(IfInterface)portList.get(j);
							interf.setTrafficFlag(0);
							boolean flag=lService.isUsed(device.getId(), interf.getId());
							if(flag){
								interf.setTrafficFlag(1);
								Element port=ports.addElement("interface");
								Element ifindex = port.addElement("ifindex");
								ifindex.addText(interf.getIfindex());
								Element upperthreshold = port.addElement("upperthreshold");
								if(interf.getUpperThreshold()!=null){
									upperthreshold.addText(interf.getUpperThreshold().toString());
								}else{
									upperthreshold.addText("");
								}							
								Element lowerthreshold = port.addElement("lowerthreshold");
								if(interf.getLowerThreshold()!=null){
									lowerthreshold.addText(interf.getLowerThreshold().toString());
								}else{
									lowerthreshold.addText("");
								}
							}
							service.update(interf);
						}
					}
				}			
			}
//			System.out.println("start write document="+document.toString());
			JDOMXML.saveXml(filePath+ "/node.xml", document);
		}
	}
}
