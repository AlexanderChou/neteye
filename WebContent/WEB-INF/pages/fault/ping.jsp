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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%
String ip = (String)request.getParameter("ip");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>ping <%=ip%></title>
<link rel='StyleSheet' href="/nmsweb/include/style.css" type="text/css">
</head>
<body bgcolor="white">
<h3>设备ping测试</h3>
<h6>IP地址：<%=ip%></h6>
<hr>
<div id="pingtest">
<%
	String osname = System.getProperty("os.name");
	if (osname.indexOf("Windows")!=-1) {
		String cmd = "";
		if (ip.indexOf(":")!=-1)
			cmd = "ping6 -n 6 " + ip;
		else
			cmd = "ping -n 6 " + ip;
		Process ping = Runtime.getRuntime().exec(cmd);
		InputStream in = ping.getInputStream();
		int r;
		while((r=in.read())!=-1) {
			out.write(r);
			if (r==10) out.write("<br>");
			out.flush();
		}
		out.write("<h5>测试结束</h5>");
		out.flush();
	}
	else if (osname.indexOf("Linux")!=-1) {
		String[] cmd = {"/bin/sh","-c",""};
		if (ip.indexOf(":")!=-1)
			cmd[2] = "ping6 -c 6 " + ip;
		else
			cmd[2] = "ping -c 6 " + ip;
		Process ping = Runtime.getRuntime().exec(cmd);
		InputStream in = ping.getInputStream();
		int r;
		while((r=in.read())!=-1) {
			out.write(r);
			if (r==10) out.write("<br>");
			out.flush();
		}
		out.write("<h5>测试结束</h5>");
		out.flush();
	}
	else { out.write("<br>ping测试目前仅能支持Windows和Linux平台<br>"); }
%>
</div>
</body>
</html>