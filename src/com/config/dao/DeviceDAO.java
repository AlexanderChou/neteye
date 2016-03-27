package com.config.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.base.model.Device;
import com.base.model.DeviceIcon;
import com.base.model.DeviceType;
import com.base.model.IfInterface;
import com.base.model.Image;
import com.base.model.Link;
import com.base.model.ServiceManage;
import com.base.model.View;
import com.base.service.DeviceService;
import com.base.service.DeviceTypeService;
import com.base.service.LinkService;
import com.base.service.PortService;
import com.base.service.ServiceManageService;
import com.base.service.ViewService;
import com.base.util.Constants;
import com.base.util.HibernateUtil;
import com.config.util.SnmpGet;
import com.config.util.SnmpWalk;
import com.config.util.XMLManager;


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
public class DeviceDAO {
	
	DeviceTypeService deviceTypeService = new DeviceTypeService();
	public boolean deviceTableIsHaveData(){
		boolean isHave = false;
		/** 判断数据库中的数据 */
		SessionFactory factory = new HibernateUtil().getSessionFactory();
		Session session = factory.openSession();
		Transaction transaction =  session.beginTransaction();
		String sql = "select count(*) as num from device";
		Long num = (Long)session.createSQLQuery(sql).addScalar("num",Hibernate.LONG).uniqueResult();
		if(num!=null && num>0){
			isHave = true;
		}else{
			SAXReader reader = new SAXReader();
			Document document = null;
			/** 判断xml文件里的内容是是否有数据 */
			List<Element> topoList = null;
			try {
				document = reader.read(new File(Constants.webRealPath + "file/topo/topoHis/history/history.xml"));
				Element topos = document.getRootElement();
				topoList = topos.elements();
				if(!topoList.isEmpty()) isHave = true;
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		return isHave;
	}
	/**
	 * 利用snmp获得结点信息
	 * @param netnode
	 * @return
	 */
	public int snmpNode(Device netnode) throws Exception {
		String ip = "";
		if (netnode.getLoopbackIP() != null && !"".equals(netnode.getLoopbackIP())) {
			ip = netnode.getLoopbackIP();
		} else if (netnode.getLoopbackIPv6() != null && !"".equals(netnode.getLoopbackIPv6())) {
			ip = netnode.getLoopbackIPv6();
		}
		String community = netnode.getReadCommunity();
		String version = netnode.getSnmpVersion();// 默认是2c
		if (ip == "" || community == "") {
			System.err.println("Null IP or Community");
			return -1;
		}

		SnmpGet snmpget = new SnmpGet();
		SnmpWalk snmpwalk = new SnmpWalk();
		// String sysDescr = snmpget.getString(ip, community,"SNMPv2-MIB::sysDescr.0",version);
		// String sysName = snmpget.getString(ip, community,"SNMPv2-MIB::sysName.0",version);
		// String sysLocation = snmpget.getString(ip, community,"SNMPv2-MIB::sysLocation.0",version);

		String sysDescr = snmpget.getString(ip, community, "1.3.6.1.2.1.1.1",version);// sysDescr
		if (sysDescr == null)
			return -1;

		String sysName = snmpget.getString(ip, community, "1.3.6.1.2.1.1.5",version);// sysName
		String sysLocation = snmpget.getString(ip, community,"1.3.6.1.2.1.1.6", version);// sysLocation
		if(netnode.getDeviceType()==null){
			String sysService = snmpget.getString(ip, community,"1.3.6.1.2.1.1.7", version);// sysService
			String ipForwarding=snmpget.getString(ip, community,"1.3.6.1.2.1.4.1", version);
			String flagType = "1";
			int flag=Integer.parseInt(sysService)&6;
			int forward=Integer.parseInt(ipForwarding);
			if(flag==2||(flag==6&&forward==2)){
				flagType="2";
			}else if(flag==4||(flag==6&&forward==1)){
				flagType="1";
			}else{
				flagType = "3";
			}
			DeviceType deviceType = deviceTypeService.findById(Long.valueOf(flagType));
			if(deviceType!=null ){
				netnode.setDeviceType(deviceType);
			}
		}

		netnode.setDescription(sysDescr);
		netnode.setName(sysName);
		netnode.setLocation(sysLocation);
		netnode.setIfinterfaces(new HashSet());
		return 1;
	}
	/**
	 * 利用snmp获得结点所包含的接口信息
	 * @param netnode
	 * @return
	 */
	public int snmpNodeInterfaces(Device netnode) {
		String loopbackIPKey = "";
		String ip = "";
		if (netnode.getLoopbackIP()!=null && !"".equals(netnode.getLoopbackIP())) {
			ip = netnode.getLoopbackIP();
		} else if (netnode.getLoopbackIPv6() != null && !netnode.getLoopbackIPv6().equals("")) {
			ip = netnode.getLoopbackIPv6();
		}
		String community = netnode.getReadCommunity();
		String version = netnode.getSnmpVersion();
		if (ip == "" || community == "") {
			System.err.println("Null IP or Community");
			return -1;
		}
		try {
			netnode.setIfinterfaces(new HashSet());
			Hashtable tempIfinterface = new Hashtable();
			// int ifnum = snmpget.getInt(ip, community, "IF-MIB::ifNumber.0");

			// 加入snmp发现的所有的接口
			SnmpWalk snmpwalk = new SnmpWalk();
			ArrayList ifIndexKey = new ArrayList();
			//snmpwalk.Walk(ip, community, "1.3.6.1.2.1.2.2.1.1",ifIndexKey,new ArrayList(),version);
			snmpwalk.Walk(ip, community, "ifIndex",ifIndexKey,new ArrayList(), version);// ifIndex
			
			//根据ifIndex生成Ifinterface，然后存入Hash中
			for(int i=0;i<ifIndexKey.size();i++){
				IfInterface ifinterface = new IfInterface();
				String ifindex = ifIndexKey.get(i).toString();
				ifinterface.setIfindex(ifindex);
				netnode.getIfinterfaces().add(ifinterface);
				ifinterface.setDevice(netnode);
				tempIfinterface.put(ifindex,ifinterface);
			}

			ArrayList ifTypeKey = new ArrayList();
			ArrayList ifTypeValue = new ArrayList();
			snmpwalk.Walk(ip, community, "ifType", ifTypeKey, ifTypeValue,version);
			int tempType = 0;
			if (ifTypeValue.size() >= ifIndexKey.size()) {
				tempType = ifIndexKey.size();			
			} else {
				tempType = ifTypeValue.size();
			}
			for (int i = 0; i < tempType; i++) {
				String ifType = ifTypeValue.get(i).toString();
				IfInterface ifinterface = (IfInterface)tempIfinterface.get(ifTypeKey.get(i).toString());
				if(ifinterface!=null){
					ifinterface.setInterfaceType(ifType);
				}
			}
			
			ArrayList ifDescrKey = new ArrayList();
			ArrayList ifDescrValue = new ArrayList();
			snmpwalk.Walk(ip, community, "ifDescr", ifDescrKey, ifDescrValue,version);
			for(int i = 0; i < ifDescrKey.size(); i ++){
				String ifDescr = ifDescrValue.get(i).toString();
				//获得loopbackip对应的法ifIndex
				if(ifDescr.equals("lo0.0") || ifDescr.equals("Loopback 0")
						|| ifDescr.equals("LoopBack0")
						|| ifDescr.equals("Loopback0")){
					loopbackIPKey = ifDescrKey.get(i).toString();
				}else if(ifDescr.equals("Vlan100") || ifDescr.equals("Vlan10") || ifDescr.equals("Vlan1")){
					loopbackIPKey = ifDescrKey.get(i).toString();
				}
				IfInterface ifinterface = (IfInterface)tempIfinterface.get(ifDescrKey.get(i).toString());
				if(ifinterface!= null){
					ifinterface.setDescription(ifDescr);
				}
			}
			
			ArrayList ifPhysAddressKey = new ArrayList();
			ArrayList ifPhysAddressValue = new ArrayList();
			snmpwalk.Walk(ip, community, "ifPhysAddress", ifPhysAddressKey, ifPhysAddressValue,version);
			for(int i = 0; i < ifPhysAddressKey.size(); i ++){
				String ifPhysAddress = ifPhysAddressValue.get(i).toString();
				IfInterface ifinterface = (IfInterface)tempIfinterface.get(ifPhysAddressKey.get(i).toString());
				if(ifinterface != null){
					ifinterface.setMAC(ifPhysAddress);
				}
			}
			
			ArrayList ifSpeedKey = new ArrayList();
			ArrayList ifSpeedValue = new ArrayList();
			snmpwalk.Walk(ip, community, "ifHighSpeed", ifSpeedKey, ifSpeedValue,version);
			if(ifSpeedKey.size()>0){
				for(int i = 0; i < ifSpeedKey.size(); i ++){
					IfInterface ifinterface = (IfInterface)tempIfinterface.get(ifSpeedKey.get(i).toString());
					if(ifinterface != null){
						Double temp = Double.valueOf(ifSpeedValue.get(i).toString());
						ifinterface.setSpeed(temp);
						ifinterface.setMaxSpeed(temp);
					}
				}
			}else{
				snmpwalk.Walk(ip, community, "ifSpeed", ifSpeedKey, ifSpeedValue,version);
				for(int i = 0; i < ifSpeedKey.size(); i ++){
					IfInterface ifinterface = (IfInterface)tempIfinterface.get(ifSpeedKey.get(i).toString());
					if(ifinterface != null){
						Double temp = Double.valueOf(ifSpeedValue.get(i).toString())/1000000;
						ifinterface.setSpeed(temp);
						ifinterface.setMaxSpeed(temp);
					}
				}
			}
			
			ArrayList ifNameKey = new ArrayList();
			ArrayList ifNameValue = new ArrayList();
			snmpwalk.Walk(ip, community, "ifAlias", ifNameKey, ifNameValue,version);		
			for(int i = 0; i < ifNameKey.size(); i ++){
				String ifDescr = ifNameValue.get(i).toString();
				IfInterface ifinterface = (IfInterface)tempIfinterface.get(ifNameKey.get(i).toString());
				if(ifinterface != null){
					ifinterface.setName(ifDescr);
				}
			}
			
			ArrayList ifAdminStatusKey = new ArrayList();
			ArrayList ifAdminStatusValue = new ArrayList();
			snmpwalk.Walk(ip, community, "ifAdminStatus", ifAdminStatusKey, ifAdminStatusValue,version);
			for(int i = 0; i < ifAdminStatusKey.size(); i ++)
			{
				String ifAdminStatus = ifAdminStatusValue.get(i).toString();
				IfInterface ifinterface = (IfInterface)tempIfinterface.get(ifAdminStatusKey.get(i).toString());
				if(ifinterface != null){
					ifinterface.setManageStatus(ifAdminStatus);	
				}
			}	
			
			ArrayList ifOperStatusKey = new ArrayList();
			ArrayList ifOperStatusValue = new ArrayList();
			snmpwalk.Walk(ip, community, "ifOperStatus", ifOperStatusKey, ifOperStatusValue,version);
			for(int i = 0; i < ifOperStatusKey.size(); i ++)
			{
				String ifOperStatus = ifOperStatusValue.get(i).toString();
				IfInterface ifinterface = (IfInterface)tempIfinterface.get(ifOperStatusKey.get(i).toString());
				if(ifinterface != null){
					ifinterface.setOperateStatus(ifOperStatus);
				}
			}
			
			if(Constants.IsIPv6.equals("0")){
				ArrayList ipv6IdentifierKey = new ArrayList();
				ArrayList ipv6IdentifierValue = new ArrayList();//ipv6's address			
				ArrayList ipv6IdentifierLengthValue = new ArrayList();
				snmpwalk.walkPart(ip, community, "1.3.6.1.2.1.55.1.8.1.2", ipv6IdentifierKey, ipv6IdentifierLengthValue,ipv6IdentifierValue,version);	
				for(int i  = 0; i < ipv6IdentifierKey.size(); i ++){
					String ipv6Identifier = ipv6IdentifierValue.get(i).toString();				
					String ipv6IdentifierLength = ipv6IdentifierLengthValue.get(i).toString();				
					IfInterface ifinterface = (IfInterface)tempIfinterface.get(ipv6IdentifierKey.get(i).toString());
					if(ifinterface != null){							
						ifinterface.setPrefix(ipv6IdentifierLength);
						if(!snmpwalk.toHex(ipv6Identifier).equals("-1")){
							String ipv6 = snmpwalk.toHex(ipv6Identifier);
							ifinterface.setIpv6(ipv6);
							if(ipv6IdentifierKey.get(i).equals(loopbackIPKey)){
								netnode.setLoopbackIPv6(ipv6);
							}
						}
					}
				}
				
				ArrayList ifAdminStatusv6Key = new ArrayList();
				ArrayList ifAdminStatusv6Value = new ArrayList();
				snmpwalk.Walk(ip, community, "IPV6-MIB::ipv6IfAdminStatus", ifAdminStatusv6Key, ifAdminStatusv6Value,version);
				//snmpwalk.Walk(ip, community, "1.3.6.1.2.1.55.1.5.1.9", ifAdminStatusv6Key, ifAdminStatusv6Value,version);
				if(ifAdminStatusv6Key.size()>0){
					for(int i = 0; i < ifAdminStatusv6Key.size(); i ++){
						String ifAdminStatusv6 = ifAdminStatusv6Value.get(i).toString();
						IfInterface ifinterface = (IfInterface)tempIfinterface.get(ifAdminStatusv6Key.get(i).toString());
						if(ifinterface != null){
							ifinterface.setManageStatusIPv6(ifAdminStatusv6);
						}
					}
				}
				
				ArrayList ifOperStatusv6Key = new ArrayList();
				ArrayList ifOperStatusv6Value = new ArrayList();
				snmpwalk.Walk(ip, community, "IPV6-MIB::ipv6IfOperStatus", ifOperStatusv6Key, ifOperStatusv6Value,version);
				//snmpwalk.Walk(ip, community, "1.3.6.1.2.1.55.1.5.1.10", ifOperStatusv6Key, ifOperStatusv6Value,version);
				if(ifOperStatusv6Key.size()>0){
					for(int i = 0; i < ifOperStatusv6Key.size(); i ++){
						String ifOperStatusv6 = ifOperStatusv6Value.get(i).toString();
						IfInterface ifinterface = (IfInterface)tempIfinterface.get(ifOperStatusv6Key.get(i).toString());
						if(ifinterface != null){
							ifinterface.setOperateStatusIPv6(ifOperStatusv6);
						}
					}
				}
			}//Endof if(Constants.IsIPv6.equals("0"))		

			ArrayList indexKey = new ArrayList();
			ArrayList indexValue = new ArrayList();// ipv4's address
			ArrayList maskKey = new ArrayList();
			ArrayList maskValue = new ArrayList();
			snmpwalk.Walk(ip, community, "ipAdEntIfIndex", indexKey,indexValue, version);
			snmpwalk.Walk(ip, community, "ipAdEntNetMask", maskKey, maskValue,version);
			for (int i = 0; i < indexKey.size(); i++) {
				String ifindex = indexValue.get(i).toString();
				if (ifindex != null && !ifindex.equals("")) {
					String ipv4 = indexKey.get(i).toString();
					if(ifindex.equals(loopbackIPKey)){
						netnode.setLoopbackIP(ipv4);
					}
					IfInterface ifinterface = (IfInterface)tempIfinterface.get(ifindex);
					if(ifinterface != null){
						if(ifinterface.getIpv4()!=null&&!ifinterface.getIpv4().equals(ipv4)){
							IfInterface interface2=new IfInterface();
							interface2.setDescription(ifinterface.getDescription());
							interface2.setIfindex(ifinterface.getIfindex());
							interface2.setInterfaceType(ifinterface.getInterfaceType());
							interface2.setLowerThreshold(ifinterface.getLowerThreshold());
							interface2.setUpperThreshold(ifinterface.getUpperThreshold());
							interface2.setMAC(ifinterface.getMAC());
							interface2.setManageStatus(ifinterface.getManageStatus());
							interface2.setManageStatusIPv6(ifinterface.getManageStatusIPv6());
							interface2.setName(ifinterface.getName());
							interface2.setSpeed(ifinterface.getSpeed());
							interface2.setMaxSpeed(ifinterface.getMaxSpeed());
							interface2.setIpv6(ifinterface.getIpv6());
							interface2.setNetmask(ifinterface.getNetmask());
							interface2.setPrefix(ifinterface.getPrefix());
							interface2.setIpv4(ipv4);
							interface2.setNetmask(maskValue.get(i).toString());
							netnode.getIfinterfaces().add(interface2);
							interface2.setDevice(netnode);
						}else{
							ifinterface.setIpv4(ipv4);
							ifinterface.setNetmask(maskValue.get(i).toString());
						}
					}
				}
			}
			return 1;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return -1;
		} catch (Exception ex) {
			return -1;
		}
	}
	/**
	 * 根据接口索引获得某一结点的接口信息
	 * @param node 包含结点信息的值对象
	 * @param index 接口索引
	 * @return 包含接口信息的值对象
	 */
	public IfInterface getInterfaceByIfIndex(Device node, String index) {
		Collection list = (Collection) node.getIfinterfaces();
		if (list != null) {
			Iterator itr = list.iterator();
			while (itr.hasNext()) {
				IfInterface ifinterface = (IfInterface) itr.next();
				if (ifinterface.getIfindex() == index)
					return ifinterface;
			}
		}
		return null;
	}
	/**
	 * 增加新结点
	 * @param netnode 待增加的新结点
	 * @return 新增结点id
	 */
	public Device addNode(Device netnode) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		try {
			if (netnode.getIfinterfaces() != null) {
				Iterator itr = (netnode.getIfinterfaces()).iterator();
				while (itr.hasNext()) {
					IfInterface ifinterface = (IfInterface) itr.next();
					if (ifinterface != null) {
						session.save(ifinterface);
					}
				}
			}
			if(netnode.getDeviceType()==null){
				DeviceType deviceType = deviceTypeService.findById(Long.valueOf("1"));
				if(deviceType==null){
					deviceType = new DeviceType();
					deviceType.setName("router");
					deviceType.setDescription("router");
					session.save(deviceType);
				}
				netnode.setDeviceType(deviceType);
			}
			session.save(netnode);
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
		}
		if (netnode.getId() != null) {
			return netnode;
		} else {
			return null;
		}
	}
	public void deleteDevice(long deviceId) throws Exception{
		 DeviceService service=new DeviceService();
		 PortService portService=new PortService();
		 LinkService linkService=new LinkService();
		 ServiceManageService virtualService=new ServiceManageService();
		 LinkedHashSet<Long> linkIdSet = new LinkedHashSet<Long>();
		 LinkedHashSet<Long> routerSet = new LinkedHashSet<Long>();
		 LinkedHashSet<Long> switchSet = new LinkedHashSet<Long>();
		 LinkedHashSet<Long> serverSet = new LinkedHashSet<Long>();
		 LinkedHashSet<Long> workstationSet = new LinkedHashSet<Long>();
		 LinkedHashSet<Long> customSet = new LinkedHashSet<Long>();
		 List portList=portService.getPortList(deviceId);						
			if(!portList.isEmpty()){
				IfInterface port0 = (IfInterface) portList.get(0);
				Long typeId = port0.getDevice().getDeviceType().getId();
				if(typeId==1){
					routerSet.add(deviceId);
				}else if(typeId==2){
					switchSet.add(deviceId);
				}else if(typeId==3){
					serverSet.add(deviceId);
				}else if(typeId==4){
					workstationSet.add(deviceId);
				}else{
					customSet.add(deviceId);
				}							
				for(int j=0;j<portList.size();j++){
					IfInterface port=(IfInterface) portList.get(j);								
					long portId=port.getId();
					List linkList=linkService.getLinkListByInterfId(portId);
					for(int k=0;k<linkList.size();k++){
						Link link=(Link)linkList.get(k);
						linkIdSet.add(link.getId());
						linkService.deleteLink(link);
					}
					portService.delete(port);
				}
			}
			service.delete(Device.class, Long.valueOf(deviceId));
			List virtualList=virtualService.getListByDeviceId(Long.valueOf(deviceId));
			if(virtualList!=null){
				for(int j=0;j<virtualList.size();j++){
					ServiceManage virtualPort=(ServiceManage)virtualList.get(j);
					virtualService.delete(virtualPort);
				}
			}
			
			List<View> views = new ViewService().getViewList();
			for(View view:views){
				Long userId = view.getUserId();
				String userName = view.getUserName();
				String viewName = view.getName();
				String path = Constants.webRealPath + "file/user/";
				String userDirectory = userName + "_" + userId;
				String fileStr = path + userDirectory + "/" + viewName + ".xml"; 
				File file = new File(fileStr);
				if(file.exists()){
					if(file.length()>0){
						Document document = XMLManager.readXml(fileStr);
						//删除视图中相关链路
						Iterator<Long> it = linkIdSet.iterator();
						while(it.hasNext()) {
							Long linkId = it.next();
							XMLManager.deleteTag(document,"links","link","id",linkId.toString());
						}
						//删除视图中相关的设备
						Iterator<Long> routerIt = routerSet.iterator();
						while(routerIt.hasNext()){
							Long routerId = routerIt.next();
							XMLManager.deleteTag(document,"routers","router","id",routerId.toString());
						}
						Iterator<Long> switchIt = switchSet.iterator();
						while(switchIt.hasNext()){
							Long switchId = switchIt.next();
							XMLManager.deleteTag(document,"switchs","switch","id",switchId.toString());
						}
						Iterator<Long> serverIt = serverSet.iterator();
						while(serverIt.hasNext()){
							Long serverId = serverIt.next();
							XMLManager.deleteTag(document,"servers","service","id",serverId.toString());
						}
						Iterator<Long> workstationIt = workstationSet.iterator();
						while(workstationIt.hasNext()){
							Long workstationId = workstationIt.next();
							XMLManager.deleteTag(document,"workstations","workstation","id",workstationId.toString());
						}
						Iterator<Long> customIt = customSet.iterator();
						while(customIt.hasNext()){
							Long customId = customIt.next();
							XMLManager.deleteTag(document,"workstations","workstation","id",customId.toString());
						}
						XMLManager.writeXml(document, fileStr);
					}
				}
			}
	}
	
	public void topoSaveInitText(List content, String viewName, boolean isAppend) {
		if (content!=null&&content.size() > 0 ) {
			File file = new File(Constants.webRealPath + "file/topo/data/");
			if (!file.exists()) {
				file.mkdirs();
			}
			String savePath = Constants.webRealPath + "file/topo/data/" + viewName + ".txt";
			File temp=new File(savePath);
			if(temp.exists()){
				temp.delete();
			}
			PrintWriter writer = null;
			try {
				writer = new PrintWriter(new FileOutputStream(savePath, isAppend));				
					for(int i=0;i<content.size();i++){
					writer.write(content.get(i).toString() + "\r\n");
					}
					
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
					writer.close();
				}
			}
		}
	}
	
