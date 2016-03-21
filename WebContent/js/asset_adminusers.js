Ext.onReady(function() {
	// 数据读取


			
	//数据		



	var assetUserreader = new Ext.data.JsonReader({
				root : "assetUsers",
				totalProperty : 'totalCount',
				fields : ['id', 'userName', 'userEmail', 'userMobile', 'userTel', 'userDepart']
			});
	var assetUserproxy = new Ext.data.HttpProxy({
				url : "json/listAssetUser.do"
			});

	// 建立表格
	var assetUsersm = new Ext.grid.CheckboxSelectionModel();
	assetUserStore = new Ext.data.Store({
				proxy : assetUserproxy,
				reader : assetUserreader
			});
	assetUserStore.setDefaultSort('userName', 'DESC');
	assetUserStore.load({
				params : {
					start : 0,
					limit : 28
				}
			});
	var assetUserPanel = new Ext.grid.GridPanel({
		store : assetUserStore,
		height : document.body.clientHeight * 0.45 + 5,
		width : 800,
		autoScroll : true,
		title : '维护管理员管理',
		columns : [new Ext.grid.RowNumberer(), {
					id : "userName",
					header : "姓名",
					// width : 110,
					sortable : true,
					dataIndex : 'userName'
				}, {
					header : "所在部门",
					//width : 600,
					sortable : true,
					dataIndex : 'userDepart'
				},{
					
					header : "联系电话",
					// width : 110,
					sortable : true,
					dataIndex : 'userTel'
				},{
					
					header : "手机",
					// width : 110,
					sortable : true,
					dataIndex : 'userMobile'
				},{
					
					header : "Email",
					// width : 110,
					sortable : true,
					dataIndex : 'userEmail'
				}, assetUsersm],
		sm : assetUsersm,
		stripeRows : true,
		frame : true,
		autoExpandColumn : 'userName',
		tbar : ['->', {
		//	id : "addRecord",
			text : "增加管理员",
			handler : function() {
				var addassetUserform = new Ext.FormPanel({
							labelWidth : 75,
							frame : true,
							title : '',
							bodyStyle : 'padding:5px 5px 0',
							width : 400,
							defaults : {
								width : 230
							},
							defaultType : 'textfield',
							items : [{
										fieldLabel : '姓名',
										name : 'assetUser.userName',
										allowBlank : false,
										blankText : "不能为空！"
									}, {
										fieldLabel : '所在部门',
								     	name : 'assetUser.userDepart'
									}, {
										fieldLabel : '联系电话',
								     	name : 'assetUser.userTel'
									}, {
										fieldLabel : '手机',
								     	name : 'assetUser.userMobile'
									}, {
										fieldLabel : 'Email',
								     	name : 'assetUser.userEmail'
									}],

							buttons : [{
								text : '  保存      ',
								handler : function() {
									if (addassetUserform.getForm().isValid()) {
										addassetUserform.getForm().submit({
											url : "json/addAssetUser.do",
											success : function(form, action) {
												addassetUserWin.close();
												assetUserStore.reload();
											},
											failure : function(form, action) {
												Ext.Msg.alert("",
														"该级别名已经存在，请重新添加！");
											}
										});
									}
								}
							}]
						});
				// 添加弹出窗口
				var addassetUserWin = new Ext.Window({
							width : 400,
							height : 255,
							layout : 'fit',
							modal : true,
							plain : true,
							frame : true,
							title : '增加管理员',
							bodyStyle : 'padding:5px 5px 0',
							buttonAlign : 'center',
							items : [addassetUserform]
						});
				addassetUserWin.show();
			}
		}, '->', '-', {
			id : "deleteButton",
			text : "删除",
			handler : function() {
				var ids = "";
				var record = "";
				var selected = assetUsersm.getSelections();
				if (selected.length == 0) {
					Ext.Msg.alert("","请选择！");
					return;
				}
				var isDelete = confirm("确定要删除吗？");
				if (isDelete) {
					for (var i = 0; i < selected.length; i++) {
						record = selected[i];
						var data = selected[i].data;
						ids += data.id;
						ids += ";";
					}

					Ext.Ajax.request({
								url : "json/deleteAssetUser.do",
								params : {
									assetUserIds : ids
								},
								success : function(response, request) {
									if (response.responseText == "ok") {
										for (var i = 0; i < selected.length; i++) {
											record = selected[i];
											assetUserStore.remove(record);
										}
										Ext.Msg.alert("","删除成功！");
									} else {
										Ext.Msg.alert("","删除失败! 你没有操作权限");
									}
								}
							});
				}
			}
		}],
		bbar : new Ext.PagingToolbar({
					pageSize : 28,
					store : assetUserStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
					emptyMsg : '没有数据'
				})
	});
	// 双击编辑窗口。
	assetUserPanel.addListener('rowdblclick', rowClickFn);
	function rowClickFn(grid, rowIndex) {
		var ts = assetUserPanel.getStore();
		var ttps = ts.getAt(rowIndex);
		var assetUserId = ttps.get("id");
		var tcouserName = ttps.get("userName");
		var tuserDepart = ttps.get("userDepart");
		var tuserTel = ttps.get("userTel");
		var tuserMobile = ttps.get("userMobile");
		var tuserEmail = ttps.get("userEmail");
		
		var modifyassetUserform = new Ext.FormPanel({
					labelWidth : 75,
					frame : true,
					title : '',
					bodyStyle : 'padding:5px 5px 0',
					width : 400,
					defaults : {
						width : 230
					},
					defaultType : 'textfield',
					items : [{
										fieldLabel : '姓名',
										id:'modifyuserName',
										//name : 'assetUser.userName',
										allowBlank : false,
										value:tcouserName,
										blankText : "不能为空！"
										
									}, {
										fieldLabel : '所在部门',
										value:tuserDepart,
										id:'modifyuserdepart'
								     //	name : 'assetUser.userDepart'
									}, {
										fieldLabel : '联系电话',
										value:tuserTel,
										id:'modifyusertel'
								     //	name : 'assetUser.userTel'
									}, {
										fieldLabel : '手机',
										value:tuserMobile,
										id:'modifyusermobile'
								     //	name : 'assetUser.userMobile'
									}, {
										fieldLabel : 'Email',
										value:tuserEmail,
										id:'modifyuseremail'
								     //	name : 'assetUser.userEmail'
									}],

					buttons : [{
								text : '  保存      ',
								handler : function() {
									var puserName = Ext.get('modifyuserName').dom.value;
									var puserDepart = Ext.get('modifyuserdepart').dom.value;
									var puserTel = Ext.get('modifyusertel').dom.value;
									var puserMobile = Ext.get('modifyusermobile').dom.value;
									var puserEmail = Ext.get('modifyuseremail').dom.value;
									
									
									Ext.Ajax.request({
												url : "json/modifyAssetUser.do",
												params : {
													assetUserId : assetUserId,
													userName :puserName,
													userDepart:puserDepart,
													userTel:puserTel,
													userMobile:puserMobile,
													userEmail:puserEmail
              
												},
												success : function(response,
														request) {
													if (response.responseText == "ok") {
														Ext.Msg
																.alert("",
																		"成功！");
														assetUserStore
																.reload();
														modifyassetUserWin.close();
													} else {
														Ext.Msg
																.alert("",
																		"失败!");
													}
												}
											});
								}
							}]
				});

		var modifyassetUserWin = new Ext.Window({
					width : 400,
					height : 255,
					layout : 'fit',
					plain : true,
					frame : true,
					modal : true,
					title : '维护管理员管理',
					bodyStyle : 'padding:5px 5px 0',
					buttonAlign : 'center',
					items : [modifyassetUserform]
				});
		modifyassetUserWin.show();
	}

	assetUserPanel.render('showGrid');
		
});
