package com.topo.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.base.model.View;
import com.base.service.ViewService;
import com.base.util.BaseAction;
import com.base.util.Constants;
import com.topo.dao.FileDAO;
import com.topo.dao.TopoDAO;
import com.topo.dao.TopoHisDAO;
import com.topo.dao.Topo_Edit;

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
 * <p>Title: 拓扑历史查询</p>
 * <p>Description: 提供拓扑历史查询功能</p>
 * @version 1.0
 * @author 李宪亮
 * <p>Company: 网络中心</p>
 * <p>Copyright: Copyright (c) 2009</p>
 */
public class ToPoHisManagerAction extends BaseAction {
	private String topoName;
	private TopoHisDAO topoHisDAO = new TopoHisDAO();
	private List list = null;
	private final String POTOHISTORY_PATH = Constants.webRealPath + "file/topo/topoHis/";
	private static final long serialVersionUID = 1L;
	private boolean isMore;
	private String disNameStr;
	private String htmlAreaStr;
	private View view;
	private boolean topoEnd;

	/**
	 * 得到拓扑历史的记录列表
	 * @return 
	 * @throws Exception
	 */
	public String getToPoHisList() throws Exception {
		list = topoHisDAO.getAllhistoryTxtData(POTOHISTORY_PATH + "history/history.xml");
		return SUCCESS;
	}
	
	
	/**
	 * 得到某一条拓扑历史记录的详细
	 * @return
	 * @throws Exception
	 */
	public String topoDeviceList() throws Exception {
		String dir = this.getRequest().getParameter("dir").replace("?", "");
		list = topoHisDAO.getDevices(POTOHISTORY_PATH, dir + ".xml");
		return SUCCESS;
	}
	
	/**
	 * 得到设备的链接
	 * @return
	 * @throws Exception
	 */
	public String potoLink() throws Exception {
		String deviceName = this.getRequest().getParameter("name");
		String dir = this.getRequest().getParameter("dir");
		list = topoHisDAO.findFileAndGetData(POTOHISTORY_PATH, dir + ".xml", deviceName);
		return SUCCESS;
	}
	
	/**
	 * 删除拓扑历史中的记录
	 * @return
	 * @throws Exception
	 */
	public String deleteTopoHis() throws Exception {
		String names = this.getRequest().getParameter("names");
		topoHisDAO.deleteTopoHis(POTOHISTORY_PATH, names);
		PrintWriter writer = this.getResponse().getWriter();
		writer.print("ok");
		writer.close();
		return SUCCESS;
	}
	
	/**
	 * 在拓扑页面初始化时，初始表单中的一些值
	 * @return
	 * @throws Exception
	 */
	public String topoInit() throws Exception {
		//拓扑名称的初始化
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
		String dateTime = format1.format(new Date()).trim();
		String name = "topo" + dateTime;
		ServletActionContext.getRequest().setAttribute("name", name);
		
		String ip = topoHisDAO.getWangGuan();
		ServletActionContext.getRequest().setAttribute("ip", ip);
		return SUCCESS;
	}
	
	/**
	 * 拓扑子网发现
	 * @return
	 * @throws Exception
	 */
	public String topoInit2() throws Exception {
		//拓扑名称的初始化
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyyMMddhhmmss");
		String dateTime = format1.format(new Date()).trim();
		String name = "topo" + dateTime;
		ServletActionContext.getRequest().setAttribute("name", name);
		//ip初始化
		String ip = topoHisDAO.getWangGuan();
		ServletActionContext.getRequest().setAttribute("ip", ip);
		//子网掩码的初始化
		String sub = topoHisDAO.getNetAddress();
		this.getRequest().setAttribute("sub", sub);
		return SUCCESS;
	}
	
	/**
	 * 改变视图的 跳转方向 
	 * 判断该用户的用户名和拓扑的创建者是否相同 来控制跳转方向
	 * @return
	 * @throws Exception
	 */
	public String switchTopoView() throws Exception {
		String userName = this.getRequest().getParameter("userName");
		String disName = this.getRequest().getParameter("name");
		String loginName = this.getRequest().getSession().getAttribute("userName").toString();
		String txtDir = Constants.webRealPath + "file/topo/topoHis/";
		
		File xmlFile = new File(txtDir + disName.split("\\[")[0] + ".xml");
		if (xmlFile.exists()) {
			String txtFile = txtDir + disName + ".txt";
			String txtFile1 = txtDir + disName + "[1].txt";
			String txtFile2 = txtDir + disName + "[1-1].txt";
			File file = new File(txtFile);
			File file1 = new File(txtFile1);
			File file2 = new File(txtFile2);
			if(!file.exists() && !file1.exists() && !file2.exists()){
				TopoDAO.getDevices(txtDir,disName,null);
			}
		}
		if (userName.equals(loginName)) {
			/** 进入浏览的页面 */
			return "viewTopo";
		} else {
			/** 进入修改视图的页面 */
			return "modifyTopoView";
		}
	}
	/**
	 * 首先将以前分区生成的文件删除，然后根据新的阈值重新分区并生成相应的txt文件
	 * @throws Exception
	 */
	public String changeThreshold() throws Exception {
		String disName = this.getRequest().getParameter("name");
		String threshold = this.getRequest().getParameter("threshold");
		String txtDir = Constants.webRealPath + "file/topo/topoHis/";
		File[] contents = new File(txtDir).listFiles();
		for (File f : contents) {
			String fileName = f.getName();
			String srcName = fileName.split("\\[")[0].replace(".txt", "");
			if (fileName.endsWith(".txt")) {
				if (srcName.equals(disName)) {
					f.delete();
				}
				
				if (srcName.equals(disName + "toLink")) {
					f.delete();
				}
			}
			
			if (fileName.endsWith(".png") && fileName.equals(disName + ".png")) {
				f.delete();
			}
			
		}
		TopoDAO.getDevices(txtDir,disName,Integer.valueOf(threshold));
		return SUCCESS;
	}
	
