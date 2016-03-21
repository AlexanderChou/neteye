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
<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.update.dao.UpdateDAO"%>
<html>
<head>
<%
	//调用java方法，获得当前升级的状态
	String status = UpdateDAO.getUpdateStatus();
	String version = UpdateDAO.getVersionStatus();
	String flag = "0";
	String message = "已打开";
	String buttonInfo = "关闭自动升级功能";
	String versionInfo = "version 091229 release 1.0";
    if(status!=null && status.equals("stop")){
    	message = "已关闭";
    	buttonInfo = "打开自动升级功能";
    	flag = "1";
    }
    if(version!=null && !version.equals("")){
    	versionInfo = version;
    }
%>
<title>数据备份</title>
<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="css/topoInit.css" />
    <script type="text/javascript" src="js/ext-base.js"></script>
    <script type="text/javascript" src="js/ext-all.js"></script>
</head>
<body>
	
	<center>
		<div>
			<div id="showDiv" style="margin-top:10%;">
			</div>
			<div id="msgDiv">		
			</div>
		</div>
	</center>
</body>
<SCRIPT LANGUAGE="JavaScript">
	Ext.onReady(function(){
		var formPanel = new Ext.FormPanel({
			layout:'form',
		    labelAlign: 'left',
		    title: '自动升级',
		    buttonAlign:'right',
		   	//renderTo:"form",
		    bodyStyle:'padding:5px',
		    width: 600,
		    frame:true,
		    labelWidth:80,	
		    defaultType: 'textfield',	    
            items: [{
            		xtype:'textarea',
            		disabled : false,
	        		fieldLabel:'说明',
	        		height:150,
	        		value: '系统目前的版本是：<%=versionInfo%>\n\n清华大学网络中心 版权所有 Copyright 2008-2012,All Rights Reserved \n\n主页:http://www.inetboss.com \n\n自动升级：<%=message%>',                   
	        		anchor:'90%'
        		}]
		  });
		  
		var mk = new Ext.LoadMask("msgDiv", {
			msg: '正在提交数据，请稍候！',
			removeMask: true //完成后移除
		});
		
	  	var buttonPanel = new Ext.Panel({
			id:"button",
			buttonAlign:"center",				
			frame:true,
			buttons:[{
				text:"<%=buttonInfo%>",
				handler:function(){					
					panel.hide();
					mk.show();				
					Ext.Ajax.request({
					url:"json/update.do",
					params:{flag:<%=flag%>},
					success:function(response,request){
						window.location = "autoupdate.do";
					}
				  });
			   }		
			}]				
		});
			
		var panel = new Ext.Panel({
			width: 600,
			renderTo:"showDiv",
			buttonAlign:'left',
			items:[
				formPanel,
				buttonPanel
				]
		});
		
	});
</SCRIPT>
</html>