<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<title><s:text name='statistic.statisticUserInformation.title'/></title>
	<link rel='StyleSheet' href="css/savitopoInit.css" type="text/css" />
	<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>
	<script type="text/javascript" src="js/statistic/statisticUserInformation.js"></script>
	<script type="text/javascript">	
		var displayMsgText = "<s:text name='grid.displayMsg'/>";
		var noRecordText = "<s:text name='grid.noRecord'/>";
		var userInfoText = "<s:text name='statistic.statisticUserInformation.title'/>";
		var userNameText = "<s:text name='statistic.userInfo.userName'/>"; 
		var userMACText = "<s:text name='statistic.userInfo.userMAC'/>";
		var userIPText = "<s:text name='statistic.userInfo.userIP'/>";
		var ifIndexText = "<s:text name='statistic.userInfo.ifIndex'/>";
		var switchNameText = "<s:text name='statistic.userInfo.switchName'/>";
		var switchIPv4Text = "<s:text name='statistic.userInfo.switchIPv4'/>";
		var switchIPv6Text = "<s:text name='statistic.userInfo.switchIPv6'/>";
		var startTimeText = "<s:text name='statistic.userInfo.startTime'/>";
		var endTimeText = "<s:text name='statistic.userInfo.endTime'/>";
		var buttonText =  "<s:text name='statistic.userInfo.button'/>";
		var userOnlineText="<s:text name='statistic.userInfo.userOnline'/>";
		var userNameIsNotNullText="<s:text name='statistic.userInfo.userNameIsNotNull'/>";
		var bBindingTypeText = "<s:text name='bindingTable.bindingType'/>";
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