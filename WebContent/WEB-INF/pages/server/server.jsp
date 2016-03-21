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
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>服务器列表</title>
    <link rel='StyleSheet' href="css/topoInit.css" type="text/css">
    <link rel="stylesheet" type="text/css" href="css/ext-all.css" />
    <script type="text/javascript" src="js/ext-base.js"></script>
    <script type="text/javascript" src="js/ext-all.js"></script> 
    <script type="text/javascript" src="js/servefixTime.js"></script>
<script language="javascript">
  function goback(){
 	window.location.href="serverList.do?status=0&deviceTypeId=3";
 }
</script>
</head>
<body>
<div>	
	<table width="70%" border="1" cellspacing="0" cellpadding="2" align="center">	   
		<s:form name="myform1" id="myid">
		<tr align="center">
			<td width="20%">主机名称:<s:property value="device.chineseName"/></td>
			<td colspan="2">IP地址: <s:property value="device.loopbackIP"/></td>			
			</tr>
			<tr align="center">
			<td>服务类型</td>
			<td>状态描述</td>	
			<td>图形显示</td>
			</tr>
			<s:iterator value="serverList" status="st">	
					
				<s:if test="style=='snmp'">
				<tr align="center">
					<td>	<s:property value="style"/></td>
					<td>	<s:textarea name="description" rows="20" cols="50"></s:textarea></td>
					<td><a href="javascript:dogo1('<s:property value="url"/>','<s:property value="style"/>')"><img src="/file/service/dat/pic/<s:property value="device.loopbackIP"/>_cpuusage.gif"></a>
						<a href="javascript:dogo2('<s:property value="url"/>','<s:property value="style"/>')"><img src="/file/service/dat/pic/<s:property value="device.loopbackIP"/>_diskusage.gif"></a>
						<a href="javascript:dogo3('<s:property value="url"/>','<s:property value="style"/>')"><img src="/file/service/dat/pic/<s:property value="device.loopbackIP"/>_memusage.gif"></a>	
					</td>
				</tr>
				</s:if>				
				<s:else>
				<tr align="center">
					<td>	<s:property value="style"/></td>
					<td>	<s:textarea name="description" rows="8" cols="40"></s:textarea></td>
					<td><a href="javascript:dogo('<s:property value="url"/>','<s:property value="style"/>')"><img src="/file/service/dat/pic/<s:property value="url"/>.gif"></a></td>
				</tr>
				</s:else>
			</s:iterator>
		</s:form>
		<tr>
			<td colspan="3" align="right"><input type="button"class="button" name="add" value="返回" onclick="goback()"></td>
		</tr>
		</table>		
</div>
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