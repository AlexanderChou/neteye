Ext.namespace("savi");
Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'side';
var win = "1";
var sm = new Ext.grid.CheckboxSelectionModel();
var store = "";

Ext.onReady( function() {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='qtip';  

	var Record = new Ext.data.Record.create( [ 
	{
		name : "id",
		mapping : "id"
	}, {
		name : "name",
		mapping : "name"
	}, {
		name : "ipv4subNet",
		mapping : "ipv4subNet"
	}, {
		name : "ipv6subNet",
		mapping : "ipv6subNet"
	}, {
		name : "switchNumber",
		mapping : "switchbasicinfos"
	},{name:'cz'},{name:'dd'},{name:'cc'} ]);

	var reader = new Ext.data.JsonReader( {
		root : "subnetListForCombo",
		totalProperty : 'totalCount'
	}, Record);

	var proxy = new Ext.data.HttpProxy( {
		url : "show/listShowSubnetsForCombo.do"
	});
	var colm = new Ext.grid.ColumnModel( [ 
	new Ext.grid.RowNumberer({header:'', width:20}),			
	sm,
     {
		header : subnetID,	
		hidden: true,
		dataIndex : "id",
		sortable : true
	}, {
		header: subnetName,
		dataIndex : "name",
		sortable : true,
		width:400
	}, {
		header: ipv4subNetText,
		dataIndex : "ipv4subNet",
		sortable : true,
		width:300
	}, {
		id: "ipv6subNet",
		header: ipv6subNetText,
		dataIndex : "ipv6subNet",
		sortable : true,
		width:200
	}
	/*
	{
		header : switchNumber,
		dataIndex : "switchNumber",
		sortable : false,
		width:150,
		renderer: renderSwitchNumber
	}*/]);
	/*
	function renderSwitchNumber(val){
		var switchNum = 0;
		for(var i = 0; i < val.length; i++ ){
			if(val[i].isDelete==0){
				switchNum += val[i].switchcurs.length;
			}
		}
		return switchNum;
	}
	*/
	store = new Ext.data.Store( {
		proxy : proxy,
		reader : reader
	});
	
	store.load( {
		params : {
			start : 0,
			limit : 22
		}
	});
	
	var grid = new Ext.grid.GridPanel( {
		title: title,
		store : store,
		height : document.body.clientHeight - 37,
		autoExpandColumn: 'ipv6subNet',
		width : document.body.clientWidth < 1024? 960:1220,
		cm : colm,
		sm : sm,
		autoScroll : true,
		renderTo : "showDiv",
		bbar : new Ext.PagingToolbar( {
			pageSize : 22,
			store : store,
			displayInfo : true,
			displayMsg : displayMsgText,
			emptyMsg : noRecordText
		}),
		tbar : [ '->','-','->', {
			text : addOrUpdateSubnetText,
			handler: addOrUpdateSubnet
			},'->','-','->', {
			text : deleteSubnetText,
			handler: deleteSubnet
			},'->','-','->', {
			text : barText,
			handler: function(){
				var sids = "";
				var selected = sm.getSelections();
				for ( var i = 0; i < selected.length; i++) {
					var data = selected[i].data;
					sids += data.id;
					
					if(i < selected.length - 1)
						sids += "|";
				}
				document.location.href = 'configSwitch.do?sids='+sids+'&ids=';
			}},'->','-','->', {
			text : saviSystemTableText,
			handler: function(){
				var sids = "";
				var selected = sm.getSelections();
				for ( var i = 0; i < selected.length; i++) {
					var data = selected[i].data;
					sids += data.id;
					
					if(i < selected.length - 1)
						sids += "|";
				}
				document.location.href = 'configSavi.do?sids='+sids+'&ids=';
			}},'->','-'
		]
	});
	
	function rowdblclickFn(grid, rowIndex, e){//双击事件
		 var row = grid.store.getById(grid.store.data.items[rowIndex].id);
		 //document.location.href = 'showSubnetDetails.do?subnetId=' + row.get("id");
		 window.open('showSubnetDetails.do?subnetId=' + row.get("id"),"_blank");
	}
	
	function addOrUpdateSubnet(){
		var selected = sm.getSelections();
		if(selected.length < 1) var data = null;
		else var data = selected[0].data;
		
		
		var addSubnet = new Ext.FormPanel( {
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
						fieldLabel : 'id',
						name : 'subnet.id',
						value: data == null ? '' : data.id,
						hidden: true,	
						hideLabel: true
					},{
						fieldLabel : subnetName,
						name : 'subnet.name',
						value: data == null ? '' : data.name,
						blankText: emptyTipText,
						allowBlank:false
					},{
						fieldLabel : ipv4subNetText,
						name : 'subnet.ipv4subNet',
						value: data == null ? '' : data.ipv4subNet,
						allowBlank:true
					}, {
						fieldLabel : ipv6subNetText,
						name : 'subnet.ipv6subNet',
						value: data == null ? '' : data.ipv6subNet,
						allowBlank:true
					}
			],
			buttons : [ {
						text : submitButtonText,
						handler : function() {
							if (addSubnet.getForm().isValid()){
								addSubnet.getForm().submit({
									url:"config/saveSubnet.do",
									waitMsg: waitMsgText,
									success:function(form, action){
										addSubnetWin.close();
										store.reload();
									//	Ext.Msg.alert(noticeTitleText, saveOrUpdateSuccessText);
										Ext.Msg.show({   
                                title:noticeTitleText,   
                                msg:saveOrUpdateSuccessText,   
                                icon:Ext.MessageBox.INFO,   
                                buttons:Ext.MessageBox.OK   
                                                            }); 
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
		var addSubnetWin = new Ext.Window( {
			width : 440,
			height : 320,
			layout : 'fit',
			plain : true,
			frame : true,
			title : subnetInfoText,
			bodyStyle : 'padding:5px 5px 0',
			buttonAlign : 'center',
			items : [addSubnet]
			});
		addSubnetWin.show();
	}
	
	function deleteSubnet(){
		var ids = "";
		var selected = sm.getSelections();
		for ( var i = 0; i < selected.length; i++) {
			var data = selected[i].data;
			ids += data.id;
			
			if(i < selected.length - 1)
				ids += "|";
		}
		if(ids=="")return;
		Ext.MessageBox.confirm(noticeTitleText,deleteConfirmText,callBack);
		function callBack(id){
			if(id=='yes'){
	            var myMask = new Ext.LoadMask(Ext.getBody(), {
                    msg: waitMsgText,
                    removeMask: true //完成后移除
                });
	            //myMask.show();				
				Ext.Ajax.request( {
					url : "config/deleteSubnet.do",
					disableCaching : true,
					params : {
						ids : ids
					},
					success : function(response, request) {
						myMask.hide();
						var success=Ext.decode(response.responseText).success;
						var errMsg=Ext.decode(response.responseText).errMsg;
						if (success == true) {
							for ( var i = 0; i < selected.length; i++) {
								store.remove(selected[i]);
							}
							Ext.Msg.show({   
                                title:noticeTitleText,   
                                msg:deleteSuccessText,   
                                icon:Ext.MessageBox.INFO,   
                                buttons:Ext.MessageBox.OK   
                                                            }); 
					//		Ext.MessageBox.alert(noticeTitleText,deleteSuccessText);
						} else {
							store.reload();
							Ext.MessageBox.alert(noticeTitleText,deleteFailedText + errMsg);
						}
					}
				});
			}else{
				return;
			}
			myMask.destroy();
		}
	}
	
	grid.addListener('rowdblclick', rowdblclickFn);//添加双击事件
});