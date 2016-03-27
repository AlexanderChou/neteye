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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN "   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>事件状态</title>
	<link rel='StyleSheet' href="css/topoInit.css" type="text/css">
	<link type="text/css" href="css/ext-all.css"  rel="stylesheet"/>   
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>
	
	<STYLE type="text/css">
	
	
		#valueList {
			text-align:left;
			float:left;
		}
	
		#valueList li {
			float:left;
		}
		
	</STYLE>
	
</head>
<body>
<div id="outer">
	<div id="bodyDiv">
		<div id="menu">
			<s:include value="../config/right_menu.jsp"></s:include>
		</div>
	
		<div id="infoDiv">
			
			<div id="eventListDiv">
					<ul id="eventList">
						<s:iterator value="deviceMap" id="tmp">     
									<h3><s:property value='#tmp.key'/></h3>
									<ul id="valueList">
										<table border="1">
										<s:iterator value="#tmp.value" status="tmptable"> 
										<td>
								        		<img src="images/green.gif"/><a href="objEventStatus.do?objId=<s:property value='id'/>&objName=<s:property value='name'/>"><s:property value='name'/></a>
								        	 </td>
										<s:if test="#tmptable.index%6==5">
							                     <tr>
                                         </s:if>   
								        </s:iterator>
								    </table>
								    </ul>
						</s:iterator>
					</ul>
						<br />
			</div>	
		<div id="showDiv"></div>	
			
		</div>
	</div>
</div>

	<SCRIPT type="text/javascript">
		Ext.onReady(function(){
			var panel = new Ext.Panel({
				title:"事件状态",
				width:840,
				items:[{
					width:840,
					height:document.body.clientHeight*0.95 - 20,
					autoScroll:true,
					contentEl:"eventListDiv"
				}]
			});
			
			panel.render("showDiv");
		});
	</SCRIPT>
	
</body>
</html>