package com.topo.dao;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.base.util.Constants;
import com.base.util.W3CXML;
import com.topo.dto.Link;
import com.topo.dto.MyRandoms;
import com.topo.dto.Router;

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
//主要是计算各节点的坐标x，y，返回Arraylist
public class TopoDAO {
	private static Map<String, Router> deviceMap = null;
	private static List<String> linkList = null;
	private static MyRandoms random = new MyRandoms();
	private static int limitNum = 100;

	
	public static ArrayList<Router> parseTopoXML(String path, String fileName) {
		deviceMap = new TreeMap<String, Router>();
		File file = new File(path + File.separator + fileName + ".xml");
		/** 判断视图文件是否存在 不存在就返回 */
		if (!file.exists()) {
			return null;
		}

		int circleX = 0;
		int circleY = 0;
		int redius = 200;//同心圆半径

		double angle = 0;
		double imageRedius = 12.5;//设备图片的半径
		String[] content = null;

		Map<String, Router> rootMap = new HashMap<String, Router>();
		ArrayList<Router> list = new ArrayList<Router>(); //非跟桥		
		ArrayList<Router> result = new ArrayList<Router>(); //结果列表
		String rootIP = "";

		//以下代码主要是为了判断是子网发现还是全网发现
		boolean isSubNet = true;

		org.w3c.dom.Document xmldoc = W3CXML.loadXMLDocumentFromFile(path + File.separator + fileName + ".xml");
		NodeList hops = xmldoc.getElementsByTagName("deviceHop");
		for (int i = 0; i < hops.getLength(); i++) {
			Node linkNode = hops.item(i);
			if (!linkNode.getTextContent().equals("1")) {
				isSubNet = false;
				break;
			}
		}
		List<String> lineList = FileDAO.getDeviceFromXml(path + File.separator + fileName + ".xml", isSubNet);
		//例：D-JXZL-E050;222.28.247.132;0x00e0fc3c7c01;bfsuwlzx;222.28.247.132;0;1;2;id;
		for (String line : lineList) {
			content = line.split(";");
			if (content.length == 9) {
				Router r = new Router();
				r.setId(Integer.parseInt(content[8]));
				r.setFound(true); // already found
				r.setName(content[0]);
				r.setIpv4(content[1]);
				r.setMAC(content[2]);
				r.setCommunity(content[3]);
				r.setParent(content[4]); //默认的Parent是跟桥
				r.setRootCost(content[5]); //deviceHop
				r.setVersion(content[6]);
				r.setDeviceType(Integer.parseInt(content[7]));
				deviceMap.put(content[1], r);
				if (content[5].trim().equals("0")) { //已发现的跟桥节点
					rootMap.put(content[1], r);
					rootIP = content[1];
				} else {
					if (content[5].trim().equals("1")) {
						if (!rootIP.equals("")) {
							Router fth = rootMap.get(rootIP); //父节点的router对象
							ArrayList<String> children = fth.getChildren(); //父节点孩子列表
							children.add(content[1]); //孩子结点IP加入父节点的孩子列表
							fth.setChildren(children);
						}
					} else {
						list.add(r); //非根桥加入列表
					}
				}
			} else {
				continue;
			}
		}
		
		/* 查看有多少个设备 ，一圈显示30个设备，查看有多少个圈 */
		int total_layer_count = deviceMap.size();
		int count_layer = total_layer_count / 30 + 1;

		
		circleX = count_layer * redius;
		circleY = count_layer * redius;
		
		//如果存在直接父节点，则Parent为直接父节点IP,将子节点加到父节点的children列表中
		lineList.clear();
		linkList = FileDAO.getLisksFromXml(path + File.separator + fileName + ".xml");
		if (isSubNet) {
			Iterator<String> iterator = deviceMap.keySet().iterator();
			int devcieCount = deviceMap.size();
			int layer_Count = devcieCount / 30 + 1;

			if (devcieCount % 30 == 0) {
				layer_Count = devcieCount / 30;
			}

			int i = 0;//子网设备数
			while (iterator.hasNext()) {

				int layer_index = i % layer_Count + 1;
				int n = deviceMap.size();
				angle = 2 * 3.1415926 / n;
				String key = iterator.next();
				Router r = deviceMap.get(key);
				int x = (int) (circleX + layer_index * redius * Math.cos(angle * i) + imageRedius);
				int y = (int) (circleY + layer_index * redius * Math.sin(angle * i) + imageRedius);
				if(x<0){
					x = x-x+(int)(circleX + layer_index * redius  + imageRedius);
				}
				if(y<0){
					y = y-y+(int)(circleX + layer_index * redius  + imageRedius);
				}
				r.setRX(x);
				r.setRY(y);
				result.add(r);
				i++;
			}
		} else {
			String[] lineContent = null;
			int count = 0;
			for (String line : linkList) {
				count++;
				lineContent = line.split(";");
				if (lineContent.length == 6 && deviceMap.get(lineContent[1]) != null && deviceMap.get(lineContent[4]) != null) {
					Router fth = deviceMap.get(lineContent[1]);
					Router chd = deviceMap.get(lineContent[4]); //由子节点IP作为key，hash得到Router对象
					if (fth.getParent().trim().equals(lineContent[4].trim())) {
						if (fth.getRootCost().trim().equals("1"))
							continue;
						ArrayList<String> children = chd.getChildren(); //父节点孩子列表
						children.add(lineContent[1]);
						chd.setChildren(children);
					} else if (chd.getParent().trim().equals(lineContent[1].trim())) {
						if (chd.getRootCost().trim().equals("1"))
							continue;
						ArrayList<String> children = fth.getChildren(); //父节点孩子列表
						children.add(lineContent[4]);
						fth.setChildren(children);
					}
				} else {
					continue;
				}
			}

			//设置各跟桥节点（含已发现、未发现两部分）的坐标，并由此计算第一圈结点的坐标
			if (!rootMap.isEmpty()) {
				Iterator<String> iterator = rootMap.keySet().iterator();
				int j = 0;
				int i = 0; //根桥计数
				while (iterator.hasNext()) {
					String key = iterator.next();

					Router r = rootMap.get(key);
					r.setRX(circleX);
					r.setRY(circleY + 4 * i * redius);
					ArrayList<String> children = r.getChildren();

					int devcieCount = children.size();
					int layer_Count = devcieCount / 30 + 1;

					if (devcieCount % 30 == 0) {
						layer_Count = devcieCount / 30;
					}

					int n = children.size(); //孩子数
					if (n != 0) {
						angle = 2 * 3.1415926 / n;
						Iterator<String> iterator2 = children.iterator();
						int k = 0; //孩子计数

						while (iterator2.hasNext()) { //计算第一圈各节点角度,坐标

							int layer_index = k % layer_Count + 1;

							String IP = iterator2.next();
							Router child = deviceMap.get(IP);
							int x = (int) (r.getRX() + layer_index * redius * Math.cos(angle * k) + imageRedius);
							int y = (int) (r.getRY() + layer_index * redius * Math.sin(angle * k) + imageRedius);
							if(x<0){
								x = x-x+(int)(circleX + layer_index * redius  + imageRedius);
							}
							if(y<0){
								y = y-y+(int)(circleX + layer_index * redius  + imageRedius);
							}
							child.setRX(x);
							child.setRY(y);
							result.add(child);
							k++;

						}
					}
					result.add(r);
					i++;
				}
			}
			Map<String, Router> tempMap = new HashMap<String, Router>();
			Map<String, Router> tempParMap = new HashMap<String, Router>();//存储拥有孩子的设备集合
			Map<String, Integer> noParMap = new HashMap<String, Integer>();//key=设备跳数 value=该跳上未有父级的设备数目

			if (!list.isEmpty()) {
				Iterator<Router> iterator = list.iterator();

				int devcieCount = list.size();
				int layer_Count = devcieCount / 30 + 1;

				if (devcieCount % 30 == 0) {
					layer_Count = devcieCount / 30;
				}
				int i = 0;

				while (iterator.hasNext()) {

					int layer_index = i % layer_Count + 1;

					Router r = iterator.next();
					if (r.getParent() == null || r.getParent().equals("")) {
						int chdx = 0;
						int chdy = 0;
						if (noParMap.containsKey(r.getRootCost())) {
							Integer value = noParMap.get(r.getRootCost()) + 1;
							chdx = (int) (circleX + layer_index * redius * Math.cos(Math.PI / 12 * value) + imageRedius);
							chdy = (int) (circleY + layer_index * redius * Math.sin(Math.PI / 12 * value) + imageRedius);
							noParMap.put(r.getRootCost(), value);
						} else {
							chdx = (int) (circleX + layer_index * redius * Math.cos(Math.PI / 12) + imageRedius);
							chdy = (int) (circleY + layer_index * redius * Math.sin(Math.PI / 12) + imageRedius);
							noParMap.put(r.getRootCost(), new Integer(1));
						}
						if(chdx<0){
							chdx = chdx-chdx+(int)(circleX + layer_index * redius  + imageRedius);
						}
						if(chdy<0){
							chdy = chdy-chdy+(int)(circleX + layer_index * redius  + imageRedius);
						}
						r.setRX(chdx);
						r.setRY(chdy);
						result.add(r);
					} else {
						if (!tempParMap.containsKey(r.getParent())) {
							Router fth = deviceMap.get(r.getParent());
							tempParMap.put(r.getParent(), fth);

							int fx = fth.getRX();
							int fy = fth.getRY();

							ArrayList<String> children = fth.getChildren();

							int devcieCount_children = list.size();
							int layer_Count_children = devcieCount_children / 30 + 1;

							if (devcieCount % 30 == 0) {
								layer_Count_children = devcieCount_children / 30;
							}

							int n = children.size(); //孩子数
							if (n != 0) {
								double initAngle = Math.atan2(fy, fx);

								Iterator<String> iterator2 = children.iterator();
								int k = 0; //孩子计数
								while (iterator2.hasNext()) { //计算各节点角度,坐标

									int layer_index_children = k % layer_Count_children + 1;
									String tempIP = iterator2.next();
									Router child = deviceMap.get(tempIP);
									int chdx = (int) (fx + layer_index_children * redius * Math.cos(initAngle + Math.PI / n * k) + imageRedius);
									int chdy = (int) (fy + layer_index_children * redius * Math.sin(initAngle + Math.PI / n * k) + imageRedius);
									if(chdx<0){
										chdx = chdx-chdx+(int)(circleX + layer_index_children * redius  + imageRedius);
									}
									if(chdy<0){
										chdy = chdy-chdy+(int)(circleX + layer_index_children * redius  + imageRedius);
									}
									child.setRX(chdx);
									child.setRY(chdy);
									k++;
									if (tempMap.containsKey(tempIP)) {
										continue;
									} else {
										tempMap.put(tempIP, child);
										result.add(child);
									}
								}
							} else {//End if(n!=0){
								r.setRX(fx * 2);
								r.setRY(fy * 2);
								tempMap.put(r.getIpv4(), r);
								result.add(r);
							}
						}
					}
					i++;
				}//Endof while
			}
		}
		return result;
	}

