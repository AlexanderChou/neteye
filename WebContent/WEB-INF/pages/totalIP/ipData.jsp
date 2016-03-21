<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>节点IPv6地址记录与次数统计</title>
	<link rel='StyleSheet' href="css/topoInit.css" type="text/css" />
	<link type="text/css" href="css/ipDate.css"  rel="StyleSheet">
	<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="flex/css/Style.css" />
    <script type="text/javascript" src="flex/js/FusionCharts.js"></script>
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>
	<script type="text/javascript" src="js/ipData.js"></script>
</head>
<body >
<input id="engName" type="hidden" value='<s:property value="engName"/>'/>
<input id="chineseName" type="hidden" value='<s:property value="chineseName"/>'/>
<input id="dateStr" type="hidden" value='<s:property value="dateStr"/>'/>

<div id="outer">
	<div id="bodyDiv">
	
		
			<div id="infoDiv">
				<div id='tabDiv'>
				</div>
				<div id='historyPanelDiv' style="background-color:yellow;display:none;">
				</div>
				
				<div id="showDiv" style="clear:both;display:none;">
				</div>
				<div id="selectDateDiv">
							
					<s:iterator  value="dateList" status="st">
						
						<s:if test="ipCount<10000 ">
							<div id="selectDateButton"  onmouseover="this.style.backgroundColor='#D6EFFC'" onmouseout="this.style.backgroundColor='#F5FAFC'" >
							<a href="ipData.do?engName=<s:property value="engName"/>&dateStr=<s:property value="date"/> ">
								<s:property value="date" /> 
							</a><br>
							<img src="images/common/_blank.gif" style= "width:25px;height:10px;background:url(images/common/green_bar.jpg) no-repeat scroll 0 0;"/>
			  				<span >&nbsp;<s:property value="ipCount"/></span>
							</div>
						</s:if>
						<s:else>
							<div id="selectDateButton"  onmouseover="this.style.backgroundColor='#D6EFFC'" onmouseout="this.style.backgroundColor='#F5FAFC'" >
							<a href="ipData.do?engName=<s:property value="engName"/>&dateStr=<s:property value="date"/> ">
								<s:property value="date" /> 
							</a><br>
							<img src="images/common/_blank.gif" style= "width:25px;height:10px;background:url(images/common/red_bar.jpg) no-repeat scroll 0 0;">
			  				<span >&nbsp;<s:property value="ipCount"/></span>
							</div>	
						</s:else>
							
						
					</s:iterator>
							
				</div>
			</div>
		</div>
</div>
<script type="text/javascript">
	var switchBindingTypeHeader = '&nbsp;&nbsp;' + 
		'&nbsp;&nbsp;<span>小于1万&nbsp;&nbsp;</span>' + 
		'<img src="images/common/_blank.gif" style= "width:25px;height:10px;background:url(images/common/green_bar.jpg) no-repeat scroll 0 0;">' +
		'&nbsp;&nbsp;&nbsp;&nbsp;<span>大于1万&nbsp;&nbsp;</span>' + 
		'<img src="images/common/_blank.gif" style= "width:25px;height:10px;background:url(images/common/red_bar.jpg) no-repeat scroll 0 0;">&nbsp;&nbsp;';
var dateBar = new Ext.Toolbar([new Ext.Toolbar.TextItem(switchBindingTypeHeader)
				//	{id: 'CurrentDateItem',
				//	pressed : false,
				//	text : switchBindingTypeHeader,	
				//	},switchBindingTypeHeader


]);
var historyPanel = new Ext.Panel({
		title:"历史日期IP地址统计",
		border: false,
		tbar : dateBar,
		items:[
			{
				autoScroll: true, 
				height:document.body.clientHeight*0.95-50,
				contentEl:"selectDateDiv"
			}
			
		]
});
	historyPanel.render("historyPanelDiv");  
	var tabs = new Ext.TabPanel({
	 renderTo: "tabDiv",
	 activeTab: 0,
	 height : document.body.clientHeight - 50,
	width : document.body.clientWidth < 1024? 960:1220,
     items:[{title: '实时IP地址统计',
     			contentEl:'showDiv',
               	listeners :{
               			activate:function(tab) {
                			createHistoryPanel();
               				document.getElementById("showDiv").style.display = "inline";
              	 	}
               }
           },historyPanel]
});

</script>
</body>
</html>