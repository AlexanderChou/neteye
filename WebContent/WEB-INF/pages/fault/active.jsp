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
 <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Refresh" CONTENT="60">
    <title>视图节点状态</title>
    <link rel="stylesheet" type="text/css" href="css/ext-all.css" />
</head>
    
<body>
<table border="1" cellspacing="1" cellpadding="1" width="100%">
	<s:iterator value="nodeList" status="st">
	<tr   height='21' class='title' >
			<s:set name="type" value='<s:property value="type"/>'/>
			<s:set name="status" value='<s:property value="status"/>'/>
			<s:if test="type==1">		 	
			 	<s:if test="status==1">
				<td align="left" width="15%"><img src="/images/green.gif"><s:property value="name"/></td>
				<td>&nbsp;
					<s:iterator value="result.keySet()" id="class"> 	
					 		<s:if test="id==#class">
						    <s:iterator value="result.get(#class)" id="portNode">
						    		<s:set name="portstatus" value='<s:property value="#portNode.status"/>'/>
						    		<s:if test="portstatus==1">
									<img src="/images/green.gif">
									</s:if>
									<s:else>
									<img src="/images/red.gif">
									</s:else>
									<a href='rttLossPic.do?ip=<s:property value="#portNode.ip"/>'><s:property value="#portNode.ip"/></a>	    				    		
						    </s:iterator>
						    </s:if>
					</s:iterator>
				 </td>
				</s:if>
				<s:else>
				<td ><img src="/images/red.gif"><s:property value="name"/></td>
				<td>&nbsp;
					<s:iterator value="result.keySet()" id="class"> 	
					 		<s:if test="id==#class">
						    <s:iterator value="result.get(#class)" id="portNode">						    		
						    		<s:set name="portstatus" value='<s:property value="#portNode.status"/>'/>
						    		<s:if test="portstatus==1">
									<img src="/images/green.gif">
									</s:if>
									<s:else>
									<img src="/images/red.gif">
									</s:else>	
									<a href="rttLossPic.do?ip=<s:property value="#portNode.ip"/>"><s:property value="#portNode.ip"/></a>	    		
						    </s:iterator>
						    </s:if>
					</s:iterator></td>
				</s:else>	
			</s:if>	
			<s:else>
			 	<s:if test="status==1">
				<td align="left" colspan="2"><img src="/images/green.gif"><s:property value="name"/></td>
				</s:if>
				<s:else>
				<td colspan="2"><img src="/images/red.gif"><s:property value="name"/></td>
				</s:else>	
			</s:else>			
	</tr>
	</s:iterator>
</table>
 </body>
 </html>
			