<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<title><s:text name='config.configSavi.title'/></title>
	<link rel='StyleSheet' href="css/savitopoInit.css" type="text/css" />
	<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>
	<script type="text/javascript" src="js/config/configSavi.js"></script>
	<script type="text/javascript">
		var pleaseSelectText = "<s:text name='combo.pleaseSelect'/>";
		var loadingText = "<s:text name='combo.loadingText'/>";
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

		var idText =  "<s:text name='switchcur.id'/>";
		var switchNameText =  "<s:text name='switchcur.switchName'/>";
		var ipVersionText =  "<s:text name='switchcur.ipVersion'/>";
		var systemModeText =  "<s:text name='switchcur.systemMode'/>";
		var maxDadDelayText =  "<s:text name='switchcur.maxDadDelay'/>";
		var maxDadPrepareDelayText =  "<s:text name='switchcur.maxDadPrepareDelay'/>";

		var submitButtonText = "<s:text name='config.configSavi.button.submit'/>";
		var saviInfoText = "<s:text name='config.configSavi.saviInfo'/>";
		var modifyText = "<s:text name='config.configSavi.modify'/>";
		var interfaceText = "<s:text name='config.configSavi.interface'/>";
		var bindingTableText = "<s:text name='config.configSavi.bindingTable'/>";
		var viewAllText = "<s:text name='config.configSavi.viewAll'/>";
		var saviSystemTableText = "<s:text name='config.configSavi.saviSystemTable'/>";
		var batchModifyText = "<s:text name='config.configSavi.batchModify'/>";
		var switchbasicinfoText = "<s:text name='config.configSavi.switchbasicinfo'/>";
	</script>
	<script type="text/javascript" >
	
	function doModify(){
		var selected = sm.getSelections();
		if(selected.length < 1) return;
		else var data = selected[0].data;

		var saviStore=new Ext.data.SimpleStore({
			fields:['systemMode','value'],
			data:[['DISABLE','1'],['DEFAULT','2'],['DHCP','3'],['SLAAC','4'],['DHCP-SLAAC-MIX','5']]
		});
		
		var saviComboBox=new Ext.form.ComboBox({
			id: 'systemMode',
			fieldLabel : systemModeText,
			triggerAction:'all',
			store:saviStore,
			displayField: 'systemMode',
			valueField:'value',
			mode:'local',
			forceSelection:true,
			resizable:true,
			editable: false,
			value: data.systemMode,
			handleHeight:10,
			width:230,
			listeners : {
				select : function(combo2, record, index) {
					saviComboBox.value = record.get("value");
				}
			}
		});	
		
		var updateSavi = new Ext.FormPanel( {
			labelWidth : 113,
			frame : true,
			title : '',
			bodyStyle : 'padding:5px 5px 0',
			width : 420,
			defaults : {
				width : 230
			},
			labelAlign: 'left',
			defaultType : 'textfield',
			items : [ 
					{
						fieldLabel : 'id',
						name : 'switchcur.id',
						value: data.id,
						hidden: true,	
						hideLabel: true
					},{
						fieldLabel : 'switchbasicinfoId',
						name : 'switchbasicinfoId',
						value: data.switchbasicinfoId,
						hidden: true,	
						hideLabel: true
					},{
						fieldLabel : 'IP Version',
						name : 'switchcur.ipVersion',
						value: data.ipVersion,
						hidden: true,	
						hideLabel: true
					}, {
						fieldLabel : maxDadDelayText,
						name : 'switchcur.maxDadDelay',
						value: data.maxDadDelay / 100,
						blankText: emptyTipText,
						allowBlank:false
					}, {
						fieldLabel : maxDadPrepareDelayText,
						name : 'switchcur.maxDadPrepareDelay',
						value: data.maxDadPrepareDelay / 100,
						blankText: emptyTipText,
						allowBlank:false
					}, saviComboBox
			],
			buttons : [ {
						text : submitButtonText,
						handler : function() {
							if (updateSavi.getForm().isValid()){
								updateSavi.getForm().submit({
									url:"config/updateSavi.do?switchcur.systemMode=" + saviComboBox.value,
									waitMsg: waitMsgText,
									success:function(form, action){
										updateSaviWin.close();
										Ext.Msg.alert(noticeTitleText, updateSuccessText);
										store.reload();
									},
									failure:function(from, action){
										var errMsg = Ext.decode(action.response.responseText).errMsg;
										Ext.Msg.alert(noticeTitleText, updateFailedText + errMsg);
									}
								});
							}
						}
			}]
		});
		var updateSaviWin = new Ext.Window( {
			width : 440,
			height : 320,
			layout : 'fit',
			plain : true,
			frame : true,
			title : saviInfoText,
			bodyStyle : 'padding:5px 5px 0',
			buttonAlign : 'center',
			items : [updateSavi]
			});
		updateSaviWin.show();
	}
	</script>
</head>
<body>
	<div id="bodyDiv" class="align-center">
		<div id="infoDiv">
			<div id='showDiv' ></div>
		</div>
	</div>
</body>
</html>