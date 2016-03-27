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
    <meta HTTP-EQUIV="refresh" Content="300">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>全局故障监控</title>
	 <link rel='StyleSheet' href="css/topoInit.css" type="text/css" />
    <link rel="stylesheet" type="text/css" href="css/ext-all.css"/>
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>
	
	<STYLE type="text/css">
		#viewname {
		width:800px;
			text-align:left;
			float:left;
			
		}
		#valueList li {
			float:left;
		}
		#view1{
	width:100px;
	height:20px;
	padding-right:1px;
	margin:1px;
	text-align:left; 
	float:left;
	border:1px dashed #C9C4CA;
	overflow:hidden;
}
  div#girdshow {
 text-align:left;
 width:1050px;
 margin:0 auto;
  }
	</STYLE>
	
</head>
<body>
<%

String viewname = ( String )request.getAttribute( "viewname" );
String submitView = ( String )request.getAttribute( "submitView" );

		 
%>
<br>
<CENTER>
<FORM action="FaultEventSatusView.do" method=post name=submitView>
选择视图
<SELECT name=submitView size=1>
<s:iterator value="views" status="st" >
<OPTION value='<s:property value="id"/>'><s:property value="name"/></OPTION>
</s:iterator>
</SELECT>
<INPUT type="submit" value="提交" name=postsub>
</FROM>
<br>
<%--		<div id="menu">
<div id="outer">
	<div id="bodyDiv">
			<s:include value="right_menu.jsp"></s:include>
		</div>
	--%>
			<%--
		<div id="infoDiv">
			
			<div id="eventListDiv">
						<s:iterator value="deviceMap" id="tmp"> 
									<div id="viewname"><h2><s:property value='#tmp.key'/></h2></div>
					<s:form name="myform">
										<s:iterator value="#tmp.value" status="tmptable"> 
										<div id="view">
								        		<img src="<s:property value='picture'/>"/><br>
								        		<a href="portListAll.do?deviceId=<s:property value='id'/>&style=1&objName=<s:property value='name'/>"><s:property value='name'/></a>
								        </div>
								        </s:iterator>
					</s:form>
						</s:iterator>
		
			
		</div>
	</div>
</div>
</div>	
--%>		
<br>
	  <div id="girdshow"></div>	

	<SCRIPT type="text/javascript">
	var submitView ="<%= submitView %>";
	 var viewname= "<%= viewname %>";
	 
	
		Ext.onReady(function(){
	var reader = new Ext.data.JsonReader( {
		root : "faultnodeslists",
		totalProperty : 'count',
			fields : ['id', 'nodestutas', 'name', 'faultnodelist', 'count']
	});

	var proxy = new Ext.data.HttpProxy( {
		url : "json/Faultlists.do"
	});
	
	 store = new Ext.data.Store( {
		proxy : proxy,
		reader : reader
	});
	
	store.load( {
		params: {
			submitView:submitView
		}
	});
	store.setDefaultSort('name','ASC');   
	  
		var grid = new Ext.grid.GridPanel( {
		title: viewname+"节点状况",
		store : store,
		height : document.body.clientHeight - 37,
		width : 1000,
		autoExpandColumn: 'listfa',
		columns : [
				new Ext.grid.RowNumberer(),
				  {header : "id",dataIndex : 'id',hidden: true,	sortable : false,width:50	},
				 {header : "状态",dataIndex : 'nodestutas',sortable : false,width:90,renderer: stuasnodes},
	             {header: "名字",dataIndex : 'name',hidden: true,	sortable : true,width:110}, 
	             {header : "计数",dataIndex : 'count',hidden: true,	sortable : false,width:50	},
	             {id:"listfa",	header : "节点状态",dataIndex : 'faultnodelist',sortable : false,renderer: listfaults}
	     
	 ],
	 tbar : [ '->','-','->', {
			id : "basic",
			text : "返回",
			handler: function(){
				document.location.href = 'FaultEventSatusAll.do';
			}
			}
		],
		autoScroll : true,
		renderTo : 'girdshow'
	
	});
		function stuasnodes(val){
		var nodesnameall = val.split(";");
    	var nodesname = nodesnameall[0];
    	var nodespic = nodesnameall[1];
    	html= '<img src='+nodespic+' width="50" height="50"><br>'+nodesname;
    	          
    	
		return html;
		
		};
		function listfaults(val){
		var html = '';
		var routerstutas ="images/yellow_color.gif";
		var GlobalViewList = val;
		for(var i = 0; i < GlobalViewList.length; i++ ){
		if(GlobalViewList[i].status==2){	routerstutas ="images/green_color.gif";}
		if(GlobalViewList[i].status==0){	routerstutas ="images/red_color.gif";}
		if(GlobalViewList[i].status==3){	routerstutas ="images/red_color.gif";}
        		
		html += ' <div id="view1">'+
		       		'<img src='+routerstutas+' width="20" height="20">'+
		            '<a>'+GlobalViewList[i].name+'</a>'+
								        '</div>'
		}
		return html;
		
		}
     	grid.addListener('rowdblclick', rowClickFn);
	    function rowClickFn(grid, rowIndex) {
		var ts = grid.getStore();
		var ttps = ts.getAt(rowIndex);
		var ttid = ttps.get("id");
		var ttname = ttps.get("name");
		 document.location.href ="portListAll.do?deviceId="+ttid
	}
	
		
		
			var panel = new Ext.Panel({
				title:"故障监控",
				width:840,
				items:[
				{
					width:840,
					height:document.body.clientHeight*0.95 - 20,
					autoScroll:true,
					contentEl:"eventListDiv"
				}]
			});
			
//			panel.render("showDiv");
		});
	</SCRIPT>
	
</body>
</html>