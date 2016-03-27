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
 <%@ taglib prefix="s" uri="/struts-tags" %>
 <%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>设备的过滤</title>
<script type="text/javascript" src="js/jquery.min.js"></script>
<link type="text/css" href="css/ext-all.css" rel="stylesheet" />
<script type="text/javascript" src="js/ext-base.js"></script>
<script type="text/javascript" src="js/ext-all.js"></script>
<script type="text/javascript">
	$(function(){
		$("#submit").click(function(){
					var deviceArr = "";
					$(".device").each(function(i, v){
						var deviceName = $(this).children(".deviceName").text();
						var deviceIP = $(this).children(".deviceIp").text();
						var community = $(this).children(".community").text();
						var version = $(this).children(".version").text();
						var model = $(this).children(".model").text();
						var updateType = $("input[name=changeType_" + i + "][checked]").val();
						var string=	deviceName + ";" + deviceIP + ";"  + community + ";" + version + ";" + updateType+ ";" + model;;	
						deviceArr+=string+"|";							
					});
					topoSave(deviceArr);
					showBar();
				});				
	});
	function topoSave(deviceArr) {
		$.ajax({
		  type: "POST",
		  url: "json/configSave.do",
		  data:{deviceArr:deviceArr},
		  dataType: "json",
		  timeout:300000,
		  success:function(msg){
		  	  window.location.href = "devicelistaction.do?deviceTypeId=${deviceTypeId}&status=${status}";
		  }
		}); 
	}
	function showBar(){
		var progressBar = function(){ 
		    Ext.MessageBox.show({
                 title:"请稍后!",
                 msg:"正在进行保存!",
                 progress:true,
                 width:300,
                 closable:true,
                 wait:true,
                 waitConfig:{interval:300}//0.3s进度条自动加载一定长度
	    	});
	    
	    }();
	}
</script>
	<style type="text/css">
		<!--
		body {
			margin-left: 0px;
			margin-top: 0px;
			margin-right: 0px;
			margin-bottom: 0px;
		}
		
		#bodyDiv {
			margin-left:10%;
			width:80%;
		}
		
		#mainTable {
			border-right-width:1px;
			border-right-color:#99BBE8;
			border-right-style:solid;
			border-left-width:1px;
			border-left-color:#99BBE8;
			border-left-style:solid;
		}		
		
		.STYLE1 {font-size: 12px}
		.STYLE3 {font-size: 12px; font-weight: bold; }
		.STYLE4 {
			color: #03515d;
			font-size: 12px;
		}
		
		#pop {
			top:45%;
			left:40%;
			width:250px;
			position:absolute;
			display:none;
		}
		
		#title {
			width:250px;
			height:20px;
			background-color:#EE6326;
			padding-left:10px;
			padding-top:3px;
			font-size:80%;
		}
		
		#info {	
			padding:3px 0 0 5px;		
			width:255px;
			height:25px;
			background-color:white;
			font-size:90%;
		}
		-->
	</style>
