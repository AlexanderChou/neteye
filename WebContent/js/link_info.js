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
var picType = "";
var ip_param = "";
var index_param = "";

var currentDate = new Date();

Ext.namespace("cernet2");


cernet2.nodeInfoApp = function() {
		Ext.BLANK_IMAGE_URL = 'images/s.gif';
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());//使用cookie记录窗口等状态信息			
			/*
			 * ------------首先获取必要的基本信息----------------
			 */
			//首先从url中获取当前的link数据
			var s = window.location.search.substring(1);
			var pLink = Ext.urlDecode(s);
					
			var upIPPart = pLink.srcIP;
			var upIPPartInf =pLink.srcInfIP;
			var downIPPartInf =pLink.destInfIP;
			var downIPPart = pLink.destIP;	
			var upIfindex = pLink.srcIfIndex;
			var downIfindex = pLink.destIfIndex;
					
			
			if(upIPPart==null || upIPPart=="") {
				upIPPart = pLink.srcIPv6.replace(/:/g,"-");
			}			
			if(upIPPartInf==null || upIPPartInf=="") {
				upIPPartInf = pLink.srcInfIPv6.replace(/:/g,"-");
			}			
			if(downIPPart==null||downIPPart=="") {
				downIPPart = pLink.destIPv6.replace(/:/g,"-");
			}			
			if(downIPPartInf==null||downIPPartInf=="") {
				downIPPartInf = pLink.destInfIPv6.replace(/:/g,"-");
			}
			//流量图的基地址
			var flowChartBaseUrl =  "/file/flow/flowscan/dat/pic/";
			var upPktUrl = flowChartBaseUrl + upIPPart +"_"+ pLink.srcIfIndex +"_pkt.gif?" + currentDate;
			var upLenUrl =  flowChartBaseUrl + upIPPart +"_"+ pLink.srcIfIndex +"_len.gif?" + currentDate;
			var upBitUrl =  flowChartBaseUrl + upIPPart +"_"+ pLink.srcIfIndex +"_bit.gif?" + currentDate;
			var downPktUrl = flowChartBaseUrl + downIPPart +"_"+ pLink.destIfIndex +"_pkt.gif?" + currentDate;
			var downLenUrl =  flowChartBaseUrl + downIPPart +"_"+ pLink.destIfIndex +"_len.gif?" + currentDate;
			var downBitUrl =  flowChartBaseUrl + downIPPart +"_"+ pLink.destIfIndex +"_bit.gif?" + currentDate;
			//故障图的基地址
			var faultChartBaseUrl =  "/file/fault/dat/pic/";
			
			//RTT图
			upRttUrl =  faultChartBaseUrl + upIPPartInf +"_rtt.gif?" + currentDate;
			downRttUrl =  faultChartBaseUrl + downIPPartInf +"_rtt.gif?" + currentDate;
			//LOSS图
			upLossUrl =  faultChartBaseUrl + upIPPartInf +"_loss.gif?" + currentDate;
			downLossUrl =  faultChartBaseUrl + downIPPartInf +"_loss.gif?" + currentDate;
			//设备图的基地址
			var deviceChartBaseUrl = "images/pic/";
			//流量模板
			var flowChartTpl = new Ext.Template(
					'<div style="padding:10px;">',
						'<img src="{pic}">',
					'</div>'
			);
			//故障 模板
			var faultChartTpl = new Ext.Template(
                    '<div style="margin-top:10px;text-align:left;">',
                            '<img src="{pic}">',
                    '</div>'
            );
			// create the data store
			var upEventStore = new Ext.data.Store({
				//使用ScriptTagProxy跨域获取数据，如果数据在同一域内，使用HttpProxy会更好
				proxy : new Ext.data.HttpProxy({
					url :'json/InfoAction_fetchInfEventInfos.do'
				}),
				//创建JsonReader读取interface记录
				reader : new Ext.data.JsonReader({
					root : 'infEventInfoList',
					totalProperty:'totalCount',
					fields : ['id','occurTime','receiveTime','moduleId','moduleType','typeValue']
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
			var upEventPanel = new Ext.grid.GridPanel({
				store : upEventStore,
				height: 400,
				width: 400,
				border: true,
				//列模型
				columns : [
					new Ext.grid.RowNumberer(),
					{header: "发生时间", width: 60, sortable: true, dataIndex: 'occurTime'},
					{header: "接收时间", width: 60, sortable: true, dataIndex: 'receiveTime'},
					{header: "所属模块", width: 80, sortable: true, dataIndex: 'moduleId'},
					{header: "事件类型", width:	 40,sortable: true,dataIndex : 'moduleType'},
					{header: "状态", width: 40,id:'sid',sortable: true,dataIndex : 'typeValue',renderer : statusRenderer}
					],
				sm: new Ext.grid.RowSelectionModel({singleSelect: true}), //单选
				autoExpandColumn:'sid',
				bbar:new Ext.PagingToolbar({
					store:upEventStore,
					pageSize:15,
					displayInfo:true,
					displayMsg:'显示第 {0} 条到 {1} 条记录，一共 {2} 条',
					emptyMsg:'没有数据'
				})
				//frame: true
			});
			
			upEventStore.load({
				params: {
					infId : pLink.srcInfId,//此处应该添加上行端口的id
					start:0,
					limit:15
				}
			});
						
			var downEventStore = new Ext.data.Store({
				//使用ScriptTagProxy跨域获取数据，如果数据在同一域内，使用HttpProxy会更好
				proxy : new Ext.data.HttpProxy({
					url :'json/InfoAction_fetchInfEventInfos.do'
				}),
				//创建JsonReader读取interface记录
				reader : new Ext.data.JsonReader({
					root : 'infEventInfoList',
					totalProperty:'totalCount',
					fields : ['id','occurTime','receiveTime','moduleId','moduleType','typeValue']
					})
			});	
			var downEventPanel = new Ext.grid.GridPanel({
				store : downEventStore,
				height: 400,
				width: 400,
				border: true,
				//列模型
				columns : [
					new Ext.grid.RowNumberer(),
					{header: "发生时间", width: 60, sortable: true, dataIndex: 'occurTime'},
					{header: "接收时间", width: 60, sortable: true, dataIndex: 'receiveTime'},
					{header: "所属模块", width: 80, sortable: true, dataIndex: 'moduleId'},
					{header: "事件类型", width:	 40,sortable: true,dataIndex : 'moduleType'},
					{header: "状态", width: 40,sortable: true,dataIndex : 'typeValue',renderer : statusRenderer}
					],
				sm: new Ext.grid.RowSelectionModel({singleSelect: true}), //单选
				// autoExpandColumn:"typeValue",
				bbar:new Ext.PagingToolbar({
					pageSize:15,
					store:downEventStore,
					displayInfo:true,
					displayMsg:'显示第 {0} 条到 {1} 条记录，一共 {2} 条',
					emptyMsg:'没有数据'
				})
				//frame: true
			});
			
			downEventStore.load({
				params: {
					infId : pLink.destInfId,//此处应该添加下行端的Id
					start:0,
					limit:15
				}
			});
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
			
		    var viewport = new Ext.Viewport({
		        layout:'border',
		        style:"text-align:left",
		        items:[{
		            xtype:'portal',
		            region:'center',
		            margins:'0 5 5 0',
		            items:[{
		                columnWidth:.25,
		                style:'padding:10px 0 10px 20px',
		                items:[{
								layout:"fit",
								title : '上行端口运行状态',
								html : 	'<div style="padding: 10px">' +
											'<table width="100%">' +
											'<tr>' +
											'<td width="50%" align="center"><img src="'+ deviceChartBaseUrl + upIPPart+'_'+upIfindex 
		                                       +'_in.png?' + currentDate + '"/></td>' + 
											'<td align="center"><img src="'+ deviceChartBaseUrl + upIPPart+'_'+upIfindex  +'_out.png?' + currentDate + '" /></td>' +
											'</tr>' +
											'<tr height="15px"></tr>' +
											'<tr>' +
											'<td width="50%" align="center"><img src="'+ deviceChartBaseUrl + upIPPartInf +'_rtt.png?' + currentDate + '" /></td>' +
											'<td align="center"><img src="'+ deviceChartBaseUrl + upIPPartInf +'_loss.png?' + currentDate + '" /></td>' +
											'</tr>' +
											'</table>'+
											'</div>'
		                	},{
		               			title : '上行端口基本信息',
								//layout:"fit",
								items : [new Ext.grid.PropertyGrid({
									collapsed : false,
									collapsible : true,
									height : 600,
									width : 340,
									source : {
										"上行端口状态": '通',
										"上行端口描述" : pLink.srcDescription!=null?pLink.srcDescription:'',
										"上行端口IPv4地址" : pLink.srcInfIP!=null?pLink.srcInfIP:'',
										"上行端口IPv6地址" : pLink.srcInfIPv6!=null?pLink.srcInfIPv6:'',
										"上行端口速率" :   pLink.srcSpeed!=null?pLink.srcSpeed:'',
										"上行端口所属结点名称" : pLink.srcName!=null?pLink.srcName:'',
										"所属结点中文名称" : pLink.srcChineseName!=null?pLink.srcChineseName:'',
										"所属结点IPv4地址" : pLink.srcIP!=null?pLink.srcIP:'',
										"所属结点IPv6地址" : pLink.srcIPv6!=null?pLink.srcIPv6:''
									},listeners:{
										beforeedit:function(e){
											e.cancel = true;
										}	
									}
								})]
		                	},{
		                		title:"上行端口事件列表",
		                		//layout:"fit",
		                		items:[upEventPanel]
		                	}]
		            },{
		            	columnWidth:.25,
		                style:'padding:10px 0px 10px 10px',
		                items:[{
		                		title:"上行端口比特流量图",
		                		//layout:"fit",
		                		html : flowChartTpl.apply({pic:upBitUrl})
		                	},{
		                		title:"上行端口分组流量图",
		                		//layout:"fit",
		                		html : flowChartTpl.apply({pic:upPktUrl})
		                	},{
		                		title:"上行端口包长流量图",
		                		//layout:"fit",
		                		html : flowChartTpl.apply({pic:upLenUrl})
		                	},{
		                		title:"上行端口Loss曲线",
		                		layout:"fit",
		                		html: faultChartTpl.apply({pic:upRttUrl})
		                	},{
		                		title:"上行端口Rtt曲线",
		                		//layout:"fit",
		                		html: faultChartTpl.apply({pic: upLossUrl})
		                	}]
		            },{
		            	columnWidth:.25,
		                style:'padding:10px 0px 10px 10px',
		                items:[{
		                		title:"下行端口比特流量图",
		                		//layout:"fit",
		                		html : flowChartTpl.apply({pic:downBitUrl})
		                	},{
		                		title:"下行端口分组流量图",
		                		html : flowChartTpl.apply({pic:downPktUrl})
		                	},{
		                		title:"下行端口包长流量图",
		                		//layout:"fit",
		                		html : flowChartTpl.apply({pic:downLenUrl})
		                	},{
		                		title:"下行端口Loss曲线",
		                		//layout:"fit",
		                		html: faultChartTpl.apply({pic: downRttUrl})
		                	},{
		                		title:"下行端口Rtt曲线",
		                		//layout:"fit",
		                		html: faultChartTpl.apply({pic: downLossUrl})
		                	}]
		            },{
		                columnWidth:.25,
                		style:'padding:10px 30px 10px 10px',
		                items:[{
								//layout:"fit",
								title : '下行端口运行状态',
								html : 	'<div style="padding: 10px">' +
											'<table width="100%">' +
											'<tr>' +
											'<td width="50%" align="center"><img src="'+ deviceChartBaseUrl + downIPPart+'_'+downIfindex 
		                                       +'_in.png?' + currentDate + '"/></td>' + 
											'<td align="center"><img src="'+ deviceChartBaseUrl + downIPPart +'_'+downIfindex +'_out.png?' + currentDate + '" /></td>' +
											'</tr>' +
											'<tr height="15px"></tr>' +
											'<tr>' +
											'<td width="50%" align="center"><img src="'+ deviceChartBaseUrl + downIPPartInf +'_rtt.png?' + currentDate + '" /></td>' +
											'<td align="center"><img src="'+ deviceChartBaseUrl + downIPPartInf +'_loss.png?' + currentDate + '" /></td>' +
											'</tr>' +
											'</table>'+
											'</div>'
		                	},{
		               			title : '下行端口基本信息',
								//layout:"fit",
								items : [new Ext.grid.PropertyGrid({
									collapsed : false,
									collapsible : true,
									height : 600,
									width : 340,
									source : {
										"下行端口状态": '通',
										"下行端口描述" : pLink.destDescription!=null?pLink.destDescription:'',
										"下行端口IPv4地址" : pLink.destInfIP!=null?pLink.destInfIP:'',
										"下行端口IPv6地址" : pLink.destInfIPv6!=null?pLink.destInfIPv6:'',
										"下行端口速率" :   pLink.destSpeed!=null?pLink.destSpeed:'',
										"下行端口所属结点名称" : pLink.destName!=null?pLink.destName:'',
										"所属结点中文名称" : pLink.destChineseName!=null?pLink.destChineseName:'',
										"所属结点IPv4地址" : pLink.destIP!=null?pLink.destIP:'',
										"所属结点IPv6地址" : pLink.destIPv6!=null?pLink.destIPv6:''
									},listeners:{
										beforeedit:function(e){
											e.cancel = true;
										}	
									}
								})]
		                	},{
		                		title:"下行端口事件列表",
		                		//layout:"fit",
		                		items:[downEventPanel]
		                	}]
		            }]
		        }]
		    });
}

Ext.onReady(cernet2.nodeInfoApp);
