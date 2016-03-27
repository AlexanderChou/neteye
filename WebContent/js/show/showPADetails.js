Ext.namespace("savi");
Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'side';
var ipVersion='';
var switchbasicinfoId = '';
var switchName='';
var ap;
var totalUser=0;

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
	switchbasicinfoId = query.switchbasicinfoId;
	
	//获取交换机的基本信息
	Ext.Ajax.request({
		url : 'show/listShowPAcur.do?apid=' + switchbasicinfoId,
		disableCaching : true,
		method : 'GET',
		success : function(result, request) {
			ap=Ext.decode(result.responseText).ap;
			switchName = ap.apname;
			totalUser = Ext.decode(result.responseText).count;
			/***
			var ipanel = Ext.getCmp('interfacePancel');
			ipanel.setTitle(interfacePanelText + '(' + onlineUserNumText + totalUser + ')'+
					'(&nbsp;<span>vali-enable&nbsp;&nbsp;</span>' + 
					'<img src="images/interface/validation-enable.jpg" style= "width:25px;height:20px;background:url(images/interface/validation-enable.jpg) no-repeat scroll 0 0;">' +
					'&nbsp;&nbsp;<span>vali-disable&nbsp;&nbsp;</span>' + 
					'<img src="images/interface/validation-disable.jpg" style= "width:25px;height:20px;background:url(images/interface/validation-disable.jpg) no-repeat scroll 0 0;">' +
					'&nbsp;&nbsp;<span>ra-trust&nbsp;&nbsp;</span>' + 
					'<img src="images/interface/ra-trust.jpg" style= "width:25px;height:20px;background:url(images/interface/ra-trust.jpg) no-repeat scroll 0 0;">' +
					'&nbsp;&nbsp;<span>dhcp-trust&nbsp;&nbsp;</span>' + 
					'<img src="images/interface/dhcp-trust.jpg" style= "width:25px;height:20px;background:url(images/interface/dhcp-trust.jpg) no-repeat scroll 0 0;">' +
					'&nbsp;&nbsp;<span>dhcp-ra-trust&nbsp;&nbsp;</span>' + 
					'<img src="images/interface/dhcp-ra-trust.jpg" style= "width:25px;height:20px;background:url(images/interface/dhcp-ra-trust.jpg) no-repeat scroll 0 0;">&nbsp;&nbsp;)');
			***/
			var panel = Ext.getCmp('switchInfo');
			
			//属性记录
			var ipv4addressRec = new Ext.grid.PropertyRecord({
			    name: ipv4addressText,
			    value: ap.ipv4Address!=null?ap.ipv4Address:''
			});
			
			var ipv6addressRec = new Ext.grid.PropertyRecord({
			    name: ipv6addressText,
			    value: ap.ipv6Address!=null?ap.ipv6Address:''
			});
			
			
			var nameRec = new Ext.grid.PropertyRecord({
			    name: nameText,
			    value: ap.apname!=null?ap.apname:''
			});
			
			panel.store.add(ipv4addressRec);
			panel.store.add(ipv6addressRec);
			panel.store.add(nameRec);
		}
	});
	
	// 表盘面板

    var infoPanel = new Ext.Panel({
		height: 300,
		margins : '0 0 5 5',
		bodyStyle: 'background-color: #FFFFFF',
		defaults:{
			bodyStyle: 'background-color: #FFFFFF'
		},
		layout:'column',
		items : [
		{
			xtype: 'portlet',
			draggable: false,
			style: 'padding: 0px 10px 0px 0px',
			title : basicInfoText,
			border : true,
			height: 300,
			layout:"fit",
			columnWidth: 1.02,
			bodyStyle: 'background-color: #FFFFFF',
			items : [
			  new Ext.grid.PropertyGrid({
				id: 'switchInfo',
				collapsed : false,
				collapsible : true,
				height : 300,
				listeners:{
					beforeedit:function(e){
						e.cancel = true;
					}	
				}
			})]
		}
		/**
		,{
					xtype: 'portlet',
					draggable: false,
					border : true,
					title : runningStateText,
					height: 300,
					columnWidth: .50,
					html : 	'<table width="100%">' +
								'<tr><td><br/></td></tr>'+
								'<tr>' +
								'<td width="50%" align="center"><img src="images/temp/IfValidation_'+ipVersion + "_" + switchbasicinfoId + ".png" +'"/></td>' + 
								'<td align="center"><img src="images/temp/IfTrust_'+ipVersion + "_" + switchbasicinfoId + ".png" +'"/></td>' +
								'</tr>' +
								'<tr><td><br/></td></tr>'+
								'<tr>' +
								'<td width="50%" align="center"><img src="images/temp/Filtering_'+ipVersion + "_" + switchbasicinfoId + ".png" +'"/></td>' +
								'<td align="center"><img src="images/temp/Binding_'+ipVersion + "_" + switchbasicinfoId + ".png" +'"/></td>' +
								'</tr>' +
								'</table>'
				}
		
		**/
		]
            
	});
	
	//统计图模板
	var chartTpl = new Ext.Template(
            '<div style="margin-top:10px;text-align:left;">',
                  '<img src="{pic}">',
            '</div>'
    );
	

	//用户曲线图面板
	var userCurvesChartPanel = new Ext.Panel({
		height : 245,
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
			columnWidth: .5,
			height: 245,
			style: 'padding: 0px 10px 0px 0px',
			title : "运行状态(1个月)",
			html : chartTpl.apply({pic: 'jfreechart/genPAUserOnlineTimeMonthChart.do?switchbasicinfoId=' + switchbasicinfoId})
		}, {
			columnWidth: .5,
			height: 245,
			title : "运行状态(24小时)",
			html : chartTpl.apply({pic: 'jfreechart/genPAUserOnlineTimeChart.do?switchbasicinfoId=' + switchbasicinfoId})
		}]
	});
	/***
	//用户柱状图面板
	var userBarChartPanel = new Ext.Panel({
		height : 245,
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
			columnWidth: .5,
			height: 245,
			style: 'padding: 0px 10px 0px 0px',
			title : interfaceUserNumberSortChartText,
			html : chartTpl.apply({pic: 'jfreechart/genInterfaceUserNumberSortChart.do?ipVersion='+ipVersion + "&switchbasicinfoId=" + switchbasicinfoId})
		}, {
			columnWidth: .5,
			height: 245,
			title : userOnlineTimeSortChartText,
			html : chartTpl.apply({pic: 'jfreechart/genUserOnlineTimeSortChart.do?ipVersion='+ipVersion + "&switchbasicinfoId=" + switchbasicinfoId})
		}]
	});
	**/
	// create the data store
	var userChangeStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'show/listHuaSanPAUserChangeInfo.do?acId='+switchbasicinfoId
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
		height : 300,
		title : userChangeInfoText,
		border: true,
		collapsible: true,
		collapseMode: 'mini',
		autoExpandColumn : 'apName',
		//列模型
		columns : [
			{header: uTimeText, width: 120, sortable: true, dataIndex: 'time'},
			//{header: uNameText, width: 80, sortable: true, dataIndex: 'name'},
			{id:"ipAddress", header: uIPAddressText, width: 200,sortable: true,dataIndex: 'ipAddress'}, 
			{id:"apName", header: uSwitchNameText, width:100,sortable: true,dataIndex : 'apName'},
			{header: uStatusText, width: 60, sortable: false, dataIndex: 'status', renderer : renderState}
			],
		tools:[{
			id: 'right',
			handler: function(e, target, panel){
				window.open('showHuaSanPAUserChangeInfo.do?subnetId=' + switchbasicinfoId);
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
	
	
	var bindingTableStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'show/listHuaSanPABindingTableInfo.do?acId='+switchbasicinfoId
		}),

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
				{id:"ipAddress", header: bIPAddressText, width: 120,sortable: true,dataIndex: 'ipAddress'},
				{header: bMACAddressText, width: 120,sortable: true,dataIndex: 'macAddress'},
				{header: bLifeTimeText, width:100,sortable: false,dataIndex : 'lifeTime',renderer:renderLifeTime}
				
				
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
		tools:[{
			id: 'right',
			handler: function(e, target, panel){
			      window.open('showHuaSanPAbangdingInfo.do?subnetId=' + switchbasicinfoId) ;
		}
		}],
		bbar:new Ext.PagingToolbar({
			pageSize:5,
			store:bindingTableStore,
			displayInfo:true,
			displayMsg:displayMsgText,
			emptyMsg:noRecordText
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
	
	var interfacetpl = new Ext.Template(
			'{html}'
		);

	// 端口面板
	 /***
    var interfacePanel = new Ext.Panel({
		height: 300,
		margins : '0 0 5 5',
		autoScroll: true,
		layout:'column',
		autoScroll: true,
		bodyStyle: 'background-color: #FFFFFF',
		items : [{
			 		id: 'interfacePancel',
			 		autoScroll: true,
                	draggable: false,
					border : true,
					title : interfacePanelText,
					height: 280,
					columnWidth: 1.0
				}]
            
	});
	
	//获取端口列表
   
	Ext.Ajax.request({
		url : 'show/listInterfaces.do?ipVersion='+ipVersion + "&switchbasicinfoId=" + switchbasicinfoId + "&start=0&limit=65535",
		disableCaching : true,
		method : 'GET',
		success : function(result, request) {
			var val=Ext.decode(result.responseText).interfaceList;
			
			var panel = Ext.getCmp('interfacePancel');
			var html = '<table width="100%"	style="padding:0px 0px 0px 10px">';
			
			var loop=0;
			for(var i = 0; i < val.length; i++ ){
				if(loop == 0){
					html += '<tr>';
				}
				
				loop++;
				
				var ifTrustStatus =  val[i].ifTrustStatus;
				var modeString = '';
				
				if(ifTrustStatus == '1') {
					if(val[i].ifValidationStatus=='1') modeString='validation-enable';
					else if(val[i].ifValidationStatus=='2')modeString='validation-disable';
				}
				else if(ifTrustStatus == '2') modeString = 'dhcp-trust';
				else if(ifTrustStatus == '3') modeString = 'ra-trust';
				else if(ifTrustStatus == '4') modeString = 'dhcp-ra-trust';
				else modeString = 'unkown';
				
				html += '<td width="14.3%" height="40px"><table>';
				
				if(modeString == 'unkown') html += 'UNKOWN';
				else {
					html += '<tr><td width="45px" height="10px"><span style="font-size:10pt;">' + val[i].ifIndex + '</span></td></tr>';//第一行显示端口号
					
					html += '<tr>';//第二行有两列，第二列再分成两行
					
					html += '<td width="35px" height="30px">' + //第一列
							'<img src="images/interface/' + modeString + '.jpg"' + 
							'style= "width:35px;height:30px;"' + 
							'</td>';
					
					html += '<td><table>';//第二列
					html += '<tr><td width="10px" height="19px">' //第二列，第一行
						 +'<img src="images/common/_blank.gif" style= "width:8px;height:8px;background:'
						 +'url(images/common/face.jpg) no-repeat scroll 0 0;">' 
						 + '</td></tr>';
					html += '<tr><td width="10px" height="19px"><span style="font-size:12px">' + //第二列，第二行
								val[i].userNumber + '</span></td></tr>';
					html += '</table></td>';
					
					html += '</tr>';
				}
				
				html += '</table></td>';
				
				if(loop > 6){
					loop = 0;
					html += '</tr>';
				}
			}
			
			for(var i = loop; i < 7 && loop > 0; i++){
				html += '<td width="14.3%" height="40px"></td>';
				if(i == 6) html +='</tr>';
			}
			
			html += '</table>';
			interfacetpl.overwrite(panel.body,{html:html});
		}
	});
	
**/
	
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
        		columnWidth: '.5',
        		style: 'padding: 10px 10px 0px 30px',
                items:[
                       {
                	xtype: 'portlet',
                	draggable: false,
                	items:[infoPanel]
                },
                {
                	xtype: 'portlet',
                	draggable: false,
                	items:[userCurvesChartPanel]
                }
                ]

            },{
        		xtype: 'portalcolumn',
        		columnWidth: '.5',
        		style: 'padding: 10px 30px 0px 0px',
                items:[
                       /***
                {
                	xtype: 'portlet',
                	draggable: false,
                	items:[interfacePanel]
                },**/{
                	xtype: 'portlet',
                	draggable: false,
                	items:[userChangePanel]
                },{
                	xtype: 'portlet',
                	draggable: false,
                	items:[bindingTablePanel]
                }
                ]
            }]
           }
        ]
    });
});