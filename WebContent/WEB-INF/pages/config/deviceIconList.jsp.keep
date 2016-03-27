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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
	 <link rel='StyleSheet' href="css/topoInit.css" type="text/css">
	 <link rel="stylesheet" type="text/css" href="css/ext-all.css"/>
	 <script type="text/javascript" src="js/ext-base.js"></script>
	 <script type="text/javascript" src="js/ext-all.js"></script>
	 
	 <script type="text/javascript">
	 
	 	Ext.onReady(function(){
	 	
	 		Ext.QuickTips.init();
	 		
	 		var DeviceType = new Ext.data.Record.create( [ {
				name : "id",
				mapping : "id"
			}, {
				name : "name",
				mapping : "name"
			} ]);
		
			var dd = new Ext.data.JsonReader( {
				root : "deviceTypeList"
			}, DeviceType);
		
			var pp = new Ext.data.HttpProxy( {
				url : "listAllDeviceType.do"
			});
		
			var dStore = new Ext.data.Store( {
				proxy : pp,
				reader : dd
			});
		
	        dStore.load();
			var Record = new Ext.data.Record.create([
				{name:"id", mapping:"id"},
				{name:"manufacture", mapping:"manufacture"},
				{name:"description", mapping:"description"},
				{name:"model", mapping:"model"},
				{name:"iconFile", mapping:"iconFile"},
				{name:"deviceTypeName", mapping:"deviceType.name"}
			]);
			
			var reader = new Ext.data.JsonReader({
					root:"iconList",
					totalProperty:'totalCount'
					},Record
			    );
			
			var proxy = new Ext.data.HttpProxy({
				url:"getAllDeviceIcons.do"
			});
		
			var colm = new Ext.grid.ColumnModel([
				{header:"图标序列号", dataIndex:"id",sortable:true},
				{header:"设备类型", dataIndex:"deviceTypeName",sortable:true},
				{id:"deviceHome", header:"设备厂家", dataIndex:"manufacture",sortable:true},
				{header:"设备型号", dataIndex:"model",sortable:true},
				{header:"描述", dataIndex:"description",sortable:true},
				{header:"图标图形", dataIndex:"iconFile",renderer : imgRenderer},
				{header:"操作项", dataIndex:"id",width:150,renderer:getOptions}
			]);
			
			iconstore = new Ext.data.Store({
				proxy:proxy,
				reader:reader
			});
			
			iconstore.load({params:{start:0,limit:28}});
			var grid = new Ext.grid.GridPanel({
				title:"图标管信息列表",
				store:iconstore,
				height:document.body.clientHeight* 0.95 + 10,
				width:840,
				cm:colm,
				autoExpandColumn:"deviceHome",
				autoScroll:true,
				renderTo:"showDiv",
				tbar:["->", {
					id:"addRecord",
					text:"添加图标",
					handler:function(){
						
						
						var combo = new Ext.form.ComboBox({
						     	fieldLabel: '设备类型',
						     	id:"department",
						   		store: dStore,
						   		displayField: 'name',
						   		name:'deviceiconname',
						   		valueField: 'name',
						    	mode : 'local',
						     	typeAhead: true, //自动将第一个搜索到的选项补全输入
						     	triggerAction: 'all',
						    	emptyText: '请选择设备类型',
						    	selectOnFocus: true,
						   	 	forceSelection: true,
						        itemCls :"margin-top:15px;"
							});
					
						var simple = new Ext.FormPanel( {
							labelWidth : 75, // label settings here cascade unless overridden
							frame : true,
							bodyStyle : 'padding:5px 5px 0',
							width : 400,
							fileUpload: true,  
							defaults : {
								width : 230
							},
							defaultType : 'textfield',
							items : [combo,{
								fieldLabel : '设备厂家',
								name : 'deviceIcon.manufacture',
								allowBlank:false,
								id: 'manufacture',
								blankText:"不能为空！"
							},{
								fieldLabel : '设备型号',
								name : 'deviceIcon.model',
								id: 'model',
								allowBlank:false,
								blankText:"不能为空！"
							},{
								fieldLabel : '描述',
								name : 'deviceIcon.description',
								id: 'description', 
								allowBlank:true
							},{
								fieldLabel : '图片上传',
								id:'fileName',
								xtype: 'textfield',
								name:'upload',
								inputType:"file",
								allowBlank:false,
								blankText:"不能为空！"
							}],
		
							buttons : [ {
								text : '  保存       ',
								handler : function() {
									if (simple.form.isValid()) {
										simple.form.doAction('submit',{
											url:"addDeviceIcon.do",
											method: 'post',
											waitTitle:"提示",
					     					waitMsg:"<font color='black'>正在发送，请稍后......</font>",
					     					success:function(simple, action){
					     						Ext.Msg.alert("", "添加成功！");
					     						grid.store.reload();
												myWin.hide();									
											}, 
											failure:function(simple, action){
												alert("该图标信息保存失败！");
											}
										});
									}
								}
							}]
						});
						
						var myWin = new Ext.Window( {
							width : 400,
							height : 220,
							layout : 'fit',
							plain : true,
							title : '添加图标',
							bodyStyle : 'padding:5px 5px 0',
							buttonAlign : 'center',
							items : [simple]
						});
						myWin.show();
					}
				}],
				bbar:new Ext.PagingToolbar({
					pageSize:13,
					store:iconstore,
					displayInfo:true,
					displayMsg:'显示第 {0} 条到 {1} 条记录，一共 {2} 条',
					emptyMsg:'没有数据'
				})
			});
	 	
	 	});
	 	
	 	function imgRenderer(value){
	 		return "<img src='images/" + value +  "?" +  new Date() + "' />";
	 	}
	 	
	 	function getOptions(value) {
	 		return "<input type='button' value = '删除' onclick='deleteDeviceIcon(" + value + ")'/><input style='margin-left:5px;' onclick='alterDeviceIcon(" + value + ")' type='button' value='更改图标' />"
	 	}
	 	
	 	function deleteDeviceIcon(deviceIconId) {
	 		Ext.MessageBox.confirm ('提示', '确认删除？', function(confirm) {
	 			if(confirm == "yes") {
	 				Ext.Ajax.request({
			 			url:"deleteDeviceIcon.do?id=" + deviceIconId,
			 			success:function(){
			 			    //删除成功后进行页面刷新
			 				iconstore.reload();
			 				Ext.Msg.alert("", "删除成功！");
			 			},failure:function(){
			 				Ext.Msg.alert("", "删除失败！");
			 			}
			 		});
	 			}
	 		});
	 		
	 	}
	 	var deviceIconId = 0;
	 	var win = null;
	 	function alterDeviceIcon(id) {
	 		deviceIconId = id;
	 		win = new Ext.Window({
	 			title:"更改图标",
	 			width : 350,
				height : 150,
				layout : 'fit',
				plain : true,
				closable: true,closeAction : 'hide',
				bodyStyle : 'padding:5px 5px 0',
				buttonAlign : 'center',
				items:[alertForm]
	 		});
	 		win.show();
	 	}
	 	
	 	
	 	var alertForm = new Ext.form.FormPanel({
			baseCls: 'x-plain',
	        labelAlign: 'left',
	    	buttonAlign: 'center',
	    	frame: true,
	    	labelWidth: 65,
	    	defaultType: 'textfield',
	    	fileUpload: true,  
	    	defaults: {
	    		allowBlank: false
	    	},
	        items: [{
	        	xtype:'fieldset',
	        	title:'属性信息',
	        	collapsible:true,
	        	width:320,
	        	autoHeight:true,//使自适应展开排版
	        	defaults:{width:200,height:30},
	        	defaultType:'textfield',
	        	items:[{
	        		id:'fileName',
	        		xtype: 'textfield',   
			        fieldLabel: '图片上传',
			        allowBlank:false,
			        name: 'upload',   
			        inputType: 'file'//文件类型   
	        	}],
	        	buttons: [{   
			        text: '上传',   
			        handler: function() {
			         if(alertForm.form.isValid()){
	            		alertForm.form.doAction('submit', {
	            			url: 'alterDeviceIcon.do',
	            			params: {'deviceIconId': deviceIconId},
	     					method: 'post',
	     					waitTitle:"提示",
	     					waitMsg: "<font color='black'>正在上传，请等待......</font>",
	     					success:function(alertForm,action){
	     					Ext.Msg.alert("", "上传成功！");
	     						iconstore.reload();
	     						win.close();
	     					},
	     					failure:function(alertForm,action){
	     						Ext.Msg.alert('错误', '文件上传失败');  
	     					}
	        			});
	        		}
			      }   
			    }]
			    }]
	    });
	 	
	 	
	 </script>
	 
	 <style type="text/css">
	 	.x-grid3-cell {
	 		vertical-align:middle !important;
	 	}
	 </style>
	 
</head>
<body>

	<div id="outer">
		<div id="bodyDiv">
			<div id="menu">
				<s:include value="right_menu.jsp"></s:include>
			</div>
		
			<div id="infoDiv">
				<div id='showDiv'></div>
			</div>
		</div>
	</div>

</body>
</html>