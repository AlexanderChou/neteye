package com.event.util;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.base.util.BaseAction;
import com.base.util.Constants;

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
public class JDOMXML extends BaseAction {
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
	public String submit() throws Exception {
		String[] eventmodel = this.getRequest().getParameter("filterConfigEventModels").trim().split(";");
        String[] eventtype = this.getRequest().getParameter("filterConfigTypes").trim().split(";");
        String[] ip = this.getRequest().getParameter("filterConfigIps").trim().split(";");  
    Document document = DocumentHelper.createDocument();
	Element root = document.addElement("event");
    Element results = root.addElement("filters");
    for(int j=0;j<eventmodel.length;j++){
    	Element result = results.addElement("filter");
    	result.addElement("module").addText(eventmodel[j]);
    	result.addElement("eventType").addText(eventtype[j]);
    	Element ips = result.addElement("IPs");
        ips.addElement("IP").addText(ip[j]);

    }
 //   JDOMXML.saveXml(Constants.webRealPath+"file/event/FilterConfig.xml", document);
    JDOMXML.saveXml(Constants.webRealPath+"file/event/filter.xml", document);
    
    PrintWriter writer = this.getResponse().getWriter();
	writer.print("ok");
	writer.close();
	return null;
}
		
	
	 
}
