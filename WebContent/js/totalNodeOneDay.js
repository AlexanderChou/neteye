Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'side';
Ext.onReady( function() {
	var dateStr =  Ext.fly("dateStr").dom.value; 
    var day = dateStr.substring(0,4)+"年"
			  +dateStr.substring(4,6)+"月"
			  +dateStr.substring(6,8)+"日"
			  
	var NodeDayIPNumStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'json/totalNodeOneDay.do?dateStr='+dateStr
		}),
		reader : new Ext.data.JsonReader({
			root : 'totalNodeOneDate',
			fields : ['IPNum','chineseName','engName']
		})
	});
	NodeDayIPNumStore.load();
	var ipDayNum = new Array();
	var engName = new Array();
	var chineseName = new Array();
	NodeDayIPNumStore.on('load',function(){
		for(var i=0;i<NodeDayIPNumStore.getCount();i++){
			engName[i] = NodeDayIPNumStore.getAt(i).data.engName;
			ipDayNum[i] = NodeDayIPNumStore.getAt(i).data.IPNum;
			chineseName[i] = NodeDayIPNumStore.getAt(i).data.chineseName;
	   	}
 	});
	var ipDayHtml ='';
	var topHtml = '';
	var endHtml = '';
 	var IPDayPanel  = new Ext.Panel({
 		  width : document.body.clientWidth < 1024? 960:1220,
	  	  html:'<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" align="left"><tr><td valign="top" class="text" align="left"><div id="NodeIPDayDiv" align="left"></div></td></tr></table>'
	})
	setTimeout(function(){
		getChHtml(); 
		var	ipDayFlex = new FusionCharts('flex/Line.swf', "ChartRId", "100%", '100%');
		ipDayFlex.setDataXML(encodeURI(topHtml+ipDayHtml+endHtml));
		ipDayFlex.render('NodeIPDayDiv');
	}, 1000);
	function getChHtml(){
   		topHtml = '<chart   anchorBgColor="#FFFF00"  baseFont="Times"  bgColor="#CCFF99" canvasBgColor="#FAEBD7" baseFontColor="#0000FF" showValues="0" decimals="2" numberSuffix="" Decimals="0" >';
		endHtml = '</chart>';
		for (i=0;i<ipDayNum.length;i++){
	        	ipDayHtml += '<set label="'+chineseName[i]+'" toolText="'+chineseName[i]+'IP地址数为'+ipDayNum[i]+'" value="'+ipDayNum[i]+'" />';
	    }
	}
	var PanelAll1 = new Ext.Panel({
		  title:"24个节点"+day+"IP地址数",
   		 width : document.body.clientWidth < 1024? 960:1220,
	  	 items:[IPDayPanel],
  	     renderTo:'showDiv'
	});
	 
});
