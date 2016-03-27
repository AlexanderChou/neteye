Ext.onReady(function() {
	var reader = new Ext.data.JsonReader({
				root : "projects",
				totalProperty : 'totalCount',
				fields : ['id', 'name', 'description']
			});
	var proxy = new Ext.data.HttpProxy({
				url : "listProjects.do"
			});

	var sm = new Ext.grid.CheckboxSelectionModel();
	projectStore = new Ext.data.Store({
				proxy : proxy,
				reader : reader
			});
	projectStore.setDefaultSort('name', 'DESC');
	projectStore.load({
				params : {
					start : 0,
					limit : 28
				}
			});
	var projectPanel = new Ext.grid.GridPanel({
		store : projectStore,
		height : document.body.clientHeight * 0.95 + 5,
		width : 800,
		autoScroll : true,
		title : '项目管理',
		columns : [new Ext.grid.RowNumberer(), {
					id : "name",
					header : "名称",
					//width : 110,
					sortable : true,
					dataIndex : 'name'
				}, {
					header : "描述",
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
			text : "增加项目",
			handler : function() {
				var simple = new Ext.FormPanel({
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
								fieldLabel : '项目名称',
								name : 'project.name',
								allowBlank : false,
								blankText : "不能为空！"
							}, {
								fieldLabel : '描述',
								xtype:'textarea',
								name : 'project.description'
							}],

					buttons : [{
								text : '  保存      ',
								handler : function() {
									if (simple.getForm().isValid()) {
										simple.getForm().submit({
													url : "addProject.do",
													success : function(form,
															action) {
														myWin.close();
														projectStore.reload();
													},
													failure : function(form,
															action) {
														alert("该项目名已经存在，请重新添加！");
													}
												});
									}
								}
							}]
				});

				var myWin = new Ext.Window({
							width : 400,
							height : 255,
							layout : 'fit',
							plain : true,
							modal:true,
							frame : true,
							title : '项目信息',
							bodyStyle : 'padding:5px 5px 0',
							buttonAlign : 'center',
							items : [simple]
						});
				myWin.show();
			}
		}, '->', '-', {
			id : "deleteButton",
			text : "删除",
			handler : function() {
				var ids = "";
				var record = "";
				var selected = sm.getSelections();
				if (selected.length == 0) {
					alert("请选择项目！");
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
								url : "deleteProject.do",
								params : {
									projectIds : ids
								},
								success : function(response, request) {
									if (response.responseText == "ok") {
										for (var i = 0; i < selected.length; i++) {
											record = selected[i];
											projectStore.remove(record);
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
					store : projectStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
					emptyMsg : '没有数据'
				})
	});

	projectPanel.addListener('rowdblclick', rowClickFn);
	function rowClickFn(grid, rowIndex, e) {
		var s = projectPanel.getStore();
		var proj = s.getAt(rowIndex);
		var projectId = proj.get("id");
		var lineName= proj.get("name");
		var desc1=proj.get("description")
		var simpledit = new Ext.FormPanel({
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
								fieldLabel : '项目名称',
								name : 'name',
								value: lineName,
								allowBlank : false,
								blankText : "不能为空！"
							}, {
								fieldLabel : '描述',
								xtype:'textarea',
								value: desc1,
								name : 'description'
							}],

					buttons : [{
								text : '  保存      ',
								handler : function() {
									if (simpledit.getForm().isValid()) {
										simpledit.getForm().submit({
													url : "modifyProject.do",
													params : {
														projectId : projectId
													},

													success : function(form,
															action) {
														myWin1.close();
														projectStore.reload();
													},
													failure : function(form,
															action) {
														Ext.Msg.alert("","该项目名已经存在，请重新添加！");
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
					modal : true,
					title : '项目信息',
					bodyStyle : 'padding:5px 5px 0',
					buttonAlign : 'center',
					items : [simpledit]
				});
		myWin1.show();
	}

	projectPanel.render('showGrid');
		// projectPanel.getSelectionModel().selectFirstRow();
});