	/**
	 * 检验名字是否存在
	 * @return
	 * @throws Exception
	 */
	public String checkName() throws Exception {
		String name = this.getRequest().getParameter("name");
		boolean isHave = topoHisDAO.checkNameIsHave(POTOHISTORY_PATH + "history/history.xml", name);
		PrintWriter writer = this.getResponse().getWriter();
		if (isHave) {
			writer.print("have");
		} else {
			writer.print("none");
		}
		return SUCCESS;
	}

	/**
	 * 用 ajax 检查是否有多个区间
	 * @return
	 * @throws Exception
	 */
	public String checkIsMore() throws Exception {
		String disName = ServletActionContext.getRequest().getParameter("disName").split("\\[")[0];
		String dirPath = Constants.webRealPath + "file/topo/topoHis/";
		FileDAO fileDAO = new FileDAO();
		isMore = fileDAO.checkIsMore(dirPath, disName);
		return SUCCESS;
	}
	
	/**
	 * 用Ajax取回区间的各个名字
	 * @return
	 * @throws Exception
	 */
	public String disNameStrResturn() throws Exception {
		String dirPath = Constants.webRealPath + "file/topo/topoHis/";
		String disName = ServletActionContext.getRequest().getParameter("disName").split("\\[")[0];
		FileDAO fileDAO = new FileDAO();
		disNameStr = fileDAO.returnNameStr(dirPath, disName);
		return SUCCESS;
	}
	
	/**
	 * 得到拓扑视图图片热区域 area 
	 * @return
	 * @throws Exception
	 */
	public String listHtmlArea() throws Exception {
		String disName = this.getRequest().getParameter("disName");
		htmlAreaStr = Topo_Edit.listHTMLAREA(disName);
		return SUCCESS;
	}

	/**
	 * 对view的初始化
	 * @return
	 * @throws Exception
	 */
	public String initView() throws Exception {
		//String viewName = this.getRequest().getParameter("viewName");
		String viewId = this.getRequest().getParameter("viewId");
		
		//ViewDAO viewDAO = new ViewDAO();
		//view = viewDAO.getViewByViewName(viewId);
		view = new ViewService().findById(Long.valueOf(viewId));
		return SUCCESS;
	}
	
	/**
	 * 判断某个拓扑发现是否结束
	 * @return
	 * @throws Exception
	 */
	public String topoDisIsEnd() throws Exception {
		String srcXml = Constants.webRealPath + "file/topo/topoHis/history/history.xml";
		topoEnd = topoHisDAO.topoIsOver(srcXml, topoName);
		return SUCCESS;
	}
	
	/**
	 * 终止某个拓扑发现进程
	 * @return
	 * @throws Exception
	 */
	public String stopTopoProcess() throws Exception {
		String disName = this.getRequest().getParameter("disName");
		String cmd = "topo-stop " + disName;
		Process process = Runtime.getRuntime().exec(cmd);
		process.getErrorStream();
		return SUCCESS;
	}
	
	public String getTopoName() {
		return topoName;
	}


	public void setTopoName(String topoName) {
		this.topoName = topoName;
	}


	public List getList() {
		return list;
	}


	public void setList(List list) {
		this.list = list;
	}


	public boolean isMore() {
		return isMore;
	}


	public void setMore(boolean isMore) {
		this.isMore = isMore;
	}


	public String getDisNameStr() {
		return disNameStr;
	}


	public void setDisNameStr(String disNameStr) {
		this.disNameStr = disNameStr;
	}


	public String getHtmlAreaStr() {
		return htmlAreaStr;
	}


	public void setHtmlAreaStr(String htmlAreaStr) {
		this.htmlAreaStr = htmlAreaStr;
	}


	public View getView() {
		return view;
	}


	public void setView(View view) {
		this.view = view;
	}


	public boolean isTopoEnd() {
		return topoEnd;
	}


	public void setTopoEnd(boolean topoEnd) {
		this.topoEnd = topoEnd;
	}
}