</head>
<body onload="maxBody()">
<div id="bodyDiv">
	<table width="100%" border="0" id="mainTable" cellspacing="0" cellpadding="0">
		  <tr>
	    <td height="30" background="images/rr/h2bg.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td width="46%" valign="middle"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	              <tr>
	                <td width="5%"><div align="center"><img src="images/tb.gif" width="16" height="16" /></div></td>
	                <td width="95%" class="STYLE1"><span class="STYLE3">你当前的位置</span>：lookbackIP重复设备列表</td>
	              </tr>
	            </table></td>
	            <td width="54%"><table border="0" align="right" cellpadding="0" cellspacing="0">
	              <tr>
	                <td width="60"><table width="90%" border="0" cellpadding="0" cellspacing="0">
	                  <tr>
	                    <td class="STYLE1"><div align="center"></div></td>
	                    <td class="STYLE1"><div align="center"></div></td>
	                  </tr>
	                </table></td>
	                <td width="60"><table width="90%" border="0" cellpadding="0" cellspacing="0">
	                  <tr>
	                    <td class="STYLE1"><div align="center"></div></td>
	                    <td class="STYLE1"><div align="center"></div></td>
	                  </tr>
	                </table></td>
	                <td width="52"><table width="88%" border="0" cellpadding="0" cellspacing="0">
	                  <tr>
	                    <td class="STYLE1"><div align="center"></div></td>
	                    <td class="STYLE1"><div align="center"></div></td>
	                  </tr>
	                </table></td>
	                <td width="60"><table width="87%" border="0" cellpadding="0" cellspacing="0">
	                  <tr>
	                    <td class="STYLE1"><div align="center">
	                     
	                    </div></td>
	                    <td class="STYLE1"></td>
	                  </tr>
	                </table></td>
	              </tr>
	            </table></td>
	          </tr>
	        </table></td>
	      </tr>
	    </table></td>
	  </tr>
		  <tr >
		    <td><table  width="100%" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td width="8" background="images/tab_12.gif">&nbsp;</td>
		        <td><table  width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="b5d6e6" onmouseover="changeto()"  onmouseout="changeback()">
		          <tr>		           
		            <td  height="22"  bgcolor="#DEECFD"><div align="center"><span class="STYLE1">中文名称</span></div></td>		            
		            <td   bgcolor="#DEECFD"><div align="center"><span class="STYLE1">loopbackIP</span></div></td>
		            <td  height="22"  bgcolor="#DEECFD"><div align="center"><span class="STYLE1">Community</span></div></td>
		            <td  height="22"  bgcolor="#DEECFD"><div align="center"><span class="STYLE1">SNMP版本号</span></div></td>
		            <td  height="22"  bgcolor="#DEECFD"><div align="center"><span class="STYLE1">设备类型</span></div></td>
		            <td height="22"  bgcolor="#DEECFD" ><div align="center"><span class="STYLE1">更新方式</span></div></td>
		            <td  height="22"  bgcolor="#DEECFD"><div align="center"><span class="STYLE1">名称（已存在）</span></div></td>	
		            <td  height="22"  bgcolor="#DEECFD"><div align="center"><span class="STYLE1">中文名称（已存在）</span></div></td>		            
		            <td   bgcolor="#DEECFD"><div align="center"><span class="STYLE1">loopbackIP（已存在）</span></div></td>
		            <td  height="22"  bgcolor="#DEECFD"><div align="center"><span class="STYLE1">loopbackIPv6（已存在）</span></div></td>
		            <td  height="22"  bgcolor="#DEECFD"><div align="center"><span class="STYLE1">设备类型（已存在）</span></div></td>
		            <td  height="22"  bgcolor="#DEECFD"><div align="center"><span class="STYLE1">端口数（已存在）</span></div></td>          
		          </tr>
		          <s:iterator value="existList" id='device' status='st'> 
		          <tr class="device">
		            <td bgcolor="#FFFFFF" class="deviceName"><div align="center"><span class="STYLE1">${device.chineseName }</span></div></td>		            
		            <td height="20" bgcolor="#FFFFFF" class="deviceIp"><div align="center"><span class="STYLE1">${device.loopbackIP }</span></div></td>
		            <td height="20" bgcolor="#FFFFFF" class="community"><div align="center"><span class="STYLE1">${device.readCommunity} </span></div></td>
		            <td height="20" bgcolor="#FFFFFF" class="version"><div align="center"><span class="STYLE1">${device.snmpVersion }</span></div></td>
		            <td height="20" bgcolor="#FFFFFF" class="model"><div align="center"><span class="STYLE1">${device.model }</span></div></td>
		            <td height="20" bgcolor="#FFFFFF" class="updateType"><div align="center" ><span class="STYLE1"><input type="radio" checked name="changeType_${st.index }" value="0" />忽略<input type="radio"  name="changeType_${st.index }" value="1" />更新名称<input name="changeType_${st.index }" value="2" type="radio" />更新设备</span></div></td>
		            <td bgcolor="#FFFFFF" ><div align="center"><span class="STYLE1">${device.name }</span></div></td>	
		            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">${device.serial}</span></div></td>	            
		            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">${device.productor} </span></div></td>
		            <td height="20" bgcolor="#FFFFFF" ><div align="center"><span class="STYLE1">${device.loopbackIPv6 }</span></div></td>
		            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">${device.supporter }</span></div></td>		           
		            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">${device.ifNum}</span></div></td>
		           </tr>
		           </s:iterator> 
		        </table></td>
		        <td width="8" background="images/tab_15.gif">&nbsp;</td>
		      </tr>
		    </table>
		    </td>
		  </tr>
	  <tr>
	    <td height="28" background="images/rr/h2bg.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td class="STYLE4"></td>
	            <td><table border="0" align="right" cellpadding="0" cellspacing="0">
	                <tr>
	                  <td width="40"></td>
	                  <td width="45"></td>
	                  <td width="45"></td>
	                  <td width="40"></td>
	                  <td width="100"><div align="center"><span class="STYLE1">
	                   </span></div></td>
	                  <td width="40"><input type="button" id="submit" value="  确认提交    " /></td>
	                </tr>
	            </table></td>
	          </tr>
	        </table></td>
	      </tr>
	    </table></td>
	  </tr>
	</table>	
</div>
</body>
</html>