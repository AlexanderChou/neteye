/*******************************************************************************
 * * Copyright (c) 2008, 2009, 2010 * The Regents of the Tsinghua University,
 * PRC. All rights reserved. * * Redistribution and use in source and binary
 * forms, with or without modification, are permitted provided that the
 * following conditions are met: * 1. Redistributions of source code must retain
 * the above copyright notice, this list of conditions and the following
 * disclaimer. * 2. Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution. * 3. All
 * advertising materials mentioning features or use of this software must
 * display the following acknowledgement: * This product includes software
 * (iNetBoss) developed by Tsinghua University, PRC and its contributors. * THIS
 * SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND * ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE * IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE * ARE
 * DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE * FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL * DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS * OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) * HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT *
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY *
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF * SUCH
 * DAMAGE.
 * 
 ******************************************************************************/
Ext.apply(Ext.form.VTypes, {
			daterange : function(val, field) {
				var date = field.parseDate(val);

				// We need to force the picker to update values to recaluate the
				// disabled dates display
				var dispUpd = function(picker) {
					var ad = picker.activeDate;
					picker.activeDate = null;
					picker.update(ad);
				};

				if (field.startDateField) {
					var sd = Ext.getCmp(field.startDateField);
					sd.maxValue = date;
					if (sd.menu && sd.menu.picker) {
						sd.menu.picker.maxDate = date;
						dispUpd(sd.menu.picker);
					}
				} else if (field.endDateField) {
					var ed = Ext.getCmp(field.endDateField);
					ed.minValue = date;
					if (ed.menu && ed.menu.picker) {
						ed.menu.picker.minDate = date;
						dispUpd(ed.menu.picker);
					}
				}
				/*
				 * Always return true since we're only using this vtype to set
				 * the min/max allowed values (these are tested for after the
				 * vtype test)
				 */
				return true;
			}
		});
