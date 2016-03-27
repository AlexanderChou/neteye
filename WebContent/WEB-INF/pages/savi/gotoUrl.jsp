<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
<form id="sava1" action="http://101.6.30.74:8080/admin/login.action" method="post">
	<input name="user.username" type="hidden" value="admin" />
	<input name="user.password" type="hidden" value="admin" />
</form>
<form id="sava2" action="http://202.112.237.136/php/loginchk.php" method="post">
	<input name="username" type="hidden" value="root" />
	<input name="pwd" type="hidden" value="123456" />
</form>

<script type="text/javascript">
	var url = location.href;
	var param = url.substr(url.indexOf("type=") + 5);
	//alert(param);
	if(param == "sava1"){
		document.getElementById("sava1").submit();
	}
	if(param == "sava2"){
		document.getElementById("sava2").submit();
	}
	
</script>
</body>
</html>