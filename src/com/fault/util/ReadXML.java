package com.fault.util;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.base.model.View;
import com.base.util.W3CXML;
import com.base.util.Constants;
import com.view.dao.ViewDAO;

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
public class ReadXML {
	public List getNode(String name){
		List nodeList=new ArrayList();
		ViewDAO viewDAO = new ViewDAO();
		View view = viewDAO.getViewByViewName(name);
		String path = Constants.webRealPath + "file/user/" + view.getUserName() + "_" + view.getUserId() + "/";
		//String path="D:/CERNET/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/NetEye/file/view/data";
		String fileName=path +  "/"+name+".xml";
		org.w3c.dom.Document xmlViewdoc = W3CXML.loadXMLDocumentFromFile(fileName);
		String[] str={"to:router","to:switch","to:server","to:workstation"};
		for(int k=0;k<str.length;k++){
			NodeList nodes = xmlViewdoc.getElementsByTagName(str[k]);
			for(int i=0;i<nodes.getLength();i++){
				String id="";
				Node node= nodes.item(i);
				NodeList subNodes=node.getChildNodes();
				for(int j=0;j<subNodes.getLength();j++){
					Node childNode=subNodes.item(j);
					String nodeName=childNode.getNodeName();
					if(nodeName.equals("to:id")){
						id=childNode.getTextContent();					
					}				
				}
				nodeList.add(id);
			}
		}
		return nodeList;
	}
}