Ext.onReady(function() {
	var s = window.location.search.substring(1);
	var obj = Ext.urlDecode(s);
	var objName = obj.objName;
	var objId = obj.objId;
	var sm = new Ext.grid.CheckboxSelectionModel();
	var reader = new Ext.data.JsonReader({
				root : 'events',
				totalProperty : 'totalCount',
				fields : ['id', 'occurNum', 'occurTime', 'receiveTime',
						'moduleId', 'moduleType', 'typeValue', 'priority',
						'objType', 'objName', 'objIPv4', 'objIPv6', 'title',
						'content']
			});
	var titleStr = '事件列表';
	var eventStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'json/GlobalEvenList.do'
						}),
				// 创建JsonReader读取router记录
				reader : reader
			});
	eventStore.setDefaultSort('occurTime', 'DESC');
	eventStore.load({
				params : {
					start : 0,
					limit : 28
				}
			});
	var eventPanel = new Ext.grid.GridPanel({
		store : eventStore,
		autoScroll : true,
		height : document.body.clientHeight * 0.95 + 5,
		autoExpandColumn : 'pro',
		width : 840,
		title : titleStr,
		columns : [
//			{
//					header : "事件id",
//					width : 50,
//					sortable : true,
//					dataIndex : 'id'
//				}, 
					sm,{
					header : "发生次数",
					width : 60,
					sortable : true,
					renderer : change,
					dataIndex : 'occurNum'
				}, {
					header : "发生时间",
					width : 130,// 130没效果...奇怪..
					sortable : true,
					dataIndex : 'occurTime'
				}, {
				
					header : "收到时间",
					width : 130,
					sortable : true,
					dataIndex : 'receiveTime'
				}, {
					header : "模块名称",
					width : 60,
					sortable : true,
					dataIndex : 'moduleId'
				}, {
					header : "事件类型",
					width : 80,
					sortable : true,
					dataIndex : 'moduleType'
				}, {
					header : "事件值",
					width : 80,
					sortable : true,
					dataIndex : 'typeValue'
				}, {
						id : 'pro',
					header : "优先级",
					width : 60,
					sortable : true,
					dataIndex : 'priority'
				}, {
					header : "对象类型",
					width : 60,
					sortable : true,
					dataIndex : 'objType'
				}, {
					header : "对象名称",
					width : 80,
					sortable : true,
					dataIndex : 'objName'
				}, {
					header : "IPv4",
					width : 100,
					sortable : true,
					dataIndex : 'objIPv4'
				}, {
					header : "IPv6",
					width : 120,
					sortable : true,
					dataIndex : 'objIPv6'
				}, {
					header : "事件主题",
					width : 70,
					sortable : true,
					dataIndex : 'title'
				}, {
					header : "事件内容",
					width : 360,
					sortable : true,
					dataIndex : 'content'
				}],
		sm : sm,
		tbar : [new Ext.Toolbar.TextItem('显示条目:'), {
					pressed : true,
					text : '最近100条',
					handler : function() {
						Ext.getCmp("page").pageSize = 100;
						eventStore.proxy = new Ext.data.HttpProxy({
									url : 'json/GlobalEvenList.do'
											+ "?howMuch=" + "0"
								});
						eventStore.reload();
					}
				}, {
					pressed : true,
					text : '今日',
					handler : function() {
						Ext.getCmp("page").pageSize = 28;
						eventStore.proxy = new Ext.data.HttpProxy({
									url : 'json/GlobalEvenList.do'
											+ "?howMuch=" + "1"
								});
						eventStore.reload();
					}
				}, '从', new Ext.form.DateField({
							fieldLabel : '开始时间',
							id : 'fromDate',
							name : 'fromDate',
							vtype : 'daterange',
							format : 'Y-m-d',
							endDateField : 'toDate'
						}), '到', new Ext.form.DateField({
							fieldLabel : '结束时间',
							id : 'toDate',
							name : 'toDate',
							vtype : 'daterange',
							format : 'Y-m-d',
							startDateField : 'fromDate'
						}), {
					pressed : true,
					text : '按时段查询',
					handler : function() {
						Ext.getCmp("page").pageSize = 28;
						eventStore.proxy = new Ext.data.HttpProxy({
									url : 'json/GlobalEvenList.do?howMuch='
											+ '2&fromTime='
											+ Ext.get("fromDate").getValue()
											+ '&toTime='
											+ Ext.get("toDate").getValue()
								});
						eventStore.reload();
					}
				}, '->',
//				{
//					id : "addRecord",
//					text : "生成ticket",
//					handler : function() {
//						var ids = "";
//						var record = "";
//						var selected = sm.getSelections();
//						if (selected.length == 0) {
//							Ext.Msg.alert("请选择事件！");
//							return;
//						}
//						var isAdd = confirm("是否生成ticket事件？");
//						if (isAdd) {
//							for (var i = 0; i < selected.length; i++) {
//								record = selected[i];
//								var data = selected[i].data;
//								ids += data.id;
//								ids += ";";
//							}
//
//							Ext.Ajax.request({
//								url : "json/outputTicket.do",
//								params : {
//									eventIds : ids
//								},
//								success : function(response, request) {
//									if (response.responseText == "ok") {
//										alert("添加成功！");
//									} else {
//										alert("添加失败! 你没有操作权限");
//									}
//								}
//							});
//						}
//					}
//				}, '->', '-',
				{
					id : "deleteButton",
					text : "删除",
					handler : function() {
						var ids = "";
						var record = "";
						var selected = sm.getSelections();
						if (selected.length == 0) {
							alert("请选择事件！");
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
								url : "json/deleteEvent.do",
								params : {
									eventIds : ids
								},
								success : function(response, request) {
									if (response.responseText == "ok") {
										for (var i = 0; i < selected.length; i++) {
											record = selected[i];
											eventStore.remove(record);
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
					id : "page",
					pageSize : 28,
					store : eventStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
					emptyMsg : '没有数据'
				})
	});

	eventPanel.addListener('rowdblclick',rowClickFn);
			function rowClickFn(grid, rowIndex,/* Ext.data.Record */e) {
				var s = eventPanel.getStore();
		var eventR = s.getAt(rowIndex);
		var eventId = eventR.get("id");
		var eventName = eventR.get("objName");
		var eventType = eventR.get("objType");
				window.location.href = "objEventSeq.do?eventId=" + eventId
						+ "&objName=" + eventName + "&objType="
						+ eventType;
			};
	function change(val) {
		return '<span style="color:blue;">' + val + '</span>';
	}
	eventPanel.render('showGrid');
});