Ext.namespace("savi");
Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'side';
var subnetId='';
var subnetName='';

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
	subnetId = query.subnetId;
	subnetName = query.subnetName;
	
	var bindingTableStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'show/listSubnetBindingTableInfo.do?subnetId='+subnetId
		}),
		reader : new Ext.data.JsonReader({
			root : 'bindingTableInfoList',
			totalProperty:'totalCount',
			fields : ['id','switchName','ifIndex','ipAddressType','ipAddress', 'macAddress', 'bindingType','bindingState', 'lifeTime','user', 'isInFilteringTable']
			})
	});
	
	var bindingTableColm = new Ext.grid.ColumnModel( 
		[
			{header: bSwitchNameText, width: 80, sortable: true, dataIndex: 'switchName'},
			{header: bIfIndexText, width: 80, sortable: true, dataIndex: 'ifIndex'},
			{header: bIPAddressTypeText, width: 60, sortable: false, dataIndex: 'ipAddressType', renderer : renderIpVersion},
			{id:"ipAddress", header: bIPAddressText, width: 120,sortable: true,dataIndex: 'ipAddress'},
			{header: bMACAddressText, width: 120,sortable: true,dataIndex: 'macAddress'},
			{header: bBindingTypeText, width:100,sortable: false,dataIndex : 'bindingType', renderer:renderBindingType},
			{header: bBindingStateText, width:100,sortable: false,dataIndex : 'bindingState', renderer:renderBindingState},
			{header: bLifeTimeText, width:150,sortable: false,dataIndex : 'lifeTime',renderer:renderLifeTime},
			{header: bUserText, width:100,sortable: true,dataIndex : 'user'},
			{id:'isInFilteringTable',header: bIsInFilteringTableText, width:100,sortable: false,dataIndex : 'isInFilteringTable', renderer:renderIsInFilteringTable}
		]);

	//Binding Table面板
	var bindingTablePanel = new Ext.grid.GridPanel({
		store : bindingTableStore,
		height : document.body.clientHeight - 37,
		width : document.body.clientWidth < 1024? 960:1220,
		renderTo : "showDiv",
		title : bindingTableForSubnetText + '(' + subnetName + ')',
		border: true,
		collapsible: false,
		autoExpandColumn : 'ipAddress',
		//列模型
		cm: bindingTableColm,
//		tools:[{
//			id: 'left',
//			handler: function(e, target, panel){
//				document.location.href = 'showSubnetDetails.do?subnetId=' + subnetId;
//			}
//		}],
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
		if(val > 5 || val < 1) width = 0;
		else width = val * 8 - 2;
		
		var state = 'UNKOWN';
		if(val == 1) state = "START";
		else if(val == 2) state = "LIVE";
		else if(val == 3) state = "DETECTION";
		else if(val == 4) state = "QUERY";
		else if(val == 5) state = "BOUND";
		
		return '<table>' + 
			   '<tr><td><img src="images/common/_blank.gif" style= "width:' + width +'px;height:6px;background:url(images/common/short_green_block_bar.jpg) no-repeat scroll 0 0;"></td></tr>' + 
			   '<tr><td><span>' + state + '</span></td></tr>' +
			   '</table>'; 
	}
	
	function renderLifeTime(val){
		//val[0] = Math.round(val[0] / 100);//还算成秒
		//val[1] = Math.round(val[1] / 100);
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

});