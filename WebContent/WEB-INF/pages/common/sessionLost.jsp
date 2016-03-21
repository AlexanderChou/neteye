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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<title></title>
<script language="JavaScript">
function goroot(){

	/* 设置cookie 记录最后的登录连接 */
	document.cookie = "lastUrl=" + document.location.href + ";name=CNGI";
	var parwindow =window.parent;
	if (parwindow.parent==null){
	    parwindow.location.href="welcome.do";
	}else{
	    parwindow.parent.parent.location.href="welcome.do";
	}
}
window.setTimeout(goroot,500);
</script>
</head>
<body scroll="no" id="body">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
      <tr><td valign="middle" align="center"><br>
	  <br>
		<table cellspacing="0" cellpadding="0" class="labeltable_middle">
	        <tr>
	        	<td class="labeltd_middle"><br>
	             <table  class="labeltable_middle_table">
	  					<tr height="20">
	                        <td height="20" class="labeltable_middle_td">系统提示</td>
					    </tr>
					    <tr>
	                        <td align="center" > <br>
	                          <br>
	                          <br>
	                          <font color="#000000" style="font-size:14px"> 您可能未登陆系统或已经超时了，本页在5秒后回到主页。</font><br>
	                          <br>
	                          <br>
	                          <html:text property="title" styleClass="input"  style="width:98%" maxlength="100" />
	                        </td>
	  					</tr>
				</table>
     				<br>
           	  </td>
       		</tr>
		</table>
		<table border="0" cellpadding="0" cellspacing="0" class="labeltable_bottom">
		    <tr>
		        <td class="left">&nbsp;</td>
		        <td class="center">&nbsp;</td>
		        <td class="right">&nbsp;</td>
		    </tr>
		</table>
      <br>     
   </td>
   </tr>
</table>
</body>
</html>