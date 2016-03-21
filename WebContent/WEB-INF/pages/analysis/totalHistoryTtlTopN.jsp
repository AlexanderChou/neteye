<%--
** Copyright (c) 2008, 2009, 2010
**      The Regents of the Tsinghua University, PRC.  All rights reserved.
**
** Redistribution and use in source and binary forms, with or without  modification, are permitted provided that the following conditions are met:
** 1. Redistributions of source code must retain the above copyright  notice, this list of conditions and the following disclaimer.
** 2. Redistributions in binary form must reproduce the above copyright  notice, this list of conditions and the following disclaimer in the  documentation and/or other materials provided with the distribution.
** 3. All advertising materials mentioning features or use of this software  must display the following acknowledgement:
**  This product includes software (iNetBoss) developed by Tsinghua University, PRC and its contributors.
** THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
** ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
** IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
** ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
** FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
** DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
** OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
** HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
** LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
** OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
** SUCH DAMAGE.
*
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN " "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>TopN历史整体流量排序</title>
    <link rel='StyleSheet' href="css/topoInit.css" type="text/css">
    <link rel="stylesheet" type="text/css" href="css/ext-all.css"/>
    <link rel="stylesheet" type="text/css" href="flex/css/Style.css" />
    <script type="text/javascript">
    	var linkStore = "";
    </script>
    <script type="text/javascript" src="flex/js/FusionCharts.js"></script>
    <script type="text/javascript" src="js/ext-base.js"></script>
    <script type="text/javascript" src="js/ext-all.js"></script>
  <script>
Ext.onReady(function() {
	var reader = new Ext.data.JsonReader({
				root : 'totalResult',
				fields : ['resultData','picName']
			});
	var topNStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'json/HistoryTtl.do'
						}),
				// 创建JsonReader读取router记录
				reader : reader
			});
	topNStore.load();
   
    var trafficalltable = new Array();
	var typeName = new Array();	
	var userName = new Array();
	var userValue = new Array();
	
	topNStore.on('load',function(){   
  	var PicName = topNStore.getAt(0).data.picName;
  	var temp = topNStore.getAt(0).data.resultData;
	for (i=0;i<temp.length;i++){
  	  typeName[i] = temp[i].typeName;      
      userName[i] = temp[i].userName;
      userValue[i] = temp[i].userValue;
      
      var tabledata = new Array();
      tabledata.push(userName[i],userValue[i]);      
      trafficalltable.push(tabledata);
    }
	
	var trafficallstore = new Ext.data.SimpleStore({
        fields: [ 
           {name: 'userName'},
           {name: 'userValue'}
        ]
    });
    trafficallstore.loadData(trafficalltable);
    
	
     
    var trafficallPanel = new Ext.grid.GridPanel({
        store: trafficallstore,
        columnWidth:0.40,  height: 500,
        columns:[new Ext.grid.RowNumberer(),
                 {id:'names',header: "TTL区间", width: 200, sortable: true,  dataIndex: 'userName'},
                 {header: "包数（M）", width: 200, sortable: true,  dataIndex: 'userValue'}
        ]
    }) 
    
	var chartHotsite= new Ext.Panel({
	  columnWidth:0.60,
      //html:'<img src="file/analysis/downLoadFile/pic/Historyttl.jpg"></img>'
      html:'<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left"><tr><td valign="top" class="text" align="left"><div id="chartrightdiv" align="left"></div></td></tr></table>'
	})
    setTimeout(function(){
		var chartRightFlex = new FusionCharts('flex/Line.swf', "ChartRId", "100%", "460");
		var rightdataXml = encodeURI(getChHtml());
		chartRightFlex.setDataXML(rightdataXml);		   
		chartRightFlex.render('chartrightdiv');	
	
	}, 500);
   function getChHtml(){
		var topHtml = '<chart yAxisName="" showValues="0" decimals="0" numberSuffix="" lineColor="FCB541" Decimals="2" >';
		var endHtml = '</chart>';
		var cenHtml = '';
		for (i=0;i<temp.length;i++){
	      userName[i] = temp[i].userName;
	      userValue[i] = temp[i].userValue;
	      
	      cenHtml += '<set label="'+userName[i]+'"toolText="'+userName[i]+'区间的包数为：'+userValue[i]+'M" value="'+userValue[i]+'" />';
	    }
		return topHtml+cenHtml+endHtml;
	}
    
      
	  
	var Panel = new Ext.Panel({
	   width : 1000, 
	 height: 480,
	   items : [{
	   xtype:'panel',
	   layout:"column",
	   title:'TTL历史分布',
	   items:[trafficallPanel,chartHotsite]
	   }]
   })

      
   var Panelall = new Ext.Panel({
	   xtype:'panel',
	   items:[ Panel]	   
	   })  
	   Panelall.render('showGrid');
   })
})
</script> 

</head>
<body bgcolor="#FFFFFF" link="#000080" alink="#0000FF" leftmargin="20" topmargin="10">

	<font face="Arial" size="+1" color=#000000 text-align="center"><CENTER>网络整体流量(1小时)</CENTER></font>
	<p>
	<hr style="border: 1px dashed #ccc; width: 100%; height: 5px;" />
	<a href="totalHistoryPktlen.do">包长</a>| <a href="totalHistoryProtocol.do">协议</a>| <a href="totalHistoryPort.do">端口</a>| <b><font color=#0000FF >TTL</font></b>| <a href="totalHistoryHotsite.do">热门站点</a>| 
	<p>
	<div id="outer">
		<div id="bodyDiv">
			
			<div id="infoDiv">
				<div id='showGrid'></div>
			</div>
		</div>
	</div>
</body>
</html>
