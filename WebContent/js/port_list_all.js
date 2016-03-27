
Ext.onReady(function(){  
		var reader = new Ext.data.JsonReader({
			root : "faultlistdo",
			totalProperty:'totalCount',
			fields : ['id','ip','ip6','name','status','loss','rrt','dd','uu']
		});
		var eventStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : "json/listDeviceEvent.do?deviceid="+device_id
				}),
				//创建JsonReader读取router记录
				reader : reader
		});
		eventStore.setDefaultSort('status','DESC'); 
		eventStore.load();
	    var eventPanel = new Ext.grid.GridPanel({			
		store : eventStore,	
		title:'当前设备状态',
		autoScroll:true,
	    height:document.body.clientHeight*0.95-5,
	    width:840,
		columns : [
			{id:"name", header:"设备名称", width:220,dataIndex:"name"},
//		{header:"节点id",sortable: true, dataIndex:"id",renderer:type},
		{header:"设备状态",dataIndex:"status",width:80,renderer:renderStatus},
		{header:"IP地址",sortable: true, dataIndex:"ip",width:120,renderer:rrtAndLoss},
		{header:"IPv6地址",sortable: true, dataIndex:"ip6",width:200,renderer:rrtAndLoss},
		{header:"丢包（%）", width:80,dataIndex:"loss"},
		{header:"延时（ms）", width:80,dataIndex:"rrt"}
//		{header:"ping操作",dataIndex:"ip",width:80,renderer:showUrl},
//		{header:"traceroute操作",dataIndex:"ip",width:80,renderer:traceRoute}
		]
	});	 
	
 function showUrl(value)
{
return '<a href="javascript:doPing(\''+value+'\')">ping</a>';
}
function traceRoute(value)
{
return '<a href="javascript:doTrace(\''+value+'\')">traceroute</a>'
}
function rrtAndLoss(value)
{
return "<a href='rttLossPic.do?ip="+value+"'>"+value+"</a>";
}

function doPing(value){
		var win = new Ext.Window({
	        				title:"设备ping测试",
                            width:450,
                            height:520,
                            maximizable:true,
                            contentEl : Ext.DomHelper.append(document.body, {
							    tag : 'iframe',
							    style : "border 0px none;scrollbar:true",
							    src : 'ping.do?ip='+value,
							    height : "100%",
							    width : "100%"
							   }),
                            modal:true,
                            resizable : false,
                            tbar:[{
							pressed:true,
							text:"ip地址："+value,
							handler:function(){
										
								}						
						}]
	    });
	    win.show();
}
function doTrace(value){
		var win = new Ext.Window({
	        				title:"设备TraceRouter测试",
                            width:450,
                            height:520,
                            maximizable:true,
                            contentEl : Ext.DomHelper.append(document.body, {
							    tag : 'iframe',
							    style : "border 0px none;scrollbar:true",
							    src : 'traceroute.do?ip='+value,
							    height : "100%",
							    width : "100%"
							   }),
                            modal:true,
                            resizable : false,
                            tbar:[{
							pressed:true,
							text:"ip地址："+value,
							handler:function(){
										
								}						
						}]
	    });
	    win.show();
}


function  renderStatus(value){
	if (value=='0'){
		return "<img src='/images/red.gif' />";
	}
	else if (value == '1') {
        return "<img src='/images/yellow.gif' />";
    } else if(value=='2'){
        return "<img src='/images/green.gif' />";
    }else{
    	return "<img src='/images/black.gif' />";		
    }
}
    
//	eventPanel.getSelectionModel().on('rowselect',function(sm,rowIdx,/*Ext.data.Record*/r) {
//		 window.location.href = "objEventSeq.do?eventId="+r.get("id")+"&objName="+r.get("objName");  
//	});
	function change(val){
           return '<span style="color:blue;">' + val + '</span>';
   	}
    eventPanel.render('showGrid');
});