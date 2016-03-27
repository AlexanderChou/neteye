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
    <title>TopN排序</title>
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
							url : 'json/UserTrafficStatistic.do'
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
      if (typeName[i]=="TrafficAllTopN"){
      	 trafficalltable.push(tabledata);
      }else if(typeName[i] == "TrafficInTopN"){
      	 trafficintable.push(tabledata);
      }else if (typeName[i] == "TrafficOutTopN"){
         trafficouttable.push(tabledata);//读记录的时候是根据不同的类型来分别处理
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
        columnWidth:0.4,
          height: 500,
        columns:[new Ext.grid.RowNumberer(),
                 {id:'names',header: "IP/用户ID", width: 280, sortable: true,  dataIndex: 'userName'},
                 {header: "流量(MB)", width: 100, sortable: true,  dataIndex: 'userValue'}
        ]
      //  autoExpandColumn: 'names'
    }) 
    var trafficinPanel = new Ext.grid.GridPanel({
        store: trafficinstore,
        columnWidth:0.4,
          height: 500,
        columns:[new Ext.grid.RowNumberer(),
                 {id:'names',header: "IP/用户ID", width: 280, sortable: true,  dataIndex: 'userName'},
                 {header: "流量(MB)", width: 100, sortable: true,  dataIndex: 'userValue'}
        ]
       // autoExpandColumn: 'names'
    }) 
    var trafficoutPanel = new Ext.grid.GridPanel({
        store: trafficoutstore,
        columnWidth:0.4,
         height: 500,
        columns:[new Ext.grid.RowNumberer(),
                 {id:'names',header: "IP/用户ID", width: 280, sortable: true,  dataIndex: 'userName'},
                 {header: "流量(MB)", width: 100, sortable: true,  dataIndex: 'userValue'}
        ]
      //  autoExpandColumn: 'names'
    }) 
    
    var chartTrafficall= new Ext.Panel({
	  columnWidth:0.6,
      //html:'<img src="file/analysis/downLoadFile/pic/TrafficAllTopN.jpg"></img>'
      html:'<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left"><tr><td valign="top" class="text" align="left"><div id="chartAlldiv" align="left"></div></td></tr></table>'
	})	
	var chartTrafficin= new Ext.Panel({
	  columnWidth:0.6,
      //html:'<img src="file/analysis/downLoadFile/pic/TrafficInTopN.jpg"></img>'
      html:'<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left"><tr><td valign="top" class="text" align="left"><div id="chartIndiv" align="left"></div></td></tr></table>'
	})	
    var chartTrafficout= new Ext.Panel({
	  columnWidth:0.6,
      //html:'<img src="file/analysis/downLoadFile/pic/TrafficOutTopN.jpg"></img>'
      html:'<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left"><tr><td valign="top" class="text" align="left"><div id="chartOutdiv" align="left"></div></td></tr></table>'
	})	
	var cenAllHtml = '';
	var cenInHtml = '';
	var cenOutHtml = '';
	var topHtml = '';
	var endHtml = '';
	
	setTimeout(function(){
		var chartAllFlex = new FusionCharts('flex/Column3D.swf', "ChartRId", "100%", "480");
		var chartInFlex = new FusionCharts('flex/Column3D.swf', "ChartRId", "100%", "480");
		var chartOutFlex = new FusionCharts('flex/Column3D.swf', "ChartRId", "100%", "480");
		getChHtml();
		chartAllFlex.setDataXML(encodeURI(topHtml+cenAllHtml+endHtml));		   
		chartAllFlex.render('chartAlldiv');
		chartInFlex.setDataXML(encodeURI(topHtml+cenInHtml+endHtml));		   
		chartInFlex.render('chartIndiv');
		chartOutFlex.setDataXML(encodeURI(topHtml+cenOutHtml+endHtml));		   
		chartOutFlex.render('chartOutdiv');	
	
	}, 1000);
   function getChHtml(){
   		topHtml = '<chart yAxisName="" showValues="0" decimals="0" numberSuffix="MB" Decimals="2" >';
		endHtml = '</chart>';
		var countAll=0;
		var countIn=0;
		var countOut=0;
		for (i=0;i<temp.length;i++){
	       typeName[i] = temp[i].typeName;      
	       userName[i] = temp[i].userName;
	       userValue[i] = temp[i].userValue;
	       if (typeName[i]=="TrafficAllTopN"){
	       	  countAll = countAll+1;
	          cenAllHtml += '<set label="'+countAll+'" toolText="'+userName[i]+'的流量为：'+userValue[i]+'MB" value="'+userValue[i]+'" />';
	       }else if(typeName[i] == "TrafficInTopN"){
	       	  countIn = countIn+1;
	       	  cenInHtml += '<set label="'+countIn+'"toolText="'+userName[i]+'的流量为：'+userValue[i]+'MB" value="'+userValue[i]+'" />';
	       }else if (typeName[i] == "TrafficOutTopN"){
	       	  countOut = countOut+1;
	          cenOutHtml += '<set label="'+countOut+'"toolText="'+userName[i]+'的流量为：'+userValue[i]+'MB" value="'+userValue[i]+'" />';
	       } 
	    }
	}
  
	  
	var Panel = new Ext.Panel({
	   width : 1000, 
	   height: 500,
	   items : [{
	   xtype:'panel',
	   layout:"column",
	   title:'用户总流量TopN排序',
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
	   title:'用户入流量TopN排序',
	   items:[trafficinPanel,chartTrafficin]
	   }]
   })
    
   var Panel2 = new Ext.Panel({
	   width : 1000, 
	   height: 500,
	   items : [{
	   xtype:'panel',
	   layout:"column",
	   title:'用户出流量TopN排序',
	   items:[trafficoutPanel,chartTrafficout]
	   }]
   })
   
   var Panelall = new Ext.Panel({
	   autoScroll : true,
	   items:[ Panel,Panel1,Panel2]})  
	   Panelall.render('showGrid');
   })
})
</script> 

</head>
<body bgcolor="#FFFFFF" link="#000080" alink="#0000FF" leftmargin="20" topmargin="10">

	<font face="Arial" size="+1" color=#000000 text-align="center"><CENTER>TopN用户流量(1分钟)</CENTER></font>
	<p>
	<hr style="border: 1px dashed #ccc; width: 100%; height: 5px;" />
	<!--<hr width="100%" color="#000000" size=2>-->
	<b><font color=#0000FF>流量</font></b> | <a href="userPacket.do">包数</a>| <a href="userProtocol.do">协议</a>| <a href="userPort.do">端口</a> 
	<p>
	<br>
	<div id="outer">
		<div id="bodyDiv">
			<div id="infoDiv">
				<div id='showGrid'></div>
			</div>
		</div>
	</div>
</body>
</html>
