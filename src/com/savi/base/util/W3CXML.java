package com.savi.base.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

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
public class W3CXML {
	public static Document newXMLDocument() {
	try {
		return newDocumentBuilder().newDocument();
	} catch (ParserConfigurationException e) {
		throw new RuntimeException(e.getMessage());
	}
	}

	/**
	* 初始化一个DocumentBuilder
	* @return a DocumentBuilder
	* @throws ParserConfigurationException
	*/
	public static DocumentBuilder newDocumentBuilder()
	throws ParserConfigurationException {
	return newDocumentBuilderFactory().newDocumentBuilder();
	}

	/**
	* 初始化一个DocumentBuilderFactory
	* @return a DocumentBuilderFactory
	*/
	public static DocumentBuilderFactory newDocumentBuilderFactory() {
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	dbf.setNamespaceAware(true);
	return dbf;
	}
	/**
	* 将传入的一个XML String转换成一个org.w3c.dom.Document对象返回。
	* @param xmlString 一个符合XML规范的字符串表达。
	* @return a Document
	*/
	public static Document parseXMLDocument(String xmlString) {
		if (xmlString == null) {
			throw new IllegalArgumentException();
		}
		try {
		return newDocumentBuilder().parse(
		new InputSource(new StringReader(xmlString)));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	/**
	* 给定一个文件名，获取该文件并解析为一个org.w3c.dom.Document对象返回。
	* @param fileName 待解析文件的文件名
	* @return a org.w3c.dom.Document
	*/
	public static Document loadXMLDocumentFromFile(InputStream is) {//注释有问题
			try {
				return newDocumentBuilder().parse(is);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	}
	/**
	* 给定一个文件名，获取该文件并解析为一个org.w3c.dom.Document对象返回。
	* @param fileName 待解析文件的文件名
	* @return a org.w3c.dom.Document
	*/
	public static Document loadXMLDocumentFromFile(String fileName) {
		File file = new File(fileName);
		if (file.exists()) {
			if (fileName == null) {
				throw new IllegalArgumentException("未指定文件名及其物理路径！");
			}
			try {
				return newDocumentBuilder().parse(new File(fileName));
			} catch (SAXException e) {
				throw new IllegalArgumentException("目标文件（" + fileName + "）不能被正确解析为XML！\n" + e.getMessage());
			} catch (IOException e) {
				throw new IllegalArgumentException("不能获取目标文件（" + fileName + "）！\n" + e.getMessage());
			} catch (ParserConfigurationException e) {
				throw new RuntimeException(e.getMessage());
			}
		} else {
			return null;
		}
	}
	/**
	* 将传入的一个DOM Node对象输出成字符串。如果失败则返回一个空字符串""。
	* @param node DOM Node 对象。
	* @return a XML String from node
	*/
	public static String toString(Node node) {
		if (node == null) {
			throw new IllegalArgumentException();
		}
		Transformer transformer = newTransformer();
		if (transformer != null) {
			try {
			StringWriter sw = new StringWriter();
			transformer.transform(new DOMSource(node),new StreamResult(sw));
			return sw.toString();
		} catch (TransformerException te) {
			throw new RuntimeException(te.getMessage());
		}
		}
		return "不能生成XML信息！";
	}

	/**
	* 获取一个Transformer对象，由于使用时都做相同的初始化，所以提取出来作为公共方法。
	* @return a Transformer encoding gb2312
	*/
	public static Transformer newTransformer() {
		try {
			Transformer transformer =TransformerFactory.newInstance().newTransformer();
			Properties properties = transformer.getOutputProperties();
			properties.setProperty(OutputKeys.ENCODING, "utf-8");
			properties.setProperty(OutputKeys.METHOD, "xml");
			properties.setProperty(OutputKeys.VERSION, "1.0");
			properties.setProperty(OutputKeys.INDENT, "no");
			transformer.setOutputProperties(properties);
			return transformer;
		} catch (TransformerConfigurationException tce) {
			throw new RuntimeException(tce.getMessage());
		}
	}

	public static boolean doc2XmlFile(Document document,String filename){ 
      boolean flag = true; 
      try{ 
            /** 将document中的内容写入文件中   */ 
             TransformerFactory tFactory = TransformerFactory.newInstance();    
             Transformer transformer = tFactory.newTransformer();  
            /** 编码 */ 
            //transformer.setOutputProperty(OutputKeys.ENCODING, "GB2312"); 
             DOMSource source = new DOMSource(document);  
             StreamResult result = new StreamResult(new File(filename));    
             transformer.transform(source, result);  
         }catch(Exception ex){ 
             flag = false; 
             ex.printStackTrace(); 
         } 
        return flag;       
    }

	public static Document load(String filename){ 
      Document document = null; 
      try{  
            DocumentBuilderFactory   factory = DocumentBuilderFactory.newInstance();    
            DocumentBuilder builder=factory.newDocumentBuilder();    
            document=builder.parse(new File(filename));    
            document.normalize(); 
       }catch (Exception ex){ 
           ex.printStackTrace(); 
       }   
      return document; 
    }

	/**
	 * 演示修改文件的具体某个节点的值(事例)  
	 */
	public static void xmlUpdateDemo() 
    { 
       Document document = load("c://Message.xml"); 
       Node root=document.getDocumentElement(); 
      /** 如果root有子元素 */ 
      if(root.hasChildNodes()){ 
         /** ftpnodes */ 
          NodeList ftpnodes = root.getChildNodes(); 
         /** 循环取得ftp所有节点 */ 
         for (int i=0;i<ftpnodes.getLength();i++){                       
             NodeList ftplist = ftpnodes.item(i).getChildNodes(); 
            for (int k=0;k<ftplist.getLength();k++){ 
               Node subnode = ftplist.item(k); 
              /** 删除ftp-chn节点 */ 
//              if (subnode.getNodeType()==Node.ELEMENT_NODE&&subnode.getNodeName()=="ftp-chn"){ 
//                  ftpnodes.item(i).removeChild(subnode); 
//               } 
              /** 修改ftp-host的值为 192.168.0.1 */ 
              if (subnode.getNodeType()==Node.ELEMENT_NODE&&subnode.getNodeName()=="status"){                  
                  subnode.getFirstChild().setNodeValue("1"); 
               } 
             } 
             
          } 
       }      
       doc2XmlFile(document,"c://Message.xml"); 
    }

}
