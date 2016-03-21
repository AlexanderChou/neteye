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
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN " "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>网络整体流量</title>
    <link rel='StyleSheet' href="css/topoInit.css" type="text/css">
</head>
<body>

	<font face="Arial" size="+1" color=#F00000><CENTER>网络整体流量统计即时显示</CENTER></font>
	<p>
	<hr style="border: 1px dashed #ccc; width: 100%; height: 5px;" />
	<!--<hr width="100%" color="#000000" size=2>-->
	<b>概览</b> | <a href="javascript:freshImg('bps')">BPS</a> | <a href="javascript:freshImg('pps')">PPS</a> | <a href="javascript:freshImg('pkt')">pktlen分布</a>| <a href="javascript:freshImg('ttl')">ttl分布</a> | <a href="javascript:freshImg('port')">指定port流量分布</a> | <a href="javascript:freshImg('protocol')">指定 protocol流量分布</a> | <a href="totalPort.do">TOPN port 流量分布</a>| <a href="totalHotsite.do">TOPN 站点情况统计</a>| <a href="totalProtocol.do">TOPN protocol 流量分布</a>| <a href="totalTtl.do">Ttl 流量分布</a>| <a href="totalPktlen.do">pktlen分布</a>
	<p>
	<center>
		<table>
			<tr>
				<td><img id="img1" /></td>
				<td><img id="img2" /></td>
			</tr>
			<tr>
				<td><img id="img3" /></td>
				<td><img id="img4" /></td>
			</tr>
		</table>
	</center>
	
	<script type="text/javascript">
		var ip = "${IP}";
		var port = "${port}";
		var itemName = "${itemName}";
		function freshImg(name) {
			document.location.href = "netUnityFlow.do?itemName=" + name;
		}


		function showImg(itemName){
			if (!itemName) {
				itemName = "bps";
			}
			var img1 = $("img1");
			var img2 = $("img2");
			var img3 = $("img3");
			var img4 = $("img4");

			img1.src = ip + port + "/graph/" + itemName + "_hour.gif?" + new Date() ;
			img2.src = ip + port + "/graph/" + itemName + "_day.gif?"+ new Date();
			img3.src = ip + port + "/graph/" + itemName + "_week.gif?"+ new Date();
			img4.src = ip + port + "/graph/" + itemName + "_month.gif?"+ new Date();
		}
		
		
		function $(id) {
			var e = document.getElementById(id);
			return e;
		}

		showImg(itemName);
		//setInteval(1000 * 60 *5);
	</script>
</body>
</html>
