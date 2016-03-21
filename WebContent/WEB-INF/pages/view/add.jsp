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
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<TITLE>浮动层居中的对话框效果演示</TITLE>
<META http-equiv=content-type content="application/xhtml+xml; charset=UTF-8">
</HEAD>
<BODY>
	<H1>浮动层居中的对话框效果演示</H1>
		<div id="layer" style="position:absolute;z-index:100;display:none;"> 
		</div> 
		<input type="button" name="添加" value="点击生成浮动层" onclick="javascript:generateFloatLayer()">
</BODY>
</HTML>
<script language="JavaScript">
function generateFloatLayer()
	{
		floatArea=document.getElementById("layer");
 		floatArea.style.display="none";
 		divClose='<div id="close" style="position:absolute; right:10px; top:5px; left:auto; bottom:auto;border soild 3px"><a href="javascript:closeFloat();">X</a></div><br>';
		floatArea.innerHTML=divClose+"<div id=\"floatcontent\">要显示的内容</div>";
		floatArea.style.border="black 2px solid";
		floatArea.style.left=(document.documentElement.scrollLeft)+"px";
		floatArea.style.top=(document.documentElement.scrollTop)+"px";
		floatArea.style.width="400px";
		floatArea.style.height="500px";
		floatArea.style.display="";

	}
function closeFloat()
	{
		 floatArea=document.getElementById("layer");
		 floatArea.innerHTML="";
		 floatArea.style.display="none";
	}
</script>	