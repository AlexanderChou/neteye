package com.savi.cernet.soa;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.savi.base.util.Constants;
import com.savi.base.util.W3CXML;
import com.savi.cernet.util.NasCoderUtil;
import com.savi.show.dto.OnlineInfo;
public class WSUtil{
	public static String getUserName(String ip,ArrayList<Integer> timeoutFlag){
		try{
			if(timeoutFlag.size()==0){
				timeoutFlag.add(0);
			}
			/*
			while(true){
				if(Constants.webServiceURL!=null)break;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			*/
			if(Constants.webServiceURL==null){
				//return "";
				//Constants.webServiceURL="http://166.111.9.13:8080/localOnlineSoa/localOnlineSoa";
				Constants.webServiceURL="http://202.112.237.92:8080/localOnlineSoa/localOnlineSoa";
			}
	    	LocalOnlineSoaPortBindingStub binding;
	        binding = (LocalOnlineSoaPortBindingStub)
	                          new LocalOnlineSoaServiceLocator(Constants.webServiceURL).getlocalOnlineSoaPort();
	        binding.setTimeout(500);
			String r = "12345678";
			long time = System.currentTimeMillis();
			String serverName = "serverName11";
			String serverPwd = "serverPwd";
			//String serverName = "gLr6mcAsOSs52DIr";
			//String serverPwd = "oldGhqYQXrSMeQOt";
			String md5String = NasCoderUtil.stringToMD5(serverName + serverPwd + r + time);
			GetOnlineUserByIp arg=new GetOnlineUserByIp();
			/*
			arg.setAuthType("1");
			arg.setAuthenticateStr(md5String);
			arg.setAuthenticateReserved("");
			arg.setRandomNum(r);
			arg.setTheTimestamp(time+"");
			arg.setIp("2001:da8:23d:f110:357d:4777:a09b:71fc 2001:da8:23d:f110:2e0:4cff:fe36:e433");
			*/
	        // Test operation
	        //GetOnlineUserByIpResponse value = null;
	        String xmlString2 = binding.getOnlineUserByIp("1", md5String, "", r, time+"", ip);
			//返回用户信息XML格式样例
			/*
			String xmlString2=""+
			"<?xml version=\"1.0\" encoding=\"GBK\"?>"+
			"<list>"+
				"<user>"+
					"<ACCTSESSIONID>1</ACCTSESSIONID>"+
					"<ACCTSTARTTIME>1</ACCTSTARTTIME>"+
					"<IPV6>1</IPV6>"+
					"<MAC>1</MAC>"+
					"<NAS_PORT>1</NAS_PORT>"+  
					"<IPV4>1</IPV4>"+
					"<LOGIN_NAME>zhangli</LOGIN_NAME>"+
					"<NAS_IP>1</NAS_IP>"+     
				"</user>"+
			"</list>";
			*/
			//对xml进行解析，提取出用户名
			org.w3c.dom.Document xmlViewdoc = W3CXML.parseXMLDocument(xmlString2);
			NodeList nodes=xmlViewdoc.getElementsByTagName("LOGIN_NAME");
			Node node=nodes.item(0);
			if(node!=null){
				String userName=node.getChildNodes().item(0).getNodeValue();
				return userName;
			}else{
				return "";
			}
		}catch(java.rmi.RemoteException e){
			timeoutFlag.set(0, 1);
			return "";
		}catch(Exception e){
			//e.printStackTrace();
			return "";
		}
	}
	
