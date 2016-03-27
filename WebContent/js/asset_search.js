Ext.onReady(function() {
	// 数据读取

	// 管理员数据

	getUserStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : "json/listAssetUserName.do"

						}),
				reader : new Ext.data.JsonReader({
							root : "assetUserNames",
							fields : ['id', 'userName']
						})
			});
	getUserStore.load();
	// 部门数据

	getDepartStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : "json/listAssetDepartName.do"
						}),
				reader : new Ext.data.JsonReader({
							root : "assetDepartNames",

							fields : ['id', 'name']
						})
			});
	getDepartStore.load();

	// 录入窗口生成
	
	
	

	var addnewdepart = new Ext.form.FormPanel({

			//	title : '录入新设备',
				height : 300,
				autoScroll : true,
				defaults : {
					labelWidth : 200
				},
               items: [{
              xtype:'tabpanel',
               activeTab: 0,
               defaults:{autoHeight:true, bodyStyle:'padding:10px'}, 

             items:[{
                title:'内容1',
                 layout:'form',
                defaults: {width: 330},
                defaultType: 'textfield',

				items : [{
							xtype : 'textfield',	
							fieldLabel : '设备编号',	allowBlank : false,
							name : 'assetInfo.deviceNumber',
                            regex: /^[0-9]+$/,
                            regexText:'只能输入整数格式',
							blankText : "不能为空！"
						}, {
							fieldLabel : '设备分类号',	allowBlank : false,
							name : 'assetInfo.deviceClasses',

							blankText : "不能为空！"
						}, {

							xtype : 'combo',
							EmptyText : '请选择部门',

							triggerAction : 'all',
							forceSelection : true,
							editable : false,
							mode : 'remote',
							fieldLabel : '使用单位ID',	allowBlank : false,
							name : 'assetInfo.departmentId',
							hiddenName : 'assetInfo.departmentId',

							displayField : 'name',
							valueField : 'id',
							store : getDepartStore

						}, {
							fieldLabel : '设备分类',	allowBlank : false,
							name : 'assetInfo.deviceName',

							blankText : "不能为空！"
						}, {
							fieldLabel : '设备详细配置',	allowBlank : false,
							name : 'assetInfo.deviceConfig',

							blankText : "不能为空！"
						}, {
							fieldLabel : '设备型号',	allowBlank : false,
							name : 'assetInfo.deviceModel',

							blankText : "不能为空！"
						}, {
							fieldLabel : '购置金额',	allowBlank : false,
							name : 'assetInfo.purchaseMoney',
                            regex: /^[0-9]+$/,
                            regexText:'只能输入整数格式',
							blankText : "不能为空！"
						}, {
							fieldLabel : '厂家',	allowBlank : false,
							name : 'assetInfo.deviceVender',

							blankText : "不能为空！"
						}]
             }, 
             
							{
                title:'内容2',
                layout:'form',
                defaults: {width: 330},
                defaultType: 'textfield',

				items : [{
							fieldLabel : '出厂号',	allowBlank : false,
							name : 'assetInfo.produceNumber',

							blankText : "不能为空！"
						}, {
							fieldLabel : '购置日期',	allowBlank : false,
							name : 'assetInfo.purchaseDate',

							blankText : "不能为空！"
						}, {
							fieldLabel : '建帐日期',	allowBlank : false,
							name : 'assetInfo.establishDate',

							blankText : "不能为空！"
						}, {
							fieldLabel : '设备用途',	allowBlank : false,
							name : 'assetInfo.deviceUses',

							blankText : "不能为空！"
						}, {
							fieldLabel : '域名',	allowBlank : false,
							name : 'assetInfo.domainName',

							blankText : "不能为空！"
						}, {
							fieldLabel : 'IP地址',	allowBlank : false,
							name : 'assetInfo.deviceIP',

							blankText : "不能为空！"
						}, {

							name : 'assetInfo.adminId',
							xtype : 'combo',
							EmptyText : '请选择管理员',

							triggerAction : 'all',
							forceSelection : true,
							editable : false,
							mode : 'remote',
							fieldLabel : '管理员ID',	allowBlank : false,
							hiddenName : 'assetInfo.adminId',
							displayField : 'userName',
							valueField : 'id',
							store : getUserStore

						},{

							name : 'assetInfo.backupAdminId',
							xtype : 'combo',
							EmptyText : '备份管理员ID',
							triggerAction : 'all',
							forceSelection : true,
							editable : false,
							mode : 'remote',
							fieldLabel : '备份管理员ID',	allowBlank : false,
							hiddenName : 'assetInfo.backupAdminId',
							displayField : 'userName',
							valueField : 'id',
							store : getUserStore

						}]
							},{
                title:'内容3',
                layout:'form',
                defaults: {width: 330},
                defaultType: 'textfield',

				items : [  {
							fieldLabel : '设备归属',	allowBlank : false,
							name : 'assetInfo.ownAdminId',

							blankText : "不能为空！"
						}, {
							fieldLabel : '机位',	allowBlank : false,
							name : 'assetInfo.deviceLocation',

							blankText : "不能为空！"
						}, {
							fieldLabel : '机位（排列）',	allowBlank : false,
							name : 'assetInfo.deviceArrange',

							blankText : "不能为空！"
						}, {
							fieldLabel : '设备状态',	allowBlank : false,
							name : 'assetInfo.deviceStatus',

							blankText : "不能为空！"
						}, {
							fieldLabel : '维护记录',	
							name : 'assetInfo.maintainRecord',

							blankText : "不能为空！"
						}, {
							fieldLabel : '报废日期',	
							name : 'assetInfo.discardDate',

							blankText : "不能为空！"
						}, {
							fieldLabel : '经费来源',	allowBlank : false,
							name : 'assetInfo.fundSource',

							blankText : "不能为空！"
						}, {
							fieldLabel : '备注',	
							name : 'assetInfo.description',

							blankText : "不能为空！"
						}]
							
               }]
	}],

				buttons : [{
							text : '  保存      ',
							handler : function() {
								if (addnewdepart.getForm().isValid()) {
									addnewdepart.getForm().submit({
												url : "json/addAssetInfo.do",

												success : function(form, action) {
													Ext.Msg.alert("", "成功!")
													addnewdepartWin.hide();
													asset_search_Store.reload();

												},
												failure : function(form, action) {
													Ext.Msg.alert("",
															"该名已经存在，请重新添加！");
												}
											});
								}
							}
						}]
			});
	// 添加弹出窗口
	var addnewdepartWin = new Ext.Window({
				width : 700,
				height : 400,
				modal : true,
				// layout : 'fit',
				autoScroll : true,
				closable : true,
				closeAction : 'hide',

				frame : true,
				title : '添加设备',
				items : [addnewdepart]
			});

	// 资产管理数据
	var asset_search_reader = new Ext.data.JsonReader({
				root : "assetLists",
				totalProperty : 'totalCount',
				fields : ['id', 'deviceNumber', 'deviceName', 'deviceClasses',
						'departmentName', 'deviceConfig', 'deviceModel',
						'purchaseMoney', 'deviceVender', 'produceNumber',
						'purchaseDate', 'deviceUses', 'domainName',
						'establishDate', 'adminName', 'ownAdminId',
						'backupAdminName', 'deviceLocation', 'maintainRecord',
						'discardDate', 'fundSource', 'description', 'deviceIP',
						'deviceArrange', 'deviceStatus']
			});
	var asset_search_proxy = new Ext.data.HttpProxy({
				url : "json/listAssetInfo.do"
			});

	var asset_search_sm = new Ext.grid.CheckboxSelectionModel();
	asset_search_Store = new Ext.data.Store({
				proxy : asset_search_proxy,
				reader : asset_search_reader
			});
	asset_search_Store.setDefaultSort('deviceName', 'DESC');
	asset_search_Store.load({
				params : {
					start : 0,
					limit : 28
				}
			});

	var asset_search_Panel = new Ext.grid.GridPanel({
		store : asset_search_Store,
		height : document.body.clientHeight * 0.55 + 5,
		width : 820,
		autoScroll : true,
		title : '设备列表',
		columns : [new Ext.grid.RowNumberer(), asset_search_sm, {
					id : "sd2",
					header : "设备编号",
					sortable : true,width : 120,
					dataIndex : 'deviceNumber'
				}, {
					header : "设备类型",
					// width : 200,
					sortable : true,
					dataIndex : 'deviceClasses'
				}, {
					header : "设备名称",
					// width : 300,
					sortable : true,
					dataIndex : 'deviceName'
				}, {
					header : "使用单位",
					// width : 200,
					sortable : true,
					dataIndex : 'departmentName'
				}, {
					header : "设备详细配置",
					// width : 300,
					sortable : true,
					dataIndex : 'deviceConfig'
				}, {
					header : "设备型号",
					// width : 200,
					sortable : true,
					dataIndex : 'deviceModel'
				}, {
					header : "购置金额",
					// width : 300,
					sortable : true,
					dataIndex : 'purchaseMoney'
				}, {
					header : "厂家",
					// width : 200,
					sortable : true,
					dataIndex : 'deviceVender'
				}, {
					header : "出厂号",
					// width : 300,
					sortable : true,
					dataIndex : 'produceNumber'
				}, {
					header : "购置日期",
					// width : 300,
					sortable : true,
					dataIndex : 'purchaseDate'
				}, {
					header : "建帐日期",
					// width : 200,
					sortable : true,
					dataIndex : 'establishDate'
				}, {
					header : "设备用途",
					// width : 300,
					sortable : true,
					dataIndex : 'deviceUses'
				}, {
					header : "域名",
					// width : 200,
					sortable : true,
					dataIndex : 'domainName'
				}, {
					header : "IP地址",
					// width : 300,
					sortable : true,
					dataIndex : 'deviceIP'
				}, {
					header : "管理员",
					// width : 300,
					sortable : true,
					dataIndex : 'adminName'
				}, {
					header : "备份管理员",
					// width : 200,
					sortable : true,
					dataIndex : 'backupAdminName'
				}, {
					header : "设备归属",
					// width : 300,
					sortable : true,
					dataIndex : 'ownAdminId'
				}, {
					header : "机位",
					// width : 300,
					sortable : true,
					dataIndex : 'deviceLocation'
				}, {
					header : "机位（排列）",
					// width : 200,
					sortable : true,
					dataIndex : 'deviceArrange'
				}, {
					header : "设备状态",
					// width : 300,
					sortable : true,
					dataIndex : 'deviceStatus'
				}, {
					header : "维护记录",
					// width : 200,
					sortable : true,
					dataIndex : 'maintainRecord'
				}, {
					header : "报废日期",
					// width : 300,
					sortable : true,
					dataIndex : 'discardDate'
				}, {
					header : "经费来源",
					// width : 200,
					sortable : true,
					dataIndex : 'fundSource'
				}, {
					header : "备注",
					// width : 300,
					sortable : true,
					dataIndex : 'description'
				}],
		sm : asset_search_sm,
		stripeRows : true,
		frame : true,
		autoExpandColumn : 'sd2',
		tbar : ['->', {
					text : "录入新设备",
					scope : this,
					handler : function() {

						addnewdepartWin.show();
					}

				}, '->', '-', {
					id : "deleteButton",
					text : "删除",
					handler : function() {
						var ids = "";
						var record = "";
						var selected = asset_search_sm.getSelections();
						if (selected.length == 0) {
							Ext.Msg.alert("", "请选择！");
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
								url : "json/deleteAssetInfo.do",
								params : {
									assetInfoIds : ids
								},
								success : function(response, request) {
									if (response.responseText == "ok") {
										for (var i = 0; i < selected.length; i++) {
											record = selected[i];
											asset_search_Store.remove(record);
										}
										Ext.Msg.alert("", "删除成功！");
									} else {
										Ext.Msg.alert("", "删除失败! 你没有操作权限");
									}
								}
							});
						}
					}
				}],
		bbar : new Ext.PagingToolbar({
					pageSize : 28,
					store : asset_search_Store,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
					emptyMsg : '没有数据'
				})
	});

	var searchbutton = new Ext.Button({
		text : "查询",
		scope : this,
		handler : function() {
			var sdeviceNumber = Ext.get('getdeviceNumber').dom.value;
			var sdeviceClasses = Ext.get('getdeviceClasses').dom.value;
			var sdeviceName = Ext.get('getdeviceName').dom.value;
			var sdepartmentId = Ext.get('unitName').dom.value;
			var sdeviceConfig = Ext.get('getdeviceConfig').dom.value;
			var sdeviceModel = Ext.get('getdeviceModel').dom.value;
			var spurchaseMoney = Ext.get('getpurchaseMoney').dom.value;
			var sdeviceVender = Ext.get('getdeviceVender').dom.value;
			var sproduceNumber = Ext.get('getproduceNumber').dom.value;
			var spurchaseDate = Ext.get('getpurchaseDate').dom.value;
			var sdeviceUses = Ext.get('getdeviceUses').dom.value;
			var sdomainName = Ext.get('getdomainName').dom.value;
			var sestablishDate = Ext.get('getestablishDate').dom.value;
			var sdiscardDate = Ext.get('getdiscardDate').dom.value;
			var sadminId = Ext.get('userName_id').dom.value;
			var sownAdminId = Ext.get('getownAdminId').dom.value;
			var sbackupAdminId = Ext.get('bakuserName_id').dom.value;
			var sdeviceLocation = Ext.get('getdeviceLocation').dom.value;
			var smaintainRecord = Ext.get('getmaintainRecord').dom.value;
			var sfundSource = Ext.get('getfundSource').dom.value;
			var sdescription = Ext.get('getdescription').dom.value;
			var sdeviceIP = Ext.get('getdeviceIP').dom.value;
			var sdeviceArrange = Ext.get('getdeviceArrange').dom.value;
			var sdeviceStatus = Ext.get('getdeviceStatus').dom.value;

			get_asset_search_Store = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
									url : "json/listAssetInfoQuery.do"
								}),
						reader : new Ext.data.JsonReader({
									root : "assetInfoQuerys",
									totalProperty : 'totalCount',
									fields : ['id', 'deviceNumber',
											'deviceClasses', 'deviceName',
											'departmentId', 'deviceConfig',
											'deviceModel', 'purchaseMoney',
											'deviceVender', 'produceNumber',
											'purchaseDate', 'deviceUses',
											'domainName', 'establishDate',
											'adminId', 'ownAdminId',
											'backupAdminId', 'deviceLocation',
											'maintainRecord', 'discardDate',
											'fundSource', 'description',
											'deviceIP', 'deviceArrange',
											'deviceStatus']
								})
					});
			get_asset_search_Store.setDefaultSort('deviceName', 'DESC');
			get_asset_search_Store.load({
						params : {
							deviceNumber : sdeviceNumber,
							deviceClasses : sdeviceClasses,
							deviceName : sdeviceName,
							departmentId : sdepartmentId,
							deviceConfig : sdeviceConfig,
							deviceModel : sdeviceModel,
							purchaseMoney : spurchaseMoney,
							deviceVender : sdeviceVender,
							produceNumber : sproduceNumber,
							purchaseDate : spurchaseDate,
							deviceUses : sdeviceUses,
							domainName : sdomainName,
							establishDate : sestablishDate,
							adminId : sadminId,
							ownAdminId : sownAdminId,
							backupAdminId : sbackupAdminId,
							deviceLocation : sdeviceLocation,
							maintainRecord : smaintainRecord,
							discardDate : sdiscardDate,
							fundSource : sfundSource,
							description : sdescription,
							deviceIP : sdeviceIP,
							deviceArrange : sdeviceArrange,
							deviceStatus : sdeviceStatus
						}
					});

			var asset_search_smgrid = new Ext.grid.CheckboxSelectionModel();
			var searchwinshowgrid = new Ext.grid.GridPanel({
				store : get_asset_search_Store,
			    height : document.body.clientHeight * 0.90 + 5,
				width : 800,

				autoScroll : true,
				title : '设备列表',
				columns : [new Ext.grid.RowNumberer(), asset_search_smgrid, {
							id : "sd3",
							header : "设备编号",
							// width : 110,
							sortable : true,
							dataIndex : 'deviceNumber'
						}, {
							header : "设备类型",
							// width : 200,
							sortable : true,
							dataIndex : 'deviceClasses'
						}, {
							header : "设备名称",
							// width : 300,
							sortable : true,
							dataIndex : 'deviceName'
						}, {
							header : "使用单位ID",
							// width : 200,
							sortable : true,
							dataIndex : 'departmentId'
						}, {
							header : "设备详细配置",
							// width : 300,
							sortable : true,
							dataIndex : 'deviceConfig'
						}, {
							header : "设备型号",
							// width : 200,
							sortable : true,
							dataIndex : 'deviceModel'
						}, {
							header : "购置金额",
							// width : 300,
							sortable : true,
							dataIndex : 'purchaseMoney'
						}, {
							header : "厂家",
							// width : 200,
							sortable : true,
							dataIndex : 'deviceVender'
						}, {
							header : "出厂号",
							// width : 300,
							sortable : true,
							dataIndex : 'produceNumber'
						}, {
							header : "购置日期",
							// width : 300,
							sortable : true,
							dataIndex : 'purchaseDate'
						}, {
							header : "建帐日期",
							// width : 200,
							sortable : true,
							dataIndex : 'establishDate'
						}, {
							header : "设备用途",
							// width : 300,
							sortable : true,
							dataIndex : 'deviceUses'
						}, {
							header : "域名",
							// width : 200,
							sortable : true,
							dataIndex : 'domainName'
						}, {
							header : "IP地址",
							// width : 300,
							sortable : true,
							dataIndex : 'deviceIP'
						}, {
							header : "管理员ID",
							// width : 300,
							sortable : true,
							dataIndex : 'adminId'
						}, {
							header : "备份管理员ID",
							// width : 200,
							sortable : true,
							dataIndex : 'backupAdminId'
						}, {
							header : "设备归属",
							// width : 300,
							sortable : true,
							dataIndex : 'ownAdminId'
						}, {
							header : "机位",
							// width : 300,
							sortable : true,
							dataIndex : 'deviceLocation'
						}, {
							header : "机位（排列）",
							// width : 200,
							sortable : true,
							dataIndex : 'deviceArrange'
						}, {
							header : "设备状态",
							// width : 300,
							sortable : true,
							dataIndex : 'deviceStatus'
						}, {
							header : "维护记录",
							// width : 200,
							sortable : true,
							dataIndex : 'maintainRecord'
						}, {
							header : "报废日期",
							// width : 300,
							sortable : true,
							dataIndex : 'discardDate'
						}, {
							header : "经费来源",
							// width : 200,
							sortable : true,
							dataIndex : 'fundSource'
						}, {
							header : "备注",
							// width : 300,
							sortable : true,
							dataIndex : 'description'
						}],
				sm : asset_search_smgrid,
				stripeRows : true,
				frame : true,
				autoExpandColumn : 'sd3',
				tbar : ['->', '->', '-', {
					id : "deleteButton",
					text : "删除",
					handler : function() {
						var ids = "";
						var record = "";
						var selected = asset_search_smgrid.getSelections();
						if (selected.length == 0) {
							Ext.Msg.alert("", "请选择！");
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
								url : "json/deleteAssetInfo.do",
								params : {
									assetInfoIds : ids
								},
								success : function(response, request) {
									if (response.responseText == "ok") {
										for (var i = 0; i < selected.length; i++) {
											record = selected[i];
											get_asset_search_Store
													.remove(record);
											asset_search_Store.reload();
										}
										Ext.Msg.alert("", "删除成功！");
									} else {
										Ext.Msg.alert("", "删除失败! 你没有操作权限");
									}
								}
							});
						}
					}
				}]
			});

			var searchwinshow = new Ext.Window({
					//	width : document.body.clientHeight * 0.90 + 10,
					//	height : 600,
						autoScroll : true,
						closable : true,
						closeAction : 'hide',
						plain : true,
						frame : true,
						modal : true,
						title : '查询结果',
					
						buttonAlign : 'center',
						items : [searchwinshowgrid

						]
					});

			searchwinshow.show();

			searchwinshowgrid.addListener('rowdblclick', rowClickFn);
			function rowClickFn(grid, rowIndex) {
				var ts = searchwinshowgrid.getStore();
				var ttps = ts.getAt(rowIndex);

				var psassetmodifyid = ttps.get("id");
				var psdeviceNumber = ttps.get('deviceNumber');
				var psdeviceClasses = ttps.get('deviceClasses');
				var psdeviceName = ttps.get('deviceName');
				var psdepartmentId = ttps.get('departmentId');
				var psdeviceConfig = ttps.get('deviceConfig');
				var psdeviceModel = ttps.get('deviceModel');
				var pspurchaseMoney = ttps.get('purchaseMoney');
				var psdeviceVender = ttps.get('deviceVender');
				var psproduceNumber = ttps.get('produceNumber');
				var pspurchaseDate = ttps.get('purchaseDate');
				var psdeviceUses = ttps.get('deviceUses');
				var psdomainName = ttps.get('domainName');
				var psestablishDate = ttps.get('establishDate');
				var psdiscardDate = ttps.get('discardDate');
				var psadminId = ttps.get('adminId');
				var psownAdminId = ttps.get('ownAdminId');
				var psbackupAdminId = ttps.get('backupAdminId');
				var psdeviceLocation = ttps.get('deviceLocation');
				var psmaintainRecord = ttps.get('maintainRecord');
				var psfundSource = ttps.get('fundSource');
				var psdescription = ttps.get('description');
				var psdeviceIP = ttps.get('deviceIP');
				var psdeviceArrange = ttps.get('deviceArrange');
				var psdeviceStatus = ttps.get('deviceStatus');
                 var modifyassetsearnWin = new Ext.Window({
							width : 800,
							height : document.body.clientHeight * 0.80 + 5,

							autoScroll : true,
							closable : true,
							// closeAction : 'hide',
							plain : true,
							frame : true,
							modal : true,
							title : '部门信息',
						
							buttonAlign : 'center',
							items : new Ext.FormPanel({
					labelWidth : 75,
					frame : true,
					title : '',
					bodyStyle : 'padding:5px 5px 0',
					width : 820,
				layout : 'form',
				defaultType : 'textfield',

				defaults : {
					labelWidth : 200,
					width : 500
				},
					
					items : [{
								xtype : 'textfield',
								fieldLabel : '设备编号',
								name : 'modifydeviceNumber',
								hiddenName : 'modifydeviceNumber',
								id : 'modifydeviceNumber',
								value : psdeviceNumber,

								blankText : "不能为空！"
							}, {
								fieldLabel : '设备分类号',
								name : 'modifydeviceClasses',
								hiddenName : 'modifydeviceClasses',
								id : 'modifydeviceClasses',
								value : psdeviceClasses,
								blankText : "不能为空！"
							}, {

								xtype : 'combo',

								value : psdepartmentId,
								triggerAction : 'all',
								forceSelection : true,
								editable : false,
								mode : 'remote',
								fieldLabel : '使用单位ID',
							// hiddenName : 'modifydepartmentId',
								name : 'modifydepartmentId',
								hiddenName : 'modifydepartmentId',

								displayField : 'name',
								valueField : 'id',
								store : getDepartStore

							}, {
								fieldLabel : '设备分类',
								name : 'modifydeviceName',
								hiddenName : 'modifydeviceName',
								id : 'modifydeviceName',
								value : psdeviceName,

								blankText : "不能为空！"
							}, {
								fieldLabel : '设备详细配置',
								name : 'modifydeviceConfig',
								hiddenName : 'modifydeviceConfig',
								id : 'modifydeviceConfig',
								value : psdeviceConfig,

								blankText : "不能为空！"
							}, {
								fieldLabel : '设备型号',
								name : 'modifydeviceModel',
								hiddenName : 'modifydeviceModel',
								id : 'modifydeviceModel',
								value : psdeviceModel,

								blankText : "不能为空！"
							}, {
								fieldLabel : '购置金额',
								name : 'modifypurchaseMoney',
								hiddenName : 'modifypurchaseMoney',
								id : 'modifypurchaseMoney',
								value : pspurchaseMoney,

								blankText : "不能为空！"
							}, {
								fieldLabel : '厂家',
								name : 'modifydeviceVender',
								hiddenName : 'modifydeviceVender',
								id : 'modifydeviceVender',
								value : psdeviceVender,

								blankText : "不能为空！"
							}, {
								fieldLabel : '出厂号',
								name : 'modifyproduceNumber',
								hiddenName : 'modifyproduceNumber',
								id : 'modifyproduceNumber',
								value : psproduceNumber,

								blankText : "不能为空！"
							}, {
								fieldLabel : '购置日期',
								name : 'modifypurchaseDate',
								hiddenName : 'modifypurchaseDate',
								id : 'modifypurchaseDate',
								value : pspurchaseDate,

								blankText : "不能为空！"
							}, {
								fieldLabel : '建帐日期',
								name : 'modifyestablishDate',
								hiddenName : 'modifyestablishDate',
								id : 'modifyestablishDate',
								value : psestablishDate,

								blankText : "不能为空！"
							}, {
								fieldLabel : '设备用途',
								name : 'modifydeviceUses',
								hiddenName : 'modifydeviceUses',
								id : 'modifydeviceUses',
								value : psdeviceUses,

								blankText : "不能为空！"
							}, {
								fieldLabel : '域名',
								name : 'modifydomainName',
								hiddenName : 'modifydomainName',
								id : 'modifydomainName',
								value : psdomainName,

								blankText : "不能为空！"
							}, {
								fieldLabel : 'IP地址',
								name : 'modifydeviceIP',
								hiddenName : 'modifydeviceIP',
								id : 'modifydeviceIP',
								value : psdeviceIP,

								blankText : "不能为空！"
							}, {

								name : 'modifyadminId',
							// id : 'modifyadminId',
								value : psadminId,
								xtype : 'combo',
								EmptyText : '请选择管理员',

								triggerAction : 'all',
								forceSelection : true,
								editable : false,
								mode : 'remote',
								fieldLabel : '管理员ID',
								hiddenName : 'modifyadminId',
								displayField : 'userName',
								valueField : 'id',
								store : getUserStore

							}, {

								name : 'modifybackupAdminId',
							// id : 'modifybackupAdminId',
								value : psbackupAdminId,
								xtype : 'combo',
								EmptyText : '备份管理员ID',
								triggerAction : 'all',
								forceSelection : true,
								editable : false,
								mode : 'remote',
								fieldLabel : '备份管理员ID',
								hiddenName : 'modifybackupAdminId',
								displayField : 'userName',
								valueField : 'id',
								store : getUserStore

							}, {
								fieldLabel : '设备归属',
								name : 'modifyownAdminId',
								hiddenName : 'modifyownAdminId',
								id : 'modifyownAdminId',
								value : psownAdminId,

								blankText : "不能为空！"
							}, {
								fieldLabel : '机位',
								name : 'modifydeviceLocation',
								hiddenName : 'modifydeviceLocation',
								id : 'modifydeviceLocation',
								value : psdeviceLocation,

								blankText : "不能为空！"
							}, {
								fieldLabel : '机位（排列）',
								name : 'modifydeviceArrange',
								hiddenName : 'modifydeviceArrange',
								id : 'modifydeviceArrange',
								value : psdeviceArrange,

								blankText : "不能为空！"
							}, {
								fieldLabel : '设备状态',
								name : 'modifydeviceStatus',
								hiddenName : 'modifydeviceStatus',
								id : 'modifydeviceStatus',
								value : psdeviceStatus,

								blankText : "不能为空！"
							}, {
								fieldLabel : '维护记录',
								name : 'modifymaintainRecord',
								hiddenName : 'modifymaintainRecord',
								id : 'modifymaintainRecord',
								value : psmaintainRecord,

								blankText : "不能为空！"
							}, {
								fieldLabel : '报废日期',
								name : 'modifydiscardDate',
								hiddenName : 'modifydiscardDate',
								id : 'modifydiscardDate',
								value : psdiscardDate,

								blankText : "不能为空！"
							}, {
								fieldLabel : '经费来源',
								name : 'modifyfundSource',
								hiddenName : 'modifyfundSource',
								id : 'modifyfundSource',
								value : psfundSource,

								blankText : "不能为空！"
							}, {
								fieldLabel : '备注',
								name : 'modifydescription',
								hiddenName : 'modifydescription',
								id : 'modifydescription',
								value : psdescription,

								blankText : "不能为空！"
							}],

					buttons : [{
						text : '  保存      ',
						handler : function() {
							var mosdeviceNumber = Ext.get('modifydeviceNumber').dom.value;
							var mosdeviceClasses = Ext
									.get('modifydeviceClasses').dom.value;
							var mosdeviceName = Ext.get('modifydeviceName').dom.value;
							var mosdepartmentId = Ext.get('modifydepartmentId').dom.value;
							var mosdeviceConfig = Ext.get('modifydeviceConfig').dom.value;
							var mosdeviceModel = Ext.get('modifydeviceModel').dom.value;
							var mospurchaseMoney = Ext
									.get('modifypurchaseMoney').dom.value;
							var mosdeviceVender = Ext.get('modifydeviceVender').dom.value;
							var mosproduceNumber = Ext
									.get('modifyproduceNumber').dom.value;
							var mospurchaseDate = Ext.get('modifypurchaseDate').dom.value;
							var mosdeviceUses = Ext.get('modifydeviceUses').dom.value;
							var mosdomainName = Ext.get('modifydomainName').dom.value;
							var mosestablishDate = Ext
									.get('modifyestablishDate').dom.value;
							var mosdiscardDate = Ext.get('modifydiscardDate').dom.value;
							var mosadminId = Ext.get('modifyadminId').dom.value;
							var mosownAdminId = Ext.get('modifyownAdminId').dom.value;
							var mosbackupAdminId = Ext.get('modifybackupAdminId').dom.value;
							var mosdeviceLocation = Ext
									.get('modifydeviceLocation').dom.value;
							var mosmaintainRecord = Ext
									.get('modifymaintainRecord').dom.value;
							var mosfundSource = Ext.get('modifyfundSource').dom.value;
							var mosdescription = Ext.get('modifydescription').dom.value;
							var mosdeviceIP = Ext.get('modifydeviceIP').dom.value;
							var mosdeviceArrange = Ext
									.get('modifydeviceArrange').dom.value;
							var mosdeviceStatus = Ext.get('modifydeviceStatus').dom.value;
							Ext.Ajax.request({
										url : "json/modifyAssetInfo.do",
										params : {
											assetInfoId:psassetmodifyid,
											deviceNumber : mosdeviceNumber,
											deviceClasses : mosdeviceClasses,
											deviceName : mosdeviceName,
											departmentId : mosdepartmentId,
											deviceConfig : mosdeviceConfig,
											deviceModel : mosdeviceModel,
											purchaseMoney : mospurchaseMoney,
											deviceVender : mosdeviceVender,
											produceNumber : mosproduceNumber,
											purchaseDate : mospurchaseDate,
											deviceUses : mosdeviceUses,
											domainName : mosdomainName,
											establishDate : mosestablishDate,
											adminId : mosadminId,
											ownAdminId : mosownAdminId,
											backupAdminId : mosbackupAdminId,
											deviceLocation : mosdeviceLocation,
											maintainRecord : mosmaintainRecord,
											discardDate : mosdiscardDate,
											fundSource : mosfundSource,
											description : mosdescription,
											deviceIP : mosdeviceIP,
											deviceArrange : mosdeviceArrange,
											deviceStatus : mosdeviceStatus

										},
										success : function(response, request) {
											if (response.responseText == "ok") {
												modifyassetsearnWin.close();
												get_asset_search_Store.reload();
												asset_search_Store.reload();

												Ext.Msg.alert("", "成功！");
											} else {
												modifyassetsearnWin.close();
												Ext.Msg
														.alert("",
																"失败! 你没有操作权限");
											}
										}
									});

						}
					}]
					})
				});
                
				
				modifyassetsearnWin.show();
			}

		}
	});

	var searchfield1 = new Ext.form.FieldSet({
				labelWidth : 80,
				layout : 'column',
				xtypy : 'fieldset',
				title : '资产查询',
				autoHeight : true,

				items : [{
							columnWidth : .25,
							layout : 'form',
							items : [{
								xtype : 'textfield',
								fieldLabel : '设备编号',
								width : 80,
								id : 'getdeviceNumber',
								name : 'deviceNumber'
									// anchor:'95%'
								}, {
								xtype : 'textfield',
								fieldLabel : '设备分类号',
								width : 80,
								id : 'getdeviceClasses',
								name : 'deviceClasses'
									// anchor:'95%'
								}, {
								xtype : 'textfield',
								fieldLabel : '设备名称',
								width : 80,
								id : 'getdeviceName',
								name : 'deviceName'
									// anchor:'95%'
								}]
						}, {
							columnWidth : .25,
							layout : 'form',
							items : [{
										xtype : 'combo',
										EmptyText : '请选择部门',
										width : 100,
										triggerAction : 'all',
										forceSelection : true,
										editable : false,
										mode : 'remote',
										fieldLabel : '使用单位ID',
										id : 'getdepartmentId',
										name : 'unitName',
										hiddenName : 'unitName',
										displayField : 'name',
										valueField : 'id',
										store : getDepartStore

									}, {
										xtype : 'textfield',
										fieldLabel : '设备详细配置',
										width : 80,
										id : 'getdeviceConfig',
										name : 'deviceConfig'

									}, {
										xtype : 'textfield',
										fieldLabel : '设备型号',
										width : 80,
										id : 'getdeviceModel',
										name : 'deviceModel'
										// anchor:'95%'
								}]
						}, {
							columnWidth : .25,
							layout : 'form',
							items : [{
										xtype : 'textfield',
										fieldLabel : '购置金额',
										width : 80,
										id : 'getpurchaseMoney',
										name : 'purchaseMoney'

									}, {
										xtype : 'textfield',
										fieldLabel : '厂家',
										width : 80,
										id : 'getdeviceVender',
										name : 'deviceVender'
										// anchor:'95%'
								}	, {
										xtype : 'textfield',
										fieldLabel : '出厂号',
										width : 80,
										id : 'getproduceNumber',
										name : 'produceNumber'
										// anchor:'95%'
								}]
						}, {
							columnWidth : .25,
							layout : 'form',
							items : [{
								xtype : 'textfield',
								fieldLabel : '购置日期',
								width : 80,
								id : 'getpurchaseDate',
								name : 'purchaseDate'
									// anchor:'95%'
								}, {
								xtype : 'textfield',
								fieldLabel : '建帐日期',
								width : 80,
								id : 'getestablishDate',
								name : 'establishDate'

									// anchor:'95%'
								}, {
								xtype : 'textfield',
								fieldLabel : '设备用途',
								width : 80,
								id : 'getdeviceUses',
								name : 'deviceUses'
									// anchor:'95%'
								}]
						}]

			});
	var searchfield2 = new Ext.form.FieldSet({
				labelWidth : 80,
				layout : 'column',
				xtypy : 'fieldset',
				title : '高级资产查询',
				collapsed : true,

				autoHeight : true,
				checkboxToggle : true,

				defaults : {
					width : 100
				},
				items : [{
							columnWidth : .25,
							layout : 'form',
							items : [{
								xtype : 'textfield',
								fieldLabel : '域名',
								width : 80,
								id : 'getdomainName',
								name : 'domainName'
									// anchor:'95%'
								}, {
								xtype : 'textfield',
								fieldLabel : 'IP地址',
								width : 80,
								id : 'getdeviceIP',
								name : 'deviceIP'
									// anchor:'95%'
								}, {

								name : 'backupAdminId',
								xtype : 'combo',
								EmptyText : '备份管理员ID',
								width : 100,
								triggerAction : 'all',
								forceSelection : true,
								editable : false,
								mode : 'remote',
								fieldLabel : '备份管理员ID',
								// name : 'userName_id',
								name : 'bakuserName_id',
								hiddenName : 'bakuserName_id',

								displayField : 'userName',
								valueField : 'id',
								id : 'getbackupAdminId',
								store : getUserStore
									// anchor:'95%'
								}]
						}, {
							columnWidth : .25,
							layout : 'form',
							items : [{
								name : 'adminId',
								xtype : 'combo',
								EmptyText : '请选择管理员',
								width : 100,
								triggerAction : 'all',
								forceSelection : true,
								editable : false,
								mode : 'remote',
								fieldLabel : '管理员ID',
								// name : 'userName_id',
								name : 'userName_id',
								hiddenName : 'userName_id',
								displayField : 'userName',
								valueField : 'id',
								id : 'getadminId',
								store : getUserStore
									// anchor:'95%'
								}, {
								xtype : 'textfield',
								fieldLabel : '设备归属',
								width : 80,
								id : 'getownAdminId',
								name : 'ownAdminId'
									// anchor:'95%'
								}, {
								xtype : 'textfield',
								width : 80,
								id : 'getdeviceLocation',
								fieldLabel : '机位',
								name : 'deviceLocation'
									// anchor:'95%'
								}]
						}, {
							columnWidth : .25,
							layout : 'form',
							items : [{
								xtype : 'textfield',
								fieldLabel : '设备状态',
								width : 80,
								id : 'getdeviceStatus',
								name : 'deviceStatus'
									// anchor:'95%'
								}, {
								xtype : 'textfield',
								fieldLabel : '维护记录',
								width : 80,
								id : 'getmaintainRecord',
								name : 'maintainRecord'
									// anchor:'95%'
								}, {
								xtype : 'textfield',
								fieldLabel : '报废日期',
								width : 80,
								id : 'getdiscardDate',
								name : 'discardDate'
									// anchor:'95%'
								}]
						}, {
							columnWidth : .25,
							layout : 'form',
							items : [{
								xtype : 'textfield',
								fieldLabel : '经费来源',
								width : 80,
								id : 'getfundSource',
								name : 'fundSource'
									// anchor:'95%'
								}, {
								xtype : 'textfield',
								fieldLabel : '备注',
								width : 80,
								id : 'getdescription',
								name : 'description'

									// anchor:'95%'
								}, {
								xtype : 'textfield',
								fieldLabel : '机位（排列）',
								width : 80,
								id : 'getdeviceArrange',
								name : 'deviceArrange'
									// anchor:'95%'
								}]
						}]

			});

	var assetsearchmain = new Ext.FormPanel({
				// labelWidth : 200,
				width : 840,
				height : document.body.clientHeight * 0.95 + 5,
				autoScroll : true,
				frame : true,
				title : '资产管理',
				items : [searchfield1,

						searchfield2,

						searchbutton, asset_search_Panel

				]

			});

	assetsearchmain.render('showGrid');

});
