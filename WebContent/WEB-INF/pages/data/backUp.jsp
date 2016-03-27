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
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN "   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>数据备份</title>
<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
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
			<div id='showDiv'></div>
			<div id='msgDiv'></div>
		</div>
	</div>
</div>
</body>
<SCRIPT LANGUAGE="JavaScript">
	Ext.onReady(function(){
		var formPanel = new Ext.FormPanel({
			layout:'form',
		    labelAlign: 'left',
		    title: '数据备份',
		    buttonAlign:'right',
		   	//renderTo:"form",
		    bodyStyle:'padding:5px',
		    height:document.body.clientHeight*0.95-55,
		    frame:true,
		    labelWidth:80,	
		    defaultType: 'textfield',	    
            items: [{
            		xtype:'textarea',
            		disabled : false,
	        		fieldLabel:'说明',
	        		height:200,
	        		value: '系统提供两种形式的数据备份功能 \n\n（1）定时备份:\n    每天晚上12：00，系统自动进行数据备份；\n\n（2）任意时刻备份:\n    用户可选择任意时刻，对系统数据进行备份；\n   备份名称可取默认值，用户也可进行更改',                   
	        		anchor:'90%'
        		},{
                fieldLabel: '备份名称',
                id:"backUpName",
                name:"backUpName",
                value:"${name}",
                anchor:'90%'
                }]
		  });
		  
		var mk = new Ext.LoadMask(showDiv, {
			msg: '正在提交数据，请稍候！',
			removeMask: true //完成后移除
		});
		
	  	var buttonPanel = new Ext.Panel({
			id:"button",
			buttonAlign:"center",				
			frame:true,
			buttons:[{
				text:"确定",
				handler:function(){	
				  if(confirm("您确认要进行数据备份吗？")){				
						var backUpName = Ext.get("backUpName").getValue();												
						mk.show();				
						Ext.Ajax.request({					
							url:"json/nameExits.do",
							params:{name:backUpName},
							success:function(response,request){
								if (response.responseText == "ok") {									
									Ext.Ajax.request({
									url:"json/doBackUp.do",
									timeout:"90000",
									method:"post",
									params:{backUpName:backUpName},
									success:function(response, request){
										if (response.responseText == "ok"){										
											mk.hide();
											panel.hide();										
											window.location.href="comeBack.do";
											}else{
											alert("备份失败！");
											mk.hide();
											}
										}
									  });				
								} else {
									alert("该备份名称已经被使用！");
									mk.hide();
								}
							}
					  });
				   }
			   }		
			}]				
		});
			
		var panel = new Ext.Panel({
			height:document.body.clientHeight*0.95 +5,
			width:840,
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