Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'side';
Ext.onReady( function() {
	var dateStr =  Ext.fly("dateStr").dom.value; 
    var month = dateStr.substring(0,4)+"年"
			  +dateStr.substring(4,6)+"月"
	var NodeMonthIPNumStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'json/totalNodeOneMonth.do?dateStr='+dateStr
		}),
		reader : new Ext.data.JsonReader({
			root : 'totalNodeOneDate',
			fields : ['IPNum','chineseName','engName']
		})
	});
	NodeMonthIPNumStore.load();
	var ipMonthNum = new Array();
	var engName = new Array();
	var chineseName = new Array();
	NodeMonthIPNumStore.on('load',function(){
		for(var i=0;i<NodeMonthIPNumStore.getCount();i++){
			engName[i] = NodeMonthIPNumStore.getAt(i).data.engName;
			ipMonthNum[i] = NodeMonthIPNumStore.getAt(i).data.IPNum;
			chineseName[i] = NodeMonthIPNumStore.getAt(i).data.chineseName;
	   	}
 	});
	var ipMonthHtml ='';
	var topHtml = '';
	var endHtml = '';
 	var IPMonthPanel  = new Ext.Panel({
 		  width : document.body.clientWidth < 1024? 960:1220,
	  	  html:'<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" align="left"><tr><td valign="top" class="text" align="left"><div id="NodeIPDayDiv" align="left"></div></td></tr></table>'
	})
	setTimeout(function(){
		getChHtml(); 
		var	ipMonthFlex = new FusionCharts('flex/Line.swf', "ChartRId", "100%", '100%');
		ipMonthFlex.setDataXML(encodeURI(topHtml+ipMonthHtml+endHtml));
		ipMonthFlex.render('NodeIPDayDiv');
	}, 1000);
	function getChHtml(){
   		topHtml = '<chart anchorBgColor="#FFFF00"  baseFont="Times"  bgColor="#CCFF99" canvasBgColor="#FAEBD7" baseFontColor="#0000FF" showValues="0" decimals="2" numberSuffix="" Decimals="0" >';
		endHtml = '</chart>';
		for (i=0;i<ipMonthNum.length;i++){
	        	ipMonthHtml += '<set label="'+chineseName[i]+'" toolText="'+chineseName[i]+'IP地址数为'+ipMonthNum[i]+'" value="'+ipMonthNum[i]+'" />';
	    }
	}
	var PanelAll1 = new Ext.Panel({
		  title:"24个节点"+month+"IP地址数",
   		 width : document.body.clientWidth < 1024? 960:1220,
	  	 items:[IPMonthPanel],
  	     renderTo:'showDiv'
	});
	 
});
