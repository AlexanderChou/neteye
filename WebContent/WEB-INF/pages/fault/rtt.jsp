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
 <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Refresh" CONTENT="60">
    <title>Rtt-Loss图表显示</title>
    <link rel="stylesheet" type="text/css" href="css/ext-all.css" />
</head>
<body>
<h4>图表显示</h4>
<h5>设备名称：<s:property value="port.name"/><br>
相关IP ：<s:property value="port.ipv4"/><br>
设备描述：<s:property value="port.description"/><br> </h5>
<hr>
<table border="0" align="left" cellpadding="6" cellspacing="6">
<tr><td>
	<table>
		<tr>
			<td colspan="2">
				RTT&nbsp;(TCP往返传输时间)(<font color="green">图中横轴代表时间</font>,<font color="blue">蓝值代表RTT</font>)
			</td>
		</tr>
	</table>
</td><td>
</td></tr>
<tr><td colspan="2">
	RTT 日趋势图
	<br>		
		<img src="http://localhost80/file/fault/dat/pic/<s:property value="ip"/>_rtt_day.gif"/>		
	<br>
</td></tr>
<tr><td colspan="2">
	RTT 周趋势图
	<br>		
		<img src="http://localhost:80/file/fault/dat/pic/<s:property value="ip"/>_rtt_week.gif"/>		
	<br>
</td></tr>
<tr><td colspan="2">
	RTT 月趋势图
	<br>		
		<img src="http://localhost:80/file/fault/dat/pic/<s:property value="ip"/>_rtt_month.gif"/>		
	<br>
</td></tr>
<tr><td colspan="2">
	RTT 年趋势图
	<br>
		<img src="http://localhost:80/file/fault/dat/pic/<s:property value="ip"/>_rtt_year.gif"/>		
	<br>
</td></tr>
</table>
</body>
</html>