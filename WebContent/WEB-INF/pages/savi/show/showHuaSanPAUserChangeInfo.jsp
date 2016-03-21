<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<title><s:text name='show.showSubnetUserChange.title'/></title>
	<link rel='StyleSheet' href="css/savitopoInit.css" type="text/css" />
	<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>
	<script type="text/javascript" src="js/show/showHuaSanPAUserChangeInfo.js"></script>
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

		var uTimeText = "<s:text name='userChangeInfo.time'/>";
		var uNameText = "<s:text name='userChangeInfo.name'/>";
		var uStatusText = "<s:text name='userChangeInfo.status'/>";
		var uIPAddressText = "<s:text name='userChangeInfo.ipAddress'/>";
		var uIfIndexText = "<s:text name='userChangeInfo.ifIndex'/>";
		var uSwitchNameText = "<s:text name='userChangeInfo.switchName'/>";

		var userChangeInfoText = "<s:text name='show.showSubnetUserChange.userChangeInfo'/>";
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