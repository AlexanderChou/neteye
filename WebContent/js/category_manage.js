Ext.onReady(function() {
	var reader = new Ext.data.JsonReader({
				root : "categories",
				totalProperty : 'totalCount',
				fields : ['id', 'name', 'description']
			});
	var proxy = new Ext.data.HttpProxy({
				url : "listCategories.do"
			});

	var sm = new Ext.grid.CheckboxSelectionModel();
	categoryStore = new Ext.data.Store({
				proxy : proxy,
				reader : reader
			});
	categoryStore.setDefaultSort('name', 'DESC');
	categoryStore.load({
				params : {
					start : 0,
					limit : 28
				}
			});
	var categoryPanel = new Ext.grid.GridPanel({
		store : categoryStore,
		height : document.body.clientHeight * 0.95 + 5,
		width : 800,
		autoScroll : true,
		title : '分类管理',
		columns : [new Ext.grid.RowNumberer(), {
					id : "name",
					header : "类别名称",
					// width : 110,
					sortable : true,
					dataIndex : 'name'
				}, {
					header : "类别描述",
					width : 600,
					sortable : true,
					dataIndex : 'description'
				}, sm],
		sm : sm,
		stripeRows : true,
		frame : true,
		autoExpandColumn : 'name',
		tbar : ['->', {
			id : "addRecord",
			text : "增加",
			handler : function() {
				var catesimple = new Ext.FormPanel({
					labelWidth : 75,
					frame : true,
					title : '',
					bodyStyle : 'padding:5px 5px 0',
					width : 800,
					defaults : {
						width : 230
					},
					defaultType : 'textfield',
					items : [{
								fieldLabel : '类别名称',
								name : 'category.name',
								allowBlank : false,
								blankText : "不能为空！"
							}, {
								fieldLabel : '类别描述',
								xtype:'textarea',
								name : 'category.description'
							}],

					buttons : [{
								text : '  保存      ',
								handler : function() {
									if (catesimple.getForm().isValid()) {
										catesimple.getForm().submit({
													url : "addCategory.do",
													success : function(form,
															action) {
														myWin1.close();
														categoryStore.reload();
													},
													failure : function(form,
															action) {
														alert("该类别名已经存在，请重新添加！");
													}
												});
									}
								}
							}]
				});

				var myWin1 = new Ext.Window({
							width : 400,
							height : 255,
							layout : 'fit',
							plain : true,
							modal:true,
							frame : true,
							title : '类别信息',
							bodyStyle : 'padding:5px 5px 0',
							buttonAlign : 'center',
							items : [catesimple]
						});
				myWin1.show();
			}
		}, '->', '-', {
			id : "deleteButton",
			text : "删除",
			handler : function() {
				var ids = "";
				var record = "";
				var selected = sm.getSelections();
				if (selected.length == 0) {
					alert("请选择类别！");
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
								url : "deleteCategory.do",
								params : {
									categoryIds : ids
								},
								success : function(response, request) {
									if (response.responseText == "ok") {
										for (var i = 0; i < selected.length; i++) {
											record = selected[i];
											categoryStore.remove(record);
										}
										alert("删除成功！");
									} else {
										alert("删除失败! 你没有操作权限");
									}
								}
							});
				}
			}
		}],
		bbar : new Ext.PagingToolbar({
					pageSize : 28,
					store : categoryStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
					emptyMsg : '没有数据'
				})
	});

	categoryPanel.addListener('rowdblclick', rowClickFn);
	function rowClickFn(grid, rowIndex, e) {
		var s = categoryPanel.getStore();
		var categ = s.getAt(rowIndex);
		var categoryId = categ.get("id");
		var lineName = categ.get("name");
		var desc1 = categ.get("description")
		var catesimpledit = new Ext.FormPanel({
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
								fieldLabel : '类别名称',
								id:'newName',
								value : lineName,
								allowBlank : false,
								blankText : "不能为空！"
							}, {
								fieldLabel : '类别描述',
								xtype:'textarea',
								id:'newDesc',
								value : desc1
							}],

					buttons : [{
						text : '  保存      ',
						handler : function() {
							if (catesimpledit.getForm().isValid()) {
								catesimpledit.getForm().submit({
											url : "modifyCategory.do",
											params : {
												categoryId : categoryId,
												name : Ext.get('newName').getValue(),
												description : Ext.get('newDesc').getValue()
											},

											success : function(form, action) {
												myWin1.close();
												categoryStore.reload();
											},
											failure : function(form, action) {
												Ext.Msg.alert("",
														"该类别名已经存在，请重新添加！");
											}
										});
							}
						}
					}]
				});

		var myWin1 = new Ext.Window({
					width : 400,
					height : 255,
					layout : 'fit',
					plain : true,
					frame : true,
					modal:true,
					title : '类别信息',
					bodyStyle : 'padding:5px 5px 0',
					buttonAlign : 'center',
					items : [catesimpledit]
				});
		myWin1.show();
		
		}

	categoryPanel.render('showGrid');
		// categoryPanel.getSelectionModel().selectFirstRow();
});
