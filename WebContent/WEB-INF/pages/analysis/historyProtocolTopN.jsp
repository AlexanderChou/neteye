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
							url : 'json/HistoryProtocolStatistic.do'
						}),
				// 创建JsonReader读取router记录
				reader : reader
			});
	topNStore.load();
   
    var trafficintable = new Array();
	var trafficouttable = new Array();
	var trafficalltable = new Array();
	var othertable = new Array();
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
         if (typeName[i]=="protocol6"){
         	 trafficalltable.push(tabledata);
         }else if(typeName[i] == "protocol17"){
         	 trafficintable.push(tabledata);
         }else if (typeName[i] == "protocol58"){
            trafficouttable.push(tabledata);
         }else if (typeName[i] == "protocolother"){
            othertable.push(tabledata);
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
    
	var otherstore = new Ext.data.SimpleStore({
        fields: [ 
           {name: 'userName'},
           {name: 'userValue'}
        ]
    });
    otherstore.loadData(othertable);
     
    var trafficallPanel = new Ext.grid.GridPanel({
        store: trafficallstore,
        columnWidth:0.40,  height: 500,
        columns:[new Ext.grid.RowNumberer(),
                 {id:'names',header: "协议", width: 280, sortable: true,  dataIndex: 'userName'},
                 {header: "value(GB)", width: 100, sortable: true,  dataIndex: 'userValue'}
            ]
    }) 
    var trafficinPanel = new Ext.grid.GridPanel({
        store: trafficinstore,
        columnWidth:0.40,  height: 500,
        columns:[new Ext.grid.RowNumberer(),
                 {id:'names',header: "协议", width: 280, sortable: true,  dataIndex: 'userName'},
                 {header: "value(GB)", width: 100, sortable: true,  dataIndex: 'userValue'}
            ]
    }) 
    var trafficoutPanel = new Ext.grid.GridPanel({
        store: trafficoutstore,
        columnWidth:0.40,  height: 500,
        columns:[new Ext.grid.RowNumberer(),
                 {id:'names',header: "协议", width: 280, sortable: true,  dataIndex: 'userName'},
                 {header: "value(GB)", width: 100, sortable: true,  dataIndex: 'userValue'}
            ]
    }) 
    var otherPanel = new Ext.grid.GridPanel({
        store: otherstore,
        columnWidth:0.40,  height: 500,
        columns:[new Ext.grid.RowNumberer(),
                 {id:'names',header: "协议", width: 280, sortable: true,  dataIndex: 'userName'},
                 {header: "value(GB)", width: 100, sortable: true,  dataIndex: 'userValue'}
            ]
    }) 
    
    var chartTrafficall= new Ext.Panel({
	  columnWidth:0.60,
      //html:'<img src="file/analysis/downLoadFile/pic/Historyprotocol6.jpg"></img>'
      html:'<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left"><tr><td valign="top" class="text" align="left"><div id="chartTCPdiv" align="left"></div></td></tr></table>'
	})
	var chartTrafficin= new Ext.Panel({
	  columnWidth:0.60,
      //html:'<img src="file/analysis/downLoadFile/pic/Historyprotocol17.jpg"></img>'
      html:'<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left"><tr><td valign="top" class="text" align="left"><div id="chartUDPdiv" align="left"></div></td></tr></table>'
	})
    var chartTrafficout= new Ext.Panel({
	  columnWidth:0.60,
      //html:'<img src="file/analysis/downLoadFile/pic/Historyprotocol58.jpg"></img>'
      html:'<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left"><tr><td valign="top" class="text" align="left"><div id="chartICMPdiv" align="left"></div></td></tr></table>'
	})
    var chartOther= new Ext.Panel({
	  columnWidth:0.60,
      //html:'<img src="file/analysis/downLoadFile/pic/Historyprotocolother.jpg"></img>'
      html:'<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left"><tr><td valign="top" class="text" align="left"><div id="chartotherdiv" align="left"></div></td></tr></table>'
	})	
	var cenTCPHtml = '';
	var cenUDPHtml = '';
	var cenICMPHtml = '';
	var cenotherHtml = '';
	var topHtml = '';
	var endHtml = '';
	
	setTimeout(function(){
		var chartTCPFlex = new FusionCharts('flex/Column3D.swf', "ChartRId", "100%", "480");
		var chartUDPFlex = new FusionCharts('flex/Column3D.swf', "ChartRId", "100%", "480");
		var chartICMPFlex = new FusionCharts('flex/Column3D.swf', "ChartRId", "100%", "480");
		var chartotherFlex = new FusionCharts('flex/Column3D.swf', "ChartRId", "100%", "480");
		getChHtml();
		chartTCPFlex.setDataXML(encodeURI(topHtml+cenTCPHtml+endHtml));		   
		chartTCPFlex.render('chartTCPdiv');
		chartUDPFlex.setDataXML(encodeURI(topHtml+cenUDPHtml+endHtml));		   
		chartUDPFlex.render('chartUDPdiv');
		chartICMPFlex.setDataXML(encodeURI(topHtml+cenICMPHtml+endHtml));		   
		chartICMPFlex.render('chartICMPdiv');	
		chartotherFlex.setDataXML(encodeURI(topHtml+cenotherHtml+endHtml));		   
		chartotherFlex.render('chartotherdiv');
	
	}, 1000);
   function getChHtml(){
   		topHtml = '<chart yAxisName="" showValues="0" decimals="0" numberSuffix="GB" Decimals="2" >';
		endHtml = '</chart>';
		var countTCP=0;
		var countUDP=0;
		var countICMP=0;
		var countother=0;
		for (i=0;i<temp.length;i++){
	       typeName[i] = temp[i].typeName;      
	       userName[i] = temp[i].userName;
	       userValue[i] = temp[i].userValue;
	       if (typeName[i]=="protocol6"){
	       	  countTCP = countTCP+1;
	          cenTCPHtml += '<set label="'+countTCP+'" toolText="'+userName[i]+'的流量为：'+userValue[i]+'GB" value="'+userValue[i]+'" />';
	       }else if(typeName[i] == "protocol17"){
	       	  countUDP = countUDP+1;
	       	  cenUDPHtml += '<set label="'+countUDP+'"toolText="'+userName[i]+'的流量为：'+userValue[i]+'GB" value="'+userValue[i]+'" />';
	       }else if (typeName[i] == "protocol58"){
	       	  countICMP = countICMP+1;
	          cenICMPHtml += '<set label="'+countICMP+'"toolText="'+userName[i]+'的流量为：'+userValue[i]+'GB" value="'+userValue[i]+'" />';
	       }else if (typeName[i] == "protocolother"){
	       	  countother = countother+1;
	          cenotherHtml += '<set label="'+countother+'"toolText="'+userName[i]+'的流量为：'+userValue[i]+'GB" value="'+userValue[i]+'" />';
	       }  
	    }
	}
	  
	var Panel = new Ext.Panel({
	  width : 1000, 
	   height: 500,
	   items : [{
	   xtype:'panel',
	   layout:"column",
	   title:'用户TCP TopN历史排序',
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
	   title:'用户UDP TopN历史排序',
	   items:[trafficinPanel,chartTrafficin]
	   }]
   })
    
   var Panel2 = new Ext.Panel({
	  width : 1000, 
	   height: 500,
	   items : [{
	   xtype:'panel',
	   layout:"column",
	   title:'用户ICMPV6 TopN历史排序',
	   items:[trafficoutPanel,chartTrafficout]
	   }]
   })
   
   var Panel3 = new Ext.Panel({
	  width : 1000, 
	   height: 500,
	   items : [{
	   xtype:'panel',
	   layout:"column",
	   title:'用户other TopN历史排序',
	   items:[otherPanel,chartOther]
	   }]
   })
   
   var Panelall = new Ext.Panel({
	   xtype:'panel',
	   items:[ Panel,Panel1,Panel2,Panel3]
	   
	   })  
	   Panelall.render('showGrid');
   })
})
</script> 

</head>
<body bgcolor="#FFFFFF" link="#000080" alink="#0000FF" leftmargin="20" topmargin="10">

	<font face="Arial" size="+1" color=#000000 text-align="center"><CENTER>TopN用户流量(1小时)</CENTER></font>
	<p>
	<hr style="border: 1px dashed #ccc; width: 100%; height: 5px;" />
	<!--<hr width="100%" color="#000000" size=2>-->
	<a href="historyTraffic.do">流量</a> | <a href="historyPacket.do">包数</a> | <b><font color=#0000FF >协议</font></b> | <a href="historyPort.do">端口</a>
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
