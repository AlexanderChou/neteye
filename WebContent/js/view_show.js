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
//显示设备基本信息的面板
cernet2.BaseInfoPanel = function() {
	this.panel = new Ext.Panel ({
		region: 'north',
		height: 180,
		border: false
	});
}
cernet2.BaseInfoPanel.prototype = {
	//面板的样式模板
	infoTpl: new Ext.Template(
				'<div style="margin-top:0px;text-align:left;">',
					'<table width="100%" align="center">',
					'	<tr><td><font face="黑体" size="3">名称: </font></td><td><font face="黑体" size="3">{name}</font></td></tr><tr><td><font face="黑体" size="3">ipv4: </font></td><td><font face="黑体" size="3">{ipv4}</font></td></tr>',
					'	<tr><td><font face="黑体" size="3">中文名称: </font></td><td><font face="黑体" size="3">{chineseName}</font></td></tr><tr><td><font face="黑体" size="3">ipv6: </font></td><td><font face="黑体" size="3">{ipv6}</font></td></tr>', 
					'</table>',
					'<table width="100%"><tr>',
					'<td><img src="{tempPic}"></td>',
					'<td><img src="{cpumemPic}"></td>',
					'</tr></table>',
				'</div>'
				),
	getPanel: function() {
		return this.panel;
	},
	updatePanel: function(device) {
		var baseUrl = "images/pic/";
		var ip = device.ipv4;
		if(ip==null||ip=="") {
			ip = device.ipv6.replace(/:/g,"-");
		}
		var cfg = {
			name: device.name,
			chineseName: device.chineseName,
			ipv4: device.ipv4,
			ipv6: device.ipv6,
			tempPic: baseUrl+ ip + "_temperature.png",
			cpumemPic: baseUrl + ip +"_cpu_mem.png"
		}
		this.panel.body.update(this.infoTpl.apply(cfg));
	}
};

