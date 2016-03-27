<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="flex/css/Style.css" />
<link rel='StyleSheet' href="css/topoInit.css" type="text/css">
<script type="text/javascript" src="flex/js/FusionCharts.js"></script>
<script type="text/javascript" src="js/ext-base.js"></script>
<script type="text/javascript" src="js/ext-all.js"></script>
<script type="text/javascript">
		Ext.onReady(function(){
			var sm = new Ext.grid.CheckboxSelectionModel();
			var cm = new Ext.grid.ColumnModel([
				//new Ext.grid.RowNumberer(),//自动显示行号
				/* {hearder:'ID',dataIndex:'id',sortable:true},//每一行对应表格的一列 */
				{header:'Network_type',dataIndex:'region'},
				{header:'Interruption',dataIndex:'interruption'},
				{header:'Connection',dataIndex:'connection'},
				{header:'Interrupt_rate',dataIndex:'interrupt_rate'}
			]); 
			//var cm = new Ext.grid.ColumModel(...) 负责创建表格的列信息
			//header:首部显示文本
			//dataIndex:列对应的记录集字段
			//sortable:是否可排序
			//renderer:渲染函数
			//format列格式化信息
			/*数据存储器Store*/
			var reader = new Ext.data.JsonReader({
				root : 'reachability',
				fields : ['region','interruption','connection','interrupt_rate']
			});
			var reachability = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'json/getv4v6Reachability.do'
						}),
				//创建JsonReader读取router记录
				reader : reader
				});
			reachability.load();
			var grid = new Ext.grid.GridPanel({
				renderTo:'grid',//指示表格渲染到什么地方去
				store:reachability,//数据存储器
				cm:cm,//列定义信息
				autoHeight:true,
			});
			}
		);
</script>
</head>
<body>
<table width="80%" border="0" cellspacing="0" cellpadding="0" align="right">
<tr>
<td><div id="header1"><a href="v4v6Reachability.do" style="text-decoration:none;">可达性</a></div></td>
<td><div id="header2"><a href="v4v6performanceTask.do" style="text-decoration:none;">延时</a></div></td>
<td><div id="header3"><a href="v4v6Loss.do" style="text-decoration:none;">丢包</a></div></td>
<td><div id="header4"><a href="v4v6Reordering.do" style="text-decoration:none;">乱序</a></div></td>
</tr>
</table>
<div id="outer">
		<div id="bodyDiv">
			<div id="infoDiv">
			<div id="menu">
					<s:include value="left_menu.jsp"></s:include>
				</div>
			<div id = "grid" ></div>				
			</div>
		</div>
</div>
</body>
</html>