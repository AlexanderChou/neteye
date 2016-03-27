package com.netflow.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
import org.jfree.data.category.DefaultCategoryDataset;

import com.base.util.Constants;
import com.netflow.bpo.NetFlowBPO;
import com.netflow.dto.SessionData;
import com.netflow.dto.TopNData;
import com.netflow.dto.TopNTotalData;
import com.opensymphony.xwork2.ActionSupport;


public class TopNInfoAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(TopNInfoAction.class.getName());
	private List<TopNTotalData> totalResult = new ArrayList<TopNTotalData>();
	private List<TopNData> inputresult =new ArrayList<TopNData>();    
	

	public String execute() throws Exception{
		String fileName = "topN.xml";
		//从配置文件读取web servcie的URL或者从发布的服务列表中读取（待定）
		
		String endpoint = "http://"+Constants.NETFLOW_IP+":"+Constants.NETFLOW_PORT+"/axis/services/topNService";
		//String endpoint = "http://[2001:da8:ff3a:c897:f00::]:8080/axis/services/topNService";
		
		InputStreamReader ins = null;
		BufferedReader br = null;
		FileWriter fw = null;	
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();
			DataHandler dh = new DataHandler(new FileDataSource(fileName));
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			call.setOperationName(new QName(endpoint, "getTopNInfo"));
			QName qnameattachment = new QName("topNService", "DataHandler");
			call.registerTypeMapping(dh.getClass(), qnameattachment,
			JAFDataHandlerSerializerFactory.class,
			JAFDataHandlerDeserializerFactory.class);
			call.addParameter("s1", qnameattachment, ParameterMode.IN);
			call.setReturnType(new QName("topNService", "DataHandler"),DataHandler.class);
			DataHandler ret = (DataHandler) call.invoke(new Object[] { fileName });
			ins = new InputStreamReader(ret.getInputStream());
			br = new BufferedReader(ins);
			
			File dir=new File(Constants.webRealPath+"file/netflow/downLoadFile/");
//			File dir=new File("d:/");
			
			
			if(!dir.exists()){
			dir.mkdir(); 
				logger.debug("下载文件存放目录不存在，创建 【"+dir.getAbsolutePath()+" 】目录");
			}
			File f = new File(dir,fileName);
			fw = new FileWriter(f);
			String tmp = br.readLine();
			while(tmp!=null){
				fw.write(tmp+"\n");
				tmp = br.readLine();
			}
			fw.close();

			NetFlowBPO bpo = new NetFlowBPO();
			TopNTotalData totalData = new TopNTotalData();
			List<TopNData> inputResult =new ArrayList<TopNData>();		
			SAXReader reader = new SAXReader();
			Document document = null;
			document = reader.read(dir+File.separator+fileName);
			Element root = document.getRootElement();
			List<Element> results = root.element("results").elements();
			for (Element result : results) {
				//生成图片（生成柱状图）
				DefaultCategoryDataset dataset = new DefaultCategoryDataset();
				String type = result.elementText("type");
				List<Element> packinfos = result.element("packinfos").elements();
				if(type.equals("protocol")||type.equals("srcport")||type.equals("dstport")){
					for (Element packinfo : packinfos) {
						TopNData data = new TopNData();
						data.setTypename(type);
						data.setKey(packinfo.elementText("key"));
						data.setBytes(packinfo.elementText("bytes"));
						data.setPkts(packinfo.elementText("pkts"));
						dataset.addValue(Integer.parseInt(packinfo.elementText("bytes")), "tytes", packinfo.elementText("key"));
						dataset.addValue(Integer.parseInt(packinfo.elementText("pkts")), "pkts", packinfo.elementText("key"));
						inputResult.add(data);
						
					}
				}
				else if(type.equals("session")){
					for (Element packinfo : packinfos) {
						SessionData data = new SessionData();
						data.setBytes(packinfo.elementText("bytes"));
						data.setTypename(type);
						data.setPkts(packinfo.elementText("pkts"));
						data.setSrcip(packinfo.elementText("srcip"));
						data.setDstip(packinfo.elementText("dstip"));
						data.setSrcport(packinfo.elementText("srcport"));
						data.setDstport(packinfo.elementText("dstport"));
						data.setProtocol(packinfo.elementText("protocol"));
						data.setStarttime(packinfo.elementText("starttime"));
						data.setDuration(packinfo.elementText("duration"));
						//data.setFlows(packinfo.elementText("flows"));
						String icon = packinfo.elementText("srcip")+"_"+packinfo.elementText("srcport")+" "+packinfo.elementText("dstip")+"_"+packinfo.elementText("dstport")
						            + packinfo.elementText("protocol")+" "+packinfo.elementText("starttime");
						dataset.addValue(Integer.parseInt(packinfo.elementText("bytes")), "tytes", icon);
						dataset.addValue(Integer.parseInt(packinfo.elementText("pkts")), "pkts", icon);
						//dataset.addValue(Integer.parseInt(packinfo.elementText("flows")), "flow", icon);
						dataset.addValue(Integer.parseInt(packinfo.elementText("duration")), "duration", icon);
						inputResult.add(data);
					}
				}
				bpo.category3DImage(dataset,type+".jpg",type);
				totalData.setResultData(inputResult);//加入列表结果集
				totalData.setPicName(type+".jpg");//加入结果图片
				totalResult.add(totalData);
			}
		
		
			
			//parseXML(dir+File.separator+fileName);//解析文件，用于页面显示
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
		return SUCCESS;
	}
	
	public String parseXML() throws Exception{
		NetFlowBPO bpo = new NetFlowBPO();
		TopNTotalData totalData = new TopNTotalData();
		List<TopNData> inputResult =new ArrayList<TopNData>();		
		SAXReader reader = new SAXReader();
		Document document = null;
		document = reader.read("d:/topN.xml");
		Element root = document.getRootElement();
		List<Element> results = root.element("results").elements();
		for (Element result : results) {
			//生成图片（生成柱状图）
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			String type = result.elementText("type");
			List<Element> packinfos = result.element("packinfos").elements();
			if(type.equals("protocol")||type.equals("srcport")||type.equals("dstport")){
				for (Element packinfo : packinfos) {
					TopNData data = new TopNData();
					data.setTypename(type);
					data.setKey(packinfo.elementText("key"));
					data.setBytes(packinfo.elementText("bytes"));
					data.setPkts(packinfo.elementText("pkts"));
					dataset.addValue(Integer.parseInt(packinfo.elementText("bytes")), "tytes", packinfo.elementText("key"));
					dataset.addValue(Integer.parseInt(packinfo.elementText("pkts")), "pkts", packinfo.elementText("key"));
					inputResult.add(data);
				}
			}
			else if(type.equals("session")){
				for (Element packinfo : packinfos) {
					SessionData data = new SessionData();
					data.setBytes(packinfo.elementText("bytes"));
					data.setPkts(packinfo.elementText("pkts"));
					data.setSrcip(packinfo.elementText("srcip"));
					data.setDstip(packinfo.elementText("dstip"));
					data.setSrcport(packinfo.elementText("srcport"));
					data.setDstport(packinfo.elementText("dstport"));
					data.setProtocol(packinfo.elementText("protocol"));
					data.setStarttime(packinfo.elementText("starttime"));
					data.setDuration(packinfo.elementText("duration"));
					//data.setFlows(packinfo.elementText("flows"));
					String icon = packinfo.elementText("srcip")+"_"+packinfo.elementText("srcport")+" "+packinfo.elementText("dstip")+"_"+packinfo.elementText("dstport")
					            + packinfo.elementText("protocol")+" "+packinfo.elementText("starttime");
					dataset.addValue(Integer.parseInt(packinfo.elementText("bytes")), "tytes", icon);
					dataset.addValue(Integer.parseInt(packinfo.elementText("pkts")), "pkts", icon);
					//dataset.addValue(Integer.parseInt(packinfo.elementText("flows")), "flow", icon);
					dataset.addValue(Integer.parseInt(packinfo.elementText("duration")), "duration", icon);
					inputResult.add(data);
				}
			}
			bpo.category3DImage(dataset,type+".jpg",type);
			totalData.setResultData(inputResult);//加入列表结果集
			totalData.setPicName(type+".jpg");//加入结果图片
			totalResult.add(totalData);
		}//Endof for
	return SUCCESS;
	}
	

   public static void main(String[] args) throws Exception {
	   	TopNInfoAction test = new TopNInfoAction();
	   	test.execute();
   }

  
   public List<TopNData> getInputresult() {
		return inputresult;
	}
   
   
   public void setInputresult(List<TopNData> inputresult) {
		this.inputresult = inputresult;
	}   
   
   
public List<TopNTotalData> getTotalResult() {
	return totalResult;
}

public void setTotalResult(List<TopNTotalData> totalResult) {
	this.totalResult = totalResult;
}


}

