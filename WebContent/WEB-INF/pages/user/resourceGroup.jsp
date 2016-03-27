<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>资源组信息</title>
		<link rel='StyleSheet' href="css/topoInit.css" type="text/css" />
		<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
		<script type="text/javascript" src="js/ext-base.js"></script>
		<script type="text/javascript" src="js/ext-all.js"></script>
		<script type="text/javascript" src="js/resourceGroup.js"></script>
		<script type="text/javascript">
			var userName='<%=session.getAttribute("userName")%>';
		</script>
		<style type="text/css">
		.x-tree-node-icon {
			display: none;
		}
		</style>
	</head>
	<body>
		<div id="outer">
		<div id="bodyDiv">
			<div id="menu">
				<s:include value="right_menu.jsp"></s:include>
			</div>
		
			<div id="infoDiv">
				<div id='showDiv'></div>
			</div>
		</div>
	</div>
	</body>
</html>