//显示设备链路流量信息的面板
cernet2.FlowPanel = function() {
	this.drawer = new jsGraphics("linkInfo");//用于显示链路利用率tip
	
	var flowChartTpl = cernet2.FlowPanel.prototype.flowTpl;
	this.panel = new Ext.TabPanel({
		activeTab : 0,
		height: 250,
		plain : true,
		defaults : { // 子项目元素(Compenent)的默认属性
			//autoScroll : true
		},
		// 每一个元素都是一个Panel
		items : [{
					id: cernet2.FlowPanel.prototype.BIT_PID,
					title : '比特统计图',
					// listeners: {activate: handleActivate},
					html : flowChartTpl.apply({pic:'images/blue-loading.gif'})
				},
				{
					id: cernet2.FlowPanel.prototype.PK_PID,
					title : '分组统计图',
					html : cernet2.FlowPanel.prototype.flowTpl.apply({pic:'images/blue-loading.gif'})
				}, {
					id: cernet2.FlowPanel.prototype.PK_LEN_PID,
					title : '包长统计图',
					html : flowChartTpl.apply({pic:'images/blue-loading.gif'})
				}]
	});
	var thisFlowPanel = this;
	//timerTask的作用是轮流显示设备的链路信息
	this.timerTask = {
		device: null,	//目前针对哪个设备， must be set before used
		lastDevice: null, //上一次显示的设备
		pos: 0,	//当前设备的目前的链路
		lastPos: 0,	//当前设备的上一次链路
		started: false,
    	run: function(){
    		if(!this.device||!thisFlowPanel) {
	    			return;
	    	}
    	    if(this.device.links.length!=0){//有可能设备没有链路
	    		//reset the last link
	    		if(this.lastPos != this.pos) {
	    			 var lastLink = this.device.links[this.lastPos][1];
	    			 lastLink.clear();
	    			 lastLink.paint();
	    		}
	    		
	    		this.lastPos = this.pos;
	    		//update the flow chart info
	    		thisFlowPanel.doUpdatePanel(this.device,this.device.links[this.pos][0]);	
	    		var curLink = this.device.links[this.pos][1];
	    		curLink.clear();
	    		curLink.paint(7);
	    		
	    		var inFlow,outFlow;
	    		var inFlowStr,outFlowStr
	    		if(this.device.name == curLink.srcName) {
	    			outFlow = curLink.srcFlow;
	    			inFlow = curLink.destFlow;
	    			outFlowStr = curLink.srcFlowStr;
	    			inFlowStr = curLink.destFlowStr;
	    		}else {
	    			outFlow = curLink.destFlow;
	    			inFlow = curLink.srcFlow;
	    			outFlowStr = curLink.destFlowStr;
	    			inFlowStr = curLink.srcFlowStr;
	    		}
	    		if(outFlowStr==null){
					outFlowStr = 0.0;
				}
				if(inFlowStr==null){
					inFlowStr = 0.0;
				}
				var tipContent = "出:" + outFlowStr  + "<br/>" +"入:"+ inFlowStr;
	    		var tx = (curLink.point1[0]+curLink.point2[0])/2;
	    		var ty = (curLink.point1[1]+curLink.point2[1])/2;
	    		thisFlowPanel.drawer.clear();
	    		thisFlowPanel.drawer.setFont("verdana","11px",Font.BOLD);
	    		thisFlowPanel.drawer.drawString(tipContent,tx-15,ty-10);
	    		thisFlowPanel.drawer.paint();	
	    		this.pos++;
	        	this.pos%=this.device.links.length;
	        }else{
	    		thisFlowPanel.doUpdatePanel();	            
	        }
		},
	    interval: 2*1000 //2 second
	}
}
cernet2.FlowPanel.prototype = {
	PK_PID: 'packageChartId',
	PK_LEN_PID: 'packageLenChartId',
	BIT_PID: 'bitChartId',
	flowTpl: new Ext.Template(
		'<div style="margin-top:10px;text-align:center;">',
			'<img src="{pic}">',
		'</div>'
	),
	getPanel: function() {
		return this.panel;
	},
	doUpdatePanel: function(device,ifindex) {
	    if(device){
			var theActiveTab = this.panel.getActiveTab();
			var panel = Ext.getCmp(this.PK_PID);
			this.panel.setActiveTab(panel);
			var ip = device.ipv4;
			if(ip==null||ip=="") {
				ip = device.ipv6.replace(/:/g,"=");
			}	
			//分组统计图
			var flowChartBaseUrl="file/flow/flowscan/dat/pic/";
			var picUrl = flowChartBaseUrl + ip +"_"+ ifindex +"_pkt.gif";
			this.flowTpl.overwrite(panel.body,{pic:picUrl});
			//包长统计图
			panel = Ext.getCmp(this.PK_LEN_PID);
			this.panel.setActiveTab(panel);
			picUrl =  flowChartBaseUrl + ip +"_"+ ifindex +"_len.gif";
			this.flowTpl.overwrite(panel.body,{pic:picUrl});
			//比特图
			panel = Ext.getCmp(this.BIT_PID);
			this.panel.setActiveTab(panel);
			picUrl =  flowChartBaseUrl + ip +"_"+ ifindex +"_bit.gif";
			this.flowTpl.overwrite(panel.body,{pic:picUrl});	
			this.panel.setActiveTab(theActiveTab);
		}else{//如果设备没有链路，则链路信息面板处为空白
		    var theActiveTab = this.panel.getActiveTab();
			var panel = Ext.getCmp(this.PK_PID);
			this.panel.setActiveTab(panel);
			var picUrl = "images/s.gif";
			this.flowTpl.overwrite(panel.body,{pic:picUrl});
			panel = Ext.getCmp(this.PK_LEN_PID);
			this.panel.setActiveTab(panel);
			this.flowTpl.overwrite(panel.body,{pic:picUrl});
			panel = Ext.getCmp(this.BIT_PID);
			this.panel.setActiveTab(panel);
			this.flowTpl.overwrite(panel.body,{pic:picUrl});									
		}
	},
	updatePanel: function(device) {
		//停止定时器
		if(this.timerTask.started) {
			Ext.TaskMgr.stop(this.timerTask);
			this.timerTask.started = false;
	    	this.drawer.clear();//清除上一个路由器最后的链路信息
		}		
		//确保恢复上个路由器的链路显示
		var lastDevice = this.timerTask.device;
		if(lastDevice){
			for(var i = 0;i<lastDevice.links.length;i++) {
				lastDevice.links[i][1].clear();
				lastDevice.links[i][1].paint();
			}
		}
		this.timerTask.device = device;
		//重置定时器
		this.timerTask.pos=0;
		this.timerTask.lastPos=0;
		Ext.TaskMgr.start(this.timerTask);
		this.timerTask.started = true;	
	}
};
//显示设备故障信息的面板
cernet2.FaultPanel = function(){
	var proto = cernet2.FaultPanel.prototype;	//为方便访问
	this.panel = new Ext.TabPanel({
		activeTab : 0,
		height: 250,
		plain : true,
		defaults : { // 子项目元素(Compenent)的默认属性
			//autoScroll : true
		},
		items : [{
					id: proto.RTT_PID,
					title : 'RTT曲线',
					html : proto.faultTpl.apply({pic:'images/blue-loading.gif'})
				}, {
					id: proto.LOSS_PID,
					title : 'LOSS曲线',
					html : proto.faultTpl.apply({pic:'images/blue-loading.gif'})
				}
				]
	});
	var thisFaultPanel = this;
	this.timerTask = {
			device:null,
			lastDevice:null,
			pos:0,
			lastPos:0,
			started:false,
			run:function(){
				if(!this.device||!thisFaultPanel){
					return;
				}
				if(this.device.links.length!=0){					
		    		this.lastPos = this.pos;
		    		var curLink = this.device.links[this.pos][1];
		    		var ip;
		    		if(this.device.name == curLink.srcName){
		    			ip = curLink.srcInfIP;
		    			if(ip==null||ip=="") {
		    				ip =  curLink.srcInfIPv6.replace(/:/g,"-");
		    			}
		    		}else{
		    			ip = curLink.destInfIP;
		    			if(ip==null||ip=="") {
		    				ip =  curLink.destInfIPv6.replace(/:/g,"-");
		    			}
		    		}
		    		thisFaultPanel.doUpdatePanel(this.device,ip);	
		    		this.pos++;
		        	this.pos%=this.device.links.length;
				}else{
					thisFaultPanel.doUpdatePanel();
				}
			},
			interval: 2*1000 //2 second
	}
};
cernet2.FaultPanel.prototype = {
	//只读的公用的属性可以放prototype里面,当然放构造函数里也行
	faultTpl: new Ext.Template(
		'<div style="margin-top:10px;text-align:center;">',
			'<img src="{pic}">',
		'</div>'
	),
	RTT_PID: 'rttId',
	LOSS_PID: 'lossId',
	getPanel: function() {
		return this.panel;
	},
	doUpdatePanel: function(device,linkIP) {
	    if(device){
	    	var theActiveTab = this.panel.getActiveTab();
			var panel = Ext.getCmp(this.RTT_PID);
			this.panel.setActiveTab(panel);
			//RTT图
			var faultChartBaseUrl="file/fault/dat/pic/";
			var picUrl = faultChartBaseUrl + linkIP + "_rtt.gif";
			this.faultTpl.overwrite(panel.body,{pic:picUrl});
			//Loss图
			panel = Ext.getCmp(this.LOSS_PID);
			this.panel.setActiveTab(panel);
			picUrl =  faultChartBaseUrl + linkIP + "_loss.gif";
			this.faultTpl.overwrite(panel.body,{pic:picUrl});	
			this.panel.setActiveTab(theActiveTab);
	    }else{
		    var theActiveTab = this.panel.getActiveTab();
			var panel = Ext.getCmp(this.RTT_PID);
			this.panel.setActiveTab(panel);
			var picUrl = "images/s.gif";
			this.faultTpl.overwrite(panel.body,{pic:picUrl});
			panel = Ext.getCmp(this.LOSS_PID);
			this.panel.setActiveTab(panel);
			this.faultTpl.overwrite(panel.body,{pic:picUrl});
	    }
	},
	updatePanel: function(device) {
		//停止定时器
		if(this.timerTask.started) {
			Ext.TaskMgr.stop(this.timerTask);
			this.timerTask.started = false;
	    	//this.drawer.clear();//清除上一个路由器最后的链路信息
		}		
		//确保恢复上个路由器的链路显示
		var lastDevice = this.timerTask.device;
		if(lastDevice){
			for(var i = 0;i<lastDevice.links.length;i++) {
				lastDevice.links[i][1].clear();
				lastDevice.links[i][1].paint();
			}
		}
		this.timerTask.device = device;
		//重置定时器
		this.timerTask.pos=0;
		this.timerTask.lastPos=0;
		Ext.TaskMgr.start(this.timerTask);
		this.timerTask.started = true;
	}
};
//路由器信息窗口，包含上面3个面板
//singleton单实例模式
cernet2.DeviceInfoWindow = function() {
	// 不能放Ext之类的
	var winInstance = null;
	return {
		getInstance : function() {
			Ext.BLANK_IMAGE_URL = 'images/s.gif';
			//此if语句的条件很重要，winInstance == null作用是如果cernet2.DeviceInfoWindow.getInstance（）方法
			//是第一次调用，则会执行if里面的语句，否则只是将上次生成的实例返回，然后由update函数负责更新面板的内容。
			//cernet2.Device.prototype.destroyInfoWinFlag=='true'的作用是如果用户将此面板关闭，那么此实例中
			//winInstance中的win窗口会被破坏，那么里面相应的show方法将不起作用，所以同样需要进入if里面重新生成winInstance
			if (winInstance == null||cernet2.Device.prototype.destroyInfoWinFlag=='true') {
			    cernet2.Device.prototype.destroyInfoWinFlag='false';
				var basePanel = new cernet2.BaseInfoPanel();
				var flowPanel = new cernet2.FlowPanel();
				var faultPanel = new cernet2.FaultPanel();
				//建立窗口实例
				winInstance = {
					baseInfoPanel: basePanel,
					flowInfoPanel: flowPanel,
					faultInfoPanel: faultPanel,
					win: new Ext.Window({
						applyTo : 'deviceInfoWin',
						onEsc: Ext.emptyFn,
						width : 300,
						height : 750,
						pageX: 1600,
						pageY: 5,
						title:'设备信息',
						expandOnShow:false,
                        collapsible: true,
						collapsed:true,
						layout:'border',
						layoutConfig:{
                        	animate:true//折叠或展开时应用动画效果
                    	},
                    	listeners:{"beforedestroy":function(obj){
                    	                             cernet2.Device.prototype.windowFlag2='true';
		                                             cernet2.Device.prototype.windowFlag='false';
		                                             cernet2.DeviceInfoWindow.getInstance().hidden();
		                                             cernet2.Device.prototype.destroyInfoWinFlag='true';
                    	                           }
                    	                           
                    	},                   	
						items: [
								basePanel.getPanel(),
							{
								region: 'center',
								layout:'border',
                    			layoutConfig:{
                        			animate:true
                    			},
                    			items:[
	                    			{
	                    				region: 'north',
	                    				collapsible : true,
	                    				height: 260,
			                        	title:'链路信息',
				                        border:false,
				                        iconCls:'nav',
				                        items: flowPanel.getPanel()
			                        },{
			                        	region: 'center',
			                        	collapsible : true,
			                        	height: 260,
			                        	title:'故障信息',
				                        border:false,
				                        iconCls:'nav',
				                        items: faultPanel.getPanel()
			                        }
                    			]
							}
						]
					}),
					show: function(/*device*/) {
						this.win.show();
					},
					update: function(device) {
						Ext.Ajax.request({
							url : 'json/generatePhyPic.do',
							disableCaching : true,
							params : {
								ipv4 : device.ipv4,
								ipv6 : device.ipv6
							},
							method : 'post',
							success : function(result, request) {							    
							},
							failure : function(result, request) {
								//Ext.Msg.alert("<font color='black'>生成图形时发生错误!</font>");
							}
						});
						
						this.baseInfoPanel.updatePanel(device);
						this.flowInfoPanel.updatePanel(device);
						this.faultInfoPanel.updatePanel(device);
					},
					hidden:function(){
					    var thisFlowPanel=cernet2.DeviceInfoWindow.getInstance().flowInfoPanel;
					    var thisFaultPanel=cernet2.DeviceInfoWindow.getInstance().faultInfoPanel;
					    if(thisFlowPanel.timerTask.started) {
			                Ext.TaskMgr.stop(thisFlowPanel.timerTask);
			                thisFlowPanel.timerTask.started = false;
	    	                thisFlowPanel.drawer.clear();//清除上一个路由器最后的链路信息
		                }	
					    if(thisFaultPanel.timerTask.started) {
					    	Ext.TaskMgr.stop(thisFaultPanel.timerTask);
					    	thisFaultPanel.timerTask.started = false;
					    	//thisFaultPanel.drawer.clear();//清除上一个路由器最后的链路信息
					    }	
		                //确保恢复上个路由器的链路显示
		                var lastDevice = thisFlowPanel.timerTask.device;
		                if(lastDevice){
			                 for(var i = 0;i<lastDevice.links.length;i++) {
				                  lastDevice.links[i][1].clear();
				                  lastDevice.links[i][1].paint();
			                 }
		                }
		                var lastDevice2 = thisFaultPanel.timerTask.device;
		                if(lastDevice2){
		                	for(var i = 0;i<lastDevice2.links.length;i++) {
		                		lastDevice2.links[i][1].clear();
		                		lastDevice2.links[i][1].paint();
		                	}
		                }
		                thisFlowPanel.timerTask.pos=0;
		                thisFlowPanel.timerTask.lastPos=0;
		                thisFaultPanel.timerTask.pos=0;
		                thisFaultPanel.timerTask.lastPos=0;
					    this.win.hide();
					}
				}
			}
			return winInstance;
		}
	};
}();
//设备轮询显示的进度条。
cernet2.ProgressBar = function() {
	var pbarInstance= null;
	return {
		getInstance: function() {
			if(pbarInstance == null) {
				pbarInstance = {
					pbar: new Ext.ProgressBar({
						id: 'pbar',
						text: '目前进度: 0 of 0',
						width: 300,
						renderTo: 'pbar'
					}),
					show: function() {
						this.pbar.show();
					},
					updateProcess: function(cur,total) {
						this.pbar.updateProgress(cur/total,"目前进度: "+ cur + " of " + total );
					}
				};
			}
			return pbarInstance;
		}
	};
}();
//cfg是配置对象，pDevice是父级路由器
cernet2.Device = function(cfg, pDevice) {
	if (pDevice == null) {
		// 树根路由器，一个假的路由器
		this.id = 0;
		this.name = '';
		this.chineseName = '';
		this.ipv4 = "0.0.0.0";
		this.ipv6 = '';
		this.rX = 0;	
		this.rY = 0;	
		// 设置图片大小为0，不显示
		this.imgWidth = "0px";
		this.imgHeight = "0px";
	} else {
	    this.productor;
	    this.model;
	    this.serial;
	    this.location;
	    this.label;
	    this.description;
	    this.ifNum;
	    this.subView;
	    this.trafficIp;
	    this.faultFlag;
	    this.picture;
	    this.deviceType;
		// 配置设备属性
		Ext.apply(this, cfg);	
		// 设备图标大小
		this.imgWidth;
		this.imgHeight;	
	}
	this.pDevice = pDevice;
	this.children = [];	//下级子路由器
	this.childrenLink = [];	//下级的子链路
	this.elInBig = null; // 大图中对应的Ext.Element
	this.links = []; /*数组每个元素格式为[ifindex,link]*/ //与自己关联的链路，这里只处理第一级的就行	
	this.services=[];
	//用于处理异步的问题
	this.fetchChildrenDone = false;
	this.fetchChildrenLinkDone = false;
	this.fetchServiceDone=false;	
};
cernet2.Device.prototype = {
	// 属性的默认值
	id : 0,
	name : 'device',
	chineseName : '设备',
	ipv4 : '0.0.0.0',
	ipv6 : '',
	rX : 0,
	rY : 0,
	nX : 0,
	nY : 0,
	status : 'green',
	bigMapId: 'bigMap',
	currentDevice: null,	//static
	deviceType:'router',
	windowFlag:'false',     
	windowFlag2:'false',    //以上两个标记是用来控制信息面板的显示与隐藏
	destroyInfoWinFlag:'false',	//此标记用来指明信息面板是否被关闭
	/*以下两个标记是用来控制轮循以及故障告警时，匹配轮循和告警显示的图标文件名 正常：green__manuIcon_modelIcon.gif
	 * 轮循：purple__manuIcon_modelIcon.gif  告警:red__manuIcon_modelIcon.gif
	 */
	manuIcon:'',
	modelIcon:'',
	focusTimerTask: {
		root: null,	//must be set, a little ugly
		pos: 0,
		run: function(){
			if(!this.root) {
				return;
			}
			if(!this.root.fetchChildrenLinkDone) { //应该等到一起就绪后才开始
				this.interval = 300;
				return;
			}else {
				this.interval = 10000;
			}
			if(this.root.children.length!=0){//如果视图中的设备为空，则不进行轮询
				this.root.children[this.pos++].onChosen();
				//设置进度条
				//cernet2.ProgressBar.getInstance().updateProcess(this.pos,this.root.children.length);
				//
	        	this.pos%=this.root.children.length;
	        }
		},
		interval: 10000 //10 second
	},
	delayedTask: new Ext.util.DelayedTask(),
    	//路由器的显示模板
	template: new Ext.Template(
				'<div id="{id}" class="device_class" >',
					'<img src="{pic}" width="{width}px" height="{height}px"/>',
					'<br/>',
					'<p style="white-space:nowrap;font-size:{fontSize}px;font-weight: {fontWeight};">{name}<p/>',
				'</div>'),
	// public functions
	showDeviceInfo: function() {
		cernet2.DeviceInfoWindow.getInstance().update(this);
	},
	hideDeviceInfo: function() {
	    cernet2.DeviceInfoWindow.getInstance().hidden();
	},
	onMouseOverInBigMap: function() {
		//停止delayed的任务
		this.delayedTask.cancel();
		//停止轮转
		Ext.TaskMgr.stop(this.focusTimerTask);
		//选中当前者
		this.onChosen();
		subViewOfCurrentObj = this.subView;
	},
	onMouseOutInBigMap : function() {
		    //delay some time to let the user to do something himself
			var fn = function() {
				this.unFocus();
				Ext.TaskMgr.start(this.focusTimerTask);
			}
			this.delayedTask.cancel();
			this.delayedTask.delay(2000,fn,this);
	},
	onClick: function() {
		
	 	 	if(subViewOfCurrentObj != null && subViewOfCurrentObj !=""){
 	 	 		window.location.href = "read.do?viewId="+subViewOfCurrentObj;
 //	 	 		alert("subview="+subViewOfCurrentObj);
 	 	 	}else{
 	 	 		Ext.Msg.alert('系统信息', '该设备没有下级子视图！');
 	 	 	}
		
//		var s = Ext.urlEncode(this);
//		//alert("device s="+s);
//		if(this.deviceType=='router'||this.deviceType=='switch'){
//		    document.location.href = 'routerOrSwitchInfo.do?'+s;
//		} else if(this.deviceType=='server'){
//		    document.location.href = 'serverInfo.do?'+s,'serverInfo';
//		} else {
//		    document.location.href = 'workstationInfo.do?'+s,'workstationInfo';
//		}
	},
	//private
	showInTarget: function(target,id) {
		var fontSize = 12;
		var fontWeight = "plain";
		var targetEl = Ext.get(target);
		var deviceNode = this.template.append(targetEl, {
			id : id,	// 以路由器的英文名称+ip地址作为Element的id		
			name : this.chineseName,
			pic : this.picture,
			width : this.imgWidth,
			height : this.imgHeight,
			fontSize: fontSize,
			fontWeight: fontWeight
			});
		
		var xy = [this.rX,this.rY];
		var deviceEl = Ext.get(deviceNode);
		deviceEl.setStyle("position", "absolute");
		deviceEl.setLeftTop(xy[0],xy[1]);
		var deviceImgEl = Ext.get(deviceNode.childNodes[0]);
		deviceImgEl.on("click", this.onClick,this);//click事件
		return deviceEl;
	},
	//画在大图中
	showInBigMap: function() {
		var targetDiv = null;
		if(this.pDevice==null) {
			targetDiv = this.bigMapId;
		}else {
			targetDiv = this.pDevice.getElInBig().id;
		}
		// 以路由器的英文名称+ip地址+bigMapId作为Element的id	
		this.elInBig = this.showInTarget(targetDiv,this.name + this.ipv4 + this.bigMapId);
		this.elInBig.first().on("mouseover", this.onMouseOverInBigMap, this);	//第一个元素是img
		this.elInBig.first().on("mouseout", this.onMouseOutInBigMap, this);
	},
	showChildrenInMap: function(viewId,viewName) {
		//一般来说topo信息很少变化，所以这里有的话就不发ajax请求了
		if(this.fetchChildrenDone) {
			//画子路由器
			for (i = 0; i < this.children.length; i++) {
			    this.children[i].showInBigMap();
			}
			//画子链路
			if(this.fetchChildrenLinkDone) {
				this.refreshChildrenLink();
			} else {
				this.showChildrenLinkInMap(viewId,viewName);
			}
			return;
		}
		//fetch and show
		var thisDevice = this;
		Ext.Ajax.request({
			url : 'json/ViewShowAction_getChildrenDevices.do?viewId='+viewId+'&viewName='+viewName,
			disableCaching : true,
			method : 'GET',
			success : function(result, request) {
				// 获取下级路由器列表并显示
				thisDevice.children = [];
				//以下两句是获得该视图的背景图片url,同时对页面背景进行设置，与该函数的其它功能没有交叉，写在这里不够优雅
				var background=Ext.decode(result.responseText).background;
				document.body.style.background = "url("+background+")";
	     		document.body.style.backgroundRepeat = "no-repeat";
				document.body.style.backgroundPosition = "0 0";
				var deviceList = Ext.decode(result.responseText).deviceList;
				for (i = 0; i < deviceList.length; i++) {
				    //通过服务器端返回的每个device类的信息，创建js里面的Device类，并对其进行初始化
				    //对Device初始化时加上加上其父级节点信息在此程序中没有用，但如果以后考虑设备的层级显示，则可以利用这一字段
					thisDevice.children[i] = new cernet2.Device(deviceList[i],thisDevice);
					thisDevice.children[i].showInBigMap();
					if(thisDevice.children[i].deviceType=="server"){
					    //如果设备类型是server,那么它一定有一到多个服务，所以需要获得其服务，并在页面上显示
					    thisDevice.children[i].showServiceInMap(viewId,viewName);
					}
				}
				thisDevice.fetchChildrenDone = true;
				//设备画好之后可以画链路了
				thisDevice.showChildrenLinkInMap(viewId,viewName);
			},
			failure : function(result, request) {
				//Ext.log('获取路由器时有错误发生，错误内容为：'+ result.responseText);
			}
		});
	},
	showServiceInMap:function(viewId,viewName){
		if(this.fetchServiceDone) {
			//画服务
			for (i = 0; i < this.services.length; i++) {
			    this.services[i].showService1InMap();
			    this.services[i].showServiceLinkInMap();
			}
			return;
		}
		var thisServer = this;
		Ext.Ajax.request({
			url : 'json/ViewShowAction_getServices.do?viewId='+viewId+'&viewName='+viewName,
			disableCaching : true,
			params : {
				serverId : thisServer.id
			},
			method : 'GET',
			success : function(result, request) {
				// 获取服务并显示
				thisServer.services= [];
				var serviceList = Ext.decode(result.responseText).serviceList;
				for (i = 0; i < serviceList.length; i++) {
				    //通过服务器端返回的每个service类的信息，创建js里面的Service类，并对其进行初始化
					thisServer.services[i] = new cernet2.Service(serviceList[i],thisServer);
					thisServer.services[i].showServiceInMap();
					thisServer.services[i].showServiceLinkInMap();
				}
				thisServer.fetchServiceDone = true;
			},
			failure : function(result, request) {
				//Ext.log('获取路由器时有错误发生，错误内容为：'+ result.responseText);
			}
		});	    
	},
	showChildrenLinkInMap: function(viewId,viewName) {
		//同样，一般来说topo信息很少变化，所以这里有的话就不发ajax请求了
		if(this.fetchChildrenLinkDone) {
			for (i = 0; i < this.childrenLink.length; i++) {
				this.childrenLink[i].show();
			}
			return;
		}		
		var thisDevice = this;
		// 显示所有子链路
		Ext.Ajax.request({
			url : 'json/ViewShowAction_getChildrenLinks.do?viewId='+viewId+'&viewName='+viewName,
			disableCaching : true,
			method : 'GET',
			success : function(result, request) {
				// 获取链路列表并显示
				thisDevice.childrenLink = [];
				var linkList = Ext.decode(result.responseText).linkList;
				for (i = 0; i < linkList.length; i++) {
				    //通过服务器端返回的每个link类的信息，创建js里面的Link类，并对其进行初始化
					thisDevice.childrenLink[i] = new cernet2.Link(linkList[i],this);
					thisDevice.childrenLink[i].show();
				}
				//这里计算设备有哪些链路
				var deviceHash=[];
				for(var i=0;i<thisDevice.children.length;i++) {
					deviceHash[thisDevice.children[i].id] = thisDevice.children[i];
				}
				for(var i=0;i<thisDevice.childrenLink.length;i++) {
					/*example: "12_32--11_43"*/
					var linkName = thisDevice.childrenLink[i].name;
					var tmpArr = linkName.split("--");
					var tmp = tmpArr[0];
					var index = tmp.lastIndexOf("_");
					var lDeviceId = tmp.substring(0,index);	//12
					var lDeviceIfIndex = tmp.substring(index+1);	//32
					tmp = tmpArr[1];
					index = tmp.lastIndexOf("_");
					var rDeviceId = tmp.substring(0,index);	//11
					var rDeviceIfIndex = tmp.substring(index+1);	//43
					//设置设备的关联链路
					var lDevice = deviceHash[lDeviceId];
					lDevice.links.push([lDeviceIfIndex,thisDevice.childrenLink[i]]);
					var rDevice = deviceHash[rDeviceId];
					rDevice.links.push([rDeviceIfIndex,thisDevice.childrenLink[i]]);	
					//并设置链路两端的中文名字以方便后面显示(新需求)
					thisDevice.childrenLink[i].srcChineseName = lDevice.chineseName;
					thisDevice.childrenLink[i].destChineseName = rDevice.chineseName;
				}
				thisDevice.fetchChildrenLinkDone = true;
			},
			failure : function(result, request) {
				//Ext.log('获取链路时错误发生，错误内容为：'+ result.responseText);
			}
		});
	},
	refreshChildrenLink: function() {
		for(var i = 0;i<this.childrenLink.length;i++) {
			this.childrenLink[i].clear();
			this.childrenLink[i].paint();
		}
	},
	onFocus: function() {
		if(!this.elInBig) {
			return;
		}
		cernet2.Device.prototype.currentDevice = this;	//放在最前面
		//放大当前设备
		var imgEl = this.elInBig.first();
		if(this.status!="red") {
		    //如果该设备已经出故障，则一直显示红色，直到故障解决为止
			if(this.manuIcon!=undefined && this.manuIcon!="" && this.modelIcon!=undefined && this.modelIcon!=""){
				imgEl.dom.src = "images/purple_"+this.manuIcon+"_"+this.modelIcon+".gif";
			}else if(this.manuIcon!=undefined && this.manuIcon!=""){
				imgEl.dom.src = "images/purple_"+this.manuIcon+".gif";
			}else{
				imgEl.dom.src = "images/purple_"+this.deviceType+".gif";
			}
		}
		
		imgEl.setWidth(this.imgWidth);
		imgEl.setHeight(this.imgHeight);
		//显示路由器的信息
		//if(this.deviceType=='router'||this.deviceType=='switch' || this.deviceType=='custom' || this.deviceType=='server'){
		    if(cernet2.Device.prototype.windowFlag=='false'){
		        //如果用户将信息面板关闭后，轮询到其他设备，再想将它显示出来，首先要将删除的div层创建出来
		        if(document.getElementById("deviceInfoWin")==null){
		            var o = document.body;
		            var div=document.createElement("div");
		            div.id="deviceInfoWin";
		            o.appendChild(div);			            
		        }
		        cernet2.DeviceInfoWindow.getInstance().show();
		        cernet2.Device.prototype.windowFlag='true';
		        cernet2.Device.prototype.windowFlag2='false';
		    }
		    this.showDeviceInfo();
		/*}else{
		    //如果不是路由器和交换机，则不显示信息面板
		    if(cernet2.Device.prototype.windowFlag2=='false'){
		        cernet2.Device.prototype.windowFlag2='true';
		        cernet2.Device.prototype.windowFlag='false';
		        if(cernet2.Device.prototype.destroyInfoWinFlag=='false'){
		            this.hideDeviceInfo();
		        }
		    }
		}*/
	},
	unFocus: function() {
		var cr = cernet2.Device.prototype.currentDevice;
		if(!cr) {
			return;
		}
		//复原前一个路由器的图片和大小
		var imgEl = cr.elInBig.first();
		if(cr.status!="red") {
		    imgEl.dom.src = cr.picture;
		}
		imgEl.setWidth(cr.imgWidth);
		imgEl.setHeight(cr.imgHeight);
	},
	onChosen: function() {
		this.unFocus();
		this.onFocus();
	},
	//更新服务的状态信息，如果服务出现故障则图标会变红
	updateServiceStatus:function(){
	    var thisDevice=this;
	    if(thisDevice.deviceType=='server'){
	        for(var i=0;i<thisDevice.services.length;i++){        	
	            thisDevice.services[i].updateStatus();
	        }
	    }
	},
	//更新设备的状态信息，如果设备出现故障则图标会变红
	updateStatus : function() {
		//TODO 目前只处理本域的，跨域ajax请求暂不处理
		var thisDevice = this;
		Ext.Ajax.request({
			url : 'json/InfoAction_fetchDeviceInfo.do',
			disableCaching : true,
			params : {
				deviceId : thisDevice.id
			},
			method : 'GET',
			success : function(result, request) {
				// 获取下级路由器列表并显示
				var deviceInfo = Ext.decode(result.responseText).deviceInfo;
				if(!deviceInfo) {
					return;
				}
				if(deviceInfo.status=="red") {
					thisDevice.status = "red";
					//更新图片
					var imgEl = thisDevice.elInBig.first();
					
					if(thisDevice.manuIcon!=undefined && thisDevice.manuIcon!="" && thisDevice.modelIcon!=undefined && thisDevice.modelIcon!=""){
						imgEl.dom.src = "images/"+thisDevice.status+"_"+thisDevice.manuIcon+"_"+thisDevice.modelIcon+".gif";
					}else if(thisDevice.manuIcon!=undefined && thisDevice.manuIcon!=""){
						imgEl.dom.src = "images/"+thisDevice.status+"_"+thisDevice.manuIcon+".gif";
					}else{
						imgEl.dom.src = "images/"+thisDevice.status+"_"+thisDevice.deviceType+".gif";
					}
					
				}else{
				    var imgEl = thisDevice.elInBig.first();
                    if(cernet2.Device.prototype.currentDevice==thisDevice){
                        //如果恰好轮询到此设备，则将图片变成紫色
				        if(thisDevice.manuIcon!=undefined && thisDevice.manuIcon!="" && thisDevice.modelIcon!=undefined && thisDevice.modelIcon!=""){
							imgEl.dom.src = "images/purple_"+thisDevice.manuIcon+"_"+thisDevice.modelIcon+".gif";
						}else if(thisDevice.manuIcon!=undefined && thisDevice.manuIcon!=""){
							imgEl.dom.src = "images/purple_"+thisDevice.manuIcon+".gif";
						}else{
							imgEl.dom.src = "images/purple_"+thisDevice.deviceType+".gif";
						}
				    }else{
				        imgEl.dom.src = thisDevice.picture;	
				    }
					thisDevice.status = deviceInfo.status;		
				}
			},
			failure : function(result, request) {
				//Ext.log('获取路由器状态时有错误发生，错误内容为：'+ result.responseText);
			}
		});
	},
	getElInBig : function() {
		return this.elInBig;
	}
};
//服务构造函数
cernet2.Service=function(cfg,pDevice){
    this.id=0;
    this.name='';
    this.port='0';
    this.rX=0;
    this.rY=0;
    this.picture='';
    this.imgWidth="";
    this.imgHeight="";
    Ext.apply(this,cfg);
    this.pDevice=pDevice;
    this.elInBig=null;
    
};
cernet2.Service.prototype={
    status:'green',
    serviceMapId:'serviceMapId',
    template: new Ext.Template(
		'<div id="{id}" class="{cls}">',
		'<img src="{pic}" width="{width}px" height="{height}px"/>',
		'<br/>',
		'<p style="white-space:nowrap;font-size:{fontSize}px;font-weight: {fontWeight};">{name}<p/>',
		'</div>'),
    showInTarget:function(target,id,xy){
		var fontSize = 10;
		var fontWeight = "bolder";
		var targetEl = Ext.get(target);
		var serviceNode = this.template.append(targetEl, {
			id : id,	// 以服务名称+ip地址作为Element的id		
			name : this.name,
			pic : this.picture,
			width : this.imgWidth,
			height : this.imgHeight,
			fontSize: fontSize,
			fontWeight: fontWeight
			});
		var xy = [this.rX,this.rY];
		var serviceEl = Ext.get(serviceNode);
		serviceEl.setStyle("position", "absolute");
		serviceEl.setLeftTop(xy[0],xy[1]);
		var serviceImgEl = Ext.get(serviceNode.childNodes[0]);
		return serviceEl;    
    },
    showServiceInMap:function(){
		var targetDiv = null;
		targetDiv = this.pDevice.pDevice.getElInBig();
		this.elInBig = this.showInTarget(targetDiv,this.name  +this.pDevice.ipv4+ this.serviceMapId);
    },
    linkTemplate: new Ext.Template(
				'<div id={id}>',
					'<p style="display:none;">{name}<p/>', 
				'</div>'),
    showServiceLinkInMap:function(){
        var targetEl = Ext.get('linkCanvas');
		var linkNode = this.linkTemplate.append(targetEl, {
						id : 'link'+this.name  +this.pDevice.ipv4+ this.serviceMapId,
						name : 'link'+this.name  +this.pDevice.ipv4+ this.serviceMapId
					});
		var linkEl = Ext.get(linkNode);
		linkEl.setStyle("position", "absolute");
		var fromEl=Ext.get(this.pDevice.name + this.pDevice.ipv4 + cernet2.Device.prototype.bigMapId);
		var cx1=(Ext.get(fromEl.dom.childNodes[0])).getWidth()/2;
		var point1=[fromEl.getX()+cx1,fromEl.getY()+cx1];
        var toEl = Ext.get(this.name  +this.pDevice.ipv4+ this.serviceMapId);
		var cx=(Ext.get(toEl.dom.childNodes[0])).getWidth()/2;
		var point2=[toEl.getX()+cx,toEl.getY()+cx];
		var drawer=new jsGraphics('link'+this.name  +this.pDevice.ipv4+ this.serviceMapId);
		drawer.setStroke(3);
		drawer.setColor("#00B266");
		drawer.drawLine(point1[0],point1[1],point2[0],point2[1])
		drawer.paint();
    },
    updateStatus : function() {
		//TODO 目前只处理本域的，跨域ajax请求暂不处理
		var thisService = this;
		Ext.Ajax.request({
			url : 'json/InfoAction_fetchServiceInfo.do',
			disableCaching : true,
			params : {
				serviceId : thisService.id
			},
			method : 'GET',
			success : function(result, request) {
				var serviceInfo = Ext.decode(result.responseText).serviceInfo;
				if(!serviceInfo) {
					return;
				}
				if(serviceInfo.status=="red") {
					thisService.status = "red";
					//更新图片
					var imgEl = thisService.elInBig.first();
					imgEl.dom.src = "images/"+thisService.status+"_"+thisService.name+".gif";
				}else{
				    var imgEl = thisService.elInBig.first();
				    imgEl.dom.src = thisService.picture;	
				    thisService.status = serviceInfo.status;
				}
			},
			failure : function(result, request) {
				//Ext.log('获取路由器状态时有错误发生，错误内容为：'+ result.responseText);
			}
		});
   }
};
//链路构造函数
cernet2.Link = function(cfg, parentDevice) {
	this.pDevice = parentDevice;
	this.targetDiv = 'linkCanvas';
	this.drawer = null;
	Ext.apply(this, cfg);
};
//链路的原型
cernet2.Link.prototype = {
	id : '',
	name : '',
	srcDescription:'',
	destDescription:'',
	srcStatus:'',
	destStatus:'',
	srcName : '',
	destName : '',
	srcChineseName: '',
	destChineseName: '',
	srcIP : '',
	destIP : '',
	srcInfIP : '',
	destInfIP : '',
	srcInfId : 0,
	destInfId : 0,
	srcIPv6 : '',
	destIPv6 : '',
	srcInfIPv6 : '',
	destInfIPv6 : '',
	srcIfIndex: '',
	destIfIndex: '',
	srcSpeed : '',
	destSpeed : '',
	srcFlow: '',
	destFlow: '',
	srcFlowStr: '',
	destFlowStr: '',
	point1: null,	//[x1,y1]
	point2: null,	//[x2,y2]
	linkNum: '', //两个设备之间链路的数目
	isSrcFlow:'0',//源端口是否进行流量监控
	isDestFlow:'0',//目的端口是否进行流量监控
	el: null,	//对应的Ext.Element
	//利用率对应的颜色
	//colorLT5: "#DFC9A7",
	colorLT5: "#8080FF",
	colorLT10: "#33FF00",
	colorLT15: "#00B266",
	colorLT30: "#009999",
	colorLT50: "#FFB200",
	colorLT70: "#FF8000",
	colorLT100:"#FF0000",
	PopUpWindow: new Ext.Window({
		title: "比特流量图",
		pageX: 420,
		pageY: 250,
		width: 230,
		html: "",
		closeAction: "hide"
	}),
	template: new Ext.Template(
				'<div id={id} class="link_class">',
		//			'<p style="">{name}<p/>', 
				'</div>'),
	onMouseOver : function() {
		cernet2.Link.prototype.currentLink = this;
		Tip(this.srcChineseName+"到"+this.destChineseName+ " : " + this.srcFlowStr +"<br/>"
		+this.destChineseName+"到"+this.srcChineseName+ " : " + this.destFlowStr);
		//InfoOfCurrentLink = this;
	},
	onMouseOut : function() {
		UnTip();
	},
	onClick: function() {
			var flowChartBaseUrl="file/flow/flowscan/dat/pic/";
//		var ip;	
//		var bitChartUrl;
//		if(this.isSrcFlow!=0){
//			ip = this.srcIP;
//			alert("ip="+ip);
//			if(ip==null||ip=="") {
//				ip = this.srcIPv6.replace(/:/g,"-");
//			}			
//
//			bitChartUrl = flowChartBaseUrl + ip +"_"+this.srcIfIndex+"_bit.gif";
//			
//		}else if(this.isDestFlow!=0){
//			ip = this.destIP;
//			if(ip==null||ip=="") {
//				ip = this.destIPv6.replace(/:/g,"-");
//			}
//			
//			bitChartUrl = flowChartBaseUrl + ip +"_"+this.destIfIndex+"_bit.gif";
//		}else{
//			bitChartUrl = "images/null.gif";
//		}
		//重写链路流量图小窗口。wzw
		var srcip =this.srcIP;
		if(srcip==null||srcip=="") {
				srcip = this.srcIPv6.replace(/:/g,"=");
		}
			var destip =this.srcIP;
		if(destip==null||srcip=="") {
				destip = this.destIPv6.replace(/:/g,"=");
		}
		srcbitChartUrl = flowChartBaseUrl + srcip +"_"+this.srcIfIndex+"_bit.gif";
	  destbitChartUrl = flowChartBaseUrl + destip +"_"+this.destIfIndex+"_bit.gif";
		this.PopUpWindow.show();
	//	this.PopUpWindow.body.update("<img src="+bitChartUrl+">");
	this.PopUpWindow.body.update("<img src="+srcbitChartUrl+" onerror='this.src=&quot;images/null.gif&quot;'>");
//	this.PopUpWindow.body.update("<img src="+srcbitChartUrl+" onerror='this.src=&quot;images/null.gif&quot;'><img src="+destbitChartUrl+" onerror='this.src=&quot;images/null.gif&quot;'>");
	},
	//构造存放链路的div，并在其中显示
	show : function() {
		//el为空则首先创建div节点
		if(this.el==null) {
			this.id = "link" + this.id;
			var targetEl = Ext.get(this.targetDiv);
			var linkNode = this.template.append(targetEl, {
						id : this.id,
						name : this.name
					});
			var linkEl = Ext.get(linkNode);
			linkEl.on("mouseover", this.onMouseOver, this);
			linkEl.on("mouseout", this.onMouseOut, this);
			linkEl.setStyle("position", "absolute");
			linkEl.on("click", this.onClick,this);
			this.el = linkEl;
		}
		//然后在div内画线
		this.paint();
	},
	drawLegend: function() {
		var width = 70;
		var height = 35;
		var jg = new jsGraphics("legend");
		jg.setFont("arial","14px",Font.BOLD);
		jg.drawString("链路利用率",0,-5-height);
		jg.setColor(this.colorLT5);
		jg.fillRect(0,0,width,height);
		jg.drawString("&nbsp;<5%",width,0);
		jg.setColor(this.colorLT10);
		jg.fillRect(0,height,width,height);
		jg.drawString("&nbsp;<10%",width,height);
		jg.setColor(this.colorLT15);
		jg.fillRect(0,height*2,width,height);
		jg.drawString("&nbsp;<15%",width,2*height);
		jg.setColor(this.colorLT30);
		jg.fillRect(0,height*3,width,height);
		jg.drawString("&nbsp;<30%",width,3*height);
		jg.setColor(this.colorLT50);
		jg.fillRect(0,height*4,width,height);
		jg.drawString("&nbsp;<50%",width,4*height);
		jg.setColor(this.colorLT70);
		jg.fillRect(0,height*5,width,height);
		jg.drawString("&nbsp;<70%",width,5*height);
		jg.setColor(this.colorLT100);
		jg.fillRect(0,height*6,width,height);
		jg.drawString("&nbsp;<100%",width,6*height);
		jg.paint();
	},
	//画出链路
	flowToColor: function(flow) {
		if(flow<5) {
			return this.colorLT5;
		}else if(flow<10) {
			return this.colorLT10;
		}else if(flow<15) {
			return this.colorLT15;
		}else if(flow<30){
			return this.colorLT30;
		}else if(flow<50){
			return this.colorLT50;
		}else if(flow<70){
			return this.colorLT70;
		}else {
			return this.colorLT100;
		}
	},
	
	paint: function(lineSize) {
		this.clear();
		// 找出两端路由器，然后连线
			suffix = cernet2.Device.prototype.bigMapId;
			
	    var fromEl = Ext.get(this.srcName + this.srcIP + suffix);
		var toEl = Ext.get(this.destName + this.destIP + suffix);
		if(!this.drawer) {
			this.drawer = new jsGraphics(this.id);
		}
		if(!lineSize) {
			this.drawer.setStroke(4);
		}else {
			this.drawer.setStroke(lineSize);
		}
		//发现原来系统中的配置文件会有无效的链路，所以必须跳过
		if(!fromEl || !toEl) {
			//Ext.log("有无效链路");
			return;	//nothing to do
		}
		var cx = (Ext.get(fromEl.dom.childNodes[0])).getWidth()/2;
		if(!this.point1||!this.point2) {
			this.point1 = [fromEl.getX() + cx,fromEl.getY() + cx];
			this.point2 = [toEl.getX() + cx, toEl.getY() + cx];
		}
		
//		color1 = this.flowToColor(this.srcFlow);
	//	color2 = this.flowToColor(this.destFlow);
		color1 = this.flowToColor(this.srcFlow/(this.srcSpeed/100));
		color2 = this.flowToColor(this.destFlow/(this.destSpeed/100));
		
	//	var horizontalFirst = !isInBig; 
		
		drawPolyline(this.drawer,this.point1[0],this.point1[1],this.point2[0],this.point2[1],20,color1,color2);
		
		this.drawer.paint();
	},
	
	paintold: function(lineSize) {
		this.clear();
		// 找出两端路由器，然后连线
		var suffix = cernet2.Device.prototype.bigMapId;
		var tempSrcEl = document.getElementById(this.srcName + this.srcIP + suffix);
		var fromEl = Ext.get(tempSrcEl);
		var tempDestEl = document.getElementById(this.destName + this.destIP + suffix);
		var toEl = Ext.get(tempDestEl);
		if(!this.drawer) {
			this.drawer = new jsGraphics(this.id);
		}
		if(!lineSize) {
			this.drawer.setStroke(2);//修改线路大小
		}else {
			this.drawer.setStroke(lineSize);
		}
		if(!fromEl || !toEl) {
			return;	//nothing to do
		}
		//var cx = (Ext.get(fromEl.dom.childNodes[0])).getWidth()/2;	
		if(!this.point1||!this.point2) {
			this.point1=[fromEl.getX()+tempSrcEl.childNodes[0].width/2,fromEl.getY()+tempSrcEl.childNodes[0].height/2];
            this.point2=[toEl.getX()+tempDestEl.childNodes[0].width/2,toEl.getY()+tempDestEl.childNodes[0].height/2];
		}
		if(this.linkNum==1){			
			var middlePointX=(this.point1[0]+this.point2[0])/2;
			var middlePointY=(this.point1[1]+this.point2[1])/2;
			color1 = this.flowToColor(this.srcFlow);
			//color1=this.colorLT15;
			this.drawer.setColor(color1);
			this.drawer.drawLine(this.point1[0],this.point1[1],middlePointX,middlePointY);
			color2 = this.flowToColor(this.destFlow);
			//color2=this.colorLT10;
			this.drawer.setColor(color2);
			this.drawer.drawLine(middlePointX,middlePointY,this.point2[0],this.point2[1]);
			this.drawer.paint();
		}else{
		    var x1 = this.point1[0];
			var y1 = this.point1[1];
			var x2 = this.point2[0];
			var y2 = this.point2[1];
			var angle;
			if(this.linkNum%2==0){
				angle = (Math.PI/60)*this.linkNum;
			}else{
				angle = (Math.PI/60)*this.linkNum*(-1);
			}	
			var k1 = (y2-y1)/(x2-x1);
			var k2 = (k1+Math.tan(angle))/(1-Math.tan(angle)*k1);
			var x = ((y2-y1)*k1+(2*k1*k2+1)*x1+x2)/(2*(1+k1*k2));
			var y = ((x2-x1)*k2+(y1+y2)*k1*k2+2*y1)/(2*(1+k1*k2));
			color1 = this.flowToColor(this.srcFlow);
			//color1=this.colorLT15;
			this.drawer.setColor(color1);
			this.drawer.drawLine(this.point1[0],this.point1[1],x,y);
			color2 = this.flowToColor(this.destFlow);
			//color2=this.colorLT10;
			this.drawer.setColor(color2);
			this.drawer.drawLine(x,y,this.point2[0],this.point2[1]);
			this.drawer.paint();
		}
	},
	//清除画出的链路
	clear : function() {
		if(this.drawer!=null) {
			this.drawer.clear();
		}
	}
};
cernet2.topoApp = function() {
	// do NOT access DOM from here; elements don't exist yet

	// private variables

	// private functions

	// public space
	return {
		init : function() {
			/** 由最初从url获得参数改为从action获得参数
			var s = window.location.search.substring(1);
			var obj = Ext.urlDecode(s);
			var viewName = obj.viewName;*/
			//显示进度条
			//cernet2.ProgressBar.getInstance().show();
			//构造一个假根路由器，然后显示
			var fakeDevice = new cernet2.Device();
			fakeDevice.showInBigMap();
			fakeDevice.showChildrenInMap(viewId,viewName);
			fakeDevice.focusTimerTask.root = fakeDevice;
			Ext.TaskMgr.start(fakeDevice.focusTimerTask);
			//加一个时钟，定期地更新设备及服务的状态
			var statusTask = {
				run: function(){
					for(var i=0;i<fakeDevice.children.length;i++) {
						fakeDevice.children[i].updateStatus();
						fakeDevice.children[i].updateServiceStatus();
					}
				},
				interval: 6*1000 //6 second
			};
            Ext.TaskMgr.start(statusTask);			
			//画图例
			cernet2.Link.prototype.drawLegend();
		},
		initWithSomeDelay: function() {
			window.setTimeout(this.init,300);	//某些IE不delay一下，开始执行的时候就是有点问题...恶心...
		}
	};
}();
//虽然用的是onReady,但不知道为啥有些IE就是感觉没ready,所以这里delay一下再执行
Ext.onReady(cernet2.topoApp.initWithSomeDelay, cernet2.topoApp);//可以不要第二个参数，待试