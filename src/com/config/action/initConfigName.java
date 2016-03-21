package com.config.action;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.base.util.BaseAction;
import com.base.util.Constants;
import com.base.util.W3CXML;

public class initConfigName extends BaseAction{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private String systemid="主干网";
    private String systemtopname="主干网管理系统";
 //   private String systemdownname;
	public String readsystemname() throws Exception{
		String path = Constants.webRealPath + "file/initConfigName.xml";
		Document doc = W3CXML.loadXMLDocumentFromFile(path);
		NodeList tempsystemid = doc.getElementsByTagName("systemid");
		NodeList tempsystemTopName = doc.getElementsByTagName("systemTopName");
//		NodeList tempsystemBottomName = doc.getElementsByTagName("systemBottomName");
//		if(tempsystemid.item(0)!=null){
//			if(tempsystemid.item(0).getTextContent().equals("校园网"))
//			{System.out.println("+--+"+tempsystemid.item(0).getTextContent());
//			systemid="校园网";
//				}
//		}
		if(tempsystemTopName.item(0)!=null){
			systemtopname = tempsystemTopName.item(0).getTextContent();
		}		
//		if(tempsystemBottomName.item(0)!=null){
//			systemdownname = tempsystemBottomName.item(0).getTextContent();
//		}
		return SUCCESS;
		
		
	}
	public String updatasystemname() throws Exception{
//		systemid = this.getRequest().getParameter("inputid");
		systemtopname = this.getRequest().getParameter("inputtopname");
//		systemdownname = this.getRequest().getParameter("inputdowname");
		String path = Constants.webRealPath + "file/initConfigName.xml";
		Document document = W3CXML.loadXMLDocumentFromFile(path);
	    Node root=document.getDocumentElement(); 
	      if(root.hasChildNodes()){ 
	          NodeList ftpnodes = root.getChildNodes(); 
	             for (int k=0;k<ftpnodes.getLength();k++){ 
	               Node subnode = ftpnodes.item(k); 
//	              if (subnode.getNodeName()=="systemid"){                  
//	                  subnode.getFirstChild().setNodeValue(systemid); 
//	               } 
	              if (subnode.getNodeName()=="systemTopName"){                  
	                  subnode.getFirstChild().setNodeValue(systemtopname); 
	               } 
//	              if (subnode.getNodeName()=="systemBottomName"){                  
//	                  subnode.getFirstChild().setNodeValue(systemdownname); 
//	               } 
	          } 
	       }      
	      W3CXML.doc2XmlFile(document,path); 
	    
		
		return SUCCESS;
	}

//	public String getSystemid() {
//		return systemid;
//	}
//
//
//	public void setSystemid(String systemid) {
//		this.systemid = systemid;
//	}


	public String getSystemtopname() {
		return systemtopname;
	}


	public void setSystemtopname(String systemtopname) {
		this.systemtopname = systemtopname;
	}


//	public String getSystemdownname() {
//		return systemdownname;
//	}
//
//
//	public void setSystemdownname(String systemdownname) {
//		this.systemdownname = systemdownname;
//	}
//	
	
}
