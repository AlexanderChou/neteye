<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<title><s:text name='config.configSwitch.title'/></title>
	<link rel='StyleSheet' href="css/savitopoInit.css" type="text/css" />
	<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>
	<script type="text/javascript" src="js/config/configSwitch.js"></script>
	<script type="text/javascript">
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
		var loadingText = "<s:text name='combo.loadingText'/>";
		var pleaseSelectText = "<s:text name='combo.pleaseSelect'/>";
		
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

		var switchbasicinfoText = "<s:text name='config.configSwitch.switchbasicinfo'/>";
		var batchAddSwitchText = "<s:text name='config.configSwitch.batchAddSwitch'/>";
		var addOrUpdateSwitchText = "<s:text name='config.configSwitch.addOrUpdateSwitch'/>";
		var deleteSwitchText = "<s:text name='config.configSwitch.deleteSwitch'/>";
		var saviSystemTableText = "<s:text name='config.configSwitch.saviSystemTable'/>";
		var ipErrorText = "<s:text name='config.configSwitch.ipError'/>";
		var submitButtonText = "<s:text name='config.configSwitch.button.submit'/>";
		var switchInfoText = "<s:text name='config.configSwitch.switchInfo'/>";
		var subnetListText = "<s:text name='config.configSwitch.subnetList'/>";
		var ipv4StartText = "<s:text name='config.configSwitch.ipv4Start'/>";
		var ipv4EndText = "<s:text name='config.configSwitch.ipv4End'/>";
		var ipv6StartText = "<s:text name='config.configSwitch.ipv6Start'/>";
		var ipv6EndText = "<s:text name='config.configSwitch.ipv6End'/>";
		var ipv4ErrorText = "<s:text name='config.configSwitch.ipv4Error'/>";
		var ipv6ErrorText = "<s:text name='config.configSwitch.ipv6Error'/>";
		var batchAddSwitchScriptText="<s:text name='config.configSwitch.batchAddSwitchScript'/>";
		var loseSubnetInfoText="<s:text name='config.configSwitch.loseSubnetInfo'/>";
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