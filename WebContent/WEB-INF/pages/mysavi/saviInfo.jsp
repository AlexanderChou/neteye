<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN "   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta HTTP-EQUIV="refresh" Content="300">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>路由器详细信息</title>
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
		width:1000px;
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
	</STYLE>
	
</head>
<body>

<br>
<CENTER>

<br>
<br>
	<div id="girdshow"></div>	

	<SCRIPT type="text/javascript">
	
	
	var submitView="1";
	Ext.onReady(function(){
		var reader = new Ext.data.JsonReader({ 
		
		root : "saviInfoList",
		totalProperty : 'count',
		fields : ['id', 'saviManual', 'saviDhcpv4', 'saviDhcpv6','saviSend','saviFcfs']
		});

		var proxy = new Ext.data.HttpProxy( {
			url : "json/saviInfo.do"
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
			{header : "id",dataIndex : 'id',sortable : false,width:80},
			 {header : "交换机名称",sortable : false,width:120},
             {header: "savi-manual",dataIndex : 'saviManual',sortable : true,width:120}, 
             {header: "savi-dhcpv4",dataIndex : 'saviDhcpv4',sortable : true,width:120}, 
             {header : "savi-dhcpv6",dataIndex : 'saviDhcpv6',sortable : false,width:120},
             {header : "savi-fcfs",dataIndex : 'saviFcfs',sortable : false,width:120, renderer : renderSaviFcfs},
             {header : "savi-send",dataIndex : 'saviSend',sortable : false,width:120, renderer : renderSaviSend},
             {header : "端口列表",dataIndex : 'id',sortable : false,width:130, renderer : renderPortList},
             {header : "绑定表",dataIndex : 'id',sortable : false,width:130, renderer : renderBindingTable}
		]);
	   
		var grid = new Ext.grid.GridPanel( {
		title: '路由器详细信息',
		store : store,
		//height : document.body.clientHeight - 37,
		//height :1000,
		autoHeight: true,
		
		width : 1080,
		cm :bindingTableColm,
		tbar : ['->','-','->', {
			id : "switch_basic_information",
			text : "savi交换机列表",
			handler: function(){
				document.location.href = 'mySaviTotalView.do';
			}
		}],
		autoScroll : true,
		renderTo : 'girdshow'
	
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
		
		function renderSaviFcfs(val)
		{
			if (val == true)
			{
				html = '<a href="saviFcfs.do?id='+val+'">true</a>';
				return html;
			}
			return val;
		};
		
		function renderSaviSend(val)
		{
			if (val == true)
			{
				html = '<a href="saviSend.do?id='+val+'">true</a>';
				return html;
			}
			return val;
		};
		
		function renderPortList(val)
		{
			html = '<a href="switchPortList.do?id='+val+'">click</a>';
			console.log(html);
			return html;
		};
		
		function renderBindingTable(val)
		{
			html = '<a href="bindingTable.do?id='+val+'">click</a>';
			console.log(html);
			return html;
		};
	});
	
	</SCRIPT>
</body>
</html>