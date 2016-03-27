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
<%@page language="java" import="java.util.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String systemtopname = ( String )request.getAttribute( "systemtopname" );
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<HTML class="IE IE_Win IE_M6 IE_D0" xmlns="http://www.w3.org/1999/xhtml"
	xmlns:web xmlns:sp>
<HEAD>
<TITLE>iNetBoss Home</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<LINK href="css/hig+start.css" type=text/css rel=StyleSheet>
<script type="text/javascript">

	var logId = "${logId}";
	var userId="${userId}";
	var userName = "${userName}";
	var userPwd = "${userPwd}"
	var saviIP = "${saviIP}";
	function  ttt(e){
		var event = e || window.event;
		var i =  document.getElementById("main");
		if (event.keyCode == 122) {
			i.contentWindow.location.reload(true);
		}
	} 
	function doSearch(){
		document.uxp_hdr_search.submit(); 
		return false;
	}
</script>
<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
<SCRIPT type="text/javascript" src="js/jquery.min.js"></SCRIPT>
<script type="text/javascript" src="js/ext-base.js"></script>
<script type="text/javascript" src="js/ext-all.js"></script>
<link rel="stylesheet" type="text/css" href="css/deviceManage.css" />
<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript" src="js/menu.js"></script>
<script type="text/javascript">
	$(function(){
		$(document).keydown(function(e) {
   			if (e.keyCode == 122) {
				setTimeout("document.getElementById('main').contentWindow.location.reload()", 1000);
			}
		}); 
	})
</script>
<script for="window" event="onbeforeunload">  
 	 if (event.clientX < document.body.clientWidth && event.clientY < 0 ) {
 	 	Ext.Ajax.request({
 	 		url:"logout.do?logId=${logId}"
 	 	});
 	 }
</script>

<style type="text/css">
	
	#sub_manu {
		position:absolute;margin-left:5px;width:130px;BORDER-RIGHT: #a5a8a8 1px solid;height:670px;
		BORDER-RIGHT: #42555c 2px solid;  BORDER-TOP: #a5a8a8 1px solid; BORDER-LEFT: #a5a8a8 1px solid;BORDER-BOTTOM: #42555c 2px solid; 
	}
	
	

	#sub_manu li {
		width:120px;
	}
	
	.myclass {
		BORDER-RIGHT: #b9eaff 1px solid; PADDING-RIGHT: 8px; BORDER-TOP: #b9eaff 1px solid; PADDING-LEFT: 8px; BACKGROUND: #d6effc; PADDING-BOTTOM: 4px; BORDER-LEFT: #b9eaff 1px solid; PADDING-TOP: 4px; BORDER-BOTTOM: #b9eaff 1px solid
	}
	
	#uxp_hdr_jewelMenu {
		height:100% 
	}
	
	.parent_manu {
		padding-left:20px;
	}
	
	* html #arrowPosition {
		color:blue;DISPLAY: inline-block; VERTICAL-ALIGN: middle; OVERFLOW: hidden; WIDTH: 7px; POSITION: relative; TOP: -4px; HEIGHT: 7px
	}
	
	#arrowPosition {
		color:red;DISPLAY: inline-block; VERTICAL-ALIGN: middle; OVERFLOW: hidden; WIDTH: 7px; POSITION: relative; TOP: -4px; HEIGHT: 7px
	}
	*+html #arrowPosition {
		color:red;DISPLAY: inline-block; VERTICAL-ALIGN: middle; OVERFLOW: hidden; WIDTH: 7px; POSITION: relative; TOP: -4px; HEIGHT: 7px
	}
	
</style>

