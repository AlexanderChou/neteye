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
 <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    	<meta http-equiv="Refresh" CONTENT="60">
        <title>网络节点流量故障图</title>
        <link rel="stylesheet" type="text/css" href="css/ext-all.css" />
    </head>
   
    <body>
		<table width="85%" border="1" cellspacing="0" cellpadding="0" align="center" bgcolor="#ffffff">
		<s:set name="nodeTyple" value='<s:property value="nodeTyple"/>'/>
		<s:if test="nodeTyple == 1">
		 <tr align="center">
		 	<td>节点<s:property value="chineseName"/>性能</td>
		 </tr>
		 <tr>
				 	<td align="left">CPU利用率：</td>
		 </tr>
		  <tr>
			 	<td width="100%" >
				 	<a href="javascript:dogo('<s:property value="deviceIpv4"/>','9_2_0_0','cpuusage','<s:property value="trafficIp"/>')"><img src='file/flow/physicalscan/dat/pic/<s:property value="deviceIpv4"/>_9_2_0_0_cpuusage.gif'></a>
				 	<a href="javascript:dogo('<s:property value="deviceIpv4"/>','9_1_0_0','cpuusage','<s:property value="trafficIp"/>')"><img src='file/flow/physicalscan/dat/pic/<s:property value="deviceIpv4"/>_9_1_0_0_cpuusage.gif'></a>
			 	</td>
			</tr>
			<tr>
				 <td align="left">温度：</td>
			 </tr>
			 <tr>
				 	<td width="100%">
					 	<a href="javascript:dogo('<s:property value="deviceIpv4"/>','9_2_0_0','temperature','<s:property value="trafficIp"/>')"><img src='file/flow/physicalscan/dat/pic/<s:property value="deviceIpv4"/>_9_2_0_0_temperature.gif'></a>
					 	<a href="javascript:dogo('<s:property value="deviceIpv4"/>','9_1_0_0','temperature','<s:property value="trafficIp"/>')"><img src='file/flow/physicalscan/dat/pic/<s:property value="deviceIpv4"/>_9_1_0_0_temperature.gif'></a>
				 	</td>
			 </tr>
		</s:if>
		<s:elseif test="nodeTyple == 2">
		  <tr align="center">
			 	<td>节点<s:property value="chineseName"/>性能</td>
		  </tr>
		  <tr>
				<td align="left">CPU利用率：</td>
		  </tr>
		  <tr>
			 	<td width="100%" >
				 	<a href="javascript:dogo('<s:property value="deviceIpv4"/>','9216','cpuusage','<s:property value="trafficIp"/>')"><img src='file/flow/physicalscan/dat/pic/<s:property value="deviceIpv4"/>_9216_cpuusage.gif'></a>
				 	<a href="javascript:dogo('<s:property value="deviceIpv4"/>','8704','cpuusage','<s:property value="trafficIp"/>')"><img src='file/flow/physicalscan/dat/pic/<s:property value="deviceIpv4"/>_8704_cpuusage.gif'></a>
			 	</td>
			</tr>
			<tr>
				 	<td align="left">内存利用率：</td>
		 </tr>
		  <tr>
			 	<td width="100%">
				 	<a href="javascript:dogo('<s:property value="deviceIpv4"/>','9216','memusage','<s:property value="trafficIp"/>')"><img src='file/flow/physicalscan/dat/pic/<s:property value="deviceIpv4"/>_9216_memusage.gif'></a>
				 	<a href="javascript:dogo('<s:property value="deviceIpv4"/>','8704','memusage','<s:property value="trafficIp"/>')"><img src='file/flow/physicalscan/dat/pic/<s:property value="deviceIpv4"/>_8704_memusage.gif'></a>
			 	</td>
			</tr>
			<tr>
				 	<td align="left">温度：</td>
		 	</tr>
		 	<tr>
			 	<td width="100%">
				 	<a href="javascript:dogo('<s:property value="deviceIpv4"/>','9216','temperature','<s:property value="trafficIp"/>')"><img src='file/flow/physicalscan/dat/pic/<s:property value="deviceIpv4"/>_9216_temperature.gif'></a>
				 	<a href="javascript:dogo('<s:property value="deviceIpv4"/>','8704','temperature','<s:property value="trafficIp"/>')"><img src='file/flow/physicalscan/dat/pic/<s:property value="deviceIpv4"/>_8704_temperature.gif'></a>
			 	</td>
		 	 </tr>
		</s:elseif>
		 <tr align="center">
		 	<td>节点<s:property value="name"/>下接口流量图及故障图</td>
		 </tr>
		 <s:iterator value="portList" status="st">
				 <tr>
				 	<td align="left">即时流量图：</td>
				 </tr>
				 <tr>
				 	<td>
				 	<a href="javascript:dogo('<s:property value="deviceIpv4"/>','<s:property value="ifindex"/>','bit','<s:property value="trafficIp"/>')"><img src='file/flow/flowscan/dat/pic/<s:property value="deviceIpv4"/>_<s:property value="ifindex"/>_bit.gif'></a>
				 	<a href="javascript:dogo('<s:property value="deviceIpv4"/>','<s:property value="ifindex"/>','pkt','<s:property value="trafficIp"/>')"><img src='file/flow/flowscan/dat/pic/<s:property value="deviceIpv4"/>_<s:property value="ifindex"/>_pkt.gif'></a>
				 	<a href="javascript:dogo('<s:property value="deviceIpv4"/>','<s:property value="ifindex"/>','len','<s:property value="trafficIp"/>')"><img src='file/flow/flowscan/dat/pic/<s:property value="deviceIpv4"/>_<s:property value="ifindex"/>_len.gif'></a>
				 	</td>
				  </tr>
				  <tr><td>ip:<s:property value="ipv4"/></td></tr>
				  <s:set name="mytest" value='<s:property value="ipv4"/>'/>
				  <s:if test="mytest eq ''"> 
				  	  <tr><td>aaa空</td></tr>
					  <tr>
					 	<td align="left">即时故障图：</td>
					 </tr>
					 <tr>
					 	<td>
					 	<a href='lossPic.do?ip=<s:property value="ipv4"/>'><img src='file/fault/dat/pic/<s:property value="ipv4"/>_loss_day.gif'></a>
					 	</td>
					 </tr>
					 <tr>
					 	<td>
					 	<a href='rttPic.do?ip=<s:property value="ipv4"/>'><img src='file/fault/dat/pic/<s:property value="ipv4"/>_rtt_day.gif'></a>
					 	</td>
					 </tr>
				 </s:if>
				 <s:else>
				 	<tr><td>bbb不空</td></tr>
				 </s:else>
		 </s:iterator>
		 </table>
    </body>
    <SCRIPT LANGUAGE="JavaScript">
	<!--
		function dogo( routerIP,ifIndex,picType,trafficIp){
			this.location.href="fixTimePic.do?routerIP="+routerIP+"&ifIndex="+ifIndex+"&picType="+picType+"&trafficIP="+trafficIp;
		}
	//-->
	</SCRIPT>
</html>