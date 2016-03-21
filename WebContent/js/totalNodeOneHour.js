Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'side';
Ext.onReady( function() {
	var dateStr =  Ext.fly("dateStr").dom.value; 
    var hour = dateStr.substring(0,4)+"年"
			  +dateStr.substring(4,6)+"月"
			  +dateStr.substring(6,8)+"日"
			  +dateStr.substr(8)+"小时";
	var NodeHourIPNumStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'json/totalNodeOneHour.do?dateStr='+dateStr
		}),
		reader : new Ext.data.JsonReader({
			root : 'totalNodeOneDate',
			fields : ['engName','chineseName','IPNum']
		})
	});
	NodeHourIPNumStore.load();
	var ipHourNum = new Array();
	var engName = new Array();
	var chineseName = new Array();
	
	NodeHourIPNumStore.on('load',function(){
		for(var  i=0;i<NodeHourIPNumStore.getCount();i++){
			engName[i] = NodeHourIPNumStore.getAt(i).data.engName;
			chineseName[i] = NodeHourIPNumStore.getAt(i).data.chineseName;
			ipHourNum[i] = NodeHourIPNumStore.getAt(i).data.IPNum;
	   	}
 	});
	var ipHourHtml ='';
	var topHtml = '';
	var endHtml = '';
 	var IPHourPanel  = new Ext.Panel({
 		  width : document.body.clientWidth < 1024? 960:1220,
	  	  html:'<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" align="left"><tr><td valign="top" class="text" align="left"><div id="NodeIPDayDiv" align="left"></div></td></tr></table>'
	})
	setTimeout(function(){
		getChHtml(); 
		var	ipHourFlex = new FusionCharts('flex/Line.swf', "ChartRId", "100%", '100%');
		ipHourFlex.setDataXML(encodeURI(topHtml+ipHourHtml+endHtml));
		ipHourFlex.render('NodeIPDayDiv');
	}, 1000);
	function getChHtml(){
   		topHtml = '<chart  anchorBgColor="#FFFF00"  baseFont="Times"  bgColor="#CCFF99" canvasBgColor="#FAEBD7" baseFontColor="#0000FF" showValues="0" decimals="2" numberSuffix="" Decimals="0" >';
		endHtml = '</chart>';
		for (i=0;i<ipHourNum.length;i++){
	        	ipHourHtml += '<set label="'+chineseName[i]+'" toolText="'+chineseName[i]+'IP地址数为'+ipHourNum[i]+'" value="'+ipHourNum[i]+'" />';
	    }
	}
	var PanelAll1 = new Ext.Panel({
		  title:"24个节点"+hour+"IP地址数",
   		 width : document.body.clientWidth < 1024? 960:1220,
	  	 items:[IPHourPanel],
  	     renderTo:'showDiv'
	});
	 
});
