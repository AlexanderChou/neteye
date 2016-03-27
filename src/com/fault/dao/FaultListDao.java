package com.fault.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.base.model.Device;
import com.base.model.IfInterface;
import com.base.service.DeviceService;
import com.base.service.PortService;
import com.base.util.Constants;
import com.fault.dto.FaultNode;

public class FaultListDao {
	private String totalCount="0";
	private List<FaultNode> nodeList;
	private DeviceService service=new DeviceService();
	public String checkRouterStuts(int id) throws Exception{
		List<FaultNode> templist = new ArrayList();
		templist = getDeviceEvent(String.valueOf(id));
		
		int tmpyellow =0;
		int tmpred =0;
		int tmpgreen =0;
		for(int i=0;i<templist.size();i++){
			if (templist.get(i).getStatus()==1){tmpyellow++;}
			if (templist.get(i).getStatus()==0){tmpred++;}
			if (templist.get(i).getStatus()==3){tmpred++;}
			if (templist.get(i).getStatus()==2){tmpgreen++;}
		}
		int tmp1 = templist.size();
		String routerstutas ="images/yellow_color.gif";
		if (tmp1 == tmpgreen ){
			routerstutas ="images/green_color.gif";
		}
		if( tmpred == tmp1){
			routerstutas ="images/red_color.gif";
				}
		return routerstutas;
		
	}
	
