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
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN "   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta HTTP-EQUIV="refresh" Content="300;url='allWatch.do';text-html; charset=UTF-8">
<title>Insert title here</title>
        <link rel="stylesheet" type="text/css" href="css/ext-all.css" />
          	<link type="text/css" href="css/topoInit.css"  rel="StyleSheet">
        <script type="text/javascript" src="js/ext-base.js"></script>
        <script type="text/javascript" src="js/ext-all.js"></script>
        <script type="text/javascript" src="js/fixTime.js"></script>
        <script type="text/javascript" src="js/AllWatch.js"></script>
        
        <style type="text/css">
 
  
  div#girdshow {
 text-align:left;
 width:1000px;
 margin:0 auto;
  }
  </style>
  
</head>
<body>
<%

String viewname = ( String )request.getAttribute( "viewname" );
String sv = ( String )request.getAttribute( "submitView" );
		 
%>		 
<br>
<font face="Arial" size="+1" color=#000000 text-align="center"><CENTER>流量汇总监测图</CENTER></font>
<CENTER>
<FORM action="flowEventStatus.do" method=post name=submitView>
选择视图
<SELECT name=submitView size=1>
<s:iterator value="views" status="st" >
	<OPTION value='<s:property value="name"/>_<s:property value="id"/>'><s:property value="name"/></OPTION>
</s:iterator>
</SELECT>
<INPUT type="submit" value="提交" name=postsub>
</FROM>
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <hr style="border: 1px dashed #ccc; width: 100%; height: 5px;" />
    <a href="bitWatch.do">比特流量图</a> | <a href="packetWatch.do">分组流量图</a> | <a href="lenWatch.do">包长统计图</a> | <b>汇总监测图</b> | <a href="FlowStatisticQuery.do">流量统计</a>
  </tr>
</table>

</CENTER>
<br>
	<div id="girdshow" >
</div>

<div id="responseDiv"></div>
<div id="tabDiv"></div>
<div id="inputDiv"></div>
</body>
<SCRIPT LANGUAGE="JavaScript">
		var picType="";
        var ip_param="";
        var index_param="";
        var viewname= "<%= viewname %>";
	function dobit( routerIp,ifindex )
	{
		picType="bit";
		ip_param=routerIp;
        index_param=ifindex;
				Ext.Ajax.request({
          		url:"fixTimePic.do?routerIP="+routerIp+"&ifIndex="+ifindex+"&picType=bit",
          		success:function(response, request){
          			showWindow(response.responseText);
          		}
			});
	
	}
function dopkt( routerIp,ifindex )
	{
		picType="pkt";
		ip_param=routerIp;
		index_param=ifindex;

			Ext.Ajax.request({
			disableCaching : true,				
          		url:"fixTimePic.do?routerIP="+routerIp+"&ifIndex="+ifindex+"&picType=pkt&type=1",
          		success:function(response, request){
          			showWindow(response.responseText);
          		}
			});
	

		
	}
function dolen( routerIp,ifindex )
	{
		picType="len";
		ip_param=routerIp;
        index_param=ifindex;

		Ext.Ajax.request({
          		url:"fixTimePic.do?routerIP="+routerIp+"&ifIndex="+ifindex+"&picType=len&type=2",
          		success:function(response, request){
          			showWindow(response.responseText);
          		}
			});
	}
</SCRIPT>

</html>