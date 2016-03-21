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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>视图管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link type="text/css" href="css/view.css"  rel="StyleSheet">
    <link type="text/css" href="css/ext-all.css"  rel="stylesheet"/>   
    <script type="text/javascript" src="js/ext-base.js"></script>
    <script type="text/javascript" src="js/ext-all.js"></script>
	<script type="text/javascript" src="js/view_add.js"></script>
	<script type="text/javascript" src="js/view_manager.js"></script>
</head>
<body>
	<div id="devicetype_header">设备类型</div>
	<div id="devicetype_table">
	<s:form name="myform1">
			<s:hidden id="submitDeviceType" name="submitDeviceType"></s:hidden>
			<s:iterator value="types" status="st">
			<s:if test="name=='router'">
			<div id="device_type" onmouseover="this.style.backgroundColor='#D6EFFC'" onmouseout="this.style.backgroundColor='#F5FAFC'">
					<a href="deviceAdd.do?id=<s:property value="id"/>"><img src="images/router.png" title="单击添加" style="margin:center"/></a><br>
					<input type="checkbox" devicetypeID = <s:property value="id"/> class="radio" name="deviceType"><s:property value="name"/>
				</div>
			</s:if>
			<s:elseif test="name=='switch'">
			<div id="device_type" onmouseover="this.style.backgroundColor='#D6EFFC'" onmouseout="this.style.backgroundColor='#F5FAFC'">
					<a href="deviceAdd.do?id=<s:property value="id"/>"><img src="images/switch.png" title="单击添加" border="2px" style="margin:center" /></a><br>
					<input type="checkbox" devicetypeID = <s:property value="id"/> class="radio" name="deviceType"><s:property value="name"/>
				</div>
			</s:elseif>
			<s:elseif test="name=='server'">
			<div id="device_type" onmouseover="this.style.backgroundColor='#D6EFFC'" onmouseout="this.style.backgroundColor='#F5FAFC'">
					<a href="deviceAdd.do?id=<s:property value="id"/>"><img src="images/sun-pc.png" title="单击添加" border="2px" style="margin:center" /></a><br>
					<input type="checkbox" devicetypeID = <s:property value="id"/> class="radio" name="deviceType"><s:property value="name"/>
				</div>
			</s:elseif>
			<s:elseif test="name=='workstation'">
				<div id="device_type" onmouseover="this.style.backgroundColor='#D6EFFC'" onmouseout="this.style.backgroundColor='#F5FAFC'">
					<a href="deviceAdd.do?id=<s:property value="id"/>"><img src="images/sun-workstation.png" title="单击添加" border="2px" style="margin:center" /></a><br>
					<input type="checkbox" devicetypeID = <s:property value="id"/> class="radio" name="deviceType"><s:property value="name"/>
				</div>
			</s:elseif>
			<s:else>
			<div id="device_type" onmouseover="this.style.backgroundColor='#D6EFFC'" onmouseout="this.style.backgroundColor='#F5FAFC'">
					<a href="deviceAdd.do?id=<s:property value="id"/>"><img src="images/sun-workstation.png" title="单击添加" border="2px" style="margin:center" /></a><br>
					<input type="checkbox" devicetypeID = <s:property value="id"/> class="radio" name="deviceType"><s:property value="name"/>
				</div>
			</s:else>
			</s:iterator>
			<p>
				<input type="checkbox" class="radio" onclick="doSelAllDeviceType(this)">全选
				<input type="button" name="delete" value="删除" onclick="doSubmitDeviceType()"> 
				<input type="button" name="add" value="添加" onclick="addDeviceType()"> 
			</p>
	</s:form>
	</div>
	<div id="tree" style="height:450px;width:300px"></div>
	<div id="tree2" style="height:450px;width:300px"></div>
</body>
</html>