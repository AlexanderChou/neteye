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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.List"%>
<%@ page import="java.util.List" %>

<%@page import="com.topo.dto.Device"%>
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
	
		if ("${empty deviceList}" == "true") {
			$("#bodyDiv").hide();
			showBar();
			topoSave("");
		}
		
		$("#submit").click(function(){
			var deviceArr = new Array();
			$(".device").each(function(i, v){
			
				var deviceId = $(this).children(".deviceId").text();
				var deviceName = $(this).children(".deviceName").text();
				var deviceIP = $(this).children(".deviceIP").text();
				var community = $(this).children(".community").text();
				var deviceTypeName = $(this).children(".deviceTypeName").text();
				var topoTypeName = $(this).children(".topoTypeName").text();
				var updateType = $("input[name=changeType_" + i + "][checked]").val();
				if(updateType=="2"){
					deviceArr.push(deviceId + ";" + deviceName + ";" + deviceIP + ";" + updateType + ";" + community + ";" + topoTypeName);
				}else{
					deviceArr.push(deviceId + ";" + deviceName + ";" + deviceIP + ";" + updateType + ";" + community + ";" + deviceTypeName);
				}
				
			});
			topoSave(deviceArr);
			$("#bodyDiv").hide();
			showBar();
		});
	});
	
	function topoSave(deviceArr) {
		$.ajax({
		  type: "POST",
		  url: "json/topoSave.do",
		  data:{deviceArr:deviceArr,devices: eval('${devices}'),initLinks: eval('${initLinks}'),homePage:"${homePage}", topoViewName:"${topoViewName}", description:"${description}"},
		  dataType: "json",
		  timeout:300000,
		  success:function(msg){
		  	  window.parent.location.href = "read.do?viewId="+msg.info.viewId;
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
<script>
	

	var  highlightcolor='#c1ebff';
	//此处clickcolor只能用win系统颜色代码才能成功,如果用#xxxxxx的代码就不行,还没搞清楚为什么:(
	var  clickcolor='#51b2f6';
	function  changeto(){
		source=event.srcElement;
		if (source.tagName=="TR"||source.tagName=="TABLE")
			return;
		while(source.tagName!="TD")
			source=source.parentElement;
		source = source.parentElement;
		cs  =  source.children;
		
		//alert(cs.length);
		if  (cs[1].style.backgroundColor!=highlightcolor&&source.id!="nc"&&cs[1].style.backgroundColor!=clickcolor)
		for(i=0;i<cs.length;i++){
			cs[i].style.backgroundColor=highlightcolor;
		}
	}
	
	function  changeback(){
		if  (event.fromElement.contains(event.toElement)||source.contains(event.toElement)||source.id=="nc")
		return
		if  (event.toElement!=source&&cs[1].style.backgroundColor!=clickcolor)
		//source.style.backgroundColor=originalcolor
		for(i=0;i<cs.length;i++){
			cs[i].style.backgroundColor="";
		}
	}
	
	function maxBody(){
		window.parent.frame.cols = "0,*";
	}
</script>


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
		            <td width="3%" height="22"  style="display:none;" bgcolor="#DEECFD"><div align="center"><span class="STYLE1">设备ID</span></div></td>
		            <td  height="22"  bgcolor="#DEECFD"><div align="center"><span class="STYLE1">设备名称</span></div></td>
		            <td  height="22"  bgcolor="#DEECFD"><div align="center"><span class="STYLE1">中文名称</span></div></td>
		            <td  height="22"  bgcolor="#DEECFD"><div align="center"><span class="STYLE1">设备类型</span></div></td>
		            <td   bgcolor="#DEECFD"><div align="center"><span class="STYLE1">loopbackIP(ipv4)</span></div></td>
		            <td  height="22"  bgcolor="#DEECFD"><div align="center"><span class="STYLE1">loopbackIP(ipv6)</span></div></td>
		            <td  height="22"  bgcolor="#DEECFD"><div align="center"><span class="STYLE1">端口数</span></div></td>
		            <td height="22"  bgcolor="#DEECFD" ><div align="center"><span class="STYLE1">更新方式</span></div></td>
		            <td  height="22"  bgcolor="#DEECFD"><div align="center"><span class="STYLE1">设备名称（新发现）</span></div></td>
		            <td  height="22"  bgcolor="#DEECFD"><div align="center"><span class="STYLE1">设备类型（新发现）</span></div></td>
		            <td  height="22"  bgcolor="#DEECFD"><div align="center"><span class="STYLE1">looback_IP（新发现）</span></div></td>
		            <td  height="22"  bgcolor="#DEECFD"><div align="center"><span class="STYLE1">端口数（新发现）</span></div></td>
		            <td  height="22"  style="display:none;" bgcolor="#DEECFD"><div align="center"><span class="STYLE1">Community（新发现）</span></div></td>
		          </tr>
		          <s:iterator value="deviceList" id='device' status='st'> 
		          <tr class="device">
		            <td height="20" bgcolor="#FFFFFF" class="deviceId" style="display:none;"><div align="center"><span class="STYLE1">${device.id}</span></div></td>
		            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">${device.name} </span></div></td>
		            <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">${device.chineseName }</span></div></td>
		            <td bgcolor="#FFFFFF" class="deviceTypeName"><div align="center"><span class="STYLE1">${device.deviceTypeName }</span></div></td>
		            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">${device.ipv4 }</span></div></td>
		            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">${device.ipv6 }</span></div></td>
		            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">${device.ifNum }</span></div></td>
		            <td height="20" bgcolor="#FFFFFF" class="updateType"><div align="center" ><span class="STYLE1"><input type="radio" checked name="changeType_${st.index }" value="0" />忽略<input type="radio"  name="changeType_${st.index }" value="1" />更新名称<input name="changeType_${st.index }" value="2" type="radio" />更新设备</span></div></td>
		            <td height="20" bgcolor="#FFFFFF" class="deviceName"><div align="center"><span class="STYLE1">${device.topo_name} </span></div></td>
		            <td height="20" bgcolor="#FFFFFF" class="topoTypeName"><div align="center"><span class="STYLE1">${device.topo_deviceTypeName} </span></div></td>
		            <td height="20" bgcolor="#FFFFFF" class="deviceIP"><div align="center"><span class="STYLE1">${device.topo_ip} </span></div></td>
		            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">${device.topo_ifNum} </span></div></td>
		            <td height="20" bgcolor="#FFFFFF" class="community" style="display:none;"><div align="center"><span class="STYLE1">${device.community} </span></div></td>
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
	                  <td width="40"><div style="z-index:10000;position:fixed;right:2px;bottom:2px;">
	
		<input type="button" id="submit" value="  确认提交    " />
	
	</div></td>
	                </tr>
	            </table></td>
	          </tr>
	        </table></td>
	      </tr>
	    </table></td>
	  </tr>
	</table>	
</div>

	<div id="pop">
		<div id="title"> 『友情提示！』</div>
		<div id="info">
			<img src="images/Load.gif" />
			  视图正在保存中 .........
		</div>
	</div>
	
</body>
</html>