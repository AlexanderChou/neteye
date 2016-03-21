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
<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN " "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>拓扑初始化信息</title>
<link rel='StyleSheet' href="css/topoInit.css" type="text/css">
<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
<script type="text/javascript" src="js/ext-base.js"></script>
<script type="text/javascript" src="js/ext-all.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript">
	$(function(){
	
		$("#addCommunity").click(function(){
			addCommunity();
		});
	});

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
</script>
</head>
<body>
<script type="text/javascript" src="js/wz_tooltip.js"></script>
	
	<div id="outer">
		<div id="bodyDiv">
			<div id="menu">
				<s:include value="right_menu.jsp"></s:include>
			</div>
		
			<div id="infoDiv">
			
				<div id="menuDiv1" class="menuDiv" style="width:840px;">
					<div id="top" style="width:840px;">
						<h2>拓扑发现</h2>
					</div>
				
					<div id="leftMenu" style="width:840px;background-color:#F2F4FF;overflow:auto;height:100%;">
						<form name="InfoInput" method="POST">
							<table width="100%" class="ListTable" id="addPara">
								<tr height="23" align="left" class="title">
									<td colspan="4">&nbsp;</td>
								</tr>
								<tr height="23" align="left" class="title">
									<td width="5%">&nbsp;</td>
									<td width="15%">发现名称：</td>
									<td colspan="2">
										<input type="text" name="topoName" onblur="checkName()" style="width:202px;" value='${name }' maxlength="50" / id="name">
										<span id='nameinfo'></span>
									</td>
								</tr>
								<tr height="23" align="left" class="title">
									<td width="5%">&nbsp;</td>
									<td width="15%">OSPF协议：</td>
									<td><select name="ospf" id="ospf" style="width:202px;" onchange="javascript:isOSPF()">
										<option value="0">使用</option>
										<option value="1" selected>不使用</option>
									</select></td>
								</tr>
								<tr height="23" align="left" class="title" id="topo_IP">
									<td width="5%">&nbsp;</td>
									<td width="15%">起始地址：</td>
									<td colspan="2">
										<a href="#" onmouseover="Tip('为了提高拓扑发现的速度和<br>性能，如果您的拓扑结构是星<br>型的，请输入您的中心IP:<br><img src=\'images/star.jpg\' width=\'120\' height=\'120\'><br>如果您的拓扑结构是树型<br>的，请输入您的根IP:<br><img src=\'images/tree.jpg\' width=\'120\'>')" onmouseout="UnTip()"> 
										<input type="text" name="seed" onblur="checkIP()" style="width:202px;" value='${ip }' maxlength="50" value="" id="addr1">
										</a>
										<span id='ipinfo'></span>
									</td>
								</tr>
								<tr height="23" align="left" class="title" id="topo_Depth">
									<td width="5%">&nbsp;</td>
									<td width="15%">最大深度：</td>
									<td><select name="maxheight" style="width:202px;">
										<option value="1" selected>1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
										<option value="6">6</option>
										<option value="7">7</option>
										<option value="8">8</option>
										<option value="9">9</option>
										<option value="10">10</option>
									</select></td>
								</tr>
								<tr height="23" align="left" class="title" id="topo_SpanTree">
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
									<td width="15%" colspan="2"><select name="version"  style="width:202px;">
										<option value="1">1</option>
										<option value="2C" selected>2C</option>
									</select></td>
								</tr>  -->
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
							</table>
							<input class="button" type="button" value="启 动" onclick="dogo()" style="margin-left:200px;"/>
						</form>
					</div>
				</div>
			</div>
		</div>
		</div>
