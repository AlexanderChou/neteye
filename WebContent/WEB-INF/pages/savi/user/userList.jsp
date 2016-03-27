<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<title><s:text name='userList.title'/></title>
	<link rel='StyleSheet' href="css/savitopoInit.css" type="text/css" />
	<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>
	<script type="text/javascript" src="js/user/userList.js"></script>
	<script type="text/javascript">
		var emptyTipText = "<s:text name='tip.empty'/>";
		var displayMsgText = "<s:text name='grid.displayMsg'/>";
		var noRecordText = "<s:text name='grid.noRecord'/>";
		var noticeTitleText = "<s:text name='notice.title'/>";
		var updateFailedText = "<s:text name='notice.update.failed'/>";
		var saveOrUpdateFailedText = "<s:text name='notice.saveOrUpdate.failed'/>";
		var saveFailedText = "<s:text name='notice.save.failed'/>";
		var deleteSuccessText = "<s:text name='notice.delete.success'/>";
		var deleteFailedText = "<s:text name='notice.delete.failed'/>";
		var deleteConfirmText = "<s:text name='notice.delete.confirm'/>";

		var userRoleText = "<s:text name='userInfo.role.user'/>";
		var adminRoleText = "<s:text name='userInfo.role.admin'/>";
		var superAdminRoleText = "<s:text name='userInfo.role.superAdmin'/>";
		
		var usernameText =  "<s:text name='userInfo.username'/>";
		var roleText = "<s:text name='userInfo.role'/>";
		var passwordText = "<s:text name='userInfo.password'/>";
		var rePasswordText = "<s:text name='userInfo.rePassword'/>";

		var titleText = "<s:text name='userList.title'/>";
		var addUserText = "<s:text name='userList.button.addUser'/>";
		var deleteUserText = "<s:text name='userList.button.deleteUser'/>";
		var updateUserText = "<s:text name='userList.button.updateUser'/>";
		var saveText = "<s:text name='userList.button.save'/>";
		var updateText = "<s:text name='userList.button.update'/>";
		var userInfoText = "<s:text name='userList.userInfo'/>";
	</script>
</head>
<body>
	<div id="bodyDiv" class="align-center">
		<div id="infoDiv">
			<div id='showDiv' ></div>
		</div>
	</div>
</body>
</html>