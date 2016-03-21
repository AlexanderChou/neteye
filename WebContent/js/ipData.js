function  createHistoryPanel(){
Ext.QuickTips.init();
var dateStr =  Ext.fly("dateStr").dom.value;
var engName =  Ext.fly("engName").dom.value;
var chineseName =  Ext.fly("chineseName").dom.value;
	var IpDataTableStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'json/dayIpData.do?dateStr='+dateStr
		}),
		reader : new Ext.data.JsonReader({
			root : 'ipList',
			totalProperty:'totalCount',
			fields : ['IPv6']
		})
	});
	IpDataTableStore.load({
		params: {
			start:0,
			limit:20
		}
	});

	//IPv6访问地址记录面板
	var IpDataTablaPanel = new Ext.grid.GridPanel({
		store : IpDataTableStore,
		height: 500,
		tbar :[new Ext.Toolbar.TextItem("IpV6访问记录")],
	    columnWidth:0.3,
		//列模型
		columns: [new Ext.grid.RowNumberer(),
			{id:'IPv6',header: "iPv6地址", width: 337,sortable: true,dataIndex: 'IPv6'}
		],
		bbar:new Ext.PagingToolbar({
			pageSize:20,
			store:IpDataTableStore,
			displayInfo:true,
			displayMsg:'显示第 {0}条记录,一共 {2} 条',
			emptyMsg:'没有数据'
		})
		
	});
	
	
	var IpHourDataTableStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'json/ipHourData.do?dateStr='+dateStr
		}),
		reader : new Ext.data.JsonReader({
			root : 'ipList',
			totalProperty:'totalCount',
			fields : ['hour','hourNum']
		})
	});
	IpHourDataTableStore.load();
	var ipHourNum = new Array();
	var hour = new Array();
	IpHourDataTableStore.on('load',function(){
	
	
	
	for(i=0;i<IpHourDataTableStore.getCount();i++){
		ipHourNum[i] = IpHourDataTableStore.getAt(i).data.hourNum;
		hour[i] = IpHourDataTableStore.getAt(i).data.hour;
    }
   var ipHourPanel= new Ext.Panel({
   	tbar :[new Ext.Toolbar.TextItem("IpV6每小时访问次数")],
   		height: 500,
   		columnWidth:0.7,
      //html:'<img src="file/analysis/downLoadFile/pic/TrafficAllTopN.jpg"></img>'
      html:'<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left"><tr><td valign="top" class="text" align="left"><div id="ipHourDiv" align="left"></div></td></tr></table>'
	})
	var ipHourHtml = '';
	
	var topHtml = '';
	var endHtml = '';
	
	setTimeout(function(){
		var ipHourFlex = new FusionCharts('flex/Line.swf', "ChartRId", "100%", "450");
		getChHtml();
		ipHourFlex.setDataXML(encodeURI(topHtml+ipHourHtml+endHtml));		   
		ipHourFlex.render('ipHourDiv');
	}, 1000);
	
	function getChHtml(){
   		topHtml = '<chart  showValues="0" decimals="2" numberSuffix="个" Decimals="0" >';
		endHtml = '</chart>';
		for (i=0;i<ipHourNum.length;i++){
	          ipHourHtml += '<set label="'+hour[i]+'" toolText="'+ipHourNum[i]+'" value="'+ipHourNum[i]+'" />';
	    }
	}
//	var PanelHour = new Ext.grid.GridPanel({
///		columnWidth:0.7,
///		border: false,
///		hight:500,
//	    autoScroll : true,
//	   	title:"ipV6每小时访问次数",
//	   	items:ipHourPanel}); 
   var PanelAll = new Ext.Panel({
   		 width : document.body.clientWidth < 1024? 960:1220,
	  	 height: document.body.clientHeight - 50,
  	 	 title:chineseName,
	  	 items : [{
	  	 xtype:'panel',
	     layout:"column",
	  	 items:[IpDataTablaPanel,ipHourPanel]
	    }]
   		}); 
	   	 PanelAll.render('showDiv');
   });
    
}