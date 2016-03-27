Ext.namespace("savi");
Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'side';
var subnetStore = "";
var switchStore = "";
var keywords="";

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
	keywords = query.keywords;
	
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='qtip';  
	
	var switchBindingTypeHeader = bindingHeader+'&nbsp;&nbsp;' + 
		'(&nbsp;&nbsp;<span>DISABLE&nbsp;&nbsp;</span>' + 
		'<img src="images/common/_blank.gif" style= "width:25px;height:10px;background:url(images/switch/small/disable_switch.jpg) no-repeat scroll 0 0;">' +
		'&nbsp;&nbsp;&nbsp;&nbsp;<span>DEFAULT&nbsp;&nbsp;</span>' + 
		'<img src="images/common/_blank.gif" style= "width:25px;height:10px;background:url(images/switch/small/default_switch.jpg) no-repeat scroll 0 0;">' +
		'&nbsp;&nbsp;&nbsp;&nbsp;<span>SLAAC&nbsp;&nbsp;</span>' + 
		'<img src="images/common/_blank.gif" style= "width:25px;height:10px;background:url(images/switch/small/slaac_switch.jpg) no-repeat scroll 0 0;">' +
		'&nbsp;&nbsp;&nbsp;&nbsp;<span>DHCP&nbsp;&nbsp;</span>' + 
		'<img src="images/common/_blank.gif" style= "width:25px;height:10px;background:url(images/switch/small/dhcp_switch.jpg) no-repeat scroll 0 0;">' +
		'&nbsp;&nbsp;&nbsp;&nbsp;<span>MIX&nbsp;&nbsp;</span>' + 
		'<img src="images/common/_blank.gif" style= "width:25px;height:10px;background:url(images/switch/small/mix_switch.jpg) no-repeat scroll 0 0;">&nbsp;&nbsp;)';;
	
	var Record = new Ext.data.Record.create( [ 
	{
		name : "id",
		mapping : "id"
	}, {
		name : "name",
		mapping : "name"
	}, {
		name : "switchNumber",
		mapping : "switchNum"
	}, {
		name : "switchBindingType",
		mapping : "switchForGlobalViewList"
	}, {
		name : "userNumber",
		mapping : "userNum"
	},{name:'cz'},{name:'dd'},{name:'cc'} ]);

	var reader = new Ext.data.JsonReader( {
		root : "subnetList",
		totalProperty : 'totalCount'
	}, Record);

	var proxy = new Ext.data.HttpProxy( {
		url : "show/searchSubnet.do?keywords="+keywords
	});
	var colm = new Ext.grid.ColumnModel( [ {
		header : subnetID,	
		hidden: true,
		dataIndex : "id",
		sortable : true
	}, {
		header: subnetName,
		dataIndex : "name",
		sortable : true,
		width:100
	}, {
		header : switchNumber,
		dataIndex : "switchNumber",
		sortable : false,
		width:70
		//renderer: renderSwitchNumber
	},{
		id:"switchBindingType",
		header : switchBindingTypeHeader,
		dataIndex : "switchBindingType",
		sortable : false,
		width:650,
		renderer: renderSwitchBindingType
	}, {
		header : userNumber,
		dataIndex : "userNumber",
		sortable : false,
		width:50
		//renderer: renderUserNumber
	}]);
	/*
	function renderSwitchNumber(val){
		var switchNum = 0;
		for(var i = 0; i < val.length; i++ ){
			switchNum += val[i].switchcurs.length;
		}
		return switchNum;
	}
	*/
	function orderById(values){
		for(var j=1;j<values.length; j++){
			var key=values[j];
			i=j-1;
			while(i>=0&&values[i].switchBasicInfoId>key.switchBasicInfoId){
				values[i+1]=values[i];
				i--;
			}
			values[i+1]=key;
		}
	}	
	function renderSwitchBindingType(val){
		var count=0;
		var flag=1;
		var html = '<table>';
		var switchForGlobalViewList = val;
		orderById(switchForGlobalViewList);
		for(var i = 0; i < switchForGlobalViewList.length; i++ ){
			var switchbasicinfoId = switchForGlobalViewList[i].switchBasicInfoId;
			if(count%25==0){
				if(flag==1){
					html+='<tr>';
					flag=0;
				}
				else html+='</tr><tr>';					
			}
			count++;			
			var ipVersion = switchForGlobalViewList[i].ipVersion;
			var systemMode =switchForGlobalViewList[i].systemMode;
			var userNum = switchForGlobalViewList[i].userNum;
			var modeString = '';	
			if(systemMode == '1') modeString = 'disable';
			else if(systemMode == '2') modeString = 'default';
			else if(systemMode == '3') modeString = 'dhcp';
			else if(systemMode == '4') modeString = 'slaac';
			else if(systemMode == '5') modeString = 'mix';
			else modeString = 'unkown';
			html += '<td width="36px" height="25px"><table>';
			if(modeString == 'unkown') html += 'UNKOWN';
			else html += '<tr><td width="36px" height="10px">' +
						'<a href="showSwitchDetails.do?ipVersion=' + ipVersion + "&switchbasicinfoId=" + switchbasicinfoId +'" target="_blank">' +
						'<img src="images/common/_blank.gif" style= "width:25px;height:10px;background:' +
						'url(images/switch/small/' + modeString + '_switch.jpg) no-repeat scroll 0 0;">' + 
						'</a></td></tr>';
			html += '<tr><td width="36px" height="15px">' + 
						'<img src="images/common/_blank.gif" style= "width:8px;height:8px;background:' +
						'url(images/common/face.jpg) no-repeat scroll 0 0;">&nbsp;' + userNum +
						'</td></tr>';
			html += '</table></td>';
		}
		html += '</tr></table>';
		return html;
	}
	/*
	function renderUserNumber(val){
		var userNum = 0;
		for(var i = 0; i < val.length; i++ ){
			for(var j = 0; j < val[i].switchcurs.length; j++){
				userNum += val[i].switchcurs[j].staticNum;
				userNum += val[i].switchcurs[j].slaacNum;
				userNum += val[i].switchcurs[j].dhcpNum;
			}
		}
		return userNum;
	}
	*/
	subnetStore = new Ext.data.Store( {
		proxy : proxy,
		reader : reader
	});
	
	subnetStore.load( {
		params : {
			start : 0,
			limit : 5
		}
	});
	
	var subnetGrid = new Ext.grid.GridPanel( {
		title: subnetListText,
		store : subnetStore,
		height : document.body.clientHeight / 2 - 35,
		autoExpandColumn: 'switchBindingType',
		width : document.body.clientWidth < 1024? 945:1205,
		cm : colm,
		autoScroll : true,
		bbar : new Ext.PagingToolbar( {
			pageSize : 5,
			store : subnetStore,
			displayInfo : true,
			displayMsg : displayMsgText,
			emptyMsg : noRecordText
		})
	});
	
	function rowdblclickFn(grid, rowIndex, e){//双击事件
		 var row = grid.store.getById(grid.store.data.items[rowIndex].id);
		 //document.location.href = 'showSubnetDetails.do?subnetId=' + row.get("id");
		 window.open('showSubnetDetails.do?subnetId=' + row.get("id"),"_blank");
	}
	
	subnetGrid.addListener('rowdblclick', rowdblclickFn);//添加双击事件
	
	
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
		url : "show/searchSwitch.do?keywords="+keywords
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
	
	switchStore = new Ext.data.Store( {
		proxy : proxy,
		reader : reader
	});
	
	switchStore.load( {
		params : {
			start : 0,
			limit : 10
		}
	});
	
	var switchGrid = new Ext.grid.GridPanel( {
		title: switchbasicInfoText,
		store : switchStore,
		height : document.body.clientHeight / 2 - 35,
		autoExpandColumn: 'ipv6address',
		width : document.body.clientWidth < 1024? 945:1205,
		sm: sm,
		cm : colm,
		autoScroll : true,
		bbar : new Ext.PagingToolbar( {
			pageSize : 10,
			store : switchStore,
			displayInfo : true,
			displayMsg : displayMsgText,
			emptyMsg : noRecordText
		}),
		tbar : [ '->','-','->', {
			id : "show_savi_infomation_table",
			text : saviSystemTableText,
			handler: function(){
				var sIds = "";
				var selected = sm.getSelections();
				for(var i = 0; i < selected.length; i++){
					sIds += selected[i].data.id;
					if(i < selected.length - 1)
						sIds += "|";
				}
			
				window.open('showSearchSaviSystemTable.do?keywords='+keywords + "&ids=" + sIds);
			}},'->','-'
		]
	});	
	var w = document.body.clientWidth;
	var p = w < 1024? (w - 960) / 2:(w - 1220) / 2;
	var padding = 'padding: 10px ' + p + 'px 10px ' + p + 'px';
	
	//整体布局
    var viewport = new Ext.Viewport({
        layout:'border',
        width: '%100',
        autoScroll: false,
        cls: 'x-portal',
        style:"text-align:left;background-color: #FFFFFF",
        border: false,
		defaults:{
    		draggable: false
		},
        items:[{
            xtype:'portal',
            region:'center',
            title: searchResultsText,
            autoScroll: false,
            style: padding,
            draggable: false,
        	items:[{
        		xtype: 'portalcolumn',
        		columnWidth: '1.0',
        		//style: padding,
                items:[{
                	xtype: 'portlet',
                	draggable: false,
                	items:[subnetGrid]
                },{
                	xtype: 'portlet',
                	draggable: false,
                	items:[switchGrid]
                }
                ]

            }]
           }
        ]
    });
});