Ext.onReady(function() {
	// 数据读取
	var reader = new Ext.data.JsonReader({
				root : "priorities",
				totalProperty : 'totalCount',
				fields : ['id', 'name', 'color']
			});
	var proxy = new Ext.data.HttpProxy({
				url : "listPriorities.do"
			});
	// 建立表格
	var sm = new Ext.grid.CheckboxSelectionModel();
	priorityStore = new Ext.data.Store({
				proxy : proxy,
				reader : reader
			});
	priorityStore.setDefaultSort('name', 'DESC');
	priorityStore.load({
				params : {
					start : 0,
					limit : 28
				}
			});
	var priorityPanel = new Ext.grid.GridPanel({
		store : priorityStore,
		height : document.body.clientHeight * 0.95 + 5,
		width : 800,
		autoScroll : true,
		title : '优先级管理',
		columns : [new Ext.grid.RowNumberer(), {
					id : "name",
					header : "级别",
//					width : 110,
					sortable : true,
					dataIndex : 'name'
				}, {
					header : "含义",
					width : 600,
					sortable : true,
					dataIndex : 'color'
				}, sm],
		sm : sm,
		stripeRows : true,
		frame : true,
		autoExpandColumn : 'name',
		tbar : ['->', {
			id : "addRecord",
			text : "增加级别",
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
								fieldLabel : '优先级',
								name : 'priority.name',
								allowBlank : false,
								blankText : "不能为空！"
							}, {
								fieldLabel : '含义',
								xtype:'textarea',
								name : 'priority.color'
							}],

					buttons : [{
								text : '  保存      ',
								handler : function() {
									if (simple.getForm().isValid()) {
										simple.getForm().submit({
													url : "addPriority.do",
													success : function(form,
															action) {
														myWin.close();
														priorityStore.reload();
													},
													failure : function(form,
															action) {
														Ext.Msg.alert("","该级别名已经存在，请重新添加！");
													}
												});
									}
								}
							}]
				});
				// 添加项目弹出窗口
				var myWin = new Ext.Window({
							width : 400,
							height : 255,
							layout : 'fit',
							modal:true,
							plain : true,
							frame : true,
							title : '添加优先级级别',
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
					alert("请选择！");
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
								url : "deletePriority.do",
								params : {
									priorityIds : ids
								},
								success : function(response, request) {
									if (response.responseText == "ok") {
										for (var i = 0; i < selected.length; i++) {
											record = selected[i];
											priorityStore.remove(record);
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
					store : priorityStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
					emptyMsg : '没有数据'
				})
	});
	// 双击编辑窗口。
	priorityPanel.addListener('rowdblclick', rowClickFn);
	function rowClickFn(grid, rowIndex, e) {
		var s = priorityPanel.getStore();
		var prio = s.getAt(rowIndex);
		var priorityId = prio.get("id");
		var co1 = prio.get("name");
		var co2 = prio.get("color")
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
								fieldLabel : '优先级',
								id : 'name1',
								value : co1,
								allowBlank : false,
								blankText : "不能为空！"
							}, {
								fieldLabel : '含义',
								xtype:'textarea',
								value : co2,
								id : 'color1'
							}],

					buttons : [{
						text : '  保存      ',
						handler : function() {
                                          var v1=Ext.get('name1').dom.value;
                                          var v2=Ext.get('color1').dom.value;
							Ext.Ajax.request({
								url : "modifyPriority.do",
								params : {
									priorityId : priorityId,
									name: v1,
		                          	color:v2

					
								},
								success : function(response, request) {
									if (response.responseText == "ok") {
										Ext.Msg.alert("","成功！");
										priorityStore.reload();
										myWin1.close();
									} else {
										Ext.Msg.alert("","失败!");
									}
								}
							});
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
					title : '优先级管理',
					bodyStyle : 'padding:5px 5px 0',
					buttonAlign : 'center',
					items : [simpledit]
				});
		myWin1.show();
	}

	priorityPanel.render('showGrid');
		// projectPanel.getSelectionModel().selectFirstRow();
});
