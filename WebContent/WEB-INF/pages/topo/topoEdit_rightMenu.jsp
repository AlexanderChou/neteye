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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript">
		var areaObject = null;
		$(function(){
			
			openView();
			
			$(".subMenu li:even").click(function(){
				$(this).children(".ocButton").trigger("click");
				modifyShowDivPosition(areaObject);
			});
					
			$(".ocButton").toggle(function(){
				var index = $(".ocButton").index(this);
				var img = $(this).children("img");
				img.attr("src", "images/maximize.gif");
				$(".baseInfo").eq(index).parent().toggle();
			}, function(){
				var index = $(".ocButton").index(this);
				var img = $(this).children("img");
				img.attr("src", "images/minimize.gif");
				$(".baseInfo").eq(index).parent().toggle();
			});
			
			$("#hideButton").click(function(){
				$("#menu_show").toggle();
				$("#menu_hide").toggle();
				parent.frame.cols = "35,*";
				$("#menu_hide").css("height", "100px");
				window.document.body.bgColor = "#999999";
				$("#showDiv").hide();
			});
			
			$("#showButton").click(function(){
				$("#menu_show").toggle();
				$("#menu_hide").toggle();
				parent.frame.cols = "200,*";
				window.document.body.bgColor = "#CCE8CF";
				$("#showDiv").show();
			});
			
			$("#allButton").toggle(function(){
				$(".baseInfo").parent().hide();
				var img = $(this).children("img");
				img.attr("src", "images/maximize.gif");
				$(".ocButton img").attr("src", "images/maximize.gif");
				$(this).text("全部打开").append(img);
				$("#showDiv").hide();
			}, function(){
				$(".baseInfo").parent().show();
				var img = $(this).children("img");
				img.attr("src", "images/minimize.gif");
				$(".ocButton img").attr("src", "images/minimize.gif");
				$(this).text("全部折叠").append(img);
				$("#showDiv").show();
			});
			
			/* 功能菜单的控制 */
			$(".description").click(function(){
				$(".description_info").slideUp();
				$(".description_info").eq($('.description').index($(this))).slideDown(500);
			});
		});
		
		
		function openView(){
			var isMore = false;
			var disNameStr = "";
			$.post('checkIsMore.do', {disName:"${param.name}"}, function(responseData){
				isMore = responseData.more;
				if (isMore) {
					$.post("disNameStrResturn.do", {disName:"${param.name}"}, function(responseData){  
						disNameStr = responseData.disNameStr;
						if (disNameStr != "") {
							var names = disNameStr.split(";");
							var html = "";
							for(var i = 0;i <names.length - 1; i ++) {
								var divHtml = "<div class='nameDiv'><span class='nameSpan' onclick='selectView(this)'><a href='#'>";
								divHtml += names[i];
								divHtml += "</a></span></div>";
								html += divHtml;
							}
							$("#showDiv1").html(html);
						}
					}, "json");
					
					$.post("listHtmlArea.do", {disName:"${param.name}"}, function(responseData){
						var htmlAreaStr = responseData.htmlAreaStr;
						$("#map").html(htmlAreaStr);			
					}, "json");
					
				} else {
					$("#showDiv1").html("暂无分区信息。");
					$("#slImg").parent().html("暂无缩略图");
					//window.parent.location.href = "topoEdit.do?name=" + viewText + "&userName=${param.userName}"; 
				}
			}, "json");
		}
		function selectView(obj) {
			var viewText = obj.childNodes[0].innerHTML;
			try{
				window.parent.frames['bodyFrame'].location.href = "topoEdit.do?name=" + obj.value + "&userName=${param.userName}"; 
			} catch(e) {
				window.parent.frames[1].location.href = "topoEdit.do?name=" + obj.value + "&userName=${param.userName}"; 
			}
			$("area").each(function(){
				if (this.value == viewText){
					areaObject = this;
					showDiv(areaObject);
				}
			});
		}
		
		function modifyShowDivPosition(obj){
			if (obj != null) {
				var arr = obj.coords.split(",");
				var x = arr[2];
				var y = arr[3];
				moveDiv(obj.offsetLeft, obj.offsetTop,arr[2]-arr[0],arr[3]-arr[1]);
			}
		}
		
		function showDiv(obj){
			var arr = obj.coords.split(",");
			var x = arr[2];
			var y = arr[3];
			moveDiv(obj.offsetLeft, obj.offsetTop,arr[2]-arr[0],arr[3]-arr[1]);
			try{
				window.parent.frames['bodyFrame'].location.href = "topoEdit.do?name=" + obj.value + "&userName=${param.userName}"; 
			} catch(e) {
				window.parent.frames[1].location.href = "topoEdit.do?name=" + obj.value + "&userName=${param.userName}"; 
			}
		}
		
		/* 修改域值 */
		function modifyValue() {
			$("#fieldValue_div").toggle();
		}
		
		/* 提交域值 */
		function completeModify() {
			var threshold = $("#fieldValue").val();
			if (threshold == "") {
				alert("域值不能为空！");
				return;
			}
			$.post("changeThreshold.do", {name:"${param.name}", threshold:threshold}, function(){
				$("#fieldValue_div").hide();
				try {
					window.parent.frames["menuFrame"].location.reload();
					window.parent.frames["bodyFrame"].location.href= "topoEdit.do?name=${param.name}&userName=${param.userName}";
				} catch(e) {
					window.parent.frames[0].location.reload();
					window.parent.frames[1].location.href= "topoEdit.do?name=${param.name}&userName=${param.userName}";
				}
			});
		}
		
		function moveDiv(x, y,width,height){
			var img = document.getElementById("slImg");
			img.style.position = "absolute";
			var showDiv = document.getElementById("showDiv");
			showDiv.style.position = "absolute";
			showDiv.style.display = "inline";
			showDiv.style.left = eval(img.offsetLeft + "+" + x);
			showDiv.style.top = eval(img.offsetTop + "+" + y + "-" + 14);
			showDiv.style.width = width;
			showDiv.style.height = height;
			img.style.position = "static";

			if (showDiv.offsetTop <  0 ) {
				showDiv.style.display = "none";
				return;
			}
		}
		
	</script>
	
	<style type="text/css">
		ul {
			list-style-type:none;
		}
	
		#leftDiv {
			position:absolute;
			margin:-15px;
			margin-left:-10px;
			width:195px;
		}

		.subMenu {
			+margin-top:-18px;
		}
		
		.ocButton {
			float:right;
			margin-top:-5px;
			+margin-top:-15px;
		}
		
		.out_ul {
			margin-left:-75px;
			+margin-left:-39px;
			margin-top:0px;
		}
		
		.baseInfo {
			width:100%;
			height:100%;
			font-size:80%;
		}
		
		#head {
			background-color:#7A97DF;
		}
		
		#menu_hide {
			background-color:#999999;
			width:29px;
			display:none;
		}
		
		.nameDiv {
			float:left;
			width:80px;
			margin-left:10px;
		}
		
		
		.menuTitle {
			background-color:#5C85EF;
			font-size:70%;
			padding-top:3px;
			padding-left:5px;
		}
		
		#head {
			font-size:70%;
		}
		
	</style>
