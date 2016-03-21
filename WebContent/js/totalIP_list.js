Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'side';
Ext.onReady( function() {
var IpDataTableStore = new Ext.data.GroupingStore({
		proxy : new Ext.data.HttpProxy({
			url :'json/totalNodeAllDateIP.do'
		}),
		reader : new Ext.data.JsonReader({
			root : 'nodeAllDateIPNumlist',
			fields : [{},{name: 'engName'},{name: 'chineseName'},{name: 'hourNum'},{name: 'dayNum'},{name: 'weekNum'},{name: 'monthNum'},{name: 'totalNum'},{name: 'groupName'}]
		}),
		sortInfo:{field: 'engName', direction: "ASC"},
	 	groupField:'groupName'
	});
	
	var IpDataTableColm = new Ext.grid.ColumnModel([
			{id:"engName",header:"英文名称",weith:100,hidden:true},
			{header: "节点名称", width: 100,sortable: true,dataIndex: 'chineseName',renderer:readNode},
			{header: "最近一小时IP地址数", width: 200,sortable: true,dataIndex: 'hourNum',renderer:renderHourNum},
			{header: "最近一天IP地址数", width: 200,sortable: true,dataIndex: 'dayNum',renderer:renderDayNum},
			{header: "最近一周访问IP地址数", width: 200,sortable: true,dataIndex: 'weekNum',renderer:renderWeekNum},
			{header: "最近一月IP地址数", width: 200,sortable: true,dataIndex: 'monthNum',renderer:renderMonthNum},
			{id:'totalNum',header: "截止到目前的IP地址数", width: 200,sortable: true,dataIndex: 'totalNum',renderer:renderTotalNum},
			{id:"groupName",header:"节点名称",weith:100,hidden:true,dataIndex: 'groupName'}
		]);
	
	//IPv6地址统计面板
	var IpDataTablaPanel = new Ext.grid.GridPanel({
		store : IpDataTableStore,
		autoExpandColumn : 'totalNum',
		view: new Ext.grid.GroupingView({
            forceFit:false,
            groupTextTpl: '{text} ({[values.rs.length]} {[values.rs.length > 1 ? "Items" : "Item"]})'
        }),
		height : 650,
		width : document.body.clientWidth < 1024? 960:1220,
		title : 'IP地址数记录',
		border: true,
		collapsible: false,
		renderTo :'showTotalDiv',
		//列模型
		cm: IpDataTableColm
	});
	IpDataTableStore.load();
	
    //显示近12小时
	
	var NodeHourIPNumStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'json/totalNodeHourIP.do'
		}),
		reader : new Ext.data.JsonReader({
			root : 'nodeHourIPNumList',
			fields : ['IPNum','hour']
		})
	});
	NodeHourIPNumStore.load();
	var ipHourNum = new Array();
	var hour = new Array();
	var hourStr = new Array();
	NodeHourIPNumStore.on('load',function(){
		for(var i=0;i<NodeHourIPNumStore.getCount();i++){
			ipHourNum[i] = NodeHourIPNumStore.getAt(i).data.IPNum;
			hourStr[i] =  NodeHourIPNumStore.getAt(i).data.hour;
			hour[i] = hourStr[i].substr(8)+'小时';
	   	}
 	});
 	var IPHourPanel  = new Ext.Panel({
 		  tbar:new Ext.Toolbar([
			new Ext.Toolbar.TextItem('近12小时IP地址数')]),
		  columnWidth: 0.6,
	  	  html:'<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" align="left"><tr><td valign="top" class="text" align="left"><div id="NodeIPHourDiv" align="left"></div></td></tr></table>'
	})
	var ipHourHtml ='';
	var topHourHtml = '';
	var endHourHtml = '';
	setTimeout(function(){
		getChHtml(); 
		var	ipHourFlex = new FusionCharts('flex/Line.swf', "ChartRId", "100%", '274');
		ipHourFlex.setDataXML(encodeURI(topHourHtml+ipHourHtml+endHourHtml));
		ipHourFlex.render('NodeIPHourDiv');
	}, 1000);
	function getChHtml(){
   		topHourHtml = '<chart  anchorRadius="5" anchorBgColor="#FFFF00"  baseFont="Times"  bgColor="#CCFF99" canvasBgColor="#FAEBD7" baseFontColor="#0000FF" showValues="0" decimals="2" numberSuffix="" Decimals="0" >';
		endHourHtml = '</chart>';
		for (i=0;i<ipHourNum.length;i++){
	        	ipHourHtml += '<set label="'+hour[i]+'" toolText="'+hour[i]+'IP地址数为'+ipHourNum[i]+'" value="'+ipHourNum[i]+'" link="n-totalNodeHour.do?dateStr='+hourStr[i]+'" />';
	    }
	}
	//bing
	var NodeHourPieIPNumStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'json/everyNodeTotal12Hour.do'
		}),
		reader : new Ext.data.JsonReader({
			root : 'everyNodeTotalDateIP',
			fields : ['IPNum','chineseName','engName','dateStr']
		})
	});
	NodeHourPieIPNumStore.load();
	var ipHourPieNum = new Array();
	var hourPiechineseName = new Array();
	var hourEngName = new Array();
	var dateStr = new Array();
	NodeHourPieIPNumStore.on('load',function(){
		for(var i=0;i<NodeHourPieIPNumStore.getCount();i++){
			ipHourPieNum[i] = NodeHourPieIPNumStore.getAt(i).data.IPNum;
			hourPiechineseName[i] =  NodeHourPieIPNumStore.getAt(i).data.chineseName;
			hourEngName[i] = NodeHourPieIPNumStore.getAt(i).data.engName;
			dateStr[i] = NodeHourPieIPNumStore.getAt(i).data.dateStr;
	   	}
 	});
 	var IPHourPiePanel  = new Ext.Panel({
 		  tbar:new Ext.Toolbar([
			new Ext.Toolbar.TextItem('24节点近12小时总IP地址数饼图')]),
		  columnWidth: 0.4,
	  	  html:'<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" align="left"><tr><td valign="top" class="text" align="left"><div id="NodeIPPieHourDiv" align="left"></div></td></tr></table>'
	})
	var ipHourPieHtml ='';
	var topHourPieHtml = '';
	var endHourPieHtml = '';
	setTimeout(function(){
		getHourPieChHtml(); 
		var	ipHourPieFlex = new FusionCharts('flex/Pie3D.swf', "ChartRId", "100%", '274');
		ipHourPieFlex.setDataXML(encodeURI(topHourPieHtml+ipHourPieHtml+endHourPieHtml));
		ipHourPieFlex.render('NodeIPPieHourDiv');
	}, 2000);
	function getHourPieChHtml(){
   		topHourPieHtml = '<chart  anchorRadius="5" anchorBgColor="#FFFF00"  baseFont="Times"  bgColor="#CCFF99" canvasBgColor="#FAEBD7" baseFontColor="#0000FF" showValues="0" decimals="2" numberSuffix="" Decimals="0" >';
		endHourPieHtml = '</chart>';
		for (i=0;i<ipHourPieNum.length;i++){
	        	ipHourPieHtml += '<set label="'+hourPiechineseName[i]+'" toolText="'+hourPiechineseName[i]+'IP地址数为'+ipHourPieNum[i]+'" value="'+ipHourPieNum[i]+'" />';
	    }
	}
	var PanelAll1 = new Ext.Panel({
		layout:'column',
		title:"所有节点总的IP地址数",
   		 width : document.body.clientWidth < 1024? 960:1220,
	  	 items:[IPHourPanel,IPHourPiePanel],
  	     renderTo:'showHourDiv'
	});
	
	//显示近14天IP地址IP地址数
	
	//bing
	var NodeDayPieIPNumStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'json/everyNodeTotal7Day.do'
		}),
		reader : new Ext.data.JsonReader({
			root : 'everyNodeTotalDateIP',
			fields : ['IPNum','chineseName','engName','dateStr']
		})
	});
	NodeDayPieIPNumStore.load();
	var ipDayPieNum = new Array();
	var dayPiechineseName = new Array();
	var dayEngName = new Array();
	var dateStr = new Array();
	NodeDayPieIPNumStore.on('load',function(){
		for(var i=0;i<NodeDayPieIPNumStore.getCount();i++){
			ipDayPieNum[i] = NodeDayPieIPNumStore.getAt(i).data.IPNum;
			dayPiechineseName[i] =  NodeDayPieIPNumStore.getAt(i).data.chineseName;
			dayEngName[i] = NodeDayPieIPNumStore.getAt(i).data.engName;
			dateStr[i] = NodeDayPieIPNumStore.getAt(i).data.dateStr;
	   	}
 	});
 	var IPDayPiePanel  = new Ext.Panel({
 		  tbar:new Ext.Toolbar([
			new Ext.Toolbar.TextItem('24节点近7天总IP地址数饼图')]),
		  columnWidth: 0.4,
	  	  html:'<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" align="left"><tr><td valign="top" class="text" align="left"><div id="NodeIPPieDayDiv" align="left"></div></td></tr></table>'
	})
	var ipDayPieHtml ='';
	var topDayPieHtml = '';
	var endDayPieHtml = '';
	setTimeout(function(){
		getDayPieChHtml(); 
		var	ipDayPieFlex = new FusionCharts('flex/Pie3D.swf', "ChartRId", "100%", '274');
		ipDayPieFlex.setDataXML(encodeURI(topDayPieHtml+ipDayPieHtml+endDayPieHtml));
		ipDayPieFlex.render('NodeIPPieDayDiv');
	}, 2000);
	function getDayPieChHtml(){
   		topDayPieHtml = '<chart  anchorRadius="5" anchorBgColor="#FFFF00"  baseFont="Times"  bgColor="#CCFF99" canvasBgColor="#FAEBD7" baseFontColor="#0000FF" showValues="0" decimals="2" numberSuffix="" Decimals="0" >';
		endDayPieHtml = '</chart>';
		for (i=0;i<ipDayPieNum.length;i++){
	        	ipDayPieHtml += '<set label="'+dayPiechineseName[i]+'" toolText="'+dayPiechineseName[i]+'IP地址数为'+ipDayPieNum[i]+'" value="'+ipDayPieNum[i]+'"  />';
	    }
	}
	//line
	var NodeDayIPNumStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'json/totalNodeDayIP.do'
		}),
		reader : new Ext.data.JsonReader({
			root : 'nodeDayIPNumList',
			fields : ['IPNum','day']
		})
	});
	NodeDayIPNumStore.load();
	var ipDayNum = new Array();
	var day = new Array();
	var dayStr = new Array();
	NodeDayIPNumStore.on('load',function(){
		for(var i=0;i<NodeDayIPNumStore.getCount();i++){
			ipDayNum[i] = NodeDayIPNumStore.getAt(i).data.IPNum;
			dayStr[i] = NodeDayIPNumStore.getAt(i).data.day;
			day[i] = dayStr[i].substr(6)+"日";
	   	}
	  
 	});
 	var IPDayPanel  = new Ext.Panel({
 		  tbar:new Ext.Toolbar([
			new Ext.Toolbar.TextItem('近14天IP地址数')]),
		  columnWidth: 0.6,
	  	  html:'<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" align="left"><tr><td valign="top" class="text" align="left"><div id="NodeIPDayDiv" align="left"></div></td></tr></table>'
	})
	var ipDayHtml ='';
	var topDayHtml = '';
	var endDayHtml = '';
	setTimeout(function(){
		getChDayHtml(); 
		var	ipDayFlex = new FusionCharts('flex/Line.swf', "ChartRId", "100%", '274');
		ipDayFlex.setDataXML(encodeURI(topDayHtml+ipDayHtml+endDayHtml));
		ipDayFlex.render('NodeIPDayDiv');
	}, 2000);
	function getChDayHtml(){
   		topDayHtml = '<chart anchorRadius="5" anchorBgColor="#FFFF00" baseFont="Times" baseFont="Times" bgColor="#CCFF99" canvasBgColor="#FAEBD7" baseFontColor="#0000FF" showValues="0" decimals="2" numberSuffix="" Decimals="0" >';
		endDayHtml = '</chart>';
		for (i=0;i<ipDayNum.length;i++){
	        	ipDayHtml += '<set label="'+day[i]+'" toolText="'+day[i]+'IP地址数为'+ipDayNum[i]+'" value="'+ipDayNum[i]+'" link="n-totalNodeDay.do?dateStr='+dayStr[i]+'" />';
	    }
	}
	var PanelAll2 = new Ext.Panel({
		layout:'column',
   		 width : document.body.clientWidth < 1024? 960:1220,
	  	 items:[IPDayPanel,IPDayPiePanel],
  	     renderTo:'showDayDiv'
	});
	//显示4周的panel
	
	
	var NodeWeekPieIPNumStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'json/everyNodeTotal4Week.do'
		}),
		reader : new Ext.data.JsonReader({
			root : 'everyNodeTotalDateIP',
			fields : ['IPNum','chineseName','engName','dateStr']
		})
	});
	NodeWeekPieIPNumStore.load();
	var ipWeekPieNum = new Array();
	var weekPiechineseName = new Array();
	var weekEngName = new Array();
	var dateStr = new Array();
	NodeWeekPieIPNumStore.on('load',function(){
		for(var i=0;i<NodeWeekPieIPNumStore.getCount();i++){
			ipWeekPieNum[i] = NodeWeekPieIPNumStore.getAt(i).data.IPNum;
			weekPiechineseName[i] =  NodeWeekPieIPNumStore.getAt(i).data.chineseName;
			weekEngName[i] = NodeWeekPieIPNumStore.getAt(i).data.engName;
			dateStr[i] = NodeWeekPieIPNumStore.getAt(i).data.dateStr;
	   	}
 	});
 	var IPWeekPiePanel  = new Ext.Panel({
 		  tbar:new Ext.Toolbar([
			new Ext.Toolbar.TextItem('24节点近4周总IP地址数饼图')]),
		  columnWidth: 0.4,
	  	  html:'<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" align="left"><tr><td valign="top" class="text" align="left"><div id="NodeIPPieWeekDiv" align="left"></div></td></tr></table>'
	})
	var ipWeekPieHtml ='';
	var topWeekPieHtml = '';
	var endWeekPieHtml = '';
	setTimeout(function(){
		getWeekPieChHtml(); 
		var	ipWeekPieFlex = new FusionCharts('flex/Pie3D.swf', "ChartRId", "100%", '274');
		ipWeekPieFlex.setDataXML(encodeURI(topWeekPieHtml+ipWeekPieHtml+endWeekPieHtml));
		ipWeekPieFlex.render('NodeIPPieWeekDiv');
	}, 1000);
	function getWeekPieChHtml(){
   		topWeekPieHtml = '<chart  anchorRadius="5" anchorBgColor="#FFFF00"  baseFont="Times"  bgColor="#CCFF99" canvasBgColor="#FAEBD7" baseFontColor="#0000FF" showValues="0" decimals="2" numberSuffix="" Decimals="0" >';
		endWeekPieHtml = '</chart>';
		for (i=0;i<ipWeekPieNum.length;i++){
	        	ipWeekPieHtml += '<set label="'+weekPiechineseName[i]+'" toolText="'+weekPiechineseName[i]+'IP地址数为'+ipWeekPieNum[i]+'" value="'+ipWeekPieNum[i]+'" />';
	    }
	}
	
	//24节点近12周line图
	var NodeWeekIPNumStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'json/totalNodeWeekIP.do'
		}),
		reader : new Ext.data.JsonReader({
			root : 'nodeWeekIPNumList',
			fields : ['IPNum','week']
		})
	});
	NodeWeekIPNumStore.load();
	var ipWeekNum = new Array();
	var week = new Array();
	var WeekStr =new Array();
	var weekStr = new Array();
	var month2;
	var weekNum ;
	NodeWeekIPNumStore.on('load',function(){
		for(var i=0;i<NodeWeekIPNumStore.getCount();i++){
	  	ipWeekNum[i] = NodeWeekIPNumStore.getAt(i).data.IPNum;
		weekStr[i] = NodeWeekIPNumStore.getAt(i).data.week;
		month2 = weekStr[i].substring(4,6)
		weekNum = Math.ceil(weekStr[i].substring(6)/7);
		week[i] = month2+"月"+weekNum+"周";
	   	}
 	});
 	var IPWeekPanel  = new Ext.Panel({
 		  tbar:new Ext.Toolbar([
			new Ext.Toolbar.TextItem('近12周IP地址数')]),
		  columnWidth: 0.6,
	  	  html:'<table width="100%"  border="0" cellspacing="0" cellpadding="0" align="left"><tr><td valign="top" class="text" align="left"><div id="NodeIPWeekDiv" align="left"></div></td></tr></table>'
	})
	var ipWeekHtml ='';
	var topWeekHtml = '';
	var endWeekHtml = '';
	setTimeout(function(){
		getChWeekHtml(); 
		var	ipWeekFlex = new FusionCharts('flex/Line.swf', "ChartRId", "100%", '274');
		ipWeekFlex.setDataXML(encodeURI(topWeekHtml+ipWeekHtml+endWeekHtml));
		ipWeekFlex.render('NodeIPWeekDiv');
				
	}, 1000);
	function getChWeekHtml(){
   		topWeekHtml = '<chart anchorRadius="5" anchorBgColor="#FFFF00" outCnvBaseFontSize="9" baseFont="Times" bgColor="#CCFF99" canvasBgColor="#FAEBD7" baseFontColor="#0000FF" baseFont="Times" bgColor="#CCFF99" canvasBgColor="#FAEBD7" baseFontColor="#0000FF" baseFont="Times" bgColor="#CCFF99" canvasBgColor="#FAEBD7" baseFontColor="#0000FF" baseFont="Times"  showValues="0" decimals="2" numberSuffix="" Decimals="0" >';
		endWeekHtml = '</chart>';
		for (i=0;i<ipWeekNum.length;i++){
	        	ipWeekHtml += '<set label="'+week[i]+'" toolText="'+week[i]+'IP地址数为'+ipWeekNum[i]+'" value="'+ipWeekNum[i]+'" link="n-totalNodeWeek.do?dateStr='+weekStr[i]+'" />';
	    }
	}
	var PanelAll3 = new Ext.Panel({
		layout:'column',
   		 width : document.body.clientWidth < 1024? 960:1220,
	  	 items:[IPWeekPanel,IPWeekPiePanel],
  	     renderTo:'showWeekDiv'
	});
	//显示近12IP地址月的panel
	
	//bing
	var NodeMonthPieIPNumStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'json/everyNodeTotal12Month.do'
		}),
		reader : new Ext.data.JsonReader({
			root : 'everyNodeTotalDateIP',
			fields : ['IPNum','chineseName','engName','dateStr']
		})
	});
	NodeMonthPieIPNumStore.load();
	var ipMonthPieNum = new Array();
	var monthPiechineseName = new Array();
	var monthEngName = new Array();
	var dateStr = new Array();
	NodeMonthPieIPNumStore.on('load',function(){
		for(var i=0;i<NodeMonthPieIPNumStore.getCount();i++){
			ipMonthPieNum[i] = NodeMonthPieIPNumStore.getAt(i).data.IPNum;
			monthPiechineseName[i] =  NodeMonthPieIPNumStore.getAt(i).data.chineseName;
			monthEngName[i] = NodeMonthPieIPNumStore.getAt(i).data.engName;
			dateStr[i] = NodeMonthPieIPNumStore.getAt(i).data.dateStr;
	   	}
 	});
 	var IPMonthPiePanel  = new Ext.Panel({
 		  tbar:new Ext.Toolbar([
			new Ext.Toolbar.TextItem('24个节点近12个月总IP地址数饼图')]),
		  columnWidth: 0.4,
	  	  html:'<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" align="left"><tr><td valign="top" class="text" align="left"><div id="NodeIPPieMonthDiv" align="left"></div></td></tr></table>'
	})
	var ipMonthPieHtml ='';
	var topMonthPieHtml = '';
	var endMonthPieHtml = '';
	setTimeout(function(){
		getMonthPieChHtml(); 
		var	ipMonthPieFlex = new FusionCharts('flex/Pie3D.swf', "ChartRId", "100%", '274');
		ipMonthPieFlex.setDataXML(encodeURI(topMonthPieHtml+ipMonthPieHtml+endMonthPieHtml));
		ipMonthPieFlex.render('NodeIPPieMonthDiv');
	}, 1000);
	function getMonthPieChHtml(){
   		topMonthPieHtml = '<chart  anchorRadius="5" anchorBgColor="#FFFF00"  baseFont="Times"  bgColor="#CCFF99" canvasBgColor="#FAEBD7" baseFontColor="#0000FF" showValues="0" decimals="2" numberSuffix="" Decimals="0" >';
		endMonthPieHtml = '</chart>';
		for (i=0;i<ipMonthPieNum.length;i++){
	        	ipMonthPieHtml += '<set label="'+monthPiechineseName[i]+'" toolText="'+monthPiechineseName[i]+'IP地址数为'+ipMonthPieNum[i]+'" value="'+ipMonthPieNum[i]+'"  />';
	    }
	}
	//显示所有节点近12个月折线图
	var NodeMonthIPNumStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'json/totalNodeMonthIP.do'
		}),
		reader : new Ext.data.JsonReader({
			root : 'nodeMonthIPNumList',
			fields : ['IPNum','month']
		})
	});
	NodeMonthIPNumStore.load();
	var ipMonthNum = new Array();
	var monthStr = new Array(); 
	var month = new Array();
	var year="";
	NodeMonthIPNumStore.on('load',function(){
		for(var i=0;i<NodeMonthIPNumStore.getCount();i++){
	  	ipMonthNum[i] = NodeMonthIPNumStore.getAt(i).data.IPNum;
	  	monthStr[i] = NodeMonthIPNumStore.getAt(i).data.month;
	  	year = monthStr[i].substring(2,4)+"年";
		month[i] = year+monthStr[i].substr(4)+"月";
	   	}
 	});
 	var IPMonthPanel  = new Ext.Panel({
		  tbar:new Ext.Toolbar([
			new Ext.Toolbar.TextItem('近12月IP地址数')]),
		  columnWidth: 0.6,
	  	  html:'<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" align="left"><tr><td valign="top" class="text" align="left"><div id="NodeIPMonthDiv" align="left"></div></td></tr></table>'
	})
	var ipMonthHtml ='';
	var topMonthHtml = '';
	var endMonthHtml = '';
	setTimeout(function(){
		getChMonthHtml(); 
		var	ipMonthFlex = new FusionCharts('flex/Line.swf', "ChartRId", "100%", '274');
		ipMonthFlex.setDataXML(encodeURI(topMonthHtml+ipMonthHtml+endMonthHtml));
		ipMonthFlex.render('NodeIPMonthDiv');
				
	}, 1000);
	function getChMonthHtml(){
   		topMonthHtml = '<chart anchorRadius="5" anchorBgColor="#FFFF00" outCnvBaseFontSize="9" baseFont="Times" bgColor="#CCFF99" canvasBgColor="#FAEBD7" baseFontColor="#0000FF" showValues="0" decimals="2" numberSuffix="" Decimals="0" >';
		endMonthHtml = '</chart>';
		for (i=0;i<ipMonthNum.length;i++){
	        	ipMonthHtml += '<set label="'+month[i]+'" toolText="'+month[i]+'IP地址数为'+ipMonthNum[i]+'" value="'+ipMonthNum[i]+'" link="n-totalNodeMonth.do?dateStr='+monthStr[i]+'" />';
	    }
	}
	var PanelAll4 = new Ext.Panel({
		layout:'column',
   		 width : document.body.clientWidth < 1024? 960:1220,
	  	 items:[IPMonthPanel,IPMonthPiePanel],
  	     renderTo:'showMonthDiv'
	});
 

});
