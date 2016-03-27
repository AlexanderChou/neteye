/****************************************
** Copyright (c) 2008, 2009, 2010
**      The Regents of the Tsinghua University, PRC.  All rights reserved.
**
** Redistribution and use in source and binary forms, with or without  modification, are permitted provided that the following conditions are met:
** 1. Redistributions of source code must retain the above copyright  notice, this list of conditions and the following disclaimer.
** 2. Redistributions in binary form must reproduce the above copyright  notice, this list of conditions and the following disclaimer in the  documentation and/or other materials provided with the distribution.
** 3. All advertising materials mentioning features or use of this software  must display the following acknowledgement:
**  This product includes software (iNetBoss) developed by Tsinghua University, PRC and its contributors.
** THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
** ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
** IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
** ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
** FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
** DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
** OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
** HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
** LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
** OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
** SUCH DAMAGE.
*
******************************************/
Ext.onReady(function(){

	sm = new Ext.grid.CheckboxSelectionModel();
	
	var Record = new Ext.data.Record.create([
		{name:"id", mapping:"id"},
		{name:"loginTime", mapping:"loginTime"},
		{name:"quitTime", mapping:"quitTime"},
		{name:"userName", mapping:"userName"},
		{name:"ip", mapping:"ip"},
		{name:"sm"}
	]);
	
	var reader = new Ext.data.JsonReader(
		{root:"logs",
		totalProperty:'totalCount'
		},
		Record
	);
	
	var proxy = new Ext.data.HttpProxy({
		url:"userLoginInfo.do"
	});
	
	var colm = new Ext.grid.ColumnModel([
		{header:"日志id", dataIndex:"id",sortable:true,width:150},
		{id:"name", header:"用户名", dataIndex:"userName",sortable:true,width:150},
		{header:"登录时间", dataIndex:"loginTime",sortable:true,width:150},
		{header:"退出时间", dataIndex:"quitTime",sortable:true,width:150},
		{header:"登录IP", dataIndex:"ip",sortable:true,width:150},
		sm
	]);
	
	var store = new Ext.data.Store({
		proxy:proxy,
		reader:reader
	});
	
	store.load({params:{start:0,limit:28}});
	
	var grid = new Ext.grid.GridPanel({
		title:"用户登陆信息",
		store:store,
		autoExpandColumn: 'name',
		height:document.body.clientHeight* 0.95 + 10,
		width:840,
		cm:colm,
		sm:sm,
		autoScroll:true,
		renderTo:"showDiv",
		tbar:['->', {
			id:'deleteButton',
			text:'删除',
			handler:function(){
				var selected = sm.getSelections();
				var ids = "";
				var record = "";
				if (selected.length ==0) {
					alert("请选择日志信息!");
					return;
				}
				var isDelete = confirm("确定要删除吗？");
				if (isDelete) {
					for(var i=0;i<selected.length;i++){
						record = selected[i];
						var data = selected[i].data;
						store.remove(record);
						ids += data.id;
						ids += ";";
					}
					Ext.Ajax.request({
						url:"deleteLogs.do?logIds=" + ids,
						success:function(response, request){
							if (response.responseText == "ok") {
								alert("删除成功！！！");
							} else {
								alert("删除失败，你没有权限删除！");
							}
						}
					});
				} 
			}
		}],
		bbar:new Ext.PagingToolbar({
			pageSize:28,
			store:store,
			displayInfo:true,
			displayMsg:'显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg:'没有数据'
		})
	});
});