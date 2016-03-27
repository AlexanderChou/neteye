<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>

<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <title>链路综合信息显示</title>
	    <link rel="stylesheet" type="text/css" href="css/ext-all.css" />
        <link rel='StyleSheet' href="css/topoInit.css" type="text/css" />

        <script type="text/javascript" src="js/ext-base.js"></script>
        <script type="text/javascript" src="js/ext-all.js"></script>
        <script type="text/javascript" src="js/ext-all-debug.js"></script>
	    <script type="text/javascript" src="js/Portal.js"></script>
	    <script type="text/javascript" src="js/PortalColumn.js"></script>
	    <script type="text/javascript" src="js/Portlet.js"></script>
 
 <script>
Ext.onReady(function(){
    var viewport = new Ext.Viewport({
        layout:'border',
        items:[{ 
            xtype:'portal',
            region:'center',
            margins:'35 5 5 0',
            items:[{id:"aaa",
                columnWidth:.33,
                style:'padding:10px 0 10px 10px'
            },{id:"bbb",
                columnWidth:.33,
                style:'padding:10px 0 10px 10px'
           
            },{id:"ccc",
                columnWidth:.33,
                style:'padding:10px'
             
            }]
        }]
    });
   var reader = new Ext.data.JsonReader({
				root : 'result',
				fields : ['IP', 'name','port']
			});
	
	var netflowStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'json/NetFlowXml.do'
						}),
				// 创建JsonReader读取router记录
				reader : reader
			});
	netflowStore.load();
	
        var IP = new Array();
        var Name = new Array();
        var port = new Array();
	
	netflowStore.on('load',function(){   
		for (j=0;j<netflowStore.getTotalCount();j++)   
		   {
		      IP[j] = netflowStore.getAt(j).data.IP;   
              Name[j] = netflowStore.getAt(j).data.name;
              port[j] = netflowStore.getAt(j).data.port;
            } 
            
                                       
  var A=netflowStore.getTotalCount();
  var netflowPanel = new Array();
  for (i=0;i<A;i++)
    {           
      netflowPanel[i]=new Ext.Panel({
	      title : 'NetFlow采集',   
          html : 
                 '名称：'+Name[i]+'<p>ip地址：'+IP[i]+'<p><a href=overview.do?IP='+IP[i]+'&port='+port[i]+'>服务信息</a>', 
		  frame : true,   
          collapsible : true,   
          draggable : true,   
          cls : 'x-portlet'  
	 });
  if (i%3==0)
   {
      Ext.getCmp('aaa').add(netflowPanel[i]);
      Ext.getCmp('aaa').doLayout();
   }else if (i%3==1)
     {
     Ext.getCmp('bbb').add(netflowPanel[i]);
     Ext.getCmp('bbb').doLayout();
   }else 
     {
     Ext.getCmp('ccc').add(netflowPanel[i]);
     Ext.getCmp('ccc').doLayout();
     }
   }
        });    
});

</script> 

 </head>
 <body>
</body>
</html> 