	public List<FaultNode> getDeviceEvent(String id) throws Exception{
		nodeList=new ArrayList();
		List<FaultNode> tempList=new ArrayList();
		List<FaultNode> tmpList=new ArrayList();
		Hashtable active = new Hashtable();
		
		String filePath = Constants.webRealPath+"file/fault/dat";
		File activeFile=new File(filePath+"/active");
		if(activeFile.exists()){
			FileReader fr;
			try {
				fr = new FileReader(activeFile);
				BufferedReader br = new BufferedReader(fr);
	            String myreadline; 
	            while (br.ready()) {
	                myreadline = br.readLine();//读取一行
	                active.put(myreadline, "1");
	            }
	            br.close();
	            fr.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//创建FileReader对象，用来读取字符流
            
		}
		Hashtable unuse = new Hashtable();
		File unuseable=new File(filePath+"/unuseableiplist");
		if(unuseable.exists()){
			FileReader fr;
			try {
				fr = new FileReader(unuseable);
				BufferedReader br = new BufferedReader(fr);
	            String myreadline; 
	            while (br.ready()) {
	                myreadline = br.readLine();//读取一行
	                unuse.put(myreadline, "1");
	            }
	            br.close();
	            fr.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//创建FileReader对象，用来读取字符流
            
		}	
		Hashtable lossrtt = new Hashtable();
		File lossrttFile=new File(filePath+"/lossrttlist");
		if(lossrttFile.exists()){
			FileReader fr;
			try {
				fr = new FileReader(lossrttFile);
				BufferedReader br = new BufferedReader(fr);
	            String myreadline; 
	            while (br.ready()) {
	                myreadline = br.readLine();//读取一行
	                lossrtt.put(myreadline.substring(0,myreadline.indexOf("|")), myreadline.substring(myreadline.indexOf("|")+1, myreadline.lastIndexOf("|")));
	            }
	            br.close();
	            fr.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//创建FileReader对象，用来读取字符流
            
		}	
		FaultNode node=new FaultNode();
		Long longid = Long.parseLong(id);
		Device device=service.getDevice(longid);
		node.setId(device.getId());
		node.setIp("--");
		Hashtable verify=new Hashtable();
		if(device.getLoopbackIP()!=null){
		node.setIp(device.getLoopbackIP());
		verify.put(device.getLoopbackIP(), "1");
		}
		node.setIp6("--");
		if(device.getLoopbackIPv6()!=null){
			node.setIp6(device.getLoopbackIPv6());
			verify.put(device.getLoopbackIPv6(), "1");
		}
		node.setName("NoName");
		if(device.getName()!=null&&!("").equals(device.getName())){
			node.setName(device.getName());
		}
		if(device.getChineseName()!=null&&!("").equals(device.getChineseName())){
			node.setName(device.getChineseName());
		}
		if(device.getLoopbackIP()!=null&&!("").equals(device.getLoopbackIP())){
			if(active.get(device.getLoopbackIP())!=null){
				node.setStatus(0);
				node.setLoss("100");
				node.setRrt("~~");
			}else if(unuse.get(device.getLoopbackIP())!=null){
				node.setStatus(3);
			}else if(lossrtt.get(device.getLoopbackIP())!=null){
				node.setStatus(2);
				String temp=(String)lossrtt.get(device.getLoopbackIP());
				String a[]=temp.split("\\|");
				if(a!=null){
					node.setLoss(a[0]);
					node.setRrt(a[1]);
				}
			}else{
				node.setStatus(1);
			}
		}
		else if(device.getLoopbackIPv6()!=null){
    	   if(active.get(device.getLoopbackIPv6())!=null){
				node.setStatus(0);
				node.setLoss("100");
				node.setRrt("~~");
			}else if(unuse.get(device.getLoopbackIPv6())!=null){
				node.setStatus(3);
			}else if(lossrtt.get(device.getLoopbackIPv6())!=null){
				node.setStatus(2);
				String temp=(String)lossrtt.get(device.getLoopbackIPv6());
				String a[]=temp.split("\\|");
				if(a!=null){
					node.setLoss(a[0]);
					node.setRrt(a[1]);
				}
			}else{
				node.setStatus(1);
			}
    	   
    	   
		}
		tempList.add(node);
		
		if(device.getDeviceType().getId()<=2){
			PortService portService=new PortService();
			List<IfInterface> portList=portService.getPortList(device.getId());
//			String tmpip4="";
//			String tmpip6="";
//			int flagip =0;
//			if(device.getLoopbackIP()!=null){
//				tmpip4=device.getLoopbackIP();
//			}
//			if(device.getLoopbackIPv6()!=null){
//				tmpip6=device.getLoopbackIPv6();
//			}
			if(portList!=null){
				for(int j=0;j<portList.size();j++){		
					IfInterface port=(IfInterface) portList.get(j);
//					if(device.getLoopbackIP()!=null&&!("").equals(device.getLoopbackIP())){
//						 if(device.getLoopbackIP().equals(port.getIpv4())){
//							 flagip=1;
//						 }
//					}
//					if(device.getLoopbackIPv6()!=null&&!("").equals(device.getLoopbackIPv6())){
//						if(device.getLoopbackIPv6().equals(port.getIpv6())){
//							 flagip=1;
//						 }
//					}
					
					
					
					if(port.getIpv4()!=null&&!("").equals(port.getIpv4())){
						
	//					if(verify.get(port.getIpv4())==null){
//							verify.put(port.getIpv4(), "1");
						FaultNode portNode=new FaultNode();
						portNode.setId(device.getId());
						portNode.setIp("--");
						if(port.getIpv4()!=null){
					    	portNode.setIp(port.getIpv4());
						}
						portNode.setIp6("--");
						if(port.getIpv6()!=null){
							portNode.setIp6(port.getIpv6());
						}
						portNode.setName("NoName");
						if(port.getDescription()!=null&&!("").equals(port.getDescription())){
							portNode.setName(port.getDescription());
						}
						if(port.getName()!=null&&!("").equals(port.getName())){
							portNode.setName(port.getName());
						}
						if(port.getChineseName()!=null&&!("").equals(port.getChineseName())){
							portNode.setName(port.getChineseName());
						}
						if(active.get(port.getIpv4())!=null){
							portNode.setStatus(0);
							portNode.setLoss("100");
							portNode.setRrt("~~");
						}else if(unuse.get(port.getIpv4())!=null){
							portNode.setStatus(3);
						}else if(lossrtt.get(port.getIpv4())!=null){
							portNode.setStatus(2);
							String temp=(String)lossrtt.get(port.getIpv4());
							String a[]=temp.split("\\|");
							if(a!=null){
								portNode.setLoss(a[0]);
								portNode.setRrt(a[1]);
							}
						}else{
							portNode.setStatus(1);
						}
						tempList.add(portNode);
						
				//		}
					}else{
					if (port.getIpv6()!=null&&!("").equals(port.getIpv6())){
	//					if(verify.get(device.getLoopbackIPv6())==null){
//							verify.put(device.getLoopbackIPv6(), "1");
						FaultNode portNode=new FaultNode();
						portNode.setId(device.getId());
						portNode.setIp("--");
						if(port.getIpv4()!=null){
					    	portNode.setIp(port.getIpv4());
						}
						portNode.setIp6("--");
						if(port.getIpv6()!=null){
							portNode.setIp6(port.getIpv6());
						}
						portNode.setName("NoName");
						if(port.getDescription()!=null&&!("").equals(port.getDescription())){
							portNode.setName(port.getDescription());
						}
						if(port.getName()!=null&&!("").equals(port.getName())){
							portNode.setName(port.getName());
						}
						if(port.getChineseName()!=null&&!("").equals(port.getChineseName())){
							portNode.setName(port.getChineseName());
						}
						if(active.get(port.getIpv6())!=null){
							portNode.setStatus(0);
							portNode.setLoss("100");
							portNode.setRrt("~~");
						}else if(unuse.get(port.getIpv6())!=null){
							portNode.setStatus(3);
						}else if(lossrtt.get(port.getIpv6())!=null){
							portNode.setStatus(2);
							String temp=(String)lossrtt.get(port.getIpv6());
							String a[]=temp.split("\\|");
							if(a!=null){
								portNode.setLoss(a[0]);
								portNode.setRrt(a[1]);
							}
						}else{
							portNode.setStatus(1);
						
						}
						tempList.add(portNode);
					}
			//		}
					}
			//			}
					}
				}
			
			
		
			}
		if(tempList!=null){
			totalCount=Integer.toString(tempList.size());
		}
		List<FaultNode> redlist=new ArrayList<FaultNode>();
		List<FaultNode> greenlist=new ArrayList<FaultNode>();
		List<FaultNode> yellowlist=new ArrayList<FaultNode>();
		List<FaultNode> unuselist=new ArrayList<FaultNode>();
//		System.out.println("tempList="+tempList.size());
//		System.out.println("device.getDeviceType().getId()="+device.getDeviceType().getId());
		for(int i=0;i<tempList.size();i++){
			if(tempList.get(i).getStatus()==0){
				redlist.add(tempList.get(i));
			}
			else if(tempList.get(i).getStatus()==3){
				unuselist.add(tempList.get(i));
			}
			if(tempList.get(i).getStatus()==1){
				yellowlist.add(tempList.get(i));
			}
			if(tempList.get(i).getStatus()==2){
				greenlist.add(tempList.get(i));
			}
		}
		tmpList.addAll(redlist);
		tmpList.addAll(unuselist);
		tmpList.addAll(yellowlist);
		tmpList.addAll(greenlist);
//		System.out.println("redlist="+redlist.size());
//		System.out.println("unuselist="+unuselist.size());
//		System.out.println("yellowlist="+yellowlist.size());
//		System.out.println("greenlist="+greenlist.size());
//		System.out.println("tmpList="+tmpList.size());
		nodeList.addAll(tmpList);
//		System.out.println("nodeList="+nodeList.size());
		return tmpList;
	}
	
	
}
