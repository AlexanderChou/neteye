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
var ip="";
function showUrl(value)
{
return "<a href='javascript:doPing()'>"+value+"</a>";
}
function traceRoute(value)
{
return "<a href='javascript:doTrace()'>"+value+"</a>";
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
function showip(value)
{
	if(value.indexOf(":")>0){
		return "<font color=#0000FF >"+value+"</font>";
	}else {
		return value;
	}
}


function doPing(){
	var win = new Ext.Window({
	        				title:"设备ping测试",
                            width:450,
                            height:520,
                            maximizable:true,
                            contentEl : Ext.DomHelper.append(document.body, {
							    tag : 'iframe',
							    style : "border 0px none;scrollbar:true",
							    src : 'ping.do?ip='+ip,
							    height : "100%",
							    width : "100%"
							   }),
                            modal:true,
                            resizable : false,
                            tbar:[{
							pressed:true,
							text:"ip地址："+ip,
							handler:function(){
										
								}						
						}]
	    });
	    win.show();
	}
function doTrace(){
		var win = new Ext.Window({
	        				title:"设备TraceRouter测试",
                            width:450,
                            height:520,
                            maximizable:true,
                            contentEl : Ext.DomHelper.append(document.body, {
							    tag : 'iframe',
							    style : "border 0px none;scrollbar:true",
							    src : 'traceroute.do?ip='+ip,
							    height : "100%",
							    width : "100%"
							   }),
                            modal:true,
                            resizable : false,
                            tbar:[{
							pressed:true,
							text:"ip地址："+ip,
							handler:function(){
										
								}						
						}]
	    });
	    win.show();
}
Ext.onReady(function(){  
		var xg = Ext.grid;
		var sm = new xg.CheckboxSelectionModel();
		var faultStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : 'faultjson/currentFault.do'
				}),
				//创建JsonReader读取router记录
				reader : new Ext.data.JsonReader({
					root : 'faultList',
					totalProperty:'totalCount',
					fields : ['id','ip','beginTime','loss','rrt','ping','traceroute']
				})
		});
		faultStore.setDefaultSort('beginTime','DESC'); 
		faultStore.load({params:{start:0,limit:28}});
		var faultPanel = new Ext.grid.GridPanel({			
			store : faultStore,
			height:document.body.clientHeight*0.95+5,	
			width:840,		
        	autoWidth:true,
			autoScroll:true,
        	title:'当前故障列表',
			columns : [
				new Ext.grid.RowNumberer(),
				{id:"ip",header: "IP地址", width: 20, sortable: true, dataIndex: 'ip',renderer:showip}, 
				{header:"开始时间",sortable: true, dataIndex:"beginTime"},
				{header:"丢包（%）", dataIndex:"loss",renderer:showdata},
				{header:"延时（ms）", dataIndex:"rrt",renderer:showrrt},
				{header:"ping操作",dataIndex:"ping",renderer:showUrl},
				{header:"traceroute操作",dataIndex:"traceroute",renderer:traceRoute}
				],
				stripeRows: true,
			frame:true,
			sm:sm,
			tbar:[{
			text:"当前故障列表："			
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
					var faultTimeStore = new Ext.data.Store({
				         proxy : new Ext.data.HttpProxy({
					url : 'json/searchfaulttime.do?startTime='+startTime+'&endTime='+endTime
				         }),
				//创建JsonReader读取router记录
				reader : new Ext.data.JsonReader({
					root : 'faultList',
					totalProperty:'totalCount',
					fields : ['id','ip','beginTime','loss','rrt','ping','traceroute']
			 	})
		             });
		        faultTimeStore.setDefaultSort('beginTime','DESC'); 
	          	faultTimeStore.load({params:{start:0,limit:28}});
	          	
	       	var faultTimePanel = new Ext.grid.GridPanel({			
			store : faultTimeStore,
			height:600,	
			width:840,		
        	autoWidth:true,
			autoScroll:true,
        	title:'查询结果',
			columns : [
				new Ext.grid.RowNumberer(),
				{id:"ip",header: "IP地址", width: 20, sortable: true, dataIndex: 'ip'}, 
				{header:"开始时间",sortable: true, dataIndex:"beginTime"},
				{header:"丢包（%）", dataIndex:"loss"},
				{header:"延时（ms）", dataIndex:"rrt"},
				{header:"ping操作",dataIndex:"ping",renderer:showUrl},
				{header:"traceroute操作",dataIndex:"traceroute",renderer:traceRoute}
				],
				stripeRows: true,
			frame:true,
			sm:sm,
	        autoExpandColumn: 'ip',
			bbar:new Ext.PagingToolbar({
			pageSize:28,
			store:faultTimeStore,
			displayInfo:true,
			cls:'x-btn-text-icon details',			
			displayMsg:'显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg:'没有数据'
		   })	  	
	         });
	         faultTimePanel.addListener('cellclick', rowClickFn);     
  			function rowClickFn(grid, rowIndex, e) { 
  				var s=faultPanel.getStore();
  				var fault=s.getAt(rowIndex);    
			    ip=fault.get("ip");    
		}
	         var faultTimePanelwin = new Ext.Window({
							items : [faultTimePanel]
	    });
	    faultTimePanelwin.show();
	         
	//	       		window.location.href="searchTime.do?startTime="+startTime+"&endTime="+endTime;
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
					  	var faultIPStore = new Ext.data.Store({
				         proxy : new Ext.data.HttpProxy({
					url : 'json/searchfaultip.do?ip='+keyIp
				         }),
				//创建JsonReader读取router记录
				reader : new Ext.data.JsonReader({
					root : 'faultList',
					totalProperty:'totalCount',
					fields : ['id','ip','beginTime','loss','rrt','ping','traceroute']
			 	})
		             });
		        faultIPStore.setDefaultSort('beginTime','DESC'); 
	          	faultIPStore.load({params:{start:0,limit:28}});
	          	var faultIPPanel = new Ext.grid.GridPanel({			
			store : faultIPStore,
			height:600,	
			width:840,		
        	autoWidth:true,
			autoScroll:true,
        	title:'查询结果',
			columns : [
				new Ext.grid.RowNumberer(),
				{id:"ip",header: "IP地址", width: 20, sortable: true, dataIndex: 'ip'}, 
				{header:"开始时间",sortable: true, dataIndex:"beginTime"},
				{header:"丢包（%）", dataIndex:"loss"},
				{header:"延时（ms）", dataIndex:"rrt"},
				{header:"ping操作",dataIndex:"ping",renderer:showUrl},
				{header:"traceroute操作",dataIndex:"traceroute",renderer:traceRoute}
				],
				stripeRows: true,
			frame:true,
			sm:sm,
	        autoExpandColumn: 'ip',
			bbar:new Ext.PagingToolbar({
			pageSize:28,
			store:faultIPStore,
			displayInfo:true,
			cls:'x-btn-text-icon details',			
			displayMsg:'显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg:'没有数据'
		   })	  	
	         });	
	          faultIPPanel.addListener('cellclick', rowClickFn);     
  			function rowClickFn(grid, rowIndex, e) { 
  				var s=faultPanel.getStore();
  				var fault=s.getAt(rowIndex);    
			    ip=fault.get("ip");    
		}
	         var faultIPPanelwin = new Ext.Window({
							items : [faultIPPanel]
	    });
	    faultIPPanelwin.show();
//					  	window.location.href="searchIp.do?ip="+keyIp;
				}
			}					
		],
			autoExpandColumn: 'ip',
			bbar:new Ext.PagingToolbar({
			pageSize:28,
			store:faultStore,
			displayInfo:true,
			cls:'x-btn-text-icon details',			
			displayMsg:'显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg:'没有数据'
		   })	
		});	
		faultPanel.addListener('cellclick', rowClickFn);     
  			function rowClickFn(grid, rowIndex, e) { 
  				var s=faultPanel.getStore();
  				var fault=s.getAt(rowIndex);    
			    ip=fault.get("ip");    
		}
		
        faultPanel.render('showGrid');
   		//faultPanel.getSelectionModel().selectFirstRow();
});
