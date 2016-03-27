package com.analysis.bpo;

import java.awt.Color;
import java.awt.GradientPaint;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.ser.JAFDataHandlerDeserializerFactory;
import org.apache.axis.encoding.ser.JAFDataHandlerSerializerFactory;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPosition;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.CategoryLabelWidthType;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.text.TextBlockAnchor;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

import com.analysis.dto.TopNData;
import com.analysis.dto.TopNTotalData;
import com.base.util.Constants;
import com.base.util.RoundTool;
import java.io.FileNotFoundException; 
import java.io.FileReader; 
import java.io.IOException; 

public class AnalysisBPO {
	static Logger logger = Logger.getLogger(AnalysisBPO.class.getName());
	public void category3DImage(CategoryDataset dataset,String fileName,String type,String valueType,String titleName) throws Exception{
		String title = titleName+type+"("+valueType+")";
		 JFreeChart chart = ChartFactory.createBarChart3D(
		            "",      // chart title
		            "",               // domain axis label
		            "",                  // range axis label
		            dataset,                  // data
		            PlotOrientation.HORIZONTAL, // orientation
		            false,                     // include legend
		            true,                     // tooltips
		            false                     // urls
		        );
		 
		        CategoryPlot plot = chart.getCategoryPlot();
		        plot.setForegroundAlpha(1.0f);
		        CategoryAxis axis = plot.getDomainAxis();
		        CategoryLabelPositions p = axis.getCategoryLabelPositions();
		        
		        CategoryLabelPosition left = new CategoryLabelPosition(
			            RectangleAnchor.LEFT, TextBlockAnchor.CENTER_LEFT, 
			            TextAnchor.CENTER_LEFT, 0.0,
			            CategoryLabelWidthType.RANGE, 0.30f
			        );
			    axis.setCategoryLabelPositions(CategoryLabelPositions.replaceLeftPosition(p, left));
			    BarRenderer renderer = (BarRenderer) plot.getRenderer();
			    GradientPaint gp1 = new GradientPaint(
			            0.0f, 0.0f, Color.blue, 
			            0.0f, 0.0f, Color.lightGray
			        );
			    renderer.setPaint(gp1);
			    
		        String dir = Constants.webRealPath+"file/analysis/downLoadFile/pic/";
		        
		        FileOutputStream fos = new FileOutputStream(dir+fileName);
			   	ChartUtilities.writeChartAsJPEG(
			   			 fos, 
			   			 1, 
			   			 chart, 
			   			 600, 
			   			 470,
			   			 null 
			   	 );
				fos.close();
	}
	public List<TopNTotalData> createResult(String element,String file,String valueType) throws Exception{
		List<TopNTotalData> totalResult = new ArrayList<TopNTotalData>();
		TopNTotalData totalData = new TopNTotalData();
		List<TopNData> inputResult =new ArrayList<TopNData>();	
		SAXReader reader = new SAXReader();
		Document document = reader.read(file);
		Element root = document.getRootElement();
		//String[] elements = {"topntraffic","topnpacket","topnport","topnprotocol"};
		List<Element> results = root.element(element).elements();
		for (Element result : results) {
			//DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			String type = result.getName();
			List<Element> trafficalltopns = result.elements();
			for (Element trafficalltopn : trafficalltopns) {
				TopNData data = new TopNData();
				data.setTypeName(type);
				data.setUserName(trafficalltopn.elementText("username"));
				double temp = 0d;
				if(element.equals("topnpacket")){
					temp = (double)(Long.parseLong(trafficalltopn.elementText("value")));
				}else{
					temp = ((double)(Long.parseLong(trafficalltopn.elementText("value")))/(1024*1024));
				}
				//保留小数点后两位
				double newValue = RoundTool.round(temp,3,BigDecimal.ROUND_HALF_UP);
				data.setUserValue(newValue);
				//dataset.addValue(newValue, valueType, trafficalltopn.elementText("username"));
				inputResult.add(data);
			}
			//生成图形
			//category3DImage(dataset,type+".jpg",type,valueType,"用户");
			totalData.setResultData(inputResult);//加入列表结果集
			totalData.setPicName(type+".jpg");//加入结果图片
			totalResult.add(totalData);
		}//Endof for
		return totalResult;
	}
	//topN历史流量调用方法
	public List<TopNTotalData> createHistoryResult(String element,String file,String valueType) throws Exception{
		//file = "d:/topNHistory.xml";
		List<TopNTotalData> totalResult = new ArrayList<TopNTotalData>();
		TopNTotalData totalData = new TopNTotalData();
		List<TopNData> inputResult =new ArrayList<TopNData>();	
		SAXReader reader = new SAXReader();
		Document document = null;
		document = reader.read(file);
		Element root = document.getRootElement();
		List<Element> results = root.element(element).elements();
		for (Element result : results) {
			//DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			String type = result.getName();
			List<Element> trafficalltopns = result.elements();
			for (Element trafficalltopn : trafficalltopns) {
				TopNData data = new TopNData();
				data.setTypeName(type);
				data.setUserName(trafficalltopn.elementText("username"));
				double temp = 0d;
				if(element.equals("topnpacket")){
					temp = ((double)(Long.parseLong(trafficalltopn.elementText("value")))/(1024*1024));
				}else{
					temp = ((double)(Long.parseLong(trafficalltopn.elementText("value")))/(1024*1024*1024));
				}
				//保留小数点后两位
				double newValue = RoundTool.round(temp,5,BigDecimal.ROUND_HALF_UP);
				data.setUserValue(newValue);
				//dataset.addValue(newValue, valueType, trafficalltopn.elementText("username"));
				inputResult.add(data);
			}
			//生成图形
			//category3DImage(dataset,"History"+type+".jpg",type,valueType,"用户");
			totalData.setResultData(inputResult);//加入列表结果集
			totalData.setPicName("History"+type+".jpg");//加入结果图片
			totalResult.add(totalData);
		}//Endof for
		return totalResult;
	}
	public void getFile(File dir,String fileName){
		//从配置文件读取web servcie的URL或者从发布的服务列表中读取（待定）		
		String endpoint = "http://"+Constants.ANALYSIS_IP+":"+Constants.ANALYSIS_PORT+"/axis/services/topNService";
		InputStreamReader ins = null;
		BufferedReader br = null;
		FileWriter fw = null;	
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();
			DataHandler dh = new DataHandler(new FileDataSource(fileName));
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			call.setOperationName(new QName(endpoint, "getTopNInfo"));
			QName qnameattachment = new QName("TopNService", "DataHandler");
			call.registerTypeMapping(dh.getClass(), qnameattachment,
			JAFDataHandlerSerializerFactory.class,
			JAFDataHandlerDeserializerFactory.class);
			call.addParameter("s1", qnameattachment, ParameterMode.IN);
			call.setReturnType(new QName("TopNService", "DataHandler"),DataHandler.class);
			DataHandler ret = (DataHandler) call.invoke(new Object[] { fileName });
			ins = new InputStreamReader(ret.getInputStream());
			br = new BufferedReader(ins);
			if(!dir.exists()){
			dir.mkdir(); 
				logger.debug("下载文件存放目录不存在，创建 【"+dir.getAbsolutePath()+" 】目录");
			}
			
			File f = new File(dir,fileName);
			//如果该文件存在，先将其删除
			if(f.exists()){
				f.delete();
			}
			fw = new FileWriter(f);
			String tmp = br.readLine();
			while(tmp!=null && !tmp.equals("")){
				fw.write(tmp+"\n");
				tmp = br.readLine();
			}
			fw.close();
		} catch (Exception e) {
			logger.error("调用文件下载Web服务出错：" + e.getMessage());
		}finally{
			try{
				br.close();
				ins.close();
				fw.close();
			}catch(Exception ee){
	
			}
		}
	}
	//此方法不是用户历史的web service，需要修改
	public void getHistoryFile(File dir,String fileName){
		//从配置文件读取web servcie的URL或者从发布的服务列表中读取（待定）		
		String endpoint = "http://"+Constants.ANALYSIS_IP+":"+Constants.ANALYSIS_PORT+"/axis/services/topNCombineService";
		InputStreamReader ins = null;
		BufferedReader br = null;
		FileWriter fw = null;	
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();
			DataHandler dh = new DataHandler(new FileDataSource(fileName));
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			call.setOperationName(new QName(endpoint, "getTopNCombine"));
			QName qnameattachment = new QName("topNCombineService", "DataHandler");
			call.registerTypeMapping(dh.getClass(), qnameattachment,
			JAFDataHandlerSerializerFactory.class,
			JAFDataHandlerDeserializerFactory.class);
			call.addParameter("s1", qnameattachment, ParameterMode.IN);
			call.setReturnType(new QName("topNCombineService", "DataHandler"),DataHandler.class);
			DataHandler ret = (DataHandler) call.invoke(new Object[] { fileName });
			ins = new InputStreamReader(ret.getInputStream());
			br = new BufferedReader(ins);
			if(!dir.exists()){
			dir.mkdir(); 
				logger.debug("下载文件存放目录不存在，创建 【"+dir.getAbsolutePath()+" 】目录");
			}
			File f = new File(dir,fileName);
			if(f.exists()){
				f.delete();
			}
			fw = new FileWriter(f);
			String tmp = br.readLine();
			while(tmp!=null  && !tmp.equals("")){
				fw.write(tmp+"\n");
				tmp = br.readLine();
			}
			fw.close();
		} catch (Exception e) {
			logger.error("调用文件下载Web服务出错：" + e.getMessage());
		}finally{
			try{
				br.close();
				ins.close();
				fw.close();
			}catch(Exception ee){
	
			}
		}
	}
	//此web service的发布有错误，xml文件没有结束标准，目前用fw.write("</topn>")补齐
	public void getTotalFile(File dir,String fileName){
		//从配置文件读取web servcie的URL或者从发布的服务列表中读取（待定）		
		String endpoint = "http://"+Constants.ANALYSIS_IP+":"+Constants.ANALYSIS_PORT+"/axis/services/topNTotalService";
		InputStreamReader ins = null;
		BufferedReader br = null;
		FileWriter fw = null;	
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();
			DataHandler dh = new DataHandler(new FileDataSource(fileName));
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			call.setOperationName(new QName(endpoint, "getTopNTotalInfo"));
			QName qnameattachment = new QName("TopTotalNService", "DataHandler");
			call.registerTypeMapping(dh.getClass(), qnameattachment,
			JAFDataHandlerSerializerFactory.class,
			JAFDataHandlerDeserializerFactory.class);
			call.addParameter("s1", qnameattachment, ParameterMode.IN);
			call.setReturnType(new QName("TopTotalNService", "DataHandler"),DataHandler.class);
			DataHandler ret = (DataHandler) call.invoke(new Object[] { fileName });
			ins = new InputStreamReader(ret.getInputStream());
			br = new BufferedReader(ins);
			if(!dir.exists()){
			dir.mkdir(); 
				logger.debug("下载文件存放目录不存在，创建 【"+dir.getAbsolutePath()+" 】目录");
			}
			File f = new File(dir,fileName);
			if(f.exists()){
				f.delete();
			}
			fw = new FileWriter(f);
			String tmp = br.readLine();
			while(tmp!=null  && !tmp.equals("")){
				fw.write(tmp+"\n");
				tmp = br.readLine();
			}
			//fw.write("</topn>");
			fw.close();
		} catch (Exception e) {
			logger.error("调用文件下载Web服务出错：" + e.getMessage());
		}finally{
			try{
				br.close();
				ins.close();
				fw.close();
			}catch(Exception ee){
	
			}
		}
	}
	//此方法需要修改，并不是真正的历史整体网络流量service方法
	public void getTotalHistoryFile(File dir,String fileName){
		//从配置文件读取web servcie的URL或者从发布的服务列表中读取（待定）		
		String endpoint = "http://"+Constants.ANALYSIS_IP+":"+Constants.ANALYSIS_PORT+"/axis/services/topNTotalCombineService";
		InputStreamReader ins = null;
		BufferedReader br = null;
		FileWriter fw = null;	
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();
			DataHandler dh = new DataHandler(new FileDataSource(fileName));
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			call.setOperationName(new QName(endpoint, "getTopNTotalCombine"));
			QName qnameattachment = new QName("topNTotalCombineService", "DataHandler");
			call.registerTypeMapping(dh.getClass(), qnameattachment,
			JAFDataHandlerSerializerFactory.class,
			JAFDataHandlerDeserializerFactory.class);
			call.addParameter("s1", qnameattachment, ParameterMode.IN);
			call.setReturnType(new QName("topNTotalCombineService", "DataHandler"),DataHandler.class);
			DataHandler ret = (DataHandler) call.invoke(new Object[] { fileName });
			ins = new InputStreamReader(ret.getInputStream());
			br = new BufferedReader(ins);
			if(!dir.exists()){
			dir.mkdir(); 
				logger.debug("下载文件存放目录不存在，创建 【"+dir.getAbsolutePath()+" 】目录");
			}
			File f = new File(dir,fileName);
			if(f.exists()){
				f.delete();
			}
			fw = new FileWriter(f);
			String tmp = br.readLine();
			while(tmp!=null  && !tmp.equals("")){
				fw.write(tmp+"\n");
				tmp = br.readLine();
			}
			//fw.write("</topn>");
			fw.close();
		} catch (Exception e) {
			logger.error("调用文件下载Web服务出错：" + e.getMessage());
		}finally{
			try{
				br.close();
				ins.close();
				fw.close();
			}catch(Exception ee){
	
			}
		}
	}
	//TopN整体流量调用方法
	public List<TopNTotalData> createTotalResult(String element,String file,String valueType,String resultType1,String resultType2) throws Exception{
		List<TopNTotalData> totalResult = new ArrayList<TopNTotalData>();
		TopNTotalData totalData = new TopNTotalData();
		List<TopNData> inputResult =new ArrayList<TopNData>();	
		SAXReader reader = new SAXReader();
		Document document = null;
		document = reader.read(file);
		Element root = document.getRootElement();
		List<Element> results = root.element(element).elements();
		//DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Element result : results) {			
			TopNData data = new TopNData();
			data.setTypeName(element);
			if(element.equals("protocolstat")){//仅在protocolstat中修改
				FileReader read = new FileReader(Constants.webRealPath+"file/analysis/downLoadFile/protocol.txt"); 
				//读取特定目录下的文件
				BufferedReader br = new BufferedReader(read); 
				  String row=null; 
				  for(row = br.readLine();row!=null;row=br.readLine()) //一行一行的读 
				  if(row.startsWith(result.elementText(resultType1))) {//进行匹配
						data.setUserName(row);//设置名称
						break;
	                   }
	
				  else if(Integer.parseInt(result.elementText(resultType1))>=138&&Integer.parseInt(result.elementText(resultType1))<=254){
					    data.setUserName(result.elementText(resultType1)+"/未分配");
					    break;
				  }//有待修改
			}
			else{
				data.setUserName(result.elementText(resultType1));
			} 
			double 	temp = 0d;
			if(element.equals("portstat") || element.equals("protocolstat")){
				temp=((double)(Long.valueOf(result.elementText(resultType2)))/(1024*1024*1024));
			}else if(element.equals("pktlen") || element.equals("ttl")){
				temp=((double)(Long.valueOf(result.elementText(resultType2)))/(1024));
			}else{
				temp=(double)Long.valueOf(result.elementText(resultType2));
			}
			//保留小数点后两位
			double newValue = RoundTool.round(temp,3,BigDecimal.ROUND_HALF_UP);
			data.setUserValue(newValue);
			//dataset.addValue(newValue, valueType, result.elementText(resultType1));
			inputResult.add(data);			
		}
		//category3DImage(dataset,"Total"+element+".jpg",element,valueType,"网络整体");
		totalData.setResultData(inputResult);//加入列表结果集
		totalData.setPicName("Total"+element+".jpg");//加入结果图片
		totalResult.add(totalData);
		return totalResult;
	}
	public List<TopNTotalData> createTotalHistoryResult(String element,String file,String valueType,String resultType1,String resultType2) throws Exception{
		List<TopNTotalData> totalResult = new ArrayList<TopNTotalData>();
		TopNTotalData totalData = new TopNTotalData();
		List<TopNData> inputResult =new ArrayList<TopNData>();	
		SAXReader reader = new SAXReader();
		Document document = null;
		document = reader.read(file);
		Element root = document.getRootElement();
		List<Element> results = root.element(element).elements();
		//DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Element result : results) {			
			TopNData data = new TopNData();
			data.setTypeName(element);
			if(element.equals("protocolstat")){//仅在protocolstat中修改
				FileReader read = new FileReader(Constants.webRealPath+"file/analysis/downLoadFile/protocol.txt"); 
				//读取特定目录下的文件
				BufferedReader br = new BufferedReader(read); 
				  String row=null; 
				  for(row = br.readLine();row!=null;row=br.readLine()) //一行一行的读 
				  if(row.startsWith(result.elementText(resultType1))) {//进行匹配
						data.setUserName(row);//设置名称
						break;
	                   }
				  else if(Integer.parseInt(result.elementText(resultType1))>=138&&Integer.parseInt(result.elementText(resultType1))<=254){
					    data.setUserName(result.elementText(resultType1)+"/未分配");
					    break;
				  }//有待修改
			}
			else{
				data.setUserName(result.elementText(resultType1));
			} 
			double 	temp = 0d;
			if(element.equals("portstat") || element.equals("protocolstat")){
				temp=((double)(Long.valueOf(result.elementText(resultType2)))/(1024*1024*1024));
			}else if(element.equals("pktlen") || element.equals("ttl")){
				temp=((double)(Long.valueOf(result.elementText(resultType2)))/(1024*1024));
			}else{
				temp=((double)(Long.valueOf(result.elementText(resultType2)))/(1024));
			}
			//保留小数点后两位
			double newValue = RoundTool.round(temp,3,BigDecimal.ROUND_HALF_UP);
			data.setUserValue(newValue);
			//dataset.addValue(newValue, valueType, result.elementText(resultType1));
			inputResult.add(data);			
		}//Endof for
		//生成图形
		//category3DImage(dataset,"History"+element+".jpg",element,valueType,"历史网络整体");
		totalData.setResultData(inputResult);//加入列表结果集
		totalData.setPicName("History"+element+".jpg");//加入结果图片
		totalResult.add(totalData);
		return totalResult;
	}
}
