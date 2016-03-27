Ext.onReady(function() {
	var eventmodels = [[1, 'SNMPTRAP'], [2, 'FAULT'], [3, 'FLOW']];
	var eventtypes = new Array();
	eventtypes[1] = [[11, 'cold_start'], [12, 'warm_start'], [13, 'link_down'],
			[14, 'link_up'], [15, 'authentication_failure'],
			[16, 'egp_neighbor_loss'], [17, 'enterprise_specific']];
	eventtypes[2] = [[21, 'ping_up'], [22, 'ping_down']];
	eventtypes[3] = [[31, 'normal_to_up'], [32, 'normal_to_down'],
			[33, 'upper_to_normal'], [34, 'lower_to_normal']];

	var Eventmodel = new Ext.form.ComboBox({

				store : new Ext.data.SimpleStore({
							fields : ["modelId", "modelName"],
							data : eventmodels
						}),

				listeners : {

					select : function(combo, record, index) {
						Eventtype.clearValue();
						Eventtype.store
								.loadData(eventtypes[record.data.modelId]);
					}
				},

				valueField : "modelName",
				displayField : "modelName",
				height : 20,
				mode : 'local',
				forceSelection : true,
				emptyText : "请选择事件模块",
				blankText : '事件模块',
				hiddenName : 'eventmodel',
				editable : false,
				triggerAction : 'all',
				allowBlank : true,
				fieldLabel : '事件模块',
				name : 'eventmodel',
				width : 130

			});

	var Eventtype = new Ext.form.ComboBox({
				store : new Ext.data.SimpleStore({
							fields : ["typeId", 'typeName'],
							data : []
						}),
				valueField : "typeName",
				displayField : "typeName",
				mode : 'local',
				emptyText : "请选择事件类型",
				height : 20,
				forceSelection : true,
				blankText : '事件类型',
				hiddenName : 'eventtype',
				editable : false,
				triggerAction : 'all',
				allowBlank : true,
				fieldLabel : '事件类型',
				name : 'eventtype',
				width : 130
			});
	var Filterip = new Ext.form.TextField({
				fieldLabel : '类别名称',
				id : 'ip',
				height : 20,
				emptyText : "请输入IP",
				allowBlank : false,
				blankText : "不能为空！"
			});
	var submitbutton = new Ext.Button({
				text : "提交",
				buttonAlign : "center",
				ip : 'submit',
				handler : function() {
					var ids = "";
					var eventmodels = "";
					var eventtypes = "";
					var ips = "";
					var record = "";
					var selected = sm.getSelections();
					if (selected.length == 0) {
						Ext.Msg.alert("", "请选择！");
						return;
					} else {
						for (var i = 0; i < selected.length; i++) {
							record = selected[i];
							var data = selected[i].data;
							ids += data.id;
							ids += ";";
							eventmodels += data.eventmodel;
							eventmodels += ";";
							eventtypes += data.eventtype;
							eventtypes += ";";
							ips += data.ip;
							ips += ";";

						}

						Ext.Ajax.request({
									url : "json/submitFilterConfig.do",
									params : {
										filterConfigIds : ids,
										filterConfigEventModels : eventmodels,
										filterConfigTypes : eventtypes,
										filterConfigIps : ips
									},
									success : function(response, request) {
										if (response.responseText == "ok") {
											Ext.Msg.alert("", "提交成功！");
										} else {
											Ext.Msg.alert("", "提交失败");
										}
									}
								});
					}
				}

			})
	var editform = new Ext.FormPanel({
				layout : "column",
				height : 18,
				items : [Eventmodel, Eventtype, Filterip]
			});

	var reader = new Ext.data.JsonReader({
				root : 'filterConfigs',
				fields : ['id', 'eventmodel', 'eventtype', 'ip']
			});
	var titleStr = '事件过滤';
	var eventStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'json/filterConfig.do'
						}),
				// 创建JsonReader读取router记录
				reader : reader
			});
	eventStore.load();
	var sm = new Ext.grid.CheckboxSelectionModel();

	var eventPanel = new Ext.grid.GridPanel({
		store : eventStore,
		autoScroll : true,
		height : document.body.clientHeight * 0.95 + 5,
		width : 840,
		title : titleStr,
		columns : [sm, {
					header : "事件id",
					hidden : true,
					sortable : true,
					dataIndex : 'id'
				}, {
					header : "事件模块",
					width : 200,
					sortable : true,
					dataIndex : 'eventmodel'
				}, {
					header : "事件类型",
					width : 300,
					sortable : true,
					dataIndex : 'eventtype'
				}, {
					header : "IP",
					width : 300,
					sortable : true,
					dataIndex : 'ip'
				}],
		sm : sm,
		tbar : [editform, '->', {
					text : "添加",
					handler : function() {

						if (editform.getForm().isValid()) {
							editform.getForm().submit({
										url : "json/addFilterConfig.do",

										success : function(form, action) {
											Ext.Msg.alert("", "成功!")
											eventStore.reload();
										},
										failure : function(form, action) {
											Ext.Msg.alert("", "该名已经存在，请重新添加！");
										}
									});
						}

					}

				},'|',{
					id : "deleteButton",
					text : "删除",
					handler : function() {
						var ids = "";
						var record = "";
						var selected = eventPanel.getSelections();
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
										url : "json/deleteFilterConfig.do",
										params : {
											FilterConfigids : ids
										},
										success : function(response, request) {
											if (response.responseText == "ok") {
												eventStore.reload();
												Ext.Msg.alert("","删除成功！");
											} else {
												Ext.Msg.alert("","删除失败! 你没有操作权限");
											}
										}
									});
						}
					}
				}],
			
		buttons : [submitbutton],
		buttonAlign : 'center'

	});

	eventPanel.render('showDiv');
});