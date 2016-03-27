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
	<title>设备分类管理</title>
	<link rel='StyleSheet' href="css/topoInit.css" type="text/css">
	<link type="text/css" href="css/view.css"  rel="StyleSheet">
    <link type="text/css" href="css/ext-all.css"  rel="stylesheet"/>   
    <script type="text/javascript" src="js/ext-base.js"></script>
    <script type="text/javascript" src="js/ext-all.js"></script>
	<script type="text/javascript" src="js/view_add.js"></script>
	<script type="text/javascript" src="js/view_manager.js"></script>
</head>
<body>
	<div id="outer">
		<div id="bodyDiv">
			<div id="menu">
				<s:include value="right_menu.jsp"></s:include>
			</div>
		
			<div id="infoDiv">
				<div id="showDiv"></div>
				<div id="deviceTypeListDiv">
					<s:form name="myform1" id="myid">
							<s:hidden id="submitDeviceType" name="submitDeviceType"></s:hidden>
							<s:iterator value="types" status="st">
							<s:if test="name=='router'">
								<div id="device_type" onmouseover="this.style.backgroundColor='#D6EFFC'" onmouseout="this.style.backgroundColor='#F5FAFC'">
									<a href="devicelistaction.do?status=0&deviceTypeId=<s:property value='id'/>"><img src="images/router.png" title="单击查看 -- <s:property value='name'/>" style="margin:center"/></a><br>
									<s:property value="name"/>
								</div>
							</s:if>
							<s:elseif test="name=='switch'">
							<div id="device_type" onmouseover="this.style.backgroundColor='#D6EFFC'" onmouseout="this.style.backgroundColor='#F5FAFC'">
									<a href="devicelistaction.do?status=0&deviceTypeId=<s:property value="id"/>"><img src="images/switch.png" title="单击查看 -- <s:property value='name'/>" border="2px" style="margin:center" /></a><br>
									<s:property value="name"/>
								</div>
							</s:elseif>
							<s:elseif test="name=='server'">
							<div id="device_type" onmouseover="this.style.backgroundColor='#D6EFFC'" onmouseout="this.style.backgroundColor='#F5FAFC'">
									<a href="devicelistaction.do?status=0&deviceTypeId=<s:property value="id"/>"><img src="images/server.png" title="单击查看 -- <s:property value='name'/>" border="2px" style="margin:center" /></a><br>
									<s:property value="name"/>
								</div>
							</s:elseif>
							<s:elseif test="name=='workstation'">
								<div id="device_type" onmouseover="this.style.backgroundColor='#D6EFFC'" onmouseout="this.style.backgroundColor='#F5FAFC'">
									<a href="devicelistaction.do?status=0&deviceTypeId=<s:property value="id"/>"><img src="images/workstation.png" title="单击查看  -- <s:property value='name'/>" border="2px" style="margin:center" /></a><br>
									<s:property value="name"/>
								</div>
							</s:elseif>
							<s:else>
								<div id="device_type" onmouseover="this.style.backgroundColor='#D6EFFC'" onmouseout="this.style.backgroundColor='#F5FAFC'">
									<a href="devicelistaction.do?status=0&deviceTypeId=<s:property value="id"/>"><img src="images/custom.png" title="单击查看  -- <s:property value='name'/>" border="2px" style="margin:center" /></a><br>
									<input type="checkbox" devicetypeID = "<s:property value="id"/>" class="radio" name="deviceType"/><s:property value="name"/>
								</div>
							</s:else>
							</s:iterator>
					</s:form>
				</div>
				
				
				<div id='buttonDiv'>
					<input type="checkbox" class="radio" onclick="doSelAllDeviceType(this)">全选
					<input type="button" class="button" name="delete" value="删除" onclick="doSubmitDeviceType()"> 
					<input type="button"class="button" name="add" value="添加" onclick="addDeviceType()"> 
				</div>
			</div>
		</div>
	</div>
	

<script type="text/javascript">

	Ext.onReady(function(){
	
		var panel = new Ext.Panel({
			title:"设备类型",
			width:840,
			items:[{
				width:840,
				height:document.body.clientHeight* 0.95 - 55,
				autoScroll:true,
				contentEl:"deviceTypeListDiv"
			},{
				contentEl:"buttonDiv"
			}]
		});
		
		panel.render("showDiv");
	});
</script>

</body>
</html>