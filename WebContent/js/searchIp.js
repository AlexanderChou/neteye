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
 	var s = window.location.search.substring(1);
    var id = Ext.urlDecode(s);
	sm = new Ext.grid.CheckboxSelectionModel();

	var Record = new Ext.data.Record.create([
		{name:"id", mapping:"id"},
		{name:"faultIp", mapping:"faultIp"},
		{name:"beginTime", mapping:"beginTime"},
		{name:"recoverTime", mapping:"recoverTime"},
		{name:"faultReason", mapping:"faultReason"},
		{name:"persistTime", mapping:"persistTime"}
	]);
	
	var reader = new Ext.data.JsonReader({
		root:"faultList",
		totalProperty:'totalCount'
		},
		Record
	    );
	
	var proxy = new Ext.data.HttpProxy({
		url:"faultjson/seachByIp.do?ip="+id.ip
	});
	
	var colm = new Ext.grid.ColumnModel([
		{header:"故障id", dataIndex:"id"},
		{header:"故障IP",sortable: true, dataIndex:"faultIp"},
		{header:"发生时间",sortable: true, dataIndex:"beginTime"},
		{header:"结束时间",sortable: true, dataIndex:"recoverTime"},
		{header:"故障原因", dataIndex:"faultReason",editor:new Ext.form.Field()},
		{header:"持续时间(S)",sortable: true, dataIndex:"persistTime"}
	]);
	
	store = new Ext.data.Store({
		proxy:proxy,
		reader:reader
	});
	store.load({params:{start:0,limit:28,startTime:id.ip}});
	var grid = new Ext.grid.EditorGridPanel({
		store:store,
		height:document.body.clientHeight* 0.95 + 10,
		width:800,
		cm:colm,
		autoScroll:true,
		renderTo:"showDiv",
		sm:sm,	
		tbar:[{
			text:id.ip+"历史故障列表"			
		}],
		bbar:new Ext.PagingToolbar({
			pageSize:28,
			store:store,
			displayInfo:true,
			displayMsg:'显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg:'没有数据'
		})
	});
	grid.on("afteredit", afterEdit, grid);
});

function afterEdit(obj) {
	var r = obj.record;//获取被修改的行
	var l = obj.field;//获取被修改的列
	var id = r.get("id");
    var faultReason = r.get("faultReason");
	Ext.Ajax.request({
		url:"faultEdit.do",
		params:{
				id:id,
				faultReason:faultReason
			},
		success:function(response,request){
			//后台执行完成后重新加载 数据 
			if (response.responseText == "ok") {
				store.reload();				
			} else {
				alert("修改失败！");
				store.reload();
			}
		}
	});
}