	public static void getTopoIMG(String path, String fileName) throws Exception {
		ArrayList<Router> result = parseTopoXML(path, fileName);
		//将全局拓扑图生成为一个静态的图片，用以页面显示
		List<Link> links = getLinks(path, fileName);
		graphic(result, links, fileName);
	}

	public static List<Router> getDevices(String path, String fileName, Integer threshold) throws Exception {
		if (threshold == null) {
			threshold = limitNum;
		}
		ArrayList<Router> result = parseTopoXML(path, fileName);
		StringBuffer registerStation2TXT = new StringBuffer();
		if (result != null && result.size() <= threshold) {//将整个拓扑直接显示，不再分片
			for (Router router : result) {
				registerStation(router, registerStation2TXT);
			}
			registerStation2TXT.append(getLinks(linkList, registerStation2TXT));
			saveRegisterStation2TXT(fileName, registerStation2TXT.toString());
		} else {
			//将全局拓扑图生成为一个静态的图片，用以页面显示
			List<Link> links = getLinks(path, fileName);
			graphic(result, links, fileName);
			//求平面上的质点系的重心
			ArrayList<Router> partion1 = new ArrayList<Router>();
			ArrayList<Router> partion2 = new ArrayList<Router>();
			ArrayList<Router> partion3 = new ArrayList<Router>();
			ArrayList<Router> partion4 = new ArrayList<Router>();
			int weightX = 0;
			int heighY = 0;
			for (Router router : result) {
				weightX += router.getRX();
				heighY += router.getRY();
			}
			weightX /= result.size();
			heighY /= result.size();

			for (Router router : result) {
				int x = router.getRX();
				int y = router.getRY();
				if ((x >= 0 && x <= weightX) && (y >= 0 && y <= heighY)) {
					partion1.add(router);
				} else if (x >= weightX && (y >= 0 && y <= heighY)) {
					router.setRX(router.getRX() - 600);
					partion2.add(router);
				} else if ((x >= 0 && x <= weightX) && y >= heighY) {
					router.setRY(router.getRY() - 200);
					partion3.add(router);
				} else if (x >= weightX && y >= heighY) {
					router.setRX(router.getRX() - 600);
					router.setRY(router.getRY() - 200);
					partion4.add(router);
				}
			}
			//将所有链路保存在一个topoNametoLink.txt文件中，便于添加设备时进行后续处理
			StringBuffer linkBuffer = new StringBuffer();
			String[] content = null;
			for (String line : linkList) {
				content = line.split(";");
				if (content.length == 6) {
					try {
						if (deviceMap.get(content[1]) != null && deviceMap.get(content[4]) != null) {
							linkBuffer.append("registerLine3('" + "s" + random.getRandom() + "','" + content[0] + "_" + content[1] + "_" + content[2] + "--" + content[3] + "_" + content[4] + "_" + content[5] + "','" + "s" + deviceMap.get(content[1]).getId() + "','" + "s" + deviceMap.get(content[4]).getId() + "'," + content[2] + "," + content[5] + ");" + "\r\n");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					continue;
				}
			}
			saveRegisterStation2TXT(fileName + "toLink", linkBuffer.toString());

			createLastPartion(partion1, 1, fileName, linkList, threshold);
			createLastPartion(partion2, 2, fileName, linkList, threshold);
			createLastPartion(partion3, 3, fileName, linkList, threshold);
			createLastPartion(partion4, 4, fileName, linkList, threshold);
		}
		return result;
	}

	public static void createLastPartion(ArrayList<Router> partion, int n, String topoName, List<String> lineList, Integer threshold) throws Exception {
		StringBuffer partionStr = new StringBuffer();
		StringBuffer first = new StringBuffer();
		StringBuffer second = new StringBuffer();
		if (partion.size() >= threshold) {
			//重新求该部分的重心坐标
			int weightX = 0;
			for (Router router : partion) {
				weightX += router.getRX();
			}
			weightX /= partion.size();
			for (Router router : partion) {
				int x = router.getRX();
				if (x <= weightX) {
					registerStation(router, first);
				} else {
					registerStation(router, second);
				}
			}
		} else {
			for (Router router : partion) {
				registerStation(router, partionStr);
			}
		}
		if (!first.toString().equals("") && !second.toString().equals("")) {//生成子分区
			//求出该分区包含的链路
			first.append(getLinks(lineList, first));
			second.append(getLinks(lineList, second));
			saveRegisterStation(n, first, "-1", topoName);
			saveRegisterStation(n, second, "-2", topoName);
		} else {
			partionStr.append(getLinks(lineList, partionStr));
			saveRegisterStation(n, partionStr, "", topoName);
		}
	}

	public static void registerStation(Router router, StringBuffer partion) {
		partion.append("registerStation2('" + "s" + router.getId() + "','" + router.getName() + "','" + router.getIpv4() + "','" + router.getCommunity() + "','" + "1" + "','" + router.getDeviceType() + "'," + router.getRX() + "," + router.getRY() + ");" + "\r\n");
	}

	public static void saveRegisterStation(int n, StringBuffer partion, String postfix, String viewName) {
		switch (n) {
		case 2:
			saveRegisterStation2TXT(viewName + "[1" + postfix + "]", partion.toString());
			break;
		case 1:
			saveRegisterStation2TXT(viewName + "[2" + postfix + "]", partion.toString());
			break;
		case 3:
			saveRegisterStation2TXT(viewName + "[3" + postfix + "]", partion.toString());
			break;
		case 4:
			saveRegisterStation2TXT(viewName + "[4" + postfix + "]", partion.toString());
			break;
		}
	}

	/**
	 * 保存 js RegisterStation2 调用函数 
	 * @param fileName
	 * @param content
	 */
	public static void saveRegisterStation2TXT(String fileName, String content) {
		String targetFile = Constants.webRealPath + "file/topo/topoHis/" + fileName + ".txt";
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileOutputStream(targetFile));
			writer.print(content);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	/**
	 * 在 jsp 页面打印 RegisterStation2 js 调用函数
	 * @param path
	 * @param out
	 * @param isEdit
	 * @param fileName
	 */
	public static void printRegisterStation2(JspWriter out, String fileName) {
		String filePath = Constants.webRealPath + "file/topo/topoHis/" + fileName + ".txt";
		File file = new File(filePath);
		if (file.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
				String line = null;
				try {
					line = reader.readLine();
					while (StringUtils.isNotEmpty(line)) {
						out.println(line);
						line = reader.readLine();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public static String getLinks(List<String> lineList, StringBuffer devicesBuf) throws Exception {
		String[] content = null;
		StringBuffer sb = new StringBuffer();
		String devices = devicesBuf.toString();
		List<String> dels = new ArrayList<String>();
		for (String line : lineList) {
			content = line.split(";");
			if (content.length == 6) {
				try {
					if (deviceMap.get(content[1]) != null && deviceMap.get(content[4]) != null) {
						String srcId = "'s" + deviceMap.get(content[1]).getId() + "'";
						String destId = "'s" + deviceMap.get(content[4]).getId() + "'";
						//此处需要考虑：若某个设备不存在，是否需要将该链路显示出为（？）
						if (devices.indexOf(srcId) >= 0 && devices.indexOf(destId) >= 0) {
							sb.append("registerLine3('" + "s" + random.getRandom() + "','" + content[0] + "_" + content[1] + "_" + content[2] + "--" + content[3] + "_" + content[4] + "_" + content[5] + "','" + "s" + deviceMap.get(content[1]).getId() + "','" + "s" + deviceMap.get(content[4]).getId() + "'," + content[2] + "," + content[5] + ");" + "\r\n");
							dels.add(line);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				continue;
			}
		}
		if (dels.size() > 0) {
			for (String line : dels) {
				lineList.remove(line);
			}
		}
		return sb.toString();
	}

	/**
	 * 有路径和文件名得到链路名
	 * @param path
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static List<Link> getLinks(String path, String fileName) throws Exception {
		ArrayList<Link> list = new ArrayList<Link>();
		List<String> lineList = FileDAO.getLisksFromXml(path + File.separator + fileName + ".xml");
		String[] content = null;
		MyRandoms random = new MyRandoms();
		for (String line : lineList) {
			content = line.split(";");
			if (content.length == 6) {
				Link link = new Link();
				link.setId(random.getRandom());
				link.setSrcName(content[0]);
				link.setSrcIP(content[1]);
				link.setSrcPort(content[2]);
				link.setDestName(content[3]);
				link.setDestIP(content[4]);
				link.setDestPort(content[5]);
				list.add(link);
			} else {
				continue;
			}
		}
		return list;
	}

	public static void graphic(List<Router> routerList, List<Link> linkList, String fileName) throws Exception {
		File file = new File(Constants.webRealPath + "file/topo/topoHis/" + fileName + ".png");
		//		File imageFile=new File( Constants.webRealPath+"images/china.jpg"); 
		//		Image readImage = ImageIO.read(imageFile);
		String url = Constants.webRealPath + "images/china.jpg";
		//		readImage = Toolkit.getDefaultToolkit().getImage(url); 
		//		MediaTracker tracker = new MediaTracker(null); 
		//		tracker.addImage(readImage, 0);
		//		ImageIcon icon = new ImageIcon(url);
		//		readImage = icon.getImage();

		int width = 1280;
		int height = 1024;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphic2D = image.createGraphics();
		image = graphic2D.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		graphic2D.dispose();
		graphic2D = image.createGraphics();
		//		graphic2D.drawImage(readImage, 0, 0,null);
		TopoDAO dao = new TopoDAO();
		if (routerList != null) {
			for (int i = 0; i < routerList.size(); i++) {
				Router router = (Router) routerList.get(i);
				graphic2D.setPaint(Color.green);
				graphic2D.fillOval(router.getRX(), router.getRY(), 10, 10);
				graphic2D.setPaint(Color.black);
				graphic2D.drawString(router.getName(), router.getRX() + 10, router.getRY() + 10);
			}
		}
		if (linkList != null) {
			for (int i = 0; i < linkList.size(); i++) {
				Link link = (Link) linkList.get(i);
				Router Drouter = dao.deviceMap.get(link.getDestIP());
				Router Srouter = dao.deviceMap.get(link.getSrcIP());
				graphic2D.setPaint(Color.blue);
				graphic2D.draw(new Line2D.Double(Drouter.getRX() + 5, Drouter.getRY() + 5, Srouter.getRX() + 5, Srouter.getRY() + 5));

			}
		}
		graphic2D.dispose();
		ImageIO.write(image, "png", file);
	}

	public int getLimitNum() {
		return limitNum;
	}

	public void setLimitNum(int limitNum) {
		this.limitNum = limitNum;
	}
}
