Ext.namespace("savi");
Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'side';
var store = "";
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
	
	// create the data store
	var userChangeStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'show/listSwitchUserChangeInfo.do?ipVersion='+ipVersion + "&switchbasicinfoId=" + switchbasicinfoId
		}),
		//创建JsonReader读取interface记录
		reader : new Ext.data.JsonReader({
			root : 'userInfoList',
			totalProperty:'totalCount',
			fields : ['id','time','name','status','ipAddress','ifIndex','switchName']
			})
	});
	userChangeStore.load({
		params: {
			start:0,
			limit:50
		}
	});
	//用户变化信息面板
	var userChangePanel = new Ext.grid.GridPanel({
		store : userChangeStore,
		height : document.body.clientHeight - 37,
		width : document.body.clientWidth < 1024? 960:1220,
		renderTo : "showDiv",
		title : userChangeInfoText,
		border: true,
		collapsible: false,
		autoExpandColumn : 'ipAddress',
		//列模型
		columns : [
					{header: uTimeText, width: 150, sortable: true, dataIndex: 'time'},
					{header: uNameText, width: 150, sortable: true, dataIndex: 'name'},
					{header: uStatusText, width: 150, sortable: false, dataIndex: 'status', renderer : renderState},
					{id:"ipAddress", header: uIPAddressText, width: 200,sortable: true,dataIndex: 'ipAddress'}, 
					{header: uIfIndexText, width:150,sortable: true,dataIndex : 'ifIndex'},
					{id:"switchName", header: uSwitchNameText, width:150,sortable: true,dataIndex : 'switchName'}
			],
		bbar:new Ext.PagingToolbar({
			pageSize:50,
			store:userChangeStore,
			displayInfo:true,
			displayMsg:displayMsgText,
			emptyMsg:noRecordText
		})
	});
	
	function renderState(val){
		if(val == '1'){
			return '<span>come&nbsp;&nbsp;</span><img src="images/common/_blank.gif" style= "width:15px;height:15px;background:url(images/common/correct.jpg) no-repeat scroll 0 0;">';
		}else{
			return '<span>leave&nbsp;&nbsp;</span><img src="images/common/_blank.gif" style= "width:15px;height:15px;background:url(images/common/wrong.jpg) no-repeat scroll 0 0;">';
		}
	};
});