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
    <title>TopN历史排序</title>
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
							url : 'json/HistoryPacketStatistic.do'
						}),
				// 创建JsonReader读取router记录
				reader : reader
			});
	topNStore.load();
   
    var trafficintable = new Array();
	var trafficouttable = new Array();
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
       if (typeName[i]=="PacketAllTopN"){
       	  trafficalltable.push(tabledata);
       }else if(typeName[i] == "PacketInTopN"){
       	  trafficintable.push(tabledata);
       }else if (typeName[i] == "PacketOutTopN"){
          trafficouttable.push(tabledata);
       }
    }
	
	var trafficallstore = new Ext.data.SimpleStore({
        fields: [ 
           {name: 'userName'},
           {name: 'userValue'}
        ]
    });
    trafficallstore.loadData(trafficalltable);
    
	var trafficinstore = new Ext.data.SimpleStore({
        fields: [ 
           {name: 'userName'},
           {name: 'userValue'}
        ]
    });
    trafficinstore.loadData(trafficintable);
    
	var trafficoutstore = new Ext.data.SimpleStore({
        fields: [ 
           {name: 'userName'},
           {name: 'userValue'}
        ]
    });
    trafficoutstore.loadData(trafficouttable);
     
    var trafficallPanel = new Ext.grid.GridPanel({
        store: trafficallstore,
        columnWidth:0.40,  height: 500,
        columns:[new Ext.grid.RowNumberer(),
                 {id:'names',header: "IP/用户ID", width: 280, sortable: true,  dataIndex: 'userName'},
                 {header: "包数(M)", width: 100, sortable: true,  dataIndex: 'userValue'}
            ]
        
        
    }) 
    var trafficinPanel = new Ext.grid.GridPanel({
        store: trafficinstore,
        columnWidth:0.40,  height: 500,
        columns:[new Ext.grid.RowNumberer(),
                 {id:'names',header: "IP/用户ID", width: 280, sortable: true,  dataIndex: 'userName'},
                 {header: "包数(M)", width: 100, sortable: true,  dataIndex: 'userValue'}
            ]
        
        
    }) 
    var trafficoutPanel = new Ext.grid.GridPanel({
        store: trafficoutstore,
        columnWidth:0.40,  height: 480,
        columns:[new Ext.grid.RowNumberer(),
                 {id:'names',header: "IP/用户ID", width: 280, sortable: true,  dataIndex: 'userName'},
                 {header: "包数(M)", width: 100, sortable: true,  dataIndex: 'userValue'}
            ]
    }) 
    
    var chartTrafficall= new Ext.Panel({
	  columnWidth:0.60,
      //html:'<img src="file/analysis/downLoadFile/pic/HistoryPacketAllTopN.jpg"></img>'
      html:'<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left"><tr><td valign="top" class="text" align="left"><div id="charttopdiv" align="left"></div></td></tr></table>'
	})
	var chartTrafficin= new Ext.Panel({
	  columnWidth:0.60,
      //html:'<img src="file/analysis/downLoadFile/pic/HistoryPacketInTopN.jpg"></img>'
      html:'<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left"><tr><td valign="top" class="text" align="left"><div id="chartmiddiv" align="left"></div></td></tr></table>'
	})
    var chartTrafficout= new Ext.Panel({
	  columnWidth:0.60,
      //html:'<img src="file/analysis/downLoadFile/pic/HistoryPacketOutTopN.jpg"></img>'
      html:'<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left"><tr><td valign="top" class="text" align="left"><div id="chartbottomdiv" align="left"></div></td></tr></table>'
	})
	var cenAllHtml = '';
	var cenInHtml = '';
	var cenOutHtml = '';
	var topHtml = '';
	var endHtml = '';
	
	setTimeout(function(){
		var chartTopFlex = new FusionCharts('flex/Column2D.swf', "ChartRId", "100%", "480");
		var chartMidFlex = new FusionCharts('flex/Column2D.swf', "ChartRId", "100%", "480");
		var chartBottomFlex = new FusionCharts('flex/Column2D.swf', "ChartRId", "100%", "480");
		getChHtml();
		chartTopFlex.setDataXML(encodeURI(topHtml+cenAllHtml+endHtml));		   
		chartTopFlex.render('charttopdiv');
		chartMidFlex.setDataXML(encodeURI(topHtml+cenInHtml+endHtml));		   
		chartMidFlex.render('chartmiddiv');	
		chartBottomFlex.setDataXML(encodeURI(topHtml+cenOutHtml+endHtml));		   
		chartBottomFlex.render('chartbottomdiv');
	
	}, 1000);
   function getChHtml(){
   		topHtml = '<chart yAxisName="" showValues="0" decimals="0" numberSuffix="M" Decimals="2" >';
		endHtml = '</chart>';
		var countAll=0;
		var countIn=0;
		var countOut=0;
		for (i=0;i<temp.length;i++){
	       typeName[i] = temp[i].typeName;      
	       userName[i] = temp[i].userName;
	       userValue[i] = temp[i].userValue;
	       if (typeName[i]=="PacketAllTopN"){
	       	  countAll = countAll+1;
	          cenAllHtml += '<set label="'+countAll+'" toolText="'+userName[i]+'的总包数为：'+userValue[i]+'M" value="'+userValue[i]+'" />';
	       }else if(typeName[i] == "PacketInTopN"){
	       	  countIn = countIn+1;
	       	  cenInHtml += '<set label="'+countIn+'"toolText="'+userName[i]+'的入包数为：'+userValue[i]+'M" value="'+userValue[i]+'" />';
	       }else if (typeName[i] == "PacketOutTopN"){
	       	  countOut = countOut+1;
	          cenOutHtml += '<set label="'+countOut+'"toolText="'+userName[i]+'的出包数为：'+userValue[i]+'M" value="'+userValue[i]+'" />';
	       }
	    }
	}
	
	var Panel = new Ext.Panel({
	  width : 1000, 
	   height: 500,
	   items : [{
	   xtype:'panel',
	   layout:"column",
	   title:'用户历史总包数topN排序',
	   items:[trafficallPanel,chartTrafficall]
	   }]
   })

   var Panel1 = new Ext.Panel({
	  width : 1000, 
	   //collapsible: true,
	   //collapsed: true,
	   height: 500,
	   items : [{
	   xtype:'panel',
	   layout:"column",
	   title:'用户历史入包数TopN排序',
	   items:[trafficinPanel,chartTrafficin]
	   }]
   })
    
   var Panel2 = new Ext.Panel({
	   width : 1000, 
	   height: 500,
	   items : [{
	   xtype:'panel',
	   layout:"column",
	   title:'用户历史出包数TopN排序',
	   items:[trafficoutPanel,chartTrafficout]
	   }]
   })
   
  var Panelall = new Ext.Panel({
	   xtype:'panel',
	   items:[ Panel,Panel1,Panel2]
	   
	   })  
	Panelall.render('showGrid');
   })
})
	// categoryPanel.getSelectionModel().selectFirstRow();
</script> 

</head>
<body bgcolor="#FFFFFF" link="#000080" alink="#0000FF" leftmargin="20" topmargin="10">

	<font face="Arial" size="+1" color=#000000 text-align="center"><CENTER>TopN用户流量(1小时)</CENTER></font>
	<p>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
	<hr style="border: 1px dashed #ccc; width: 100%; height: 5px;" />
	
	<!--<hr width="100%" color="#000000" size=2>-->
	<a href="historyTraffic.do">流量</a> | <b><font color=#0000FF >包数</font></b> | <a href="historyProtocol.do">协议</a> | <a href="historyPort.do">端口</a>
	  </tr>
</table>
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
