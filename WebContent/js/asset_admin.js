Ext.onReady(function() {
	// 数据读取


			
	//数据		
				var departreader = new Ext.data.JsonReader({
				root : "assetDeparts",
				totalProperty : 'totalCount',
				fields : ['id','name','departcode','address']
			});
	var departproxy = new Ext.data.HttpProxy({
				url : "json/listAssetDepart.do"
			});

	var departsm = new Ext.grid.CheckboxSelectionModel();
	assetDepartStore = new Ext.data.Store({
				proxy : departproxy,
				reader : departreader
			});
	assetDepartStore.setDefaultSort('name', 'DESC');
	assetDepartStore.load({
				params : {
					start : 0,
					limit : 28
				}
			});
	var assetDepartPanel = new Ext.grid.GridPanel({
		store : assetDepartStore,
		height : document.body.clientHeight * 0.45 + 5,
		width : 800,
		autoScroll : true,
		title : '部门管理',
		columns : [new Ext.grid.RowNumberer(), {
					id : "sd1",
					header : "部门名称",
					// width : 110,
					sortable : true,
					dataIndex : 'name'
				
				}, {
					header : "部门编号",
					width : 200,
					sortable : true,
					dataIndex : 'departcode'
				
				},{
					header : "部门地址",
					width : 300,
					sortable : true,
					dataIndex : 'address'
					
				}, departsm],
		sm : departsm,
		stripeRows : true,
		frame : true,
		autoExpandColumn : 'sd1',
		tbar : ['->', {
			//id : "adddepartinfo",
			text : "增加部门信息",
			handler : function() {
				var addassetdepart = new Ext.FormPanel({
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
										fieldLabel : '部门名称',
										name : 'assetDepart.name',
										allowBlank : false,
										blankText : "不能为空！"
									}, {
										fieldLabel : '部门编号',
										
										name : 'assetDepart.departcode'
									}, {
										fieldLabel : '部门地址',
										
										name : 'assetDepart.address'
									}],

							buttons : [{
								text : '  保存      ',
								handler : function() {
									if (addassetdepart.getForm().isValid()) {
										addassetdepart.getForm().submit({
													url : "json/addAssetDepart.do",
													
													success : function(form,
															action) {
																Ext.Msg.alert("","成功!")
														addassetdepartWin.close();
														assetDepartStore.reload();
													},
													failure : function(form,
															action) {
														Ext.Msg.alert("","该名已经存在，请重新添加！");
													}
												});
									}
								}
							}]
						});

				var addassetdepartWin = new Ext.Window({
							width : 400,
							height : 255,
							layout : 'fit',
							plain : true,
							modal : true,
							frame : true,
							title : '部门信息',
							bodyStyle : 'padding:5px 5px 0',
							buttonAlign : 'center',
							items : [addassetdepart]
						});
				addassetdepartWin.show();
			}
		}, '->', '-', {
			id : "deleteButton",
			text : "删除",
			handler : function() {
				var ids = "";
				var record = "";
				var selected = departsm.getSelections();
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
								url : "json/deleteAssetDepart.do",
								params : {
									assetDepartIds : ids
								},
								success : function(response, request) {
									if (response.responseText == "ok") {
										for (var i = 0; i < selected.length; i++) {
											record = selected[i];
											assetDepartStore.remove(record);
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
					store : assetDepartStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
					emptyMsg : '没有数据'
				})
	});

	assetDepartPanel.addListener('rowdblclick', rowClickFn);
	function rowClickFn(grid, rowIndex) {
		var s = assetDepartPanel.getStore();
		var tps = s.getAt(rowIndex);
		var assetDepartId = tps.get("id");
		var pname = tps.get("name");
		var pcode =tps.get("departcode");
		var paddress = tps.get("address");
		var modifydepartform = new Ext.FormPanel({
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
										fieldLabel : '部门名称',
										name : 'name',
										id:'modifyname',
										value : pname,
										allowBlank : false,
										blankText : "不能为空！"
									}, {
										fieldLabel : '部门编号',
										id:'modifycode',
									name : 'departcode',
										value : pcode
									}, {
										fieldLabel : '部门地址',
										id:'modifyaddress',
										name : 'address',
										value : paddress
									}],

					buttons : [{
						text : '  保存      ',
						handler : function() {
							var modifyname=Ext.get('modifyname').dom.value;
							var modifycode=Ext.get('modifycode').dom.value;
							var modifyaddress=Ext.get('modifyaddress').dom.value;
							Ext.Ajax.request({
								url : "json/modifyAssetDepart.do",
								params : {
									assetDepartId :assetDepartId,
									address:modifyaddress,
									name:modifyname,
									departcode:modifycode
									
								},
								success : function(response, request) {
									if (response.responseText == "ok") {
											modifydepartWin.close();
												assetDepartStore.reload();
										
										Ext.Msg.alert("","成功！");
									} else {
										Ext.Msg.alert("","失败! 你没有操作权限");
									}
								}
							});
					
						
						
						}
					}]
				});

		var modifydepartWin = new Ext.Window({
					width : 400,
					height : 255,
					layout : 'fit',
					plain : true,
					frame : true,
					modal : true,
					title : '部门信息',
					bodyStyle : 'padding:5px 5px 0',
					buttonAlign : 'center',
					items : [modifydepartform]
				});
		modifydepartWin.show();
	}

	assetDepartPanel.render('showGridTest');


	
});
