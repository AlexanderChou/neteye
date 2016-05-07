<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<%
	String flag = request.getParameter("flag");
	if(flag==null || flag.equals("")){
		flag = "true";
	}
%>
<head>
	<title><s:text name='show.showSubnets.title'/></title>
	<%if(flag.equals("true")){ %>
	<meta http-equiv="refresh"  content="1;url=showSubnets.do?flag=false">

	<% }else{ %>
	<meta http-equiv="refresh"  content="300;url=showSubnets.do?flag=false" >

	<%flag = "false";} %>
	<link rel='StyleSheet' href="css/savitopoInit.css" type="text/css" />
	<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>
	<script type="text/javascript" src="js/mysavi/totalView.js"></script>
	<SCRIPT type="text/javascript">
		var displayMsgText = "<s:text name='grid.displayMsg'/>";
		var noRecordText = "<s:text name='grid.noRecord'/>";
		var subnetName = "<s:text name='show.showSubnets.subnetName'/>";
		var switchNumber = "<s:text name='show.showSubnets.subnetNumber'/>";
		var subnetID = "<s:text name='show.showSubnets.subnetID'/>";
		var switchBindingTypeHeader = "<s:text name='show.showSubnets.switchBindingTypeHeader'/>";
		var userNumber = "<s:text name='show.showSubnets.userNumber'/>";
		var title = "<s:text name='show.showSubnets.title'/>";
		var barText = "<s:text name='show.showSubnets.barText'/>";
		var bindingHeader = "<s:text name='show.showSubnets.bindingHeader'/>";
		var saviText = "<s:text name='show.showSwitches.savi'/>";
	</script>
	<%if(flag.equals("true")){ %>
	<script type="text/javascript">
		Ext.onReady( function() {
			var tableDiv=document.getElementById("bodyDiv");
			tableDiv.style.display = 'none';
		});
	</script>
	<%} %>
</head>
<body>
	<div id="bodyDiv" class="align-center">
		<div id="infoDiv">
			<div id='showDiv' ></div>
		</div>
	</div>
</body>
</html>