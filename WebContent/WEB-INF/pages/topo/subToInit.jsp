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
<%@ page language="java" %>
<%@ page contentType = "text/html; charset=UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN " "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>子网拓扑初始化信息</title>
<link rel='StyleSheet' href="css/topoInit.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript">
	
	$(function(){
		$("#addCommunity").click(function(){
			addCommunity();
		});
		$("#addNetAndMask").click(function(){
			addNetAndMask();
		});
	})


</script>

</head>
<body>
<div id="outer">
	<div id="bodyDiv">
		<div id="menu">
			<s:include value="right_menu.jsp"></s:include>
		</div>
	
		<div id="infoDiv">
		
			<div id="menuDiv1" class="menuDiv" style="width:840px;background-color:#F2F4FF;">
				<div id="top" style="width:840px;">
					<h2>子网拓扑发现</h2>
				</div>
			
				<div id="leftMenu" style="width:840px;background-color:#F2F4FF;overflow:auto;height:100%;">
		
					<form name="InfoInput" method="POST" action="totaltopodisc.do">
						<table width="100%" class="ListTable" id="addPara" >
											<tr height="23" align="left" class="title">
												<td colspan="4">&nbsp;</td>
											</tr>
											<tr height="23" align="left" class="title">
												<td width="5%">&nbsp;</td>
												<td width="15%">发现名称：</td>
												<td colspan="2">
													<input type="text" name="topoName" onblur="checkName()" style="width:202px;" value='${name }'  maxlength="50"/ id="name">
													<span id='nameinfo'></span>
												</td >									
											</tr>
											<tr height="23" align="left" class="title subnet">
												<td width="5%">&nbsp;</td>
												<td width="15%">子网地址：</td>
												<td colspan="2">
													<input type="text" name="seed" onblur="checkIP()" style="width:202px;" value='${ip }' maxlength="50" value="" id="addr1">
													<span id='ipinfo'></span>
												</td>									
											</tr>
											<tr height="23" align="left" class="title submask">
												<td width="5%">&nbsp;</td>
												<td width="15%">子网掩码：</td>
												<td colspan="2">
													<input type="text" name="mask" onblur="checkMask()" value='${sub }' style="width:202px;" maxlength="50" value="" id="maskId1">
													<span id='maskinfo'></span><img align='absmiddle' src="images/default/dd/drop-add.gif" alt="添加" id="addNetAndMask" style="cursor:hand">
												</td>
																					
											</tr>
											
											<tr height="23" align="left" class="title">
												<td width="5%">&nbsp;</td>
												<td width="15%">生成树协议：</td>
												<td><select name="stp" style="width:202px;">
													<option value="0" selected>使用</option>
													<option value="1">不使用</option>
												</select></td>
											</tr>
											<!--<tr height="23" align="left" class="title">
												<td width="5%">&nbsp;</td>
												<td width="15%">SNMP版本:</td>
												<td width="15%" colspan="2">
												 <select name="version" style='width:202px;'>
												  <option value="1">1</option>
												  <option value="2C" selected>2C</option>
												 </select>
											     </td>
											</tr>-->
											<tr height="23" align="left" class="title community">
												<td width="5%">&nbsp;</td>								
							  					<td width="15%">Community：</td>
							  					<td width="15%"> 
												<input name="community" type="password" maxlength="50" style="width:202px;">
							  					</td>				  					
							  					<td align="left">
												<img align='absmiddle' src="images/default/dd/drop-add.gif" alt="添加" id="addCommunity" style="cursor:hand">
												</td>
											</tr>								
											<tr height="23" align="left" class="title">
												<td width="5%">&nbsp;</td>								
							  					<td width="15%"></td>
							  					<td width="15%" align="center"> 
												<input class="button" type="button" value="启 动" onclick="dogo()">
							  					</td>				  					
							  					<td align="left">
												</td>
											</tr>								
										</table>							
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

</body>
<SCRIPT LANGUAGE="JavaScript">

	function addNetAndMask(){
		var subnets = $(".subnet");
		var submask = $(".submask");
		
		if (subnets.length > 0) {
			var old_net = $(subnets[subnets.length-1]);
			var old_mask = $(submask[submask.length-1]);
			
			var new_net = old_net.clone().attr("id", "net" + subnets.length);
			old_mask.after(new_net);
			
			var new_mask = old_mask.clone().attr("id", "mask" + submask.length);
			var img = new_mask.children("td").children("img");
			img.attr("src", "images/delete.gif").attr("alt", "删除").attr("id", "removeImg");
			img.unbind();
			img.click(function(){
				$("#" + "net" + subnets.length).remove();
				$("#" + "mask" + submask.length).remove();
			});
			new_net.after(new_mask);
		}
	}
	
	function addCommunity(){
	
		var coms = $(".community");
		
		if (coms.length > 0) {
			var old_com = $(coms[coms.length-1]);
			var new_com = old_com.clone().attr("id", "community" + coms.length);
			var img = new_com.children("td").children("img");
			img.attr("src", "images/delete.gif").attr("alt", "删除");
			img.unbind();
			img.click(function(){
				$("#" + "community" + coms.length).remove();
			});
			old_com.after(new_com);
		}
	
	}


	var menuDiv = document.getElementById("menuDiv1");
	var height = document.body.clientHeight * 0.95;
	menuDiv.style.height = height;