</head>
<body>
	<div id="leftDiv">
		<div id="menu_show">
			<div id="head">
				<span id="hideButton"><img src="images/toc.gif" />隐藏</span>
				<span style="float:right;margin-top:-20px;" id="allButton">全部折叠<img src="images/minimize.gif"></span>
			</div>
			<ul class="out_ul">
				<!-- 拓扑发现基本信息 -->
				<li>
					<ul class="subMenu">
						<li class="menuTitle">
							<span id="title">拓扑发现信息</span>
							<span class="ocButton"><img src="images/minimize.gif" /></span>
						</li>
						<li>
							<div class="baseInfo">
								<table id="topoBaseInfo">
									<tr>
										<td width="60px;">发现名称:</td><td style="width:140px;word-break: break-all;">${param.name }</td>
									</tr>
									
									<tr>
										<td>开始时间:</td><td>${param.beginTime }</td>
									</tr>
									
									<tr>
										<td>结束时间:</td><td>${param.endTime }</td>
									</tr>
									
									<tr>
										<td>节点数:</td><td>${param.devicesCount }</td>
									</tr>
									
									<tr>
										<td>链路数:</td><td>${param.linksCount }</td>
									</tr>
									
									<tr>
										<td>跳数:</td><td>${param.hop }</td>
									</tr>
								
								</table>
							</div>
						</li>
					</ul>
				</li>
				
				<!-- 拓扑发现分区基本信息 -->
				<li>
					<ul class="subMenu">
						<li class="menuTitle">
							<span id="title">列表分区信息</span>
							<span class="ocButton"><img src="images/minimize.gif" /></span>
						</li>
						<li>
							<div class="baseInfo">
								<div id="subarea">
									如果显示设备数量过多，建议重新分区。系统默认分区数量（域值）是60。 <a href="javascript:modifyValue()">点击更改分区域值</a>
								</div>
								<div id="fieldValue_div" style="display:none;">
									请输入新的域值：<input type="text" id="fieldValue" /><input type="button" value="提交" onclick="completeModify()"/>
								</div>
								<hr />
								<div id="showDiv1"></div>
							</div>
						</li>
					</ul>
				</li>
				
				<!-- 拓扑视图缩略图 -->
				<li>
					<ul class="subMenu">
						<li class="menuTitle">
							<span id="title">拓扑视图缩略图</span>
							<span class="ocButton"><img src="images/minimize.gif" /></span>
						</li>
						<li>
							<div class="baseInfo">
								<img src="file/topo/topoHis/${param.name}.png" ismap="ismap" usemap="#map" id="slImg" width="190" height="152" /> 
								<map name="map" id="map">
									
								</map>
							</div>
						</li>
					</ul>
				</li>
				
				<!-- 页面功能使用说明 -->
				<li>
					<ul class='subMenu' id='gnInfo'>
						<li class="menuTitle"">
							<span id="title">页面功能(右键菜单)使用说明</span>
							<span class="ocButton"><img src="images/minimize.gif" /></span>
						</li>
						<li>
							<div class="baseInfo">
							
								<div id="functionInfo">
									<span class="description"><a href="#">删除设备</a></span> | <span class="description"><a href="#">删除链路</a></span> | <span class="description"><a href="#">添加链路</a></span>
									<div class="description_info" >
										首先，鼠标单击想要删除的路由器，其背景变红时如果此路由器有相关链路系统提示你应该先删除该路由器上所有的链路，该路由器上所有的链路都删除后，再次选中该路由器即可删除。其次，若路由器上无任何链路，选中后可直接删除。									
									</div>
									<div class="description_info" style="display:none">
										首先，需要双击选中你要删除的链路。其次，当选中的链路变红的时候，即可删除该链路。									
									</div>
									<div class="description_info" style="display:none">
										首先，按住Shift键，鼠标单击想要添加链路所需的第一个设备，当设备背景变黄时 ，再用鼠标点击要添加链路所需的第二个设备，这时两个设备之间的链路就自动添加了。									
									</div>
								</div>
							</div>
						</li>
					</ul>
				</li>
			</ul>
		</div>
		
		<div id="menu_hide">
			<span id="showButton"><img src="images/toc2.gif" /></span>
		</div>
		
		
	</div>
	
	
	<div id="showDiv" style="display:none;border:solid yellow 2px;">
		
	<div>
</body>
</html>