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

var isOpen = false;
var isHide = true;
/** 主调函数 */
$(document).ready(function(){
   addFrameClick();
  		 
	$("#uxp_hdr_jewelLink").click(function(){	
		
 		if (isHide){
 			createMunu();
 			isHide = false;
 		} else {
 			$(".uxp_hdr_menu").hide();
 			isHide = true;
 		}
 	});
 	
 	$(document.body).click(function(e){
		if (e.pageX > 350) {
			$(".uxp_hdr_menu").hide();
			isHide = true;
		}
	});
});


/** 菜单数组 */
var data = new Array();
var arraw = '<SPAN class=uxp_hdr_rightArrow> <SPAN id="arrowPosition"><SPAN  class=uxp_hdr_rightArrow_ie6 style="WIDTH: 16px; POSITION: absolute; HEIGHT: 790"> <IMG  alt=""	src="images/headerBG_24_~HeaderGradientImageType~.png" onload=uxp_p(this)> </SPAN> </SPAN> </SPAN>';
//一下目录列表
data['拓扑发现'] = [{'title':'启动拓扑发现', 'url':"totalinit.do"},{'title':'子网拓扑发现', 'url':"subinit.do"},{'title':'拓扑历史查询', 'url':"topoHisList.do"}];
//当含有空的元素时为所在的 <li > 添加样式         uxp_hdr_menuSeparator
data['配置管理'] = [{'title':'视图管理', 'url':"manage.do"},{'title':'设备管理', 'url':"deviceClass.do"},{'title':'链路管理', 'url':"tolink.do"},"null", {'title':'添加视图', 'url':"toaddview.do"},{'title':'添加分类', 'url':"forwardType.do"},"null",{'title':'数据字典', 'url':"dicList.do"},{'title':'数据字典详表', 'url':"dicdetailList.do"}, {'title':'监控服务初始化', 'url':"initconfig.do"}];
data['服务管理'] = [{'title':'服务列表', 'url':"serverList.do?deviceTypeId=3"},{'title':'服务汇总', 'url':"allServer.do"}];
data['故障管理'] = [{'title':'节点状态', 'url':"nodeStatus.do?type=4"},{'title':'当前故障节点', 'url':"currFault.do"},{'title':'历史故障', 'url':"historyFault.do"},{'title':'故障统计', 'url':"FaultStatisticQuery.do"}];
data['流量监控'] = [{'title':'比特流量图', 'url':"bitWatch.do"},{'title':'分组流量图', 'url':"packetWatch.do"},{'title':'包长统计图', 'url':"lenWatch.do"},{'title':'汇总监测图', 'url':"allWatch.do"},{'title':'CPU利用率', 'url':"cpuWatch.do"},{'title':'内存利用率', 'url':"memWatch.do"},{'title':'温度', 'url':"tempWatch.do"},"null",{'title':'流量统计', 'url':"FlowStatisticQuery.do"}];
data['事件管理'] = [{'title':'事件列表', 'url':"GlobalEventStatus.do"},{'title':'事件统计', 'url':"EventStatisticQuery.do"},{'title':'邮件配置','url':"EmailInfoConfiguration.do"},{'title':'短信配置','url':"MessageInfo.do"},{'title':'事件过滤','url':"filterConfig.do"}];
data['用户管理'] = [{'title':'资源组管理', 'url':"resourceGroupInfo.do"},{'title':'部门管理', 'url':"department.do"},{'title':'角色管理 ', 'url':"userGroup.do"},{'title':' 用户管理 ', 'url':"user.do"},"null",{'title':'用户个人信息 ', 'url':"initUser.do"},{'title':'切换用户身份 ', 'url':'logout.do?type=changeUser&logId=' + logId},{'title':'用户登录信息 ', 'url':"log.do"}];
data['SAVI交换机'] = [{'title':'显示','url':"showSubnets.do"},{'title':'管理','url':"configSubent.do"},{'title':'统计','url':"showStatistic.do"},{'title':'五元组信息','url':"showFiveInfo.do"},{'title':'更新绑定信息','url':"show/refreshBingdingInfo.do"}];
data['无线网SAVI管理'] = [{'title':'H3C管理','url':"HuaSanView.do"},{'title':'五元组信息','url':"showOnlineInfoList.do"},{'title':'更新绑定信息','url':"collectionTaskExcu.do"}];
data['二维路由'] = [{'title':'路由器列表', 'url':"tdrouterlist.do"},{'title':'拓扑发现', 'url':"networktopo.do"}];
data['savi(NETCONF)'] = [{'title':'全局视图', 'url':"mySaviTotalView.do"}];
data['NetFlow监控'] = [{'title':'NetFlow采集 ','url':"dongtaiyemian.do"},{'title':'全网topN ','url':"topNStatistic.do"},{'title':'流量矩阵','url':"trafficMatrix.do"},{'title':'流量矩阵(域间)','url':"trafficMatrixInRegion.do"}];
//data['用户流量分析'] = [{'title':'TopN用户流量（1m）','url':"userTraffics.do"},{'title':'TopN用户流量（1h）','url':"historyTraffic.do"},{'title':'网络整体流量分布（1m）','url':"totalPktlen.do"},{'title':'网络整体流量分布（1h）','url':"totalHistoryPktlen.do"},{'title':'流量曲线','url':"netUnityFlowAll.do"}];
//data['计费管理'] = [{'title':'计费系统', 'url':"#"},{'title':'计费查询', 'url':"http://taurus.serv.edu.cn/dataquery/index.php"}];
data['null'] = []; //当为null时 此时给 <li> 添加样式 uxp_hdr_menuSeparator
data['自动升级'] = [];


