Ext.namespace("savi");
Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'side';
var win = "1";
var sm = new Ext.grid.CheckboxSelectionModel();
var store = "";
var ids = "";
var sids="";
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
	ids = query.ids;
	sids=query.sids;
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
		name : "equipmentType",
		mapping : "equipmentType"
	}, {
		name : "ipv4address",
		mapping : "ipv4address"
	}, {
		name : "ipv6address",
		mapping : "ipv6address"
	}, {
		name : "snmpVersion",
		mapping : "snmpVersion"
	}, {
		name : "readCommunity",
		mapping : "readCommunity"
	}, {
		name : "writeCommunity",
		mapping : "writeCommunity"
	}, {
		name : "authKey",
		mapping : "authKey"
	}, {
		name : "privateKey",
		mapping : "privateKey"
	}, {
		name:"description",
		mapping:"description"
	}, {
		name : "subnetName",
		mapping : "subnetName"
	}, {
		name : "subnetId",
		mapping : "subnetId"
	}, {
		name: "status",
		mapping: "status"
	} ]);

	var reader = new Ext.data.JsonReader( {
		root : "switchList",
		totalProperty : 'totalCount'
	}, Record);
	var proxy = new Ext.data.HttpProxy( {
		url : ids == "" ? "show/listShowSwitches.do?sids="+sids : "show/batchGetSwitch.do?ids=" + ids
	});
	var colm = new Ext.grid.ColumnModel( [ 
	new Ext.grid.RowNumberer({header:'', width:20}),			
	sm,{
		id: "id",
		header : "SwitchID",
		hidden: true,
		dataIndex : "id",
		sortable : true
	}, {
		header : nameText,
		dataIndex : "name",
		sortable : true,
		width:80
	}, {
		id:"equipmentType",
		header : equipmentTypeText,
		dataIndex : "equipmentType",
		sortable : true,
		width:100
	}, {
		id:"snmpVersion",
		header : snmpVersionText,
		dataIndex : "snmpVersion",
		sortable : true,
		width:80
	}, {
		header : ipv4addressText,
		dataIndex : "ipv4address",
		sortable : true,
		width:100
	}, {
		id:"ipv6address",
		header : ipv6addressText,
		dataIndex : "ipv6address",
		sortable : true,
		width:100
	}, {
		id:"readCommunity",
		header : readCommunityText,
		dataIndex : "readCommunity",
		sortable : true,
		width:100
	}, {
		id:"writeCommunity",
		header : writeCommunityText,
		dataIndex : "writeCommunity",
		sortable : true,
		width:100
	}, {
		id:"authKey",
		header : authKeyText,
		dataIndex : "authKey",
		sortable : true,
		width:80
	}, {
		id:"privateKey",
		header : privateKeyText,
		dataIndex : "privateKey",
		sortable : true,
		width:80
	}, {
		id:"description",
		header : descriptionText,
		dataIndex : "description",
		sortable : true,
		width:100
	}, {
		header : subnetNameText,
		dataIndex : "subnetName",
		sortable : true,
		width:100
	}, {
		dataIndex : "subnetId",
		hidden: true
	}, {
		header: statusText,
		dataIndex: "status",
		sortable:false,
		renderer : statusRenderer,
		width:100
	}]);
	
	function statusRenderer(val){
		if (val == '1') {
			return '<img src="images/switch/green.jpg" width="15px" height="5px"/>';
		}
		else {
			return '<img src="images/switch/red.jpg" width="15px" height="5px"/>';
		}
	}
	
	store = new Ext.data.Store( {
		proxy : proxy,
		reader : reader
	});
	
	store.load( {
		params : {
			start : 0,
			limit : 200
		}
	});
	
	var grid = new Ext.grid.GridPanel( {
		title: switchbasicinfoText,
		store : store,
		height : document.body.clientHeight - 37,
		autoExpandColumn: 'ipv6address',
		width : document.body.clientWidth < 1024? 960:1220,
		cm : colm,
		sm : sm,
		autoScroll : true,
		renderTo : "showDiv",
		bbar : new Ext.PagingToolbar( {
			pageSize : ids == "" ? 200 : 65535,
			store : store,
			displayInfo : true,
			displayMsg : displayMsgText,
			emptyMsg : noRecordText
		}),
		tbar : [ '->','-','->', {
			text : batchAddSwitchScriptText,
			handler: batchAddSwitchScript
			},'->','-','->', {
			text : batchAddSwitchText,
			handler: batchAddSwitch
			},'->','-','->', {
			text : addOrUpdateSwitchText,
			handler: addOrUpdateSwitch
			},'->','-','->', {
			text : deleteSwitchText,
			handler: deleteSwitch
			},'->','-','->', {
			id : "show_savi_infomation_table",
			text : saviSystemTableText,
			handler: function(){
				var selected = sm.getSelections();
				var ids2;
				if(selected.length==0){
					ids2=ids;
				}else{
					var ids2 = "";
					for(var i = 0; i < selected.length; i++){
						ids2 += selected[i].data.id;
						if(i < selected.length - 1)
							ids2 += "|";
					}
				}
				document.location.href = 'configSavi.do?sids='+sids+'&ids=' + ids2;
			}},'->','-'
		]
	});
	
	//批量添加时，验证IP的VType
	Ext.apply(Ext.form.VTypes,{
		batchIpAddress: function(val, field){
			var ipv4StartValue = null;
			var ipv4EndValue = null;
			var ipv6StartValue = null;
			var ipv6EndValue = null;
		
			if(field.batchIpAddress){
				var ipv4Start = field.batchIpAddress.ipv4Start;
				var ipv4End = field.batchIpAddress.ipv4End;
				var ipv6Start = field.batchIpAddress.ipv66Start;
				var ipv6End = field.batchIpAddress.ipv6End;
				
				this.ipv4StartField = Ext.getCmp(ipv4Start);
				this.ipv4EndField = Ext.getCmp(ipv4End);
				this.ipv6StartField = Ext.getCmp(ipv4Start);
				this.ipv6EndField = Ext.getCmp(ipv6End);
				
				var ipv4StartValue = this.ipv4StartField.getValue();
				var ipv4EndValue = this.ipv4EndField.getValue();
				var ipv6StartValue = this.ipv6StartField.getValue();
				var ipv6EndValue = this.ipv6EndField.getValue();
			}
			
			if((ipv4StartValue != null && ipv4EndValue != null && ipv4StartValue != "" && ipv4EndValue != "") ||
			   (ipv6StartValue != null && ipv6EndValue != null && ipv6StartValue != "" && ipv6EndValue != "")){
				return true;
			}else{
				return false;
			}
		},
		batchIpAddressText: ipErrorText
	});
	function batchAddSwitchScript(){
		var addSwitchScript = new Ext.Panel( {
			//labelWidth : 113,
			frame : true,
			title : '',
			bodyStyle : 'padding:5px 5px 0',
			width : 600,
			//labelAlign: 'left',
			defaultType : 'textfield',
			items : [ 
					new Ext.form.TextArea({
						name:"addSwitchScript",
						//fieldLabel:'textarea',
						value:"添加格式为每台交换机的信息一行，一行的信息格式如下：\n" +
								"所属子网|ipV4地址|ipV6地址|设备名称|设备型号|SNMP版本|读共同体(用户名)|写共同体(上下文)|认证密钥|私有密钥|描述\n" +
								"如果某一项没有，则置空",
						id:'content',
						boder:false,
						width:600,
						height:400,
						allowBlank : true
					})
			],
			buttons :[{
			          text:submitButtonText,
					  handler:function(){
							var content = Ext.get("content").getValue();
							Ext.Ajax.request({
								url:"config/batchAddSwitchbasicinfoScript.do",
								timeout:"800000",
								method:"post",
								params:{addSwitchScript:content},
								success:function(response, request){
									if(Ext.decode(response.responseText).success){
										addSwitchScriptWin.close();
										store.reload();
										Ext.Msg.alert(noticeTitleText, saveSuccessText);
									}else{
										var errMsg = Ext.decode(response.responseText).errMsg;
										Ext.Msg.alert(noticeTitleText, saveFailedText + errMsg);
									}
									
								},
								failure:function(response, request){
									var errMsg = Ext.decode(response.responseText).errMsg;
									Ext.Msg.alert(noticeTitleText, saveFailedText + errMsg);
								}
							});
						}
			}]
			/*
			buttons : [ {
						text : submitButtonText,
						handler : function() {
							if (addSwitchScript.getForm().isValid()){
								addSwitchScript.getForm().submit({				
									url:"config/batchAddSwitchbasicinfoScript.do",
									waitMsg: waitMsgText,
									success:function(form, action){
										addSwitchScriptWin.close();
										store.reload();
										Ext.Msg.alert(noticeTitleText, saveSuccessText);
									},
									failure:function(from, action){
										var errMsg = Ext.decode(action.response.responseText).errMsg;
										Ext.Msg.alert(noticeTitleText, saveFailedText + errMsg);
									}
								});
							}
						}
			}]
			*/
		});
		var addSwitchScriptWin = new Ext.Window( {
			width : 600,
			height : 480,
			layout : 'fit',
			plain : true,
			frame : true,
			title : switchInfoText,
			bodyStyle : 'padding:5px 5px 0',
			buttonAlign : 'center',
			items : [addSwitchScript]
			});		
		addSwitchScriptWin.show();
	}
	
	function batchAddSwitch(){	
		var subnetStore = new Ext.data.JsonStore({
			url: 'show/listShowSubnetsForCombo.do',
			root : 'subnetListForCombo',
			totalProperty:'totalCount',
			fields:['id', 'name']
		});
		
		var subnetComboBox = new Ext.form.ComboBox({
			id: 'subnet',
			loadingText: loadingText,
			fieldLabel: subnetListText,
			store: subnetStore,
			triggerAction: 'all',
			forceSelection:true,
			displayField: 'name',
			valueField:'id',
			pageSize: 10,
			editable: false,
			mode: 'remote',
			value: pleaseSelectText,
			lazyInit: false,
			listeners : {
				select : function(combo2, record, index) {
					combo2.value = record.get("id");
				}
			}
		});
		
		var snmpStore=new Ext.data.SimpleStore({
			fields:['version','value'],
			data:[['1','1'],['2c','2c'],['3','3']]
		});
		
		var snmpComboBox=new Ext.form.ComboBox({
			id: 'switchbasicinfo.snmpVersion',
			fieldLabel : snmpVersionText,
			triggerAction:'all',
			store:snmpStore,
			displayField:'version',
			valueField:'value',
			mode:'local',
			forceSelection:true,
			editable: false,
			resizable:true,
			value:'1',
			handleHeight:10,
			width:230
		});	
		
		var addSwitch = new Ext.FormPanel( {
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
						fieldLabel : nameText,
						name : 'switchbasicinfo.name',
						blankText:emptyTipText,
						allowBlank:false
					}, {
						name:"switchbasicinfo.equipmentType",
						fieldLabel : equipmentTypeText,
						allowBlank : true
					},snmpComboBox, {
						id: 'ipv4Start',
						name:"ipv4Start",
						fieldLabel : ipv4StartText,
						batchIpAddress: {ipv4Start: 'ipv4Start', ipv4End: 'ipv4End', ipv6Start:'ipv6Start', ipv6End:'ipv6End'},
						vtype: 'batchIpAddress',
						regex: /\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}/,
						regexText: ipv4ErrorText, 
						allowBlank : true
					}, {
						id: 'ipv4End',
						name:"ipv4End",
						fieldLabel : ipv4EndText,
						batchIpAddress: {ipv4Start: 'ipv4Start', ipv4End: 'ipv4End', ipv6Start:'ipv6Start', ipv6End:'ipv6End'},
						vtype: 'batchIpAddress',
						regex: /\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}/,
						regexText: ipv4ErrorText, 
						allowBlank : true
					}, {
						id: 'ipv6Start',
						name:"ipv6Start",
						fieldLabel : ipv6StartText,
						batchIpAddress: {ipv4Start: 'ipv4Start', ipv4End: 'ipv4End', ipv6Start:'ipv6Start', ipv6End:'ipv6End'},
						vtype: 'batchIpAddress',
						//regex: /(([0-9a-fA-F]{4,4}\:){7,7}[0-9a-fA-F]{4,4})|([0-9a-fA-F]{4,4}\:\:[0-9a-fA-F]{4,4})/,
						regexText: ipv6ErrorText, 
						allowBlank : true
					},  {
						id: 'ipv6End',
						name:"ipv6End",
						fieldLabel : ipv6EndText,
						batchIpAddress: {ipv4Start: 'ipv4Start', ipv4End: 'ipv4End', ipv6Start:'ipv6Start', ipv6End:'ipv6End'},
						vtype: 'batchIpAddress',
						//regex: /(([0-9a-fA-F]{4,4}\:){7,7}[0-9a-fA-F]{4,4})|([0-9a-fA-F]{4,4}\:\:[0-9a-fA-F]{4,4})/,
						regexText: ipv6ErrorText, 
						allowBlank : true
					}, {
						name:"switchbasicinfo.readCommunity",
						fieldLabel : readCommunityText,
						allowBlank : true
					}, {
						name:"switchbasicinfo.writeCommunity",
						fieldLabel : writeCommunityText,
						allowBlank : true
					}, {
						name:"switchbasicinfo.authKey",
						fieldLabel : authKeyText,
						allowBlank : true
					}, {
						name:"switchbasicinfo.privateKey",
						fieldLabel : privateKeyText,
						allowBlank : true
					}, subnetComboBox,
					new Ext.form.TextArea({
						name:"switchbasicinfo.description",
						fieldLabel : descriptionText,
						allowBlank : true
					})
			],
			buttons : [ {
						text : submitButtonText,
						handler : function() {
							if (addSwitch.getForm().isValid()){							
								var subnetId = subnetComboBox.value;							
								if(subnetId==pleaseSelectText){
									Ext.Msg.alert(noticeTitleText,loseSubnetInfoText);
								}else{
									addSwitch.getForm().submit({				
										url:"config/batchAddSwitchbasicinfo.do?subnetId=" + subnetId,
										waitMsg: waitMsgText,
										success:function(form, action){
											addSwitchWin.close();
											store.reload();
											Ext.Msg.alert(noticeTitleText, saveSuccessText);
										},
										failure:function(from, action){
											var errMsg = Ext.decode(action.response.responseText).errMsg;
											Ext.Msg.alert(noticeTitleText, saveFailedText + errMsg);
										}
									});
								}
								
							}
						}
			}]
		});
		
		var addSwitchWin = new Ext.Window( {
			width : 440,
			height : 480,
			layout : 'fit',
			plain : true,
			frame : true,
			title : switchInfoText,
		bodyStyle : 'text-align:left',
			buttonAlign : 'center',
			items : [addSwitch]
			});
		
		addSwitchWin.show();
	}
	
	function addOrUpdateSwitch(){
		var selected = sm.getSelections();
		if(selected.length < 1) var data = null;
		else var data = selected[0].data;
		
		var subnetStore = new Ext.data.JsonStore({
			url: 'show/listShowSubnetsForCombo.do',
			root : 'subnetListForCombo',
			totalProperty:'totalCount',
			fields:['id', 'name']
		});
		
		var subnetComboBox = new Ext.form.ComboBox({
			id: 'subnet',
			loadingText: loadingText,
			fieldLabel: subnetListText,
			store: subnetStore,
			triggerAction: 'all',
			forceSelection:true,
			displayField: 'name',
			valueField:'id',
			pageSize: 10,
			editable: false,
			mode: 'remote',
			width:230,
		//	anchor:"90%",
			lazyInit: false,
			value: data == null ? pleaseSelectText : data.subnetName,
			listeners : {
				select : function(combo2, record, index) {
					combo2.value = record.get("id");
				}
			}
		});
		
		var snmpStore=new Ext.data.SimpleStore({
			fields:['version','value'],
			data:[['1','1'],['2c','2c'],['3','3']]
		});
		
		var snmpComboBox=new Ext.form.ComboBox({
			id: 'switchbasicinfo.snmpVersion',
			fieldLabel : snmpVersionText,
			triggerAction:'all',
			store:snmpStore,
			displayField:'version',
			valueField:'value',
			mode:'local',
			forceSelection:true,
			editable: false,
			resizable:true,
			value:data == null ? '1' : data.snmpVersion,
			handleHeight:10,
		//	anchor:"90%",
			width:230
		});	
		
		var addSwitch = new Ext.FormPanel( {
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
						name : 'switchbasicinfo.id',
						value: data == null ? '' : data.id,
						hidden: true,	
						hideLabel: true
					},{
						fieldLabel : nameText,
						name : 'switchbasicinfo.name',
						value: data == null ? '' : data.name,
						blankText: emptyTipText,
						allowBlank:false
						//anchor:"95%"
					}, {
						name:"switchbasicinfo.equipmentType",
						fieldLabel : equipmentTypeText,
						value: data == null ? '' : data.equipmentType,
						allowBlank : true
						//anchor:"95%"
					},snmpComboBox, {
						name:"switchbasicinfo.ipv4address",
						fieldLabel : ipv4addressText,
						value: data == null ? '' : data.ipv4address,
						allowBlank : true,
						regex: /\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}/,
						regexText: ipv4ErrorText
						//anchor:"95%"
					}, {
						name:"switchbasicinfo.ipv6address",
						fieldLabel : ipv6addressText,
						value: data == null ? '' : data.ipv6address,
					    //regex: /(([0-9a-fA-F]{4,4}\:){7,7}[0-9a-fA-F]{4,4})|([0-9a-fA-F]{4,4}\:\:[0-9a-fA-F]{4,4})/,
					    regexText: ipv6ErrorText,
						allowBlank : true
						//anchor:"95%"
					}, {
						name:"switchbasicinfo.readCommunity",
						fieldLabel : readCommunityText,
						value: data == null ? '' : data.readCommunity,
						allowBlank : true
						//anchor:"95%"
					}, {
						name:"switchbasicinfo.writeCommunity",
						fieldLabel : writeCommunityText,
						value: data == null ? '' : data.writeCommunity,
						allowBlank : true
						//anchor:"95%"
					}, {
						name:"switchbasicinfo.authKey",
						fieldLabel : authKeyText,
						value: data == null ? '' : data.authKey,
						allowBlank : true
						//anchor:"95%"
					}, {
						name:"switchbasicinfo.privateKey",
						fieldLabel : privateKeyText,
						value: data == null ? '' : data.privateKey,
						allowBlank : true
						//anchor:"95%"
					}, subnetComboBox,
					new Ext.form.TextArea({
						name:"switchbasicinfo.description",
						fieldLabel : descriptionText,
						value: data == null ? '' : data.description,
						allowBlank : true
						//anchor:"95%"
					})
			],
			buttons : [ {
						text : submitButtonText,
						handler : function() {
							if (addSwitch.getForm().isValid()){
								
								var subnetId = '';
								if(data != null && data.subnetName == subnetComboBox.value)
									subnetId = data.subnetId;
								else subnetId = subnetComboBox.value;
								if(subnetId==pleaseSelectText){
									Ext.Msg.alert(noticeTitleText, loseSubnetInfoText);
								}else{
									addSwitch.getForm().submit({
										url:"config/saveSwitchbasicinfo.do?subnetId=" + subnetId,
										success:function(form, action){
											addSwitchWin.close();
											store.reload();
											Ext.Msg.alert(noticeTitleText, saveOrUpdateSuccessText);
										},
										failure:function(from, action){
											var errMsg = Ext.decode(action.response.responseText).errMsg;
											Ext.Msg.alert(noticeTitleText, saveOrUpdateFailedText + errMsg);
										}
									});
								}
							}
						}
			}]
		});
		var addSwitchWin = new Ext.Window( {
			width : 440,
			height : 420,
			layout : 'fit',
			plain : true,
			frame : true,
			title : switchInfoText,
			bodyStyle : 'text-align:left',
			buttonAlign : 'center',
			items : [addSwitch]
			});
		addSwitchWin.show();
	}
	
	function deleteSwitch(){
		var ids = "";
		var record = "";
		var selected = sm.getSelections();
		for ( var i = 0; i < selected.length; i++) {
			record = selected[i];
			var data = selected[i].data;
			ids += data.id;
			
			if(i < selected.length - 1)
				ids += "|";
		}
		if(ids=="")return;
		
		Ext.MessageBox.confirm(noticeTitleText, deleteConfirmText,callBack);
		function callBack(id){
			if(id=='yes'){
	            var myMask = new Ext.LoadMask(Ext.getBody(), {
                    msg: waitMsgText,
                    removeMask: true 
                });
	            myMask.show();
	            
				Ext.Ajax.request( {
					url : "config/deleteSwitchbasicinfo.do",
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
								record = selected[i];
								store.remove(record);
							}
							Ext.MessageBox.alert(noticeTitleText,deleteSuccessText);
						} else {
							Ext.MessageBox.alert(noticeTitleText,deleteFailedText + errMsg);
							store.reload();
						}
					}
				});
				myMask.destroy();
			}else{
				return;
			}
		}
	}
});