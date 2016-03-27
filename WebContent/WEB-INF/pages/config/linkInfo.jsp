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
	<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>
<title>接口管理</title>
<script type="text/javascript">
			Ext.onReady(function(){
				Ext.QuickTips.init();
			    Ext.form.Field.prototype.msgTarget = 'side';			    
								
				var simple = new Ext.FormPanel({
			        labelWidth: 75, // label settings here cascade unless overridden
			        url:'linkUpdate.do',
			        frame:true,
			        title: '链路信息',
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
			                fieldLabel: '链路名称',
			                name: 'name',
			                value:"${link.name}"
			            },{
			                fieldLabel: '最大速率',
			                value:"${link.maxSpeed}",
			                name: 'maxSpeed',
			                regex: /^[0-9]+[.]?[0-9]*$|/,   
               				regexText:'只能输入数字格式'
			            },{
			                fieldLabel: '上链地址',
			                value:"${link.upInterface.ipv4}",
			                name: 'upInterface.ipv4',
			                disabled:true
			            },{
			                fieldLabel: '下链地址',
			                value:"${link.downInterface.ipv4}",
			                name: 'downInterface.ipv4',
			                disabled:true
			            },{
			                fieldLabel: '上链V6地址',
			                value:"${link.upInterface.ipv6}",
			                name: 'upInterface.ipv6',
			                disabled:true
			            },{
			                fieldLabel: '下链V6地址',
			                value:"${link.downInterface.ipv6}",
			                name: 'downInterface.ipv6',
			                disabled:true
			            },{
			                fieldLabel: '上链端口名称',
			                value:"${link.upInterface.description}",
			                name: 'upInterface.description',
			                disabled:true
			            },{
			                fieldLabel: '下链端口名称',
			                value:"${link.downInterface.description}",
			                name: 'downInterface.ifindex',
			                disabled:true
			            },{
			                fieldLabel: '上链端口索引',
			                value:"${link.upInterface.ifindex}",
			                name: 'upInterface.ifindex',
			                disabled:true
			            },{
			                fieldLabel: '上链端口索引',
			                value:"${link.downInterface.ifindex}",
			                name: 'downInterface.ifindex',
			                disabled:true
			            },{
			            	xtype : 'textarea',
			            	fieldLabel : '描述',
			            	value:"${link.description}",
			            	name : 'description',
			            	anchor : '90%',
			            	height : 100
			            	},
			            new Ext.form.Hidden({
			            	name:"id",
			            	value:"${id}"
			            })
			        ],
			
			        buttons: [{
			            text: '  查看上链设备  ',
		            	handler:function(){
		            		var id = '${link.id}';
			        		var linkId='${link.upDevice}';
			        		window.location.href="router_view.do?id=${upDevice}&linkId=${id}";
		        		}
		            	},{
			            text: '  查看下链设备  ',
		            	handler:function(){
		            		var id = '${link.id}';
			            	var linkId='${link.downDevice}';
			        		window.location.href="router_view.do?id=${downDevice}&linkId=${id}";
		        		}
		            	},{
			            text: '  保存  ',
			            	handler:function(){
		            			simple.submit();
			        		}
			            },
			            {text: '  返回  ',
			            	handler:function(){
			            	window.location.href="tolink.do";
			            	}
			            }]
		    	});
		
		    simple.render("formDiv6");

			});
		</script>
</head>
<body>
<div id="formDiv6" style="margin-top:5%;margin-left:20%;"></div>
</body>
</html>