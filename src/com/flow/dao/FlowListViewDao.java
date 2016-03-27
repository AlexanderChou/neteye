package com.flow.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.base.model.Device;
import com.base.service.DeviceService;
import com.base.service.PortService;
import com.base.util.Constants;
import com.base.util.HibernateUtil;
import com.base.util.W3CXML;
import com.flow.dto.FlowListTemp;

public class FlowListViewDao {
	private DeviceService service=new DeviceService();
	public String getDeviceIp(Long id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select * from device where id = "+ id ;
		List<Device> tp = session.createSQLQuery(sql).addEntity(Device.class).list();
		String ip ="";
		if(!("").equals(tp.get(0).getLoopbackIP())&&tp.get(0).getLoopbackIP()!=null)
		{
		ip = tp.get(0).getLoopbackIP();
		}else{
		ip = tp.get(0).getLoopbackIPv6();
		}
		transaction.commit();
		return ip;
	}
	public  List<FlowListTemp>  getinf(String ip) throws Exception{
		  File name = new File( Constants.webRealPath+"file/flow/flowscan/dat/flow.txt" );
			List<FlowListTemp> inflist =new ArrayList() ;
			String text;
			if(name.exists()){	
				BufferedReader input = new BufferedReader( new FileReader( name ) );
				while ( ( text = input.readLine() ) != null )
				{ 
					if(!text.equals("")&&text.contains("|")){
						String a[]=text.split("\\|");
						if(a[0].equals(ip)&&!("0").equals(a[2])){
							String ipname = "NoName";
							//此处只需查一次数据库即可，需要优化
							if(service.findDeviceByIP(ip).getDescription()!=null&&!("").equals(service.findDeviceByIP(ip).getDescription()))
							{
								ipname = service.findDeviceByIP(ip).getDescription();
							}
							if(service.findDeviceByIP(ip).getName()!=null&&!("").equals(service.findDeviceByIP(ip).getName()))
							{
							ipname = service.findDeviceByIP(ip).getName();
							}
							if(service.findDeviceByIP(ip).getChineseName()!=null&&!("").equals(service.findDeviceByIP(ip).getChineseName()))
							{
							ipname = service.findDeviceByIP(ip).getChineseName();
								}
//							PortService portService=new PortService();
//							List<IfInterface> portList=portService.getPortList(service.findDeviceByIP(ip).getId());
							String ipde ="";
							String ipto6 ="";
							String ip2 ="";
//							for(int i=0;i<portList.size();i++){
//								if(portList.get(i).getIfindex().equals(a[1])){
//									ipde =portList.get(i).getDescription();
//									ipto6= portList.get(i).getIpv6();
//									
//								}
//							}
//							
							String iptestname =ipname+"_"+ip+"_"+a[1];
							FlowListTemp temp = new FlowListTemp();
							temp.setInf(a[1]);
							temp.setIpde(ipde);
							temp.setIp(ip);
							temp.setIpv6(ipto6);
							temp.setName(ipname);
							temp.setIpname(iptestname);
							ip2=ip;
							if(ip.contains(":")){
								ip2=ip.replace(":", "=");
							}//由于ipv6的地址带有“：”无法成为文件名。故做了相应的转换。需后台支持。
							temp.setPic1("/file/flow/flowscan/dat/pic/"+ip2+"_"+a[1]+"_bit.gif");
							temp.setPic2("/file/flow/flowscan/dat/pic/"+ip2+"_"+a[1]+"_pkt.gif");
							temp.setPic3("/file/flow/flowscan/dat/pic/"+ip2+"_"+a[1]+"_len.gif");
							inflist.add(temp);
						}
						
						}
					}
				
				input.close();
			}
			return inflist;
		}
	
	public  List<FlowListTemp>  getinfinfoother(Set list) throws Exception{
			List<FlowListTemp> inflist =new ArrayList() ;
			List templist =new ArrayList(list);
			 for(int i=0;i<templist.size();i++){ 
				       if(templist.get(i).toString()!=null){
						String a[]=templist.get(i).toString().split("_");
						String ip =a[0];
						String ipname = "NoName";
							if(service.findDeviceByIP(ip).getDescription()!=null&&!("").equals(service.findDeviceByIP(ip).getDescription()))
							{
								ipname = service.findDeviceByIP(ip).getDescription();
							}
							if(service.findDeviceByIP(ip).getName()!=null&&!("").equals(service.findDeviceByIP(ip).getName()))
							{
							ipname = service.findDeviceByIP(ip).getName();
							}
							if(service.findDeviceByIP(ip).getChineseName()!=null&&!("").equals(service.findDeviceByIP(ip).getChineseName()))
							{
							ipname = service.findDeviceByIP(ip).getChineseName();
								}
//							PortService portService=new PortService();
//							List<IfInterface> portList=portService.getPortList(service.findDeviceByIP(ip).getId());
							String ipde ="";
							String ipto6 ="";
							String ip2 ="";
//							for(int i=0;i<portList.size();i++){
//								if(portList.get(i).getIfindex().equals(a[1])){
//									ipde =portList.get(i).getDescription();
//									ipto6= portList.get(i).getIpv6();
//									
//								}
//							}
//							
							String iptestname =ipname+"_"+ip+"_"+a[1];
							FlowListTemp temp = new FlowListTemp();
							temp.setInf(a[1]);
							temp.setIpde(ipde);
							temp.setIp(ip);
							temp.setIpv6(ipto6);
							temp.setName(ipname);
							temp.setIpname(iptestname);
							ip2=ip;
							if(ip.contains(":")){
								ip2=ip.replace(":", "=");
							}//由于ipv6的地址带有“：”无法成为文件名。故做了相应的转换。需后台支持。
							temp.setPic1("/file/flow/flowscan/dat/pic/"+ip2+"_"+a[1]+"_bit.gif");
							temp.setPic2("/file/flow/flowscan/dat/pic/"+ip2+"_"+a[1]+"_pkt.gif");
							temp.setPic3("/file/flow/flowscan/dat/pic/"+ip2+"_"+a[1]+"_len.gif");
							inflist.add(temp);
						}
						
						}

			return inflist;
		}
	
