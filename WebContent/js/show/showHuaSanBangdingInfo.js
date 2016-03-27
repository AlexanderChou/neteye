Ext.namespace("savi");
Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'side';
var store = "";
var subnetId='';

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
	
	// create the data store
	var userChangeStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'show/listHuaSanBindingTableInfo.do?acId='+subnetId
		}),
		reader : new Ext.data.JsonReader({
			root : 'bindingTableInfoList',
			totalProperty:'totalCount',
			fields : ['id','apName','ipAddressType','ipAddress','macAddress','lifeTime']
			})
	});

	//用户变化信息面板
	var userChangePanel = new Ext.grid.GridPanel({
		store : userChangeStore,
		height : document.body.clientHeight - 37,
		width : document.body.clientWidth < 1024? 960:1220,

		renderTo : "bangDiv",
		title : userChangeInfoText,
		border: true,
		collapsible: false,
		//autoExpandColumn : 'ipAddress',
		//列模型
		columns : 
			[
					{header: bSwitchNameText, width: 80, sortable: true, dataIndex: 'apName'},
					{header: "IP协议版本", width: 80, sortable: false, dataIndex: 'ipAddressType', renderer : renderIpVersion},
					{id:"ipAddress", header: bIPAddressText, width: 250,sortable: true,dataIndex: 'ipAddress'},
					{header: bMACAddressText, width: 250,sortable: true,dataIndex: 'macAddress'},
					{header: bLifeTimeText, width:150,sortable: false,dataIndex : 'lifeTime',renderer:renderLifeTime}
					
					
				],

		bbar:new Ext.PagingToolbar({
			pageSize:50,
			store:userChangeStore,
			displayInfo:true,
			displayMsg:displayMsgText,
			emptyMsg:noRecordText
		})
	});
	userChangeStore.load({
		params: {
			start:0,
			limit:50
		}
	});
	function renderIpVersion(val){
		if(val == '1') return '<img src="images/common/ipv4.jpg" width="15px" height="15px"/>';
		else if(val == '2') return '<img src="images/common/ipv6.jpg" width="15px" height="15px"/>';
		else return 'Unkown';
	}	
	function renderLifeTime(val){
		var length = 80;
		var width = (val[0] / val[1]) * length;
		
		return '<img src="images/common/_blank.gif" style= "width:' + width +
			   'px;height:12px;background:url(images/common/green_bar.jpg) no-repeat scroll 0 0;">' +
			   '<span>&nbsp;&nbsp;' + val[0] + '</span>';
	}
	function renderState(val){
		if(val == '1'){
			return '<span>come&nbsp;&nbsp;</span><img src="images/common/_blank.gif" style= "width:15px;height:15px;background:url(images/common/correct.jpg) no-repeat scroll 0 0;">';
		}else{
			return '<span>leave&nbsp;&nbsp;</span><img src="images/common/_blank.gif" style= "width:15px;height:15px;background:url(images/common/wrong.jpg) no-repeat scroll 0 0;">';
		}
	};
});