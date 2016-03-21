package com.topo.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.base.util.Constants;
import com.base.util.FileUtil;
import com.base.util.W3CXML;

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
public class FileDAO {
	public static List<String> readFile(String fileName) throws Exception {
		File file = new File(fileName);
		List<String> result = new ArrayList<String>();
		// 该文件是否存在
		if (file.exists()) {
			FileReader reader = null;
			try {
				reader = new FileReader(fileName);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			BufferedReader br = new BufferedReader(reader);
			String line = null;
			try {
				while ((line = br.readLine()) != null) {
					if (line.equals("")) {
						continue;
					} else {
						result.add(line);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				br.close();
				reader.close();
			} finally {
				br.close();
				reader.close();
			}
		} else {
			System.out.println("该文件不存在 ***");
		}
		return result;
	}

	/**
	 * 提取xml文件信息 目标内容相当于 switch.txt
	 * 
	 * @param file
	 * @return
	 */
	public static List<String> getDeviceFromXml(String file, boolean isSubNet) {
		List<String> list = new ArrayList<String>();
		Document document = readXML(file);
		String rootIP = "";
		if (document != null) {
			Element root = document.getRootElement();
			List<Element> devices = root.element("devices").elements();
			for (Element device : devices) {
				String deviceId = device.elementText("id");
				String parentIP = "";
				String deviceIP = device.elementText("IP");
				String hop = device.elementText("deviceHop");
				if (hop.equals("1")) {
					parentIP = deviceIP;
					rootIP = deviceIP;
				} else if (hop.equals("2") && !rootIP.equals("")) {
					parentIP = rootIP;
				} else {
					if (getParent(deviceId, document, Integer.parseInt(hop)) != null) {
						parentIP = getParent(deviceId, document, Integer.parseInt(hop));
					}
				}
				StringBuffer sb = new StringBuffer();
				sb.append(device.elementText("name") + ";");
				sb.append(deviceIP + ";");
				sb.append(device.elementText("bridgeAddr") + ";");
				sb.append(device.elementText("community") + ";");

				// parent rootCost
				sb.append(parentIP + ";");
				if (isSubNet) {
					sb.append(Integer.parseInt(hop) + ";");
				} else {
					sb.append(Integer.parseInt(hop) - 1 + ";");
				}

				sb.append("1;");

				String deviceType = device.elementText("deviceType");
				String dt = null;
				if ("router".equals(deviceType)) {
					dt = "1";
				} else if (deviceType.indexOf("switch")>=0) {
					dt = "2";
				} else if ("server".equals(deviceType)) {
					dt = "3";
				} else {
					dt = "4";
				}
				sb.append(dt + ";" + deviceId + ";");
				list.add(sb.toString());
			}
		}
		return list;
	}

	/**
	 * 得到字符串List 相当于 早期的 sw_lk.txt
	 * @param file
	 * @return
	 * 注：在获得链路时，应该将重复的链路去掉
	 *     已做了修改 不再有重复的记录
	 */
	public static List<String> getLisksFromXml(String file) {
		List<String> list = new ArrayList<String>();
		Document document = readXML(file);
		Map<String, String> linkMap = new HashMap<String, String>();//存储找到的链路字符串以及链路相反顺序的字符串,便于进行重复判断
		if (document != null) {
			Element root = document.getRootElement();
			List<Element> devices = root.element("devices").elements();
			// 循环device
			for (Element device : devices) {
				// 得到interface 在循环interface
				List<Element> intfs = device.element("interfaces").elements();
				for (Element intf : intfs) {
					Element destsElement = intf.element("dests");
					if (destsElement != null) {
						List<Element> dests = destsElement.elements();
						for (Element dest : dests) {
							String deviceId = dest.elementText("destId");
							String ifIndex = dest.elementText("destIndex");
							// 根据deviceId 和 ifIndex得到对应的 device 和 ifIndex
							OUTLOOP: for (Element dev : devices) {
								if (deviceId.equals(dev.elementText("id"))) {
									List<Element> is = dev.element("interfaces").elements();
									for (Element i : is) {
										if (ifIndex.equals(i.elementText("ifIndex"))) {
											StringBuffer sb = new StringBuffer();
											StringBuffer opposite = new StringBuffer();
											sb.append(device.elementText("name") + ";");
											sb.append(device.elementText("IP") + ";");
											sb.append(intf.elementText("ifIndex") + ";");
											sb.append(dev.elementText("name") + ";");
											sb.append(dev.elementText("IP") + ";");
											sb.append(dest.elementText("destIndex") + ";");
											opposite.append(dev.elementText("name") + ";").append(dev.elementText("IP") + ";").append(dest.elementText("destIndex") + ";").append(device.elementText("name") + ";").append(device.elementText("IP") + ";").append(intf.elementText("ifIndex") + ";");
											if (linkMap.containsKey(sb.toString()) || linkMap.containsKey(opposite.toString())) {
												continue;
											} else {
												linkMap.put(sb.toString(), sb.toString());
												linkMap.put(opposite.toString(), opposite.toString());
												/** 滤掉重复的链路 */
												if (!list.contains(opposite.toString())) {
													list.add(opposite.toString());
												}
											}
											break OUTLOOP; // 找到跳出
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
	 * 在xml文件中得到设备的父节点
	 * 
	 * @param deviceId
	 *            该节点的设备名
	 * @param document
	 * @return 得到父节点的ip
	 */
	public static String getParent(String deviceId, Document document, int parentHop) {
		String parent = null;
		List<Element> devices = document.getRootElement().element("devices").elements();
		for (Element device : devices) {
			List<Element> is = device.element("interfaces").elements();
			for (Element i : is) {
				List<Element> dests = i.element("dests").elements();
				for (Element d : dests) {
					String hop = device.elementText("deviceHop");
					int hopInt = Integer.parseInt(hop) + 1;
					if (parentHop == hopInt && d.elementText("destId").equals(deviceId)) {
						parent = device.elementText("IP");
						break;
					}
				}
			}
		}
		return parent;
	}

	/**
	 * 阅读xml文件
	 * 
	 * @param file
	 * @return
	 */
	public static Document readXML(String file) {
		Document document = null;
		try {
			InputStream ifile = new FileInputStream(file);
			InputStreamReader ir = new InputStreamReader(ifile, "UTF-8"); 
			SAXReader reader = new SAXReader(); 
			document = reader.read(ir); 
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} 
		return document;
	}

	/**
	 * 从拓扑生成的xml文件中读取tag标签下的内容
	 * 
	 * @param file,tag
	 * @return
	 */
	public static String ReadTagDataFromXML(String file, String tag) {
		String nodeData = null;
		String fileStr = file + ".xml";
		org.w3c.dom.Document xmldoc = W3CXML.loadXMLDocumentFromFile(fileStr);
		if (xmldoc != null) {
			NodeList nodeLists = xmldoc.getElementsByTagName(tag);
			Node node = nodeLists.item(0);
			if (node != null) {
				nodeData = node.getTextContent();
			}
		}
		return nodeData;
	}

	/**
	 * 将传递过来的数据保存在txt文件中
	 * @param viewName
	 * @param content  
	 * @param isAppend  是否追加
	 */

	public void topoSaveInitText(Object[] content, String viewName, boolean isAppend) {
		if (content.length > 0 && StringUtils.isNotEmpty(content[0].toString())) {
			File file = new File(Constants.webRealPath + "file/topo/data/");
			if (!file.exists()) {
				file.mkdirs();
			}
			String savePath = Constants.webRealPath + "file/topo/data/" + viewName + ".txt";
			PrintWriter writer = null;
			try {
				writer = new PrintWriter(new FileOutputStream(savePath, isAppend));
				for (Object line : content) {

					writer.write(line.toString() + "\r\n");
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
	 * 在目标文件中读取 设备的id 和name 放在map中
	 * @param viewName
	 * @return 
	 */
	public Map<String, Object> getRoueterIdFromDataFile(String viewName) {
		System.out.println("viewName="+viewName);
		String filePath = Constants.webRealPath + "file/topo/data/" + viewName + ".xml";
		Document document = readXML(filePath);

		Element init = document.getRootElement();
		Element devices = init.element("devices");
		List<Element> device_infos = devices.elements("device_info");
		//Map<String, Long> map = new HashMap<String, Long>();
		Map<String, Object> map = new HashMap<String, Object>();

		for (Element device_info : device_infos) {
			String key = device_info.elementText("device_name");
			String value = device_info.elementText("id");
            String sysDescr = device_info.elementText("sysDescr"); 
			if (StringUtils.isNotEmpty(value)) {
				map.put(key, value+"||"+sysDescr);
			}
		}
		return map;
	}

	/**
	 * 检查某个视图是否含有多个区间
	 * @param dirPath 路径
	 * @param disName 发现名称
	 * @return 返回 true 说明含有多个区间
	 */
	public boolean checkIsMore(String dirPath, String disName) {
		File file = new File(dirPath);
		File[] files = file.listFiles();
		for (File f : files) {
			if (f.getName().indexOf(disName) != -1 && f.getName().indexOf("[") != -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 取回各个区间的名字
	 * @param dirPath
	 * @param disName
	 * @return 区间的名字 + “；”
	 */
	public String returnNameStr(String dirPath, String disName) {
		StringBuffer sb = new StringBuffer();
		File[] contents = new FileUtil().getFiles(dirPath, disName, "[", "txt");
		for (File f : contents) {
			sb.append(f.getName().replace(".txt", ""));
			sb.append(";");
		}
		return sb.toString();
	}
}
