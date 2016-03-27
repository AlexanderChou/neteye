<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
<!--<html><head><title>International Gateway Traffic Statistics</title>-->
<%@page language="java" import="java.util.*"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title>NetFlow Monitor</title>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Refresh" content="150; "></head>

<body bgcolor="#FFFFFF" text="#F00000" link="#000080" alink="#FF0000">
<font face="Arial" size="+1" color=#F00000><CENTER>NetFlow 流量监控</CENTER></font>
<hr style="border: 1px dashed #ccc; width: 100%; height: 5px;" />
<a href="overview.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">概览</a> | <a href="displayPort.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">端口</a> | <b>协议(<a href="protByte.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">带宽</b>  <a href="protPkt.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">包速</a>) | <a href="displayFlow.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">流持续时间/大小累计分布</a> | <a href="netFlowSession.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">TopN会话</a>
<p>
<table align="CENTER" valign="TOP" border="0" cellpadding="1" cellspacing="1" width="100%">
<tr>
<td>
<table>
<tr>
<td><ul><li>Protocol Bytes Pie</ul></td>
<td><ul><li>Protocol Packets Pie</ul></td>
<td><ul><li>Protocol Bytes Bar</ul></td>
</tr>
<tr>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/protstat.gif" border=0 alt="protocol"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/protstatPkt.gif" border=0 alt="protocol"></a></td>
</tr>
<tr>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/otherstat.gif" border=0 alt="other"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/otherstatPkt.gif" border=0 alt="other"></a></td>
</tr>
<tr>
<td>
<ul>
<li><a><a href="protByte.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">Protocol Bytes</a>
</ul>
</td>
<td>
<ul>
<li><a><a href="protPkt.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">Protocol Packets</a>
</ul>
</td>
</tr>
</table>
</td>
<td>
<table>
<tr>
<td rowspan="3"><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/protbar.gif"border=0 alt="topN protocol"></a></td>
</tr>
</table>
</td>
</tr>



</table>
<hr width="100%" color="#000000" size=2>
<p>
<CENTER><a href="mailto:gaolei04@gmail.com">Admin</a></CENTER>
<CENTER>CopyRight&copy2010</CENTER>
<BR>
</body>
</html>
