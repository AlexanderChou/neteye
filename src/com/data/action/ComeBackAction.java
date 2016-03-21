package com.data.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.base.util.BaseAction;
import com.base.util.Constants;
import com.data.dto.DataDto;
import com.data.util.PageInfo;
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
public class ComeBackAction extends BaseAction {
	private List dataList;
	private String totalCount="0";
	private String mesg;
	
	public String view() throws Exception {
		dataList=new ArrayList();
		List tempList=new ArrayList();
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();   
		DocumentBuilder db= dbf.newDocumentBuilder();
		String filePath=Constants.BACKUP_PATH+"/backup/custom/";
		File file = new File(filePath);
		 if (file.exists()&&file.isDirectory()) {			
			 File filelist[] = file.listFiles();
			 for (int i = 0; i < filelist.length; i++) {
		          if (!filelist[i].isDirectory()) {
		            if(filelist[i].getAbsolutePath().contains(".xml")){
		            	File directory=new File(filePath+filelist[i].getName().substring(0,filelist[i].getName().lastIndexOf(".")));
		            	if(directory.exists()){
			            	DataDto dto=new DataDto();
			            	Document doc=db.parse(filelist[i]);
			            	NodeList nodeList=doc.getElementsByTagName("style"); 
			            	Node fatherNode=nodeList.item(0); 
			            	NodeList childNodes = fatherNode.getChildNodes();
			            	for(int j=0;j<childNodes.getLength();j++){   
			     			   Node childNode=childNodes.item(j); 
			     			   if(childNode instanceof Element){   
			     			     if(childNode.getNodeName().equals("name")){
			     			    	 dto.setName(childNode.getFirstChild().getNodeValue());
			     			     }else if(childNode.getNodeName().equals("userName")){
			     			    	 dto.setUserName(childNode.getFirstChild().getNodeValue());
			     			     }else if(childNode.getNodeName().equals("time")){
			     			    	 dto.setTime(childNode.getFirstChild().getNodeValue());
			     			     }
			     			   }   
			     			  } 
			            	tempList.add(dto);
		            	}
		            }
		          }
			 }
		 }
		String start = this.getRequest().getParameter("start");
		String limit = this.getRequest().getParameter("limit");
		if(tempList!=null){
			totalCount=Integer.toString(tempList.size());
		}
		PageInfo page=new PageInfo();
		page.setStart(start);
		page.setLimit(limit);
		page.setResult(tempList);
		dataList=page.getPageResult();
		return SUCCESS;
	}
	public String delete() throws Exception {
		//String filePath=Constants.BACKUP_PATH+"/backup/custom/";
		String dirPath=Constants.BACKUP_PATH+"/backup/custom/";
		String names = this.getRequest().getParameter("names");
		String name[]=names.split(";");
		if(name!=null){
			for(int i=0;i<name.length;i++){
				if(name[i]!=null&&!name[i].equals("")){
					File fileName=new File(dirPath+name[i]+".xml");
					if(fileName.exists()){
						fileName.delete();						
					}
					File backDirectory=new File(dirPath+name[i]);
				    if(backDirectory.exists()&&backDirectory.isDirectory()){
				    	this.deleteFile(dirPath+name[i]);
				    }
				}
			}
			mesg="数据备份删除成功！";
		}
		return SUCCESS;
	}
	public static void deleteFile(String filePath){
		File f = new File(filePath);
		if(f.exists() && f.isDirectory()){
			File delFiles[]= f.listFiles();
			for(int i = 0;i<delFiles.length;i++){
				deleteFile(delFiles[i].getAbsolutePath());
			}
		}
		f.delete();
	}
	
	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public String getMesg() {
		return mesg;
	}

	public void setMesg(String mesg) {
		this.mesg = mesg;
	}
}
