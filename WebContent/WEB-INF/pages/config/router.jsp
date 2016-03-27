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
<head>
<title>路由器详细信息</title>
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
				
				var simple = new Ext.FormPanel({
			        labelWidth: 75, // label settings here cascade unless overridden
			        url:'deviceUpdateAction.do',
			        frame:true,
			        title: '节点${device.name}的详细信息',
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
			                fieldLabel: '名称',
			                name: 'deviceName',
			                value:"${device.name}",
			                allowBlank:false
			            },{
			                fieldLabel: '中文名称',
			                value:"${device.chineseName}",
			                name: 'chineseName'
			            },{
			                fieldLabel: '序列号',
			                value:"${device.serial}",
			                name: 'serial'
			            },{
			                fieldLabel: 'SNMP版本号',
			                value:"${device.snmpVersion}",
			                name: 'snmpVersion',
			                editable:false
			            },{
			                fieldLabel: 'IPV4地址',
			                value:"${device.loopbackIP}",
			                name: 'loopbackIP',
			                editable:false
			            },{
			                fieldLabel: 'IPV6地址',
			                value:"${device.loopbackIPv6}",
			                name: 'loopbackIPv6',
			                editable:false
			            },{
			                fieldLabel: '存放地址',
			                value:"${device.location}",
			                name: 'location'
			                
			            },{
			                fieldLabel: '生产厂商',
			                value:"${device.productor}",
			                name: 'productor'
			                
			            },{
			                fieldLabel: '设备型号',
			                value:"${device.model}",
			                name: 'model'
			                
			            },{
			                fieldLabel: '设备类型',
			                value:"${device.deviceType.name}",
			                name: 'deviceType.name'
			                
			            },
			            {
			                fieldLabel: '流量采集地址',
			                value:"${device.trafficIp}",
			                name: 'trafficIP'
			                
			            },{
			            	xtype : 'textarea',
			            	fieldLabel : '设备描述',
			            	name : 'description',
			            	anchor : '90%',
			            	height : 100
			            	},
			            new Ext.form.Hidden({
			            	name:"deviceId",
			            	value:"${device.id}"
			            }),
			            new Ext.form.Hidden({
			            	id:"deviceTypeId",
			            	name:"deviceTypeId",
			            	value:"${device.deviceType.id}"
			            })
			        ],
			
			        buttons: [{text: '  返回  ',
			            		handler:function(){
			            		var id = '${link.linkId}';
			            		window.location.href="link_view.do?id=${linkId}";
			            	}
					        
			            }]
		    	});
		
		    simple.render("formDiv7");

			});
		</script>
</head>
<body>
<div id="formDiv7" style="margin-top:3%;margin-left:20%;"></div>
</body>
</html>