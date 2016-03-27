<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<title><s:text name='config.configRealTimeSwitchBindingTableInfo.title'/></title>
	<link rel='StyleSheet' href="css/savitopoInit.css" type="text/css" />
	<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>
	<script type="text/javascript" src="js/config/configRealTimeSwitchBindingTableInfo.js"></script>
	<script type="text/javascript">
		var pleaseSelectText = "<s:text name='combo.pleaseSelect'/>";
		var loadingText = "<s:text name='combo.loadingText'/>";
		var emptyTipText = "<s:text name='tip.empty'/>";
		var waitMsgText = "<s:text name='form.waitMsg'/>";
		var displayMsgText = "<s:text name='grid.displayMsg'/>";
		var noRecordText = "<s:text name='grid.noRecord'/>";
		var noticeTitleText = "<s:text name='notice.title'/>";
		var updateFailedText = "<s:text name='notice.update.failed'/>";
		var updateSuccessText = "<s:text name='notice.update.success'/>";
		var saveOrUpdateFailedText = "<s:text name='notice.saveOrUpdate.failed'/>";
		var saveOrUpdateSuccessText = "<s:text name='notice.saveOrUpdate.success'/>";
		var saveFailedText = "<s:text name='notice.save.failed'/>";
		var saveSuccessText = "<s:text name='notice.save.success'/>";
		var deleteSuccessText = "<s:text name='notice.delete.success'/>";
		var deleteFailedText = "<s:text name='notice.delete.failed'/>";
		var deleteConfirmText = "<s:text name='notice.delete.confirm'/>";
	
		var bSwitchNameText = "<s:text name='bindingTable.switchName'/>"; 
		var bIfIndexText = "<s:text name='bindingTable.ifIndex'/>";
		var bIPAddressTypeText = "<s:text name='bindingTable.ipAddressType'/>";
		var bIPAddressText = "<s:text name='bindingTable.ipAddress'/>";
		var bMACAddressText = "<s:text name='bindingTable.macAddress'/>";
		var bBindingTypeText = "<s:text name='bindingTable.bindingType'/>";
		var bBindingStateText = "<s:text name='bindingTable.bindingState'/>";
		var bLifeTimeText = "<s:text name='bindingTable.lifeTime'/>";
		var bUserText = "<s:text name='bindingTable.user'/>";
		var bIsInFilteringTableText = "<s:text name='bindingTable.isInFilteringTable'/>";
		
		var bindingTableForSwitchText = "<s:text name='config.configRealTimeSwitchBindingTableInfo.bindingTableForSwitch'/>";
		var switchText = "<s:text name='config.configRealTimeSwitchBindingTableInfo.switch'/>";
		var indexText = "<s:text name='config.configRealTimeSwitchBindingTableInfo.index'/>";
		var showStaticText = "<s:text name='config.configRealTimeSwitchBindingTableInfo.showStatic'/>";
		var showSlaacText = "<s:text name='config.configRealTimeSwitchBindingTableInfo.showSlaac'/>";
		var showDhcpText = "<s:text name='config.configRealTimeSwitchBindingTableInfo.showDhcp'/>";
		var showAllText = "<s:text name='config.configRealTimeSwitchBindingTableInfo.showAll'/>";
		var submitButtonText = "<s:text name='config.configRealTimeSwitchBindingTableInfo.button.submit'/>";
		var createStaticText = "<s:text name='config.configRealTimeSwitchBindingTableInfo.createStatic'/>";
		var deleteStaticText = "<s:text name='config.configRealTimeSwitchBindingTableInfo.deleteStatic'/>";
		var bindingTableInfoText = "<s:text name='config.configRealTimeSwitchBindingTableInfo.bindingTableInfo'/>";
		var deleteErrorText = "<s:text name='config.configRealTimeSwitchBindingTableInfo.deleteError'/>";
		var interfaceListText = "<s:text name='config.configRealTimeSwitchBindingTableInfo.interfaceList'/>";
		var MacErrorText="<s:text name='config.configRealTimeSwitchBindingTableInfo.macErrorText'/>";
		var ipv6ErrorText="<s:text name='config.configRealTimeSwitchBindingTableInfo.ipv6ErrorText'/>";
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