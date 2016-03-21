

Ext.onReady(function(){  
		var reader = new Ext.data.JsonReader({
			root : "allwatchlist",
			totalProperty:'totalCount',
					fields : ['ipde','ipv6','ipname','name','ip','inf','pic1','pic2','pic3']
		});
		var eventStore = new Ext.data.GroupingStore({
				proxy : new Ext.data.HttpProxy({
					url : "json/listallwatch.do"
				}),
				//创建JsonReader读取router记录
				reader : reader,
				groupField:'ip'
		});
		eventStore.load();
		eventStore.setDefaultSort('name','DESC'); 
	    var eventPanel = new Ext.grid.GridPanel({			
		store : eventStore,	
		title:'设备流量图',
		autoScroll:true,
	    height:document.body.clientHeight*0.95-5,
	    width:1000,
	    
		columns : [
			{id:"name",header:"设备名称", width:120,sortable: true,dataIndex : 'ipname',renderer:getlineother},
			{header:"设备名称", width:80,sortable: true,dataIndex : 'name'},
			{header:"设备ip", width:80,sortable: true,dataIndex : 'ip'},
			{header:"设备ipv6", width:80,sortable: true,dataIndex : 'ipv6'},
			{header:"接口", width:120,sortable: true,dataIndex : 'inf'},
			{header:"接口描述", width:60,sortable: true,dataIndex : 'ipde'},
			{header:"比特", width:230,sortable: true,dataIndex : 'pic1',renderer:renderdobit}
		],
		 view: new Ext.grid.GroupingView({
            forceFit:true,
            startCollapsed:true,
            groupTextTpl: '{text} ({[values.rs.length]} {[values.rs.length > 1 ? "Ports" : "port"]})'
        })
	});	 
	function  getlineother(value){
    	
    	var msgdo =value.split("_");
    	var name = msgdo[0];
    	var ip =msgdo[1];
    	var indexif =msgdo[2];
    	var javs= name+"<br>"+ip+"<br>"+indexif;
    	
         return javs;   
     //   return "<img src='"+value+"' /><a href='"+value+"'/>";   
    
    };
	
    function  renderdobit(value){
    	
    	var strs = value.split("/");
    	var msgstrs = strs[6];
    	var msgdo =msgstrs.split("_");
    	var ip = msgdo[0];
    	var indexif =msgdo[1];
    	var javs= "<a href='javascript:dobit(&quot;"+ip+"&quot;,&quot;"+indexif+"&quot;)'><img src='"+value+"' /></a>";
    	
         return javs;   
     //   return "<img src='"+value+"' /><a href='"+value+"'/>";   
    
    };
    
	 function  renderdopkt(value){
    	var strs = value.split("/");
    	var msgstrs = strs[6];
    	var msgdo =msgstrs.split("_");
    	var ip = msgdo[0];
    	var indexif =msgdo[1];
    	var javs= "<a href='javascript:dopkt(&quot;"+ip+"&quot;,&quot;"+indexif+"&quot;)'><img src='"+value+"' /></a>";
         return javs;   
     //   return "<img src='"+value+"' /><a href='"+value+"'/>";   
    
    };
    
     function  renderdolen(value){
    	var strs = value.split("/");
    	var msgstrs = strs[6];
    	var msgdo =msgstrs.split("_");
    	var ip = msgdo[0];
    	var indexif =msgdo[1];
    	var javs= "<a href='javascript:dolen(&quot;"+ip+"&quot;,&quot;"+indexif+"&quot;)'><img src='"+value+"' /></a>";
         return javs;   
     //   return "<img src='"+value+"' /><a href='"+value+"'/>";   
    
    };
        function  renderStatus(value){
        return "<img src='"+value+"' />";   
    
    };
	function change(val){
           return '<span style="color:blue;">' + val + '</span>';
   	}
//   eventPanel.addListener('rowdblclick', rowClickFn);
//	function rowClickFn(grid, rowIndex,columnIndex, e) {
//		var ts = eventPanel.getStore();
//		
//		var ttps = rowIndex;
//		var ttcs = columnIndex;
//		alert(ttps);
//		alert(ttcs);
//		
//	}
   
    eventPanel.render('girdshow');
});