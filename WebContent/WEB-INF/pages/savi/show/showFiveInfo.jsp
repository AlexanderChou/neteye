<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<title>五元组信息</title>
	<link rel='StyleSheet' href="css/savitopoInit.css" type="text/css" />
	<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>
	<script type="text/javascript" src="js/show/showFiveInfo.js"></script>
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
	
		var bSwitchNameText = "<s:text name='bindingTable.switchName'/>"; 
		var bIfIndexText = "<s:text name='bindingTable.ifIndex'/>";
		var bIPAddressTypeText = "<s:text name='bindingTable.ipAddressType'/>";
		var bMACAddressText = "<s:text name='bindingTable.macAddress'/>";
		var bIPAddressText = "<s:text name='bindingTable.ipAddress'/>";
		var bBindingTypeText = "<s:text name='bindingTable.bindingType'/>";
		var bBindingStateText = "<s:text name='bindingTable.bindingState'/>";
		var bLifeTimeText = "<s:text name='bindingTable.lifeTime'/>";
		var bUserText = "<s:text name='bindingTable.user'/>";
		var bIsInFilteringTableText = "<s:text name='bindingTable.isInFilteringTable'/>";

		var bindingTableForSwitchText = "<s:text name='show.showRealTimeSwitchBindingTableInfo.bindingTableForSwitch'/>";
		var switchText = "<s:text name='show.showRealTimeSwitchBindingTableInfo.switch'/>";
		var indexText = "<s:text name='show.showRealTimeSwitchBindingTableInfo.index'/>";
		var showStaticText = "<s:text name='show.showRealTimeSwitchBindingTableInfo.showStatic'/>";
		var showSlaacText = "<s:text name='show.showRealTimeSwitchBindingTableInfo.showSlaac'/>";
		var showDhcpText = "<s:text name='show.showRealTimeSwitchBindingTableInfo.showDhcp'/>";
		var showAllText = "<s:text name='show.showRealTimeSwitchBindingTableInfo.showAll'/>";
	
	
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