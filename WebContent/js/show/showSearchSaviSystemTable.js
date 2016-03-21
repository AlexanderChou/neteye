Ext.namespace("savi");
Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'side';
var store = "";
var switchName="";
var ipVersion='';
var switchbasicinfoId = '';
var maxBindingNum = 0;
var keywords="";
var ids="";

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
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='qtip';

	var query = QueryString();
	keywords = query.keywords;
	ids = query.ids;
	
	var Record = new Ext.data.Record.create( [
	 {
		name : "id",
		mapping : "id"
	},{
		name : "switchId",
		mapping : "switchId"
	},{
		name : "switchName",
		mapping : "switchName"
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
		url : ids=="" ? "show/listSearchSwitchSavi.do?keywords=" + keywords : "show/batchGetSavi.do?ids=" + ids
	});
	
	var colm = new Ext.grid.ColumnModel( [
	{
		dataIndex : "switchId",
		hidden: true,
		renderer: renderSwitchId
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
		sortable : true,
		renderer: renderIpVersion
	}, {
		header : systemModeText,
		dataIndex : "systemMode",
		width:200,
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
		header : interfaceText,
		width:100,
		renderer: gotoInterface
	}, {
		dataIndex: "id",
		header : bindingTableText,
		width:100,
		renderer: gotoBindingTable
	}, {
		dataIndex: "id",
		header : viewAllText,
		width:100,
		renderer: gotoViewAll
	}]);

	store = new Ext.data.Store( {
		proxy : proxy,
		reader : reader
	});
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
		val = val / 100;
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
	
	function gotoInterface(val){
		return '<a href="showRealTimeSwitchInterface.do?ipVersion='+ipVersion + "&switchbasicinfoId=" + switchbasicinfoId + '&switchName=' + switchName +'"><img src="images/common/green_arrow.jpg" width="35px" height="13px"/></a>';
	}
	
	function gotoBindingTable(val){
		return '<a href="showRealTimeSwitchBindingTableInfo.do?ipVersion='+ipVersion + "&switchbasicinfoId=" + switchbasicinfoId + '&switchName=' + switchName +'"><img src="images/common/green_arrow.jpg" width="35px" height="13px"/></a>';
	}
	
	function gotoViewAll(val){
		return '<a href="showSwitchDetails.do?ipVersion='+ipVersion + "&switchbasicinfoId=" + switchbasicinfoId + '"><img src="images/common/blue_arrow.jpg" width="35px" height="13px"/></a>';
	}
	
	store.load( { 
		params : {
			start : 0,
			limit : 22
		}
	});
	
	var grid = new Ext.grid.GridPanel( {
		title:titleText,
		store : store,
		height : document.body.clientHeight - 37,
		width : document.body.clientWidth < 1024? 960:1220,
		cm : colm,
		autoExpandColumn: 'switchName',
		autoScroll : true,
		renderTo : "showDiv",
		bbar : new Ext.PagingToolbar( {
			pageSize : 22,
			store : store,
			displayInfo : true,
			displayMsg : displayMsgText,
			emptyMsg : 'no record'
		})
	});
	
});


