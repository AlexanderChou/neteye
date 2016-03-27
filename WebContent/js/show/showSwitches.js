Ext.namespace("savi");
Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'side';
var store = "";
var ids = "";
var subnetId="";
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
	ids = query.ids;
	subnetId = query.subnetId;
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
		name : "subnetName",
		mapping : "subnetName"
	},{
		name: "status",
		mapping: "status"
	},{name:'cz'},{name:'dd'},{name:'cc'} ]);

	var reader = new Ext.data.JsonReader( {
		root : "switchList",
		totalProperty : 'totalCount'
	}, Record);

	var proxy = new Ext.data.HttpProxy( {
		url : ids == "" ? "show/listShowSwitches.do?subnetId="+subnetId : "show/batchGetSwitch.do?ids=" + ids
	});
	
	var colm = new Ext.grid.ColumnModel( [
	new Ext.grid.RowNumberer({header:'', width:20}),			
	sm,{
		header : idText,
		hidden: true,
		dataIndex : "id",
		sortable : true
	}, {
		header : nameText,
		dataIndex : "name",
		sortable : true,
		width:200
	}, {
		id:"equipmentType",
		header : equipmentTypeText,
		dataIndex : "equipmentType",
		sortable : true,
		width:200
	}, {
		header : ipv4addressText,
		dataIndex : "ipv4address",
		sortable : true,
		width:200
	}, {
		id:"ipv6address",
		header : ipv6addressText,
		dataIndex : "ipv6address",
		sortable : true,
		width:200
	}, {
		header : subnetNameText,
		dataIndex : "subnetName",
		sortable : true,
		width:200
	},{
		header: statusText,
		dataIndex: "status",
		sortable:false,
		renderer : statusRenderer,
		width:150
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
	var subnetStore = new Ext.data.JsonStore({
		url: 'show/listShowSubnetsForCombo.do',
		root : 'subnetListForCombo',
		totalProperty:'totalCount',
		fields:['id', 'name']
	});
	var subnetComboBox = new Ext.form.ComboBox({
		id: 'subnet',
		loadingText: loadingText,
		width: 230,
		//fieldLabel: subnetListText,
		store: subnetStore,
		triggerAction: 'all',
		forceSelection:true,
		displayField: 'name',
		valueField:'id',
		pageSize: 20,
		editable: false,
		mode: 'remote',
		value:pleaseSelectText,
		lazyInit: false,
		listeners : {
			select : function(combo2, record, index) {
				combo2.value = record.get("id");
				ids="";
				store.proxy = new Ext.data.HttpProxy({
					url :'show/listShowSwitches.do?subnetId='+combo2.value
				});
				store.reload();
			}
		}
	});
	var grid = new Ext.grid.GridPanel( {
		title: titleText,
		store : store,
		height : document.body.clientHeight - 37,
		autoExpandColumn: 'ipv6address',
		width : document.body.clientWidth < 1024? 960:1220,
		sm: sm,
		cm : colm,
		autoScroll : true,
		renderTo : "showDiv",
		bbar : new Ext.PagingToolbar( {
			pageSize : ids == "" ? 200 : 65535,
			store : store,
			displayInfo : true,
			displayMsg : displayMsgText,
			emptyMsg : noRecordText
		}),
		tbar : [ '->','-',subnetComboBox,
		         '->','-','->', {
			id : "show_savi_infomation_table",
			text : saviText,
			handler: function(){
				var sIds = "";
				var selected = sm.getSelections();
				for(var i = 0; i < selected.length; i++){
					sIds += selected[i].data.id;
					if(i < selected.length - 1)
						sIds += "|";
				}
				if(sIds==""){
					sIds=ids;
				}
				if(subnetComboBox.value==pleaseSelectText){
					document.location.href = 'showSaviSystemTable.do?ids=' + sIds;
				}else{
					document.location.href = 'showSaviSystemTable.do?ids=' + sIds+'&subnetId='+subnetComboBox.value;
				}
			}}
		]
	});
});