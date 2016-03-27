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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报警信息</title>
<link href="css/alarm.css" rel="stylesheet" type="text/css" />
<SCRIPT type="text/javascript" src = "js/jquery.min.js"></SCRIPT>
<STYLE type="text/css">

	body {
		text-align :center;
	}

</STYLE>
</head>
<body>
	<div id=content style="margin-top:20px;">
	  <s:form name="myform">
		<center>
		<s:iterator value="alarms" status="st">
			<div class="alam_box">
			<div class="l srr_tt" id="srr_tt_${st.index }" >
				<div class="l mt30 " style="width:15px;"></div>
				<div class="q_htq2 p10 tac"><img src="images/xx_xtxx.gif" /></div>
				<div class="wgbr_r" style="width:630px;">
					<div>
						<div class="l"><b><img src='images/ico_mm_s.gif' width=16> 事件报警：</b></div>
						<div class="r c9"><s:property value="occurTime"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:delAlarm('<s:property value="id"/>', ${st.index });" class="gb" title="删除">×</a></div>
						<div class="c"></div>
					</div>				
					<div class="l">
						<s:set name="IP" value="<s:property value='objIPv4'/>"/>
						
						<ul>
							<li>
								事件类型：<s:property value="typeValue"/>
							</li>
							
							<li>
								事件发生地：
									<s:if test="#IP != ''">
										<p style="margin-left:25px;"/>IPv4地址为<s:property value="objIPv4"/>
										
											，名称为<s:property value="objName"/>
										<s:if test="objType == 'device'">
											的设备
										</s:if>
									</s:if>
									<s:else>
										<div style="width:23em;">IPv6地址为<s:property value="objIPv6"/>，名称为<s:property value="objName"/>的设备
									</s:else>
							</li>
							
							<li>
								事件发生时间：<s:property value="occurTime"/>
							</li>
							
							
							<li>
								事件描述：<s:property value="content"/>
							</li>
						</ul>
						
						
					</div>
					<div class="c"></div>
				</div>
			</div>
			</div>
		</s:iterator>
		</center>
		<div class="c"></div>
		<div class="w265 alam_box">
			<div class="c9 mt5" id="divsysmsgclearall"><a href="javascript:clearAlarms('alarm');" class="sl" title="清空全部报警消息">清空全部报警消息</a></div>
		</div>
	  </s:form>
	</div>
</body>
<SCRIPT LANGUAGE="JavaScript">
<!--

	function delAlarm(id, divId) {
		$("#srr_tt_" + divId).remove();
		$.post("delAlarm.do?alarmId="+id);
	}
	
	function clearAlarms(flag) {
		myform.action = "clearAlarms.do";
		myform.submit();
	}
	
//-->
</SCRIPT>
</html>