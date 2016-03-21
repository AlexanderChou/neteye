package com.totalIP.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import com.opensymphony.xwork2.ActionSupport;
import com.totalIP.dao.ReorderingDao;
import com.totalIP.dto.NodeReorderingshow;
import com.totalIP.dto.XMLOb;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.*;
import javax.xml.xpath.*;

public class ReorderingXmlAction extends ActionSupport{
	private List<XMLOb> allXML = new ArrayList<XMLOb>();
	public List<XMLOb> getAllXML() {
		return allXML;
	}
	public void setAllXML(List<XMLOb> allXML) {
		this.allXML = allXML;
	}
	public String eighthoursreorderingxml() {
		String xml = null;
		Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/HH");
        String eighthoursagodoc = df.format(new Date(date.getTime() - 7 * 60 * 60 * 1000));
        String doc = df.format(date);
		try {
		//解析器工厂类
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		//解析器
		DocumentBuilder builder = factory.newDocumentBuilder();
		//操作的Document对象
		Document document = builder.newDocument();
		//设置XML的版本
		document.setXmlVersion("1.0");
		//创建根节点
		Element root = document.createElement("chart");
		root.setAttribute("caption", "Reordering in recent 8 hours");
		root.setAttribute("subcaption", "(From "+eighthoursagodoc+" to "+doc+")");
		root.setAttribute("lineThickness", "1");
		root.setAttribute("showValues", "0");
		root.setAttribute("formatNumberScale", "0");
		root.setAttribute("anchorRadius", "2");
		root.setAttribute("divLineAlpha", "20");
		root.setAttribute("divLineColor", "CC3300");
		root.setAttribute("divLineIsDashed", "1");
		root.setAttribute("showAlternateHGridColor", "1");
		root.setAttribute("alternateHGridColor", "CC300");
		root.setAttribute("shadowAlpha", "40");
		root.setAttribute("labelStep", "2");
		root.setAttribute("numvdivlines", "5");
		root.setAttribute("chartRightMargin", "35");
		root.setAttribute("bgColor", "FFFFFF,CC3300");
		root.setAttribute("bgAngle", "270");
		root.setAttribute("bgAlpha", "10,10");
		//将根节点添加到Document对象中
		document.appendChild(root);
		/**categories*/
		//设置第一个元素到
		Element categoriesElement = document.createElement("categories");
		/**category*/
		//设置categories节点
		for(int i = 7; i >= 0; i--){
		Element categoryElement = document.createElement("category");
		//给category设置label属性
		categoryElement.setAttribute("label",df.format(new Date(date.getTime() - i * 60 * 60 * 1000)));
		//添加category节点到categories节点内
		categoriesElement.appendChild(categoryElement);
		}
		root.appendChild(categoriesElement);
		/**dataset.afrnic*/
		//设置dataset节点
		Element afrnicdatasetElement = document.createElement("dataset");
		afrnicdatasetElement.setAttribute("seriesName","AFRNIC");
		afrnicdatasetElement.setAttribute("color","1D8BD1");
		afrnicdatasetElement.setAttribute("anchorBorderColor","1D8BD1");
		afrnicdatasetElement.setAttribute("anchorBgColor","1D8BD1");
		for(int i = 0; i <= 7; i++){
			ReorderingDao dd = new ReorderingDao();
			List<NodeReorderingshow> nds = dd.getRecentEightHoursReordering();
			Element setElement = document.createElement("set");
			setElement.setAttribute("value", ""+nds.get(5*i).getReordering());
			afrnicdatasetElement.appendChild(setElement);
		}
		//添加dataset节点到root节点内
		root.appendChild(afrnicdatasetElement);
		/**dataset.apnic*/
		//设置dataset节点
		Element apnicdatasetElement = document.createElement("dataset");
		apnicdatasetElement.setAttribute("seriesName","APNIC");
		apnicdatasetElement.setAttribute("color","F1683C");
		apnicdatasetElement.setAttribute("anchorBorderColor","F1683C");
		apnicdatasetElement.setAttribute("anchorBgColor","F1683C");
		for(int i = 0; i <= 7; i++){
			ReorderingDao dd = new ReorderingDao();
			List<NodeReorderingshow> nds = dd.getRecentEightHoursReordering();
			Element setElement = document.createElement("set");
			setElement.setAttribute("value", ""+nds.get(5*i+1).getReordering());
			apnicdatasetElement.appendChild(setElement);
		}
		//添加dataset节点到root节点内
		root.appendChild(apnicdatasetElement);
		/**dataset.arin*/
		//设置dataset节点
		Element arindatasetElement = document.createElement("dataset");
		arindatasetElement.setAttribute("seriesName","ARIN");
		arindatasetElement.setAttribute("color","2AD62A");
		arindatasetElement.setAttribute("anchorBorderColor","2AD62A");
		arindatasetElement.setAttribute("anchorBgColor","2AD62A");
		for(int i = 0; i <= 7; i++){
			ReorderingDao dd = new ReorderingDao();
			List<NodeReorderingshow> nds = dd.getRecentEightHoursReordering();
			Element setElement = document.createElement("set");
			setElement.setAttribute("value", ""+nds.get(5*i+2).getReordering());
			arindatasetElement.appendChild(setElement);
		}
		//添加dataset节点到root节点内
		root.appendChild(arindatasetElement);
		/**dataset.lacnic*/
		//设置dataset节点
		Element lacnicdatasetElement = document.createElement("dataset");
		lacnicdatasetElement.setAttribute("seriesName","LACNIC");
		lacnicdatasetElement.setAttribute("color","DBDC25");
		lacnicdatasetElement.setAttribute("anchorBorderColor","DBDC25");
		lacnicdatasetElement.setAttribute("anchorBgColor","DBDC25");
		for(int i = 0; i <= 7; i++){
			ReorderingDao dd = new ReorderingDao();
			List<NodeReorderingshow> nds = dd.getRecentEightHoursReordering();
			Element setElement = document.createElement("set");
			setElement.setAttribute("value", ""+nds.get(5*i+3).getReordering());
			lacnicdatasetElement.appendChild(setElement);
		}
		//添加dataset节点到root节点内
		root.appendChild(lacnicdatasetElement);
		/**dataset.ripe*/
		//设置dataset节点
		Element ripedatasetElement = document.createElement("dataset");
		ripedatasetElement.setAttribute("seriesName","RIPE");
		ripedatasetElement.setAttribute("color","000000");
		ripedatasetElement.setAttribute("anchorBorderColor","000000");
		ripedatasetElement.setAttribute("anchorBgColor","000000");
		for(int i = 0; i <= 7; i++){
			ReorderingDao dd = new ReorderingDao();
			List<NodeReorderingshow> nds = dd.getRecentEightHoursReordering();
			Element setElement = document.createElement("set");
			setElement.setAttribute("value", ""+nds.get(5*i+4).getReordering());
			ripedatasetElement.appendChild(setElement);
		}
		//添加dataset节点到root节点内
		root.appendChild(ripedatasetElement);
		/**styles*/
		Element stylesElement = document.createElement("styles");
		Element definitionElement = document.createElement("definition");
		Element styleElement = document.createElement("style");
		styleElement.setAttribute("name", "CaptionFont");
		styleElement.setAttribute("type", "font");
		styleElement.setAttribute("size", "12");
		definitionElement.appendChild(styleElement);
		stylesElement.appendChild(definitionElement);
		Element applicationElement = document.createElement("application");
		Element applyElement = document.createElement("apply");
		applyElement.setAttribute("toObject", "CAPTION");
		applyElement.setAttribute("styles", "CaptionFont");
		applicationElement.appendChild(applyElement);
		Element apply2Element = document.createElement("apply");
		apply2Element.setAttribute("toObject", "SUBCAPTION");
		apply2Element.setAttribute("styles", "CaptionFont");
		applicationElement.appendChild(apply2Element);
		stylesElement.appendChild(applicationElement);
		root.appendChild(stylesElement);
		//开始把Document映射到文件
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer transFormer = transFactory.newTransformer();
		//设置输出结果
		DOMSource domSource = new DOMSource(document);
		//生成xml文件
		/*File file = new File("D:\\java_workspace\\new_neteye-web-v3\\WebContent\\flex\\data\\eighthoursloss.xml");
		//判断是否存在,如果不存在,则创建
		file.delete();
		if(!file.exists()){
				file.createNewFile();
				}
		//文件输出流
		FileOutputStream out = new FileOutputStream(file);
		//设置输入源
		StreamResult xmlResult = new StreamResult(out);
		//输出xml文件
		transFormer.transform(domSource, xmlResult);
		//测试文件输出的路径
		System.out.println(file.getAbsolutePath());*/
		StringWriter stringWriter = new StringWriter();  
        Result result = new StreamResult(stringWriter);   
        transFormer.setOutputProperty(OutputKeys.INDENT, "yes");  
        transFormer.transform(domSource, result);
        xml = stringWriter.getBuffer().toString();
        System.out.println("end");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;
	}
	public String onedayreorderingxml() {
		String xml = null;
		Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/HH");
        String onedayagodoc = df.format(new Date(date.getTime() - 23 * 60 * 60 * 1000));
        String doc = df.format(date);
		try {
		//解析器工厂类
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		//解析器
		DocumentBuilder builder = factory.newDocumentBuilder();
		//操作的Document对象
		Document document = builder.newDocument();
		//设置XML的版本
		document.setXmlVersion("1.0");
		//创建根节点
		Element root = document.createElement("chart");
		root.setAttribute("caption", "Reordering in recent one day");
		root.setAttribute("subcaption", "(From "+onedayagodoc+" to "+doc+")");
		root.setAttribute("lineThickness", "1");
		root.setAttribute("showValues", "0");
		root.setAttribute("formatNumberScale", "0");
		root.setAttribute("anchorRadius", "2");
		root.setAttribute("divLineAlpha", "20");
		root.setAttribute("divLineColor", "CC3300");
		root.setAttribute("divLineIsDashed", "1");
		root.setAttribute("showAlternateHGridColor", "1");
		root.setAttribute("alternateHGridColor", "CC300");
		root.setAttribute("shadowAlpha", "40");
		root.setAttribute("labelStep", "2");
		root.setAttribute("numvdivlines", "5");
		root.setAttribute("chartRightMargin", "35");
		root.setAttribute("bgColor", "FFFFFF,CC3300");
		root.setAttribute("bgAngle", "270");
		root.setAttribute("bgAlpha", "10,10");
		//将根节点添加到Document对象中
		document.appendChild(root);
		/**categories*/
		//设置第一个元素到
		Element categoriesElement = document.createElement("categories");
		/**category*/
		//设置categories节点
		for(int i = 23; i >= 0; i--){
		Element categoryElement = document.createElement("category");
		//给category设置label属性
		categoryElement.setAttribute("label",df.format(new Date(date.getTime() - i * 60 * 60 * 1000)));
		//添加category节点到categories节点内
		categoriesElement.appendChild(categoryElement);
		}
		root.appendChild(categoriesElement);
		/**dataset.afrnic*/
		//设置dataset节点
		Element afrnicdatasetElement = document.createElement("dataset");
		afrnicdatasetElement.setAttribute("seriesName","AFRNIC");
		afrnicdatasetElement.setAttribute("color","1D8BD1");
		afrnicdatasetElement.setAttribute("anchorBorderColor","1D8BD1");
		afrnicdatasetElement.setAttribute("anchorBgColor","1D8BD1");
		for(int i = 0; i < 24; i++){
			ReorderingDao dd = new ReorderingDao();
			List<NodeReorderingshow> nds = dd.getRecentOneDayReordering();
			Element setElement = document.createElement("set");
			setElement.setAttribute("value", ""+nds.get(5*i).getReordering());
			afrnicdatasetElement.appendChild(setElement);
		}
		//添加dataset节点到root节点内
		root.appendChild(afrnicdatasetElement);
		/**dataset.apnic*/
		//设置dataset节点
		Element apnicdatasetElement = document.createElement("dataset");
		apnicdatasetElement.setAttribute("seriesName","APNIC");
		apnicdatasetElement.setAttribute("color","F1683C");
		apnicdatasetElement.setAttribute("anchorBorderColor","F1683C");
		apnicdatasetElement.setAttribute("anchorBgColor","F1683C");
		for(int i = 0; i < 24; i++){
			ReorderingDao dd = new ReorderingDao();
			List<NodeReorderingshow> nds = dd.getRecentOneDayReordering();
			Element setElement = document.createElement("set");
			setElement.setAttribute("value", ""+nds.get(5*i+1).getReordering());
			apnicdatasetElement.appendChild(setElement);
		}
		//添加dataset节点到root节点内
		root.appendChild(apnicdatasetElement);
		/**dataset.arin*/
		//设置dataset节点
		Element arindatasetElement = document.createElement("dataset");
		arindatasetElement.setAttribute("seriesName","ARIN");
		arindatasetElement.setAttribute("color","2AD62A");
		arindatasetElement.setAttribute("anchorBorderColor","2AD62A");
		arindatasetElement.setAttribute("anchorBgColor","2AD62A");
		for(int i = 0; i < 24; i++){
			ReorderingDao dd = new ReorderingDao();
			List<NodeReorderingshow> nds = dd.getRecentOneDayReordering();
			Element setElement = document.createElement("set");
			setElement.setAttribute("value", ""+nds.get(5*i+2).getReordering());
			arindatasetElement.appendChild(setElement);
		}
		//添加dataset节点到root节点内
		root.appendChild(arindatasetElement);
		/**dataset.lacnic*/
		//设置dataset节点
		Element lacnicdatasetElement = document.createElement("dataset");
		lacnicdatasetElement.setAttribute("seriesName","LACNIC");
		lacnicdatasetElement.setAttribute("color","DBDC25");
		lacnicdatasetElement.setAttribute("anchorBorderColor","DBDC25");
		lacnicdatasetElement.setAttribute("anchorBgColor","DBDC25");
		for(int i = 0; i < 24; i++){
			ReorderingDao dd = new ReorderingDao();
			List<NodeReorderingshow> nds = dd.getRecentOneDayReordering();
			Element setElement = document.createElement("set");
			setElement.setAttribute("value", ""+nds.get(5*i+3).getReordering());
			lacnicdatasetElement.appendChild(setElement);
		}
		//添加dataset节点到root节点内
		root.appendChild(lacnicdatasetElement);
		/**dataset.ripe*/
		//设置dataset节点
		Element ripedatasetElement = document.createElement("dataset");
		ripedatasetElement.setAttribute("seriesName","RIPE");
		ripedatasetElement.setAttribute("color","000000");
		ripedatasetElement.setAttribute("anchorBorderColor","000000");
		ripedatasetElement.setAttribute("anchorBgColor","000000");
		for(int i = 0; i < 24; i++){
			ReorderingDao dd = new ReorderingDao();
			List<NodeReorderingshow> nds = dd.getRecentOneDayReordering();
			Element setElement = document.createElement("set");
			setElement.setAttribute("value", ""+nds.get(5*i+4).getReordering());
			ripedatasetElement.appendChild(setElement);
		}
		//添加dataset节点到root节点内
		root.appendChild(ripedatasetElement);
		/**styles*/
		Element stylesElement = document.createElement("styles");
		Element definitionElement = document.createElement("definition");
		Element styleElement = document.createElement("style");
		styleElement.setAttribute("name", "CaptionFont");
		styleElement.setAttribute("type", "font");
		styleElement.setAttribute("size", "12");
		definitionElement.appendChild(styleElement);
		stylesElement.appendChild(definitionElement);
		Element applicationElement = document.createElement("application");
		Element applyElement = document.createElement("apply");
		applyElement.setAttribute("toObject", "CAPTION");
		applyElement.setAttribute("styles", "CaptionFont");
		applicationElement.appendChild(applyElement);
		Element apply2Element = document.createElement("apply");
		apply2Element.setAttribute("toObject", "SUBCAPTION");
		apply2Element.setAttribute("styles", "CaptionFont");
		applicationElement.appendChild(apply2Element);
		stylesElement.appendChild(applicationElement);
		root.appendChild(stylesElement);
		//开始把Document映射到文件
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer transFormer = transFactory.newTransformer();
		//设置输出结果
		DOMSource domSource = new DOMSource(document);
		//生成xml文件
		/*File file = new File("D:\\java_workspace\\new_neteye-web-v3\\WebContent\\flex\\data\\eighthoursloss.xml");
		//判断是否存在,如果不存在,则创建
		file.delete();
		if(!file.exists()){
				file.createNewFile();
				}
		//文件输出流
		FileOutputStream out = new FileOutputStream(file);
		//设置输入源
		StreamResult xmlResult = new StreamResult(out);
		//输出xml文件
		transFormer.transform(domSource, xmlResult);
		//测试文件输出的路径
		System.out.println(file.getAbsolutePath());*/
		StringWriter stringWriter = new StringWriter();  
        Result result = new StreamResult(stringWriter);   
        transFormer.setOutputProperty(OutputKeys.INDENT, "yes");  
        transFormer.transform(domSource, result);
        xml = stringWriter.getBuffer().toString();
        System.out.println("end");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;
	}
	public String oneweekreorderingxml() {
		String xml = null;
		Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd");
        String onedayagodoc = df.format(new Date(date.getTime() - 7 * 24 * 60 * 60 * 1000));
        String doc = df.format(date);
		try {
		//解析器工厂类
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		//解析器
		DocumentBuilder builder = factory.newDocumentBuilder();
		//操作的Document对象
		Document document = builder.newDocument();
		//设置XML的版本
		document.setXmlVersion("1.0");
		//创建根节点
		Element root = document.createElement("chart");
		root.setAttribute("caption", "Reordering in recent one week");
		root.setAttribute("subcaption", "(From "+onedayagodoc+" to "+doc+")");
		root.setAttribute("lineThickness", "1");
		root.setAttribute("showValues", "0");
		root.setAttribute("formatNumberScale", "0");
		root.setAttribute("anchorRadius", "2");
		root.setAttribute("divLineAlpha", "20");
		root.setAttribute("divLineColor", "CC3300");
		root.setAttribute("divLineIsDashed", "1");
		root.setAttribute("showAlternateHGridColor", "1");
		root.setAttribute("alternateHGridColor", "CC300");
		root.setAttribute("shadowAlpha", "40");
		root.setAttribute("labelStep", "2");
		root.setAttribute("numvdivlines", "5");
		root.setAttribute("chartRightMargin", "35");
		root.setAttribute("bgColor", "FFFFFF,CC3300");
		root.setAttribute("bgAngle", "270");
		root.setAttribute("bgAlpha", "10,10");
		//将根节点添加到Document对象中
		document.appendChild(root);
		/**categories*/
		//设置第一个元素到
		Element categoriesElement = document.createElement("categories");
		/**category*/
		//设置categories节点
		for(int i = 7; i > 0; i--){
		Element categoryElement = document.createElement("category");
		//给category设置label属性
		categoryElement.setAttribute("label",df.format(new Date(date.getTime() - i * 24 * 60 * 60 * 1000)));
		//添加category节点到categories节点内
		categoriesElement.appendChild(categoryElement);
		}
		root.appendChild(categoriesElement);
		/**dataset.afrnic*/
		//设置dataset节点
		Element afrnicdatasetElement = document.createElement("dataset");
		afrnicdatasetElement.setAttribute("seriesName","AFRNIC");
		afrnicdatasetElement.setAttribute("color","1D8BD1");
		afrnicdatasetElement.setAttribute("anchorBorderColor","1D8BD1");
		afrnicdatasetElement.setAttribute("anchorBgColor","1D8BD1");
		for(int i = 0; i < 7; i++){
			ReorderingDao dd = new ReorderingDao();
			List<NodeReorderingshow> nds = dd.getRecentOneWeekReordering();
			Element setElement = document.createElement("set");
			setElement.setAttribute("value", ""+nds.get(5*i).getReordering());
			afrnicdatasetElement.appendChild(setElement);
		}
		//添加dataset节点到root节点内
		root.appendChild(afrnicdatasetElement);
		/**dataset.apnic*/
		//设置dataset节点
		Element apnicdatasetElement = document.createElement("dataset");
		apnicdatasetElement.setAttribute("seriesName","APNIC");
		apnicdatasetElement.setAttribute("color","F1683C");
		apnicdatasetElement.setAttribute("anchorBorderColor","F1683C");
		apnicdatasetElement.setAttribute("anchorBgColor","F1683C");
		for(int i = 0; i < 7; i++){
			ReorderingDao dd = new ReorderingDao();
			List<NodeReorderingshow> nds = dd.getRecentOneWeekReordering();
			Element setElement = document.createElement("set");
			setElement.setAttribute("value", ""+nds.get(5*i+1).getReordering());
			apnicdatasetElement.appendChild(setElement);
		}
		//添加dataset节点到root节点内
		root.appendChild(apnicdatasetElement);
		/**dataset.arin*/
		//设置dataset节点
		Element arindatasetElement = document.createElement("dataset");
		arindatasetElement.setAttribute("seriesName","ARIN");
		arindatasetElement.setAttribute("color","2AD62A");
		arindatasetElement.setAttribute("anchorBorderColor","2AD62A");
		arindatasetElement.setAttribute("anchorBgColor","2AD62A");
		for(int i = 0; i < 7; i++){
			ReorderingDao dd = new ReorderingDao();
			List<NodeReorderingshow> nds = dd.getRecentOneWeekReordering();
			Element setElement = document.createElement("set");
			setElement.setAttribute("value", ""+nds.get(5*i+2).getReordering());
			arindatasetElement.appendChild(setElement);
		}
		//添加dataset节点到root节点内
		root.appendChild(arindatasetElement);
		/**dataset.lacnic*/
		//设置dataset节点
		Element lacnicdatasetElement = document.createElement("dataset");
		lacnicdatasetElement.setAttribute("seriesName","LACNIC");
		lacnicdatasetElement.setAttribute("color","DBDC25");
		lacnicdatasetElement.setAttribute("anchorBorderColor","DBDC25");
		lacnicdatasetElement.setAttribute("anchorBgColor","DBDC25");
		for(int i = 0; i < 7; i++){
			ReorderingDao dd = new ReorderingDao();
			List<NodeReorderingshow> nds = dd.getRecentOneWeekReordering();
			Element setElement = document.createElement("set");
			setElement.setAttribute("value", ""+nds.get(5*i+3).getReordering());
			lacnicdatasetElement.appendChild(setElement);
		}
		//添加dataset节点到root节点内
		root.appendChild(lacnicdatasetElement);
		/**dataset.ripe*/
		//设置dataset节点
		Element ripedatasetElement = document.createElement("dataset");
		ripedatasetElement.setAttribute("seriesName","RIPE");
		ripedatasetElement.setAttribute("color","000000");
		ripedatasetElement.setAttribute("anchorBorderColor","000000");
		ripedatasetElement.setAttribute("anchorBgColor","000000");
		for(int i = 0; i < 7; i++){
			ReorderingDao dd = new ReorderingDao();
			List<NodeReorderingshow> nds = dd.getRecentOneWeekReordering();
			Element setElement = document.createElement("set");
			setElement.setAttribute("value", ""+nds.get(5*i+4).getReordering());
			ripedatasetElement.appendChild(setElement);
		}
		//添加dataset节点到root节点内
		root.appendChild(ripedatasetElement);
		/**styles*/
		Element stylesElement = document.createElement("styles");
		Element definitionElement = document.createElement("definition");
		Element styleElement = document.createElement("style");
		styleElement.setAttribute("name", "CaptionFont");
		styleElement.setAttribute("type", "font");
		styleElement.setAttribute("size", "12");
		definitionElement.appendChild(styleElement);
		stylesElement.appendChild(definitionElement);
		Element applicationElement = document.createElement("application");
		Element applyElement = document.createElement("apply");
		applyElement.setAttribute("toObject", "CAPTION");
		applyElement.setAttribute("styles", "CaptionFont");
		applicationElement.appendChild(applyElement);
		Element apply2Element = document.createElement("apply");
		apply2Element.setAttribute("toObject", "SUBCAPTION");
		apply2Element.setAttribute("styles", "CaptionFont");
		applicationElement.appendChild(apply2Element);
		stylesElement.appendChild(applicationElement);
		root.appendChild(stylesElement);
		//开始把Document映射到文件
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer transFormer = transFactory.newTransformer();
		//设置输出结果
		DOMSource domSource = new DOMSource(document);
		//生成xml文件
		/*File file = new File("D:\\java_workspace\\new_neteye-web-v3\\WebContent\\flex\\data\\eighthoursloss.xml");
		//判断是否存在,如果不存在,则创建
		file.delete();
		if(!file.exists()){
				file.createNewFile();
				}
		//文件输出流
		FileOutputStream out = new FileOutputStream(file);
		//设置输入源
		StreamResult xmlResult = new StreamResult(out);
		//输出xml文件
		transFormer.transform(domSource, xmlResult);
		//测试文件输出的路径
		System.out.println(file.getAbsolutePath());*/
		StringWriter stringWriter = new StringWriter();  
        Result result = new StreamResult(stringWriter);   
        transFormer.setOutputProperty(OutputKeys.INDENT, "yes");  
        transFormer.transform(domSource, result);
        xml = stringWriter.getBuffer().toString();
        System.out.println("end");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;
	}
	public String allreorderingxml() {
		ReorderingXmlAction d = new ReorderingXmlAction();
		XMLOb a = new XMLOb();
		a.setEighthoursxmlName(d.eighthoursreorderingxml());
		System.out.println("11");
		a.setOnedayxmlName(d.onedayreorderingxml());
		System.out.println("22");
		a.setOneweekxmlName(d.onedayreorderingxml());
		System.out.println("33");
		allXML.add(a);
		return SUCCESS;
	}
	/*public static void main(String[] args){
		new ReorderingXmlAction().eighthoursreorderingxml();
		new ReorderingXmlAction().onedayreorderingxml();
		new ReorderingXmlAction().oneweekreorderingxml();
	}*/
}