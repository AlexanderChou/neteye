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
	<title>设备分类管理</title>
	<link rel='StyleSheet' href="css/topoInit.css" type="text/css">
    <link type="text/css" href="css/ext-all.css"  rel="stylesheet"/>   
    <script type="text/javascript" src="js/ext-base.js"></script>
    <script type="text/javascript" src="js/ext-all.js"></script>
</head>
<body>

	<div id="outer">
		<div id="bodyDiv">
			<div id="menu">
				<s:include value="right_menu.jsp"></s:include>
			</div>
		
			<div id="infoDiv">
				<div id="showDiv" >
				</div>
				<div id="msgDiv" style='position:absolute;top:150px;left:200px;'>		
				</div>
				<div id="buttonDiv" >
					<center>
						<input type="button" onclick="initConfig()" value= "监控服务初始化" />
					</center>
				</div>
			</div>
		</div>
	</div>



<SCRIPT LANGUAGE="JavaScript">
	var mk = new Ext.LoadMask("msgDiv", {
		msg: '正在提交数据，请稍候！',
		removeMask: true //完成后移除
	});
	Ext.onReady(function(){
		var formPanel = new Ext.FormPanel({
			layout:'form',
		    labelAlign: 'left',
		   	//renderTo:"form",
		    labelWidth:80,	
		    bodyStyle:"background-color:#DFE8F6;",
		    height:document.body.clientHeight* 0.95-55,
		    defaultType: 'textfield',	    
            items: [{
            		xtype:'textarea',
            		disabled : false,
	        		fieldLabel:'说明',
	        		height:250,
	        		value: '对设备故障及性能监控，系统提供两种配置接口 \n\n(1)监控服务初始化：系统默认对那些拥有IP地址的设备进行故障监控；对有链路的端口进行流量采集和性能监控\n\n(2)自定义配置：分类管理-》路由器或交换机-》端口列表-》选择待监控的端口;系统仅会对选择的端口进行故障监控和流量采集',                   
	        		anchor:'90%'
        		}]
		  });
		  
		var panel = new Ext.Panel({
			width:840,
			title: '监控服务初始化',
			height:document.body.clientHeight* 0.95 + 5,
			renderTo:"showDiv",
			items:[
				formPanel,
				{
					contentEl:"buttonDiv"
				}
				]
		});
		
	});
	
	function initConfig(){
		if(confirm("您确认要进行监控初始化吗？确定后以前的所有数据会被覆盖！")){				
			mk.show();				
			Ext.Ajax.request({
			url:"faultjson/toInitConfig.do",
			method:"post",
			success:function(response,request){
				alert("配置成功！");
			}
		  });
	   }
	}
</SCRIPT>

</body>
</html>
