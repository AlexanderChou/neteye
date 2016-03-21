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
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.view.util.MyXmlUtil"%>
<%@ page import="com.base.model.View"%>
<%@ page import="com.base.service.ViewService"%>
<html>
<%
	//String strOut = request.getParameter("viewName");
	//String viewName = new String(strOut.getBytes("ISO8859-1"), "utf-8");
	String viewId = request.getParameter("viewId");
	View view = new ViewService().findById(Long.valueOf(viewId));
	String viewName = view.getName();
	//View view = new ViewService().findById(Long.valueOf(viewId));
%>
<head>
<script type="text/javascript">
	window.onbeforeunload = function (evt) {
	  return "";
	}
	var allLinks = []; 
</script>
<link type="text/css" href="css/ext-all.css" rel="stylesheet" />
<script type="text/javascript" src="js/wz_jsgraphics.js"></script>
<script type="text/javascript" src="js/ext-base.js"></script>
<script type="text/javascript" src="js/ext-all.js"></script>
<script type="text/javascript" src="js/link_add.js"></script>
<script type="text/javascript" src="js/property_update.js"></script>
<script type="text/javascript" src="js/topo_second.js"></script>
<script type="text/javascript" src="js/view_join.js"></script>
<title>视图编辑</title>
<style type="text/css">
html,body {
	margin: 0px;
	padding: 0px;
	width: 100%;
	height: 100%;
	overflow: show;
}

.x-tree-node-icon {
	display: none;
}

.contextMenu {
	position: absolute;
	width: 4106px;
	height: 8182px;
	margin: 0px;
	padding: 2px;
	border: none;
	background-color: transparent;
}

ul,li {
	list-style: none;
	margin: 0px;
	padding: 0px;
}

.item {
	list-style: none;
	margin: 0px;
	padding: 0px;
	height: 25px;
	line-height: 25px;
	display: block;
	padding: 0px;
	padding-left: 10px;
}

.itemOver {
	background-color: #316AC5;
	color: #fff;
	cursor: default;
}

.separator {
	height: 1px;
	border-top: 1px solid #cccccc;
	border-bottom: 1px solid #cccccc;
	margin: 0px;
	padding: 0px;
}

#bmenu li {
	margin-top:0px;
	padding:3px 0 0px 5px; 
	border-bottom: 1px solid #cccccc;
}

#addDevice {
	margin-top:0px;
}
</style>

<script type="text/javascript">
<!--

function contextMenu(props,events){
   this.props = props;
   this.events = events;
}

contextMenu.prototype.buildContextMenu = function(){
   var menuObj = document.getElementById(this.props.menuID);
   var targetEle = this.props.targetEle;
   var eventFunc = this.events;

   document.oncontextmenu = function(evt){
	 	 try{
			    clearEventBubble(evt);
			    var cobj = ele(evt);
			    if (cobj.className == targetEle) {
			    	if(currentLine != null){
						currentLine.clear();
						currentLine.paint("2","blue");
						currentLine = null;
					}
					
					if (currentObj != null && currentObj.name != null) {
			  			currentObj.setBgColor("transparent");
			  			currentObj = null;
			  		}
						
				  	var _items = menuObj.getElementsByTagName("li");
				  	for(var i=0;i<_items.length;i++){
				  	    _items[i].style.display = "block";
				  		if(_items[i].className.indexOf("body_menu") != -1){
				  			 _items[i].className =  "body_menu item";
					  		 _items[i].onmouseover = function(){this.className ="body_menu item itemOver";};
					  		 _items[i].onmouseout = function(){this.className = "body_menu item";}	;
					  		 _items[i].onclick = function(){hide();func(this.id,cobj);};
				  	  	} else {
				  	  		_items[i].style.display = "none";
				  	  	}
			  		}
				  	var _bodyWidth = document.body.offsetWidth ||document.body.clientWidth;
				  	var _bodyHeight = document.body.offsetHeight || document.body.clientHeight;
				  	var _mWidth = menuObj.style.width;
				  	var _mHeight = menuObj.offsetHeight;
				  	
					menuObj.style.left =((parseInt(getX(evt))+parseInt(_mWidth)) > parseInt(_bodyWidth)?(parseInt(getX(evt))-parseInt(_mWidth)):getX(evt))+"px";
					menuObj.style.top =((parseInt(getY(evt))+parseInt(_mHeight)) > parseInt(_bodyHeight)?(parseInt(getY(evt))-parseInt(_mHeight)):getY(evt))+"px";
				  	menuObj.style.display = "block";
	
			  } else if (cobj.parentNode.parentNode.parentNode.className == "device_class" || cobj.parentNode.className == "device_class"){
			  		if (currentObj != null && currentObj.name != null) {
			  			currentObj.setBgColor("transparent");
			  			currentObj = null;
			  		}
			  		
		  			if(currentLine != null){
						currentLine.clear();
						currentLine.paint("2","blue");
						currentLine = null;
					}	
			  		
			  		var elementName = cobj.parentNode.parentNode.parentNode.elementName || cobj.parentNode.elementName;
			  		
			  		for (var index = 0; index < dd.elements.length; index++) {
			  			var e = dd.elements[index];
			  			if (e.name == elementName) {
			  				currentObj = e;
		            		currentObj.setBgColor("red");
		            		break;
			  			}
			  		}
				  	var _items = menuObj.getElementsByTagName("li");
				  	for(var i=0;i<_items.length;i++){
				  	    _items[i].style.display = "block";
				  		if(_items[i].className.indexOf("device_menu") != -1){
				  			 _items[i].className =  "device_menu item";
					  		 _items[i].onmouseover = function(){this.className ="device_menu item itemOver";};
					  		 _items[i].onmouseout = function(){this.className = "device_menu item";}	;
					  		 _items[i].onclick = function(){hide();func(this.id,cobj);};
				  	  	} else {
				  	  		_items[i].style.display = "none";
				  	  	}
			  		}
				  	var _bodyWidth = document.body.offsetWidth ||document.body.clientWidth;
				  	var _bodyHeight = document.body.offsetHeight || document.body.clientHeight;
				  	var _mWidth = menuObj.style.width;
				  	var _mHeight = menuObj.offsetHeight;
				  	
					menuObj.style.left =((parseInt(getX(evt))+parseInt(_mWidth)) > parseInt(_bodyWidth)?(parseInt(getX(evt))-parseInt(_mWidth)):getX(evt))+"px";
					menuObj.style.top =((parseInt(getY(evt))+parseInt(_mHeight)) > parseInt(_bodyHeight)?(parseInt(getY(evt))-parseInt(_mHeight)):getY(evt))+"px";
				  	menuObj.style.display = "block";
			  
			  } else if (cobj.parentNode.parentNode.className == "linkClass" || cobj.parentNode.className == "linkClass"){
			  	    
			  		if (currentObj != null && currentObj.name != null) {
			  			currentObj.setBgColor("transparent");
			  			currentObj = null;
			  		}
			   //		cobj.fireEvent("onclick");//firefox中没有fireEvent方法。
				  	var _items = menuObj.getElementsByTagName("li");
				  	for(var i=0;i<_items.length;i++){
				  	    _items[i].style.display = "block";
				  		if(_items[i].className.indexOf("link_menu") != -1){
				  			 _items[i].className =  "link_menu item";
					  		 _items[i].onmouseover = function(){this.className ="link_menu item itemOver";};
					  		 _items[i].onmouseout = function(){this.className = "link_menu item";}	;
					  		 _items[i].onclick = function(){hide();func(this.id,cobj);};
				  	  	} else {
				  	  		_items[i].style.display = "none";
				  	  	}
			  		}
				  	var _bodyWidth = document.body.offsetWidth ||document.body.clientWidth;
				  	var _bodyHeight = document.body.offsetHeight || document.body.clientHeight;
				  	var _mWidth = menuObj.style.width;
				  	var _mHeight = menuObj.offsetHeight;
				  	
					menuObj.style.left =((parseInt(getX(evt))+parseInt(_mWidth)) > parseInt(_bodyWidth)?(parseInt(getX(evt))-parseInt(_mWidth)):getX(evt))+"px";
					menuObj.style.top =((parseInt(getY(evt))+parseInt(_mHeight)) > parseInt(_bodyHeight)?(parseInt(getY(evt))-parseInt(_mHeight)):getY(evt))+"px";
				  	menuObj.style.display = "block";
			  
			  } else{
			    hide();	
			  }
			  
		  }catch(e){
		  }finally{
		  	clearEventBubble(evt);
		  	return false;	
		  }
	  
 }
 
 document.onclick = function(){hide();}
  
  func = function(fid,srcEle){
  		eventFunc.bindings[fid](srcEle);
  }
  
  hide = function(){
   	 try{
  		 if(menuObj){
  		 		menuObj.style.display = "none";
  		 	}
  	}catch(e){}
  }
  ele = function(evt){
      evt = evt||window.event;
		  return (evt.srcElement || evt.target);
   }

}


