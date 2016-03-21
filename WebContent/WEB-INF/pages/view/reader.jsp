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
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Refresh" CONTENT="600">
    <link rel="stylesheet" type="text/css" href="css/topoInit.css" />
    <link rel="stylesheet" type="text/css" href="css/ext-all.css" />
    <link rel="stylesheet" type="text/css" href="css/cernet2.css" />
    <SCRIPT type="text/javascript">
    	var viewName = "<s:property value='%{viewName}'/>";
    	var viewId = "<s:property value='%{viewId}'/>";
    	var repeatLine = [];//存放两个设备之间链路数目的数目，其key值为链路:srcId+"--"+destId 值为：数目整数值
    	var subViewOfCurrentObj;
    	var InfoOfCurrentLink;
    	function modifyView(){
    		window.location.href = 'viewFrame.do?viewId=${viewId}&userId=${userId}&userName=${userName}'
    	}
    	
    </SCRIPT>
    <script type="text/javascript" src="js/ext-base.js">
    </script>
    <script type="text/javascript" src="js/ext-all.js">
    </script>
    <script type="text/javascript" src="js/ext-all-debug.js">
    </script>
	<script type="text/javascript" src="js/extPatch.js">
	</script>
    <script type="text/javascript" src="js/wz_jsgraphics.js">
    </script>
	<script type="text/javascript" src="js/myGraphics.js">
    </script>
	
    <script type="text/javascript" src="js/view_show.js"></script>
<%--    <script type="text/javascript" src="js/router_switch_info.js"></script>
 --%>   
    <title></title>
</head>
<body>

	<div class="bottomDiv"><input size="1000" type="button" value=" 修改视图 " onclick="modifyView()"/></div>

	<div style="text-align:left;">
	<script type="text/javascript" src="js/wz_tooltip.js"></script>

	<div id="bigMap" style="background-repeat:no-repeat;
			background-position:center;">
	</div>
	<div id="pbar" ></div>
    <div id="deviceInfo"></div>
    <div id="linkInfo"></div>
    <div id="linkCanvas"></div>
	<div id="deviceInfoWin"></div>
	<div id="routerAndSwichInfoWin"></div>
	<div id="legend"></div>
	</div>
	<div id="contextMenuID" class="contextMenu"></div>
<div id="linkCanvas"></div>
<div id="bmenu"
	style="position: absolute; z-index:6000; display: none; top: 0px; left: 0px; width: 150px; margin: 0px; padding: 2px; border: 1px solid #cccccc; background-color: #CEE2FF;">
<ul>
	<li id="joinToView" class="device_menu" style="border-bottom:0px;">设备综合信息</li>
	<li id="linkTotalInfo" class="link_menu" style="border-bottom:0px;">链路综合信息</li>
	<li class="body_menu" id="cancel">取&nbsp;&nbsp;&nbsp;&nbsp;消</li>
	<li  class="body_menu" id="return" style="border-bottom: 0px solid #cccccc;">返&nbsp;&nbsp;&nbsp;&nbsp;回</li>
</ul>
</div>
<SCRIPT type="text/javascript">
    
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
	 	 		/*     下面两个是用到的两个当前对象    有可能有用到这两个类的信息     */
	 	 		var currentObj = cernet2.Device.prototype.currentDevice != undefined ? cernet2.Device.prototype.currentDevice :null;
	 	 		var currentLine = cernet2.Link.prototype.currentLink != undefined ? cernet2.Link.prototype.currentLink : null;
	 	 		InfoOfCurrentLink = currentLine;
	 	 		InfoOfCurrentDevice = currentObj;
	 	 		
			    clearEventBubble(evt);
			    var cobj = ele(evt);
			    if (cobj.parentNode.className == "device_class"){
					
					///在操作时 可以调用 currentObj 的信息
					
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
			  
			  } else if (cobj.parentNode.parentNode.className == "link_class"){
			  	  
			  	  	///在操作时 可以调用 currentLink的信息
			  	  
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
    
    var cmenu = new contextMenu(
    {
	 	 menuID : "bmenu",
	 	 targetEle : "contextMenu"
	 	},
	 	{
	 	 bindings:{
	 	 	 'joinToView':function joinToView(){
	 			var s = Ext.urlEncode(InfoOfCurrentDevice);
	 			if(InfoOfCurrentDevice.deviceType=='router'||InfoOfCurrentDevice.deviceType=='switch'){
	 			    document.location.href = 'routerOrSwitchInfo.do?'+s;
	 			} else if(InfoOfCurrentDevice.deviceType=='server'){
	 			    document.location.href = 'serverInfo.do?'+s,'serverInfo';
	 			} else {
	 			    document.location.href = 'workstationInfo.do?'+s,'workstationInfo';
	 			}
	 	 		 
//	 	 	 	if(subViewOfCurrentObj != null && subViewOfCurrentObj !=""){
//	 	 	 		window.location.href = "read.do?viewId="+subViewOfCurrentObj;
//	 	 	 	}else{
//	 	 	 		Ext.Msg.alert('系统信息', '该设备没有下级子视图！');
//	 	 	 	}
	 	 	 },
	 	 	 'linkTotalInfo':function linkInfo(){
	 	 	 		var s = Ext.urlEncode(InfoOfCurrentLink);
	 	 	 	//	Ext.Msg.alert("S=",s);
	 	 	 	document.location.href = 'linkTotalInfo.do?'+s;
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
    
    
</SCRIPT>
	
</body>
</html>