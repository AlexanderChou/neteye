Ext.namespace("savi");
Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'side';
var switchName='';
var maxFilteringNum = 1;
var ifIndex = '';
var ipVersion='';
var switchbasicinfoId = '';
var interfaceStore = null;
var sm = new Ext.grid.CheckboxSelectionModel();

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
	ipVersion = query.ipVersion;
	switchbasicinfoId = query.switchbasicinfoId;
	switchName = query.switchName;
	
	// create the data store
	interfaceStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'show/listRealTimeSwitchInterface.do?ipVersion='+ipVersion + "&switchbasicinfoId=" + switchbasicinfoId
		}),
		//创建JsonReader读取interface记录
		reader : new Ext.data.JsonReader({
			root : 'interfaceList',
			totalProperty:'totalCount',
			fields : ['maxFilteringNum', 'ifIndex','ipVersion','ifValidationStatus','ifTrustStatus','ifFilteringNum']
			})
	});
	
	var interfaceColm = new Ext.grid.ColumnModel( 
		[	new Ext.grid.RowNumberer({header:'', width:20}),			
			sm,
		 	{header: "MaxFilteringNum", hidden: true, dataIndex: 'maxFilteringNum', renderer : renderMaxFilteringNum},
			{header: ifIndexText, width: 150, sortable: true, dataIndex: 'ifIndex', renderer : renderIfIndex},
			{header: ipVersionText, width: 150, sortable: false, dataIndex: 'ipVersion', renderer : renderIpVersion},
			{header: ifValidationStatusText, width:200,sortable: false,dataIndex : 'ifValidationStatus', renderer:renderIfValidationStatus},
			{header: ifTrustStatusText, width:200,sortable:false,dataIndex : 'ifTrustStatus', renderer:renderIfTrustStatus},
			{id: "ifFilteringNum", header: ifFilteringNumText, width:200,sortable: false,dataIndex : 'ifFilteringNum', renderer:renderIfFilteringNum},
			{header: modifyText, dataIndex: "IfIndex", width:100, renderer:modify},
			{header: bindingTableText, dataIndex: "IfIndex", width:100, renderer:gotoBindingTable}
		]);

	//Binding Table面板
	var interfacePanel = new Ext.grid.GridPanel({
		store : interfaceStore,
		height : document.body.clientHeight - 37,
		width : document.body.clientWidth < 1024? 960:1220,
		renderTo : "showDiv",
		title : interfaceOfSwitchText + '(' + switchName + ')',
		border: true,
		collapsible: false,
		autoExpandColumn : 'ifFilteringNum',
		sm: sm,
		//列模型
		cm: interfaceColm,
		bbar:new Ext.PagingToolbar({
			pageSize:25,
			store:interfaceStore,
			displayInfo:true,
			displayMsg: displayMsgText,
			emptyMsg: noRecordText
		}),		
		tbar : [ '->','-','->', {
			text : batchModifyText,
			handler: batchModify
			},'->','-'
		]
	});
	
	interfaceStore.load({
		params: {
			start:0,
			limit:25
		}
	});
	
	function renderMaxFilteringNum(val){
		maxFilteringNum = val;
	}
	
	function renderIfIndex(val){
		ifIndex = val;
		return val;
	}
	
	function renderIpVersion(val){
		if(val == '1') return '<img src="images/common/ipv4.jpg" width="15px" height="15px"/>';
		else if(val == '2') return '<img src="images/common/ipv6.jpg" width="15px" height="15px"/>';
		else return 'Unkown';
	}	
	
	function renderIfValidationStatus(val){
		if(val == '1') 
			return '<img src="images/common/_blank.gif" ' +
				'style= "width:15px;height:15px;background:url(images/interface/small/validation.jpg) no-repeat scroll 0 0;">' +
				'<span>&nbsp;&nbsp;&nbsp;&nbsp;Enable</span>';
		else if(val == '2')
			return '<img src="images/common/_blank.gif" ' +
				'style= "width:15px;height:15px;background:url(images/interface/small/default.jpg) no-repeat scroll 0 0;">' +
				'<span>&nbsp;&nbsp;&nbsp;&nbsp;Disable</span>';
		else return 'Unkown';
	}
	
	function renderIfTrustStatus(val){
		if(val == '1') 
			return '<img src="images/common/_blank.gif" ' +
				'style= "width:15px;height:15px;background:url(images/interface/small/no-trust.jpg) no-repeat scroll 0 0;">' +
				'<span>&nbsp;&nbsp;&nbsp;&nbsp;NO-TRUST</span>';
		else if(val == '2')
			return '<img src="images/common/_blank.gif" ' +
				'style= "width:15px;height:15px;background:url(images/interface/small/dhcp-trust.jpg) no-repeat scroll 0 0;">' +
				'<span>&nbsp;&nbsp;&nbsp;&nbsp;DHCP-TRUST</span>';
		else if(val == '3') 
			return '<img src="images/common/_blank.gif" ' +
				'style= "width:15px;height:15px;background:url(images/interface/small/ra-trust.jpg) no-repeat scroll 0 0;">' +
				'<span>&nbsp;&nbsp;&nbsp;&nbsp;RA-TRUST</span>';
		else if(val == '4') 
			return '<img src="images/common/_blank.gif" ' +
				'style= "width:15px;height:15px;background:url(images/interface/small/dhcp-ra-trust.jpg) no-repeat scroll 0 0;">' +
				'<span>&nbsp;&nbsp;&nbsp;&nbsp;DHCP-RA-TRUST</span>';
		else return 'Unkown';
	}
	
	function renderIfFilteringNum(val){
		var length = 150;
		var width = (val / maxFilteringNum) * length;
		
		return '<img src="images/common/_blank.gif" style= "width:' + width +
			   'px;height:12px;background:url(images/common/green_bar.jpg) no-repeat scroll 0 0;">' +
			   '<span>&nbsp;&nbsp;' + val + '</span>';
	}
	
	function gotoBindingTable(){
		return '<a href="configRealTimeInterfaceBindingTableInfo.do?ipVersion=' + ipVersion + "&switchbasicinfoId=" + switchbasicinfoId + 
				'&ifIndex=' + ifIndex + '&switchName=' + switchName +'">' + 
				'<img src="images/common/blue_arrow.jpg" width="35px" height="13px"/></a>';
	}
	
	function modify(val){
		return '<a href="javascript:void(0);" onclick="doModify();"><img src="images/common/blue_arrow.jpg" width="35px" height="13px"/></a>';
	}
	
	function batchModify(){
		var selected = sm.getSelections();
		if(selected.length < 1) return;
		
		var ifIndexes = "";
		for ( var i = 0; i < selected.length; i++) {
			record = selected[i];
			var data = selected[i].data;
			
			ifIndexes += data.ifIndex;
			
			if(i < selected.length - 1){
				ifIndexes += "|";
			}
		}
		

		var validationStore=new Ext.data.SimpleStore({
			fields:['validationStatus','value'],
			data:[['Enable','1'],['Disable','2']]
		});

		var trustStore=new Ext.data.SimpleStore({
			fields:['trustStatus','value'],
			data:[['NO-TRUST','1'],['DHCP-TRUST','2'],['RA-TRUST','3'],['DHCP-RA-TRUST','4']]
		});
		
		var validationComboBox=new Ext.form.ComboBox({
			id: 'validationStatus',
			fieldLabel : ifValidationStatusText,
			triggerAction:'all',
			store:validationStore,
			displayField:'validationStatus',
			valueField:'value',
			mode:'local',
			value: '1',
			forceSelection:true,
			editable: false,
			resizable:true,
			handleHeight:10,
			width:230,
			listeners : {
				select : function(combo2, record, index) {
			    combo2.value = record.get("value");
				}
			}
		});	

		var trustComboBox=new Ext.form.ComboBox({
			id: 'trustStatus',
			fieldLabel : ifTrustStatusText,
			triggerAction:'all',
			store: trustStore,
			displayField:'trustStatus',
			valueField:'value',
			mode:'local',
			forceSelection:true,
			editable: false,
			resizable:true,
			value: '1',
			handleHeight:10,
			width:230,
			listeners : {
				select : function(combo2, record, index) {
			    combo2.value = record.get("value");
				}
			}
		});	
		
		var updateInterface = new Ext.FormPanel( {
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
						name : 'interfacecur.ipVersion',
						value: data.ipVersion,
						hidden: true,
						hideLabel: true
					}, {
						fieldLabel : ifFilteringNumText,
						name : 'interfacecur.ifFilteringNum',
						blankText: emptyTipText,
						allowBlank:false
					}, validationComboBox,
					trustComboBox
			],
			buttons : [ {
						text : submitButtonText,
						handler : function() {
							if (updateInterface.getForm().isValid()){
								updateInterface.getForm().submit({
									url:"config/batchUpdateInterface.do?interfacecur.ifTrustStatus=" + trustComboBox.value + "&interfacecur.ifValidationStatus=" + validationComboBox.value + "&switchbasicinfoId=" + switchbasicinfoId + "&ifIndexes=" + ifIndexes,
									waitMsg: waitMsgText,
									success:function(form, action){
										updateInterfaceWin.close();
										Ext.Msg.alert(noticeTitleText, saveOrUpdateSuccessText);
										interfaceStore.reload();
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
		var updateInterfaceWin = new Ext.Window( {
			width : 440,
			height : 320,
			layout : 'fit',
			plain : true,
			frame : true,
			title : interfaceInfoText,
			bodyStyle : 'padding:5px 5px 0',
			buttonAlign : 'center',
			items : [updateInterface]
			});
		updateInterfaceWin.show();
	}

});