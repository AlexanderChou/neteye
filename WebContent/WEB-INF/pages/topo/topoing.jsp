<%--
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
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.topo.dao.TopoDAO"%>
<%@ page import="com.topo.dao.FileDAO"%>
<%@ page import="com.base.util.Constants"%>
<%@ page import="java.io.File"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.apache.commons.io.FileUtils"%>
<%@page import="java.util.Date"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta HTTP-EQUIV="refresh" Content="10">
<title>拓扑发现过程中……</title>
<link rel="stylesheet" type="text/css" href="css/topoInit.css" />
<script type="text/javascript">
	function stop_topo_proccess(){
		var disName = "${param.name}";
		window.location.href = "totalinit.do" ;
	}
</script>

</head>
<body>
	<div class="bottomDiv"><input type="button" onclick="stop_topo_proccess()" value="终止该发现" /></div>
	<%
	String disname = request.getParameter("name");
	String path = Constants.webRealPath + "file/topo/topoHis";
	String fileStr = path + File.separator + disname;
	String status = null;
	String devNum = "0";
	String linkNum = "0";
	File file = new File(fileStr+".xml");
	try {
		status = FileDAO.ReadTagDataFromXML(fileStr, "status");
		String userName = (String)request.getSession().getAttribute("userName");
		if (status.contains("topoing")) {
			out.println("正在进行拓扑发现……已发现：<br>");
			TopoDAO.getTopoIMG(path,disname);
		} else if (status.contains("end")) {
			out.println("拓扑发现结束！共发现：<br>");
			TopoDAO.getDevices(path,disname,null);
			response.sendRedirect("topoEdit.do?name=" + disname + "&userName=" + userName );
			%>
			<script type="text/javscript">
				document.href = "topoFrame.do?name=" + <%=disname%> + "&userName=" + <%=userName%>; 
			</script>
			<%
		} else {
			response.sendRedirect("topoError.do?path=" + fileStr);
		}
		if (file.exists()) {
			devNum = FileDAO.ReadTagDataFromXML(fileStr, "nodeNumber");
			devNum = devNum!=null?devNum:"0";
			linkNum = FileDAO.ReadTagDataFromXML(fileStr, "linkNumber");
			linkNum = linkNum!=null?linkNum:"0";
		}
	} catch (Exception e) {
		/* 出错时  复制文件  并输出错误文件 */
		//FileUtils.copyFile(file, new File(file.getParent() + "/" + disname + "_error_" + new Date().getTime()+ ".xml"));
		%>
		<script type="text/javscript">
			window.history.back();
		</script>
		<%
	}
	out.println("设备: <font color='red'>" + devNum + "</font>台      ");
	out.println("链路: <font color='red'>" + linkNum + "</font>条<br>");
	String imgStr = fileStr + ".png"; 
	File imgFile = new File(imgStr);
	if(imgFile.exists()){
		String img = disname + ".png";
		long time = System.currentTimeMillis();
%>
<img src="file/topo/topoHis/<%=img%>?<%=time%>" width="1280" height="1024">
<%}else{
	out.println("正在处理数据，请稍候......");
}%>
</body>
</html>
