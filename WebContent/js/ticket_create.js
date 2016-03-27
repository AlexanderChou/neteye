Ext.onReady(function() {

	Ext.QuickTips.init();

	Ext.form.Field.prototype.msgTarget = 'side';
	
	
	
    var flag=0;//是否上传附件的标志
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

	var underTakerStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'underTakerList.do'
						}),
				// 创建JsonReader读取router记录
				reader : new Ext.data.JsonReader({
							root : 'underTakerList',
							fields : ['id', 'name']
						})
			});
	underTakerStore.load();
	var copyUserStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'copyUserList.do'
						}),
				// 创建JsonReader读取router记录
				reader : new Ext.data.JsonReader({
							root : 'copyUserList',
							fields : ['id', 'name']
						})
			});
	copyUserStore.load();
	// 上传文件
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
			title : '附件信息',
			collapsible : true,
			width : 320,
			autoHeight : true,// 使自适应展开排版
			defaults : {
				width : 200,
				height : 30
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
					MsgForm.form.doAction('submit', {
								url : 'uploadAttach.do',
								method : 'post',
								params : {
								
								},
								waitTitle : "提示",
								waitMsg : "<font color='black'>正在发送，请稍后......</font>",
								success : function(MsgForm, action) {

									Ext.Msg.alert('成功', "文件上传成功");
                                    flag=1;
                                    win.hide();
								},
								failure : function(MsgForm, action) {

									Ext.Msg.alert('错误', '文件上传失败');
                                    win.hide();
								}
							});

				}
			}, {
				text : '关闭',
				handler : function() {
					win.close();
				}
			}]
		}]
	});

	var win = new Ext.Window({
				title : '上传附件',
				width : 350,
				height : 180,
				x : 330,
				y : 150,
				layout : 'fit',
				plain : true,
				bodyStyle : 'padding:5px;color:black;',
				buttonAlign : 'center',
				items : MsgForm
			});

	var ticketcr = new Ext.FormPanel({
				height : document.body.clientHeight * 0.95 + 5,
				width : 840,
				autoScroll : true,
				frame : true,
				title : '新建事件',
				defaultType : 'textfield',
				bodyStyle : 'padding:20px 20px 20px 20px',
				// },
				// layout:'fit',
				// region:'center',
				// margins: '20 auto',
				// bodyStyle :'padding:5px 5px 0',
				// width: 350,
				items : [

				{
							fieldLabel : '标题',
							name : 'title',
							width : 500
						}, {
							xtype : 'textarea',
							fieldLabel : '内容',
							value : '注:',
							name : 'content',
							grow : false,
							preventScrollbars : true,
							width : 500,
							height : 150
						}, {
							xtype : 'textarea',
							fieldLabel : '描述',
							value : '注:',
							name : 'description',
							grow : false,
							preventScrollbars : true,
							width : 500,
							height : 150
						}, {
							xtype : 'combo',
							value : '请选择所属项目',
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
						}, {
							xtype : 'combo',
							mode : 'remote',
							value : '请选择所属分类',
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
						}, {
							xtype : 'combo',
							mode : 'remote',
							value : '请选择所属优先级',
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
						}, {
							xtype : 'combo',
							mode : 'local',
							value : '请选择负责人',
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
						}, {

							xtype : 'checkbox',

							checked : true,
							fieldLabel : '是否为标记为精华',
							boxLabel : '',
							name : 'isdigest',
							inputValue : '1'

						}, {

							xtype : 'lovcombo',
							mode : 'remote',
							value : '请选择抄送人',
							width : 200,
							triggerAction : 'all',
							editable : false,
							allowBlank : true,
							fieldLabel : '抄送人',
							name : 'user_id',
							hiddenName : 'user_id',
							displayField : 'name',
							valueField : 'id',
							store : copyUserStore

						},{
							xtype : 'button',
							text : '上传文件',
							handler : function() {
								win.show();
							}

						}

				],
				buttons : [ {
							text : '提交',
							handler : function() {

								this.disabled = true;

								ticketcr.form.doAction('submit', {
											url : 'submitInCreating.do',
											waitTitle : '提交数据。。。。',
											wairtMsg : 'Posting...',
											method : 'post',

											params : {
											 attachflag:flag 
											},
											success : function(form, action) {

												Ext.Msg.alert('操作', "提交事件成功");
												this.disabled = false;

											},
											failure : function(form, action) {

												Ext.Msg.alert('警告', "提交事件失败 请确保你有管理员身份");

												this.disabled = false;

											}
										});
								this.disabled = false;

							}
						}, {
							text : '重置',
							scope : this,
							handler : function() {
								ticketcr.form.reset();
							}
						}]
			});

	ticketcr.render('showGrid');
});