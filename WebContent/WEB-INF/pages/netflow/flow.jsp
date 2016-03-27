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
<meta http-equiv="Refresh" content="150;"></head>

<body bgcolor="#FFFFFF" text="#F00000" link="#000080" alink="#FF0000">
<font face="Arial" size="+1" color=#F00000><CENTER>NetFlow 流量监控</CENTER></font>
<hr style="border: 1px dashed #ccc; width: 100%; height: 5px;" />
<a href="overview.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">概览</a> | <a href="displayPort.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">端口</a> | <a>协议(<a href="protByte.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">带宽</a>  <a href="protPkt.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">包速</a>)</a> | <b>流持续时间/大小累计分布</b> | <a href="netFlowSession.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">TopN会话</a>
<p>
<table align="CENTER" valign="TOP" border="0" cellpadding="1" cellspacing="1" width="100%">
<tr>
<td>
<ul>
<li>Flow Duration
</ul>
</td>
</tr>
<tr>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/duration.gif" border=0 alt="protocol"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/durationByte.gif" border=0 alt="protocol"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/durationPkt.gif" border=0 alt="other"></a></td>
</tr>
<tr>
<td>
<ul>
<li>Flow Size
</ul>
</td>
</tr>
<tr>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/flowsize.gif" border=0 alt="protocol"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/flowsizeByte.gif" border=0 alt="protocol"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/flowsizePkt.gif" border=0 alt="other"></a></td>
</tr>
</table>
<hr width="100%" color="#000000" size=2>
<p>
<CENTER><a href="mailto:gaolei04@gmail.com">Admin</a></CENTER>
<CENTER>CopyRight&copy2010</CENTER>
<BR>
</body>
</html>
