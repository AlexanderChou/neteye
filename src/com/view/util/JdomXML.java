/**
 * 文件名：SysProperty.java
 *
 * 版本信息：
 * 日期：2009-6-17
 * 版权所有 2009All rights reserved.
 * 创建者：wht
 *
 */
package com.view.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.base.util.W3CXML;
import com.view.dto.Router;

public class JdomXML {
	
	   public Element find=null; 
	   public void setvalueaaa(Element e,String id,String value)
	   {
                if("router".equalsIgnoreCase(e.getName()) || "switch".equalsIgnoreCase(e.getName()) || "custom".equalsIgnoreCase(e.getName()) ||
                   "workstation".equalsIgnoreCase(e.getName()) || "server".equalsIgnoreCase(e.getName())){
	                	List items=e.getChildren();
	    				for(Object o:items){
	    					Element e1=(Element)o;
	    					  if("id".equalsIgnoreCase(e1.getName())&& e1.getText().equalsIgnoreCase(id))
	    						  find=e1;
	                    }
                }else{
                	List items=e.getChildren();
    				for(Object o:items){
    					Element e1=(Element)o;
    					//System.out.println(e1.getName());
    					setvalueaaa(e1, id,value);
                    }
                }
	   }
	   public void setvalue(org.jdom.Document doc,String id,String value,String fileStr){
				Element root=	doc.getRootElement();
				setvalueaaa(root, id, value);
				
				if(find!=null){
					Element eee=find.getParentElement();
					Element temp=null;
					List items=eee.getChildren();
					for(Object o:items)
					{
						Element e1=(Element)o;
						if("subView".equalsIgnoreCase(e1.getName()))
						temp=e1;
	                }
					if(temp!=null)eee.removeContent(temp);
					
					Element ad=new Element("subView");
					Namespace namespace =Namespace.getNamespace( "to","http://www.inetboss.com/view");
					ad.setNamespace(namespace);
					ad.addContent(value);
					find.getParentElement().addContent(ad);
				}
				 
				//设置输出的格式及编码
				org.jdom.output.Format format = org.jdom.output.Format.getCompactFormat(); 
				format.setEncoding("UTF-8"); 
				format.setIndent("　 "); //缩进2个空格后换行,空格数自己设 

				//输出xml				
				XMLOutputter outputter = new XMLOutputter(format);
				try {
					//outputter.output(doc,System.out);
					outputter.output(doc,new FileOutputStream(fileStr));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	   }
	   public void delvalue(org.jdom.Document doc,String id,String value,String fileStr){
			Element root=	doc.getRootElement();
			setvalueaaa(root, id, value);
			
			if(find!=null)
			{
				Element eee=find.getParentElement();
				Element temp=null;
				List items=eee.getChildren();
				for(Object o:items)
				{
					Element e1=(Element)o;
					if("subView".equalsIgnoreCase(e1.getName()))
					temp=e1;
                }
				if(temp!=null)eee.removeContent(temp);
			}

			 
			//设置输出的格式及编码
			org.jdom.output.Format format = org.jdom.output.Format.getCompactFormat(); 
			format.setEncoding("UTF-8"); 
			format.setIndent("　 "); //缩进2个空格后换行,空格数自己设 

			//输出xml				
			XMLOutputter outputter = new XMLOutputter(format);
			try {
				//outputter.output(doc,System.out);
				outputter.output(doc,new FileOutputStream(fileStr));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
  }
	   public static String getvalue(String file,String id)throws Exception{
		   org.w3c.dom.Document xmldoc = W3CXML.loadXMLDocumentFromFile(file);
		   String[] tagnames ={"to:router","to:switch","to:server","to:workstation","to:custom"};
		   int flag =0;
		   String result="";
		   for(int i=0;i<tagnames.length;i++){
		   String tagName = tagnames[i];
		   NodeList nodeLists = xmldoc.getElementsByTagName(tagName);
		   if(nodeLists!=null){
			   for (int j = 0; j < nodeLists.getLength(); j++) {
				   
				   Node routerNode = nodeLists.item(j);
				   NodeList childLists = routerNode.getChildNodes();
				   for (int k = 0; k < childLists.getLength(); k++) {
					   Node childNode = childLists.item(k);
					   String nodeName = childNode.getNodeName();
					   if (nodeName.equals("to:id")) {
					   if(id.equals(childNode.getTextContent())){
						  flag=1;
					   }
							}
					  if(flag==1&&nodeName.equals("to:subView")) {
						  result =childNode.getTextContent();
						} 
					   
				   }
			   }
		   
            }
           
			
			
			}
		 return result;	 
		
  }
	
	public static void main(String[] clientid) throws Exception
	{
//	   SAXBuilder as=new SAXBuilder();
//	   try {
//		   //这种方法运行一次就会往文件中写一次，最好是写时能判断，若已存在，覆盖原来的文件(待修改)
//		    org.jdom.Document doc=as.build(new File("e:/ZJ10.xml"));
//		    new JdomXML().setvalue(doc, "9", "fff","e:/ZJ10.xml");
//        }catch(Exception e){e.printStackTrace();}
		String file = "e:/testa.xml";
		String a = getvalue(file,"3");
		System.out.println("e:/testa.xml"+a);
		
	}
	
}
