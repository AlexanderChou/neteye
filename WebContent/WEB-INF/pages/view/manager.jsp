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
<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN " "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%
	String flag = request.getParameter("flag");
	if(flag==null || flag.equals("")){
		flag = "true";
	}
%>
<head>
	<title>视图管理</title>
	<%if(flag.equals("true")){ %>
	<meta http-equiv="refresh"  content="1;url=manage.do?flag=false">

	<%
		
	}else{ %>
	<meta http-equiv="refresh"  content="300;url=manage.do?flag=false" >

	<%flag = "false";
	} %>
	<link rel='StyleSheet' href="css/topoInit.css" type="text/css">
	<link type="text/css" href="css/view.css"  rel="StyleSheet">
    <link type="text/css" href="css/ext-all.css"  rel="stylesheet"/>   
    <script type="text/javascript" src="js/ext-base.js"></script>
    <script type="text/javascript" src="js/ext-all.js"></script>
	<script type="text/javascript" src="js/view_add.js"></script>
	<script type="text/javascript" src="js/view_manager.js"></script>
	<%if(flag.equals("true")){ %>
	<script type="text/javascript">
		Ext.onReady( function() {
			var tableDiv=document.getElementById("outer");
			tableDiv.style.display = 'none';
		});
	</script>
	<%} %>
</head>
<body>
	<div id="outer">
		<div id="bodyDiv">
			<div id="menu">
				<s:include value="../config/right_menu.jsp"></s:include>
			</div>
		
			<div id="infoDiv">
				<div id="showDiv"></div>
				<div id="viewListDiv">
					<s:form name="myform" id="myform">
					<s:hidden name="submitView"></s:hidden>
						<s:iterator value="views" status="st" >
						   <s:if test="homePage">
							<div id="view" onmouseover="this.style.backgroundColor='#D6EFFC'" onmouseout="this.style.backgroundColor='#F5FAFC'" value="<s:property value='id'/>">
								<a href="read.do?viewId=<s:property value='id'/>"><img src="images/view_page.png" title="单击查看视图--<s:property value='name'/>" style="margin:center"/></a><br>
								
								<input type="checkbox" viewid='<s:property value="name"/>_<s:property value="id"/>' class="radio" name="view"><s:property value="name"/>
							</div> 
						   </s:if>
						   <s:else>
							   <div id="view" onmouseover="this.style.backgroundColor='#D6EFFC'" onmouseout="this.style.backgroundColor='#F5FAFC'" value="<s:property value='id'/>">
									<a href="read.do?viewId=<s:property value='id'/>"><img src="images/view.png" title="单击查看视图--<s:property value='name'/>" style="margin:center"/></a><br>
									
									<input type="checkbox" viewid='<s:property value="name"/>_<s:property value="id"/>' class="radio" name="view"><s:property value="name"/>
								</div> 
						   </s:else>
						</s:iterator>
					</s:form>
				</div>
				<div id="buttonDiv" >
					<input type="checkbox"  class="radio"  onclick="doSelAllView(this)">全选
					<input type="button" class="button" name="delete" value="删除" onclick="doSubmitView()"> 
					<input type="button" class="button" name="add" value="添加" onclick="addView()">
					<input type="button" class="button" name="rename" value="重命名" onclick="rename()">
					<input type="button" class="button" name="rename" value="设置为主监控页面" onclick="pageConfig()">
					<input type="button" class="button" name="add" value="事件状态视图" onclick="doEventStatus()"> 
					<input type="button" class="button" name="fault" value="故障监控视图" onclick="doFaultStatus()"> 
					<input type="button" class="button" name="flow" value="流量监控视图" onclick="doFlowStatus()"> 
					<INPUT type="button" value="流量监控配置" name=postconfig onclick="doConfig()">
				</div>
			</div>
		</div>
	</div>
	
	<SCRIPT type="text/javascript">
		Ext.onReady(function(){
			
			var panel = new Ext.Panel({
				title:"网络视图",
				width:840,
				items:[{
					width:840,
					height:document.body.clientHeight*0.95  - 55,
					autoScroll:true,
					contentEl:"viewListDiv"
				},{
					contentEl:"buttonDiv"
				}]
			});
			
			panel.render("showDiv");
			
		});
		
	</SCRIPT>
</body>
</html>