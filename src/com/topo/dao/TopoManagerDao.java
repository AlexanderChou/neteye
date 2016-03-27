package com.topo.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
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
/**
 * 这个类为拓扑综合管理类 
 * @author 李宪亮
 *
 */
public class TopoManagerDao {
	/**
	 * 保存xml
	 * @param file xml文件的存储路径
	 * @param document xml文件体
	 */
	public void saveXml(String file, Document document) {
		XMLWriter writer = null;
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			writer = new XMLWriter(new FileWriter(new File(file)), format);
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
	 * 读取xml文件
	 * @param file xml文件位置
	 * @return xml
	 */
	public Document readXml(String file){
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
	 * 得到xml文件的数据
	 * @see
	 * @param file  需要阅读的xml
	 * @param condition 需要的查询条件
	 * @return 
	 */
	public List<Map<String, String>> getFileData(String file, String condition){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Document document = readXml(file);
		Element root = document.getRootElement();
		List<Element> elements = root.elements();
		for (Element e : elements) {
			if ("devices".equals(e.getName())) {
				if (StringUtils.isNotEmpty(condition)) {
					//TODO 以后在后台需要查询时添加查询条件
				} else {
					List<Element> devices = e.elements();
					for (Element device : devices) {
						Map<String, String> attributes = new HashMap<String, String>();
						List<Element> as = device.elements();
						for (Element attribute : as) {
							if (attribute.isTextOnly()){
								attributes.put(attribute.getName(), attribute.getText());
							}
						}
						list.add(attributes);
					}
				}
			}
		}
		
		return list;
	}
}
