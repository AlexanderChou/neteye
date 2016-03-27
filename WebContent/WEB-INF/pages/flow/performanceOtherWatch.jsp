<%@page language="java" import="java.util.*"%>
<%@ page import="com.flow.util.PerformanceJsonUtil"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN "   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%
	String viewname = ( String )request.getAttribute( "viewname" );
	String sv = ( String )request.getAttribute( "submitView" );	
	String dicContentId = ( String )request.getAttribute( "para" );
	String dicContentName = ( String )request.getAttribute( "paraName" );
%>
<script type="text/javascript">
<!--
	function  renderPic(value){  
		if(value!=''){
		   var msgstrs = value.split("/");
		   var msgdo =(msgstrs[msgstrs.length-1]).split("_");
		   var performanceid = msgdo[1];
		   var deviceid = msgdo[2];
		   var oid = msgdo[3].substring(0,(msgdo[3].length-4));
		   var javs= "<a href='javascript:doPerformance(&quot;"+deviceid+"&quot;,&quot;"+performanceid+"&quot;,&quot;"+oid+"&quot;)'><img src='"+value+"' /></a>";
		   return javs; 
		}
	}
 	var json = <%PerformanceJsonUtil.columnToJsonData2(out,dicContentId,sv);%>;
//-->
</script>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel='StyleSheet' href="css/topoInit.css" type="text/css">
	<link type="text/css" href="css/ext-all.css"  rel="stylesheet"/>   
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>
	<script type="text/javascript" src="js/performanceFixTime.js"></script>
	<script type="text/javascript" src="js/performanceAllWatch.js"></script>
	<STYLE type="text/css">
	 div#girdshow {
		 text-align:left;
		 width:1000px;
		 margin:0 auto;
	  }
	</STYLE>
</head>
<body>
<br>
<font face="Arial" size="+1" color=#000000 text-align="center"><CENTER><%=dicContentName%>性能图</CENTER></font>
<CENTER>
<FORM action="performanceOtherWatch.do?para=<%=dicContentId%>&&paraName=<%=dicContentName%>" method=post name=submitView>
选择视图
<SELECT name=submitView size=1>
	<s:iterator value="views" status="st" >
	<OPTION value='<s:property value="name"/>_<s:property value="id"/>'><s:property value="name"/></OPTION>
	</s:iterator>
</SELECT>
<INPUT type="submit" value="提交" name=postsub>
</FROM>
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <hr style="border: 1px dashed #ccc; width: 100%; height: 5px;" />
    <s:iterator value="dicDetailList" status="st" >
    	<a href="performanceOtherWatch.do?para=<s:property value='dicContenId'/>&paraName=<s:property value='dicContentName'/>"><s:property value='dicContentName'/>性能图</a> |
    </s:iterator>
     <a href="performanceAllWatch.do">汇总监测图</a> 
  </tr>
</table>
</CENTER>
<br>
<div id="girdshow"></div>
<div id="responseDiv"></div>
<div id="tabDiv"></div>
<div id="inputDiv"></div>
<SCRIPT type="text/javascript">
var viewname= "<%= viewname %>";
var deviceid_para,performanceid_para,oid_para;
function doPerformance( deviceid,performanceid,oid ){
	oid_para = oid;
	performanceid_para = performanceid;
	deviceid_para = deviceid;
	Ext.Ajax.request({
   		url:"fixTimePerformancePic.do?deviceid="+deviceid+"&performanceid="+performanceid+"&oid="+oid,
   		success:function(response, request){
   			showWindow(response.responseText);
   		}
	});
}
</SCRIPT>
	
</body>
</html>