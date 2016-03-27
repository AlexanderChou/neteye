<%--
** Copyright (c) 2008, 2009, 2010
**      The Regents of the Tsinghua University, PRC.  All rights reserved.
**
** Redistribution and use in source and binary forms, with or without  modification, are permitted provided that the following conditions are met:
** 1. Redistributions of source code must retain the above copyright  notice, this list of conditions and the following disclaimer.
** 2. Redistributions in binary form must reproduce the above copyright  notice, this list of conditions and the following disclaimer in the  documentation and/or other materials provided with the distribution.
** 3. All advertising materials mentioning features or use of this software  must display the following acknowledgement:
**  This product includes software (iNetBoss) developed by Tsinghua University, PRC and its contributors.
** THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
** ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
** IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
** ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
** FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
** DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
** OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
** HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
** LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
** OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
** SUCH DAMAGE.
*
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN "   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta HTTP-EQUIV="refresh" Content="300">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>AP视图</title>
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
	</STYLE>
	
</head>
<body>

<br>
<CENTER>

<br>
<br>
	  <div id="girdshow"></div>	

	<SCRIPT type="text/javascript">
		Ext.onReady(function(){
	var reader = new Ext.data.JsonReader( {
		
		root : "aplist",
		totalProperty : 'count',
		fields : ['id','apid' ,'apname', 'ipv4Address', 'ipv6Address','usernum','status' ]
	});

	var proxy = new Ext.data.HttpProxy( {
		url : "json/paList.do"
	});
	
	 store = new Ext.data.Store( {
		proxy : proxy,
		reader : reader
	});
	
	store.load( {
	
	});
	   var bindingTableColm = new Ext.grid.ColumnModel( 
		[
		    {header : "id",dataIndex : 'id',sortable : false,width:50	},
		    {header : "apid",dataIndex : 'apid',hidden:true,sortable : false,width:50	},
			{header : "名称",dataIndex : 'apname',sortable : false,width:100,renderer: stuasnodes},
            {header: "ipv4地址",dataIndex : 'ipv4Address',sortable : true,width:150}, 
            {header : "ipv6地址",dataIndex : 'ipv6Address',sortable : false,width:150	},
            {header : "用户数",dataIndex : 'usernum',sortable : false,width:150	},
            {header : "状态",dataIndex : 'status',sortable : false}
		]);
	   
		var grid = new Ext.grid.GridPanel( {
		title: "AP列表",
		store : store,
		height : document.body.clientHeight - 37,
		width : 1000,
		//autoExpandColumn: 'apid',
		cm :bindingTableColm,
	   
	 tbar : [ 
		     
			'->','-','->',
			{
				id : "basic",
				text : "返回",
				handler: function(){
					document.location.href = 'HuaSanView.do';
				}
				}
	
		],
		autoScroll : true,
		renderTo : 'girdshow'
	
	});
		
		function addOrUpdateSubnet(){
			
			var snmpStore=new Ext.data.SimpleStore({
				fields:['version','value'],
				data:[['1','1'],['2c','2c'],['3','3']]
			});
			var subnetStore = new Ext.data.JsonStore({
				url: 'show/listShowSubnetsForCombo.do',
				root : 'subnetListForCombo',
				totalProperty:'totalCount',
				fields:['id', 'name']
			});
			var subnetComboBox = new Ext.form.ComboBox({
				id: 'subnet',
				loadingText: loadingText,
				fieldLabel: subnetListText,
				store: subnetStore,
				triggerAction: 'all',
				forceSelection:true,
				displayField: 'name',
				valueField:'id',
				pageSize: 10,
				editable: false,
				mode: 'remote',
				width:230,
			//	anchor:"90%",
				lazyInit: false,
				value: '',
				listeners : {
					select : function(combo2, record, index) {
						combo2.value = record.get("id");
					}
				}
			});
			
			var snmpStore=new Ext.data.SimpleStore({
				fields:['version','value'],
				data:[['1','1'],['2c','2c'],['3','3']]
			});
			var snmpComboBox=new Ext.form.ComboBox({
				id: 'deviceinfo.snmpVersion',
				fieldLabel : snmpVersionText,
				triggerAction:'all',
				store:snmpStore,
				displayField:'version',
				valueField:'value',
				mode:'local',
				forceSelection:true,
				editable: false,
				resizable:true,
				value:'',
				handleHeight:10,
			//	anchor:"90%",
				width:230
			});	
			var addSubnet = new Ext.FormPanel( {
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
								fieldLabel : '名称',
								name : 'deviceinfo.name',
								blankText:emptyTipText,
								allowBlank:false
							}, {
								name:"deviceinfo.equipmentType",
								fieldLabel : equipmentTypeText,
								allowBlank : true
							},snmpComboBox, {
								fieldLabel : 'ipv4',
								name : 'deviceinfo.ipv4address',
								blankText:emptyTipText,
								allowBlank:false
							}, {
								fieldLabel : 'ipv6',
								name : 'deviceinfo.ipv6address',
								blankText:emptyTipText,
								allowBlank:false
							}, {
								name:"deviceinfo.readCommunity",
								fieldLabel : readCommunityText,
								allowBlank : true
							}, {
								name:"deviceinfo.writeCommunity",
								fieldLabel : writeCommunityText,
								allowBlank : true
							}, {
								name:"deviceinfo.authKey",
								fieldLabel : authKeyText,
								allowBlank : true
							}, {
								name:"deviceinfo.privateKey",
								fieldLabel : privateKeyText,
								allowBlank : true
							}, 
							new Ext.form.TextArea({
								name:"deviceinfo.description",
								fieldLabel : descriptionText,
								allowBlank : true
							})
					],
				buttons : [ {
							text : '提交',
							handler : function() {
					            alert(5);
								addSubnet.getForm().submit({
									url:"config/saveDeviceinfo.do",
									waitMsg: waitMsgText,
									success:function(form, action){
										addSubnetWin.close();
										store.reload();
												Ext.Msg.show({   
					                                title:noticeTitleText,   
					                                msg:saveOrUpdateSuccessText,   
					                                icon:Ext.MessageBox.INFO,   
					                                buttons:Ext.MessageBox.OK   
		                                                            }); 
									},
									failure:function(from, action){
										var errMsg = Ext.decode(action.response.responseText).errMsg;
										Ext.Msg.alert(noticeTitleText, saveOrUpdateFailedText + errMsg);
									}
								});
							}
				}]
			});
			var addSubnetWin = new Ext.Window( {
				width : 440,
				height : 320,
				layout : 'fit',
				plain : true,
				frame : true,
				title : '添加AC',
				bodyStyle : 'padding:5px 5px 0',
				buttonAlign : 'center',
				items : [addSubnet]
				});
			addSubnetWin.show();
		}


		
		function stuasnodes(val){
		
		var routerstutas ="images/yellow_color.gif";
    	html= '<img src='+routerstutas+' width="50" height="50"><br>'+val;
		return html;
		
		};
		function listfaults(val){
	
		var html = '';
		var routerstutas ="images/yellow_color.gif";
		var GlobalViewList = val;
		for(var i = 0; i < GlobalViewList.length; i++ ){
        		
		html += ' <div id="view1">'+
		       		'<img src='+routerstutas+' width="20" height="20">'+
		            '<a>'+GlobalViewList[i].apname+'</a>'+
								        '</div>'
		}
		return html;
		
		}
     	grid.addListener('rowdblclick', rowClickFn);
     	
	    function rowClickFn(grid, rowIndex) {
		var ts = grid.getStore();
		var ttps = ts.getAt(rowIndex);
		var ttid = ttps.get("apid");
		var ttname = ttps.get("name");
		// document.location.href ="portListAll.do?deviceId="+ttid
		window.open('showPADetails.do?switchbasicinfoId='+ttid,"_blank");
		 
	}
		
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