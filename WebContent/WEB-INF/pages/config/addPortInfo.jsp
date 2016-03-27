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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
<script type="text/javascript" src="js/ext-base.js"></script>
<script type="text/javascript" src="js/ext-all.js"></script>
<link type="text/css" href="css/addPort.css"  rel="StyleSheet">
<title>添加接口</title>
<script language="javascript">
  function goback(id,style){
 	window.location.href="equip_view.do?id="+id+"&style="+style;
 }
</script>
</head>
<body>
<div id="header">添加<font color="red">
	<s:if test="#device.chineseName==null">
		<s:property value="device.name"/>
	</s:if>
	<s:else>
		<s:property value="device.chineseName"/>
	</s:else>
	<s:if test="#device.loopbackIP==null">
		(<s:property value="device.loopbackIPv6"/>)
	</s:if>
	<s:else>
		(<s:property value="device.loopbackIP"/>)
	</s:else></font>下的接口
</div>
<div id='showDiv' style='position:absolute;top:100px;left:300px;'>

</div>

<script type="text/javascript">
	Ext.onReady(function(){
	var formPanel = new Ext.FormPanel({
		id:"form",
	    labelAlign: 'left',
	    title: '添加接口',
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
	            columnWidth:.3,  
	            layout: 'form',
	            border:false,
	            items: [{                    
	                cls : 'key',
	                xtype:'textfield',
	                fieldLabel: '接口描述',
	                id:"ifindexDescription",
	                anchor:'90%'
	            },{ //select 
					cls : 'key',
	                xtype:'textfield',
	                fieldLabel: '接口名称',
	                id:"ifindexName",
	                anchor:'90%'
				}]
	        },{
	            columnWidth:.3,  
	            layout: 'form',
	            border:false,
	            items: [{                    
	                cls : 'key',
	                xtype:'textfield',
	                fieldLabel: 'Ipv4地址',
	                id:"Ipv4",
	                anchor:'90%'
	            },{ //select 
					cls : 'key',
	                xtype:'textfield',
	                fieldLabel: 'Ipv6地址',
	                id:"Ipv6",
	                anchor:'90%'
				}]
	        },{
	            columnWidth:.3,  
	            layout: 'form',
	            border:false,
	            items: [{                    
	                cls : 'key',
	                xtype:'textfield',
	                fieldLabel: 'ifindex',
	                id:"ifindexText",
	                anchor:'90%',
	                regex: /^[0-9]+$/,   
               		regexText:'只能输入整数格式'	
	            },{ //select 
					cls : 'key',
	                xtype:'textfield',
	                fieldLabel: 'speed',
	                id:"speedText",
	                anchor:'90%',
	                regex: /^[0-9]+[.]?[0-9]*$/,   
               		regexText:'只能输入数字格式'	
				}]
	        }
	        
	        ]
	    },{
	    	text:"添加",
    		xtype:'button',
            inputType:'button',
            handler:function(){
            	var ifindexDescription = Ext.get("ifindexDescription").getValue();
            	var ifindexName = Ext.get("ifindexName").getValue();
            	var Ipv4 = Ext.get("Ipv4").getValue();
            	var Ipv6 = Ext.get("Ipv6").getValue();
            	var ifindexText = Ext.get("ifindexText").getValue();
            	var speedText = Ext.get("speedText").getValue();
            	var value = Ext.get("content").getValue();
            	document.getElementById("content").value  = value + ifindexDescription + "|" +ifindexName  + "|" + ifindexText + "|" + Ipv4 + "|" + Ipv6 + "|" + speedText  + "\n";
            }
    	}]
	});
	
	var textAreaPanel = new Ext.Panel({
			title:"批量添加",
			width: 600,
			items:[
				  new  Ext.form.TextArea({    //TextArea 
		                fieldLabel:'textarea', 
		                id:'content',
		                boder:false,
		                width:600,
		                height:200
          		  }) 
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
					var content = Ext.get("content").getValue();
					if (!checkStyle(content)) {
						return;
					}
					Ext.Ajax.request({
						url:"portAdd.do",
						timeout:"90000",
						method:"post",
						params:{ports:content, style:"${style}",deviceId:"${device.id}"},
						success:function(response, request){
							mk.hide();
							goback("${id}","${style}");	
						}
					});
				}
			},{
				text:"返回",
				handler:function(){
					window.history.back();	
				}
			}]
		});
		
	var panel = new Ext.Panel({
		width: 600,
		renderTo:"showDiv",
		buttonAlign:'center',
		items:[
			formPanel,
			textAreaPanel,
			buttonPanel
			]
	});
		
	});
	/* 检查样式是否有错误 */
	function checkStyle(textAreaValue){
		var sss = textAreaValue.replace(/\r\n/ig, "@%#"); 
		var lines = sss.split("@%#");
		var reg = /^[\S*\|\S+\|\d*\|[0-9.]*\|[0-9a-z:]*\|\d*$/;  //只是做了简单的校验
		var msg = "";
		for (var index =0; index < lines.length; index ++) {
		 		var line = parseInt(index) + 1;
				var content = lines[index].split("|");
				if(!lines[index]==""){
		 		if (!reg.test(lines[index])) {
					msg += line + "   ";
						}
		    }
		}
		if (msg != "") {
			alert("第 " + msg + " 行内容格式出错！");
			return false;
		}
		
		return true;
		
	}
</script>

<div id="msgDiv" style="postion:absolute;top:300px;left:60px;">

</div>

</body>
</html>