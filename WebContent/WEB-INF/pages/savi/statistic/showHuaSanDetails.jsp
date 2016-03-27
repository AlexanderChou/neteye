<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<title>AC视图</title>
	<meta http-equiv="refresh" content="180">
	<link rel='StyleSheet' href="css/savitopoInit.css" type="text/css" />
	<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>
	<script type="text/javascript" src="js/Portal.js"></script>
    <script type="text/javascript" src="js/PortalColumn.js"></script>
    <script type="text/javascript" src="js/Portlet.js"></script>
	<script type="text/javascript" src="js/show/showHuaSanDetails.js"></script>
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

		var userNumText = "<s:text name='show.showSubnetDetails.userNum'/>"; 
		var subnetNameText = "<s:text name='show.showSubnetDetails.subnetName'/>"; 
		var switchRunningStateChartText = "<s:text name='show.showSubnetDetails.switchRunningStateChart'/>"; 
		var interfaceTrustTypeChartText = "<s:text name='show.showSubnetDetails.interfaceTrustTypeChart'/>"; 
		var ipBindingTypeStateChartText = "<s:text name='show.showSubnetDetails.ipBindingTypeStateChart'/>"; 
		var interfaceValidatonStatusChartText = "<s:text name='show.showSubnetDetails.interfaceValidatonStatusChart'/>"; 
		var userNumberChangeChartText = "<s:text name='show.showSubnetDetails.userNumberChangeChart'/>"; 
		
		var userOnlineTimeChartText = "<s:text name='show.showSubnetDetails.userOnlineTimeChart'/>"; 
		var ipBindingStateChartText = "<s:text name='show.showSubnetDetails.ipBindingStateChart'/>"; 
		
		var switchUserNumChartText = "<s:text name='show.showSubnetDetails.switchUserNumChart'/>"; 
		var userChangeInfoText = "<s:text name='show.showSubnetDetails.userChangeInfo'/>"; 
		var bindingTableText = "<s:text name='show.showSubnetDetails.bindingTable'/>"; 
	</script>
</head>
<body>
</body>
</html>