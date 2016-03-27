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
	
	
	var s = window.location.search.substring(1);
    var parameter = Ext.urlDecode(s);
    
	sm = new Ext.grid.CheckboxSelectionModel();

	var Record = new Ext.data.Record.create([
		{name:"id", mapping:"id"},
		{name:"status", mapping:"status"},
		{name:"ip", mapping:"ip"},
		{name:"ip6", mapping:"ip6"},
		{name:"name", mapping:"name"},
		{name:"loss", mapping:"loss"},
		{name:"rrt", mapping:"rrt"},
		{name:"dd"},	
		{name:"uu"}
	]);
	
	var reader = new Ext.data.JsonReader({
		root:"nodeList",
		totalProperty:'totalCount'
		},
		Record
	    );
	
	var proxy = new Ext.data.HttpProxy({
		url:"faultjson/nodeStatusList.do?type="+parameter.type
	});
	
	var colm = new Ext.grid.ColumnModel([
		{id:"name", header:"设备名称", width:120,dataIndex:"name"},
//		{header:"节点id",sortable: true, dataIndex:"id",renderer:type},
		{header:"设备状态",dataIndex:"status",width:60,renderer:renderStatus},
		{header:"IP地址",sortable: true, dataIndex:"ip",width:120,renderer:rrtAndLoss},
		{header:"IPv6地址",sortable: true, dataIndex:"ip6",width:200,renderer:rrtAndLoss},
		{header:"丢包（%）", width:80,dataIndex:"loss",renderer:showdata},
		{header:"延时（ms）", width:80,dataIndex:"rrt",renderer:showrrt},
		{header:"ping操作",dataIndex:"ip",width:80,renderer:showUrl},
		{header:"traceroute操作",dataIndex:"ip",width:80,renderer:traceRoute}
	]);
	
	store = new Ext.data.Store({
		proxy:proxy,
		reader:reader
	});
	store.load({params:{start:0,limit:28}});
	var grid = new Ext.grid.EditorGridPanel({
		title:"节点状态",
		store:store,
		height:document.body.clientHeight* 0.95 + 5,
		autoExpandColumn: 'name',
		width:840,
		cm:colm,
		autoScroll:true,
		renderTo:"showDiv",
		sm:sm,	
		tbar:[{
			pressed:true,
			text:"IPv4节点状态列表",
			handler:function(){
						window.location.href="nodeStatus.do?type=4";
				}						
		},
		{	
				pressed:true,
				text:'IPv6节点状态列表',
				handler:function(){
						window.location.href="nodeStatus.do?type=6";
				}
			},'绿色表示通、红色表示断、黄色表示后台没有监控、黑色表示ip地址不可用'
			
		],
		bbar:new Ext.PagingToolbar({
			pageSize:28,
			store:store,
			displayInfo:true,
			displayMsg:'显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg:'没有数据'
		})
	});
});

function showUrl(value)
{ 
return '<a href="javascript:doPing(\''+value+'\')">ping</a>';
}
function traceRoute(value)
{
return '<a href="javascript:doTrace(\''+value+'\')">traceroute</a>'
}
function rrtAndLoss(value)
{
return "<a href='rttLossPic.do?ip="+value+"'>"+value+"</a>";
}

function showdata(value)
{
	if(value>80){
		return "<font color=#F00000 >"+value+"</font>";
	}else if(value>50){
		return "<font color=#0000FF >"+value+"</font>";
	}else {
		return value;
	}
}
function showrrt(value)
{
	if(value>1000){
		return "<font color=#F00000 >"+value+"</font>";
	}else if(value>500){
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
/*
function doPing(value){	
		ping = window.open("ping.do?ip="+value,"ping", "left=60, top=60, height=500","scrollbars=yes", "bReplace=true");
		ping.focus();
}
function doTrace(value) {		
		trace = window.open("traceroute.do?ip="+value, "tracert", "left=60, top=60, height=500, scrollbars=yes", "bReplace=true");
		trace.focus();
}*/
function  renderStatus(value){
	if (value=='0'){
		return "<img src='/images/red.gif' />";
	}
	else if (value == '1') {
        return "<img src='/images/yellow.gif' />";
    } else if(value=='2'){
        return "<img src='/images/green.gif' />";
    }else{
    	return "<img src='/images/black.gif' />";		
    }
}
function type(value){
	if(value=='0'){
		return "";
	}else{		
		return value;
	}
}