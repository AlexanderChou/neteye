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
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<HEAD>
	<TITLE>iNetBoss Home</TITLE>
	<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>
	
	<script type="text/javascript">
			Ext.onReady(function(){
				Ext.QuickTips.init();
				Ext.form.Field.prototype.msgTarget = 'side';
				var deviceTypeStore = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
							url : 'json/deviceTypeList.do'
						}),
						//创建JsonReader读取router记录
						reader : new Ext.data.JsonReader({
							root : 'deviceTypeList',
							fields : ['id','name']
						})
				});				
				deviceTypeStore.load();
				
				var combo = new Ext.form.ComboBox({
			     	fieldLabel: '设备类型',
			     	id:"deviceType",
			     	name:"typeId",
			   		store: deviceTypeStore,
			   		displayField: 'name',
			   		valueField: 'id',
			    	mode : 'remote',
			     	typeAhead: true, //自动将第一个搜索到的选项补全输入
			     	triggerAction: 'all',
			    	emptyText: '${device.deviceType.name}',
			    	hiddenId: '${device.deviceType.id}',
			    	selectOnFocus: true,
			   	 	forceSelection: true,
			   	 	hiddenName: 'typeId',
			   	 	listeners:{
			   	 		select : function(combo, record, index) {
			   	 			combo.value = record.get("id");
			   	 		}
			   	 	}
				});
				
				var simple = new Ext.FormPanel({
			        labelWidth: 75, // label settings here cascade unless overridden
			        url:'deviceUpdateAction.do',
			        frame:true,
			        title: '',
			        bodyStyle:'padding:5px 5px 0',
			        width: 450,
			        defaults: {width: 308},
			        defaultType: 'textfield',
					onSubmit: Ext.emptyFn,
			        submit:function() {
			            this.getForm().getEl().dom.action = this.url;
			            this.getForm().getEl().dom.submit();
			        },
			        items: [{
			                fieldLabel: '主机名称',
			                name: 'deviceName',
			                value:"${chineseName}",
			                allowBlank:false
			            },{
			                fieldLabel: 'IPV4地址',
			                value:"${loopbackIP}",
			                name: 'loopbackIP',
			                regex: /^([0-9]|[0-1]{0,1}[0-9][0-9]|2[0-4][0-9]|25[0-5])\.([0-9]|[0-1]{0,1}[0-9][0-9]|2[0-4][0-9]|25[0-5])\.([0-9]|[0-1]{0,1}[0-9][0-9]|2[0-4][0-9]|25[0-5])\.([0-9]|[0-1]{0,1}[0-9][0-9]|2[0-4][0-9]|25[0-5])$/,   
               				regexText:'IPV4地址不正确'				                
			            },{
			                fieldLabel: 'IPV6地址',
			                value:"${loopbackIPv6}",
			                name: 'loopbackIPv6',
			                regex: /^[0-9,a-f,A-F,:]+$/,   
               				regexText:'IPV6地址不正确'			                
			            },{
			            	layout : 'column',
			                xtype: 'fieldset',
			                title: '服务类型',
			                autoHeight: true,
			                defaultType: 'checkbox', // each item will be a checkbox
			                items: [{
			                	columnWidth : .25,
			                    boxLabel: 'DNS',
			                    name: 'serviceType',
                                inputValue : 'DNS',
                                anchor : '95%'
			                }, {
			                	columnWidth : .25,
			                    labelSeparator: '',
			                    boxLabel: 'Email',
			                    name: 'serviceType',
                                inputValue : 'Email',
                                anchor : '95%'
			                }, {
			                	columnWidth : .25,
			                    labelSeparator: '',
			                    boxLabel: 'FTP',
			                    name: 'serviceType',
                                inputValue : 'FTP',
                                anchor : '95%'
			                }, {
			                	columnWidth : .25,
			                    labelSeparator: '',
			                    boxLabel: 'HTTP',
			                    name: 'serviceType',
                                inputValue : 'HTTP',
                                anchor : '95%'
			                }]
			        },combo,{    
			            	xtype : 'textarea',
			            	fieldLabel : '描          述',
			            	value:"${description}",
			            	name : 'description',
			            	anchor : '90%',
			            	height : 100
			            },
			            new Ext.form.Hidden({
			            	name:"deviceId",
			            	value:"${id}"
			            }),
			            new Ext.form.Hidden({
			            	id:"deviceTypeId",
			            	name:"deviceTypeId",
			            	value:"${deviceTypeId}"
			            })
			        ],
			
			        buttons: [{
			            text: '  保存  ',
			            	handler:function(){
		            			simple.submit();
			        		}
			            },
			            {text: '  返回  ',
			            	handler:function(){
			            		window.location.href="devicelist.do?deviceTypeId=${deviceTypeId}";
			            	}
					        
			            }]
		    	});
		    	if(${dnsFlag}){
		    		var chk=simple.items.get(3).items.get(0);
		    		chk.setValue(true);
		    	}
		    	if(${emailFlag}){
		    		var chk=simple.items.get(3).items.get(1);
		    		chk.setValue(true);
		    	}
		    	if(${ftpFlag}){
		    		var chk=simple.items.get(3).items.get(2);
		    		chk.setValue(true);
		    	}
		    	if(${httpFlag}){
		    		var chk=simple.items.get(3).items.get(3);
		    		chk.setValue(true);
		    	}
		    simple.render("formDiv4");
			});

</script>
</HEAD>
<body>
<div id="formDiv4" style="margin-top:10%;margin-left:30%;"></div>
</body>
</html>