</body>
<SCRIPT LANGUAGE="JavaScript">
	var menuDiv = document.getElementById("menuDiv1");
	var height = document.body.clientHeight * 0.95;
	menuDiv.style.height = height;
	document.InfoInput.topoName.focus();
	isName = /^[A-Za-z\_\d]*$/;//名称只能是英文和数字的组合
	//isName = /^[A-Za-z\-\_\d\(\)\u0391-\uFFE5-\\.]*$/; //名称可以是中文、英文和数字的组合
	//IP地址校验 
	ip = '(25[0-5]|2[0-4]\\d|1\\d\\d|\\d\\d|\\d)';
	ipdot = ip + '\\.';
	isIPaddress = new RegExp('^'+ipdot+ipdot+ipdot+ip+'$'); //IP地址校验
	function isOSPF(){
		var use = document.InfoInput.ospf.options[document.InfoInput.ospf.selectedIndex].value;
		document.getElementById('topo_IP').style.display= "inline" ;
		document.getElementById('topo_Depth').style.display= "inline" ;
		document.getElementById('topo_SpanTree').style.display= "inline" ;
		if(use==0){
			Ext.Ajax.request({
			url : 'json/hasOSPF.do?use=' + use,
			disableCaching : true,
			method : 'post',	
			timeout:300000,			
			success : function(result, request) {
				var info = Ext.decode(result.responseText).info;
				if(info == "enableOSPF"){
					//选项框的值为使用，IP和深度变为不可用，鼠标的焦点跳到community区域
					document.getElementById('topo_IP').style.display= "none" ;
					document.getElementById('topo_Depth').style.display= "none" ;
					document.getElementById('topo_SpanTree').style.display= "none" ;
					return;
				}else if(info == "successConfigOSPF"){
					/*调用A程序，根据A程序返回的XML文件，弹出一个页面显示该XML文件的值（单选按钮），用户选中某一行值	提交后，
	                                                调用B程序（带参数），根据B程序生成的文件是否存在，若存在，OSPF协议框为使用，否则，OSPF协议框为不使用*/
					 viewOSPFConfig();                       
				}else{
					alert("网络配置不正确，请检查！");
					document.getElementById("ospf").selectedIndex = 1;
				}
			},
			failure : function(result, request) {
				Ext.Msg.alert("failure","网络配置不正确，请检查！");
				document.getElementById("ospf").selectedIndex = 1;
			}
		});
		}else{
			return;
		}
	}	
	//显示OSPF设备配置内容
	function viewOSPFConfig() {
		var configStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : 'json/configList.do'
				}),
				//创建JsonReader读取router记录
				reader : new Ext.data.JsonReader({
					root : 'configList',
					fields : ['content']
				})
		});
	   configStore.load();
	
	    var MsgForm = new Ext.form.FormPanel({
	        baseCls: 'x-plain',
	        layout:'absolute',
        	items:[{
               xtype:"combo",
               name:'ospfConfig',
               width:300,
               fieldLabel:'设备配置',
               store:configStore,
               displayField:'content',
               mode: 'remote',
               emptyText:'选择设备配置',
               triggerAction:'all',
               hiddenName:'ospfConfig',
               editable : false,
               forceSelection : true
			}]
	    });
	
	    var window = new Ext.Window({
	        title: '设备配置信息',
	        width:325,
	        height:300,
	        minWidth: 300,
	        minHeight: 200,
	        layout: 'fit',
	        plain:true,
	        bodyStyle:'padding:5px;',
	        buttonAlign:'center',
	        items: MsgForm,
	        buttons: [{
	            text: '提交',
	            handler  : function(){
	            	var congfigValue = Ext.get('ospfConfig').getValue(false);
	                if(MsgForm.form.isValid()){
	            		MsgForm.form.doAction('submit',{
	            			url: 'json/cofigOSPF.do?congfigValue='+congfigValue,
	     					method: 'post',
	     					waitTitle:"提示",
	     					waitMsg:"<font color='black'>正在发送，请稍后......</font>",
	     					success:function(form,action){
	     						window.close();
	     						//选项框的值为使用，IP和深度变为不可用，鼠标的焦点跳到community区域
								document.getElementById('topo_IP').style.display= "none" ;
								document.getElementById('topo_Depth').style.display= "none" ;
								document.getElementById('topo_SpanTree').style.display= "none" ;
	     					},
	     					failure:function(form,action){
	     						Ext.Msg.alert('Failure', "<font color='red'>无法得到OSPF信息，请选择其它的网络配置</font>");
	     					}
	        			}); 
        			}//Endof if
            	}
	        },{
	            text: '取消',
	            handler  : function(){
            		window.close();
	            	document.getElementById("ospf").selectedIndex = 1;
                }
	        }]
	    });
	
	    window.show();
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
			var use = document.InfoInput.ospf.options[document.InfoInput.ospf.selectedIndex].value;
			if(use==1){
				if(InfoInput.seed.value == ""){
					alert("IP地址不能为空，请输入有效的IP地址！");
					return;
				}else{
					if(!isIPaddress.test(InfoInput.seed.value)) {
						alert("请输入有效的IPV4地址!");
						return;
					}
				}
			}//Endof if(use==1)
			
			if(para_name.length != undefined){
					for(var i=0; i<para_name.length; i++){
						if(para_name[i].value==""){
							alert("Community为空!");
							para_name[i].focus();
							return false;
						}
					}//Endof for
			}else{		   
					if(para_name.value==""){
						alert("Community为空!");
						para_name.focus();
						return false;
					}
			}//Endof else
			
			var name = $("#name").val();
			$.post('json/nameIsHave.do',{'name':name}, function(v){
				if (v.indexOf('have') == -1) {
					document.InfoInput.action="totaltopodisc.do?name="+topoName;
					document.InfoInput.submit();
				} else {
					alert("该发现名称已经被使用！");
				}
			});
		}//Endof else
	}
</SCRIPT>
<script type="text/javascript" src="js/topo_check.js"></script>
</html>