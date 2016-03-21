Ext.onReady(function(){
	    var cm = new Ext.grid.ColumnModel(json.columModle);
	    var ds = new Ext.data.JsonStore({
	    	data:json.data,
	    	fields:json.fieldsNames
	    });
	
	    var eventPanel = new Ext.grid.GridPanel({
	    cm:cm,
	    ds:ds,
		title:viewname+'设备性能图',
		autoScroll:true,
	    height:document.body.clientHeight*0.95-5,
	    width:1000
	});	
    eventPanel.render('girdshow');
});