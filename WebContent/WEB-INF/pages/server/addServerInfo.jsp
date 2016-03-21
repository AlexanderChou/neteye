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
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<HEAD>
	<TITLE>iNetBoss Home</TITLE>
	<META http-equiv=Content-Type content="text/html; charset=utf-8">
	<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
<script type="text/javascript" src="js/ext-base.js"></script>
<script type="text/javascript" src="js/ext-all.js"></script>
</HEAD>
<script language="javascript">
  function goback(){
 	window.location.href="serverList.do?deviceTypeId=3";
  }
  function goAdd(){
 	window.location.href="serviceAdd.do";
  }

</script>
<body>

<div id="showDiv" style='position:absolute;top:80px;left:200px;'>	
		
</div>
<div id="msgDiv" style='position:absolute;top:150px;left:200px;'>	
		
</div>

<script type="text/javascript">
	Ext.onReady(function(){
	Ext.QuickTips.init(); 
	Ext.form.Field.prototype.msgTarget = 'side'; 	
		var formPanel = new Ext.FormPanel({
			id:"form",
		    labelAlign: 'left',
		    title: '添加设备',
		    buttonAlign:'right',
		   	//renderTo:"form",
		    bodyStyle:'padding:5px',
		    width: 600,
		    frame:true,
		    labelWidth:80,
		    items: [{
		        layout:'column',   //定义该元素为布局为列布局方式
		        border:false,
		        labelSeparator:'：',
		        items:[{
		            columnWidth:.5,  
		            layout: 'form',
		            border:false,
		            items: [{                    
		                cls : 'key',
		                xtype:'textfield',
		                fieldLabel: '主机名称',
		                id:"chineseName",
		                name:"chineseName",
		                anchor:'90%'
		            },{ //select 
						cls : 'key',
		                xtype:'textfield',
		                fieldLabel: ' IP   地   址',
		                id:"IP",
		                name:"IP",
		                allowBlank:false,
		                emptyText:'请输入IP地址…',
		                blankText:"IP地址不能为空，请填写",
		                anchor:'90%'
					}]
		        },{
		            columnWidth:.5,  
		            layout: 'form',
		            border:false,
		            items: [{
		           		 cls : 'key',
		                xtype:'textfield',
		                fieldLabel: '英文名称',
		                id:"name",
		                name:"name",
		                anchor:'90%'                    
		                
		            },{ //select 
						cls:'key',
		                xtype:'textfield',
		                fieldLabel: '主机描述',
		                id:"description",
		                name:"description",
		                anchor:'90%'
					}]
		        }       
		        ]
		    },
		    {
            xtype:'fieldset',
            checkboxToggle:true,
            checkboxName:'snmp',
            title: 'snmp',
            autoHeight:true,
            defaults: {width: 210},
            defaultType: 'textfield',
            collapsed: true,
            items :[{
                    fieldLabel: 'snmpIP地址',
                    name: 'snmpIp'
                },{
                    fieldLabel: 'community',
                    name: 'community'
                }
            ]
        },
		    {
            xtype:'fieldset',
            checkboxToggle:true,
            checkboxName:'http',
            title: 'http',
            autoHeight:true,
            defaults: {width: 210},
            defaultType: 'textfield',
            collapsed: true,
            items :[{
                    fieldLabel: ' U R L',
                    name: 'httpUrl'
                },{
                    fieldLabel: '端口号',
                    name: 'httpPort',
                    value:'80'
                }
            ]
        },
		    {
            xtype:'fieldset',
            checkboxToggle:true,
            checkboxName:'smtp',
            title: 'smtp',
            autoHeight:true,
            defaults: {width: 210},
            defaultType: 'textfield',
            collapsed: true,
            items :[{
                    fieldLabel: '服务器',
                    name: 'smtpServer'
                },{
                    fieldLabel: 'Source',
                    name: 'smtpSource'
                },{
                    fieldLabel: '端口号',
                    name: 'smtpPort',
                    value:'25'
                }, {
                    fieldLabel: 'destination',
                    name: 'smtpDestination'
                }
            ]
        },
		    {
            xtype:'fieldset',
            checkboxToggle:true,
            checkboxName:'pop3',
            title: 'pop3',
            autoHeight:true,
            defaults: {width: 210},
            defaultType: 'textfield',
            collapsed: true,
            items :[{
                    fieldLabel: 'pop3服务器',
                    name: 'pop3Server'
                },{
                    fieldLabel: 'pop3端口号',
                    name: 'pop3Port',
                    value:'110'
                },{
                    fieldLabel: '用 户 名',
                    name: 'pop3Username'
                }, {
                    fieldLabel: '密  码',
                    name: 'pop3Passwd'
                }
            ]
        },
		    {
            xtype:'fieldset',
            checkboxToggle:true,
            checkboxName:'ftp',
            title: 'ftp',
            autoHeight:true,
            defaults: {width: 210},
            defaultType: 'textfield',
            collapsed: true,
            items :[{
                    fieldLabel: '服务器',
                    name: 'ftpServer'
                },{
                    fieldLabel: '端口号',
                    name: 'ftpPort',
                    value:'21'
                },{
                    fieldLabel: '用户名',
                    name: 'ftpUsername'
                }, {
                    fieldLabel: '密 码',
                    name: 'ftpPasswd'
                }, {
                    fieldLabel: 'URL',
                    name: 'ftpUrl'
                }
            ]
        },
		    {
            xtype:'fieldset',
            checkboxToggle:true,
            checkboxName:'dns',
            title: 'dns',
            autoHeight:true,
            defaults: {width: 210},
            defaultType: 'textfield',
            collapsed: true,
            items :[{
                    fieldLabel: '服务器',
                    name: 'dnsServer'
                },{
                    fieldLabel: '端口号',
                    name: 'dnsPort',
                    value:'53'
                }
            ]
        }
		    ]
		    
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
					text:"保存",
					handler:function(){	
						var ip = Ext.getCmp("IP").getValue();
						if(ip == "") {
	            			alert("IP 不能为空！");
	            			return;
	            		}
						mk.show();
						formPanel.form.submit({
							url:"json/serverAdd.do",
							timeout:"90000",
							method:"post",
							success:function(form,action){
							    Ext.Msg.buttonText.yes='确定';
	   							Ext.Msg.buttonText.no="取消";
								Ext.Msg.buttonText.cancel="继续添加";
	                           var message=Ext.Msg.show({
										title:'处理结果 ',								
										buttons:Ext.MessageBox.YESNOCANCEL,
										msg:   action.result.mesg, 
	                                   	fn:function(btn){
											if(btn=='yes'){
												goback();
											}
											if(btn=='no'){
												formPanel.form.reset(); 
												message.close();
											}
											if(btn=='cancel'){
												panel.hide();
												goAdd();
											}
										}
									});
						 },
						 failure:function(form,action){
								Ext.Msg.buttonText.yes='确定';
	   							Ext.Msg.buttonText.no="取消";
								Ext.Msg.buttonText.cancel="继续添加";
	                           var message=Ext.Msg.show({
										title:'处理结果 ',								
										buttons:Ext.MessageBox.YESNOCANCEL,
										msg:   action.result.mesg, 
	                                   	fn:function(btn){
											if(btn=='yes'){
												goback();
											}
											if(btn=='no'){
												formPanel.form.reset(); 
												message.close();
											}
											if(btn=='cancel'){
											    panel.hide();
												goAdd();
											}
										}
									});
	                             }
							});
					}
				},{
					text:"返回",
					handler:function(){
						goback();	
					}
				}]				
			});
			
		var panel = new Ext.Panel({
			width: 600,
			renderTo:"showDiv",
			buttonAlign:'center',
			items:[
				formPanel,
				buttonPanel
				]
		});
		
	});	

</script>

</body>
</html>