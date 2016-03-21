Ext.apply(Ext.form.VTypes, {
	inputtest : function(v) {

		var sss = v.replace(/\r\n/ig, "@#%"); //去除换号符号,
		var lines = sss.split("@#%");
		// var reg = /^[0-9]{6}$/;
		var reg = /^\S+[\|]{2}([a-z0-9.]+([,]\w+)+[;])+$/;
		var msg = "";
		var isExist = new Array();
		var isExist2 = new Array();
		var msg2 = "";
		var msg3 = "";
      
		for (var index = 0; index < lines.length; index++) {
			var line = parseInt(index) + 1;

			if (!reg.test(lines[index])) {
				msg += line + ",";
			}
		}

		if (msg != "") {	
//		alert("第 " + msg + " 行内容格式出错！");
			return false;
		}
		return true;
	},

	inputtestText : "格式不对!"
		// CodeMask: /\d/
	});

Ext.apply(Ext.form.VTypes, {
	inputtest1 : function(v) {

		var sss = v.replace(/\r\n/ig, "@#%");
		var lines = sss.split("@#%");
		// var reg = /^[0-9]{6}$/;
		var reg = /^\S+[\|]{2}\S+/;
		var msg = "";
		var isExist = new Array();
		var isExist2 = new Array();
		var msg2 = "";
		var msg3 = "";
		for (var index = 0; index < lines.length; index++) {
			var line = parseInt(index) + 1;

			if (!reg.test(lines[index])) {
				msg += line + ",";
			}
		}

		if (msg != "") {
	//		alert("第 " + msg + " 行内容格式出错！");
			return false;
		}
		return true;
	},

	inputtest1Text : "格式不对!"
		// CodeMask: /\d/
	});

Ext.apply(Ext.form.VTypes, {
	inputtest2 : function(v) {

		var sss = v.replace(/\r\n/ig, "@#%");
		var lines = sss.split("@#%");
		// var reg = /^[0-9]{6}$/;
		var reg = /^\S+[\|]{2}[a-z0-9.]+$/;
		var msg = "";
		var isExist = new Array();
		var isExist2 = new Array();
		var msg2 = "";
		var msg3 = "";

		for (var index = 0; index < lines.length; index++) {
			var line = parseInt(index) + 1;

			if (!reg.test(lines[index])) {
				//alert("第 " + msg + " 行内容格式出错！");
				msg += line + ",";
			}
		}

		if (msg != "") {
			return false;
		}
		return true;
	},

	inputtest2Text : "格式不对!"
		// CodeMask: /\d/
	});
Ext.apply(Ext.form.VTypes, {
	inputtest3 : function(v) {

		var sss = v.replace(/\r\n/ig, "@#%"); 
		var lines = sss.split("@#%");
		// var reg = /^[0-9]{6}$/;
		var reg = /^\S+[\|]{2}[a-z0-9.]+[_]\w+/;
		var msg = "";
		var isExist = new Array();
		var isExist2 = new Array();
		var msg2 = "";
		var msg3 = "";

		for (var index = 0; index < lines.length; index++) {
			var line = parseInt(index) + 1;

			if (!reg.test(lines[index])) {	
			//alert("第 " + msg + " 行内容格式出错！");
				msg += line + ",";
			}
		}

		if (msg != "") {
			return false;
		}
		return true;
	},

	inputtest3Text : "格式不对!"
		// CodeMask: /\d/
	});

