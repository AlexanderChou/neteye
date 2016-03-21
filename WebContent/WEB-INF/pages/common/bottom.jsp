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
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML class="IE IE_Win IE_M6 IE_D0" xmlns="http://www.w3.org/1999/xhtml" 
xmlns:web xmlns:sp>
<HEAD>
	<TITLE>iNetBoss Home</TITLE>
	<META http-equiv=Content-Type content="text/html; charset=utf-8">
	<LINK href="css/hig+start.css" type=text/css rel=StyleSheet>
	<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>
</HEAD>
<BODY >
<form name="myform" id="myforms" target="main">
 <TABLE id=uxp_ftr_control cellSpacing=0 cellPadding=0>
  <TBODY>
	<TR>
		<TD id=uxp_ftr_left>
		  <UL>
			<LI>
				<A id=uxp_ftr_link_trademark href="#" target=_top>2010 清华大学网络中心</A> </LI>
		   </UL>
	    </TD>
		
		
		<TD id=uxp_ftr_right>
		  <TABLE id=uxp_ftr_right_nest cellSpacing=0 cellPadding=0>
			<TBODY>
				<TR>
				  <TD>
					<UL>
					  <LI>
					  	<A id=uxp_ftr_link_helpcentral  href="#"></A> 
					  </LI>
					  <LI>
					  	<A id=uxp_ftr_link_account  href="#">当前用户：${userName }</A> 
					  </LI>
					  <LI class=uxp_ftr_item_last>	
						<A id=uxp_ftr_link_feedback href="javascript:doTicket()"><span id="ticket"></span></A> 	
					  	<A id=uxp_ftr_link_feedback href="javascript:dogo()"><span id="alarm"><font color="blue">报警消息：0条新</font></span></A> 						
					  </LI>
					</UL>
				   </TD>
				 </TR>
			 </TBODY>
		  </TABLE>
		</TD>
	  </TR>
	</TBODY>
 </TABLE>
 </form>
</BODY>
<SCRIPT LANGUAGE="JavaScript">
<!--
Ext.namespace("cernet2");
cernet2.alarmApp = function() {
	return {
		init : function() {var statusTask = {
				run: function(){
					Ext.Ajax.request({
						url : 'json/alarm.do',
						disableCaching : true,
						method : 'post',				
						success : function(result, request) {
							var alarmNum =  Ext.decode(result.responseText).alarmNum;
							var ticketInfo =  Ext.decode(result.responseText).ticketInfo;
							if(alarmNum>0){
								Ext.get("ticket").dom.innerHTML ="<font color='red'>"+ ticketInfo+"</font>";
								Ext.get("alarm").dom.innerHTML ="	报警消息：<font color='red'>"+alarmNum+"条新</font>";
							}else{
								Ext.get("ticket").dom.innerHTML = "<font color='red'>"+ticketInfo+"</font>";
							}
						},
						failure : function(result, request) {
							//Ext.Msg.alert("fault alarm error!");
						}
					});
				},
				interval: 30*1000 //30 second
			};
			Ext.TaskMgr.start(statusTask);
		},
		initWithSomeDelay: function() {
			window.setTimeout(this.init,300);
		}
	};
}();
Ext.onReady(cernet2.alarmApp.initWithSomeDelay, cernet2.alarmApp);
function dogo(){
	document.getElementById('alarm').innerHTML="报警消息：<font color='blue'>0条新</font>"; 
	document.getElementById("myforms").action = "alarmInfo.do";
	document.getElementById("myforms").submit();
}
function doTicket(){
	document.getElementById('ticket').innerHTML=""; 
	document.getElementById("myforms").action = "allTicketList.do";
	document.getElementById("myforms").submit();
}
//-->
</SCRIPT>

<script type="text/javascript" >
	/** 时间的变化 和 用户名的变化 */
	function timeInit(){
		try {
			setInterval("addTime()", 1000);
		} catch (err) {
			window.location.reload();	
		}
	}
	
	function addTime(){
		/* 设置 top frame 下 的mainframe 的大小 */
		setBodySize();
		var date = new Date();
	    document.getElementById("uxp_ftr_link_helpcentral").innerText ="当前时间：" +  date.getYear() + "-" + eval(date.getMonth() + 1 )+ "-" + date.getDate() + "  " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
	}
	
	timeInit();
	
	function setBodySize(){
	
		/* 在这里捕获异常是在top.jsp 刷新的时候 这里不出现错误  */
		try {
			var mainFrame = null;
			var menuDiv = null;
			try {
				/* ie 和 ff 下能运行 */
				mainFrame = window.top.document.getElementById('topFrame').contentDocument.getElementById("main");
				menuDiv = window.top.document.getElementById('topFrame').contentDocument.getElementById("menuDiv");
			} catch (e) {
				/* 在搜狗浏览器下运行  */
				mainFrame = parent.frames['topFrame'].document.getElementById("main");
				menuDiv = parent.frames['topFrame'].document.getElementById("menuDiv");
			} 
			var bodyHeight = window.parent.document.body.offsetHeight;
			var frameHeight = bodyHeight - menuDiv.offsetHeight - 44;
			mainFrame.height = frameHeight;
		} catch (err){
			window.location.reload();
		}
	}
	
</script>
</HTML>
