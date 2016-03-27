<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
<!--<html><head><title>International Gateway Traffic Statistics</title>-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<p>
<hr style="border: 1px dashed #ccc; width: 100%; height: 5px;" />
<!--<hr width="100%" color="#000000" size=2>-->
<b>概览</b> | <a href="displayPort.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">端口</a> | <a href="displayProt.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">协议(<a href="protByte.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">带宽</a>  <a href="protPkt.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">包速</a>)</a> | <a href="displayFlow.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">流持续时间/大小累计分布</a> | <a href="netFlowSession.do?IP=<s:property value='IP'/>&port=<s:property value='port'/>">TopN会话</a>
<table align="CENTER" valign="TOP" border="0" cellpadding="1" cellspacing="1" width="100%">
<tr>
<td><ul><li>Bytes</ul></td>
</tr>
<tr>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/byteday.png" border=0 alt="last 1 days"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/byte.png" border=0 alt="last 1 week"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/bytemonth.png" border=0 alt="last 1 months"></a></td>
</tr>
<tr>
<td><ul><li>Flows</ul></td>
</tr>
<tr>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/flowday.png" border=0 alt="last 1 days"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/flow.png" border=0 alt="last 1 week"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/flowmonth.png" border=0 alt="last 1 months"></a></td>
</tr>
<tr>
<td><ul><li>Packets</ul></td>
</tr>
<tr>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/pktday.png" border=0 alt="last 1 days"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/pkt.png" border=0 alt="last 1 week"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/pktmonth.png" border=0 alt="last 1 months"></a></td>
</tr>
<tr>
<td><ul><li>Average Flow Length</ul></td>
</tr>
<tr>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/averageFlowLengthday.png" border=0 alt="last 1 days"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/averageFlowLength.png" border=0 alt="last 1 week"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/averageFlowLengthmonth.png" border=0 alt="last 1 months"></a></td>
</tr>
<tr>
<td><ul><li>Average Flow Size</ul></td>
</tr>
<tr>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/averageFlowSizeday.png" border=0 alt="last 1 days"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/averageFlowSize.png" border=0 alt="last 1 week"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/averageFlowSizemonth.png" border=0 alt="last 1 months"></a></td>
</tr>
<tr>
<td><ul><li>Average Packet Size</ul></td>
</tr>
<tr>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/averagePktSizeday.png" border=0 alt="last 1 days"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/averagePktSize.png" border=0 alt="last 1 week"></a></td>
<td><a><IMG src="http://<s:property value='IP'/>:<s:property value='port'/>/pic/averagePktSizemonth.png" border=0 alt="last 1 months"></a></td>
</tr>
</table>
<hr width="100%" color="#000000" size=2>
<p>
<CENTER><a href="mailto:gaolei04@gmail.com">Admin</a></CENTER>
<CENTER>CopyRight&copy2010</CENTER>
<BR>
</body>
</html>
