<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<title><s:text name='show.showSaviSystemTable.title'/></title>
	<link rel='StyleSheet' href="css/savitopoInit.css" type="text/css" />
	<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>
	<script type="text/javascript" src="js/show/showSaviSystemTable.js"></script>
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

		var idText =  "<s:text name='switchcur.id'/>";
		var switchNameText =  "<s:text name='switchcur.switchName'/>";
		var ipVersionText =  "<s:text name='switchcur.ipVersion'/>";
		var systemModeText =  "<s:text name='switchcur.systemMode'/>";
		var maxDadDelayText =  "<s:text name='switchcur.maxDadDelay'/>";
		var maxDadPrepareDelayText =  "<s:text name='switchcur.maxDadPrepareDelay'/>";

		var titleText =  "<s:text name='show.showSaviSystemTable.title'/>";
		var interfaceText =  "<s:text name='show.showSaviSystemTable.interface'/>";
		var bindingTableText =  "<s:text name='show.showSaviSystemTable.bindingTable'/>";
		var viewAllText =  "<s:text name='show.showSaviSystemTable.viewAll'/>";
		var switchbasicInfoText =  "<s:text name='show.showSaviSystemTable.switchbasicInfo'/>";
	</script>
</head>
<body>
	<div id="bodyDiv">
		<div id="infoDiv">
			<div id='showDiv'></div>
		</div>
	</div>
</body>
</html>