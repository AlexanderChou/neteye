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
	<link rel='StyleSheet' href="css/topoInit.css" type="text/css">
	<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>
	<script type="text/javascript">
	Ext.onReady(function(){
		var formPanel = new Ext.FormPanel({
			id:"form",
		    labelAlign: 'left',
		    title: '添加设备',
		    buttonAlign:'right',
		   	// renderTo:"form",
		    bodyStyle:'padding:5px',
		  ///  width: 800,
		    frame:true,
		    labelWidth:80,
		    items: [{
		        layout:'column',   // 定义该元素为布局为列布局方式
		        border:false,
		        labelSeparator:'：',
		        items:[{
		            columnWidth:.3,  
		            layout: 'form',
		            border:false,
		            items: [{                    
		                cls : 'key',
		                xtype:'textfield',
		                fieldLabel: '中文名称',
		                width: 1000,
		                id:"chineseName",
		                anchor:'90%'
		            },{ // select
						cls : 'key',
		                xtype:'textfield',
		                fieldLabel: 'IP地址',
		                width: 1000,
		                id:"ipAddress",
		                anchor:'90%'
					}]
		        },{
		            columnWidth:.3,  
		            layout: 'form',
		            border:false,
		            items: [{
		           		 cls : 'key',
		                xtype:'textfield',
		                fieldLabel: '英文名称',
		                width: 1000,
		                id:"englishName",
		                anchor:'90%'                    
		            },{ // select
						cls:'key',
		                xtype:'textfield',
		                fieldLabel: 'IPv6地址',
		                width: 1000,
		                id:"ipv6",
		                anchor:'90%'
					}]
		        },{
		        	columnWidth:.3,
		        	layout: 'form',
		        	border:false,
		        	items: [{
		        	    cls : 'key',
		                xtype:'textfield',
		                inputType:'password',
		                fieldLabel: 'Community',
		                width: 1000,
		                id:"community",
		                anchor:'90%'
		        	},{
		                cls:'key',
		        	    xtype:'textfield',
		        	    fieldLabel:'SNMP版本',
		        	    width: 1000,
		        	    id:"snmpVersion",
		        	    anchor:'90%'  
		        	}]
		        },{
		        	columnWidth:.3,
		        	layout: 'form',
		        	border:false,
		        	items: [{
		                cls:'key',
		        	    xtype:'textfield',
		        	    fieldLabel:'描述',
		        	    width: 1000,
		        	    id:"description",
		        	    anchor:'90%'  
		        	}]
		        }]
		    },{
		    	text:"添加",
	    		xtype:'button',
	            inputType:'button',
	            handler:function(){
	            	var chineseName = Ext.get("chineseName").getValue();
		       		var englishName = Ext.get("englishName").getValue();
		            var ipAddress = Ext.get("ipAddress").getValue();
		            var ipv6 = Ext.get("ipv6").getValue();
		            var description = Ext.get("description").getValue();
					var community = Ext.get("community").getValue();
					var snmpVersion = Ext.get("snmpVersion").getValue();
		            document.getElementById("chineseName").value="";
	            	document.getElementById("ipAddress").value="";
	            	document.getElementById("englishName").value="";
	            	document.getElementById("description").value="";
	            	document.getElementById("community").value="";
	            	document.getElementById("snmpVersion").value="";
	            	document.getElementById("ipv6").value="";
		            var value = Ext.get("content").getValue();
		            document.getElementById("content").value  = value + chineseName + "|" + ipAddress + "|" + englishName+ "|" + ipv6 + "|" + community + "|" + snmpVersion  + "|" + description+ "\n";
		       	}
	    	}]
		});
		
		var textAreaPanel = new Ext.Panel({
				title:"批量添加",
				//width: 840,
				items:[
				  new  Ext.form.TextArea({    // TextArea
		                fieldLabel:'textarea', 
		                id:'content',
		                boder:false,
		                width: 840,
		                height:400
          		  }) 
				]
			});
		
		var mk = new Ext.LoadMask("showGrid", {
			msg: '正在提交数据，请稍候！',
			removeMask: true // 完成后移除
		});
		
			
		var buttonPanel = new Ext.Panel({
				id:"button",
				//width: 800,
				buttonAlign:"center",
				title:"批量添加&nbsp;格式如：'DNS服务器|192.168.0.2|passwd|描述",
				frame:true,
				buttons:[{
					text:"保存",
					handler:function(){
						mk.show();
						var content = Ext.get("content").getValue();
						Ext.Ajax.request({
							url:"json/routerAdd.do",
							timeout:"90000",
							method:"post",
							params:{device:content, deviceTypeId:"${id}"},
							success:function(response, request){
							var data = Ext.decode(response.responseText);
							mk.hide();
							Ext.Msg.buttonText.yes='确定';
   							Ext.Msg.buttonText.no="取消";
							Ext.Msg.buttonText.cancel="继续添加";
							var message=Ext.Msg.show({
								title:'处理结果 ',								
								buttons:Ext.MessageBox.YESNOCANCEL,
								msg:Ext.decode(response.responseText).mesg,
								fn:function(btn){
									if(btn=='yes'){
										goback("${id}");
									}
									if(btn=='no'){
										message.close();
									}
									if(btn=='cancel'){
										panel.hide();
										goAdd("${id}");
									}
								}
							});
						}
						});
					}
				},{
					text:"返回",
					handler:function(){
						goback("${id}");	
					}
				}]
			});
			
		var temppanel = new Ext.Panel({
			width: 840,
			frame : true,
			
			bodyStyle : 'padding:20px 20px 20px 20px',
			//renderTo:"showDiv",
			autoScroll : true,
			height : document.body.clientHeight * 0.95 + 5,
			buttonAlign:'center',
			items:[
				formPanel,
				textAreaPanel,
				buttonPanel
				]
		});
		temppanel.render('showGrid');
	});
</script>
</HEAD>
<script language="javascript">
  function goback(id){
 	window.location.href="devicelistaction.do?deviceTypeId="+id;
  }
  function goAdd(id){
 	window.location.href="deviceAdd.do?id="+id;
  }
</script>
<body>
<div id="outer">
		<div id="bodyDiv">
			<div id="menu">
				<s:include value="right_menu.jsp"></s:include>
			</div>
		
			<div id="infoDiv">
				<div id="showGrid">
			</div>
				
			<div id="msgDiv"></div>
		</div>
</div>



</body>
</html>