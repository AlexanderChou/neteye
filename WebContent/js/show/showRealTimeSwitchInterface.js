Ext.namespace("savi");
Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'side';
var switchName='';
var maxFilteringNum = 1;
var ifIndex = '';
var ipVersion='';
var switchbasicinfoId = '';

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
	var interfaceStore = new Ext.data.Store({
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
		[
		 	{header: "MaxFilteringNum", hidden: true, dataIndex: 'maxFilteringNum', renderer : renderMaxFilteringNum},
			{header: ifIndexText, width: 150, sortable: true, dataIndex: 'ifIndex', renderer : renderIfIndex},
			{header: ipVersionText, width: 150, sortable: false, dataIndex: 'ipVersion', renderer : renderIpVersion},
			{header: ifValidationStatusText, width:200,sortable: false,dataIndex : 'ifValidationStatus', renderer:renderIfValidationStatus},
			{header: ifTrustStatusText, width:200,sortable: false,dataIndex : 'ifTrustStatus', renderer:renderIfTrustStatus},
			{id: "ifFilteringNum", header: ifFilteringNumText, width:200,sortable: false,dataIndex : 'ifFilteringNum', renderer:renderIfFilteringNum},
			{header: bindingTableText, dataIndex: "IfIndex", width:100, renderer:gotoBindingTable}
		]);

	//Binding Table面板
	var interfacePanel = new Ext.grid.GridPanel({
		store : interfaceStore,
		height : document.body.clientHeight - 37,
		width : document.body.clientWidth < 1024? 960:1220,
		renderTo : "showDiv",
		title : switchInterfaceText + '(' + switchName + ')',
		border: true,
		collapsible: false,
		autoExpandColumn : 'ifFilteringNum',
		//列模型
		cm: interfaceColm,
		bbar:new Ext.PagingToolbar({
			pageSize:25,
			store:interfaceStore,
			displayInfo:true,
			displayMsg: displayMsgText,
			emptyMsg: noRecordText
		})
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
		return '<a href="showRealTimeInterfaceBindingTableInfo.do?ipVersion=' + ipVersion + "&switchbasicinfoId=" + switchbasicinfoId + 
				'&ifIndex=' + ifIndex + '&switchName=' + switchName +'">' + 
				'<img src="images/common/blue_arrow.jpg" width="35px" height="13px"/></a>';
	}

});