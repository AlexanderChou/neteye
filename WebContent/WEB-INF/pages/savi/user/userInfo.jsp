<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<title><s:text name='userInfo.title'/></title>
	<link rel='StyleSheet' href="css/savitopoInit.css" type="text/css" />
	<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>
	<script type="text/javascript">
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='qtip'; 
	 
	var emptyTip = '<s:text name="tip.empty"/>';
	
	function updateUser(){
	var updateUser = new Ext.FormPanel( {
		labelWidth : 113,
		frame : true,
		title : '',
		bodyStyle : 'padding:5px 5px 0',
		width : 420,
		defaults : {
			width : 230
		},
		labelAlign: 'left',
		defaultType : 'textfield',
		items : [ {
					fieldLabel : '<s:text name="userInfo.username"/>',
					name : 'name',
					readOnly: true,
					value: '${username}',
					blankText: emptyTip,
					allowBlank:false
				}, {
					id:'oldPassword',
					fieldLabel : '<s:text name="userInfo.oldPassword"/>',
					name : 'oldPassword',
					blankText:emptyTip,
					inputType: 'password',
					allowBlank:false
				}, {
					id:'password',
					fieldLabel : '<s:text name="userInfo.password"/>',
					name : 'password',
					blankText:emptyTip,
					inputType: 'password',
					allowBlank:false
				}, {
					id:'rePassword',
					fieldLabel : '<s:text name="userInfo.rePassword"/>',
					name : 'rePassword',
					blankText:emptyTip,
					inputType: 'password',
					allowBlank:false
				}
		],
		buttons : [ {
					text : ' <s:text name="userInfo.button.update"/> ',
					handler : function() {
						if (updateUser.getForm().isValid()){
							updateUser.getForm().submit({
								url:"user/update.do",
								success:function(form, action){
									updateUserWin.close();
								},
								failure:function(from, action){
									var errMsg = Ext.decode(action.response.responseText).errMsg;
									Ext.Msg.alert('<s:text name="notice.title"/>', '<s:text name="notice.update.failed"/>' + errMsg);
								}
							});
						}
					}
		}]
	});

	var updateUserWin = new Ext.Window( {
		width : 440,
		height : 320,
		layout : 'fit',
		plain : true,
		frame : true,
		title : '<s:text name="userInfo.userInfo"/>',
		bodyStyle : 'padding:5px 5px 0',
		buttonAlign : 'center',
		items : [updateUser]
		});
	
	updateUserWin.show();

}
</script>
<style type="text/css">

body {
	background-color:#F2F4FF;
	border-color:-moz-use-text-color -moz-use-text-color -moz-use-text-color #8BB1E8;
	border-style:none none none solid;
	border-width:medium medium medium 1px;
	margin:0;
}

#showDiv  {
	background:url("../images/common/light_green.gif") repeat-x scroll 0 -1px transparent;
	border:1px solid #99BBE8;
	color:#15428B;
	font:bold 11px/15px tahoma,arial,verdana,sans-serif;
	overflow:hidden;
	padding:5px 3px 4px 5px;
}

</style>


</head>
<body>
<br/><br/><br/>
<div id='showDiv' style="position: relative;width:500px;height:270px;margin: 0 auto; ">
	<br/><br/>
	<font style="font-size:20px"><s:text name='userInfo.userInfo'/></font>
	<br/><br/><br/><br/>
	<table border='0px' style="margin:auto; width:80%;">
		<tr>
				<td width='100px' height="40px"><s:text name='userInfo.username'/>：</td>
				<td width='200px'>${username}</td>
		</tr>
		<tr>
			<td width='100px' height="30px"><s:text name='userInfo.role'/>：</td>
			<td>
				<s:if test="%{#session.role==1}"><s:text name='userInfo.role.user'/></s:if>
				<s:if test="%{#session.role==2}"><s:text name='userInfo.role.admin'/></s:if>
				<s:if test="%{#session.role==3}"><s:text name='userInfo.role.superAdmin'/></s:if>
			</td>
		</tr>
		<tr><td width='100px' height="30px">
		<br/><br/>
			<input class="x-btn-center" type="button" value="<s:text name='userInfo.button.modifyPwd'/>" onclick="updateUser()">
		</td></tr>	
	</table>
</div>

</body>
</html>