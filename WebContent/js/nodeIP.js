Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'side';
Ext.onReady( function() {
var chineseName =  Ext.fly("chineseName").dom.value; 
var engName =  Ext.fly("engName").dom.value; 
	var NodeDayIPNumStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'json/nodeHourIP.do?engName='+engName
		}),
		reader : new Ext.data.JsonReader({
			root : 'nodeDayIPNumList',
			fields : ['IPNum','hour']
		})
	});
	NodeDayIPNumStore.load();
	var ipHourNum = new Array();
	var hour = new Array();
	NodeDayIPNumStore.on('load',function(){
		for(var i=0;i<NodeDayIPNumStore.getCount();i++){
			ipHourNum[i] = NodeDayIPNumStore.getAt(i).data.IPNum;
			hour[i] = NodeDayIPNumStore.getAt(i).data.hour.substr(8);
	   	}
 	});
 	var IPHourPanel  = new Ext.Panel({
		  columnWidth: 0.5,
	  	  html:'<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" align="left"><tr><td valign="top" class="text" align="left"><div id="NodeIPDayDiv" align="left"></div></td></tr></table>'
	})
	var ipHourHtml ='';
	var topHtml = '';
	var endHtml = '';
	setTimeout(function(){
		getChHtml(); 
		var	ipHourFlex = new FusionCharts('flex/Column3D.swf', "ChartRId", "100%", '274');
		ipHourFlex.setDataXML(encodeURI(topHtml+ipHourHtml+endHtml));
		ipHourFlex.render('NodeIPDayDiv');
				
	}, 1000);
	function getChHtml(){
   		topHtml = '<chart  outCnvBaseFontSize="25px" caption="近24个小时IP地址个数"  baseFont="Times" baseFont="Times"   baseFontColor="#0000FF" showValues="0" decimals="2" numberSuffix="" Decimals="0" >';
		endHtml = '</chart>';
		for (i=0;i<ipHourNum.length;i++){
	        	ipHourHtml += '<set label="'+hour[i]+'" toolText="'+hour[i]+'IP个数为'+ipHourNum[i]+'" value="'+ipHourNum[i]+'" />';
	    }
	}
	
	//显示7天IP地址个数
	
	var NodeWeekIPNumStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'json/nodeDayIP.do?engName='+engName
		}),
		reader : new Ext.data.JsonReader({
			root : 'nodeWeekIPNumList',
			fields : ['IPNum','day']
		})
	});
	NodeWeekIPNumStore.load();
	var ipDayNum = new Array();
	var day = new Array();
	NodeWeekIPNumStore.on('load',function(){
		for(var i=0;i<NodeWeekIPNumStore.getCount();i++){
			ipDayNum[i] = NodeWeekIPNumStore.getAt(i).data.IPNum;
			day[i] = NodeWeekIPNumStore.getAt(i).data.day.substr(6)+"日";
	   	}
	  
 	});
 	var IPWeekPanel  = new Ext.Panel({
		  columnWidth: 0.5,
	  	  html:'<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" align="left"><tr><td valign="top" class="text" align="left"><div id="NodeIPWeekDiv" align="left"></div></td></tr></table>'
	})
	var ipWeekHtml ='';
	var topWeekHtml = '';
	var endWeekHtml = '';
	setTimeout(function(){
		getChWeekHtml(); 
		var	ipWeekFlex = new FusionCharts('flex/Column3D.swf', "ChartRId", "100%", '274');
		ipWeekFlex.setDataXML(encodeURI(topWeekHtml+ipWeekHtml+endWeekHtml));
		ipWeekFlex.render('NodeIPWeekDiv');
	}, 1000);
	function getChWeekHtml(){
   		topWeekHtml = '<chart baseFontSize="100px" caption="近15天IP地址个数"  baseFont="Times" baseFont="Times"   baseFontColor="#0000FF" showValues="0" decimals="2" numberSuffix="" Decimals="0" >';
		endWeekHtml = '</chart>';
		for (i=0;i<ipDayNum.length;i++){
	        	ipWeekHtml += '<set label="'+day[i]+'" toolText="'+day[i]+'IP个数为'+ipDayNum[i]+'" value="'+ipDayNum[i]+'" />';
	    }
	}
	var PanelAll2 = new Ext.Panel({
		 title:chineseName,
		 layout:'column',
   		 width : document.body.clientWidth < 1024? 960:1220,
	  	 items:[IPHourPanel,IPWeekPanel],
  	     renderTo:'showWeekDiv'
	});
	//显示4周的panel
	
	
	var NodeMonthIPNumStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'json/nodeWeekIP.do?engName='+engName
		}),
		reader : new Ext.data.JsonReader({
			root : 'nodeMonthIPNumList',
			fields : ['IPNum','week']
		})
	});
	NodeMonthIPNumStore.load();
	var ipWeekNum = new Array();
	var week = new Array();
	var weekNum ;
	var weekStr ;
	NodeMonthIPNumStore.on('load',function(){
		for(var i=0;i<NodeMonthIPNumStore.getCount();i++){
	  	ipWeekNum[i] = NodeMonthIPNumStore.getAt(i).data.IPNum;
	  	weekStr = NodeMonthIPNumStore.getAt(i).data.week;
		weekNum= weekStr.substring(6,8);
		weekNum = Math.ceil((weekNum)/7);
    	week[i] = weekStr.substring(4,6)+"月"+weekNum+"周";
	   	}
 	});
 	var IPMonthPanel  = new Ext.Panel({
 		  columnWidth: 0.5,
	  	  html:'<table width="100%"  border="0" cellspacing="0" cellpadding="0" align="left"><tr><td valign="top" class="text" align="left"><div id="NodeIPMonthDiv" align="left"></div></td></tr></table>'
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
   		topMonthHtml = '<chart baseFontSize="15px" caption="近12周IP地址个数" anchorRadius="5" anchorBgColor="#FFFF00" baseFont="Times" baseFont="Times" bgColor="#CCFF99" canvasBgColor="#FAEBD7" baseFontColor="#0000FF" showValues="0" decimals="2" numberSuffix="" Decimals="0" >';
		endMonthHtml = '</chart>';
		for (i=0;i<ipWeekNum.length;i++){
	        	ipMonthHtml += '<set label="'+week[i]+'" toolText="'+week[i]+'IP个数为'+ipWeekNum[i]+'" value="'+ipWeekNum[i]+'" />';
	    }
	}
	//显示年的panel
	
	
	
	var NodeYearIPNumStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'json/nodeMonthIP.do?engName='+engName
		}),
		reader : new Ext.data.JsonReader({
			root : 'nodeYearIPNumList',
			fields : ['IPNum','month']
		})
	});
	NodeYearIPNumStore.load();
	var ipMonthNum = new Array();
	var month = new Array();
	var year="";
	NodeYearIPNumStore.on('load',function(){
		for(var i=0;i<NodeYearIPNumStore.getCount();i++){
	  	ipMonthNum[i] = NodeYearIPNumStore.getAt(i).data.IPNum;
	  	year =  NodeYearIPNumStore.getAt(i).data.month.substring(2,4)+"年";
		month[i] = year+NodeYearIPNumStore.getAt(i).data.month.substr(4)+"月";
	   	}
 	});
 	var IPYearPanel  = new Ext.Panel({
	 	  columnWidth: 0.5,
	  	  html:'<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" align="left"><tr><td valign="top" class="text" align="left"><div id="NodeIPYearDiv" align="left"></div></td></tr></table>'
	})
	var ipYearHtml ='';
	var topYearHtml = '';
	var endYearHtml = '';
	setTimeout(function(){
		getChYearHtml(); 
		var	ipYearFlex = new FusionCharts('flex/Line.swf', "ChartRId", "100%", '274');
		ipYearFlex.setDataXML(encodeURI(topYearHtml+ipYearHtml+endYearHtml));
		ipYearFlex.render('NodeIPYearDiv');
				
	}, 1000);
	function getChYearHtml(){
   		topYearHtml = '<chart  baseFontSize="15px" caption="近12个月IP地址个数"  anchorRadius="5" anchorBgColor="#FFFF00" baseFont="Times" baseFont="Times" bgColor="#CCFF99" canvasBgColor="#FAEBD7" baseFontColor="#0000FF" showValues="0" decimals="2" numberSuffix="" Decimals="0" >';
		endYearHtml = '</chart>';
		for (i=0;i<ipMonthNum.length;i++){
	        	ipYearHtml += '<set label="'+month[i]+'" toolText="'+month[i]+'IP个数为'+ipMonthNum[i]+'" value="'+ipMonthNum[i]+'" />';
	    }
	}
	var PanelAll4 = new Ext.Panel({
		 layout:'column',
   		 width : document.body.clientWidth < 1024? 960:1220,
	  	 items:[IPMonthPanel,IPYearPanel],
  	     renderTo:'showYearDiv'
	});
 
});
