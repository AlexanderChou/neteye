Ext.namespace("savi");
Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'side';
var subnetId='';
var acname='';
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
Ext.onReady( function() {
	var query = QueryString();
	subnetId = query.subnetId;
	
	//获取子网的基本信息
	Ext.Ajax.request({
		url : 'json/listShowAC.do?acId='+subnetId,
		disableCaching : true,
		method : 'GET',
		success : function(result, request) {
			var ac=Ext.decode(result.responseText).ac;
			acname = ac.name;
			var panel = Ext.getCmp('subnetSwitches');
			var html = '<table width="100%">';
			var val = ac.apForGlobalViewList;
			orderById(val);
			var total=ac.userNum;
			var loop=0;
			for(var i = 0; i < val.length; i++ ){
				if(loop == 0){
					html += '<tr>';
				}
				loop++;	
				var apname = val[i].apname;
				var switchbasicinfoId = val[i].apid;
				
				var modeString = '';
				var userNum = val[i].userNum;
				
				modeString = 'default';
				if(status=='0'){
					modeString = 'default';

				}else{
					modeString = 'slaac';
					
				}
				
				html += '<td width="16.66%" height="25px"><table>';
				if(modeString == 'unkown') 
					html += 'UNKOWN';
				else {
					html += '<tr><td width="30px" height="10px">' +	
					
							'<a href="showPADetails.do?switchbasicinfoId=' +  switchbasicinfoId + '" target="_blank">' +
							
							'<img src="images/common/_blank.gif" style= "width:30px;height:10px;background:' +
							'url(images/switch/small/' + modeString + '_switch.jpg) no-repeat scroll 0 0;">' + 
							'</a></td><td>'+apname+'</td></tr>';
					html += '<tr><td width="30px" height="15px" style="font:11px arial,tahoma,helvetica,sans-serif">' + 
								'<img src="images/common/_blank.gif" style= "width:8px;height:8px;background:' +
								'url(images/common/face.jpg) no-repeat scroll 0 0;">&nbsp;&nbsp;' + userNum +
								'</td></tr>';
					html += '</table></td>';
				}
				if(loop > 1){
					loop = 0;
					html += '</tr>';
				}
			}
			
			for(var i = loop; i < 2 && loop > 0; i++){
				html += '<td width="16.66%" height="25px"></td>';
				if(i == 1) html +='</tr>';
			}
				
			html += '</table>';
			panel.setTitle(ac.name + "(" + userNumText + " " + total + ")" );
			subnetSwitchstpl.overwrite(panel.body,{html:html});
		}
	});
	
	var subnetSwitchstpl = new Ext.Template(
		'{html}'
	);

	// 信息面板
    var infoPanel = new Ext.Panel({
		height: 500,
		margins : '0 0 5 5',
		layout:'column',
		autoScroll: true,
		bodyStyle: 'background-color: #FFFFFF',
		items : [{
			 		id: 'subnetSwitches',
			 		autoScroll: true,
                	draggable: false,
					border : true,
					title : subnetNameText,
					height: 490,
					columnWidth: 1.0
				}]
            
	});
	
	//统计图模板
	var chartTpl = new Ext.Template(
            '<div style="margin-top:10px;text-align:left;">',
                  '<img src="{pic}">',
            '</div>'
    );
	/****
	
	//饼状图面板
	var pieChartPanel = new Ext.Panel({
		height : 300,
		border: false,
		split:'true',
		collapsible: true,
		collapseMode: 'mini',
		margins : '0 0 2 0',
		layout:'column',
		bodyStyle: 'background-color: #FFFFFF',
		defaults:{
			xtype: 'portlet',
			bodyStyle: 'background-color: #FFFFFF',
			draggable: false
		},
		items : [{
			columnWidth: .25,
			height: 300,
			style: 'padding: 0px 10px 0px 0px',
			title :switchRunningStateChartText,
			html : chartTpl.apply({pic: 'jfreechart/genSwitchRunningStateChart.do?subnetId=' + subnetId})
		}, {
			columnWidth: .25,
			style: 'padding: 0px 10px 0px 0px',
			height: 300,
			title : interfaceTrustTypeChartText,
			html : chartTpl.apply({pic: 'jfreechart/genInterfaceTrustTypeChart.do?subnetId=' + subnetId})
		}, {
			columnWidth: .25,
			style: 'padding: 0px 10px 0px 0px',
			height: 300,
			title : ipBindingTypeStateChartText,
			html : chartTpl.apply({pic: 'jfreechart/genIPBindingTypeStateChart.do?subnetId=' + subnetId})
		}, {
			columnWidth: .25,
			height: 300,
			title : interfaceValidatonStatusChartText,
			html : chartTpl.apply({pic: 'jfreechart/genInterfaceValidatonStatusChart.do?subnetId=' + subnetId})
		}]
	});
	***/
	//用户变化曲线面板-在线用户数24小时
	var userNumberChangeChartPanel = new Ext.Panel({
		height : 245,
		border: false,
		split:'true',
		collapsible: true,
		collapseMode: 'mini',
		margins : '0 0 2 0',
		layout:'column',	
		bodyStyle: 'background-color: #FFFFFF',
		defaults:{
			bodyStyle: 'background-color: #FFFFFF'
		},
		items : [{
					columnWidth: 1.0,
					height: 235,
					title : userNumberChangeChartText,
					html : chartTpl.apply({pic:'jfreechart/genACUserNumberChangeChart.do?subnetId=' + subnetId})
				}]
		});
	/***
	//用户在线时间面板
	var userOnlineTimeChartPanel = new Ext.Panel({
		height : 245,
		border: false,
		split:'true',
		collapsible: true,
		collapseMode: 'mini',
		margins : '0 0 2 0',
		layout:'column',
		bodyStyle: 'background-color: #FFFFFF',
		defaults:{
			bodyStyle: 'background-color: #FFFFFF'
		},
		items : [{
					columnWidth: 1.0,
					height: 235,
					title : userOnlineTimeChartText,
					html : chartTpl.apply({pic:'jfreechart/genUserOnlineTimeChart.do?subnetId=' + subnetId})
				}]
		});
	
	
	//IP绑定状态变化曲线面板
	var ipBindingStateChartPanel = new Ext.Panel({
		height : 245,
		border: false,
		split:'true',
		collapsible: true,
		collapseMode: 'mini',
		margins : '0 0 2 0',
		layout:'column',	
		bodyStyle: 'background-color: #FFFFFF',
		defaults:{
			bodyStyle: 'background-color: #FFFFFF'
		},
		items : [{
					columnWidth: 1.0,
					height: 235,
					title : ipBindingStateChartText,
					html : chartTpl.apply({pic:'jfreechart/genIPBindingStateChart.do?subnetId=' + subnetId})
				}]
		});
	***/
	//交换机用户数TopN面板
	var switchUserNumChartPanel = new Ext.Panel({
		height : 245,
		border: false,
		split:'true',
		collapsible: true,
		collapseMode: 'mini',
		margins : '0 0 2 0',
		layout:'column',	
		bodyStyle: 'background-color: #FFFFFF',
		defaults:{
			bodyStyle: 'background-color: #FFFFFF'
		},
		items : [{
					columnWidth: 1.0,
					height: 235,
					title : "AP用户数TopN",
					html :  chartTpl.apply({pic:'jfreechart/genACUserNumChart.do?subnetId=' + subnetId})
				}]
		});
	
	// create the data store
	var userChangeStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'show/listHuaSanUserChangeInfo.do?acId='+subnetId
		}),
		//创建JsonReader读取interface记录
		reader : new Ext.data.JsonReader({
			root : 'userInfoList',
			totalProperty:'totalCount',
			fields : ['id','time','name','status','ipAddress','apName']
			})
	});
	userChangeStore.load({
		params: {
			start:0,
			limit:6
		}
	});
	//用户变化信息面板
	var userChangePanel = new Ext.grid.GridPanel({
		store : userChangeStore,
		height : 245,
		title : userChangeInfoText,
		border: true,
		collapsible: true,
		collapseMode: 'mini',
		autoExpandColumn : 'apName',
		//列模型
		columns : [
			{header: uTimeText, width: 120, sortable: true, dataIndex: 'time'},
			//{header: uNameText, width: 80, sortable: true, dataIndex: 'name'},
			{header: uStatusText, width: 60, sortable: false, dataIndex: 'status', renderer : renderState},
			{id:"ipAddress", header: uIPAddressText, width: 200,sortable: true,dataIndex: 'ipAddress'}, 
			{id:"apName", header: uSwitchNameText, width:100,sortable: true,dataIndex : 'apName'}
			],
		tools:[{
			id: 'right',
			handler: function(e, target, panel){
				window.open('showHuaSanUserChangeInfo.do?subnetId=' + subnetId);
			}
		}],
		bbar:new Ext.PagingToolbar({
			pageSize:6,
			store:userChangeStore,
			displayInfo:true,
			displayMsg: displayMsgText,
			emptyMsg: noRecordText
		})
	});
	function renderState(val){
		if(val == '1'){
			return '<span>come&nbsp;&nbsp;</span><img src="images/common/_blank.gif" style= "width:15px;height:15px;background:url(images/common/correct.jpg) no-repeat scroll 0 0;">';
		}else{
			return '<span>leave&nbsp;&nbsp;</span><img src="images/common/_blank.gif" style= "width:15px;height:15px;background:url(images/common/wrong.jpg) no-repeat scroll 0 0;">';
		}
	};
	
	
	// create the data store
	var bindingTableStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'show/listHuaSanBindingTableInfo.do?acId='+subnetId
		}),
		//创建JsonReader读取interface记录
		reader : new Ext.data.JsonReader({
			root : 'bindingTableInfoList',
			totalProperty:'totalCount',
			fields : ['id','apName','ipAddressType','ipAddress','macAddress','lifeTime','user']
			})
	});
	
	var bindingTableColm = new Ext.grid.ColumnModel( 
		[
			{header: bSwitchNameText, width: 80, sortable: true, dataIndex: 'apName'},
			{header: "IP协议版本", width: 60, sortable: false, dataIndex: 'ipAddressType', renderer : renderIpVersion},
			{id:"ipAddress", header: bIPAddressText, width: 160,sortable: true,dataIndex: 'ipAddress'},
			{header: bMACAddressText, width: 160,sortable: true,dataIndex: 'macAddress'},
			{header: bLifeTimeText, width:120,sortable: false,dataIndex : 'lifeTime',renderer:renderLifeTime}
			
			
		]);

	//Binding Table面板
	var bindingTablePanel = new Ext.grid.GridPanel({
		store : bindingTableStore,
		height : 245,
		title : bindingTableText,
		border: true,
		collapsible: true,
		collapseMode: 'mini',
		//autoExpandColumn : 'isInFilteringTable',
		//列模型
		cm: bindingTableColm,
		autoWidth: true,
	
		tools:[{
			id: 'right',
			handler: function(e, target, panel){
				window.open('showHuaSanbangdingInfo.do?subnetId=' + subnetId) ;
			}
		}],
		bbar:new Ext.PagingToolbar({
			pageSize:5,
			store:bindingTableStore,
			displayInfo:true,
			displayMsg: displayMsgText,
			emptyMsg: noRecordText
		})
	});
	
	bindingTableStore.load({
		params: {
			start:0,
			limit:5
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
	
		var length = 40;
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
	
	//整体布局
    var viewport = new Ext.Viewport({
        layout:'border',
        width: '100%',
        autoScroll: true,
        cls: 'x-portal',
        style:"text-align:left",
        border: false,
		defaults:{
    		draggable: false
		},
        items:[{
            xtype:'portal',
            region:'center',
            margins:'0 5 5 0',
            draggable: false,
        	items:[{
        		xtype: 'portalcolumn',
        		columnWidth: '.25',
        		style: 'padding: 10px 10px 0px 30px',
                items:[{
                	xtype: 'portlet',
                	draggable: false,
                	items:[infoPanel]
                }
                ]

            },{
        		xtype: 'portalcolumn',
        		columnWidth: '.75',
        		style: 'padding: 10px 30px 0px 0px',
                items:[
                {
                	xtype: 'portlet',
                	layout: 'column',
                	width: '100%',
                	frame: false,
                	border: false,
                	cls: 'x-portal-column',
                	draggable: false,
                	items:[{
                		xtype: 'portalcolumn',
                		columnWidth: '.33',
                		style: 'padding: 0px 10px 0px 0px',
                		items:[{
                        	xtype: 'portlet',
                        	draggable: false,
                        	items:[switchUserNumChartPanel]
                        } ,{
                        	xtype: 'portlet',
                        	draggable: false,
                        	items:[userNumberChangeChartPanel]
                        }
                        ]
                	},{
                		xtype: 'portalcolumn',
                		columnWidth: '.67',
                		style: 'padding: 0px 0px 0px 0px',
                		items:[{
                        	xtype: 'portlet',
                        	draggable: false,
                        	items:[userChangePanel]
                        },{
                        	xtype: 'portlet',
                        	draggable: false,
                        	items:[bindingTablePanel]
                        }]
                	}]
                }
                ]
            }]
           }
        ]
    });
});