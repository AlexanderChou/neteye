<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN " "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<TITLE><s:text name='loginInfo.login.title'/></TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<META http-equiv=pragma content=no-cache>
<link rel='StyleSheet' href="css/savitopoInit.css" type="text/css" />
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
				show_info.text("<s:text name='loginInfo.login.notNull'/>");
				
			} 
			else {
				var value_length = this.value.length;
				if (value_length < 6) {
					error_info.show();
					show_info.text("<s:text name='loginInfo.login.pwdShort'/>");
				} else if (value_length > 24){
					error_info.show();
					show_info.text("<s:text name='loginInfo.login.pwdLong'/>");
				} else {
					error_info.hide();
					show_info.empty();
				}
			}
		});
		
		$("#submitButton").click(function(){
		
			if (userName.val() == "") {
				error_info.show();
				show_info.text("<s:text name='loginInfo.login.nullUsername'/>");
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
		$.post("login.do",{ "name": userName.val(), "password": password.val()}, 
					callBack, "json");
	}

	function callBack(data){
		var login_info = data.login_info;
		var error_info = $("#error_info");
		if (login_info == "success") {
			document.location.href = "mainFrame.do";
		} else {
			error_info.show();
			var show_info = $("#show_info");
			show_info.text(login_info);
		}
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
								<TD class=UIDStyle vAlign=top align=left><s:text name='loginInfo.login.username'/>:<INPUT 
									class=loginInput id='userName'/></TD>
							</TR>
							<TR>
								<TD>&nbsp;</TD>
							</TR>
							<TR>
								<TD class=UIDStyle vAlign=top align=left><s:text name='loginInfo.login.password'/>:
								<INPUT class=loginInputPass id="password" 
									 type=password />
								</TD>
							</TR>
							<TR>
								<TD>&nbsp;</TD>
							</TR>
							<TR>
								<TD><center><input id="submitButton" type="button" value="<s:text name='loginInfo.login.login'/>" style="width:80px" /></center></TD>
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
				<TD class=copyright1 vAlign=center align=middle height=24><s:text name='loginInfo.login.organization'/>
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
