

<%@page language="java" import="java.util.*"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN "   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>Insert title here</title>
        <link rel="stylesheet" type="text/css" href="css/ext-all.css" />
          	<link type="text/css" href="css/topoInit.css"  rel="StyleSheet">
        <script type="text/javascript" src="js/ext-base.js"></script>
        <script type="text/javascript" src="js/ext-all.js"></script>
        <script type="text/javascript" src="js/fixTime.js"></script>
         <script language="javascript" src="js/flowlistview.js"></script>
        
        <style type="text/css">
  body { text-align:center; }
  div#girdshow {
 text-align:left;
 width:1000px;
 margin:0 auto;
  }



  </style>
  
</head>
<body>
<%

String sv = ( String )request.getAttribute( "submitView" );
String viewname = ( String )request.getAttribute( "viewname" );
		 
		 
%>	
<br>
    <font face="Arial" size="+1" color=#000000 text-align="center"><CENTER>流量汇总监测图</CENTER></font>
<FORM action="flowEventStatus.do" method=post name=submitView>
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
    <a href="bitWatch.do?submitView=<%= sv %>">比特流量图</a> | <a href="packetWatch.do?submitView=<%= sv %>">分组流量图</a> | <a href="lenWatch.do?submitView=<%= sv %>">包长统计图</a> | 汇总监测图  | <a href="FlowStatisticQuery.do">流量统计</a>
  </tr>
</table>
<br>
	<div id="girdshow" >
</div>

<div id="responseDiv"></div>
<div id="tabDiv"></div>
</body>
    <script language="javascript" >
    	var deviceType = "${param.style}";
    	var device_id = "${param.deviceId}";
    	var submitView ="${param.submitView}";
    	var viewname = submitView.split("_");
    	var viewnameuse = viewname[0];
    	if(viewnameuse =="ALL"){
    	viewnameuse = "全部"}
    	else{
    	viewnameuse ="<%= viewname %>"
    	}
    	
    </script>
<SCRIPT LANGUAGE="JavaScript">
		var picType="";
        var ip_param="";
        var index_param="";
	function dobit( routerIp,ifindex )
	{
		picType="bit";
		ip_param=routerIp;
        index_param=ifindex;
		Ext.Ajax.request({
          		url:"fixTimePic.do?routerIP="+routerIp+"&ifIndex="+ifindex+"&picType=bit",
          		success:function(response, request){
          			showWindow(response.responseText);
          		}
			});
	}
function dopkt( routerIp,ifindex )
	{
		picType="pkt";
		ip_param=routerIp;
		index_param=ifindex;
		Ext.Ajax.request({				
          		url:"fixTimePic.do?routerIP="+routerIp+"&ifIndex="+ifindex+"&picType=pkt&type=1",
          		success:function(response, request){
          			showWindow(response.responseText);
          		}
			});
	}
function dolen( routerIp,ifindex )
	{
		picType="len";
		ip_param=routerIp;
        index_param=ifindex;
		Ext.Ajax.request({
          		url:"fixTimePic.do?routerIP="+routerIp+"&ifIndex="+ifindex+"&picType=len&type=2",
          		success:function(response, request){
          			showWindow(response.responseText);
          		}
			});
	}
</SCRIPT>

</html>