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
 
var picType = "";
var ip_param = "";
var ip_used ="";
var index_param = "";

var currentDate = new Date();

Ext.namespace("cernet2");
cernet2.nodeInfoApp = function() {
	// do NOT access DOM from here; elements don't exist yet
	// private variables
	// private functions
	// public space
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
			//流量图的基地址
			var flowChartBaseUrl =  "/file/flow/flowscan/dat/pic/";
			//故障图的基地址
			var faultChartBaseUrl =  "/file/fault/dat/pic/";
			//设备图的基地址
			var deviceChartBaseUrl = "images/pic/";		
			var ipPart = pDevice.ipv4;//用于拼接图片地址
			var deviceId = pDevice.id;
			if(ipPart==null||ipPart=="") {
				 ipPart = pDevice.ipv6.replace(/:/g,"-");
			}
			// ------------右边（东部的流量面板）,采用border布局，但只使用北和中------------
			// 1.流量图标签面板
			//模板
			
			var flowChartTpl = new Ext.Template(
					'<div style="padding:10px;">',
						'<img src="{pic}" onerror="this.src={errgif}">',
					'</div>'
			);
			//标记tab面板的id
			var PK_PID ='packageChartId';
			var PK_LEN_PID='packageLenChartId';
			var BIT_PID='bitChartId';
			var flowChartTabPanel = new Ext.Panel({
				region : 'north',
				width : 700,
				height : 250,
				split:'true',
				collapsible: true,
				collapseMode: 'mini',
				margins : '0 0 3 0',
				layout:'column',
				defaults : { // 子项目元素(Compenent)的默认属性
				},
				items : [{
							id: PK_PID,
							columnWidth: .33,
							height: 250,
							title : '分组统计图',
				 //  	html : flowChartTpl.apply({pic:'imgs/blue-loading.gif',errgif:'images/null.gif'}),
					//		html : flowChartTpl.apply({pic:'images/null.gif'}),
							listeners : {
					        render : function() {//渲染后添加click事件
					          Ext.get(this.el).on('click', function(e, t) {
								   picType = "len";	
								   ip_param = ipPart;
								   index_param = ipIndex;				            
					               Ext.Ajax.request({
					               		url:"fixTimePic.do?routerIP="+ipPart+"&ifIndex="+ipIndex+"&picType=" + picType + "&trafficIP=" + ipPart + "&type=1",
					               		success:function(response, request){
					               			showWindow(response.responseText);
					               		}
					               });
					          });
					         }
       					 	}
						}, {
							id: PK_LEN_PID,
							columnWidth: .34,
							height: 250,
							title : '包长统计图',
						//	html : flowChartTpl.apply({pic:'imgs/blue-loading.gif',errgif:'images/null.gif'}),
							listeners : {
					          render : function() {//渲染后添加click事件
					          Ext.get(this.el).on('click', function(e, t) {
					               picType = "pkt";	
								   ip_param = ipPart;
								   index_param = ipIndex;				            
					               Ext.Ajax.request({
					               		url:"fixTimePic.do?routerIP="+ipPart+"&ifIndex="+ipIndex+"&picType=" + picType + "&trafficIP="+ipPart + "&type=2",
					               		success:function(response, request){
					               			showWindow(response.responseText);
					               		}
					               });
					            });
					         }
       					 	}
						}, {
							id: BIT_PID,
							columnWidth: .33,
							height: 250,
							title : '比特统计图',
						//	html : flowChartTpl.apply({pic:'imgs/blue-loading.gif',errgif:'images/null.gif'}),
							listeners : {
						          render : function() {//渲染后添加click事件
						          Ext.get(this.el).on('click', function(e, t) {
							               picType = "bit";	
										   ip_param = ipPart;
										   index_param = ipIndex;				            
							               Ext.Ajax.request({
							               		url:"fixTimePic.do?routerIP="+ipPart+"&ifIndex="+ipIndex+"&picType=" + picType + "&trafficIP="+ipPart + "&type=3",
							               		success:function(response, request){
							               			showWindow(response.responseText);
							               		}
							               });
						            });
						         }
	      					 }
					}]
				});
			// 2.设备端口表格面板
			// create the data store
			var interfaceStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url :'/json/InfoAction_fetchInterfaces.do?deviceId=' + deviceId
				}),
				//创建JsonReader读取interface记录
				reader : new Ext.data.JsonReader({
					root : 'interfaceList',
					totalProperty:'totalCount',
					fields : ['id','index','ipv4','ipv6','speed',
						'managementStatusV4','operationStatusV4'
						,'managementStatusV6','operationStatusV6']
					})
			});
		//	interfaceStore.setDefaultSort('ipv4','DESC'); //ASC
			interfaceStore.setDefaultSort('index','DESC'); //ASC
			function ifStatusRenderer(val){
				if (val == '1') {
					return '<img src="images/green.jpg?' + currentDate + '" width="15px" height="5px"/>';
				}
				else if(val == '2'){
					return '<img src="images/red.jpg?' + currentDate + '" width="15px" height="5px"/>';
				} else {
					return '<img src="images/gray.jpg?' + currentDate + '" width="15px" height="5px"/>';
				}
			}
			var interfacePanel = new Ext.grid.GridPanel({
				store : interfaceStore,
				iconCls : 'settings',
				height: 365,
				autoScroll:true,
				//列模型
				columns : [
					new Ext.grid.RowNumberer(),
						{header: "端口号", width: 40, sortable: true, dataIndex: 'index'}, 
						{header: "ipv4", width: 50 ,sortable: true,dataIndex: 'ipv4'}, 
						{header: "ipv6", width: 120,sortable: true,dataIndex : 'ipv6'},
						{header: "速率(M)", width: 50,sortable: true,dataIndex : 'speed'},
						{header: "v4管理状态", width: 70,sortable: true,dataIndex : 'managementStatusV4',renderer : ifStatusRenderer},
						{header: "v4运行状态", width: 70,sortable: true,dataIndex : 'operationStatusV4',renderer : ifStatusRenderer},
						{header: "v6管理状态", width: 70,sortable: true,dataIndex : 'managementStatusV6',renderer : ifStatusRenderer},
						{header: "v6运行状态", width: 70,sortable: true,dataIndex : 'operationStatusV6',renderer : ifStatusRenderer}
					],
				sm: new Ext.grid.RowSelectionModel({singleSelect: true}), //单选
				bbar:new Ext.PagingToolbar({
					pageSize:15,
					store:interfaceStore,
					displayInfo:true,
					displayMsg:'显示第 {0} 条到 {1} 条记录，一共 {2} 条',
					emptyMsg:'没有数据'
				})
			});
			
			var ipIndex = "0";
				
			interfacePanel.getSelectionModel().on('rowselect',function(sm,rowIdx,/*Ext.data.Record*/r) {
				ipIndex = r.get("index");
				ipPartV4 = r.get("ipv4");
				ipPartV6 = r.get("ipv6");
				
		          
				
				
				
				//分组统计图
				var panel = Ext.getCmp(PK_PID);
				var picUrl = flowChartBaseUrl + pDevice.ipv4 +"_"+ ipIndex +"_pkt.gif?" + currentDate;
				flowChartTpl.overwrite(panel.body,{pic:picUrl,errgif:"'images/null.gif'"});
				//包长统计图
				panel = Ext.getCmp(PK_LEN_PID);
				picUrl =  flowChartBaseUrl + pDevice.ipv4 +"_"+ ipIndex +"_len.gif?" + currentDate;
		//		picUrl = "images/null.gif";
				flowChartTpl.overwrite(panel.body,{pic:picUrl,errgif:"'images/null.gif'"});
				//比特图
				panel = Ext.getCmp(BIT_PID);
				picUrl =  flowChartBaseUrl + pDevice.ipv4 +"_"+ ipIndex +"_bit.gif?" + currentDate;
		//		picUrl = "images/null.gif";
				flowChartTpl.overwrite(panel.body,{pic:picUrl,errgif:"'images/null.gif'"});
				
				//故障图
				var badipPart ="127.0.0.1";
				if (ipPartV4 != "") {
					badipPart = ipPartV4;
				} else {
					if (ipPartV6 != "") {
						badipPart = ipPartV6;
					}
				}
				
				if (badipPart ==  null ||  badipPart ==  'null' || badipPart == "") {
                        badipPart = pDevice.ipv4;
                }
				
				//RTT图
				panel = Ext.getCmp("RTT_PIC");
				picUrl =  faultChartBaseUrl + badipPart +"_rtt.gif?" + currentDate;
				faultChartTpl.overwrite(panel.body,{pic:picUrl,errgif:"'images/null_rtt.gif'"});
				
				//LOSS图
				panel = Ext.getCmp("LOSS_PIC");
				picUrl =  faultChartBaseUrl + badipPart +"_loss.gif?" + currentDate;
				faultChartTpl.overwrite(panel.body,{pic:picUrl,errgif:"'images/null_loss.gif'"});
			});
			
			//获取数据
			interfaceStore.load({
				params: {
					deviceId : pDevice.id,
					start:0,
					limit:15
				},
				callback:function(r,option,success){
					if(r.length>0) {
						interfacePanel.getSelectionModel().selectRow(1);
					}
				}
			});
			

			
			// 信息面板
		    var infoPanel = new Ext.Panel({
				height: 285,
				margins : '0 0 5 5',
				layout:'column',
				items : [{
							border : true,
							title : '运行状态',
							height: 270,
							columnWidth: .5,
							html : 	'<div style="padding: 10px">' +
										'<table width="100%">' +
										'<tr>' +
										'<td width="50%" align="center"><img src="'+ deviceChartBaseUrl + ipPart 
                                        +'_temperature.png?' + currentDate + '"/></td>' + 
										'<td align="center"><img src="'+ deviceChartBaseUrl + ipPart +'_cpu_mem.png?' + currentDate + '" /></td>' +
										'</tr>' +
										'<tr height="15px"></tr>' +
										'<tr>' +
										'<td width="50%" align="center"><img src="'+ deviceChartBaseUrl + ipPart +'_rtt.png?' + currentDate + '" /></td>' +
										'<td align="center"><img src="'+ deviceChartBaseUrl + ipPart +'_loss.png?' + currentDate + '" /></td>' +
										'</tr>' +
										'</table>'+
										'</div>'
						}, {
							title : '基本信息',
							border : true,
							height: 270,
							layout:"fit",
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
                    '<div style="margin-top:10px;text-align:left;">',
                     //       '<img src="{pic}">',
                    '<img src="{pic}" onerror="this.src={errgif}">',
                    '</div>'
            );
           var faultChartPanel = new Ext.Panel({
				region: 'north',
				height : 240,
				border: false,
				split:'true',
				collapsible: true,
				collapseMode: 'mini',
				margins : '0 0 2 0',
				layout:'column',
				items: [
					{
						id:"RTT_PIC",
						height: 240,
						columnWidth: .5,
						title: 'RTT图',
						html: faultChartTpl.apply({pic: faultChartBaseUrl + ipPart + "_rtt.gif"})
					},
					{
						id:"LOSS_PIC",
						height: 240,
						columnWidth: .5,
						title: 'LOSS图',
						html: faultChartTpl.apply({pic: faultChartBaseUrl + ipPart + "_loss.gif"})
					}
				]
			});
			
			// create the data store
			var eventStore = new Ext.data.Store({
				//使用ScriptTagProxy跨域获取数据，如果数据在同一域内，使用HttpProxy会更好
				proxy : new Ext.data.HttpProxy({
					url :'/json/InfoAction_fetchEventInfos.do'
				}),
				//创建JsonReader读取interface记录
				reader : new Ext.data.JsonReader({
					root : 'eventInfoList',
					totalProperty:'totalCount',
					fields : ['id','occurTime','receiveTime','ifIndex','ipv4','ipv6','moduleType',
										'title','typeValue']
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
				height: 400,
				width: 400,
				border: true,
				//列模型
				columns : [
					new Ext.grid.RowNumberer(),
					{header: "发生时间", width: 60, sortable: true, dataIndex: 'occurTime'},
					{header: "接收时间", width: 60, sortable: true, dataIndex: 'receiveTime'},
					{header: "端口号", width: 80, sortable: true, dataIndex: 'ifIndex'},
					{header: "端口ipv4地址", width: 100,sortable: true,dataIndex: 'ipv4'}, 
					{header: "端口ipv6地址", width:	 130,sortable: true,dataIndex : 'ipv6'},
					{header: "类型", width:	 40,sortable: true,dataIndex : 'moduleType'},
					{id:"description", header: "描述", width:	 50,sortable: true,dataIndex : 'title'},
					{header: "状态", width: 40,sortable: true,dataIndex : 'typeValue',renderer : statusRenderer}
					],
				sm: new Ext.grid.RowSelectionModel({singleSelect: true}), //单选
				autoExpandColumn:"description",
				bbar:new Ext.PagingToolbar({
					pageSize:15,
					store:eventStore,
					displayInfo:true,
					displayMsg:'显示第 {0} 条到 {1} 条记录，一共 {2} 条',
					emptyMsg:'没有数据'
				})
				//frame: true
			});
			
			eventStore.load({
				params: {
					deviceId : pDevice.id,
					start:0,
					limit:15
				}
			});
			
					
			
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

    // create some portlet tools using built in Ext tool ids
    var tools = [{
        id:'gear',
        handler: function(){
            Ext.Msg.alert('Message', 'The Settings tool was clicked.');
        }
    },{
        id:'close',
        handler: function(e, target, panel){
            panel.ownerCt.remove(panel, true);
        }
    }];
    
    function reflash(infoPanel){
    	infoPanel.doLayout();
    }

	 setInterval(function(){
                 infoPanel.doLayout();
                 eventStore.reload();
        }, 10000);

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
                	},{
                		title:"事件列表",
                		//tools:tools,
                		layout:"fit",
                		items:[eventPanel]
                	}	
                	]
            },{
                columnWidth:.50,
                style:'padding:10px 50px 10px 10px',
                items:[{
                		title:"流量面板",
                		//tools:tools,
                		layout:"fit",
                		items:[flowChartTabPanel]
                	},{
                		title:"端口面板",
                		//tools:tools,
                		items:[interfacePanel]
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
