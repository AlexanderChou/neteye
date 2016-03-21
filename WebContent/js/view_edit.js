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
Ext.namespace("cernet2");
cernet2.Router = function(cfg){
	this.targetDiv = 'deviceCanvas';
	this.children = [];
	this.childrenLink = [];
	this.links = [];
	Ext.apply(this,cfg);
};
cernet2.Router.prototype = {
	id:0,
	name:'device',
	chineseName:'被管设备',
	ipv4:'',
	ipv6:'',
	rX:0,
	rY:0,
	status:'green',
	focusTimerTask: {
		run: function(){
			cernet2.Router.prototype.clearDeviceCavas();
			cernet2.Router.prototype.clearLinkCanvas();
			cernet2.Router.prototype.showDevices();
		},
		interval: 2000//20 second
	},
	delayedTask: new Ext.util.DelayedTask(),
	template:new Ext.Template(
		'<div id="{id}" class="{cls}">',
			'<img src="{pic}" width="25px" height="25px"/>',
			'<br/>',
			'<p style="white-space:nowrap;font-size:{fontSize}px;font-weight:{fontWeight};">{name}</p>',
		'</div>'),
	showInMap:function(){		
		this.showInTarget(this.targetDiv,this.name+this.ipv4);
	},
	clearDeviceCavas: function() {
		Ext.get("deviceCanvas").update("");
	},
	clearLinkCanvas: function() {
		Ext.get("linkCanvas").update("");
	},
	showInTarget:function(target,id,xy){
		var thisRouter = this;
		var fontSize = 20;
		var fontWeight = "bold";
		var targetEl = Ext.get(target);
		var routerNode = this.template.append(targetEl,{
			id:id,
			name:this.chineseName,
			pic:"images/"+this.status+"_router.gif",
			fontSize:fontSize,
			fontWeight:fontWeight
			});
		var x,y;
		if(!xy ||Array.isPrototypeOf(xy.prototype) || xy.length<2) {
			xy = [this.rX,this.rY];
		}
		var routerEl = Ext.get(routerNode);
		routerEl.setStyle("position","absolute");	
		routerEl.setLeftTop(xy[0],xy[1]);		
		return routerEl;		
	},
	showDevices:function(){
		var thisRouter = this;		
		Ext.Ajax.request({
			url:'json/viewAction_getDevices.do',
			disableCaching:true,
			method:'GET',
			success:function(result,request){
				thisRouter.children = [];
				var routerList = Ext.decode(result.responseText).routerList;
				for (i = 0; i < routerList.length; i++) {
					thisRouter.children[i] = new cernet2.Router(routerList[i]);
					thisRouter.children[i].showInMap();
				}
				thisRouter.showLinks();
				var topoInfo = Ext.get("topoNum").update("");
				topoInfo.dom.innerHTML+="已发现：<font color='red'>"+routerList.length+"</font>个节点，";
				if(thisRouter.childrenLink){
					topoInfo.dom.innerHTML+="<font color='red'>"+thisRouter.childrenLink.length+"</font>条链路";
				}
			},
			failure:function(result,request){
				Ext.log('获取被管设备时发生错误，错误内容为：'+result.responseText);
			}
		});
	},
	showLinks:function(){
		var thisRouter = this;
		Ext.Ajax.request({
			url:'json/viewAction_getLinks.do',
			disableCaching:true,
			method:'GET',
			success:function(result,request){
				thisRouter.childrenLink = [];
				var linkList = Ext.decode(result.responseText).linkList;
				for(i=0;i<linkList.length;i++){
					thisRouter.childrenLink[i] = new cernet2.Link(linkList[i]);
					thisRouter.childrenLink[i].show(); 
				}
			},
			failure:function(result,request){
				Ext.log('获取链路时发生错误，错误内容为：'+result.responseText);
			}
		});
	}
};
cernet2.Link = function(cfg){
	this.targetDiv = 'linkCanvas';
	this.drawer = null;
	Ext.apply(this,cfg);
};
cernet2.Link.prototype = {
	id:'',
	name:'',
	srcName:'',
	destName:'',
	srcChineseName:'',
	destChineseName:'',
	srcIP:'',
	destIP:'',
	srcInfIP : '',
	destInfIP : '',
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
	point1:null,
	point2:null,
	el:null, //Ext.Element
	//利用率对应的颜色
	colorLT5: "#8080FF",
	colorLT10: "#33FF00",
	colorLT15: "#00B266",
	colorLT30: "#009999",
	colorLT50: "#FFB200",
	colorLT70: "#FF8000",
	colorLT100:"#FF0000",
	template:new Ext.Template(
		'<div id={id}>',
			'<p style="display:none;">{name}</p>',
		'</div>'),
	show:function(){
		if(this.el==null){
			this.id = "link" + this.id;
			var targetEl = Ext.get(this.targetDiv);
			var linkNode = this.template.append(targetEl,{
				id:this.id,
				name:this.name
				});
			var linkEl = Ext.get(linkNode);
			linkEl.setStyle("position","absolute");
			this.el = linkEl;
		}
		this.paint();
	},
	paint:function(lineSize){
		this.clear();
		var suffix = cernet2.Router.prototype.targetDiv;
		var fromEl = Ext.get(this.srcName + this.srcIP);
		var toEl = Ext.get(this.destName + this.destIP);
		if(!this.drawer){
			this.drawer = new jsGraphics(this.id);
		}
		if(!lineSize){
			this.drawer.setStroke(6);
		}else{
			this.drawer.setStroke(lineSize);
		}
		if(!fromEl || !toEl){
			Ext.log("有无效链路！");
			return;
		}
		var cx = Ext.get(fromEl.dom.childNodes[0]).getWidth()/2;
		if(!this.point1 || !this.point2){
			this.point1 = [fromEl.getX() + cx,fromEl.getY() +cx];
			this.point2 = [toEl.getX() + cx,toEl.getY() + cx];
		}
		color1 = this.flowToColor(this.srcFlow);
		color2 = this.flowToColor(this.destFlow);
		this.drawer.setColor("#FF0000");
		this.drawer.drawLine(this.point1[0],this.point1[1],this.point2[0],this.point2[1]);
		this.drawer.paint();
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
	clear : function() {
		if(this.drawer!=null) {
			this.drawer.clear();
		}
	}
};

cernet2.topoApp = function(){
	return{
		init:function(){			
			var fakeRouter = new cernet2.Router();
			fakeRouter.showDevices();
			//fakeRouter.focusTimerTask.root = fakeRouter;
			Ext.TaskMgr.start(fakeRouter.focusTimerTask);
		},
		initWithSomeDelay:function(){
			window.setTimeout(this.init,300);
		}
	};
}();
Ext.onReady(cernet2.topoApp.initWithSomeDelay,cernet2.topoApp);