	public static List getUserNameByIp(String ip,ArrayList<Integer> timeoutFlag){
		try{
			if(timeoutFlag.size()==0){
				timeoutFlag.add(0);
			}
			if(Constants.webServiceURL==null){
				Constants.webServiceURL="http://202.112.237.92:8080/localOnlineSoa/localOnlineSoa";
			}
	    	LocalOnlineSoaPortBindingStub binding;
	        binding = (LocalOnlineSoaPortBindingStub)
	                          new LocalOnlineSoaServiceLocator(Constants.webServiceURL).getlocalOnlineSoaPort();
	        binding.setTimeout(500);
			String r = "12345678";
			long time = System.currentTimeMillis();
			String serverName = "serverName11";
			String serverPwd = "serverPwd";
			//String serverName = "gLr6mcAsOSs52DIr";
			//String serverPwd = "oldGhqYQXrSMeQOt";
			String md5String = NasCoderUtil.stringToMD5(serverName + serverPwd + r + time);
			GetOnlineUserByIp arg=new GetOnlineUserByIp();
	        String xmlString2 = binding.getOnlineUserByIp("1", md5String, "", r, time+"", ip);
			
			//对xml进行解析，提取出用户名
			org.w3c.dom.Document xmlViewdoc = W3CXML.parseXMLDocument(xmlString2);
			NodeList users = xmlViewdoc.getElementsByTagName("user");
			
			NodeList ids=xmlViewdoc.getElementsByTagName("ACCTSESSIONID");
			NodeList starts=xmlViewdoc.getElementsByTagName("ACCTSTARTTIME");
			NodeList ipv6s=xmlViewdoc.getElementsByTagName("IPV6");
			NodeList ipv4s=xmlViewdoc.getElementsByTagName("IPV4");
			NodeList macs=xmlViewdoc.getElementsByTagName("MAC");
			NodeList logins=xmlViewdoc.getElementsByTagName("LOGIN_NAME");
			NodeList nasIps=xmlViewdoc.getElementsByTagName("NAS_IP");
			NodeList nasPorts=xmlViewdoc.getElementsByTagName("NAS_PORT");
			
			List onlineList = new ArrayList();
			if(users!=null && users.getLength()>0){
				for(int i=0; i<users.getLength(); i++) {
					OnlineInfo online = new OnlineInfo();
					online.setId(Long.parseLong(ids.item(i).getFirstChild().getNodeValue()));
					online.setLogin(logins.item(i).getFirstChild().getNodeValue());
//					online.setIpv4(ipv4s.item(i).getFirstChild().getNodeValue());
					online.setIpv4("");
					online.setIpv6(ipv6s.item(i).getFirstChild().getNodeValue());
					online.setMac(macs.item(i).getFirstChild().getNodeValue());
					online.setStart(starts.item(i).getFirstChild().getNodeValue());
					online.setNasIp(nasIps.item(i).getFirstChild().getNodeValue());
					online.setNasPort(nasPorts.item(i).getFirstChild().getNodeValue());
					onlineList.add(online);
				}
				return onlineList;
			}else{
				return null;
			}
		}catch(java.rmi.RemoteException e){
			timeoutFlag.set(0, 1);
			return null;
		}catch(Exception e){
			//e.printStackTrace();
			return null;
		}
	}
	
