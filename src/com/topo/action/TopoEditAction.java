package com.topo.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.json.annotations.JSON;

import com.base.util.Constants;
import com.opensymphony.xwork2.ActionSupport;

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
 * <p>Title: 拓扑发现页面校验</p>
 * <p>Description: 对拓扑发现名称是否重复进行校验</p>
 * @version 1.0
 * @author guoxi
 * <p>Company: 网络中心</p>
 * <p>Copyright: Copyright (c) 2009</p>
 */
public class TopoEditAction extends ActionSupport {
	private String checkId;
	private String chosedId;
	private String topoName;
	private ArrayList<String> linkList = new ArrayList<String>();
	@JSON(serialize=false)
	public String addDevice() throws Exception {
		checkId = checkId + ",";
		chosedId = chosedId +",";
		String path = Constants.webRealPath + "file/topo/topoHis/";
		String fileStr = path+ "/" + topoName + "toLink.txt";
		File file = new File(fileStr);
		if(file.exists()){
			//找出相关的链路
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileStr)));
			String line = reader.readLine();
			while (StringUtils.isNotEmpty(line)) {
				//对取出的行进行处理
				int prefixBracket = line.indexOf("(");
				int postfixBracket = line.indexOf(")");
				String middleStr = line.substring(prefixBracket+1,postfixBracket);
				String[] middleArray = middleStr.split(",");
				String srcId = middleArray[2].replace("'", "") + ",";
				String destId = middleArray[3].replace("'", "") + ",";
				boolean flag1 = false;
				boolean flag2 = false;
				if(checkId.indexOf(srcId)>=0 || chosedId.indexOf(srcId)>=0){
					flag1 = true;
				}
				if(checkId.indexOf(destId)>=0 || chosedId.indexOf(destId)>=0){
					flag2 = true;
				}
				if(flag1 && flag2){
					linkList.add(line);
				}
				line = reader.readLine();
			}
		}
		return SUCCESS;
	}
	public ArrayList<String> getLinkList() {
		return linkList;
	}
	public void setLinkList(ArrayList<String> linkList) {
		this.linkList = linkList;
	}
	public String getCheckId() {
		return checkId;
	}
	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}
	public String getChosedId() {
		return chosedId;
	}
	public void setChosedId(String chosedId) {
		this.chosedId = chosedId;
	}
	public String getTopoName() {
		return topoName;
	}
	public void setTopoName(String topoName) {
		this.topoName = topoName;
	}
}
