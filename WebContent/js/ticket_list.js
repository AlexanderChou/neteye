Ext.onReady(function() {

	Ext.QuickTips.init();

	Ext.form.Field.prototype.msgTarget = 'side';



   //附件列表
	var attachStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							autoLoad : false,
							url : 'attachList.do'
						}),
				// 创建JsonReader读取router记录
				reader : new Ext.data.JsonReader({
							root : 'attachList',
							fields : ['fileName', 'id']
						})
			});

	

	function linker(val) {

		// if(typeof val=='object'){

		return '<a href=downloadAttach.do?id=' + val + '>下载</a>'

		// }

		// return val;

	};
	
	var fileupPanel = new Ext.grid.GridPanel({
		store : attachStore,
		collapsed : true,
		collapsible : true,
		height : 200,
		width : 300,
		autoScroll : true,
		title : '附件',
		listeners:{
		'beforeexpand':function(){
			var ticketId = gridForm.findById("ticketId").getValue();
		    attachStore.proxy.conn.url='attachList.do?ticketId='+ticketId;
			attachStore.load();
		    
		}
		
		},
		columns : [{
					id : "sd1",
					header : "文件名",
					 width : 150,
					sortable : true,
					dataIndex : 'fileName'

				}, {
					header : "链接地址",
					width : 100,
					sortable : true,
					dataIndex : 'id',
					renderer : linker
                   
				}],
		
		stripeRows : true,
		frame : true,
		autoExpandColumn : 'sd1'
		
	});

	var underTakerStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							autoLoad : true,
							url : 'underTakerList.do'
						}),
				// 创建JsonReader读取router记录
				reader : new Ext.data.JsonReader({
							root : 'underTakerList',
							fields : ['id', 'name']
						})
			});

	underTakerStore.load();

	var projectStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'projectList.do'
						}),
				// 创建JsonReader读取router记录
				reader : new Ext.data.JsonReader({
							root : 'projectList',
							fields : ['id', 'name']
						})
			});
	projectStore.load();

	var priorityStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'priorityList.do'
						}),
				// 创建JsonReader读取router记录
				reader : new Ext.data.JsonReader({
							root : 'priorityList',
							fields : ['id', 'name']
						})
			});
	priorityStore.load();

	var categoryStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'categoryList.do'
						}),
				// 创建JsonReader读取router记录
				reader : new Ext.data.JsonReader({
							root : 'categoryList',
							fields : ['id', 'name']
						})
			});
	categoryStore.load();

	var descText = new Ext.form.TextArea({

				xtype : 'textarea',
				fieldLabel : '处理事件记录',

				width : 500,
				height : 300,
				name : 'content',
				grow : false,
				preventScrollbars : true

			});
	var descText1 = new Ext.form.TextArea({

				xtype : 'textarea',
				fieldLabel : '处理事件记录',

				width : 500,
				height : 300,
				name : 'content',
				grow : false,
				preventScrollbars : true

			});

	var editorContent = new Ext.form.TextArea({

				xtype : 'textarea',
				fieldLabel : '内容',
				value : '',
				name : 'content',
				grow : false,
				preventScrollbars : true,
				width : 400,
				height : 100

			});

	var projectCombox = new Ext.form.ComboBox({
				value : '选择项目名称',
				width : 200,
				// triggerAction : 'all',

				forceSelection : true,
				editable : false,
				mode : 'remote',
				fieldLabel : '所属项目',
				name : 'project_id',
				hiddenName : 'project_id',
				displayField : 'name',
				valueField : 'id',
				store : projectStore
			});

	var categoryCombox = new Ext.form.ComboBox({
				mode : 'local',
				value : '选择分类',
				width : 200,
				triggerAction : 'all',

				forceSelection : true,
				editable : false,
				fieldLabel : '所属分类',
				name : 'category_id',
				hiddenName : 'category_id',
				displayField : 'name',
				valueField : 'id',
				store : categoryStore

			});

	var priorityCombox = new Ext.form.ComboBox({
				mode : 'local',
				value : '选择优先级',
				width : 200,
				triggerAction : 'all',

				forceSelection : true,
				editable : false,
				fieldLabel : '优先级',
				name : 'priority_id',
				hiddenName : 'priority_id',
				displayField : 'name',
				valueField : 'id',
				store : priorityStore
			});

	var underTakerCombox = new Ext.form.ComboBox({
				mode : 'local',
				value : '选择负责人',
				width : 200,
				triggerAction : 'all',

				forceSelection : true,
				editable : false,
				fieldLabel : '负责人',
				name : 'undertaker_id',
				hiddenName : 'undertaker_id',
				displayField : 'name',
				valueField : 'id',
				store : underTakerStore
			});

	var noAcceptInfoText = new Ext.form.TextArea({
				xtype : 'textarea',
				fieldLabel : '不接受的理由',

				width : 500,
				height : 300,
				name : 'noAcceptInfo',
				grow : false,
				preventScrollbars : true

			});
	var noDelegationText = new Ext.form.TextArea({

				xtype : 'textarea',
				fieldLabel : '不接受委托的原因',

				width : 500,
				height : 300,
				name : 'noDelegationInfo',
				grow : false,
				preventScrollbars : true

			});

	var auditionPassTest = new Ext.form.TextArea({

				xtype : 'textarea',
				fieldLabel : '审批通过',
				width : 500,
				height : 300,
				name : 'auditionInfo',
				grow : false,
				preventScrollbars : true
			});
	var auditionUnpassTest = new Ext.form.TextArea({
				xtype : 'textarea',
				fieldLabel : '审批不通过原因',
				width : 500,
				height : 300,
				name : 'audtionInfo',
				grow : false,
				preventScrollbars : true
			});

	var ds = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'myTicketList.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'myTicketList',
							fields : [{
										name : 'attachFlag',
										mapping : 'attachFlag'
									}, {
										name : 'id',
										mapping : 'id'
									}, {
										name : 'pid',
										mapping : 'pid'
									}, {
										name : 'createTime',
										mapping : 'createTime',

										type : 'date'
									}, {
										name : 'content',
										mapping : 'content'
									}, {
										name : 'status',
										mapping : 'status'
									}, {
										name : 'ApproverId',
										mapping : 'userByApproverId'
									}, {
										name : 'approverName',
										mapping : 'approverName'
									}, {
										name : 'projectId',
										mapping : 'project'
									}, {
										name : 'projectName',
										mapping : 'projectName'
									}, {
										name : 'undertakerId',
										mapping : 'userByUndertakerId'
									},

									{
										name : 'underTakerName',
										mapping : 'underTakerName'
									}, {
										name : 'categoryId',
										mapping : 'category'
									}, {
										name : 'categoryName',
										mapping : 'categoryName'
									}, {
										name : 'priorityId',
										mapping : 'priority'
									}, {
										name : 'priorityName',
										mapping : 'priorityName'
									}, {
										name : 'title',
										mapping : 'title'
									}, {
										name : 'desc',
										mapping : 'desc'
									},
									{
									name:'createTime',
									mapping:'createTime'
									
									},

									{
										name : 'commitTime',
										mapping : 'commitTime'

									}, {
										name : 'commitApproverTime',
										mapping : 'commitApproverTime'

									}, {
										name : 'receiveApproverTime',
										mapping : 'receiveApproverTime'

									}, {
										name : 'approverPassTime',
										mapping : 'approverPassTime'

									}, {
										name : 'closeTime',
										mapping : 'closeTime'

									}, {
										name : 'delegateTime',
										mapping : 'delegateTime'

									}, {
										name : 'receiveDelegateTime',
										mapping : 'receiveDelegateTime'

									},

									{
										name : 'isdigest',
										mapping : 'isdigest'
									}, {
										name : 'ccIds',
										mapping : 'ccIds'
									}, {
										name : 'ccGroupIds',
										mapping : 'ccGroupIds'
									}, {
										name : 'statusInfo',
										mapping : 'statusInfo'
									}, {
										name : 'operation',
										mapping : 'operation'
									}]

						})
			});
	ds.load();
	// 按钮生成

	var auditorStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							autoLoad : true,
							url : 'auditorList.do'
						}),
				// 创建JsonReader读取router记录
				reader : new Ext.data.JsonReader({
							root : 'auditorList',
							fields : ['id', 'name']
						})
			});

	auditorStore.load();

	var underTakerStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							autoLoad : true,
							url : 'underTakerList.do'
						}),
				// 创建JsonReader读取router记录
				reader : new Ext.data.JsonReader({
							root : 'underTakerList',
							fields : ['id', 'name']
						})
			});

	underTakerStore.load();

	var delegatorCombox = new Ext.form.ComboBox({
				// id:'auditorId',
				// xtype : 'combo',
				mode : 'remote',
				value : '选择委托人',
				width : 200,
				triggerAction : 'all',
				fieldLabel : '委托人',
				forceSelection : true,
				// editable : false,

				name : 'delegatorId',
				hiddenName : 'delegatorId',
				displayField : 'name',
				valueField : 'id',
				store : underTakerStore
			});
	var auditorCombox = new Ext.form.ComboBox({
				// id:'auditorId',
				// xtype : 'combo',
				mode : 'remote',
				value : '请选择审批人',
				width : 200,
				triggerAction : 'all',
				fieldLabel : '审批人',
				forceSelection : true,
				// editable : false,

				name : 'auditorId',
				hiddenName : 'auditorId',
				displayField : 'name',
				valueField : 'id',
				store : auditorStore
			});

	var auditorCombox1 = new Ext.form.ComboBox({
				// id:'auditorId',
				// xtype : 'combo',
				mode : 'remote',
				value : '请选择审批人',
				width : 200,
				triggerAction : 'all',
				fieldLabel : '审批人',
				forceSelection : true,
				// editable : false,

				name : 'auditorId',
				hiddenName : 'auditorId',
				displayField : 'name',
				valueField : 'id',
				store : auditorStore
			});

	// 用于文件上传的form
	var MsgForm = new Ext.form.FormPanel({
		baseCls : 'x-plain',
		labelAlign : 'left',
		buttonAlign : 'center',
		frame : true,
		labelWidth : 65,
		defaultType : 'textfield',
		fileUpload : true,
		defaults : {
			allowBlank : false
		},
		items : [{
			xtype : 'fieldset',
			title : '属性信息',
			collapsible : true,
			width : 420,
			autoHeight : true,// 使自适应展开排版
			defaults : {
				width : 300,
				height : 20
			},
			defaultType : 'textfield',
			items : [{
						id : 'fileName',
						xtype : 'textfield',
						fieldLabel : '文件上传',
						allowBlank : false,
						name : 'upload',
						inputType : 'file'// 文件类型
					}],
			buttons : [{
				text : '上传',
				handler : function() {
					var ticketId = gridForm.findById("ticketId").getValue();

					MsgForm.form.doAction('submit', {
								url : 'uploadAttach.do',
								method : 'post',
								params : {
									ticketId : ticketId
								},
								waitTitle : "提示",
								waitMsg : "<font color='black'>正在发送，请稍后......</font>",
								success : function(MsgForm, action) {

									Ext.Msg.alert('成功', "文件上传成功");

								},
								failure : function(MsgForm, action) {

									Ext.Msg.alert('错误', '文件上传失败');
								}
							});

				}
			}]
		}]
	});

	var MsgForm1 = new Ext.form.FormPanel({
		baseCls : 'x-plain',
		labelAlign : 'left',
		buttonAlign : 'center',
		frame : true,
		labelWidth : 65,
		defaultType : 'textfield',
		fileUpload : true,
		defaults : {
			allowBlank : false
		},
		items : [{
			xtype : 'fieldset',
			title : '属性信息',
			collapsible : true,
			width : 420,
			autoHeight : true,// 使自适应展开排版
			defaults : {
				width : 300,
				height : 20
			},
			defaultType : 'textfield',
			items : [{
						id : 'fileName',
						xtype : 'textfield',
						fieldLabel : '文件上传',
						allowBlank : false,
						name : 'upload',
						inputType : 'file'// 文件类型
					}],
			buttons : [{
				text : '上传',
				handler : function() {
					var ticketId = gridForm.findById("ticketId").getValue();

					MsgForm1.form.doAction('submit', {
								url : 'uploadAttach.do',
								method : 'post',
								params : {
									ticketId : ticketId
								},
								waitTitle : "提示",
								waitMsg : "<font color='black'>正在发送，请稍后......</font>",
								success : function(MsgForm1, action) {

									Ext.Msg.alert('成功', "文件上传成功");

								},
								failure : function(MsgForm1, action) {

									Ext.Msg.alert('错误', '文件上传失败');
								}
							});

				}
			}]
		}]
	});
	var accept = new Ext.Button({
				id : 'accept',
				text : '接受',
				name : 'ss',
				handler : function() {
					gridForm.form.doAction('submit', {
								url : 'accept.do',
								method : 'post',
								params : {},
								success : function(form, action) {
									Ext.Msg.alert("Info", "操作成功");

									ds.load();
									hideButton();
								}
							});
				}
			});

	var _wina1 = new Ext.Window({
				title : '不接受委托的原因',
				closable : true,
				closeAction : 'hide',
				width : 700,
				height : 400,
				// border:false,
				plain : true,
				layout : 'fit',
				listeners : {
					"hide" : function() {

					}
				},

				items : [{

							xtype : 'fieldset',
							// checkboxToggle:true,
							title : '不接受委托',// 审批通过的窗口。更新审批状况，以及一些补充。
							autoHeight : true,

							defaultType : 'textfield',
							// collapsed: true,
							items : noAcceptInfoText

						}],

				buttons : [{
							text : '确定',
							// 更新审批状况，updata事件时间。
							handler : function() {
								var noAcceptInfo = noAcceptInfoText.getValue();
								gridForm.form.doAction('submit', {
											url : 'noAccept.do',
											method : 'post',
											params : {
												noAcceptInfo : noAcceptInfo
											},
											success : function(form, action) {
												Ext.Msg.alert("Info", "操作成功");
												ds.load();
												hideButton();
												_wina1.hide();
											}
										});
							}
						}, {
							text : '关闭',
							handler : function() {
								_wina1.hide();
							}
						}]

			});
	var noAccept = new Ext.Button({
				text : '不接受',
				handler : function() {

					_wina1.show();

				}

			});

	var _winb1 = new Ext.Window({
				title : '事件处理',
				closable : true,
				closeAction : 'hide',
				width : 700,
				autoHeight : true,
				// border:false,
				plain : true,
				layout : 'form',

				items : [{

							xtype : 'fieldset',
							// checkboxToggle:true,
							title : '事件处理',
							autoHeight : true,

							defaultType : 'textfield',
							// collapsed: true,
							items : descText1

						}, MsgForm1, auditorCombox1],

				buttons : [{
					text : '确定',
					disabled : false,
					handler : function() {

						var auditorId = auditorCombox1.getValue();
						var descp = descText1.getValue();

						gridForm.form.doAction('submit', {
									url : 'submitInAccepting.do',
									method : 'post',
									params : {
										auditorId : auditorId,
										contentInfo : descp
									},
									success : function(form, action) {
										Ext.Msg.alert("Info", "操作成功");
										ds.load();
										hideButton();
										_winb1.hide();

									}
								});
					}

						// 提交后更新gird的内容。
					}, {
					text : '关闭',
					handler : function() {
						_winb1.hide();
					}
				}]

			});
	var submit = new Ext.Button({
				text : '处理事件',

				handler : function() {

					_winb1.show();

				}
			});

	var reDeal = new Ext.Button({
				text : '重新处理事件',

				handler : function() {

					_winb1.show();

				}
			});		

	var delegate = new Ext.Button({
				text : '接受委托',
				handler : function() {
					gridForm.form.doAction('submit', {
								url : 'acceptDelegate.do',
								method : 'post',
								params : {},
								success : function(form, action) {
									Ext.Msg.alert("Info", "操作成功");
									ds.load();
									hideButton();
								}
							});
				}
			});

	var _winc1 = new Ext.Window({
				title : '不接受委托的原因',
				closable : true,
				closeAction : 'hide',
				width : 700,
				height : 400,
				// border:false,
				plain : true,
				layout : 'fit',
				listeners : {
					"hide" : function() {

					}
				},

				items : [{

							xtype : 'fieldset',
							// checkboxToggle:true,
							title : '不接受委托',// 审批通过的窗口。更新审批状况，以及一些补充。
							autoHeight : true,

							defaultType : 'textfield',
							// collapsed: true,
							items : noDelegationText

						}],

				buttons : [{
							text : '确定',
							// 更新审批状况，updata事件时间。
							handler : function() {
								var delegationInfo = noDelegationText
										.getValue();
								gridForm.form.doAction('submit', {
											url : 'noAcceptDelegate.do',
											method : 'post',
											params : {
												delegationInfo : delegationInfo
											},
											success : function(form, action) {
												Ext.Msg.alert("Info", "操作成功");
												ds.load();
												hideButton();
												_winc1.hide();
											}
										});
							}
						}, {
							text : '关闭',
							handler : function() {
								_winc1.hide();
							}
						}]

			});

	var noDelegate = new Ext.Button({
				text : '不接受委托',
				handler : function() {

					_winc1.show();

				}

			});

	var _wind1 = new Ext.Window({
				title : '委托他人',
				closable : true,
				closeAction : 'hide',
				width : 400,
				autoHeight : true,
				// border:false,
				plain : true,
				layout : 'form',

				items : delegatorCombox,

				buttons : [{
					text : '确定',
					disabled : false,
					handler : function() {

						var delegatorId = delegatorCombox.getValue();

						gridForm.form.doAction('submit', {
									url : 'delegation.do',
									method : 'post',
									params : {
										delegatorId : delegatorId
									},
									success : function(form, action) {
										Ext.Msg.alert("Info", "操作成功");
										ds.load();
										hideButton();
										_wind1.hide();
									}
								});
					}

						// 提交后更新gird的内容。
					}, {
					text : '关闭',
					handler : function() {
						_wind1.hide();
					}
				}]

			});
	var usingDelegate = new Ext.Button({
				text : '委托他人',
				handler : function() {

					_wind1.show();
				}
			});
	// 委托人处理事件
	var _wine1 = new Ext.Window({
				title : '事件处理',
				closable : true,
				closeAction : 'hide',
				width : 700,
				autoHeight : true,
				// border:false,
				plain : true,
				layout : 'form',

				items : [{

							xtype : 'fieldset',
							// checkboxToggle:true,
							title : '事件处理',
							autoHeight : true,

							defaultType : 'textfield',
							// collapsed: true,
							items : descText

						}, MsgForm, auditorCombox],

				buttons : [{
					text : '确定',
					disabled : false,
					handler : function() {

						var auditorId = auditorCombox.getValue();
						var descp = descText.getValue();

						gridForm.form.doAction('submit', {
									url : 'delegatorDeal.do',
									method : 'post',
									params : {
										auditorId : auditorId,
										contentInfo : descp
									},
									success : function(form, action) {
										Ext.Msg.alert("Info", "操作成功");
										ds.load();
										hideButton();
										_wine1.hide();

									}
								});
					}

						// 提交后更新gird的内容。
					}, {
					text : '关闭',
					handler : function() {
						_wine1.hide();
					}
				}]

			});
	var delegatorDealEvent = new Ext.Button({
				text : '处理事件',

				handler : function() {

					_wine1.show();

				}
			});
	var resubmit = new Ext.Button({
				text : '重置为新Ticket',
				handler : function() {
					gridForm.form.doAction('submit', {
								url : 'resetTicket.do',
								method : 'post',
								params : {},
								success : function(form, action) {
									Ext.Msg.alert("Info", "操作成功");
									ds.load();
									hideButton();
								}
							});
				}
			});
	// 重新编辑时候主要编辑是内容，属性和负责人

	var _winf1 = new Ext.Window({
				title : '事件不被接受重新编辑',
				closable : true,
				closeAction : 'hide',
				width : 700,
				autoHeight : true,
				// border:false,
				plain : true,
				layout : 'form',
				items : [

						editorContent, projectCombox, categoryCombox,
						priorityCombox, underTakerCombox],

				buttons : [{
					text : '确定',
					disabled : false,
					handler : function() {
						var contentInfo = editorContent.getValue();
						var projectId = projectCombox.getValue();
						var categoryId = categoryCombox.getValue();
						var priorityId = priorityCombox.getValue();
						var underTakerId = underTakerCombox.getValue();
						gridForm.form.doAction('submit', {
									url : 'editorTicket.do',
									method : 'post',
									params : {
										contentInfo : contentInfo,
										project_id : projectId,
										category_id : categoryId,
										priority_id : priorityId,
										undertaker_id : underTakerId
									},
									success : function(form, action) {
										Ext.Msg.alert("Info", "操作成功");
										ds.load();
										hideButton();
										_winf1.hide();
									}
								});
					}
						// 提交后更新gird的内容。
					}, {
					text : '关闭',
					handler : function() {
						_winf1.hide();
					}
				}]

			});
	var editor = new Ext.Button({
				text : '重新编辑',
				scope : this,
				handler : function() {

					_winf1.show();

				}
			});

	var del = new Ext.Button({
				text : '删除记录',
				handler : function() {
					gridForm.form.doAction('submit', {
								url : 'delete.do',
								method : 'post',
								params : {},
								success : function(form, action) {
									Ext.Msg.alert("Info", "操作成功");
									ds.load();
									hideButton();

								}
							});
				}
			});
	var create = new Ext.Button({
				text : '提交ticket',
				handler : function() {
					gridForm.form.doAction('submit', {
								url : 'admin_submitEvent.do',
								method : 'post',
								params : {},
								success : function(form, action) {
									Ext.Msg.alert("Info", "操作成功");
									ds.load();
									hideButton();
								}
							});
				}
			});
	var _wing1 = new Ext.Window({
				title : '审批通过',
				closable : true,
				closeAction : 'hide',
				width : 700,
				height : 400,
				// border:false,
				plain : true,
				layout : 'fit',
				listeners : {
					"hide" : function() {

					}
				},

				items : [{

							xtype : 'fieldset',
							// checkboxToggle:true,
							title : '审批通过',// 审批通过的窗口。更新审批状况，以及一些补充。
							autoHeight : true,

							defaultType : 'textfield',
							// collapsed: true,
							items : auditionPassTest

						}],

				buttons : [{
							text : '确定',
							// 更新审批状况，updata事件时间。
							handler : function() {
								var auditonPassInfo = auditionPassTest
										.getValue();
								gridForm.form.doAction('submit', {
											url : 'pass.do',
											method : 'post',
											params : {
												auditionInfo : auditonPassInfo
											},
											success : function(form, action) {
												Ext.Msg.alert("Info", "操作成功");
												ds.load();
												hideButton();
												_wing1.hide();
											}
										});
							}
						}, {
							text : '关闭',
							handler : function() {
								_wing1.hide();
							}
						}]

			});
	var pass = new Ext.Button({
				text : '审核通过',
				scope : this,
				handler : function() {

					_wing1.show();

				}
			});

	var _winh1 = new Ext.Window({
				title : '审批不通过',
				closable : true,
				closeAction : 'hide',
				width : 700,
				height : 400,
				// border:false,
				plain : true,
				layout : 'fit',
				listeners : {
					"hide" : function() {
						// Ext.Msg.alert("警告", "未保存数据将被丢失！");
					}
				},

				items : [{

							xtype : 'fieldset',
							// checkboxToggle:true,
							title : '审批不通过',
							autoHeight : true,

							defaultType : 'textfield',
							// collapsed: true,
							items : auditionUnpassTest

						}],

				buttons : [{
					text : '确定',
					disabled : false,
					handler : function() {
						var auditionInfo = auditionUnpassTest.getValue();
						gridForm.form.doAction('submit', {
									url : 'noPass.do',
									method : 'post',
									params : {
										auditionInfo : auditionInfo
									},
									success : function(form, action) {
										Ext.Msg.alert("Info", "操作成功");
										ds.load();
										hideButton();
										_winh1.hide();
									}
								});
					}
						// 更新ticket状况。以及审批不通过的状况。
					}, {
					text : '关闭',
					handler : function() {
						_winh1.hide();
					}
				}]

			});
	var noPass = new Ext.Button({
				text : '审核不通过',
				scope : this,
				handler : function() {

					_winh1.show();

				}
			});

	// 表格颜色控制。可以用于优先级，以及超过时限等事件的表述
	function italic(value) {
		return '<i>' + value + '</i>';
	}

	function change(val) {
		if (val > 0) {
			return '<span style="color:green;">' + val + '</span>';
		} else if (val < 0) {
			return '<span style="color:red;">' + val + '</span>';
		}
		return val;
	}
	function pctChange(val) {
		if (val > 0) {
			return '<span style="color:green;">' + val + '%</span>';
		} else if (val < 0) {
			return '<span style="color:red;">' + val + '%</span>';
		}
		return val;
	}

	// 表格建立
	var colModel = new Ext.grid.ColumnModel([{
				id : 'company',
				header : "标题",
				width : 75,
				sortable : true,
				dataIndex : 'title'
			}, {
				header : "状态",
				width : 150,
				sortable : true,
				dataIndex : 'statusInfo'
			}, {
				header : "创建日期",
				width : 100,
				sortable : true,
				dataIndex : 'createTime'
			}, {
				header : "所属项目",
				width : 70,
				sortable : true,
				dataIndex : 'projectName'
			}, {
				header : "项目优先级",
				width : 70,
				sortable : true,
				dataIndex : 'priorityName'
			}, {
				header : "负责人",
				width : 70,
				sortable : true,
				dataIndex : 'underTakerName'
			}, {
				header : "审批人",
				width : 70,
				sortable : true,
				dataIndex : 'approverName'
			}

	]);

	var gridForm = new Ext.FormPanel({
				id : 'company-form',
				frame : true,
				renderTo : 'showGrid',
				autoScroll : true,
				height : document.body.clientHeight * 0.95 + 5,
				labelAlign : 'left',
				title : '事件列表',
				bodyStyle : 'padding:5px',
				width : 840,
				layout : 'column',
				items : [{
							columnWidth : 0.6,
							layout : 'fit',
							items : {
								id : 'ticketList',
								xtype : 'grid',
								ds : ds,
								cm : colModel,
								sm : new Ext.grid.RowSelectionModel({
											singleSelect : true,
											listeners : {
												'rowselect' : function(sm, row,
														rec) {

													hideButton();
													showButton(rec);
                                                    fileupPanel.collapse(); 
												}
											}
										}),
								autoExpandColumn : 'company',
								height : 1000,

								title : '事件列表',
								border : true,
								listeners : {
									render : function(g) {
										g.getSelectionModel().selectRow(0);
									},
									delay : 10
								}
							}
						}, {
							columnWidth : 0.4,
							xtype : 'fieldset',
							labelWidth : 90,
							defaults : {
							// width : 200
							},
							defaultType : 'textfield',
							autoHeight : true,
							bodyStyle : Ext.isIE
									? 'padding:0 0 5px 15px;'
									: 'padding:10px 15px;',
							border : false,
							style : {
								"margin-left" : "10px", // when you add custom
								// margin in
								// IE 6...
								"margin-right" : Ext.isIE6 ? (Ext.isStrict
										? "-10px"
										: "-13px") : "0" // you have to
								// adjust for it
								// somewhere else
							},
							// 2.3解决浏览器问题。尝试下。

							// 读取左边grid的数据。
							items : [{
										fieldLabel : '标题',
										readOnly : true,
										width : 200,
										name : 'title'
									}, {
										xtype : 'textarea',
										readOnly : true,
										width : 200,
										fieldLabel : '内容',
										name : 'content'
									}, {
										fieldLabel : '所属项目',
										readOnly : true,
										width : 200,
										name : 'projectName'
									}, {
										fieldLabel : '所属分类',
										width : 200,
										readOnly : true,
										name : 'categoryName'
									}, {
										fieldLabel : '优先级',
										width : 200,
										readOnly : true,
										name : 'priorityName'
									}, {
										fieldLabel : '负责人',
										width : 200,
										readOnly : true,
										name : 'underTakerName'
									}, {
										fieldLabel : '审批人',
										width : 200,
										readOnly : true,
										name : 'approverName'
									}, {
										xtype : 'checkbox',
										fieldLabel : '精华标示',
										width : 200,
										readOnly : true,
										name : 'isdigest'
									}, {
										fieldLabel : '创建日期',
										width : 200,
										readOnly : true,
										// vtype:'date',

										name : 'createTime'
									}, {
										
										fieldLabel : '接受日期',
										width : 200,
										readOnly : true,

										name : 'receiveApproverTime'
									}, {
										
										width : 200,
										
										fieldLabel : '提交审批日期',
										readOnly : true,

										name : 'commitApproverTime'
									}, {
										
										fieldLabel : '审批通过日期',
										width : 200,
										readOnly : true,

										name : 'approverPassTime'
									}, {
										
										fieldLabel : '关闭日期',
										width : 200,
										readOnly : true,

										name : 'closeTime'
									}, {
										
										fieldLabel : '委托日期',
										width : 200,
										readOnly : true,

										name : 'delegateTime'
									}, {
									
										fieldLabel : '接受委托日期',
										width : 200,
										readOnly : true,

										name : 'receiveDelegateTime'
									}, {
										xtype : 'textarea',
										fieldLabel : '处理状况',
										width : 200,
										readOnly : true,
										name : 'desc'
									}, {
										xtype : 'textarea',
										fieldLabel : '当前状态',
										readOnly : true,
										width : 200,
										name : 'statusInfo'
									}, fileupPanel, {
										id : "ticketId",
										fieldLabel : 'ticketId',
										hidden : true,
										hideLabel : true,
										width : 200,
										readOnly : true,
										name : 'id'
									}, {

										fieldLabel : 'undertakerId',
										readOnly : true,
										width : 200,
										hideLabel : true,
										hidden : true,
										name : 'undertakerId'
									}, {
										fieldLabel : '流程号',
										readOnly : true,
										hideLabel : true,
										width : 200,
										hidden : true,
										name : 'pid'
									}, {
										xtype : 'fieldset',
										labelWidth : 90,
										id : 'butop',
										title : '',
										defaults : {
											width : 500
										}, // Default config options for child
										// items
										defaultType : 'textfield',
										autoHeight : true,
										bodyStyle : Ext.isIE
												? 'padding:0 0 5px 15px;'
												: 'padding:10px 15px;',
										// border: false,
										style : {
											"margin-left" : "10px", // when you
											// add
											// custom margin in
											// IE 6...
											"margin-right" : Ext.isIE6
													? (Ext.isStrict
															? "-10px"
															: "-13px")
													: "0" // you have to
											// adjust for it
											// somewhere
											// else
										},
										listeners : {
											render : function() {

											}
										}
									}]

						}

				]

			});

	function hideButton() {
		accept.hide();
		noAccept.hide();
		submit.hide();
		reDeal.hide();
		delegate.hide();
		noDelegate.hide();
		usingDelegate.hide();
		resubmit.hide();
		editor.hide();
		del.hide();
		create.hide();
		pass.hide();
		noPass.hide();
		delegatorDealEvent.hide();
		
	};

	function showButton(rec) {
		Ext.getCmp("company-form").getForm().loadRecord(rec);

		var strs = new Array();
		strs = rec.get("operation").split("@"); // 字符分割
	
		for (i = 0; i < strs.length; i++) {
			if (strs[i] == "accept") {
				accept.show();
				accept.render('butop');
			};
			if (strs[i] == "noAccept") {
				noAccept.show();
				noAccept.render('butop');
			};
			if (strs[i] == "submit") {
				submit.show();
				submit.render('butop')
			};
			if (strs[i] == "delegatorDeal") {
				delegatorDealEvent.show();
				delegatorDealEvent.render('butop')
			}
			if (strs[i] == "acceptDelegate") {
				delegate.show();
				delegate.render('butop')
			};
			if (strs[i] == "noAcceptDelegate") {
				noDelegate.show();
				noDelegate.render('butop')
			};
			if (strs[i] == "usingDelegate") {
				usingDelegate.show();
				usingDelegate.render('butop')
			};
			if (strs[i] == "reset") {
				resubmit.show();
				resubmit.render('butop')
			};
			if (strs[i] == "reDelegate") {
				usingDelegate.show();
				usingDelegate.render('butop')
			};
			if (strs[i] == "reDoing") {
				reDeal.show();
				reDeal.render('butop')
			};

			if (strs[i] == "editor") {
				editor.show();
				editor.render('butop')
			};
			if (strs[i] == "delete") {
				del.show();
				del.render('butop')
			};
			if (strs[i] == "admin_submit") {
				create.show();
				create.render('butop')
			};
			if (strs[i] == "pass") {
				
				pass.show();
				pass.render('butop')
			};
			if (strs[i] == "noPass") {
				noPass.show();
				noPass.render('butop')
			};
		
		};

	};

});