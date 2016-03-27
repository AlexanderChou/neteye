<%@page language="java" import="java.util.*"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN "   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        	<link rel='StyleSheet' href="css/topoInit.css" type="text/css">
        <link rel="stylesheet" type="text/css" href="css/ext-all.css" />
        <script type="text/javascript" src="js/ext-base.js"></script>
        <script type="text/javascript" src="js/ext-all.js"></script>
          <script type="text/javascript" src="js/fixTime.js"></script>
<style>
  div#girdshow {
 text-align:left;
 width:1000px;
 margin:0 auto;
  }
		#viewname {
		width:1000px;
			text-align:left;
			float:left;
			
		}
		#view{
	width:250px;
	height:200px;
	padding-right:3px;
	margin:2px;
	text-align:center; 
	float:left;
	border:1px dashed #C9C4CA;
	overflow:hidden;
}
		
</style>
</head>
<body>
<%

String viewtoname = ( String )request.getAttribute( "viewtoname" );
String sv = ( String )request.getAttribute( "submitView" );		 
%>
<br>
    <font face="Arial" size="+1" color=#000000 text-align="center"><CENTER>包 长 统 计 图</CENTER></font>
    <CENTER>
    <FORM action="lenWatch.do?toview=1" method=post name=submitView>
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
    <a href="bitWatch.do?submitView=<%= sv %>">比特流量图 </a> | <a href="packetWatch.do?submitView=<%= sv %>">分组流量图</a> | <b>包长统计图</b> | <a href="flowEventStatus.do?submitView=<%= sv %>">汇总监测图</a> | <a href="FlowStatisticQuery.do">流量统计</a> 
  </tr>
</table>
</CENTER>
<br>
<div id="outer">
	<div id="bodyDiv">
		<div id="menu">
		</div>
	
		<div id="infoDiv">
			
			<div id="eventListDiv">
						<s:iterator value="FlowEvent" id="tmp"> 
									<div id="viewname"><h2><s:property value='#tmp.key'/></h2></div>
					<s:form name="myform">
										<s:iterator value="#tmp.value" status="tmptable"> 
										<div id="view">
								        		<a href="javascript:dolen('<s:property value='ip'/>','<s:property value='inf'/>')"><img src="<s:property value='pic3'/>"/></a><br>
								        </div>
								        </s:iterator>
					</s:form>
						</s:iterator>
			</div>	
		<div id="showDiv"></div>	
			
		</div>
	</div>
</div>
<div id="responseDiv"></div>
<div id="tabDiv"></div>
<div id="inputDiv"></div>
	<SCRIPT type="text/javascript">
	var submitView ="${param.submitView}";
	var toview ="${param.toview}";
    	var viewname = submitView.split("_");
    	var viewnameuse = viewname[0];
    	if(viewname=="ALL"){
    	
    	viewnameuse ="全部"}
    	else{
    	viewnameuse ="<%= viewtoname %>"
    	}
    	
		Ext.onReady(function(){
			var panel = new Ext.Panel({
				title:viewnameuse+"包长统计图",
				width:1070,
				items:[
				{
					width:1070,
					height:document.body.clientHeight*0.95 - 20,
					autoScroll:true,
					contentEl:"eventListDiv"
				}]
			});
			
			panel.render("showDiv");
		});
		
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
	</body>
</html>