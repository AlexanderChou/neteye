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
<%@page language="java" import="java.util.*"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<meta HTTP-EQUIV="refresh" Content="300;url='';text-html; charset=UTF-8">
<title>Insert title here</title>
        <link rel="stylesheet" type="text/css" href="css/ext-all.css" />
        <script type="text/javascript" src="js/ext-base.js"></script>
        <script type="text/javascript" src="js/ext-all.js"></script>
        <script type="text/javascript" src="js/fixTime.js"></script>
<style>
div#gallery{
    width: 100%;
    border: 0px dashed gray;
    padding: 0px;
}

.imagebox{
    float: left;
    padding: 0px;
    margin: 1px;
    border:0px solid gray;
}

.imagebox p{
        margin: 0px;
    text-align: center;
}
</style>
</head>
<body bgcolor="#FFFFFF" text="#F00000" link="#000080" alink="#FF0000" leftmargin="20" topmargin="10">
<%
	Vector vector = ( Vector )request.getAttribute( "memory" );
%>
<br>
<font face="Arial" size="+1" color=#000000 text-align="center"><CENTER>内 存 利 用 率</CENTER></font>
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <hr style="border: 1px dashed #ccc; width: 100%; height: 5px;" />
    <a href="bitWatch.do">比特流量图</a> | <a href="packetWatch.do">分组流量图</a> | <a href="lenWatch.do">包长统计图</a> | <a href="allWatch.do">汇总监测图</a> | <a href="cpuWatch.do">CPU利用率</a> | <b>内存利用率</b> | <a href="tempWatch.do">温度</a> 
<%--| <a href="FlowStatisticQuery.do">流量统计</a>--%>
  </tr>
</table>
<br>
<table width="100%" align="right">
<div id="gallery">
<%
	Hashtable memoryTable=(Hashtable)vector.get(0);
	Iterator  iterator=memoryTable.keySet().iterator();
	while(iterator.hasNext())
	{		
		String text=iterator.next().toString();
		String memload=text+"_memusage.gif";	
		String a[]=text.split("_");
%>
<div class="imagebox"><a href="javascript:dogo('<%= a[0] %>')">
<img src="/file/flow/physicalscan/dat/pic/<%=memload %>"/><br><%= (String)memoryTable.get(text) %></a></div>
<%
	}
	if(memoryTable.size()==0){
%>
<div class="imagebox" align="center"><font style="font-size:10pt"><strong>无华为设备或没有进行监控初始化（仅对华为路由器进行内存测量）</strong></font></div>
<%} %>
</div>
</table>
<div id="responseDiv"></div>
<div id="tabDiv"></div>
</body>
<SCRIPT LANGUAGE="JavaScript">
        var picType="memusage";
        var ip_param="";
        var index_param="";
        function dogo( routerIp,ifindex )
        {
                ip_param=routerIp;
                index_param=ifindex;
                Ext.Ajax.request({
                        url:"fixTimePic.do?routerIP="+routerIp+"&ifIndex="+ifindex+"&picType=memusage",
                        success:function(response, request){
                                showWindow(response.responseText);
                        }
                        });
        }
</SCRIPT>
</html>