	/**
	 * 得到所有的图标信息
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<DeviceIcon> getAllDeviceIcons(String start, String limit) {
		List<DeviceIcon> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		list = session.createCriteria(DeviceIcon.class).setFirstResult(Integer.parseInt(start)).setMaxResults(Integer.parseInt(limit)).list();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			DeviceIcon deviceIcon = (DeviceIcon) iterator.next();
			Hibernate.initialize(deviceIcon.getDeviceType());
		}
		transaction.commit();
		return list;
	}
	public List<DeviceIcon> getAllDeviceIcons(String sql) {
		List<DeviceIcon> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		if(sql.equals("model")){
			list = session.createCriteria(DeviceIcon.class).add(Restrictions.eq("model", null)).list();
		}else if(sql.equals("all")){
			list = session.createCriteria(DeviceIcon.class).list();
		}
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			DeviceIcon deviceIcon = (DeviceIcon) iterator.next();
			Hibernate.initialize(deviceIcon.getDeviceType());
		}
		transaction.commit();
		return list;
	}
	
	/**
	 * 删除图标
	 * @param id
	 * @return
	 */
	public boolean deleteDeviceIcon(String id){
		boolean success = false;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			DeviceIcon di =(DeviceIcon) session.load(DeviceIcon.class, Long.valueOf(id));	
			if(di!=null){
				session.delete(di);
			}
			transaction.commit();
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	/**
	 * 添加图标
	 * @param deviceIcon
	 * @return
	 */
	public boolean addDeviceIcon(DeviceIcon deviceIcon) {
		boolean success = false;
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		try {
			if(deviceIcon.getModel().equals("")){
				deviceIcon.setModel(null);
			}
			session.save(deviceIcon);
			success = true;
			System.out.println("8888 db success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return success;
	}
	
	/**
	 * 更改设备图标
	 * @param deviceIconId
	 * @param imgPath
	 * @return
	 */
	public boolean alterDeviceIcon(String deviceIconId, String imgPath){
		boolean success = false;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		String udpateSql = "update device_icon set iconFile = '" + imgPath + "' where id = " + deviceIconId;
		Transaction transaction = session.beginTransaction();
		try {
			session.createSQLQuery(udpateSql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		transaction.commit();
		return success;
	}
	
	/**
	 * 由图标Id得到图标
	 * @param deviceIconId
	 * @return
	 */
	public DeviceIcon getDeviceIconById(String deviceIconId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		DeviceIcon deviceIcon = new DeviceIcon();
		Transaction transaction = session.beginTransaction();
		try {
			deviceIcon = (DeviceIcon)session.createCriteria(DeviceIcon.class).add(Restrictions.eq("id", Long.parseLong(deviceIconId))).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deviceIcon;
	}
	
	/**
	 * 设备类型名字得到设备类型
	 * @param deviceIconId
	 * @return
	 */
	public DeviceType getDeviceIconByName(String name) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		DeviceType  deviceType = new DeviceType();
		Transaction transaction = session.beginTransaction();
		try {
			deviceType = (DeviceType)session.createCriteria(DeviceType.class).add(Restrictions.eq("name", name)).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deviceType;
	}
	
	/**
	 * 得到所有的设备类型
	 * @return
	 */
	public List<DeviceType> listAllDeviceType(){
		List<DeviceType> list = new ArrayList<DeviceType>();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			list = session.createCriteria(DeviceType.class).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
}
