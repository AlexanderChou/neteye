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
<title>路由器详细信息</title>
     <link rel='StyleSheet' href="css/topoInit.css" type="text/css" />
	<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>
	
	<script type="text/javascript">
			Ext.onReady(function(){
				Ext.QuickTips.init();
				// turn on validation errors beside the field globally
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
			        title: '节点${device.name}的详细信息',
			        bodyStyle:'padding:5px 5px 0',
			        width: 840,
			        //height:document.body.clientHeight*0.95 + 5,
			        defaults: {width: 308},
			        defaultType: 'textfield',
					onSubmit: Ext.emptyFn,
			        submit:function() {
			            this.getForm().getEl().dom.action = this.url;
			            this.getForm().getEl().dom.submit();
			        },
			        items: [{
			                fieldLabel: '名称',anchor : '90%',
			                name: 'deviceName',
			                value:"${name}",
			                allowBlank:false
			            },{
			                fieldLabel: '中文名称',anchor : '90%',
			                value:"${chineseName}",
			                name: 'chineseName'
			            },{
			                fieldLabel: '序列号',anchor : '90%',
			                value:"${serial}",
			                name: 'serial'
			            },{
			                fieldLabel: 'SNMP版本号',anchor : '90%',
			                value:"${snmpVersion}",
			                name: 'snmpVersion'			                
			            },{
			                fieldLabel: 'IPV4地址',anchor : '90%',
			                value:"${loopbackIP}",
			                name: 'loopbackIP',
			                regex: /^([0-9]|[0-1]{0,1}[0-9][0-9]|2[0-4][0-9]|25[0-5])\.([0-9]|[0-1]{0,1}[0-9][0-9]|2[0-4][0-9]|25[0-5])\.([0-9]|[0-1]{0,1}[0-9][0-9]|2[0-4][0-9]|25[0-5])\.([0-9]|[0-1]{0,1}[0-9][0-9]|2[0-4][0-9]|25[0-5])$/,   
               				regexText:'IPV4地址不正确'		                
			            },{
			                fieldLabel: 'IPV6地址',anchor : '90%',
			                value:"${loopbackIPv6}",
			                name: 'loopbackIPv6',
			                regex: /^[0-9,a-f,A-F,:]+$/,   
               				regexText:'IPV6地址不正确'			                
			            },{
			                fieldLabel: '存放地址',anchor : '90%',
			                value:"${location}",
			                name: 'location'
			                
			            },{
			                fieldLabel: '生产厂商',anchor : '90%',
			                value:"${productor}",
			                name: 'productor'
			                
			            },{
			                fieldLabel: '设备型号',anchor : '90%',
			                value:"${model}",
			                name: 'model'
			                
			            },combo,
			            {
			                fieldLabel: '流量采集地址',anchor : '90%',
			                value:"${trafficIp}",
			                name: 'trafficIP'
			                
			            },{
			            	xtype : 'textarea',
			            	fieldLabel : '设备描述',
			            	value:"${description}",
			            	name : 'description',
			            	anchor : '90%',
			            	height : 200
			            	},
			            new Ext.form.Hidden({
			            	name:"deviceId",
			            	id:"deviceId",
			            	value:"${id}"
			            }),
			            new Ext.form.Hidden({
			            	name:"style",
			            	value:"${style}"
			            }),
			            new Ext.form.Hidden({
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
				            	if('${style}' == 1){
				            		window.location.href="devicelistaction.do?deviceTypeId="+id+"&status=0";
						        }
				            	else if('${style}' == 2){
				            		window.location.href="devicelistaction.do?deviceTypeId="+id+"&status=1";
					            }
				            	else{
				            		window.location.href="devicelistaction.do?deviceTypeId="+id+"&status=2";
					            }
			            	}
					        
			            },
			            {text: '  查看接口  ',
				            handler:function(){
				            	window.location.href="toport.do?deviceId=${device.id}&style=${style}";
				            }
			            }]
		    	});
		
		    simple.render("showGrid");
		    
		    
		var dicDetailStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : 'json/dicDetailList.do'
				}),
				//创建JsonReader读取router记录
				reader : new Ext.data.JsonReader({
					root : 'dicDetailList',
					fields : ['dicContenId','dicContentName']
				})
		});
		dicDetailStore.load();
		
		var dicDetailCombo = new Ext.form.ComboBox({
	     	fieldLabel: '设备性能参数',
	     	id:"dicDetail",
	     	name:"dicDetailId",
	   		store: dicDetailStore,
	   		displayField: 'dicContentName',
	   		valueField: 'dicContenId',
	    	mode : 'remote',
	     	typeAhead: true, //自动将第一个搜索到的选项补全输入
	     	triggerAction: 'all',
	    	//emptyText: '${device.deviceType.name}',
	    	//hiddenId: '${device.deviceType.id}',
	    	selectOnFocus: true,
	   	 	forceSelection: true,
	   	 	hiddenName: 'dicDetailId',
	   	 	listeners:{
	   	 		select : function(dicDetailCombo, record, index) {
	   	 		dicDetailCombo.value = record.get("dicContenId");
	   	 		}
	   	 	}
		});

		    var fsf = new Ext.FormPanel({
		        labelWidth: 100, // label settings here cascade unless overridden
		        url:'',
		        frame:true,
		        title: '设备性能参数配置',
		        bodyStyle:'padding:5px 5px 0',
		        width: 840,
		        height:305,
		        items: [{
		            xtype:'fieldset',
		            checkboxToggle:true,
		            title: '性能指标数据字典',
		            autoHeight:true,
		            width: 735,
		            defaults: {width: 210},
		            defaultType: 'textfield',
		            collapsed: true,
		            items :[dicDetailCombo,{
			        	xtype: "textarea",
	                    fieldLabel: "参数配置以行为基础，每行内容输入格式如域内所示",
	                    value:"oid:1.3.6.1.2.1.4.40.1.1.1.1||desc:CPU1的利用率",
	                    id: "memo",
	                    //labelWidth: 840,
	                    width: 610
			        }]
		        }],

		        buttons: [{
		            text: '保存',
		            handler: showValue
		        }]
		    });

		    function showValue() {
		    	var dicDetailId = fsf.getForm().findField("dicDetail").getValue();
                var memo = fsf.getForm().findField("memo").getValue();
                var deviceId = Ext.get("deviceId").getValue(false);   
                //Ext.Msg.alert("dicDetailId=",dicDetailId);
               // Ext.Msg.alert("deviceId=",deviceId);
                //后台代码未完成
                Ext.Ajax.request({
    				url : 'json/performanceParaConfig.do',
    				disableCaching : true,
    				params : {
    					dicDetailId : dicDetailId,
    					performancePara: memo,
    					deviceId: deviceId
    				},
    				method : 'post',
    				success : function(result, request) {
    					 Ext.MessageBox.alert('Status', '配置设备性能指标参数成功！');
    				},
    				failure : function(result, request) {
    					Ext.Msg.alert("<font color='black'>配置设备性能指标参数时发生错误!</font>");
    				}
    			});
            }
	    	
		    fsf.render("performanceConfig");

			});
		</script>
	
</head> 
<body>
<div id="outer">
	<div id="bodyDiv">
		<div id="menu">
			<s:include value="right_menu.jsp"></s:include>
		</div>
	
		<div id="infoDiv">
			<div id="performanceConfig">
			<div id="showGrid">
		</div>
	</div>
</div>

</div>
	<div id="formDiv2" style="margin-top:3%;margin-left:20%;"></div>
</body>
</html>