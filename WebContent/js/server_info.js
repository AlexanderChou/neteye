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
/*
 *author:JiangNing
 *date:2009-7-22
 *version:V1
 */
Ext.namespace("cernet2");
cernet2.nodeInfoApp = function() {
	return {
		init : function() {
			Ext.BLANK_IMAGE_URL = 'images/s.gif';
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());//使用cookie记录窗口等状态信息		
			/*
			 * ------------首先获取必要的基本信息----------------
			 */
			//首先从url中获取当前的device数据
			var s = window.location.search.substring(1);
			var pDevice = Ext.urlDecode(s);
			//故障图的基地址
			var faultChartBaseUrl =  "/file/fault/dat/pic/";
			//设备图的基地址
			var deviceChartBaseUrl = "images/pic/";			
			var ipPart = pDevice.ipv4;//用于拼接图片地址
			if(ipPart==null||ipPart=="") {
				 ipPart = pDevice.ipv6.replace(/:/g,"-");
			}
			// 1.服务表格面板
			// create the data store
			var serviceStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url :'/json/InfoAction_fetchServices.do'
				}),
				//创建JsonReader读取interface记录
				reader : new Ext.data.JsonReader({
					root : 'serviceInfoList',
					fields : ['id','serviceType','port','description','status']
					})
			});	
			function statusRenderer(val){
				if (val == 'up') {
					    return '<img src="images/green.jpg" width="15px" height="5px"/>';
				}
				else {
					    return '<img src="images/red.jpg" width="15px" height="5px"/>';
				}
			}
			var servicePanel = new Ext.grid.GridPanel({
				store : serviceStore,
				//height: 300,
				width: 700,
				border: true,
				height:300,
				autoScroll:true,
				margins : '0 0 0 5',
				//列模型
				columns : [
					new Ext.grid.RowNumberer(),
					{header: "类型", width: 80, sortable: true, dataIndex: 'serviceType'},
					{header: "端口", width: 90, sortable: true, dataIndex: 'port'},
					{header: "描述", width: 100, sortable: true, dataIndex: 'description'},
					{header: "状态", width: 40,sortable: true,dataIndex : 'status',renderer : statusRenderer}
					],
				sm: new Ext.grid.RowSelectionModel({singleSelect: true}) //单选
				//frame: true
			});
			serviceStore.load({
				params: {
					deviceId : pDevice.id
				}
			});
			// 2.信息面板
		    var infoPanel = new Ext.Panel({
				id : 'north-panel',
				iconCls : 'settings',
				split : true,
				height: 285,
				border: false,
				width:500,
				split:'true',
				collapsible: true,
				collapseMode: 'mini',
				margins : '0 0 5 5',
				layout:'column',
				layoutConfig : {
					animate : true
				},
				items : [{
							border : true,
							title : '<h3>运行状态</h3>',
							height: 240,
							columnWidth: .5,
							html : 	'<div style="padding: 10px">' +
										'<table width="100%">' +
										'<tr>' +
										'<td width="50%" align="center"><img src="'+ deviceChartBaseUrl + ipPart 
                                        +'_temperature.png"/></td>' + 
										'<td align="center"><img src="'+ deviceChartBaseUrl + ipPart +'_cpu_mem.png" /></td>' +
										'</tr>' +
										'<tr height="15px"></tr>' +
										'<tr>' +
										'<td width="50%" align="center"><img src="'+ deviceChartBaseUrl + ipPart +'_rtt.png" /></td>' +
										'<td align="center"><img src="'+ deviceChartBaseUrl + ipPart +'_loss.png" /></td>' +
										'</tr>' +
										'</table>'+
										'</div>'
						}, {
							title : '基本信息',
							border : true,
							layout:"fit",
							height: 240,
							
							columnWidth: .5,
							items : [new Ext.grid.PropertyGrid({
								collapsed : false,
								collapsible : true,
								height : 600,
								width : 340,
								source : {
									"实际名称" : pDevice.name!=null?pDevice.name:'',
									"中文名称" : pDevice.chineseName!=null?pDevice.chineseName:'',
									"IPv4地址" : pDevice.ipv4!=null?pDevice.ipv4:'',
									"IPv6地址" : pDevice.ipv6!=null?pDevice.ipv6:'',
									"描述" : pDevice.description!=null?pDevice.description:'',
									"制造商" : pDevice.productor!=null?pDevice.productor:'',
									"所在地" : pDevice.location!=null?pDevice.location:'',
									"型号" :   pDevice.model!=null?pDevice.model:'',
									"序列号": pDevice.serial!=null?pDevice.serial:'',
									"类别": pDevice.deviceType!=null?pDevice.deviceType:'',
									"标签": pDevice.label!=null?pDevice.label:'',
									"端口数目": pDevice.ifNum!=null?pDevice.ifNum:'',
									"流量采集地址": pDevice.trafficIp!=null?pDevice.trafficIp:'',
									"是否对其故障监控": pDevice.faultFlag!=null?pDevice.faultFlag:''
								},listeners:{
									beforeedit:function(e){
										e.cancel = true;
									}	
								}
							})]
						}]
                    
			});
			// ------------故障面板------------
			//1. 故障相关的图的面板
			//模板
			var faultChartTpl = new Ext.Template(
				'<div style="margin-top:10px;text-align:center;">',
					'<img src="{pic}">',
				'</div>'
			);
			var faultChartPanel = new Ext.Panel({
				id : 'fault-panel',
				iconCls : 'settings',
				split : true,
				height: 285,
				border: false,
				split:'true',
				collapsible: true,
				collapseMode: 'mini',
				margins : '0 0 3 0',
				layout:'column',
				layoutConfig : {
					animate : true
				},
				items: [
					{
						height: 240,
						columnWidth: .5,
						title: '<h3>RTT图</h3>',
						html: faultChartTpl.apply({pic: faultChartBaseUrl + ipPart + "_rtt.gif"})
					},
					{
						height: 240,
						columnWidth: .5,
						title: '<h3>LOSS图</h3>',
						html: faultChartTpl.apply({pic: faultChartBaseUrl + ipPart + "_loss.gif"})
					}
				]
			});
			// create the data store
			var eventStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url :'/json/InfoAction_fetchServiceEventInfos.do'
				}),
				//创建JsonReader读取interface记录
				reader : new Ext.data.JsonReader({
					root : 'serviceEventInfoList',
					totalProperty:'totalCount',
					fields : ['id','occurTime','serviceType','port','typeValue']
					})
			});	
			function statusRenderer(val){
				if (val == 'up') {
					    return '<img src="images/green.jpg" width="15px" height="5px"/>';
				}
				else {
					    return '<img src="images/red.jpg" width="15px" height="5px"/>';
				}
			}
			var eventPanel = new Ext.grid.GridPanel({
				store : eventStore,
				//height: 300,
				width: 400,
				height:365,
				border: true,
				//列模型
				columns : [
					new Ext.grid.RowNumberer(),
					{header: "发生时间", width: 120, sortable: true, dataIndex: 'occurTime'},
					{header: "服务类型", width: 100, sortable: true, dataIndex: 'serviceType'},
					{header: "服务端口", width: 100, sortable: true, dataIndex: 'port'},
					{header: "状态", width: 40,sortable: true,dataIndex : 'typeValue',renderer : statusRenderer}
					],
				sm: new Ext.grid.RowSelectionModel({singleSelect: true}), //单选
				bbar:new Ext.PagingToolbar({
					pageSize:15,
					store:eventStore,
					displayInfo:true,
					displayMsg:'显示第 {0} 条到 {1} 条记录，一共 {2} 条',
					emptyMsg:'没有数据'
				})
			});
		
			eventStore.load({
				params: {
					deviceId : pDevice.id,
					start:0,
					limit:15
				}
			});
	var viewport = new Ext.Viewport({
        layout:'border',
        style:"text-align:left",
        items:[{
            xtype:'portal',
            region:'center',
            margins:'0 5 5 0',
            items:[{
                columnWidth:.50,
                style:'padding:10px 0 10px 50px',
                items:[{
                		title:"信息面板",
                		layout:"fit",
                		//tools:tools,
                		items:[infoPanel]
                	},{
                		title:"故障面板",
                		layout:"fit",
                		//tools:tools,
                		items:[faultChartPanel]
                	}
                	]
            },{
                columnWidth:.50,
                style:'padding:10px 50px 10px 10px',
                items:[{
                		title:"事件列表",
                		//tools:tools,
                		layout:"fit",
                		items:[eventPanel]
                	},{
                		title:"服务面板",
                		//tools:tools,
                		layout:"fit",
                		items:[servicePanel]
                	}	
               ]
            }]
                   
            /*
             * Uncomment this block to test handling of the drop event. You could use this
             * to save portlet position state for example. The event arg e is the custom 
             * event defined in Ext.ux.Portal.DropZone.
             */
//            ,listeners: {
//                'drop': function(e){
//                    Ext.Msg.alert('Portlet Dropped', e.panel.title + '<br />Column: ' + 
//                        e.columnIndex + '<br />Position: ' + e.position);
//                }
//            }
        }]
    });
		
		}
	};
}();

Ext.onReady(cernet2.nodeInfoApp.init, cernet2.nodeInfoApp);