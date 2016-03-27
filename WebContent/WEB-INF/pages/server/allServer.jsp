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
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page language="java" import="java.util.*"%>
<html>
<head>
<meta HTTP-EQUIV="refresh" Content="300;url='';text-html; charset=UTF-8">
<title>Insert title here</title>
        <link rel="stylesheet" type="text/css" href="css/ext-all.css" />
        <script type="text/javascript" src="js/ext-base.js"></script>
        <script type="text/javascript" src="js/ext-all.js"></script>
        <script type="text/javascript" src="js/servefixTime.js"></script>
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
<body bgcolor="#FFFFFF" text="#000000" leftmargin="20" topmargin="10">
<%
	Vector vector = ( Vector )request.getAttribute( "allServer" );
%>
<br>
<table width="700" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td width="700"><font style="font-size:13pt"><strong>服 务 监 测 图</strong></font></td>
  </tr>
  <tr>
    <td width="700"><img src="/images/line2.gif" width="700" height="20"></td>
  </tr>
</table>
<br>
<table width="90%" align="right">
<div id="gallery">
<%
	Hashtable serverNameTable=(Hashtable)vector.get(0);
	Iterator  iterator=serverNameTable.keySet().iterator();
	while(iterator.hasNext())
	{		
		String text=iterator.next().toString();
		String serverload=text+".gif";	
		String a[]=text.split("_");
		if(a[1].equals("snmp")){
%>
<div class="imagebox" align="center"><a href="javascript:dogo1('<%= a[0] %>','<%= a[1] %>')">
<img src="/file/service/dat/pic/<%=a[0] %>_cpuusage.gif"/><br><%= a[0] %>_CPU</a></div>
<div class="imagebox" align="center"><a href="javascript:dogo2('<%= a[0] %>','<%= a[1] %>')">
<img src="/file/service/dat/pic/<%=a[0] %>_diskusage.gif"/><br><%= a[0] %>_DISK</a></div>
<div class="imagebox" align="center"><a href="javascript:dogo3('<%= a[0] %>','<%= a[1] %>')">
<img src="/file/service/dat/pic/<%=a[0] %>_memusage.gif"/><br><%= a[0] %>_MEM</a></div>
<%
	}else{
%>
<div class="imagebox" align="center"><a href="javascript:dogo('<%= text %>','<%= a[2] %>')">
<img src="/file/service/dat/pic/<%=serverload %>"/><br><%= text %></a></div>
<%			}
		}%>
</div>
</table>
<div id="responseDiv"></div>
<div id="tabDiv"></div>
</body>
<SCRIPT LANGUAGE="JavaScript">
        var url_param="";
        var type_param="";
        var style_param="";
        function dogo(url,type)
        {        		
                url_param=url;
                type_param=type;
                style_param="";
                Ext.Ajax.request({
                        url:"serveTimePic.do?url="+url+"&type="+type,
                        success:function(response, request){
                                showWindow(response.responseText);
                        }
                        });
        }
          function dogo1(url,type)
        {        		
                url_param=url;
                type_param=type;
                style_param="cpuusage";
                Ext.Ajax.request({
                        url:"serveTimePic.do?url="+url+"&type="+type+"&style=cpuusage",
                        success:function(response, request){
                                showWindow(response.responseText);
                        }
                        });
        }
          function dogo2(url,type)
        {        		
                url_param=url;
                type_param=type;
                style_param="diskusage";
                Ext.Ajax.request({
                        url:"serveTimePic.do?url="+url+"&type="+type+"&style=diskusage",
                        success:function(response, request){
                                showWindow(response.responseText);
                        }
                        });
        }
          function dogo3(url,type)
        {        		
                url_param=url;
                type_param=type;
                style_param="memusage";
                Ext.Ajax.request({
                        url:"serveTimePic.do?url="+url+"&type="+type+"&style=memusage",
                        success:function(response, request){
                                showWindow(response.responseText);
                        }
                        });
        }
</SCRIPT>
</html>