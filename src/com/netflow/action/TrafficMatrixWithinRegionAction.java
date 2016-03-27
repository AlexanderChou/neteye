package com.netflow.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
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
import org.apache.axis.encoding.XMLType;
import org.apache.axis.encoding.ser.JAFDataHandlerDeserializerFactory;
import org.apache.axis.encoding.ser.JAFDataHandlerSerializerFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.base.util.Constants;
import com.netflow.dto.TrafficeMatrixInfo;
import com.opensymphony.xwork2.ActionSupport;

/**
 * <pre>
 * 流量矩阵域间视图调用的action
 * 负责解释从WS端获取的xml信息文件
 * 把数据传给前段
 * @author slj
 * </pre>
 */
public class TrafficMatrixWithinRegionAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(TrafficeMatrixInfoAction.class.getName());
	List<TrafficeMatrixInfo> tmList =new ArrayList<TrafficeMatrixInfo>();
	private boolean success =true;
	private String row="";
	private String col="";
	private String url="";
    /**
	 * 请求web service
	 * 获得xml文件
	 * 解析矩阵信息
	 * @return
	 */
	public String queryTrafficMatrixInfoInRegion(){
		System.setProperty("javax.xml.parsers.DocumentBuilderFactory","com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");

		String fileName = "tmIsp.xml";
		String desFileName ="tmWithinRegion.xml";
	
		String endpoint = "http://219.243.208.45:8080/axis/services/topNService";
		InputStream ins = null;
        FileOutputStream fos = null;
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
			ins = ret.getInputStream();
			
			
			File dir=new File(Constants.webRealPath+"file/netflow/downLoadFile/");
				if(!dir.exists()){
				dir.mkdir(); 
					logger.debug("下载文件存放目录不存在，创建 【"+dir.getAbsolutePath()+" 】目录");
				}
				File f = new File(Constants.webRealPath+"file/netflow/downLoadFile/tmWithinRegion.xml");
				fos = new FileOutputStream(f);
				byte[]buff = new byte[1024];
				int flag = 0;
				while((flag = ins.read(buff))!= -1){
					fos.write(buff, 0, flag);
				}
			
				//针对从远程获得的xml文件，进行解析，读取peer节点的信息，如名称、IP地址等，用于页面显示
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		        try {
				     DocumentBuilder db = dbf.newDocumentBuilder();
				     Document doc = db.parse( dir+File.separator+desFileName);
				     Element root = doc.getDocumentElement();
				     //得到srcas 节点， 每个srcas 下有25个entry节点 组成一组记录
				     NodeList srcAsList = root.getElementsByTagName("srcas");
				     NodeList trafficlist = root.getElementsByTagName("trafficmatrix");
				     //解析每个srcas下的记录并保存在 TrafficeMatrixInfo pojo对象中
				     for(int i=0;i<trafficlist.getLength();i++){
				    	Element srcas = (Element)srcAsList.item(i);
				    	Element traffic = (Element)trafficlist.item(i);
				    	NodeList dstlist = traffic.getElementsByTagName("dstas");
				    	NodeList byteslist = traffic.getElementsByTagName("bytes");
				    
				    	for(int j=0;j<dstlist.getLength();j++){

				    		Element dst = (Element)dstlist.item(j);
				    		Element bytes = (Element)byteslist.item(j);
				    		TrafficeMatrixInfo tm = new TrafficeMatrixInfo();
				    		tm.setSrcAs(srcas.getTextContent());

				    		tm.setBytes(bytes.getTextContent());
				    		tm.setDstAs(dst.getTextContent());
				    		tmList.add(tm);
				    	}
				    	
				    
			        }
		        }catch(Exception  e){
		        	e.printStackTrace();
		         this.success =false;
		        }
		       
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("调用文件下载Web服务出错：" + e.getMessage());
				  this.success =false;
			}finally{
				try{
				
					ins.close();
					fos.close();
				}catch(Exception ee){
		           ee.printStackTrace();
				}
			}
			return SUCCESS;	
	}
	
	public String viewHistory(){
		try{
			String endpoint = "http://219.243.208.45:8080/axis/services/tmService";
		
		   Service service = new Service();
		   Call call = (Call) service.createCall();
		   call.setTargetEndpointAddress( new java.net.URL(endpoint) );
		   call.setOperationName( "getTMInfo" );
		   url = (String) call.invoke( new Object[] {"isp",row,col} );
		   
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	} 
    

	public List<TrafficeMatrixInfo> getTmList() {
		return tmList;
	}


	public void setTmList(List<TrafficeMatrixInfo> tmList) {
		this.tmList = tmList;
	}

	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}

	public String getCol() {
		return col;
	}

	public void setCol(String col) {
		this.col = col;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
}