/*==============================================================*/
 	function getX(evt){
		 	evt = evt || window.event;
		   /** 
		   使右键菜单，在有滚动条的情况下，也能正常在点击出弹出。
		   */
			var scrollPosX; 
			if (typeof window.pageYOffset != 'undefined') { 
			   scrollPosX = window.pageXOffset; 
			} 
			else if (typeof document.compatMode != 'undefined' && document.compatMode != 'BackCompat') { 
			   scrollPosX = document.documentElement.scrollLeft; 
			} 
			else if (typeof document.body != 'undefined') { 
			   scrollPosX = document.body.scrollLeft; 
			} 

		 	return (evt.x || evt.clientX || evt.pageX)+scrollPosX;
	}
	
			function getY(evt){
			evt = evt || window.event;
			/**
		   		使右键菜单，在有滚动条的情况下，也能正常在点击出弹出。
		   */
			var scrollPosY; 
			if (typeof window.pageYOffset != 'undefined') { 
			   scrollPosY = window.pageYOffset; 
			} 
			else if (typeof document.compatMode != 'undefined' && document.compatMode != 'BackCompat') { 
			   scrollPosY = document.documentElement.scrollTop; 
			} 
			else if (typeof document.body != 'undefined') { 
			   scrollPosY = document.body.scrollTop; 
			} 

		 	return (evt.y || evt.clientY || evt.pageY)+scrollPosY;
	}
	
	function clearEventBubble(evt){
	   evt = evt || window.event;
	   if(evt.stopPropagation){
	   	 evt.stopPropagation();
	   }else{
	     evt.cancelBubble = true; 
	   }	 
	   if(evt.preventDefault){
	    	 evt.preventDefault();
	   }else{ 
	       evt.returnValue = false;
	   }

	} 
