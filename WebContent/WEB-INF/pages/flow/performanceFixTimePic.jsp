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

	<div id="tabDiv">
		
	</div>
	
	<div id="div1">
		<div id="flagDiv"></div>
		<div id="imgDiv1" >
			<ul>
    			<li>日性能图</li>     		
    			<li><img src='file/performance/dat/pic/perf_<s:property value="performanceid"/>_<s:property value="deviceid"/>_<s:property value="oid"/>_day.gif' onerror="this.src='images/noday.gif'" /></li>
    		</ul>
    	</div>
    	<div id="imgDiv2" >
    		<ul>
    			<li>周 性能图</li>
    			<li><img src='file/performance/dat/pic/perf_<s:property value="performanceid"/>_<s:property value="deviceid"/>_<s:property value="oid"/>_week.gif' onerror="this.src='images/noday.gif'" /></li>
    		</ul>
    	</div>
		<div id="imgDiv3" >
			<ul>		
    			<li>月性能图</li>
    			<li><img src='file/performance/dat/pic/perf_<s:property value="performanceid"/>_<s:property value="deviceid"/>_<s:property value="oid"/>_month.gif' onerror="this.src='images/noday.gif'" /></li>
    		</ul>
    	</div>
    	<div id="imgDiv4" >
			<ul>	
				<li>年性能图</li>
    			<li><img src='file/performance/dat/pic/perf_<s:property value="performanceid"/>_<s:property value="deviceid"/>_<s:property value="oid"/>_year.gif' onerror="this.src='images/noday.gif'" /></li>
    		</ul>
    	</div>
	</div>    
	
	<div id="div2">
		 	<div id="imgPanel"><span id="anypic"></span></div>
	 </div>