/** 创建菜单 */
function createMunu(){
	if (isOpen) {
		$("#uxp_hdr_jewelMenu").show();
	} else {
		isOpen = true;
		var hc = "";
		for (value in data) {
		    //添加父菜单
		    if (value != "remove" && value != "indexOf") {
				if (value == "null") {
					hc += "<li class='uxp_hdr_menuSeparator'></li>";
				} else {
					if (data[value].length == 0 ) {
						
						/* 自动升级  */
						if (value == "自动升级") {
							hc += "<li><A target='main' href='autoupdate.do' id = '" + value+ "'>" + value + "</A></li>"
						} 
						/*
						if (value == "交换机管理") {
							hc += "<li><A target='_blank' href='http://"+saviIP+"/savimanager/login2.do?name="+userName+"&password="+userPwd+"' id = '" + value+ "'>" + value + "</A></li>"
						} 
						*/					
					} else {
						hc += "<li><A href='#' id = '" + value+ "'>" + arraw + value + "</A></li>"
					}
				}
			}
		}
		
		$("#uxp_hdr_jewelMenu").html(hc).css("height", "670px");
		$("#uxp_hdr_jewelMenu").show();
		$("#sub_manu").remove();
		$("#uxp_hdr_jewelMenu").after("<ul class = 'uxp_hdr_menu' id = 'sub_manu' ></ul>");
		$("#sub_manu").hide();
		$("#uxp_hdr_jewelMenu > li, #uxp_hdr_jewelMenu > .uxp_hdr_menuSeparator").hover(function(e){
		    $("#sub_manu").empty();
		    $("#sub_manu").show();
			var suhc = "";
			
			/** 添加子菜单 */
			var arr = data[$(this).children().attr("id")];
			suhc = "";
			for (v in arr) {
				if (v != "indexOf" && v != "remove") {
					if (arr[v] == "null") {
						suhc += "<li class='uxp_hdr_menuSeparator'></li>";
					} else {
						suhc += "<li><span><a href ='" + arr[v].url + "' target='main'>" + arr[v].title + "</a></span></li>"			
					}
				}	
			}
			$("#sub_manu").html(suhc);		
			$("#sub_manu").css("left", "10.6em");
	
			/* 处理样式 uxp_hdr_menu_open */
			$("#uxp_hdr_jewelMenu").css("BORDER-RIGHT", "#a5a8a8 1px solid");
			
			if (suhc == "") {
				$("#sub_manu").hide();
			} else {
				$("#sub_manu").show();
			}
			
			/** 控制滑入滑出功能 主要是在滑出时 隐藏子菜单*/
			$("#sub_manu").hover(function(){
				$("#sub_manu").show();
			},function(){
				$("#sub_manu").hide();
			});
			
			/** 改变li 的背景颜色 */
			$(".uxp_hdr_menu li[class!='uxp_hdr_menuSeparator']").hover(function(){
				$(this ).css("background-color", "#d6effc");
				
				/** 下面代码想增加箭头指向变化 */
				//$(this + " uxp_hdr_downArrow_ie6").removeClass("uxp_hdr_downArrow_ie6");
				//$("#img").addClass("uxp_hdr_rightArrow_ie6");
			},function(){
				//$("#img").removeClass("uxp_hdr_rightArrow_ie6");
				//$("#img").addClass("uxp_hdr_downArrow_ie6");
				$(this).css("background-color", "#f5fafc");
			});
			
			$("#sub_manu > li").click(function(){
				$(".uxp_hdr_menu").hide();
				isHide = true;
			});
		},function(){
		
		});
	}
}
/* 在 main frame 点击的时候 隐藏 菜单 */
function addFrameClick(){
	var iframe = document.getElementById("main");
	if (typeof(window.addEventListener) == "undefined") {
	 	iframe.attachEvent("onload", function(){
		 	$(iframe.contentDocument).click(function(e){
		 		if (e.pageX > 300) {
					$(".uxp_hdr_menu").hide();
					isHide = true;
				}
		 	});
	  	});
	} else {
	 	iframe.addEventListener("load", function(){
		 	$(iframe.contentDocument).click(function(e){
		 		if (e.pageX > 300) {
					$(".uxp_hdr_menu").hide();
					isHide = true;
				}
		 	});
	  	}, true);
	}
}