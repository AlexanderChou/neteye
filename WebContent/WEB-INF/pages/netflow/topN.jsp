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
    <script type="text/javascript">
    	var linkStore = "";
    </script>
    
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
							url : 'json/TopNstatistic.do'
						}),
				// 创建JsonReader读取router记录
				reader : reader
			});
	topNStore.load();
   
	var protocoltable = new Array();
	var srcporttable = new Array();
	var dstporttable = new Array();
	var sessiontable = new Array();
	var typename = new Array();
	var key = new Array();
	var bytes =new Array();
	var pkts = new Array();
	var srcip = new Array();
	var dstip = new Array();
	var srcport = new Array();
	var dstport = new Array();
	var protocol = new Array();
	var starttime = new Array();
	var duration = new Array();
	
	topNStore.on('load',function(){   
		   	 var PicName = topNStore.getAt(0).data.picName;
		   	 var temp = topNStore.getAt(0).data.resultData;
  		     for (i=0;i<temp.length;i++){
		   	  typename[i] = temp[i].typename;      
              key[i] = temp[i].key;
              bytes[i] = temp[i].bytes;  
              pkts[i] = temp[i].pkts;
              srcip[i] = temp[i].srcip;
              dstip[i] = temp[i].dstip;
              srcport[i] = temp[i].srcport;
              dstport[i] = temp[i].dstport;
              protocol[i] = temp[i].protocol;
              starttime[i] = temp[i].starttime;
              duration[i] = temp[i].duration;
              var tabledata = new Array();
              tabledata.push(key[i],bytes[i],pkts[i]);
              var sessiondata = new Array();
              sessiondata.push(srcip[i],dstip[i],srcport[i],dstport[i],protocol[i],starttime[i],duration[i],bytes[i],pkts[i]);
              if (typename[i]=="protocol")
                   {
              	     protocoltable.push(tabledata);
              	     //alert("table[0]="+protocoltable.length);
                   }
                else if(typename[i] == "srcport")
                   {
              	     srcporttable.push(tabledata);
              	     
                   }
                else if (typename[i] == "dstport") 
                   {
                   	 dstporttable.push(tabledata);
                   }
                else if (typename[i] == "session")
                   {
                   	 sessiontable.push(sessiondata);
                   }
  		        }
          //   alert(temp.length);
	
	  var protocolstore = new Ext.data.SimpleStore({
        fields: [ 
           {name: 'key'},
           {name: 'bytes'},
           {name: 'pkts'}
        ]
    });
    protocolstore.loadData(protocoltable);
     
     
     var srcportstore = new Ext.data.SimpleStore({
        fields: [
           {name: 'key'},
           {name: 'bytes'},
           {name: 'pkts'}
        ]
    });
    srcportstore.loadData(srcporttable);
     
     
     var dstportstore = new Ext.data.SimpleStore({
        fields: [
           
           {name: 'key'},
           {name: 'bytes'},
           {name: 'pkts'}
        ]
    });
    dstportstore.loadData(dstporttable);        
   
    var sessionstore = new Ext.data.SimpleStore({
        fields:[
         
          {name: 'srcip'},
          {name: 'dstip'},
          {name: 'srcport'},
          {name: 'dstport'},
          {name: 'protocol'},
          {name: 'startime'},
          {name: 'duration'},
          {name: 'bytes'},
          {name: 'pkts'}
          
        ]
     	
    });
     sessionstore.loadData(sessiontable); 
     
    var protocolPanel = new Ext.grid.GridPanel({
        store: protocolstore,
        columnWidth:0.50,
        columns:[{id:'names',header: "Protocol", width: 70, sortable: true, dataIndex: 'key'},
                 {header: "bytes", width: 145, sortable: true,  dataIndex: 'bytes'},
                 {header: "pkts", width: 145, sortable: true,  dataIndex: 'pkts'}
            ],
        autoExpandColumn: 'names',
        width:400
        
        
    }) 
    
     
    var srcportPanel = new Ext.grid.GridPanel({
        store: srcportstore,
        columnWidth:0.50,
        columns:[ {id:'names',header: "Srcport", width: 70, sortable: true, dataIndex: 'key'},
                  {header: "bytes", width: 145, sortable: true,  dataIndex: 'bytes'},
                  {header: "pkts", width: 145, sortable: true,  dataIndex: 'pkts'}
            ],
        stripeRows: true,
        autoExpandColumn: 'names',
        width:400
       
        
    }) 
    
     
	
	
    
    
    
    var dstportPanel = new Ext.grid.GridPanel({
        store: dstportstore,
        columnWidth:0.50,
        columns:[ {id:'names',header: "Dstport", width: 70, sortable: true, dataIndex: 'key'},
                  {header: "bytes", width: 145, sortable: true,  dataIndex: 'bytes'},
                  {header: "pkts", width: 145, sortable: true,  dataIndex: 'pkts'}
            ],    
        stripeRows: true,
        autoExpandColumn: 'names',
        width:400
       
        
    })        
    
    var sessionPanel = new Ext.grid.GridPanel({
        store: sessionstore,
        columns:[  {header:"Srcip",width: 70,sortable:true,dataIndex:'srcip'},
                   {header:"dstip",width: 70,sortable:true,dataIndex:'dstip'},
                   {header:"srcport",width: 70,sortable:true,dataIndex:'srcport'},
                   {header:"dstport",width: 70,sortable:true,dataIndex:'dstport'},
                   {header:"protocol",width: 70,sortable:true,dataIndex:'protocol'},
                   {header:"duration",width: 70,sortable:true,dataIndex:'duration'},
                   {header:"bytes",width: 70,sortable:true,dataIndex:'bytes'},
                   {header:"pkts",width: 70,sortable:true,dataIndex:'pkts'} 
        ],
        stripeRows: true,
        
        width:800
        
    })
    
     var chartProtocol= new Ext.Panel({
	  columnWidth:0.50,
      html:'<img src="file/netflow/downLoadFile/pic/protocol.jpg"></img>'
	})
    
	 var chartSrcport= new Ext.Panel({
	  columnWidth:0.50,
      html:'<img src="file/netflow/downLoadFile/pic/srcport.jpg"></img>'
	})
    
      var chartDstport= new Ext.Panel({
	  columnWidth:0.50,
      html:'<img src="file/netflow/downLoadFile/pic/dstport.jpg"></img>'
	})
      
	  
	 var Panel = new Ext.Panel({
	   width : 800, 
	   height:270,
	   items : [{
	   xtype:'panel',
	   layout:"column",
	   title:'全网topN协议',
	   items:[protocolPanel,chartProtocol]
	   }]
   })

   var Panel1 = new Ext.Panel({
	   width : 800, 
	   //collapsible: true,
	   //collapsed: true,
	   height:270,
	   items : [{
	   xtype:'panel',
	   layout:"column",
	   title:'全网topN源端口',
	   items:[srcportPanel,chartSrcport]
	   }]
   })
    
   var Panel2 = new Ext.Panel({
	   width : 800, 
	   height:270,
	   items : [{
	   xtype:'panel',
	   layout:"column",
	   title:'全网topN目的端口',
	   items:[dstportPanel,chartDstport]
	   }]
   })
   
   var Panel3 = new Ext.Panel({
       width : 800,
       height:270,
       items : [{
       xtype:'panel',
       layout:'column',
       title: "Session统计",
       items:sessionPanel
       }]
   })
  var Panelall = new Ext.Panel({
	   width : 820,
	   autoScroll : true,
	   title : '全网topN排序',
	   height : document.body.clientHeight * 0.95 + 5,
	   xtype:'panel',
	   items:[ Panel,Panel1,Panel2,Panel3]
	   
	   })  
   
   
   
	Panelall.render('showGrid');
   })

})
		// categoryPanel.getSelectionModel().selectFirstRow();



</script> 

</head>
<body>
	<div id="outer">
		<div id="bodyDiv">
			<div id="menu">
			<s:include value="right_menu.jsp"></s:include>
			</div>
		
			<div id="infoDiv">
				<div id='showGrid'></div>
				<div id='showGrid1'></div>
				<div id='showGrid2'></div>
			</div>
		</div>
	</div>
</body>
</html>
