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
   <link rel="stylesheet" type="text/css" href="css/ext-all.css" />
</head>
<body bgcolor="#FFFFFF" link="#000080" alink="#0000FF" leftmargin="20" topmargin="10">

	<font face="Arial" size="+1" color=#000000 text-align="center"><CENTER>流量曲线</CENTER></font>
	<p>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" align="right">
  <tr>
	<hr style="border: 1px dashed #ccc; width: 100%; height: 5px;" />
	<!--<hr width="100%" color="#000000" size=2>-->
	<b><font color=#0000FF >汇总</font></b> | <a href="netUnityFlow.do?itemName=bps">BPS</a> | <a href="netUnityFlow.do?itemName=pps">PPS</a> | <a href="netUnityFlow.do?itemName=pkt">包长</a>| <a href="netUnityFlow.do?itemName=protocol">协议</a>| <a href="netUnityFlow.do?itemName=port">端口</a> | <a href="netUnityFlow.do?itemName=ttl">TTL分布</a> 
	<p>
	  </tr>
</table>
	<center>
		<table>
			<tr align="center">
				<td ><img id="img21" /><img id="img22" /></td>
			</tr>
			<tr align="center">
				<td><img id="img23" /><img id="img24" /><td>
			</tr>
			<tr align="center">
				<td><img id="img25" /><img id="img26" /></td>
			</tr>
			<tr align="center">
				<td><img id="img31" /> <img id="img32" /></td>
			</tr>
			<tr align="center">
				<td><img id="img33" /> <img id="img34" /></td>
			</tr>
			<tr align="center">
				<td><img id="img35" /> <img id="img36" /></td>
			</tr>
			<tr align="center">
				<td><img id="img41" /> <img id="img42" /></td>
			</tr>
			<tr align="center">
				<td><img id="img43" /> <img id="img44" /></td>
			</tr>
			<tr align="center">
				<td><img id="img45" /> <img id="img46" /></td>
			</tr>
		</table>
	</center>
	
	<script type="text/javascript">
		var ip = "${IP}";
		var port = "${port}";
		
		var itemName1 ="bps";
		var itemName2 ="pps";
		var itemName3 ="pkt";
		var itemName4 ="ttl";
		var itemName5 ="port";
		var itemName6 ="protocol";
		var img21 = $("img21");
		var img22 = $("img22");
		var img23 = $("img23");
		var img24 = $("img24");
		var img25 = $("img25");
		var img26 = $("img26");
		var img31 = $("img31");
		var img32 = $("img32");
		var img33 = $("img33");
		var img34 = $("img34");
		var img35 = $("img35");
		var img36 = $("img36");
		var img41 = $("img41");
		var img42 = $("img42");
		var img43 = $("img43");
		var img44 = $("img44");
		var img45 = $("img45");
		var img46 = $("img46");

		
		img21.src = "http://"+ip + port + "/graph/" + itemName1 + "_day.gif?"+ new Date();
		img31.src = "http://"+ip + port + "/graph/" + itemName1 + "_week.gif?"+ new Date();
		img41.src = "http://"+ip + port + "/graph/" + itemName1 + "_month.gif?"+ new Date();
	
		
		img22.src = "http://"+ip + port + "/graph/" + itemName2 + "_day.gif?"+ new Date();
		img32.src = "http://"+ip + port + "/graph/" + itemName2 + "_week.gif?"+ new Date();
		img42.src = "http://"+ip + port + "/graph/" + itemName2 + "_month.gif?"+ new Date();
		
		
		img23.src = "http://"+ip + port + "/graph/" + itemName3 + "_day.gif?"+ new Date();
		img33.src = "http://"+ip + port + "/graph/" + itemName3 + "_week.gif?"+ new Date();
		img43.src = "http://"+ip + port + "/graph/" + itemName3 + "_month.gif?"+ new Date();
		
		
		img24.src = "http://"+ip + port + "/graph/" + itemName4 + "_day.gif?"+ new Date();
		img34.src = "http://"+ip + port + "/graph/" + itemName4 + "_week.gif?"+ new Date();
		img44.src = "http://"+ip + port + "/graph/" + itemName4 + "_month.gif?"+ new Date();
		
		
		img25.src = "http://"+ip + port + "/graph/" + itemName5 + "_day.gif?"+ new Date();
		img35.src = "http://"+ip + port + "/graph/" + itemName5 + "_week.gif?"+ new Date();
		img45.src = "http://"+ip + port + "/graph/" + itemName5 + "_month.gif?"+ new Date();
		
		
		img26.src = "http://"+ip + port + "/graph/" + itemName6 + "_day.gif?"+ new Date();
		img36.src = "http://"+ip + port + "/graph/" + itemName6 + "_week.gif?"+ new Date();
		img46.src = "http://"+ip + port + "/graph/" + itemName6+ "_month.gif?"+ new Date();
		
		function $(id) {
			var e = document.getElementById(id);
			return e;
		}

		//setInteval(1000 * 60 *5);
	</script>
</body>
</html>
