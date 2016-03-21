package com.topo.dao;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.base.util.JDOMXML;


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
public class XMLManager {
	public static void main(String[] args) throws Exception {
//		String filePath = "c://test10.xml";
//		Document document = readXml(filePath);
//		Element devicesElement = document.getRootElement().element("devices");
//		
//		List<Element> devices = devicesElement.elements("device");
//		
//		for (Element device : devices) {
//			Element interfacesElement = device.element("interfaces");
//			List<Element> interfaces = interfacesElement.elements("interface");
//			for (Element intf : interfaces) {
//				Element destsElement = intf.element("dests");
//				if (destsElement.isTextOnly()) {
//					interfacesElement.remove(intf);
//				}
//			}
//		}
//		writeXml(document, "c:/test11.xml");
	}
	
	public static Document readXml(String filePath) throws Exception{
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(filePath)); 
		return document;
	}
	
	public static void writeXml(Document document, String filePath) throws Exception {
		XMLWriter writer = new XMLWriter(new FileWriter(new File(filePath)));
		writer.write(document);
		writer.close();
	}
	
}