	public  List<FlowListTemp>  getFlowViewInfo(File file) throws Exception{
		List<FlowListTemp> inflist =new ArrayList<FlowListTemp>() ;
		String text = null;
		Device device = null;
		String ipv4 = null;//设备的IPv4地址
		String ipv6 = null;//设备的IPv6地址
		String ipname = null;//页面显示的设备名称
		String deviceInfo = null;//页面显示的设备综合信息
		String IPInfo = null;//页面显示的设备IP地址信息
		String tempIP = null;
		
		BufferedReader input = new BufferedReader( new FileReader( file ) );
		HashMap<String,Device>  deviceMap = new HashMap<String,Device>();
		while ( ( text = input.readLine() ) != null ){ 
			String arr[]=text.split("_");//text的格式为:设备id_ifindex_infId
			if(!deviceMap.containsKey(arr[0])){
				device = service.findById(Long.parseLong(arr[0]));
			}else{
				deviceMap.put(arr[0], device);
			}
			if(device.getChineseName()!=null&&!("").equals(device.getChineseName())){
				ipname = device.getChineseName();
			}else if(device.getName()!=null&&!("").equals(device.getName())){
				ipname = device.getName();
			}else if(device.getDescription()!=null&&!("").equals(device.getDescription())){
				ipname = device.getDescription();
			}
			FlowListTemp temp = new FlowListTemp();
			ipv4 = device.getLoopbackIP();
			ipv6 = device.getLoopbackIPv6();
			if(ipv4!=null && !"".equals(ipv4)){
				IPInfo = ipv4;
			}
			if(ipv6!=null && !"".equals(ipv6)){
				IPInfo = IPInfo + "<br>" +ipv6;
			}
			deviceInfo =ipname+"<br>"+IPInfo+"<br>"+arr[1];
			temp.setInf(arr[1]);
			temp.setName(ipname);
			temp.setIpname(deviceInfo);
			
			if(ipv4!=null && !"".equals(ipv4)){
				temp.setPic1("/file/flow/flowscan/dat/pic/"+ipv4+"_"+arr[1]+"_bit.gif");
				temp.setPic2("/file/flow/flowscan/dat/pic/"+ipv4+"_"+arr[1]+"_pkt.gif");
				temp.setPic3("/file/flow/flowscan/dat/pic/"+ipv4+"_"+arr[1]+"_len.gif");
			}else if(ipv6!=null && !"".equals(ipv6)){
				tempIP=ipv6.replace(":", "=");//由于ipv6的地址带有“：”无法成为文件名。故做了相应的转换。需后台支持。
				temp.setPic1("/file/flow/flowscan/dat/pic/"+tempIP+"_"+arr[1]+"_bit.gif");
				temp.setPic2("/file/flow/flowscan/dat/pic/"+tempIP+"_"+arr[1]+"_pkt.gif");
				temp.setPic3("/file/flow/flowscan/dat/pic/"+tempIP+"_"+arr[1]+"_len.gif");
			}
			inflist.add(temp);	
		}
		input.close();
		return inflist;
	}
	public  List<FlowListTemp>  getinfinfo(Set list) throws Exception{
		  File name = new File( Constants.webRealPath+"file/flow/flowscan/dat/flow.txt" );
			List<FlowListTemp> inflist =new ArrayList<FlowListTemp>() ;
			String text = null;
			Device device = null;
			if(name.exists()){	
				BufferedReader input = new BufferedReader( new FileReader( name ) );
				while ( ( text = input.readLine() ) != null )
				{ 
					if(!text.equals("")&&text.contains("|")){
						String a[]=text.split("\\|");
						String tempa = a[0]+"_"+a[1];
						String ip =a[0];
						if(list.contains(tempa)){
							String ipname = "NoName";
							device = service.findDeviceByIP(ip);
							if(device.getChineseName()!=null&&!("").equals(device.getChineseName())){
								ipname = device.getChineseName();
							}else if(device.getName()!=null&&!("").equals(device.getName())){
								ipname = device.getName();
							}else if(device.getDescription()!=null&&!("").equals(device.getDescription())){
								ipname = device.getDescription();
							}
//							PortService portService=new PortService();
//							List<IfInterface> portList=portService.getPortList(service.findDeviceByIP(ip).getId());
							String ipde ="";
							String ipto6 ="";
							String ip2 ="";
//							for(int i=0;i<portList.size();i++){
//								if(portList.get(i).getIfindex().equals(a[1])){
//									ipde =portList.get(i).getDescription();
//									ipto6= portList.get(i).getIpv6();
//									
//								}
//							}
//							
							String iptestname =ipname+"<br>"+ip+"<br>"+a[1];
							FlowListTemp temp = new FlowListTemp();
							temp.setInf(a[1]);
							temp.setIpde(ipde);
							temp.setIp(ip);
							temp.setIpv6(ipto6);
							temp.setName(ipname);
							temp.setIpname(iptestname);
							ip2=ip;
							if(ip.contains(":")){
								ip2=ip.replace(":", "=");
							}//由于ipv6的地址带有“：”无法成为文件名。故做了相应的转换。需后台支持。
							temp.setPic1("/file/flow/flowscan/dat/pic/"+ip2+"_"+a[1]+"_bit.gif");
							temp.setPic2("/file/flow/flowscan/dat/pic/"+ip2+"_"+a[1]+"_pkt.gif");
							temp.setPic3("/file/flow/flowscan/dat/pic/"+ip2+"_"+a[1]+"_len.gif");
							inflist.add(temp);
						}
						
						}
					}
				
				input.close();
			}
			return inflist;
		}
	
