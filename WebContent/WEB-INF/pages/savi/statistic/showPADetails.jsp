<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<title>AP视图</title>
	<meta http-equiv="refresh" content="180">
	<link rel='StyleSheet' href="css/savitopoInit.css" type="text/css" />
	<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>
	<script type="text/javascript" src="js/Portal.js"></script>
    <script type="text/javascript" src="js/PortalColumn.js"></script>
    <script type="text/javascript" src="js/Portlet.js"></script>
	<script type="text/javascript" src="js/show/showPADetails.js"></script>
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

		var idText = "<s:text name='switchbasicinfo.id'/>";
		var nameText = "<s:text name='switchbasicinfo.name'/>";
		var equipmentTypeText = "<s:text name='switchbasicinfo.equipmentType'/>";
		var ipv4addressText = "<s:text name='switchbasicinfo.ipv4address'/>";
		var ipv6addressText = "<s:text name='switchbasicinfo.ipv6address'/>";
		var subnetNameText = "<s:text name='switchbasicinfo.subnetName'/>";
		var statusText = "<s:text name='switchbasicinfo.status'/>";
		var snmpVersionText = "<s:text name='switchbasicinfo.snmpVersion'/>";
		var readCommunityText = "<s:text name='switchbasicinfo.readCommunity'/>";
		var writeCommunityText = "<s:text name='switchbasicinfo.writeCommunity'/>";
		var authKeyText = "<s:text name='switchbasicinfo.authKey'/>";
		var privateKeyText = "<s:text name='switchbasicinfo.privateKey'/>";
		var descriptionText = "<s:text name='switchbasicinfo.description'/>";

		var ipVersionText = "<s:text name='switchcur.ipVersion'/>";

		var uTimeText = "<s:text name='userChangeInfo.time'/>";
		var uNameText = "<s:text name='userChangeInfo.name'/>";
		var uStatusText = "<s:text name='userChangeInfo.status'/>";
		var uIPAddressText = "<s:text name='userChangeInfo.ipAddress'/>";
		var uIfIndexText = "<s:text name='userChangeInfo.ifIndex'/>";
		var uSwitchNameText = "<s:text name='userChangeInfo.switchName'/>";

		var bSwitchNameText = "<s:text name='bindingTable.switchName'/>"; 
		var bIfIndexText = "<s:text name='bindingTable.ifIndex'/>";
		var bIPAddressTypeText = "<s:text name='bindingTable.ipAddressType'/>";
		var bIPAddressText = "<s:text name='bindingTable.ipAddress'/>";
		var bBindingTypeText = "<s:text name='bindingTable.bindingType'/>";
		var bMACAddressText = "<s:text name='bindingTable.macAddress'/>";
		var bBindingStateText = "<s:text name='bindingTable.bindingState'/>";
		var bLifeTimeText = "<s:text name='bindingTable.lifeTime'/>";
		var bUserText = "<s:text name='bindingTable.user'/>";
		var bIsInFilteringTableText = "<s:text name='bindingTable.isInFilteringTable'/>";

		var interfacePanelText = "<s:text name='show.showSwitchDetails.interfacePanel'/>";
		var onlineUserNumText = "<s:text name='show.showSwitchDetails.onlineUserNum'/>";
		var basicInfoText = "<s:text name='show.showSwitchDetails.basicInfo'/>";
		var runningStateText = "<s:text name='show.showSwitchDetails.runningState'/>";
		var switchUserNumberChangeChartText = "<s:text name='show.showSwitchDetails.switchUserNumberChangeChart'/>";
		
		var switchUserOnlineTimeChartText = "<s:text name='show.showSwitchDetails.switchUserOnlineTimeChart'/>";
		var interfaceUserNumberSortChartText = "<s:text name='show.showSwitchDetails.interfaceUserNumberSortChart'/>";

		
		var userOnlineTimeSortChartText = "<s:text name='show.showSwitchDetails.userOnlineTimeSortChart'/>";

		
		var userChangeInfoText = "<s:text name='show.showSwitchDetails.userChangeInfo'/>";
		var bindingTableText = "<s:text name='show.showSwitchDetails.bindingTable'/>";
	</script>
</head>
<body>
</body>
</html>