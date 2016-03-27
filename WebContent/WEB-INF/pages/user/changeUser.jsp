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
				Ext.QuickTips.init();
				var simple = new Ext.FormPanel({
			        labelWidth: 75, // label settings here cascade unless overridden
			        frame:true,
			        title: '切换用户登录',
			        bodyStyle:'padding:5px 5px 0',
			        width: 400,
			        defaults: {width: 230},
			        defaultType: 'textfield',
			
			        items: [{
			                fieldLabel: '用户名',
			                name: 'user.name',
			                allowBlank:false,
			                blankText:"请输入用户名"
			                
			            },{
			                fieldLabel: '密码',
			                inputType:'password',
			                name: 'user.password',
			                allowBlank:false,
			                blankText:"请输入密码"
			            },new Ext.form.Hidden({
			            	name:"user.id",
			            	value:"${userId}"
			            })
			        ],
			        buttons: [{
			            text: '  登录  ',
			            handler:function(){
			            	if (simple.form.isValid()) {
			            		simple.getForm().submit({
			            			url:"changeUser.do?logId=${logId}",
			            			success:function (form, action) {
					            		if (window.confirm("切换成功,是否进入主控页面？")) {
						            		window.parent.parent.frames['bottom'].location.reload();
					            			document.location.href = "read.do";
					            		}
			            			},
			            			failure:function(form, action){
			            				alert(action.result.errMsg);
			            			}
		            			});
			            	} 
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
					<div id='formDiv' style="position:relative;margin:100px 0px 0px 50px;"></div>
				</div>
			</div>
		</div>
	</body>
</html>