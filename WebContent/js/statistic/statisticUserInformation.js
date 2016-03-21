Ext.namespace("savi");
Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'side';

Ext.onReady( function() {	
	var userInfoStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'statistic/listUserInfo.do'
		}),
		reader : new Ext.data.JsonReader({
			root : 'userInfoList',
			totalProperty:'totalCount',
			fields : ['userName','userMAC','userIP','ifIndex','switchName','switchIPv4', 'switchIPv6', 'startTime','endTime','bindingType']
			})
	});
	
	var userInfoTableColm = new Ext.grid.ColumnModel( 
		[
			{header: userNameText, width:80, sortable: false, dataIndex: 'userName'},
			{header: userMACText, width: 110, sortable: false, dataIndex: 'userMAC'},
			{id:"userIP", header: userIPText, width: 222,sortable: false,dataIndex: 'userIP'},
			{header: ifIndexText, width: 60,sortable: true,dataIndex: 'ifIndex'},
			{header: switchNameText, width:90,sortable: false,dataIndex : 'switchName'},
			{header: switchIPv4Text, width:110,sortable: false,dataIndex : 'switchIPv4'},
			{header: switchIPv6Text, width:215,sortable: false,dataIndex : 'switchIPv6'},
			{header: startTimeText, width:115,sortable: true,dataIndex : 'startTime'},
			{id:'endTime',header: endTimeText, width:115,sortable: true,dataIndex : 'endTime'},
			{header: bBindingTypeText, width:78,sortable: false,dataIndex : 'bindingType', renderer:renderBindingType}
		]);

	//userInfo面板
	var userInfoPanel = new Ext.grid.GridPanel({
		store : userInfoStore,
		height : document.body.clientHeight - 37,
		width : document.body.clientWidth < 1024? 960:1220,
		renderTo : "showDiv",
		title : userInfoText,
		border: true,
		collapsible: false,
		//autoExpandColumn : 'ipAddress',
		//列模型
		cm: userInfoTableColm,
		bbar:new Ext.PagingToolbar({
			pageSize:50,
			store:userInfoStore,
			displayInfo:true,
			displayMsg:displayMsgText,
			emptyMsg:noRecordText
		}),
		tbar : [ '->','-','->',
		    new Ext.form.Checkbox({
		    	 id:'isOnline',
				 name:'isOnline',
				 boxLabel:userOnlineText
			}),'->','-','->',
		    new Ext.form.Checkbox({
		    	 id:'isNull',
				 name:'isNull',
				 boxLabel:userNameIsNotNullText
			}),'->','-','->',
		    new Ext.form.Label({
		    	text:userNameText
		    }),
		    new Ext.form.TextField({
		    	id:'userName',
		    	fieldLable:userNameText,
		    	allowBlank:true
		    }),'->','-','->', 
		    new Ext.form.Label({
		    	text:userMACText
		    }),
		    new Ext.form.TextField({
		    	id:'userMAC',
		    	fieldLable:userMACText,
		    	allowBlank:true
		    }),'->','-','->', 
		    new Ext.form.Label({
		    	text:userIPText
		    }),
		    new Ext.form.TextField({
		    	id:'userIP',
		    	fieldLable:userIPText,
		    	allowBlank:true
		    }),'->','-',
		    new Ext.form.Label({
		    	text:startTimeText
		    }),
		    new Ext.form.DateField({
		    	id:'startTime',
		    	fieldLable:startTimeText,
		    	format:'Y-m-d'
		    }),'->','-',
		    new Ext.form.Label({
		    	text:endTimeText
		    }),
		    new Ext.form.DateField({
		    	id:'endTime',
		    	fieldLable:endTimeText,
		    	format:'Y-m-d'
		    }),'->','-',
		    new Ext.Button({
				text:buttonText,
				handler:function(){
					var userName = Ext.get("userName").getValue();
					var userMAC = Ext.get("userMAC").getValue();
					var userIP = Ext.get("userIP").getValue();
					var startTime=Ext.get("startTime").getValue();
					var endTime=Ext.get("endTime").getValue();
					var isOnline=Ext.getCmp("isOnline").getValue();
					var isNull=Ext.getCmp("isNull").getValue();
					var act='statistic/getUserInfo.do?'+"userName="+userName+"&userMAC="+userMAC+"&userIP="+userIP+"&startTime="+startTime+"&endTime="+endTime+"&isOnline="+isOnline+"&isNull="+isNull;
					act=encodeURI(act); 
					act=encodeURI(act); //写一个不行。如果写一个就是？？？？号。
					userInfoStore.proxy = new Ext.data.HttpProxy({
						url :act
						//method:"post",
						//params:{userName:userName, userMAC:userMAC,userIP:userIP}
					});
					userInfoStore.reload({
						params: {
							start:0,
							limit:50
						}
					});
				}
		    })
		    
		]
	});
	
	userInfoStore.load({
		params: {
			start:0,
			limit:50
		}
	});
	
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
});