<!--
	document.InfoInput.topoName.focus();
	isName = /^[A-Za-z\_\d]*$/;//名称只能是英文和数字的组合
	//isName = /^[A-Za-z\-\_\d\(\)\u0391-\uFFE5-\\.]*$/; //名称可以是中文、英文和数字的组合
	//IP地址校验 
	isIPaddress = new RegExp('^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])(\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])){3}$'); //IP地址校验
	isMask = new RegExp('^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])(\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])){3}$');
	/**
	* 函数名： _checkIput_fomartIP
	*   函数功能： 返回传入参数对应的8位二进制值
	* 传入参数： ip:点分十进制的值(0~255),int类型的值，
	* 返回值:   ip对应的二进制值(如：传入255，返回11111111;传入1,返回00000001)
	**/
	function _checkIput_fomartIP(ip){
		return (ip+256).toString(2).substring(1); //格式化输出(补零)
	}
	/**
	* 函数名： validateMask
	*   函数功能： 验证子网掩码的合法性
	* 传入参数： MaskStr:点分十进制的子网掩码(如：255.255.255.192)
	* 返回值：    true:   MaskStr为合法子网掩码
	*      	   false: MaskStr为非法子网掩码
	**/
	function validateMask(MaskStr){
		/* 有效性校验 */
		var maskPattern = /^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/;
		if(!maskPattern.test(MaskStr))return false;
	
		/* 检查域值 */
		var maskArray = MaskStr.split(".");
		var mask1 = parseInt(maskArray[0]);
		var mask2 = parseInt(maskArray[1]);
		var mask3 = parseInt(maskArray[2]);
		var mask4 = parseInt(maskArray[3]);
		if ( mask1<0 || mask1>255 /* 每个域值范围0-255 */
		   || mask2<0 || mask2>255
		   || mask3<0 || mask3>255
		   || mask4<0 || mask4>255 )
		{
		   return false;
		}

		/* 检查二进制值是否合法 */
		//拼接二进制字符串
		var mask_binary = _checkIput_fomartIP(mask1) + _checkIput_fomartIP(mask2) + _checkIput_fomartIP(mask3) + _checkIput_fomartIP(mask4);
	
		if(-1 != mask_binary.indexOf("01"))return false;
		return true;
	}
	
	function dogo(){
		var topoName = document.InfoInput.topoName.value;
		var para_name = document.InfoInput.community;		
		if(topoName==""){
			alert("拓扑名称不能为空！");
			document.InfoInput.topoName.focus();
			return false;
		}else{
			if (!isName.test(topoName)){
				alert("拓朴发现名字只能用英文字母、数字和下划线组成!");
				return;
			}
			
			var seedElements = InfoInput.seed;
			if(seedElements.value==undefined){
					for (var i = 0; i < seedElements.length - 1; i++ ){
						if (i == 0 ) {
							if(seedElements[i].value == ""){
								alert("起始地址不能为空!");
								seedElements[i].focus();
								return ;
							}
							
							if(!isIPaddress.test(seedElements[i].value)) {
								alert("请输入有效的IPV4地址!");
								return;
							}
						} else {
							if (seedElements[i].value != "") {
								if (!isIPaddress.test(seedElements[i].value)) {
									alert("请输入有效的IPV4地址!");
									return;
								}
							}
						}
					}
				
					var maskElements = InfoInput.mask;
					for (var i = 0 ; i < maskElements.length -1; i ++ ){
						if (i == 0 ) {
							if(maskElements[i].value == ""){
								alert("子网掩码不能为空！");
								maskElements[i].focus();
								return ;
							}
							
							if(!isMask.test(maskElements[i].value)) {
								alert("请输入有效的子网掩码!!");
								return;
							}
						} else {
							if (maskElements[i].value != "") {
								if (!isMask.test(maskElements[i].value)) {
									alert("请输入有效的子网掩码!");
									return;
								}
							}
						}
					}
				}else{		
					if(seedElements.value == ""){
								alert("起始地址不能为空!");
								seedElements.focus();
								return ;
							}
							
							if(!isIPaddress.test(seedElements.value)) {
								alert("请输入有效的IPV4地址!");
								return;
							}
					var maskElements = InfoInput.mask;
					if(maskElements.value == ""){
								alert("IP掩码不能为空！");
								maskElements.focus();
								return ;
							}
							
							if(!isMask.test(maskElements.value)) {
								alert("请输入有效的IP掩码!!");
								return;
						}
				}
					
			var name = $("#name").val();
			$.post('json/nameIsHaveSub.do',{'name':name}, function(v){
				if (v.indexOf('have') == -1) {
					document.InfoInput.action="subtopodisc.do?name="+topoName;	
					document.InfoInput.submit();
				} else {
					alert("该发现名称正在使用！");
				}
			});
		}
	 }
//-->
</SCRIPT>
	<script type="text/javascript" src="js/topo_check.js"></script>
</html>