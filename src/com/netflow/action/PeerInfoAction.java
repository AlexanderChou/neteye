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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.ser.JAFDataHandlerDeserializerFactory;
import org.apache.axis.encoding.ser.JAFDataHandlerSerializerFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.base.util.Constants;
import com.netflow.dto.NetFlowData;
import com.opensymphony.xwork2.ActionSupport;


public class PeerInfoAction extends ActionSupport{
	static Logger logger = Logger.getLogger(PeerInfoAction.class.getName());
    List<NetFlowData> result =new ArrayList<NetFlowData>();
        
	public String execute() throws Exception{
		String fileName = "period.xml";
		//从配置文件读取web servcie的URL或者从发布的服务列表中读取（待定）
		
		String endpoint = "http://"+Constants.NETFLOW_IP+":"+Constants.NETFLOW_PORT+"/axis/services/PeriodService";
		
		InputStreamReader ins = null;
		BufferedReader br = null;
		FileWriter fw = null;	
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();
			DataHandler dh = new DataHandler(new FileDataSource(fileName));
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			call.setOperationName(new QName(endpoint, "getPeriodInfo"));
			QName qnameattachment = new QName("PeriodService", "DataHandler");
			call.registerTypeMapping(dh.getClass(), qnameattachment,
			JAFDataHandlerSerializerFactory.class,
			JAFDataHandlerDeserializerFactory.class);
			call.addParameter("s1", qnameattachment, ParameterMode.IN);
			call.setReturnType(new QName("PeriodService", "DataHandler"),DataHandler.class);
			DataHandler ret = (DataHandler) call.invoke(new Object[] { fileName });
			ins = new InputStreamReader(ret.getInputStream());
			br = new BufferedReader(ins);
			
			File dir=new File(Constants.webRealPath+"file/netflow/downLoadFile");
			//File dir=new File("d:/");
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
			//针对从远程获得的xml文件，进行解析，读取peer节点的信息，如名称、IP地址等，用于页面显示
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        try {
			     DocumentBuilder db = dbf.newDocumentBuilder();
			     Document doc = db.parse( dir+File.separator+fileName);
			    // Document doc = db.parse( "d:/period.xml");
			     //得到根节点
			     Element root = doc.getDocumentElement();
			     NodeList name = root.getElementsByTagName("name");
			     NodeList ip = root.getElementsByTagName("ip");
			     NodeList port = root.getElementsByTagName("port");
			     
			     for(int i=0;i<name.getLength();i++){
			    	Element nodeName = (Element)name.item(i);
		            Element IP = (Element)ip.item(i);
		            Element portElement = (Element)port.item(i);
		            NetFlowData data = new NetFlowData();
		            data.setIP(IP.getTextContent());
		            data.setName(nodeName.getTextContent());
		            data.setPort(portElement.getTextContent());
		            result.add(data);
		            System.out.println(portElement.getTextContent());
		        }
	        }catch(Exception  e){
	         e.printStackTrace();
	        }
	       
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
	
	
	public String parseXML(){
		 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        try {
			     DocumentBuilder db = dbf.newDocumentBuilder();
//			     Document doc = db.parse( Constants.webRealPath+"file/netflow/downLoadFile/period.xml");
			     Document doc = db.parse( "d:/period.xml");
			     //得到根节点
			     Element root = doc.getDocumentElement();
			     NodeList name = root.getElementsByTagName("name");
			     NodeList ip = root.getElementsByTagName("ip");
			     
			     
			     for(int i=0;i<name.getLength();i++){
			    	Element e = (Element) name.item(i);
		            Element f = (Element) ip.item(i);
		            System.out.println("ip="+f.getTextContent());
		            System.out.println("name="+e.getTextContent());
		            NetFlowData data = new NetFlowData();
		            data.setIP(f.getTextContent());
		            data.setName(e.getTextContent());
		            result.add(data);
		            
		        }
	        }catch(Exception  e){
	         e.printStackTrace();
	        }
	       
	 return SUCCESS;	
	}
	
	public static void main(String[] args) throws Exception{
		PeerInfoAction temp = new PeerInfoAction();
		temp.execute();
		
	}
	

	public List<NetFlowData> getResult() {
		return result;
	}


	public void setResult(List<NetFlowData> result) {
		this.result = result;
	}
}