//-->
</script>
<script type="text/javascript">
	// 使用event对象的keyCode属性判断输入的键值
	function show(evt){				
		evt = evt || window.event;                
		if (evt.keyCode == 16) { // keycode 16 = Shift_L
			isdrawing = true;
		}
	}
	
	function keyup(evt){
		evt = evt || window.event;                
		if (evt.keyCode == 16) {
			isdrawing = false;
			
			if (lstartObj != null) {
				lstartObj.setBgColor("pink");
				lstartObj = null;
			}
			if (lendObj != null) {
				lendObj.setBgColor("pink");
				lendObj = null;
				
			}
		}
	}	
   document.onkeydown = show;
   document.onkeyup = keyup;

   //将链路单独定义为一个类
   Link = function(cfg) {
		this.targetDiv = 'linkCanvas';
		this.drawer = null;
		Ext.apply(this, cfg);
	};
   Link.prototype = {
		id : '',
		name : '',
		srcId : '',
		destId : '',
		srcName : '',
		destName : '',	
		className:'',
		srcIfIndex: '',
		destIfIndex: '',
		point1: null,	//[x1,y1]
		point2: null,	//[x2,y2]
		el: null,	//对应的Ext.Element
		lineNum:1,//两个设备之间链路的数目		
		template: new Ext.Template(
			'<div id={id} class="linkClass">',
				'<p style="display:none;">{name}<p/>', 
			'</div>'),		
		onClick: function() {
			
			if (currentObj != null && currentObj.name != null) {
	  			currentObj.setBgColor("transparent");
	  			currentObj = null;
	  		}
				
			currentLine = this;
			currentObj=null;
			if(dd.elements[old_currentObj_index] != undefined){
			   dd.elements[old_currentObj_index].setBgColor("transparent");
			}
			if(!isSelectLinking){				
				prefixLine = currentLine;
				isSelectLinking = true;
			}else{
				if(prefixLine!=null){
					prefixLine.clear();
					prefixLine.paint("2","blue");
					prefixLine = currentLine;
				}
			}
			currentLine.paint("4","#FF0000");
		},
		//构造存放链路的div，并在其中显示
		show : function(lineSize,lineColor) {		
			//el为空则首先创建div节点
			if(this.el==null) {				
				var targetEl = Ext.get(this.targetDiv);
				var linkNode = this.template.append(targetEl, {
							id : this.id,
							name : this.name
				});
				var linkEl = Ext.get(linkNode);			
				linkEl.setStyle("position", "absolute");
				linkEl.on("click", this.onClick,this);
				this.el = linkEl;
			}
			//然后在div内画线
			this.paint(lineSize,lineColor);
		},		
		paint: function(lineSize,lineColor) {
			this.clear();
			// 找出两端设备，然后连线	
			var tempSrcEl = document.getElementById(this.srcId);			
			var fromEl = Ext.get(tempSrcEl);
			var tempDestEl = document.getElementById(this.destId);
			var toEl = Ext.get(tempDestEl);
			if(!this.drawer) {
				this.drawer = new jsGraphics(this.id);
			}
			if(!lineSize) {
				this.drawer.setStroke(2);
			}else {
				this.drawer.setStroke(lineSize);
			}
			if(!fromEl || !toEl) {
				return;	//nothing to do
			}
				
			var cx1 = (fromEl.dom.childNodes[0]).width/2;
			var cy1 = (fromEl.dom.childNodes[0]).height/2;
			var cx2 = (toEl.dom.childNodes[0]).width/2;
			var cy2 = (toEl.dom.childNodes[0]).height/2;
			if(!this.point1||!this.point2) {
				this.point1 = [fromEl.getX(),fromEl.getY()];
				this.point2 = [toEl.getX(), toEl.getY()];
			}			
			if(!lineColor){	
				this.drawer.setColor("blue");			
			}else{					
				this.drawer.setColor(lineColor);	
			}
			if(this.lineNum==1){
				this.drawer.drawLine(this.point1[0]+cx1,this.point1[1]+cy1,this.point2[0]+cx2,this.point2[1]+cy2);
				this.drawer.paint();
			}else if(this.lineNum>1){//实现两个设备之间显示多条链路
				var x1 = fromEl.getX();
				var y1 = fromEl.getY();
				var x2 = toEl.getX();
				var y2 = toEl.getY();
				var angle;
				if(this.lineNum%2==0){
					angle = (Math.PI/60)*this.lineNum;
				}else{
					angle = (Math.PI/60)*this.lineNum*(-1);
				}	
				var k1 = (y2-y1)/(x2-x1);
				var k2 = (k1+Math.tan(angle))/(1-Math.tan(angle)*k1);
				var x = ((y2-y1)*k1+(2*k1*k2+1)*x1+x2)/(2*(1+k1*k2));
				var y = ((x2-x1)*k2+(y1+y2)*k1*k2+2*y1)/(2*(1+k1*k2));
				this.drawer.drawLine(this.point1[0]+cx1,this.point1[1]+cy1,x+cx2,y+cy2);			
				this.drawer.drawLine(x+cx2,y+cy2,this.point2[0]+cx2,this.point2[1]+cy2);
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
</script>
</head>

<body>
<script type="text/javascript" src="js/wz_dragdrop.js"></script>
<%
	MyXmlUtil.writeIcon(out);
%>
<script type="text/javascript" src="js/wz_tooltip.js"></script>
<script type="text/javascript" src="js/tip_balloon.js"></script>
<%
	String userName = request.getParameter("userName");
	String userId = request.getParameter("userId");
	MyXmlUtil.writeImage(out, viewName, userName, userId);
%>
<div id="contextMenuID" class="contextMenu"></div>
<div id="linkCanvas"></div>
<div id="bmenu"
	style="position: absolute; z-index:6000; display: none; top: 0px; left: 0px; width: 150px; margin: 0px; padding: 2px; border: 1px solid #cccccc; background-color: #CEE2FF;">
<ul>
	<li id="addDevice" class="body_menu">添加设备
	</li>
	
	<li id="delDevice" class="device_menu" ><a href="#"
		onmouseover="Tip('怎样删除设备？<br/>首先，鼠标单击想要删除<br/> 的设备，其背景变红时，<br/>如果此设备有相关链路，<br/> 系统提示你应该先删除该<br/>设备上所有的链路，该<br/>设备上所有的链路都删<br/>除后，再次选中该设备<br/>即可删除。其次，若设备<br/>上无任何链路，选中后<br/>可直接删除。')"
		onmouseout="UnTip()">删除设备</a>
	</li>
	<li id="addLink" class="body_menu">添加链路
	</li>
		
	<li id="delLink" class="link_menu"><a href="#"
		onmouseover="Tip('怎样删除链路？<br/>首先，需要双击选中你<br/> 要删除的链路。其次，<br/>当选中的链路变红的时<br/> 候，即可删除该链路。<br/>')"
		onmouseout="UnTip()">删除链路</a>
	</li>
	<li id="propChange" class="device_menu">更改设备属性
	</li>
	<li id="joinView" class="device_menu">关联子视图
	</li>
	<li id="startTopo" class="device_menu" style="border-bottom:0px;">启动拓扑发现
	</li>
	<li id="propChange" class="link_menu" style="border-bottom:0px;">更改链路属性
	</li>
	<li id="propChange" class="body_menu">更改视图属性
	</li>
	<li class="body_menu" id="save">保&nbsp;&nbsp;&nbsp;&nbsp;存
	</li>
	<li class="body_menu" id="cancel">取&nbsp;&nbsp;&nbsp;&nbsp;消
	</li>
	<li  class="body_menu" id="return" style="border-bottom: 0px solid #cccccc;">返&nbsp;&nbsp;&nbsp;&nbsp;回
	</li>
</ul>
</div>
<%
	MyXmlUtil.writeDiv(out);
%>

<script type="text/javascript">
   <!--
	<%
		MyXmlUtil.writeIconHead(out);
 		//由java动态生成该语句SET_DHTML(CURSOR_MOVE, "leop","router","switchs","server","workstation");  // make image draggable 
	%>
   var imgSize = "32px";     
   var stations = Array();
   var currentObj;
   var currentLine;
   var isdrawing = false;
   var lstartObj;
   var lendObj;
   var old_currentObj_index = 0;
   var isSelectLinking = false;
   var prefixLine;
   var repeatLine = [];//存放两个设备之间链路数目的数目，其key值为链路:srcId+"--"+destId 值为：数目整数值

   //注册站点和直线，必须在stations集合声明之后使用
   <%MyXmlUtil.registerFromFile(out, viewName + ".xml", userName, userId);%>
	
	function registerLine3(sid, name,srcId,destId,srcIfId,destIfId){
		var idStr = srcId + "_" + srcIfId + "--" + destId + "_" + destIfId;
		var tempName = srcId+"--"+destId;
		var oppName = destId+"--"+srcId;
		var lineNum = 1;
		if(tempName in repeatLine){
			lineNum = repeatLine[tempName];	
			lineNum++;
			repeatLine[tempName] = lineNum;
		}else if(oppName in repeatLine){
			lineNum = repeatLine[oppName];	
			lineNum++;
			repeatLine[oppName] = lineNum;
		}else{
			repeatLine[tempName] = 1;
		}
		var link = new Link({
			id:idStr,
			name:name,
			srcId:srcId,
			destId:destId,
			srcIfId:srcIfId,
			destIfId:destIfId,
			linkId:sid,
			lineNum:lineNum
		});
		link.show("2","blue");
		allLinks.push([link.id,link]);
	}
	
    function registerStation2(uniqueId, stationName,deviceImg,deviceType, x, y,port,iconId,isChangeIcon,xSize,ySize,subview){
		var addElement;       
		var i;
        switch(iconId){
          <%MyXmlUtil.writeRegister(out);%>
          /**
          case "1":{
            dd.elements.router.copy(1);          	
			i = dd.elements.router.copies.length - 1;
          	addElement = dd.elements.router.copies[i];
          	if(deviceImg!='' &&  deviceImg!='images/green_router.gif'){
          		addElement.swapImage(deviceImg);
          	}
            break;
          }
          case "2":{
            dd.elements.switchs.copy(1);          	
			i = dd.elements.switchs.copies.length - 1;
          	addElement = dd.elements.switchs.copies[i];
          	if(deviceImg!='' &&  deviceImg!='images/green_switch.gif'){
          		addElement.swapImage(deviceImg);
          	}
            break;
          }
          case "3":{
            dd.elements.server.copy(1);          	
			i = dd.elements.server.copies.length - 1;
          	addElement = dd.elements.server.copies[i];
          	if(deviceImg!='' &&  deviceImg!='images/green_server.gif'){
          		addElement.swapImage(deviceImg);
          	}
          	break;
          }
          case "4":{
            dd.elements.workstation.copy(1);          	
			i = dd.elements.workstation.copies.length - 1;
          	addElement = dd.elements.workstation.copies[i];
          	if(deviceImg!='' &&  deviceImg!='images/green_workstation.gif'){
          		addElement.swapImage(deviceImg);
          	}
          	break;
          }
          default:{
            dd.elements.leop.copy(1);          	
			i = dd.elements.leop.copies.length - 1;
          	addElement = dd.elements.leop.copies[i];
          	if(deviceImg!='' &&  deviceImg!='images/green_custom.gif'){
          		addElement.swapImage(deviceImg);
          	}
          }*/          	
        }//Endof switch

		addElement.div.style.position = "absolute";
		addElement.div.className = "device_class";
		addElement.div.elementName = stationName;
		addElement.moveTo(x + "px",y + "px");
		addElement.resizeTo(xSize+"px",ySize+"px");
		addElement.name = stationName; 
		//addElement.write('stationName');
		var jggg = new jsGraphics(addElement.div);
		//jggg.setColor("#FF00CC");
		//jggg.setFont("verdana","11px",Font.BOLD);  		
		jggg.drawString('<p id="text" >'+stationName+'</p>', 5, 5);
		jggg.paint();
		
		addElement.div.id = uniqueId;

		var ii;   
		ii = stations.length;
					
		stations[ii] = addElement;
		
		stations[ii].isChangeIcon = isChangeIcon;
		stations[ii].myIconId = iconId;
		stations[ii].myType = deviceType;
		stations[ii].subview = subview;
		stations[ii].myPort = port;//应用服务的端口
    } 
    
    function clearSelectedLine(){
		if(currentLine!=null && currentLine != undefined){
			currentLine.clear();
			currentLine.paint("2","blue");
			isSelectLinking = false;
			prefixLine = null;
			currentLine = null;
		}
	}  
   
    function my_DropFunc(){	  
		currentObj = dd.obj;
        old_currentObj_index = dd.obj.index;
        currentObj.setBgColor("red"); 
        clearSelectedLine();
		for(var i = 0;i<allLinks.length;i++) {		     
			var tempValue = allLinks[i][0];				
			var valueType = typeof tempValue;
			if(valueType=="string" && tempValue!="undefined"){				
				var tempId = tempValue.split("--");
				var src = tempId[0].split("_");
				var dest = tempId[1].split("_");
				var srcIfId = src[0];
				var destIdId = dest[0];
				
				if(srcIfId==currentObj.div.id || destIdId==currentObj.div.id){	
				  var link = allLinks[i][1];
				   if(link.srcId==currentObj.div.id){
						link.point1[0] = currentObj.x;
						link.point1[1] = currentObj.y;
				   }
				   if(link.destId==currentObj.div.id){
						link.point2[0] = currentObj.x;
						link.point2[1] = currentObj.y;
				   }
					link.paint("2","blue");
				}							

			}
		}		
      }     

      function my_PickFunc(){
      	
      	  if (currentObj != null && currentObj.name != null) {
	  			currentObj.setBgColor("transparent");
	  			currentObj = null;
	  		}
      
          if(dd.elements[old_currentObj_index] != undefined){
              dd.elements[old_currentObj_index].setBgColor("transparent");
          }
          currentObj = dd.obj;
          old_currentObj_index = dd.obj.index;
          currentObj.setBgColor("red");
          toInitialDrag = true;
          clearSelectedLine();
       
		 for(var i = 0;i<allLinks.length;i++) {		     
			var tempValue = allLinks[i][0];				
			var valueType = typeof tempValue;
			if(valueType=="string" && tempValue!="undefined"){				
				var tempId = tempValue.split("--");
				var src = tempId[0].split("_");
				var dest = tempId[1].split("_");
				var srcIfId = src[0];
				var destIdId = dest[0];
				if(srcIfId==currentObj.div.id || destIdId==currentObj.div.id){					
					allLinks[i][1].clear();
				}							
			}
		  }
		  if (isdrawing) {
              if (lstartObj == null) {
                  lstartObj = currentObj;
                  lstartObj.setBgColor("yellow");
              }
              else 
                  if (lendObj == null) {
                      lendObj = currentObj;
                      lendObj.setBgColor("yellow");
                      var srcid = lstartObj.div.id;
                      var destid = lendObj.div.id;
                      var srcName = lstartObj.name;
                      var destName = lendObj.name;
                      addLink(srcid.replace("s",""),destid.replace("s",""),srcName,destName, allLinks);
                      lstartObj.setBgColor("transparent");
                      lendObj.setBgColor("transparent");
                      lstartObj = null;
                      lendObj = null;
                  }
          }
      }

	  function deleteStation(currentObj){			
		var stationLinecount = 0;
		var stationLinemsg = "";

		for(var i = 0;i<allLinks.length;i++) {		     
			var tempValue = allLinks[i][0];				
			var valueType = typeof tempValue;
			if(valueType=="string" && tempValue!="undefined"){				
				var tempId = tempValue.split("--");
				var src = tempId[0].split("_");
				var dest = tempId[1].split("_");
				var srcIfId = src[0];
				var destIdId = dest[0];
				if(srcIfId==currentObj.div.id || destIdId==currentObj.div.id){					
					stationLinecount++;
				}
			}
		}
		if(stationLinecount > 0){
			alert("有 "+ stationLinecount + "条链路" + stationLinemsg + "请先删除这些链路");
		 }else if(stationLinecount == 0){
			for(var j = 0; j < stations.length; j++){
				if(currentObj == stations[j]){
					 var temp = stations.splice(j,1);             	 
					 currentObj.del();
					 break;
				}
			 }
		 }
     } 
	 //当在页面添加时，需要在此处进行更改，以获得该设备对应的图标id，默认以deviceType代替
     function createDiv(id,name,deviceType){
     var img;
      switch(deviceType){
          case "1":{
            img = "images/green_router.gif";
            break;
          }
          case "2":{
            img = "images/green_switch.gif";
            break;
          }
          case "3":{
            img = "images/green_server.gif";
          	break;
          }
          case "4":{
            img = "images/green_workstation.gif";
          	break;
          }
          default:{
            img = "images/green_custom.gif";
          }          	
        }//Endof switch
     	 
         registerStation2("s"+id, name,img,deviceType,'150','150','0',deviceType,'0','48','48','');//id,name,deviceType,x,y,port(应用服务器的端口)
     }
     //添加背景双击事件，以便能够更改背景属性
	 document.body.ondblclick = function(){
		if(currentObj != null){
			currentObj.setBgColor("transparent");
			currentObj = null;
		}
		if(currentLine != null){
			currentLine.clear();
			currentLine.paint("2","blue");
			currentLine = null;
		}		
	 }	
//-->
</script>
</body>
<script type="text/javascript">
 var cmenu = new contextMenu(
    {
	 	 menuID : "bmenu",
	 	 targetEle : "contextMenu"
	 	},
	 	{
	 	 bindings:{
	 	 	'joinView':function joinView(){
	 	 		if(currentObj){
	 	 			var deviceId = currentObj.div.id;
					joinToView(deviceId.replace("s",""),currentObj.name,'<%=viewId%>',currentObj.subview);
				}
	 	 	},
	 	 	'startTopo':function startTopo(){
	 	 	 	 var ids = new Array();
	 	 	 	 var IP = currentObj.myIP;
		         if(stations.length>0){
			        for(var i = 0;i < stations.length;i++){
			            var s = stations[i];
			            if(s.id==null){
			            	continue;
			            }else{
				            ids.push((s.div.id).replace("s",""));
			            }
			        }
			    }
               	getTopoStartPage(ids,"id",IP);
	 	 	 },
		 	 'addDevice' : function addDevice(){	
		 	 	var treeDiv = Ext.get("tree");
				if(treeDiv==null){
					var addDIV = document.createElement("div");
					addDIV.id = "tree";
					addDIV.style.width = "300px";
					addDIV.style.height = "450px";
					document.body.appendChild(addDIV);
				}
				var chosedId = new Array();
				for(i = 0; i < stations.length; i++){
					var s = stations[i];
					chosedId.push((s.div.id).replace("s",""));
				}
	
				var Tree = Ext.tree;
				var tree = new Tree.TreePanel({
					el:'tree',
					animate:true, 
					autoScroll:true,
					loader: new Tree.TreeLoader({dataUrl:'tochose.do?chosedId='+chosedId.toString()}),
					enableDD:true,
					containerScroll: true
				});
    
				new Tree.TreeSorter(tree, {folderSort:true});
				var root = new Tree.AsyncTreeNode({
					text: '设备列表', 
					draggable:false, // disable root node dragging
					checked:false,
					id:'source'
				});
				tree.setRootNode(root);
				tree.render(); // render the tree
				root.expand(true, true);
				
				tree.on('checkchange', function(node, checked) {        
					mytoggleChecked(node);
				}, tree);
    
				function mytoggleChecked(node){
					if(node.hasChildNodes){
						node.eachChild(function(child){
							child.getUI().toggleCheck(node.attributes.checked);
							child.attributes.checked = node.attributes.checked;
							child.on("checkchange",function(sub){
								mytoggleChecked(sub);
							});
							mytoggleChecked(child);
						});
					}
				};
	
				var win = new Ext.Window({
					title: '添加设备',
					contentEl:"tree",
					width: 330,
					autoScroll:true,
					resizable:false,
					plain: true,
					bodyStyle:'padding:5px;color:black;',
					buttonAlign:'center',
					buttons: [{
						text: '添加',
						handler  : function(){
							var checkId = new Array();
							var checkName = new Array();
							var b = tree.getChecked();
							var c =new Array();
							for(var i=0;i<b.length;i++){
								if(b[i].leaf){
									checkId.push(b[i].id);
									checkName.push(b[i].text);
								}
							}
				            if(checkId.length>0){
				            	
				            	for(var i=0;i<checkId.length;i++){
				            	  var tempc = checkId[i].split("_");
				            	  c.push(tempc[0]);
				            		
				            	}
				            if(c.length>0){	
				            	var d =c[0];
				            	for(var i=1;i<c.length;i++){
				            		d =d+","+c[i];
				            		
				            	}
				            	Ext.Ajax.request({
				            		
								url : "json/getcheckidlink.do",
								params : {
									checkedId : d
								},
								success : function(result, request) {
									var data = Ext.decode(result.responseText);
									var linkgetadd =data.linklist;
									for(i=0;i<linkgetadd.length;i++)
									{
										
									var tempglinkid = linkgetadd[i][0];	
									var tempglinkname = linkgetadd[i][1];	
									var tempglinksrcid = linkgetadd[i][2];	
									var tempglinkdestid = linkgetadd[i][3];	
									var tempglinksrcCheckId = linkgetadd[i][4];	
									var tempglinkdestCheckId = linkgetadd[i][5];
									 registerLine3("s"+tempglinkid, tempglinkname,"s"+tempglinksrcid,"s"+tempglinkdestid,tempglinksrcCheckId,tempglinkdestCheckId);	
										
									}
						            
						            
									
									
								}
				            		
				            		
				            		
				            	})
				            }
				           }
							//??°é????????è????1 = checkId - chosedId
							var temp =  ","+chosedId+",";
							for(var i=0;i<checkId.length;i++){
								//将checkId以下划线隔开
								var words = checkId[i].split("_");
								if(temp.indexOf(","+words[0]+",")==-1){
									createDiv(words[0],checkName[i],words[1]);
									
								}
							}
							win.close();
					   }
					},{
						text: '取消',
						handler  : function(){
							win.close();
						}
					}]
				});
				win.show();
							},
			'delDevice' : function doDeleteDevice(){
				if(currentObj!=null){
					deleteStation(currentObj);
				}
			},
			'addLink' : function toAddLink(){
				Ext.MessageBox.alert('AddLink', '怎样添加链路？首先，按住Shift键，鼠标单击想要添加链路所需的第一个设备，当设备背景变黄时 ，再用鼠标点击要添加链路所需的第二个设备，这时两个设备之间的链路就自动添加了。');
			},							
			'delLink' : function doDeleteLine(){
				currentLine.clear();				
				//需要将该链路从allLinks中删除 
				for(var j = 0; j < allLinks.length; j++){
					if(currentLine == allLinks[j][1]){
					allLinks.splice(j,1); 
					break;
					}
				}
				currentLine = null;
				prefixLine = null;
			},
			'propChange' : function doChange(){
				if(currentObj){
					var tempObj = document.getElementById(currentObj.div.id);
					changeDevice(currentObj,(Ext.get(tempObj).dom.childNodes[0]).width,(Ext.get(tempObj).dom.childNodes[0]).height);
				}
				else if(currentLine){
					var linkName = currentLine.name;			
					var tmpArr = linkName.split("--");
					var tmp = tmpArr[0];
					var index = tmp.lastIndexOf("_");
					var srcName = tmp.substring(0,index);	
					var srcIfIndex = tmp.substring(index+1);	
					tmp = tmpArr[1];
					index = tmp.lastIndexOf("_");
					var destName = tmp.substring(0,index);	
					var destIfIndex = tmp.substring(index+1);		    
					changeLink(currentLine.id,currentLine.srcId,currentLine.destId,srcName,destName,currentLine.srcIfId,currentLine.destIfId);
				}
				else{
					changeBackground();
				}
			},
			'save' : function saveToServer(){
				var XMLHEAD = '<?xml version="1.0" encoding="utf-8"?>\n';
				XMLHEAD += '<to:view xmlns:to="http://www.inetboss.com/view" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.inetboss.com/view /file/view/schema/ViewSchema.xsd">\n';
				if(stations.length>0){
					var count1 = 0;
					for(var i = 0;i < stations.length;i++){
						var s = stations[i];
						var tempObj = document.getElementById(s.div.id);			
						
						var tempImg = s.src;
						var img = tempImg.substring(tempImg.lastIndexOf("\/")+1,tempImg.length);
						if((stations[i].myType) == "1"){
							count1++;
							if(count1 == 1){
								XMLHEAD += '<to:routers>\n';
							}
							XMLHEAD += "<to:router>\n";
							XMLHEAD += "<to:id>" + (s.div.id).replace("s","") + "</to:id>\n";
							XMLHEAD += "<to:name>" + s.name + "</to:name>\n";
							XMLHEAD += "<to:coordinateX>"+s.x+"</to:coordinateX>\n";
							XMLHEAD += "<to:coordinateY>"+s.y+"</to:coordinateY>\n";
							XMLHEAD += "<to:picture>images/" +img + "</to:picture>\n"; 
							XMLHEAD += "<to:iconId>" +s.myIconId + "</to:iconId>\n"; 
							XMLHEAD += "<to:isChangeIcon>" +s.isChangeIcon + "</to:isChangeIcon>\n"; 
							XMLHEAD += "<to:iconWidth>" +(Ext.get(tempObj).dom.childNodes[0]).width + "</to:iconWidth>\n"; 
							XMLHEAD += "<to:iconHeight>" +(Ext.get(tempObj).dom.childNodes[0]).height + "</to:iconHeight>\n"; 
							XMLHEAD += "<to:subView>" +s.subview+ "</to:subView>\n"; 
							XMLHEAD += "</to:router>\n";
						}
					}
						if(count1 > 0){
							XMLHEAD += "</to:routers>\n";
						}
			    }
				if(stations.length>0){
					var count2 = 0;
					for(var i = 0;i < stations.length;i++){
						var s = stations[i];
						var tempObj = document.getElementById(s.div.id);
						var tempImg = s.src;
						var img = tempImg.substring(tempImg.lastIndexOf("\/")+1,tempImg.length);
						if((stations[i].myType) == "2"){
							count2++;
							if(count2 == 1){
								XMLHEAD += '<to:switches>\n';
							}
							XMLHEAD += "<to:switch>\n";
							XMLHEAD += "<to:id>" + (s.div.id).replace("s","") + "</to:id>\n";
							XMLHEAD += "<to:name>" + s.name + "</to:name>\n";
							XMLHEAD += "<to:coordinateX>"+s.x+"</to:coordinateX>\n";
							XMLHEAD += "<to:coordinateY>"+s.y+"</to:coordinateY>\n";
							XMLHEAD += "<to:picture>images/" +img + "</to:picture>\n"; 
							XMLHEAD += "<to:iconId>" +s.myIconId + "</to:iconId>\n";
							XMLHEAD += "<to:isChangeIcon>" +s.isChangeIcon + "</to:isChangeIcon>\n"; 
							XMLHEAD += "<to:iconWidth>" +(Ext.get(tempObj).dom.childNodes[0]).width + "</to:iconWidth>\n"; 
							XMLHEAD += "<to:iconHeight>" +(Ext.get(tempObj).dom.childNodes[0]).height + "</to:iconHeight>\n";
							XMLHEAD += "<to:subView>" +s.subview+ "</to:subView>\n";  
						    XMLHEAD += "</to:switch>\n";
						}
					}
					if(count2 > 0){
						XMLHEAD += "</to:switches>\n";
					}
			    }
				if(stations.length>0){
					var count3 = 0;
					for(var i = 0;i < stations.length;i++){
						var s = stations[i];
						var tempObj = document.getElementById(s.div.id);
						var tempImg = s.src;
						var img = tempImg.substring(tempImg.lastIndexOf("\/")+1,tempImg.length);
						if(s.myType == "3" && s.myPort == "0"){
							count3++;
							if(count3 == 1){
								XMLHEAD += '<to:servers>\n';
							}
							var count = 0;
							XMLHEAD += "<to:server>\n";
							XMLHEAD += "<to:id>" + (s.div.id).replace("s","") + "</to:id>\n";
							XMLHEAD += "<to:name>" + s.name + "</to:name>\n";
						    XMLHEAD += "<to:coordinateX>"+s.x+"</to:coordinateX>\n";
							XMLHEAD += "<to:coordinateY>"+s.y+"</to:coordinateY>\n";
							XMLHEAD += "<to:picture>images/" +img + "</to:picture>\n"; 
							XMLHEAD += "<to:isChangeIcon>" +s.isChangeIcon + "</to:isChangeIcon>\n"; 
							XMLHEAD += "<to:iconId>" +s.myIconId + "</to:iconId>\n";
							XMLHEAD += "<to:iconWidth>" +(Ext.get(tempObj).dom.childNodes[0]).width + "</to:iconWidth>\n"; 
							XMLHEAD += "<to:iconHeight>" +(Ext.get(tempObj).dom.childNodes[0]).height + "</to:iconHeight>\n";
							XMLHEAD += "<to:subView>" +s.subview+ "</to:subView>\n"; 
							for(var j=0;j<stations.length;j++){//遇到一个server后就把整个页面上的对象全部查找一遍，看是否有对应该server的service
								if(((stations[j].div.id.indexOf(stations[i].div.id+"_")) != -1)){
									count++;
									if(count == 1){
										XMLHEAD += '<to:services>\n';
									}							   
									var find = stations[j];						
									XMLHEAD += "<to:service>\n";
									XMLHEAD += "<to:id>" + (find.div.id).replace("s","") + "</to:id>\n";
									XMLHEAD += "<to:name>" + find.name + "</to:name>\n";
									XMLHEAD += "<to:port>" + find.myPort + "</to:port>\n";
									XMLHEAD += "<to:coordinateX>" + find.x + "</to:coordinateX>\n";
									XMLHEAD += "<to:coordinateY>" + find.y + "</to:coordinateY>\n";
									XMLHEAD += "<to:picture>images/" + img + "</to:picture>\n";
									XMLHEAD += "</to:service>\n";
								}
						    }
							if(count > 0){
								XMLHEAD += "</to:services>\n";							
							}
							
						    XMLHEAD += "</to:server>\n";
						}
					}
					if(count3 > 0){
						XMLHEAD += "</to:servers>\n";
					}
				}
				
				if(stations.length>0){
					var count4 = 0;
					for(var i = 0;i < stations.length;i++){
						var s = stations[i];
						var tempObj = document.getElementById(s.div.id);
						var tempImg = s.src;
						var img = tempImg.substring(tempImg.lastIndexOf("\/")+1,tempImg.length);
						if((stations[i].myType) == "4"){
							count4++;
							if(count4 == 1){
								XMLHEAD += '<to:workstations>\n';
							}
							XMLHEAD += "<to:workstation>\n";
							XMLHEAD += "<to:id>" + (s.div.id).replace("s","") + "</to:id>\n";
							XMLHEAD += "<to:name>" + s.name + "</to:name>\n";
							XMLHEAD += "<to:coordinateX>"+s.x+"</to:coordinateX>\n";
							XMLHEAD += "<to:coordinateY>"+s.y+"</to:coordinateY>\n";
							XMLHEAD += "<to:picture>images/" +img + "</to:picture>\n"; 
							XMLHEAD += "<to:iconId>" +s.myIconId + "</to:iconId>\n"; 
							XMLHEAD += "<to:isChangeIcon>" +s.isChangeIcon + "</to:isChangeIcon>\n";
							XMLHEAD += "<to:iconWidth>" +(Ext.get(tempObj).dom.childNodes[0]).width + "</to:iconWidth>\n"; 
							XMLHEAD += "<to:iconHeight>" +(Ext.get(tempObj).dom.childNodes[0]).height + "</to:iconHeight>\n";
							XMLHEAD += "<to:subView>" +s.subview+ "</to:subView>\n";   
							XMLHEAD += "</to:workstation>\n";
						}
					}
					if(count4 > 0){
						XMLHEAD += "</to:workstations>\n";
					}
				}
				if(stations.length>0){
					var count11 = 0;
					for(var i = 0;i < stations.length;i++){
						var s = stations[i];
						var tempObj = document.getElementById(s.div.id);
						var tempImg = s.src;
						var img = tempImg.substring(tempImg.lastIndexOf("\/")+1,tempImg.length);
						if(((stations[i].myType) != "1")&&((stations[i].myType) != "2")&&((stations[i].myType) != "3")&&((stations[i].myType) != "4")&&
													((stations[i].myType) != "HTTP")&&((stations[i].myType) != "DNS")&&((stations[i].myType) != "Email")&&((stations[i].myType) != "FTP")){
							count11++;
							if(count11 == 1){
								XMLHEAD += '<to:customs>\n';
							}
							XMLHEAD += "<to:custom>\n";
							XMLHEAD += "<to:id>" + (s.div.id).replace("s","") + "</to:id>\n";
							XMLHEAD += "<to:name>" + s.name + "</to:name>\n";
							XMLHEAD += "<to:coordinateX>"+s.x+"</to:coordinateX>\n";
							XMLHEAD += "<to:coordinateY>"+s.y+"</to:coordinateY>\n";
							XMLHEAD += "<to:picture>images/" +img + "</to:picture>\n"; 
							XMLHEAD += "<to:iconId>" +s.myIconId + "</to:iconId>\n"; 
							XMLHEAD += "<to:isChangeIcon>" +s.isChangeIcon + "</to:isChangeIcon>\n";
							XMLHEAD += "<to:iconWidth>" +(Ext.get(tempObj).dom.childNodes[0]).width + "</to:iconWidth>\n"; 
							XMLHEAD += "<to:iconHeight>" +(Ext.get(tempObj).dom.childNodes[0]).height + "</to:iconHeight>\n"; 
							XMLHEAD += "<to:subView>" +s.subview+ "</to:subView>\n";  
							XMLHEAD += "</to:custom>\n";
						}
					}
					if(count11 > 0){
						XMLHEAD += "</to:customs>\n";
					}
				}   
								    
				XMLHEAD += "<to:links>\n";
				for(var i = 0;i<allLinks.length;i++) {		     
					var link = allLinks[i][1];				
					var valueType = typeof link;			
					if(valueType=="object" && link!="undefined"){
						XMLHEAD += "<to:link>\n";
						XMLHEAD += "<to:id>" + (link.linkId).replace("s","") + "</to:id>\n";
						XMLHEAD += "<to:name>" + link.name + "</to:name>\n";
						XMLHEAD += "<to:srcId>" + (link.srcId).replace("s","") + "</to:srcId>\n";
						XMLHEAD += "<to:destId>" + (link.destId).replace("s","") + "</to:destId>\n";
						XMLHEAD += "<to:srcInterfaceId>" + link.srcIfId + "</to:srcInterfaceId>\n";
						XMLHEAD += "<to:destInterfaceId>" + link.destIfId + "</to:destInterfaceId>\n";
						XMLHEAD += "</to:link>\n";
					}
				}		
				XMLHEAD += "</to:links>\n";	
							        
				var tempImg = Ext.get("pic1").dom.style.backgroundImage;
				//var tempImg = document.body.style.backgroundImage;
				var img = tempImg.substring(tempImg.lastIndexOf("\/")+1,tempImg.length-1);
				XMLHEAD += "<to:backGround>images/" + img.replace("url(", "") + "</to:backGround>\n";
				XMLHEAD += "</to:view>";
				Ext.Ajax.request({
					url : 'json/save.do',
					disableCaching : true,
					params : {
						viewId : '<%=viewId%>',
						viewInfoXML : XMLHEAD 
					},
					method : 'post',				
					success : function(result, result) {
						window.parent.location.href = "read.do?viewId="+'<%=viewId%>';
					},
					failure : function(result, request) {
						Ext.Msg.alert("视图保存时发生错误!");
						Ext.Msg.alert("failure",result);
					}
				});
			},
			'cancel' : function toCancel(){
				window.location.reload();
			},
			'return' : function toReturn(){
				window.parent.location.href="manage.do";
			}
	 	 }
	  }
 	);
 
 cmenu.buildContextMenu();
</script>
</html>
