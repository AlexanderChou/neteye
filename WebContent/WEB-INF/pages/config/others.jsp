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
<head>
<link type="text/css" href="css/otherInfo.css"  rel="StyleSheet">
<title>设备信息</title>
	<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>

	<script type="text/javascript">
			Ext.onReady(function(){
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
			        width: 400,
			        defaults: {width: 280},
			        defaultType: 'textfield',
					onSubmit: Ext.emptyFn,
			        submit:function() {
			            this.getForm().getEl().dom.action = this.url;
			            this.getForm().getEl().dom.submit();
			        },
			        items: [{
			                fieldLabel: '名称',
			                name: 'deviceName',
			                value:"${chineseName}",
			                allowBlank:false
			            },{
			                fieldLabel: 'Read Community',
			                inputType:'password',
			                value:"${readCommunity}",
			                name: 'chineseName'
			            },{
			                fieldLabel: 'IPV4地址',
			                value:"${loopbackIP}",
			                name: 'loopbackIP'
			                
			            },{
			                fieldLabel: 'IPV6地址',
			                value:"${loopbackIPv6}",
			                name: 'loopbackIPv6',
			                regex: /^[0-9,a-f,A-F,:]+$/,   
               				regexText:'IPV6地址不正确'			                
			            },combo,{
			            	xtype : 'textarea',
			            	fieldLabel : '备注',
			            	value:"${description}",
			            	name : 'description',
			            	anchor : '95%',
			            	height : 100
			            	},
			            new Ext.form.Hidden({
			            	name:"deviceId",
			            	value:"${id}"
			            }),
			            new Ext.form.Hidden({
			            	id:"deviceTypeId",
			            	name:"deviceTypeId",
			            	value:"${device.deviceType.id}"
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
			            		var id = '${device.deviceType.id}';
			            		window.location.href="devicelist.do?deviceTypeId=${deviceTypeId}";
			            	}
					        
			            }]
		    	});
		
		    simple.render("formDiv3");

			});
		</script>

</head>
<body>
<div id="formDiv3" style="margin-top:10%;margin-left:30%;"></div>

</body>
</html>