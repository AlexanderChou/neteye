Ext.namespace("savi");

var win = "1";
var sm = new Ext.grid.CheckboxSelectionModel();

Ext.onReady( function() {	
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='qtip';  
	
	var roleStore=new Ext.data.SimpleStore({
		fields:['description','role'],
		data:[[userRoleText,'1'],[adminRoleText,'2'],[superAdminRoleText,'3']]
	});
	
	var userListStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url :'user/listAdminList.do'
		}),
		//创建JsonReader读取interface记录
		reader : new Ext.data.JsonReader({
			root : 'adminList',
			totalProperty:'totalCount',
			fields : ['name','password','role']
			})
	});

	//用户列表
	var userChangePanel = new Ext.grid.GridPanel({
		store : userListStore,
		height : document.body.clientHeight - 37,
		width : document.body.clientWidth < 1024? 960:1220,
		renderTo : "showDiv",
		title : titleText,
		border: true,
		collapsible: false,
		sm : sm,
		autoExpandColumn : 'name',
		//列模型
		columns : [
		    new Ext.grid.RowNumberer({header:'', width:20}),			
		    sm,
			{id:'name', header: usernameText, width: 300, sortable: true, dataIndex: 'name'},
			{header: passwordText, width: 280, sortable: true, dataIndex: 'password'},
			{header: roleText, width: 280, sortable: false, dataIndex: 'role', renderer : renderRole}
			],
	    tbar : [ '->', {
			id : "addButton",
			text : addUserText,
			handler: addUser
		},'->','-', {
			id : "deleteButton",
			text : deleteUserText,
			handler : deleteUser
		},'->','-',{
			id : "updateButton",
			text : updateUserText,
			handler :updateUser
		} ,'->','-'],
		bbar:new Ext.PagingToolbar({
			pageSize:22,
			store:userListStore,
			displayInfo:true,
			displayMsg:displayMsgText,
			emptyMsg:noRecordText
		})
	});
	
	userListStore.load({
		params: {
			start:0,
			limit:22
		}
	});
	
	function renderRole(val){
		if(val == '1'){
			return userRoleText;
		}else if(val == '2'){
			return adminRoleText;
		}else if(val == '3'){
			return superAdminRoleText;
		}else
			return 'UNKOWN';
	};
	
	function addUser(){
		var combo2=new Ext.form.ComboBox({
			id: 'roleName',
			fieldLabel : userRoleText,
			triggerAction:'all',
			store:roleStore,
			displayField:'description',
			valueField:'role',
			mode:'local',
			forceSelection:true,
			resizable:true,
			value:1,
			handleHeight:10,
			width:230,
			listeners : {
				select : function(combo2, record, index) {
					combo2.value = record.get("role");
				}
			}
		});	
		var addUser = new Ext.FormPanel( {
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
			items : [ {
						fieldLabel : usernameText,
						name : 'name',
						blankText:emptyTipText,
						allowBlank:false
					},{
						id:'password',
						fieldLabel : passwordText,
						name : 'password',
						blankText:emptyTipText,
						allowBlank:false
					}, {
						id:'rePassword',
						fieldLabel : rePasswordText,
						name : 'rePassword',
						blankText:emptyTipText,
						allowBlank:false
					}, 
					combo2
			],
			buttons : [ {
						text : saveText,
						handler : function() {
							if (addUser.getForm().isValid()){
								addUser.getForm().submit({
									url:"user/add.do?role="+combo2.value,
									success:function(form, action){
										addUserWin.close();
										userListStore.reload();
									},
									failure:function(from, action){
										var errMsg = Ext.decode(action.response.responseText).errMsg;
										Ext.Msg.alert(noticeTitleText, saveFailedText + errMsg);
									}
								});
							}
						}
			}]
		});
		var addUserWin = new Ext.Window( {
			width : 440,
			height : 320,
			layout : 'fit',
			plain : true,
			frame : true,
			title : userInfoText,
			bodyStyle : 'padding:5px 5px 0',
			buttonAlign : 'center',
			items : [addUser]
			});
		addUserWin.show();
	}
	function updateUser(){
		var selected = sm.getSelections();
		if(selected.length < 1) return;
		var data = selected[0].data;
		
		var combo2=new Ext.form.ComboBox({
			id: 'roleName',
			fieldLabel : roleText,
			triggerAction:'all',
			store:roleStore,
			displayField:'description',
			valueField:'role',
			mode:'local',
			forceSelection:true,
			resizable:true,
			value:data.role,
			handleHeight:10,
			width:230,
			listeners : {
				select : function(combo2, record, index) {
					combo2.value = record.get("role");
				}
			}
		});	
		var updateUser = new Ext.FormPanel( {
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
			items : [ {
						fieldLabel : usernameText,
						name : 'name',
						value: data.name,
						blankText:emptyTipText,
						allowBlank:false
					},{
						id:'password',
						fieldLabel : passwordText,
						name : 'password',
						value: data.password,
						blankText:emptyTipText,
						allowBlank:false
					}, {
						id:'rePassword',
						fieldLabel : rePasswordText,
						name : 'rePassword',
						value: data.password,
						blankText: emptyTipText,
						allowBlank:false
					}, 
					combo2
			],
			buttons : [ {
						text : updateText,
						handler : function() {
							if (updateUser.getForm().isValid()){
								updateUser.getForm().submit({
									url:"user/update.do?role="+combo2.value,
									success:function(form, action){
										updateUserWin.close();
										userListStore.reload();
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
		
		var updateUserWin = new Ext.Window( {
			width : 440,
			height : 320,
			layout : 'fit',
			plain : true,
			frame : true,
			title : userInfoText,
			bodyStyle : 'padding:5px 5px 0',
			buttonAlign : 'center',
			items : [updateUser]
			});
		updateUserWin.show();
	}
	function deleteUser(){
		var names = "";
		var record = "";
		var selected = sm.getSelections();
		for ( var i = 0; i < selected.length; i++) {
			record = selected[i];
			var data = selected[i].data;
			names += data.name;
			
			if(i < selected.length - 1)
				names += "|";
		}
		if(names=="")return;
		Ext.MessageBox.confirm(noticeTitleText,deleteConfirmText,callBack);
		function callBack(id){
			if(id=='yes'){
				Ext.Ajax.request( {
					url : "user/delete.do",
					disableCaching : true,
					params : {
						names : names
					},
					success : function(response, request) {
						var success=Ext.decode(response.responseText).success;
						var errMsg=Ext.decode(response.responseText).errMsg;
						if (success == true) {
							for ( var i = 0; i < selected.length; i++) {
								record = selected[i];
								userListStore.remove(record);
							}
							Ext.MessageBox.alert(noticeTitleText,deleteSuccessText);
						} else {
							Ext.MessageBox.alert(noticeTitleText,deleteFailedText + errMsg);
						}
					}
				});
			}else{
				return;
			}
		}
	}
});