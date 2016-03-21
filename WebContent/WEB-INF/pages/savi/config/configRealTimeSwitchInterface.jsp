<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<title><s:text name='config.configRealTimeSwitchInterface.title'/></title>
	<link rel='StyleSheet' href="css/savitopoInit.css" type="text/css" />
	<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>
	<script type="text/javascript" src="js/config/configRealTimeSwitchInterface.js"></script>
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

		var ifIndexText =  "<s:text name='interface.ifIndex'/>";
		var ipVersionText =  "<s:text name='interface.ipVersion'/>";
		var ifValidationStatusText =  "<s:text name='interface.ifValidationStatus'/>";
		var ifTrustStatusText =  "<s:text name='interface.ifTrustStatus'/>";
		var ifFilteringNumText =  "<s:text name='interface.ifFilteringNum'/>";
		var bindingTableText =  "<s:text name='interface.bindingTable'/>";

		var interfaceOfSwitchText = "<s:text name='config.configRealTimeSwitchInterface.interfaceOfSwitch'/>";
		var submitButtonText = "<s:text name='config.configRealTimeSwitchInterface.button.submit'/>";
		var modifyText = "<s:text name='config.configRealTimeSwitchInterface.modify'/>";
		var interfaceInfoText = "<s:text name='config.configRealTimeSwitchInterface.interfaceInfo'/>";
		var batchModifyText = "<s:text name='config.configRealTimeSwitchInterface.batchModify'/>";
		var interfaceOfSwitchText = "<s:text name='config.configRealTimeSwitchInterface.interfaceOfSwitch'/>";
		
	</script>
	<script type="text/javascript" >
	
	function doModify(){
		var selected = sm.getSelections();
		if(selected.length < 1) return;
		else var data = selected[0].data;

		var validationStore=new Ext.data.SimpleStore({
			fields:['validationStatus','value'],
			data:[['Enable','1'],['Disable','2']]
		});

		var trustStore=new Ext.data.SimpleStore({
			fields:['trustStatus','value'],
			data:[['NO-TRUST','1'],['DHCP-TRUST','2'],['RA-TRUST','3'],['DHCP-RA-TRUST','4']]
		});
		
		var validationComboBox=new Ext.form.ComboBox({
			id: 'validationStatus',
			fieldLabel : ifValidationStatusText,
			triggerAction:'all',
			store:validationStore,
			displayField:'validationStatus',
			valueField:'value',
			mode:'local',
			forceSelection:true,
			editable: false,
			resizable:true,
			value: data.ifValidationStatus,
			handleHeight:10,
			width:230,
			listeners : {
				select : function(combo2, record, index) {
				validationComboBox.value = record.get("value");
				}
			}
		});	

		var trustComboBox=new Ext.form.ComboBox({
			id: 'trustStatus',
			fieldLabel : ifTrustStatusText,
			triggerAction:'all',
			store: trustStore,
			displayField:'trustStatus',
			valueField:'value',
			mode:'local',
			forceSelection:true,
			editable: false,
			resizable:true,
			value: data.ifTrustStatus,
			handleHeight:10,
			width:230,
			listeners : {
				select : function(combo2, record, index) {
					trustComboBox.value = record.get("value");
				}
			}
		});	
		
		var updateInterface = new Ext.FormPanel( {
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
						name : 'interfaceOld.ifFilteringNum',
						value: data.ifFilteringNum,
						hidden: true,	
						hideLabel: true
					},{
						name : 'interfaceOld.ifValidationStatus',
						value: data.ifValidationStatus,
						hidden: true,
						hideLabel: true
					}, {
						name : 'interfaceOld.ifTrustStatus',
						value: data.ifTrustStatus,
						hidden: true,	
						hideLabel: true
					},{
						fieldLabel : 'ifIndex',
						name : 'interfacecur.ifIndex',
						value: data.ifIndex,
						hidden: true,	
						hideLabel: true
					},{
						fieldLabel : 'IP Version',
						name : 'interfacecur.ipVersion',
						value: data.ipVersion,
						hidden: true,
						hideLabel: true
					}, {
						fieldLabel : ifFilteringNumText,
						name : 'interfacecur.ifFilteringNum',
						value: data.ifFilteringNum,
						blankText: emptyTipText,
						allowBlank:false
					}, validationComboBox,
					trustComboBox
			],
			buttons : [ {
						text : submitButtonText,
						handler : function() {
							if (updateInterface.getForm().isValid()){
								updateInterface.getForm().submit({
									url:"config/updateInterface.do?interfacecur.ifTrustStatus=" + trustComboBox.value + "&interfacecur.ifValidationStatus=" + validationComboBox.value + "&switchbasicinfoId=" + switchbasicinfoId,
									waitMsg: waitMsgText,
									success:function(form, action){
										updateInterfaceWin.close();
										Ext.Msg.alert(noticeTitleText, updateSuccessText);
										interfaceStore.reload();
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
		var updateInterfaceWin = new Ext.Window( {
			width : 440,
			height : 320,
			layout : 'fit',
			plain : true,
			frame : true,
			title : interfaceInfoText,
			bodyStyle : 'padding:5px 5px 0',
			buttonAlign : 'center',
			items : [updateInterface]
			});
		updateInterfaceWin.show();
	}
	</script>
</head>
<body>
	<div id="bodyDiv">
		<div id="infoDiv">
			<div id='showDiv'></div>
		</div>
	</div>
</body>
</html>