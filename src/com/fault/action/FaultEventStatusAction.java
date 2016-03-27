package com.fault.action;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.base.model.View;
import com.base.util.Constants;
import com.fault.dao.FaultListDao;
import com.opensymphony.xwork2.ActionSupport;
import com.view.dao.ViewDAO;
import com.view.dto.Router;
import com.view.util.MyXmlUtil;
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
 * <p>Title: 视图事件状态</p>
 * <p>Description: 根据用户的选择，显示相应视图下设备的最新事件状态</p>
 * @version 1.0
 * @author 郭玺
 * <p>Company: 网络中心</p>
 * <p>Copyright: Copyright (c) 2009</p>
 */
public class FaultEventStatusAction extends  ActionSupport{ 
	private FaultListDao faultlistdao = new FaultListDao();
	private String submitView;//得到中的xml文件名
	private Map<String,List<Router>> deviceMap = new TreeMap<String,List<Router>>();
	public String execute() throws Exception{
		
		if(submitView.trim().length()>0){
			String a[] = submitView.split(";");
			if(a!=null){
				for(int i=0;i<a.length;i++){
					if(!a[i].equals("")){
						if(a[i].lastIndexOf("_")>-1){
							String name = a[i].substring(0,a[i].lastIndexOf("_"));
							ViewDAO viewDAO = new ViewDAO();
							View view = viewDAO.getViewByViewName(name);
							String filePath = Constants.webRealPath + "file/user/" + view.getUserName() + "_" + view.getUserId() + "/";
							File file=new File(filePath+name+".xml");
							if(file.exists()){//如果文件存在，获得该文件中包含的所有设备
								List<Router> nodes = MyXmlUtil.getNodeIdList(filePath+name+".xml");
								for(int j=0;j<nodes.size();j++){
									String pic= faultlistdao.checkRouterStuts(nodes.get(j).getId());
									nodes.get(j).setPicture(pic);
									
								}			
								
								deviceMap.put(name, nodes);
							}
						}
					}
				}
			}
		}
		return SUCCESS;
	}
	public String getSubmitView() {
		return submitView;
	}
	public void setSubmitView(String submitView) {
		this.submitView = submitView;
	}
	public Map<String, List<Router>> getDeviceMap() {
		return deviceMap;
	}
	public void setDeviceMap(Map<String, List<Router>> deviceMap) {
		this.deviceMap = deviceMap;
	}
}