	//有线获取USERNAME
	//public static List getSaviUserNameByIp(String ip,ArrayList<Integer> timeoutFlag){
	//	try{
	//		if(timeoutFlag.size()==0){
	//			timeoutFlag.add(0);
	//		}
	//		//Constants.webServiceURL="http://211.68.122.59:8080/localOnlineSoa/localOnlineSoa";
	//		if(Constants.webserviceURLWLAN==null){
	//			Constants.webserviceURLWLAN="http://211.68.122.59:8080/localOnlineSoa/localOnlineSoa";
	//		}
	//		System.out.println("Constants.webserviceURLWLAN" + Constants.webserviceURLWLAN);
	//    	LocalOnlineSoaPortBindingStub binding;
	//        binding = (LocalOnlineSoaPortBindingStub)
	//                          new LocalOnlineSoaServiceLocator(Constants.webserviceURLWLAN).getlocalOnlineSoaPort();
	 //       binding.setTimeout(500);
	//		String r = "12345678";
	//		long time = System.currentTimeMillis();
	//		String serverName = "serverName11";
	//		String serverPwd = "serverPwd";
	//		String md5String = NasCoderUtil.stringToMD5(serverName + serverPwd + r + time);
	//		GetOnlineUserByIp arg=new GetOnlineUserByIp();
	//		System.out.println("binding.getOnlineUserByIp  begin");
	//        String xmlString2 = binding.getOnlineUserByIp("1", md5String, "", r, time+"", ip);
	//        System.out.println("xmlString2"+xmlString2);
	//		//对xml进行解析，提取出用户名
	//		org.w3c.dom.Document xmlViewdoc = W3CXML.parseXMLDocument(xmlString2);
	//		NodeList users = xmlViewdoc.getElementsByTagName("user");
	//		
	//		NodeList ids=xmlViewdoc.getElementsByTagName("ACCTSESSIONID");
	//		NodeList logins=xmlViewdoc.getElementsByTagName("LOGIN_NAME");

			
	//		List onlineList = new ArrayList();
	//		if(users!=null && users.getLength()>0){
	//			for(int i=0; i<users.getLength(); i++) {
	//				OnlineInfo online = new OnlineInfo();
	//				online.setId(Long.parseLong(ids.item(i).getFirstChild().getNodeValue()));
	//				online.setLogin(logins.item(i).getFirstChild().getNodeValue());
	//				onlineList.add(online);
	//			}
	//			return onlineList;
	//		}else{
	//			return null;
	//		}
	//	}catch(java.rmi.RemoteException e){
	//		timeoutFlag.set(0, 1);
	//		return null;
	//	}catch(Exception e){
	//		//e.printStackTrace();
	//		return null;
	//	}
	//}
	
	
	//有线获取USERNAME
	public static List getSaviUserNameByIp(String ip,String mac,Integer ifIndex,String ipv4address, ArrayList<Integer> timeoutFlag){
		try{
			if(timeoutFlag.size()==0){
				timeoutFlag.add(0);
			}
			//Constants.webServiceURL="http://211.68.122.59:8080/localOnlineSoa/localOnlineSoa";
			if(Constants.webserviceURLWLAN==null){
				Constants.webserviceURLWLAN="http://211.68.122.59:8080/localOnlineSoa/localOnlineSoa";
			}
			System.out.println("Constants.webserviceURLWLAN" + Constants.webserviceURLWLAN);
	    	LocalOnlineSoaPortBindingStub binding;
	        binding = (LocalOnlineSoaPortBindingStub)
	                          new LocalOnlineSoaServiceLocator(Constants.webserviceURLWLAN).getlocalOnlineSoaPort();
	        binding.setTimeout(500);
			String r = "12345678";
			long time = System.currentTimeMillis();
			String serverName = "serverName11";
			String serverPwd = "serverPwd";
			String md5String = NasCoderUtil.stringToMD5(serverName + serverPwd + r + time);
			GetOnlineUserByIp arg=new GetOnlineUserByIp();
			System.out.println("binding.getSaviOnlineUserByIp  begin");
	        String xmlString2 = binding.getSaviOnlineUserByIp("1", md5String, "", r, time+"", ip, mac,ifIndex + "",ipv4address);
	        System.out.println("xmlString2"+xmlString2);
			//对xml进行解析，提取出用户名
			org.w3c.dom.Document xmlViewdoc = W3CXML.parseXMLDocument(xmlString2);
			NodeList users = xmlViewdoc.getElementsByTagName("user");
			
			NodeList ids=xmlViewdoc.getElementsByTagName("ACCTSESSIONID");
			NodeList logins=xmlViewdoc.getElementsByTagName("LOGIN_NAME");

			
			List onlineList = new ArrayList();
			if(users!=null && users.getLength()>0){
				for(int i=0; i<users.getLength(); i++) {
					OnlineInfo online = new OnlineInfo();
					if(ids.item(i) != null && ids.item(i).getFirstChild() != null && ids.item(i).getFirstChild().getNodeValue() != null ){
						online.setId(Long.parseLong(ids.item(i).getFirstChild().getNodeValue()));
					}
					if(logins.item(i) != null && logins.item(i).getFirstChild() != null && logins.item(i).getFirstChild().getNodeValue() != null ){
						online.setLogin(logins.item(i).getFirstChild().getNodeValue());
					}else{
						online.setLogin("");
					}
					onlineList.add(online);
				}
				return onlineList;
			}else{
				return null;
			}
		}catch(java.rmi.RemoteException e){
			timeoutFlag.set(0, 1);
			return null;
		}catch(Exception e){
			//e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	public static List getOnlineList(ArrayList<Integer> timeoutFlag) {

		try{
			if(timeoutFlag.size()==0){
				timeoutFlag.add(0);
			}
			/*
			while(true){
				if(Constants.webServiceURL!=null)break;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			*/
			//Constants.webServiceURL="http://202.112.237.92:8080/localOnlineSoa/localOnlineSoa";
			if(Constants.webServiceURL==null){
//				return null;
				//Constants.webServiceURL="http://166.111.9.13:8080/localOnlineSoa/localOnlineSoa";
				Constants.webServiceURL="http://202.112.237.92:8080/localOnlineSoa/localOnlineSoa";
			}
	    	LocalOnlineSoaPortBindingStub binding;
	        binding = (LocalOnlineSoaPortBindingStub)
	                          new LocalOnlineSoaServiceLocator(Constants.webServiceURL).getlocalOnlineSoaPort();
	        binding.setTimeout(500);
			String r = "12345678";
			long time = System.currentTimeMillis();
			String serverName = "serverName11";
			String serverPwd = "serverPwd";
//			String serverName = "gLr6mcAsOSs52DIr";
//			String serverPwd = "oldGhqYQXrSMeQOt";
			String md5String = NasCoderUtil.stringToMD5(serverName + serverPwd + r + time);
			/*
			GetOnlineUserByIp arg=new GetOnlineUserByIp();
			arg.setAuthType("1");
			arg.setAuthenticateStr(md5String);
			arg.setAuthenticateReserved("");
			arg.setRandomNum(r);
			arg.setTheTimestamp(time+"");
			arg.setIp("2001:da8:23d:f110:357d:4777:a09b:71fc 2001:da8:23d:f110:2e0:4cff:fe36:e433");
			*/
	        // Test operation
	        //GetOnlineUserByIpResponse value = null;
	        String xmlString = binding.getOnlineList("1", md5String, "", r, time+"");
//	        System.out.println(xmlString);
			//返回用户信息XML格式样例
			/*
			String xmlString2=""+
			"<?xml version=\"1.0\" encoding=\"GBK\"?>"+
			"<list>"+
				"<user>"+
					"<ACCTSESSIONID>1</ACCTSESSIONID>"+
					"<ACCTSTARTTIME>1</ACCTSTARTTIME>"+
					"<IPV6>1</IPV6>"+
					"<MAC>1</MAC>"+
					"<NAS_PORT>1</NAS_PORT>"+  
					"<IPV4>1</IPV4>"+
					"<LOGIN_NAME>zhangli</LOGIN_NAME>"+
					"<NAS_IP>1</NAS_IP>"+     
				"</user>"+
			"</list>";
			*/
			//对xml进行解析，提取出用户名
			org.w3c.dom.Document xmlViewdoc = W3CXML.parseXMLDocument(xmlString);
			
			NodeList users = xmlViewdoc.getElementsByTagName("user");
			
			NodeList ids=xmlViewdoc.getElementsByTagName("ACCTSESSIONID");
			NodeList starts=xmlViewdoc.getElementsByTagName("ACCTSTARTTIME");
			NodeList ipv6s=xmlViewdoc.getElementsByTagName("IPV6");
			NodeList ipv4s=xmlViewdoc.getElementsByTagName("IPV4");
			NodeList macs=xmlViewdoc.getElementsByTagName("MAC");
			NodeList logins=xmlViewdoc.getElementsByTagName("LOGIN_NAME");
			NodeList nasIps=xmlViewdoc.getElementsByTagName("NAS_IP");
			NodeList nasPorts=xmlViewdoc.getElementsByTagName("NAS_PORT");
			NodeList apnames=xmlViewdoc.getElementsByTagName("AP_NAME");
			System.out.println(apnames);
			List onlineList = new ArrayList();
//			System.out.println(users.getLength());
			if(users!=null && users.getLength()>0){
				for(int i=0; i<users.getLength(); i++) {
					OnlineInfo online = new OnlineInfo();
					if(ids.item(i) != null && ids.item(i).getFirstChild() != null && ids.item(i).getFirstChild().getNodeValue() != null ){
						online.setId(Long.parseLong(ids.item(i).getFirstChild().getNodeValue()));
					}
					if(logins.item(i) != null && logins.item(i).getFirstChild() != null && logins.item(i).getFirstChild().getNodeValue() != null ){
						online.setLogin(logins.item(i).getFirstChild().getNodeValue());
					}else{
						online.setLogin("");
					}
					
//					online.setIpv4(ipv4s.item(i).getFirstChild().getNodeValue());
					online.setIpv4("");
					if(ipv6s.item(i) != null && ipv6s.item(i).getFirstChild() != null && ipv6s.item(i).getFirstChild().getNodeValue() != null ){
						online.setIpv6(ipv6s.item(i).getFirstChild().getNodeValue());
					}else{
						online.setIpv6("");
					}
					
					if(macs.item(i) != null && macs.item(i).getFirstChild() != null && macs.item(i).getFirstChild().getNodeValue() != null){
						online.setMac(macs.item(i).getFirstChild().getNodeValue());
					}else{
						online.setMac("");
					}
					if(starts.item(i) != null && starts.item(i).getFirstChild() != null && starts.item(i).getFirstChild().getNodeValue() != null ){
						online.setStart(starts.item(i).getFirstChild().getNodeValue());
					}else{
						online.setStart("");
					}
					if(nasIps.item(i) != null && nasIps.item(i).getFirstChild() != null && nasIps.item(i).getFirstChild().getNodeValue() != null ){
						online.setNasIp(nasIps.item(i).getFirstChild().getNodeValue());
					}else{
						online.setNasIp("");
					}
					if(nasPorts.item(i) != null && nasPorts.item(i).getFirstChild() != null && nasPorts.item(i).getFirstChild().getNodeValue() != null ){
						online.setNasPort(nasPorts.item(i).getFirstChild().getNodeValue());
					}else{
						online.setNasPort("");
					}
//					String userName=node.getChildNodes().item(0).getNodeValue();
					if(null != apnames.item(i).getFirstChild()){
						online.setApname(apnames.item(i).getFirstChild().getNodeValue());
					}else{
						online.setApname("");
					}
					
					onlineList.add(online);
				}
				return onlineList;
			}else{
				return null;
			}
		}catch(java.rmi.RemoteException e){
			timeoutFlag.set(0, 1);
			return null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public static List  getOnlineListBy24Hours(ArrayList<Integer> timeoutFlag) {
		try{
			if(timeoutFlag.size()==0){
				timeoutFlag.add(0);
			}
		
			if(Constants.webServiceURL==null){
				Constants.webServiceURL="http://202.112.237.92:8080/localOnlineSoa/localOnlineSoa";
			}
	    	LocalOnlineSoaPortBindingStub binding;
	        binding = (LocalOnlineSoaPortBindingStub)
	                          new LocalOnlineSoaServiceLocator(Constants.webServiceURL).getlocalOnlineSoaPort();
	        binding.setTimeout(500);
			String r = "12345678";
			long time = System.currentTimeMillis();
			String serverName = "serverName11";
			String serverPwd = "serverPwd";
			String md5String = NasCoderUtil.stringToMD5(serverName + serverPwd + r + time);
	        String xmlString = binding.getOnlineListBy24Hours("1", md5String, "", r, time+"");
	        
			//对xml进行解析，提取出用户名
			org.w3c.dom.Document xmlViewdoc = W3CXML.parseXMLDocument(xmlString);
			
			NodeList users = xmlViewdoc.getElementsByTagName("user");
			
			NodeList ids=xmlViewdoc.getElementsByTagName("ACCTSESSIONID");
			NodeList starts=xmlViewdoc.getElementsByTagName("ACCTSTARTTIME");
			NodeList ipv6s=xmlViewdoc.getElementsByTagName("IPV6");
			NodeList ipv4s=xmlViewdoc.getElementsByTagName("IPV4");
			NodeList macs=xmlViewdoc.getElementsByTagName("MAC");
			NodeList logins=xmlViewdoc.getElementsByTagName("LOGIN_NAME");
			NodeList nasIps=xmlViewdoc.getElementsByTagName("NAS_IP");
			NodeList nasPorts=xmlViewdoc.getElementsByTagName("NAS_PORT");
			
			List onlineList = new ArrayList();
			if(users!=null && users.getLength()>0){
				for(int i=0; i<users.getLength(); i++) {
					OnlineInfo online = new OnlineInfo();
					online.setId(Long.parseLong(ids.item(i).getFirstChild().getNodeValue()));
					online.setLogin(logins.item(i).getFirstChild().getNodeValue());
					online.setIpv4("");
					online.setIpv6(ipv6s.item(i).getFirstChild().getNodeValue());
					online.setMac(macs.item(i).getFirstChild().getNodeValue());
					online.setStart(starts.item(i).getFirstChild().getNodeValue());
					online.setNasIp(nasIps.item(i).getFirstChild().getNodeValue());
					online.setNasPort(nasPorts.item(i).getFirstChild().getNodeValue());
					onlineList.add(online);
				}
				return onlineList;
			}else{
				return null;
			}
		}catch(java.rmi.RemoteException e){
			timeoutFlag.set(0, 1);
			return null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	public static String setApname(Long onlineId, String apname,
			ArrayList<Integer> timeoutFlag) {
		try{
			if(timeoutFlag.size()==0){
				timeoutFlag.add(0);
			}
		
			if(Constants.webServiceURL==null){
				Constants.webServiceURL="http://202.112.237.92:8080/localOnlineSoa/localOnlineSoa";
			}
	    	LocalOnlineSoaPortBindingStub binding;
	        binding = (LocalOnlineSoaPortBindingStub)
	                          new LocalOnlineSoaServiceLocator(Constants.webServiceURL).getlocalOnlineSoaPort();
	        binding.setTimeout(500);
			String r = "12345678";
			long time = System.currentTimeMillis();
			String serverName = "serverName11";
			String serverPwd = "serverPwd";
			String md5String = NasCoderUtil.stringToMD5(serverName + serverPwd + r + time);
	        String xmlString = binding.setApname("1", md5String, "", r, time+"", onlineId+"", apname);
	        if(xmlString.equals("1")){
	        	 return xmlString;
	        }else{
	        	return "";
	        }
	       
			
		}catch(java.rmi.RemoteException e){
			timeoutFlag.set(0, 1);
			return "";
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
		
		
	}
	
	public static String setEndTime(String ip, String mac,Long endTime,
			ArrayList<Integer> timeoutFlag) {
		try{
			if(timeoutFlag.size()==0){
				timeoutFlag.add(0);
			}
		
			if(Constants.webServiceURL==null){
				Constants.webServiceURL="http://211.68.122.59:8080/localOnlineSoa/localOnlineSoa";
			}
	    	LocalOnlineSoaPortBindingStub binding;
	        binding = (LocalOnlineSoaPortBindingStub)
	                          new LocalOnlineSoaServiceLocator(Constants.webServiceURL).getlocalOnlineSoaPort();
	        binding.setTimeout(500);
			String r = "12345678";
			long time = System.currentTimeMillis();
			String serverName = "serverName11";
			String serverPwd = "serverPwd";
			String md5String = NasCoderUtil.stringToMD5(serverName + serverPwd + r + time);
	        String xmlString = binding.setEndTime("1", md5String, "", r, time+"", ip, mac, endTime+"");
	        if(xmlString.equals("1")){
	        	 return xmlString;
	        }else{
	        	return "";
	        }
	       
			
		}catch(java.rmi.RemoteException e){
			timeoutFlag.set(0, 1);
			return "";
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
		
		
	}
	static public void main(String args[]){
		ArrayList<Integer> flag=new ArrayList<Integer>();
		//List list = WSUtil.getOnlineList(flag);
		//if(list == null) {
		//	System.out.println(list);
		//	return;
		//}
		//System.out.println(list.size());
		//for(Object o : list){
		//	OnlineInfo online = (OnlineInfo) o;
		//	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
		//System.out.println("[" + online.getId() + "," + online.getLogin() + "," 
		//			+ online.getIpv4() + "," + online.getIpv6() + "," 
		//			+ online.getMac() + "," + online.getStart() + ","
		//			+ online.getNasIp() + "," + online.getNasPort() + "]");
		//	
		//}
		
		//String userName = WSUtil.getUserName("2001:da8:ff3a:c8ec:2371:3dbc:173:1d65",flag);
		//System.out.println(userName);
		List<OnlineInfo> onlineList = WSUtil.getSaviUserNameByIp("2001:da8:266:f501:de4c:6eff:8cee:f6c5","10 dd b1 ba d1 8b",8,"211.68.122.58",flag);
		if(null != onlineList){
			for(int i = 0;i < onlineList.size();i++){
				System.out.println(onlineList.get(i).getLogin());
			}
		}
		
	}

	

		
}

