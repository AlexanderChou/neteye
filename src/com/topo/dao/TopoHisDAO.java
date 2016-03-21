package com.topo.dao;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.base.model.Device;
import com.base.model.IfInterface;
import com.base.model.Link;
import com.base.service.DeviceService;
import com.base.service.LinkService;
import com.base.service.PortService;
import com.base.util.Constants;
import com.base.util.HibernateUtil;
import com.base.util.JDOMXML;
import com.base.util.W3CXML;
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
/**
 * 这个类为处理拓扑历史记录所用
 * 
 * @author 李宪亮
 * 
 */
@SuppressWarnings("unused")
public class TopoHisDAO {

	/**
	 * 阅读history.xml 文件 得到他的内容
	 * 
	 * @see
	 * @param fileName
	 *            history.xml 文件
	 * @return
	 */
	public List<String[]> getAllhistoryTxtData(String fileName) {
		List<String[]> list = getTxtFileData(fileName, "");
		return list;
	}

	/**
	 * 阅读history.xml 文件
	 * 
	 * @see
	 * @param file
	 *            拓扑历史 history.xml 文件
	 * @return list 拓扑历史的列表
	 * 注：由于history.xml文件中，有多个seedIP数据，而该方法仅取一个seedIP,
	 * 可能会导致拓扑历史结果查询页面，种子节点的IP地址显示是永远是最后一条记录的值
	 */
	public List<String[]> getTxtFileData(String file, String condition) {

		List<String[]> list = new ArrayList<String[]>();
		try {
			org.w3c.dom.Document xmldoc = W3CXML.loadXMLDocumentFromFile(file);
			NodeList rootNodeList = xmldoc.getElementsByTagName("topo");

			if (rootNodeList != null) {
				for (int i = 0; i < rootNodeList.getLength(); i++) {
					Node rootNode = rootNodeList.item(i);
					NodeList childLists = rootNode.getChildNodes();
					String sName = "";
					String sbeginTime = "";
					String sendTime = "";
					String sdeviceCount = "";
					String slinkCount = "";
					String sIP = "";
					String ssnmpVersion = "1.0";
					String sUserName = "";
					String hop = "";
					long startTime = 0;
					long endTime = 0;
					String[] ss = new String[9];
					ss[6] = ssnmpVersion;// snmp鐗堟湰

					for (int j = 0; j < childLists.getLength(); j++) {
						Node childNode = childLists.item(j);
						String nodeName = childNode.getNodeName();
						if (nodeName.equals("topoName")) {
							sName = childNode.getTextContent();
							ss[0] = sName;
						} else if (nodeName.equals("startDate")) {
							sbeginTime = childNode.getTextContent();
							if (sbeginTime != null && !"".equals(sbeginTime)) {// 澶勭悊鏃堕棿锛屽皢鏃堕棿浠庢椂闂存杞崲涓烘椂鍒伙紝xml涓瓨鍌ㄧ殑鏄寜绉掔畻鐨勶紝鑰宩ava涓槸鎸夌収姣璁＄畻鐨�
								startTime = Long.parseLong(sbeginTime) * 1000;
								Timestamp startTs = new Timestamp(startTime);
								sbeginTime = startTs.toString()
										.substring(0, 19);
								ss[1] = sbeginTime;
							}
						} else if (nodeName.equals("endDate")) {
							sendTime = childNode.getTextContent();
							if (sendTime != null && !"".equals(sendTime)) {// 澶勭悊鏃堕棿锛屽皢鏃堕棿浠庢椂闂存杞崲涓烘椂鍒伙紝xml涓瓨鍌ㄧ殑鏄寜绉掔畻鐨勶紝鑰宩ava涓槸鎸夌収姣璁＄畻鐨�
								endTime = Long.parseLong(sendTime) * 1000;
								Timestamp endTs = new Timestamp(endTime);
								sendTime = endTs.toString().substring(0, 19);
								ss[2] = sendTime;
							}
						} else if (nodeName.equals("nodeNumber")) {
							sdeviceCount = childNode.getTextContent();
							ss[3] = sdeviceCount;
						} else if (nodeName.equals("linkNumber")) {
							slinkCount = childNode.getTextContent();
							ss[4] = slinkCount;
						} else if (nodeName.equals("seedIP")) {
							sIP = childNode.getTextContent();
							ss[5] = sIP;
						} else if (nodeName.equals("userName")) {
							sUserName = childNode.getTextContent();
							ss[7] = sUserName;
						}else if (nodeName.equals("hop")) {
							hop = childNode.getTextContent();
							ss[8] = hop;
						}
					}
					list.add(ss);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	
	}

	/**
	 * 在history.txt 文件里得到路径后 在得到targetFile里的数据 （无条件的）
	 * @see
	 * @param dir
	 *            在history.txt得到的文件夹名
	 * @param targetFile
	 *            需要阅读的文件
	 * @return 目标文件的内容
	 */
	public List<Map<String, String>> getDevices(String dir, String targetFile) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		File file = new File(dir);
		if (file.exists()) {
			File[] files = file.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].getName().equals(targetFile)) {
						list = getFileData(dir + "/" + targetFile, "");
					}
				}
			}
		} else {
			System.out.println(" 该目录不存在： " + dir);
		}

		return list;
	}

	/**
	 * 得到链路
	 * 
	 * @param file
	 *            xml文件
	 * @param deviceName
	 *            查看某一设备的名字
	 * @return
	 */
	public List<Map<String, String>> getTopoLinks(String file, String deviceName) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Document document = readXml(file);
		Element root = document.getRootElement();
		List<Element> devices = root.element("devices").elements();

		for (Element device : devices) {
			if (device.elementText("name").equals(deviceName)) {
				List<Element> intfs = device.element("interfaces").elements();
				for (Element intf : intfs) {

					List<Element> dests = intf.element("dests").elements();
					for (Element dest : dests) {
						String deviceId = dest.elementText("destId");
						String ifIndex = dest.elementText("destIndex");
						
						if(StringUtils.isNotEmpty(ifIndex) && StringUtils.isNotEmpty(deviceId)) {
							for (Element dev : devices) {
								if (deviceId.equals(dev.element("id").getText())) {
									List<Element> is = dev.element("interfaces").elements();
									for (Element i : is) {
										if (ifIndex.equals(i.elementText("ifIndex"))) {
											Map<String, String> map = new HashMap<String, String>();
											map.put("name", deviceName);
											map.put("IP", intf.elementText("ifIndex"));
											
											List<Element> addresses = intf.element("addresses").elements("address");
											
											if (!addresses.isEmpty()) {
												map.put("subIP", getSubnet(device.elementText("IP"), addresses.get(0).elementText("netmask")));
												map.put("netMask",  addresses.get(0).elementText("netmask"));
											}
											
											map.put("IP2", i.elementText("ifIndex"));
											map.put("name2", dev.elementText("name"));
											list.add(map);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return list;
	}

	/**
	 * 在history.txt 文件里得到路径后 在得到targetFile里的数据 
	 * @see
	 * @param dir  在history.txt 得到的文件夹名
	 * @param targetFile  需要阅读的文件
	 * @param condition
	 *            目标设备
	 * @return 目标设备的链路
	 */
	public List<Map<String, String>> findFileAndGetData(String dir,
			String targetFile, String condition) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		File file = new File(dir);
		if (file.exists()) {
			File[] files = file.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].getName().equals(targetFile)) {
						list = getTopoLinks(dir + "/" + targetFile, condition);
						break;
					}
				}
			}
		} else {
			System.out.println(" 该目录不存在： " + dir);
		}

		return list;
	}

	/**
	 * 该方法为查询所用 
	 * @see
	 * @param filePath    查询的文件
	 * @param keyWord   查询的设备名
	 * @return
	 */
	public List<Map<String, String>> getDeviceByKeyWord(String filePath,
			String keyWord) {
		return getFileData(filePath, keyWord);
	}

	/**
	 * 删除历史记录
	 * 
	 * @see
	 * @param filePath
	 *            拓扑历史的路径所在
	 * @param names
	 */
	public void deleteTopoHis(String filePath, String names) {
		SAXReader reader = new SAXReader();
		Document document;
		try {
			document = reader.read(new File(filePath + "history/history.xml"));
			Element root = document.getRootElement();
			List<Element> topos = root.elements("topo");
			for (Element topo : topos) {
				String topoName = topo.element("topoName").getText();
				String[] nameArr = names.split(";");
				for (String name : nameArr) {
					if (name.equals(topoName)) {
						root.remove(topo);
						/** 在topoHis下有两文件 一个是xml（写的设备间的关系） 和 txt文件（写的是调用js）  */
						File txtFile = new File(filePath + topoName + ".txt");
						File xmlFile = new File(filePath + topoName + ".xml");
						
						if (txtFile.exists()) {
							txtFile.delete();
						}
						
						if (xmlFile.exists()) {
							xmlFile.delete();
						}
						break;
					}
				}
			}
			saveXml(filePath + "history/history.xml", document);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用于检验拓扑历史的名字是否存在
	 * 
	 * @param filePath
	 * @param nameV
	 *            传入的拓扑历史名
	 * @return
	 */
	public boolean checkNameIsHave(String filePath, String nameV) {
		boolean isHave = false;
		org.w3c.dom.Document xmldoc = W3CXML.loadXMLDocumentFromFile(filePath);
		if (xmldoc != null) {
			NodeList linkLists = xmldoc.getElementsByTagName("topoName");
			if(linkLists!=null){
				for (int i = 0; i < linkLists.getLength(); i++) {
					org.w3c.dom.Node linkNode = linkLists.item(i);
					if(linkNode.getTextContent().equals(nameV)){
						isHave = true;
						break;
					}
				}
			}
		}		
		return isHave;
	}
	public static void main(String[] args) {
		boolean isHave = false;
		org.w3c.dom.Document xmldoc = W3CXML.loadXMLDocumentFromFile("f:/history.xml");
		if (xmldoc != null) {
			NodeList linkLists = xmldoc.getElementsByTagName("topoName");
			if(linkLists!=null){
				for (int i = 0; i < linkLists.getLength(); i++) {
					org.w3c.dom.Node linkNode = linkLists.item(i);
					System.out.println(linkNode.getTextContent());
					if(linkNode.getTextContent().equals("topo20101116085844")){
						isHave = true;
						break;
					}
				}
			}
		}		
	}
	/**
	 * 保存xml
	 * 
	 * @param file
	 *            xml文件的存储路径
	 * @param document
	 *            xml文件体
	 */
	public void saveXml(String file, Document document) {
		XMLWriter writer = null;
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			writer = new XMLWriter(new FileWriter(new File(file)), format);
			writer.write(document);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读取xml文件
	 * 
	 * @param file
	 *            xml文件位置
	 * @return xml
	 */
	public Document readXml(String file) {
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;
	}

	/**
	 * 阅读topoName.xml 文件 得到devices
	 * 
	 * @see
	 * @param file
	 *            需要阅读的xml
	 * @param condition
	 *            需要的查询条件 name:zhangsan 查询的是在devices节点下的节点
	 * @return
	 */
	public List<Map<String, String>> getFileData(String file, String condition) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Document document = readXml(file);
		Element root = document.getRootElement();
		List<Element> elements = root.elements();
		for (Element e : elements) {
			if ("devices".equals(e.getName())) {
				List<Element> devices = e.elements();
				for (Element device : devices) {
					Map<String, String> attributes = new HashMap<String, String>();
					List<Element> as = device.elements();
					if (StringUtils.isNotEmpty(condition)) {
						String name = device.elementText("name");
						if (name.equals(condition)) {
							attributes.put("name", device.elementText("name"));
							attributes.put("IP", device.elementText("IP"));
							list.add(attributes);
						}
					} else {
						for (Element attribute : as) {
							if (attribute.isTextOnly()) {
								attributes.put(attribute.getName(), attribute.getText());
							}
						}
						list.add(attributes);
					}
				}
			}
		}

		return list;
	}

	/**
	 * 判断当前系统是Linux 还是 window xp 不同的系统调用不同的方法 得到IP
	 * @return
	 */
	public String getIP() {
		String address = null;
		String system = System.getProperty("os.name");
		if ("Windows XP".equals(system)) {
			address = getIPForWindow();
		} else {
			address = getIPForLinux();
		}
		return address;
	}

	/**
	 * 判断当前系统是Linux 还是 window xp 不同的系统调用不同的方法
	 * 
	 * @return
	 */
	public String getNetAddress() {
		String address = null;
		String system = System.getProperty("os.name");
		if ("Windows XP".equals(system)) {
			address = getSubnetAddressForWindow();
		} else {
			address = getSubnetAddressForLinux();
		}
		return address;
	}

	/**
	 * 使用ipconfig/all命令 获取windows系统上的IP
	 * 
	 * @return
	 */
	public String getIPForWindow() {
		String cmd = "cmd.exe /c ipconfig/all"; // window下获取子网掩码的命令
		String find = "IP Address";// 子网掩码的提示

		Vector ipConfigs;
		ipConfigs = execute(cmd);
		Object[] ipConfigArray = ipConfigs.toArray();
		String result = null;
		for (int i = 0; i < ipConfigArray.length; i++) {
			String ipConfig = ipConfigArray[i].toString();
			if (!ipConfig.equalsIgnoreCase("") && ipConfig.indexOf(find) != -1) {
				String[] subnet = ipConfig.split(":");
				result = subnet[1].substring(1);
				break;
			}
		}
		return result;
	}

	/**
	 * 使用ipconfig/all命令 获取windows系统上的子网掩码
	 * 
	 * @return
	 */
	public String getSubnetAddressForWindow() {
		String cmd = "cmd.exe /c ipconfig/all"; // window下获取子网掩码的命令
		String find = "Subnet Mask";// 子网掩码的提示

		Vector ipConfigs;
		ipConfigs = execute(cmd);
		Object[] ipConfigArray = ipConfigs.toArray();
		String result = null;
		for (int i = 0; i < ipConfigArray.length; i++) {
			String ipConfig = ipConfigArray[i].toString();
			if (!ipConfig.equalsIgnoreCase("") && ipConfig.indexOf(find) != -1) {
				String[] subnet = ipConfig.split(":");
				result = subnet[1].substring(1);
				break;
			}
		}
		return result;
	}

	/**
	 * 得到网关的ip
	 * @return
	 */
	public String getWangGuan() {
		String cmd = null; // window下获取子网掩码的命令
		String system = System.getProperty("os.name");

		if ("Windows XP".equals(system)) {
			cmd = "cmd.exe /c ipconfig/all";// windows下的操作命令
		} else {
			cmd = "ifconfig";// Linux下的操作命令
		}
		String find = "Default Gateway";// 子网掩码的提示
		Vector ipConfigs;
		ipConfigs = execute(cmd);
		Object[] ipConfigArray = ipConfigs.toArray();
		String result = null;
		for (int i = 0; i < ipConfigArray.length; i++) {
			String ipConfig = ipConfigArray[i].toString();
			if (!ipConfig.equalsIgnoreCase("") && ipConfig.indexOf(find) != -1) {
				String[] subnet = ipConfig.split(":");
				result = subnet[1].substring(1);
				if (StringUtils.isNotEmpty(result)) {
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 使用ifconfig命令 获取linux系统上的子网掩码
	 * @return
	 */
	private String getSubnetAddressForLinux() {
		String cmd = "ifconfig"; // linux下获取子网掩码的命令
		String find = "Mask";// 子网掩码的提示

		Vector ipConfigs;
		ipConfigs = execute(cmd);
		Object[] ipConfigArray = ipConfigs.toArray();
		String result = null;
		for (int i = 0; i < ipConfigArray.length; i++) {
			String ipConfig = ipConfigArray[i].toString();
			if (!ipConfig.equalsIgnoreCase("") && ipConfig.indexOf(find) != -1) {

				String[] subnet1 = ipConfig.split(find);
				String[] subnet = subnet1[1].split(":");
				result = subnet[1];
				if (StringUtils.isNotEmpty(result)) {
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 使用ifconfig命令 获取linux系统上的IP
	 * 
	 * @return
	 */
	private String getIPForLinux() {
		String cmd = "ifconfig"; // linux下获取子网掩码的命令
		String find = "inet addr";// 子网掩码的提示

		Vector ipConfigs;
		ipConfigs = execute(cmd);
		Object[] ipConfigArray = ipConfigs.toArray();
		String result = null;
		for (int i = 0; i < ipConfigArray.length; i++) {
			String ipConfig = ipConfigArray[i].toString();
			if (!ipConfig.equalsIgnoreCase("") && ipConfig.indexOf(find) != -1) {

				String[] subnet1 = ipConfig.split(find);
				String[] subnet = subnet1[1].split(":");
				result = subnet[1];
				if (StringUtils.isNotEmpty(result)) {
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 执行命令
	 * 
	 * @param shellCommand
	 * @return
	 */
	private Vector<String> execute(String shellCommand) {
		try {
			Start(shellCommand);
			Vector<String> vResult = new Vector<String>();
			DataInputStream in = new DataInputStream(p.getInputStream());
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			String line;
			do {
				line = reader.readLine();
				if (line == null) {
					break;
				} else {
					vResult.addElement(line);
				}
			} while (true);
			reader.close();
			return vResult;
		} catch (Exception e) {
			return null;
		}
	}

	private static Process p;

	private void Start(String shellCommand) {
		try {
			if (p != null) {
				p.destroy();
				p = null;
			}
			Runtime sys = Runtime.getRuntime();
			p = sys.exec(shellCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 有ip 和 子网掩码 得到子网号
	 * @param ip
	 * @param netMask
	 * @return
	 */
	public  String getSubnet(String ip, String netMask){
		String[] ipContent = ip.split("\\.");
		String[] netMaskContent = netMask.split("\\.");
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < ipContent.length; i++) {
			int num1 = Integer.parseInt(ipContent[i]);
			int num2 = Integer.parseInt(netMaskContent[i]);
			sb.append(num1&num2);
			if (i == 3) {
				continue;
			}
			sb.append(".");
		}
		
		return sb.toString();
	}
	
	/**
	 * 判断该拓扑发现是否结束
	 * @param srcXml xml源文件
	 * @param topoName 拓扑发现的名字
	 * @return 返回true 拓扑发现结束 
	 */
	public boolean topoIsOver(String srcXml, String topoName){
		Document document = JDOMXML.readXML(srcXml);
		Element root = document.getRootElement();
		List<Element> topos =root.elements();
		boolean isOver = false;
		
		for (Element topo : topos) {
			String tpName = topo.elementText("topoName");
			if (tpName.equals(topoName)) {
				String status = topo.elementText("status");
				if ("end".equals(status)) {
					isOver = true;
				}
				break;
			}
		}
		
		return isOver;
	}
	
	/**
	 * 对重复设备的处理 更新
	 * @param devicesStr
	 * @param viewName
	 * @return
	 */
	public List<Object> updateDuplicationOfDevice(String[] devicesStr, String viewName) {
		List<String> deleteDeviceIds = new ArrayList<String>();
		Map<Long, String> updateDeviceIds = new HashMap<Long, String>();
		//Map<String, Long> ignoreDevices = new HashMap<String, Long>();
		Map<String, Object> ignoreDevices = new HashMap<String, Object>();
		for (String device : devicesStr) {
			String[] arr = device.split(";");
			int flag = Integer.parseInt(arr[3]);
			if (flag == 1) {
				//更新设备的名字     需要
				long deviceId = Long.parseLong(arr[0]);
				String deviceName = arr[1].trim();
				updateDeviceIds.put(deviceId, deviceName);
			} else if( flag == 2 ){
				//在deleteDeviceIds 中添加删除设备的 设备id  删除设备
				String deviceId = arr[0].trim();
				deleteDeviceIds.add(deviceId);
			} else if( flag == 0 ) {
				//long deviceId = Long.parseLong(arr[0]);
				String deviceName = arr[1].trim();
				ignoreDevices.put(deviceName, arr[0].trim());
			}
		}
		
		DeviceService deviceService = new DeviceService();
		/* 更新设备操作 */
		if (!updateDeviceIds.isEmpty()) {
			deviceService.updateDeviceByBatch(updateDeviceIds);
		}
		
		/* 删除设备 及 相关操作  */
		if (!deleteDeviceIds.isEmpty()) {
			deviceService.deleteDeviceByBatch(deleteDeviceIds.toString());
		}
		
		/*  对 devicesStr 的过滤 即在 这个数组中仅留 删除的设备   */
		List<String> dvOfView = new ArrayList<String>(Arrays.asList(devicesStr));
		List<String> removeContent = new ArrayList<String>();
		for (String str : dvOfView) {
			String id = str.split(";")[0].trim();
			if (deleteDeviceIds.contains(id)) {
				removeContent.add(str);
			}
		}
		
		Object[] result = null;
		/*  此函数返回的结果集   */
		List<Object> resultList = new ArrayList<Object>();
		
		//TODO 删除视图操作 
		try {
			if (!removeContent.isEmpty()) {
				
				result = removeContent.toArray();
				deleteViewByDeviceIds(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/* index 为零的元素  放置更新设备的结果集*/
		resultList.add(result);
		
		/* index 为 1 的元素 放置 更新名字的设备  */
		resultList.add(updateDeviceIds);
		
		/* index 为2 的元素 放置 忽略操作的设备 */
		
		resultList.add(ignoreDevices);
		return resultList;
	}
	
	/**
	 * 返回具有重复lookbackIp的设备
	 * @param lookbackIps ip的字符串
	 * @return
	 */
	public List<Device> getDuplicationDeviceByLookBackIPs (String lookbackIPs){
		List<Device> devices = new  ArrayList<Device>(); 
		if (StringUtils.isNotEmpty(lookbackIPs)) {
			lookbackIPs = lookbackIPs.substring(0, lookbackIPs.length() - 1);
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			Transaction transaction = session.beginTransaction();
			
			String ipv4_sql = "select * from device where loopbackip in (" + lookbackIPs + ")";
			devices = session.createSQLQuery(ipv4_sql).addEntity(Device.class).list();
			
			String ipv6_sql = "select *  from device where loopbackipv6 in (" + lookbackIPs + ")";
			List<Device> ipv6Device = session.createSQLQuery(ipv6_sql).addEntity(Device.class).list();
			
			if (ipv6Device != null) {
				devices.addAll(ipv6Device);
			}
			
		}
		return devices;
	}
	
	/**
	 *  保存lookbackIp不重复的 设备
	 * @param devices 设备数组
	 * @param loobackIP 
	 * @param deviceList lookbackIP 重复的 设备
	 * @param topoViewName 保存为 txt 的文件名字
	 * @return 返回重复设备 和 新发现设备的信息
	 */
	public List<com.topo.dto.Device> saveDeviceAsText(String[] devices, String loobackIP, List<Device> deviceList, String topoViewName, String disName){
		loobackIP = loobackIP.replaceAll("'", "");
		List<String> deviceStr = new ArrayList<String>(Arrays.asList(devices));
		List<String> ipList = Arrays.asList(loobackIP.split(","));
		List<com.topo.dto.Device> list = new ArrayList<com.topo.dto.Device>();
		Map<String, String> map = getIfNumMap(loobackIP, disName);
		
		List<String> removeIds = new ArrayList<String>(); 
		for (Device device : deviceList) {
			int index = 0;
			String deviceLookbackIP = "";
			boolean isFind = false;
			if (StringUtils.isNotEmpty(device.getLoopbackIP())) {
				index = ipList.indexOf(device.getLoopbackIP());
				deviceLookbackIP = device.getLoopbackIP();
				removeIds.add(deviceStr.get(index));
				isFind = true;
			}
			
			if (!isFind && StringUtils.isNotEmpty(device.getLoopbackIPv6())) {
				index = ipList.indexOf(device.getLoopbackIPv6());
				deviceLookbackIP = device.getLoopbackIPv6();
				removeIds.add(deviceStr.get(index));
				isFind = true;
			}
			
			if (!deviceStr.isEmpty()) {
				com.topo.dto.Device d = new com.topo.dto.Device();
				d.setId(device.getId());
				d.setChineseName(device.getChineseName());
				d.setIfNum(device.getIfNum());
				d.setIpv4(device.getLoopbackIP());
				d.setName(device.getName());
				d.setIpv6(device.getLoopbackIPv6());
				d.setDeviceTypeName(device.getDeviceType().getName());
				
				String[] topoDevice = deviceStr.get(index).split("\\|\\|");
				String ifNum = map.get(deviceLookbackIP);
				d.setTopo_ifNum(Integer.valueOf(ifNum));
				d.setTopo_ip(topoDevice[1]);
				d.setTopo_name(topoDevice[0]);
				d.setCommunity(topoDevice[2]);
				d.setTopo_deviceTypeName(getDeviceType(Integer.parseInt(topoDevice[4])));
				list.add(d);
			}
		}
		
		/* 去掉重复的设备 */
		
		deviceStr.removeAll(removeIds);
		if (!deviceStr.isEmpty()) {
			Object[] content = deviceStr.toArray();
			for (int i = 0; i < content.length; i++) {
				if (StringUtils.isNotEmpty(content[i].toString())) {
					String[] subArr = content[i].toString().split("\\|\\|");
					content[i] =subArr[4] + "//" + subArr[1] + "//" + subArr[2] + "//" + subArr[0];
				}
			}
			FileDAO fileDAO = new FileDAO();
			fileDAO.topoSaveInitText(content, topoViewName, false);
		}
		
		return list;
	}
	
	/**
	 * 由下标得到数据类型
	 * @param index
	 * @return
	 */
	public String getDeviceType(int index) {
		String deviceType = "router";
		switch (index) {
		case 2:
			deviceType = "switch";break;
		case 3:
			deviceType = "server";break;
		case 4:
			deviceType = "workstation";
		}
		
		return deviceType;
	}
	
	/**
	 * 由设备的 lookbackIp 得到各设备的端口数
	 * @param lookbackIp 字符串
	 * @param disName 
	 * @return 得到key 是
	 */
	public Map<String, String> getIfNumMap(String lookbackIp, String disName){
		Map<String, String> map = new HashMap<String, String>();
		if(disName.indexOf("[")!=-1){
			disName = disName.substring(0,disName.indexOf("["));
		}
		String viewFilePath = Constants.webRealPath + "file/topo/topoHis/" + disName + ".xml";
		Document document = JDOMXML.readXML(viewFilePath);
		Element root = document.getRootElement();
		Element devicesElement = root.element("devices");
		List<Element> devices = devicesElement.elements();
		
		for (Element device : devices) {
			String ip = device.elementText("IP");
			if(lookbackIp.indexOf(ip) != -1) {
				Element interfaces = device.element("interfaces");
				int ifNum = interfaces.elements().size();
				map.put(ip, String.valueOf(ifNum));
			}
		}
		
		return map;
	}
	
	/**
	 * 
	 * @param deviceIps
	 * @throws Exception 
	 */
	public void deleteViewByDeviceIds(Object[] devices) throws Exception {
		String userName  = (String)ServletActionContext.getRequest().getSession().getAttribute("userName");
		Long userId = (Long)ServletActionContext.getRequest().getSession().getAttribute("userId");
		String path = Constants.webRealPath + "file/user/" + userName + "_" + userId + "/";
		
		File viewFile = new File(path);
		File[] files = viewFile.listFiles();
		for (File view : files) {
			Document document = XMLManager.readXml(view);
			PortService portService = new PortService();
			for (Object device : devices) {
				String[] atts = device.toString().split(";");
				/* 删除视图中的链路信息 */
				List<IfInterface> portList=portService.getPortList(Long.parseLong(atts[0]));
				for (IfInterface ifInterface : portList) {
					LinkService linkService = new LinkService();
					List<Link> linkList=linkService.getLinkListByInterfId(ifInterface.getId());
					for (Link link : linkList) {
						XMLManager.deleteTag(document,"links","link","id",link.getId().toString());
					}
				}
				
				/* 删除视图中的设备信息 */
				if ("router".equals(atts[5])) {
					XMLManager.deleteTag(document,"routers","router","id",String.valueOf(Long.parseLong(atts[0])));
				} else if ("switch".equals(atts[5])) {
					XMLManager.deleteTag(document,"switchs","switch","id",atts[0]);
				} else if ("server".equals(atts[5])) {
					XMLManager.deleteTag(document,"servers","service","id",String.valueOf(atts[0]));
				} else if ("workstation".equals(atts[5])) {
					XMLManager.deleteTag(document,"workstations","workstation","id",atts[0]);
				}
				
			}
		}
				
	}
}
