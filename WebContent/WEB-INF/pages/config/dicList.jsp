<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@ include file="/WEB-INF/pages/common/include.jsp"%>
		<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
		<script type="text/javascript" src="js/ext-base.js"></script>
		<script type="text/javascript" src="js/ext-all.js"></script>
		
		<script type="text/javascript" src="js/common/common.js"></script>
		
		<script type="text/javascript" src="js/dicList.js"></script>
		<title>数据字典基本表</title>
	</head>
	<body>
		<div id="outer">
		<div id="bodyDiv">
			<div id="menu">
				<s:include value="right_menu.jsp"></s:include>
			</div>
		
			<div id="infoDiv">
				<div id='showDicGrid'></div>
			</div>
		</div>
	</div>
	</body>
</html>
