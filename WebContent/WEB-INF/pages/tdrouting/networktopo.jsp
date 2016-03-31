<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div style="height: 500px;" id="canvas"/>
<script src="http://demo.qunee.com/lib/qunee-min.js"></script>
<script language="JavaScript" type="text/javascript">
var graph = new Q.Graph('canvas');
var hello = graph.createNode("Hello", -100, -50);
var qunee = graph.createNode("Qunee", 100, 50);
var edge = graph.createEdge("Hello\nQunee", hello, qunee);
</script>
</body>
</html>