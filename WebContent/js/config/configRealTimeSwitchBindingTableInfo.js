Ext.namespace("savi");
Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'side';
var switchName='';
var ipVersion='';
var switchbasicinfoId = '';

var sm = new Ext.grid.RowSelectionModel({singleSelection:true});

function QueryString()
{ 
    var name,value,i; 
    var str=location.href;
    var num=str.indexOf("?"); 
    str=str.substr(num+1);
    var arrtmp=str.split("&");
    for(i=0;i < arrtmp.length;i++)
    { 
        num=arrtmp[i].indexOf("="); 
        if(num>0)
        { 
            name=arrtmp[i].substring(0,num);
            value=arrtmp[i].substr(num+1);
            this[name]=value; 
       } 
    }
    return this;
}

Ext.onReady( function() {
	var query = QueryString();
	switchName = query.switchName;
	ipVersion = query.ipVersion;
	switchbasicinfoId = query.switchbasicinfoId;
	
	var bindingTableStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'show/listRealTimeSwitchBindingTableInfo.do?ipVersion=' + ipVersion + "&switchbasicinfoId=" + switchbasicinfoId
		}),
		reader : new Ext.data.JsonReader({
			root : 'bindingTableInfoList',
			totalProperty:'totalCount',
			fields : ['ifIndex','ipAddressType','ipAddress','macAddress','bindingType','bindingState', 'lifeTime','user', 'isInFilteringTable']
			})
	});
	
	var bindingTableColm = new Ext.grid.ColumnModel( 
		[
			{header: bIfIndexText, width:80, sortable: true, dataIndex: 'ifIndex'},
			{header: bIPAddressTypeText, width: 80, sortable: true, dataIndex: 'ipAddressType', renderer : renderIpVersion},
			{id:"ipAddress", header: bIPAddressText, width: 120,sortable: true,dataIndex: 'ipAddress'},
			{header: bMACAddressText, width: 150,sortable: true,dataIndex: 'macAddress'},
			{header: bBindingTypeText, width:120,sortable: false,dataIndex : 'bindingType', renderer:renderBindingType},
			{header: bBindingStateText, width:120,sortable: false,dataIndex : 'bindingState', renderer:renderBindingState},
			{header: bLifeTimeText, width:150,sortable: false,dataIndex : 'lifeTime',renderer:renderLifeTime},
			{header: bUserText, width:120,sortable: true,dataIndex : 'user'},
			{id:'isInFilteringTable',header: bIsInFilteringTableText, width:120,sortable: false,dataIndex : 'isInFilteringTable', renderer:renderIsInFilteringTable}
		]);

	//Binding Table面板
	var bindingTablePanel = new Ext.grid.GridPanel({
		store : bindingTableStore,
		height : document.body.clientHeight - 37,
		width : document.body.clientWidth < 1024? 960:1220,
		renderTo : "showDiv",
		title : bindingTableForSwitchText  + '(' + switchName + ')',
		border: true,
		collapsible: false,
		autoExpandColumn : 'ipAddress',
		//列模型
		cm: bindingTableColm,
		sm: sm,
		bbar:new Ext.PagingToolbar({
			pageSize:25,
			store:bindingTableStore,
			displayInfo:true,
			displayMsg: displayMsgText,
			emptyMsg: noRecordText
		}),
		tbar : [ '->','-','->', {
				text : createStaticText,
				handler: createStatic
			},'->','-','->', {
				text : deleteStaticText,
				handler: deleteStatic
			},'->','-','->', {
			text : showStaticText,
			handler: function(){
				bindingTableStore.proxy = new Ext.data.HttpProxy({
					url :'show/listRealTimeSwitchBindingTableInfoByType.do?ipVersion=' + ipVersion + "&switchbasicinfoId=" + switchbasicinfoId + '&bindingType=1'
				});
				bindingTableStore.reload();
			}},'->','-','->', {
				text : showSlaacText,
				handler: function(){
					bindingTableStore.proxy = new Ext.data.HttpProxy({
						url :'show/listRealTimeSwitchBindingTableInfoByType.do?ipVersion='+ipVersion + "&switchbasicinfoId=" + switchbasicinfoId + '&bindingType=2'
					});
					bindingTableStore.reload();
			}},'->','-','->', {
				text : showDhcpText,
				handler: function(){
				bindingTableStore.proxy = new Ext.data.HttpProxy({
					url :'show/listRealTimeSwitchBindingTableInfoByType.do?ipVersion='+ipVersion + "&switchbasicinfoId=" + switchbasicinfoId + '&bindingType=3'
				});
				bindingTableStore.reload();
			}},'->','-','->', {
				text : showAllText,
				handler: function(){
					bindingTableStore.proxy = new Ext.data.HttpProxy({
						url :'show/listRealTimeSwitchBindingTableInfo.do?ipVersion='+ipVersion + "&switchbasicinfoId=" + switchbasicinfoId
					});
					bindingTableStore.reload();
			}},'->','-'
		]
	});
	
	bindingTableStore.load({
		params: {
			start:0,
			limit:25
		}
	});
	
	function renderIpVersion(val){
		if(val == '1') return '<img src="images/common/ipv4.jpg" width="15px" height="15px"/>';
		else if(val == '2') return '<img src="images/common/ipv6.jpg" width="15px" height="15px"/>';
		else return 'Unkown';
	}	
	
	function renderBindingType(val){
		if(val == '1') 
			return '<img src="images/common/_blank.gif" ' +
				'style= "width:15px;height:15px;background:url(images/common/block_green.jpg) no-repeat scroll 0 0;">' +
				'<span>&nbsp;&nbsp;STATIC</span>';
		else if(val == '2')
			return '<img src="images/common/_blank.gif" ' +
				'style= "width:15px;height:15px;background:url(images/common/block_purple.jpg) no-repeat scroll 0 0;">' +
				'<span>&nbsp;&nbsp;SLAAC</span>';
		else if(val == '3') 
			return '<img src="images/common/_blank.gif" ' +
				'style= "width:15px;height:15px;background:url(images/common/block_yellow.jpg) no-repeat scroll 0 0;">' +
				'<span>&nbsp;&nbsp;DHCP</span>';
		else return 'Unkown';
	}
	
	function renderBindingState(val){
		var width = 0;
		if(val > 5) width = 95;
		else width = val * 20 - 5;
		
		var state = 'UNKOWN';
		if(val == 1) state = "START";
		else if(val == 2) state = "LIVE";
		else if(val == 3) state = "DETECTION";
		else if(val == 4) state = "QUERY";
		else if(val == 5) state = "BOUND";
		
		return '<img src="images/common/_blank.gif" style= "width:' + width +'px;height:10px;background:url(images/common/green_block_bar.jpg) no-repeat scroll 0 0;">' 
			  + '<span>&nbsp;&nbsp;&nbsp;&nbsp;' + state + "</span>"; 
	}
	
	function renderLifeTime(val){
		//val[0] = val[0] / 100;//还算成秒
		//val[1] = val[1] / 100;
		
		var length = 80;
		var width = (val[0] / val[1]) * length;
		
		return '<img src="images/common/_blank.gif" style= "width:' + width +
			   'px;height:12px;background:url(images/common/green_bar.jpg) no-repeat scroll 0 0;">' +
			   '<span>&nbsp;&nbsp;' + val[0] + '</span>';
	}
	
	function renderIsInFilteringTable(val){
		if(val == '1'){
			return '<img src="images/common/_blank.gif" style= "width:15px;height:15px;background:url(images/common/correct.jpg) no-repeat scroll 0 0;">';
		}else{
			return '<img src="images/common/_blank.gif" style= "width:15px;height:15px;background:url(images/common/wrong.jpg) no-repeat scroll 0 0;">';
		}
	}
	
	function createStatic(){
		var interfaceStore = new Ext.data.JsonStore({
			url: 'show/listInterfaces.do?ipVersion='+ipVersion + "&switchbasicinfoId="+switchbasicinfoId,
			root : 'interfaceList',
			totalProperty:'totalCount',
			fields:['ifIndex']
		});
		
		var interfaceComboBox = new Ext.form.ComboBox({
			id: 'ifIndex',
			loadingText: loadingText,
			fieldLabel: interfaceListText,
			store: interfaceStore,
			triggerAction: 'all',
			forceSelection:true,
			displayField: 'ifIndex',
			valueField:'ifIndex',
			pageSize: 10,
			editable: false,
			mode: 'remote',
			lazyInit: false
		});
		
		var updateBindingTable = new Ext.FormPanel( {
			labelWidth : 113,
			frame : true,
			title : '',
			bodyStyle : 'padding:5px 5px 0',
			width : 420,
			defaults : {
				width : 230
			},
			labelAlign: 'left',
			defaultType : 'textfield',
			items : [ 
					{
						fieldLabel : 'IP Version',
						name : 'ipVersion',
						value: ipVersion,
						hidden: true,	
						hideLabel: true
					},{
						fieldLabel : bIPAddressText,
						name : 'ipAddress',
						regex: /(([0-9a-fA-F]{4,4}\:){7,7}[0-9a-fA-F]{4,4})|([0-9a-fA-F]{4,4}\:\:[0-9a-fA-F]{4,4})/,
						regexText: ipv6ErrorText,
						blankText: emptyTipText,
						allowBlank:false
					}, {
						fieldLabel : bMACAddressText,
						name : 'macAddress',
						regex: /([0-9a-fA-F]{2,2}\:){4,4}[0-9a-fA-F]{2,2}/,
						regexText: MacErrorText,
						blankText: emptyTipText,
						allowBlank:false
					},{
						fieldLabel : bLifeTimeText,
						name : 'lifeTime',
						allowBlank:true
					},interfaceComboBox
			],
			buttons : [ {
						text : submitButtonText,
						handler : function() {
							if (updateBindingTable.getForm().isValid()){
								updateBindingTable.getForm().submit({
									url:"config/updateBindingTable.do?switchbasicinfoId="+switchbasicinfoId,
									waitMsg: waitMsgText,
									success:function(form, action){
										updateBindingTableWin.close();
										Ext.Msg.alert(noticeTitleText, saveOrUpdateSuccessText);
										bindingTableStore.reload();
									},
									failure:function(from, action){
										var errMsg = Ext.decode(action.response.responseText).errMsg;
										Ext.Msg.alert(noticeTitleText, saveOrUpdateFailedText + errMsg);
									}
								});
							}
						}
			}]
		});
		var updateBindingTableWin = new Ext.Window( {
			width : 440,
			height : 320,
			layout : 'fit',
			plain : true,
			frame : true,
			title : bindingTableInfoText,
			bodyStyle : 'padding:5px 5px 0',
			buttonAlign : 'center',
			items : [updateBindingTable]
			});
		updateBindingTableWin.show();
	}
	
	function deleteStatic(){
		var selected = sm.getSelections();
		if(selected.length < 1) return;
		else var data = selected[0].data;
		
		if(data.bindingType != '1'){
			Ext.MessageBox.alert(noticeTitleText,deleteErrorText);
			return;
		}
		
		Ext.MessageBox.confirm(noticeTitleText,deleteConfirmText,callBack);
		function callBack(id){
			if(id=='yes'){
	            var myMask = new Ext.LoadMask(Ext.getBody(), {
                    msg: waitMsgText,
                    removeMask: true 
                });
	            myMask.show();
				
				Ext.Ajax.request( {
					url : "config/deleteBindingTable.do?switchbasicinfoId="+switchbasicinfoId,
					disableCaching : true,
					params : {
						ipVersion:ipVersion,
						ifIndex: data.ifIndex,
						ipAddress: data.ipAddress
					},
					success : function(response, request) {
						myMask.hide();
						var success=Ext.decode(response.responseText).success;
						var errMsg=Ext.decode(response.responseText).errMsg;
						if (success == true) {
							bindingTableStore.remove(selected[0]);
							Ext.MessageBox.alert(noticeTitleText, deleteSuccessText);
						} else {
							Ext.MessageBox.alert(noticeTitleText, deleteFailedText + errMsg);
							bindingTableStore.reload();
						}
					}
				});
			}else{
				return;
			}
			myMask.destroy();
		}
	}

});