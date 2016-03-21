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
			url :'show/listHuaSanUserChangeInfo.do?acId='+subnetId
		}),
		reader : new Ext.data.JsonReader({
			root : 'userInfoList',
			totalProperty:'totalCount',
			fields : ['id','time','name','status','ipAddress','apName']
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
		autoExpandColumn : 'apName',
		//列模型
		columns : [
			{header: uTimeText, width: 120, sortable: true, dataIndex: 'time'},
			//{header: uNameText, width: 80, sortable: true, dataIndex: 'name'},
			{header: uStatusText, width: 60, sortable: false, dataIndex: 'status', renderer : renderState},
			{id:"ipAddress", header: uIPAddressText, width: 200,sortable: true,dataIndex: 'ipAddress'}, 
			{id:"apName", header: uSwitchNameText, width:100,sortable: true,dataIndex : 'apName'}
			],
//		tools:[{
//			id: 'left',
//			handler: function(e, target, panel){
//				document.location.href = 'showSubnetDetails.do?subnetId=' + subnetId;
//			}
//		}],
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