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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN " "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<TITLE>网络运行管理系统</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<META http-equiv=pragma content=no-cache>
<link rel='StyleSheet' href="css/topoInit.css" type="text/css" />
<LINK media=screen href="css/login.css" type=text/css rel=stylesheet>
<SCRIPT language=JavaScript1.2 src="js/validation.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/jquery.min.js"></SCRIPT>
<SCRIPT type="text/javascript">
	
	$(function(){
	
		var userName = $("#userName");
		var password = $("#password"); 
		var error_info = $("#error_info");
		var show_info = $("#show_info");
		
		password.blur(function(){
			if (this.value == "") {
				error_info.show();
				show_info.text("用户密码不能为空！");
				
			} else {
				var value_length = this.value.length;
				if (value_length < 6) {
					error_info.show();
					show_info.text("密码长度不能小于 6 位！");
				} else if (value_length > 24){
					error_info.show();
					show_info.text("密码长度为应为 6 到 24 位之间！");
				} else {
					error_info.hide();
					show_info.empty();
				}
			}
		});
		
		$("#submitButton").click(function(){
		
			if (userName.val() == "") {
				error_info.show();
				show_info.text("用户名不能为空！");
			} else {
				error_info.hide();
				show_info.empty();
			}
		
			var errorInfo = show_info.text();
			if (errorInfo == "") {
				doSubmit();
			} else {
				error_info.show();
			}
		});
	
	});
	
	function doSubmit() {
		var userName = $("#userName");
		var password = $("#password");
		$.post("login.do",{ "name": userName.val(), "password": password.val()}, function(response){
				var login_info = response.login_info;
				var error_info = $("#error_info");
				if (login_info == "success") {
					document.location.href = "mainPage.do";
				} else {
					error_info.show();
					var show_info = $("#show_info");
					show_info.text(login_info);
				}
		}, "json");
	}
</SCRIPT>


</HEAD>
<BODY onload="document.getElementById('userName').focus();"> 

<TABLE id=wrapper>
	<TR>
		<TD vAlign=center align=middle>
		<DIV id=LoginDiv>
		<FORM id=loginForm name=loginForm action=login.do method=post>
		<TABLE class=inner cellSpacing=0 cellPadding=0 width="100%" border=0>
			<TR>
				<TD vAlign=center align=left width="30%"
					background="images/Login3_bg.gif" height=260></TD>
				<TD vAlign=center align=left width=425 height=260>
				<TABLE cellSpacing=0 cellPadding=0 width=425 border=0>
					<TR>
						<TD vAlign=center align=left width=16><IMG height=263
							src="images/Login2a_bg.gif" width=16></TD>
						<TD vAlign=top align=right background="images/Login2_bg.gif">
						<DIV id=authenticationDiv
							style="position:relative;MARGIN-LEFT:100px; MARGIN-TOP: 0px; DISPLAY: block; HEIGHT: 200px">
						<DIV style="CLEAR: both; HEIGHT: 80px"></DIV>
						<DIV id=loginHolder>
						<TABLE height=50 cellSpacing=0 cellPadding=0 align="center" width="100%" border=0>
							<TR>
								<TD  vAlign=top align=left>
									<div id="error_info" style="display:none;position:relative;margin-top:-70px;margin-left:20px;width:150px;height:45px;z-index:10000;background:url(images/login_error.gif) no-repeat left top; font-family:Tahoma,Verdana,STHeiTi,simsun,sans-serif;font-size:12px;text-align:center;">
										<div style="position:absolute;#position:relative;margin-left:4px;width:100%;height:100%;background:url(images/login_error.gif) no-repeat top right;">
											<div style="position:absolute;#position:relative;margin-top:18px;width:100%;height:100%;background:url(images/login_error.gif) no-repeat right bottom;">
												<div style="position:absolute;#position:relative;margin-top:0px;margin-left:-4px;#margin-left:-7px;width:100%;height:100%;background:url(images/login_error.gif) no-repeat left bottom;" id="show_info"></div>
											</div>
										</div>
									</div>
								</TD>
							</TR>
							<TR>
								<TD class=UIDStyle vAlign=top align=left>用户名:<INPUT 
									class=loginInput id='userName'></TD>
							</TR>
							<TR>
								<TD>&nbsp;</TD>
							</TR>
							<TR>
								<TD class=UIDStyle vAlign=top align=left>密&nbsp;&nbsp;&nbsp;码:
								<INPUT class=loginInputPass id="password" 
									 type=password />
								</TD>
							</TR>
							<TR>
								<TD>&nbsp;</TD>
							</TR>
							<TR>
								<TD><center><input id="submitButton" type="button" value=" 登录  " style="width:80px" /></center></TD>
							</TR>
						</TABLE>
						</DIV>
						</DIV>
						<DIV style="CLEAR: both"></DIV>
						</TD>
						<TD vAlign=center align=right width=16><IMG height=263
							src="images/Login2b_bg.gif" width=16></TD>
					</TR>
				</TABLE>
				</TD>
				<TD vAlign=center align=right width="40%"
					background="images/Login3_bg.gif" height=260></TD>
			</TR>
			<TR>
				<TD vAlign=center align=right height=24>&nbsp;</TD>
				<TD class=copyright1 vAlign=center align=middle height=24>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;清华大学网络中心
				Copyright &copy; 2010,All Rights Reserved</TD>
				<TD vAlign=center align=right height=24>&nbsp;</TD>
			</TR>
		</TABLE>
		</FORM>
		</DIV>
		</TD>
	</TR>
</TABLE>
</BODY>
<SCRIPT LANGUAGE="JavaScript">
<!--
  IE = (document.all) ? 1 : 0;
   function keyDown(e){
        key = 0;
        if(IE){ key=event.keyCode;}
        else { key=e.keyCode; }
        if ( key == 13 ) doSubmit();
   }
   document.onkeydown = keyDown;
//-->
</SCRIPT>
</HTML>