Ext.onReady(function() {

	Ext.QuickTips.init();

	// 建立框体
	var fsf = new Ext.FormPanel({
		labelWidth : 200,
		width : 840,
		height : document.body.clientHeight * 0.95 + 5,
		autoScroll : true,
		frame : true,
		title : '报表配置',
		items : [{
					xtype : 'fieldset',

					title : '模板名称',
					autoHeight : true,
					defaults : {
						width : 500
					},
					defaultType : 'textfield',

					items : [{
								fieldLabel : '请输入模板名称(建议输入英文名称)',
								allowBlank : false,
								name : 'template'
							}]
				},

				{
					xtype : 'fieldset',

					title : '重要事件',
					autoHeight : true,
					defaults : {
						width : 500
					},
					defaultType : 'textfield',
					html : '<p><em>注:每行内容以回车键分隔，适用于"点评"、"工程操作"等描述性事务类型</em></P>',
					items : [{
								fieldLabel : '标题',
								name : 'eventsTitle',
								width : 500
							}, {
								xtype : 'textarea',
								fieldLabel : '内容',

								name : 'events',
								grow : false,
								preventScrollbars : true,
								width : 500,
								height : 150
								

							}, {

								xtype : 'fieldset',
								title : '添加重要事件',
								autoHeight : true,
								checkboxToggle : true,
								collapsed : true,

								defaultType : 'textfield',
								items : [{
									xtype : 'button',
									text : '添加',
									handler : function() {

										var _panel = this.ownerCt;

										var _textfield = new Ext.form.FieldSet(
												{
													xtype : 'fieldset',
													title : '新增类型',
													autoHeight : true,
													items : [{
																xtype : 'textfield',
																fieldLabel : '标题',
																name : 'eventsTitle',
																width : 300
															}, {
																xtype : 'textarea',
																fieldLabel : '内容',
																name : 'events',
																grow : false,
																value : '注:每行内容以回车键分隔，适用于"点评"、"工程操作"等描述性事务类型',
																preventScrollbars : true,
																autoScroll : true,
																width : 300,
																height : 100

															}, {

																xtype : 'button',
																text : '删除',
																handler : function() {
																	_panel
																			.remove(_textfield);
																	_panel
																			.doLayout();
																}
															}]
												});

										_panel.add(_textfield);
										_panel.doLayout();
									}
										// }
								}]

							}]
				}, {
					xtype : 'fieldset',
					title : '服务器的IP',
					// collapsible: true,
					autoHeight : true,
					defaults : {
						width : 500
					},
					defaultType : 'textfield',
					items : [{
								fieldLabel : '请输入故障统计服务器的IP',
								name : 'faultIP',
								value : '202.38.120.243'

							}, {
								fieldLabel : '请输入流量采集服务器的IP',
								name : 'flowIP',
								value : '202.38.120.243'
							}, {
								fieldLabel : '请输入BGP部署服务器的IP',
								name : 'webIP',
								value : '202.38.120.247'
							}, {
								fieldLabel : '请输入路由监控服务器的IP',
								name : 'dataIP',
								value : '2001:da8:1:100::1'
							}]
				}, {
					xtype : 'fieldset',
					title : '报表类型一',

					// collapsible: true,
					autoHeight : true,
					defaults : {
						width : 500
					},
					defaultType : 'textfield',
					html : '<p>注:每行内容以回车键分隔，行内格式为"名称||数值"，适用于"主干线路故障率"、"主干运行设备事件统计"、"主干传输线路事件统计"等需要手工输入数值的统计类型</p>',

					items : [

					{
								fieldLabel : '标题',
								name : 'sumsTitle',
								width : 500
							}, {
								xtype : 'textarea',
								fieldLabel : '内容',
								name : 'sums',
								id : 'sums',
								grow : false,
								preventScrollbars : true,
								autoScroll : true,
								width : 500,
								height : 150,
								vtype : 'inputtest1'

							}, {
								xtype : 'combo',
								fieldLabel : '请选择图表类型',
								width : 150,
								EmptyText : '请选择图表类型',
								name : 'sumsImage',
								triggerAction : 'all',
								displayField : 'sumsImageValue',
								valueField : 'id',
								hiddenName : 'sumsImage',
								mode : 'local',
								value : 1,
								forceSelection : true,
								fields : ['id', 'sumsImageValue'],
								store : [[1, '柱状图'], [2, '饼状图']]

							}

							, {

								xtype : 'fieldset',
								title : '添加类型一',
								autoHeight : true,
								checkboxToggle : true,
								collapsed : true,

								defaultType : 'radio',
								items : [{
									xtype : 'button',
									text : '添加',
									handler : function() {

										var _panel = this.ownerCt;

										var _textfield = new Ext.form.FieldSet(
												{
													xtype : 'fieldset',
													title : '新增类型',
													defaultType : 'textfield',
													autoHeight : true,
													items : [

													{

																fieldLabel : '标题',
																name : 'sumsTitle',
																width : 300
															}, {
																xtype : 'textarea',
																fieldLabel : '内容',
																name : 'sums',
																grow : false,
																value : '注:每行内容以回车键分隔，行内格式为"名称||数值"，适用于"主干线路故障率"、"主干运行设备事件统计"、"主干传输线路事件统计"等需要手工输入数值的统计类型',
																preventScrollbars : true,
																autoScroll : true,
																width : 300,
																height : 100,
																vtype : 'inputtest1'

															}, {
																xtype : 'combo',
																fieldLabel : '请选择图表类型',
																width : 150,
																name : 'sumsImage',
																displayField : 'sumsImageValue',
																valueField : 'id',
																hiddenName : 'sumsImage',
																mode : 'local',
																triggerAction : 'all',
																forceSelection : true,
																value : 1,
																fields : ['id',
																		'sumsImageValue'],
																store : [
																		[1,
																				'柱状图'],
																		[2,
																				'饼状图']]
															}, {

																xtype : 'button',
																text : '删除',
																handler : function() {

																	_panel
																			.remove(_textfield);
																	_panel
																			.doLayout();
																}
															}]
												});

										_panel.add(_textfield);
										_panel.doLayout();
									}

								}]

							}]

				}, {
					xtype : 'fieldset',
					title : '报表类型二',
					autoHeight : true,
					html : '<P>注:每行内容以回车键分隔，行内格式为"名称||IP地址"，适用于"核心节点可用性"、"接入线路故障率"、"CNGI-6IX核心链路可用率"等需要根据IP地址获得相应故障率或可用性数值的统计类型</P>',

					defaults : {
						width : 500
					},
					defaultType : 'textfield',
					items : [{
								fieldLabel : '标题',
								name : 'faultsTitle',
								width : 500
							}, {
								xtype : 'textarea',
								fieldLabel : '内容',
								name : 'faults',
								grow : false,
								preventScrollbars : true,
								width : 500,
								height : 150,
								vtype : 'inputtest2'
							},
							// {
							// xtype : 'radio',
							// checked : true,
							// fieldLabel : '请选择图表类型',
							// boxLabel : '柱形图',
							// name : 'faultsImage',
							// inputValue : '1'
							// }, {
							// xtype : 'radio',
							// fieldLabel : '',
							// labelSeparator : '',
							// boxLabel : '饼形图',
							// name : 'faultsImage',
							// inputValue : '2'
							//
							// },
							{

								xtype : 'fieldset',
								title : '添加类型二',
								autoHeight : true,
								checkboxToggle : true,
								collapsed : true,

								defaultType : 'radio',
								items : [{
									xtype : 'button',
									text : '添加',
									handler : function() {

										var _panel = this.ownerCt;

										var _textfield = new Ext.form.FieldSet(
												{
													xtype : 'fieldset',
													title : '新增类型',
													autoHeight : true,

													defaultType : 'textfield',

													items : [

													{
																fieldLabel : '标题',
																name : 'faultsTitle',
																width : 300
															}, {
																xtype : 'textarea',
																fieldLabel : '内容',
																name : 'faults',
																grow : false,
																value : '注:每行内容以回车键分隔，行内格式为"名称||IP地址"，适用于"核心节点可用性"、"接入线路故障率"、"CNGI-6IX核心链路可用率"等需要根据IP地址获得相应故障率或可用性数值的统计类型',
																preventScrollbars : true,
																autoScroll : true,
																width : 300,
																height : 100,
																vtype : 'inputtest2'

																// }, {
																// xtype :
																// 'radio',
																// checked :
																// true,
																// fieldLabel :
																// '请选择图表类型',
																// boxLabel :
																// '柱形图',
																// name :
																// 'faultsImage',
																// inputValue :
																// '1'
																// }, {
																// xtype :
																// 'radio',
																// fieldLabel :
																// '',
																// labelSeparator
																// : '',
																// boxLabel :
																// '饼形图',
																// name :
																// 'faultsImage',
																// inputValue :
																// '2'

															}, {
																xtype : 'button',
																text : '删除',
																handler : function() {
																	// Ext.MessageBox.show({
																	// title :
																	// '删除类型',
																	// msg :
																	// '删除该类型?',
																	// buttons :
																	// Ext.MessageBox.OKCANCEL,
																	// animEl :
																	// 'mb4',
																	// icon :
																	// Ext.MessageBox.QUESTION
																	// });

																	_panel
																			.remove(_textfield);
																	_panel
																			.doLayout();
																}
															}

													]
												});

										_panel.add(_textfield);
										_panel.doLayout();
									}
										// }
								}]

							}]
				}, {
					xtype : 'fieldset',
					title : '报表类型三',
					// collapsible: true,
					autoHeight : true,
					html : '<P>注:每行内容以回车键分隔，行内格式为"名称||IP,ifindex,ifindex...ifindex;IP,ifindex...ifindex;"，适用于"主干网入/出流量分布"等需要根据多个设备计算其入/出流量的统计类型</p>',
					defaults : {
						width : 500
					},
					defaultType : 'textfield',
					items : [{
								fieldLabel : '标题',
								name : 'outputsTitle',
								width : 500
							}, {
								xtype : 'textarea',
								fieldLabel : '内容',
								name : 'outputs',

								grow : false,
								preventScrollbars : true,
								width : 500,
								height : 150,
								vtype : 'inputtest'

								// }, {
							// xtype : 'radio',
							// checked : true,
							// fieldLabel : '请选择图表类型',
							// boxLabel : '柱形图',
							// name : 'outputsImage',
							// inputValue : '1'
							// }, {
							// xtype : 'radio',
							// // checked: true,
							// fieldLabel : '',
							// labelSeparator : '',
							// boxLabel : '饼形图',
							// name : 'outputsImage',
							// inputValue : '2'

						}	, {

								xtype : 'fieldset',
								title : '添加类型三',
								autoHeight : true,
								checkboxToggle : true,
								collapsed : true,
								// collapsible: true,
								defaultType : 'radio',
								items : [{
									xtype : 'button',
									text : '添加',
									handler : function() {

										var _panel = this.ownerCt;

										var _textfield = new Ext.form.FieldSet(
												{
													xtype : 'fieldset',
													title : '新增类型',
													// collapsible:
													// true,
													autoHeight : true,

													defaultType : 'textfield',

													items : [

													{
																fieldLabel : '标题',
																name : 'outputsTitle',
																width : 300
															}, {
																xtype : 'textarea',
																fieldLabel : '内容',
																name : 'outputs',
																grow : false,
																value : '注:每行内容以回车键分隔，行内格式为"名称||IP,ifindex,ifindex...ifindex;IP,ifindex...ifindex;"，适用于"主干网入/出流量分布"等需要根据多个设备计算其入/出流量的统计类型',
																preventScrollbars : true,
																autoScroll : true,
																width : 300,
																height : 100,
																vtype : 'inputtest'

																// }, {
																// xtype :
																// 'radio',
																// checked :
																// true,
																// fieldLabel :
																// '请选择图表类型',
																// boxLabel :
																// '柱形图',
																// name :
																// 'outputsImage',
																// inputValue :
																// '1'
																// }, {
																// xtype :
																// 'radio',
																// // checked:
																// // true,
																// fieldLabel :
																// '',
																// labelSeparator
																// : '',
																// boxLabel :
																// '饼形图',
																// name :
																// 'outputsImage',
																// inputValue :
																// '2'

															}, {
																xtype : 'button',
																text : '删除',
																handler : function() {

																	_panel
																			.remove(_textfield);
																	_panel
																			.doLayout();
																}
															}

													]
												});

										_panel.add(_textfield);
										_panel.doLayout();
									}
										// }
								}]

							}]
				}, {
					xtype : 'fieldset',
					title : '流量曲线',
					// collapsible: true,
					autoHeight : true,
					html : '<P>注:标题为流量曲线的分类，如"CERNET2边界流量"、"北京交换中心流量"等，内容为该分类下的流量曲线,每行之间以回车键分隔，行内格式为"名称||IP_ifindex"</P>',
					defaults : {
						width : 500
					},
					defaultType : 'textfield',
					items : [{
								fieldLabel : '标题',
								name : 'curversTitle',
								width : 500
							}, {
								xtype : 'textarea',
								fieldLabel : '内容',
								name : 'curvers',
								grow : false,
								preventScrollbars : true,
								width : 500,
								height : 150,
								vtype : 'inputtest3'
							}, {

								xtype : 'fieldset',
								title : '添加流量曲线',
								autoHeight : true,
								checkboxToggle : true,
								collapsed : true,
								// collapsible: true,
								defaultType : 'radio',
								items : [{
									xtype : 'button',
									text : '添加',
									handler : function() {

										var _panel = this.ownerCt;

										var _textfield = new Ext.form.FieldSet(
												{
													xtype : 'fieldset',
													title : '新增类型',

													// collapsible: true,
													autoHeight : true,
													// defaults: {width: 210},
													defaultType : 'textfield',

													items : [

													{
																fieldLabel : '标题',
																name : 'curversTitle',
																width : 300
															}, {
																xtype : 'textarea',
																fieldLabel : '内容',
																name : 'curvers',
																grow : false,
																value : '注:标题为流量曲线的分类，如"CERNET2边界流量"、"北京交换中心流量"等，内容为该分类下的流量曲线,每行之间以回车键分隔，行内格式为"名称||IP_ifindex',
																preventScrollbars : true,
																autoScroll : true,
																width : 300,
																height : 100,
																vtype : 'inputtest2'

															}, {
																xtype : 'button',
																text : '删除',
																handler : function() {

																	_panel
																			.remove(_textfield);
																	_panel
																			.doLayout();
																}
															}

													]
												});

										_panel.add(_textfield);
										_panel.doLayout();
									}
										// }
								}]

							}]
				}],

		buttons : [{
			text : "保存",
			scope : this,
			handler : function() {
				if (fsf.getForm().isValid()) {
					fsf.getForm().submit({
								url : "json/addSelfReport.do",
								success : function(form, action) {
									Ext.Msg.alert("", "报表模板保存成功");

								},
								failure : function(form, action) {
									Ext.Msg.alert("", "提交错误！");
								}
							});
				} else {
					Ext.Msg.alert("表单错误", "存在错误内容！");
				}

			}
				// }
			}, {
			text : "取消",
			scope : this,
			handler : function() {
				fsf.form.reset();
			}
		}]

	});

	fsf.render('showGrid');

});