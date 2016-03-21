Ext.namespace("savi");
Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'side';
var store = "";
var switchcurId=0;
var switchName="";
var ipVersion='';
var switchbasicinfoId = '';
var maxBindingNum = 0;
var sm = new Ext.grid.CheckboxSelectionModel();

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
	sids= query.sids;
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='qtip';

	var Record = new Ext.data.Record.create( [
	 {
		name : "id",
		mapping : "id"
	},{
		name : "switchName",
		mapping : "switchName"
	},{
		name : "switchbasicinfoId",
		mapping : "switchId"
	}, {
		name : "ipVersion",
		mapping : "ipVersion"
	}, {
		name : "systemMode",
		mapping : "systemMode"
	}, {
		name : "maxDadDelay",
		mapping : "maxDadDelay"
	}, {
		name : "maxDadPrepareDelay",
		mapping : "maxDadPrepareDelay"
	}, {name:'cz'},{name:'dd'},{name:'cc'}]);

	var reader = new Ext.data.JsonReader( {
		root : "saviList",
		totalProperty : 'totalCount'
	}, Record);

	var proxy = new Ext.data.HttpProxy( {
		url : ids=="" ? "show/listSaviTable.do?sids="+sids: "show/batchGetSavi.do?ids=" + ids
	});
	
	var colm = new Ext.grid.ColumnModel( [
	new Ext.grid.RowNumberer({header:'', width:20}),			
	sm,{
		id:"SwitchcurId",
		header : "id",
		dataIndex : "id",
		hidden: true,
		renderer: renderId
	},	{
		id:"switchbasicinfoId",
		header : "switchbasicinfoId",
		dataIndex : "switchbasicinfoId",
		renderer: renderSwitchId,
		hidden: true
	}, {
		id : "switchName",
		header : switchNameText,
		dataIndex : "switchName",
		width:150,
		sortable : true,
		renderer: renderSwitchName
	}, {
		header : ipVersionText,
		dataIndex : "ipVersion",
		width:150,
		sortable : false,
		renderer: renderIpVersion
	}, {
		header : systemModeText,
		dataIndex : "systemMode",
		width:150,
		sortable : false,
		renderer: renderSystemMode
	}, {
		header : maxDadDelayText,
		dataIndex : "maxDadDelay",
		width:200,
		sortable : false,
		renderer: renderDelay
	}, {
		header : maxDadPrepareDelayText,
		dataIndex : "maxDadPrepareDelay",
		width:200,
		sortable : false,
		renderer: renderDelay
	}, {
		dataIndex: "id",
		header : modifyText,
		width:80,
		renderer: modify
	}, {
		dataIndex: "id",
		header : interfaceText,
		width:80,
		renderer: gotoInterface
	}, {
		dataIndex: "id",
		header : bindingTableText,
		width:80,
		renderer: gotoBindingTable
	}, {
		dataIndex: "id",
		header : viewAllText,
		width:80,
		renderer: gotoViewAll
	}]);

	store = new Ext.data.Store( {
		proxy : proxy,
		reader : reader
	});
	
	function renderId(val){
		switchcurId = val;
	}
	
	function renderSwitchId(val){
		switchbasicinfoId=val;
		return val;
	}
	
	function renderSwitchName(val){
		switchName = val;
		return val;
	}
	
	function renderIpVersion(val){
		ipVersion = val;
		if(val == '1') return '<img src="images/common/ipv4.jpg" width="15px" height="15px"/>';
		else if(val == '2') return '<img src="images/common/ipv6.jpg" width="15px" height="15px"/>';
		else return 'Unkown';
	}	
	
	function renderDelay(val){
		val = val /100;
		var width = 0;
		if(val > 5) width = 95;
		else width = val * 20 - 5;
		return '<img src="images/common/_blank.gif" style= "width:' + width +'px;height:10px;background:url(images/common/green_block_bar.jpg) no-repeat scroll 0 0;">' 
				+ '<span>&nbsp;&nbsp;&nbsp;&nbsp;' + val + "</span>"; 
	}
	
	function renderSystemMode(val){
		if(val == '1') 	return '<img src="images/common/_blank.gif" style= "width:25px;height:10px;background:url(images/switch/small/disable_switch.jpg) no-repeat scroll 0 0;"><span>&nbsp;&nbsp;&nbsp;&nbsp;DISABLE</span>'; 
		else if(val == '2') return '<img src="images/common/_blank.gif" style= "width:25px;height:10px;background:url(images/switch/small/default_switch.jpg) no-repeat scroll 0 0;"><span>&nbsp;&nbsp;&nbsp;&nbsp;DEFAULT</span>'; 
		else if(val == '3') return '<img src="images/common/_blank.gif" style= "width:25px;height:10px;background:url(images/switch/small/dhcp_switch.jpg) no-repeat scroll 0 0;"><span>&nbsp;&nbsp;&nbsp;&nbsp;DHCP</span>'; 
		else if(val == '4') return '<img src="images/common/_blank.gif" style= "width:25px;height:10px;background:url(images/switch/small/slaac_switch.jpg) no-repeat scroll 0 0;"><span>&nbsp;&nbsp;&nbsp;&nbsp;SLAAC</span>'; 
		else if(val == '5') return '<img src="images/common/_blank.gif" style= "width:25px;height:10px;background:url(images/switch/small/mix_switch.jpg) no-repeat scroll 0 0;"><span>&nbsp;&nbsp;&nbsp;&nbsp;MIX</span>'; 
		else return '<span>UNKOWN</span>'; 
	}
	
	function modify(val){
		return '<a href="javascript:void(0);" onclick="doModify();"><img src="images/common/blue_arrow.jpg" width="35px" height="13px"/></a>';
	}
	
	function gotoInterface(val){
		return '<a href="configRealTimeSwitchInterface.do?ipVersion='+ipVersion + "&switchbasicinfoId=" + switchbasicinfoId + '&switchName=' + switchName +'"><img src="images/common/green_arrow.jpg" width="35px" height="13px"/></a>';
	}
	
	function gotoBindingTable(val){
		return '<a href="configRealTimeSwitchBindingTableInfo.do?ipVersion='+ipVersion + "&switchbasicinfoId=" + switchbasicinfoId + '&switchName=' + switchName +'"><img src="images/common/green_arrow.jpg" width="35px" height="13px"/></a>';
	}
	
	function gotoViewAll(val){
		return '<a href="showSwitchDetails.do?ipVersion='+ipVersion + "&switchbasicinfoId=" + switchbasicinfoId + '"><img src="images/common/blue_arrow.jpg" width="35px" height="13px"/></a>';
	}
	
	store.load( { 
		params : {
			start : 0,
			limit : 200
		}
	});
	
	var grid = new Ext.grid.GridPanel( {
		title: saviSystemTableText,
		store : store,
		height : document.body.clientHeight - 37,
		width : document.body.clientWidth < 1024? 960:1220,
		cm : colm,
		sm: sm,
		autoExpandColumn: 'switchName',
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
				text : batchModifyText,
				handler: batchModify
			},'->','-','->', {
				id : "switch_basic_infomation",
				text : switchbasicinfoText,
				handler: function(){
					var selected = sm.getSelections();
					var ids2;
					if(selected.length==0){
						ids2=ids;
					}else{
						var ids2 = "";
						for(var i = 0; i < selected.length; i++){
							ids2 += selected[i].data.switchbasicinfoId;
							if(i < selected.length - 1)
								ids2 += "|";
						}
					}
					document.location.href = 'configSwitch.do?sids='+sids+'&ids=' + ids2;
			}},'->','-'
		]
	});
	
	function batchModify(){
		var selected = sm.getSelections();
		if(selected.length < 1) return;
		
		var ids = "";
		var ipVersions = "";
		
		for ( var i = 0; i < selected.length; i++) {
			record = selected[i];
			var data = selected[i].data;
			
			ids += data.switchbasicinfoId;
			ipVersions += data.ipVersion;
			
			if(i < selected.length - 1){
				ids += "|";
				ipVersions += "|";
			}
		}

		var saviStore=new Ext.data.SimpleStore({
			fields:['systemMode','value'],
			data:[['DISABLE','1'],['DEFAULT','2'],['DHCP','3'],['SLAAC','4'],['DHCP-SLAAC-MIX','5']]
		});
		
		var saviComboBox=new Ext.form.ComboBox({
			id: 'systemMode',
			fieldLabel : systemModeText,
			triggerAction:'all',
			store:saviStore,
			displayField:'systemMode',
			valueField:'value',
			editable: false,
			mode:'local',
			forceSelection:true,
			resizable:true,
			value: '1',
			handleHeight:10,
			width:230,
			listeners : {
				select : function(combo2, record, index) {
					saviComboBox.value = record.get("value");
				}
			}
		});	
		
		var updateSavi = new Ext.FormPanel( {
			labelWidth : 213,
			frame : true,
			title : '',
			bodyStyle : 'padding:5px 5px 0',
			width : 420,
			defaults : {
				width : 130
			},
			labelAlign: 'left',
			defaultType : 'textfield',
			items : [ 
			         {
						fieldLabel : maxDadDelayText,
						name : 'switchcur.maxDadDelay',
						blankText: emptyTipText,
						allowBlank:false
					}, {
						fieldLabel : maxDadPrepareDelayText,
						name : 'switchcur.maxDadPrepareDelay',
						blankText: emptyTipText,
						allowBlank:false
					}, saviComboBox
			],
			buttons : [ {
						text : submitButtonText,
						handler : function() {
							if (updateSavi.getForm().isValid()){
								updateSavi.getForm().submit({
									url:"config/batchUpdateSavi.do?switchcur.systemMode=" + saviComboBox.value + "&ids=" + ids + "&ipVersions=" + ipVersions,
									waitMsg: waitMsgText,
									success:function(form, action){
										updateSaviWin.close();
										store.reload();
										Ext.Msg.alert(noticeTitleText, updateSuccessText);
									},
									failure:function(from, action){
										var errMsg = Ext.decode(action.response.responseText).errMsg;
										store.reload();
										Ext.Msg.alert(noticeTitleText, updateFailedText + errMsg);
									}
								});
							}
						}
			}]
		});
		
		var updateSaviWin = new Ext.Window( {
			width : 440,
			height : 320,
			layout : 'fit',
			plain : true,
			frame : true,
			title : saviInfoText,
			bodyStyle : 'text-align:left',
			buttonAlign : 'center',
			items : [updateSavi]
			});
		updateSaviWin.show();
	}
	
});


