<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN "   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta HTTP-EQUIV="refresh" Content="300">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>路由表信息</title>
	 <link rel='StyleSheet' href="css/topoInit.css" type="text/css" />
    <link rel="stylesheet" type="text/css" href="css/ext-all.css"/>
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>
	<script type="text/javascript">
		var emptyTipText = "<s:text name='tip.empty'/>";
		var waitMsgText = "<s:text name='form.waitMsg'/>";
		var displayMsgText = "<s:text name='grid.displayMsg'/>";
		var noRecordText = "<s:text name='grid.noRecord'/>";
		var noticeTitleText = "<s:text name='notice.title'/>";
		var updateFailedText = "<s:text name='notice.update.failed'/>";
		var updateSuccessText = "<s:text name='notice.update.success'/>";
		var saveOrUpdateFailedText = "<s:text name='notice.saveOrUpdate.failed'/>";
		var saveOrUpdateSuccessText = "<s:text name='notice.saveOrUpdate.success'/>";
		var saveFailedText = "<s:text name='notice.save.failed'/>";
		var saveSuccessText = "<s:text name='notice.save.success'/>";
		var deleteSuccessText = "<s:text name='notice.delete.success'/>";
		var deleteFailedText = "<s:text name='notice.delete.failed'/>";
		var deleteConfirmText = "<s:text name='notice.delete.confirm'/>";
		var loadingText = "<s:text name='combo.loadingText'/>";
		var pleaseSelectText = "<s:text name='combo.pleaseSelect'/>";
		
		var idText = "<s:text name='switchbasicinfo.id'/>";
		var nameText = "<s:text name='switchbasicinfo.name'/>";
		var equipmentTypeText = "<s:text name='switchbasicinfo.equipmentType'/>";
		var ipv4addressText = "<s:text name='switchbasicinfo.ipv4address'/>";
		var ipv6addressText = "<s:text name='switchbasicinfo.ipv6address'/>";
		var subnetNameText = "<s:text name='switchbasicinfo.subnetName'/>";
		var statusText = "<s:text name='switchbasicinfo.status'/>";
		var snmpVersionText = "<s:text name='switchbasicinfo.snmpVersion'/>";
		var readCommunityText = "<s:text name='switchbasicinfo.readCommunity'/>";
		var writeCommunityText = "<s:text name='switchbasicinfo.writeCommunity'/>";
		var authKeyText = "<s:text name='switchbasicinfo.authKey'/>";
		var privateKeyText = "<s:text name='switchbasicinfo.privateKey'/>";
		var descriptionText = "<s:text name='switchbasicinfo.description'/>";

		var switchbasicinfoText = "<s:text name='config.configSwitch.switchbasicinfo'/>";
		var batchAddSwitchText = "<s:text name='config.configSwitch.batchAddSwitch'/>";
		var addOrUpdateSwitchText = "<s:text name='config.configSwitch.addOrUpdateSwitch'/>";
		var deleteSwitchText = "<s:text name='config.configSwitch.deleteSwitch'/>";
		var saviSystemTableText = "<s:text name='config.configSwitch.saviSystemTable'/>";
		var ipErrorText = "<s:text name='config.configSwitch.ipError'/>";
		var submitButtonText = "<s:text name='config.configSwitch.button.submit'/>";
		var switchInfoText = "<s:text name='config.configSwitch.switchInfo'/>";
		var subnetListText = "<s:text name='config.configSwitch.subnetList'/>";
		var ipv4StartText = "<s:text name='config.configSwitch.ipv4Start'/>";
		var ipv4EndText = "<s:text name='config.configSwitch.ipv4End'/>";
		var ipv6StartText = "<s:text name='config.configSwitch.ipv6Start'/>";
		var ipv6EndText = "<s:text name='config.configSwitch.ipv6End'/>";
		var ipv4ErrorText = "<s:text name='config.configSwitch.ipv4Error'/>";
		var ipv6ErrorText = "<s:text name='config.configSwitch.ipv6Error'/>";
		var batchAddSwitchScriptText="<s:text name='config.configSwitch.batchAddSwitchScript'/>";
		var loseSubnetInfoText="<s:text name='config.configSwitch.loseSubnetInfo'/>";
	</script>
	
	<STYLE type="text/css">
		#viewname {
		width:800px;
			text-align:left;
			float:left;
			
		}
		#valueList li {
			float:left;
		}
		#view1{
	width:100px;
	height:20px;
	padding-right:1px;
	margin:1px;
	text-align:left; 
	float:left;
	border:1px dashed #C9C4CA;
	overflow:hidden;
}
	div#girdshow {
 text-align:left;
 width:1050px;
 margin:0 auto;
 }
 	div#gridshow {
 text-align:left;
 width:1050px;
 margin:0 auto;
 }
	</STYLE>
	
