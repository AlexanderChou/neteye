<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IP地址统计的节点列表</title>
	<link rel='StyleSheet' href="css/topoInit.css" type="text/css" />
	<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="flex/css/Style.css" />
	<script type="text/javascript">
    	var linkStore = "";
    </script>
    <script type="text/javascript" src="flex/js/FusionCharts.js"></script>
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>
	<script type="text/javascript" src="js/nodeIP.js"></script>
</head>
<body>

<input id="chineseName" type="hidden" value='<s:property value="chineseName"/>'/>
<input id="engName" type="hidden" value='<s:property value="engName"/>'/>
<div id="bodyDiv" class="align-center">
		<div id="infoDiv">
				<div id='showDiv'></div>
				<div id='showWeekDiv'></div>
				<div id='showMonthDiv'></div>
				<div id='showYearDiv'></div>
		</div>
	</div>

</body>
</html>