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
<%@ page import="com.topo.dao.TopoDAO"%>
<%@ page import="com.view.util.MyXmlUtil"%>
<%@ page import="com.base.util.Constants"%>
<%@ page import="java.io.*"%>
<script type="text/javascript">
	window.onbeforeunload = function (evt) {
	  return "";
	}
</script>
<html>
<head>
<link type="text/css" href="css/ext-all.css" rel="stylesheet" />
<script type="text/javascript" src="js/wz_jsgraphics.js"></script>
<script type="text/javascript" src="js/ext-base.js"></script>
<script type="text/javascript" src="js/ext-all.js"></script>
<script type="text/javascript" src="js/topo_link_add.js"></script>
<script type="text/javascript" src="js/property_update.js"></script>
<script type="text/javascript" src="js/topo_second.js"></script>
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
	
			  } else if (cobj.parentNode.parentNode.className == "linkClass"){
			  		if (currentObj != null && currentObj.name != null) {
			  			currentObj.setBgColor("transparent");
			  			currentObj = null;
			  		}
			   		cobj.fireEvent("onclick");
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
		srcIfIndex: '',
		destIfIndex: '',
		point1: null,	//[x1,y1]
		point2: null,	//[x2,y2]
		el: null,	//对应的Ext.Element
		lineNum:1,//两个设备之间重复链路的数目		
		template: new Ext.Template(
			'<div id={id}  class="linkClass">',
				'<p style="display:none;">{name}<p/>', 
			'</div>'),		
		onClick: function() {		
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
		
			var cx = (fromEl.dom.childNodes[0]).width/3;		
			if(!this.point1||!this.point2) {
				this.point1 = [fromEl.getX() + cx,fromEl.getY() + cx];
				this.point2 = [toEl.getX() + cx, toEl.getY() + cx];
			}			
			if(!lineColor){	
				this.drawer.setColor("blue");			
			}else{					
				this.drawer.setColor(lineColor);	
			}
			if(this.lineNum==1){
				this.drawer.drawLine(this.point1[0]+12,this.point1[1]+12,this.point2[0]+12,this.point2[1]+12);
				this.drawer.paint();
			}else if(this.lineNum>1){
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
				this.drawer.drawLine(this.point1[0]+12,this.point1[1]+12,x+12,y+12);			
				this.drawer.drawLine(x+12,y+12,this.point2[0]+12,this.point2[1]+12);
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

<body >
<form id="topoForm" method="post">
	<input type="hidden" name="devices" id="devices" />
	<input type="hidden" name="initLinks" id="initLinks" />
	<input type="hidden" name="description" id="description" />
	<input type="hidden" name="homePage" id="homePage" />
	<input type="hidden" name="topoViewName" id="topoViewName" />
	<input type="hidden" name="disName" id="disName" />
</form>
<script type="text/javascript">
	var mk = null;
	mk = new Ext.LoadMask(Ext.getBody(), {
		msg: '正在加载数据，请稍候!',
		removeMask: true //完成后移除
	});
	mk.show();
</script>
<div id="showDiv"></div>

<script type="text/javascript" src="js/wz_dragdrop.js"></script>

<div style="display:none;">	
	<img name="router" src="images/green_router.gif" width="1" height="1" alt="">
	<img name="switchs" src="images/green_switch.gif" width="1" height="1" alt="">
	<img name="server" src="images/green_server.gif" width="1" height="1" alt="">
	<img name="workstation" src="images/green_workstation.gif" width="1" height="1" alt="">
	<img name="leop" src="images/green_custom.gif" width="1" height="1" alt="">
</div>  
<script type="text/javascript" src="js/wz_tooltip.js"></script>
<script type="text/javascript" src="js/tip_balloon.js"></script>
<%
	String disname = request.getParameter("name");
	MyXmlUtil.writeImage(out);
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
   SET_DHTML(CURSOR_MOVE, "leop","router","switchs","server","workstation");  // make image draggable 

   var imgSize = "32px";     
   var allLinks = []; 
   var stations = Array();
   var currentObj;
   var currentLine;
   var isdrawing = false;
   var lstartObj;
   var lendObj;
   var old_currentObj_index = 0;
   var isSelectLinking = false;
   var prefixLine;
   var repeatLine = [];

   //注册站点和直线，必须在stations集合声明之后使用
    <%  
		String path = Constants.webRealPath + "file/topo/topoHis/";
   		File xmlFile = new File(path + disname.split("\\[")[0] + ".xml");
		String fileStr = "";
   		if (!xmlFile.exists()) {
   	%>    	
			alert("该视图文件不存在");
	<%	
   		} else {
	    	File txtFile = new File(path + disname + ".txt");
		    File txtFile1 = new File(path + disname + "[1].txt");
		    File txtFile2 = new File(path + disname + "[1-1].txt");
		    boolean isFlesh = true;
		    if(txtFile.exists()){
		    	TopoDAO.printRegisterStation2(out, disname);
		    	fileStr = disname + ".txt";
		    	isFlesh = false;
		    }else if(txtFile1.exists()){
		    	TopoDAO.printRegisterStation2(out, disname+"[1]");
		    	fileStr = disname + "[1].txt";
		    }else if(txtFile2.exists()){
		    	TopoDAO.printRegisterStation2(out, disname+"[1-1]");
		    	fileStr = disname + "[1-1].txt";
		    } else {
	%>    	
			alert("该视图文件不存在");
	<%
	    	}
		    out.print("mk.hide();");
	    	if (isFlesh) {
	    		out.print("window.parent.frames['menuFrame'].location.reload();");
	    	}
   		}
	%>	
	function registerLine3(sid, name,srcId,destId,srcIfIndex,destIfIndex){
		var idStr = srcId + "_" + srcIfIndex + "--" + destId + "_" + destIfIndex;
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
			srcIfIndex:srcIfIndex,
			destIfIndex:destIfIndex,
			linkId:sid,
			lineNum:lineNum
		});
		link.show("2","blue");
		allLinks.push([link.id,link]);
	}
	function registerStation2(uniqueId,stationName,IP,community,version,deviceType,x,y){
		var addElement;    	
		var i;
        switch(deviceType){
			  case "1":{			   
				dd.elements.router.copy(1);          	
				i = dd.elements.router.copies.length - 1;
				addElement = dd.elements.router.copies[i];
				break;
			  }
			  case "2":{			  	
				dd.elements.switchs.copy(1);          	
				i = dd.elements.switchs.copies.length - 1;
				addElement = dd.elements.switchs.copies[i];
				break;
			  }
			  case "3":{
				dd.elements.server.copy(1);          	
				i = dd.elements.server.copies.length - 1;
				addElement = dd.elements.server.copies[i];
				break;
			  }
			  case "4":{
				dd.elements.workstation.copy(1);          	
				i = dd.elements.workstation.copies.length - 1;
				addElement = dd.elements.workstation.copies[i];
				break;
			  }
			  default:{
				dd.elements.leop.copy(1);          	
				i = dd.elements.leop.copies.length - 1;
				addElement = dd.elements.leop.copies[i];
			  }          	
         }

		addElement.div.style.position = "absolute";
		addElement.div.className = "device_class";
		addElement.div.elementName = stationName;
		addElement.moveTo(x + "px",y + "px");
		addElement.resizeTo(imgSize,imgSize);
		addElement.name = stationName; 
		var jggg = new jsGraphics(addElement.div);
		jggg.drawString('<p id="text" >'+stationName+'</p>', 5, 5);
		//jggg.drawString(stationName, 5, 5);
		jggg.paint();

		addElement.div.id = uniqueId;

		var ii;   
		ii = stations.length;
					
		stations[ii] = addElement;
			
		stations[ii].myIP = IP;
		stations[ii].myCommunity = community;
		stations[ii].myVersion = version;
		stations[ii].myType = deviceType;	
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
				var srcId = src[0];
				var destId = dest[0];
				if(srcId==currentObj.div.id || destId==currentObj.div.id){	
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
				var srcId = src[0];
				var destId = dest[0];
				if(srcId==currentObj.div.id || destId==currentObj.div.id){					
					allLinks[i][1].clear();
				}							
			}
		  }	
		   if (isdrawing) {
              if (lstartObj == null) {
                  lstartObj = currentObj;
                  lstartObj.setBgColor("yellow");
              } else if (lendObj == null) {
                      lendObj = currentObj;
                      lendObj.setBgColor("yellow");
                      var srcid = lstartObj.div.id;
                      var destid = lendObj.div.id;
                      var srcName = lstartObj.name;
                      var destName = lendObj.name;
                      addLink(srcid.replace("s",""),destid.replace("s",""),srcName,destName, '<%=disname%>');
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
				var srcId = src[0];
				var destId = dest[0];
				if(srcId==currentObj.div.id || destId==currentObj.div.id){					
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
<SCRIPT LANGUAGE="JavaScript">
  <!--  
  function saveToServer(viewName, viewDescription, isHomePage){
  		var devices = new Array();
  		var initLinks = new Array(); 
        if(stations.length>0){
	        for(var i = 0;i < stations.length;i++){
	            var s = stations[i];
				 var tempImg = s.src;
			    var img = tempImg.substring(tempImg.lastIndexOf("\/")+1,tempImg.length);
	            if(s.id==null){
	            	continue;
	            }else{
		            devices.push(s.name+"||"+s.myIP+"||"+s.myCommunity+"||"+s.myVersion+"||"+s.myType+"||"+s.x+"||"+s.y + "||" + "images/"+img);
	            }
	        }
	    }

		for(var i = 0;i<allLinks.length;i++) {		     
			var link = allLinks[i][1];				
			var valueType = typeof link;			
			if(valueType=="object" && link!="undefined"){
				initLinks.push(link.name);
			}
		 }

	    var progressBar = function(){ 
		    Ext.MessageBox.show({
                 title:"请稍后!",
                 msg:"正在进行保存!",
                 progress:true,
                 width:300,
                 closable:true,
                 wait:true,
                 waitConfig:{interval:300}	//0.3s进度条自动加载一定长度
	    	});
	    }();
		
		/* 参数的赋值传递 */
	    var viewNameEle = document.getElementById("topoViewName");
	    var devicesEle = document.getElementById("devices");
	    var initLinksEle = document.getElementById("initLinks");
	    var descriptionEle = document.getElementById("description");
	    var homePageEle = document.getElementById("homePage");
	    var disNameEle = document.getElementById("disName");
	    devicesEle.value = devices;
	    initLinksEle.value = initLinks;
	    descriptionEle.value = viewDescription;
	    homePageEle.value = isHomePage;
	    viewNameEle.value = viewName;
	    disNameEle.value = "<%=disname%>";

	    var topoForm = document.getElementById("topoForm");
	    topoForm.action = "resultFilter.do";  
	    topoForm.submit();
	    
	    /* 因 涉及到对设备结果集的过滤 所以对跳转做了改动 下面代码暂且不用*/
	    
	    /*Ext.Ajax.request({
			url : 'json/topoSave.do?userName=' + "${userName}",
			disableCaching : true,
			params : {
				viewName : viewName,
				devices: devices,
				initLinks:initLinks,
				description:viewDescription,
				homePage:isHomePage
			},
			method : 'post',	
			timeout:300000,			
			success : function(result, request) {
				var flag = Ext.decode(result.responseText).flag;
				var topoNameUnique = Ext.decode(result.responseText).info.topoNameUnique;
				if(topoNameUnique == "yes"){
					if(flag==0){
						window.parent.location.href = "read.do?viewName="+viewName;
					}else{
						Ext.alert('拓扑视图保存时出现错误，错误内容为：'+ result.responseText);
					}
				}else{
					Ext.Msg.alert("警告！","拓扑保存视图名称重复");	
				}
			},
			failure : function(result, request) {
				Ext.Msg.alert("failure","failure:"+result.responseText);
				window.parent.location.href = "read.do?viewName="+viewName;
			}
		});*/
  }
  //-->
</SCRIPT>

<script type="text/javascript">
 var cmenu = new contextMenu(
    {
	 	 menuID : "bmenu",
	 	 targetEle : "contextMenu"
	 	},
	 	{
	 	 bindings:{
	 	 	 'startTopo':function startTopo(){
	 	 	   var IP = currentObj.myIP;
	 	 	   var IPs = new Array();
	 	 	   /*
		        if(stations.length>0){
			        for(var i = 0;i < stations.length;i++){
			            var s = stations[i];
			            if(s.id==null){
			            	continue;
			            }else{
				            IPs.push(s.myIP);
			            }
			        }
			    }*/
			    IPs.push('<%=disname%>');
               	getTopoStartPage(IPs,"IP",IP);
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
					chosedId.push(s.div.id);
				}
				var Tree = Ext.tree;
				var tree = new Tree.TreePanel({
					el:'tree',
					animate:true, 
					autoScroll:true,
					loader: new Tree.TreeLoader({dataUrl:'topoChose.do?chosedFile=<%=fileStr%>&topoViewName=<%=disname%>'}),
					enableDD:true,
					containerScroll: true
				});
    
				new Tree.TreeSorter(tree, {folderSort:true});
				var root = new Tree.AsyncTreeNode({
					text: '设备列表', 
					draggable:false, // disable root node dragging
					checked:false,
					id:'source',
					listeners:{
						"checkchange":function(node){
							mytoggleChecked(node);
						}
					}
				});
				tree.setRootNode(root);
				tree.render(); // render the tree
				root.expand(true, true);
    
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
					plain: true,
					bodyStyle:'padding:5px;color:black;',
					buttonAlign:'center',
					buttons: [{
						text: '添加',
						handler  : function(){
							var b = tree.getChecked();
							var checkName = new Array();
							var checkValue = new Array();
							for(var i=0;i<b.length;i++){
								if(b[i].leaf){
									var checkId = b[i].id;
									checkName.push(b[i].text);
									var tmpArr = checkId.split("--");
									checkValue.push(tmpArr[0]);
									registerStation2(tmpArr[0],b[i].text,tmpArr[1],tmpArr[2],tmpArr[3],tmpArr[4],tmpArr[5],tmpArr[6]);
								}
							}
							//如何将相关的链路也同时加入
							Ext.Ajax.request({
		        				url : 'json/addTopoDevice.do',
		        				method : 'post',		
		        				params : {checkId:checkValue.toString(),chosedId:chosedId.toString(),topoName:'<%=disname%>'},
		        				success : function(result, request) {
		        		    		var linkList = Ext.decode(result.responseText).linkList;
									for (i = 0; i < linkList.length; i++) {
									    //通过服务器端返回的每个link类的信息，创建js里面的Link类，并对其进行初始化
										eval(linkList[i]);
									}
		        				}
		        			});
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
					changeDevice(currentObj);
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
			'save' : function saveViewName(){
				Ext.QuickTips.init();
				Ext.form.Field.prototype.msgTarget = 'side';
			    var MsgForm = new Ext.form.FormPanel({
					baseCls: 'x-plain',
			        labelAlign: 'left',
			    	buttonAlign: 'center',
			    	autoHeight:true,
			    	monitorValid:true,//绑定验证	    	
			    	frame: true,
			    	labelWidth: 60,
			        items: [{
			        	xtype:'fieldset',
			        	title:'视图信息',
			        	collapsible:true,
			        	width:290,
			        	height:140,
			        	defaults:{width:200,height:30},
			        	defaultType:'textfield',
			        	items:[{
				        		fieldLabel:'视图名称',
				        		id:'viewName',
				        		name:'name',
				        		value:'<%=disname%>',
				        		allowBlank: false,
				        		blankText:'视图名称不能为空!',
				        		//invalidText:'视图名称不能为空!',
				        		listeners: {
			                       blur  : function(combo) {
			                          /*
			                       	   var zwReg = /[u4E00-u9FA5]/;
						               if (!zwReg.test(Ext.get("name").getValue())) {
						               		alert("视图名字不能为中文！")
						               		return;
						               }*/
				        			}
		                  	},
				        		anchor:"95%"	//330px-labelWidth剩下的宽度的95%，留下5%作为后面提到的验证错误提示
			        		},{
				        		fieldLabel:'描述',
				        		anchor:"95%",
				        		id:'viewDescription',
				        		name:'description'
			        		},{
			                    xtype:"combo",
			                    id:'isHomePage',
			                    name: 'homePage',
			                    store:["yes","no"],
			                    fieldLabel:"级别",
			                    emptyText:'选择是否设定为首页',
			                    blankText:'请选择是否设定为首页',
			                    triggerAction:'all',
			                    hiddenName:'homePage',
			                    allowBlank : false,
			                    editable : false,
			                    forceSelection : true,
			                    listeners: {
			                        blur : function(combo) {
					        			Ext.Ajax.request({
					        				url : 'json/checkHomePage.do',
					        				method : 'post',		
					        				params : {homePageValue:Ext.get("isHomePage").getValue()},
					        				success : function(result, request) {
					        		    		var isHaveRecord = Ext.decode(result.responseText).isHaveRecord;
					        		    		if(isHaveRecord == "yes"){
					        		    			Ext.Msg.alert("警告！","<font color=red>首页已设定，继续将覆盖原首页</font>");
					        		    		}
					        				}
					        			});
					        		}
			                    },
		                    	anchor:"95%"		//330px-labelWidth剩下的宽度的95%，留下5%作为后面提到的验证错误提示
		        		}]
			        }],
			        buttons: [{
			            text: '确定',
			            formBind:true,//绑定验证，使按钮为灰色
			            handler  : function(){
			            if(MsgForm.form.isValid()){ 		//验证合法后进行保存   
				               var viewName = Ext.get("viewName").getValue();
				               /*
				               var zwReg = /[\u2e80-\u9fff]/;
				               if (zwReg.test(viewName)) {
				               		alert("视图名字不能为中文！")
				               		return;
				               }*/
				               
				               Ext.Ajax.request({
			        				url : 'json/checkName.do',
			        				method : 'post',		
			        				params : {name:viewName},
			        				success : function(result, request) {
			        		    		var isHaveRecord = Ext.decode(result.responseText).isHaveRecord;
			        		    		if(isHaveRecord == "yes"){
			        		    			Ext.Msg.alert("警告！","<font color=red>拓扑保存视图名称重复</font>");
			        		    		} else {
			        		    		   var viewDescription = Ext.get("viewDescription").getValue();
							   			   var isHomePage = Ext.get("isHomePage").getValue();
							   			   win.close();
							   			   saveToServer(viewName, viewDescription, isHomePage);
			        		    		}
			        				}
			        			});
			        		}
			            }
			        },{
			            text: '关闭',
			            handler  : function(){
			            	win.close();
			            }
			        }],
			        onSubmit: Ext.emptyFn,
			        submit: function() {}
			    });
		  		var homePageItem = Ext.getCmp('isHomePage');
		  		var name = Ext.getCmp('viewName');
			    var win = new Ext.Window({
			        title: '拓扑视图另存为',
			        width: 320,
			        height:230,
			        layout: 'fit',
			        plain: true,
			        bodyStyle:'padding:5px;color:black;',
			        buttonAlign:'center',
			        items: MsgForm
			    });
			    win.show();
			},
			'cancel' : function doCancel(){
				window.location.reload();
			},
			'return' : function doReturn(){
				window.parent.location.href="topoHisList.do";
			}
	 	 }
	  }
 	);
 
 cmenu.buildContextMenu();
 
/* 显示全局图 的函数  不过现在没用到 */
function showBigMap(){
	var imgDiv = document.createElement("div");
	imgDiv.id = "imgDiv";
	document.body.appendChild(imgDiv);
	var img = document.createElement("img");
	imgDiv.appendChild(img);
	var imgName = "${param.name}".split("[")[0];
	img.src = "/file/topo/topoHis/" + imgName + ".png";
	
	var imgWin = new Ext.Window({
		title:"全局图",
		width:600,
		height:600,
		autoScroll:true,
		maximizable:true,
		contentEl:"imgDiv"				
	});
	imgWin.show();
}
</script>
</html>
