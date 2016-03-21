package com.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

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
public class JDOMXML {
  
	/**
	 * 通过标签名获得其值
	 * @param doc
	 * @param tagName
	 * @return
	 */
   public static List<String>getValueByTagName(Document doc,String tagName){
	   List<String> result =new ArrayList<String>();
	   Element elem = doc.getRootElement();
	   getElementList(elem,tagName,result);
	   if(result.size()==0){
		   result.add("");
	   }
	   return result;
   }
   /**
    * 使用递归方法遍历DOCUMENT文档
    * @param element
    * @param tagName
    * @param result
    */
   private static void getElementList(Element element,String tagName,List<String> result) {
       List  elements = element.elements();
        if (elements.size() == 0) {
            String value = element.getText();
            if(tagName.equals(element.getName())){
            	result.add(value);
            }
         } else {
       //有子元素
       for (Iterator it = elements.iterator(); it.hasNext();) {
           Element elem = (Element) it.next();
           //递归遍历
           getElementList(elem,tagName,result);
           }
      }
   }
   /**
    * 使用DOM4J的xpath存在jar冲突的问题
    * 这是自己重写的selectNOdes私有方法
    * xPath要从根目录开始
    * @param doc
    * @param xpath
    * @return
    */
    private static List selectNodes(Document doc,String xpath){
    	List<Element> dataRecord=null;
        String path[]=xpath.split("/");
        Element el=doc.getRootElement();
        for(int i=2;i<path.length-1;i++){
        	el= el.element(path[i]);
        	
        }
        dataRecord = el.elements(path[path.length-1]);
        return dataRecord;
    }

	/**
	 * 根据标签(xPath)读取值
	 * @param doc
	 * @param tagName
	 * @return 
	 */
	public static String[] getSingleText(Document doc, String xPath) {
		String result[] = new String[10];
		int counter=0;
	     List elements = selectNodes(doc,xPath);
         if(elements !=null){
        	 Iterator it = elements.iterator();
        	 while(it.hasNext()){
        		 Element e =(Element)it.next();
        		 if(counter<10)
        		 result[counter++]=e.getText();
        	 }
        	 
         }
         return result;
	    }

	/**
	 *  修改指定xpath下元素的值
	 * @param doc
	 * @param tagName
	 * @param value
	 */
    public static void setNodeValues(Document doc,String xPath,String value){
    	     List elements = selectNodes(doc,xPath);
             if(elements !=null){
            	 Iterator it = elements.iterator();
            	 while(it.hasNext()){
            		 Element e =(Element)it.next();
            		
            		 e.setText(value);
            	 }
            	 
             }
             
    }
    /**
	 *  修改指定xpath下元素的多个值
	 * @param doc
	 * @param tagName
	 * @param value[]
	 */
    public static void setNodeValues(Document doc,String xPath,String value[]){
    	Element e=null;
    	int counter=0;
    	String tagName=null;
    	List elements = selectNodes(doc,xPath);
        //先删除原先存在的值
    	if(elements !=null){
       	  Iterator it = elements.iterator();
       	  while(it.hasNext()){
       		 e =(Element)it.next();
       		 Iterator rEmail = e.elementIterator();
       		 while(rEmail.hasNext()){
       			 Element re = (Element)rEmail.next();
       			 tagName = re.getName();
       			 if(counter < value.length){
       				 re.setText(value[counter++]);
       			 }
       			 else{
       				 e.remove(re);
       			 }
       		 }
       		 for(int i=counter;i<value.length;i++){
       			Element rec=e.addElement(tagName);
       			rec.setText(value[counter]);
       		 }
       		 
       	  }
        }
}
    /**
     * 通过TagName来设置标签的值
     * @param doc
     * @param tagName
     * @param value
     */
    public static void setValueByTagName(Document doc,String tagName,String value){
    	 Element elem = doc.getRootElement();
    	 treeWalk(elem,tagName,value);
   }    
    /**
     * 私有的遍历文档方法
     * @param element
     * @param tagName
     * @param value
     */
    private static void treeWalk(Element element,String tagName,String value){
    	List  elements = element.elements();
        if (elements.size() == 0) {
            if(tagName.equals(element.getName())){
            	element.setText(value);
            }
         } else {
       //有子元素
       for (Iterator it = elements.iterator(); it.hasNext();) {
           Element elem = (Element) it.next();
           //递归遍历
           treeWalk(elem,tagName,value);
           }
      }
    }
    
	/**
	 * 阅读文件
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
	
	public static Document readXML(File file) {
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
	 * 生成xml文件
	 * @param file xml文件的存储路径
	 * @param document xml文件体
	 */
	public static void saveXml(String file, Document document) {
		XMLWriter writer = null;
		//这里有疑问
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			FileOutputStream fos = new FileOutputStream(new File(file));  
			writer = new XMLWriter(fos, format);
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
	 * 将输入的字符串内容生成一个xml文件
	 * @param file  xml文件的路径和名称
	 * @param content  xml文件内容
	 */
	public static void writeXML(String file,String content){
		try{
		   OutputFormat format = OutputFormat.createPrettyPrint();  
		   format.setEncoding("utf-8");  
		   FileOutputStream fos = new FileOutputStream(new File(file));  
		   //writer = new XMLWriter(new FileWriter(xmlFile), format);  
		   XMLWriter writer = new XMLWriter(fos, format); 
		   Document document = DocumentHelper.parseText(content);
		   writer.write(document);  
		   writer.close();  
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	 public static void main(String[] args) {
		    
		 	List filters = new ArrayList();
		 	filters.add("testFilter");
		 	List IPs = new ArrayList();
		 	IPs.add("testIP");
	        Document document = DocumentHelper.createDocument();
			Element root = document.addElement("event");
	        Element results = root.addElement("filters");
	        Element result = results.addElement("filter");
	        for(int j=0;j<filters.size();j++){
	        	result.addElement("module").addText(filters.get(j).toString());
	        	result.addElement("eventType").addText(filters.get(j).toString());
	        	Element ip = result.addElement("IPs");
	        	for(int k=0;k<IPs.size();k++){
	        		ip.addElement("IP").addText(IPs.get(k).toString());
	        	}
	        }
	        JDOMXML.saveXml("d:/myTest.xml", document);
		}
}
