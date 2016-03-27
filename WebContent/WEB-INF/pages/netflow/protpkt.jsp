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
<a href="overview.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">概览</a> | <a href="displayPort.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">端口</a> | <a href="displayProt.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">协议(<a href="protByte.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">带宽</a>  <b>包速</b>)</a> | <a href="displayFlow.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">流持续时间/大小累计分布</a> | <a href="netFlowSession.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">TopN会话</a>
<p>
<table align="CENTER" valign="TOP" border="0" cellpadding="1" cellspacing="1" width="100%">
<tr>
<td><ul><li>TCP Packets</ul></td>
</tr>
<tr>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/TCPPktday.png" border=0 alt="TCP last 1 day"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/TCPPkt.png" border=0 alt="TCP last 1 week"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/TCPPktmonth.png" border=0 alt="TCP last 1 month"></a></td>
</tr>
<tr>
<td><ul><li>UDP Packets</ul></td>
</tr>
<tr>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/UDPPktday.png" border=0 alt="UDP last 1 day"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/UDPPkt.png" border=0 alt="UDP last 1 week"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/UDPPktmonth.png" border=0 alt="UDP last 1 month"></a></td>
</tr>
<tr>
<td><ul><li>ICMPv6 Packets</ul></td>
</tr>
<tr>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/ICMPPktday.png"border=0 alt="ICMPv6 last 1 day"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/ICMPPkt.png"border=0 alt="ICMPv6 last 1 week"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/ICMPPktmonth.png" border=0 alt="ICMPv6 last 1 month"></a></td>
</tr>
<tr>
<td><ul><li>IPIP Packets</ul></td>
</tr>
<tr>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/IPPktday.png" border=0 alt="IPIP last 1 day"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/IPPkt.png" border=0 alt="IPIP last 1 week"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/IPPktmonth.png" border=0 alt="IPIP last 1 month"></a></td>
</tr>
<tr>
<td><ul><li>PIM Packets</ul></td>
</tr>
<tr>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/PIMPktday.png" border=0 alt="PIM last 1 day"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/PIMPkt.png" border=0 alt="PIM last 1 week"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/PIMPktmonth.png" border=0 alt="PIM last 1 month"></a></td>
</tr>
<tr>
<td><ul><li>IPv6-NoNxt Packets</ul></td>
</tr>
<tr>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/NoNxtPktday.png"border=0 alt="IPv6-NoNxt last 1 day"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/NoNxtPkt.png"border=0 alt="IPv6-NoNxt last 1 week"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/NoNxtPktmonth.png" border=0 alt="IPv6-NoNxt last 1 month"></a></td>
</tr>

</table>
<hr width="100%" color="#000000" size=2>
<p>
<CENTER><a href="mailto:gaolei04@gmail.com">Admin</a></CENTER>
<CENTER>CopyRight&copy2010</CENTER>
<BR>
</body>
</html>
