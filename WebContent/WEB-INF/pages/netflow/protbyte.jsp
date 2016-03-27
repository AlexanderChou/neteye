<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
<!--<html><head><title>International Gateway Traffic Statistics</title>-->
<%@page language="java" import="java.util.*"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
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
<a href="overview.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">概览</a> | <a href="displayPort.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">端口</a> | <a href="displayProt.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">协议(<b>带宽</b>  <a href="protPkt.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">包速</a>)</a> | <a href="displayFlow.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">流持续时间/大小累计分布</a> | <a href="netFlowSession.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">TopN会话</a>
<p>
<table align="CENTER" valign="TOP" border="0" cellpadding="1" cellspacing="1" width="100%">
<tr>
<td><ul><li>TCP Bytes</ul></td>
</tr>
<tr>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/TCPByteday.png" border=0 alt="TCP last 1 day"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/TCPByte.png" border=0 alt="TCP last 1 week"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/TCPBytemonth.png" border=0 alt="TCP last 1 month"></a></td>
</tr>
<tr>
<td><ul><li>UDP Bytes</ul></td>
</tr>
<tr>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/UDPByteday.png" border=0 alt="UDP last 1 day"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/UDPByte.png" border=0 alt="UDP last 1 week"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/UDPBytemonth.png" border=0 alt="UDP last 1 month"></a></td>
</tr>
<tr>
<td><ul><li>ICMPv6 Bytes</ul></td>
</tr>
<tr>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/ICMPByteday.png"border=0 alt="ICMPv6 last 1 day"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/ICMPByte.png"border=0 alt="ICMPv6 last 1 week"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/ICMPBytemonth.png" border=0 alt="ICMPv6 last 1 month"></a></td>
</tr>
<tr>
<td><ul><li>IPIP Bytes</ul></td>
</tr>
<tr>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/IPByteday.png" border=0 alt="IPIP last 1 day"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/IPByte.png" border=0 alt="IPIP last 1 week"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/IPBytemonth.png" border=0 alt="IPIP last 1 month"></a></td>
</tr>
<tr>
<td><ul><li>PIM Bytes</ul></td>
</tr>
<tr>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/PIMByteday.png" border=0 alt="PIM last 1 day"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/PIMByte.png" border=0 alt="PIM last 1 week"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/PIMBytemonth.png" border=0 alt="PIM last 1 month"></a></td>
</tr>
<tr>
<td><ul><li>IPv6-NoNxt Bytes</ul></td>
</tr>
<tr>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/NoNxtByteday.png"border=0 alt="IPv6-NoNxt last 1 day"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/NoNxtByte.png"border=0 alt="IPv6-NoNxt last 1 week"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/NoNxtBytemonth.png" border=0 alt="IPv6-NoNxt last 1 month"></a></td>
</tr>

</table>
<hr width="100%" color="#000000" size=2>
<p>
<CENTER><a href="mailto:gaolei04@gmail.com">Admin</a></CENTER>
<CENTER>CopyRight&copy2010</CENTER>
<BR>
</body>
</html>
