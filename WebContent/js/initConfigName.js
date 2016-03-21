

Ext.onReady(function() {

	Ext.QuickTips.init();

	Ext.form.Field.prototype.msgTarget = 'side';

	var fsfs = new Ext.Panel({
				labelWidth : 200,
				width : 840,
				height : document.body.clientHeight * 0.95 + 5,
				autoScroll : true,
				frame : true,
				title : '系统定制',

				bodyStyle : 'padding:20px 20px 20px 20px',

				items : [
					
						
							
								{
							xtype : 'fieldset',
							title : '系统标题',

							autoHeight : true,

							defaultType : 'textfield',

							items : [
								
//									{
//						xtype : 'combo',
//						fieldLabel : '系统属性',
//						name:'systembelong',
//						triggerAction : 'all',
//						displayField : 'systemused',
//						valueField : 'value',
//						hiddenName : 'systembelong',
//						mode : 'local',
//						value :systemid,
//						forceSelection : true,
//						allowBlank : false,
//						fields : ['value', 'systemused'],
//						store : [['主干网', '主干网'], ['校园网', '校园网']]
//
//					},
						
							{
										fieldLabel : '主标题',
								minValue : 1,
								id : 'topname',
								value:systemtopname,
								width : 300
									}
//									,
//									{
//									fieldLabel : '底部标题',
//								minValue : 1,
//								id : 'downname',
//								value:systemdownname,
//								width : 300
//
//									}
									]

						},{
								xtype : 'button',
								text : '提交',
								handler : function() {
									Ext.Ajax.request({
												url : "json/updatainitname.do",
												params : {
									//				inputid : Ext.get('systembelong').getValue()
									//			,
											inputtopname : Ext.get('topname').getValue()
									//			,
									//				inputdowname : Ext.get('downname').getValue()
												},
												success : function(result,
														request) {
													Ext.Msg.alert('成功', '成功！请按刷新更新页面');
												},
												failure : function(result,
														request) {
													Ext.Msg.alert('失败', '失败');

												}

											});
								}

							}

				// over

				]
			});

	fsfs.render('showGrid');

});