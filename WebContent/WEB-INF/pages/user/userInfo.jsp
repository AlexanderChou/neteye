<%--
	Copyright (c) 2008, 2009, 2010
	he Regents of the Tsinghua University, PRC.  All rights reserved.

	Redistribution and use in source and binary forms, with or without  modification, are permitted provided that the following conditions are met:
	1. Redistributions of source code must retain the above copyright  notice, this list of conditions and the following disclaimer.
	2. Redistributions in binary form must reproduce the above copyright  notice, this list of conditions and the following disclaimer in the  documentation and/or other materials provided with the distribution.
	3. All advertising materials mentioning features or use of this software  must display the following acknowledgement:
	This product includes software (iNetBoss) developed by Tsinghua University, PRC and its contributors.
	THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
	ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
	IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
	ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
	FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
	DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
	OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
	HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
	LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
	OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
	SUCH DAMAGE.
 --%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN "   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>用户个人信息修改</title>
		<link rel='StyleSheet' href="css/topoInit.css" type="text/css">
		<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
		<script type="text/javascript" src="js/ext-base.js"></script>
		<script type="text/javascript" src="js/ext-all.js"></script>
		
		<script type="text/javascript">
			Ext.onReady(function(){
				
				var Department = new Ext.data.Record.create([
					{name:"id", mapping:"id"},
					{name:"name", mapping:"name"}
				]);
				
				var dd = new Ext.data.JsonReader({
					root:"departments"
					},
					Department
				    );
				
				var pp = new Ext.data.HttpProxy({
					url:"listAllDepartment.do"
				});
				
				var dStore = new Ext.data.Store({
					proxy:pp,
					reader:dd
				});
				
				dStore.load();
				
				var combo = new Ext.form.ComboBox({
			     	fieldLabel: '部门名称',
			     	id:"department",
			   		store: dStore,
			   		displayField: 'name',
			   		valueField: 'id',
			    	mode : 'local',
			    	value:"${user.department.name}",
			     	typeAhead: true, //自动将第一个搜索到的选项补全输入
			     	triggerAction: 'all',
			    	emptyText: '请选择部门',
			    	selectOnFocus: true,
			   	 	forceSelection: true,
			   	 	listeners:{
			   	 		select : function(combo, record, index) {
			   	 			combo.value = record.get("id");
			   	 		}
			   	 	},
			        itemCls :"margin-top:15px;"
				});
				
				var departmentHidden = new Ext.form.Hidden({
			            	id:"departmentId",
			            	name:"user.department.id",
			            	value:"${user.department.id}"
			            })
				
				var simple = new Ext.FormPanel({
			        labelWidth: 75, // label settings here cascade unless overridden
			        url:'http://www.baidu.com',
			        frame:true,
			        title: '用户个人信息',
			        bodyStyle:'padding:5px 5px 0',
			        width: 500,
			        height: 400,
			        defaults: {width: 230},
			        defaultType: 'textfield',
			
			        items: [{
			                fieldLabel: '昵称',
			                name: 'user.name',
			                value:"${user.name}",
			                allowBlank:false,
			                style:"margin-top:15px;"
			            },{
			                fieldLabel: '真实姓名',
			                value:"${user.realName}",
			                name: 'user.realName',
			                style:"margin-top:15px;"
			            },{
			                fieldLabel: '密码',
			                inputType:'password',
			                value:"${user.password}",
			                name: 'user.password',
			                style:"margin-top:15px;"
			            }, {
			                fieldLabel: '邮箱',
			                value:"${user.email}",
			                name: 'user.email',
			                style:"margin-top:15px;"
			            }, {
			                fieldLabel: '电话',
			                value:"${user.telephone}",
			                name: 'user.telephone',
			                style:"margin-top:15px;"
			                
			            }, combo,
			            new Ext.form.Hidden({
			            	name:"user.id",
			            	value:"${user.id}",
			                style:"margin-top:15px;"
			            }),
			            departmentHidden       
			        ],
			
			        buttons: [{
			            text: '  保存      ',
			            handler:function(){
			            	var str = combo.value;
			            	if (!isNaN(str)) {
								departmentHidden.setValue(str);		            		
			            	} 
		            		simple.getForm().submit({
		            			url:"editUserInfo.do"
		            		});
		            		alert(" 修改完成！");
		            		simple.hide();
			            }
			        }]
		    	});
		
		    simple.render("formDiv");

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
			<div id='formDiv' style="margin:50px 0px 0px 100px;"></div>
		</div>
	</div>
</div>
</body>
</html>