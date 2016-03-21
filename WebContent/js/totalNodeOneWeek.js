Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'side';
Ext.onReady( function() {
	var dateStr =  Ext.fly("dateStr").dom.value; 
   	var	year  = dateStr.substring(0,4);
    var	month = dateStr.substring(4,6);
	var	weekNum = Math.ceil(dateStr.substring(6)/7);
	var	week = year+"年"+month+"月"+weekNum+"周";
	var NodeWeekIPNumStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'json/totalNodeOneWeek.do?dateStr='+dateStr
		}),
		reader : new Ext.data.JsonReader({
			root : 'totalNodeOneDate',
			fields : ['IPNum','chineseName','engName']
		})
	});
	NodeWeekIPNumStore.load();
	var ipWeekNum = new Array();
	var engName = new Array();
	var chineseName = new Array();
	NodeWeekIPNumStore.on('load',function(){
		for(var i=0;i<NodeWeekIPNumStore.getCount();i++){
			engName[i] = NodeWeekIPNumStore.getAt(i).data.engName;
			ipWeekNum[i] = NodeWeekIPNumStore.getAt(i).data.IPNum;
			chineseName[i] = NodeWeekIPNumStore.getAt(i).data.chineseName;
	   	}
 	});
	var ipWeekHtml ='';
	var topHtml = '';
	var endHtml = '';
 	var IPWeekPanel  = new Ext.Panel({
 		  width : document.body.clientWidth < 1024? 960:1220,
	  	  html:'<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" align="left"><tr><td valign="top" class="text" align="left"><div id="NodeIPDayDiv" align="left"></div></td></tr></table>'
	})
	setTimeout(function(){
		getChHtml(); 
		var	ipWeekFlex = new FusionCharts('flex/Line.swf', "ChartRId", "100%", '100%');
		ipWeekFlex.setDataXML(encodeURI(topHtml+ipWeekHtml+endHtml));
		ipWeekFlex.render('NodeIPDayDiv');
	}, 1000);
	function getChHtml(){
   		topHtml = '<chart  anchorBgColor="#FFFF00"  baseFont="Times"  bgColor="#CCFF99" canvasBgColor="#FAEBD7" baseFontColor="#0000FF" showValues="0" decimals="2" numberSuffix="" Decimals="0" >';
		endHtml = '</chart>';
		for (i=0;i<ipWeekNum.length;i++){
	        	ipWeekHtml += '<set label="'+chineseName[i]+'" toolText="'+chineseName[i]+'IP地址数为'+ipWeekNum[i]+'" value="'+ipWeekNum[i]+'" />';
	    }
	}
	var PanelAll1 = new Ext.Panel({
		  title:"24个节点"+week+"IP地址数",
   		 width : document.body.clientWidth < 1024? 960:1220,
	  	 items:[IPWeekPanel],
  	     renderTo:'showDiv'
	});
	 
});
