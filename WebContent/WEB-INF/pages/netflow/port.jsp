<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title> NetFlow Monitor</title>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Refresh" content="150;"></head>

<body bgcolor="#FFFFFF" text="#F00000" link="#000080" alink="#FF0000">
<font face="Arial" size="+1" color=#F00000><CENTER>NetFlow 流量监控</CENTER></font>
<hr style="border: 1px dashed #ccc; width: 100%; height: 5px;" />
<a href="overview.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">概览</a> | <b>端口</b> | <a href="displayProt.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">协议(<a href="protByte.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">带宽</a>  <a href="protPkt.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">包速</a>)</a> | <a href="displayFlow.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">流持续时间/大小累计分布</a> | <a href="netFlowSession.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">TopN会话</a>
<p>
<table align="CENTER" valign="TOP" border="0" cellpadding="1" cellspacing="1" width="100%">
<tr>
<td><ul><li>Port Bytes</ul></td>
<td><ul><li>Port Packets</ul></td>
<td><ul><li>Port Bytes Bar</ul></td>
</tr>
<tr>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/srcportstat.gif" border=0 alt="src port"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/srcportstatPkt.gif" border=0 alt="src port"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/srcportbar.gif"border=0 alt="src port"></a></td>
</tr>
<tr>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/dstportstat.gif" border=0 alt="dst port"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/dstportstatPkt.gif" border=0 alt="dst port"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/dstportbar.gif" border=0 alt="dst port"></a></td>
</tr>
</table>
<hr width="100%" color="#000000" size=2>
<p>
<CENTER><a href="mailto:gaolei04@gmail.com">Admin</a></CENTER>
<CENTER>CopyRight&copy2010</CENTER>
<BR>
</body>
</html>
