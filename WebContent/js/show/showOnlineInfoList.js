Ext.namespace("savi");
Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'side';

Ext.onReady( function() {
	
	var bindingTableStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'show/wlanOnlineList.do'
		}),
		reader : new Ext.data.JsonReader({
			root : 'onlineInfoList',
			totalProperty:'totalCount',
			fields : ['login','ipv4','ipv6','mac','start','apname']//,'nasPort']
			})
	});
	var bindingTableColm = new Ext.grid.ColumnModel( 
		[
			{header: '用户名', width:80, sortable: true, dataIndex: 'login'},
			{header:'ipv6地址', width: 220,sortable: true,dataIndex: 'ipv6'},
			{id:'ipv4',header: 'ipv4地址', width:80, sortable: true, dataIndex: 'ipv4'},
			{header:'mac地址', width: 120,sortable: true,dataIndex: 'mac'},
			{header: '开始时间', width: 120,sortable: true,dataIndex: 'start'},
			{header: 'apname', width: 120,sortable: true,dataIndex: 'apname'}//,
			//{header: '端口号', width: 120,sortable: true,dataIndex: 'nasPort'}
		]);

	//Binding Table面板
	var bindingTablePanel = new Ext.grid.GridPanel({
		store : bindingTableStore,
		height : document.body.clientHeight - 37,
		width : document.body.clientWidth < 1024? 960:1220,
		renderTo : "showDiv",
		title : '五元组信息',
		border: true,
		collapsible: false,
		//autoExpandColumn : 'ipv4',
		//列模型
		cm: bindingTableColm,
		bbar:new Ext.PagingToolbar({
			pageSize:50,
			store:bindingTableStore,
			displayInfo:true,
			displayMsg:displayMsgText,
			emptyMsg:noRecordText
		})
		
		
	});
	
	bindingTableStore.load({
		params: {
			start:0,
			limit:50
		}
	});
	
});