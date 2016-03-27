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
 
  function goback(id,style){
  	if ("${param.sstatus}" == "0") {
  		window.location.href="devicelistaction.do?deviceTypeId="+id+"&status="+0;
  	} else if ("${param.sstatus}" == "1"){
  		window.location.href="devicelistaction.do?deviceTypeId="+id+"&status="+1;
  	} else {
  		window.location.href="devicelistaction.do?deviceTypeId="+id+"&status="+2;
  	}
 	
 }
 function goAdd(id,style){
 	window.location.href="deviceAdd.do?sstatus=${param.sstatus}&id="+id+"&style="+style;
 }
Ext.onReady(function() {
  
  	var arr=[[1, '1'], ['2c', '2c'],[3, '3']]; 
  	var reader = new Ext.data.ArrayReader( 
			{id: 0}, 
			[ 
			{name: 'value'}, 
			{name: 'key'} 
	]); 
	var store=new Ext.data.Store({ 
		reader:reader,
		proxy: new Ext.data.MemoryProxy(arr)
	}); 
	store.load(); 

  
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
	            columnWidth:.5,  //该列占用的宽度，标识为50％
	            layout: 'form',
	            border:false,
	            items: [{                     //这里可以为多个Item，表现出来是该列的多行
	                cls : 'key',
	                xtype:'textfield',
	                fieldLabel: '名称',
	                name: 'name',
	                id:"chineseName",
	                anchor:'90%'
	            },{                     //这里可以为多个Item，表现出来是该列的多行
	                cls : 'key',
	                xtype:'textfield',
	                fieldLabel: 'IP地址',
	                id:"ipAddress",
	                anchor:'90%'
	            }]
	        },{
	            columnWidth:.5,  
	            layout: 'form',
	            border:false,
	            items: [{                    
	                cls : 'key',
	                xtype:'textfield',
//	                inputType:'password',
	                fieldLabel: 'Community',
	                id:"community",
	                anchor:'90%'
	            },new Ext.form.ComboBox({ //select 
					fieldLabel:'SNMP版本号', 
					editable:false, 
					triggerAction: 'all',
					width:175,
					valueField:'value', 
					displayField:'key', 
					value:'2c',
					store:store ,
					id:"SNMPVision"
				})]
	        }]
	    },{
	    	text:"添加",
    		xtype:'button',
            inputType:'button',
            handler:function(){
            	var chineseName = Ext.get("chineseName").getValue();
            	var ipAddress = Ext.get("ipAddress").getValue();
            	var community = Ext.get("community").getValue();
            	document.getElementById("chineseName").value="";
            	document.getElementById("ipAddress").value="";
            	document.getElementById("community").value="";
            	var SNMPVision = Ext.get("SNMPVision").getValue();
            	var value = Ext.get("content").getValue();
            	document.getElementById("content").value  = value + chineseName + "|" + ipAddress + "|" + community + "|" + SNMPVision + "\n";
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
	
	var mk = new Ext.LoadMask(document.getElementById("showDiv"), {
		msg: '正在提交数据，请稍候！',
		removeMask: true //完成后移除
	});
	
		
	var buttonPanel = new Ext.Panel({
			id:"button",
			buttonAlign:"center",
			title:"批量添加&nbsp;格式如：DNS服务器|192.168.0.2|passwd|2c(community仅支持数字、字符及'.'和'_')",
			frame:true,
			buttons:[{
				text:"保存",
				handler:function(){					
					var content = Ext.get("content").getValue();
					if(checkDevicees(content)){
						return;
					}
					mk.show();
					
					Ext.Ajax.request({
						url:"json/routerAdd.do",
						timeout:"90000",
						method:"post",
						params:{device:content, deviceTypeId:"${id}", style:"${style}"},
						success:function(response, request){
							mk.hide();
							var data = Ext.decode(response.responseText).mesg;
							if(data=='exist'){
								var string = Ext.decode(response.responseText).exist;
								window.location.href="spilth.do?exist="+string+"&deviceTypeId=${id}&status=${param.sstatus}";
							}else{						
								Ext.Msg.buttonText.yes='确定';
	   							Ext.Msg.buttonText.no="取消";
								Ext.Msg.buttonText.cancel="继续添加";
								var message=Ext.Msg.show({
									title:'处理结果 ',								
									buttons:Ext.MessageBox.YESNOCANCEL,
									msg:Ext.decode(response.responseText).mesg,
									fn:function(btn){
										if(btn=='yes'){
											goback("${id}","${style}");
										}
										if(btn=='no'){
											message.close();
										}
										if(btn=='cancel'){
											panel.hide();
											goAdd("${id}","${style}");
										}
									}
								});
							}
						}
					});
				}
			},{
				text:"返回",
				handler:function(){
					goback("${id}","${style}");	
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

/* 校验批处理 */

function checkDevicees(tareaValue){
//	var sss = tareaValue.replace(/\n/g, "@%#"); //这里地换了一次，不知道为什么，不换的话，那个换行符去不掉
//	var lines = sss.split("@%#");
	var lines = tareaValue.split("\n");
	var reg = /^\S+\|[0-9a-zA-Z.:]+\|\S+\|(1|2c|3)/;
	var msg = "";
	var isExist = new Array();
	var isExist2 = new Array();
	var linevalue = new Array();
	var msg2 = "";
	var msg3 = "";
	for (var index =0; index < lines.length ; index ++) {
		var line = parseInt(index) + 1;
		var content = lines[index].split("|");
		var name = content[0];
		var ip = content[1];
		if (isExist[name] != undefined ) {
			msg2 += isExist[name] + "==" + line + "  ";
		}
	
		if (isExist2[ip] != undefined ) {
			msg3 += isExist2[ip] + "==" + line + "  ";
		}
	
		isExist[name] =  line;
		isExist2[ip] = line;
		if(!lines[index]==""){
		if (!reg.test(lines[index])) {
			msg += line + "   ";
		}
		}
	}
	
	if (msg != "") {
		alert("第 " + msg + " 行内容格式出错！");
		return true;
	}
	
	if (msg2 != "") {
		alert("下列行: " + msg2 + " 重复");
		return true;
	}
	
	if (msg3 != "") {
		alert("下列行: " + msg3 + " ip重复");
		return true;
	}
	
	return false;
}

</script>
<body>

<div id='showDiv' style='position:absolute;top:100px;left:300px;'>

</div>


<div id="msgDiv" style="postion:absolute;top:300px;left:300px;">

</div>

</body>
</html>