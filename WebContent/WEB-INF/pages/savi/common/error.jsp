<%@ page contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
   <title><s:text name="common.error.title"/></title>
</head>
<body>
<TABLE width="780" align="center" CELLSPACING=0 background="images/bodybg.jpg">
<tr> 
	<td height="39" valign=top>
	<br><div align="center"><font color="#FF0000" size="+1"><b><s:text name="common.error.info"/></b></font></div>
	</td>
</tr>
<tr>
	<td height="100" valign=top>
	<s:property value="exception.message"/>
	<span style="color:red;font:9pt bold"><s:property value="exception.message"/></span>
	</td>
</tr>
<tr>
	<td valign=top><div align="center" style="font:10pt"><s:text name="common.error.tip"/></div><br></td>
</tr>
</table>
</body>
</html>