</HEAD>
<BODY>
<div id="menuDiv">
	<DIV class=datetext></DIV>
	<DIV id=start_top_minwidth></DIV>
	<DIV id=hdr_wrapper> <SCRIPT type=text/javascript>
				function uxp_p(t){t.parentNode.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+t.src+"')";}
			</SCRIPT>
	</DIV>
	<DIV id=uxp_hdr_parent_ie6>
	<TABLE id=uxp_hdr cellSpacing=0>
	
		<TR>
			<TD class=uxp_hdrInner>
			<TABLE class=uxp_hdrInnerTable cellSpacing=0>
	
				<TR>
					<TD class=uxp_hdr_pointer id=uxp_hdr_jewelParent
				    ><A
						id=uxp_hdr_jewelLink href="#"> <SPAN
						style="DISPLAY: inline-block; VERTICAL-ALIGN: top; OVERFLOW: hidden; WIDTH: 85px; POSITION: relative; HEIGHT: 37px">
					<SPAN > <IMG
						src="images/jewel_24_hover.png" onload=uxp_p(this)> </SPAN> </SPAN> <SPAN id="uxp_hdr_windowsLiveText"
						class=uxp_hdr_windowsLiveText style="VERTICAL-ALIGN: bottom;"><%= systemtopname %></SPAN> </A> <BR>
					<DIV class=uxp_hdr_menuParent id=uxp_hdr_menuParent>
					<UL class=uxp_hdr_menu id=uxp_hdr_jewelMenu style="DISPLAY: none">
					</UL>
	
					</DIV>
					</TD>
					<TD>
					<TABLE id=uxp_hdr_middleArea>
						<TR>
							<TD id=uxp_hdr_tabsParent>
							<TABLE class=uxp_hdr_pointer id=uxp_hdr_tabs cellSpacing=0>
								<TR>
									<TD><A id=uxp_hdr_tabs_current href="read.do" target="main"><SPAN>主监控图</SPAN></A></TD>
									<TD><A href="manage.do" target="main"><SPAN>视图管理</SPAN></A></TD>
									<TD><A href="topoHisList.do" target="main"><SPAN>拓扑历史查询</SPAN></A></TD>
									<TD><A href="deviceClass.do" target="main"><SPAN>设备管理</SPAN></A></TD>
									<TD><A href="FaultEventSatusView.do" target="main"><SPAN>节点状态</SPAN></A></TD>
									<TD><A href="allWatch.do" target="main"><SPAN>流量汇总监测图</SPAN></A></TD>
									<TD><A href="performanceAllWatch.do" target="main"><SPAN>性能汇总监测图</SPAN></A></TD>
									<TD><A href="GlobalEventStatus.do" target="main"><SPAN>事件列表</SPAN></A></TD>
									<TD><A href="totalIP.do" target="main"><SPAN>IP地址统计</SPAN></A></TD>
									<TD><A href="performanceTest.do" target="main"><SPAN>性能测量</SPAN></A></TD>
									<TD><A href="HuaSanView.do" target="main"><SPAN>H3C管理</SPAN></A></TD>
									<TD><A href="gotoUrl.do?type=sava1" target="_blank"><SPAN>域内SAVA管理</SPAN></A></TD>
								</TR>
							</TABLE>
							</TD>
				  			<TD id=uxp_hdr_searchParent>
							<FORM id=uxp_hdr_search name=uxp_hdr_search action="showSearchResults.do" target="main" method=get>
							<LABEL style="DISPLAY: none" for=uxp_hdr_searchInput>Search</LABEL> 
								<INPUT id=uxp_hdr_searchInput name=keywords> 
								<a href="javascript:void(0)" onclick="doSearch();">
								<img src="images/search.gif">
								</a>
							</FORM>
							</TD>
						</TR>
					</TABLE>
					</TD>
					<TD class=uxp_hdr_pointer id=uxp_hdr_meParent>
						<A id=uxp_hdr_signIn onclick=event.cancelBubble=true href="logout.do?logId=${logId }" target=_top>退出</A>
					</TD>
				</TR>
	
			</TABLE>
			</TD>
		</TR>
	
	</TABLE>
	</DIV>
</div>


 <iframe src="" ID='main' NAME="main"  MARGINHEIGHT="0" LEFTMARGIN="0"
	TOPMARGIN="0" style="width: 100%;" BORDER="0"
	FRAMEBORDER="no" SCROLLING="yes" NORESIZE="false"></iframe>
	
<script type="text/javascript">
	var mainFrame = document.getElementById("main");
	var cookies = document.cookie.split(";");
	var isHaveLastUrl = true;
	for (var index in cookies) {
		if (!isNaN(index)) {
			var kv = cookies[index].split("=");
			if ("lastUrl" == kv[0].trim()) {
				if (kv[1].indexOf("bottom.do") == -1) {
					mainFrame.src = "showSubnets.do";
					//mainFrame.src = kv[1];
				} else {
					mainFrame.src = "showSubnets.do";
				}
				isHaveLastUrl = false;
			    document.cookie = "lastUrl=showSubnets.do";  /* 小隐患 用法可能不对，待查 */
				break;  
			}
		}
	}
	
	if (isHaveLastUrl) {
		mainFrame.src = "showSubnets.do";
	}
</script>
</BODY>
</HTML>