</head>
<body>

<br>
<CENTER>

<br>
<br>
<div id="girdshow"></div>	
<br>
<br>
<div id="gridshow"></div>
<SCRIPT type="text/javascript">
	var rname='';
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
	
	var submitView="1";
	Ext.onReady(function(){
		var query = QueryString();
		rname = query.routername;
		var reader = new Ext.data.JsonReader( { 
		
		root : "v6routing",
		totalProperty : 'count',
		fields : ['dstprefix', 'outinterface', 'nexthop', 'flag', 'routername']
	});

	var proxy = new Ext.data.HttpProxy( {
		url : "json/ipv6routing.do?routername="+rname
	});
	
	 store = new Ext.data.Store( {
		proxy : proxy,
		reader : reader
	});
	
	store.load( {
		/**
		params: {
			submitView:submitView
		}
		**/
	});
	//store.setDefaultSort('name','ASC');   
	   	var bindingTableColm = new Ext.grid.ColumnModel( 
		[
			{header : "目的地址前缀",dataIndex : 'dstprefix',sortable : false,width:200	},
				 {header : "出接口",dataIndex : 'outinterface',sortable : false,width:200},
	             {header: "下一跳",dataIndex : 'nexthop',sortable : true,width:200},
	             {header: "flag",dataIndex : 'flag',sortable : true,width:200},
	             {header : "所属路由器",dataIndex : 'routername',sortable : false,width:200}
		]);
	   
		var grid = new Ext.grid.GridPanel( {
		title: '一维路由表',
		store : store,
		//height : document.body.clientHeight - 37,
		//height :1000,
		autoHeight: true,		
		width : 1000,
		cm :bindingTableColm,
		autoScroll : true,
		renderTo : 'girdshow'	
	});
		
		var bindingTableStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
				url :"json/ipv6tdrouting.do?routername="+rname
			}),
			//创建JsonReader读取interface记录
			reader : new Ext.data.JsonReader({
				root : 'v6tdrouting',
				totalProperty:'totalCount',
				fields : ['dstprefix', 'srcprefix', 'outinterface', 'nexthop', 'flag', 'routername']
				})
		});
		
		bindingTableStore.load({
			params: {
				start:0,
				limit:10
			}
		});
		
		var bindingTableColm = new Ext.grid.ColumnModel( 
			[
				{header : "目的地址前缀",dataIndex : 'dstprefix',sortable:false,width:150},
				{header : "目的地址前缀",dataIndex : 'srcprefix',sortable:false,width:150},
				{header : "出接口",dataIndex : 'outinterface',sortable:false,width:150},
	            {header: "下一跳",dataIndex : 'nexthop',sortable:true,width:150},
	            {header: "flag",dataIndex : 'flag',sortable:true,width:150},
	            {header : "所属路由器",dataIndex : 'routername',sortable:false,width:150}
			]);

		//二维路由面板
		var bindingTablePanel = new Ext.grid.GridPanel({
			store : bindingTableStore,
			height : 245,
			width : 904,
			title : '二维路由表',
			border: true,
			collapsible: true,
			collapseMode: 'mini',
			cm: bindingTableColm,
			tbar : [ 
				     
					'->','-','->',
					{
						id : "basic",
						text : "二维路由COST",
						handler: function(){
							document.location.href = 'tdroutingCost.do?routername='+rname;
						}
					}
			
				],
			autoWidth: true,
			renderTo : 'gridshow'
		});
		
		var panel = new Ext.Panel({
			title:"故障监控",
			width:840,
			items:[
			{
				width:840,
				height:document.body.clientHeight*0.95 - 20,
				autoScroll:true,
				contentEl:"eventListDiv"
			}]
		});	
});		
	</SCRIPT>	
</body>
</html>