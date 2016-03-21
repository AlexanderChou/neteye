<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="flex/css/Style.css" />
<script type="text/javascript" src="flex/js/FusionCharts.js"></script>
<script type="text/javascript" src="js/ext-base.js"></script>
<script type="text/javascript" src="js/ext-all.js"></script>
<script type="text/javascript">
Ext.onReady(function() {
	var reader = new Ext.data.JsonReader({
				root : 'onedayXML',
				fields : ['xmlName']
			});
	var DelayXmlStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'json/getonedayDelayXML.do'
						}),
				// 创建JsonReader读取router记录
				reader : reader
			});
	DelayXmlStore.load();
	   
	var xmlName;
	
	DelayXmlStore.on('load',function(){   
	  	xmlName = DelayXmlStore.getAt(0).data.xmlName;	  
	  	//alert("gx="+LossXmlStore.getAt(0).data.xmlName); 
	});
	
	var chartDelay= new Ext.Panel( {
		width:1000,
	      //html:'<img src="file/analysis/downLoadFile/pic/Totalportstat.jpg"></img>'
	    html:'<table width="80%" border="0" cellspacing="0" cellpadding="0" align="right"><tr><td valign="top" class="text" align="right"><div id="chartrightdiv" align="right"></div></td></tr></table>'  
		} 
		)
	chartDelay.render('showGrid');
	    setTimeout(function(){
			var chartRightFlex = new FusionCharts('flex/MSLine.swf', "ChartRId", "100%", "530");
			var rightdataXml = encodeURI(eighthoursxmlName);
			chartRightFlex.setDataXML(rightdataXml);		   
			chartRightFlex.render('chartrightdiv');	
		}, 5000);
	  
});
</script>
<body>
<div id="infoDiv">
	<div id='showGrid'></div>
</div>
</body>
</html>