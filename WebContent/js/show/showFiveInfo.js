Ext.namespace("savi");
Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'side';



Ext.onReady( function() {
	
	var bindingTableStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'show/listFiveInfo.do'
		}),
		reader : new Ext.data.JsonReader({
			root : 'fiveInfoList',
			totalProperty:'totalCount',
			fields : ['userName','ipAddress','macAddress','ifIndex','startTimeString','ipv4address']
			})
	});
	
	var bindingTableColm = new Ext.grid.ColumnModel( 
		[
		 	{header: "用户名", width:120, sortable: true, dataIndex: 'userName'},
			{id:"ipAddress", header: bIPAddressText, width: 240,sortable: true,dataIndex: 'ipAddress'},
			{header: bMACAddressText, width: 240,sortable: true,dataIndex: 'macAddress'},
			{header: bIfIndexText, width:80, sortable: true, dataIndex: 'ifIndex'},
			{header: "开始时间", width:240, sortable: true, dataIndex: 'startTimeString'},
			{header: "交换机IP", width:240, sortable: true, dataIndex: 'ipv4address'}
		]);

	//Binding Table面板
	var bindingTablePanel = new Ext.grid.GridPanel({
		store : bindingTableStore,
		height : document.body.clientHeight - 37,
		width : document.body.clientWidth < 1024? 960:1220,
		renderTo : "showDiv",
		title :'五元组信息',
		border: true,
		collapsible: false,
		//autoExpandColumn : 'ipAddress',
		//列模型
		cm: bindingTableColm,
		bbar:new Ext.PagingToolbar({
			pageSize:25,
			store:bindingTableStore,
			displayInfo:true,
			displayMsg:displayMsgText,
			emptyMsg:noRecordText
		})
	});
	bindingTableStore.load({
		params: {
			start:0,
			limit:25
		}
	});
	
});