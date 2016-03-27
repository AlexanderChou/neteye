<%--
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
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@taglib prefix="s" uri="/struts-tags"%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN "   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>拓扑历史记录</title>
<link rel='StyleSheet' href="css/topoInit.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
<script type="text/javascript" src="js/ext-base.js"></script>
<script type="text/javascript" src="js/ext-all.js"></script>

<script type="text/javascript">
var dir = "";
var deviceName="";

var div = null;
var div2 = null;
var div3 = null;

var flag = false;
var flag2 = false;
var flag3 = false;

var win2 = null;
var win3 = null;

var xg = Ext.grid;
var sm = new xg.CheckboxSelectionModel();

function manage() {
	if (flag) {
		div.show();
	} else {
		manage1();
	}
}

function detail(value) {
	return "<a href = 'javascript:deviceList(\"" + value +"\")'>" + value + "</a>";
}
function potoImg(){
	
	return "<a href = 'javascript:topoView()'>查看拓扑图</a>";
}
function topoView(){
	var selected = sm.getSelections();
	var name = "";
	var userName = "";
	var beginTime = "";
	var endTime = "";
	var deviceCount = "";
	var linksCount = "";
	var hop = "";
	for(var i=0;i<selected.length;i++ ){
		var link = selected[i].data;
		name=link.name;
		userName = link.userName;
		beginTime = link.beginTime;
		endTime = link.endTime;
		deviceCount = link.deviceCount;
		linksCount = link.linksCount;
		hop = link.hop;
	}
	
	var loginUserName = "${userName}";
	
	Ext.Ajax.request({
		url:"topoDisIsEnd.do",
		params:{"topoName":name},
		success:function(response, request){
			var jsonData = Ext.decode(response.responseText);
			var isEnd = jsonData.topoEnd;
			if (isEnd) {
				window.location.href = "topoFrame.do?name=" + name + "&userName=" + userName + "&beginTime=" + beginTime + "&endTime=" + endTime + "&devicesCount=" + deviceCount + "&linksCount=" + linksCount + "&hop=" + hop;
			} else {
				/* 如果不是当前用户 */
				if (userName != loginUserName) {
					alert("该拓扑发现视图还没有结束，请稍等.........");
				} else {
					var isContinue = window.confirm("该拓扑还未结束,是否查看！");
					if (isContinue ) {
						window.location.href = "topoDisplay.do?name=" + name;
					} else {

					}
				}
			}
		}
	});
}

function deviceList(name) {
	dir = name;
	manage2(name);	
}

function manage1(name){
	dir = name;
	flag = true;
    div = Ext.get("showDiv");
    div2 = Ext.get("showDiv2");
    div3 = Ext.get("showDiv3");
	index = 1;
	div.show();
	var xg = Ext.grid;
	var data ="";
	var reader = new Ext.data.JsonReader({
		root:"list"},
		[ {name: 'name', mapping:0},
		{name: 'beginTime', mapping:1},
		{name: 'endTime', mapping:2},
		{name: 'deviceCount', mapping:3},
		{name: 'linksCount', mapping:4},
		{name: 'ip', mapping:5},
		{name: 'hop', mapping:8},
	    {name: 'snmpVision', mapping:6},
	    {name: 'userName', mapping:7},
	    {name:'cz'},
	    {name:'dd'}
	    ]
	);
	
	var proxy = new Ext.data.HttpProxy({
		url:'json/topoHisList.do'
	});
	
	
	var colm = new Ext.grid.ColumnModel(
		[{id:"name", header:'名称', dataIndex:'name',renderer:detail,width:100,sortable:true},
		 {header:'开始时间', dataIndx:'beginTime', width:100,sortable:true},
		 {header:'结束时间', dataIndx:'endTime', width:100,sortable:true},
		 {header:'节点数', dataIndx:'deviceCount', width:60,sortable:true},
		 {header:'链路数', dataIndx:'linksCount', width:60,sortable:true},
		 {header:'种子节点', dataIndx:'ip',width:60,sortable:true},
		 {header:'跳数', dataIndx:'hop',width:60,sortable:true},
		 {header:'Snmp版本', dataIndx:'snmpVision', width:60,sortable:true},
		 {header:'创建者', dataIndx:'userName', width:60,sortable:true},
		 sm,
		 {header:'查看拓扑图', renderer:potoImg}
		]
	);
	
	var store = new Ext.data.Store({
				proxy:proxy,
				reader:reader
			});
	store.load();
	var grid = new Ext.grid.GridPanel({
		title:"拓扑历史记录",
		store:store,
		height:document.body.clientHeight*0.95+5,
		autoExpandColumn: 'name',
		width:840,
		cm:colm,
		autoScroll:true,
		sm:sm,
		renderTo:'showDiv',
		tbar:['->',{
			id:'deleteButton',
			text:'删除',
			handler:function(){
				var selected = sm.getSelections();
				var names = "";
				var record = "";
				
				if (selected.length == 0) {
					alert("请选择记录！");
					return;
				}
				
				for(var i=0;i<selected.length;i++){
					record = selected[i];
					var data = selected[i].data;
					var endTime = selected[i].endTime;
					if (endTime == "") {
						alert(data.name + "  还未结束，删除失败!");
						continue;
					}
					store.remove(record);
					names += data.name;
					names += ";";
				}
				
				
				Ext.Ajax.request({
					url:"json/deleteTopoHis.do?names=" + names,
					success:function(response, request){
						if (response.responseText == "ok") {
							alert("删除成功！！！");
						} else {
							alert("删除失败，你没有权限删除！");
						}
					}
				});
			}
		}]
	});
}

