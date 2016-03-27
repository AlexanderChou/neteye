<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<title><s:text name='config.configSubnet.title'/></title>
	<link rel='StyleSheet' href="css/savitopoInit.css" type="text/css" />
	<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>
	<script type="text/javascript" src="js/config/configSubnet.js"></script>
	<script type="text/javascript">
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

		var subnetName = "<s:text name='show.showSubnets.subnetName'/>";
		var switchNumber = "<s:text name='show.showSubnets.subnetNumber'/>";
		var subnetID = "<s:text name='show.showSubnets.subnetID'/>";
		var switchBindingTypeHeader = "<s:text name='show.showSubnets.switchBindingTypeHeader'/>";
		var userNumber = "<s:text name='show.showSubnets.userNumber'/>";
		var title = "<s:text name='show.showSubnets.title'/>";
		var barText = "<s:text name='show.showSubnets.barText'/>";
		var saviSystemTableText = "<s:text name='config.configSwitch.saviSystemTable'/>";
		var bindingHeader = "<s:text name='show.showSubnets.bindingHeader'/>";

		var ipv4subNetText = "<s:text name='config.configSubnet.ipv4subNet'/>";
		var ipv6subNetText = "<s:text name='config.configSubnet.ipv6subNet'/>";
		var addOrUpdateSubnetText = "<s:text name='config.configSubnet.addOrUpdateSubnet'/>";
		var deleteSubnetText = "<s:text name='config.configSubnet.deleteSubnet'/>";
		var submitButtonText = "<s:text name='config.configSubnet.button.submit'/>";
		var subnetInfoText = "<s:text name='config.configSubnet.subnetInfo'/>";
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