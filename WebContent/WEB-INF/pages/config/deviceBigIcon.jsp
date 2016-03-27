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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN "   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel='StyleSheet' href="css/topoInit.css" type="text/css" />
<link type="text/css" href="css/deviceManage.css"  rel="StyleSheet">
<link type="text/css" href="css/ext-all.css"  rel="stylesheet"/>   
<script type="text/javascript" src="js/ext-base.js"></script>
<script type="text/javascript" src="js/ext-all.js"></script>
<script type="text/javascript" src="js/deviceManage.js"></script>
<script language="javascript" src="js/device_port.js"></script>
<script language="javascript" src="js/device_list.js"></script>
<title>设备管理</title>
</head>
<body>
<div id="outer">
	<div id="bodyDiv">
	
			<div id="menu">
				<s:include value="right_menu.jsp"></s:include>
			</div>
		
			<div id="infoDiv">
				<div id='tabDiv'>
				</div>
				<div id='panelDiv' style="background-color:yellow;display:none;">
				</div>
				<div id='buttonDiv'>
				</div>
				<div id="bigIconDiv"">
					
						<div id="bigIconddd">
						<s:form name="myform" id="myid">
						<s:hidden name="submitDevice"></s:hidden>
						<s:hidden name="deviceTypeId"></s:hidden>
						<s:hidden name="style"></s:hidden>
							<s:iterator  value="list" status="st">
								<div id="singleDevice" onmouseover="this.style.backgroundColor='#D6EFFC'" onmouseout="this.style.backgroundColor='#F5FAFC'">
									<a href="equip_view.do?id=<s:property value='id'/>&deviceTypeId=<s:property value='deviceTypeId'/>&style=1">
										<s:if test="deviceTypeId == 1">
											<img src="images/router.png" title="单击编辑路由器信息" style="margin:center;border:0px"/>
										</s:if>
										<s:if test="deviceTypeId == 2">
											<img src="images/switch.png" title="单击编辑交换机由器信息" style="margin:center;border:0px"/>
										</s:if>
									</a><br>
									<input type="checkbox" deviceId=<s:property value="id"/> class="radio" name="device"><s:property value="chineseName"/>		
								</div>
								
							</s:iterator>
								<div id='button' class="buttonDiv">
									<input type="checkbox" class="radio"  onclick="doSelAllDevice(this)">全选
									<input type="button" name="delete" value="删除" onclick="doSubmitDevice(1)"> 
									<input type="button" name="add" value="添加" onclick="addDevice('<s:property value='deviceTypeId'/>',1)"> 
									<input type="button" name="delete" value="返回" onclick="goback()"> 
								</div>
							</s:form>
						</div>
					
				</div>	
				
				<div id="showGrid" style="clear:both;display:none;">
				</div>
				
				<div id="showGrid2" style="clear:both;display:none;">
				</div>
			</div>
		</div>
</div>

<script type="text/javascript">
	if ("${param.status}" != "0" && "${param.status}" != "") {
		document.getElementById("bigIconddd").style.display = "none";
	} else {
		document.getElementById("bigIconddd").style.display = "inline";
	}
</script>
	

<script type="text/javascript">
	var panel = new Ext.Panel({
		title:"大图标",
		items:[
			{
				autoScroll: true, 
				height:document.body.clientHeight*0.95-100,
				contentEl:"bigIconddd"
			},
			{
				autoScroll: true, 
				contentEl:"button"
			}
		]
	});
	
	if ("${param.status}" == "0" || "${param.status}" == "") {
		panel.render("panelDiv");
	}
	
	
	var portGridIsLoad = false;
	var linkGridIsLoad = false;
	var activateIndex = 0;
	if ("${param.status}" != "") {
		activateIndex = "${param.status}";
	}
	var tabs = new Ext.TabPanel({
        renderTo: "tabDiv",
        activeTab: activateIndex,
        width:840,
        height:document.body.clientHeight*0.95-20,
        defaults:{autoScroll: true},
        items:[panel,{
               title: '小图标',
               contentEl:'showGrid',
               listeners :{
               	activate:function(tab) {
               		if (!portGridIsLoad) {
                		createPortGrid();
               			portGridIsLoad = true;
               		}
               		document.getElementById("showGrid").style.display = "inline";
               	}
               }
           },{
               title: '列表',
               contentEl:'showGrid2',
               listeners :{
               	activate:function(tab) {
               		if (!linkGridIsLoad) {
                		createGrid();
               			linkGridIsLoad = true;
               		}
               		document.getElementById("showGrid2").style.display = "inline";
               	}
               }
           }]
    });
</script>
</body>
</html>