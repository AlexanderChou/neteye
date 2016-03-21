<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title><s:text name="common.seesionLost.title"/></title>
<script language="JavaScript">
function goroot(){
	/* 设置cookie 记录最后的登录连接 */
	document.cookie = "lastUrl=" + document.location.href + ";name=CNGI";
	var parwindow =window.parent;
	if (parwindow.parent==null){
	    parwindow.location.href="welcome.do";
	}else{
	    parwindow.parent.parent.location.href="welcome.do";
	}
}

window.setTimeout(goroot,5000);

</script>
</head>
<body scroll="no" id="body">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
      <tr><td valign="middle" align="center"><br>
	  <br>
		<table cellspacing="0" cellpadding="0" class="labeltable_middle">
	        <tr>
	        	<td class="labeltd_middle"><br>
	             <table  class="labeltable_middle_table">
	  					<tr height="20">
	                        <td height="20" class="labeltable_middle_td"><s:text name="common.seesionLost.info"/></td>
					    </tr>
					    <tr>
	                        <td align="center" > <br>
	                          <br>
	                          <br>
	                          <font color="#000000" style="font-size:14px"><s:text name="common.seesionLost.tip"/></font><br>
	                          <br>
	                          <br>
	                          <html:text property="title" styleClass="input"  style="width:98%" maxlength="100" />
	                        </td>
	  					</tr>
				</table>
     				<br>
           	  </td>
       		</tr>
		</table>
		<table border="0" cellpadding="0" cellspacing="0" class="labeltable_bottom">
		    <tr>
		        <td class="left">&nbsp;</td>
		        <td class="center">&nbsp;</td>
		        <td class="right">&nbsp;</td>
		    </tr>
		</table>
      <br>     
   </td>
   </tr>
</table>
</body>
</html>