	public Set getlinkformview(String file) throws Exception{		
		org.w3c.dom.Document xmldoc = W3CXML.loadXMLDocumentFromFile(file);
		Set linkview=new HashSet();
		NodeList linkLists = xmldoc.getElementsByTagName("to:link");
		if(linkLists!=null){
			for (int i = 0; i < linkLists.getLength(); i++) {
				Node linkNode = linkLists.item(i);// linkNode是每一个to:link节点
				NodeList childLists = linkNode.getChildNodes();// childLists存放link节点的子节点
				String sID = "";
				String sName = "";
				String sSrcId = "";
				String sDestId = ""; 
				String sSrcInterfaceId = "";
				String sDestInterfaceId = "";
				for (int j = 0; j < childLists.getLength(); j++) {
					Node childNode = childLists.item(j);// childNode是link的每一个子节点
					String nodeName = childNode.getNodeName();// nodeName是每一个子节点的名字
					if (nodeName.equals("to:name")) {
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
			 PortService portService=new PortService();
			 FlowListViewDao flowlistdaotwo = new FlowListViewDao();
			 String srcip = flowlistdaotwo.getDeviceIp(Long.valueOf(sSrcId));
			 String srcinf =portService.findById(Long.valueOf(sSrcInterfaceId)).getIfindex();
			 String destinf =portService.findById(Long.valueOf(sDestInterfaceId)).getIfindex();
			 String srclinks = srcip+"_"+srcinf;
			 linkview.add(srclinks);
			 String destip = flowlistdaotwo.getDeviceIp(Long.valueOf(sDestId));
			 String destlinks = destip+'_'+destinf;
			 linkview.add(destlinks);
			
			}
		}
		return linkview;
	}
	public Set<Long> getInformview(String file) throws Exception{		
		org.w3c.dom.Document xmldoc = W3CXML.loadXMLDocumentFromFile(file);
		Set<Long> linkview=new HashSet<Long>();
		NodeList linkLists = xmldoc.getElementsByTagName("to:link");
		if(linkLists!=null){
			for (int i = 0; i < linkLists.getLength(); i++) {
				Node linkNode = linkLists.item(i);// linkNode是每一个to:link节点
				NodeList childLists = linkNode.getChildNodes();// childLists存放link节点的子节点		
				String srcInterfaceId = "";
				String destInterfaceId = "";
				for (int j = 0; j < childLists.getLength(); j++) {
					Node childNode = childLists.item(j);// childNode是link的每一个子节点
					String nodeName = childNode.getNodeName();// nodeName是每一个子节点的名字
					if (nodeName.equals("to:srcInterfaceId")) {
						srcInterfaceId = childNode.getTextContent();
						if(srcInterfaceId!=null && !"".equals(srcInterfaceId) && !linkview.contains(Long.valueOf(srcInterfaceId))){
							linkview.add(Long.valueOf(srcInterfaceId));
						}
					}else if (nodeName.equals("to:destInterfaceId")) {
						destInterfaceId = childNode.getTextContent();
						if(destInterfaceId!=null && !"".equals(destInterfaceId) && !linkview.contains(Long.valueOf(destInterfaceId))){
							linkview.add(Long.valueOf(destInterfaceId));
						}
					}
				}
			}
		}
		return linkview;
	}
}
