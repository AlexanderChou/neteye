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
		{name:"statTime", mapping:"statTime"},
		{name:"srcIp", mapping:"srcIp"},
		{name:"descIp", mapping:"descIp"},
		{name:"srcPort", mapping:"srcPort"},
		{name:"descPort", mapping:"descPort"},
		{name:"protocolNum", mapping:"protocolNum"},
		{name:"bytes", mapping:"bytes"},
		{name:"pkts", mapping:"pkts"},
		{name:"flows", mapping:"flows"},
		{name:"uu"}
	]);
	
	var reader = new Ext.data.JsonReader({
		root:"statList"
		},
		Record
	    );
	
	var proxy = new Ext.data.HttpProxy({
		url:"json/sessionStat.do"
	});
	
	var colm = new Ext.grid.ColumnModel([
		{header:"流开始时间",sortable: true,align:'center',dataIndex:"statTime"},
		{header:"源地址",sortable: true,align:'center',dataIndex:"srcIp"},
		{header:"目的地址",sortable: true,align:'center',dataIndex:"descIp"},
		{header:"源端口",sortable: true,align:'center',dataIndex:"srcPort"},
		{header:"目的端口",sortable: true,align:'center',dataIndex:"descPort"},
		{header:"协议号",sortable: true,align:'center',dataIndex:"protocolNum"},
		{header:"bytes",sortable: true,align:'center',dataIndex:"bytes"},
		{header:"pkts",sortable: true,align:'center',dataIndex:"pkts"},
		{header:"flows",sortable: true,align:'center',dataIndex:"flows"},
		{header:"预留",sortable: true,align:'center',dataIndex:"srcIp"}
	]);
	
	store = new Ext.data.Store({
		proxy:proxy,
		reader:reader
	});
	store.load();
	var grid = new Ext.grid.EditorGridPanel({
		title:"流量统计",
		store:store,
		height:document.body.clientHeight*0.95+5,
		width:840 ,
		cm:colm,
		autoScroll:true,
		renderTo:"showDiv",
		sm:sm,	
		tbar:[{
		    pressed:true,
			text:'基于端口的统计',
			handler:function(){
				  window.location.href="trafficStat.do";
				}		
		    },
			{	pressed:true,
	      		text:'基于协议的流量统计',
				handler:function(){
				  window.location.href="protocol.do";
				}
			},
			{	
				pressed:true,
				text:'基于会话的流量统计',
				handler:function(){
					window.location.href="session.do";
				}
			}]
	});
});

