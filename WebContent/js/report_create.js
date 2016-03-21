

Ext.onReady(function() {

	Ext.QuickTips.init();

	Ext.form.Field.prototype.msgTarget = 'side';

	// 页面主体

	var templateStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'json/getAllSelfTemplates.do'
						}),
				// 创建JsonReader读取router记录
				reader : new Ext.data.JsonReader({
							root : 'allTemplates',
							fields : ['id', 'myTemplate']
						})
			});
	templateStore.load();

	var usedtemplateStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'json/getSelfTemplates.do'
						}),
				// 创建JsonReader读取router记录
				reader : new Ext.data.JsonReader({
							root : 'selfTemplates',
							fields : ['createdFile']
						})
			});
	usedtemplateStore.load();

	// 报表生成 年月周
	var yearform = new Ext.form.FormPanel({
				title : '年报表',
				autoHeight : true,
				labelWidth : 200,
				collapsed : false,
				collapsible : true,
				onSubmit : Ext.emptyFn, 
		    	submit : function() {
		    		var temp = Ext.get('reportTemplate').getValue();
		    		if(temp==''){
		    			alert("报表模板不能为空！");
		    			return;
		    		} 
			    	this.getEl().dom.action ='outputSelfReport.do?type=year&year='
			    								+Ext.get('firstYear').getValue()
			    								+'&template='+Ext.get('reportTemplate').getValue();
			    	this.getEl().dom.method = 'post';      
			    	this.getEl().dom.submit();      
		    	},
				items : [{
					xtype : 'fieldset',
					autoHeight : true,
					bodyStyle : 'padding:20px 20px 20px 20px',
					items : [{
								xtype : 'numberfield',
								fieldLabel : '请选择年份',
								value : '2010',
								minValue : 1900,
								maxValue : 2100,
								id : 'firstYear',
								name : 'firstYear',
								width : 200
							}, {
								xtype : 'button',
								text : '生成年报表',
								handler : function() {
									yearform.form.submit();
								}
							}]
				}]

			});

	var moonform = new Ext.form.FormPanel({
		title : '月报表',
		collapsed : true,
		collapsible : true,
		autoHeight : true,
		defaultType : 'textfield',
		onSubmit : Ext.emptyFn, 
    	submit : function() {  
    		var temp = Ext.get('reportTemplate').getValue();
    		if(temp==''){
    			alert("报表模板不能为空！");
    			return;
    		}   
	    	this.getEl().dom.action ='outputSelfReport.do?type=month&year='
	    								+Ext.get('twoYear').getValue()
	    								+'&template='+Ext.get('reportTemplate').getValue();
	    	this.getEl().dom.method = 'post';      
	    	this.getEl().dom.submit();      
    	},
		items : [{
			xtype : 'fieldset',
			autoHeight : true,
			bodyStyle : 'padding:20px 20px 20px 20px',
			items : [{
						xtype : 'numberfield',
						fieldLabel : '请选择年份',
						value : '2010',
						minValue : 1900,
						maxValue : 2100,
						id : 'twoYear',
						name : 'twoYear',
						width : 200
					}, {
						xtype : 'combo',
						fieldLabel : '请选择月份',
						// emptyText : '一月',
						width : 150,
						name : 'number',
						triggerAction : 'all',
						displayField : 'moon',
						valueField : 'value',
						hiddenName : 'number',
						mode : 'local',
						forceSelection : true,

						// allowBlank : false,
						fields : ['value', 'moon'],
						store : [[1, '一月'], [2, '二月'], [3, '三月'], [4, '四月'],
								[5, '五月'], [6, '六月'], [7, '七月'], [8, '八月'],
								[9, '九月'], [10, '十月'], [11, '十一月'], [12, '十二月']]

					}, {
						xtype : 'button',
						text : '生成月报表',
						handler : function() {
							moonform.form.submit();
						}
					}]
		}

		]

	});

	var weekform = new Ext.form.FormPanel({
				title : '周报表',
				collapsed : true,
				collapsible : true,
				autoHeight : true,
				onSubmit : Ext.emptyFn, 
		    	submit : function() {  
		    		var temp = Ext.get('reportTemplate').getValue();
		    		if(temp==''){
		    			alert("报表模板不能为空！");
		    			return;
		    		}   
			    	this.getEl().dom.action ='outputSelfReport.do?type=week&year='
			    								+Ext.get('threeYear').getValue()
			    								+'&template='+Ext.get('reportTemplate').getValue();
			    	this.getEl().dom.method = 'post';      
			    	this.getEl().dom.submit();      
		    	},
				items : [{
					xtype : 'fieldset',
					autoHeight : true,
					bodyStyle : 'padding:20px 20px 20px 20px',
					items : [{
								xtype : 'numberfield',
								fieldLabel : '请选择年份',
								value : '2010',
								id : 'threeYear',
								name : 'threeYear',
								minValue : 1900,
								maxValue : 2100,
								width : 200
							}, {
								xtype : 'numberfield',
								fieldLabel : '请选择周次',
								minValue : 1,
								name : 'number',
								maxValue : 55,
								width : 200
							}, {
								xtype : 'button',
								text : '生成周报表',
								handler : function() {
									weekform.form.submit();
								}
							}]
				}]

			});
	var reportmanager = new Ext.form.FormPanel({
				title : '已有模板列表',
				autoHeight : true,
				collapsed : true,
				collapsible : true,
				defaultType : 'textfield',
				onSubmit : Ext.emptyFn, 
		    	submit : function() {  
		    		var test = Ext.get('saveBtn').getValue() + 'Action.do'
			    	this.getEl().dom.action = test;
			    	this.getEl().dom.method = 'post';      
			    	this.getEl().dom.submit();      
		    	},
				items : [{
						xtype : 'combo',
						width : 200,
						triggerAction : 'all',
						id:'saveBtn',
						name : 'submitNodes',
						displayField : 'createdFile',
						valueField : 'createdFile',
						hiddenName : 'submitNodes',
						forceSelection : true,
						editable : false,
						mode : 'remote',
						fieldLabel : '选择模板',
						store : usedtemplateStore
					}, {
					xtype : 'fieldset',
					autoHeight : true,
					border : false,
					items : [{
								xtype : 'button',
								text : '另存为',
								handler : function() {
									reportmanager.form.submit();
								}
							}]
				}]
			});
	// 报表删除模块.以后可以合并生成,修改等功能.
	var reportmodify = new Ext.form.FormPanel({
				title : '报表管理',
				autoHeight : true,
				collapsed : true,
				collapsible : true,
				defaults : {
	
				},
				defaultType : 'textfield',
				
				items : [{
					xtype : 'combo',
					width : 200,
					triggerAction : 'all',
					id:'chosedNode',
					name : 'deleteNode',
					displayField : 'createdFile',
					valueField : 'createdFile',
					hiddenName : 'deleteNode',
					forceSelection : true,
					editable : false,
					mode : 'remote',
					fieldLabel : '选择模板',
					store : usedtemplateStore
					},
					{
					xtype : 'fieldset',
					autoHeight : true,
					border : false,
					defaultType : 'radio',
					items : [{
								xtype : 'button',
								text : '删除',
								handler : function() {
									Ext.Ajax.request({
												url : "deleteReport.do",
												params : {
													deleteNode : Ext.get('chosedNode').getValue()
												},
												success : function(result,
														request) {
													Ext.Msg.alert('恭喜', '成功！');
													usedtemplateStore.reload();
												},
												failure : function(result,
														request) {
													Ext.Msg.alert('失败', '失败');

												}

											});
								}

							}]
				}

				]
			});

	var fsfs = new Ext.Panel({
				labelWidth : 200,
				width : 840,
				height : document.body.clientHeight * 0.95 + 5,
				autoScroll : true,
				frame : true,
				title : '报表生成',

				bodyStyle : 'padding:20px 20px 20px 20px',

				items : [{
							xtype : 'fieldset',
							title : '生成报表',

							autoHeight : true,

							defaultType : 'textfield',

							items : [{
										xtype : 'combo',
										width : 200,
										triggerAction : 'all',
										id : 'reportTemplate',
										//name : 'reportTemplate',
										displayField : 'myTemplate',
										valueField : 'id',
										hiddenName : 'reportTemplateTemp',
										forceSelection : true,
										editable : false,
										fieldLabel : '选择模板',
										store : templateStore,
										mode : 'remote'
									}, {
										xtype : 'fieldset',
										title : '选择报表类型',
										
										id : 'reportid',
										autoHeight : true,
										labelWidth : 200,

										frame : true,

										items : [yearform, moonform, weekform]

									}]

						}, {
							xtype : 'fieldset',
							title : '',

							autoHeight : true,
							labelWidth : 200,

							frame : true,

							items : [reportmanager]

						}, {

							xtype : 'fieldset',
							title : '',

							autoHeight : true,
							labelWidth : 200,

							frame : true,

							items : [reportmodify]

						}

				// over

				]
			});

	fsfs.render('showGrid');

});