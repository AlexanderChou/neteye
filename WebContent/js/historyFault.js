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
var store = "";
var ip = "";
Ext.onReady(function(){

	sm = new Ext.grid.CheckboxSelectionModel();

	var Record = new Ext.data.Record.create([
		{name:"id", mapping:"id"},
		{name:"faultIp", mapping:"faultIp"},
		{name:"beginTime", mapping:"beginTime"},
		{name:"recoverTime", mapping:"recoverTime"},
		{name:"faultReason", mapping:"faultReason"},
		{name:"persistTime", mapping:"persistTime"},
		{name:"dd"},	
		{name:"uu"}
	]);
	
	var reader = new Ext.data.JsonReader({
		root:"faultList",
		totalProperty:'totalCount'
		},
		Record
	    );
	
	var proxy = new Ext.data.HttpProxy({
		url:"faultjson/hisFault.do"
	});
	
	var colm = new Ext.grid.ColumnModel([
  	new Ext.grid.RowNumberer(),
	//	{header:"故障id",sortable: true,align:'center',dataIndex:"id"},
		{header:"故障IP",sortable: true,align:'center',dataIndex:"faultIp",renderer:showip},
		{header:"发生时间",sortable: true,align:'center',dataIndex:"beginTime"},
		{header:"结束时间",sortable: true,align:'center',dataIndex:"recoverTime"},
		{id:"name", header:"故障原因", dataIndex:"faultReason",align:'center',editor:new Ext.form.Field()},
		{header:"持续时间(S)",sortable: true,align:'center',dataIndex:"persistTime"},
		{header:"ping操作",dataIndex:"faultIp",align:'center',renderer:showUrl},
		{header:"traceroute操作",dataIndex:"faultIp",align:'center',renderer:traceRoute}
	]);
	
	store = new Ext.data.Store({
		proxy:proxy,
		reader:reader
	});
	store.setDefaultSort('beginTime','DESC'); 
	store.load({params:{start:0,limit:28}});
	var grid = new Ext.grid.EditorGridPanel({
		title:"历史故障",
		store:store,
		height:document.body.clientHeight*0.95+5,
		autoExpandColumn: 'name',
		width:840 ,
		cm:colm,
		autoScroll:true,
		renderTo:"showDiv",
		sm:sm,	
		tbar:[{
			text:"历史故障列表：（默认是当前两周）"			
		},'从',					
			new Ext.form.DateField({
				fieldLabel: '开始时间',
				id:'fromDate',
				name:'fromDate',
				format: 'Y-m-d',
        		endDateField: 'toDate' 
			}),'到',
			new Ext.form.DateField({
				fieldLabel: '结束时间',
				id:'toDate',
				name:'toDate',
				format: 'Y-m-d', 
				startDateField: 'fromDate'
			}),
			{	pressed:true,
	      		text:'按时段查询',
				handler:function(){
					var startTime=Ext.get("fromDate").getValue();
					var endTime=Ext.get("toDate").getValue();
					if(startTime.length<1){
						alert("起始日期不能为空！");						
						return false;
					}
					if(endTime.length<1){					
						alert("结束日期不能为空！");
						return false;
					}
		       		window.location.href="searchTime.do?startTime="+startTime+"&endTime="+endTime;
				}
			},
			new Ext.form.TextField({
                     id:"keyWord"
             }),
			{	
				pressed:true,
				text:'按IP地址查询',
				handler:function(){
					  	var keyIp = Ext.get("keyWord").getValue();
					  	if(keyIp.length<1){
						  	alert("IP地址不能为空！");
							return false;
					  	}
						window.location.href="searchIp.do?ip="+keyIp;
				}
			}					
		],
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

function showUrl(value)
{
return '<a href="javascript:doPing(\''+value+'\')">ping</a>';
}
function traceRoute(value)
{
return '<a href="javascript:doTrace(\''+value+'\')">traceroute</a>'
}
function showip(value)
{
	if(value.indexOf(":")>0){
		return "<font color=#0000FF >"+value+"</font>";
	}else {
		return value;
	}
}
function doPing(value){
		var win = new Ext.Window({
	        				title:"设备ping测试",
                            width:450,
                            height:520,
                            maximizable:true,
                            contentEl : Ext.DomHelper.append(document.body, {
							    tag : 'iframe',
							    style : "border 0px none;scrollbar:true",
							    src : 'ping.do?ip='+value,
							    height : "100%",
							    width : "100%"
							   }),
                            modal:true,
                            resizable : false,
                            tbar:[{
							pressed:true,
							text:"ip地址："+value,
							handler:function(){
										
								}						
						}]
	    });
	    win.show();
}
function doTrace(value){
		var win = new Ext.Window({
	        				title:"设备TraceRouter测试",
                            width:450,
                            height:520,
                            maximizable:true,
                            contentEl : Ext.DomHelper.append(document.body, {
							    tag : 'iframe',
							    style : "border 0px none;scrollbar:true",
							    src : 'traceroute.do?ip='+value,
							    height : "100%",
							    width : "100%"
							   }),
                            modal:true,
                            resizable : false,
                            tbar:[{
							pressed:true,
							text:"ip地址："+value,
							handler:function(){
										
								}						
						}]
	    });
	    win.show();
}
