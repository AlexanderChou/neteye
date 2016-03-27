package com.topo.action;

import java.io.BufferedWriter;
import java.io.FileWriter;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.base.util.Constants;
import com.base.util.W3CXML;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class TopoSecStartAction  extends ActionSupport {
	private String[] values;
	private String flag;
	private String secondTopoName;
	private String[] community;
	private String IP;
	private boolean success;
	public String execute() throws Exception{
		FileWriter fw;
		BufferedWriter bf;
	    //将IPs或ids地址写入文件，传给后台
		String file = Constants.webRealPath + "file/topo/idfile";
		if(flag.equals("IP")){
			file = Constants.webRealPath + "file/topo/ipfile";
			fw = new FileWriter(file);
			bf = new BufferedWriter(fw);
			String viewName = values[0];
			//根据视图名称，获得拓扑发现生成的xml文件中所有IP地址
			String fileStr =Constants.webRealPath + "file/topo/topoHis/"+viewName+".xml";
			org.w3c.dom.Document xmldoc = W3CXML.loadXMLDocumentFromFile(fileStr);
			NodeList routerLists = xmldoc.getElementsByTagName("IP");
			for (int j = 0; j < routerLists.getLength(); j++) {
				Node childNode = routerLists.item(j);// childNode是router的每一个子节点
				String IP = childNode.getTextContent();
				bf.write(IP);
				bf.newLine();
			}
		}else{
			fw = new FileWriter(file);
			bf = new BufferedWriter(fw);
			for(String value:values){
				bf.write(value);
				bf.newLine();
			}
		}
		bf.close();
		String userName = (String)ActionContext.getContext().getSession().get("userName");
		String cmd = new String();
		cmd = "topo --name "+secondTopoName+ " --exipfile " + file + " --ip " + IP + " --hop 2";
		for(int i=0;i<community.length;i++){
	    	cmd+= " --community " + community[i];
	    }
		try{ 
			Process ps=java.lang.Runtime.getRuntime().exec(cmd +" --user " + userName + " 2>1 >/dev/null &");  
			ps.getErrorStream();
			success = true;
		}catch(java.io.IOException   e){
			e.printStackTrace();  
			success = false;
		} 
		return SUCCESS;
	}
	public static void main(String[] args) {
		String fileStr ="e:/7_W_10_13_7502E_core.xml";
		org.w3c.dom.Document xmldoc = W3CXML.loadXMLDocumentFromFile("e:/7_W_10_13_7502E_core.xml");
		NodeList routerLists = xmldoc.getElementsByTagName("IP");
		for (int j = 0; j < routerLists.getLength(); j++) {
			Node childNode = routerLists.item(j);// childNode是router的每一个子节点
			String IP = childNode.getTextContent();
			System.out.println("IP="+IP);
		}
	}

	public String getSecondTopoName() {
		return secondTopoName;
	}

	public void setSecondTopoName(String secondTopoName) {
		this.secondTopoName = secondTopoName;
	}


	public String[] getCommunity() {
		return community;
	}
	
	public void setCommunity(String[] community) {
		this.community = community;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String ip) {
		IP = ip;
	}
}