function detail2(value) {
	return "<a href = 'javascript:manage3(\"" + value + "\")'>" + value + "</a>";
}

function manage2(str){
	flag2 = true;
	index = 2;
	var reader = new Ext.data.JsonReader({
		root:"list"},
		[ {name: 'name', mapping:"name"},
		{name: 'ipv4', mapping:'IP'}
	    ]
	);
	var proxy = new Ext.data.HttpProxy({
		url:'json/topoDetail.do?dir=' + dir
	});
	
	
	var colm = new Ext.grid.ColumnModel(
		[{header:'名称', dataIndex:'name',width:247.5,renderer:detail2,sortable:true},
		 {header:'ipv4地址', dataIndx:'ipv4', width:247.5,sortable:true}
		]
	);
	
	
	var store = new Ext.data.Store({
				proxy:proxy,
				reader:reader
			});
			
	store.load();
	
	var grid = new Ext.grid.GridPanel({
		store:store,
		autoScroll:true,
		cm:colm,
		renderTo:'showDiv2',
		tbar:[
			 new Ext.form.TextField({
                     id:"keyWord"
             }),
			{	text:'查询（名字）',
				handler:function(){
					  var keyWord = Ext.get("keyWord").getValue();
					  store.filter("name", keyWord,true);
				}
			}
		]
	});
	
	win2 = new Ext.Window({
		title:str,
		width:612,
		height:450,
		layout:"fit",
		modal:true,
		resizable:false,
		items:[grid]
	});
	win2.show();
}


function manage3(str){
	deviceName=str;
	index = 3;
	flag3 = true;
	var reader = new Ext.data.JsonReader({
		root:"list"},
		[ {name: 'device1', mapping:'name'},
		  {name: 'InDevice1', mapping:'IP'},
		  {name: 'ziwang', mapping:'subIP'},
		  {name: 'yanma', mapping:'netMask'},
		  {name: 'InDevice2', mapping:'IP2'},
		  {name: 'device2', mapping:'name2'}
	    ]
	);
	
	var proxy = new Ext.data.HttpProxy({
		url:'json/topoLinksList.do?dir=' + dir + "&name=" + deviceName 
	});
	
	var colm = new Ext.grid.ColumnModel(
		[{header:'设备1', dataIndex:'device1',width:100,sortable:true},
		 {header:'设备1接口', dataIndex:'InDevice1',width:100,sortable:true},
		 {header:'子网号', dataIndex:'ziwang',width:100,sortable:true},
		 {header:'子网掩码', dataIndex:'yanma',width:100,sortable:true},
		 {header:'设备2接口', dataIndex:'InDevice2',width:100,sortable:true},
		 {header:'设备2', dataIndex:'device2',width:100,sortable:true}
		]
	);
	
	var store = new Ext.data.Store({
				proxy:proxy,
				reader:reader
			});
	store.load();
	
	var grid = new Ext.grid.GridPanel({
		store:store,
		height:400,
		width:608,
		cm:colm,
		renderTo:'showDiv3'
	});
	
	win3 = new Ext.Window({
		title:'设备：' + str + ' 链路信息',
		width:622,
		height:430,
		modal:true,
		resizable:false,
		items:[grid]
	});
	
	win3.show();
}

function close() {
	div.hide();
	div2.hide();
	div3.hide();
}
</script>
</head>
<body onload="manage()">

	<div id="outer">
		<div id="bodyDiv">
			<div id="menu">
				<s:include value="right_menu.jsp"></s:include>
			</div>
		
			<div id="infoDiv">
				<div id='winDiv'></div>
				<div id="showDiv"></div>
				<div id="showDiv2"></div>
				<div id="showDiv3"></div>
			</div>
		</div>
	</div>

	
</body>
</html>